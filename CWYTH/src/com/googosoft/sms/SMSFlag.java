package com.googosoft.sms;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SMSFlag {
	
	static {
		Properties prop = new Properties();
		InputStream is = SMSFlag.class.getClassLoader().getResourceAsStream("global.properties");
		try {
			prop.load(is);
		} catch (IOException e) {
			e.printStackTrace();
		}
		APIKEY = (String) prop.getProperty("apikey");
		YZM_COMPANY = (String) prop.getProperty("yzm_company");
		YZM_TEXT = (String) prop.getProperty("yzm_text");
		TPL_ID = Long.parseLong(prop.getProperty("tpl_id"));
		COMPANY = (String) prop.getProperty("company");
	}
	
	//查账户信息的http地址
    public static String URI_GET_USER_INFO = "http://yunpian.com/v1/user/get.json";
    //通用发送接口的http地址
    public static String URI_SEND_SMS = "http://yunpian.com/v1/sms/send.json";
    //模板发送接口的http地址
    public static String URI_TPL_SEND_SMS = "http://yunpian.com/v1/sms/tpl_send.json";
    //状态报告获取
    public static String URI_PULL_STATUS= "http://yunpian.com/v1/sms/pull_status.json";
    //编码格式。发送编码格式统一用UTF-8
    public static String ENCODING = "UTF-8";
    
    //发送短信的apikey，注册后获得:云片网（https://www.yunpian.com/product.html）
    public static String APIKEY;
    
    /**************** 使用通用接口发短信(推荐) *****************/
    //设置您要发送的内容 (内容必须和某个模板匹配。以下例子匹配的是系统提供的1号模板）
    //public static String YZM_TEXT = "【云片网】您的验证码是";
    
    //验证码提示信息
    public static String YZM_COMPANY;
    public static String YZM_TEXT;
    
    //新密码短信提示
    //public static String NEWMM_COMPANY = "云片网";
    //public static String NEWMM_TEXT = "新密码是";
    
    /**************** 使用模板接口发短信(不推荐) *****************/
    //设置模板ID，如使用1号模板:【#company#】您的验证码是#code#
    public static long TPL_ID;
    //设置对应的模板变量值
    //如果变量名或者变量值中带有#&=%中的任意一个特殊符号，需要先分别进行urlencode编码
    //如code值是#1234#,需作如下编码转换
    public static String COMPANY;
    
}
