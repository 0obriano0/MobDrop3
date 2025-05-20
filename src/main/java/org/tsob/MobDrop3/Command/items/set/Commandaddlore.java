package org.tsob.MobDrop3.Command.items.set;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tsob.MobDrop3.Command.mainCommandSystem;
import org.tsob.MobDrop3.DataBase.DataBase;
import org.tsob.MobDrop3.DataBase.Itemset;

public class Commandaddlore extends mainCommandSystem{
  
  public Commandaddlore() {
    super(  "items.set.addlore",
        "/mobdrop items set addlore 設定物品名稱",
        new ArrayList<String>(Arrays.asList("mobdrop.admin.items.set.addlore")));
  }
  
  @Override
  public void run(Player player, String commandLabel, Command command, String[] args) throws Exception {
    ItemStack setitem = player.getInventory().getItemInMainHand();
    if (!setitem.getType().toString().equals("AIR")) {
      if(args.length >= 1) {
        String totalstr = "";
        boolean first = true;
        for(String str : args) {
          totalstr = totalstr + (first ? "" : " ") + str;
          first = false;
        }
        Itemset item = new Itemset(setitem).addLore(totalstr.replaceAll("&", "§"));
        
        player.getInventory().setItemInMainHand(item.getItemStack());
      }
    } else {
      DataBase.sendMessage(player,DataBase.fileMessage.getString("Command.HandNoItem"));
    }
  }
}
