package com.googosoft.service.sqspwh;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.dao.sqspwh.SqspwhDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 事前审批维护service
 * @author googosoft
 *
 */
@Service("sqspwhService")
public class SqspwhService extends BaseService{
	
	@Autowired
	private SqspwhDao sqspwhDao;
	
	
	/**
	 * 平台系统设置：事前审批维护保存
	 * @param pd
	 * @return
	 */
	@Transactional
	public int doSave(PageData pd) {
		List<String> sqsplxList = sqspwhDao.getSqsplxList();
		Gson gson = new Gson();
		Map<String, Object> map = gson.fromJson(pd.getString("json"),new TypeToken<HashMap<String,Object>>(){}.getType());
		List<Map<String, Object>> list = (List)map.get("list");
		int i = 0;
		for (Map<String, Object> sqspwh : list) {
			String mkbh = Validate.isNullToDefaultString(sqspwh.get("mkbh"), "");
			String sfqy = Validate.isNullToDefaultString(sqspwh.get("sfqy"), "0");
			String sftzxz = Validate.isNullToDefaultString(sqspwh.get("sftzxz"), "0");
			if(sqsplxList.contains(mkbh)) {
				i += sqspwhDao.updateSqspwh(mkbh, sfqy, sftzxz);
			}else {
				i += sqspwhDao.insertSqspwh(mkbh, sfqy, sftzxz);
			}
			i += sqspwhDao.updateSysmk(mkbh, sfqy);
		}
		return i;
	}
	public boolean getSftzxz(String mkbh) {
		return "1".equals(sqspwhDao.querySftzxz(mkbh)) ? true:false;
	}
	//查询是否存在正在使用中的事前审批单据
	public boolean getSqspSfsy(String type) {
		String tableName = "",shzt = "";
		switch (type) {
		case "cc":
			tableName = "cw_ccsqspb";
			shzt = "06";
			break;
		case "gwjd":
			tableName = "cw_gwjdywsqspb";
			shzt = "06";
			break;
		default:
			break;
		}
		return "0".equals(sqspwhDao.querySqspSfsy(tableName,shzt)) ? false:true;
	}
}
