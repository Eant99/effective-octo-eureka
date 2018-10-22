package com.googosoft.util;

import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;

/**
 * @description 
 * @author  作者:wxm 
 * @date 创建时间：2016年9月5日 上午9:28:53 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class Const {
	public static final String SESSION_VERIFYCODE = "VERIFYCODE";//验证码
	public static final String SESSION_USER = "USERIDBYLOGIN";//登录用户实体类
	public static final String SESSION_DWXX = "DWB";//登录用户单位信息实体类
	public static final String LOGIN = ResourceBundle.getBundle("sso-cas").getString("shiro.loginUrl");
//	public static final String LOGIN = "/login/login";				//登录地址
	public static final String SYSCONFIG = "config/Config.xml";//系统名称路径
	public static final String NO_INTERCEPTOR_PATH = ".*/((login)|(logout)|(index/offline)|(index/doUpdPwdByZhmm)|(imgFile)|(app)|(apk)|(pictures)|(static)|(phone)|(echo)|(file)).*";	//不对匹配该值的访问路径拦截（正则）
	
	public static ApplicationContext WEB_APP_CONTEXT = null; //该值会在web容器启动时由WebAppContextListener初始化
	
	public static final String SESSION_SECURITY_CODE = "sessionSecCode";
	public static final String SESSION_ROLE_RIGHTS = "sessionRoleRights";
	public static final String SESSION_menuList = "menuList";			//当前菜单
	public static final String SESSION_allmenuList = "allmenuList";		//全部菜单
	public static final String SESSION_QX = "QX";
	public static final String SESSION_userpds = "userpds";			
	public static final String SESSION_USERROL = "USERROL";				//用户对象
	public static final String SESSION_USERNAME = "USERNAME";			//用户名
	public static final String TRUE = "T";
	public static final String FALSE = "F";
	public static final String OFFLINE = "/index/offline";				//掉线
	public static final String SYSNAME = "admin/config/SYSNAME.txt";//系统名称路径
	public static final String PAGE	= "admin/config/PAGE.txt";			//分页条数配置路径
	public static final String EMAIL = "admin/config/EMAIL.txt";		//邮箱服务器配置路径
	public static final String SMS1 = "admin/config/SMS1.txt";			//短信账户配置路径1
	public static final String SMS2 = "admin/config/SMS2.txt";			//短信账户配置路径2
	public static final String FWATERM = "admin/config/FWATERM.txt";	//文字水印配置路径
	public static final String IWATERM = "admin/config/IWATERM.txt";	//图片水印配置路径
	public static final String WEIXIN	= "admin/config/WEIXIN.txt";	//微信配置路径
	public static final String FILEPATHIMG = "uploadFiles/uploadImgs/";	//图片上传路径
	public static final String FILEPATHFILE = "uploadFiles/file/";		//文件上传路径
	public static final String FILEPATHTWODIMENSIONCODE = "uploadFiles/twoDimensionCode/"; //二维码存放路径
	public static final String SALTKEY ="googosoft2017";//认证key
//	public static final String SJCJ ="http://jsgl.sdei.edu.cn/zjmsgzs/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://jsgl.sdei.edu.cn/zjmsgzs/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://jsgl.sdei.edu.cn/zjmsgzs";
	public static final String SJCJ ="http://192.168.10.174:8084/CWYTHX/phone/sczyff";//手机接口 上传路径
	public static final String SJCJ2 ="http://192.168.10.174:8084/CWYTHX/phone/sczyff";//手机接口 上传路径
	public static final String furl = "http://192.168.10.174:8084/CWYTHX";//通知公告
	public static final String TXSJCJ ="http://192.168.10.174:8084/CWYTHX/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://CWYTHX.sdaeu.edu.cn/CWYTHX/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://CWYTHX.sdaeu.edu.cn/CWYTHX/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://CWYTHX.sdaeu.edu.cn";
//	public static final String TXSJCJ ="http://CWYTHX.sdaeu.edu.cn/CWYTHX/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://172.16.240.24:8080/CWYTH";//通知公告
//	public static final String TXSJCJ ="http://172.16.240.24:8080/CWYTH/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://172.16.240.24:8080/CWYTH";//通知公告
//	public static final String TXSJCJ ="http://172.16.240.24:8080/CWYTH/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://172.16.240.24:8080/CWYTH";//通知公告
//	public static final String TXSJCJ ="http://172.16.240.24:8080/CWYTH/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://172.16.240.24:8080/CWYTH";//通知公告
//	public static final String TXSJCJ ="http://172.16.240.24:8080/CWYTH/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://172.16.240.24:8080/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://172.16.240.24:8080/CWYTH";//通知公告
//	public static final String TXSJCJ ="http://172.16.240.24:8080/CWYTH/phone/txsczyff";//手机接口 上传路径
//	public static final String SJCJ ="http://cwyth.sdaeu.edu.cn/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String SJCJ2 ="http://cwyth.sdaeu.edu.cn/CWYTH/phone/sczyff";//手机接口 上传路径
//	public static final String furl = "http://cwyth.sdaeu.edu.cn";
//	public static final String TXSJCJ ="http://cwyth.sdaeu.edu.cn/CWYTH/phone/txsczyff";//手机接口 上传路径

	
	/**
	 * APP Constants
	 */
	//app注册接口_请求协议参数)
	public static final String[] APP_REGISTERED_PARAM_ARRAY = new String[]{"countries","uname","passwd","title","full_name","company_name","countries_code","area_code","telephone","mobile"};
	public static final String[] APP_REGISTERED_VALUE_ARRAY = new String[]{"国籍","邮箱帐号","密码","称谓","名称","公司名称","国家编号","区号","电话","手机号"};
	
	//app根据用户名获取会员信息接口_请求协议中的参数
	public static final String[] APP_GETAPPUSER_PARAM_ARRAY = new String[]{"USERNAME"};
	public static final String[] APP_GETAPPUSER_VALUE_ARRAY = new String[]{"用户名"};
	

	

	
}
