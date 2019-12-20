package com.yy.mshow.peripherals.networking.services;

import com.google.gson.JsonElement;
import com.yy.mshow.cores.Program;
import java.util.Locale;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public final class HeartBeat extends Job {
  private final Completion completion;
  
  private final Program program;
  
  public HeartBeat(Program paramProgram, Completion paramCompletion) {
    this.program = paramProgram;
    this.completion = paramCompletion;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) { param1Map.put("timeout", Integer.valueOf(60000)); }
        }); }
  
  protected void process(Object paramObject) {
    if (this.completion != null) {
      boolean bool = ((JsonElement)paramObject).getAsJsonObject().get("program_status").getAsString().equals("active");
      this.completion.onSuccess(Boolean.valueOf(bool));
    } 
  }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/heartbeats/update", new Object[] { this.program.code.get() }); }
  
  public static interface Completion {
    void onSuccess(Boolean param1Boolean);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\HeartBeat.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */