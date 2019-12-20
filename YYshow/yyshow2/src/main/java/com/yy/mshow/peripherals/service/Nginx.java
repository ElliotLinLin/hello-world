package com.yy.mshow.peripherals.service;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;
import com.yy.mshow.peripherals.networking.HostAddress;
import fi.iki.elonen.NanoHTTPD;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import junit.framework.Assert;
import kits.observables.ObservableValue;

public class Nginx {
  private static final int DefaultRTMPPort = 1935;
  
  private static final String TAG = "[Nginx]";
  
  public static final int version = 1;
  
  private AssetManager assetManager;
  
  private final NginxCallbackListener callbackListener;
  
  private String dataDir;
  
  private String hostAddress = null;
  
  public int installedVersion = 0;
  
  public final ObservableValue<Boolean> isRunning = new ObservableValue(Boolean.valueOf(false));
  
  private String nginxPath;
  
  private String prefixDir;
  
  private RTMPListener rtmpListener = null;
  
  public Nginx() {
    this.isRunning.hook(this, true, new ObservableValue.Observer<Boolean>() {
          public void updated(boolean param1Boolean, Boolean param1Boolean1, Boolean param1Boolean2) {
            if (param1Boolean2.booleanValue()) {
              Nginx.access$002(Nginx.this, HostAddress.find());
              return;
            } 
            Nginx.access$002(Nginx.this, null);
          }
        });
    this.callbackListener = new NginxCallbackListener();
  }
  
  private void copyFile(AssetManager paramAssetManager, String paramString) {
    FileOutputStream fileOutputStream;
    try {
      InputStream inputStream = paramAssetManager.open(paramString);
      fileOutputStream = new FileOutputStream(this.dataDir + "/" + paramString);
      byte[] arrayOfByte = new byte[1024];
      while (true) {
        int i = inputStream.read(arrayOfByte);
        if (i != -1) {
          fileOutputStream.write(arrayOfByte, 0, i);
          continue;
        } 
        break;
      } 
    } catch (Exception paramAssetManager) {
      paramAssetManager.printStackTrace();
      return;
    } 
    paramAssetManager.close();
    fileOutputStream.flush();
    fileOutputStream.close();
  }
  
  private void copyFileOrDir(AssetManager paramAssetManager, String paramString) {
    try {
      String[] arrayOfString = paramAssetManager.list(paramString);
      if (arrayOfString.length == 0) {
        copyFile(paramAssetManager, paramString);
        return;
      } 
      String str = this.dataDir + "/" + paramString;
      File file = new File(str);
      if (!file.exists() && !file.mkdir())
        Log.e("[Nginx]", "copyFileOrDir: mkdir failed: " + str); 
      int i = arrayOfString.length;
      for (byte b = 0; b < i; b++) {
        str = arrayOfString[b];
        copyFileOrDir(paramAssetManager, paramString + "/" + str);
      } 
    } catch (IOException paramAssetManager) {
      paramAssetManager.printStackTrace();
    } 
  }
  
  private Boolean execCommand(String paramString1, String paramString2) {
    boolean bool = false;
    ShellUtils.CommandResult commandResult = ShellUtils.execCommand(paramString1 + paramString2, false);
    paramString1 = paramString1 + paramString2 + "\nreturnCode: " + commandResult.result + "\nsuccessMsg: " + commandResult.successMsg + "\nerrorMsg: " + commandResult.errorMsg;
    if (commandResult.result != 0) {
      Log.e("[Nginx]", paramString1);
    } else {
      Log.i("[Nginx]", paramString1);
    } 
    if (commandResult.result == 0)
      bool = true; 
    return Boolean.valueOf(bool);
  }
  
  private Boolean isPortInUse(int paramInt) {
    try {
      ServerSocket serverSocket = new ServerSocket(paramInt);
      serverSocket.setReuseAddress(true);
      serverSocket.close();
      return Boolean.valueOf(false);
    } catch (Exception exception) {
      return Boolean.valueOf(true);
    } 
  }
  
  public String RTMPAddressPrefix() {
    Assert.assertNotNull(this.hostAddress);
    return "rtmp://" + this.hostAddress + ":" + 'ޏ' + "/live";
  }
  
  public Boolean install() {
    copyFileOrDir(this.assetManager, "nginx");
    return execCommand("chmod", " -R 755 " + this.prefixDir);
  }
  
  public void setRTMPListener(RTMPListener paramRTMPListener) { this.rtmpListener = paramRTMPListener; }
  
  public Boolean start() {
    Assert.assertFalse(((Boolean)this.isRunning.get()).booleanValue());
    try {
      this.callbackListener.start();
      if (isPortInUse(1935).booleanValue()) {
        boolean bool2 = true;
        Boolean bool3 = Boolean.valueOf(bool2);
        this.isRunning.set(bool3);
        Log.i("[Nginx]", "start. RTMPAddressPrefix: " + RTMPAddressPrefix());
        return bool3;
      } 
    } catch (IOException iOException) {
      Log.e("[Nginx]", "failed to start http callback listener service");
      iOException.printStackTrace();
      return Boolean.valueOf(false);
    } 
    boolean bool = execCommand(this.nginxPath, " -p " + this.prefixDir).booleanValue();
    Boolean bool1 = Boolean.valueOf(bool);
    this.isRunning.set(bool1);
    Log.i("[Nginx]", "start. RTMPAddressPrefix: " + RTMPAddressPrefix());
    return bool1;
  }
  
  public Boolean stop() {
    Assert.assertTrue(((Boolean)this.isRunning.get()).booleanValue());
    String str = " -s stop" + " -p " + this.prefixDir;
    Boolean bool1 = execCommand(this.nginxPath, str);
    this.callbackListener.stop();
    ObservableValue observableValue = this.isRunning;
    if (!bool1.booleanValue()) {
      boolean bool2 = true;
      observableValue.set(Boolean.valueOf(bool2));
      return bool1;
    } 
    boolean bool = false;
    observableValue.set(Boolean.valueOf(bool));
    return bool1;
  }
  
  public void sync(Context paramContext) {
    this.assetManager = paramContext.getAssets();
    this.dataDir = (paramContext.getApplicationInfo()).dataDir;
    this.prefixDir = this.dataDir + "/nginx";
    this.nginxPath = this.prefixDir + "/sbin/nginx";
  }
  
  private class NginxCallbackListener extends NanoHTTPD {
    private static final String PUBLISH = "publish";
    
    private static final String PUBLISH_DONE = "publish_done";
    
    private static final int RANDOM_PORT = 14222;
    
    private final Set<String> connected = new HashSet();
    
    NginxCallbackListener() { super(14222); }
    
    public NanoHTTPD.Response serve(NanoHTTPD.IHTTPSession param1IHTTPSession) {
      if (param1IHTTPSession.getMethod() == NanoHTTPD.Method.GET) {
        Map map = param1IHTTPSession.getParameters();
        String str1 = (String)((List)map.get("call")).get(0);
        String str2 = (String)((List)map.get("tcurl")).get(0);
        String str3 = (String)((List)map.get("name")).get(0);
        String str4 = str2 + "/" + str3;
        if (str2 != null && str3 != null) {
          if (param1IHTTPSession.getUri().equals("/publish") && "publish".equals(str1)) {
            if (this.connected.contains(str3)) {
              Log.w("[Nginx]", "Deny duplicated connection: " + str4);
              return newFixedLengthResponse(NanoHTTPD.Response.Status.FORBIDDEN, "text/html", "Duplicated connection not allowed");
            } 
            this.connected.add(str3);
            if (Nginx.this.rtmpListener != null && Nginx.this.rtmpListener.onArrive(str4).booleanValue()) {
              Log.i("[Nginx]", "Subscribe: " + str4);
              return newFixedLengthResponse("Accepted");
            } 
            Log.w("[Nginx]", "Connect but not subscribe: " + str4);
            return newFixedLengthResponse(NanoHTTPD.Response.Status.UNAUTHORIZED, "text/html", "Default Unauthorized");
          } 
          if (param1IHTTPSession.getUri().equals("/publish_done") && "publish_done".equals(str1)) {
            if (Nginx.this.rtmpListener != null && this.connected.contains(str3))
              Nginx.this.rtmpListener.onLeave(str4); 
            this.connected.remove(str3);
            Log.i("[Nginx]", "Disconnect: " + str4);
            return newFixedLengthResponse("OK");
          } 
        } 
      } 
      return super.serve(param1IHTTPSession);
    }
    
    public void stop() {
      super.stop();
      this.connected.clear();
    }
  }
  
  public static interface RTMPListener {
    Boolean onArrive(String param1String);
    
    void onLeave(String param1String);
  }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\service\Nginx.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */