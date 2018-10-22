package com.googosoft.service.kjhs.bbzx;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.bbzx.XmmxzDao;
import com.googosoft.service.base.BaseService;

@Service("xmmxzService")
public class XmmxzService extends BaseService{

	@Autowired
	XmmxzDao xmmxzDao;
	
	
}
