package com.yy.mshow.peripherals.activity;

import android.support.annotation.UiThread;
import android.view.View;
import android.widget.Button;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import com.yy.mshow.peripherals.PinEntryEditText;

public final class ProgramNumberTakingFragment_ViewBinding implements Unbinder {
  private ProgramNumberTakingFragment target;
  
  @UiThread
  public ProgramNumberTakingFragment_ViewBinding(ProgramNumberTakingFragment paramProgramNumberTakingFragment, View paramView) {
    this.target = paramProgramNumberTakingFragment;
    paramProgramNumberTakingFragment.pinEntryEditText = (PinEntryEditText)Utils.findRequiredViewAsType(paramView, 2131624147, "field 'pinEntryEditText'", PinEntryEditText.class);
    paramProgramNumberTakingFragment.confirmButton = (Button)Utils.findRequiredViewAsType(paramView, 2131624148, "field 'confirmButton'", Button.class);
  }
  
  public void unbind() {
    ProgramNumberTakingFragment programNumberTakingFragment = this.target;
    if (programNumberTakingFragment == null)
      throw new IllegalStateException("Bindings already cleared."); 
    this.target = null;
    programNumberTakingFragment.pinEntryEditText = null;
    programNumberTakingFragment.confirmButton = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\ProgramNumberTakingFragment_ViewBinding.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */