package com.yy.mshow.cores;

import android.graphics.Rect;
import java.util.Locale;

public class MonitorPanelGrids implements MonitorPanel {
  private MonitorPanel.Portion leftBottom = new MonitorPanel.Portion("leftBottom");
  
  private MonitorPanel.Portion leftTop = new MonitorPanel.Portion("leftTop");
  
  private MonitorPanel.Portion rightBottom = new MonitorPanel.Portion("rightBottom");
  
  private MonitorPanel.Portion rightTop = new MonitorPanel.Portion("rightTop");
  
  public MonitorPanel deepCopy() {
    MonitorPanelGrids monitorPanelGrids = new MonitorPanelGrids();
    monitorPanelGrids.leftTop.linkedSlot.set(this.leftTop.linkedSlot.get());
    monitorPanelGrids.rightTop.linkedSlot.set(this.rightTop.linkedSlot.get());
    monitorPanelGrids.leftBottom.linkedSlot.set(this.leftBottom.linkedSlot.get());
    monitorPanelGrids.rightBottom.linkedSlot.set(this.rightBottom.linkedSlot.get());
    return monitorPanelGrids;
  }
  
  public Rect evalRect(Size paramSize, MonitorPanel.Portion paramPortion) {
    int i = paramSize.width / 2;
    int j = paramSize.height / 2;
    if (paramPortion == this.leftTop)
      return new Rect(0, 0, i, j); 
    if (paramPortion == this.rightTop)
      return new Rect(i, 0, paramSize.width, j); 
    if (paramPortion == this.leftBottom)
      return new Rect(0, j, i, paramSize.height); 
    if (paramPortion == this.rightBottom)
      return new Rect(i, j, paramSize.width, paramSize.height); 
    throw new RuntimeException("该portion不属于该MonitorPanelGrids: " + paramPortion);
  }
  
  public MonitorPanel.Portion[] getPortions() { return new MonitorPanel.Portion[] { this.leftTop, this.rightTop, this.leftBottom, this.rightBottom }; }
  
  public String toString() { return String.format(Locale.CHINA, "(PanelGrids lt:%s, rt:%s, lb:%s rb:%s)", new Object[] { this.leftTop, this.rightTop, this.leftBottom, this.rightBottom }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\MonitorPanelGrids.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */