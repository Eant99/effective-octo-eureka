package com.googosoft.service.ysgl.xmsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.ysgl.xmsz.Xmxx2Dao;
import com.googosoft.dao.ysgl.xmsz.XmxxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("xmxx2Service")
public class Xmxx2Service extends BaseService{

	@Resource(name="xmxx2Dao")
	private Xmxx2Dao xmxxDao;
	
	/**
	 * 删除
	 * @param pd
	 * @return 
	 */
	@Transactional
	public int doDelete(PageData pd) {
		
		xmxxDao.doDelete(pd);
	
		return 1;
		
	}
	/**
	 * 修改
	 * @param pd   
	 * @return 
	 */
	public int updateXmxx(PageData pd) {
		return xmxxDao.updateXmxx(pd);
	}
	public int getObjectById(PageData pd ) {
		return xmxxDao.getObjectById(pd);
	}
	
	public int deleteXmxx(PageData pd) {
		return xmxxDao.deleteXmxx(pd);
	}
	public boolean checkXmbhExist(PageData pd) {
		return xmxxDao.checkXmbhExist(pd);
	}
	public int doAdd(PageData pd) {
		return xmxxDao.doAdd(pd);
	}
	public List getXmfl(){
		return xmxxDao.getXmfl();
	}
	public Map<?, ?> getObjectById(String guid) {
		return xmxxDao.getObjectById(guid);
	}
	
}
