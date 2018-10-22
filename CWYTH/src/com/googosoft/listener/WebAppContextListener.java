package com.googosoft.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.springframework.web.context.support.WebApplicationContextUtils;

import com.googosoft.util.Const;
/**
 * @description 
 * @author  作者:wxm 
 * @date 创建时间：2016年9月5日 下午1:30:22 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class WebAppContextListener implements ServletContextListener {

	public void contextDestroyed(ServletContextEvent event) {
	}

	public void contextInitialized(ServletContextEvent event) {
		Const.WEB_APP_CONTEXT = WebApplicationContextUtils.getWebApplicationContext(event.getServletContext());
	}

}
