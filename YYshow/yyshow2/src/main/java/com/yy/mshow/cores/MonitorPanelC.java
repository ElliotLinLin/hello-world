package com.yy.mshow.cores;

import android.graphics.Rect;
import java.util.Locale;

public class MonitorPanelC implements MonitorPanel {
  private MonitorPanel.Portion left = new MonitorPanel.Portion("left");
  
  private MonitorPanel.Portion rightBottom = new MonitorPanel.Portion("rightBottom");
  
  private MonitorPanel.Portion rightTop = new MonitorPanel.Portion("rightTop");
  
  public MonitorPanel deepCopy() {
    MonitorPanelC monitorPanelC = new MonitorPanelC();
    monitorPanelC.left.linkedSlot.set(this.left.linkedSlot.get());
    monitorPanelC.rightTop.linkedSlot.set(this.rightTop.linkedSlot.get());
    monitorPanelC.rightBottom.linkedSlot.set(this.rightBottom.linkedSlot.get());
    return monitorPanelC;
  }
  
  public Rect evalRect(Size paramSize, MonitorPanel.Portion paramPortion) {
    if (paramPortion == this.left)
      return new Rect(0, 0, paramSize.width / 2, paramSize.height); 
    if (paramPortion == this.rightTop) {
      byte b = 0;
      int i = paramSize.height / 2;
      return new Rect(paramSize.width / 2, b, paramSize.width, i);
    } 
    if (paramPortion == this.rightBottom) {
      int j = paramSize.height / 2;
      int i = paramSize.height;
      return new Rect(paramSize.width / 2, j, paramSize.width, i);
    } 
    throw new RuntimeException("传入的portion不属于MonitorPanelC: " + paramPortion);
  }
  
  public MonitorPanel.Portion[] getPortions() { return new MonitorPanel.Portion[] { this.left, this.rightTop, this.rightBottom }; }
  
  public String toString() { return String.format(Locale.CHINA, "(PanelC l:%s, rt:%s, rb:%s)", new Object[] { this.left, this.rightTop, this.rightBottom }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\MonitorPanelC.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */