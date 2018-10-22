package com.googosoft.util;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.googosoft.dao.base.DBHelper;
/**
 * 数据字典处理类
 * @author Administrator
 *
 */
@Component
public class Dict {
	
	@Resource(name="jdbcTemplate")
	private DBHelper cacheDao;
	
	private static DBHelper dao;
	
	@PostConstruct
	public void init(){
		this.dao = cacheDao;
	}
	
	public static List getDict(String zl){
		return dao.queryForList("SELECT DM,MC FROM GX_SYS_DMB WHERE ZL=? ORDER BY DM", new Object[]{zl});
	}
}
