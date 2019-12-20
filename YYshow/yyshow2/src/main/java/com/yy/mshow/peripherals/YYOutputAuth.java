package com.yy.mshow.peripherals;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import com.yy.mshow.cores.Program;
import com.yy.mshow.peripherals.networking.YYChannel;
import com.yy.mshow.peripherals.networking.services.BindYYLiveChannel;
import com.yy.mshow.peripherals.networking.services.CreateYYChannel;
import com.yy.mshow.peripherals.networking.services.GetYYAccountBasics;
import com.yy.mshow.peripherals.networking.services.GetYYBoundLiveChannel;
import com.yy.mshow.peripherals.networking.services.GetYYOwnChannels;
import com.yy.udbauth.AuthEvent;
import com.yy.udbauth.AuthSDK;
import com.yy.udbauth.ui.AuthUI;
import com.yy.udbauth.ui.tools.OnCreditLoginListener;
import com.yy.udbauth.ui.tools.OnUdbAuthListener;
import com.yy.udbauth.ui.tools.OpreateType;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import junit.framework.Assert;
import kits.RuntimeKit;
import kits.observables.ObservableValue;
import kits.reactor.Job;
import kits.reactor.Reactor;

public class YYOutputAuth {
  private static final String TAG = "[YYOutputAuth]";
  
  public final ObservableValue<GetYYAccountBasics.YYAccountBasics> account = new ObservableValue(null);
  
  public final ObservableValue<Boolean> authorized = new ObservableValue(Boolean.valueOf(false));
  
  public Delegate delegate;
  
  private final Reactor http;
  
  public final ObservableValue<ArrayList<YYChannel>> optionalChannels = new ObservableValue(null);
  
  private final Program program;
  
  public YYOutputAuth(Reactor paramReactor, Program paramProgram) {
    this.http = paramReactor;
    this.program = paramProgram;
  }
  
  private void createAndBindChannel() {
    Integer integer = (Integer)this.program.yyOutputSetting.uid.get();
    Assert.assertNotNull(integer);
    BindYYLiveChannel bindYYLiveChannel = new BindYYLiveChannel(integer.intValue(), getPercentEscapeToken(), new BindYYLiveChannel.Completion(this, integer) {
          public void onFailure(YYChannel param1YYChannel, String param1String) { Log.w("[YYOutputAuth]", "Failed to bind yyuid: " + yyUid + " and channelID: " + param1YYChannel.id + " : " + param1String); }
          
          public void onSuccess(YYChannel param1YYChannel) {
            this.this$0.program.yyOutputSetting.channel.set(param1YYChannel);
            this.this$0.program.yyOutputSetting.enabled.set(Boolean.valueOf(true));
            Log.i("[YYOutputAuth]", "Succeeded to bind yyuid: " + yyUid + " and channelID: " + param1YYChannel.id);
          }
        });
    CreateYYChannel createYYChannel = new CreateYYChannel(integer.intValue(), getPercentEscapeToken(), new CreateYYChannel.Completion(this, bindYYLiveChannel) {
          public void onSuccess(YYChannel param1YYChannel) {
            this.val$bind.channel = param1YYChannel;
            Log.i("[YYOutputAuth]", "Create YY Channel: " + param1YYChannel);
          }
        });
    Reactor reactor = this.http;
    Reactor.LaunchingCompletion launchingCompletion = new Reactor.LaunchingCompletion() {
        public void onComplete(boolean param1Boolean) {
          if (!param1Boolean) {
            this.this$0.program.yyOutputSetting.channel.set(null);
            this.this$0.program.yyOutputSetting.enabled.set(Boolean.valueOf(false));
          } 
        }
      };
    reactor.launch(false, new Job[] { createYYChannel, bindYYLiveChannel }, launchingCompletion);
  }
  
  private YYChannel findChannel(Integer paramInteger) {
    if (paramInteger == null || this.optionalChannels.get() == null)
      return null; 
    for (YYChannel yYChannel : (ArrayList)this.optionalChannels.get()) {
      if (yYChannel.id == paramInteger.intValue())
        return yYChannel; 
    } 
    return null;
  }
  
  private String getPercentEscapeToken() {
    String str2 = AuthSDK.getToken("5060");
    String str1 = str2;
    if (str2.isEmpty())
      str1 = null; 
    return str1;
  }
  
  public static void init(Context paramContext, String paramString1, String paramString2) {
    String str = (new RuntimeKit(paramContext)).appVersion();
    AuthUI.getInstance().init(paramContext, paramString1, paramString2, str, true, null);
  }
  
  private void loadAccountInfo() {
    Integer integer = (Integer)this.program.yyOutputSetting.uid.get();
    Assert.assertNotNull(integer);
    String str = getPercentEscapeToken();
    if (str == null)
      return; 
    this.http.launch(false, new GetYYAccountBasics(integer.intValue(), str, new GetYYAccountBasics.Completion(this) {
            public void onSuccess(GetYYAccountBasics.YYAccountBasics param1YYAccountBasics) { YYOutputAuth.this.account.set(param1YYAccountBasics); }
          }), new Reactor.LaunchingCompletion() {
          public void onComplete(boolean param1Boolean) {
            if (!param1Boolean)
              YYOutputAuth.this.account.set(null); 
          }
        });
  }
  
  private void loadChannels(Boolean paramBoolean) {
    Integer integer = (Integer)this.program.yyOutputSetting.uid.get();
    Assert.assertNotNull(integer);
    Reactor reactor = this.http;
    GetYYOwnChannels getYYOwnChannels = new GetYYOwnChannels(integer.intValue(), new GetYYOwnChannels.Completion(this) {
          public void onSuccess(ArrayList<YYChannel> param1ArrayList) { YYOutputAuth.this.optionalChannels.set(param1ArrayList); }
        });
    GetYYBoundLiveChannel getYYBoundLiveChannel = new GetYYBoundLiveChannel(integer.intValue(), getPercentEscapeToken(), new GetYYBoundLiveChannel.Completion(this, paramBoolean, integer) {
          public void onFailure() { Log.w("[YYOutputAuth]", "Failed to query YYUID: " + yyUid + " bound live channel"); }
          
          public void onSuccess(Integer param1Integer) {
            if (param1Integer == null) {
              if (((ArrayList)YYOutputAuth.this.optionalChannels.get()).isEmpty()) {
                YYOutputAuth.this.createAndBindChannel();
                return;
              } 
              Assert.assertNotNull(YYOutputAuth.this.delegate);
              YYOutputAuth.this.delegate.selectChannel((List)YYOutputAuth.this.optionalChannels.get());
              return;
            } 
            YYChannel yYChannel = YYOutputAuth.this.findChannel(param1Integer);
            Assert.assertNotNull(yYChannel);
            this.this$0.program.yyOutputSetting.channel.set(yYChannel);
            if (shouldEnable.booleanValue()) {
              this.this$0.program.yyOutputSetting.enabled.set(Boolean.valueOf(true));
              return;
            } 
          }
        });
    Reactor.LaunchingCompletion launchingCompletion = new Reactor.LaunchingCompletion() {
        public void onComplete(boolean param1Boolean) {
          if (!param1Boolean) {
            this.this$0.program.yyOutputSetting.channel.set(null);
            this.this$0.program.yyOutputSetting.enabled.set(Boolean.valueOf(false));
          } 
        }
      };
    reactor.launch(false, new Job[] { getYYOwnChannels, getYYBoundLiveChannel }, launchingCompletion);
  }
  
  public void bindChannel(int paramInt, YYChannel paramYYChannel) {
    Assert.assertNotNull(this.delegate);
    BindYYLiveChannel bindYYLiveChannel = new BindYYLiveChannel(paramInt, getPercentEscapeToken(), new BindYYLiveChannel.Completion(this, paramInt) {
          public void onFailure(YYChannel param1YYChannel, String param1String) {
            YYOutputAuth.this.delegate.showToast("绑定失败：" + param1String + "\n请尝试其他频道，或更换账号");
            Log.w("[YYOutputAuth]", "Failed to bind yyuid: " + yyUID + " and channelID: " + param1YYChannel.id + " : " + param1String);
          }
          
          public void onSuccess(YYChannel param1YYChannel) {
            this.this$0.program.yyOutputSetting.channel.set(param1YYChannel);
            this.this$0.program.yyOutputSetting.enabled.set(Boolean.valueOf(true));
            YYOutputAuth.this.delegate.showToast("绑定成功！");
            Log.i("[YYOutputAuth]", "Succeeded to bind yyuid: " + yyUID + " and channelID: " + param1YYChannel.id);
          }
        });
    bindYYLiveChannel.channel = paramYYChannel;
    this.http.launch(bindYYLiveChannel);
  }
  
  String getPlainToken() {
    String str2 = new String(AuthSDK.getToken("5060", 1));
    String str1 = str2;
    if (str2.isEmpty())
      str1 = null; 
    return str1;
  }
  
  public void loginAutomatically() {
    Integer integer = (Integer)this.program.yyOutputSetting.uid.get();
    if (integer == null)
      return; 
    String str1 = String.valueOf(integer);
    String str2 = AuthSDK.getCredit(str1);
    if (str2 == null) {
      this.program.yyOutputSetting.uid.set(null);
      this.program.yyOutputSetting.enabled.set(Boolean.valueOf(false));
      this.program.yyOutputSetting.channel.set(null);
      this.authorized.set(Boolean.valueOf(false));
      return;
    } 
    AuthUI.getInstance().loginWithCredit(str1, str2, new OnCreditLoginListener() {
          public void onResult(AuthEvent.LoginEvent param1LoginEvent) {
            if (param1LoginEvent.uiAction == 0) {
              YYOutputAuth.this.authorized.set(Boolean.valueOf(true));
              YYOutputAuth.this.loadAccountInfo();
              YYOutputAuth.this.loadChannels(Boolean.valueOf(false));
            } 
          }
          
          public void onTimeout() {}
        });
  }
  
  public void loginManually(Activity paramActivity) {
    AuthUI.getInstance().setGlobalPageStyle(null);
    AuthUI.getInstance().showLoginActivity(paramActivity, new OnUdbAuthListener() {
          public void onCancel(OpreateType param1OpreateType) {}
          
          public void onError(int param1Int, OpreateType param1OpreateType) {}
          
          public void onSuccess(AuthEvent.AuthBaseEvent param1AuthBaseEvent, OpreateType param1OpreateType) {
            if (EnumSet.of(OpreateType.PWD_LOGIN, OpreateType.SMS_LOGIN, OpreateType.SMS_REGISTER, OpreateType.NEXT_VERIFY).contains(param1OpreateType)) {
              int i = Integer.parseInt(param1AuthBaseEvent.getUid());
              this.this$0.program.yyOutputSetting.enabled.set(Boolean.valueOf(false));
              this.this$0.program.yyOutputSetting.uid.set(Integer.valueOf(i));
              YYOutputAuth.this.authorized.set(Boolean.valueOf(true));
              YYOutputAuth.this.loadAccountInfo();
              YYOutputAuth.this.loadChannels(Boolean.valueOf(true));
            } 
          }
        });
  }
  
  public void logout() {
    this.authorized.set(Boolean.valueOf(false));
    this.program.yyOutputSetting.uid.set(null);
    this.program.yyOutputSetting.enabled.set(Boolean.valueOf(false));
    this.program.yyOutputSetting.channel.set(null);
    AuthUI.getInstance().logout();
  }
  
  public static interface Delegate {
    void selectChannel(List<YYChannel> param1List);
    
    void showToast(String param1String);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\YYOutputAuth.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */