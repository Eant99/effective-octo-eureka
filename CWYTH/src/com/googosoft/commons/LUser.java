package com.googosoft.commons;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.InvalidSessionException;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.Const;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;
/**
 * 当前登录用户管理类
 * @author cyd
 */
@Component("loginUser")
public class LUser {
	private static Logger logger = Logger.getLogger(LUser.class);
	/**
	 * 获取当前登录人的实体类
	 * @return
	 */
	private static GX_SYS_RYB getUser(){	
		try{
			//从shiro中获取当前登录用户
			Session session = getSession();
			if(session.getAttribute(Const.SESSION_USER) == null){
				return new GX_SYS_RYB();//为了防止手机端获取数据出错
			}
			else{
				return (GX_SYS_RYB)session.getAttribute(Const.SESSION_USER);
			}
		}catch (Exception e){
			logger.error("获取人员信息错误！");
			return new GX_SYS_RYB();//为了防止手机端获取数据出错
		}
	}
	/**
	 * 获取当前登录人的实体类
	 * @return
	 */
	private static GX_SYS_DWB getDWXX(){	
		try{
			Session session = getSession();
			if (session.getAttribute(Const.SESSION_DWXX) != null){
				return (GX_SYS_DWB)session.getAttribute(Const.SESSION_DWXX);
			}
		}catch (InvalidSessionException e){
			logger.error("获取人员信息错误！");
		}
		return null;
	}
	
	/**
	 * 获取当前登陆人的人员编号000000
	 * @return
	 */
	public static String getRybh(){
		return getUser().getRybh();
	}
	
	/**
	 * 获取当前登陆人的saas
	 * @return
	 */
	public static String getSaas(){
		return getUser().getSaas();
	}
	/**
	 * 获取当前登陆人的所在单位编号000001
	 * @return
	 */
	public static String getDwbh(){
		return getUser().getDwbh();
	}
	/**
	 * 获取当前登陆人的所在单位 (部门号)单位名称
	 * @return
	 */
	public static String getDwmc(){
		return "("+getDWXX().getBmh()+")"+getDWXX().getMc();
	}
	/**
	 * 获取当前登陆人的所在单位名称（不带部门号）
	 * @return
	 */
	public static String getOnlyDwmc(){
		return getDWXX().getMc();
	}
	/**
	 * 获取当前登陆人的姓名(000001)国子崔
	 * @return
	 */
	public static String getXm(){
		return "("+getUser().getRygh()+")"+getUser().getXm();
	}
	/**
	 * 获取当前登录人的人员工号 000001
	 * @return
	 */
	public static String getRygh(){
		return getUser().getRygh();
	}
	/**
	 * 获取当前登录人的人员姓名 国子崔
	 * @return
	 */
	public static String getRyxm(){
		return getUser().getXm();
	}
	/**
	 * 获取当前登录人的联系电话
	 * @return
	 */
	public static String getLxdh(){
		return getUser().getLxdh();
	}
	/**
	 * 获取当前登陆人的 guid
	 * @return
	 */
	public static String getGuid(){
		return getUser().getGuid();
	}
	/**
	 * 当前登录人的单位id
	 * @return
	 */
	public static String getDwid(){
		return getUser().getDwid();
	}
	/**
	 * 获取当前登录人行数
	 * @return
	 */
	public static String getRownums(){
		return getUser().getRownums().toString();
	}
	/**
	 * 获取当前登录人的自定义样式
	 * @return
	 */
	public static String getCssclass(){
		return getUser().getCssclass();
	}
	/*
	 * 
	 */
	public static String getType(){
		return getUser().getType();
	}
	/**
	 * 获取session
	 * @return
	 */
	public static Session getSession(){
		Subject subject = SecurityUtils.getSubject();
		Session session = subject.getSession(false);
		if(session == null){
			session = subject.getSession(true);
		}
		return session;
	}
}
