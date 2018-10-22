package com.googosoft.interceptor.shiro;


import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.googosoft.interceptor.LoginHandlerInterceptor;
import com.googosoft.util.Logger;


/**
 * @description 
 * @author  作者:wxm 
 * @date 创建时间：2016年9月5日 下午1:30:22 
 * @version 1.0 
 * @parameter  
 * @return  
 */
public class ShiroRealm extends AuthorizingRealm {
	private Logger logger = Logger.getLogger(ShiroRealm.class);
	/*
	 * 登录信息和用户验证信息验证(non-Javadoc)
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

		 String username = (String)token.getPrincipal();  				//得到用户名 
	     String password = new String((char[])token.getCredentials()); 	//得到密码
		
	     if(null != username && null != password){
	    	 return new SimpleAuthenticationInfo(username, password, getName());//验证成功
	     }else{
	    	 return null;//验证失败	
	     }
	     
	}
	
	/*
	 * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用,负责在应用程序中决定用户的访问控制的方法(non-Javadoc)
	 * @see org.apache.shiro.realm.AuthorizingRealm#doGetAuthorizationInfo(org.apache.shiro.subject.PrincipalCollection)
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection pc) {
		return null;
	}

}
