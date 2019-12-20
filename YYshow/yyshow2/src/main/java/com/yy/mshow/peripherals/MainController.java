package com.yy.mshow.peripherals;

import android.content.Context;

import com.yy.mshow.cores.KVOPathFromPanelToStreams;
import com.yy.mshow.cores.Program;
import com.yy.mshow.cores.RTMPOutputSetting;
import com.yy.mshow.cores.Slot;
import com.yy.mshow.cores.Stream;
import com.yy.mshow.peripherals.localmixer.LocalMixer;
import com.yy.mshow.peripherals.networking.YYChannel;
import com.yy.mshow.peripherals.networking.services.CreateProgram;
import com.yy.mshow.peripherals.networking.services.CreateRTMPAnchorSource;
import com.yy.mshow.peripherals.networking.services.CreateUpStreamingSource;
import com.yy.mshow.peripherals.networking.services.DeleteSource;
import com.yy.mshow.peripherals.networking.services.GetProgramNumber;
import com.yy.mshow.peripherals.networking.services.GetProgramSources;
import com.yy.mshow.peripherals.networking.services.GetPushURLs;
import com.yy.mshow.peripherals.networking.services.HeartBeat;
import com.yy.mshow.peripherals.networking.services.Login;
import com.yy.mshow.peripherals.networking.services.Register;
import com.yy.mshow.peripherals.networking.services.UpdateChannels;
import com.yy.mshow.peripherals.networking.services.UpdateMixerSettings;
import com.yy.mshow.peripherals.playercenter.PlayerCenter;
import com.yy.mshow.peripherals.playercenter.RTMPSource;
import com.yy.mshow.peripherals.service.Nginx;
import com.yy.mshow.peripherals.storage.AuthorizationAccessor;
import com.yy.mshow.peripherals.storage.NginxAccessor;
import com.yy.mshow.peripherals.storage.ProgramAccessor;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import kits.PreferencesStorage;
import kits.Statechart;
import kits.observables.ObservableList;
import kits.observables.ObservableValue;
import kits.reactor.Job;
import kits.reactor.Reactor;
import kits.trigger.PeriodicJob;
import kits.trigger.PeriodicTrigger;
import kits.trigger.TimerPulse;
import okhttp3.Request;

public class MainController {
  private static String HashKey = "6B64028A5D2B5FA2458962C1A42CBF9A";
  
  private final AuthorizationAccessor accAuth;
  
  private final NginxAccessor accNginx;
  
  private final ProgramAccessor accProgram;
  
  private final Authorization auth;
  
  private HeartBeat heartBeat;
  
  private final LocalMixer mixer;
  
  private final Nginx nginx;
  
  private final PeriodicTrigger periodicJobs = new PeriodicTrigger(new TimerPulse(100L));
  
  private KVOPathFromPanelToStreams pgmPath;
  
  private final PlayerCenter playerCenter;
  
  private final Program program;
  
  private final Reactor reactor;
  
  private PreferencesStorage storage;
  
  private PeriodicJob updateMixerSettings;
  
  private PeriodicJob updateSlots;
  
  public MainController(Reactor paramReactor, Authorization paramAuthorization, final Program program, LocalMixer paramLocalMixer, PlayerCenter paramPlayerCenter, Nginx paramNginx) {
    this.reactor = paramReactor;
    this.auth = paramAuthorization;
    this.program = paramProgram;
    this.mixer = paramLocalMixer;
    this.playerCenter = paramPlayerCenter;
    this.nginx = paramNginx;
    this.pgmPath = new KVOPathFromPanelToStreams(paramProgram.pgm);
    this.accProgram = new ProgramAccessor(paramProgram);
    this.accAuth = new AuthorizationAccessor(paramAuthorization);
    this.accNginx = new NginxAccessor(paramNginx);
    this.periodicJobs.addJob(new PeriodicJob(10000L, new PeriodicJob.Condition(this) {
            public Boolean shouldTrigger() {
              if (MainController.this.heartBeat != null) {
                boolean bool1 = true;
                return Boolean.valueOf(bool1);
              } 
              boolean bool = false;
              return Boolean.valueOf(bool);
            }
          },  new PeriodicJob.Action(this, paramReactor) {
            public void onTrigger(final PeriodicJob.Completion completion) { reactor.launch(false, MainController.this.heartBeat, new Reactor.LaunchingCompletion() {
                    public void onComplete(boolean param2Boolean) { completion.onComplete(Boolean.valueOf(param2Boolean)); }
                  }); }
          }), Boolean.valueOf(true));
    PeriodicJob.Condition condition = new PeriodicJob.Condition() {
        public Boolean shouldTrigger() { return Boolean.valueOf(program.isReady()); }
      };
    this.updateMixerSettings = new PeriodicJob(100L, condition, new PeriodicJob.Action(this, paramReactor, paramAuthorization, paramProgram, paramLocalMixer) {
          public void onTrigger(final PeriodicJob.Completion completion) { reactor.launch(false, new UpdateMixerSettings(auth, program, mixer), new Reactor.LaunchingCompletion() {
                  public void onComplete(boolean param2Boolean) { completion.onComplete(Boolean.valueOf(param2Boolean)); }
                }); }
        });
    this.periodicJobs.addJob(this.updateMixerSettings);
    this.updateSlots = new PeriodicJob(100L, condition, new PeriodicJob.Action(this, paramReactor, paramAuthorization, paramProgram, paramLocalMixer) {
          public void onTrigger(final PeriodicJob.Completion completion) { reactor.launch(false, new UpdateChannels(auth, program, mixer), new Reactor.LaunchingCompletion() {
                  public void onComplete(boolean param2Boolean) { completion.onComplete(Boolean.valueOf(param2Boolean)); }
                }); }
        });
    this.periodicJobs.addJob(this.updateSlots);
  }
  
  private void createProgram() { this.reactor.launch(new Job[] { new CreateProgram(new CreateProgram.Completion(this) {
              public void onSuccess(Integer param1Integer) { this.this$0.program.code.set(param1Integer); }
            }), new GetProgramNumber(this.program, new GetProgramNumber.Completion(this) {
              public void onSuccess(Integer param1Integer) { this.this$0.program.number.set(param1Integer); }
            }) }); }
  
  private void loadProgramSources() { this.reactor.launch(true, new GetProgramSources(this.program, new GetProgramSources.Completion(this) {
            public void onSuccess(List<RTMPSource> param1List) { MainController.this.program.restore(param1List); }
          })); }
  
  private void loginAuth() { this.reactor.launch(false, new Login(HashKey, this.auth, new Login.Completion(this) {
            public void onSuccess(String param1String) {
              MainController.this.reactor.setDefaultDecorator(new MainController.AuthorizationDecorator(param1String));
              MainController.this.auth.schedule(Authorization.Signal.SigDidLogin);
            }
          }), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            if (!param1Boolean)
              MainController.this.auth.schedule(Authorization.Signal.SigDidFailLogin); 
          }
        }); }
  
  private void registerAuth() {
    String str = String.valueOf((new Random()).nextInt(1000000));
    this.reactor.launch(false, new Register(HashKey, str, str, new Register.Completion(this, str) {
            public void onSuccess(Integer param1Integer) { MainController.this.auth.doneRegister(param1Integer, ticket, ticket); }
          }), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            if (!param1Boolean)
              MainController.this.auth.schedule(Authorization.Signal.SigDidFailRegister); 
          }
        });
  }
  
  private void setupProgram() {
    syncProgramWithStorage();
    syncProgramWithServices();
  }
  
  private void startHeatBeating() { this.heartBeat = new HeartBeat(this.program, null); }
  
  private void stopHeatBeating() { this.heartBeat = null; }
  
  private void syncAuth() {
    syncAuthStorage();
    this.auth.register(this, Authorization.State.ProcRegister, true, new Statechart.StateListener<Authorization.State>() {
          public void didEnter(Authorization.State param1State) { MainController.this.registerAuth(); }
        });
    this.auth.register(this, Authorization.State.ProcLogin, true, new Statechart.StateListener<Authorization.State>() {
          public void didEnter(Authorization.State param1State) { MainController.this.loginAuth(); }
        });
  }
  
  private void syncAuthStorage() {
    this.storage.load(this.accAuth);
    this.auth.register(this, Authorization.State.Authorized, true, new Statechart.StateListener<Authorization.State>() {
          public void didEnter(Authorization.State param1State) { MainController.this.storage.store(MainController.this.accAuth); }
        });
  }
  
  private void syncMixer() { this.mixer.mode.register(this, LocalMixer.MixerMode.State.Started, true, new Statechart.StateListener<LocalMixer.MixerMode.State>() {
          public void didEnter(LocalMixer.MixerMode.State param1State) { MainController.this.reactor.launch(new Job[] { new CreateUpStreamingSource((Integer)(MainController.access$300(this.this$0)).code.get(), MainController.access$400(MainController.this).getUID()), new UpdateMixerSettings(MainController.access$400(MainController.this), MainController.access$300(MainController.this), MainController.access$500(MainController.this)) }); }
          
          public void willLeave(LocalMixer.MixerMode.State param1State) { MainController.this.reactor.launch(new Job[] { new UpdateMixerSettings(MainController.access$400(MainController.this), MainController.access$300(MainController.this), MainController.access$500(MainController.this)), new DeleteSource((Integer)(MainController.access$300(this.this$0)).code.get(), MainController.access$400(MainController.this).getUID()) }); }
        }); }
  
  private void syncNginx() {
    this.storage.load(this.accNginx);
    if (this.nginx.installedVersion < 1 && this.nginx.install().booleanValue()) {
      this.nginx.installedVersion = 1;
      this.storage.store(this.accNginx);
    } 
  }
  
  private void syncPanelOfProgram() { this.pgmPath.streams.register(this, Boolean.valueOf(true), new ObservableList.Observer<Stream>() {
          public void updated(boolean param1Boolean, List<Stream> param1List) { MainController.this.updateMixerSettings.invalidate(); }
        }); }
  
  private void syncPlayerCenter() { this.playerCenter.opened.hook(this, false, new ObservableValue.Observer<Boolean>() {
          public void willChange(Boolean param1Boolean1, Boolean param1Boolean2) {
            if (!param1Boolean1.booleanValue() && param1Boolean2.booleanValue())
              MainController.this.startHeatBeating(); 
            if (param1Boolean1.booleanValue() && !param1Boolean2.booleanValue())
              MainController.this.stopHeatBeating(); 
          }
        }); }
  
  private void syncProgram() { this.auth.register(this, Authorization.State.Authorized, true, new Statechart.StateListener<Authorization.State>() {
          public void didEnter(Authorization.State param1State) { MainController.this.setupProgram(); }
        }); }
  
  private void syncProgramWithServices() {
    byte b = 0;
    if (!this.program.isReady()) {
      createProgram();
    } else {
      loadProgramSources();
    } 
    this.program.code.hook(this, true, new ObservableValue.Observer<Integer>() {
          public void updated(boolean param1Boolean, Integer param1Integer1, Integer param1Integer2) {
            if (param1Integer2 == null)
              return; 
            MainController.this.reactor.launch(new GetPushURLs(MainController.this.program, new GetPushURLs.Completion(this) {
                    public void onSuccess(List<String> param2List) { this.this$1.this$0.program.WANRTMPPushURLs = param2List; }
                  }));
          }
        });
    this.program.isOnLive.hook(this, false, new ObservableValue.Observer<Boolean>() {
          public void willChange(Boolean param1Boolean1, Boolean param1Boolean2) {
            if (!param1Boolean1.booleanValue() && param1Boolean2.booleanValue())
              MainController.this.syncPanelOfProgram(); 
            if (param1Boolean1.booleanValue() && !param1Boolean2.booleanValue())
              MainController.this.unsyncPanelOfProgram(); 
          }
        });
    Slot[] arrayOfSlot = this.program.slots;
    int i = arrayOfSlot.length;
    while (b < i) {
      (arrayOfSlot[b]).stream.hook(this, true, new ObservableValue.Observer<Stream>() {
            public void updated(boolean param1Boolean, Stream param1Stream1, Stream param1Stream2) { MainController.this.updateSlots.invalidate(); }
          });
      b++;
    } 
    this.pgmPath.streams.register(this, Boolean.valueOf(true), new ObservableList.Observer<Stream>() {
          public void updated(boolean param1Boolean, List<Stream> param1List) { MainController.this.updateSlots.invalidate(); }
        });
    this.program.interOperations = new Program.InterOperations() {
        public void doCreateRTMPSource(String param1String) {
          final RTMPSource source = RTMPSource.make(param1String);
          MainController.this.reactor.launch(false, new CreateRTMPAnchorSource(MainController.this.program, rTMPSource), new Reactor.LaunchingCompletion() {
                public void onComplete(boolean param2Boolean) {
                  if (param2Boolean)
                    this.this$1.this$0.program.rtmpSources.add(source); 
                }
              });
        }
        
        public void doDeleteRTMPSource(final RTMPSource source) { MainController.this.reactor.launch(false, new DeleteSource((Integer)this.this$0.program.code.get(), param1RTMPSource.userId), new Reactor.LaunchingCompletion() {
                public void onComplete(boolean param2Boolean) {
                  this.this$1.this$0.program.rtmpSources.remove(source);
                  RTMPSource.destroy(source);
                }
              }); }
      };
  }
  
  private void syncProgramWithStorage() {
    this.storage.load(this.accProgram);
    this.program.code.hook(this, false, new ObservableValue.Observer<Integer>() {
          public void updated(boolean param1Boolean, Integer param1Integer1, Integer param1Integer2) { MainController.this.storage.store(MainController.this.accProgram); }
        });
    this.program.number.hook(this, false, new ObservableValue.Observer<Integer>() {
          public void updated(boolean param1Boolean, Integer param1Integer1, Integer param1Integer2) { MainController.this.storage.store(MainController.this.accProgram); }
        });
    this.program.yyOutputSetting.uid.hook(this, false, new ObservableValue.Observer<Integer>() {
          public void updated(boolean param1Boolean, Integer param1Integer1, Integer param1Integer2) { MainController.this.storage.store(MainController.this.accProgram); }
        });
    this.program.yyOutputSetting.enabled.hook(this, false, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) { MainController.this.storage.store(MainController.this.accProgram); }
        });
    this.program.yyOutputSetting.channel.hook(this, false, new ObservableValue.Observer<YYChannel>() {
          public void updated(boolean param1Boolean, YYChannel param1YYChannel1, YYChannel param1YYChannel2) { MainController.this.storage.store(MainController.this.accProgram); }
        });
    this.program.rtmpOutputSettings.register(this, Boolean.valueOf(true), new ObservableList.Observer<RTMPOutputSetting>() {
          private void sync(RTMPOutputSetting param1RTMPOutputSetting) {
            param1RTMPOutputSetting.enabled.hook(this, true, new ObservableValue.Observer<Boolean>() {
                  public void updated(boolean param2Boolean, Boolean param2Boolean1, Boolean param2Boolean2) { MainController.null.this.this$0.storage.store(MainController.null.this.this$0.accProgram); }
                });
            param1RTMPOutputSetting.url.hook(this, true, new ObservableValue.Observer<String>() {
                  public void updated(boolean param2Boolean, String param2String1, String param2String2) { MainController.null.this.this$0.storage.store(MainController.null.this.this$0.accProgram); }
                });
          }
          
          private void unsync(RTMPOutputSetting param1RTMPOutputSetting) {
            param1RTMPOutputSetting.url.unhook(this);
            param1RTMPOutputSetting.enabled.unhook(this);
          }
          
          public void didAdd(RTMPOutputSetting param1RTMPOutputSetting) { sync(param1RTMPOutputSetting); }
          
          public void updated(boolean param1Boolean, List<RTMPOutputSetting> param1List) {
            if (param1Boolean) {
              Iterator iterator = param1List.iterator();
              while (iterator.hasNext())
                sync((RTMPOutputSetting)iterator.next()); 
            } 
          }
          
          public void willRemove(RTMPOutputSetting param1RTMPOutputSetting) { unsync(param1RTMPOutputSetting); }
        });
    this.program.rtmpInputHistories.register(this, Boolean.valueOf(false), new ObservableList.Observer<String>() {
          public void updated(boolean param1Boolean, List<String> param1List) { MainController.this.storage.store(MainController.this.accProgram); }
        });
  }
  
  private void unsyncPanelOfProgram() { this.pgmPath.streams.unregister(this); }
  
  public void sync(Context paramContext) {
    this.storage = new PreferencesStorage(paramContext, "DataStore");
    this.pgmPath.sync();
    syncPlayerCenter();
    syncMixer();
    syncProgram();
    syncAuth();
    syncNginx();
    this.periodicJobs.start();
  }
  
  private static class AuthorizationDecorator implements Reactor.RequestDecorator {
    private final String token;
    
    AuthorizationDecorator(String param1String) { this.token = param1String; }
    
    public void decorate(Request.Builder param1Builder) { param1Builder.addHeader("Authorization", this.token); }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\MainController.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */