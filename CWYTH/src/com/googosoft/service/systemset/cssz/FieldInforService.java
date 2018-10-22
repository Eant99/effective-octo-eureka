package com.googosoft.service.systemset.cssz;

import java.util.List;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.FieldInforDao;
import com.googosoft.service.base.BaseService;

@Service("fieldInforService")
public class FieldInforService extends BaseService{
	
	@Resource(name="fieldInforDao")
	public FieldInforDao fieldInforDao;
	
	/**
	 * 验收单必填项保存
	 */	
	public boolean doSave(String selectItems) {
		boolean result = fieldInforDao.doSave(selectItems);
		if(result){
			doAddOplog(OplogFlag.UPD,"验收单必填项设置","");
		}
		return result;
	}
	/**
	 * 获取必填项信息
	 * @return
	 */
	public List getPageList(){
		return fieldInforDao.getPageList();
	}
	/**
	 * 获取必填项信息
	 * @return
	 */
	public List getPageList(String modetype,String tablename,String flh){
		return fieldInforDao.getPageList(modetype,tablename,flh);
	}
//	public boolean red(String string,String flh) {
//		return fieldInforDao.red(string,flh);
//	}
}

