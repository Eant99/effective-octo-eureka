package com.googosoft.ccb;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

public class TestXMLUpload {

	
	//P1CLP1051 代发代扣直联交易申请
	public static String XMLHead = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Transaction><Transaction_Header><SYS_TX_CODE><![CDATA[P1CLP1051]]></SYS_TX_CODE><SYS_MSG_LEN><![CDATA[]]></SYS_MSG_LEN><SYS_REQ_TIME><![CDATA[20180313144501984]]></SYS_REQ_TIME><SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN><TXN_DT><![CDATA[20180313]]></TXN_DT><TXN_TM><![CDATA[144501]]></TXN_TM><TXN_STFF_ID><![CDATA[000001]]> </TXN_STFF_ID><MULTI_TENANCY_ID><![CDATA[CN000]]></MULTI_TENANCY_ID><LNG_ID><![CDATA[zh-cn]]></LNG_ID><REC_IN_PAGE><![CDATA[]]></REC_IN_PAGE><PAGE_JUMP><![CDATA[]]></PAGE_JUMP><STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID><CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO><IttParty_Jrnl_No><![CDATA[]]></IttParty_Jrnl_No><Txn_Itt_IP_Adr><![CDATA[128.128.128.128]]></Txn_Itt_IP_Adr></Transaction_Header>";

	public static String XMLFileReq = "<Transaction_Body><request><FILE_LIST_PACK><FILE_NUM><![CDATA[1]]></FILE_NUM><FILE_INFO><FILE_NAME><![CDATA[20180313/011/test0313]]></FILE_NAME><Msg_Smy><![CDATA[]]></Msg_Smy></FILE_INFO></FILE_LIST_PACK>";

	public static String XMLBody = "<Txn_SN><![CDATA[SD37000009021270501]]></Txn_SN><EBnk_SvAr_ID><![CDATA[SD37000009021270501]]></EBnk_SvAr_ID><Entrst_Prj_ID><![CDATA[23.122898]]></Entrst_Prj_ID><Prj_Use_ID><![CDATA[23.122898]]></Prj_Use_ID><EtrUnt_AccNo><![CDATA[23.122898]]></EtrUnt_AccNo><TDP_ID><![CDATA[23.122898]]></TDP_ID><TDP_Nm><![CDATA[23.122898]]></TDP_Nm><CntprtAcc><![CDATA[23.122898]]></CntprtAcc><Cntrprt_AccNm><![CDATA[23.122898]]></Cntrprt_AccNm><IwBk_BrNo><![CDATA[23.122898]]></IwBk_BrNo><IwBk_BkNm><![CDATA[23.122898]]></IwBk_BkNm><MltltAgrm_No><![CDATA[23.122898]]></MltltAgrm_No><CCY_ID><![CDATA[156]]></CCY_ID><SRP_TxnAmt><![CDATA[0.00]]></SRP_TxnAmt><SCSP_Smy_Dsc><![CDATA[23.122898]]></SCSP_Smy_Dsc><Rvw_Ind><![CDATA[23.122898]]></Rvw_Ind><TAmt><![CDATA[300.00]]></TAmt><TDnum><![CDATA[1]]></TDnum><VCHR_TP_CODE><![CDATA[23.122898]]></VCHR_TP_CODE><Lng_Vrsn><![CDATA[zh-cn]]></Lng_Vrsn></request></Transaction_Body></Transaction>";
	
	//P1OPME001 签到
	public static String XMLHeadQD = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Transaction><Transaction_Header><SYS_TX_CODE><![CDATA[P1OPME001]]></SYS_TX_CODE><SYS_MSG_LEN><![CDATA[0000001602]]></SYS_MSG_LEN><SYS_REQ_TIME><![CDATA[20180319074501984]]></SYS_REQ_TIME><SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN><TXN_DT><![CDATA[20180319]]></TXN_DT><TXN_TM><![CDATA[074501]]></TXN_TM><TXN_STFF_ID><![CDATA[333333]]></TXN_STFF_ID><MULTI_TENANCY_ID><![CDATA[CN000]]></MULTI_TENANCY_ID><LNG_ID><![CDATA[zh-cn]]></LNG_ID><REC_IN_PAGE><![CDATA[]]></REC_IN_PAGE><PAGE_JUMP><![CDATA[]]></PAGE_JUMP><STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID><CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO><IttParty_Jrnl_No><![CDATA[]]></IttParty_Jrnl_No><Txn_Itt_IP_Adr><![CDATA[60.208.80.195]]></Txn_Itt_IP_Adr></Transaction_Header>";

	public static String XMLFileReqQD = "<Transaction_Body><request>";

	public static String XMLBodyQD = "</request></Transaction_Body></Transaction>";
	
	
	public static String XMLHeadQu = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><Transaction><Transaction_Header><SYS_TX_CODE><![CDATA[P1CLP1055]]></SYS_TX_CODE><SYS_MSG_LEN><![CDATA[0000001602]]></SYS_MSG_LEN><SYS_REQ_TIME><![CDATA[20180319074501984]]></SYS_REQ_TIME><SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN><TXN_DT><![CDATA[20180319]]></TXN_DT><TXN_TM><![CDATA[074501]]></TXN_TM><TXN_STFF_ID><![CDATA[333333]]></TXN_STFF_ID><MULTI_TENANCY_ID><![CDATA[CN000]]></MULTI_TENANCY_ID><LNG_ID><![CDATA[zh-cn]]></LNG_ID><REC_IN_PAGE><![CDATA[]]></REC_IN_PAGE><PAGE_JUMP><![CDATA[]]></PAGE_JUMP><STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID><CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO><IttParty_Jrnl_No><![CDATA[]]></IttParty_Jrnl_No><Txn_Itt_IP_Adr><![CDATA[60.208.80.195]]></Txn_Itt_IP_Adr></Transaction_Header>";
	
	public static String XMLBodyQu = "<Transaction_Body><request><Txn_SN><![CDATA[15785612481101187612]]></Txn_SN><EBnk_SvAr_ID><![CDATA[SD37000009021270501]]></EBnk_SvAr_ID><EtrUnt_ID><![CDATA[]]></EtrUnt_ID><VchID><![CDATA[VS201803190000015202]]></VchID><Ret_Rslt_Cd><![CDATA[1]]></Ret_Rslt_Cd><Fmt_File_TpCd></Fmt_File_TpCd></request></Transaction_Body></Transaction>";
	
	public static String a = "<?xml version=\"1.0\" encoding=\"utf-8\" ?><Transaction><Transaction_Header><SYS_TX_CODE><![CDATA[P1CLP1051]]></SYS_TX_CODE><SYS_MSG_LEN><![CDATA[123]]></SYS_MSG_LEN><SYS_REQ_TIME><![CDATA[20180316102253686]]></SYS_REQ_TIME><SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN><TXN_DT><![CDATA[20180316]]></TXN_DT><TXN_TM><![CDATA[102253]]></TXN_TM><TXN_STFF_ID><![CDATA[333333]]></TXN_STFF_ID><MULTI_TENANCY_ID><![CDATA[CN000]]></MULTI_TENANCY_ID><LNG_ID><![CDATA[zh-cn]]></LNG_ID><REC_IN_PAGE><![CDATA[]]></REC_IN_PAGE><PAGE_JUMP><![CDATA[]]></PAGE_JUMP><STS_TRACE_ID><![CDATA[]]></STS_TRACE_ID><CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO><IttParty_Jrnl_No><![CDATA[]]></IttParty_Jrnl_No><Txn_Itt_IP_Adr><![CDATA[192.168.10.155]]></Txn_Itt_IP_Adr></Transaction_Header><Transaction_Body><request><FILE_LIST_PACK><FILE_NUM><![CDATA[3]]></FILE_NUM><FILE_INFO><FILE_NAME><![CDATA[20180318/433/031801.txt]]></FILE_NAME><Msg_Smy><![CDATA[d66568022aa55af40ef94c8913a6b874]]></Msg_Smy></FILE_INFO></FILE_LIST_PACK><Txn_SN><![CDATA[765890000000274371]]></Txn_SN><EBnk_SvAr_ID><![CDATA[SD37000009021270501]]></EBnk_SvAr_ID><Entrst_Prj_ID><![CDATA[370130066]]></Entrst_Prj_ID><Prj_Use_ID><![CDATA[zldf00001]]></Prj_Use_ID><EtrUnt_AccNo><![CDATA[37001618801050149199]]></EtrUnt_AccNo><TDP_ID><![CDATA[98229385]]></TDP_ID><TDP_NM><![CDATA[]]></TDP_NM><CntprtAcc><![CDATA[]]></CntprtAcc><Cntrprt_AccNm><![CDATA[]]></Cntrprt_AccNm><IwBk_Brno><![CDATA[]]></IwBk_Brno><IwBk_BkNm><![CDATA[]]></IwBk_BkNm><MltltAgrm_No><![CDATA[]]></MltltAgrm_No><CCY_ID><![CDATA[156]]></CCY_ID><Orig_File_Nm><![CDATA[031801.txt]]></Orig_File_Nm><SRP_TxnAmt><![CDATA[]]></SRP_TxnAmt><SCSP_Smy_Dsc><![CDATA[]]></SCSP_Smy_Dsc><Rvw_Ind><![CDATA[1]]></Rvw_Ind><TAmt>600</TAmt><TDnum>3</TDnum><VCHR_TP_CODE><![CDATA[1]]></VCHR_TP_CODE><Lng_Vrsn><![CDATA[1]]></Lng_Vrsn></request></Transaction_Body></Transaction>";
	
	public static String getMD5Three(String path) {
		try {
			byte[] buffer = new byte[8192];
			int len = 0;
			MessageDigest md = MessageDigest.getInstance("MD5");
			File f = new File(path);
			FileInputStream fis = new FileInputStream(f);
			while ((len = fis.read(buffer)) != -1) {
				md.update(buffer, 0, len);
			}
			fis.close();
			 BigInteger bigInt = new BigInteger(1, md.digest());
	            System.out.println("文件md5值：" + bigInt.toString(16));
//			sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
			return bigInt.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	/** 
     * 根据文件计算出文件的MD5 
     * @param file 
     * @return 
     */  
    public static String getFileMD5(String filePath) {  
    	File file = new File(filePath);
        if (!file.isFile()) {  
            return null;  
        }  
          
        MessageDigest digest = null;  
        FileInputStream in = null;  
        byte buffer[] = new byte[1024];  
        int len;  
        try {  
            digest = MessageDigest.getInstance("MD5");  
            in = new FileInputStream(file);  
            while ((len = in.read(buffer, 0, 1024)) != -1) {  
                digest.update(buffer, 0, len);  
            }  
            in.close();  
  
        } catch (NoSuchAlgorithmException e) {  
            e.printStackTrace();  
        } catch (FileNotFoundException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        BigInteger bigInt = new BigInteger(1, digest.digest());  
  
        System.out.println(bigInt.toString(16));
        
        return bigInt.toString(16);  
    }  
	
	public static byte[] signatureXML(String content){
		byte[] privateKey;
		byte[] signResult;
		try {
			privateKey = TestResponse.getMyPrivateKey();
			SignatureData sig = new SignatureData();
			signResult = sig.signByteReturn(privateKey, content);
			return signResult;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	} 
	
	public static String desXML(byte[] signResult){
		try {
			byte[] desCode = TestResponse.getDEScode();
			return ThreeDESUtil3.encryptMode(signResult, desCode);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "";
	}
	
	public static byte[] enXML(String a){
		return null;
	}
	
	public static  String testXMLUpload(String xmlContent) throws IOException{
		String chanl_cust_no = "SD37000009021270501";
		String content = xmlContent;
		byte[] signResult = signatureXML(xmlContent);
		byte[] contentByte = content.getBytes();
		String xml = URLEncoder.encode(desXML(contentByte), "UTF-8");
		
		String a = XMLHead+XMLFileReq+XMLBody;
		String xmlLength ;
		
//		xmlLength = a.replace("<?xml version=\"1.0\" encoding=\"UTF-8\"?>", "");
//		int xmllength = xmlLength.length();
//		sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();   
//		byte[] aa = decoder.decodeBuffer(a);
//		System.out.println(aa.length+"@"+a.length());
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();   
		String signature = URLEncoder.encode(encoder.encode(signResult), "UTF-8");
		
		
		String para = "chanl_cust_no="+chanl_cust_no+"&xml="+xml+"&signature="+signature;
		String url = "http://124.127.94.46:8181/interlink/interlink";
		
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
			dos.write(para.getBytes("utf-8"));
			dos.flush();
			os.close();
			InputStream inStream = uc.getInputStream();// 通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);
			System.out.println(btImg.length+"返回信息1："+new String(btImg));
			byte[] len_byte = new byte[10];
			System.arraycopy(btImg, 0, len_byte, 0, 10);
			byte[] len_byte1 = new byte[btImg.length-10-128];
			System.arraycopy(btImg, 138, len_byte1, 0, btImg.length-10-128);
			byte[] desCode = TestResponse.getDEScode();
			System.out.println("返回xml:"+new String(ThreeDESUtil3.decryptMode(len_byte1, desCode)));
			//返回xml
			String BackXml = new String( ThreeDESUtil3.decryptMode(len_byte1, desCode) );
			return BackXml;
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "111";
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
	
	public static void main(String[] args) throws IOException {
//		System.out.println(getFileMD5("D://test0313.txt"));
//		
//		String test = "测试信息123abc";
//		byte[] testa = test.getBytes();
//		String result = desXML(testa);
//		
//		BASE64Decoder de = new BASE64Decoder();
////		de.decodeBuffer(result);
//		
//		byte[] desCode;
//		try {
//			desCode = TestResponse.getDEScode();
//			System.out.println("****"+new String(ThreeDESUtil3.decryptMode(de.decodeBuffer(result), desCode)));
//		} catch (Exception e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		String c = XMLHeadQD+XMLFileReqQD+XMLBodyQD; 
		
		String d = XMLHeadQu + XMLBodyQu;
		String xmlss = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
				    +" <Transaction>"
					+"	<Transaction_Header>"
					+"		<SYS_TX_VRSN><![CDATA[01]]></SYS_TX_VRSN>"
					+"		<SYS_MSG_LEN><![CDATA[]]></SYS_MSG_LEN>"
					+"		<SYS_RECV_TIME><![CDATA[20180403081017505]]></SYS_RECV_TIME>"
					+"		<SYS_RESP_TIME><![CDATA[20180403081025849]]></SYS_RESP_TIME>"
					+"		<SYS_PKG_STS_TYPE><![CDATA[01]]></SYS_PKG_STS_TYPE>"
					+"		<SYS_TX_STATUS><![CDATA[00]]></SYS_TX_STATUS>"
					+"		<SYS_RESP_CODE><![CDATA[000000000000]]></SYS_RESP_CODE>"
					+"		<SYS_RESP_DESC_LEN><![CDATA[]]></SYS_RESP_DESC_LEN>"
					+"		<SYS_RESP_DESC><![CDATA[]]></SYS_RESP_DESC>"
					+"		<SYS_EVT_TRACE_ID><![CDATA[1010010011522757417836290]]></SYS_EVT_TRACE_ID>"
					+"		<SYS_SND_SERIAL_NO><![CDATA[]]></SYS_SND_SERIAL_NO>"
					+"		<SYS_TX_CODE><![CDATA[P1CLP1051]]></SYS_TX_CODE>"
					+"		<CHNL_CUST_NO><![CDATA[SD37000009021270501]]></CHNL_CUST_NO>"
					+"		<tran_response>"
					+"			<status><![CDATA[COMPLETE]]></status>"
					+"		</tran_response>"
					+"	</Transaction_Header>"
					+"	<Transaction_Body>"
					+"		<response>"
					+"			<Txn_SN><![CDATA[52997A7FA014BF1A69]]></Txn_SN>"
					+"			<EBnk_SvAr_ID><![CDATA[SD37000009021270501]]></EBnk_SvAr_ID>"
					+"			<VchID><![CDATA[VS201804030000016420]]></VchID>"
					+"			<PROCESS_FLAG><![CDATA[0]]></PROCESS_FLAG>"
					+"			<TAmt>111.00</TAmt>"
					+"			<TDnum><![CDATA[1]]></TDnum>"
					+"			<RDsc><![CDATA[濮旀墭椤圭洰缂栧彿锛?370130066,瀹炴椂鎵归噺浜ゆ槗涓璢]></RDsc>"
					+"			<Vchr_St><![CDATA[400]]></Vchr_St>"
					+"		</response>"
					+"	</Transaction_Body>"
					+"</Transaction>"
					+ "";
		
//		String a = getFileMD5("D:\\031801.txt");
//		String b = getMD5Three("D:f457c545a9ded88f18ecee47145a72c0\\111111.txt20180317/063/2018-03-17 10-53-34.txt");
		
		Map test = new HashMap();
		test = readStringXmlOut(xmlss);
		System.out.println(test);
//		String a = stringToAscii("序号（必填)|账号（必填）|户名（必填)|金额（必填）|跨行标志（必填，建行填“0”，他行填“1”）");
//		System.out.println(stringToAscii("序号（必填)|账号（必填）|户名（必填)|金额（必填）|跨行标志（必填，建行填“0”，他行填“1”）"));
//		System.out.println(a);
//		System.out.println(b);
	}
	
	public static String stringToAscii(String value)  
	{  
	    StringBuffer sbu = new StringBuffer();  
	    char[] chars = value.toCharArray();   
	    for (int i = 0; i < chars.length; i++) {  
	        if(i != chars.length - 1)  
	        {  
	            sbu.append((int)chars[i]).append(" ");  
	        }  
	        else {  
	            sbu.append((int)chars[i]);  
	        }  
	    }  
	    return sbu.toString();  
	}  
	
	public static Map readStringXmlOut(String xml) {
		String xml_RDsc = xml.replace("]></RDsc>","]]></RDsc>");
        Map map = new HashMap();
        Document doc = null;
        try {
            // 将字符串转为XML
            doc = DocumentHelper.parseText(xml_RDsc); 
            // 获取根节点
            Element rootElt = doc.getRootElement(); 
            // 拿到根节点的名称
            System.out.println("根节点：" + rootElt.getName()); 

            // 获取根节点下的子节点head
            Iterator iter = rootElt.elementIterator("Transaction_Header"); 
            // 遍历head节点
            while (iter.hasNext()) {

                Element recordEle = (Element) iter.next();
                // 拿到head节点下的子节点title值
                String SYS_RESP_TIME = recordEle.elementTextTrim("SYS_RESP_TIME"); 
                map.put("restime", SYS_RESP_TIME);
                // 获取子节点head下的子节点tran_response
                Iterator iters = recordEle.elementIterator("tran_response"); 
                // 遍历tran_response节点下的节点
                while (iters.hasNext()) {
                    Element itemEle = (Element) iters.next();
                    // 拿到head下的子节点script下的字节点username的值
                    String status = itemEle.elementTextTrim("status"); 
                    map.put("status", status);
                }
            }

            //获取根节点下的子节点body
            Iterator iterss = rootElt.elementIterator("Transaction_Body"); 
            // 遍历body节点
            while (iterss.hasNext()) {
                Element recordEless = (Element) iterss.next();
                // 拿到body节点下的子节点result值
                Iterator itersElIterator = recordEless.elementIterator("response"); 
                // 遍历Header节点下的Response节点
                while (itersElIterator.hasNext()) {
                    Element itemEle = (Element) itersElIterator.next();
                    // 拿到body下的子节点response下的字节点Txn_SN的值
                    String Txn_SN = itemEle.elementTextTrim("Txn_SN"); 
                    String VchID = itemEle.elementTextTrim("VchID");

                    map.put("txnsn", Txn_SN);
                    map.put("vchid", VchID);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
  
	

}
