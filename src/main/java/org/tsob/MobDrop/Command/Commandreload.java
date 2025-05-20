package org.tsob.MobDrop.Command;

import java.util.ArrayList;
import java.util.Arrays;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.tsob.MobDrop.MobDrop;
import org.tsob.MobDrop.DataBase.DataBase;

public class Commandreload extends mainCommandSystem{
  public Commandreload() {
    super(  "reload",
        "/mobdrop reload 重新讀取資料",
        new ArrayList<String>(Arrays.asList("mobdrop.admin.reload")));
  }
  
  @Override
  public void run(CommandSender sender, String commandLabel, Command command, String[] args) throws Exception {
    DataBase.Print(DataBase.fileMessage.getString("Message.Data_Re_read"));
    MobDrop.reload();
    DataBase.Print(DataBase.fileMessage.getString("Message.Data_Re_read_Complete"));
  }
  
  @Override
  public void run(Player player, String commandLabel, Command command, String[] args) throws Exception {
    DataBase.sendMessage(player,DataBase.fileMessage.getString("Message.Data_Re_read"));
    MobDrop.reload();
    DataBase.sendMessage(player,DataBase.fileMessage.getString("Message.Data_Re_read_Complete"));
  }
}
