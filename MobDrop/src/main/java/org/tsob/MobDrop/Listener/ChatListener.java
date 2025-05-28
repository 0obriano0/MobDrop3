package org.tsob.MobDrop.Listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.scheduler.BukkitTask;
import org.tsob.MobDrop.MobDrop;
import org.tsob.MobDrop.DataBase.DataBase;
import org.tsob.MobDrop.DataBase.MessageSet;
import org.tsob.MobDrop.DataBase.Mob;
import org.tsob.MobDrop.Task.CustomMobNameTask;

public class ChatListener implements Listener{
  @EventHandler
  public void onPlayerChat(AsyncPlayerChatEvent event) {
    Player player = event.getPlayer();
    if(DataBase.NewCustomMobName.containsKey(player.getName())) {
      event.setCancelled(true);
      MessageSet data = DataBase.NewCustomMobName.remove(player.getName());
      if((System.currentTimeMillis() - data.getTime())/1000 < 15) {
        Mob mob = (Mob)data.getobject();
        mob.setName(event.getMessage().replaceAll("&", "§"));
        @SuppressWarnings("unused")
        BukkitTask task = new CustomMobNameTask(player,mob).runTaskLater(MobDrop.plugin, 5);
      }
    }
    
  }
}
