package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;

import java.util.Locale;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;
import org.apache.commons.codec.digest.HmacUtils;

public class Login extends Job {
  private final Authorization auth;
  
  private final Completion completion;
  
  private final String hashKey;
  
  public Login(String paramString, Authorization paramAuthorization, Completion paramCompletion) {
    this.completion = paramCompletion;
    this.auth = paramAuthorization;
    this.hashKey = paramString;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) {
            long l = System.currentTimeMillis();
            String str = HmacUtils.hmacSha1Hex(Login.this.hashKey, String.valueOf(l) + Login.this.auth.getPassword());
            param1Map.put("passcode", Login.this.auth.getPassword());
            param1Map.put("time", Long.valueOf(l));
            param1Map.put("hash", str);
          }
        }); }
  
  protected void process(Object paramObject) {
    paramObject = ((JsonElement)paramObject).getAsJsonObject();
    this.completion.onSuccess(paramObject.get("token").getAsString());
  }
  
  protected String url() { return String.format(Locale.getDefault(), "/v1/users/%d/token/create", new Object[] { this.auth.getUID() }); }
  
  public static interface Completion {
    void onSuccess(String param1String);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\Login.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */