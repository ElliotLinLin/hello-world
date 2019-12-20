package com.yy.mshow.cores;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Resolution {
  public final int height;
  
  public final int width;
  
  public Resolution(int paramInt1, int paramInt2) {
    this.width = paramInt1;
    this.height = paramInt2;
  }
  
  public static Resolution fromString(String paramString) {
    Matcher matcher = Pattern.compile("(\\d{3,4})x(\\d{3,4})").matcher(paramString);
    return matcher.find() ? new Resolution(Integer.valueOf(matcher.group(0)).intValue(), Integer.valueOf(matcher.group(1)).intValue()) : null;
  }
  
  public static String makeString(Resolution paramResolution) { return String.format(Locale.CHINA, "%dx%d", new Object[] { Integer.valueOf(paramResolution.width), Integer.valueOf(paramResolution.height) }); }
  
  private double ratio() { return this.width / this.height; }
  
  public boolean equals(Object paramObject) {
    if (paramObject instanceof Resolution) {
      paramObject = (Resolution)paramObject;
      if (this.width == paramObject.width && this.height == paramObject.height)
        return true; 
    } 
    return false;
  }
  
  public int hashCode() { return this.width * 10000 + this.height; }
  
  public boolean isWideScreen() { return (ratio() > 1.77D); }
  
  public String toString() { return String.format(Locale.CHINA, "(Resolution %s)", new Object[] { makeString(this) }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\Resolution.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */