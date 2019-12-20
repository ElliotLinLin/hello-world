package com.yy.mshow.peripherals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import junit.framework.Assert;

public class PopupDialog {
  private int buttonsCount = 0;
  
  private final Dialog dialog;
  
  public PopupDialog(Context paramContext, boolean paramBoolean) {
    this.dialog = new Dialog(paramContext);
    this.dialog.requestWindowFeature(1);
    this.dialog.setContentView(2130968627);
    this.dialog.setCanceledOnTouchOutside(paramBoolean);
    Assert.assertNotNull(this.dialog.getWindow());
    Drawable drawable = ContextCompat.getDrawable(paramContext, 2130837654);
    drawable.setAlpha(242);
    this.dialog.getWindow().setBackgroundDrawable(drawable);
  }
  
  public PopupDialog addButton(String paramString, @ColorInt int paramInt, final View.OnClickListener onClickListener) {
    LinearLayout linearLayout = (LinearLayout)this.dialog.findViewById(2131624112);
    if (this.buttonsCount > 0) {
      View view = new View(this.dialog.getContext());
      view.setBackgroundResource(2131558473);
      linearLayout.addView(view);
      view.setLayoutParams(new LinearLayout.LayoutParams(1, -1));
    } 
    Button button = new Button(this.dialog.getContext());
    button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PopupDialog.this.dialog.dismiss();
            if (onClickListener != null)
              onClickListener.onClick(param1View); 
          }
        });
    button.setBackgroundResource(2130837637);
    button.setTextColor(paramInt);
    button.setTextSize(18.0F);
    button.setText(paramString);
    linearLayout.addView(button);
    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(-1, -1);
    button.setLayoutParams(layoutParams);
    layoutParams.weight = 1.0F;
    this.buttonsCount++;
    return this;
  }
  
  public PopupDialog addButton(String paramString, View.OnClickListener paramOnClickListener) { return addButton(paramString, -1, paramOnClickListener); }
  
  public PopupDialog setTitle(String paramString) {
    ((TextView)this.dialog.findViewById(2131624092)).setText(paramString);
    return this;
  }
  
  public void show() { this.dialog.show(); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */