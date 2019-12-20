package com.yy.mshow.peripherals.playercenter;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import com.yy.mshow.cores.Stream;

public interface Player extends Stream {
  View offerStreamView(Context paramContext, ScaleMode paramScaleMode);
  
  void play();
  
  void reclaimStreamView(View paramView);
  
  void setMute(boolean paramBoolean);
  
  void stop();
  
  Bitmap takeThumbImage();
  
  int userId();
  
  public enum ScaleMode {
    AspectFill, AspectFit;
    
    static  {
      AspectFill = new ScaleMode("AspectFill", 1);
      $VALUES = new ScaleMode[] { AspectFit, AspectFill };
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\playercenter\Player.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */