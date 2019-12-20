package com.yy.mshow.peripherals.playercenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.MainThread;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.wang.avi.AVLoadingIndicatorView;
import com.ycloud.live.YCConstant;
import com.ycloud.live.video.YCTextureVideoView;
import com.yy.mshow.cores.Stream;
import java.util.ArrayList;
import java.util.Iterator;
import junit.framework.Assert;
import kits.threading.RunInMain;
import kits.uikits.Dimens;
import kits.ycmedia.StreamPlayer;

public class YCRTMPPlayer implements Stream, Player {
  private boolean shouldMute = true;
  
  public final RTMPSource source;
  
  private StreamPlayer streamPlayer;
  
  private ArrayList<WrapperLayout> wrappers = new ArrayList();
  
  YCRTMPPlayer(RTMPSource paramRTMPSource) { this.source = paramRTMPSource; }
  
  private void attachVideoView(final WrapperLayout wrapper) { RunInMain.dispatch(true, new Runnable() {
          public void run() {
            YCTextureVideoView yCTextureVideoView = YCRTMPPlayer.this.streamPlayer.createVideoView(wrapper.getContext());
            if (yCTextureVideoView != null) {
              wrapper.attachVideoView(yCTextureVideoView);
              YCRTMPPlayer.this.streamPlayer.addThumbImageChangedListener(wrapper);
            } 
          }
        }); }
  
  private void detachVideoViewIfNeeded(final WrapperLayout wrapper) {
    if (paramWrapperLayout.videoView != null)
      RunInMain.dispatch(true, new Runnable() {
            public void run() {
              YCRTMPPlayer.this.streamPlayer.removeThumbImageChangedListener(wrapper);
              wrapper.detachVideoView();
              YCRTMPPlayer.this.streamPlayer.destroyVideoView(wrapper.videoView);
            }
          }); 
  }
  
  void bindPlayer(StreamPlayer paramStreamPlayer) {
    Assert.assertNull(this.streamPlayer);
    this.streamPlayer = paramStreamPlayer;
    this.streamPlayer.startStreaming();
    this.streamPlayer.setMute(this.shouldMute);
    Iterator iterator = this.wrappers.iterator();
    while (iterator.hasNext())
      attachVideoView((WrapperLayout)iterator.next()); 
  }
  
  @MainThread
  public View offerStreamView(Context paramContext, Player.ScaleMode paramScaleMode) {
    WrapperLayout wrapperLayout = new WrapperLayout(paramContext, paramScaleMode);
    if (this.streamPlayer != null)
      attachVideoView(wrapperLayout); 
    this.wrappers.add(wrapperLayout);
    return wrapperLayout;
  }
  
  public void play() {}
  
  @MainThread
  public void reclaimStreamView(View paramView) {
    Assert.assertTrue(paramView instanceof WrapperLayout);
    WrapperLayout wrapperLayout = (WrapperLayout)paramView;
    detachVideoViewIfNeeded(wrapperLayout);
    this.wrappers.remove(wrapperLayout);
  }
  
  public void setMute(boolean paramBoolean) {
    if (this.streamPlayer != null)
      this.streamPlayer.setMute(paramBoolean); 
    this.shouldMute = paramBoolean;
  }
  
  public void stop() {}
  
  public Bitmap takeThumbImage() { return (this.streamPlayer == null) ? null : this.streamPlayer.takeThumbImage(); }
  
  void unbindPlayer() {
    Iterator iterator = this.wrappers.iterator();
    while (iterator.hasNext()) {
      WrapperLayout wrapperLayout;
      Assert.assertNotNull(wrapperLayout.videoView);
      detachVideoViewIfNeeded(wrapperLayout);
    } 
    this.streamPlayer.stopStreaming();
    this.streamPlayer = null;
  }
  
  public int userId() { return this.source.userId.intValue(); }
  
  class WrapperLayout extends FrameLayout implements StreamPlayer.ThumbImageChangedListener {
    private final ImageView imageView;
    
    private AVLoadingIndicatorView indicatorView;
    
    private final Player.ScaleMode scaleMode;
    
    private YCTextureVideoView videoView;
    
    WrapperLayout(Context param1Context, Player.ScaleMode param1ScaleMode) {
      super(param1Context);
      this.scaleMode = param1ScaleMode;
      this.imageView = new ImageView(param1Context);
      this.imageView.setBackgroundColor(-16777216);
      ImageView imageView1 = this.imageView;
      if (param1ScaleMode == Player.ScaleMode.AspectFit) {
        scaleType = ImageView.ScaleType.FIT_CENTER;
      } else {
        scaleType = ImageView.ScaleType.CENTER_CROP;
      } 
      imageView1.setScaleType(scaleType);
      this.imageView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      addView(this.imageView);
      addLoadingIndicatorView(param1Context);
      this.indicatorView.show();
    }
    
    private void addLoadingIndicatorView(Context param1Context) {
      this.indicatorView = new AVLoadingIndicatorView(param1Context);
      this.indicatorView.setIndicator("BallTrianglePathIndicator");
      this.indicatorView.setIndicatorColor(-1);
      addView(this.indicatorView);
      FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(Dimens.Pixels.fromDP(30), Dimens.Pixels.fromDP(30));
      layoutParams.gravity = 17;
      this.indicatorView.setLayoutParams(layoutParams);
    }
    
    void attachVideoView(YCTextureVideoView param1YCTextureVideoView) {
      Assert.assertNull(this.videoView);
      this.indicatorView.setVisibility(8);
      this.videoView = param1YCTextureVideoView;
      if (this.scaleMode == Player.ScaleMode.AspectFill) {
        param1YCTextureVideoView.setScaleMode(YCConstant.ScaleMode.ClipToBounds);
      } else if (this.scaleMode == Player.ScaleMode.AspectFit) {
        param1YCTextureVideoView.setScaleMode(YCConstant.ScaleMode.AspectFit);
      } else {
        throw new RuntimeException("Undefined ScaleMode value: " + this.scaleMode);
      } 
      param1YCTextureVideoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      addView(param1YCTextureVideoView);
    }
    
    void detachVideoView() {
      if (this.videoView != null) {
        removeView(this.videoView);
        this.videoView = null;
        this.indicatorView.setVisibility(0);
      } 
    }
    
    public void update(Bitmap param1Bitmap) { this.imageView.setImageBitmap(param1Bitmap); }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\playercenter\YCRTMPPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */