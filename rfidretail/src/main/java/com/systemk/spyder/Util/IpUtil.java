package com.systemk.spyder.Util;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import javax.servlet.http.HttpServletRequest;

public class IpUtil {

	public static void getLocalServerIp() throws Exception {

		Enumeration<NetworkInterface> nienum = NetworkInterface.getNetworkInterfaces();

		while (nienum.hasMoreElements()) {
			NetworkInterface ni = nienum.nextElement();
			Enumeration<InetAddress> kk = ni.getInetAddresses();

			if (ni.getName().equals("eth0") || ni.getName().equals("wlan1")) {
				while (kk.hasMoreElements()) {
					InetAddress inetAddress = (InetAddress) kk.nextElement();
					if (inetAddress.isSiteLocalAddress()) {
						System.out.println(ni.getName());
						System.out.println("Local ip: " + inetAddress.getHostAddress());
					}
				}
			}
		}
	}

	public static String getUserIP(HttpServletRequest request) {

		String ip = request.getHeader("X-FORWARDED-FOR");

		if (ip == null) {

			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null) {

			ip = request.getHeader("WL-Proxy-Client-IP");

		}

		if (ip == null) {

			ip = request.getHeader("HTTP_CLIENT_IP");

		}

		if (ip == null) {

			ip = request.getHeader("HTTP_X_FORWARDED_FOR");

		}

		if (ip == null) {

			ip = request.getRemoteAddr();

		}

		return ip;

	}

}
