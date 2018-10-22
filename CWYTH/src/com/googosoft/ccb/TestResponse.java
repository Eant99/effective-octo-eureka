package com.googosoft.ccb;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

public class TestResponse {

	// public String transport(String url, String message){
	// 　　StringBuffer sb = new StringBuffer();
	// 　　　　URL urls = new URL(url);
	// 　　　　HttpURLConnection uc = (HttpURLConnection) urls.openConnection();
	// 　　　　uc.setRequestMethod("POST");
	// 　　　　uc.setRequestProperty("content-type",
	// "application/x-www-form-urlencoded");
	// 　　　　uc.setRequestProperty("charset", "UTF-8");
	// 　　　　uc.setDoOutput(true);
	// 　　　　uc.setDoInput(true);
	// 　　　　uc.setReadTimeout(10000);
	// 　　　　uc.setConnectTimeout(10000);
	//
	// 　　　　OutputStream os = uc.getOutputStream();
	// 　　　　DataOutputStream dos = new DataOutputStream(os);
	// 　　　　dos.write(message.getBytes("utf-8"));
	// 　　　　dos.flush();
	// 　　　　os.close();
	//
	// 　　　　BufferedReader in = new BufferedReader(new
	// InputStreamReader(uc.getInputStream(), "utf-8"));
	// 　　　　String readLine = "";
	// 　　　　while ((readLine = in.readLine()) != null)
	// 　　　　{
	// 　　　　　　sb.append(readLine);
	// 　　　　}
	// 　　　　in.close();
	// 　　　　}
	// 　　　　return sb.toString();
	// }

	public static byte[] transport(String url, String message) throws Exception {
		StringBuffer sb = new StringBuffer();
		try {
			URL urls = new URL(url);
			HttpURLConnection uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("POST");
			uc.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			uc.setRequestProperty("charset", "UTF-8");
			// uc.setRequestProperty("chanl_cust_no", "SD37000009021270501");
			// // uc.setRequestProperty("type", "des");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setReadTimeout(10000);
			uc.setConnectTimeout(10000);
			OutputStream os = uc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
			dos.write(message.getBytes("utf-8"));
			dos.flush();
			os.close();
			InputStream inStream = uc.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);
//			System.out.println(btImg.length);
			if(message.contains("des")){
				byte[] newData = new byte[32];
//				System.out.println(btImg.length + "@" + new String(btImg));
				
				for (int i = 6; i < btImg.length; i++) {
					newData[i - 6] = btImg[i];
				}
//				System.out.println(newData.length + "@" + new String(newData));
				return newData;
			}else if(message.contains("pub")){
				byte[] newData = new byte[168];
//				System.out.println(btImg.length + "@" + new String(btImg));
				
				for (int i = 6; i < btImg.length; i++) {
					newData[i - 6] = btImg[i];
				}
//				System.out.println(newData.length + "@" + new String(newData));
				return newData;
			}else if(message.contains("mypri")){
				System.out.println(btImg.length+"^^^^^^22222^^^^^^");
				byte[] newData = new byte[640];
//				System.out.println(btImg.length + "@" + new String(btImg));
				
				for (int i = 6; i < btImg.length; i++) {
					newData[i - 6] = btImg[i];
				}
//				System.out.println(newData.length + "@" + new String(newData));
				return newData;
			}else if(message.contains("mypub")){
				byte[] newData = new byte[168];
//				System.out.println(btImg.length + "@" + new String(btImg));
				
				for (int i = 6; i < btImg.length; i++) {
					newData[i - 6] = btImg[i];
				}
//				System.out.println(newData.length + "@" + new String(newData));
				return newData;
			}
			// BufferedReader in = new BufferedReader(new
			// InputStreamReader(uc.getInputStream(), "utf-8"));
			// String readLine = "";
			// while((readLine = in.readLine()) != null){
			// sb.append(readLine);
			// }
			// in.close();
			// while ((readLine = in.readLine()) != null){
			// sb.append(readLine);
			// }
			// in.close();
			// 　　　　}
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
//		try {
//			byte[] a = transport(
//					"http://124.127.94.46:8181/interlink/KeyTransfer",
//					"chanl_cust_no=SD37000009021270501&type=pub");
//			byte[] a = transport(
//					"http://60.208.80.195:38080/ccb/mypubkey",
//					"chanl_cust_no=SD37000009021270501&type=mypub");
//			System.out.println(new String(a)+"**********"+a.length);
			// System.out.println(a+"@"+a.length());

			// a = a.replace("000000", "");
			// System.out.println(a);
			// System.out.println(a.length());

			// byte[] results = a.getBytes("UTF-8");
			// System.out.println(results.length);
			// System.out.println(results);
			
			
//			byte[] pri = ThreeDESUtil.decryptMode(a);
//			SignatureData aa = new SignatureData();
//			aa.run(pri);
			
			
//			String result = new String(ThreeDESUtil.decryptMode(a));
//			System.out.println(result);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		BASE64Encoder en = new BASE64Encoder();
		
		String a = en.encode(getMyPublicKey());
		System.out.println("@@@@@@@@@@"+a);
		
		getDEScode();
//		System.out.println(new String(getRsaKey()));
		
	}

	/**
	 * 从输入流中获取数据
	 * 
	 * @param inStream
	 *            输入流
	 * @return
	 * @throws Exception
	 */
	public static byte[] readInputStream(InputStream inStream) throws Exception {
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while ((len = inStream.read(buffer)) != -1) {
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	public static byte[] getDEScode() throws Exception{
//		byte[] a = transport(
//				"http://60.208.80.195:38080/ccb/Myprikey",
//				"chanl_cust_no=SD37000009021270501&type=des");
		byte[] a = transport(
				"http://124.127.94.46:8181/interlink/KeyTransfer",
				"chanl_cust_no=SD37000009021270501&type=des");
		byte[] des = ThreeDESUtil.decryptMode(a);
		return des;
	}
	
	public static byte[] getMyPrivateKey() throws Exception{
		byte[] a = transport(
				//"http://60.216.103.154/ccb/Myprikey",
				"http://localhost:8080/ccb/Myprikey",
				"chanl_cust_no=SD37000009021270501&type=mypri");
		byte[] myPrivateKey = DESForUs.decryptMode(a);
		return myPrivateKey;
	}
	
	public static byte[] getMyPublicKey() throws Exception{
		byte[] a = transport(
				//"http://60.216.103.154/ccb/mypubkey",
				"http://localhost:8080/ccb/mypubkey",
				"chanl_cust_no=SD37000009021270501&type=mypub");
		byte[] myPublicKey = DESForUs.decryptMode(a);
		return myPublicKey;
	}
	
	public static byte[] getRsaKey() throws Exception{
		byte[] a = transport(
				"http://124.127.94.46:8181/interlink/KeyTransfer",
				"chanl_cust_no=SD37000009021270501&type=pub");
		byte[] rsaKey = ThreeDESUtil.decryptMode(a);
		return rsaKey;
	}
	
}
