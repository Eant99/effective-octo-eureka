package com.googosoft.service.xzgl.jcsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.xzgl.jcsz.GzcsszDao;
import com.googosoft.dao.ysgl.xmsz.XmxxDao;
import com.googosoft.service.base.BaseService;
@Repository("gzcsszService")
public class GzcsszService extends BaseService{
	@Resource(name="gzcsszDao")
	private GzcsszDao gzcsszDao;
	
	public Map<String, Object> getGzcsMap() {
		return gzcsszDao.getGzcsMap();
	}

	public boolean doSave(List list) {
		return gzcsszDao.doSave(list);
	}

}
