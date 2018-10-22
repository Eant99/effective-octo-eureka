package com.googosoft.service.pjgl.rcyw;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.pjgl.rcyw.PjdyDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("pjdyService")
public class PjdyService  extends BaseService{
	@Resource(name="pjdyDao")
	private PjdyDao pjdyDao;
    /**
     *  获取   增值税专用发票  信息
     * @param gid
     * @return
     */
	public Map getZzsXX(String gid) {
		return pjdyDao.getZzsXX(gid);
	}
	 /**
     *  获取   收据  发票  信息
     * @param gid
     * @return
     */
	public Map getSjXX(String gid) {
		return pjdyDao.getSjXX(gid);
	}
	/**
     *  获取   普通  发票  信息
     * @param gid
     * @return
     */
	public Map getPtfpXX(String gid) {
		return pjdyDao.getPtfpXX(gid);
	}
	/**
     *  获取   专用支票   信息
     * @param gid
     * @return
     */
	public Map getZyzpXX(String gid) {
		return pjdyDao.getZyzpXX(gid);
	}
	/**
	 * 保存
	 * @param pd
	 * @return
	 */
	public int doSaveSj(PageData pd) {
		return pjdyDao.doSaveSj(pd);
	}
	public int doSavePtfp(PageData pd) {
		return pjdyDao.doSavePtfp(pd);
	}
	public int doSaveZyfp(PageData pd) {
		return pjdyDao.doSaveZyfp(pd);
	}
	public int doSaveZzs(PageData pd) {
		return pjdyDao.doSaveZzs(pd);
	}

}
