package com.googosoft.dao.fzgn.wxzf;
import java.sql.SQLException;
import java.util.Map;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.wxzf.CW_CBSXX;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;

/**
 * 承包商信息维护dao
 * @author googosoft
 *
 */
@Repository("cbsglDao")
public class CbsglDao extends BaseDao{
	private Logger logger = Logger.getLogger(CbsglDao.class);
	public Map<String, Object> getObjectById(String guid) {
		String sql = "select A.GUID,A.CBSBH,A.CBSMC,A.CBSWXH,A.LXR,A.LXDH,TO_CHAR(A.CBKSRQ,'YYYY-MM-DD')CBKSRQ,TO_CHAR(A.CBJSRQ,'YYYY-MM-DD')CBJSRQ,A.BZ,a.sftj FROM CW_CBSXX A WHERE A.GUID='"+guid+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 判断承包商编号是否已经存在
	 * @param cbsbh
	 * @return
	 */
	public boolean checkCbsbh(String cbsbh) {
		String sql = "SELECT COUNT(1) FROM CW_CBSXX WHERE  CBSBH = ?";
		sql = String.format(sql);
		Integer result = db.queryForObject(sql, new Object[] { cbsbh },
				Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 新增承包商信息
	 */
	public int doAdd(CW_CBSXX cbsxx) {		
		String sql = "INSERT INTO CW_CBSXX(GUID,CBSBH,CBSMC,CBSWXH,LXR,LXDH,CBKSRQ,CBJSRQ,BZ,sftj)" 				
			     + "VALUES(?,?,?,?,?,?,?,?,?,?) ";		
		Object[] obj = new Object[] {cbsxx.getGuid(),cbsxx.getCbsbh(),cbsxx.getCbsmc(),cbsxx.getCbswxh(),cbsxx.getLxr(),cbsxx.getLxdh(),
				                    cbsxx.getCbksrq(),cbsxx.getCbjsrq(),cbsxx.getBz(),cbsxx.getSftj()};
		return db.update(sql, obj);
	}
	/**
	 * 删除承包商信息
	 * @param guid
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String sql = "DELETE CW_CBSXX WHERE GUID " + wstr;		
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
	 * 修改承包商信息
	 * @param cbsxx
	 * @return
	 */
	public int doUpp(CW_CBSXX cbsxx) {		
		String sql = "update CW_CBSXX set CBSBH=?,CBSMC=?,CBSWXH=?,LXR=?,LXDH=?,CBKSRQ=?,CBJSRQ=?,BZ=?,sftj=? where GUID=?" ;		
			    		
		Object[] obj = new Object[] {cbsxx.getCbsbh(),cbsxx.getCbsmc(),cbsxx.getCbswxh(),cbsxx.getLxr(),cbsxx.getLxdh(),
				                    cbsxx.getCbksrq(),cbsxx.getCbjsrq(),cbsxx.getBz(),cbsxx.getSftj(),cbsxx.getGuid()};
		return db.update(sql, obj);
	}

}
