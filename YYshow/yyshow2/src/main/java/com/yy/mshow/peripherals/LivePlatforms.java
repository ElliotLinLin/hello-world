package com.yy.mshow.peripherals;

import java.util.HashMap;

public class LivePlatforms {
  public static Platform fromRTMPString(String paramString) {
    if (paramString != null) {
      boolean bool;
      String str = paramString.toLowerCase();
      HashMap hashMap = new HashMap();
      hashMap.put("huya", Platform.Huya);
      hashMap.put("douyu", Platform.Douyu);
      hashMap.put("panda", Platform.Panda);
      hashMap.put("bilibili", Platform.Bilibili);
      hashMap.put("acg.tv", Platform.Bilibili);
      hashMap.put("longzhu", Platform.Longzhu);
      hashMap.put("zhanqi", Platform.Zhanqi);
      hashMap.put("quanmin", Platform.Quanmin);
      hashMap.put("hani", Platform.Hani);
      hashMap.put("wanwanyule", Platform.Wanwan);
      boolean bool1 = str.startsWith("rtmp://");
      if (str.length() > "rtmp://".length() + 1) {
        bool = true;
      } else {
        bool = false;
      } 
      if (bool1 && bool) {
        str = str.split("/")[2];
        for (String str1 : hashMap.keySet()) {
          if (str.contains(str1))
            return (Platform)hashMap.get(str1); 
        } 
        return Platform.Other;
      } 
    } 
    return null;
  }
  
  public enum Platform {
    Bilibili, Douyu, Hani, Huya, Longzhu, Other, Panda, Quanmin, Wanwan, Zhanqi;
    
    static  {
      Douyu = new Platform("Douyu", 1);
      Panda = new Platform("Panda", 2);
      Bilibili = new Platform("Bilibili", 3);
      Longzhu = new Platform("Longzhu", 4);
      Zhanqi = new Platform("Zhanqi", 5);
      Quanmin = new Platform("Quanmin", 6);
      Hani = new Platform("Hani", 7);
      Wanwan = new Platform("Wanwan", 8);
      Other = new Platform("Other", 9);
      $VALUES = new Platform[] { Huya, Douyu, Panda, Bilibili, Longzhu, Zhanqi, Quanmin, Hani, Wanwan, Other };
    }
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\LivePlatforms.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */