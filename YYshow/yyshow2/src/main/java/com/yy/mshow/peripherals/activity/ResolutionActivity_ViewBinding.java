package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.chenglei1986.navigationbar.NavigationBar;

public class ResolutionActivity_ViewBinding implements Unbinder {
  private ResolutionActivity target;
  
  @UiThread
  public ResolutionActivity_ViewBinding(ResolutionActivity paramResolutionActivity) { this(paramResolutionActivity, paramResolutionActivity.getWindow().getDecorView()); }
  
  @UiThread
  public ResolutionActivity_ViewBinding(ResolutionActivity paramResolutionActivity, View paramView) {
    this.target = paramResolutionActivity;
    paramResolutionActivity.navigationBar = (NavigationBar)Utils.findRequiredViewAsType(paramView, 2131624149, "field 'navigationBar'", NavigationBar.class);
    paramResolutionActivity.listView = (ListView)Utils.findRequiredViewAsType(paramView, 2131624150, "field 'listView'", ListView.class);
  }
  
  @CallSuper
  public void unbind() {
    ResolutionActivity resolutionActivity = this.target;
    if (resolutionActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    resolutionActivity.navigationBar = null;
    resolutionActivity.listView = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\ResolutionActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */