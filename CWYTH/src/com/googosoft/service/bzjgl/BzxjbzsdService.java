package com.googosoft.service.bzjgl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.bzjgl.BzxjbzsdDao;
import com.googosoft.dao.cwbb.CzbzsrzcDao;
import com.googosoft.pojo.bzjgl.CW_BZBZSZB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

/**
 * 
 * @author lishuo
 * @date 2018-3-23
 */
@Service("bzxjbzsdService")
public class BzxjbzsdService extends BaseService {
	
	@Resource(name="bzxjbzsdDao")
	private BzxjbzsdDao bzxjbzsdDao;
	/**
	 * 查询
	 * @param bzbh  
	 * @return 
	 */
	public Map<?, ?> getObjectById(String dwbh) {
		return bzxjbzsdDao.getObjectById(dwbh);
	}
	/**
	 * 修改
	 * @param bzbh  
	 * @return 
	 */
	public int doUpdate(CW_BZBZSZB bzbz) {
		return bzxjbzsdDao.doUpdate(bzbz);
	}
	/**
	 * 添加
	 * @param bzbh  
	 * @return 
	 */
	public int doAdd(CW_BZBZSZB bzbz) {
		return bzxjbzsdDao.doAdd(bzbz);
	}
	/**
	 * 删除
	 * @param bzbh  
	 * @return 
	 */
	public int doDelete(String bzbh) {
		return bzxjbzsdDao.doDelete(bzbh);
	}
	/**
	 * 批量删除
	 * @param bzbh  
	 * @return 
	 */
	public int doDelete2(String bzbh) {
		return bzxjbzsdDao.doDelete2(bzbh);
	}


}
