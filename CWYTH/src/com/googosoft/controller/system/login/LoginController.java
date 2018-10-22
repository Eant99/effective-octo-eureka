package com.googosoft.controller.system.login;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.WebUtils;

import com.googosoft.commons.LicenseUtil;
import com.googosoft.commons.PropertiesUtil;
import com.googosoft.commons.SystemConfig;
import com.googosoft.constant.Constant;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.base.BaseController;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.service.system.index.DeskService;
import com.googosoft.service.system.menu.MenuService;
import com.googosoft.service.system.user.UserService;
import com.googosoft.service.systemset.cssz.DlxxService;
import com.googosoft.service.systemset.cssz.XtbService;
import com.googosoft.util.Const;
import com.googosoft.util.DateUtil;
import com.googosoft.util.MD5;
import com.googosoft.util.PageData;
import com.googosoft.util.UserAgentUtils;
import com.googosoft.util.Validate;
import com.googosoft.util.VerifyCode;
import com.googosoft.util.security.EncryptUtils;

@Controller
@RequestMapping(value="/login")
public class LoginController extends BaseController{
	
	@Resource(name="userService")
	private UserService userService;
	
	@Resource(name="dwbService")
	private DwbService dwbService;
	
	@Resource(name="systemConfig")
	private SystemConfig systemConfig;
	
	@Resource(name="verifyCode")
	private VerifyCode verifyCode;
	
	@Resource(name="xtbService")
	private XtbService xtbService;
	
	@Resource(name="dlxxService")
	private DlxxService dlxxService;
	
	@Resource(name="deskService")
	private DeskService deskService;
	
	@Resource(name="menuService")
	private MenuService menuService;
	
	/**
	 * 访问登录页面
	 * 默认进入的登录首页
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/login")
	public ModelAndView toLogin() throws UnsupportedEncodingException{
		ModelAndView mv = this.getModelAndView();
		if(!LicenseUtil.doLicenseCheck()){//检测是否在试用期内
			//mv.addObject("close",true);
			mv.addObject("licenseInfo","您好，软件已过试用期！");
		}
		//当数据库连接失败读出数据库配置文件信息
		if(systemConfig.DBState()!=""){
			mv.setViewName("system/login/modify_db");
			return mv;
		}
		else{
			Cookie usercookie=WebUtils.getCookie(getRequest(), "c_uname");
			if(usercookie!=null)
			{
				 mv.addObject("username",URLDecoder.decode(usercookie.getValue(), "utf-8"));//用户名
				 mv.addObject("remenberme","checked");//选择框选中
			}
			Map<?, ?> map = dlxxService.getXtcs();//获取系统初始信息			
			mv.addObject("sysname",map.get("XTMC"));//系统名称
			mv.addObject("dlfs", map.get("DLFS"));
			mv.addObject("email", map.get("EMAIL"));
			mv.addObject("lxdh", map.get("LXDH"));
			mv.addObject("jszc", map.get("JSZC"));
			mv.addObject("lxdz", map.get("LXDZ"));
			mv.addObject("xxmc", map.get("XXMC"));
			mv.addObject("bglist", map.get("bglist"));
			String dlfsmc = dlxxService.getDlfs();//获取登录方式
			mv.addObject("dlfsmc", dlfsmc);
			
			mv.addObject("websocket_uri", PropertiesUtil.getGlobalValueByKey("WebSocket_Uri"));
			mv.addObject("QRKey", this.get32UUID());
			mv.addObject("appDisplay", PropertiesUtil.getGlobalValueByKey("appDisplay"));
			mv.addObject("year", Constant.MR_YEAR());
			mv.addObject("message", systemConfig.ConfigState());//系统状态
			mv.setViewName("system/login/login");
			return mv; 
		} 
	}
	
	/**
	 * 获取验证码 
	 * @param request  
	 * @param response
	 */
	@RequestMapping(value="/getVerifyCode")
	public void getVerifyCode(HttpServletRequest request,HttpServletResponse response){
		BufferedImage image = verifyCode.getImage();//获取一次性验证码图片
		try {
			Subject subject = SecurityUtils.getSubject();
			Session session = subject.getSession();
			session.setAttribute(Const.SESSION_VERIFYCODE, verifyCode.getText());
			VerifyCode.output(image, response.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	/**
	 * 验证用户名是否存在
	 * 存在则返回，不存在则返回false
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/checkUsername",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object checkUserName(){
		PageData pd = this.getPageData();
		return  userService.findByUsername(pd.getString("username"),pd.getString("method"));
	} 
	
	
	/**
	 * 验证用户名跟密码
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping(value="/checkLogin")
	@ResponseBody
	public Object checkLogin(HttpServletResponse response) throws UnsupportedEncodingException{
		if(!LicenseUtil.doLicenseCheck()){//检测是否在试用期内
			return "{\"code\":\"7\",\"msg\":\"您好，软件已过试用期！\"}";
		}
		PageData pd = this.getPageData();
		String codes[] = pd.getString("code").split(",");
		String dlfs = pd.getString("dlfs");
		String errInfo = "";//错误信息
		String errCode = "";//错误代码
		//验证码
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		String sessionCode = (String) session.getAttribute(Const.SESSION_VERIFYCODE);//获取session中的验证码
		String code = codes[2];//验证码
		if(Validate.noNull(sessionCode) && sessionCode.equalsIgnoreCase(code))
		{
				String username = codes[0];//用户名
				String password = EncryptUtils.encryptToSHA(codes[1],Const.SALTKEY);//密码加密
				Map<?, ?> ryxx_map = userService.getUserByNameAndPwd(username,password,dlfs);
				String result=ryxx_map.get("result").toString();
				if("1".equals(result))
				{
					GX_SYS_RYB ryb = new GX_SYS_RYB();
					ryb.setRybh(ryxx_map.get("rybh")+"");//人员编号
					ryb.setXm(ryxx_map.get("xm")+"");//人员姓名
					ryb.setRygh(ryxx_map.get("rygh")+"");//人员工号
					ryb.setDwbh(ryxx_map.get("dwbh")+"");//人员单位
					ryb.setGuid(ryxx_map.get("guid")+"");
					ryb.setType(ryxx_map.get("type")+"");//人员类型Type
					ryb.setLxdh(Validate.isNullToDefaultString(ryxx_map.get("lxdh"),""));
					
					GX_SYS_DWB dwb=new GX_SYS_DWB();
					dwb.setBmh(ryxx_map.get("bmh")+"");//部门号
					dwb.setMc(ryxx_map.get("mc")+"");//部门名称
					ryb.setRownums(Integer.parseInt(ryxx_map.get("rownums").toString()));//分页行数
					session.setAttribute("rownum", ryxx_map.get("rownums"));//注意逐步替换上述方式
					session.setAttribute(Const.SESSION_USER, ryb);
					session.setAttribute(Const.SESSION_DWXX, dwb);
					session.setAttribute("IP", UserAgentUtils.getUserIp(this.getRequest()));//获取ip地址
					//获取组织机构名称
					String organizationname=userService.getorganizationname();
					session.setAttribute("organizationname",organizationname);
					//shiro加入身份验证
				    UsernamePasswordToken token = new UsernamePasswordToken(username, password);
				    boolean remember=Boolean.parseBoolean(pd.getString("rememberme"));//记住用户
				    if(remember)
				    {
				    	Cookie cookie = new Cookie("c_uname",URLEncoder.encode(username, "utf-8"));
						cookie.setMaxAge(60 * 60 * 24 * 14);//保存两周
						cookie.setPath("/");
						
						response.addCookie(cookie);
				    	token.setRememberMe(true);//记住我
				    }
				    session.removeAttribute(Const.SESSION_VERIFYCODE);
				    try 
				    {
				    	currentUser.login(token);
				    	errCode = "1";
						errInfo = "登录成功";//验证成功

						String rybh = ryb.getRybh();
						
						//插入登录日志
				    	userService.doAddLog(rybh,OplogFlag.LOGIN,"系统登录");
				    } 
				    catch (AuthenticationException e) 
				    {
				    	errCode = "5";
				    	errInfo = "抱歉，系统出现问题！";//shiro验证失败
				    }
				}
				else if("3".equals(result))
				{

					errCode = "3";
					errInfo = "密码错误，请重新输入!";//密码有误
					
				}
				else if("6".equals(result))
				{
					errCode = "6";
					errInfo = ryxx_map.get("RYBH")+"";//默认密码登录
				}
				else if("4".equals(result))
				{
					errCode = "4";//重复信息
					errInfo = "存在重复，请更换其他登录方式！";
				}
		}
		else if(null == code || "".equals(code))
		{
			errCode = "0";
			errInfo = "验证码不能为空！"; //验证码为空
		}
		else
		{
			errCode = "2";
			errInfo = "验证码错误";//验证码输入有误
	    }
		//0验证码为空1登录成功2验证码错误3用户名或密码错误4缺少参数5系统异常6默认密码
		return "{\"code\":\""+errCode+"\",\"msg\":\""+errInfo+"\"}";
	}
	

	/**
	 * 扫码登录获取人员信息
	 * @return
	 * @throws UnsupportedEncodingException 
	 * @throws Exception
	 */
	@RequestMapping(value="/LoginByRybh",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object LoginByRybh(HttpServletResponse response){
		if(!LicenseUtil.doLicenseCheck()){//检测是否在试用期内
			return "您好，软件已过试用期！";
		}
		
		//验证码
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		
		Map<?, ?> ryxx_map = userService.getUserByRybh(this.getPageData().getString("rybh"));
		
		if(ryxx_map.isEmpty()){
			return "登录失败！";
		}
		else{
			GX_SYS_RYB ryb = new GX_SYS_RYB();
			ryb.setRybh(ryxx_map.get("rybh")+"");//人员编号
			ryb.setXm(ryxx_map.get("xm")+"");//人员姓名
			ryb.setRygh(ryxx_map.get("rygh")+"");//人员工号
			ryb.setDwbh(ryxx_map.get("dwbh")+"");//人员单位
			ryb.setLxdh(Validate.isNullToDefaultString(ryxx_map.get("lxdh"),""));
			
			GX_SYS_DWB dwb=new GX_SYS_DWB();
			dwb.setBmh(ryxx_map.get("bmh")+"");//部门号
			dwb.setMc(ryxx_map.get("mc")+"");//部门名称
			
			ryb.setRownums(Integer.parseInt(ryxx_map.get("rownums").toString()));//分页行数
			session.setAttribute("rownum", ryxx_map.get("rownums"));//注意逐步替换上述方式
			
			session.setAttribute(Const.SESSION_USER, ryb);
			session.setAttribute(Const.SESSION_DWXX, dwb);
			session.setAttribute("IP", UserAgentUtils.getUserIp(this.getRequest()));//获取ip地址
			
			//shiro加入身份验证
		    UsernamePasswordToken token = new UsernamePasswordToken("", "");//用户名  密码
		    session.removeAttribute(Const.SESSION_VERIFYCODE);
		    try 
		    {
		    	currentUser.login(token);
				//插入登录日志
		    	userService.doAddLog(ryb.getRybh(),OplogFlag.LOGIN,"系统登录");
				return "true";
		    } 
		    catch (AuthenticationException e) 
		    {
		    	return "登录失败！";//shiro验证失败
		    }
		}
	}

	/**
	 * 跳转人员默认修改密码页面
	 * @param rybh
	 * @return
	 */
	@RequestMapping(value="/goUpdPwdPage")
	public ModelAndView goUpdPwdPage(String rybh){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("rybh", this.getPageData().getString("rybh"));
		mv.setViewName("system/login/xgmm");
		return mv;
	}
	/**
	 * 更新默认密码
	 * @param oldpassword 旧密码
	 * @param newpassword 新密码
	 * @param confirmpassword 确认密码
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/doUpdpwd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdpwd(String oldpassword,String newpassword,String confirmpassword,String rybh){
		if(newpassword.equals(confirmpassword))
		{
			int result = userService.doUpdpwd(oldpassword,newpassword,rybh);
			if(result == 1)
			{
				return "{\"success\":true,\"msg\":\"初始密码修改成功，请重新登录系统！\"}";
			}
			else if(result == 2){
				return "{\"success\":false,\"msg\":\"不允许使用初始密码登录，请重新输入新密码！\"}";
			}
			else
			{
				return "{\"success\":false,\"msg\":\"输入旧密码错误，请重新输入！\"}";
			}
		}
		else
		{
			return "{\"success\":false,\"msg\":\"密码不一致，请重新输入！\"}";
		}
	}
	/**
	 * 获取注意事项
	 * @return
	 */
	@RequestMapping(value="/getZysx")
	public ModelAndView getZysx(){
		ModelAndView mv = this.getModelAndView();
		String content = dlxxService.getZysx();
		mv.addObject("content",content);
		mv.setViewName("system/login/notice");
		return mv;
	}
	
	/**
	 * 访问修改数据库页面
	 * @return
	 */
	@RequestMapping(value="/goModifyDb")
	public ModelAndView goModifyDb(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("system/login/modify_db");
		return mv;
		                                
	}
	/**
	 * 数据库信息配置
	 * @return
	 */
	@RequestMapping(value="/doDatabSet") 
	@ResponseBody
	public Object doDatabSet(){
		PageData pd = this.getPageData();
		String dbtype = pd.getString("dbtype");//数据库类型
		String url = pd.getString("url");//服务器地址
		String port = pd.getString("port");//端口号
		String sid = pd.getString("sid");//实例名称
		
		String servurl ="jdbc:".concat(dbtype).concat(":thin:@").concat(url) +":"+port+":"+sid+"";
		String databack = "@"+url+"/"+sid+"";//数据库备份url设置
		String username = pd.getString("username"); 
		String password = pd.getString("password");
		if(Validate.noNull(servurl)&&Validate.noNull(username)&&Validate.noNull(password)){
			 systemConfig.writeData(servurl, username, password,databack);
			 String st = systemConfig.DBState(servurl, username, password);
			 if("0".equals(st)){
				 return "{\"success\":\"true\",\"msg\":\"数据库信息配置成功！\"}";
			 }else{
				 return "{\"success\":\"false\",\"msg\":\"数据库信息配置错误，请重新配置！\"}";
			 }
		}else{
			return "{\"success\":\"false\",\"msg\":\"参数为空！\"}";
		}
	}
	
	/**
	 * 单点登录中间跳转页面加验证
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/sglogin_toIndex")
	public ModelAndView sglogin_toIndex(){
		ModelAndView mv = this.getModelAndView();
		if(systemConfig.DBState()!=""){
			mv.setViewName("system/login/modify_db");
			return mv;
		}else{
			PageData pd = this.getPageData();
			String rygh = pd.getString("txt_user");
			Map<?, ?> ryxx_map = userService.getUserByName(rygh);
			String result=ryxx_map.get("result").toString();
			String flag = pd.getString("flag");//浏览器不兼容时，继续访问标志
			mv.addObject("flag",flag);
			Subject currentUser = SecurityUtils.getSubject();  
			Session session = currentUser.getSession();
			String rybh="",ryxm="",password="";
			if("1".equals(result)){
				rybh = ryxx_map.get("rybh")+"";
				ryxm = ryxx_map.get("xm")+"";
				password = ryxx_map.get("mm")+"";
				
				//初始化人员session信息
				GX_SYS_RYB ryb = new GX_SYS_RYB();
				ryb.setRybh(ryxx_map.get("rybh")+"");//人员编号
				ryb.setXm(ryxx_map.get("xm")+"");//人员姓名
				ryb.setRygh(ryxx_map.get("rygh")+"");//人员工号
				ryb.setDwbh(ryxx_map.get("dwbh")+"");//人员单位
				ryb.setLxdh(Validate.isNullToDefaultString(ryxx_map.get("lxdh"),""));
				GX_SYS_DWB dwb=new GX_SYS_DWB();
				dwb.setBmh(ryxx_map.get("bmh")+"");//部门号
				dwb.setMc(ryxx_map.get("mc")+"");//部门名称
				ryb.setRownums(Integer.parseInt(ryxx_map.get("rownums").toString()));//分页行数
				session.setAttribute("rownum", ryxx_map.get("rownums"));//注意逐步替换上述方式
				session.setAttribute(Const.SESSION_USER, ryb);
				session.setAttribute(Const.SESSION_DWXX, dwb);
				session.setAttribute("IP", UserAgentUtils.getUserIp(this.getRequest()));//获取ip地址
				
				//shiro加入身份验证
			    UsernamePasswordToken token = new UsernamePasswordToken(rygh, password);
			    try 
			    {
			    	currentUser.login(token);
					//插入登录日志
			    	userService.doAddLog(ryb.getRybh(),OplogFlag.LOGIN,"系统登录");
			    } 
			    catch (AuthenticationException e) 
			    {
			    	mv.addObject("errinfo", "系统异常，请稍候再试！");
					mv.setViewName("error/error");//shiro验证失败
			    }
				
				Map<?, ?> map = dlxxService.getXtcs();
				mv.addObject("sysname",map.get("XTMC"));//系统名称
				mv.addObject("schoolname", map.get("XXMC"));//学校名称
				List<?> shxx = deskService.getShxx(rybh);//审核信息
				mv.addObject("shsl", shxx.size());
				mv.addObject("shxx", shxx);
				String wdsl = deskService.getWdsl(rybh);//未读通知公告数量
				mv.addObject("wdsl", wdsl);
				List<?> wdxx = deskService.getWdxx(rybh);//未读通知公告信息
				mv.addObject("wdxx", wdxx);
				
				mv.addObject("rybh", rybh);
				mv.addObject("loginName", ryxm);
				
				String grtx = userService.getGrtx(rybh);//个人头像地址
				mv.addObject("grtx", Validate.isNullToDefaultString(grtx, ""));
				mv.addObject("websocket_uri", PropertiesUtil.getGlobalValueByKey("WebSocket_Uri"));
				//跳转到index页面
				mv.setViewName("system/index/index");
			}else if("2".equals(result)){
				mv.addObject("errinfo", "用户不存在！");
				mv.setViewName("error/error");
			}else if("3".equals(result)){
				mv.addObject("errinfo", "用户重复！");
				mv.setViewName("error/error");
			}
			return mv;
		}
	}
	/**
	 * 单点登录
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/ssologin_toIndex")
	public ModelAndView ssologin_toIndex(){
		ModelAndView mv = this.getModelAndView();
		System.err.println("=====================666666================");
		PageData pd = this.getPageData();
		if(systemConfig.DBState()!=""){
			mv.setViewName("system/login/modify_db");
			return mv;
		}else{
		 	String loginflag = "-1";
			String mkbh = pd.getString("mkbh");
			String uname= pd.getString("uname") == null?"" :pd.getString("uname").toString();
			String yz= pd.getString("yz") == null ?"":pd.getString("yz").toString();
			String sj = MD5.getPassword(uname+new SimpleDateFormat("yyyyMMddHH").format(new Date()));
			
			if(Validate.isNull(uname)||Validate.isNull(yz)||!yz.equals(sj)){
				loginflag = "-2";
			}else{
				Map<?, ?> ryxx_map = userService.getUserByName(uname);
				String result=ryxx_map.get("result").toString();
				String flag = pd.getString("flag");//浏览器不兼容时，继续访问标志
				mv.addObject("flag",flag);
				Subject currentUser = SecurityUtils.getSubject();  
				Session session = currentUser.getSession();
				String rybh="",ryxm="",password="";
				if("1".equals(result)){
					rybh = ryxx_map.get("rybh")+"";
					ryxm = ryxx_map.get("xm")+"";
					password = ryxx_map.get("mm")+"";
					
					//初始化人员session信息
					GX_SYS_RYB ryb = new GX_SYS_RYB();
					ryb.setRybh(ryxx_map.get("rybh")+"");//人员编号
					ryb.setXm(ryxx_map.get("xm")+"");//人员姓名
					ryb.setRygh(ryxx_map.get("rygh")+"");//人员工号
					ryb.setDwbh(ryxx_map.get("dwbh")+"");//人员单位
					ryb.setLxdh(Validate.isNullToDefaultString(ryxx_map.get("lxdh"),""));
					
					GX_SYS_DWB dwb=new GX_SYS_DWB();
					dwb.setBmh(ryxx_map.get("bmh")+"");//部门号
					dwb.setMc(ryxx_map.get("mc")+"");//部门名称
					ryb.setRownums(Integer.parseInt(ryxx_map.get("rownums").toString()));//分页行数
					session.setAttribute("rownum", ryxx_map.get("rownums"));//注意逐步替换上述方式
					session.setAttribute(Const.SESSION_USER, ryb);
					session.setAttribute(Const.SESSION_DWXX, dwb);
					session.setAttribute("IP", UserAgentUtils.getUserIp(this.getRequest()));//获取ip地址
					
					//shiro加入身份验证
				    UsernamePasswordToken token = new UsernamePasswordToken(uname, password);
				    try {
				    	currentUser.login(token);
						//插入登录日志
				    	userService.doAddLog(ryb.getRybh(),OplogFlag.LOGIN,"系统登录");
				    }catch (AuthenticationException e) {
				    	mv.addObject("errinfo", "系统异常，请稍候再试！");
						mv.setViewName("error/error");//shiro验证失败
				    }
				    //根据模块编号获取该模块的信息
				    Map mkMap = menuService.getMkUrl(mkbh);
				    mv.addObject("mkbh", mkbh);
					mv.addObject("mkmc", mkMap.get("MKMC"));
					mv.addObject("mkurl", ((mkMap.get("URL")+"").indexOf("?")>0)?(mkMap.get("URL")+"&mkbh="+mkbh):(mkMap.get("URL")+"?mkbh="+mkbh));
					Map<?, ?> map = dlxxService.getXtcs();
					mv.addObject("sysname",map.get("XTMC"));//系统名称
					mv.addObject("schoolname", map.get("XXMC"));//学校名称
					List<?> shxx = deskService.getShxx(rybh);//审核信息
					mv.addObject("shsl", shxx.size());
					mv.addObject("shxx", shxx);
					String wdsl = deskService.getWdsl(rybh);//未读通知公告数量
					mv.addObject("wdsl", wdsl);
					List<?> wdxx =deskService.getWdxx(rybh);//未读通知公告信息
					mv.addObject("wdxx", wdxx);
					mv.addObject("rybh", rybh);
					mv.addObject("loginName", ryxm);
					String grtx = userService.getGrtx(rybh);//个人头像地址
					mv.addObject("grtx", Validate.isNullToDefaultString(grtx, ""));
					mv.addObject("websocket_uri", PropertiesUtil.getGlobalValueByKey("WebSocket_Uri"));
					//跳转到index页面
					mv.setViewName("system/index/index");
				}else if("2".equals(result)){
					mv.addObject("errinfo", "用户不存在！");
					mv.setViewName("error/error");
				}else if("3".equals(result)){
					mv.addObject("errinfo", "用户重复！");
					mv.setViewName("error/error");
				}
			}
			return mv;
		}
	}
	
	/**
	 * 找回密码，验证答案是否正确
	 * 存在则返回，不存在则返回false
	 */
	@RequestMapping(value="/checkMmzh",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object checkMmzh(){
		PageData pd = this.getPageData();
		return  userService.checkMmzh(pd.getString("rygh"),pd.getString("mmda"),pd.getString("mmwt"));
	} 
}
