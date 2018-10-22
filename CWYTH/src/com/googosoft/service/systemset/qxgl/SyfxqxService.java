package com.googosoft.service.systemset.qxgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.systemset.qxgl.SyfxqxDao;
import com.googosoft.service.base.BaseService;
/**
 * 操作权限信息service
 * @author master
 */
@Service("syfxqxService")
public class SyfxqxService extends BaseService{
	
	@Resource(name="syfxqxDao")
	public SyfxqxDao syfxqxDao;

	public List<Map<String, Object>> getSyfxList(String rybh, String syfx) {
		// TODO Auto-generated method stub
		return syfxqxDao.getSyfxList(rybh,syfx);
	}

	public boolean doSave(String syfx, String rybh) {
		// TODO Auto-generated method stub
		return syfxqxDao.doSave(syfx, rybh);
	}

}
