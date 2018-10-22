package com.googosoft.dao.fzgn.wxzf;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.wxzf.CW_XFDDGL;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;

/**
 * 消费地点信息dao
 * @author googosoft
 *
 */
@Repository("xfddglDao")
public class XfddglDao extends BaseDao{

	private Logger logger = Logger.getLogger(XfddglDao.class);
	public Map<String, Object> getObjectById(String guid) {
		String sql = "select A.guid,A.xfddbh,A.xfddmc,A.sscbsbh,A.zlwz,A.zt,A.fzr,a.ywlx from CW_XFDDGL A WHERE A.GUID='"+guid+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 获取承包商信息
	 */
	public List getCbsList() {
		String sql = "select CBSBH,cbsmc from CW_CBSXX";
		return db.queryForList(sql);
		
	}
	/**
	 * 判断承包商编号是否已经存在
	 * @param cbsbh
	 * @return
	 */
	public boolean checkCbsbh(String xfddbh) {
		String sql = "SELECT COUNT(1) FROM CW_XFDDGL WHERE  xfddbh = ?";
		sql = String.format(sql);
		Integer result = db.queryForObject(sql, new Object[] { xfddbh },
				Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 新增保存消费地点信息
	 */
	public int doAdd(CW_XFDDGL xfddxx) {		
		String sql = "insert into CW_XFDDGL(guid,xfddbh,xfddmc,sscbsbh,zlwz,zt,fzr,ywlx) values(?,?,?,?,?,?,?,?) " ;				
			  	
		Object[] obj = new Object[] {xfddxx.getGuid(),xfddxx.getXfddbh(),xfddxx.getXfddmc(),xfddxx.getSscbsbh(),xfddxx.getZlwz(),xfddxx.getZt(),xfddxx.getFzr(),xfddxx.getYwlx()};
		return db.update(sql, obj);
	}
	
	
	public int checkXfddbh(CW_XFDDGL xfddxx) {		
		String sql = "select count(1) from CW_XFDDGL a where a.XFDDBH = '"+xfddxx.getXfddbh()+"'" ;				
			  	
		return db.queryForInt(sql);
	}
	
	public int checkXfddbhupdate(CW_XFDDGL xfddxx) {		
		String sql = "select count(1) from CW_XFDDGL a where a.guid != '"+xfddxx.getGuid()+"' and a.XFDDBH = '"+xfddxx.getXfddbh()+"'" ;				
			  	
		return db.queryForInt(sql);
	}
	
	/**
	 * 删除消费地点信息
	 * @param guid
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String sql = "DELETE CW_XFDDGL WHERE GUID " + wstr;		
		int result = 0;
		try {			
			result = db.update(sql, params);
			
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();// 事务回滚
		}
		return result;
	}
	/**
	 * 修改消费地点信息
	 * @param cbsxx
	 * @return
	 */
	public int doUpp(CW_XFDDGL xfddxx) {		
		String sql = "update CW_XFDDGL set xfddbh=?,xfddmc=?,sscbsbh=?,zlwz=?,zt=?,fzr=?,ywlx=? where GUID=?" ;		
			    		
		Object[] obj = new Object[] {xfddxx.getXfddbh(),xfddxx.getXfddmc(),xfddxx.getSscbsbh(),xfddxx.getZlwz(),xfddxx.getZt(),xfddxx.getFzr(),xfddxx.getYwlx(),xfddxx.getGuid()};
		return db.update(sql, obj);
	}
	
	/**
	 * 更新二维码信息
	 * @param guid
	 * @param ewmUrl
	 * @return
	 */
	public int doUpdateEwm(String guid,String ewmUrl) {
		String sql = "update CW_XFDDGL set ewmurl=? where guid=?";
		Object[] obj = new Object[] {ewmUrl,guid};
		return db.update(sql, obj);
		
	}

}
