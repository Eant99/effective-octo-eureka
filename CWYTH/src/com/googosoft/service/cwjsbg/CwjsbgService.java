package com.googosoft.service.cwjsbg;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.cwjsbg.CwjsbgDao;
import com.googosoft.service.base.BaseService;

@Service("cwjsbgservice")
public class CwjsbgService extends BaseService{
	@Resource(name="cwjsbgdao")
	private CwjsbgDao cwjsbgdao;
}
