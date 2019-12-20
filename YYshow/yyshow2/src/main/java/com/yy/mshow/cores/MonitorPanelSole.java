package com.yy.mshow.cores;

import android.graphics.Rect;
import java.util.Locale;
import junit.framework.Assert;

public final class MonitorPanelSole implements MonitorPanel {
  public final MonitorPanel.Portion sole = new MonitorPanel.Portion("sole");
  
  public MonitorPanel deepCopy() {
    MonitorPanelSole monitorPanelSole = new MonitorPanelSole();
    monitorPanelSole.sole.linkedSlot.set(this.sole.linkedSlot.get());
    return monitorPanelSole;
  }
  
  public Rect evalRect(Size paramSize, MonitorPanel.Portion paramPortion) {
    Assert.assertEquals(this.sole, paramPortion);
    return new Rect(0, 0, paramSize.width, paramSize.height);
  }
  
  public MonitorPanel.Portion[] getPortions() { return new MonitorPanel.Portion[] { this.sole }; }
  
  public String toString() { return String.format(Locale.CHINA, "(PanelSole sole:%s)", new Object[] { this.sole }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\MonitorPanelSole.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */