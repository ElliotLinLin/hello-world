package com.yy.mshow.peripherals.networking.services;

import android.util.SparseArray;
import com.google.gson.JsonElement;
import com.yy.mshow.peripherals.networking.YYChannel;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class BindYYLiveChannel extends Job {
  private static final SparseArray<String> errMsgMap = new SparseArray<String>() {
    
    };
  
  public YYChannel channel;
  
  private final Completion completion;
  
  private final String percentEscapeToken;
  
  private final int yyUID;
  
  public BindYYLiveChannel(int paramInt, String paramString, Completion paramCompletion) {
    Assert.assertNotNull(paramCompletion);
    this.yyUID = paramInt;
    this.percentEscapeToken = paramString;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    int i = ((JsonElement)paramObject).getAsJsonObject().get("result").getAsInt();
    if (i == 0) {
      this.completion.onSuccess(this.channel);
      return;
    } 
    if (i <= 10 && i > 0) {
      paramObject = "该频道已经是其他频道类型";
    } else {
      String str = (String)errMsgMap.get(i);
      paramObject = str;
      if (str == null)
        paramObject = (String)errMsgMap.get(1000); 
    } 
    this.completion.onFailure(this.channel, paramObject);
  }
  
  protected String url() { return "/v1/yy/yyp/bind_live_room_2?yyuid=" + this.yyUID + "&roomCid=" + this.channel.id + "&token=" + this.percentEscapeToken; }
  
  public static interface Completion {
    void onFailure(YYChannel param1YYChannel, String param1String);
    
    void onSuccess(YYChannel param1YYChannel);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\BindYYLiveChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */