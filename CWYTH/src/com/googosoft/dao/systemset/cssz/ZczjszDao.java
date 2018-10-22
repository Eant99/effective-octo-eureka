package com.googosoft.dao.systemset.cssz;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB;

@Repository("zczjszDao")
public class ZczjszDao extends BaseDao{
	/**
	 * 获取资产折旧设置
	 */
	public Map getZczj() {
		return db.queryForMap("select sfqy,qyny,zjff from zc_sys_xtb ");
	}
	
	/**
	 * 保存资产折旧设置
	 * @param obj
	 * @return 受影响条数
	 */
	public int doSave(ZC_SYS_XTB obj){
		return db.update("update zc_sys_xtb set (sfqy,qyny,zjff) = (select ?,to_date(?,'yyyy-mm'),? from dual) where rownum = 1", new Object[]{obj.getSfqy(),obj.getQyny(),obj.getZjff()});
	}
	
}
