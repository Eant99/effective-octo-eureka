package com.googosoft.ccb;

import java.io.UnsupportedEncodingException;

public class test {

	public static void main(String[] args) throws UnsupportedEncodingException {
		String a = "1";
		byte[] a1 = a.getBytes("UTF-8");
		byte[] a2 = a.getBytes("ASCII");
		String a3 = new String(a1);
		String a4 = new String(a2);
		System.out.println(a3);
		System.out.println(a4);
	}
	
}
