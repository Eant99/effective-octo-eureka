package com.googosoft.dao.bzjgl;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.ysgl.grjfsz.GrjfszDao;
import com.googosoft.pojo.bzjgl.CW_BZBZSZB;
import com.googosoft.util.PageData;

@Repository("bzxjbzsdDao")
public class BzxjbzsdDao extends BaseDao {
	private Logger logger = Logger.getLogger(GrjfszDao.class);
	/**
	 * 查询补助标准设置表信息
	 * @param bzbh  
	 * @return 
	 */
	public Map<?, ?> getObjectById(String dwbh) {
		String sql = "select K.guid,K.bzbh,K.bzmc,k.sfqy, " 
				+ " to_char(bzje,'FM999999999999999999999999999999990.00') as bzje,to_char(szsj,'yyyy-MM-dd HH24:mi:ss') as szsj,K.jbr,K.bz from cw_bzbzszb K WHERE K.guid='"+dwbh+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 修改补助标准设置表信息
	 * @param bzbh  
	 * @return 
	 */
	public int doUpdate(CW_BZBZSZB bzbz) {
		String sql = "update cw_bzbzszb set bzmc=?,bzje=?,sfqy=?,bz=? where guid = ?";
		Object[] obj = new Object[]{
				bzbz.getBzmc(),
				bzbz.getBzje(),
				bzbz.getSfqy(),
				bzbz.getBz(),
				bzbz.getGuid()
		};
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
	 * 增加补助标准设置表信息
	 * @param bzbh  
	 * @return 
	 */
	public int doAdd(CW_BZBZSZB bzbz) {
		String sql = "INSERT INTO CW_BZBZSZB (BZBH,BZMC,BZJE,SFQY,BZ,JBR,SZSJ) "
				+ "VALUES(?,?,?,?,?,?,SYSDATE)";
		Object[] obj = new Object[]{
				bzbz.getBzbh(),
				bzbz.getBzmc(),
				bzbz.getBzje(),
				bzbz.getSfqy(),
				bzbz.getBz(),
				bzbz.getJbr()
		};
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
	 * 删除补助标准设置表信息
	 * @param bzbh  
	 * @return 
	 */
	public int doDelete(String bzbh) {
		String sql = "DELETE CW_BZBZSZB WHERE GUID ='"+bzbh+"'";
		return db.update(sql);
	}
	/**
	 * 批量删除补助标准设置表信息
	 * @param bzbh  
	 * @return 
	 */
	public int doDelete2(String bzbh) {
		String sql = "DELETE CW_BZBZSZB WHERE GUID IN ('"+bzbh+"')";
		return db.update(sql);
	}
}
