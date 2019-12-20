package com.yy.mshow.peripherals;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.TextView;
import kits.uikits.Dimens;

public final class TitleDetailsViewHolder {
  public TextView detailsView;
  
  public View root;
  
  public TextView titleView;
  
  private void bindViews() {
    this.titleView = (TextView)this.root.findViewById(2131624032);
    this.detailsView = (TextView)this.root.findViewById(2131624095);
  }
  
  public TitleDetailsViewHolder convert(View paramView) {
    this.root = paramView;
    bindViews();
    return this;
  }
  
  public TitleDetailsViewHolder inflate(Activity paramActivity, int paramInt) {
    FrameLayout frameLayout = new FrameLayout(paramActivity);
    this.root = paramActivity.getLayoutInflater().inflate(2130968611, frameLayout, false);
    this.root.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(paramInt)));
    bindViews();
    return this;
  }
  
  public TitleDetailsViewHolder setDetails(CharSequence paramCharSequence) {
    this.detailsView.setText(paramCharSequence);
    return this;
  }
  
  public TitleDetailsViewHolder setTitle(CharSequence paramCharSequence) {
    this.titleView.setText(paramCharSequence);
    return this;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\TitleDetailsViewHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */