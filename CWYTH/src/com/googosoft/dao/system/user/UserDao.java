package com.googosoft.dao.system.user;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.Const;
import com.googosoft.util.Validate;
import com.googosoft.util.security.EncryptUtils;
@Repository("userDao")
public class UserDao extends BaseDao{
	
	/**
	 * 查询用户是否存在
	 * @param username 用户名
	 * @return
	 * @throws Exception
	 */
	public String findByUsername(String username,String method){
		String sql="SELECT COUNT(A.XM) FROM %SRYB A WHERE RYZT='1' AND (1=2  ";
		sql=String.format(sql, SystemSet.gxBz);
		if(method!=null)
		{
			String[] dlfss = method.split(",",-1);
			if(dlfss.length>0)
			{
				for(int i=0;i<dlfss.length;i++)
				{
					if(dlfss[i].toString().equals("1"))
						sql+=" OR A.XM='"+username+"'";
					else if(dlfss[i].toString().equals("2"))
						sql+=" OR A.RYGH='"+username+"')";
					else if(dlfss[i].toString().equals("3"))
						sql+=" OR A.SFZH='"+username+"'";
					else if(dlfss[i].toString().equals("4"))
						sql+=" OR A.LXDH='"+username+"'";
				}
			}
		}		
		String counts = db.queryForSingleValue(sql);
		return counts;
	}
	/**
	 * 查询用户名字
	 * @param username
	 * @return
	 */
	public String getUsername(String username,String loginmethod){
		String sql="SELECT A.XM FROM %SRYB A WHERE 1=2 ";
		sql=String.format(sql, SystemSet.gxBz);
		if(loginmethod!=null)
		{
			String[] dlfss = loginmethod.split(",",-1);
			if(dlfss.length>0)
			{
				for(int i=0;i<dlfss.length;i++)
				{
					if(dlfss[i].toString().equals("1"))
						sql+=" OR A.XM='"+username+"'";
					else if(dlfss[i].toString().equals("2"))
						sql+=" OR A.RYGH='"+username+"'";
					else if(dlfss[i].toString().equals("3"))
						sql+=" OR A.SFZH='"+username+"'";
					else if(dlfss[i].toString().equals("4"))
						sql+=" OR A.LXDH='"+username+"'";
				}
			}
		}
		return db.queryForSingleValue(sql);
	}
	/**
	 * 登录判断
	 * @param username 用户名
	 * @param password 密码
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserByNameAndPwd(String username,String password,String method){
		String dlsql="1=2";
		String[] dlfss = method.split(",",-1);
		if(dlfss.length>0)
		{
			for(int i=0;i<dlfss.length;i++)
			{
				if(dlfss[i].toString().equals("1"))
					dlsql+=" OR XM='"+username+"'";
				else if(dlfss[i].toString().equals("2"))
					dlsql+=" OR RYGH='"+username+"'";
				else if(dlfss[i].toString().equals("3"))
					dlsql+=" OR SFZH='"+username+"'";
				else if(dlfss[i].toString().equals("4"))
					dlsql+=" OR LXDH='"+username+"'";
			}
		}
		Map<String, Object> rymap = new HashMap<String, Object>();
		String sql01="SELECT MM,RYBH,GUID,TYPE FROM %SRYB WHERE "+dlsql;
		sql01=String.format(sql01, SystemSet.gxBz);
		Map<?, ?> emptyMm = db.queryForMap(sql01);
		
		String sql ="SELECT CSMM FROM %SLOGIN WHERE ROWNUM <= 1";
		sql=String.format(sql, SystemSet.sysBz);
		String csmm = db.queryForSingleValue(sql);
		
		if(Validate.noNull(emptyMm.get("MM"))){
				String sql02="SELECT COUNT(*) from %SRYB WHERE MM=? AND ("+dlsql+") ";
				sql02=String.format(sql02, SystemSet.gxBz);
				int count = db.queryForObject(sql02,
						new Object[]{password},Integer.class);
				if(count==1)
				{
					String sql03="select r.guid,rybh,xm,type,rygh,(case nvl(rownums,0) when 0 then (select nvl(rownums,'20') from %sxtb where rownum<=1) else rownums end) rownums,cssclass,bmh,r.dwbh,mc,mm,r.lxdh from (select * from %sryb where mm=? and ("+dlsql+")) r left join %sdwb d on r.dwbh=d.dwbh";
					sql03=String.format(sql03, SystemSet.sysBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz);
					rymap = db.queryForMap(sql03,
							new Object[]{password});
					
					if(rymap.get("MM").equals(EncryptUtils.encryptToSHA(csmm,Const.SALTKEY))){
						rymap.put("result","6");//默认密码登录
						rymap.put("RYBH",rymap.get("RYBH").toString());
						return rymap;
					}
					rymap.put("result","1");//登录成功
					db.insertRydwqx(rymap.get("RYBH").toString());//初始化人员单位权限(ZC_SYS_RYDWQXB)
					return rymap;
				}
				else if(count>1)
				{
					rymap.put("result","4");//重复信息
				}
				else
				{
					rymap.put("result","3");//密码错误
				}
		}
		else{
			if(password.equals(EncryptUtils.encryptToSHA(csmm,Const.SALTKEY)))
			{
				rymap.put("result","6");//默认密码登录
				rymap.put("RYBH",emptyMm.get("RYBH").toString());
				return rymap;
			}
			else
			{
				rymap.put("result","3");//密码错误
			}
		}
		return rymap;
	}
	/**
	 * 扫码登录获取人员信息
	 * @param rybh 人员编号
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserByRybh(String rybh){
		String sql = "select rybh,xm,rygh,(case nvl(rownums,0) when 0 then (select nvl(rownums,'20') from %sxtb where rownum <= 1) else rownums end) rownums,cssclass,bmh,r.dwbh,mc,mm,r.lxdh from (select * from %sryb where rybh = ?) r left join %sdwb d on r.dwbh=d.dwbh";
		sql = String.format(sql, SystemSet.sysBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz);
		Map<String, Object> rymap = db.queryForMap(sql, new Object[]{rybh});

		db.insertRydwqx(rybh);//初始化人员单位权限(ZC_SYS_RYDWQXB)
		return rymap;
	}
	
	/**
	 * 单点登录判断
	 * @param username 用户名
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> getUserByName(String username){

		Map<String, Object> rymap = new HashMap<String, Object>();

		String sql="SELECT COUNT(*) from %SRYB  WHERE RYGH=? ";
		sql=String.format(sql, SystemSet.gxBz);
		int count = db.queryForObject(sql,
				new Object[]{username},Integer.class);
		if(count==1){
			String sql01="SELECT RYBH,XM,RYGH,(CASE NVL(ROWNUMS,0) WHEN 0 THEN (SELECT NVL(ROWNUMS,'20') FROM %SXTB WHERE ROWNUM<=1) ELSE ROWNUMS END) ROWNUMS,CSSCLASS,BMH,r.DWBH,MC,MM,r.lxdh FROM %SRYB r,%SDWB d WHERE r.DWBH=d.DWBH AND RYGH=?";
			sql01=String.format(sql01, SystemSet.sysBz, SystemSet.gxBz, SystemSet.gxBz);
			rymap = db.queryForMap(sql01, new Object[]{username});
			
			rymap.put("result","1");//登录成功
			db.insertRydwqx(rymap.get("RYBH").toString());//初始化人员单位权限(ZC_SYS_RYDWQXB)
			return rymap;
		}
		else if(count==0){
			rymap.put("result","2");//用户不存在
		}
		else if(count>1){
			rymap.put("result","3");//用户重复
		}
		return rymap;
	}
	
	/**
	 * 获取此人头像
	 * @param rybh
	 * @return
	 */
	public String getGrtx(String rybh) 
	{
		String sql = "select url from %SRYB where rybh=? ";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	
	/**
	 * 获取组织机构
	 */
	public String getorganizationname(){
		String sql=" select xxmc from CW_XXXXB  where rownum<2 ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 更新默认密码
	 * @param oldpassword 旧密码
	 * @param newpassword 新密码
	 * @param rybh 人员编号
	 * @return
	 */
	public int doUpdpwd(String oldpassword, String newpassword,String rybh) 
	{
		//判断旧密码是否是初始密码
		String sql = "SELECT COUNT(*) FROM %SLOGIN WHERE CSMM=? AND ROWNUM < 2";
		sql=String.format(sql, SystemSet.sysBz);
		int count = db.queryForObject(sql,new Object[]{oldpassword}, Integer.class);
		if(count==1){
			//判断新密码是否是初始密码
			sql = "SELECT COUNT(*) FROM %SLOGIN WHERE CSMM=? AND ROWNUM < 2";
			sql=String.format(sql, SystemSet.sysBz);
			count = db.queryForObject(sql,new Object[]{newpassword}, Integer.class);
			if(count == 0){
				sql = "update %Sryb set mm=? where rybh=?";
				sql=String.format(sql, SystemSet.gxBz);
				return db.update(sql,new Object[]{EncryptUtils.encryptToSHA(newpassword,Const.SALTKEY),rybh});
			}
			else{
				return 2;
			}
		}
		return 0;
	}
	/**
	 * 获取单个人员详细信息
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getObjectById(String rybh){
		String sql = "select A.rybh,(select '('||d.bmh||')'||d.mc from %Sdwb d where d.dwbh=a.dwbh) as dwmc,A.dwbh,A.xm,A.xb,A.csrq,A.whcd,"
				+ "A.byrq,A.sxzy,A.gzrq,A.sysgl,A.zyzc,A.zygz,A.drrq,A.txrq,A.bz,"
				+ "A.ryzt,A.zzbz,A.pxxh,A.sfzh,A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm from %SRYB A"
				+ " where rybh=?";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[]{rybh});
	}
	
	/**
	 * 获取单个人员详细信息
	 * @param rygh
	 * @return
	 */
	public Map<String, Object> getObjectByRygh(String rygh){
		String sql = "select A.rybh,(select '('||d.bmh||')'||d.mc from %Sdwb d where d.dwbh=a.dwbh) as dwmc,('('||a.rygh||')'||a.xm) as ryxm,A.dwbh,A.xm,A.xb,A.csrq,A.whcd,"
				+ "A.byrq,A.sxzy,A.gzrq,A.sysgl,A.zyzc,A.zygz,A.drrq,A.txrq,A.bz,"
				+ "A.ryzt,A.zzbz,A.pxxh,A.sfzh,A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm from %SRYB A"
				+ " where rybh=?";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[]{rygh});
	}
	
	/**
	 * 检查密码输入是否正确（设置选项页面）
	 */
	public boolean checkPwd(String passwordo,String rybh){
		String sql = "select count(1) from %SRYB where RYBH=? AND MM=?";
		sql=String.format(sql, SystemSet.gxBz);
		String passwd = EncryptUtils.encryptToSHA(passwordo, Const.SALTKEY);//密码加密
		String count = db.queryForObject(sql,new Object[]{rybh,passwd}, String.class);
		if("1".equals(count)){
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 检查新密码是否是初始密码（设置选项页面）
	 * @param passwordo  新密码
	 * @return true：不是初始密码  false：是初始密码
	 */
	public boolean checkCsmm(String passwordo,String rybh){
		String sql = "SELECT COUNT(*) FROM %SLOGIN WHERE CSMM=?";
		sql=String.format(sql, SystemSet.sysBz);
		if(db.queryForObject(sql,new Object[]{passwordo}, Integer.class) == 0){
			return true;
		}
		else{
			return false;
		}
	}
	/**
	 * 修改密码（设置选项页面）
	 */
	public int doUpdPwd(String passwordn,String rybh){
		String sql="update %SRYB set mm=? where rybh=? ";
		sql=String.format(sql, SystemSet.gxBz);
		String passwd=EncryptUtils.encryptToSHA(passwordn, Const.SALTKEY);//密码加密
		return db.update(sql,new Object[]{passwd,rybh});
	}
	/**
	 * 修改密码（登录页面，找回密码）
	 */
	public int doUpdPwdByZhmm(String rygh,String xmm){
		String sql="update %SRYB set mm=? where rybh=(select rybh from %SRYB where rygh=?) ";
		sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz);
		String passwd=EncryptUtils.encryptToSHA(xmm, Const.SALTKEY);//密码加密
		return db.update(sql,new Object[]{passwd,rygh});
	}
	/**
	 * 保存个人基本信息（设置选项页面）
	 */
	public boolean doSaveSzxx(String rownums, String lxdh, String qq, String mail, String rybh,String mmwt,String mmda) {
		List sql = new ArrayList();
		List par = new ArrayList();
		sql.add("UPDATE "+SystemSet.gxBz+"RYB SET ROWNUMS=?,LXDH=?,QQ=?,MAIL=? WHERE RYGH=?");
		par.add(new Object[]{rownums,lxdh,qq,mail,rybh});
		sql.add("delete from "+SystemSet.gxBz+"MMZH where RYGH=(select rygh from "+SystemSet.gxBz+"RYB where rybh=? )");
		par.add(new Object[]{rybh});
		sql.add("insert into "+SystemSet.gxBz+"MMZH(gid,RYGH,mmwt,mmda) values(sys_guid(),(select rygh from "+SystemSet.gxBz+"RYB where rybh=? ),?,?)");
		par.add(new Object[]{rybh,mmwt,mmda});
		return db.batchUpdate(sql, par);
	}
	/**
	 * 修改个人信息
	 */
	public int doSavePersonInfo(GX_SYS_RYB ryb){
		String sql = "update %SRYB set xm=?,xb=?,csrq=?,whcd=?,"
				+ "sfzh=?,lxdh=?,qq=?,mail=? where rybh=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				ryb.getXm(),
				ryb.getXb(),
				ryb.getCsrq(),
				ryb.getWhcd(),
				ryb.getSfzh(),
				ryb.getLxdh(),
				ryb.getQq(),
				ryb.getMail(),
				ryb.getRybh()
				};
		return db.update(sql, obj);

	}
	public boolean checkMmzh(String rygh, String mmda,String mmwt) {
		String sql = "SELECT COUNT(*) FROM %SMMZH WHERE rygh=? and mmda=? and mmwt=? ";
		sql=String.format(sql, SystemSet.gxBz);
		if(db.queryForObject(sql,new Object[]{rygh,mmda,mmwt}, Integer.class) != 0){
			return true;
		}else{
			return false;
		}
	}
}
