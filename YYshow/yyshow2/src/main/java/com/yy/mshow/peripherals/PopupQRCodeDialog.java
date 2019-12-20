package com.yy.mshow.peripherals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;
import android.widget.TextView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import java.util.Hashtable;
import junit.framework.Assert;
import kits.threading.RunInMain;

public class PopupQRCodeDialog {
  private static Bitmap BitmapFromBitMatrix(BitMatrix paramBitMatrix) {
    int j = paramBitMatrix.getWidth();
    int k = paramBitMatrix.getHeight();
    int[] arrayOfInt = new int[j * k];
    for (int i = 0; i < k; i++) {
      for (int m = 0; m < j; m++) {
        byte b;
        if (paramBitMatrix.get(m, i)) {
          b = -16777216;
        } else {
          b = -1;
        } 
        arrayOfInt[i * j + m] = b;
      } 
    } 
    Bitmap bitmap = Bitmap.createBitmap(j, k, Bitmap.Config.ARGB_8888);
    bitmap.setPixels(arrayOfInt, 0, j, 0, 0, j, k);
    return bitmap;
  }
  
  private static Bitmap createQRCodeImage(String paramString, int paramInt1, int paramInt2) {
    try {
      Hashtable hashtable = new Hashtable();
      hashtable.put(EncodeHintType.CHARACTER_SET, "utf-8");
      hashtable.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
      hashtable.put(EncodeHintType.MARGIN, Integer.valueOf(0));
      return BitmapFromBitMatrix((new QRCodeWriter()).encode(paramString, BarcodeFormat.QR_CODE, paramInt1, paramInt2, hashtable));
    } catch (WriterException paramString) {
      paramString.printStackTrace();
      return null;
    } 
  }
  
  public static void showDialog(final Context context, final String title, final String QRCodeContent, final String text) { RunInMain.dispatch(new Runnable() {
          public void run() { PopupQRCodeDialog.showQRCDialog(context, title, QRCodeContent, text); }
        }); }
  
  private static void showQRCDialog(Context paramContext, String paramString1, String paramString2, String paramString3) {
    Dialog dialog = new Dialog(paramContext);
    dialog.requestWindowFeature(1);
    dialog.setCanceledOnTouchOutside(true);
    dialog.setContentView(2130968629);
    Drawable drawable = ContextCompat.getDrawable(paramContext, 2130837654);
    drawable.setAlpha(242);
    Assert.assertNotNull(dialog.getWindow());
    dialog.getWindow().setBackgroundDrawable(drawable);
    TextView textView2 = (TextView)dialog.findViewById(2131624092);
    ImageView imageView = (ImageView)dialog.findViewById(2131624118);
    TextView textView1 = (TextView)dialog.findViewById(2131624083);
    textView2.setText(paramString1);
    Bitmap bitmap = createQRCodeImage(paramString2, 200, 200);
    Assert.assertNotNull(bitmap);
    imageView.setImageBitmap(bitmap);
    if (paramString3 == null || paramString3.isEmpty()) {
      textView1.setVisibility(8);
    } else {
      textView1.setText(paramString3);
    } 
    dialog.show();
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupQRCodeDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */