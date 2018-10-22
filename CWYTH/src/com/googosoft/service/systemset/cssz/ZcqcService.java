package com.googosoft.service.systemset.cssz;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.systemset.cssz.ZcqcDao;
import com.googosoft.service.base.BaseService;

@Service("zcqcService")
public class ZcqcService extends BaseService{
	@Resource(name="zcqcDao")
	private ZcqcDao zcqcDao;
	
	/**
	 * 添加资产清查新过程
	 */
	@Transactional
	public int doSave(String flag,String qcbh) {
		return zcqcDao.doSave(flag,qcbh);
	}
	/**
	 * 开始清查
	 */
	@Transactional
	public int doStart(String flag,String qcbh) {
		return zcqcDao.doStart(flag,qcbh);
	}
	/**
	 * 结束清查
	 */
	@Transactional
	public int doEnd(String flag,String qcbh) {
		return zcqcDao.doEnd(flag,qcbh);
	}
	/**
	 * 	添加新过程，判断是否存在已添加的，或者正在进行的数据，有，不进行添加
	 */
	public int doCheck(String flag) {
		return zcqcDao.doCheck(flag);
	}
}
