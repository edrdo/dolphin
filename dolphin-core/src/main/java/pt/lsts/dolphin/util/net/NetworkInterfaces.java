package pt.lsts.dolphin.util.net;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;

import pt.lsts.dolphin.runtime.EnvironmentException;

public final class NetworkInterfaces {

   
  public static Collection<InetAddress> get(boolean includeLoopback) {
    ArrayList<InetAddress> itfs = new ArrayList<>();
    try {
  
      Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();
      while (nis.hasMoreElements()) {
        NetworkInterface ni = nis.nextElement();
        try {
          if (ni.isLoopback() && !includeLoopback)
            continue;
        }
        catch (Exception e) {
          continue;
        }

        Enumeration<InetAddress> adrs = ni.getInetAddresses();
        while (adrs.hasMoreElements()) {
          InetAddress addr = adrs.nextElement();
          if (addr instanceof Inet4Address)
            itfs.add(addr);
        }
      }
    }
    catch (Exception e) {
      throw new EnvironmentException(e);
    }
    return itfs;
  }
  
  private NetworkInterfaces() { }
  
}
