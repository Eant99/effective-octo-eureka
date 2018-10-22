package com.googosoft.dao.ysgl.jcsz;
import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.ysgl.jjkmybmdysz;
import com.googosoft.util.CommonUtils;
import com.lowagie.text.List;

@Repository("jjkmybmdyszDao")
public class JjkmybmdyszDao extends BaseDao{
	private Logger logger = Logger.getLogger(JjkmybmdyszDao.class);
	/**
	 * 删除单条经济科目与部门对应设置信息
	 * @param guid
	 * @return
	 */
	public int doDelete(String guid) {
		String sql = "DELETE from cw_jjkmybmdyszb WHERE guid "+CommonUtils.getInsql(guid);
		Object[] obj = guid.split(",");
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	
	/**
	 * 添加经济科目与部门对应设置信息
	 * @param dmb
	 * @return
	 */
	public int doAdd1(jjkmybmdysz bmxx) {
		String sql = "insert into Cw_jjkmybmdyszb(guid,bmbh,jjkmbh)values(?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{bmxx.getGuid(),bmxx.getBmbh(),bmxx.getJjkmbh()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}

//	public List getObjectById1(String guid) {
//		// TODO Auto-generated method stub
//		return null;
//	}
	/**
	 * 经济科目与部门对应设置表信息实体
	 * @param dwbh
	 * @return
	 */
	public List getObjectById1(String guid) {
		String sql = "select * from cw_jjkmybmdysz where jjkmbh = ?";
		
		return (List) db.queryForList(sql,new Object[]{guid});
	}
	

}
