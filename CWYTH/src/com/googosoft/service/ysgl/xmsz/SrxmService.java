package com.googosoft.service.ysgl.xmsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.ysgl.xmsz.SrxmDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("srxmService")
public class SrxmService extends BaseService{

	@Resource(name="srxmDao")
	private SrxmDao srxmDao;
	
	public List<Map<String, Object>> getSrxmList(){
		return srxmDao.selectSrxmList();
	}
	public Map<String, Object> getSrxmMapById(String guid){
		return srxmDao.selectSrxmMapById(guid);
	}
	public int editSrxm(PageData pd) {
		return srxmDao.updateSrxm(pd);
	}
	public int addSrxm(PageData pd) {
		return srxmDao.insertSrxm(pd);
	}
	public int deleteSrxm(PageData pd) {
		return srxmDao.deleteSrxm(pd);
	}
	public boolean checkSrxmbhExist(PageData pd) {
		return srxmDao.checkSrxmbhExist(pd);
	}
}
