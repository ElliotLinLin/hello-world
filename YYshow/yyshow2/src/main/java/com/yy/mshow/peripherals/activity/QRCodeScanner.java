package com.yy.mshow.peripherals.activity;

import android.os.Message;
import android.support.v4.app.FragmentManager;
import com.uuzuche.lib_zxing.activity.CaptureFragment;
import com.uuzuche.lib_zxing.activity.CodeUtils;
import junit.framework.Assert;

public final class QRCodeScanner {
  private final CodeUtils.AnalyzeCallback analyzeCallback;
  
  private CaptureFragment captureFragment;
  
  private final int containerID;
  
  private final FragmentManager fragmentManager;
  
  public QRCodeScanner(FragmentManager paramFragmentManager, int paramInt, CodeUtils.AnalyzeCallback paramAnalyzeCallback) {
    this.fragmentManager = paramFragmentManager;
    this.containerID = paramInt;
    this.analyzeCallback = paramAnalyzeCallback;
  }
  
  private void setupCaptureFragment() {
    this.captureFragment = new CaptureFragment();
    CodeUtils.setFragmentArgs(this.captureFragment, 2130968633);
    this.captureFragment.setAnalyzeCallback(this.analyzeCallback);
    this.fragmentManager.beginTransaction().replace(this.containerID, this.captureFragment).commitAllowingStateLoss();
  }
  
  public void continueScanning() {
    Message message = Message.obtain();
    message.what = 2131623952;
    this.captureFragment.getHandler().sendMessageDelayed(message, 1000L);
  }
  
  public void start() {
    Assert.assertNull("不能连续start，必须先stop", this.captureFragment);
    setupCaptureFragment();
  }
  
  public void stop() {
    if (this.captureFragment == null)
      return; 
    this.captureFragment.onPause();
    this.captureFragment.onDestroy();
    this.captureFragment = null;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\QRCodeScanner.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */