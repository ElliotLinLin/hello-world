package com.yy.mshow.peripherals.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.chenglei1986.navigationbar.NavigationBar;
import com.yy.mshow.cores.RTMPOutputSetting;
import com.yy.mshow.peripherals.LivePlatforms;
import com.yy.mshow.peripherals.PastingMonitorEditText;
import com.yy.mshow.peripherals.RTMPPlatformResources;
import java.util.Stack;
import junit.framework.Assert;
import kits.observables.ObservableValue;

public class RTMPEditingActivity extends Activity {
  private static Mode mode;
  
  private ObservableValue<String> addressString = new ObservableValue("");
  
  private final Stack<Runnable> cleanups = new Stack();
  
  private ObservableValue<String> codeString = new ObservableValue("");
  
  @BindView(2131624115)
  Button confirmButton;
  
  @BindView(2131624187)
  ImageView iconImageView;
  
  @BindView(2131624189)
  EditText liveCodeField;
  
  @BindView(2131624149)
  NavigationBar navigationBar;
  
  @BindView(2131624188)
  PastingMonitorEditText urlStringField;
  
  private void setupConfirmButton() {
    this.confirmButton.setText(mode.titleConfirming());
    ObservableValue.Observer<String> observer = new ObservableValue.Observer<String>() {
        public void updated(boolean param1Boolean, String param1String1, String param1String2) {
          param1String2 = (String)RTMPEditingActivity.this.addressString.get();
          param1String1 = (String)RTMPEditingActivity.this.codeString.get();
          param1Boolean = RTMPOutputSetting.split(param1String2, param1String1, null);
          Button button = RTMPEditingActivity.this.confirmButton;
          if (param1Boolean && !param1String1.isEmpty()) {
            param1Boolean = true;
          } else {
            param1Boolean = false;
          } 
          button.setEnabled(param1Boolean);
        }
      };
    this.addressString.hook(this, true, observer);
    this.codeString.hook(this, true, observer);
    this.confirmButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            mode.apply((String)RTMPEditingActivity.this.addressString.get(), (String)RTMPEditingActivity.this.codeString.get());
            RTMPEditingActivity.this.finish();
          }
        });
    this.cleanups.push(new Runnable() {
          public void run() {
            RTMPEditingActivity.this.codeString.unhook(RTMPEditingActivity.this);
            RTMPEditingActivity.this.addressString.unhook(RTMPEditingActivity.this);
          }
        });
  }
  
  private void setupIconImageView() {
    this.addressString.hook(this, true, new ObservableValue.Observer<String>() {
          public void updated(boolean param1Boolean, String param1String1, String param1String2) { RTMPEditingActivity.this.iconImageView.setImageResource(RTMPPlatformResources.get(LivePlatforms.fromRTMPString((String)RTMPEditingActivity.this.addressString.get()))); }
        });
    this.cleanups.push(new Runnable() {
          public void run() { RTMPEditingActivity.this.addressString.unhook(RTMPEditingActivity.this); }
        });
  }
  
  private void setupLiveCodeField() {
    this.liveCodeField.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) { RTMPEditingActivity.this.codeString.set(param1Editable.toString()); }
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
    this.urlStringField.setOnPastingListener(new PastingMonitorEditText.OnPastingListener() {
          public void onPasting(String param1String) {
            Log.i("DBG", "[Pasted] " + param1String);
            param1String = (String)RTMPEditingActivity.this.codeString.get();
            if (!param1String.isEmpty())
              return; 
            final String address = (String)RTMPEditingActivity.this.addressString.get();
            RTMPOutputSetting.split(str, param1String, new RTMPOutputSetting.SplittingCallback() {
                  public void onSuccess(String param2String1, String param2String2) {
                    if (!param2String1.equals(address)) {
                      RTMPEditingActivity.null.this.this$0.addressString.set(param2String1);
                      RTMPEditingActivity.null.this.this$0.codeString.set(param2String2);
                      RTMPEditingActivity.this.urlStringField.setText(param2String1);
                      RTMPEditingActivity.this.liveCodeField.setText(param2String2);
                      RTMPEditingActivity.this.liveCodeField.requestFocus();
                      RTMPEditingActivity.this.liveCodeField.setSelection(param2String2.length());
                    } 
                  }
                });
          }
        });
    this.cleanups.push(new Runnable() {
          public void run() { RTMPEditingActivity.this.urlStringField.setOnPastingListener(null); }
        });
  }
  
  private void setupNavigationBar() { this.navigationBar.setTitle("其它平台").setDisplayBackButton(true).setBackButtonImageResource(2130903040).setOnBackButtonClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { RTMPEditingActivity.this.finish(); }
        }); }
  
  private void setupUrlStringField() { this.urlStringField.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) { RTMPEditingActivity.this.addressString.set(param1Editable.toString()); }
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        }); }
  
  public static void show(Activity paramActivity, Mode paramMode) {
    mode = paramMode;
    paramActivity.startActivity(new Intent(paramActivity, RTMPEditingActivity.class));
  }
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    Assert.assertNotNull("请使用RTMPEditingActivity.show(Activity, Mode) 来显示该Activity", mode);
    super.onCreate(paramBundle);
    setContentView(2130968656);
    ButterKnife.bind(this);
    setupNavigationBar();
    setupIconImageView();
    setupUrlStringField();
    setupLiveCodeField();
    setupConfirmButton();
    mode.setup(this);
    this.cleanups.add(new Runnable() {
          public void run() { RTMPEditingActivity.access$202(null); }
        });
  }
  
  protected void onDestroy() {
    super.onDestroy();
    while (!this.cleanups.isEmpty())
      ((Runnable)this.cleanups.pop()).run(); 
  }
  
  static class Adding implements Mode {
    public void apply(String param1String1, String param1String2) { MShow.program.rtmpOutputSettings.add(new RTMPOutputSetting(param1String1, param1String2, Boolean.valueOf(true))); }
    
    public void setup(RTMPEditingActivity param1RTMPEditingActivity) {}
    
    public String titleConfirming() { return "添 加"; }
  }
  
  static class Editing implements Mode {
    private final RTMPOutputSetting rtmp;
    
    Editing(RTMPOutputSetting param1RTMPOutputSetting) { this.rtmp = param1RTMPOutputSetting; }
    
    public void apply(String param1String1, String param1String2) { this.rtmp.set(param1String1, param1String2); }
    
    public void setup(RTMPEditingActivity param1RTMPEditingActivity) {
      param1RTMPEditingActivity.addressString.set(this.rtmp.addressPart());
      param1RTMPEditingActivity.codeString.set(this.rtmp.codePart());
      param1RTMPEditingActivity.urlStringField.setText(this.rtmp.addressPart());
      param1RTMPEditingActivity.liveCodeField.setText(this.rtmp.codePart());
    }
    
    public String titleConfirming() { return "修 改"; }
  }
  
  static interface Mode {
    void apply(String param1String1, String param1String2);
    
    void setup(RTMPEditingActivity param1RTMPEditingActivity);
    
    String titleConfirming();
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\RTMPEditingActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */