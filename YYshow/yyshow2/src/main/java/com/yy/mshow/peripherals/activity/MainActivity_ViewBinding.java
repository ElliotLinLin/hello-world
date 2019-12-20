package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;

public class MainActivity_ViewBinding implements Unbinder {
  private MainActivity target;
  
  @UiThread
  public MainActivity_ViewBinding(MainActivity paramMainActivity) { this(paramMainActivity, paramMainActivity.getWindow().getDecorView()); }
  
  @UiThread
  public MainActivity_ViewBinding(MainActivity paramMainActivity, View paramView) {
    this.target = paramMainActivity;
    paramMainActivity.directingButton = (ImageButton)Utils.findRequiredViewAsType(paramView, 2131624082, "field 'directingButton'", ImageButton.class);
    paramMainActivity.capturingButton = (Button)Utils.findRequiredViewAsType(paramView, 2131624081, "field 'capturingButton'", Button.class);
  }
  
  @CallSuper
  public void unbind() {
    MainActivity mainActivity = this.target;
    if (mainActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    mainActivity.directingButton = null;
    mainActivity.capturingButton = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\MainActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */