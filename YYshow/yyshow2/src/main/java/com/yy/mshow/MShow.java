package com.yy.mshow;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;

import com.yy.mshow.cores.Invitation;
import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.DirectorFlows;
import com.yy.mshow.peripherals.MainController;
import com.yy.mshow.peripherals.Ticker;
import com.yy.mshow.peripherals.YYOutputAuth;
import com.yy.mshow.peripherals.localmixer.LocalMixer;
import com.yy.mshow.peripherals.networking.services.CreateOutput;
import com.yy.mshow.peripherals.playercenter.PlayerCenter;
import com.yy.mshow.peripherals.service.Nginx;
import com.yy.sdk.crashreport.CrashReport;
import java.util.List;
import java.util.Locale;
import junit.framework.Assert;
import kits.Statechart;
import kits.reactor.Job;
import kits.reactor.JsonDecoder;
import kits.reactor.Reactor;
import kits.threading.Dispatcher;
import kits.threading.RunInMain;
import kits.uikits.Dimens;
import kits.ycmedia.Media;

public class MShow extends Application {
  private static final int AppID_MediaSDK = 1301014348;
  
  private static final Integer ReleaseEntKey = (TestEntKey = Integer.valueOf(25112)).valueOf(15013);
  
  private static final String ReleaseHost = "mshow.yy.com";
  
  private static final Integer TestEntKey;
  
  private static final String TestHost = "mshowtest.yy.com";
  
  public static final Dispatcher asyncMain;
  
  public static final Authorization auth;
  
  public static final DirectorFlows directorFlows;
  
  private static final Environment env = new Environment("mshow.yy.com", ReleaseEntKey.intValue());
  
  public static final Reactor http;
  
  public static final Invitation invitation;
  
  public static final MainController mainController;
  
  public static final LocalMixer mixer;
  
  public static final Nginx nginx;
  
  public static final PlayerCenter playerCenter;
  
  public static final Program program;
  
  public static MSPublisher publisher;
  
  public static final Dispatcher syncMain;
  
  private static final Ticker ticker;
  
  public static final YYOutputAuth yyOutputAuth;
  
  static  {
    PlayerCenter.init();
    CreateOutput.init(env.entId);
    auth = new Authorization();
    program = new Program();
    invitation = new Invitation(env.host);
    http = (new Reactor("https://" + env.host)).setDefaultDecoder(new JsonDecoder());
    Reactor.Logger logger = new Reactor.Logger() {
        public void logFailure(Job param1Job, int param1Int, String param1String) { Log.e("Reactor", String.format(Locale.getDefault(), "[.][%03d][%s] ! %s", new Object[] { Integer.valueOf(param1Int), param1Job.getClass().getSimpleName(), param1String })); }
        
        public void logRequest(Job param1Job, int param1Int, String param1String1, String param1String2, String param1String3) { Log.i("Reactor", String.format(Locale.getDefault(), "[>][%03d][%s] %s %s %s", new Object[] { Integer.valueOf(param1Int), param1Job.getClass().getSimpleName(), param1String1, param1String2, param1String3 })); }
        
        public void logResponse(Job param1Job, int param1Int1, int param1Int2, String param1String) {
          boolean bool;
          if (param1Int2 >= 200 && param1Int2 < 300) {
            bool = true;
          } else {
            bool = false;
          } 
          String str = String.format(Locale.getDefault(), "[.][%03d][%s] %3d %s", new Object[] { Integer.valueOf(param1Int1), param1Job.getClass().getSimpleName(), Integer.valueOf(param1Int2), param1String });
          if (bool) {
            Log.i("Reactor", str);
            return;
          } 
          Log.e("Reactor", str);
        }
        
        public boolean shouldLog(Job param1Job, int param1Int) { return !(param1Job instanceof com.yy.mshow.peripherals.networking.services.HeartBeat); }
      };
    http.setLogger(logger);
    yyOutputAuth = new YYOutputAuth(http, program);
    mixer = new LocalMixer(1301014348);
    nginx = new Nginx();
    playerCenter = new PlayerCenter(auth, program, nginx);
    mainController = new MainController(http, auth, program, mixer, playerCenter, nginx);
    asyncMain = new Dispatcher() {
        public void dispatch(Runnable param1Runnable) { RunInMain.dispatch(param1Runnable); }
      };
    syncMain = new Dispatcher() {
        public void dispatch(Runnable param1Runnable) { RunInMain.dispatch(true, param1Runnable); }
      };
    ticker = new Ticker();
    directorFlows = new DirectorFlows(auth, program, yyOutputAuth, mixer, http);
  }
  
  private boolean isMainProcess() {
    List list = ((ActivityManager)getSystemService("activity")).getRunningAppProcesses();
    String str = getPackageName();
    int i = Process.myPid();
    for (ActivityManager.RunningAppProcessInfo runningAppProcessInfo : list) {
      if (runningAppProcessInfo.pid == i && str.equals(runningAppProcessInfo.processName))
        return true; 
    } 
    return false;
  }
  
  private void registerActivityLifecycleCallbacks() { registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
          public void onActivityCreated(Activity param1Activity, Bundle param1Bundle) {}
          
          public void onActivityDestroyed(Activity param1Activity) {}
          
          public void onActivityPaused(Activity param1Activity) {}
          
          public void onActivityResumed(Activity param1Activity) {}
          
          public void onActivitySaveInstanceState(Activity param1Activity, Bundle param1Bundle) {}
          
          public void onActivityStarted(Activity param1Activity) { MShow.http.errorHandler.setActivity(param1Activity); }
          
          public void onActivityStopped(Activity param1Activity) {}
        }); }
  
  public void onCreate() {
    super.onCreate();
    CrashReport.init(getApplicationContext(), "yym-mshow-and", "market", (getApplicationInfo()).dataDir + "/lib/");
    CrashReport.startANRDetecting(getApplicationContext());
    Assert.assertTrue(isMainProcess());
    Media.init(this, 1301014348, "9b4ed44e_d6fd_48");
    YYOutputAuth.init(this, "udbtest", "c1me4CxAwd3YIU2ia34vf9N4MtUeMDvI");
    Dimens.init(getResources().getDisplayMetrics());
    auth.register(this, Authorization.State.Authorized, true, new Statechart.StateListener<Authorization.State>() {
          public void didEnter(Authorization.State param1State) { MShow.publisher = new MSPublisher(new MSAccount(MShow.auth.getUID().intValue(), MShow.auth.getUsername(), MShow.auth.getPassword()), MShow.http); }
        });
    nginx.sync(this);
    mixer.sync(program);
    mainController.sync(this);
    ticker.sync(program);
    ticker.open();
    registerActivityLifecycleCallbacks();
  }
  
  public void onTerminate() {
    super.onTerminate();
    ticker.close();
    Media.deInit();
    PlayerCenter.deinit();
  }
  
  private static class Environment {
    final int entId;
    
    final String host;
    
    Environment(String param1String, int param1Int) {
      this.host = param1String;
      this.entId = param1Int;
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\MShow.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */