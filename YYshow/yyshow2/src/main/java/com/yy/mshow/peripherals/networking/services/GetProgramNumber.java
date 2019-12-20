package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.yy.mshow.cores.Program;
import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.POST;

public class GetProgramNumber extends Job {
  private final Completion completion;
  
  private final Program program;
  
  public GetProgramNumber(Program paramProgram, Completion paramCompletion) {
    this.program = paramProgram;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new POST(); }
  
  protected void process(Object paramObject) {
    int i = ((JsonElement)paramObject).getAsJsonObject().get("qrcode").getAsInt();
    this.completion.onSuccess(Integer.valueOf(i));
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/qrcode", new Object[] { this.program.code.get() }); }
  
  public static interface Completion {
    void onSuccess(Integer param1Integer);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetProgramNumber.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */