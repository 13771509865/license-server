package com.yozosoft.licenseserver.util;

import com.google.common.net.InetAddresses;
import org.apache.commons.lang3.StringUtils;

import java.net.Inet4Address;
import java.net.InetAddress;

public class IpUtils {

    public static Integer inetAton(String ip) {
        if(StringUtils.isNotBlank(ip)){
            boolean isIp = InetAddresses.isInetAddress(ip);
            if(isIp){
                InetAddress inetAddress = InetAddresses.forString(ip);
                return InetAddresses.coerceToInteger(inetAddress);
            }
        }
        return null;
    }

    public static String inetNtoa(Integer ip) {
        if(ip!=null){
            Inet4Address inet4Address = InetAddresses.fromInteger(ip);
            return InetAddresses.toAddrString(inet4Address);
        }
        return null;
    }

    public static void main(String[] args) {
        String s = inetNtoa(0);
        System.out.println(s);
    }
}
