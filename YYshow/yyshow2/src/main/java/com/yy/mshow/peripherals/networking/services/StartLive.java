package com.yy.mshow.peripherals.networking.services;

import com.yy.mshow.cores.Program;
import java.util.Locale;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public class StartLive extends Job {
  private final Program program;
  
  private final String yyAuthToken;
  
  public StartLive(Program paramProgram, String paramString) {
    this.program = paramProgram;
    this.yyAuthToken = paramString;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) {
            if (StartLive.this.yyAuthToken != null)
              param1Map.put("token", StartLive.this.yyAuthToken); 
          }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/start", new Object[] { this.program.code.get() }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\StartLive.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */