package com.googosoft.filter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.cas.CasRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.Const;
import com.googosoft.util.SpringBeanFactoryUtils;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 用户授权信息域
 * 
 * @author coderhuang
 * 
 */
public class UserRealm extends CasRealm{
	@Autowired
    private HttpServletRequest request;
	
	protected final Map<String, SimpleAuthorizationInfo> roles = new ConcurrentHashMap<String, SimpleAuthorizationInfo>();
	
	/**
	 * 设置角色和权限信息
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {

		String account = (String) principals.getPrimaryPrincipal();
		SimpleAuthorizationInfo authorizationInfo = null;
		if (authorizationInfo == null) {
			System.err.println("测试。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。。1");
			authorizationInfo = new SimpleAuthorizationInfo();
			authorizationInfo.addStringPermission("赵丰平");
			authorizationInfo.addRole("111111");
			roles.put(account, authorizationInfo);
		}

		return authorizationInfo;
	}
	
	
	/**
	 * 1、CAS认证 ,验证用户身份
	 * 2、将用户基本信息设置到会话中
	 */
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) {
		AuthenticationInfo authc = super.doGetAuthenticationInfo(token);
		String account = (String) authc.getPrincipals().getPrimaryPrincipal();
		System.err.println("CASLoginName===:"+account);
		GX_SYS_RYB ryb = this.login(request, account);
		SecurityUtils.getSubject().getSession().setAttribute(Const.SESSION_USER, ryb);
		return authc;
	}
	/**
	 * 登录操作
	 * @param request
	 */
	private GX_SYS_RYB login(HttpServletRequest request,String username) {
		Subject currentUser = SecurityUtils.getSubject();  
		Session session = currentUser.getSession();
		Object obj = (GX_SYS_RYB)session.getAttribute(Const.SESSION_USER);
		if(obj == null){
			DBHelper db = (DBHelper) SpringBeanFactoryUtils.getBean("jdbcTemplate");
//			Map<String,Object> ryxx_map=this.getUserByNameAndPwdSSO(db, username);
			Map<String,Object> ryxx_map = this.getUserByNameAndPwdSSO(db,username);
			if(Validate.noNull(ryxx_map)){
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
				session.setAttribute("IP", this.getIp(request));//获取ip地址
				//获取组织机构名称
				String organizationname=this.getorganizationname(db);
				session.setAttribute("organizationname",organizationname);
				System.err.println("I am coming.............");
				return ryb;
			}
		}
		return null;
	}
	/**
	 * 获取客户端IP地址
	 */
	private String getIp(HttpServletRequest request) {
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
		if(ip.equals("0:0:0:0:0:0:0:1")){
			ip = "192.168.10.171";
		}
		return ip;
	}
	/**
	 * 获取组织机构
	 */
	public String getorganizationname(DBHelper db){
		String sql=" select xxmc from CW_XXXXB  where rownum<2 ";
		return db.queryForObject(sql, String.class);
	}
	private Map<String,Object> getUserByNameAndPwdSSO(DBHelper db,String username){
		StringBuffer sql=new StringBuffer();
		sql.append(" SELECT");
		sql.append(" R.GUID,RYBH,XM,TYPE,RYGH,");
		sql.append(" (CASE NVL(ROWNUMS,0) WHEN 0 THEN (SELECT NVL(ROWNUMS,'20') FROM ZC_SYS_XTB WHERE ROWNUM<=1) ELSE ROWNUMS END) ROWNUMS,");
		sql.append(" CSSCLASS,BMH,R.DWBH,MC,MM,R.LXDH");
		sql.append(" FROM (SELECT * FROM GX_SYS_RYB WHERE RYBH=?) R");
		sql.append(" LEFT JOIN GX_SYS_DWB D ON R.DWBH=D.DWBH");
		Object[] obj = {username};
		try {
			Map<String, Object> map = db.queryForMap(sql.toString(), obj);
			return map;
		} catch (Exception e) {
		}
		return null;
	}
	
	private Map<String,Object> getLoginIfo(DBHelper db,String userid){
		String sql="SELECT SCDLSJ,SCDLIP FROM PRO_LOGINIFO where USERID=?";
		try {
			Map<String, Object> map = db.queryForMap(sql, new Object[]{userid});
			return map;
		} catch (Exception e) {
		}
		return null;
	}
	private int LoginInfo(DBHelper db,String userid, String ip,String flag) {
		int i=0;
		if("C".equals(flag)){
			i=this.insertLoginInfo(db,UuidUtil.get32UUID(), userid, ip);
		}else if("U".equals(flag)){
			i=this.updateLoginInfo(db,userid, ip);
		}
		return i;
	}
	/**
	 * 修改登录信息
	 * @param rybh
	 * @param ip
	 * @return
	 */
	private int updateLoginInfo(DBHelper db,String userid, String ip) {
		String sql="UPDATE PRO_LOGINIFO SET scdlsj=TO_CHAR(SYSDATE,'yyyy-mm-dd hh:MM:ss'),scdlip=? where userid=?"; 
		return db.update(sql,new Object[]{ip, userid});
	}
	/**
	 * 首次登录无 插入登录信息
	 * @param id
	 * @param userid
	 * @param ip
	 * @return
	 */
	private int insertLoginInfo(DBHelper db,String id,String userid, String ip) {
		String sql="INSERT INTO PRO_LOGINIFO(ID,USERID,SCDLSJ,SCDLIP) VALUES (?,?,TO_CHAR(SYSDATE,'yyyy-mm-dd hh:MM:ss'),?)"; 
		return db.update(sql,new Object[]{id, userid,ip});
	}

}
