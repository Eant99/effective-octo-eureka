package com.googosoft.service.ysgl.jcsz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.ysgl.jcsz.XmflDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
/**
 * 经济科目与部门对应设置Service
 * @author Administrator
 *
 */
@Service("nxmflService")
public class XmflService extends BaseService{
	@Resource(name="nxmflDao")
	private XmflDao dao;

	public int doAdd(PageData pd) {
		return dao.doAdd(pd);
	}
	
	@Transactional
	public int addCcywsq(PageData pd) {
		String guid = pd.getString("guid");
		dao.doDelete(pd.getString("guids"));
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("___________"+map.get("guid222")+map.get("FLMCS")+map.get("YWGLBMMC")+map.get("bz"));
			if(Validate.isNull(map.get("guid222"))) {
				if(Validate.isNull(map.get("FLMCS"))&&Validate.isNull(map.get("YWGLBMMC"))&&Validate.isNull(map.get("bz"))){
					System.err.println("________");
					continue;
				}else {
					dao.insertTxrysx(map, pd,guid);
				}
			}else {
				dao.insertTxryxx(map, pd,guid);
			}
			
		}
		return 1;
	}
	
	public int doDel(String rybh){
		int result = dao.doDel(rybh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"人员基础信息：删除",rybh);
		}
		return result;
	}
	
	public int validateById(String flmcs) {
		return dao.validateById(flmcs);
	}


}
