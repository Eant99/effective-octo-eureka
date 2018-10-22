package com.googosoft.service.kjhs;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.cyzyszDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("cyzyszService")
public class CyzyszService extends BaseService{
	@Resource(name="cyzyszDao")
	public cyzyszDao cyzyszDao;
	
	/**
	 * 判断摘要是否重复（增加）
	 * @param pd
	 * @return
	 */
	public int getZyById(PageData pd ) {
		return cyzyszDao.getZyById(pd);
	}
	
	/**
	 * 判断摘要是否重复（编辑）
	 * @param pd
	 * @return
	 */
	public int getUpdZyById(PageData pd ) {
		return cyzyszDao.getUpdZyById(pd);
	}
	
	/**
	 * 保存摘要
	 * @param pd
	 * @return
	 */
	public int doAdd(PageData pd,String kjzd,String sszt) {
		return cyzyszDao.doAdd(pd,kjzd,sszt);
	}
	
	/**
	 * 编辑保存凭证类型
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int updCyzysz(PageData pd,String guid) {
		return cyzyszDao.updCyzysz(pd,guid);
	}
	
	/**
	 * 获取摘要详细信息
	 * @param guid 
	 * @return
	 */
	public Map getMapByGuid(String guid){
		return cyzyszDao.getMapByGuid(guid);
	}
	
	/**
	 * 删除摘要信息
	 * @param guid
	 * @return
	 */
	public int delete(String guid) {
		return cyzyszDao.delete(guid);
	}

}
