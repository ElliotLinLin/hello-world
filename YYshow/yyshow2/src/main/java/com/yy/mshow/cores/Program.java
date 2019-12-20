package com.yy.mshow.cores;

import com.yy.mshow.peripherals.playercenter.RTMPSource;
import java.util.List;
import junit.framework.Assert;
import kits.observables.ObservableList;
import kits.observables.ObservableValue;

public class Program {
  private static Bitrate b1000K;
  
  public static final Bitrate[] bitrateChoices;
  
  private static Resolution r720p = new Resolution(1280, 720);
  
  public static final Resolution[] resChoices;
  
  public List<String> WANRTMPPushURLs;
  
  public final ObservableValue<Bitrate> bitrate = new ObservableValue(b1000K);
  
  public final ObservableValue<Integer> code = new ObservableValue(null);
  
  public InterOperations interOperations;
  
  public final ObservableValue<Boolean> isOnLive = new ObservableValue(Boolean.valueOf(false));
  
  public final ObservableValue<Integer> liveSpan = new ObservableValue(Integer.valueOf(0));
  
  public final ObservableValue<Integer> number = new ObservableValue(null);
  
  public final ObservableValue<MonitorPanel> pgm = new ObservableValue(null);
  
  public final ObservableValue<MonitorPanel> pvw = new ObservableValue(null);
  
  public final ObservableValue<Resolution> resolution = new ObservableValue(r720p);
  
  public final ObservableList<String> rtmpInputHistories = new ObservableList();
  
  public final ObservableList<RTMPOutputSetting> rtmpOutputSettings = new ObservableList();
  
  public final ObservableList<RTMPSource> rtmpSources = new ObservableList();
  
  public final Slot[] slots = { new Slot(1), new Slot(2), new Slot(3), new Slot(4) };
  
  public final YYOutputSetting yyOutputSetting = new YYOutputSetting();
  
  static  {
    b1000K = new Bitrate(1000);
    resChoices = new Resolution[] { new Resolution(1920, 1080), r720p, new Resolution(1024, 576) };
    bitrateChoices = new Bitrate[] { new Bitrate(500), new Bitrate(800), b1000K, new Bitrate(1500), new Bitrate(2000), new Bitrate(2500), new Bitrate(3000), new Bitrate(3500), new Bitrate(4000) };
  }
  
  public Program() { setupDefaultPanels(); }
  
  private Slot findSlot(Stream paramStream) {
    for (Slot slot : this.slots) {
      if (slot.stream.get() == paramStream)
        return slot; 
    } 
    return null;
  }
  
  private void setupDefaultPGMPanel() {
    MonitorPanelSole monitorPanelSole = new MonitorPanelSole();
    monitorPanelSole.sole.linkedSlot.set(this.slots[0]);
    this.pgm.set(monitorPanelSole);
  }
  
  private void setupDefaultPVWPanel() {
    MonitorPanelSole monitorPanelSole = new MonitorPanelSole();
    monitorPanelSole.sole.linkedSlot.set(this.slots[0]);
    this.pvw.set(monitorPanelSole);
  }
  
  private void setupDefaultPanels() {
    setupDefaultPGMPanel();
    setupDefaultPVWPanel();
  }
  
  public boolean addStreamIfPossible(Stream paramStream) {
    Slot slot = findSlot(null);
    if (slot == null)
      return false; 
    slot.stream.set(paramStream);
    return true;
  }
  
  public void applyPVWtoPGM() { this.pgm.set(((MonitorPanel)this.pvw.get()).deepCopy()); }
  
  public void createRTMPSource(String paramString) {
    Assert.assertNotNull(this.interOperations);
    this.interOperations.doCreateRTMPSource(paramString);
    if (this.rtmpInputHistories.contains(paramString).booleanValue())
      this.rtmpInputHistories.remove(paramString); 
    this.rtmpInputHistories.add(0, paramString);
    if (this.rtmpInputHistories.size() > 4)
      this.rtmpInputHistories.remove(4); 
  }
  
  public void deleteRTMPSource(RTMPSource paramRTMPSource) {
    Assert.assertTrue(this.rtmpSources.contains(paramRTMPSource).booleanValue());
    this.interOperations.doDeleteRTMPSource(paramRTMPSource);
  }
  
  public boolean hasEmptySlot() { return (findSlot(null) != null); }
  
  public boolean isReady() { return (this.code.get() != null); }
  
  public void removeStream(Stream paramStream) {
    Slot slot = findSlot(paramStream);
    Assert.assertNotNull(slot);
    slot.stream.set(null);
  }
  
  public void restore(Boolean paramBoolean) { this.isOnLive.set(paramBoolean); }
  
  public void restore(List<RTMPSource> paramList) {
    boolean bool;
    if (this.rtmpSources.size() == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    Assert.assertTrue(bool);
    for (RTMPSource rTMPSource : paramList)
      this.rtmpSources.add(rTMPSource); 
  }
  
  public void swapMonitorPanels() {
    MonitorPanel monitorPanel1 = (MonitorPanel)this.pgm.get();
    MonitorPanel monitorPanel2 = (MonitorPanel)this.pvw.get();
    this.pgm.set(new MonitorPanelSole());
    this.pvw.set(monitorPanel1);
    this.pgm.set(monitorPanel2);
  }
  
  public void swapStreamInSlots(Slot paramSlot1, Slot paramSlot2) {
    Stream stream = (Stream)paramSlot1.stream.get();
    paramSlot1.stream.set(paramSlot2.stream.get());
    paramSlot2.stream.set(stream);
  }
  
  public String toString() {
    StringBuilder stringBuilder = new StringBuilder();
    stringBuilder.append("(Program: \n");
    stringBuilder.append("  code: ").append(this.code).append("\n");
    stringBuilder.append("  isLiving: ").append(this.isOnLive).append("\n");
    stringBuilder.append("  liveSpan: ").append(this.liveSpan).append("\n");
    stringBuilder.append("  resolution: ").append(this.resolution).append("\n");
    stringBuilder.append("  yyOutputSetting: ").append(this.yyOutputSetting).append("\n");
    stringBuilder.append("  rtmpOutputSettings: ").append(this.rtmpOutputSettings).append("\n");
    stringBuilder.append("  slots:\n");
    for (Slot slot : this.slots)
      stringBuilder.append("    ").append(slot).append("\n"); 
    stringBuilder.append("  pgm:\n");
    stringBuilder.append("    ").append(this.pgm).append("\n");
    stringBuilder.append(")");
    return stringBuilder.toString();
  }
  
  public static interface InterOperations {
    void doCreateRTMPSource(String param1String);
    
    void doDeleteRTMPSource(RTMPSource param1RTMPSource);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\Program.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */