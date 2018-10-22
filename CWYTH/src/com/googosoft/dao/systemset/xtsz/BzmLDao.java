package com.googosoft.dao.systemset.xtsz;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.xtsz.ZC_BZML;
import com.googosoft.util.CommonUtils;

@Repository("bzmlDao")
public class BzmLDao extends BaseDao{
	private Logger logger = Logger.getLogger(BzmLDao.class);
	
	/**
	 * 获取帮助目录的详细信息
	 */
	public Map getObjectById(String bh) {
		String sql = "SELECT A.BH,A.MC,A.ZT FROM %SBZML A WHERE A.BH=?";
		sql=String.format(sql, SystemSet.zcBz);
		return db.queryForMap(sql,new Object[]{bh});
	}
	
	/**
	 * 添加目录信息
	 */
	public int doAdd(ZC_BZML bzml){
		String sql="insert into %SBZML(bh,mc,zt) values(?,?,?) ";
		sql=String.format(sql, SystemSet.zcBz);
		Object[] obj = new Object[]{
				bzml.getBh(),
				bzml.getMc(),
				bzml.getZt()
				};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i ;
	}
	
	/**
	 * 修改目录信息
	 */
	public int doUpdate(ZC_BZML bzml){
		String sql = "update %SBZML set bh=?,mc=?,zt=? where bh=?";
		sql=String.format(sql, SystemSet.zcBz);
		Object[] obj = new Object[]{
				bzml.getBh(),
				bzml.getMc(),
				bzml.getZt(),
				bzml.getBh()
				};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i ;
	}
	
	/**
	 * 删除目录维护信息
	 */
	public int doDelete(String bh) {
		String sql = "DELETE %SBZML WHERE BH"+CommonUtils.getInsql(bh);
		sql=String.format(sql, SystemSet.zcBz);
		Object[] obj = bh.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}

}
