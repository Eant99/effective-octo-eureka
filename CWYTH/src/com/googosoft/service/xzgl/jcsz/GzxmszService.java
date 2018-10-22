package com.googosoft.service.xzgl.jcsz;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.xzgl.jcsz.GzcsszDao;
import com.googosoft.dao.xzgl.jcsz.GzxmszDao;
import com.googosoft.service.base.BaseService;
@Repository("gzxmszService")
public class GzxmszService extends BaseService{
	@Resource(name="gzxmszDao")
	private GzxmszDao gzxmszDao;

	public boolean doSave(List list) {
			return gzxmszDao.doSave(list);
	}

}
