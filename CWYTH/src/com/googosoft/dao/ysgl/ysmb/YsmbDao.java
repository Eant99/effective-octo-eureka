
package com.googosoft.dao.ysgl.ysmb;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

@Repository("ysmbDao")
public class YsmbDao extends BaseDao{
	
	
	public Map<String, Object> getYsmbByGuid(String guid) {
		return db.queryForMap("select * from cw_ysmbb where guid = " + guid);
	}
}
