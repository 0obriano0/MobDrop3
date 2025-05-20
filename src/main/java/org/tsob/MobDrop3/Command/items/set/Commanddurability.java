package org.tsob.MobDrop3.Command.items.set;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.tsob.MobDrop3.Command.mainCommandSystem;
import org.tsob.MobDrop3.DataBase.DataBase;
import org.tsob.MobDrop3.DataBase.Itemset;

public class Commanddurability extends mainCommandSystem{
	public Commanddurability() {
		super(  "items.set.durability",
				"/mobdrop items set durability 設定物品耐久度",
				new ArrayList<String>(Arrays.asList("mobdrop.admin.items.set.durability")));
	}
	
	@Override
	public void run(Player player, String commandLabel, Command command, String[] args) throws Exception {
		ItemStack setitem = player.getInventory().getItemInMainHand();
		if (!setitem.getType().toString().equals("AIR")) {
			if(args.length >= 1) {
				String str = args[0].replaceAll("&", "§");
				Itemset item = new Itemset(setitem);
				item.setDurability(Short.parseShort(str));
				player.getInventory().setItemInMainHand(item.getItemStack());
			}
		} else {
			DataBase.sendMessage(player,DataBase.fileMessage.getString("Command.HandNoItem"));
		}
	}
}
