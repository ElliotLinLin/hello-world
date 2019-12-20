package com.yy.mshow.peripherals;

import android.app.Activity;
import android.support.design.widget.BottomSheetDialog;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import junit.framework.Assert;
import kits.uikits.Dimens;

public class PopupBottomSheet {
  private int itemsCount = 0;
  
  private final BottomSheetDialog sheetDialog;
  
  private final LinearLayout sheetView;
  
  public PopupBottomSheet(Activity paramActivity) {
    this.sheetDialog = new BottomSheetDialog(paramActivity);
    FrameLayout frameLayout = new FrameLayout(paramActivity);
    this.sheetView = (LinearLayout)paramActivity.getLayoutInflater().inflate(2130968638, frameLayout, false);
    this.sheetDialog.setContentView(this.sheetView);
    Assert.assertNotNull(this.sheetDialog.getWindow());
    this.sheetDialog.getWindow().findViewById(2131624103).setBackgroundResource(17170445);
  }
  
  public PopupBottomSheet add(String paramString, final View.OnClickListener onClickListener) {
    if (this.itemsCount > 0) {
      View view = new View(this.sheetView.getContext());
      view.setBackgroundResource(2131558472);
      this.sheetView.addView(view);
      view.setLayoutParams(new LinearLayout.LayoutParams(-1, 1));
    } 
    TextView textView = new TextView(this.sheetView.getContext());
    textView.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PopupBottomSheet.this.sheetDialog.dismiss();
            if (onClickListener != null)
              onClickListener.onClick(param1View); 
          }
        });
    textView.setBackgroundResource(2130837636);
    textView.setAlpha(0.95F);
    textView.setGravity(17);
    textView.setTextColor(-1);
    textView.setTextSize(16.0F);
    textView.setText(paramString);
    textView.setAllCaps(false);
    this.sheetView.addView(textView);
    textView.setLayoutParams(new LinearLayout.LayoutParams(-1, Dimens.Pixels.fromDP(50)));
    this.itemsCount++;
    return this;
  }
  
  public PopupBottomSheet addSectionBar() {
    View view = new View(this.sheetView.getContext());
    view.setAlpha(0.95F);
    view.setBackgroundResource(2131558539);
    view.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(7)));
    this.sheetView.addView(view);
    this.itemsCount = 0;
    return this;
  }
  
  public void show() { this.sheetDialog.show(); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupBottomSheet.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */