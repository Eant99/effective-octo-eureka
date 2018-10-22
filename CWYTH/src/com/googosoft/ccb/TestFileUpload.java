package com.googosoft.ccb;
import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;


public class TestFileUpload {

	public static String getFileName(String fileName){
		try {
			//获取des
			byte[] desCode = TestResponse.getDEScode();
			
			byte[] names = fileName.getBytes();
//			  sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
//			  byte[] aa = decoder.decodeBuffer(ThreeDESUtil3.encryptMode(names, desCode));
//			  System.out.println("1111@"+new String(ThreeDESUtil3.decryptMode(aa, desCode)));
			return ThreeDESUtil3.encryptMode(names, desCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getData(String filePath){
		try {
			byte[] desCode = TestResponse.getDEScode();
			File file = new File(filePath);  
			String re = txt2String(file);
			System.out.println("txt2String方法后的txt文件"+re);
			byte[] fileContent = re.getBytes("GBK");
			return ThreeDESUtil3.encryptMode(fileContent, desCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static String getSignature(String fileName){
		byte[] privateKey;
		try {
			privateKey = TestResponse.getMyPrivateKey();
			 sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			 String pri = encoder.encode(privateKey);
			 System.out.println("密钥MyprivateKey:"+pri);
			SignatureData sig = new SignatureData();
			return sig.sign(privateKey, fileName);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	
	public static void main(String[] args) throws IOException {
//		System.out.println(getFileName("testFileName"));
//		System.out.println(getSignature("testFileName"));
//		System.out.println(getData("D://1.txt"));
		testUpload("D://031801.txt","031801.txt");
//		String a = getData("D://2018-03-16 20-10-53.txt");
//		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
//		byte[] aa = decoder.decodeBuffer(a);
//		try {
//			byte[] desCode = TestResponse.getDEScode();
//			String ac = new String(ThreeDESUtil3.decryptMode(aa, desCode));
//			System.out.println(ac);
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		testDown("/20180317/086/8828.txt","8828.txt");
//		String signature = getSignature("12345");
//		System.out.println("@@@"+signature+"$$$$");
//		File f = new File("D://2018-03-17 16-51-09.txt");
//		String a = txt2String(f);
		
//		System.out.println(txt2String(f));
	}
	
	public static  String testUpload(String filePath,String fileName) throws UnsupportedEncodingException{
		String chanl_cust_no = "SD37000009021270501";
		String filename = URLEncoder.encode(getFileName(fileName), "UTF-8");
		String signature = URLEncoder.encode(getSignature(fileName), "UTF-8"); 
		String data = URLEncoder.encode(getData(filePath), "UTF-8");
		String dirflag = URLEncoder.encode(getFileName("0"), "UTF-8");;
		
		
		
//		String para = "chanl_cust_no='"+chanl_cust_no+"'&filename='"+filename+"'&signature='"+signature+"'&data='"+data+"'&dirflag='"+dirflag+"'";
		String para1 = "chanl_cust_no="+chanl_cust_no+"&filename="+filename+"&signature="+signature+"&data="+data+"&dirflag="+dirflag+"";
		String url = "http://124.127.94.46:8181/interlink/UploadFile";
		
		URL urls;
		try {
			urls = new URL(url);
			HttpURLConnection uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("POST");
			uc.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			uc.setRequestProperty("charset", "UTF-8");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setReadTimeout(10000);
			uc.setConnectTimeout(10000);
			OutputStream os = uc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
//			dos.write(para1.getBytes("utf-8"));
			os.write(para1.getBytes("utf-8"));
			os.flush();
//			dos.flush();
			os.close();
			InputStream inStream = uc.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);
			System.out.println(btImg.length+"返回信息1："+new String(btImg));
			byte[] len_byte = new byte[10];
			System.arraycopy(btImg, 0, len_byte, 0, 10);
			System.out.println("返回信息2："+new String(len_byte));
			
			byte[] sig = new byte[128];
			System.arraycopy(btImg, 10, sig, 0, 128);
			System.out.println(new String(sig)+"^^^^^^^^^^^");
			byte[] len_byte1 = new byte[btImg.length-10-128];
			System.arraycopy(btImg, 138, len_byte1, 0, btImg.length-10-128);
			byte[] desCode = TestResponse.getDEScode();
			System.out.println(len_byte1.length+"^^^^^^^^^^^^"+new String(len_byte1));
//			ThreeDESUtil3.encryptMode(len_byte1, desCode);
			System.out.println("返回文件名:"+new String(ThreeDESUtil3.decryptMode(len_byte1, desCode)));
			return new String(ThreeDESUtil3.decryptMode(len_byte1, desCode));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static  String testDown(String filePath,String fileName) throws UnsupportedEncodingException{
		String chanl_cust_no = "SD37000009021270501";
		String filename = URLEncoder.encode(getFileName(filePath));
		String signature = URLEncoder.encode(getSignature(fileName)); 
		
//		http://ip:port/interlink/DownloadFile
		
//		String para = "chanl_cust_no='"+chanl_cust_no+"'&filename='"+filename+"'&signature='"+signature+"'&data='"+data+"'&dirflag='"+dirflag+"'";
		String para1 = "chanl_cust_no="+chanl_cust_no+"&filename="+filename+"&signature="+signature;
		String url = "http://124.127.94.46:8181/interlink/DownloadFile";
		
		URL urls;
		try {
			urls = new URL(url);
			HttpURLConnection uc = (HttpURLConnection) urls.openConnection();
			uc.setRequestMethod("POST");
			uc.setRequestProperty("content-type",
					"application/x-www-form-urlencoded");
			uc.setRequestProperty("charset", "UTF-8");
			uc.setDoOutput(true);
			uc.setDoInput(true);
			uc.setReadTimeout(10000);
			uc.setConnectTimeout(10000);
			OutputStream os = uc.getOutputStream();
			DataOutputStream dos = new DataOutputStream(os);
//			dos.write(para1.getBytes("utf-8"));
			os.write(para1.getBytes("utf-8"));
			os.flush();
//			dos.flush();
			os.close();
			InputStream inStream = uc.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);
			System.out.println(btImg.length+"返回信息1："+new String(btImg));
			byte[] len_byte = new byte[10];
			System.arraycopy(btImg, 0, len_byte, 0, 10);
			System.out.println("返回信息2："+new String(len_byte));
			byte[] len_byte1 = new byte[btImg.length-10-128];
			System.arraycopy(btImg, 138, len_byte1, 0, btImg.length-10-128);
			byte[] desCode = TestResponse.getDEScode();
//			ThreeDESUtil3.encryptMode(len_byte1, desCode);
			return new String(ThreeDESUtil3.decryptMode(len_byte1, desCode));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static  byte[] readToString(String fileName) {  
        File file = new File(fileName);  
        Long filelength = file.length();  
        byte[] filecontent = new byte[filelength.intValue()];  
        try {  
            FileInputStream in = new FileInputStream(file);  
            in.read(filecontent);  
            in.close();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return filecontent;  
    }  
	
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

	public static String txt2String(File file){
        StringBuilder result = new StringBuilder();
        int a = 0;
        try{
        	BufferedReader br=new BufferedReader(new InputStreamReader(new FileInputStream(file.getPath()),"GBK"));  //构造一个BufferedReader类来读取文件
            String s = "";
            while((s = br.readLine())!=null){//使用readLine方法，一次读一行
            	if(a!=0){
            		result.append(System.lineSeparator()+s);
            	}
//                System.out.println(result+"￥");
                a = a+1;
            }
            br.close();    
        }catch(Exception e){
            e.printStackTrace();
        }
        return result.toString();
    }
	
	public static String getContent(File file) throws IOException{
		FileReader wj = new FileReader(file.getPath());
        //字节流方式打开
        //字节流每次按一个字节读取
        //FileInputStream wj = new FileInputStream("D:/java/kj/javanew/src/Index.java");
        
        //读取文件内容
        int aa;    //这里必须定义为整形，java规定io 里面的read()这个方法的返回值是整形的
        aa = wj.read();    //读取一个字符
        String content = "";
        while(aa!=(-1)){    //ASCII码是从0开始的数字，只有什么都没有才会返回-1
            content += (char)aa;
            aa = wj.read(); //继续读取一个字符
        }
        
        //输出文件内容
        System.out.println("文件内容为：");
        content = new String(content.getBytes());
        System.out.println(content);
        
        //关闭文件
        wj.close();
        return content;
	}
	
	public static String readFile(File file)  
	{
		 String temp = "";
		 System.out.print("编号    姓名    年龄    性别\n");
		    BufferedReader br = null;
		    try {
		     br = new BufferedReader(new FileReader(file));
		     temp = br.readLine();
		     while (temp != null) {
		      System.out.print(temp);
		      temp = br.readLine();
		     }
		     br.close();
		    } catch (FileNotFoundException e) {
		     e.printStackTrace();
		    } catch (IOException e) {
		     e.printStackTrace();
		    }
		return temp;
	}   
	
	
	
}
