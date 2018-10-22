package com.googosoft.dao.systemset.xtsz;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB;

@Repository("yxszDao")
public class YxszDao extends BaseDao{
	private Logger logger = Logger.getLogger(YxszDao.class);
	
	public Map getObjectById(){
		String sql ="select MAIL,SMTP,YHM,MM FROM ZC_SYS_XTB";
		return db.queryForMap(sql);
	}
	
	public int doUpdate(ZC_SYS_XTB xtb){
		String sql = "update ZC_SYS_XTB set MAIL=?,SMTP=?,YHM=?,MM=? ";
		Object[] obj = new Object[]{
				xtb.getMail(),
				xtb.getSmtp(),
				xtb.getYhm(),
				xtb.getMm()
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}

}
