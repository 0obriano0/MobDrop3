package org.tsob.MobDrop.FileIO;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.tsob.MobDrop.AnsiColor;
import org.tsob.MobDrop.DataBase.DataBase;

public class FileMessage extends FileIO{
  public FileMessage() {
    super("message", tools.getLang() + "/Base.yml");
  }
  
  public Map<Integer,String> TimerLeft = new HashMap<Integer,String>();
  
  @Override
  public boolean reloadcmd() {
    this.setFileName(tools.getLang() + "/Base.yml");
    TimerLeft.clear();
    if(this.data.contains("Timer.TimeLeft")) {
      Pattern p = Pattern.compile("%%[0-9]*%%");//java.util.regex.Pattern;
        
      for(String str : data.getStringList("Timer.TimeLeft")) {
        Matcher matcher = p.matcher(str);//java.util.regex.Pattern;
        if(matcher.find()) {
          String getstr = matcher.group(0);
          TimerLeft.put(Integer.parseInt(getstr.replace("%", "")), str.replace("&","§").replace(getstr,"%time%"));
        }
      }
    }
    return true;
  }
  
  /**
   * 錯誤訊息顯示
   * @param title 標題
   * @param name 名稱
   */
  public void errorMessage(String title,String name) {
    DataBase.Print(AnsiColor.RED + "[Loadlanguage] " + title + " -> " + name + " data load error，use default..." + AnsiColor.RESET);
  }
}
