package com.googosoft.dao.cbgl.cbys;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("cbysDao")
public class CbysDao extends BaseDao {
	
	/**
	 * 成本要素信息
	 * @param cbys
	 * @return
	 */
	public Map<String, Object> getObjectByIdByKmsz(String guid) {
		String sql = "select guid,kmbh,kmmc,yslx,l,k,kmjc,qyf,sm from CW_JJKMB where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 获取经济科目字典树
	 */
	public List jjszMenuzj(String dm) {
		String sql = "";
		if(Validate.noNull(dm)){
			sql = "select t.guid,t.kmnd,t.kmbh,t.kmmc,t.l,t.k,T.KMJC from cw_jjkmb t WHERE T.k='302'  and t.qyf='1' ";
		}else{
			sql = "select * from cw_jjkmb t where t.kmmc='商品服务支出'  ";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public int getCount(String kmbh) {
		String sql = "select count(0) from cw_jjkmb t WHERE T.k='"+kmbh+"' and t.qyf='1' ";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0"));
		return count;
	}
	/**
	 * 删除
	 * @param guid
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String DELSQL = "DELETE FROM CW_JJKMB WHERE GUID " + wstr;
		DELSQL = String.format(DELSQL, SystemSet.gxBz);
		int result = 0;
		try {
			result = db.update(DELSQL, params);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			
			//logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();// 事务回滚
		}
		return result;
	}
	public int getCountByKmbh(String kmbh) {
		String sql = "select count(0) from CW_JJKMB where k='"+kmbh+"'";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), ""));
		return count;
	}
}