package com.googosoft.dao.kjhs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.CW_PZKMDZB;
import com.googosoft.pojo.kjhs.CW_PZLXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWMXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
@Repository("pzlxDao")
public class PzlxDao  extends BaseDao{
	private Logger logger = Logger.getLogger(PzlxDao.class);
	
	/**
	 * 验证类型名称是否重复
	 */
	public boolean getObjectById(String guid,String lxmc) {
		String sql = "select count(0) from cw_pzlxb where lxmc='"+lxmc+"' and guid not in ('"+guid+"')";
		int aa = db.queryForInt(sql);
		if(aa == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 验证凭证字是否重复
	 */
	public boolean getObjectByPzz(String guid,String pzz) {
		String sql = "select count(0) from cw_pzlxb where pzz='"+pzz+"' and guid not in ('"+guid+"')";
		int aa = db.queryForInt(sql);
		if(aa == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 添加凭证类型信息
	 * @param dmb
	 * @return
	 */
	public int doAdd(CW_PZLXB pzlxb) {
		String sql = "insert into cw_pzlxb(guid,lxbh,lxmc,pzz,sszt)values(?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{pzlxb.getGuid(),pzlxb.getLxbh(),pzlxb.getLxmc(),pzlxb.getPzz(),pzlxb.getSszt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加凭证科目对照表信息
	 * @param dmb
	 * @return
	 */
	public int doAdd1(CW_PZKMDZB pzkmdzb) {
		String sql = "insert into cw_pzkmdzb(guid,pzlxm,kmbh,xzlx)values(?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{pzkmdzb.getGuid(),pzkmdzb.getPzlxm(),pzkmdzb.getKmbh(),pzkmdzb.getXzlx()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 获得凭证类型编辑信息
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getpzlxById(String guid) {
		String sql="select lxbh,lxmc,pzz from cw_pzlxb where guid=?";
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 获得凭证类型对照表借方编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbJfById(String guid) {
		String sql="select pz.guid,pz.xzlx,pz.pzlxm,pz.kmbh,(select k.kmbh from cw_kjkmszb k where k.guid=pz.kmbh )as kmbh1,"
				+ " (select k.kmmc from cw_kjkmszb k where k.guid=pz.kmbh)as kmmc"
				+ " from cw_pzkmdzb pz where xzlx='Jf' and pzlxm=?";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	/**
	 * 获得凭证类型对照表贷方编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbDfById(String guid) {
		String sql="select pz.guid,pz.xzlx,pz.pzlxm,pz.kmbh,(select k.kmbh from cw_kjkmszb k where k.guid=pz.kmbh )as kmbh1,"
				+ " (select k.kmmc from cw_kjkmszb k where k.guid=pz.kmbh)as kmmc"
				+ " from cw_pzkmdzb pz where xzlx='Df' and pzlxm=?";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	/**
	 * 获得凭证类型对照表凭证必有编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbPzbyById(String guid) {
		String sql="select pz.guid,pz.xzlx,pz.pzlxm,pz.kmbh,(select k.kmbh from cw_kjkmszb k where k.guid=pz.kmbh )as kmbh1,"
				+ " (select k.kmmc from cw_kjkmszb k where k.guid=pz.kmbh)as kmmc"
				+ " from cw_pzkmdzb pz where xzlx='Pzby' and pzlxm=?";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	/**
	 * 获得凭证类型对照表凭证必无编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbPzbwById(String guid) {
		String sql="select pz.guid,pz.xzlx,pz.pzlxm,pz.kmbh,(select k.kmbh from cw_kjkmszb k where k.guid=pz.kmbh )as kmbh1,"
				+ " (select k.kmmc from cw_kjkmszb k where k.guid=pz.kmbh)as kmmc"
				+ " from cw_pzkmdzb pz where xzlx='Pzbw' and pzlxm=?";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	/**
	 * 编辑凭证类型信息
	 * @param dmb
	 * @return
	 */
	public int doEdit(CW_PZLXB pzlxb) {
		String sql = "update CW_pzlxb set  lxbh=?,lxmc=?,pzz=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{pzlxb.getLxbh(),pzlxb.getLxmc(),pzlxb.getPzz(),pzlxb.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 凭证科目对照表删除
	 * @param 
	 * @return 
	 */
	public int doDeletePzkmdzb(String guid,CW_PZKMDZB pzkmdzb) {
		String sql = "DELETE CW_PZKMDZB WHERE pzlxm=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {guid});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 凭证科目对照信息删除
	 * @param 
	 * @return 
	 */
//	public int doDeletePzkmdz(String guid,CW_PZKMDZB pzkmdzb) {
//		String sql = "DELETE CW_PZKMDZB WHERE guid"+CommonUtils.getInsql(guid);
//		sql=String.format(sql, SystemSet.gxBz);	
//		
//		int i = 0;
//		try {  
//			i = db.update(sql, new Object[] {pzkmdzb.getGuid()});
//		} catch (DataAccessException e) {  
//		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
//		    return -1;
//		}
//		return i;
//	}
	/**
	 * 凭证科目编辑删除
	 * @param dmb
	 * @return
	 */
	public int doDeletePzkm(String pzlxm) {
		String sql = "DELETE CW_pzkmdzb WHERE pzlxm=?";
		
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {pzlxm});
		} catch (DataAccessException e) {  
		   
		    return -1;
		}
		return i;
	}
	/**
	 * 单条删除
	 * @param dmb
	 * @return
	 */
	public int doDelete(String guid) {
		String sql = "DELETE CW_pzlxb WHERE GUID='"+guid+"'";
		String sql1 = "DELETE CW_pzkmdzb WHERE pzlxm='"+guid+"'";
		           
			       db.update(sql1);
		   return  db.update(sql);

	}
	/**
	 * 凭证类型批量删除
	 * @param guid
	 * @return
	 */
	public int doDeletes(String guid) {
	
		String sql = "DELETE CW_pzlxb WHERE guid in ('"+guid+"')";
		String sql1 = "DELETE CW_pzkmdzb WHERE pzlxm in ('"+guid+"')";
		       db.update(sql);
		return db.update(sql1);
		
		
	}
	//*****************************************************
	public int delete(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "delete from cw_srxmb where guid in ('"+guid+"')";
		return db.update(sql);
	}
	
	
	public Map getkmszObjectById(String id) {
//	String sql = "select d.gid,d.rybh,(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=d.rybh) as ryb,d.xm,d.bgdd,d.zw,d.bddh,d.moblie,d.qq,d.email,d.zt," 
//			+ "(case d.zt when '1' then '正常' when '0' then '禁用' end) ztmc,d.pxxh " 
//			+ " from ZC_TXL d where gid = ?";	
	String sql="select fyfl,kmmc,kmlbdm from cw_kjkmsz";
	sql=String.format(sql);
	return db.queryForMap(sql,new Object[]{id});
}
	/**
	 * 根据主键获取数据字典的详细信息  进行修改操作等
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		String sql = "SELECT A.DM,A.MC,A.JB,A.BZ,A.DMXH,A.ZL,A.DMSX,A.MS FROM %SDMB A WHERE A.DMXH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dmxh});
	}
	/**
	 * 获取会计科目字典树
	 */
	public  List kmszMenu(String jb){
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from gx_sys_dmb b where b.zl='"+Constant.ZCZL+"' and b.jb=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取会计科目字典树
	 */
	public  List kmszMenuzj(String jb){
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
				"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+jb+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取功能科目字典树
	 */
	public  List kmszMenuByTwo(String jb,String zl){
		String sql = "select dm,mc,zl,jb from gx_sys_dmb where jb = '"+jb+"' and zl='"+zl+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}


}
