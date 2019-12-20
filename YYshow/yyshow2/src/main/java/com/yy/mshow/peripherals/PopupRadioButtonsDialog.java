package com.yy.mshow.peripherals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import junit.framework.Assert;
import kits.uikits.Dimens;

public class PopupRadioButtonsDialog {
  private final Callback callback;
  
  private Dialog dialog;
  
  private final RadioGroup group;
  
  public PopupRadioButtonsDialog(Context paramContext, String paramString, String[] paramArrayOfString, Callback paramCallback) {
    Assert.assertNotNull(paramCallback);
    Assert.assertNotNull(paramArrayOfString);
    if (paramArrayOfString.length == 0) {
      bool = true;
    } else {
      bool = false;
    } 
    Assert.assertFalse(bool);
    this.dialog = createDialog(paramContext);
    this.group = (RadioGroup)this.dialog.findViewById(2131624113);
    this.callback = paramCallback;
    setupTitleView(paramString);
    setupRadioButtons(paramArrayOfString);
    setupConfirmButton();
    setupCancelButton();
  }
  
  private Dialog createDialog(Context paramContext) {
    Dialog dialog1 = new Dialog(paramContext);
    dialog1.requestWindowFeature(1);
    dialog1.setContentView(2130968628);
    Drawable drawable = ContextCompat.getDrawable(paramContext, 2130837654);
    drawable.setAlpha(242);
    Assert.assertNotNull(dialog1.getWindow());
    dialog1.getWindow().setBackgroundDrawable(drawable);
    return dialog1;
  }
  
  private void setupCancelButton() { ((Button)this.dialog.findViewById(2131624086)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { PopupRadioButtonsDialog.this.dialog.dismiss(); }
        }); }
  
  private void setupConfirmButton() { ((Button)this.dialog.findViewById(2131624115)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PopupRadioButtonsDialog.this.dialog.dismiss();
            int i = PopupRadioButtonsDialog.this.group.getCheckedRadioButtonId();
            param1View = PopupRadioButtonsDialog.this.group.findViewById(i);
            i = PopupRadioButtonsDialog.this.group.indexOfChild(param1View);
            PopupRadioButtonsDialog.this.callback.onConfirm(i);
          }
        }); }
  
  private void setupRadioButtons(String[] paramArrayOfString) {
    for (byte b = 0; b < paramArrayOfString.length; b++) {
      RadioGroup.LayoutParams layoutParams = new RadioGroup.LayoutParams(-1, -2);
      if (b == paramArrayOfString.length - 1) {
        layoutParams.setMargins(Dimens.Pixels.fromDP(30), Dimens.Pixels.fromDP(30), 0, Dimens.Pixels.fromDP(30));
      } else {
        layoutParams.setMargins(Dimens.Pixels.fromDP(30), Dimens.Pixels.fromDP(30), 0, 0);
      } 
      RadioButton radioButton = new RadioButton(this.dialog.getContext());
      radioButton.setLayoutParams(layoutParams);
      radioButton.setButtonDrawable(2130837620);
      radioButton.setPaddingRelative(Dimens.Pixels.fromDP(12), 0, 0, 0);
      radioButton.setText(paramArrayOfString[b]);
      radioButton.setTextColor(-1);
      radioButton.setTextSize(15.0F);
      this.group.addView(radioButton);
    } 
    this.group.check(this.group.getChildAt(0).getId());
  }
  
  private void setupTitleView(String paramString) { ((TextView)this.dialog.findViewById(2131624092)).setText(paramString); }
  
  public void show() { this.dialog.show(); }
  
  public static interface Callback {
    void onConfirm(int param1Int);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupRadioButtonsDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */