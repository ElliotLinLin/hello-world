package com.yy.mshow.cores;

public class Bitrate {
  public final int amount;
  
  public Bitrate(int paramInt) { this.amount = paramInt; }
  
  public static Bitrate fromString(String paramString) { return new Bitrate(Integer.valueOf(paramString.replace("K", "")).intValue()); }
  
  public static String makeString(Bitrate paramBitrate) { return String.valueOf(paramBitrate.amount) + "K"; }
  
  public boolean equals(Object paramObject) { return (paramObject instanceof Bitrate && ((Bitrate)paramObject).amount == this.amount); }
  
  public int hashCode() { return this.amount; }
  
  public String toString() { return "(Bitrate " + this.amount + "K)"; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\Bitrate.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */