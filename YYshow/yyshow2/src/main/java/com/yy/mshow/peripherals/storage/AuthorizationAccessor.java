package com.yy.mshow.peripherals.storage;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import kits.PreferencesStorage;

public class AuthorizationAccessor implements PreferencesStorage.Store, PreferencesStorage.Load {
  private final Authorization auth;
  
  public AuthorizationAccessor(Authorization paramAuthorization) { this.auth = paramAuthorization; }
  
  public void decode(String paramString) {
    Integer integer = null;
    String str2 = null;
    String str1 = null;
    JsonObject jsonObject = (new JsonParser()).parse(paramString).getAsJsonObject();
    paramString = str2;
    if (jsonObject.get("user_id") != null) {
      integer = Integer.valueOf(jsonObject.get("user_id").getAsInt());
      paramString = jsonObject.get("user_name").getAsString();
      str1 = jsonObject.get("passcode").getAsString();
    } 
    if (integer != null && paramString != null && str1 != null) {
      this.auth.restore(integer, paramString, str1);
      return;
    } 
    this.auth.schedule(Authorization.Signal.OpStart);
  }
  
  public String encoded() {
    JsonObject jsonObject = new JsonObject();
    jsonObject.addProperty("user_id", this.auth.getUID());
    jsonObject.addProperty("user_name", this.auth.getUsername());
    jsonObject.addProperty("passcode", this.auth.getPassword());
    return jsonObject.toString();
  }
  
  public String guard() { return "{}"; }
  
  public String key() { return "auth"; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\storage\AuthorizationAccessor.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */