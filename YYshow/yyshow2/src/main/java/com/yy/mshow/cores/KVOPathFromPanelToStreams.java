package com.yy.mshow.cores;

import kits.observables.ObservableList;
import kits.observables.ObservableMap;
import kits.observables.ObservableValue;
import org.junit.Assert;

public class KVOPathFromPanelToStreams {
  public final ObservableValue<MonitorPanel> panel = new ObservableValue(null);
  
  private final ObservableValue<MonitorPanel> panelSource;
  
  public final ObservableValue<MonitorPanel.Portion[]> portions = new ObservableValue(null);
  
  public final ObservableMap<MonitorPanel.Portion, Slot> slots = new ObservableMap();
  
  public final ObservableList<Stream> streams = new ObservableList();
  
  public KVOPathFromPanelToStreams(ObservableValue<MonitorPanel> paramObservableValue) {
    this.panelSource = paramObservableValue;
    this.slots.register(this, Boolean.valueOf(false), new ObservableMap.Observer<MonitorPanel.Portion, Slot>() {
          public void didAdd(MonitorPanel.Portion param1Portion, Slot param1Slot) {
            Assert.assertNotNull("按道理来说是不可能的，仅出于测试目的会用到这样的能力", param1Slot);
            param1Slot.stream.hook(KVOPathFromPanelToStreams.this, true, new ObservableValue.Observer<Stream>() {
                  public void updated(boolean param2Boolean, Stream param2Stream1, Stream param2Stream2) {
                    if (!param2Boolean && param2Stream1 != null)
                      KVOPathFromPanelToStreams.this.streams.remove(param2Stream1); 
                    if (param2Stream2 != null)
                      KVOPathFromPanelToStreams.this.streams.add(param2Stream2); 
                  }
                });
          }
          
          public void willRemove(MonitorPanel.Portion param1Portion, Slot param1Slot) {
            param1Slot.stream.unhook(KVOPathFromPanelToStreams.this);
            if (param1Slot.stream.get() != null)
              KVOPathFromPanelToStreams.this.streams.remove(param1Slot.stream.get()); 
          }
        });
    this.portions.hook(this, false, new ObservableValue.Observer<MonitorPanel.Portion[]>() {
          public void updated(boolean param1Boolean, MonitorPanel.Portion[] param1ArrayOfPortion1, MonitorPanel.Portion[] param1ArrayOfPortion2) {
            if (param1ArrayOfPortion2 != null) {
              int i = param1ArrayOfPortion2.length;
              byte b = 0;
              while (true) {
                if (b < i) {
                  final MonitorPanel.Portion portion = param1ArrayOfPortion2[b];
                  portion.linkedSlot.hook(KVOPathFromPanelToStreams.this, true, new Observer<Slot>() {
                        public void updated(boolean param2Boolean, Slot param2Slot1, Slot param2Slot2) { KVOPathFromPanelToStreams.this.slots.put(portion, param2Slot2); }
                      });
                  b++;
                  continue;
                } 
                return;
              } 
            } 
          }
          
          public void willChange(MonitorPanel.Portion[] param1ArrayOfPortion1, MonitorPanel.Portion[] param1ArrayOfPortion2) {
            if (param1ArrayOfPortion1 != null) {
              int i = param1ArrayOfPortion1.length;
              byte b = 0;
              while (true) {
                if (b < i) {
                  MonitorPanel.Portion portion = param1ArrayOfPortion1[b];
                  portion.linkedSlot.unhook(KVOPathFromPanelToStreams.this);
                  KVOPathFromPanelToStreams.this.slots.remove(portion);
                  b++;
                  continue;
                } 
                return;
              } 
            } 
          }
        });
    this.panel.hook(this, false, new ObservableValue.Observer<MonitorPanel>() {
          public void updated(boolean param1Boolean, MonitorPanel param1MonitorPanel1, MonitorPanel param1MonitorPanel2) {
            if (param1MonitorPanel2 != null)
              KVOPathFromPanelToStreams.this.portions.set(param1MonitorPanel2.getPortions()); 
          }
          
          public void willChange(MonitorPanel param1MonitorPanel1, MonitorPanel param1MonitorPanel2) { KVOPathFromPanelToStreams.this.portions.set(null); }
        });
  }
  
  protected void finalize() throws Throwable {
    this.panel.unhook(this);
    this.portions.unhook(this);
    this.slots.unregister(this);
    this.streams.unregister(this);
    super.finalize();
  }
  
  public void sync() throws Throwable { this.panelSource.hook(this, true, new ObservableValue.Observer<MonitorPanel>() {
          public void updated(boolean param1Boolean, MonitorPanel param1MonitorPanel1, MonitorPanel param1MonitorPanel2) { KVOPathFromPanelToStreams.this.panel.set(param1MonitorPanel2); }
          
          public void willChange(MonitorPanel param1MonitorPanel1, MonitorPanel param1MonitorPanel2) { KVOPathFromPanelToStreams.this.panel.set(null); }
        }); }
  
  public void unSync() throws Throwable {
    this.panelSource.unhook(this);
    this.panel.set(null);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\KVOPathFromPanelToStreams.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */