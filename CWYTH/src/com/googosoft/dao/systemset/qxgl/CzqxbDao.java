package com.googosoft.dao.systemset.qxgl;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;


/**
 * 操作权限dao
 * @author googosoft
 *
 */
@Repository("czqxbDao")
public class CzqxbDao extends BaseDao{
	private Logger logger = Logger.getLogger(CzqxbDao.class);
	/**
	 * 获取一级菜单
	 * @return
	 */
	public List getYjcdList(String login) {
		String sql = "select mkbh,mkmc from "+TnameU.MKB+" t where length(t.mkbh)=2 and t.qxbz='1' ";
		if(!SystemSet.AdminRybh().equals(login)){
			sql += " and exists (select 1 from ZC_SYS_CZQXB z where rybh='"+login+"' and substr(z.mkbh,0,2)=t.mkbh)";
		}
		//sql += " and exists (select 1 from ZC_SYS_CZQXB z where rybh='"+login+"' and substr(z.mkbh,0,2)=t.mkbh)";
		sql+="order by xh,mkbh";
		return db.queryForList(sql);
	}
	
	public Map getNextRyIfGly() {
		String sql = "select rybh from gx_sys_ryb where rybh!='000000' and rownum = 1 order by rybh ";
		return db.queryForMap(sql);
	}

	/**
	 * 获取下级菜单
	 * @param mkbh
	 * @return
	 */
	public List getXjcdList(String mkbh,String login) {
		String sql = "select mkbh,mkmc from "+TnameU.MKB+" t where length(t.mkbh)=length(?) + 2 and t.qxbz='1' and substr(mkbh,1,length(?))=?";
		if(!SystemSet.AdminRybh().equals(login)&&!login.contains("no")){
			sql += " and exists (select 1 from ZC_SYS_CZQXB z where rybh='"+login+"' and substr(z.mkbh,0,4)=t.mkbh)";
		}
		//sql += " and exists (select 1 from ZC_SYS_CZQXB z where rybh='"+login+"' and substr(z.mkbh,0,4)=t.mkbh)";
		if(login.substring(0,2).equals("no")){
			sql += " and exists (select 1 from ZC_SYS_CZQXB z where  z.rybh='"+login.substring(2)+"' and z.mkbh=t.mkbh)";
		}
		sql+="order by xh,mkbh";
		return db.queryForList(sql,new Object[]{mkbh,mkbh,mkbh});
	}
	
//	/**
//	 * 获取三级菜单
//	 * @param mkbh
//	 * @return
//	 */
//	public List getSjcdList(String mkbh) {
//		String sql = "SELECT MKBH,MKMC FROM "+TnameU.MKB+" T WHERE LENGTH(T.MKBH)=6 AND t.QXBZ='1' and substr(mkbh,1,4)=? order by mkbh";
//		return db.queryForList(sql,new Object[]{mkbh});
//	}

	/**
	 * 判断用户是否有该菜单的权限
	 * @param rybh
	 * @param mkbh
	 * @return
	 */
	public boolean doCheckCzqx(String rybh, String mkbh){
		String sql = "select count(1) from "+TnameU.CZQXB+" WHERE 1=1 AND RYBH=? AND MKBH=? ";
		String count = db.queryForObject(sql,new Object[]{rybh,mkbh}, String.class);
		return "0".equals(count)?false:true;
	}
	public List<Map<String, Object>> getRymc(String mkbh){
		return db.queryForList("SELECT T.XM AS MC,T.RYBH FROM GX_SYS_RYB T WHERE T.RYBH IN(SELECT RYBH FROM ZC_SYS_CZQXB WHERE MKBH=?)", new Object[]{mkbh});
	}
	/**
	 * 用户权限下的三级菜单
	 * @param rybh
	 * @return
	 */
	public List getSjcdListOfQx(String mkbh, String rybh){
		String sql = "SELECT m.MKBH,m.MKMC,nvl(y.sfzs,'0') as sfzs,y.MKMC_NEW as MKMCNEW FROM zc_sys_czqxb t left join zc_sys_mkb m on t.mkbh=m.mkbh left join ZC_SYS_YWB y on y.rybh=t.rybh and y.mkbh=t.mkbh WHERE LENGTH(t.MKBH)=6 AND m.QXBZ='1' and substr(t.mkbh,1,4)=? and t.rybh=? order by  m.xh,m.mkbh";
		return db.queryForList(sql,new Object[]{mkbh, rybh});
	}
	/**
	 * 用户权限下的三级菜单所在的二级菜单
	 * @param rybh
	 * @return
	 */
	public List getEjcdListOfQx(String mkbh, String rybh){
		String sql = "SELECT m.MKBH,m.MKMC,m.xh FROM "+TnameU.CZQXB+" t left join "+TnameU.MKB+" m on substr(t.mkbh,1,4)=m.mkbh WHERE LENGTH(t.MKBH)=6 AND m.QXBZ='1' and substr(t.mkbh,1,2)=? and t.rybh=? group by m.MKBH,m.xh,m.MKMC order by  m.xh,m.mkbh";
		return db.queryForList(sql,new Object[]{mkbh,rybh});
	}
	/**
	 * 用户权限下的三级菜单所在的一级菜单
	 * @param rybh
	 * @return
	 */
	public List getYjcdListOfQx(String rybh){
		String sql = "SELECT m.MKBH,m.MKMC,m.xh FROM "+TnameU.CZQXB+" t left join "+TnameU.MKB+" m on substr(t.mkbh,1,2)=m.mkbh WHERE LENGTH(t.MKBH)=6 AND m.QXBZ='1' and substr(t.mkbh,1,2)<>'05' and t.rybh=? group by m.MKBH,m.xh,m.MKMC order by m.xh,m.mkbh";
		return db.queryForList(sql,new Object[]{rybh});
	}

	/**
	 * 保存操作信息
	 * @param rybh
	 * @param mkbh
	 * @return
	 */
	@Resource(name="systemSet")
	private SystemSet systemSet;
	public int doSave(String mkbh, String rybh) {
		Date czrq =new Date();
		String czr = LUser.getRybh();
		String mkbhs [] =  mkbh.split(",");
		String mkbhss = mkbh.replace(",", "','");
		int k=0;
		try { 
			if(Validate.noNull(rybh)){
				String sqls = "DELETE FROM "+TnameU.CZQXB+"  WHERE RYBH =?";
				if(!czr.equals(SystemSet.AdminRybh())){
					sqls += " and mkbh in(select mkbh from "+TnameU.CZQXB+" where rybh='"+czr+"')";
				}
//				String sqls = "DELETE FROM "+TnameU.CZQXB+"  WHERE RYBH =? and mkbh in(select mkbh from "+TnameU.CZQXB+" where rybh="+czr+")";
				int m = db.update(sqls, new Object[]{rybh});
				for (int i=0;i<mkbhs.length;i++){
					if(!(mkbhs[i].equals("") || mkbhs[i] == null)){
						String sql = "INSERT INTO "+TnameU.CZQXB+"(RYBH,MKBH,CZR,CZRQ,XTBZ) VALUES(?,?,?,?,?)";
						k = db.update(sql, new Object[]{rybh,mkbhs[i],czr,czrq,systemSet.XTBZ});
					}else{
						k=1;
					}
				}
			}
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return k;
	}

}
