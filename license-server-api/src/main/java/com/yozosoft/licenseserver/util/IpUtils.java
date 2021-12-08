package com.yozosoft.licenseserver.util;

import com.google.common.net.InetAddresses;

import java.net.Inet4Address;
import java.net.InetAddress;

public class IpUtils {

    public static Integer inetAton(String ip) {
        InetAddress inetAddress = InetAddresses.forString(ip);
        return InetAddresses.coerceToInteger(inetAddress);
    }

    public static String inetNtoa(Integer ip) {
        Inet4Address inet4Address = InetAddresses.fromInteger(ip);
        return InetAddresses.toAddrString(inet4Address);
    }

    public static void main(String[] args) {
        String s = inetNtoa(2130706433);
        System.out.println(s);
    }
}
