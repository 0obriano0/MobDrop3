package org.tsob.MobDrop.Timer;

import java.util.Timer;
import java.util.TimerTask;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.tsob.MobDrop.MobDrop;
import org.tsob.MobDrop.DataBase.DataBase;
import org.tsob.MobDrop.DataBase.MessageSet;
import org.tsob.MobDrop.DataBase.Mob;
import org.tsob.MobDrop.Task.CustomMobNameTask;

public class CustomMobNameTimer {
  Timer t;
  Player player;
  
  public CustomMobNameTimer(Player player){
    this.player = player;
    
    TimerTask timerTask = new TimerTask() {
      @Override
      public void run() {
        main();
      }
    };
    
    t = new Timer();
    t.schedule(timerTask, 1000, 1000);
  }
  
  public void main() {
    MessageSet data = DataBase.NewCustomMobName.get(player.getName());
    if(data == null) {
        t.cancel();
    } else {
      int timeout = MobDrop.plugin.getConfig().getInt("Inventory.MessageSet.CustomName");
      
      if((System.currentTimeMillis() - data.getTime())/1000 >= timeout) {
        DataBase.NewCustomMobName.remove(player.getName());
        t.cancel();
        DataBase.sendMessage(player,DataBase.fileMessage.getString("Timer.TimeOut").replaceAll("%time%",timeout + ""));
        @SuppressWarnings("unused")
        BukkitTask task = new CustomMobNameTask(player,(Mob)data.getobject()).runTaskLater(MobDrop.plugin, 5);
        
      }else if(!DataBase.fileMessage.TimerLeft.isEmpty()){
        int timeleft = (int) (timeout-((System.currentTimeMillis() - data.getTime())/1000));
        if(DataBase.fileMessage.TimerLeft.containsKey(timeleft)) {
          DataBase.sendMessage(player,DataBase.fileMessage.TimerLeft.get(timeleft).replace("%time%", timeleft + ""));
        }
      }
    }
  }
}
