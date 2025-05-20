package org.tsob.MobDrop.Task;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.tsob.MobDrop.DataBase.Mob;
import org.tsob.MobDrop.InventoryGUI.InventoryMobAdd;

public class CustomMobNameTask extends BukkitRunnable{
  private final Mob mob;
  private final Player player;
  
    public CustomMobNameTask(Player player ,Mob mob) {
      this.mob = mob;
      this.player = player;
    }

    @Override
    public void run() {
      // What you want to schedule goes here
      InventoryMobAdd.getInventory(mob).open(player);
    }

}
