package com.googosoft.service.zdscpz;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.Zdscpz.ZdscpzDao;
import com.googosoft.pojo.Zdscpz.Zdscpz;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

/**
 * 凭证自动化service
 * @author googosoft
 *
 */
@Service("zdscpzService")
public class ZdscpzService extends BaseService{
	@Resource(name="zdscpzDao")
	private ZdscpzDao zdscpzDao;
	
	/**
	 * 凭证自动化信息查询
	 * @return
	 */
	public Map<String, Object> getByGid() {
		return zdscpzDao.getByGid();
	}
	
	public Map<String, Object> getGjc() {
		return zdscpzDao.getGjc();
	}
	public Map<String, Object> getCzc() {
		return zdscpzDao.getCzc();
	}
	public Map<String, Object> getCtqc() {
		return zdscpzDao.getCtqc();
	}
	public Map<String, Object> getHc() {
		return zdscpzDao.getHc();
	}
	public Map<String, Object> getGt1() {
		return zdscpzDao.getGt1();
	}
	public Map<String, Object> getLc() {
		return zdscpzDao.getLc();
	}
	public Map<String, Object> getFj() {
		return zdscpzDao.getFj();
	}
	public Map<String, Object> getPzsc() {
		return zdscpzDao.getPzsc();
	}
		
	/**
	 * 凭证自动化保存修改
	 * @param dto
	 * @return
	 */
	public int doSave(Zdscpz dto) {
		return zdscpzDao.doSave(dto);
	}
	
	public int doSave5(PageData pd) {
		return zdscpzDao.doSave5(pd);
	}
	 /**
     * 借款科目
     * @return
     */
	public Map getJkkm() {
		return zdscpzDao.getJkkm();
	}
	/**
     * 薪资项目
     * @return
     */
	public Map getXzxm(String sszt) {
		return zdscpzDao.getXzxm(sszt);
	}

	public Map getXxjjhj() {
		return zdscpzDao.getXxjjhj();
	}

	public Map getXxjjhd() {
		return zdscpzDao.getXxjjhd();
	}

	public Map getXxjjzj() {
		return zdscpzDao.getXxjjzj();
	}

	public Map getXxjjzd() {
		return zdscpzDao.getXxjjzd();
	}

	public Map getXyjjj() {
		return zdscpzDao.getXyjjj();
	}

	public Map getXyjjd() {
		return zdscpzDao.getXyjjd();
	}

	public Map getGlf() {
		return zdscpzDao.getGlf();
	}

	public Map getXxxm() {
		return zdscpzDao.getXxxm();
	}

	public Map getXyxm() {
		return zdscpzDao.getXyxm();
	}
}
