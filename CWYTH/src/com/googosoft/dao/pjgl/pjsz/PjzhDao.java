package com.googosoft.dao.pjgl.pjsz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("pjzhDao")
public class PjzhDao extends BaseDao{

	private Logger logger = Logger.getLogger(PjytDao.class);
	
	/**
	 * 票据用途信息查询
	 * @param guid
	 * @return
	 */
	public Map getPjytMapById(String guid) {
		String sql = " select t.GUID,t.ZHMC,lx.PJLXMC as PJLXMC ,t.SFQY,t.SSZT from  CW_PJZHB t left join cw_pjlxb lx on lx.guid = t.pjlx where 1=1  and  t.GUID=?    ";
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
	public int addPjyt(PageData pd, String sszt) {
		String sql = " insert into CW_PJZHB (GUID,ZHMC,PJLX,SFQY,SSZT) "
				+ " values (?,?,?,?,?) ";
		Object[] obj = new Object[]{
				pd.getString("guid"),
				pd.getString("zhmc"),
//	`			pd.getString("pjlx"),
				pd.getString("pid"),
				pd.getString("sfqy"),
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
		String sql = "update CW_PJZHB set ZHMC =?,PJLX=?,SFQY=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{
				pd.getString("zhmc"),
//				pd.getString("pjlx"),
				pd.getString("pid"),
				pd.getString("sfqy"),
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
		String sql="delete from CW_PJZHB t where t.guid in('"+guids+"')";
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
//		sql.append(" select t.guid,t.zhmc,t.pjlx,decode(t.sfqy,'1','是','0','否')sfqymc ");
		sql.append(" select t.GUID,t.ZHMC,lx.PJLXMC as PJLXMC ,decode(t.sfqy,'1','是','0','否')sfqymc,t.SSZT from  CW_PJZHB t left join cw_pjlxb lx on lx.guid = t.pjlx ");
		sql.append(" where 1=1 ");
//		sql.append(" where x.sszt = '"+sszt+"' ");
		if(Validate.noNull(guid)){
			sql.append(" and t.guid in ('"+guid+"') ");
		}
		return db.queryForList(sql.toString());
	}
	//判断是否使用
		public int checkSY(String guid) {
			String sql= "select (select count(1) from cw_pj t where t.pjzh in ('"+guid+"'))+ " +
					"(select count(1) from cw_pjgm t where t.pjzh in ('"+guid+"')) from  dual";
			int i=0;
			try{
				String s = Validate.isNullToDefault(db.queryForSingleValue(sql), "0").toString();
				i=Integer.parseInt(s);
	System.err.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@i========"+i);			
			}catch (DataAccessException e) {  
				SQLException sqle = (SQLException) e.getCause();  
			    logger.error("数据库操作失败\n" + sqle);  
			    return -1;
			}
			return i;
		}
	
}
