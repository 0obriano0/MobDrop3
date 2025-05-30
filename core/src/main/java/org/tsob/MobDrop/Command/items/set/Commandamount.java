package org.tsob.MobDrop.Command.items.set;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tsob.MobDrop.Command.mainCommandSystem;
import org.tsob.MobDrop.DataBase.DataBase;
import org.tsob.MobDrop.DataBase.Itemset;

public class Commandamount extends mainCommandSystem{
  
  public Commandamount() {
    super(  "items.set.amount",
        "/mobdrop items set amount 設定物品數量",
        new ArrayList<String>(Arrays.asList("mobdrop.admin.items.set.amount")));
  }
  
  @Override
  public void run(Player player, String commandLabel, Command command, String[] args) throws Exception {
    ItemStack setitem = player.getInventory().getItemInMainHand();
    if (!setitem.getType().toString().equals("AIR")) {
      if(args.length >= 1) {
        String str = args[0].replaceAll("&", "§");
        Itemset item = new Itemset(setitem);
        item.setAmount(Integer.parseInt(str));
        player.getInventory().setItemInMainHand(item.getItemStack());
      }
    } else {
      DataBase.sendMessage(player,DataBase.fileMessage.getString("Command.HandNoItem"));
    }
  }
}
