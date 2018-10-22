package com.googosoft.dao.base;

import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

@Repository("baseDao")
public class BaseDao {
	@Resource(name="jdbcTemplate")
	public DBHelper db;
}
