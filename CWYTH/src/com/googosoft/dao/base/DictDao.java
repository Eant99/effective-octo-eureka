package com.googosoft.dao.base;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.util.CommonUtils;

@Repository("dictDao")
public class DictDao extends BaseDao{

	/**
	 * 从数据字典中获取下拉框的内容
	 * @param zl
	 * @return
	 */
	public List<Map<String, Object>> getDict(String zl){
		return db.queryForList("select dm,mc,ms from gx_sys_dmb where zl = ? order by dm", new Object[]{zl});
	}
	
	public List<Map<String, Object>> getKyDict(String zl){
		return db.queryForList("select dm,mc from ky_dmb where zl = ? order by dm", new Object[]{zl});
	}
	
	public List<Map<String, Object>> getJcwjlx(String zl){
		return db.queryForList("select dm,mc from gx_sys_dmb where zl = ? order by dm", new Object[]{zl});
	}
	
	public List<Map<String, Object>> getDictBySplit(String zl,String split){
		return db.queryForList("select dm,dm||'" + split + "'||mc mc from gx_sys_dmb where zl = ? order by dm", new Object[]{zl});
	}

	public List<Map<String, Object>> getDictByDm(String zl,String dm){
		String sql = "select dm,mc from gx_sys_dmb where zl = '" + zl + "' and dm " + CommonUtils.getInsql(dm) + " order by dm";
		return db.queryForList(sql, dm.split(","));
	}
	
	public String getMcByDm(String zl,String dm){
		return db.queryForSingleValue("select dm||'-'||mc from gx_sys_dmb where zl = ? and dm = ?", new Object[]{zl,dm});
	}
	
	public String getDmByMc(String zl,String mc){
		return db.queryForSingleValue("select dm from gx_sys_dmb where zl = ? and mc = ?", new Object[]{zl,mc});
	}

	public String getDictMc(String xZ, String mc) {
		return db.queryForSingleValue("select mc from gx_sys_dmb where zl = ? and dm=? ", new Object[]{xZ,mc});
	}

	public String getZjzt() {
		return db.queryForSingleValue("select sfqy from zc_sys_xtb ");
	}
}
