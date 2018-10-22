package com.googosoft.service.kjhs.bbzx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.base.PageDao;
import com.googosoft.dao.kjhs.bbzx.GrwlyebDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.BaseService;

@Service("grwlyebService")
public class GrwlyebService extends BaseService {


	@Resource(name = "grwlyebDao")
	private GrwlyebDao grwlyebdao;
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	

	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		Map<String, List<Map<String,Object>>> map = grwlyebdao.getResult(params);
		return map;
	}
}
