package com.googosoft.service.ysgl.ysxd;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.ysgl.ysxd.YsxdDao;
import com.googosoft.service.base.BaseService;

@Service("ysxdService")
public class YsxdService extends BaseService{

	@Resource(name="ysxdDao")
	private YsxdDao ysxdDao;
	
}
