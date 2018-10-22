package com.googosoft.service.systemset.cssz;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.systemset.cssz.ZczjszDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB;
import com.googosoft.service.base.BaseService;

@Service("zczjszService")
public class ZczjszService extends BaseService{
	@Resource(name="zczjszDao")
	public ZczjszDao zczjszDao;

	public Map getZczj() {
		return zczjszDao.getZczj();
	}

	public int doSave(ZC_SYS_XTB obj) {
		return zczjszDao.doSave(obj);
	}
}
