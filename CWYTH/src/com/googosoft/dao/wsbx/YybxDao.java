package com.googosoft.dao.wsbx;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

@Repository("yybxDao")
public class YybxDao extends BaseDao{

	public List<Map<String, Object>> getTime(){
		return db.queryForList("SELECT KSSJ,JSSJ,YXYYRS FROM CW_YYSZMXB T", new Object[]{});
	}
}
