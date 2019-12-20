package com.yy.mshow.peripherals;

import java.util.HashMap;

public class RTMPPlatformResources {
  public static int get(LivePlatforms.Platform paramPlatform) {
    boolean bool;
    HashMap hashMap = new HashMap();
    hashMap.put(LivePlatforms.Platform.Other, Integer.valueOf(2130903064));
    hashMap.put(LivePlatforms.Platform.Huya, Integer.valueOf(2130903085));
    hashMap.put(LivePlatforms.Platform.Douyu, Integer.valueOf(2130903083));
    hashMap.put(LivePlatforms.Platform.Panda, Integer.valueOf(2130903087));
    hashMap.put(LivePlatforms.Platform.Bilibili, Integer.valueOf(2130903082));
    hashMap.put(LivePlatforms.Platform.Longzhu, Integer.valueOf(2130903086));
    hashMap.put(LivePlatforms.Platform.Zhanqi, Integer.valueOf(2130903091));
    hashMap.put(LivePlatforms.Platform.Quanmin, Integer.valueOf(2130903088));
    hashMap.put(LivePlatforms.Platform.Hani, Integer.valueOf(2130903084));
    hashMap.put(LivePlatforms.Platform.Wanwan, Integer.valueOf(2130903089));
    if (paramPlatform != null) {
      bool = true;
    } else {
      bool = false;
    } 
    return Boolean.valueOf(bool).booleanValue() ? ((Integer)hashMap.get(paramPlatform)).intValue() : 2130903064;
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\RTMPPlatformResources.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */