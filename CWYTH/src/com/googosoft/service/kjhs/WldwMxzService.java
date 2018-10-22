package com.googosoft.service.kjhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.MxzExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.kjhs.WldwMxzDao;
import com.googosoft.dao.kjhs.bbzx.MxzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("wldwmxzService")
public class WldwMxzService extends BaseService {
	@Resource(name = "wldwmxzDao")
	private WldwMxzDao dao;

	/**
	 * 查询月份
	 * 
	 * @return
	 */
	
	
	public List<Map<String, Object>> getMonth() {
		return dao.getMonth();
	}

	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch){
		return dao.getPageList(kmbh,kjzd,treesearch);
	}
	
	public String kmmc(String kmbh) {
		return dao.kmmc(kmbh);
	}
	
	
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return dao.getKjkmJb();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		dao.deleteKmyeb();
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt,String kjzd,String kmbh) {
		return dao.getResult(list, sszt,kjzd,kmbh);
	}
	
	
	
	public List<Map<String, Object>> daochu( String sszt,String kmbh) {
		return dao.daochu(sszt,kmbh);
	}
	
	
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "明细账";
		if(Validate.noNull(foo)){
			Title = "明细账";
		}
		String[] title = new String[] { "凭证日期","凭证类型","凭证号", "摘要", "经济分类", "部门编号", "项目编号", "借方金额","贷方金额","方向","余额"  };
		Map<String, Object> dataMap = MxzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	public List<Map<String, Object>> getJjPageList(String kmbh,String kjkm,String pznd,String startMonth,String endMonth,String dm,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return dao.getJjPageList(kmbh,kjkm,pznd,startMonth,endMonth,dm,pzzt, bmbh, xmbh, jfjel, jfjeh,zy);
	}
	
	public List kmbhList(String kmbh) {
		return dao.kmbhList(kmbh);
	}
	
	/**
	 * 获取往来单位设置树
	 */
	public Object getYsLx2(PageData pd,String rootPath) {
		String leaf = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		boolean bool = true;
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		Map map = new HashMap();
		//List list = treeDao.getSchoolTree();
		List list = dao.bdbxMenu();
		if(list.size()>0){
			String dm = "",mc = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
				mc = Validate.isNullToDefault(map.get("MC"), "").toString();
				children.add(new ExtTreeNode(dm, mc, bool , true, false,href+dm+"&jump=yes", target,leaf));
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	
	
}
