package com.googosoft.service.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.googosoft.dao.base.DictDao;


/**
 * 数据字典信息service
 * @author master
 */
@Service("dictService")
public class DictService extends BaseService{

	@Resource(name="dictDao")
	public DictDao dictDao;
	
	/**
	 * 从数据字典中获取下拉框的内容
	 * @param zl
	 * @return
	 */
	public List<Map<String, Object>> getDict(String zl) {
		return dictDao.getDict(zl);
	}
	
	public List<Map<String, Object>> getKYDict(String zl) {
		return dictDao.getKyDict(zl);
	}
	
	public List<Map<String, Object>> getJcwjlx(String zl) {
		return dictDao.getJcwjlx(zl);
	}

	public List<Map<String, Object>> getDictBySplit(String zl){
		return getDictBySplit(zl,"-");
	}

	public List<Map<String, Object>> getDictBySplit(String zl,String split){
		return dictDao.getDictBySplit(zl,split);
	}

	public List<Map<String, Object>> getDictByDm(String zl,String dm) {
		return dictDao.getDictByDm(zl,dm);
	}
	
	public String getMcByDm(String zl,String dm){
		return dictDao.getMcByDm(zl, dm);
	}
	
	public String getDmByMc(String zl,String mc){
		return dictDao.getDmByMc(zl, mc);
	}

	public String getDictMc(String xZ, String mc) {
		return dictDao.getDictMc(xZ, mc);
	}

	public String getZjzt() {
		return dictDao.getZjzt();
	}
}
