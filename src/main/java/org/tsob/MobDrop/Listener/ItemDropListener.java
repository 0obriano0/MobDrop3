package org.tsob.MobDrop.Listener;

import org.bukkit.ChatColor;
import org.bukkit.entity.Item;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.tsob.MobDrop.MobDrop;
import org.tsob.MobDrop.DataBase.Itemset;
import org.tsob.MobDrop.DataBase.itemset.Glow;

public class ItemDropListener implements Listener{
  @EventHandler
    public void itemDrop(ItemSpawnEvent e) {
    Item drop = e.getEntity();
    
    if(!drop.isCustomNameVisible() && MobDrop.plugin.getConfig().getBoolean("Holo.Show")) {
      Itemset itemset = new Itemset(drop.getItemStack());
      drop.setCustomName(itemset.getItemName());
      drop.setCustomNameVisible(true);
    }
    
    if(!drop.isGlowing() && MobDrop.plugin.getConfig().getString("Glow.Mode").toLowerCase().contains("all")) {
      drop.setGlowing(true);
      ChatColor color = Glow.getColor();
        Glow.setGlowColor(color, drop);
    }
  }
}
