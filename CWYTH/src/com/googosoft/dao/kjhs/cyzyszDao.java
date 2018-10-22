package com.googosoft.dao.kjhs;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;

@Repository("cyzyszDao")
public class cyzyszDao extends BaseDao{
	private Logger logger = Logger.getLogger(PzlxDao.class);
	
	/**
	 * 判断摘要是否重复（增加）
	 * 
	 * @param pd
	 * @return
	 */
	public int getZyById(PageData pd) {
		String sql = "select count(1) from cw_cyzyszb b where b.zynr = '"+pd.getString("zynr")+"'";
		return db.queryForInt(sql);
	}
	
	/**
	 * 判断摘要是否重复（编辑）
	 * 
	 * @param pd
	 * @return
	 */
	public int getUpdZyById(PageData pd) {
		String sql = "select count(1) from cw_cyzyszb b where b.zynr = '"+pd.getString("zynr")+"' and b.guid not in('"+pd.getString("guid")+"')";
		return db.queryForInt(sql);
	}
	
	/**
	 * 新增保存常用摘要信息
	 * 
	 * @param pd
	 * @return
	 */
	public int doAdd(PageData pd,String kjzd,String sszt) {
		String sql = "insert into cw_cyzyszb(guid,kmbh,zynr,sfqy,kjzd,sszt) values(?,?,?,?,'"+kjzd+"','"+sszt+"')";
		Object[] obj = {
			    pd.getString("guid"),
			    pd.getString("kmbh"),
			    pd.getString("zynr"),
				pd.getString("sfqy")	
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 获取常用摘要详细信息
	 * @param guid
	 * @return
	 */
	public Map getMapByGuid(String guid){
		String sql = "select guid,kmbh,zynr,sfqy,kjzd,sszt from cw_cyzyszb where guid=?";
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql, new Object[]{guid});
		return map;
	}
	
	/**
	 * 编辑保存摘要信息
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int updCyzysz(PageData pd,String guid) {
		String sql="update cw_cyzyszb set kmbh=?,zynr=?,sfqy=? where guid='"+guid+"'";
		Object[] obj= {
			pd.getString("kmbh"),
			pd.getString("zynr"),
			pd.getString("sfqy"),
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 删除摘要信息
	 * @param guid
	 * @return
	 */
	public int delete(String guid) {
		String sql = "delete from cw_cyzyszb where guid in ('"+guid+"')";
		return db.update(sql);
    }

}
