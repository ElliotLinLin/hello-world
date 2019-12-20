package com.yy.mshow.peripherals.networking.services;

import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.playercenter.RTMPSource;
import java.util.Locale;
import java.util.Map;
import junit.framework.Assert;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public class CreateRTMPAnchorSource extends Job {
  private final Program program;
  
  private final RTMPSource source;
  
  public CreateRTMPAnchorSource(Program paramProgram, RTMPSource paramRTMPSource) {
    Assert.assertNotNull(paramProgram.code.get());
    this.program = paramProgram;
    this.source = paramRTMPSource;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) {
            param1Map.put("name", "RTMP Source");
            param1Map.put("type", "rtmp-anchor");
            param1Map.put("url", this.this$0.source.urlString);
            param1Map.put("user_id", this.this$0.source.userId);
          }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/sources/create", new Object[] { this.program.code.get() }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\CreateRTMPAnchorSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */