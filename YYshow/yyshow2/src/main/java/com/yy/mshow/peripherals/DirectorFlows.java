package com.yy.mshow.peripherals;

import com.yy.mshow.cores.Output;
import com.yy.mshow.cores.OutputSetting;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.RTMPOutputSetting;
import com.yy.mshow.peripherals.localmixer.LocalMixer;
import com.yy.mshow.peripherals.networking.LiveOutput;
import com.yy.mshow.peripherals.networking.services.CreateOutput;
import com.yy.mshow.peripherals.networking.services.DeleteOutput;
import com.yy.mshow.peripherals.networking.services.GetOutputs;
import com.yy.mshow.peripherals.networking.services.HeartBeat;
import com.yy.mshow.peripherals.networking.services.StartLive;
import com.yy.mshow.peripherals.networking.services.StopLive;
import com.yy.mshow.peripherals.networking.services.UpdateChannels;
import com.yy.udbauth.AuthEvent;
import com.yy.udbauth.AuthSDK;
import com.yy.udbauth.ui.AuthUI;
import com.yy.udbauth.ui.tools.OnCreditLoginListener;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import junit.framework.Assert;
import kits.Statechart;
import kits.reactor.Reactor;

public final class DirectorFlows extends Statechart<DirectorFlows.State, DirectorFlows.Signal> {
  private final Authorization auth;
  
  public Delegate delegate;
  
  private final LocalMixer localMixer;
  
  private final ArrayList<OutputSetting> outputSettings = new ArrayList();
  
  private final ArrayList<Output> outputs = new ArrayList();
  
  private final Program program;
  
  private final Reactor reactor;
  
  private final YYOutputAuth yyAuth;
  
  private String yyOutputToken;
  
  public DirectorFlows(Authorization paramAuthorization, Program paramProgram, YYOutputAuth paramYYOutputAuth, LocalMixer paramLocalMixer, Reactor paramReactor) {
    super(State.Initial);
    this.auth = paramAuthorization;
    this.program = paramProgram;
    this.reactor = paramReactor;
    this.yyAuth = paramYYOutputAuth;
    this.localMixer = paramLocalMixer;
  }
  
  private void acquireYYTokenAutomatically() {
    Integer integer = (Integer)this.program.yyOutputSetting.uid.get();
    Assert.assertNotNull(integer);
    String str1 = String.valueOf(integer);
    String str2 = AuthSDK.getCredit(str1);
    AuthUI.getInstance().loginWithCredit(str1, str2, new OnCreditLoginListener() {
          public void onResult(AuthEvent.LoginEvent param1LoginEvent) {
            if (param1LoginEvent.uiAction == 0) {
              DirectorFlows.access$002(DirectorFlows.this, DirectorFlows.this.yyAuth.getPlainToken());
              Assert.assertNotNull(DirectorFlows.this.yyOutputToken);
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigSucceed);
              return;
            } 
            DirectorFlows.this.schedule(DirectorFlows.Signal.SigFailed);
          }
          
          public void onTimeout() { DirectorFlows.this.schedule(DirectorFlows.Signal.SigFailed); }
        });
  }
  
  private void cleanOutputs() { this.reactor.launch(new GetOutputs(this.program, new GetOutputs.Completion(this) {
            public void onSuccess(List<LiveOutput> param1List) {
              for (Output output : param1List)
                DirectorFlows.this.reactor.launch(new DeleteOutput(DirectorFlows.this.program, output)); 
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigSucceed);
            }
          })); }
  
  private void createOneOutput() {
    Assert.assertFalse(this.outputSettings.isEmpty());
    this.reactor.launch(false, new CreateOutput(this.program, (OutputSetting)this.outputSettings.remove(0)), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            if (!param1Boolean) {
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigFailed);
              return;
            } 
            if (DirectorFlows.this.outputSettings.isEmpty()) {
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigSucceed);
              return;
            } 
            DirectorFlows.this.schedule(DirectorFlows.Signal.SigMore);
          }
        });
  }
  
  private void doInitialHeartbeat() { this.reactor.launch(false, new HeartBeat(this.program, null), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            DirectorFlows.Signal signal;
            DirectorFlows directorFlows = DirectorFlows.this;
            if (param1Boolean) {
              signal = DirectorFlows.Signal.SigSucceed;
            } else {
              signal = DirectorFlows.Signal.SigFailed;
            } 
            directorFlows.schedule(signal);
          }
        }); }
  
  private void doStartLive() {
    String str = null;
    if (((Boolean)this.program.yyOutputSetting.enabled.get()).booleanValue()) {
      str = this.yyAuth.getPlainToken();
      Assert.assertNotNull(str);
    } 
    this.reactor.launch(false, new StartLive(this.program, str), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            DirectorFlows.Signal signal;
            DirectorFlows directorFlows = DirectorFlows.this;
            if (param1Boolean) {
              signal = DirectorFlows.Signal.SigSucceed;
            } else {
              signal = DirectorFlows.Signal.SigFailed;
            } 
            directorFlows.schedule(signal);
          }
        });
  }
  
  private void doStopLive() { this.reactor.launch(false, new StopLive(this.program), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) { DirectorFlows.this.schedule(DirectorFlows.Signal.SigSucceed); }
        }); }
  
  private void doUpdateChannels() { this.reactor.launch(false, new UpdateChannels(this.auth, this.program, this.localMixer), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            DirectorFlows.Signal signal;
            DirectorFlows directorFlows = DirectorFlows.this;
            if (param1Boolean) {
              signal = DirectorFlows.Signal.SigSucceed;
            } else {
              signal = DirectorFlows.Signal.SigFailed;
            } 
            directorFlows.schedule(signal);
          }
        }); }
  
  private boolean hasEnabledOutputSetting() {
    this.outputs.clear();
    this.outputSettings.clear();
    if (((Boolean)this.program.yyOutputSetting.enabled.get()).booleanValue())
      this.outputSettings.add(this.program.yyOutputSetting); 
    for (RTMPOutputSetting rTMPOutputSetting : this.program.rtmpOutputSettings.get()) {
      if (((Boolean)rTMPOutputSetting.enabled.get()).booleanValue())
        this.outputSettings.add(rTMPOutputSetting); 
    } 
    return !this.outputSettings.isEmpty();
  }
  
  private void postFailure(String paramString) {
    if (this.delegate != null)
      this.delegate.didOccurFailure(paramString); 
  }
  
  private void queryOutputs() { this.reactor.launch(false, new GetOutputs(this.program, new GetOutputs.Completion(this) {
            public void onSuccess(List<LiveOutput> param1List) {
              DirectorFlows.this.outputs.addAll(param1List);
              if (DirectorFlows.this.outputs.isEmpty()) {
                DirectorFlows.this.schedule(DirectorFlows.Signal.SigNoOutputs);
                return;
              } 
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigSucceed);
            }
          }), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            if (!param1Boolean)
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigFailed); 
          }
        }); }
  
  private void removeOneOutput() {
    Assert.assertFalse("It must has some Output to be removed", this.outputs.isEmpty());
    this.reactor.launch(false, new DeleteOutput(this.program, (Output)this.outputs.remove(0)), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            if (DirectorFlows.this.outputs.isEmpty()) {
              DirectorFlows.this.schedule(DirectorFlows.Signal.SigSucceed);
              return;
            } 
            DirectorFlows.this.schedule(DirectorFlows.Signal.SigMore);
          }
        });
  }
  
  protected void performSchedule(Signal paramSignal) {
    switch (paramSignal) {
      default:
        return;
      case OpStart:
        Assert.assertEquals(State.Initial, this.state);
        Assert.assertNotNull(this.program.code.get());
        if (hasEnabledOutputSetting()) {
          if (((Boolean)this.program.yyOutputSetting.enabled.get()).booleanValue()) {
            setState(State.ProCheckYYOutput);
            return;
          } 
          setState(State.QueryingExistedOutputs);
          return;
        } 
        postFailure("请配置输出平台");
        setState(State.Initial);
        return;
      case OpStop:
        Assert.assertEquals(State.Living, this.state);
        setState(State.RequestingStop);
        return;
      case OpResumeFromCrashes:
        Assert.assertEquals(State.Initial, this.state);
        setState(State.Living);
        return;
      case SigNoOutputs:
        Assert.assertEquals(State.QueryingExistedOutputs, this.state);
        setState(State.CreatingOutputs);
        return;
      case SigSucceed:
        switch ((State)this.state) {
          default:
            throw new RuntimeException("触发了未知的状态/事件 匹配: " + getState() + "/" + paramSignal);
          case OpStop:
            setState(State.QueryingExistedOutputs);
            return;
          case OpResumeFromCrashes:
            setState(State.RemovingExistedOutputs);
            return;
          case SigNoOutputs:
            setState(State.CreatingOutputs);
            return;
          case SigSucceed:
            setState(State.FirstHeartBeating);
            return;
          case SigMore:
            setState(State.RequestingStart);
            return;
          case SigFailed:
            setState(State.UpdateChannels);
            return;
          case null:
            setState(State.Living);
            return;
          case null:
            setState(State.Cleaning);
            return;
          case null:
            break;
        } 
        setState(State.Initial);
        return;
      case SigMore:
        if (this.state == State.RemovingExistedOutputs) {
          setState(State.RemovingExistedOutputs);
          return;
        } 
        if (this.state == State.CreatingOutputs) {
          setState(State.CreatingOutputs);
          return;
        } 
      case SigFailed:
        break;
    } 
    if (EnumSet.of(State.RequestingStop, State.Cleaning).contains(getState()) && getState() == State.ProCheckYYOutput)
      postFailure("YY输出认证过期，请检查"); 
    setState(State.Initial);
  }
  
  protected void setState(State paramState) {
    super.setState(paramState);
    switch (paramState) {
      default:
        return;
      case OpStart:
        this.program.isOnLive.set(Boolean.valueOf(false));
        return;
      case OpStop:
        acquireYYTokenAutomatically();
        return;
      case OpResumeFromCrashes:
        queryOutputs();
        return;
      case SigNoOutputs:
        removeOneOutput();
        return;
      case SigSucceed:
        createOneOutput();
        return;
      case SigMore:
        doInitialHeartbeat();
        return;
      case SigFailed:
        doStartLive();
        return;
      case null:
        doUpdateChannels();
        return;
      case null:
        this.program.isOnLive.set(Boolean.valueOf(true));
        return;
      case null:
        doStopLive();
        return;
      case null:
        break;
    } 
    cleanOutputs();
  }
  
  public static interface Delegate {
    void didOccurFailure(String param1String);
  }
  
  public enum Signal {
    OpResumeFromCrashes, OpStart, OpStop, SigFailed, SigMore, SigNoOutputs, SigSucceed;
    
    static  {
      OpResumeFromCrashes = new Signal("OpResumeFromCrashes", 2);
      SigNoOutputs = new Signal("SigNoOutputs", 3);
      SigSucceed = new Signal("SigSucceed", 4);
      SigMore = new Signal("SigMore", 5);
      SigFailed = new Signal("SigFailed", 6);
      $VALUES = new Signal[] { OpStart, OpStop, OpResumeFromCrashes, SigNoOutputs, SigSucceed, SigMore, SigFailed };
    }
  }
  
  public enum State {
    Initial, Living, Cleaning, CreatingOutputs, FirstHeartBeating, ProCheckYYOutput, QueryingExistedOutputs, RemovingExistedOutputs, RequestingStart, RequestingStop, UpdateChannels;
    
    static  {
      CreatingOutputs = new State("CreatingOutputs", 4);
      FirstHeartBeating = new State("FirstHeartBeating", 5);
      RequestingStart = new State("RequestingStart", 6);
      UpdateChannels = new State("UpdateChannels", 7);
      Living = new State("Living", 8);
      RequestingStop = new State("RequestingStop", 9);
      Cleaning = new State("Cleaning", 10);
      $VALUES = new State[] { 
          Initial, ProCheckYYOutput, QueryingExistedOutputs, RemovingExistedOutputs, CreatingOutputs, FirstHeartBeating, RequestingStart, UpdateChannels, Living, RequestingStop, 
          Cleaning };
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\DirectorFlows.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */