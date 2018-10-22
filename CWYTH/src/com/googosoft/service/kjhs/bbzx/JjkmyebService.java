package com.googosoft.service.kjhs.bbzx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.base.PageDao;
import com.googosoft.dao.kjhs.bbzx.JjkmyebDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.BaseService;

@Service("jjkmyebService")
public class JjkmyebService extends BaseService {

	@Resource(name = "jjkmyebDao")
	private JjkmyebDao jjkmyebdao;
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	

	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		Map<String, List<Map<String,Object>>> map = jjkmyebdao.getResult(params);
		return map;
	}
	/**
	 * 合并后的第一个页面
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult_Hb(Params params){
		Map<String, List<Map<String,Object>>> map = jjkmyebdao.getResult_Hb(params);
		return map;
	}
}
