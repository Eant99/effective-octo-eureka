package com.googosoft.sms;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/**
 * 依赖Apache HttpClient 3.1
 *
 */
public class SMSUtil {
		private static Logger logger = Logger.getLogger(SMSUtil.class);
		
	    /**
	     * 使用通用接口发短信 
	     * 单条发送
	     * @param company 短信信息头
	     * @param nrtext 发送短信内容
	     * @param mobile 接收验证码手机号
	     * @throws IOException 
	     * */
	    public static void single_send(String mobile,String code) throws IOException{
	    	String text="【"+SMSFlag.YZM_COMPANY+"】"+SMSFlag.YZM_TEXT+code;
	    	String  gg = SMSUtil.sendSms(SMSFlag.APIKEY, text, mobile);
	    	logger.debug("短信发送反馈："+gg);
	    }
	    public static void single_send(String company,String nrtext,String mobile,String code) throws IOException{
	    	String text="【"+company+"】"+nrtext+code;
	    	SMSUtil.sendSms(SMSFlag.APIKEY, text, mobile);
	    }
	    /**
	     * 使用模板接口发短信
	     * 单条发送 
	     * @param code 验证码
	     * @param mobile 接收验证码手机号
	     * @throws IOException 
	     * */
	    public static void tpl_single_send(String code,String mobile) throws IOException{
	    	
	    	String codeValue = URLEncoder.encode(code, SMSFlag.ENCODING);
	    	String tpl_value = "#code#=" + codeValue + "&#company#="+SMSFlag.COMPANY;
	    	SMSUtil.tplSendSms(SMSFlag.APIKEY, SMSFlag.TPL_ID, tpl_value, mobile);
	    }
	    
	    /**
	     * 取账户信息
	     *
	     * @return json格式字符串
	     * @throws java.io.IOException
	     */
	    public static String getUserInfo(String apikey) throws IOException, URISyntaxException {
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("apikey", apikey);
	        return post(SMSFlag.URI_GET_USER_INFO, params);
	    }

	    /**
	     * 通用接口发短信(推荐)
	     *
	     * @param apikey apikey
	     * @param text   　短信内容
	     * @param mobile 　接收短信手机号
	     * @return json格式字符串
	     * @throws IOException
	     */
	    public static String sendSms(String apikey, String text, String mobile) throws IOException {
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("apikey", apikey);
	        params.put("text", text);
	        params.put("mobile", mobile);
	        return post(SMSFlag.URI_SEND_SMS, params);
	    }

	    /**
	     * 通过模板号发送短信(推荐)
	     * @param apikey    apikey
	     * @param tpl_id    　模板id
	     * @param tpl_value 　模板变量值
	     * @param mobile    　接受的手机号
	     * @return json格式字符串
	     * @throws IOException
	     */
	    public static String tplSendSms(String apikey, long tpl_id, String tpl_value, String mobile) throws IOException {
	        Map<String, String> params = new HashMap<String, String>();
	        params.put("apikey", apikey);
	        params.put("tpl_id", String.valueOf(tpl_id));
	        params.put("tpl_value", tpl_value);
	        params.put("mobile", mobile);
	        return post(SMSFlag.URI_TPL_SEND_SMS, params);
	    }


	    /**
	     * 基于HttpClient 3.1的通用POST方法
	     *
	     * @param url       提交的URL
	     * @param paramsMap 提交<参数，值>Map
	     * @return 提交响应
	     */
	    public static String post(String url, Map<String, String> paramsMap) {
	        HttpClient client = new HttpClient();
	        try {
	            PostMethod method = new PostMethod(url);
	            if (paramsMap != null) {
	                NameValuePair[] namePairs = new NameValuePair[paramsMap.size()];
	                int i = 0;
	                for (Map.Entry<String, String> param : paramsMap.entrySet()) {
	                    NameValuePair pair = new NameValuePair(param.getKey(), param.getValue());
	                    namePairs[i++] = pair;
	                }
	                method.setRequestBody(namePairs);
	                HttpMethodParams param = method.getParams();
	                param.setContentCharset(SMSFlag.ENCODING);
	            }
	            client.executeMethod(method);
	            return method.getResponseBodyAsString();
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        return "";
	    }
	    
	    /**
	     * 生成6位数字的验证码
	     * */
	    public static String createYZM(){
	    	StringBuilder code=new StringBuilder();
			Random random=new Random();
			//6位验证码 
	    	for(int i=0;i<6;i++){
				code.append(String.valueOf(random.nextInt(10)));
			}
	    	return code.toString();
	    }
	}
	
