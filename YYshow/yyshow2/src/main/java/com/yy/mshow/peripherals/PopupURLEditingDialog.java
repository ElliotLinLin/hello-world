package com.yy.mshow.peripherals;

import android.app.Dialog;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.yy.mshow.peripherals.playercenter.RTMPSource;
import java.util.Iterator;
import java.util.List;
import junit.framework.Assert;
import kits.observables.ObservableValue;
import kits.uikits.Dimens;

public class PopupURLEditingDialog {
  private final Dialog dialog;
  
  private final ObservableValue<String> urlString = new ObservableValue(null);
  
  private EditText urlStringView;
  
  public PopupURLEditingDialog(Context paramContext) {
    this.dialog = createDialog(paramContext);
    setupURLStringField();
    setupCancelButton();
    setupConfirmButton();
    setupHistoryList();
  }
  
  private Dialog createDialog(Context paramContext) {
    Dialog dialog1 = new Dialog(paramContext);
    dialog1.requestWindowFeature(1);
    dialog1.setContentView(2130968696);
    Drawable drawable = ContextCompat.getDrawable(paramContext, 2130837654);
    drawable.setAlpha(242);
    Assert.assertNotNull(dialog1.getWindow());
    dialog1.getWindow().setBackgroundDrawable(drawable);
    return dialog1;
  }
  
  private boolean isPlaying(String paramString) {
    Iterator iterator = MShow.program.rtmpSources.get().iterator();
    while (iterator.hasNext()) {
      if (((RTMPSource)iterator.next()).urlString.toLowerCase().equals(paramString.toLowerCase()))
        return true; 
    } 
    return false;
  }
  
  private void setupCancelButton() { ((Button)this.dialog.findViewById(2131624086)).setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { PopupURLEditingDialog.this.dialog.dismiss(); }
        }); }
  
  private void setupConfirmButton() {
    final Button confirm = (Button)this.dialog.findViewById(2131624115);
    this.urlString.hook(this, true, new ObservableValue.Observer<String>() {
          public void updated(boolean param1Boolean, String param1String1, String param1String2) {
            int i;
            if (LivePlatforms.fromRTMPString((String)PopupURLEditingDialog.this.urlString.get()) != null) {
              param1Boolean = true;
            } else {
              param1Boolean = false;
            } 
            Boolean bool = Boolean.valueOf(param1Boolean);
            confirm.setEnabled(bool.booleanValue());
            if (bool.booleanValue()) {
              i = 2131558446;
            } else {
              i = 2131558472;
            } 
            confirm.setTextColor(ContextCompat.getColor(PopupURLEditingDialog.this.dialog.getContext(), i));
          }
        });
    button.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PopupURLEditingDialog.this.dialog.dismiss();
            if (PopupURLEditingDialog.this.isPlaying((String)PopupURLEditingDialog.this.urlString.get())) {
              PopupURLEditingDialog.this.showToast("该视频已存在");
              return;
            } 
            if (!MShow.program.hasEmptySlot()) {
              PopupURLEditingDialog.this.showToast("通道已满，请移除后重试");
              return;
            } 
            MShow.program.createRTMPSource((String)PopupURLEditingDialog.this.urlString.get());
          }
        });
  }
  
  private void setupHistoryList() {
    LinearLayout linearLayout = (LinearLayout)this.dialog.findViewById(2131624300);
    List list = MShow.program.rtmpInputHistories.get();
    if (list.isEmpty()) {
      linearLayout.setVisibility(8);
      return;
    } 
    Iterator iterator = list.iterator();
    while (true) {
      if (iterator.hasNext()) {
        final String urlString = (String)iterator.next();
        TextView textView = new TextView(this.dialog.getContext());
        textView.setLayoutParams(new ViewGroup.LayoutParams(-1, Dimens.Pixels.fromDP(44)));
        textView.setGravity(16);
        textView.setText(str);
        textView.setTextColor(-1);
        textView.setTextSize(14.0F);
        textView.setLines(1);
        textView.setBackground(ContextCompat.getDrawable(this.dialog.getContext(), 2130837637));
        textView.setOnClickListener(new View.OnClickListener() {
              public void onClick(View param1View) {
                PopupURLEditingDialog.this.urlStringView.setText(urlString);
                PopupURLEditingDialog.this.urlStringView.setSelection(urlString.length());
              }
            });
        linearLayout.addView(textView);
        continue;
      } 
      return;
    } 
  }
  
  private void setupURLStringField() {
    this.urlStringView = (EditText)this.dialog.findViewById(2131624188);
    this.urlStringView.addTextChangedListener(new TextWatcher() {
          public void afterTextChanged(Editable param1Editable) { PopupURLEditingDialog.this.urlString.set(param1Editable.toString()); }
          
          public void beforeTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
          
          public void onTextChanged(CharSequence param1CharSequence, int param1Int1, int param1Int2, int param1Int3) {}
        });
  }
  
  private void showKeyboard() {
    Window window = this.dialog.getWindow();
    Assert.assertNotNull(window);
    window.setSoftInputMode(5);
  }
  
  private void showToast(String paramString) {
    Toast toast = Toast.makeText(this.dialog.getContext(), paramString, 1);
    toast.setGravity(17, 0, 0);
    toast.show();
  }
  
  public void show() {
    this.dialog.show();
    showKeyboard();
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PopupURLEditingDialog.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */