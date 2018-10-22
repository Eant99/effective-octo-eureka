package com.googosoft.service.xmgl.xmsb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.xmgl.xmsb.XmsbDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("xmsbService")
public class XmsbService {
	@Resource(name="xmsbDao")
	private XmsbDao xmsbDao;
	//保存项目申报主表
	public int doAddZb(PageData pd) {
		return xmsbDao.doAddZb(pd);
	}
	//保存项目申报明细表
	public int doAddMxb(PageData pd) {
		return xmsbDao.doAddMxb(pd);
	}
	/**
	 * 项目申报主表的详细信息
	 * @param guid 
	 * @return
	 */
	public Map getMapXmsbByGuid(String guid){
		return xmsbDao.getMapXmsbByGuid(guid);
	}
	/**
	 * 编辑项目申报主表
	 * 
	 */
	public int updXmsb(PageData pd,String guid) {
		return xmsbDao.updXmsb(pd,guid);
	}
	/**
	 * 项目申报明细表的详细信息
	 * 
	 */
	public Map getMapMxbByGuid(String zbid) {
		return xmsbDao.getMapMxbByGuid(zbid);
	}
	/**
	 * 编辑项目申报明细表
	 * 
	 */
	public int updMxbxx(PageData pd,String zbid) {
		return xmsbDao.updMxbxx(pd,zbid);
	}
	/**
	 * 批量删除
	 * @param 
	 * @return
	 */
	@Transactional
	public int doDelete(String guid) {
		String array[] = guid.split("','");
		for(int i = 0;i<array.length;i++) {
		List list =	xmsbDao.getxx(array[i]);
		for(int j=0;j<list.size();j++) {
			Map map = (Map) list.get(j);
			String guid1 = (String) map.get("guid");
			xmsbDao.doDelZb(guid1);
			xmsbDao.dodelMxb(guid1);		
		}			
		}
		return 1;
	}
	/**
	 * 提交
	 * @param 
	 * @return
	 */
	@Transactional
	public int doUpdate(String guid) {
		String array[] = guid.split("','");
		for(int i = 0;i<array.length;i++) {
		List list =	xmsbDao.getxx(array[i]);
		for(int j=0;j<list.size();j++) {
			Map map = (Map) list.get(j);
			String guid1 = (String) map.get("guid");
			xmsbDao.doUpdate(guid1);
		}			
		}
		return 1;
	}
	/**
	 * 得到附件设置信息
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getFjxx(String zbid) {
		return xmsbDao.getFjxx(zbid);
	}
	
	public List<Map<String, Object>> getFjxxByXmlx(String xmlxbh) {
		return xmsbDao.getFjxxByXmlx(xmlxbh);
	}
	
	public int getObjectById(PageData pd ) {
		return xmsbDao.getObjectById(pd);
	}
	
	
	
	
	
	
	
	
	////////////////////////////////////////////////////////
	/**
	 * 获取树
	 * @param pd
	 * @param jb
	 * @param rootPath
	 * @return
	 */
	public Object getXmsbNode(PageData pd, String sjfl,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String leaf = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		bool = xmsbDao.checkIsLeaf(sjfl);
		if (href.indexOf("?") > 0) {
			href = href + "&guid=";
		} else {
			href = href + "?flmc=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		//List list = xmsb.xmsbTree(sjfl);
		List list=null;
		if("root".equals(sjfl)){
			String datas = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/xmflTree/getRootFlow", "sjfl="+sjfl);
			Gson gson = new Gson();  
		    list = gson.fromJson(datas, new TypeToken<List>(){}.getType());  
		}else{
			String datas = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/xmflTree/getXjFlow", "sjfl="+sjfl);
			Gson gson = new Gson();  
		    list = gson.fromJson(datas, new TypeToken<List>(){}.getType());  
		}
		
		Map map = new HashMap();
		if(list.size()>0){
			String guid = "",flmc = "",fljb = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				guid = Validate.isNullToDefault(map.get("GUID"), "").toString();
				flmc = Validate.isNullToDefault(map.get("FLMC"), "").toString();
				fljb = Validate.isNullToDefault(map.get("FLJB"), "").toString();
				bool = xmsbDao.checkIsLeaf(guid);
				if(bool){
					children.add(new ExtTreeNode(guid, flmc, bool , true, false, href.length() > 0 ? href + guid + "&fljb="+fljb : href, target,leaf));
				}else{
					children.add(new ExtTreeNode(guid, flmc, bool , true, false, href.length() > 0 ? href + guid + "&fljb="+fljb : href, target,icon));
				}
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取树
	 * @param pd
	 * @param jb
	 * @param rootPath
	 * @return
	 */
	public Object getCgflNode(PageData pd, String cgfl,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String leaf = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
		String target = pd.getString("target");
		String href = "";
		boolean bool = false;
		if (href.indexOf("?") > 0) {
			href = href + "&guid=";
		} else {
			href = href + "?flmc=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		//List list = xmsb.cgflTree(cgfl);
		String datas="";
		if("root".equals(cgfl)){
			datas = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/cgflTree/getRootflow", "cgfl="+cgfl);
		}else {
			datas = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/cgfloTree/getXjFlow", "cgfl="+cgfl);
		}
		Gson gson = new Gson();  
		List list = gson.fromJson(datas, new TypeToken<List>(){}.getType());  
		Map map = new HashMap();
		if(list.size()>0){
			String guid = "",flmc = "",fljb = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				guid = Validate.isNullToDefault(map.get("GUID"), "").toString();
				flmc = Validate.isNullToDefault(map.get("MLMC"), "").toString();
				fljb = Validate.isNullToDefault(map.get("MLDM"), "").toString();
				//bool = xmsb.cgflIsLeaf(fljb);
				String result = SendHttpUtil.sendPost(SystemSet.address+"/commonTree/cgflTree/IsLeafFlow", "cgfl="+fljb);
				System.out.println("result: "+result);
				result=result.substring(8,result.indexOf("}"));
				if("root".equals(fljb) ||!result.equals("0") ){
					bool= false;
				}else{
					bool= true;
				}

				if(bool){
					children.add(new ExtTreeNode(fljb, flmc, bool , true, false,"" , target,leaf));
				}else{
					children.add(new ExtTreeNode(fljb, flmc, bool , true, false,"" , target,icon));
				}
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
}
