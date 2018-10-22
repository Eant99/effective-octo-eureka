package com.googosoft.service.pjgl.rcyw;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.pjgl.rcyw.PjlbDao;
import com.googosoft.util.PageData;
@Service
public class PjlbService {

	@Resource(name="pjlbDao")
	private PjlbDao pjlbDao;
	
	public List getPjlbListById(String pjzh) {
		return pjlbDao.getPjlbListById(pjzh);
	}

	
	public Map getPjytMapById(String guid) {
		return pjlbDao.getPjytMapById(guid);
	}


	public int editPjlx(PageData pd) {
		return pjlbDao.editPjlx(pd);
	}
	public int editPjZf(String gid){
		return pjlbDao.editPjZf(gid);
	}
	public int editPjBx(String gid){
		return pjlbDao.editBx(gid);
	}


	public String getPjlxById(String guid) {
		return pjlbDao.getPjlxById(guid);
	}


	public int doDelete(String gid) {
		return pjlbDao.doDelete(gid);
	}
	public int doSavePtfp(PageData pd) {
		return pjlbDao.doSavePtfp(pd);
	}
	public int doSaveSjfp(PageData pd) {
		return pjlbDao.doSaveSjfp(pd);
	}
	public int doSaveZyfp(PageData pd) {
		return pjlbDao.doSaveZyfp(pd);
	}
	public int doSaveZzsfp(PageData pd) {
		return pjlbDao.doSaveZzsfp(pd);
	}


	public Map getPjxxById(String id) {
		return pjlbDao.getPjxxById(id);
	}


	public List getPjytList() {
		return pjlbDao.getPjytList();
	}
}
