package org.tsob.MobDrop.Listener;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.tsob.MobDrop.MobDrop;
import org.tsob.MobDrop.DataBase.DataBase;
import org.tsob.MobDrop.DataBase.Itemset;
import org.tsob.MobDrop.DataBase.MobItem;
import org.tsob.MobDrop.DataBase.itemset.Glow;

public class DeathListener implements Listener{
  
  @SuppressWarnings("deprecation")
  @EventHandler
    public void onEntityDeathEvents(EntityDeathEvent event)
    {
    LivingEntity entityDeth = event.getEntity();
      // 判斷是否為玩家殺死的
      if (entityDeth.getKiller() != null && entityDeth.getKiller() instanceof Player){
        Player killBy = entityDeth.getKiller();
        Map<String, MobItem> mobitems = null;
        String sEntitlyName = "";
        try {
          if (entityDeth.getCustomName() != null && DataBase.CustomMobsMap.containsKey(entityDeth.getCustomName().toUpperCase())) {
            sEntitlyName = entityDeth.getCustomName().toUpperCase();
            mobitems = DataBase.CustomMobsMap.get(sEntitlyName).MobItems;
          } else if (entityDeth.getCustomName() == null && DataBase.NormalMobsMap.containsKey(entityDeth.getType().getName().toUpperCase())) {
            sEntitlyName = entityDeth.getType().getName().toUpperCase();
            mobitems = DataBase.NormalMobsMap.get(sEntitlyName).MobItems;
          }
        } catch (NullPointerException e) {
          return;
        }
        
        // 判斷是否有掉落物清單
        if (mobitems != null)
        {
          // 取得掉落物清單
          MobItem MobDropItem;
          // 迴圈判斷是否掉落物品
          for (Map.Entry<String, MobItem> entry : mobitems.entrySet())
          {
            MobDropItem = entry.getValue();
            // 判斷世界是否正確
            //if (customItem.OnlyWorld.equals("") || customItem.OnlyWorld.toUpperCase().equals(entityDeth.getWorld().getName().toUpperCase()))
            //{
              // 取得基數(從1~10000中抽一個號碼)
                int iChance = (int)(Math.random() * 10000 + 1);
                // 判斷物品掉落機率(乘以100後)是否小於基數
                if (iChance <= (MobDropItem.Chance * 100))
                {  
                  // 判定掉落數量
                  ItemStack MobDropItem_ = DataBase.items.get(MobDropItem.getItemNo()).getItemStack();
                    int items_num = 1;
                  if(MobDropItem.Quantity < MobDropItem.Quantity_max) {
                    items_num = (int)(Math.random() * (MobDropItem.Quantity_max-MobDropItem.Quantity+1) + MobDropItem.Quantity);
                  } else {
                    items_num = MobDropItem.Quantity;
                  }
                  
                  //實測最大掉落堆疊數量為127
                  for(int count = 1; count <=(items_num/127) ; count++) {
                    this.ItemDrop(killBy, MobDropItem, entityDeth, MobDropItem_, 127);
                  }
                  
                  int Amount = items_num-((int)(items_num/127))*127;
                  if(Amount > 0) {
                    this.ItemDrop(killBy, MobDropItem, entityDeth, MobDropItem_, Amount);
                  }
                      
                  //顯示訊息用的
                  MobDropItem_.setAmount(items_num);
                  // 顯示掉落訊息
                  if(MobDrop.plugin.getConfig().getBoolean("GobalMessage.Show",true) && MobDrop.plugin.getConfig().getDouble("GobalMessage.Chance",20) >= MobDropItem.Chance) 
                    MobDrop.server.broadcastMessage("§b" + DataBase.fileMessage.getString("Message.Title") + " " + formatmessage(DataBase.fileMessage.getString("Message.Gobal_MobDropItem"), killBy, sEntitlyName, MobDropItem, MobDropItem_));
                    // broadcastItemMessage(MobDropItem_, DataBase.fileMessage.getString("Message.Title"));
                
                  DataBase.sendMessage(killBy, "§f" + formatmessage(DataBase.fileMessage.getString("Message.MobDropItem"), killBy, sEntitlyName, MobDropItem, MobDropItem_));
                }
            //}
          }
        }
      }
    }
  
  private String formatmessage(String message, Player player,String MobName,MobItem MobDropItem,ItemStack Item) {
    return message.replaceAll("%player%", player.getName()).replaceAll("%mob%",DataBase.fileMessage.GetEntityName(MobName)).replaceAll("%item%",DataBase.items.get(MobDropItem.getItemNo()).getItemName()).replaceAll("%item_num%","" + Item.getAmount());
  }
  
  private void ItemDrop(Player killBy, MobItem MobDropItem, LivingEntity entityDeth,ItemStack MobDropItem_, int Amount) {
    MobDropItem_.setAmount(Amount);
    
    // 判定掉落
//    if(killBy.getInventory().firstEmpty() == -1)
        Item drop = entityDeth.getWorld().dropItemNaturally(entityDeth.getLocation(), MobDropItem_);
//    else
//      killBy.getInventory().addItem(MobDropItem_);
        
        //發光設定
    if(MobDrop.plugin.getConfig().getBoolean("Glow.Show")) {
      drop.setGlowing(true);
          Glow.setGlowColor(Glow.getColor(DataBase.getDropColorByChance(MobDropItem.Chance)), drop);
    }
    
    Itemset itemset = new Itemset(drop.getItemStack());
    //名稱設定
    if(MobDrop.plugin.getConfig().getBoolean("Holo.Show")) {
      String CustomName = MobDrop.plugin.getConfig().getString("Holo.Drop_Format")
                                                    .replaceAll("%item%", itemset.getItemName())
                                                    .replaceAll("%player%",killBy.getName())
                                                    .replaceAll("&",ChatColor.COLOR_CHAR + "");
      if(Amount >=2) {
        CustomName = CustomName + MobDrop.plugin.getConfig().getString("Holo.Drop_Count")
                                                            .replaceAll("%amount%", Amount + "")
                                                            .replaceAll("&",ChatColor.COLOR_CHAR + "");
      }
      drop.setCustomName(CustomName);
      drop.setCustomNameVisible(true);
    }
    DataBase.Print("Item: " + itemset.getItemName() + " UUID: " + drop.getUniqueId());
  }

  private void broadcastItemMessage(ItemStack itemStack, String title) {
    Itemset itemset = new Itemset(itemStack);
    String nbtString = itemset.getItemNbtString().replace("\"", "\\\"");
    String displayName = (itemStack.hasItemMeta() && itemStack.getItemMeta().hasDisplayName())
        ? itemStack.getItemMeta().getDisplayName()
        : itemStack.getType().name();

    String tellraw = "[\"\","
            + "{\"text\":\"§b" + title + " \"},"
            + "{\"text\":\"[" + displayName + "]\","
            + "\"color\":\"aqua\","
            + "\"hoverEvent\":{\"action\":\"show_item\",\"value\":\"" + nbtString + "\"}},"
            + "{\"text\":\" x" + itemStack.getAmount() + "\"}"
            + "]";
    Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tellraw @a " + tellraw);
  }
}
