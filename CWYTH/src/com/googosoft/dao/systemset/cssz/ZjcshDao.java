package com.googosoft.dao.systemset.cssz;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.systemset.sjgl.XtcshDao;
/**折旧初始化
 * @author sxl
 *
 */
@Repository("zjcshDao")
public class ZjcshDao extends BaseDao {
	private Logger logger = Logger.getLogger(XtcshDao.class);
	/**
	 * 折旧初始化
	 */
	public boolean doDeleteAll() {		
		String sql = "{CALL PROC_ZJ_XTCSH}";
		int i = 0;
		try {
			return db.batchUpdateByProcedure("PROC_ZJ_XTCSH", null);
		} catch (Exception e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return false;
		}
	}
}
