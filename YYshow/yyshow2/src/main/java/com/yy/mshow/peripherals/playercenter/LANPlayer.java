package com.yy.mshow.peripherals.playercenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.mshow.msmediasdk.MSPlayer;
import com.mshow.msmediasdk.video.MSVideoView;
import com.yy.mshow.cores.Stream;

public class LANPlayer implements Player, Stream {
  public final MSPlayer msPlayer;
  
  private final int uid;
  
  LANPlayer(int paramInt) {
    this.msPlayer = MSPlayer.create(paramInt);
    this.uid = paramInt;
  }
  
  LANPlayer(String paramString) {
    this.msPlayer = MSPlayer.create(paramString, true);
    this.uid = paramString.hashCode();
  }
  
  protected void finalize() throws Throwable {
    super.finalize();
    MSPlayer.destroy(this.msPlayer);
  }
  
  public View offerStreamView(Context paramContext, Player.ScaleMode paramScaleMode) {
    MSVideoView mSVideoView = this.msPlayer.offerVideoView(paramContext);
    if (paramScaleMode == Player.ScaleMode.AspectFill) {
      mSVideoView.setAspectRatio(2);
      return mSVideoView;
    } 
    if (paramScaleMode == Player.ScaleMode.AspectFit) {
      mSVideoView.setAspectRatio(1);
      return mSVideoView;
    } 
    throw new RuntimeException("Undefined Scale mode for LANPlayer: " + paramScaleMode);
  }
  
  public void play() throws Throwable { this.msPlayer.start(); }
  
  public void reclaimStreamView(View paramView) { this.msPlayer.removeVideoView((MSVideoView)paramView); }
  
  public void setMute(boolean paramBoolean) {
    byte b;
    MSPlayer mSPlayer = this.msPlayer;
    if (paramBoolean) {
      b = 1;
    } else {
      b = 0;
    } 
    mSPlayer.mute(b);
  }
  
  public void stop() throws Throwable { this.msPlayer.stop(); }
  
  public Bitmap takeThumbImage() { return this.msPlayer.snapshot(); }
  
  public int userId() { return this.uid; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\playercenter\LANPlayer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */