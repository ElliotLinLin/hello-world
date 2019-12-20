package com.yy.mshow.peripherals;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.util.Log;

public class JumpPermissionManagement {
  private static final String MANUFACTURER_HUAWEI = "Huawei";
  
  private static final String MANUFACTURER_LENOVO = "LENOVO";
  
  private static final String MANUFACTURER_LETV = "Letv";
  
  private static final String MANUFACTURER_LG = "LG";
  
  private static final String MANUFACTURER_MEIZU = "Meizu";
  
  private static final String MANUFACTURER_OPPO = "OPPO";
  
  private static final String MANUFACTURER_SAMSUNG = "samsung";
  
  private static final String MANUFACTURER_SONY = "Sony";
  
  private static final String MANUFACTURER_VIVO = "vivo";
  
  private static final String MANUFACTURER_XIAOMI = "Xiaomi";
  
  private static final String MANUFACTURER_YULONG = "YuLong";
  
  private static final String MANUFACTURER_ZTE = "ZTE";
  
  private static void ApplicationInfo(Activity paramActivity) {
    Intent intent = new Intent();
    intent.addFlags(268435456);
    if (Build.VERSION.SDK_INT >= 9) {
      intent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
      intent.setData(Uri.fromParts("package", paramActivity.getPackageName(), null));
    } else if (Build.VERSION.SDK_INT <= 8) {
      intent.setAction("android.intent.action.VIEW");
      intent.setClassName("com.android.settings", "com.android.settings.InstalledAppDetails");
      intent.putExtra("com.android.settings.ApplicationPkgName", paramActivity.getPackageName());
    } 
    paramActivity.startActivity(intent);
  }
  
  public static void GoToSetting(Activity paramActivity) {
    String str = Build.MANUFACTURER;
    byte b = -1;
    switch (str.hashCode()) {
      default:
        switch (b) {
          default:
            ApplicationInfo(paramActivity);
            Log.e("goToSetting", "目前暂不支持此系统");
            return;
          case 0:
            Huawei(paramActivity);
            return;
          case 1:
            Meizu(paramActivity);
            return;
          case 2:
            Xiaomi(paramActivity);
            return;
          case 3:
            Sony(paramActivity);
            return;
          case 4:
            OPPO(paramActivity);
            return;
          case 5:
            LG(paramActivity);
            return;
          case 6:
            break;
        } 
        break;
      case -2122609145:
        if (str.equals("Huawei"))
          b = 0; 
      case 74224812:
        if (str.equals("Meizu"))
          b = 1; 
      case -1675632421:
        if (str.equals("Xiaomi"))
          b = 2; 
      case 2582855:
        if (str.equals("Sony"))
          b = 3; 
      case 2432928:
        if (str.equals("OPPO"))
          b = 4; 
      case 2427:
        if (str.equals("LG"))
          b = 5; 
      case 2364891:
        if (str.equals("Letv"))
          b = 6; 
    } 
    Letv(paramActivity);
  }
  
  private static void Huawei(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "android.support.v7.appcompat");
    intent.setComponent(new ComponentName("com.huawei.systemmanager", "com.huawei.permissionmanager.ui.MainActivity"));
    paramActivity.startActivity(intent);
  }
  
  private static void LG(Activity paramActivity) {
    Intent intent = new Intent("android.intent.action.MAIN");
    intent.setFlags(268435456);
    intent.putExtra("packageName", "android.support.v7.appcompat");
    intent.setComponent(new ComponentName("com.android.settings", "com.android.settings.Settings$AccessLockSummaryActivity"));
    paramActivity.startActivity(intent);
  }
  
  private static void Letv(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "android.support.v7.appcompat");
    intent.setComponent(new ComponentName("com.letv.android.letvsafe", "com.letv.android.letvsafe.PermissionAndApps"));
    paramActivity.startActivity(intent);
  }
  
  private static void Meizu(Activity paramActivity) {
    Intent intent = new Intent("com.meizu.safe.security.SHOW_APPSEC");
    intent.addCategory("android.intent.category.DEFAULT");
    intent.putExtra("packageName", "android.support.v7.appcompat");
    paramActivity.startActivity(intent);
  }
  
  private static void OPPO(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "android.support.v7.appcompat");
    intent.setComponent(new ComponentName("com.color.safecenter", "com.color.safecenter.permission.PermissionManagerActivity"));
    paramActivity.startActivity(intent);
  }
  
  private static void Sony(Activity paramActivity) {
    Intent intent = new Intent();
    intent.setFlags(268435456);
    intent.putExtra("packageName", "android.support.v7.appcompat");
    intent.setComponent(new ComponentName("com.sonymobile.cta", "com.sonymobile.cta.SomcCTAMainActivity"));
    paramActivity.startActivity(intent);
  }
  
  private static void Xiaomi(Activity paramActivity) {
    Intent intent = new Intent("miui.intent.action.APP_PERM_EDITOR");
    intent.setComponent(new ComponentName("com.miui.securitycenter", "com.miui.permcenter.permissions.AppPermissionsEditorActivity"));
    intent.putExtra("extra_pkgname", "android.support.v7.appcompat");
    paramActivity.startActivity(intent);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\JumpPermissionManagement.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */