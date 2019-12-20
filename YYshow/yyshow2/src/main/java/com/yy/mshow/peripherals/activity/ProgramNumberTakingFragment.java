package com.yy.mshow.peripherals.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.yy.mshow.peripherals.PinEntryEditText;

public final class ProgramNumberTakingFragment extends Fragment {
  private View.OnClickListener buttonOnClickListener;
  
  @BindView(2131624148)
  Button confirmButton;
  
  @BindView(2131624147)
  PinEntryEditText pinEntryEditText;
  
  private void dismissKeyboard(View paramView) { ((InputMethodManager)getActivity().getSystemService("input_method")).hideSoftInputFromWindow(paramView.getWindowToken(), 0); }
  
  private void setupViews() {
    this.pinEntryEditText.setOnChangedListener(new PinEntryEditText.OnChangedListener() {
          public void onChanged(CharSequence param1CharSequence, boolean param1Boolean) { ProgramNumberTakingFragment.this.confirmButton.setEnabled(param1Boolean); }
        });
    this.pinEntryEditText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
          public void onFocusChange(View param1View, boolean param1Boolean) { ProgramNumberTakingFragment.this.dismissKeyboard(param1View); }
        });
    this.confirmButton.setOnClickListener(this.buttonOnClickListener);
  }
  
  public String getProgramNumber() { return this.pinEntryEditText.getText().toString(); }
  
  @Nullable
  public View onCreateView(LayoutInflater paramLayoutInflater, @Nullable ViewGroup paramViewGroup, Bundle paramBundle) {
    View view = paramLayoutInflater.inflate(2130968634, paramViewGroup, false);
    ButterKnife.bind(this, view);
    setupViews();
    return view;
  }
  
  public void onResume() {
    super.onResume();
    this.pinEntryEditText.focus();
  }
  
  public void setConfirmButtonOnClickListener(View.OnClickListener paramOnClickListener) { this.buttonOnClickListener = paramOnClickListener; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\ProgramNumberTakingFragment.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */