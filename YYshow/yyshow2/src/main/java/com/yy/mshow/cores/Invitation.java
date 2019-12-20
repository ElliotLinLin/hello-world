package com.yy.mshow.cores;

import java.util.Locale;

public class Invitation {
  private final String host;
  
  public Invitation(String paramString) { this.host = paramString; }
  
  public String makeString(int paramInt, String paramString) { return (paramString != null) ? String.format(Locale.CHINA, "http://%s/mshow/index.html?lan=%s&wan=%d&old_code=/%d", new Object[] { this.host, paramString, Integer.valueOf(paramInt), Integer.valueOf(paramInt) }) : String.format(Locale.CHINA, "http://%s/mshow/%d", new Object[] { this.host, Integer.valueOf(paramInt) }); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\Invitation.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */