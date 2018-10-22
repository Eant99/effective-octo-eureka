package com.googosoft.dao.pjgl.pjsz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.mbxz.MbxzDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("pjytDao")
public class PjytDao  extends BaseDao{

	private Logger logger = Logger.getLogger(PjytDao.class);

	/**
	 * 票据用途信息查询
	 * @param guid
	 * @return
	 */
	public Map getPjytMapById(String guid) {
		String sql = " select GUID,PJYTBH,PJYTMC,SFQY,BZ,SSZT from  CW_PJYTB where 1=1 and  GUID=? ";
		Object[] obj = new Object[]{guid};
		Map map = null;
		try {  
			map = db.queryForMap(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return map;
	}
	/**
	 * 新增票据用途信息
	 * @param guid
	 * @return
	 */
	public int addPjyt(PageData pd, HttpSession session,String sszt) {
		String sql = " insert into CW_PJYTB (GUID,PJYTBH,PJYTMC,SFQY,BZ,SSZT) "
				+ " values (?,?,?,?,?,?) ";
		Object[] obj = new Object[]{
				pd.getString("guid"),
//				pd.getString("pjytbh"),
				GenAutoKey.makeCkbh("CW_PJYTB", "pjytbh", "4"),
				pd.getString("pjytmc"),
				pd.getString("sfqy"),
				pd.getString("bz"),
				sszt
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改票据用途信息
	 * @param guid
	 * @return
	 */
	public int editPjyt(PageData pd) {
		String sql = "update CW_PJYTB set PJYTBH =?,PJYTMC=?,SFQY=?,BZ=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{
				pd.getString("pjytbh"),
				pd.getString("pjytmc"),
				pd.getString("sfqy"),
				pd.getString("bz"),
				pd.getString("guid")
		};
		
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public int doDelete(String guid) {

		String guids=guid.replace(",", "','");
		String sql="delete from CW_PJYTB t where t.guid in('"+guids+"')";
		int i = 0;
		try{
			i = db.update(sql);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public List<Map<String, Object>> getList(String searchValue,String guid,String sszt) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.GUID,t.PJYTBH,t.PJYTMC,decode(t.SFQY,'1','是','0','否')SFQYMC,t.BZ ");
		sql.append(" from CW_PJYTB t where 1=1 ");
	//	sql.append(" where t.sszt = '"+sszt+"' ");
		if(Validate.noNull(guid)){
			sql.append(" and t.guid in ('"+guid+"') ");
		}
		return db.queryForList(sql.toString());
	}
	//判断是否使用
	public int checkSY(String guid) {
		String sql= "select count(1) from cw_pj t where t.pjyt in('"+guid+"') ";
		int i=0;
		try{
			String s = Validate.isNullToDefault(db.queryForSingleValue(sql), "0").toString();
			i=Integer.parseInt(s);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	public boolean doCheckDwbh(String guid) {
		String sql = "select count(1) from CW_PJYTB where  PJYTBH= ? ";
		String count = db.queryForObject(sql,new Object[]{guid}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}

}
