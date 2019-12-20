package com.yy.mshow.cores;

import android.graphics.Rect;
import java.util.Locale;
import junit.framework.Assert;

public class MonitorPanelHalf implements MonitorPanel {
  public final MonitorPanel.Portion left = new MonitorPanel.Portion("left");
  
  public final MonitorPanel.Portion right = new MonitorPanel.Portion("right");
  
  public MonitorPanel deepCopy() {
    MonitorPanelHalf monitorPanelHalf = new MonitorPanelHalf();
    monitorPanelHalf.left.linkedSlot.set(this.left.linkedSlot.get());
    monitorPanelHalf.right.linkedSlot.set(this.right.linkedSlot.get());
    return monitorPanelHalf;
  }
  
  public Rect evalRect(Size paramSize, MonitorPanel.Portion paramPortion) {
    boolean bool;
    if (paramPortion == this.left || paramPortion == this.right) {
      bool = true;
    } else {
      bool = false;
    } 
    Assert.assertTrue(bool);
    return (paramPortion == this.left) ? new Rect(0, 0, paramSize.width / 2, paramSize.height) : new Rect(paramSize.width / 2, 0, paramSize.width, paramSize.height);
  }
  
  public MonitorPanel.Portion[] getPortions() { return new MonitorPanel.Portion[] { this.left, this.right }; }
  
  public String toString() { return String.format(Locale.CHINA, "(PanelHalf l:%s, r:%s)", new Object[] { this.left, this.right }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\MonitorPanelHalf.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */