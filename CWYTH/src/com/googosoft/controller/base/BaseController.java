package com.googosoft.controller.base;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.util.Logger;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * @author  作者:wxm 
 * @date 创建时间：2016年9月5日 下午1:30:22 
 * @version 1.0 
 */
@Component("baseController")
public class BaseController {
	
	protected Logger logger = Logger.getLogger(this.getClass());
	
	/**
	 * 得到PageData
	 */
	public PageData getPageData(){
		return new PageData(this.getRequest());
	}
	/**
	 * 得到ModelAndView
	 */
	public ModelAndView getModelAndView(){
		return new ModelAndView();
	}
	/**
	 * 得到request对象
	 */
	public HttpServletRequest getRequest() {
		HttpServletRequest request = ((ServletRequestAttributes)RequestContextHolder.getRequestAttributes()).getRequest();
		return request;
	}
	/**
	 * 得到参数值
	 */
	public String getRequestParameterValue(String param) {
		return this.getRequest().getParameter(param)==null?"":this.getRequest().getParameter(param).toString();
	}
	/**
	 * 得到32位的uuid
	 * @return
	 */
	public String get32UUID(){
		return UuidUtil.get32UUID();
	}

	public static void logBefore(Logger logger, String interfaceName){
		logger.info("");
		logger.info("start");
		logger.info(interfaceName);
	}
	public static void logAfter(Logger logger){
		logger.info("end");
		logger.info("");
	}	
	
	/**
	 * 获取客户端IP地址
	 */
	public String getIp(HttpServletRequest request) {
		String ip = request.getHeader("X-Forwarded-For");
		if(Validate.noNull(ip) && !"unKnown".equalsIgnoreCase(ip)){
			//多次反向代理后会有多个ip值，第一个ip才是真实ip
			int index = ip.indexOf(",");
			if(index != -1){
				return ip.substring(0,index);
			}else{
				return ip;
			}
		}
		ip = request.getHeader("X-Real-IP");
		if(Validate.noNull(ip) && !"unKnown".equalsIgnoreCase(ip)){
			return ip;
		}
		ip = request.getRemoteAddr();
		return ip;
	}
}
