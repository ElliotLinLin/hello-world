package com.yy.mshow.peripherals;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import kits.uikits.Dimens;

public final class TitleCheckableViewHolder {
  public View root;
  
  public ImageView tickView;
  
  public TextView titleView;
  
  private void bindViews() {
    this.titleView = (TextView)this.root.findViewById(2131624092);
    this.tickView = (ImageView)this.root.findViewById(2131624091);
  }
  
  public TitleCheckableViewHolder convert(View paramView) {
    this.root = paramView;
    bindViews();
    return this;
  }
  
  public TitleCheckableViewHolder inflate(Activity paramActivity, int paramInt) {
    FrameLayout frameLayout = new FrameLayout(paramActivity);
    this.root = paramActivity.getLayoutInflater().inflate(2130968608, frameLayout, false);
    this.root.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(paramInt)));
    bindViews();
    return this;
  }
  
  public TitleCheckableViewHolder setChecked(boolean paramBoolean) {
    ImageView imageView = this.tickView;
    if (paramBoolean) {
      byte b1 = 0;
      imageView.setVisibility(b1);
      return this;
    } 
    byte b = 4;
    imageView.setVisibility(b);
    return this;
  }
  
  public TitleCheckableViewHolder setTitle(CharSequence paramCharSequence) {
    this.titleView.setText(paramCharSequence);
    return this;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\TitleCheckableViewHolder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */