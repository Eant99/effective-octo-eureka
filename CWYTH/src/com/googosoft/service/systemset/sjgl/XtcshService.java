package com.googosoft.service.systemset.sjgl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.sjgl.XtcshDao;
import com.googosoft.service.base.BaseService;


@Service("xtcshService")
public class XtcshService extends BaseService{
	
	@Resource(name="xtcshDao")
	public XtcshDao xtcshDao;
	/**
	 * 系统初始化
	 */
	public boolean doDeleteAll() {
		boolean result = xtcshDao.doDeleteAll();
		if(result){
			doAddOplog(OplogFlag.CSH,"系统初始化","");
		}
		return result;
	 }
}
