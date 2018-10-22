package com.googosoft.service.kjhs;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.kjhs.PzlxnewDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("pzlxnewService")
public class PzlxNewService extends BaseService{
	@Resource(name="pzlxnewDao")
	public PzlxnewDao pzlxnewDao;
	
	
	/**
	 * 判断凭证类型编号是否重复（增加）
	 * @param pd
	 * @return
	 */
	public int getBhById(PageData pd ) {
		return pzlxnewDao.getBhById(pd);
	}
	/**
	 * 判断序号是否重复（编辑）
	 * @param pd
	 * @return
	 */
	public int getUpdXhById(PageData pd ) {
		return pzlxnewDao.getUpdXhById(pd);
	}
	
	/**
	 * 判断凭证类型编号是否重复（编辑）
	 * @param pd
	 * @return
	 */
	public int getUpdBhById(PageData pd ) {
		return pzlxnewDao.getUpdBhById(pd);
	}
	
	/**
	 * 保存凭证类型
	 * @param pd
	 * @return
	 */
	public int doAdd(PageData pd) {
		return pzlxnewDao.doAdd(pd);
	}
	
	/**
	 * 删除凭证类型信息
	 * @param guid
	 * @return
	 */
	public int delete(String guid) {
		return pzlxnewDao.delete(guid);
	}
	
	/**
	 * 获取凭证类型详细信息
	 * @param guid 
	 * @return
	 */
	public Map getMapByGuid(String guid){
		return pzlxnewDao.getMapByGuid(guid);
	}
	
	/**
	 * 编辑保存凭证类型
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int updPzlx(PageData pd,String guid) {
		return pzlxnewDao.updPzlx(pd,guid);
	}

}
