package com.googosoft.util;

import java.net.InetAddress;
import java.net.UnknownHostException;


public class PublicUtil {
	
	
	public static String getPorjectPath(){
		String nowpath = "";
		nowpath=System.getProperty("user.dir")+"/";
		
		return nowpath;
	}
	/**
	 * 获取本机ip
	 * @return
	 */
	public static String getIp(){
		String ip = "";
		try {
			InetAddress inet = InetAddress.getLocalHost();
			ip = inet.getHostAddress();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		
		return ip;
	}
	
	public static void main(String[] args) {
		System.out.println("本机的ip=" + PublicUtil.getPorjectPath());
	}
	
}