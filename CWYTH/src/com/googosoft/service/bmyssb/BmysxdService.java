package com.googosoft.service.bmyssb;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.bmyssb.BmysxdDao;
import com.googosoft.pojo.ysgl.CW_BMYSXDB;
import com.googosoft.service.base.BaseService;

@Service("bmysxdService")
public class BmysxdService extends BaseService{
	@Resource(name="bmysxdDao")
	private BmysxdDao bmysxdDao;
	/**
	 * 先删除
	 * @param 
	 * @return
	 */
	public int doDeleteAll(String guid ) {
		int result = bmysxdDao.doDeleteAll(guid);
		return result;
	}
	/**
	 * 保存
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd(CW_BMYSXDB bmysxd) {
		
		int i = bmysxdDao.doAdd(bmysxd);
		
		return i;	
    }
	
	/**
	 * 保存下达
	 * @param params
	 * @return
	 */
	@Transactional
	public boolean doSaveXd(String[] params,String sszt){
		return bmysxdDao.doSaveXd(params, sszt);
	}
}
