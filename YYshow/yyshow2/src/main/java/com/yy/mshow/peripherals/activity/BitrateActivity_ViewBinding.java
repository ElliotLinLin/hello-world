package com.yy.mshow.peripherals.activity;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ListView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.github.chenglei1986.navigationbar.NavigationBar;

public class BitrateActivity_ViewBinding implements Unbinder {
  private BitrateActivity target;
  
  @UiThread
  public BitrateActivity_ViewBinding(BitrateActivity paramBitrateActivity) { this(paramBitrateActivity, paramBitrateActivity.getWindow().getDecorView()); }
  
  @UiThread
  public BitrateActivity_ViewBinding(BitrateActivity paramBitrateActivity, View paramView) {
    this.target = paramBitrateActivity;
    paramBitrateActivity.navigationBar = (NavigationBar)Utils.findRequiredViewAsType(paramView, 2131624149, "field 'navigationBar'", NavigationBar.class);
    paramBitrateActivity.listView = (ListView)Utils.findRequiredViewAsType(paramView, 2131624150, "field 'listView'", ListView.class);
  }
  
  @CallSuper
  public void unbind() {
    BitrateActivity bitrateActivity = this.target;
    if (bitrateActivity == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    bitrateActivity.navigationBar = null;
    bitrateActivity.listView = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\BitrateActivity_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */