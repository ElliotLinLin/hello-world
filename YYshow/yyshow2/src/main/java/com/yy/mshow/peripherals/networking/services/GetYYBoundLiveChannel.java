package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class GetYYBoundLiveChannel extends Job {
  private static final String ChannelIDKey = "300";
  
  private final Completion completion;
  
  private final String percentEscapeToken;
  
  private final int yyUID;
  
  public GetYYBoundLiveChannel(int paramInt, String paramString, Completion paramCompletion) {
    Assert.assertNotNull(paramCompletion);
    this.yyUID = paramInt;
    this.percentEscapeToken = paramString;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    paramObject = ((JsonElement)paramObject).getAsJsonObject();
    if (paramObject.get("result").getAsInt() != 0) {
      this.completion.onFailure();
      return;
    } 
    JsonObject jsonObject = paramObject.getAsJsonObject("o2odata").getAsJsonObject("UserBase").getAsJsonObject(String.valueOf(this.yyUID));
    paramObject = null;
    String str = jsonObject.get("300").getAsString();
    if (!str.isEmpty())
      paramObject = Integer.valueOf(str); 
    this.completion.onSuccess(paramObject);
  }
  
  protected String url() {
    Assert.assertNotNull(this.percentEscapeToken);
    return "/v1/yy/yyp/query_bound_live_room_2?yyuid=" + this.yyUID + "&token=" + this.percentEscapeToken;
  }
  
  public static interface Completion {
    void onFailure();
    
    void onSuccess(Integer param1Integer);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetYYBoundLiveChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */