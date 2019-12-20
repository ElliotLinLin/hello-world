package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yy.mshow.peripherals.TabButton;

public class ProgramJoiningMethodsActivity_ViewBinding implements Unbinder {
  private ProgramJoiningMethodsActivity target;
  
  @UiThread
  public ProgramJoiningMethodsActivity_ViewBinding(ProgramJoiningMethodsActivity paramProgramJoiningMethodsActivity) { this(paramProgramJoiningMethodsActivity, paramProgramJoiningMethodsActivity.getWindow().getDecorView()); }
  
  @UiThread
  public ProgramJoiningMethodsActivity_ViewBinding(ProgramJoiningMethodsActivity paramProgramJoiningMethodsActivity, View paramView) {
    this.target = paramProgramJoiningMethodsActivity;
    paramProgramJoiningMethodsActivity.backButton = (ImageButton)Utils.findRequiredViewAsType(paramView, 2131624075, "field 'backButton'", ImageButton.class);
    paramProgramJoiningMethodsActivity.albumButton = (Button)Utils.findRequiredViewAsType(paramView, 2131624076, "field 'albumButton'", Button.class);
    paramProgramJoiningMethodsActivity.scanButton = (TabButton)Utils.findRequiredViewAsType(paramView, 2131624078, "field 'scanButton'", TabButton.class);
    paramProgramJoiningMethodsActivity.inputButton = (TabButton)Utils.findRequiredViewAsType(paramView, 2131624079, "field 'inputButton'", TabButton.class);
  }
  
  @CallSuper
  public void unbind() {
    ProgramJoiningMethodsActivity programJoiningMethodsActivity = this.target;
    if (programJoiningMethodsActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    programJoiningMethodsActivity.backButton = null;
    programJoiningMethodsActivity.albumButton = null;
    programJoiningMethodsActivity.scanButton = null;
    programJoiningMethodsActivity.inputButton = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\ProgramJoiningMethodsActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */