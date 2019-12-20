package com.yy.mshow.peripherals.activity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.cunoraz.gifview.library.GifView;
import com.yy.mshow.peripherals.DurationString;
import java.util.Stack;
import kits.observables.ObservableValue;
import kits.ycmedia.Media;
import kits.ycmedia.StreamOutCamera;

public class PublisherActivity extends Activity {
  private StreamOutCamera camera = new StreamOutCamera();
  
  @BindView(2131624185)
  ImageButton cameraToggleButton;
  
  private Stack<Runnable> cleanups = new Stack();
  
  @BindView(2131624186)
  ImageButton effectorButton;
  
  @BindView(2131624182)
  GifView liveBulbView;
  
  private Media media = new Media();
  
  @BindView(2131624179)
  FrameLayout previewContainer;
  
  @BindView(2131624181)
  ImageView stateOnPreview;
  
  @BindView(2131624183)
  TextView stateTextView;
  
  @BindView(2131624180)
  TextView timerTextView;
  
  private void hideStatusBar() { getWindow().setFlags(1024, 1024); }
  
  private void performPublishing() { this.media.open(Integer.valueOf(MShow.publisher.account.id), Integer.valueOf((MShow.publisher.getProgram()).code), new Media.OpeningCompletion() {
          public void onFailure() {}
          
          public void onSuccess() {
            PublisherActivity.this.media.startPublishing(PublisherActivity.this.camera);
            PublisherActivity.this.cleanups.push(new Runnable() {
                  public void run() {
                    MShow.publisher.close();
                    PublisherActivity.null.this.this$0.media.stopPublishing();
                    PublisherActivity.null.this.this$0.media.close();
                  }
                });
          }
        }); }
  
  private void setupCameraSwitchingButton() {
    this.camera.preview.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<View>() {
          public void updated(boolean param1Boolean, View param1View1, View param1View2) {
            if (param1View2 == null) {
              PublisherActivity.this.cameraToggleButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View param2View) { Toast.makeText(PublisherActivity.null.this.this$0.getApplicationContext(), 2131230775, 0).show(); }
                  });
              return;
            } 
            PublisherActivity.this.cameraToggleButton.setOnClickListener(new View.OnClickListener() {
                  public void onClick(View param2View) { PublisherActivity.null.this.this$0.camera.toggleFacing(); }
                });
          }
        });
    this.cleanups.push(new Runnable() {
          public void run() { this.this$0.camera.preview.unhook(PublisherActivity.this); }
        });
  }
  
  private void setupCloseButton() {
    final ImageButton close = (ImageButton)findViewById(2131624184);
    imageButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) {
            PublisherActivity.this.finish();
            close.setEnabled(false);
          }
        });
  }
  
  private void setupEffectorSwitchingButton() {
    this.camera.isEffectorOn.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            int i;
            ImageButton imageButton = PublisherActivity.this.effectorButton;
            if (param1Boolean2.booleanValue()) {
              i = 2130903066;
            } else {
              i = 2130903067;
            } 
            imageButton.setBackgroundResource(i);
          }
        });
    this.effectorButton.setOnClickListener(new View.OnClickListener() {
          public void onClick(View param1View) { PublisherActivity.this.camera.toggleEffector(); }
        });
    this.cleanups.push(new Runnable() {
          public void run() { this.this$0.camera.isEffectorOn.unhook(PublisherActivity.this); }
        });
  }
  
  private void setupLivingStateWidgets() {
    MShow.publisher.presentingState.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<MSPublisher.PresentingState>() {
          public void updated(boolean param1Boolean, MSPublisher.PresentingState param1PresentingState1, MSPublisher.PresentingState param1PresentingState2) {
            switch (PublisherActivity.null.$SwitchMap$com$yy$mshow$MSPublisher$PresentingState[param1PresentingState2.ordinal()]) {
              default:
                return;
              case 1:
                PublisherActivity.this.stateOnPreview.setVisibility(4);
                PublisherActivity.this.liveBulbView.setVisibility(0);
                PublisherActivity.this.stateTextView.setText("直播中");
                return;
              case 2:
                PublisherActivity.this.stateOnPreview.setVisibility(0);
                PublisherActivity.this.stateOnPreview.setBackgroundResource(2130837641);
                PublisherActivity.this.liveBulbView.setVisibility(4);
                PublisherActivity.this.stateTextView.setText("预备");
                return;
              case 3:
                break;
            } 
            PublisherActivity.this.stateOnPreview.setVisibility(0);
            PublisherActivity.this.stateOnPreview.setBackgroundResource(2130837642);
            PublisherActivity.this.liveBulbView.setVisibility(4);
            PublisherActivity.this.stateTextView.setText("准备中");
          }
        });
    this.cleanups.push(new Runnable() {
          public void run() { MShow.publisher.presentingState.unhook(PublisherActivity.this); }
        });
  }
  
  private void setupPreviewContainerView() { this.camera.preview.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<View>() {
          public void updated(boolean param1Boolean, View param1View1, View param1View2) {
            if (param1View2 == null) {
              PublisherActivity.this.previewContainer.removeAllViews();
              return;
            } 
            PublisherActivity.this.previewContainer.addView(param1View2);
          }
        }); }
  
  private void setupStateBulb() {
    this.liveBulbView.setVisibility(4);
    this.liveBulbView.play();
  }
  
  private void setupTimerTextView() {
    MShow.publisher.elapsed.hook(this, true, MShow.asyncMain, new ObservableValue.Observer<Integer>() {
          public void updated(boolean param1Boolean, Integer param1Integer1, Integer param1Integer2) { PublisherActivity.this.timerTextView.setText(DurationString.make(param1Integer2.intValue())); }
        });
    this.cleanups.push(new Runnable() {
          public void run() { MShow.publisher.elapsed.unhook(PublisherActivity.this); }
        });
  }
  
  public void onBackPressed() {}
  
  protected void onCreate(Bundle paramBundle) {
    super.onCreate(paramBundle);
    requestWindowFeature(1);
    getWindow().addFlags(128);
    hideStatusBar();
    setContentView(2130968655);
    ButterKnife.bind(this);
    setupCloseButton();
    setupTimerTextView();
    setupStateBulb();
    setupLivingStateWidgets();
    setupEffectorSwitchingButton();
    setupCameraSwitchingButton();
    setupPreviewContainerView();
    performPublishing();
  }
  
  protected void onDestroy() {
    super.onDestroy();
    while (!this.cleanups.isEmpty())
      ((Runnable)this.cleanups.pop()).run(); 
  }
  
  protected void onPause() {
    super.onPause();
    this.camera.pause();
  }
  
  protected void onResume() {
    super.onResume();
    this.camera.resume();
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\activity\PublisherActivity.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */