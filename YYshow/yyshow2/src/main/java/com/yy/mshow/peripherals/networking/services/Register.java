package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;
import org.apache.commons.codec.digest.HmacUtils;

public final class Register extends Job {
  private final Completion completion;
  
  private final String hashKey;
  
  private final String password;
  
  private final String username;
  
  public Register(String paramString1, String paramString2, String paramString3, Completion paramCompletion) {
    this.username = paramString2;
    this.password = paramString3;
    this.hashKey = paramString1;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) {
            long l = System.currentTimeMillis();
            String str = HmacUtils.hmacSha1Hex(Register.this.hashKey, "YY" + Register.this.username + l + Register.this.password);
            param1Map.put("user_name", Register.this.username);
            param1Map.put("passcode", Register.this.password);
            param1Map.put("user_type", "YY");
            param1Map.put("time", Long.valueOf(l));
            param1Map.put("hash", str);
          }
        }); }
  
  protected void process(Object paramObject) {
    paramObject = Integer.valueOf(((JsonElement)paramObject).getAsJsonObject().get("user_id").getAsString());
    this.completion.onSuccess(paramObject);
  }
  
  protected String url() { return "/v1/users/create"; }
  
  public static interface Completion {
    void onSuccess(Integer param1Integer);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\Register.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */