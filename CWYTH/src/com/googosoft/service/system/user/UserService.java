package com.googosoft.service.system.user;


import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.system.user.UserDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.pojo.systemset.aqgl.ZC_SYS_OPLOG;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.UserAgentUtils;
/**
 * 用户信息service
 * @author cyd
 *
 */
@Service("userService")
public class UserService extends BaseService{
	@Autowired  
	private HttpServletRequest request;
	
	@Resource(name="userDao")
	public UserDao userDao;
	
	/**
	 * 用户输入用户名后的查询用户信息事件
	 * @param pd
	 * @return
	 * @throws Exception
	 */
	public String findByUsername(String username,String method){
		//判断是否为空
		String counts = userDao.findByUsername(username,method);
		//0用户名不存在1人员姓名2登录名重复
		if("0".equals(counts)){
			return "{'code':'0','msg':'用户名不存在！'}"; 
		}else if("1".equals(counts)){
			String xm = userDao.getUsername(username,method);
			return "{'code':'1','msg':'"+xm+"'}";
		}else{
			return "{'code':'2','msg':'用户名重复，请更换登录方式！'}";
		}
	}

	/**
	 * 登录判断
	 * @param pd
	 * @return
	 */
	public Map<String,Object> getUserByNameAndPwd(String username,String password,String method){
		return userDao.getUserByNameAndPwd(username, password,method);
	}
 
	public String getorganizationname(){
		return  userDao.getorganizationname();
		
	}
	/**
	 * 扫码登录获取人员信息
	 * @param pd
	 * @return
	 */
	public Map<String,Object> getUserByRybh(String rybh){
		return userDao.getUserByRybh(rybh);
	}
	
	/**
	 * 单点登录判断
	 * @param pd
	 * @return
	 */
	public Map<String,Object> getUserByName(String username){
		return userDao.getUserByName(username);
	}
	
	/**
	 * 获取此人头像
	 * @return
	 */
	public String getGrtx(String rybh) {
		return  userDao.getGrtx(rybh);
	}
	/**
	 * 更新默认密码
	 * @param oldpassword
	 * @param newpassword
	 * @param rybh
	 * @return
	 */
	public int doUpdpwd(String oldpassword, String newpassword,String rybh) {
		int i = userDao.doUpdpwd(oldpassword,newpassword,rybh);
		if(i == 1)
		{
			doAddLog(rybh,OplogFlag.UPD,"修改默认密码");
		}
		return i;
	}
	/**
	 * 根据姓名获取人员编号
	 * @param inputValue
	 * @return
	 */
	public String GetKeyId(String inputValue)
	{	
	      if (inputValue.indexOf("(") < 0)
	      {
	          return "";
	      }
	      Map<String,Object> map=userDao.getObjectByRygh(inputValue.substring(1, inputValue.indexOf(")")));
	      return map.get("ryxm").toString();
	 }
	/**
	 * 首页修改个人密码
	 * @param passwordo
	 * @param passwordn
	 * @param passwordnn
	 * @return
	 */
	public int doUpdPwd(String passwordo,String passwordn,String rybh) 
	{
		boolean flag = userDao.checkPwd(passwordo,rybh);
		if(flag)
		{
			flag = userDao.checkCsmm(passwordn,rybh);
			if(flag){
				int i = userDao.doUpdPwd(passwordn,rybh);
				if(i == 1)
				{
					doAddOplog(OplogFlag.UPD,"修改本人密码",rybh);
				}
				return i;
			}
			else{
				return 2;
			}
		}else {
			return 0;
		}
	}
	
	/**
	 * 修改密码（登录页面，找回密码）
	 */
	public int doUpdPwdByZhmm(String rygh,String xmm){
		int i = userDao.doUpdPwdByZhmm(rygh,xmm);
		if(i == 1){
			doAddOplog(OplogFlag.UPD,"修改"+rygh+"密码",rygh);
		}
		return i;
	}
	
	/**
	 * 获取个人信息实体
	 * @param rownums
	 * @return
	 */
	public Map<String,Object> getObjectById(String rybh) {
		return userDao.getObjectById(rybh);
	}
	

	/**
	 * 设置选项
	 * @param rownums
	 * @return
	 */
	public boolean doSaveSzxx(String rownums, String lxdh, String qq, String mail,String rybh,String mmwt,String mmda) {
		boolean  result=userDao.doSaveSzxx(rownums,lxdh,qq,mail,rybh,mmwt,mmda);
		if(result){
			doAddOplog(OplogFlag.UPD,"修改个人信息",rybh);
		}
		return result;
	}
	/**
	 * 修改个人信息
	 */
	public int doSavePersonInfo(GX_SYS_RYB ryb){
		int result = userDao.doSavePersonInfo(ryb);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"修改个人信息",ryb.getRybh());
		}
		return result;
	}
	
	/**
	 * 日志操作管理
	 * @param czr：操作人
	 * @param czlx：操作类型
	 * @param cznr：操作内容
	 * @param djbh：单据编号
	 */
	public void doAddLog(String czr,String czlx,String cznr)
	{
		ZC_SYS_OPLOG oplog = new ZC_SYS_OPLOG();
		oplog.setCzlx(czlx);
		oplog.setCznr(cznr);
		oplog.setRybh(czr);//人员编号
		String ip = UserAgentUtils.getUserIp(request);
		oplog.setCzjq(ip);                   //操作机器	
		oplog.setSyd((UserAgentUtils.isComputer(request)==true?"1":"0"));
		oplog.setLlq(UserAgentUtils.getBrowser(request)+"");
        oplogDao.doAdd(oplog); 
	}
	/**
	 * 找回密码，验证答案是否正确
	 * 存在则返回，不存在则返回false
	 */
	public String checkMmzh(String rygh,String mmda,String mmwt) {
		//判断是否为空
		boolean boo = userDao.checkMmzh(rygh,mmda,mmwt);
		if(boo){
			return "{\"success\":\"true\",\"msg\":\"验证成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"验证失败，密保问题或答案错误！\"}";
		}
	}
}
