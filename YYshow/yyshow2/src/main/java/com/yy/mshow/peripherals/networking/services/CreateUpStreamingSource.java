package com.yy.mshow.peripherals.networking.services;

import java.util.Locale;
import java.util.Map;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.bodies.JsonObjectBody;
import kits.reactor.methods.POST;

public final class CreateUpStreamingSource extends Job {
  private final Integer programCode;
  
  private final Integer userId;
  
  public CreateUpStreamingSource(Integer paramInteger1, Integer paramInteger2) {
    this.userId = paramInteger2;
    this.programCode = paramInteger1;
  }
  
  protected Reactor.Method method() { return new POST(new JsonObjectBody(this) {
          protected void fill(Map<String, Object> param1Map) {
            param1Map.put("name", "Android Source");
            param1Map.put("type", "anchor");
            param1Map.put("url", "");
            param1Map.put("user_id", CreateUpStreamingSource.this.userId);
          }
        }); }
  
  protected String url() { return String.format(Locale.CHINA, "/v1/programs/%d/sources/create", new Object[] { this.programCode }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\CreateUpStreamingSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */