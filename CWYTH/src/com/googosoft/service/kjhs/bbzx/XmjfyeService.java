package com.googosoft.service.kjhs.bbzx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.PageDao;
import com.googosoft.dao.kjhs.bbzx.XmjfyeDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Service("xmjfyeService")
public class XmjfyeService  extends BaseService{
	@Resource(name = "xmjfyeDao")
	private XmjfyeDao xmjfyeDao;
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		Map<String, List<Map<String,Object>>> map = xmjfyeDao.getResult(params);
		return map;
	}
	
	/**
	 * 跳转明细账
	 * @param params
	 * @return
	 */
	public boolean getResultMxz(String bmbh, String sszt, String xmbh, 
			String year, String StartMonth, String endMonth, String jzpz,String kjzd,PageData pd, String bz) {
		return xmjfyeDao.getResultMxz(bmbh, sszt,xmbh,year,StartMonth,endMonth,jzpz,kjzd,pd,bz);
	}
	
	/**
	 * 获得页面数据信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMxzPageList(String bmbh,String xmbh,String kjzd,PageData pd){
		return xmjfyeDao.getMxzPageList(bmbh,xmbh,kjzd,pd);
	}
	
}
