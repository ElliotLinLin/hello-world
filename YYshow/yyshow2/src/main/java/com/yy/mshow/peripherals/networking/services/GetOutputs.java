package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.networking.LiveOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.GET;

public class GetOutputs extends Job {
  private final Completion completion;
  
  private final Program program;
  
  public GetOutputs(Program paramProgram, Completion paramCompletion) {
    this.program = paramProgram;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new GET(); }
  
  protected void process(Object paramObject) {
    ArrayList arrayList = new ArrayList();
    paramObject = ((JsonElement)paramObject).getAsJsonObject().get("outputs").getAsJsonArray();
    for (byte b = 0; b < paramObject.size(); b++)
      arrayList.add(new LiveOutput(Integer.valueOf(paramObject.get(b).getAsJsonObject().get("output_id").getAsInt()))); 
    this.completion.onSuccess(arrayList);
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d", new Object[] { this.program.code.get() }); }
  
  public static interface Completion {
    void onSuccess(List<LiveOutput> param1List);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\GetOutputs.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */