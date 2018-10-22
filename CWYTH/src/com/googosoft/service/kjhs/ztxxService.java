package com.googosoft.service.kjhs;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.ztxxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("ztxxService")
public class ztxxService extends BaseService{

	@Resource(name="ztxxDao")
	private ztxxDao ztxxDao;
	
	public List<Map<String, Object>> getSrxmList(){
		return ztxxDao.selectSrxmList();
	}
	//获取账套信息
	public Map<String, Object> getztxxMapById(String guid){
		return ztxxDao.selectztxxMapById(guid);
	}
	public int editSrxm(PageData pd) {
		return ztxxDao.updateSrxm(pd);
	}
	public int addSrxm(PageData pd) {
		return ztxxDao.insertSrxm(pd);
	}
	//删除账套信息
	public int deleteztxx(PageData pd) {
		return ztxxDao.deleteztxx(pd);
	}
	public boolean checkSrxmbhExist(PageData pd) {
		return ztxxDao.checkSrxmbhExist(pd);
	}
	/**
	 * 判断账套名称是否存在
	 * @param dwbh
	 * @return
	 */
	public boolean getObjectById(String ztmc,String guid){
		return ztxxDao.getObjectById(ztmc,guid);
	}
	/**
	 * 新增账套信息
	 * */
	public boolean doAdd(PageData pd,String guid){
		return  ztxxDao.doAdd(pd,guid);
	}
	
	public int doUpdate(PageData pd) {
		
		return ztxxDao.doUpdate(pd);
	}
	
	
}
