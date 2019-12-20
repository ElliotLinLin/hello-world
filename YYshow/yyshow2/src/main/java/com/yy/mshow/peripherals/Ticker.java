package com.yy.mshow.peripherals;

import com.yy.mshow.cores.Program;
import kits.observables.ObservableValue;
import kits.trigger.PeriodicJob;
import kits.trigger.PeriodicTrigger;
import kits.trigger.TimerPulse;

public class Ticker {
  private PeriodicJob liveBeat;
  
  private PeriodicTrigger trigger = new PeriodicTrigger(new TimerPulse());
  
  public void close() { this.trigger.stop(); }
  
  public void open() { this.trigger.start(); }
  
  public void sync(final Program program) {
    this.trigger.addJob(new PeriodicJob(1000L, new PeriodicJob.Action(this) {
            public void onTrigger(PeriodicJob.Completion param1Completion) { param1Completion.onComplete(Boolean.valueOf(true)); }
          }), Boolean.valueOf(true));
    this.liveBeat = new PeriodicJob(1000L, new PeriodicJob.Action(this, paramProgram) {
          public void onTrigger(PeriodicJob.Completion param1Completion) {
            this.val$program.liveSpan.set(Integer.valueOf(((Integer)this.val$program.liveSpan.get()).intValue() + 1));
            param1Completion.onComplete(Boolean.valueOf(true));
          }
        });
    paramProgram.isOnLive.hook(this, true, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            if (param1Boolean2.booleanValue()) {
              Ticker.this.trigger.addJob(Ticker.this.liveBeat, Boolean.valueOf(true));
              return;
            } 
            Ticker.this.trigger.removeJob(Ticker.this.liveBeat);
            this.val$program.liveSpan.set(Integer.valueOf(0));
          }
        });
    this.trigger.start();
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\Ticker.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */