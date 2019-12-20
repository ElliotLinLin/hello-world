package com.yy.mshow.peripherals;

import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import junit.framework.Assert;

public class PopupShowingURLDialog {
  private final String URLString;
  
  private final Context context;
  
  private Dialog dialog;
  
  public PopupShowingURLDialog(Context paramContext, String paramString1, String paramString2) {
    this.context = paramContext;
    this.URLString = paramString2;
    this.dialog = createDialog();
    setupTitleView(paramString1);
    setupURLTextView();
    setupQRCodeButton();
    setupCancelButton();
    setupCopyButton();
  }
  
  private Dialog createDialog() {
    Dialog dialog1 = new Dialog(this.context);
    dialog1.requestWindowFeature(1);
    dialog1.setContentView(2130968630);
    Drawable drawable = ContextCompat.getDrawable(this.context, 2130837654);
    drawable.setAlpha(242);
    Assert.assertNotNull(dialog1.getWindow());
    dialog1.getWindow().setBackgroundDrawable(drawable);
    return dialog1;
  }
  
  private void setupCancelButton() { ((Button)this.dialog.findViewById(2131624086)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { PopupShowingURLDialog.this.dialog.dismiss(); }
        }); }
  
  private void setupCopyButton() { ((Button)this.dialog.findViewById(2131624121)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PopupShowingURLDialog.this.dialog.dismiss();
            ClipData clipData = ClipData.newPlainText("MSHOW_URL", PopupShowingURLDialog.this.URLString);
            ((ClipboardManager)PopupShowingURLDialog.this.context.getSystemService("clipboard")).setPrimaryClip(clipData);
            Toast.makeText(PopupShowingURLDialog.this.context, "复制成功", 0).show();
          }
        }); }
  
  private void setupQRCodeButton() { ((Button)this.dialog.findViewById(2131624120)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { PopupQRCodeDialog.showDialog(PopupShowingURLDialog.this.context, "使用外设APP扫码推流", PopupShowingURLDialog.this.URLString, ""); }
        }); }
  
  private void setupTitleView(String paramString) { ((TextView)this.dialog.findViewById(2131624092)).setText(paramString); }
  
  private void setupURLTextView() { ((TextView)this.dialog.findViewById(2131624119)).setText(this.URLString); }
  
  public void show() { this.dialog.show(); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupShowingURLDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */