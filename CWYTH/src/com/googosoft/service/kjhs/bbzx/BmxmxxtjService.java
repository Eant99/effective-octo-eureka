/**
 * 
 */
package com.googosoft.service.kjhs.bbzx;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.kjhs.bbzx.BmxmxxtjDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("bmxmxxtjService")
public class BmxmxxtjService extends BaseService {
	@Resource(name = "bmxmxxtjdao")
	private BmxmxxtjDao bmxmxxtjdao;

	
	
	
	/**
	 * 根据rybh获取操作权限
	 * @param rybh
	 * @return
	 */
	public List<Map> getBmxmxxList(PageData pd,String login) {	
		String bmbh=pd.getString("bmbh");
		String xmdl=pd.getString("xmdl");
		String xmsjks=pd.getString("xmsjks");
		String xmsjjs=pd.getString("xmsjjs");
		
		/*获取一级菜单*/
		List<Map> yjcd = bmxmxxtjdao.getbmbhList(login,bmbh);
		/*循环一级菜单，获取二级dalei*/
		for(int i=0;i<yjcd.size();i++){
			Map map = yjcd.get(i);
			List<Map> ejcd = bmxmxxtjdao.getXmdlList(map.get("BMBH")+"",login,xmdl);
			/*循环二级菜单，获取三级xm*/
			for(int j=0;j<ejcd.size();j++){
				Map ejcd_map = ejcd.get(j);
				List<Map> sjcd  = bmxmxxtjdao.getXmxxList(map.get("BMBH")+"",login,ejcd_map.get("XMDL")+"");
					ejcd_map.put("XMXX", sjcd);
			}
	
				map.put("XMDL", ejcd);
		}
		System.err.println("yjcd++========="+yjcd);
		return yjcd;
	}
	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		Map<String, List<Map<String,Object>>> map = bmxmxxtjdao.getResult(params);
		return map;
	}
	/**
	 * 
	 * @author  wangguanghua
	 * @version  2018-9-13上午10:21:32
	 */
	public List getPrintInfo(){
		return bmxmxxtjdao.getPrintInfo();
	}
	
}
