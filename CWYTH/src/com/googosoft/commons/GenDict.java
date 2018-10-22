package com.googosoft.commons;

import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.service.base.DictService;
/**
 * 数据字典处理类
 * @author Administrator
 *
 */
@Component
public class GenDict {
	
	/*@Resource(name="jdbcTemplate")
	private DBHelper cacheDao;

	private static DBHelper dao;
	
	@PostConstruct
	public void init(){
		this.dao = cacheDao;
	}*/
	
	private static DictService dictService;
	@Resource
	public void setDictService(DictService dictService) {
	    GenDict.dictService = dictService;
	}
	public static List<Map<String, Object>> getDict(String zl){
		return dictService.getDict(zl);
	}
	
	
	
}
