package com.googosoft.service.systemset.qxgl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.systemset.qxgl.GlqxbDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


/**
 * 管理权限service
 * @author googosoft
 *
 */
@Service("glqxbService")
public class GlqxbService extends BaseService{

	@Resource(name="glqxbDao")
	public GlqxbDao glqxbDao;
	
	/**
	 * 通过人员编号获取已授权信息
	 */
	public List findListByRybh(String rybh) {
		return glqxbDao.findListByRybh(rybh);
	}
	
	/**
	 * 通过下级单位查询该单位的上级单位
	 */
	public String findSjdwByDwbh(String dwbh){
		return glqxbDao.findSjdwByDwbh(dwbh);
	}
	
	/**
	 * 通过单位编号查询Map
	 */
	public Map findMapByDwbh(String dwbh) {
		return glqxbDao.findMapByDwbh(dwbh);
	}
	/**
	 * 保存管理权限
	 * @param qxsz 单位或者资产类别编号
	 * @param szry 分配权限的人员
	 * @param flag 标志：1、资产分类权限  2、单位权限
	 * @return
	 */
	public int doSave(String qxsz, String szry, String flag) {
		return glqxbDao.doSave(qxsz,szry,flag);
	}
	
	
	/**
	 * 获取权限下的单位（单位人员树）
	 * @param loginrybh登录的人员编号 
	 * @return
	 */
	public Object getPowerNode(PageData pd, String loginrybh) {
		String Target=pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=glqxbDao.PowerModels(loginrybh);
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
                	 List LRybs = glqxbDao.GetModels(dwbh);
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
	 * 单位人员树
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
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
		List dList =  glqxbDao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				List dwcount = glqxbDao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = glqxbDao.GetModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target));
				}
			}
		}
		dList = glqxbDao.GetModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				children.add(new ExtTreeNode(map.get("RYBH").toString(),
						"(" + map.get("RYGH").toString() + ")" + map.get("XM").toString(), true, false,
						Href.length() > 0 ? Href + map.get("RYBH").toString() : Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/vector.gif"));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取权限下的单位（单位人员树）
	 * @param pd
	 * @param loginrybh 登录的人员编号 
	 * @param rootPath
	 * @return
	 */
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
//		String icon = rootPath+"/static/plugins/ext/resources/images/default/s.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = glqxbDao.PowerModels(loginrybh);
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
				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"(" + bmh + ")"+mc+"&bmh="+map.get("BMH") : Href, Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"("+ bmh +")"+mc+"&bmh="+map.get("BMH") : Href, Target));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取权限下的单位（单位人员树）1
	 * @param pd
	 * @param loginrybh 登录的人员编号 
	 * @param rootPath
	 * @return
	 */
	public Object getPowerDwNode1(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = glqxbDao.PowerModels(loginrybh);
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
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"(" + bmh + ")"+mc+"&bmh="+map.get("BMH") : Href, Target));
				}else{
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"("+ bmh +")"+mc+"&bmh="+map.get("BMH") : Href, Target,icon));
				}
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}

	/**
	 * 获取某个单位的下级单位信息
	 * @param pd
	 * @param sjdw 上级单位编号
	 * @param rootPath
	 * @return
	 */
	public Object getDwNode(PageData pd, String sjdw,String rootPath) {
		//String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
//		String icon = rootPath+"/static/plugins/ext/resources/images/default/s.gif";
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
		List dList =  glqxbDao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString());
				if(xjcount<=0) {
				    children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH"): Href, Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() +"&qc="+"("+bmh+")"+mc+"&bmh="+map.get("BMH"): Href, Target));
                }
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取某个单位的下级单位信息1
	 * @param pd
	 * @param sjdw 上级单位编号
	 * @param rootPath
	 * @return
	 */
	public Object getDwNode1(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
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
		List dList =  glqxbDao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString());
				if(xjcount<=0) {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH"): Href, Target));
				}else{
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() +"&qc="+"("+bmh+")"+mc+"&bmh="+map.get("BMH"): Href, Target,icon));
				}
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取权限下的单位（单位人员树）
	 * @param loginrybh登录的人员编号 
	 * @return
	 */
	public Object getPowerDwRyNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=glqxbDao.PowerModels(loginrybh);
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
                	 List LRybs = glqxbDao.GetModels(dwbh);
                     xjcount =LRybs.size();
                     if(xjcount<=0){
    				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() : Href, Target));
                     }else{
                    	 children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, Href.length() > 0 ? Href + map.get("DWBH").toString() : Href, Target,icon));
                     }
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, Href.length() > 0 ? Href + map.get("DWBH").toString() : Href, Target,icon));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}

	/**
	 * 单位人员权限树
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
	public Object getqxDwRyNode(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String iconry=rootPath+"/static/plugins/ext/resources/images/default/tree/vector.gif";
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
		List dList =  glqxbDao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				List dwcount = glqxbDao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if(xjcount <= 0){
					dwcount = glqxbDao.GetModels(dwbh);
					xjcount = dwcount.size();
					if(xjcount<=0){
   				     	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() : Href, Target));
                    }else{
                   	 	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() : Href, Target,icon));
                    }
				}else{
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() : Href, Target,icon));
				}
			}
		}
		dList = glqxbDao.GetModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				children.add(new ExtTreeNode(map.get("RYBH").toString(),
						"(" + map.get("RYGH").toString() + ")" + map.get("XM").toString(), true, false,
						Href.length() > 0 ? Href + "&rybh=" + map.get("RYBH").toString() : Href, Target,iconry));
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取权限下的地点（地点树）
	 * @param loginrybh登录的人员编号 
	 * @return
	 */
	public Object getPowerDdNode(PageData pd, String loginrybh,String rootpath) {
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
		List dList=glqxbDao.PowerDdModels(loginrybh);
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
    						Href.length() > 0 ? Href + map.get("DDBH").toString() : Href, Target));
                }else{
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, false, true,true, 
    						Href.length() > 0 ? Href + map.get("DDBH").toString() : Href, Target,icon));
                }

			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	/**
	 * 获取某个地点信息树
	 * @param sjdd
	 * @return
	 */
	public Object getDdNode(PageData pd, String sjdd,String rootPath) {
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
		List dList =  glqxbDao.GetDdModels(sjdd);
		String ddbh, ddh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				ddbh = map.get("DDBH").toString();
				ddh = map.get("DDH").toString();
				mc = map.get("MC").toString();
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString());
				if(xjcount<=0){
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, true, true, false, 
    						Href.length() > 0 ? Href + map.get("DDBH").toString() : Href, Target));
                }else{
    				children.add(new ExtTreeNode("P" + ddbh, "(" + ddh + ")" + mc, false, true, false,
    						Href.length() > 0 ? Href + map.get("DDBH").toString() : Href, Target,icon));
                }
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}

	public String checkBydwbh(String left, String right) {
		if(left.equals("oot")){
			left="000001";
		}
		String[] dwbhs = new String[]{};
		if(Validate.noNull(right)){
			dwbhs = right.split(",");
		}
		List sjdwList = glqxbDao.findSjdwByXjdw(left);
		String result = "";
		StringBuffer sb = new StringBuffer();
		int b = glqxbDao.checkRootNodeByDwbh(left);//判断所选单位是否是根节点
		Map rootMap=null;
		if(b>0){
			rootMap= glqxbDao.findRootNodeMapByDwbh(left);
		}
		if(sjdwList.size()>0 && dwbhs.length>0){
			for(int i=0;i<sjdwList.size();i++){
				Map map = (Map)sjdwList.get(i);
				String sjdwbh = map.get("DWBH")+"";
				for (int j = 0; j < dwbhs.length; j++) {
					String dwbh = dwbhs[j]+"";
					if (sjdwbh.equals(dwbh)) {
						result = "FAIL";
					}
				}
			}
		}
		if(Validate.isNull(result)){
			if(b>0){
				sb.append("<div class='sc_inline number_"+rootMap.get("DWBH")+"'>");
				sb.append("<div align='left' style='padding-left: 8px'>（"+rootMap.get("BMH")+"）"+rootMap.get("MC")+"</div>");
				sb.append("<input type='hidden' name='BHH' value='"+rootMap.get("DWBH")+"'></div>");
				result = sb.toString();
			}else{
				List list = initRightDwbhByDWBHS(left, dwbhs);
				String[] arr = (String[])list.toArray(new String[list.size()]);
				for(int i=0;i<arr.length;i++){
					Map map = glqxbDao.findMapByDwbh(arr[i]);
					sb.append("<div class='sc_inline number_"+map.get("DWBH")+"'>");
					sb.append("<div align='left' style='padding-left: 8px'>（"+map.get("BMH")+"）"+map.get("MC")+"</div>");
					sb.append("<input type='hidden' name='BHH' value='"+map.get("DWBH")+"'></div>");
				}
				result = sb.toString();
			}
		}
		return result;
	}
	
	/**
	 * 通过左边单位编号和右边单位编号重新组合
	 */
	public List<String> initRightDwbhByDWBHS(String left, String[] dwbhs) {
		/***新建一个动态list集合存放重新组合后的数据***/
		List<String> list = new ArrayList<String>();
		list.addAll(Arrays.asList(dwbhs));  
		String[] nullArr = {""};
		List nullList = Arrays.asList(nullArr); 
		list.removeAll(nullList);
		
		String tjDwbhs[] = findTongjiDwbh(left);//查询同级单位编号，不包括自身
		String xjDwbhs[] = findXiajiDwbh(left);//查询下级单位编号，不包括自身
		if(xjDwbhs.length>0 && dwbhs.length>0){
			for(int i = 0; i < xjDwbhs.length; i++){
				for (int j = 0; j < dwbhs.length; j++) {
					if(dwbhs[j].equals(xjDwbhs[i])){
						list.remove(dwbhs[j]);
					}
				}
			}
		}
		List<String> rList = new ArrayList<String>();
		if(tjDwbhs.length>0 && dwbhs.length>0){
			for(int i = 0; i < tjDwbhs.length; i++){
				for (int j = 0; j < dwbhs.length; j++) {
					if(tjDwbhs[i].equals(dwbhs[j])){
						rList.add(dwbhs[j]);
					}
				}
			}
		}
		if(tjDwbhs.length==rList.size()){//如果个数相同，则去掉子单位换成父单位
			String sjdw = "";
				sjdw = glqxbDao.findSjdwByDwbh(left);
				for(int i = 0; i < tjDwbhs.length; i++){
					list.remove(tjDwbhs[i]);
				}
				String[] arr = (String[])list.toArray(new String[list.size()]);
				int b = glqxbDao.checkParNodeByDwbh(sjdw,left);//判断单位是否是父节点，大于0是父节点
				if(b>0){//如果是根节点，则不再循环initRightDwbhByDWBHS(String left, String[] dwbhs)，换成父单位
					list.add(sjdw);
				}else{
					list = initRightDwbhByDWBHS(sjdw, arr);
				}
		}else{//如果个数不同，则将左边单位加入到右边单位中
			list.add(left);
		}
		return list;
	}
	
	/**
	 * 查出左边所选单位同级的所有单位编号
	 */
	public String[] findTongjiDwbh(String left) {
		String tjDwbhs = "";
		String arr[] = new String[0];
		List list = glqxbDao.findTongjiDwbh(left);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				tjDwbhs += map.get("DWBH")+",";
			}
		}
		if(tjDwbhs.length()>0){
			tjDwbhs = tjDwbhs.substring(0,tjDwbhs.length()-1);
		}
		if(Validate.noNull(tjDwbhs)){
			arr = tjDwbhs.split(",");
		}
		return arr;
	}
	
	/**
	 * 查出左边所选单位的所有下级单位编号（不包括本身）
	 */
	public String[] findXiajiDwbh(String left){
		List list = new ArrayList();
		String xjDwbhs = "";
		String arr[] = new String[0];
		list = glqxbDao.findXiajiDwbh(left);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				xjDwbhs += map.get("XJDW")+",";
			}
		}
		if(xjDwbhs.length()>0){
			xjDwbhs = xjDwbhs.substring(0,xjDwbhs.length()-1);
		}
		if(Validate.noNull(xjDwbhs)){
			arr = xjDwbhs.split(",");
		}
		return arr;
	}
	
	/**
	 * 获取权限下的单位(单位树)
	 * @param rybh
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public String GetPowerNode(String rybh,HttpServletRequest request){
		String icon;
		String path = request.getContextPath();
		String Href=request.getParameter("pageUrl");
		String Target=request.getParameter("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=glqxbDao.PowerModels(rybh);
		icon = path+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		Map map=new HashMap();
		StringBuffer json=new StringBuffer("[");
			String dwbh="",bmh="",mc="";
			int xjcount=0;
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefault(map.get("DWBH"), "").toString();
                bmh = map.get("BMH").toString();
                mc = map.get("MC").toString();
                xjcount=Integer.parseInt(map.get("XJCOUNT").toString());
                String bool="false";
                if(xjcount<=0){
	                json.append("{id:'D"+dwbh+"',");
	    			json.append("text:'("+bmh+")"+map.get("MC")+"',leaf:"+bool+",icon:'"+icon+"',");
	    			json.append("singleClickExpand:true,expanded:true,hrefTarget:'_self'},");
                }else{
	                json.append("{id:'D"+dwbh+"',");
	          		json.append("text:'("+bmh+")"+map.get("MC")+"',leaf:"+bool+",");
	          		json.append("singleClickExpand:true,expanded:true,hrefTarget:'_self'},");	
                }
			}
		if(json.length()>1){
			json.deleteCharAt(json.length()-1);
		}
		json.append("]");
		return json.toString();
	}
	/**
	 * 添加下级单位节点
	 * @param sjdwbh
	 * @return
	 */
	public String GetTreeNode(String sjdwbh,HttpServletRequest request){
		String icon;
		String path = request.getContextPath();
		List dList=new ArrayList();
		sjdwbh = sjdwbh.substring(1);
		dList=glqxbDao.GetDwModels(sjdwbh);
		icon = path+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		Map map=new HashMap();
		StringBuffer json=new StringBuffer("[");
		    String dwbh, bmh, mc;
	        int xjcount = 0;
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefault(map.get("DWBH"), "").toString();
                bmh = Validate.isNullToDefault(map.get("BMH"), "")+"";
                mc = Validate.isNullToDefault(map.get("MC"), "")+"";
	            xjcount=Integer.parseInt(map.get("XJCOUNT").toString());
	            if(xjcount<=0){
			         json.append("{id:'D"+dwbh+"',");
					 json.append("text:'("+bmh+")"+map.get("MC")+"',leaf:true,icon:'"+icon+"',");
					 json.append("singleClickExpand:true,expanded:false,hrefTarget:'_self'},");
			     }else{
					 json.append("{id:'D"+dwbh+"',");
					 json.append("text:'("+bmh+")"+map.get("MC")+"',leaf:false,");
					 json.append("singleClickExpand:true,expanded:false,hrefTarget:'_self'},");
			  }
	    	}
		if(json.length()>1){
			json.deleteCharAt(json.length()-1);
		}
		json.append("]");
		return json.toString();
	}
	/**
	 * 获取资产类别树
	 */
	public List findZclbTree(String rybh) {
		return glqxbDao.findZclbTree(rybh);
	}
	/**
	 * 获取资产类别树
	 * @param rybh
	 * @param request
	 * @return
	 */
	public String GetZclbNode(String rybh, HttpServletRequest request) {
		String path = request.getContextPath();
		List dList=glqxbDao.getZclbModels();
		String icon = path+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		Map map=new HashMap();
		StringBuffer json=new StringBuffer("[");
		String bzdm="",flh="",flmc="";
		for(int i=0;i<dList.size();i++){
			map=(Map)dList.get(i);
			bzdm = map.get("BZDM")+"";
            flh = map.get("FLH")+"";
            flmc = map.get("FLMC")+"";
            json.append("{id:'"+bzdm+"',");
    		json.append("text:'("+flh+")"+flmc+"',leaf:'false',icon:'"+icon+"',");
    		json.append("singleClickExpand:true,expanded:true,hrefTarget:'_self'},");
		}
		if(json.length()>1){
			json.deleteCharAt(json.length()-1);
		}
		json.append("]");
		return json.toString();
	}
	
	/**
	 * 获取分类权限
	 * @param rybh
	 * @return
	 */
	public String findFlqxFromGlqxb(String rybh){
		return glqxbDao.findFlqxFromGlqxb(rybh);
    }
	/**
	 * 获取人员权限的快捷语句
	 * @param rybh 人员编号
	 * @param colName 列名
	 * @param flag D  单位权限sql   F  资产分类权限sql
	 * @return
	 */
	public String getQxsql(String rybh,String colName,String flag){
		return glqxbDao.getQxsql(rybh,colName,flag);
	}
}
