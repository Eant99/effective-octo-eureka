package com.googosoft.dao.common;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
@Repository("deptDao")
public class DwbDao extends BaseDao{
	
	/**
	 * 获取单位信息
	 * @param bmh
	 * @return
	 */
	public String getKeyId(String bmh){
		String sql = "select dwbh from gx_sys_dwb where bmh = ?";
		return db.queryForSingleValue(sql, new Object[]{bmh});
	}
	
	/**
	 * 检测是否有单位权限
	 * @param bmh
	 * @return
	 */
	public String doCheckRyDWQX(String rybh,String dwbh){
		String sql ="select count(*) from "+TnameU.RYDWQXB+" z left join gx_sys_dwb d on z.dwbh=d.dwbh where z.rybh =? and z.dwbh=? and d.dwzt='1' ";
		return db.queryForSingleValue(sql, new Object[]{rybh,dwbh});
	}
	
}
