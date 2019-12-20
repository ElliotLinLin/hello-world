package com.yy.mshow.cores;

import android.graphics.Rect;
import java.util.Locale;
import kits.observables.ObservableValue;

public interface MonitorPanel {
  MonitorPanel deepCopy();
  
  Rect evalRect(Size paramSize, Portion paramPortion);
  
  Portion[] getPortions();
  
  public static class Portion {
    public ObservableValue<Slot> linkedSlot = new ObservableValue(null);
    
    public final String name;
    
    Portion(String param1String) { this.name = param1String; }
    
    public boolean equals(Object param1Object) { return (this == param1Object); }
    
    public int hashCode() { return this.name.hashCode(); }
    
    public String toString() { return String.format(Locale.CHINA, "(Portion %s: %s)", new Object[] { this.name, this.linkedSlot.get() }); }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\MonitorPanel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */