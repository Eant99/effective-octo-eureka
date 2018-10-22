package com.googosoft.interceptor;

import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.core.NamedThreadLocal;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.Const;
import com.googosoft.util.Jurisdiction;
import com.googosoft.util.Logger;
import com.googosoft.util.DateUtil;

/**
 * @description 
 * @author  作者:wxm 
 * @date 创建时间：2016年9月5日 上午9:28:53 
 * @version 1.0 
 * @parameter  
 * @return  
 * 
 */
public class LoginHandlerInterceptor extends HandlerInterceptorAdapter{
	
	private Logger logger = Logger.getLogger(LoginHandlerInterceptor.class);
	
	private static final ThreadLocal<Long> startTimeThreadLocal =
			new NamedThreadLocal<Long>("ThreadLocal StartTime");
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		String path = request.getServletPath();
		if (logger.isDebugEnabled()){
			long beginTime = System.currentTimeMillis();//1、开始时间  
	        startTimeThreadLocal.set(beginTime);		//线程绑定变量（该数据只有当前请求的线程可见）  
	        logger.debug("开始计时:"+new SimpleDateFormat("hh:mm:ss.SSS")
		        	.format(beginTime)+"  URI: "+ request.getRequestURI());
		}
		if(path.matches(Const.NO_INTERCEPTOR_PATH))//不对匹配该值的访问路径拦截（正则）
		{
			return true;
		}
		else
		{
			//shiro管理的session
			Subject currentUser = SecurityUtils.getSubject();
			GX_SYS_RYB user = (GX_SYS_RYB)currentUser.getSession().getAttribute(Const.SESSION_USER);
			if(user!=null)
			{
				path = path.substring(1, path.length());
				boolean b = Jurisdiction.hasJurisdiction(path);
				if(!b){
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return true;
			}
			else
			{
				/*//登陆过滤
				path = path.substring(1, path.length());
				boolean b = Jurisdiction.hasJurisdiction(path);
				if(!b){
					response.sendRedirect(request.getContextPath() + Const.LOGIN);
				}
				return true;
				*/
				//登陆过滤
				logger.debug("登录用户掉线了！");
				response.sendRedirect(request.getContextPath() + Const.OFFLINE);
				return false;
			}
		}
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, 
			ModelAndView modelAndView) throws Exception {
		if (modelAndView != null){
			logger.info("ViewName: " + modelAndView.getViewName());
		}
	}
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, 
			Object handler, Exception ex) throws Exception {		
		// 打印JVM信息。
		if (logger.isDebugEnabled()){
			long beginTime = startTimeThreadLocal.get();//得到线程绑定的局部变量（开始时间）  
			long endTime = System.currentTimeMillis(); 	//2、结束时间  
	        logger.debug("计时结束："+new SimpleDateFormat("hh:mm:ss.SSS").format(endTime)+" 耗时："+DateUtil.formatDateTime(endTime - beginTime)+"  URI: "+request.getRequestURI()); 
		}
		
	}
	
	
	
}
