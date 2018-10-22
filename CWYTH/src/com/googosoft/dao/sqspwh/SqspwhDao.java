package com.googosoft.dao.sqspwh;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

/**
 * 事前审批维护dao
 * @author googosoft
 *
 */
@Repository("sqspwhDao")
public class SqspwhDao extends BaseDao{
	
	public List<String> getSqsplxList(){
		String sql = "select sqsplx from cw_sqspwhb";
		return db.queryForList(sql, String.class);
	}
	
	/**
	 * 事前审批维护保存
	 * @param mkbh
	 * @param sfqy
	 * @param sftzxz
	 * @return
	 */
	public int insertSqspwh(String mkbh,String sfqy,String sftzxz) {
		String sql = "insert into cw_sqspwhb (guid,sqsplx,sfqy,sftzxz) values (sys_guid(),?,?,?)";
		Object[] obj = {mkbh,sfqy,sftzxz};
		return db.update(sql,obj);
	}
	
	/**
	 * 平台系统设置：事前审批维护保存
	 * @param mkbh
	 * @param sfqy
	 * @param sftzxz
	 * @return
	 */
	public int updateSqspwh(String mkbh,String sfqy,String sftzxz) {
		String sql = "update cw_sqspwhb set sfqy = ?,sftzxz = ? where sqsplx = ?";
		Object[] obj = {sfqy,sftzxz,mkbh};
		return db.update(sql,obj);
	}
	/**
	 * 更新系统模块表中的模块权限
	 * @return
	 */
	public int updateSysmk(String mkbh,String sfqy) {
		String sql = "update zc_sys_mkb set qxbz = '"+sfqy+"' where mkbh = '"+mkbh+"'";
		return db.update(sql);
	}
	
	public String querySftzxz(String mkbh) {
		String sql = "select sftzxz from cw_sqspwhb where sqsplx = '"+mkbh+"'";
		return db.queryForSingleValue(sql);
	}
	//查询是否存在未审批通过的事前审批单据
	public String querySqspSfsy(String tableName,String shzt) {
		String sql = "select count(1) from "+tableName+" where shzt <> '"+shzt+"'";
		return db.queryForSingleValue(sql);
	}
	
}





















