package com.yy.mshow.peripherals.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
 import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
 import android.widget.Button;
import android.widget.ImageButton;
import butterknife.BindView;
import butterknife.ButterKnife;

import com.yy.mshow.R;
 import java.util.Stack;


public class MainActivity extends AppCompatActivity {
  @BindView(R.id.button_create_program)
  Button capturingButton;
  
  private Stack<Runnable> cleanups = new Stack();
  
  @BindView(R.id.button_join_program)
  ImageButton directingButton;
  
  private Intent getAppDetailSettingIntent() {
    Intent intent = new Intent();
    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
    intent.setData(Uri.fromParts("package", getPackageName(), null));
    return intent;
  }

    public static boolean hasCameraPermission() {

        boolean canUse =true;

        Camera mCamera =null;

        try{

            mCamera = Camera.open();

// setParameters 是针对魅族MX5。MX5通过Camera.open()拿到的Camera对象不为null

            Camera.Parameters mParameters = mCamera.getParameters();

            mCamera.setParameters(mParameters);

        }catch(Exception e) {
            e.printStackTrace();
            canUse =false;

        }

        if(mCamera !=null) {

            mCamera.release();

        }
        return canUse;

    }
  
  private void requestCameraPermission() {
    if (Build.VERSION.SDK_INT >= 23) {
      ActivityCompat.requestPermissions(this, new String[] { "android.permission.CAMERA" }, 1);
      return;
    } 
    (new AlertDialog.Builder(this)).setTitle("请检查\"相机\"权限，否则不能正常扫码、开播").setPositiveButton("去检查", new DialogInterface.OnClickListener() {
          public void onClick(DialogInterface param1DialogInterface, int param1Int) {
            MainActivity.this.startActivity(MainActivity.this.getAppDetailSettingIntent());
            MainActivity.this.finish();
          }
        }).create().show();
  }
  
  private void setupButtons() {
    this.directingButton.setEnabled(false);
    this.capturingButton.setEnabled(false);

  }
  
  private void setupFlowShowCapturingActivity() {
    }
  
  private void setupPopupDialog() {

  }
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.activity_main);
    ButterKnife.bind(this);
    startActivity(new Intent(this, Splash.class));
    setupButtons();
    setupPopupDialog();
    setupFlowShowCapturingActivity();
  }
  
  protected void onDestroy() {
    super.onDestroy();
    while (!this.cleanups.isEmpty())
      ((Runnable)this.cleanups.pop()).run(); 
  }
}



