package com.yy.mshow.cores;

import android.graphics.Rect;
import java.util.Locale;

public class MonitorPanelPIP implements MonitorPanel {
  private MonitorPanel.Portion major = new MonitorPanel.Portion("major");
  
  private MonitorPanel.Portion minor = new MonitorPanel.Portion("minor");
  
  public MonitorPanel deepCopy() {
    MonitorPanelPIP monitorPanelPIP = new MonitorPanelPIP();
    monitorPanelPIP.major.linkedSlot.set(this.major.linkedSlot.get());
    monitorPanelPIP.minor.linkedSlot.set(this.minor.linkedSlot.get());
    return monitorPanelPIP;
  }
  
  public Rect evalRect(Size paramSize, MonitorPanel.Portion paramPortion) {
    if (paramPortion == this.major)
      return new Rect(0, 0, paramSize.width, paramSize.height); 
    if (paramPortion == this.minor) {
      int i = (int)(paramSize.width * 0.38D);
      int j = (int)(paramSize.height * 0.38D);
      int k = paramSize.width - i;
      int m = paramSize.height - j;
      return new Rect(k, m, k + i, m + j);
    } 
    throw new RuntimeException("该Portion不属于MonitorPanelPIP: " + paramPortion);
  }
  
  public MonitorPanel.Portion[] getPortions() { return new MonitorPanel.Portion[] { this.major, this.minor }; }
  
  public String toString() { return String.format(Locale.CHINA, "(PanelPIP major:%s, minor:%s)", new Object[] { this.major, this.minor }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\MonitorPanelPIP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */