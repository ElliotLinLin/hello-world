package com.yy.mshow.peripherals.networking;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Pattern;

public class HostAddress {
  private static final Pattern PATTERN = Pattern.compile("^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");
  
  public static String find() {
    try {
      Enumeration enumeration = NetworkInterface.getNetworkInterfaces();
      while (enumeration.hasMoreElements()) {
        Enumeration enumeration1 = ((NetworkInterface)enumeration.nextElement()).getInetAddresses();
        while (enumeration1.hasMoreElements()) {
          InetAddress inetAddress = (InetAddress)enumeration1.nextElement();
          if (!inetAddress.isLoopbackAddress() && isIPv4(inetAddress.getHostAddress()))
            return inetAddress.getHostAddress(); 
        } 
      } 
    } catch (SocketException socketException) {
      socketException.printStackTrace();
    } 
    return null;
  }
  
  private static boolean isIPv4(String paramString) { return PATTERN.matcher(paramString).matches(); }
}


/* Location:              E:\BaiduNetdiskDownload\androidcompile\dex2jar-2.0\classes30-dex2jar.jar!\com\yy\mshow\peripherals\networking\HostAddress.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.0.7
 */