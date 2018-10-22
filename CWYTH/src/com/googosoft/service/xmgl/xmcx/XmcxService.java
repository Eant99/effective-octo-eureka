package com.googosoft.service.xmgl.xmcx;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.xmgl.xmcx.XmcxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("xmcxService1")
public class XmcxService extends BaseService {
	
	@Resource(name="xmcxDao1")
	public XmcxDao xmcxDao;
	/**
	 * 获取权限下的单位（单位人员树）
	 * @param loginrybh登录的人员编号 
	 * @return
	 */
	public Object getPowerNode(PageData pd, String loginrybh) {
		String Target=pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=xmcxDao.PowerModels(loginrybh);
		Map map=new HashMap();
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&rybh=";
		} else {
			Href = Href + "?rybh=";
		}
		if(dList.size()>0){
			String dwbh="",bmh="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefault(map.get("DWBH"), "")+"";
                bmh = map.get("BMH")+"";
                mc = map.get("MC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0) {							
                	 List LRybs = xmcxDao.GetModels(dwbh);
                     xjcount =LRybs.size();
				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true, xjcount > 0 ? true : false, "", Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true, xjcount > 0 ? true : false, "", Target));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取当前用户所在单位编号
	 * 
	 */
	public String getDwbhByLoginuser(String loginrybh){
		List dList=xmcxDao.PowerModels(loginrybh);
		Map map=new HashMap();
		map=(Map) dList.get(0);
		String bmbh;
		bmbh = Validate.isNullToDefault(map.get("DWBH"), "")+"";
		return bmbh;
		
	}
	
	
	public Object getDwRyNode(PageData pd, String sjdw,String rootPath) {
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&rybh=";
		} else {
			Href = Href + "?rybh=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  xmcxDao.GetDwModels(sjdw);
		String dwbh="", bmh="", mc="",rybh="";
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				 dwbh = map.get("DWBH").toString();
				 bmh = map.get("BMH").toString();
				 mc = map.get("MC").toString();
				List dwcount = xmcxDao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = xmcxDao.GetModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, Href.length() > 0 ? Href + rybh +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH"): Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, Href.length() > 0 ? Href + rybh +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH"): Href, Target));
				}
			}
		}
		dList = xmcxDao.GetModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				children.add(new ExtTreeNode(map.get("RYBH").toString(),
						"(" + map.get("RYGH").toString() + ")" + map.get("XM").toString(), true, false,
						Href.length() > 0 ? Href + map.get("RYBH").toString()+"&qc="+"("+bmh+")"+mc+"&bmh="+map.get("BMH") : Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/vector.gif"));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	

	public Object getDwRyTjNode(PageData pd, String sjdw,String rootPath) {
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&rybh=";
		} else {
			Href = Href + "?rybh=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  xmcxDao.GetDwModels(sjdw);
		String dwbh="", bmh="", mc="",rybh="";
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				 dwbh = map.get("DWBH").toString();
				 bmh = map.get("BMH").toString();
				 mc = map.get("MC").toString();
				List dwcount = xmcxDao.GetTjModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = xmcxDao.GetTjModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, Href.length() > 0 ? Href + rybh +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH"): Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, Href.length() > 0 ? Href + rybh +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH"): Href, Target));
				}
			}
		}
		dList = xmcxDao.GetTjModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				children.add(new ExtTreeNode(map.get("RYBH").toString(),
						"(" + map.get("RYGH").toString() + ")" + map.get("XM").toString(), true, false,
						Href.length() > 0 ? Href + map.get("RYBH").toString()+"&qc="+"("+bmh+")"+mc+"&bmh="+map.get("BMH") : Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/vector.gif"));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	

	public Map<String, Object> getXmxxMapById(String guid) {
		return xmcxDao.selectXmxxMapById(guid);
	}

	/**
	 * 获取收入科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getsrkmById(String guid, String kjzd) {
		return xmcxDao.getsrkmById(guid,kjzd);
		
	}

	/**
	 * 获取支出科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getzckmById(String guid, String kjzd) {
		return xmcxDao.getszckmById(guid,kjzd);
	}

	/**
	 * 获取经济科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getjjflkmById(String guid) {
		return xmcxDao.getjjflkmById(guid);		
	}



}
