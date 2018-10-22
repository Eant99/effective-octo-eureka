package com.googosoft.service.xzgl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.xzgl.XzshDao;
import com.googosoft.service.base.BaseService;


@Service("xzshService")
public class XzshService extends BaseService{

	@Resource(name="xzshDao")
	private XzshDao xzshDao;

	/**
	 * 在职薪资审核通过
	 */
	public int doZztg(String guid, String where,String gzyf) {
		return xzshDao.doZztg(guid, where, gzyf);
	}

	/**
	 * 在职薪资审核退回
	 */
	public int doZzth(String guid, String where) {
		return xzshDao.doZzth(guid, where);
	}

	/**
	 * 离职薪资审核通过
	 */
	public int doTxtg(String guid, String where,String gzyf) {
		return xzshDao.doTxtg(guid, where,gzyf);
	}

	/**
	 * 离职薪资审核退回
	 */
	public int doTxth(String guid, String where) {
		return xzshDao.doTxth(guid, where);
	}
	public String getLastMon(){
		return xzshDao.getLastMon();
	}
	public String getLastMonLz(){
		return xzshDao.getLastMonLz();
	}
}
