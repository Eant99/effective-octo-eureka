package com.googosoft.dao.kjhs;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;

@Repository("pzlxnewDao")
public class PzlxnewDao extends BaseDao{
	private Logger logger = Logger.getLogger(PzlxDao.class);
	
	
	/**
	 * 判断凭证类型编号是否重复（增加）
	 * 
	 * @param pd
	 * @return
	 */
	public int getBhById(PageData pd) {
		String sql = "select count(1) from cw_pzlxbnew b where b.pzlxbh = '"+pd.getString("pzlxbh")+"'";
		return db.queryForInt(sql);
	}
	/**
	 * 判断序号是否重复（编辑）
	 * 
	 * @param pd
	 * @return
	 */
	public int getUpdXhById(PageData pd) {
		String sql = "select count(1) from cw_pzlxbnew b where b.xh = '"+pd.getString("xh")+"' and guid not in('"+pd.getString("guid")+"')";
		return db.queryForInt(sql);
	}
	
	/**
	 * 判断凭证类型编号是否重复（编辑）
	 * 
	 * @param pd
	 * @return
	 */
	public int getUpdBhById(PageData pd) {
		String sql = "select count(1) from cw_pzlxbnew b where b.pzlxbh = '"+pd.getString("pzlxbh")+"' and guid not in('"+pd.getString("guid")+"')";
		return db.queryForInt(sql);
	}
	/**
	 * 新增保存凭证类型信息
	 * 
	 * @param pd
	 * @return
	 */

	public int doAdd(PageData pd) {
		String sql = "insert into cw_pzlxbnew(guid,pzlxbh,pzlxmc,pzbh) values(?,?,?,?)";
		Object[] obj = {
			    pd.getString("guid"),
			    pd.getString("pzlxbh"),
				pd.getString("pzlxmc"),
				pd.getString("pzbhs")	
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 删除凭证类型信息
	 * @param guid
	 * @return
	 */
	public int delete(String guid) {
		String sql = "delete from cw_pzlxbnew where guid in ('"+guid+"')";
		return db.update(sql);
    }
	
	/**
	 * 获取凭证类型详细信息
	 * @param guid
	 * @return
	 */
	public Map getMapByGuid(String guid){
		String sql = "select guid,pzlxbh,pzlxmc,pzbh from cw_pzlxbnew where guid=?";
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql, new Object[]{guid});
		return map;
	}
	
	/**
	 * 编辑保存项目类型信息
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int updPzlx(PageData pd,String guid) {
		String sql="update cw_pzlxbnew set xh=?,pzlxbh=?,pzlxmc=?,pzbh=? where guid='"+guid+"'";
		Object[] obj= {
			pd.getString("xh"),
			pd.getString("pzlxbh"),
			pd.getString("pzlxmc"),
			pd.getString("pzbhs")
		};
		return db.update(sql,obj);
	}

}
