package com.googosoft.service.systemset.cssz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.ZjcshDao;
import com.googosoft.service.base.BaseService;
/**折旧初始化
 * @author sxl
 *
 */
@Service("zjcshService")
public class ZjcshService extends BaseService {
	@Resource(name="zjcshDao")
	public ZjcshDao zjcshDao;
	/**
	 * 折旧初始化
	 */
	public boolean doDeleteAll() {
		boolean b = zjcshDao.doDeleteAll();
		if(b){
			doAddOplog(OplogFlag.CSH,"折旧初始化","");
		}
		return b;
	 }
}
