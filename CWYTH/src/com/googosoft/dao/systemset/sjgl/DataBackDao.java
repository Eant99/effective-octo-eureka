package com.googosoft.dao.systemset.sjgl;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;

@Repository("dataBackDao")
public class DataBackDao extends BaseDao{
     private Logger logger = Logger.getLogger(DataBackDao.class);
		
     public int getZxbf() {
		String sql = "UPDATE  %SXTB SET BFRQ=SYSDATE ";
		sql=String.format(sql, SystemSet.sysBz);
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
    public int getBfmc(String bfmc){
    	String sql = "update %Sxtb set bfmc = '"+bfmc+"', bfrq =sysdate";
    	sql=String.format(sql, SystemSet.sysBz);
    	int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
    }
}
