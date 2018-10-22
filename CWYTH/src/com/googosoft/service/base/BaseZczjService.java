package com.googosoft.service.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.base.BaseZczjDao;

/**
 * 资产折旧service基础类
 * @author zjy
 */
@Service("baseZczjService")
public class BaseZczjService extends BaseService{
	@Resource(name="baseZczjDao")
	public BaseZczjDao baseZczjDao;
	
	/**
	 * 获取系统设置表中关于折旧设置的部分
	 * @return
	 */
	public Map getZjxxBySysXtb(){
		return baseZczjDao.getZjxxBySysXtb();
	}
	
	/**
	 * 获取折旧方法
	 * @return
	 */
	public List getZjffList(){
		return baseZczjDao.getZjffList();
	}
	
	/**
	 * 批量赋值
	 * @return
	 */
	public int doPlfz(String bzdm,String xznr,String content,String flh,String tablename){
		return baseZczjDao.doPlfz(bzdm, xznr, content, flh, tablename);
	}
}
