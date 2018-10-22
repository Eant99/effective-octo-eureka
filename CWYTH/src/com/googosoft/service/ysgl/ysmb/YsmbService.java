package com.googosoft.service.ysgl.ysmb;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.ysgl.ysmb.YsmbDao;
import com.googosoft.service.base.BaseService;

@Service("ysmbService")
public class YsmbService extends BaseService{

	@Resource(name="ysmbDao")
	private YsmbDao ysmbDao;
	
	public Map<String, Object> getYsmbByGuid(String guid) {
		return ysmbDao.getYsmbByGuid(guid);
	}
}
