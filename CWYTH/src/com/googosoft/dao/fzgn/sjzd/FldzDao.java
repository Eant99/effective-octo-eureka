package com.googosoft.dao.fzgn.sjzd;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.googosoft.dao.base.BaseDao;

@Repository("fldzDao")
public class FldzDao  extends BaseDao{
	private Logger logger = Logger.getLogger(FldzDao.class);
	
	public int doSave(String bzdm,String ffldm,String fflmc,String zcdm){
		String sql="update ZC_FLB_JYB set FFLDM=?,FFLMC=?,ZCDM=?  where bzdm=?";
		int i=0;
		try {  
			i=db.update(sql,new Object[]{ffldm,fflmc,zcdm,bzdm});
		}catch (DataAccessException e) { 
			logger.error("数据库操作失败\n" + e.getCause().getMessage());
		    return -1;
		}
		return i;
	}

}
