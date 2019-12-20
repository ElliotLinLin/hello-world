package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.chenglei1986.navigationbar.NavigationBar;

public class SettingsActivity_ViewBinding implements Unbinder {
  private SettingsActivity target;
  
  @UiThread
  public SettingsActivity_ViewBinding(SettingsActivity paramSettingsActivity) { this(paramSettingsActivity, paramSettingsActivity.getWindow().getDecorView()); }
  
  @UiThread
  public SettingsActivity_ViewBinding(SettingsActivity paramSettingsActivity, View paramView) {
    this.target = paramSettingsActivity;
    paramSettingsActivity.listView = (ListView)Utils.findRequiredViewAsType(paramView, 2131624150, "field 'listView'", ListView.class);
    paramSettingsActivity.navigationBar = (NavigationBar)Utils.findRequiredViewAsType(paramView, 2131624149, "field 'navigationBar'", NavigationBar.class);
  }
  
  @CallSuper
  public void unbind() {
    SettingsActivity settingsActivity = this.target;
    if (settingsActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    settingsActivity.listView = null;
    settingsActivity.navigationBar = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\SettingsActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */