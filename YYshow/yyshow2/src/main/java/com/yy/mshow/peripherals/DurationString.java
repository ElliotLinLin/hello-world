package com.yy.mshow.peripherals;

import java.util.Locale;

public class DurationString {
  public static String make(int paramInt) {
    paramInt = Math.max(0, paramInt);
    int i = paramInt / 60;
    int j = paramInt / 3600;
    return String.format(Locale.CHINA, "%02d:%02d:%02d", new Object[] { Integer.valueOf(j % 3600), Integer.valueOf(i % 60), Integer.valueOf(paramInt % 60) });
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\DurationString.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */