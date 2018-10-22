package com.googosoft.service.systemset.aqgl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.aqgl.OplogDao;
import com.googosoft.pojo.systemset.aqgl.ZC_SYS_OPLOG;
import com.googosoft.service.base.BaseService;

@Service("oplogService")
public class OplogService extends BaseService{
	
	@Resource(name="oplogDao")
	public OplogDao oplogDao;
	/**
	 * 操作日志信息清空
	 */
	public int doDeleteAll() {
		int result = oplogDao.doDeleteAll();
		if(result >=0){
			doAddOplog(OplogFlag.DELALL,"操作日志管理","");
		}
		   return result;
	}
	
	/**
	 * 操作日志新增
	 */
	
	public int doAdd(ZC_SYS_OPLOG rzb) {
		return  oplogDao.doAdd(rzb);
		
	}
}


