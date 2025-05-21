package org.tsob.MobDrop.FileIO;

import org.bukkit.Bukkit;
import org.tsob.MobDrop.DataBase.DataBase;

import com.fasterxml.jackson.databind.JsonNode;

public class JsonFileIOMinecraftLang extends JsonFileIO {

  /**
   * @param langFromConfig 語言設定，抓不到自動用 en_us
   */
  public JsonFileIOMinecraftLang(String langFromConfig) {
    super(getLangPath(resolveLang(langFromConfig)), getLangFileName(getMinecraftVersion()));
  }

  /**
   * 取得 Minecraft 版本號（主.次.修）
   */
  private static String getMinecraftVersion() {
    String bukkitVer = Bukkit.getBukkitVersion(); // 例如 1.21.5-R0.1-SNAPSHOT
    if (bukkitVer == null || bukkitVer.isEmpty())
      return "unknown";
    String[] arr = bukkitVer.split("-")[0].split("\\.");
    if (arr.length >= 3)
      return arr[0] + "." + arr[1] + "." + arr[2];
    if (arr.length == 2)
      return arr[0] + "." + arr[1] + ".0";
    return bukkitVer.split("-")[0];
  }

  /**
   * 判斷語言，抓不到就回傳 en
   */
  private static String resolveLang(String langFromConfig) {
    if (langFromConfig == null || langFromConfig.isEmpty())
      return "en";
    return langFromConfig;
  }

  /**
   * 取得完整路徑
   */
  private static String getLangPath(String lang) {
    if (lang == null || lang.isEmpty())
      lang = "en";
    // message/zh_TW/minecraft/1.21.5.json

    String path = String.format("message/%s/minecraft", lang);
    DataBase.Print(path);
    return path;
  }

  private static String getLangFileName(String version) {
    if (version == null || version.isEmpty())
      version = "1.21.5"; // 預設版本號
    // message/zh_TW/minecraft/1.21.5.json

    String path = String.format("%s.json", version);
    DataBase.Print(path);
    return path;
  }

  /**
   * 如果需要重新指定語言和版本，可用這方法。
   */
  public void reloadWithLangAndVersion(String lang) {
    // setFileName(getMinecraftVersion());
    setUrl(getLangPath(resolveLang(lang)));
    reloadFile();
  }


  // 讀取Minecraft 文件很特殊 沒有階層
  @Override
  protected JsonNode getNodeByPath(String path) {
    if (data == null)
      return null;
    String[] parts = new String[] { path };
    JsonNode node = data;
    for (String part : parts) {
      node = node.get(part);
      if (node == null)
        break;
    }
    return node;
  }
}