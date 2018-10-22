package com.googosoft.ccb;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class SignatureData {

	 /**
     * 加密算法RSA
     */
    public static final String KEY_ALGORITHM = "RSA";
	
    /**
     * 签名算法
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    
    
	public String run(byte[] a, String content) {
		try {
			// String prikeyvalue =
			// "30820277020100300d";//这是GenerateKeyPair输出的私钥编码
			PKCS8EncodedKeySpec priPKCS8 = new PKCS8EncodedKeySpec(a);
			KeyFactory keyf = KeyFactory.getInstance("RSA");
			PrivateKey myprikey = keyf.generatePrivate(priPKCS8);

			// content =
			// "orderId=10dkfadsfksdkssdkd&amount=80&orderTime=20060509"; //
			// 要签名的信息
			// 用私钥对信息生成数字签名
			java.security.Signature signet = java.security.Signature
					.getInstance("MD5withRSA");
			signet.initSign(myprikey);
			signet.update(content.getBytes("UTF-8"));
			byte[] signed = signet.sign(); // 对信息的数字签名

			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			String sigResult = encoder.encode(signed);
//			System.out.println("signed(签名内容)原值1=" + bytesToHexStr(signed));
			System.out.println("signed(签名内容)原值2=" + sigResult);
			System.out.println("info（原值）=" + content);
			System.out.println("签名并生成文件成功");
			return sigResult;
		} catch (java.lang.Exception e) {
			e.printStackTrace();
			System.out.println("签名并生成文件失败");
		}
		return "";
	}

	public String sign(byte[] a, String content) throws Exception{
		 sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//		String strPriKeyText = new String(a);
//		System.out.println("^^^"+strPriKeyText);
//		byte[] decodeData = decoder.decodeBuffer(strPriKeyText);
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(a);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        //签名算法
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
        
        signature.update(content.getBytes("UTF-8"));
        //数字签名
        byte[] signed = signature.sign();
        //解密的公钥
        byte[] pubKey;
        pubKey = TestResponse.getMyPublicKey();
        VerifySignature a1 = new VerifySignature();
        //签名 content文件名
        a1.run1(pubKey, signed,content);
        
        
        return encoder.encode(signed);
	}
	
	public byte[] signByteReturn (byte[] a, String content) throws Exception{
		PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(a);
		KeyFactory keyFactory = KeyFactory.getInstance(KEY_ALGORITHM);
        PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
        Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
        signature.initSign(privateK);
        signature.update(content.getBytes("UTF-8"));
        byte[] signed = signature.sign();
        return signed;
	}
	
	/**
	 * Transform the specified byte into a Hex String form.
	 */
	public static final String bytesToHexStr(byte[] bcd) {
		StringBuffer s = new StringBuffer(bcd.length * 2);

		for (int i = 0; i < bcd.length; i++) {
			s.append(bcdLookup[(bcd[i] >>> 4) & 0x0f]);
			s.append(bcdLookup[bcd[i] & 0x0f]);
		}

		return s.toString();
	}

	/**
	 * Transform the specified Hex String into a byte array.
	 */
	public static final byte[] hexStrToBytes(String s) {
		byte[] bytes;

		bytes = new byte[s.length() / 2];

		for (int i = 0; i < bytes.length; i++) {
			bytes[i] = (byte) Integer.parseInt(s.substring(2 * i, 2 * i + 2),
					16);
		}

		return bytes;
	}

	private static final char[] bcdLookup = { '0', '1', '2', '3', '4', '5',
			'6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		SignatureData s = new SignatureData();
		// s.run();

	}
}
