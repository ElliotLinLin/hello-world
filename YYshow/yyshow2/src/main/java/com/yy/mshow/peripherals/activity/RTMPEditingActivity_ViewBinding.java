package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.chenglei1986.navigationbar.NavigationBar;
import com.yy.mshow.peripherals.PastingMonitorEditText;

public class RTMPEditingActivity_ViewBinding implements Unbinder {
  private RTMPEditingActivity target;
  
  @UiThread
  public RTMPEditingActivity_ViewBinding(RTMPEditingActivity paramRTMPEditingActivity) { this(paramRTMPEditingActivity, paramRTMPEditingActivity.getWindow().getDecorView()); }
  
  @UiThread
  public RTMPEditingActivity_ViewBinding(RTMPEditingActivity paramRTMPEditingActivity, View paramView) {
    this.target = paramRTMPEditingActivity;
    paramRTMPEditingActivity.navigationBar = (NavigationBar)Utils.findRequiredViewAsType(paramView, 2131624149, "field 'navigationBar'", NavigationBar.class);
    paramRTMPEditingActivity.iconImageView = (ImageView)Utils.findRequiredViewAsType(paramView, 2131624187, "field 'iconImageView'", ImageView.class);
    paramRTMPEditingActivity.liveCodeField = (EditText)Utils.findRequiredViewAsType(paramView, 2131624189, "field 'liveCodeField'", EditText.class);
    paramRTMPEditingActivity.urlStringField = (PastingMonitorEditText)Utils.findRequiredViewAsType(paramView, 2131624188, "field 'urlStringField'", PastingMonitorEditText.class);
    paramRTMPEditingActivity.confirmButton = (Button)Utils.findRequiredViewAsType(paramView, 2131624115, "field 'confirmButton'", Button.class);
  }
  
  @CallSuper
  public void unbind() {
    RTMPEditingActivity rTMPEditingActivity = this.target;
    if (rTMPEditingActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    rTMPEditingActivity.navigationBar = null;
    rTMPEditingActivity.iconImageView = null;
    rTMPEditingActivity.liveCodeField = null;
    rTMPEditingActivity.urlStringField = null;
    rTMPEditingActivity.confirmButton = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\RTMPEditingActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */