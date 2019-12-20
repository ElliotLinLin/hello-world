package com.yy.mshow.cores;

import java.util.Locale;

public class Size {
  public final int height;
  
  public final int width;
  
  public Size(int paramInt1, int paramInt2) {
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public String toString() { return String.format(Locale.CHINA, "(Size %dx%d)", new Object[] { Integer.valueOf(this.width), Integer.valueOf(this.height) }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\Size.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */