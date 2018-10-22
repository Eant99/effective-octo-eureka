package com.googosoft.service.ysgl.yszxfx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.ysgl.bmyshz.BmyshzDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("yszxfxService")
public class YszxfxService {
	

	@Resource(name="bmyshzDao")
	private BmyshzDao bmyshzDao;
	
	/**
	 * 得到项目分类
	 * 
	 */
	public List<Map<String, Object>> getXmfl(String qrzt) {
		
		List list = bmyshzDao.getXmfl(qrzt);
		
		return list;
		
		
	}
	/**
	 * 得到基本支出表
	 * 
	 */
	public List<Map<String, Object>> getJbzc(String qrzt,String sbnd) {
		
		List list = bmyshzDao.getJjbzc(qrzt,sbnd);
		
		return list;
		
		
	}
	/**
	 * 申报年度得到基本支出表
	 * 
	 */
	public List<Map<String, Object>> getJbzcSbnd(String qrzt,String sbnd) {
		
		List list = bmyshzDao.getJjbzcSbnd(qrzt, sbnd);
		
		return list;
		
		
	}
	/**
	 * 修改基本支出表
	 * 
	 */
	public int updateQrzt(String guid) {
		int i;
		 i= bmyshzDao.updateQrzt(guid);
		
		return i;
		
		
	}
	/**
	 * 修改基本收入预算表的确认状态
	 * 
	 */
	public int updateSrysQrzt(String guid) {
		int i;
		 i= bmyshzDao.updateSrysQrzt(guid);
		
		return i;
		
		
	}
	/**
	 * 修改基本收入预算表的确认状态
	 * 
	 */
	public int updateXmzcQrzt(String guid) {
		int i;
		 i= bmyshzDao.updateXmzcQrzt(guid);
		
		return i;
		
		
	}
	/**
	 * 得到项目支出表
	 * 
	 */
	public List<Map<String, Object>> getXmzc(String qrzt,String sbnd) {
		
		List list = bmyshzDao.getXmzc(qrzt,sbnd);
		
		return list;
		
		
	}
	/**
	 * 得到项目分类下一级
	 * 
	 */
	public List<Map<String, Object>> getXmflxyj(String xmfl,String qrzt,String sbnd) {
		
		List list = bmyshzDao.getXmflXyj(xmfl, qrzt,sbnd);
				
		
		return list;
		
		
	}
	
	
	
	
	/**
	 * 预算类型树
	 * @param pd
	 * @param rootPath
	 * @return
	 */
	public Object getYsLx(PageData pd,String rootPath) {
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
		List list = null;
		String datas = SendHttpUtil.sendPost(SystemSet.address+"/ysgl/bmyshz", "");
		list = SendHttpUtil.getResultToList(datas);
		if(list.size()>0){
			String dm = "",mc = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
				mc = Validate.isNullToDefault(map.get("MC"), "").toString();
				children.add(new ExtTreeNode(dm, mc, bool , true, false,href+dm, target,leaf));
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
}
