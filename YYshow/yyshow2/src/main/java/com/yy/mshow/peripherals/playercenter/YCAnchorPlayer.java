package com.yy.mshow.peripherals.playercenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import com.ycloud.live.YCConstant;
import com.ycloud.live.video.YCTextureVideoView;
import junit.framework.Assert;
import kits.ycmedia.StreamIn;
import kits.ycmedia.StreamPlayer;

public class YCAnchorPlayer implements Player {
  private final StreamPlayer internalPlayer;
  
  YCAnchorPlayer(Context paramContext, StreamIn paramStreamIn) { this.internalPlayer = new StreamPlayer(paramContext, paramStreamIn); }
  
  public View offerStreamView(Context paramContext, Player.ScaleMode paramScaleMode) {
    WrapperView wrapperView = new WrapperView(paramContext, paramScaleMode);
    this.internalPlayer.addThumbImageChangedListener(wrapperView);
    YCTextureVideoView yCTextureVideoView = this.internalPlayer.createVideoView(paramContext);
    if (yCTextureVideoView != null) {
      if (paramScaleMode == Player.ScaleMode.AspectFill) {
        yCTextureVideoView.setScaleMode(YCConstant.ScaleMode.ClipToBounds);
      } else {
        yCTextureVideoView.setScaleMode(YCConstant.ScaleMode.AspectFit);
      } 
      wrapperView.setVideoView(yCTextureVideoView);
    } 
    return wrapperView;
  }
  
  public void play() { this.internalPlayer.startStreaming(); }
  
  public void reclaimStreamView(View paramView) {
    Assert.assertTrue(paramView instanceof WrapperView);
    WrapperView wrapperView;
    if (wrapperView.videoView != null) {
      this.internalPlayer.destroyVideoView(wrapperView.videoView);
      wrapperView.unsetVideoView();
      this.internalPlayer.removeThumbImageChangedListener(wrapperView);
    } 
  }
  
  public void setMute(boolean paramBoolean) { this.internalPlayer.setMute(paramBoolean); }
  
  public void stop() { this.internalPlayer.stopStreaming(); }
  
  public Bitmap takeThumbImage() { return this.internalPlayer.takeThumbImage(); }
  
  public int userId() { return this.internalPlayer.stream.userId(); }
  
  private class WrapperView extends FrameLayout implements StreamPlayer.ThumbImageChangedListener {
    private final ImageView imageView;
    
    private YCTextureVideoView videoView;
    
    public WrapperView(Context param1Context, Player.ScaleMode param1ScaleMode) {
      super(param1Context);
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
    }
    
    public void setVideoView(YCTextureVideoView param1YCTextureVideoView) {
      Assert.assertNotNull(param1YCTextureVideoView);
      this.videoView = param1YCTextureVideoView;
      param1YCTextureVideoView.setLayoutParams(new ViewGroup.LayoutParams(-1, -1));
      addView(param1YCTextureVideoView);
    }
    
    public void unsetVideoView() {
      Assert.assertNotNull(this.videoView);
      removeView(this.videoView);
    }
    
    public void update(Bitmap param1Bitmap) { this.imageView.setImageBitmap(param1Bitmap); }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\playercenter\YCAnchorPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */