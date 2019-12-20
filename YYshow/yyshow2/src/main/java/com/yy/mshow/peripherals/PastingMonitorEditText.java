package com.yy.mshow.peripherals;

import android.content.Context;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;

public class PastingMonitorEditText extends AppCompatEditText {
  private OnPastingListener pastingListener;
  
  public PastingMonitorEditText(Context paramContext) { super(paramContext); }
  
  public PastingMonitorEditText(Context paramContext, AttributeSet paramAttributeSet) { super(paramContext, paramAttributeSet); }
  
  public PastingMonitorEditText(Context paramContext, AttributeSet paramAttributeSet, int paramInt) { super(paramContext, paramAttributeSet, paramInt); }
  
  public boolean onTextContextMenuItem(int paramInt) {
    boolean bool = super.onTextContextMenuItem(paramInt);
    if (paramInt == 16908322 && this.pastingListener != null)
      this.pastingListener.onPasting(getText().toString()); 
    return bool;
  }
  
  public void setOnPastingListener(OnPastingListener paramOnPastingListener) { this.pastingListener = paramOnPastingListener; }
  
  public static interface OnPastingListener {
    void onPasting(String param1String);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\PastingMonitorEditText.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */