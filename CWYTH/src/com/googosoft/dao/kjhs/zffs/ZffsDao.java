package com.googosoft.dao.kjhs.zffs;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
@Repository("zffsDao")
public class ZffsDao extends BaseDao {
	public List<Map<String, Object>> getZffs(){
		return db.queryForList("select guid,zfbm,zfmc from CW_ZFFSSZZB where 1=1", new Object[]{});
	}

}
