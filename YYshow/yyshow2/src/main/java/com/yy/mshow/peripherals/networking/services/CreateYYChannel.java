package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.yy.mshow.peripherals.networking.YYChannel;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.POST;

public class CreateYYChannel extends Job {
  private final Completion completion;
  
  private final String percentEscapeToken;
  
  private final int yyUID;
  
  public CreateYYChannel(int paramInt, String paramString, Completion paramCompletion) {
    Assert.assertNotNull(paramCompletion);
    this.yyUID = paramInt;
    this.percentEscapeToken = paramString;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new POST(); }
  
  protected void process(Object paramObject) {
    paramObject = ((JsonElement)paramObject).getAsJsonObject();
    paramObject = new YYChannel(paramObject.get("cid").getAsInt(), paramObject.get("name").getAsString());
    this.completion.onSuccess(paramObject);
  }
  
  protected String url() { return "/v1/yy/webdb/user/" + this.yyUID + "/create_session?token=" + this.percentEscapeToken; }
  
  public static interface Completion {
    void onSuccess(YYChannel param1YYChannel);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\CreateYYChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */