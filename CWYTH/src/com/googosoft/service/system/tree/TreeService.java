package com.googosoft.service.system.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.system.tree.TreeDao;
import com.googosoft.pojo.TreeJson;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("treeService")
public class TreeService {
	
	@Resource(name="treeDao")
	private TreeDao treeDao;
	
	/**
	 * 获取权限下的单位
	 * @param loginrybh登录的人员编号
	 * @return
	 */
	public String getPowerNode(PageData pd, String loginrybh){
		String Target=pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.powerModels(loginrybh);
		Map map=new HashMap();
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
                	 List LRybs = treeDao.getRyModels(dwbh);
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
	 * 获取某个单位下的所有直属单位及人员的信息
	 * @param pd
	 * @param sjdw 上级单位 
	 * @param rootPath
	 * @return
	 */
	public String getDwRyNode(PageData pd, String sjdw,String rootPath) {
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
		List dList =  treeDao.getDwModels(sjdw);//获取下级单位
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH")+"";
				bmh = map.get("BMH")+"";
				mc = map.get("MC")+"";
				List dwcount = treeDao.getRyModels(dwbh);//获取某个单位下的所有人员
				xjcount = Integer.parseInt(map.get("XJCOUNT")+"") + dwcount.size();
				if (xjcount <= 0) {
//					dwcount = treeDao.getRyModels(dwbh);
//					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc,true, true,
							false, "", Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc,false, true,false, "", Target));
				}
			}
		}
		dList = treeDao.getRyModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				children.add(new ExtTreeNode(map.get("RYBH")+"",
						"(" + map.get("RYGH")+"" + ")" + map.get("XM")+"", true, false,
						Href.length() > 0 ? Href + map.get("RYBH")+"" : Href, Target));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取权限下的单位（单位树）
	 * @param loginrybh登录的人员编号 
	 * @return
	 */
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = Validate.isNullToDefaultString(pd.getString("target"),"");
		String Href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
		String from = Validate.isNullToDefaultString(pd.getString("from"), "");
		if(Validate.noNull(Href)){
			if (Href.indexOf("?") > 0) {
				Href = Href + "&dwbh=";
			} else {
				Href = Href + "?dwbh=";
			}
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.powerModels(loginrybh);
		Map map=new HashMap();
		if(dList.size()>0){
			String dwbh="",bmh="",mc="",dwjc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefaultString(map.get("DWBH"), "");
				String url = Href.length() > 0 ? Href + dwbh : Href;
				bmh = Validate.isNullToDefaultString(map.get("BMH"),"");
				mc = Validate.isNullToDefaultString(map.get("MC"),"");
				
				if("ED".equals(from)){
					dwjc = Validate.isNullToDefaultString(map.get("DWJC"),"");
					if(Constant.DWJC_EJ.equals(dwjc)){
						children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
					}
				}
				else{
					xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
					if(xjcount<=0) {
						children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
					}else{
						children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, url, Target,icon));
					}
				}
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath,String xxTree) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = Validate.isNullToDefaultString(pd.getString("target"),"");
		String Href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
		String from = Validate.isNullToDefaultString(pd.getString("from"), "");
		if(Validate.noNull(Href)){
			if (Href.indexOf("?") > 0) {
				Href = Href + "&dwbh=";
			} else {
				Href = Href + "?dwbh=";
			}
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.powerModelsnew(loginrybh,xxTree);
		Map map=new HashMap();
		if(dList.size()>0){
			String dwbh="",bmh="",mc="",dwjc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefaultString(map.get("DWBH"), "");
                String url = Href.length() > 0 ? Href + dwbh : Href;
                bmh = Validate.isNullToDefaultString(map.get("BMH"),"");
                mc = Validate.isNullToDefaultString(map.get("MC"),"");
                
                if("ED".equals(from)){
	                dwjc = Validate.isNullToDefaultString(map.get("DWJC"),"");
                	if(Constant.DWJC_EJ.equals(dwjc)){
                		children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
                	}
                }
                else{
	                xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
	                if(xjcount<=0) {
					     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
	                }else{
	                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, url, Target,icon));
	                }
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取所有的单位（单位树）
	 * @param loginrybh登录的人员编号 
	 * @return
	 */
	public Object getAllDwNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = Validate.isNullToDefaultString(pd.getString("target"),"");
		String Href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
		String from = Validate.isNullToDefaultString(pd.getString("from"), "");
		if(Validate.noNull(Href)){
			if (Href.indexOf("?") > 0) {
				Href = Href + "&dwbh=";
			} else {
				Href = Href + "?dwbh=";
			}
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.allModels(loginrybh);
		Map map=new HashMap();
		if(dList.size()>0){
			String dwbh="",bmh="",mc="",dwjc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefaultString(map.get("DWBH"), "");
                String url = Href.length() > 0 ? Href + dwbh : Href;
                bmh = Validate.isNullToDefaultString(map.get("BMH"),"");
                mc = Validate.isNullToDefaultString(map.get("MC"),"");
                
                if("ED".equals(from)){
	                dwjc = Validate.isNullToDefaultString(map.get("DWJC"),"");
                	if(Constant.DWJC_EJ.equals(dwjc)){
                		children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
                	}
                }
                else{
	                xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
	                if(xjcount<=0) {
					     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
	                }else{
	                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, url, Target,icon));
	                }
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 
	 * @param pd
	 * @param loginrybh
	 * @param rootPath
	 * @return
	 */
	
	public Object getPowerDwNode1(PageData pd, String dwbh1,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = Validate.isNullToDefaultString(pd.getString("target"),"");
		String Href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
		String from = Validate.isNullToDefaultString(pd.getString("from"), "");
		if(Validate.noNull(Href)){
			if (Href.indexOf("?") > 0) {
				Href = Href + "&dwbh=";
			} else {
				Href = Href + "?dwbh=";
			}
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList;
		if(dwbh1.equals("root")){
			 dList=treeDao.dwpowerModels1(dwbh1);	
		}else{
			 dList=treeDao.dwpowerModels(dwbh1);
		}
		
		Map map=new HashMap();
		if(dList.size()>0){
			String dwbh="",bmh="",mc="",dwjc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefaultString(map.get("DWBH"), "");
                String url = Href.length() > 0 ? Href + dwbh : Href;
                mc = Validate.isNullToDefaultString(map.get("MC"),"");
                
                if("ED".equals(from)){
	                dwjc = Validate.isNullToDefaultString(map.get("DWJC"),"");
                	if(Constant.DWJC_EJ.equals(dwjc)){
                		children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target));
                	}
                }
                else{
	                xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
	                if(xjcount<=0) {
					     children.add(new ExtTreeNode("D" + dwbh, "(" + dwbh + ")" + mc, true, false, false, url, Target));
	                }else{
	                	children.add(new ExtTreeNode("D" + dwbh, "(" + dwbh + ")" + mc, false, true, true, url, Target,icon));
	                }
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取实验室标识下的单位树
	 * @param pd
	 * @param loginrybh
	 * @param rootPath
	 * @return
	 */
	public Object getPowerSysNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String iconsys = rootPath+"/static/plugins/ext/resources/images/default/tree/computer.png";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.powerSysModels(loginrybh);
		Map map=new HashMap();
		if(dList.size()>0){
			String dwbh="",bmh="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefault(map.get("DWBH"), "")+"";
				bmh = map.get("BMH")+"";
				mc = map.get("MC")+"";
				xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
				String url = Href.length() > 0 ? Href + map.get("DWBH")+"" : Href;
				if(xjcount<=0) {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, url, Target,iconsys));
				}else{
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, url, Target,icon));
				}
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	/**
	 * 获取某个单位下的所有直属单位信息
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
	public Object getDwNode(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = Validate.isNullToDefaultString(pd.getString("target"),"");
		String Href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
		String from = Validate.isNullToDefaultString(pd.getString("from"), "");
		if(Validate.noNull(Href)){
			if (Href.indexOf("?") > 0) {
				Href = Href + "&dwbh=";
			} else {
				Href = Href + "?dwbh=";
			}
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  treeDao.getDwModels(sjdw);
		String url, dwbh, bmh, mc, dwjc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = Validate.isNullToDefaultString(map.get("DWBH"), "");
                url = Href.length() > 0 ? Href + dwbh : Href;
                bmh = Validate.isNullToDefaultString(map.get("BMH"),"");
                mc = Validate.isNullToDefaultString(map.get("MC"),"");
                
                if("ED".equals(from)){
	                dwjc = Validate.isNullToDefaultString(map.get("DWJC"),"");
                	if("2".equals(dwjc)){
                		children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, url, Target));
                	}
                }
                else{
					xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
					if(xjcount<=0) {
					    children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, url, Target));
	                }else{
	                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, url, Target, icon));
	                }
                }
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取某个单位下的所有直属单位信息
	 * 传模块编号的做特殊处理
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
	public Object getDwNodeByMkbh(PageData pd, String sjdw,String rootPath,String mkbh) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = Validate.isNullToDefaultString(pd.getString("target"),"");
		String Href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
		String from = Validate.isNullToDefaultString(pd.getString("from"), "");
		if(Validate.noNull(Href)){
			if (Href.indexOf("?") > 0) {
				Href = Href + "&dwbh=";
			} else {
				Href = Href + "?dwbh=";
			}
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  treeDao.getDwModelsByMkbh(sjdw,mkbh);
		String url, dwbh, bmh, mc, dwjc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = Validate.isNullToDefaultString(map.get("DWBH"), "");
				url = Href.length() > 0 ? Href + dwbh : Href;
				bmh = Validate.isNullToDefaultString(map.get("BMH"),"");
				mc = Validate.isNullToDefaultString(map.get("MC"),"");
				
				if("ED".equals(from)){
					dwjc = Validate.isNullToDefaultString(map.get("DWJC"),"");
					if("2".equals(dwjc)){
						children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, url, Target));
					}
				}
				else{
					xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
					if(xjcount<=0) {
						children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, url, Target));
					}else{
						children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, url, Target, icon));
					}
				}
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 根据单位编号获取所有下级
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
	public Object getSysDwTreeNode(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String iconsys = rootPath+"/static/plugins/ext/resources/images/default/tree/computer.png";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  treeDao.getSysDwModels(sjdw);
		String dwbh, bmh, mc, sysbz;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH")+"";
				bmh = map.get("BMH")+"";
				mc = map.get("MC")+"";
				sysbz = map.get("SYSBZ")+"";
				xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
				if(xjcount<=0) {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, Href.length() > 0 ? Href + map.get("DWBH")+"" : Href, Target,iconsys));
				}else{
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH")+"" : Href, Target,icon));
				}
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取某个单位下的所有直属单位信息
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
	public Object getSysTreeNode(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String iconsys = rootPath+"/static/plugins/ext/resources/images/default/tree/computer.png";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  treeDao.getSysModels(sjdw);
		String dwbh, bmh, mc, sysbz;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH")+"";
				bmh = map.get("BMH")+"";
				mc = map.get("MC")+"";
				sysbz = map.get("SYSBZ")+"";
				xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
				if(xjcount<=0) {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, Href.length() > 0 ? Href + map.get("DWBH")+"" : Href, Target,iconsys));
				}else{
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH")+"" : Href, Target,icon));
				}
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取地点树
	 * @return
	 */
	public String getPowerDdNode(PageData pd,String rootpath) {
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		String icon = rootpath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		if (Href.indexOf("?") > 0) {
			Href = Href + "&ddbh=";
		} else {
			Href = Href + "?ddbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.powerDdModels();
		Map map=new HashMap();
		if(dList.size()>0){
			String ddbh="",ddh="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				ddbh = Validate.isNullToDefault(map.get("DDBH"), "")+"";
                ddh = map.get("DDH")+"";
                mc = map.get("MC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0){
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, true, false, false, 
    						Href.length() > 0 ? Href + map.get("DDBH")+"" : Href, Target));
                }else{
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, false, true,true, 
    						Href.length() > 0 ? Href + map.get("DDBH")+"" : Href, Target,icon));
                }

			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取某个地点下的直属子节点
	 * @param pd
	 * @param sjdd 上级地点
	 * @param rootPath
	 * @return
	 */
	public String getDdNode(PageData pd, String sjdd,String rootPath) {
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		if (Href.indexOf("?") > 0) {
			Href = Href + "&ddbh=";
		} else {
			Href = Href + "?ddbh=";
		}
		sjdd = sjdd.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdd);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  treeDao.getDdModels(sjdd);
		String ddbh,ddh,mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				ddbh = map.get("DDBH")+"";
				ddh = map.get("DDH")+"";
				mc = map.get("MC")+"";
				xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
				if(xjcount<=0){
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, true, true, false, 
    						Href.length() > 0 ? Href + map.get("DDBH")+"" : Href, Target));
                }else{
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, false, true, false,
    						Href.length() > 0 ? Href + map.get("DDBH")+"" : Href, Target,icon));
                }
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 财政分类树
	 * @param pid
	 * @return
	 */
	public List getCzflList(String pid) {
		return treeDao.getCzflList(pid);
	}
	/**
	 * 资产分类树
	 * @param flh 分类号
	 * @param dmjc  代码级次
	 * @return
	 */
	public List getZcflList(String flh, String dmjc) {
		return treeDao.getZcflList(flh,dmjc);
	}
	/**
	 * 获取学科分类树
	 * @param pid
	 * @return
	 */
	public Object getPowerDmNode(PageData pd,String rootPath){
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dm=";
		} else {
			Href = Href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode();
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List list = treeDao.getxkList();
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "";
			for(int i=0;i<list.size();i++){
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "")+"";
				mc = Validate.isNullToDefault(map.get("MC"), "")+"";
				children.add(new ExtTreeNode(dm,"("+dm+")"+mc,false,true,false,Href.length()>0 ? Href + map.get("DM") : Href,Target,icon));
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取某个学科下的所以直属单位信息
	 * @param pd
	 * @param dm
	 * @param rootPath
	 * @return
	 */
	public Object getDmNode(PageData pd,String dm,String rootPath){
//		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dm=";
		} else {
			Href = Href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(dm);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = treeDao.getxkxxList(dm);
		Map map = new HashMap();
		if(list.size()>0){
			String dm1,mc;
			for(int i=0;i<list.size();i++){
				map = (Map)list.get(i);
				dm1 = map.get("DM")+"";
				mc = map.get("MC")+"";
				children.add(new ExtTreeNode(dm1,"("+dm1+")"+mc,true,false,false,Href.length() > 0 ? Href + map.get("DM")+"" : Href, Target));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取国别分类树
	 * @param pid
	 * @return
	 */
	public List getgbList(String pid){
		return treeDao.getgbList(pid);
	}
	
	/**
	 * 业务审核、审核记录查询都用此树
	 * 获取权限下的菜单
	 * @param pd
	 * @param loginrybh
	 * @param rootPath
	 * @return
	 */
	public Object getPowerNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&mkbh=";
		} else {
			Href = Href + "?mkbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList= treeDao.initLevelMenu(loginrybh,2,MenuFlag.SHCD);
		Map map=new HashMap();
		if(dList.size()>0){
			String mkbh="",mkmc="";
			for(int i=0;i<dList.size();i++)
			{
				map=(Map)dList.get(i);
				mkbh = Validate.isNullToDefault(map.get("MKBH"), "").toString();
                mkmc = map.get("MKMC").toString();
				children.add(new ExtTreeNode(mkbh, mkmc, false , true, false, Href.length() > 0 ? Href + mkbh : Href, Target,icon));
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 业务审核、审核记录查询都用此树
	 * 根据模块编号获取所有下级
	 * @param sjdwbh
	 * @return
	 */
	public Object getTreeNode(PageData pd,String loginrybh, String sjmkbh,String rootPath, String mk) {
		String icon = "";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		String mkbhCheck = pd.getString("mkbhCheck");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&mkbh=";
		} else {
			Href = Href + "?mkbh=";
		}
		ExtTreeNode node = new ExtTreeNode(sjmkbh);
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=new ArrayList();
 		if(sjmkbh.length() == 2){
 			dList = treeDao.initLevelMenu(loginrybh,2,sjmkbh);
 			icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
 			Map map=new HashMap();
 			if(dList.size()>0)
 			{
 			    String mkbh,mkmc;
 				for(int i=0;i<dList.size();i++){
 					map=(Map)dList.get(i);
 					mkbh = Validate.isNullToDefault(map.get("MKBH"), "").toString();
 					if("051001".equals(mk)){//审核记录查询树上不显示数量
 						mkmc = map.get("MKMC").toString();
 					}else{//业务审核树上显示数量
 						mkmc = map.get("MKMC").toString()+treeDao.getDjcountByProcedure(mkbh,loginrybh);
 					}
 	                if(Validate.noNull(mkbhCheck)){
 	                	if(mkbh.equals(mkbhCheck.substring(0, 4))){
 	                	 children.add(new ExtTreeNode(mkbh,mkmc, false, true, true,Href.length() > 0 ? Href + mkbh : Href, Target,icon));
 	                	}else{
 	                		children.add(new ExtTreeNode(mkbh,mkmc, false, true, false,Href.length() > 0 ? Href + mkbh : Href, Target,icon));
 	                	}
 	                }else{
 	                	children.add(new ExtTreeNode(mkbh,mkmc, false, true, false,Href.length() > 0 ? Href + mkbh : Href, Target,icon));
 	                }
 				}
 				node.setChildren(children);
 			}
 	        return node.GetChildrenJsonString();
 		}else{
 			dList = treeDao.initLevelMenu(loginrybh,3,sjmkbh);
 			icon = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
 			Map map=new HashMap();
 			if(dList.size()>0){
 			    String mkbh,mkmc;
 				for(int i=0;i<dList.size();i++){
 					map=(Map)dList.get(i);
 					mkbh = Validate.isNullToDefault(map.get("MKBH"), "").toString();
 					if("051001".equals(mk)){//审核记录查询树上不显示数量
 						mkmc = map.get("MKMC").toString();
 					}else{//业务审核树上显示数量
 						mkmc = map.get("MKMC").toString()+treeDao.getDjcountByProcedure(mkbh,loginrybh);
 					}
 	               children.add(new ExtTreeNode(mkbh,mkmc, true , true, Href.length() > 0 ? Href + mkbh : Href, Target,icon));
 				}
 				node.setChildren(children);
 			}
 	        return node.GetChildrenJsonString();
 		} 
	}

	/**
	 * 审核树点击查询按钮
	 * 通过模块名称查询模块编号
	 * @return
	 */
	public String findMkbhByMkmc(PageData pd) {
		return treeDao.findMkbhByMkmc(pd);
	}

	/**
	 * 获取成本对象树
	 * @param pd
	 * @param jb
	 * @param rootPath
	 * @return
	 */
	public Object getCbdxNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = true;
		if("0".equals(jb) ||"1".equals(jb) ){
			bool = false;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = treeDao.cbdxMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int xjcount = 0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                xjcount =Integer.parseInt(map.get("XJCOUNT").toString());
                if(xjcount<=0) {
                	children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target));
                }else{
                	children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target));	
                }
                
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取成本对象树
	 * @param pd
	 * @param jb
	 * @param rootPath
	 * @return
	 */
	public Object getCbdxNode2(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = true;
		if("0".equals(jb) ||"1".equals(jb) ){
			bool = false;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = treeDao.cbdxMenu2(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int xjcount = 0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                xjcount =Integer.parseInt(map.get("XJCOUNT").toString());
                if(xjcount<=0) {
                	children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target));
                }else{
                	children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target));	
                }
                
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取数据字典树
	 * @param pd
	 * @param jb
	 * @param rootPath
	 * @return
	 */
	public Object getSjzdNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = true;
		if("0".equals(jb) ||"1".equals(jb) ){
			bool = false;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = treeDao.sjzdMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int xjcount = 0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                xjcount =Integer.parseInt(map.get("XJCOUNT").toString());
                if(xjcount<=0) {
                	children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target));
                }else{
                	children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target));	
                }
                
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取科目设置树
	 */
	public Object getKmszNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = treeDao.kmsz(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
				children.add(new ExtTreeNode(dm, mc, bool , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取二级单位人员树（资产调拨分单位内和单位间，用到）
	 * @param pd
	 * @param loginrybh
	 * @param ejdw 资产的二级单位
	 * @param flag 区分单位内和单位间的标志
	 * @return
	 */
	public String getEjdwNode(PageData pd, String loginrybh, String ejdw, String flag){
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		String Target = pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = treeDao.powerModels(loginrybh,ejdw,flag);
		Map map=new HashMap();
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
                	 List LRybs = treeDao.getRyModels(dwbh);
                     xjcount =LRybs.size();
				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? true : false, false, xjcount > 0 ? true : false, Href.length() > 0 ? Href + map.get("DWBH")+"" : Href, Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true, xjcount > 0 ? true : false, Href.length() > 0 ? Href + map.get("DWBH")+"" : Href, Target));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	/**
	 * 获取分类树，显示财政6还是教育16根据传入的zjff决定，默认是教育16
	 * @param pd
	 * @param rybh
	 * @param rootPath
	 * @return
	 */
	public String getKbflNode(PageData pd, String rybh,String rootPath){
		List list = treeDao.getKbflNode(pd,rybh);

		StringBuilder json = new StringBuilder();
		json.append("[");
		if(list.size() > 0){
			String target = Validate.isNullToDefaultString(pd.getString("target"),"");
			String href = Validate.isNullToDefaultString(pd.getString("pageUrl"),"");
			if(Validate.noNull(href)){
				if (href.indexOf("?") > 0) {
					href = href + "&flh=";
				} else {
					href = href + "?flh=";
				}
			}
			
			Map map;
			for(int i = 0; i < list.size(); i++){
				map = (Map)list.get(i);
				String flh = Validate.isNullToDefault(map.get("FLH"),"") + "";
				String flmc = Validate.isNullToDefault(map.get("FLMC"),"") + "";
				String yflh = Validate.isNullToDefault(map.get("YFLH"),"") + "";
				
				json.append("{");
		        json.append("id:'" + Validate.isNullToDefault(map.get("BZDM"),"") + "'");
		        json.append(",text:'(" + yflh + ")" + flmc + "'");
		        json.append(",flh:'" + flh + "'");
		        json.append(",yflh:'" + Validate.isNullToDefault(map.get("YFLH"),"") + "'");
		        json.append(",flmc:'" + flmc + "'");
		        json.append(",fljc:'" + Validate.isNullToDefault(map.get("DMJC"),"") + "'");
		        String cnt = Validate.isNullToDefault(map.get("CNT"),"0") + "";
		        if("0".equals(cnt)){
		        	json.append(",icon:'" + rootPath + "/static/plugins/ext/resources/images/default/tree/leaf.gif'");
			        json.append(",leaf:true");
		        }
		        else{
		        	json.append(",icon:'" + rootPath + "/static/plugins/ext/resources/images/default/tree/folder.gif'");
			        json.append(",leaf:false");
		        }
		        json.append(",expanded:false");
		        if("1".equals(pd.getString("zjff"))){
		        	json.append(",flzd:'flgbm'");
		        	if(Validate.noNull(href)){
		        		json.append(",href:'" + href + flh + "&flzd=flgbm'");
		        	}
		        }
		        else{
		        	json.append(",flzd:'flh'");
		        	if(Validate.noNull(href)){
		        		json.append(",href:'" + href + flh + "&flzd=flh'");
		        	}
		        }
		        if(Validate.noNull(target)){
		        	json.append(",hrefTarget:'" + target + "'");
		        }
		        json.append("},");
			}
			json.deleteCharAt(json.length() - 1);
		}
		json.append("]");
		
        return json.toString();
	}
	
	/**
	 * 分类树点击查询按钮
	 * 通过(flh)flmc查询flh
	 * @return
	 */
	public String findFlhByFlmc(PageData pd) {
		return treeDao.findFlhByFlmc(pd);
	}
	/**
	 * 获取财政十大类树
	 */
	public String getPowerCzNode(PageData pd,String rootpath) {
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		String icon = rootpath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dlh=";
		} else {
			Href = Href + "?dlh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.powerCzModels();
		Map map=new HashMap();
		if(dList.size()>0){
			String dm="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dm = map.get("DM")+"";
                mc = map.get("MC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0){
    				children.add(new ExtTreeNode("P" + dm, "(" + dm.substring(4) + ")" + mc, true, false, false, 
    						Href.length() > 0 ? Href + dm.substring(4) : Href, Target));
                }else{
    				children.add(new ExtTreeNode("P" + dm, "(" + dm.substring(4) + ")" + mc, false, true,false, 
    						Href.length() > 0 ? Href + dm.substring(4) : Href, Target,icon));
                }

			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取某个大类下的直属子节点
	 */
	public String getCzNode(PageData pd, String dlh,String rootPath) {
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		if (Href.indexOf("?") > 0) {
			Href = Href + "&zcdm=";
		} else {
			Href = Href + "?zcdm=";
		}
		dlh = dlh.substring(1);
		ExtTreeNode node = new ExtTreeNode(dlh);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  treeDao.getCzModels(dlh);
		String dm,mc;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dm = map.get("ZCDM")+"";
				mc = map.get("MC")+"";
    				children.add(new ExtTreeNode("P" + dm, "(" + dm + ")" + mc, true, true, false, 
    						Href.length() > 0 ? Href + map.get("ZCDM")+"" : Href, Target,icon));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	public List getDwJson(String id, String name, String level,String searchInput) {
		List list;
		//id如果为空，说明是第一次请求的参数，则获取根元素
		if(Validate.noNull(id)){
			//获取某个元素下的元素
			list = treeDao.getDwJson(id);
		}else{
			if(Validate.noNull(searchInput)){
				list = treeDao.getDwSearch(searchInput);
			}else{
				//获取根目录
				list = treeDao.getDwRoot();
			}
		}
		return list;
	}
	
	public List<Map> shTree(String id, String name, String level, String searchInput,String loginrybh) {
		List list;
		if(Validate.noNull(id)){
			list = treeDao.shTree(id,name,level,loginrybh);
		}else{
			if(Validate.noNull(searchInput)){
				list = treeDao.shTree(searchInput,loginrybh);
			}else{
				list = treeDao.shTree(loginrybh);
			}
		}
		return list;
	}
	
	/**
	 * 获取目录分类树
	 * @param pid
	 * @return
	 */
	public List getmlList(){
		return treeDao.getmlList();
	}
	public Object getXxBySjjd(PageData pd, String sjjd, String rootPath) {
		// TODO Auto-generated method stub
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		String mkbh = pd.getString("mkbh");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&jdbh=";
		} else {
			Href = Href + "?jdbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=treeDao.getXxBySjjd(sjjd,mkbh);
		Map map=new HashMap();
		if(dList.size()>0){
			String jdbh="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				jdbh = Validate.isNullToDefault(map.get("JDBH"), "")+"";
                mc = map.get("MC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                String url = Href.length() > 0 ? Href + map.get("JDBH")+"" : Href;
                if(xjcount<=0) {
				     children.add(new ExtTreeNode(jdbh, mc, true, false, false, url, Target));
                }else{
                	children.add(new ExtTreeNode(jdbh, mc, false, true, true, url, Target,icon));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	public List getShMenuList(String pid){
		return treeDao.getShMenuList(pid);
	}
	public Object getSChool(PageData pd,String rootPath) {
		String leaf = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		if (href.indexOf("?") > 0) {
			href = href + "&xxbh=";
		} else {
			href = href + "?xxbh=";
		}
		boolean bool = true;
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		Map map = new HashMap();
		List list = treeDao.getSchoolTree();
		if(list.size()>0){
			String xxbh = "",xxmc = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				xxbh = Validate.isNullToDefault(map.get("XXBH"), "").toString();
				xxmc = Validate.isNullToDefault(map.get("XXMC"), "").toString();
				children.add(new ExtTreeNode(xxbh, xxmc, bool , true, false,href, target,leaf));
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
}
