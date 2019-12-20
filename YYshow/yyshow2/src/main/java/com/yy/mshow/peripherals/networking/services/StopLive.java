package com.yy.mshow.peripherals.networking.services;

import com.yy.mshow.cores.Program;
import java.util.Locale;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public class StopLive extends Job {
  private final Program program;
  
  public StopLive(Program paramProgram) { this.program = paramProgram; }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) { param1Map.put("liveAction", "liveAction"); }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/stop", new Object[] { this.program.code.get() }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\StopLive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */