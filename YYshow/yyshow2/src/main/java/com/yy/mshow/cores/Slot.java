package com.yy.mshow.cores;

import java.util.Locale;
import kits.observables.ObservableValue;

public final class Slot {
  public final int index;
  
  public ObservableValue<Stream> stream = new ObservableValue(null);
  
  public Slot(int paramInt) { this.index = paramInt; }
  
  public String toString() { return String.format(Locale.CHINA, "(Slot %d: stream: %s)", new Object[] { Integer.valueOf(this.index), this.stream }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\Slot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */