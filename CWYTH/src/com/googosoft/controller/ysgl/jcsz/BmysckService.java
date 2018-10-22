package com.googosoft.controller.ysgl.jcsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.ysgl.xmsz.XmxxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("bmysckService")
public class BmysckService extends BaseService{
	@Resource(name="bmysckDao")
	private BmysckDao bmysckDao;
	
	public Map<?, ?> getObjectById(String guid) {
		return bmysckDao.getObjectById(guid);
	}
	
	
	
	
	
}
