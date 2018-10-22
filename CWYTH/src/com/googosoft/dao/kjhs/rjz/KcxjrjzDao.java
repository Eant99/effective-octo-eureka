package com.googosoft.dao.kjhs.rjz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("kcxjrjzDao")
public class KcxjrjzDao extends BaseDao {
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		String sql = "SELECT DISTINCT KMJC AS JB FROM CW_KJKMSZB WHERE 1=1 AND KMJC<>'root' ORDER BY TO_NUMBER(KMJC)";
		List list = db.queryForList(sql);
		return list;
	}
	
	public  boolean runPro(String proName,List list) throws SQLException{
		try {
			super.db.batchUpdateByProcedure(proName,list);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		String sql = "delete from cwpt_kcxjrjz where login='" + LUser.getGuid()+ "'";
		db.execute(sql);
	}
	/**
	 * 获取列表数据
	 * @param sql
	 * @return
	 */
	public List<Map<String, Object>> getPageList(String sql) {
		return db.queryForList(sql);
	}
	
}
