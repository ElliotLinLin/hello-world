package com.yy.mshow.peripherals.playercenter;

import java.util.ArrayList;
import java.util.Arrays;
import junit.framework.Assert;

public class RTMPSource {
  private static ArrayList<Integer> availableRTMPIDs = new ArrayList(Arrays.asList(new Integer[] { null, null, null, (new Integer[4][2] = (new Integer[4][1] = (new Integer[4][0] = Integer.valueOf(10001)).valueOf(10002)).valueOf(10003)).valueOf(10004) }));
  
  public final String urlString;
  
  public final Integer userId;
  
  private RTMPSource(int paramInt, String paramString) {
    this.userId = Integer.valueOf(paramInt);
    this.urlString = paramString;
  }
  
  public static boolean containsId(int paramInt) { return (paramInt >= 10001 && paramInt <= 10004); }
  
  public static void destroy(RTMPSource paramRTMPSource) {
    boolean bool;
    Assert.assertTrue(containsId(paramRTMPSource.userId.intValue()));
    if (!availableRTMPIDs.contains(paramRTMPSource.userId)) {
      bool = true;
    } else {
      bool = false;
    } 
    Assert.assertTrue(bool);
    availableRTMPIDs.add(paramRTMPSource.userId);
  }
  
  public static RTMPSource make(String paramString) {
    Assert.assertNotNull(paramString);
    if (!availableRTMPIDs.isEmpty()) {
      boolean bool1 = true;
      Assert.assertTrue("最多只能创建4个", bool1);
      return new RTMPSource(((Integer)availableRTMPIDs.remove(0)).intValue(), paramString);
    } 
    boolean bool = false;
    Assert.assertTrue("最多只能创建4个", bool);
    return new RTMPSource(((Integer)availableRTMPIDs.remove(0)).intValue(), paramString);
  }
  
  public static RTMPSource restore(int paramInt, String paramString) {
    Assert.assertTrue(availableRTMPIDs.contains(Integer.valueOf(paramInt)));
    availableRTMPIDs.remove(Integer.valueOf(paramInt));
    return new RTMPSource(paramInt, paramString);
  }
  
  public boolean equals(Object paramObject) { return (paramObject instanceof RTMPSource && this.userId.equals(((RTMPSource)paramObject).userId)); }
  
  public int hashCode() { return this.userId.intValue(); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\playercenter\RTMPSource.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */