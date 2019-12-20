package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class GetProgramCodeFromNumber extends Job {
  private Completion completion;
  
  private String number;
  
  public GetProgramCodeFromNumber(String paramString, Completion paramCompletion) {
    this.number = paramString;
    this.completion = paramCompletion;
  }
  
  public Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    paramObject = (JsonElement)paramObject;
    if (paramObject.isJsonObject()) {
      paramObject = paramObject.getAsJsonObject();
      if (paramObject.has("program_id")) {
        this.completion.onSuccess(Integer.valueOf(Integer.parseInt(paramObject.get("program_id").getAsString())));
        return;
      } 
    } 
    this.completion.onFailure();
  }
  
  public String url() { return "/v1/qrcode/" + this.number; }
  
  public static interface Completion {
    void onFailure();
    
    void onSuccess(Integer param1Integer);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetProgramCodeFromNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */