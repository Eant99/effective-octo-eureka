package com.googosoft.dao.base;

import java.util.List;

import org.springframework.stereotype.Repository;

@Repository("provincesDao")
public class ProvincesDao  extends BaseDao{

	public List getProvicesList(){
		return db.queryForList("select provinceid, province from cw_provinces ");
	}
	
	public List getCitiesByProvince(String provinceid){
		return db.queryForList("select provinceid, cityid, city from cw_cities where provinceid = ? ", new Object[]{provinceid});
	}
}
