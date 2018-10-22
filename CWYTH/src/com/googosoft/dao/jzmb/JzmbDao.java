package com.googosoft.dao.jzmb;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.Cw_jzmbmx;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("jzmbDao")
public class JzmbDao extends BaseDao{

	/**
	 * 删除
	 * @param guid   
	 * @return 
	 */
	public int doDelete(String guid) {
	
		String sql = "delete from cw_jzmbb where guid in ('"+guid+"')";
	
		int i = 0;
		try {  
			i = db.update(sql);
		
		} catch (Exception e) {
		    return -1;
		}
		return i;
	}
	
	/**
	 * 查找凭证类型列表
	 * @param guid   
	 * @return 
	 */
	public List selectSrxmMapById(String guid){
		String sql = "select t.guid as dm,t.lxmc as mc from cw_pzlxb t";//where guid = ?
		return db.queryForList(sql);
	}
	
	/**
	 * 查找摘要列表（与数据字典表的项匹配）
	 * @param guid   
	 * @return 
	 */
	public List selectjzmbzyMapById(String guid){
		String sql = "select t.dm,t.mc from gx_sys_dmb t where zl='zy'";//where guid = ?
		return db.queryForList(sql);
	}
	
	/**
	 * 凭证类型信息
	 * @param guid
	 * @return
	 */
	public List getPzlxMapById(){
		String sql = " select '('||b.pzlxbh||')'||b.pzlxmc pzlx, b.pzlxbh from cw_pzlxbnew b order by xh asc ";
		return db.queryForList(sql);
	}
	
	/**
	 * 修改
	 * @param guid   
	 * @return 
	 */
	public int updateJzmb(PageData pd) {
		String sql = "UPDATE CW_JZMBB SET "
				+ "MBBH = ?,MBMC = ?,PZLX = ?,SFBHWJZPZ = ?,ZY = ?,pzlxbh=?,pzlxmc=? where guid = ?";
		Object[] obj = {
				pd.getString("mbbh"),//CommonUtil.getBeginText(pd.getString("mbbh")),
				pd.getString("mbmc"),
				pd.getString("pzlx"),
				pd.getString("sfbhwjzpz"),
				pd.getString("zy"),
				CommonUtil.getBeginText(pd.getString("pzlx")),
				CommonUtil.getEndText(pd.getString("pzlx")),
				pd.getString("guid")
		};
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	/**
	 * 增加
	 * @param guid   
	 * @return 
	 */
	public int insertJzmb(PageData pd,HttpSession session) {
		String sszt = Constant.getztid(session);
		String sql = "insert into cw_jzmbb (guid,mbbh,mbmc,pzlx,sfbhwjzpz,zy,czr,czrq,sszt,PZLXBH,pzlxmc)" 
				+ " values(?,?,?,?,?,?,?,sysdate,?,?,?)";

		Object[] obj = {
			
				pd.getString("guid"),
				GenAutoKey.makeCkbh("cw_jzmbb", "mbbh", "2"),
				pd.getString("mbmc1"),
				pd.getString("pzlx"),
				pd.getString("sfbhwjzpz"),
				pd.getString("zy"),
				LUser.getGuid(),
				sszt,
				CommonUtil.getBeginText(pd.getString("pzlx")),
				CommonUtil.getEndText(pd.getString("pzlx")),
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	/**
	 * 查看信息
	 * @param guid
	 * @return
	 */
	public Map<String, Object> selectjzmbMapById(String guid){
		Object[] obj = {guid};
		String sql = "select t.guid, t.mbbh, t.mbmc, t.pzlx, t.sfbhwjzpz, t.zy from CW_JZMBB t where t.guid = ?";
		return db.queryForMap(sql,obj);
	}
	/**
	 * 添加收入预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doAddmx(Cw_jzmbmx jzmbmx) {
		String sql = "insert into cw_jzmbmxb(guid,zckmbh,zrkmbh,zjid,sszt)values(?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{jzmbmx.getGuid(),jzmbmx.getZckmbh(),jzmbmx.getZrkmbh(),jzmbmx.getZjid(),jzmbmx.getSszt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		  //  logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 获得凭证模板明细编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getjzmbmxById(String guid) {
		String sql="select guid, zckmbh,(select '('||(select kmbh from cw_kjkmszb kj1 where kj1.guid=jz.zckmbh )||')'|| kmmc from cw_kjkmszb kj where kj.guid = jz.zckmbh) as zckmbh1," + 
				"zrkmbh,zjid,(select '('||(select kmbh from cw_kjkmszb kj1 where kj1.guid=jz.zrkmbh )||')'|| kmmc from cw_kjkmszb kj where kj.guid = jz.zrkmbh) as zrkmbh1 from cw_jzmbmxb jz "
				+ "where zjid=? order by zrkmbh1";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	/**
	 * 删除结转模板明细
	 * 
	 */
	public int dodeletejzmbmx(String guid) {
		String sql="delete cw_jzmbmxb where zjid in (?)";
		int i=0;
		i=db.update(sql,new Object[] {guid});
		return i;
	}
	/**
	 * 判断模板编号是否重复
	 * @param xmlxbh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckDwbh(String mbbh){
		String sql = "select count(1) from Cw_jzmbb where  mbbh= ? ";
		String count = db.queryForObject(sql,new Object[]{mbbh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 添加收入预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doAddjwjl(Map map) {
		String sql = "insert into cw_sys_ywjlb(guid,ywid,czid,czr,czsj,kmlx,zbid,sszt) values(?,?,?,?,sysdate,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{map.get("guid"),map.get("ywid"),map.get("czid"),map.get("czr"),map.get("kmlx"),map.get("zbid"),map.get("sszt")});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		  //  logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public List<Map<String, Object>> getJsList(String guid, String searchValue,
			String rybh, String s1) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT K.GUID, K.MBBH,K.MBMC,");
		sql.append("(SELECT B.MC FROM GX_SYS_DMB B WHERE B.ZL='" + s1 + "' AND B.DM=K.ZY) AS PZZY, ");
		sql.append("(SELECT WM_CONCAT(M.KMBH) FROM CW_KJKMSZB M LEFT JOIN CW_JZMBMXB B ON B.ZCKMBH=M.GUID WHERE B.ZJID=K.GUID) AS ZCKM, ");
		sql.append("(SELECT WM_CONCAT(TO_CHAR(M.KMBH)) FROM CW_KJKMSZB M LEFT JOIN CW_JZMBMXB B ON B.ZRKMBH=M.GUID WHERE B.ZJID=K.GUID) AS ZRKM FROM CW_JZMBB K ");
//		sql.append("WHERE 1=1 and  K.guid "+CommonUtils.getInsql(guid)+" ");
		sql.append("WHERE 1=1  ");
		
		if(Validate.noNull(guid)){
			sql.append(" and  k.guid in ('"+guid.trim()+"') ");
		}
		
//		Object[] guid2 = guid.split(",");
//		return db.queryForList(sql.toString(),guid2);
		return db.queryForList(sql.toString());
	}
	public List getxx(String guid) {
 		String sql1 = "select mbbh,sszt,guid from cw_jzmbb where guid in ('"+guid+"')";
 		return db.queryForList(sql1);
 	}
 	public int updatebh(String sszt,String mbbh) {
 		String sql = "update cw_jzmbb set mbbh=to_char(mbbh-1,'FM909') where mbbh>'"+mbbh+"' and sszt = '"+sszt+"'";
 		return db.update(sql);
 	}
}
