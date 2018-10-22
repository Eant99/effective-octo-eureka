package com.googosoft.dao.systemset.sjgl;

import java.sql.SQLException;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.googosoft.dao.base.BaseDao;

@Repository("xtcshDao")
public class XtcshDao extends BaseDao{
	private Logger logger = Logger.getLogger(XtcshDao.class);
	/**
	 * 系统初始化
	 */
	public boolean doDeleteAll() {		
		String sql = "{CALL PROC_SYS_XTCSH}";
		try {  
			db.execute(sql);
			return true;
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return false;
		}
	}

}


