package com.googosoft.service.ysgl.yszx;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.ysgl.yszx.YszxDao;
import com.googosoft.service.base.BaseService;

@Service("yszxService")
public class YszxService extends BaseService{

	@Resource(name="yszxDao")
	private YszxDao yszxDao;
	
}
