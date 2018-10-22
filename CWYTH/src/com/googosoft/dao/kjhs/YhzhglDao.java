package com.googosoft.dao.kjhs;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.Cw_yhzhgl;
import com.googosoft.util.CommonUtils;

@Repository("yhzhglDao")
public class YhzhglDao extends BaseDao{

private Logger logger = Logger.getLogger(YhzhglDao.class);
	
	/**
	 * 添加银行账号
	 * @param dmb
	 * @return
	 */
	public int doAdd(Cw_yhzhgl yhzhb){
		String sql = "insert into cw_yhzhb(guid, kmbh, yhmc, khyhh, lhh, sszt)values(?, ?, ?, ?, ?, ?)";
		int i = 0;
		try{
			i = db.update(sql,new Object[]{yhzhb.getGuid(), yhzhb.getKmbh(), yhzhb.getYhmc(), yhzhb.getKhyhh(), yhzhb.getLhh(),yhzhb.getSszt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 银行账号全删除
	 * @param dmb
	 * @return
	 */
	public int doDeleteAll(){
		String sql = " DELETE from cw_yhzhb";	
		return db.update(sql);
	}

	
}
