package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.yy.mshow.MSAccount;
import com.yy.mshow.MSProgram;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class GetUpStreamingLiveState extends Job {
  private final MSAccount account;
  
  private final Completion completion;
  
  private final MSProgram program;
  
  public GetUpStreamingLiveState(int paramInt1, int paramInt2, Completion paramCompletion) { this(new MSAccount(paramInt1, "", ""), new MSProgram(paramInt2), paramCompletion); }
  
  private GetUpStreamingLiveState(MSAccount paramMSAccount, MSProgram paramMSProgram, Completion paramCompletion) {
    this.account = paramMSAccount;
    this.program = paramMSProgram;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    JsonElement jsonElement;
    if (paramObject == null || !((JsonElement)paramObject).isJsonArray()) {
      this.completion.onSuccess(false, false);
      return;
    } 
    paramObject = ((JsonElement)paramObject).getAsJsonArray().iterator();
    while (true) {
      if (paramObject.hasNext()) {
        jsonElement = (JsonElement)paramObject.next();
        if (Integer.valueOf(((JsonObject)jsonElement).get("source").getAsInt()).equals(Integer.valueOf(this.account.id)))
          break; 
        continue;
      } 
      return;
    } 
    boolean bool1 = ((JsonObject)jsonElement).get("is_in_pgm").getAsBoolean();
    boolean bool2 = ((JsonObject)jsonElement).get("is_in_pvw").getAsBoolean();
    this.completion.onSuccess(Boolean.valueOf(bool1).booleanValue(), Boolean.valueOf(bool2).booleanValue());
  }
  
  protected String url() { return this.program.makeUrl("channel_info?statue=statue"); }
  
  public static interface Completion {
    void onSuccess(boolean param1Boolean1, boolean param1Boolean2);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetUpStreamingLiveState.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */