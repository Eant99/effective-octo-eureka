/**
 * 
 */
package com.googosoft.dao.wsbx;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.wsbx.Cw_Shyj;

/**
 * @author lifei
 * @date 2018-4-3
 * @title ShyjDao.java
 */
@Repository("ShyjDao")
public class ShyjDao extends BaseDao {
	private Logger logger = Logger.getLogger(ShyjDao.class);

	/**
	 * 增加
	 * 
	 * @param dwb
	 * @return
	 */
	public int doAdd(Cw_Shyj po) {
		String sql = "insert into CW_SHYJB(guid,czrq,czr,shlx,shyj,title)values(sys_guid(),?,?,?,?,?)";
		Object[] obj = new Object[] { po.getCzrq(), po.getCzr(), po.getShlx(),
				po.getShyj(), po.getTitle() };
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
	 * 修改
	 * 
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public int doUpdate(Cw_Shyj po) {
		String sql = "update CW_SHYJB set czrq=?,czr=?,shlx=?,shyj=?,title=? where guid = ?";
		Object[] obj = new Object[] { po.getCzrq(), po.getCzr(), po.getShlx(),
				po.getShyj(), po.getTitle(), po.getGuid() };

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
	 * 删除
	 * 
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public int doDelete(String guid) {
		String sql = "delete from CW_SHYJB where guid in('"+guid+"')";
		int i = 0;
		try {
			i = db.update(sql);
		} catch (DataAccessException e) {
			logger.error("数据库操作失败\n" + e.getCause().getMessage());
			return -1;
		}
		return i;
	}
	
	/**
	 * 信息查询
	 * @param guid
	 * @return
	 */
	public Map getMapINfoByGuid(String guid){
		String sql = "select * from CW_SHYJB where guid=?";
		return db.queryForMap(sql,new Object[]{guid});
	}

}
