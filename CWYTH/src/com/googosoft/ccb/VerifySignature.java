package com.googosoft.ccb;
import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;


public class VerifySignature {

	public void run1(byte[] a,byte[] b,String c) { 
		try { 
		X509EncodedKeySpec bobPubKeySpec = new X509EncodedKeySpec(a); 
		KeyFactory keyFactory = KeyFactory.getInstance("RSA"); 
		PublicKey pubKey = keyFactory.generatePublic(bobPubKeySpec); 


//		String info = "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060519"; 
		byte[] signed = b;//这是SignatureData输出的数字签名 
		java.security.Signature signetcheck=java.security.Signature.getInstance("MD5withRSA"); 
		signetcheck.initVerify(pubKey); 
		signetcheck.update(c.getBytes()); 
		if (signetcheck.verify(signed)) { 
				System.out.println("info=" + c);
				System.out.println("签名正常");
		} 
		else System.out.println("非签名正常"); 
		} 
		catch (java.lang.Exception e) {e.printStackTrace();} 
		} 
		/** 
		* Transform the specified byte into a Hex String form. 
		*/ 
	
}
