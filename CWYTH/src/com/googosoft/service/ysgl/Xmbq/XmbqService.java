package com.googosoft.service.ysgl.Xmbq;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.ysgl.Xmbq.XmbqDao;
import com.googosoft.pojo.ysgl.CW_XMBQMXB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("xmbqService")
public class XmbqService extends BaseService{
	@Resource(name="xmbqDao")
	public XmbqDao xmbqDao;
	
	/**
	 * 判断序号是否重复（增加）
	 * @param pd
	 * @return
	 */
	public int getXhById(PageData pd ) {
		return xmbqDao.getXhById(pd);
	}
	
	/**
	 * 保存项目标签主表
	 * @param pd
	 * @return
	 */
	public int doAdd(PageData pd) {
		return xmbqDao.doAdd(pd);
	}
	/**
	 * 删除项目标签明细表
	 * @param zbid
	 * @return
	 */
	 public int doDelMxb(String zbid) {	
	    int i = xmbqDao.doDelMxb(zbid);	
		return i;
    }
	
	/**
	 * 保存项目标签明细表
	 * @param xmbqmxb
	 * @return
	 */
    public int doSaveMxb(CW_XMBQMXB xmbqmxb) {
		int i = xmbqDao.doSaveMxb(xmbqmxb);
		return i;
    }
    
    /**
   	 * 获取项目标签编辑信息
   	 * @param
   	 * @return
   	 */
   	public Map<?, ?> getXmbqById(String guid) {
   		return xmbqDao.getXmbqById(guid);
   	}
   	/**
	 * 获取项目标签明细编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getXmbqmxById(String guid) {
		return xmbqDao.getXmbqmxById(guid);
	}
	
	/**
	 * 编辑保存项目标签主表
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int updXmbqzb(PageData pd,String guid) {
		return xmbqDao.updXmbqzb(pd,guid);
	}
	
	/**
	 * 删除项目标签信息
	 * @param guid
	 * @return
	 */
	public int delete(String guid) {
		return xmbqDao.delete(guid);
	}
	/**
	 * 验证标签编号是否重复
	 */
	public boolean getObjectByBqbh(String guid, String bqbh) {
		return xmbqDao.getObjectByBqbh(guid, bqbh);
	}

}
