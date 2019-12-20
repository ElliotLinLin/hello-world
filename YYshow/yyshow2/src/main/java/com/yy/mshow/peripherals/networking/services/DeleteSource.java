package com.yy.mshow.peripherals.networking.services;

import java.util.Locale;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.reactor.methods.POST;

public final class DeleteSource extends Job {
  private final int programCode;
  
  private final int sourceID;
  
  public DeleteSource(Integer paramInteger1, Integer paramInteger2) {
    this.programCode = paramInteger1.intValue();
    this.sourceID = paramInteger2.intValue();
  }
  
  protected Reactor.Method method() { return new POST(); }
  
  protected String url() { return String.format(Locale.getDefault(), "/v1/programs/%d/sources/%d/delete", new Object[] { Integer.valueOf(this.programCode), Integer.valueOf(this.sourceID) }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\services\DeleteSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */