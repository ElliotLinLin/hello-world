package com.yy.mshow.peripherals.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.yy.mshow.peripherals.service.Nginx;
import kits.PreferencesStorage;

public class NginxAccessor implements PreferencesStorage.Store, PreferencesStorage.Load {
  private final Nginx nginx;
  
  public NginxAccessor(Nginx paramNginx) { this.nginx = paramNginx; }
  
  public void decode(String paramString) {
    JsonObject jsonObject = (new JsonParser()).parse(paramString).getAsJsonObject();
    if (jsonObject.get("version") != null)
      this.nginx.installedVersion = jsonObject.get("version").getAsInt(); 
  }
  
  public String encoded() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("version", Integer.valueOf(this.nginx.installedVersion));
    return jsonObject.toString();
  }
  
  public String guard() { return "{}"; }
  
  public String key() { return "Nginx"; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\storage\NginxAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */