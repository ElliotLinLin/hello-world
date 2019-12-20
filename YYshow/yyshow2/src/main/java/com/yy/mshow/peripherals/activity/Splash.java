package com.yy.mshow.peripherals.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.yy.mshow.R;

public class Splash extends AppCompatActivity {
  public void onBackPressed() {}
  
  protected void onCreate(@Nullable Bundle paramBundle) {
    super.onCreate(paramBundle);
    setContentView(R.layout.splash);
    (new Handler(getMainLooper())).postDelayed(new Runnable() {
          public void run() {
            Splash.this.finish();
            Splash.this.overridePendingTransition(17432576, 17432577);
          }
        },2000L);
  }
}


