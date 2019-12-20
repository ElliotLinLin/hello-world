package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Locale;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public final class GetYYAccountBasics extends Job {
  private final Completion completion;
  
  private final String token;
  
  private final int uid;
  
  public GetYYAccountBasics(int paramInt, String paramString, Completion paramCompletion) {
    Assert.assertNotNull(paramString);
    this.uid = paramInt;
    this.token = paramString;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    JsonObject jsonObject = ((JsonElement)paramObject).getAsJsonObject().get("info").getAsJsonObject();
    paramObject = jsonObject.get("key_index").getAsJsonObject();
    JsonArray jsonArray = jsonObject.get("dataset").getAsJsonArray().get(0).getAsJsonArray();
    int i = paramObject.get("nick").getAsInt();
    int j = paramObject.get("hdlogo").getAsInt();
    String str2 = jsonArray.get(Integer.valueOf(i).intValue()).getAsString();
    String str1 = jsonArray.get(Integer.valueOf(j).intValue()).getAsString();
    paramObject = str1;
    if (str1.isEmpty())
      paramObject = null; 
    this.completion.onSuccess(new YYAccountBasics(str2, paramObject));
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/yy/webdb/user/%d?token=%s", new Object[] { Integer.valueOf(this.uid), this.token }); }
  
  public static interface Completion {
    void onSuccess(GetYYAccountBasics.YYAccountBasics param1YYAccountBasics);
  }
  
  public class YYAccountBasics {
    public final String avatarUrl;
    
    public final String nick;
    
    YYAccountBasics(String param1String1, String param1String2) {
      this.nick = param1String1;
      this.avatarUrl = param1String2;
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetYYAccountBasics.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */