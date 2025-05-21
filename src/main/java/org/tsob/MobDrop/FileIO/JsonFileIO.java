package org.tsob.MobDrop.FileIO;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.tsob.MobDrop.MobDrop;

import javax.annotation.Nonnull;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonFileIO implements IJsonFileIO {

  private transient String fileName;
  private transient String url = null;
  protected JsonNode data = null;
  protected final ObjectMapper objectMapper = new ObjectMapper();

  public JsonFileIO(@Nonnull String fileName) {
    this.fileName = fileName;
  }

  public JsonFileIO(String url, @Nonnull String fileName) {
    this.fileName = fileName;
    this.url = url;
  }

  @Override
  public String getFileName() {
    return fileName;
  }

  protected void setFileName(String fileName) {
    this.fileName = fileName;
    readFile();
  }

  @Override
  public String getPath() {
    String fullUrl = fileName;
    if (url == null || url.isEmpty())
      fullUrl = "./" + MobDrop.plugin.getDataFolder().toString() + "/" + fileName;
    else
      fullUrl = "./" + MobDrop.plugin.getDataFolder().toString() + "/" + url + "/" + fileName;
    return fullUrl;
  }

  protected void setUrl(String url) {
    this.url = url;
    readFile();
  }

  @Override
  public Object getJsonObject() {
    if (data == null)
      reloadFile();
    return data;
  }

  @Override
  public String getString(String path) {
    if (data == null)
      reloadFile();
    JsonNode node = getNodeByPath(path);
    return node != null && node.isTextual() ? node.asText() : null;
  }

  @Override
  public List<String> getStringList(String path) {
    if (data == null)
      reloadFile();
    JsonNode node = getNodeByPath(path);
    List<String> result = new ArrayList<>();
    if (node != null && node.isArray()) {
      for (JsonNode n : node) {
        result.add(n.asText());
      }
    }
    return result;
  }

  protected JsonNode getNodeByPath(String path) {
    if (data == null)
      return null;
    String[] parts = path.split("\\.");
    JsonNode node = data;
    for (String part : parts) {
      node = node.get(part);
      if (node == null)
        break;
    }
    return node;
  }

  protected void readFile() {
    File fileLoad;
    String fullUrl = fileName;
    if (url == null || url.isEmpty())
      fileLoad = new File(MobDrop.plugin.getDataFolder(), fileName);
    else {
      fileLoad = new File("./" + MobDrop.plugin.getDataFolder().toString() + "/" + url + "/" + fileName);
      fullUrl = url + "\\" + fileName;
    }
    if (!fileLoad.exists())
      MobDrop.plugin.saveResource(fullUrl, true);
    try {
      data = objectMapper.readTree(fileLoad);
    } catch (IOException e) {
      e.printStackTrace();
      data = null;
    }
  }

  @Override
  public boolean reloadFile() {
    readFile();
    return reloadcmd();
  }

  @Override
  public boolean reloadcmd() {
    return true;
  }
}