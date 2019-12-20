package com.yy.mshow.cores;

import kits.observables.ObservableValue;
import org.junit.Assert;

public class RTMPOutputSetting implements OutputSetting {
  public final ObservableValue<Boolean> enabled = new ObservableValue(Boolean.valueOf(false));
  
  public final ObservableValue<String> url = new ObservableValue(null);
  
  public RTMPOutputSetting(String paramString, Boolean paramBoolean) {
    Assert.assertTrue(split(paramString, null));
    this.url.set(paramString);
    this.enabled.set(paramBoolean);
  }
  
  public RTMPOutputSetting(String paramString1, String paramString2, Boolean paramBoolean) {
    Assert.assertTrue(split(paramString1, paramString2, null));
    set(paramString1, paramString2);
    this.enabled.set(paramBoolean);
  }
  
  private static boolean split(String paramString, SplittingCallback paramSplittingCallback) {
    paramString = paramString.trim();
    if (!paramString.toLowerCase().startsWith("rtmp://"))
      return false; 
    paramString = paramString.substring("rtmp://".length());
    int i = paramString.indexOf('?');
    if (i != -1) {
      str1 = paramString.substring(0, i);
      paramString = paramString.substring(i);
    } else {
      str1 = paramString;
      paramString = null;
    } 
    if (!str1.contains("/") || str1.endsWith("/"))
      return false; 
    i = str1.lastIndexOf("/");
    Assert.assertNotEquals("前面已经有了contains判断，所以肯定可以找到斜杠", -1L, i);
    String str3 = str1.substring(0, i);
    if (str3.length() < 3)
      return false; 
    String str2 = str1.substring(i + 1);
    String str1 = str2;
    if (paramString != null)
      str1 = str2 + paramString; 
    if (paramSplittingCallback != null)
      paramSplittingCallback.onSuccess("rtmp://" + str3, str1); 
    return true;
  }
  
  public static boolean split(String paramString1, String paramString2, SplittingCallback paramSplittingCallback) {
    paramString1 = paramString1.trim();
    String str = paramString2.trim();
    paramString2 = paramString1;
    if (paramString1.endsWith("/"))
      paramString2 = paramString1.substring(0, paramString1.length() - 1); 
    paramString1 = str;
    if (str.startsWith("/"))
      paramString1 = str.substring(1); 
    if (paramString2.endsWith("/") || paramString1.startsWith("/"))
      return false; 
    str = paramString1;
    if (!paramString1.isEmpty())
      str = "/" + paramString1; 
    return split(paramString2 + str, paramSplittingCallback);
  }
  
  public String addressPart() {
    final String[] result = new String[1];
    arrayOfString[0] = null;
    split((String)this.url.get(), new SplittingCallback() {
          public void onSuccess(String param1String1, String param1String2) { result[0] = param1String1; }
        });
    return arrayOfString[0];
  }
  
  public String codePart() {
    final String[] result = new String[1];
    arrayOfString[0] = null;
    split((String)this.url.get(), new SplittingCallback() {
          public void onSuccess(String param1String1, String param1String2) { result[0] = param1String2; }
        });
    return arrayOfString[0];
  }
  
  public void set(String paramString1, String paramString2) {
    boolean bool = split(paramString1, paramString2, new SplittingCallback() {
          public void onSuccess(String param1String1, String param1String2) { RTMPOutputSetting.this.url.set(param1String1 + '/' + param1String2); }
        });
    Assert.assertTrue("所设置的地址无效，请在调用RTMPOutputSetting.set(addressPart, codePart)前进行验证。(addressPart: " + paramString1 + ", codePart: " + paramString2 + ")", bool);
  }
  
  public String toString() { return (String)this.url.get(); }
  
  public static interface SplittingCallback {
    void onSuccess(String param1String1, String param1String2);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\cores\RTMPOutputSetting.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */