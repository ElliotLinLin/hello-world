package com.yy.mshow.peripherals;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import junit.framework.Assert;
import kits.uikits.Dimens;

public class PopupMenu {
  private final View contextView;
  
  private int itemsCount = 0;
  
  private final PopupWindow popup;
  
  private final LinearLayout templateView;
  
  private int width = 120;
  
  public PopupMenu(View paramView) {
    this.contextView = paramView;
    this.popup = new PopupWindow(Dimens.Pixels.fromDP(this.width), -2);
    FrameLayout frameLayout = new FrameLayout(paramView.getContext());
    this.templateView = (LinearLayout)LayoutInflater.from(paramView.getContext()).inflate(2130968637, frameLayout, false);
    this.popup.setContentView(this.templateView);
    this.popup.setOutsideTouchable(true);
    Drawable drawable = paramView.getContext().getResources().getDrawable(2130837654);
    drawable.setAlpha(242);
    this.popup.setBackgroundDrawable(drawable);
  }
  
  public PopupMenu add(String paramString, final View.OnClickListener onClickListener) {
    Assert.assertNotNull(paramOnClickListener);
    if (this.itemsCount > 0) {
      View view = new View(this.templateView.getContext());
      view.setBackgroundResource(2131558473);
      this.templateView.addView(view);
      view.setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
    } 
    Button button = new Button(this.templateView.getContext());
    button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PopupMenu.this.popup.dismiss();
            onClickListener.onClick(param1View);
          }
        });
    button.setBackgroundResource(2130837637);
    button.setTextColor(-1);
    button.setTextSize(15.0F);
    button.setText(paramString);
    this.templateView.addView(button);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, Dimens.Pixels.fromDP(55));
    button.setLayoutParams(layoutParams);
    layoutParams.weight = 1.0F;
    this.itemsCount++;
    return this;
  }
  
  public PopupMenu setWidth(int paramInt) {
    this.width = paramInt;
    this.popup.setWidth(Dimens.Pixels.fromDP(paramInt));
    return this;
  }
  
  public void show() { this.popup.showAsDropDown(this.contextView, (this.contextView.getWidth() - Dimens.Pixels.fromDP(this.width)) / 2, Dimens.Pixels.fromDP(3)); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupMenu.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */