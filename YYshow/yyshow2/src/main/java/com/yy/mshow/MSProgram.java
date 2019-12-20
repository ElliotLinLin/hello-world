package com.yy.mshow;

import junit.framework.Assert;

public class MSProgram {
  private static final String baseString = "/v1/programs";
  
  public final String baseRequestString;
  
  public final int code;
  
  public MSProgram(int paramInt) {
    this.code = paramInt;
    this.baseRequestString = "/v1/programs/" + paramInt;
  }
  
  public static String makeBaseUrl(String paramString) { return "/v1/programs/" + paramString; }
  
  private String makeUrl(String paramString) {
    Assert.assertTrue("[MSProgram][makeUrl] opString参数应该以'/'作为根符号", paramString.startsWith("/"));
    return this.baseRequestString + paramString;
  }
  
  public boolean equals(Object paramObject) { return (paramObject instanceof MSProgram && ((MSProgram)paramObject).code == this.code); }
  
  public String makeUrl(Object paramObject) { return makeUrl("/" + paramObject); }
  
  public String makeUrl(Object paramObject1, Object paramObject2) { return makeUrl("/" + paramObject1 + "/" + paramObject2); }
  
  public String makeUrl(Object paramObject1, Object paramObject2, Object paramObject3) { return makeUrl("/" + paramObject1 + "/" + paramObject2 + "/" + paramObject3); }
  
  public String toString() { return "(MSProgram %d" + this.code + ")"; }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\MSProgram.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */