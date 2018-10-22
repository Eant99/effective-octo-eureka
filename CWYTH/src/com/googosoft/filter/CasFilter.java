package com.googosoft.filter;

import java.io.IOException;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.cas.CasToken;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AuthenticatingFilter;
import org.apache.shiro.web.util.WebUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CasFilter extends AuthenticatingFilter {

	 private static Logger logger = LoggerFactory.getLogger(CasFilter.class);
	  private static final String TICKET_PARAMETER = "ticket";
	  private String failureUrl;

	  protected AuthenticationToken createToken(ServletRequest request, ServletResponse response)
	    throws Exception
	  {
		  System.err.println("成功--------------createToken----------------");
	    HttpServletRequest httpRequest = (HttpServletRequest)request;
	    String ticket = httpRequest.getParameter("ticket");
	    return new CasToken(ticket);
	  }

	  protected boolean onAccessDenied(ServletRequest request, ServletResponse response)
	    throws Exception
	  {
		  System.err.println("成功--------------onAccessDenied----------------");
	    return executeLogin(request, response);
	  }

	  protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue)
	  {
		  System.err.println("成功--------------isAccessAllowed----------------");
	    return false;
	  }

	  protected boolean onLoginSuccess(AuthenticationToken token, Subject subject, ServletRequest request, ServletResponse response)
	    throws Exception
	  {
		  System.err.println("成功--------------onLoginSuccess----------------");
	    issueSuccessRedirect(request, response);
	    return false;
	  }

	  protected boolean onLoginFailure(AuthenticationToken token, AuthenticationException ae, ServletRequest request, ServletResponse response)
	  {
	    Subject subject = getSubject(request, response);
	    if ((subject.isAuthenticated()) || (subject.isRemembered())){
	    	System.err.println("成功------------------------------");
	    	try {
//	    		System.err.println(request.getParameter("style"));
	    		 HttpServletRequest httpRequest = (HttpServletRequest)request;
	    		 httpRequest.getSession().setAttribute("STYLE", request.getParameter("style"));
//	    		 subject.logout();
	    		issueSuccessRedirect(request, response);
	    	} catch (Exception e) {
	    		logger.error("Cannot redirect to the default success url", e);
	    	}
	    }
	    else {
	    	System.err.println("失败------------------------------");
	      try {
	    	  //*有问题  暂时这样改  2017-11-16 00:33:01wdm
//	    	  issueSuccessRedirect(request, response);
	        WebUtils.issueRedirect(request, response, this.failureUrl);
//	    	  subject.logout();
//	    		issueSuccessRedirect(request, response);
	      } catch (Exception e) {
	        logger.error("Cannot redirect to failure url : {}", this.failureUrl, e);
	      }
	    }
	    return false;
	  }

	  public void setFailureUrl(String failureUrl) {
	    this.failureUrl = failureUrl;
	  }

}
