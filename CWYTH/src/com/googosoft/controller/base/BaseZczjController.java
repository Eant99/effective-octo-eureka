package com.googosoft.controller.base;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.googosoft.service.base.BaseZczjService;

/**
 * @author  zjy
 * @date 2017-09-18
 * @version 1.0 
 */
@Component("baseZczjController")
public class BaseZczjController extends BaseController {
	@Resource(name="baseZczjService")
	public BaseZczjService baseZczjService;
	
	/**
	 * 获取系统设置表中关于折旧设置的部分
	 * @return
	 */
	public Map getZjxxBySysXtb(){
		return baseZczjService.getZjxxBySysXtb();
	}
	
	/**
	 * 获取折旧方法
	 * @return
	 */
	public List getZjffList(){
		return baseZczjService.getZjffList();
	}
	
	/**
	 * 批量赋值
	 * @return
	 */
	public int doPlfz(String bzdm,String xznr,String content,String flh,String tablename){
		return baseZczjService.doPlfz(bzdm, xznr, content, flh, tablename);
	}
}
