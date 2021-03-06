package com.googosoft.service.bmyssb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.activiti.engine.IdentityService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.WorkflowContant;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.base.ModelDao;
import com.googosoft.dao.bmyssb.BmyssbDao;
import com.googosoft.dao.bmyssb.BmyssbDao1;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.base.WorkflowService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("bmyssbService1")
public class BmyssbService1 extends BaseService {
	@Autowired
	IdentityService identityService;
	@Autowired
	WorkflowService workflowService;
	@Autowired
	ModelDao modelDao;
	@Autowired
	private RuntimeService runtimeService;

	@Resource(name="bmyssbDao1")
	public BmyssbDao1 bmyssbDao;
	
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String bzid = pd.getString("bzid");
		System.err.println("_____________sid1_"+bzid);
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = bmyssbDao.PowerModels();
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
				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"(" + bmh + ")"+mc+"&bmh="+map.get("BMH")+"&bzid="+bzid+"&dm="+map.get("DM") : Href, Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"("+ bmh +")"+mc+"&bmh="+map.get("BMH")+"&bzid="+bzid+"&dm="+map.get("DM") : Href, Target,icon));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	public Object getDwNode(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String bzid = pd.getString("bzid");
		System.err.println("_____________sid_"+bzid);
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&dwbh=";
		} else {
			Href = Href + "?dwbh=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  bmyssbDao.GetDwModels(sjdw);
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
				    children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() +"&qc="+"("+ bmh +")" +mc+"&bmh="+map.get("BMH")+"&bzid="+bzid+"&dm="+map.get("DM"): Href, Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, false, Href.length() > 0 ? Href + map.get("DWBH").toString() +"&qc="+"("+bmh+")"+mc+"&bmh="+map.get("BMH")+"&bzid="+bzid+"&dm="+map.get("DM"): Href, Target,icon));
                }
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	
	public int doAdd(PageData pd) {
		System.err.println("dm_:"+pd.getString("dm"));
		String bzid = pd.getString("bzid");
		switch (pd.getString("dm")) {
			case "2":return bmyssbDao.doAddjbxxb(pd,bzid);
			case "3":return doAddsrysb(pd);
			case "4":return doAddzxywfzcysb(pd);
			case "5":return doAddWxzlfzcysb(pd,bzid);
			case "6":return doAddZfzgysb(pd,bzid);
			case "7":return doAddCzzcjxmb(pd);
			case "8":return doAddRcgyzfysb(pd,bzid);
			case "9":return doAddZfzgysb(pd,bzid);
			case "10":return doAddZfzgysb10(pd,bzid);
			case "11":return doAddZysb(pd,bzid);
			case "12":return doAddZfcgmx(pd,bzid);//政府采购明细（办公家具）
			case "13":return doAddZfcgmx2(pd,bzid);//政府采购明细（通用设备）
			case "14":return doAddZfcgmx3(pd,bzid);//政府采购明细3（专用设备）
			default:
				return bmyssbDao.doAddfmb(pd);
		}
	}
	
	public int doUpdate(PageData pd) {
		switch (pd.getString("dm")) {
			case "2":return bmyssbDao.doUpdatejbxxb(pd);
			case "3":return bmyssbDao.doUpdatesrysb(pd);
			case "4":return bmyssbDao.doUpdatefmb(pd);
			case "5":return bmyssbDao.doUpdatefmb(pd);
			case "6":return bmyssbDao.doUpdatefmb(pd);
			case "7":return bmyssbDao.doUpdatefmb(pd);
			case "8":return bmyssbDao.doUpdatefmb(pd);
			default:return bmyssbDao.doUpdatefmb(pd);
		}
	}
	public Map<?,?> getObjectById(PageData pd,String guid){
		switch (pd.getString("dm")) {
			case "2":return bmyssbDao.getObjectById2(guid);
//			case "3":return bmyssbDao.getObjectById3(guid);
//			case "4":return bmyssbDao.getObjectById4(guid);
//			case "5":return bmyssbDao.getObjectById5(guid);
			case "6":return bmyssbDao.getObjectById6(guid);
			case "7":return bmyssbDao.getObjectById7(guid);
//			case "8":return bmyssbDao.getObjectById7(guid);
			default:return bmyssbDao.getObjectById1(guid);
		}
	}
	public List getxlkinfo() {
		return bmyssbDao.getxlkinfo();
	}
	public List getLengthBydm(String dm) {
		return bmyssbDao.getLengthBydm(dm);
	}
	public void doSubmit(String dwbh) {
		bmyssbDao.doSubmit(dwbh);
	}
	public String getBmmcByDwbh(String userDwbh) {
		return bmyssbDao.getBmmcByDwbh(userDwbh);
	}
	public String getRymcByRybh(String userRybh) {
		return bmyssbDao.getRymcByRybh(userRybh);
	}
	
	public List getObjectsById(PageData pd,String guid){
		switch (pd.getString("dm")) {
			case "3":return bmyssbDao.getObjectById3(guid);
			case "4":return bmyssbDao.getZxywfzcysById(guid);//专项业务费支出预算
			case "5":return bmyssbDao.getWxzlfById(guid);
//			case "6":return bmyssbDao.getObjectById6(guid);
//			case "7":return bmyssbDao.getObjectById7(guid);
			case "8":return bmyssbDao.getBmyssbById(guid);
			case "9":return bmyssbDao.getObjectById9(guid);
			case "10":return bmyssbDao.getObjectById10(guid);
			case "11":return bmyssbDao.getObjectById11(guid);
			
			default:return bmyssbDao.getObjectById3(guid);
		}
	}
	
	
	
	public Map getMxObjectById(PageData pd,String guid) {
		switch (pd.getString("dm")) {
			case "9":return bmyssbDao.getMxObjectById(guid);
			case "10":return bmyssbDao.getMxObjectById2(guid);
			case "11":return bmyssbDao.getMxObjectById3(guid);
			
			default:return (Map) bmyssbDao.getObjectById3(guid);
	}
	}
	public List getMxObjectsById(PageData pd,String guid) {
		switch (pd.getString("dm")) {
			case "9":return bmyssbDao.getMxObjectsById(guid);	
			case "10":return bmyssbDao.getMxObjectsById2(guid);	
			case "11":return bmyssbDao.getMxObjectsById3(guid);	
			default:return bmyssbDao.getObjectById3(guid);
		}
	}
	@Transactional
	public int doAddsrysb(PageData pd) {
		String tbbm = WindowUtil.getText(pd.getString("tbbm"));
		System.out.println("tbbm------------"+tbbm);
		String bzid = pd.getString("bzid");
		bmyssbDao.doDelete(tbbm);
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("___________"+map.get("guid")+map.get("xmmc")+map.get("yjwcse")+map.get("wcsj")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("xmmc"))){
					continue;
				}else {
					bmyssbDao.insertTxrysx(map, pd,bzid,json.get("tbbm"));//添加
				}
			}
			
		}
		return 1;
	}
	
	@Transactional
	public int doAddzxywfzcysb(PageData pd) {
		System.err.println("_________"+pd.getString("TBBM1"));
		bmyssbDao.doDeleteZxywfzcysb(pd.getString("TBBM1"));
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("___________"+map.get("guid")+map.get("xmmc")+map.get("yjwcse")+map.get("wcsj")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("xmmc"))){
					continue;
				}else {
					bmyssbDao.insertZxywfzcysb(map, pd,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	
	@Transactional
	public int doAddWxzlfzcysb(PageData pd,String bzid) {
		String guid = pd.getString("guid");
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteWxzlfzcysb(json.get("tbbm"));
		System.out.println("json====1="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("guid")+map.get("XMMC")+map.get("yjwcse")+map.get("wcsj")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("XMMC"))){
					continue;
				}else {
					bmyssbDao.insertWxzlfzcysb(map, pd, json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	
	@Transactional
	public int doAddZfzgysb(PageData pd,String bzid) {
		String tbbm = WindowUtil.getText(pd.getString("tbbm"));
		bmyssbDao.doDeleteZfcgysb(tbbm);
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("pmbm")+map.get("pmmc")+map.get("sbfl")+map.get("ggyq")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("pmbm"))){
					continue;
				}else {
					bmyssbDao.insertZfcgysb(map, pd, bzid,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	@Transactional
	public int doAddZfzgysb10(PageData pd,String bzid) {
		String tbbm = WindowUtil.getText(pd.getString("tbbm"));
		bmyssbDao.doDeleteZfcgysb10(tbbm);
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("pmbm")+map.get("pmmc")+map.get("sbfl")+map.get("ggyq")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("PMBM"))){
					continue;
				}else {
					bmyssbDao.insertZfcgysb10(map, pd, bzid,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	
	
	@Transactional
	public int doAddZysb(PageData pd,String bzid) {
		String tbbm = WindowUtil.getText(pd.getString("tbbm"));
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteZysb(CommonUtil.getBeginText(json.get("tbbm")+""));
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("pmbm")+map.get("pmmc")+map.get("sbfl")+map.get("ggyq")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("PMBM"))){
					continue;
				}else {
					bmyssbDao.insertZysb(map, pd, bzid,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	
	@Transactional
	public int doAddZfcgmx(PageData pd,String bzid) {
		Gson gson = new Gson();
		Map<String, Object> jsonStr = gson.fromJson(pd.getString("jsonStr"), new TypeToken<HashMap<String,Object>>(){}.getType());
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteZfcgmxb(LUser.getDwbh());
		bmyssbDao.doDeleteZfcgmxmxb(LUser.getDwbh());
		System.err.println("json====="+json);
		System.err.println("jsonStr====="+jsonStr);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		System.out.println("txryxxList==="+txryxxList);
		bmyssbDao.insertZfcgmxb( pd, bzid,jsonStr.get("cgdw"),jsonStr.get("xmmc"),jsonStr.get("xmqqdyfzr"),jsonStr.get("dwysbm"),jsonStr.get("lxfs"),jsonStr.get("tbbm"));
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("flmc")+json.get("tbbm")+map.get("sbfl")+map.get("ggyq")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("flmc"))){
					continue;
				}else {
					bmyssbDao.insertZfcgmxmxb( map,pd,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	@Transactional
	public int doAddZfcgmx2(PageData pd,String bzid) {
		Gson gson = new Gson();
		Map<String, Object> jsonStr = gson.fromJson(pd.getString("jsonStr"), new TypeToken<HashMap<String,Object>>(){}.getType());
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteZfcgmxb(LUser.getDwbh());
		bmyssbDao.doDeleteZfcgmxmxb(LUser.getDwbh());
		System.err.println("json====="+json);
		System.err.println("jsonStr====="+jsonStr);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		System.out.println("txryxxList==="+txryxxList);
		bmyssbDao.insertZfcgmxb( pd, bzid,jsonStr.get("cgdw"),jsonStr.get("xmmc"),jsonStr.get("xmqqdyfzr"),jsonStr.get("dwysbm"),jsonStr.get("lxfs"),jsonStr.get("tbbm"));
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("flmc")+json.get("tbbm")+map.get("sbfl")+map.get("ggyq")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("flmc"))){
					continue;
				}else {
					bmyssbDao.insertZfcgmxmxb( map,pd,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	@Transactional
	public int doAddZfcgmx3(PageData pd,String bzid) {
		Gson gson = new Gson();
		Map<String, Object> jsonStr = gson.fromJson(pd.getString("jsonStr"), new TypeToken<HashMap<String,Object>>(){}.getType());
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteZfcgmxb(LUser.getDwbh());
		bmyssbDao.doDeleteZfcgmxmxb(LUser.getDwbh());
		System.err.println("json====="+json);
		System.err.println("jsonStr====="+jsonStr);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		System.out.println("txryxxList==="+txryxxList);
		bmyssbDao.insertZfcgmxb( pd, bzid,jsonStr.get("cgdw"),jsonStr.get("xmmc"),jsonStr.get("xmqqdyfzr"),jsonStr.get("dwysbm"),jsonStr.get("lxfs"),jsonStr.get("tbbm"));
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("flmc")+json.get("tbbm")+map.get("sbfl")+map.get("ggyq")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("flmc"))){
					continue;
				}else {
					bmyssbDao.insertZfcgmxmxb( map,pd,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	@Transactional
	public int doAddCzzcjxmb(PageData pd) {
		String guid = pd.getString("guid");
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		Map<String, Object> jsonStr = gson.fromJson(pd.getString("jsonStr"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteCzzcjxmb(CommonUtil.getBeginText(json.get("tbbm")+""));
		System.err.println("_____________"+json);
		bmyssbDao.doAddczzcjx(pd,jsonStr.get("xmmc"),jsonStr.get("tbbm"),jsonStr.get("zgbm"),jsonStr.get("xmssdw"),jsonStr.get("xmfzr"),
				jsonStr.get("zgbmbm"),jsonStr.get("lxdh"),jsonStr.get("xmlb"),jsonStr.get("xmlx"),jsonStr.get("kssj"),
				jsonStr.get("jssj"),jsonStr.get("zjze"),jsonStr.get("czbk"),jsonStr.get("sysr"),jsonStr.get("jysr"),jsonStr.get("qt"),
				jsonStr.get("csyjjsm"),jsonStr.get("xmdwzngs"),jsonStr.get("xmgkzynrjyt"),jsonStr.get("xmlxdyj"),
				jsonStr.get("xmsbdkxxhbyx"),jsonStr.get("cqmb"),jsonStr.get("ndmb"),
				
				jsonStr.get("slzb1"),jsonStr.get("slzb2"),jsonStr.get("slzb3"),jsonStr.get("slzb4"),jsonStr.get("slzb5"),jsonStr.get("slzb6"),
				jsonStr.get("zlzb1"),jsonStr.get("zlzb2"),jsonStr.get("zlzb3"),jsonStr.get("zlzb4"),jsonStr.get("zlzb5"),jsonStr.get("zlzb6"),
				jsonStr.get("sxzb1"),jsonStr.get("sxzb2"),jsonStr.get("sxzb3"),jsonStr.get("sxzb4"),jsonStr.get("sxzb5"),jsonStr.get("sxzb6"),
				jsonStr.get("cbzb1"),jsonStr.get("cbzb2"),jsonStr.get("cbzb3"),jsonStr.get("cbzb4"),jsonStr.get("cbzb5"),jsonStr.get("cbzb6"),
				
				jsonStr.get("jjxyzb1"),jsonStr.get("jjxyzb2"),jsonStr.get("jjxyzb3"),jsonStr.get("jjxyzb4"),jsonStr.get("jjxyzb5"),jsonStr.get("jjxyzb6"),
				jsonStr.get("shxyzb1"),jsonStr.get("shxyzb2"),jsonStr.get("shxyzb3"),jsonStr.get("shxyzb4"),jsonStr.get("shxyzb5"),jsonStr.get("shxyzb6"),
				jsonStr.get("stxyzb1"),jsonStr.get("stxyzb2"),jsonStr.get("stxyzb3"),jsonStr.get("stxyzb4"),jsonStr.get("stxyzb5"),jsonStr.get("stxyzb6"),
				jsonStr.get("kcxyxzb1"),jsonStr.get("kcxyxzb2"),jsonStr.get("kcxyxzb3"),jsonStr.get("kcxyxzb4"),jsonStr.get("kcxyxzb5"),jsonStr.get("kcxyxzb6"),
				
				jsonStr.get("ndslzb1"),jsonStr.get("ndslzb2"),jsonStr.get("ndslzb3"),jsonStr.get("ndslzb4"),jsonStr.get("ndslzb5"),jsonStr.get("ndslzb6"),
				jsonStr.get("ndzlzb1"),jsonStr.get("ndzlzb2"),jsonStr.get("ndzlzb3"),jsonStr.get("ndzlzb4"),jsonStr.get("ndzlzb5"),jsonStr.get("ndzlzb6"),
				jsonStr.get("ndsxzb1"),jsonStr.get("ndsxzb2"),jsonStr.get("ndsxzb3"),jsonStr.get("ndsxzb4"),jsonStr.get("ndsxzb5"),jsonStr.get("ndsxzb6"),
				jsonStr.get("ndcbzb1"),jsonStr.get("ndcbzb2"),jsonStr.get("ndcbzb3"),jsonStr.get("ndcbzb4"),jsonStr.get("ndcbzb5"),jsonStr.get("ndcbzb6"),
				
				jsonStr.get("ndjjxyzb1"),jsonStr.get("ndjjxyzb2"),jsonStr.get("ndjjxyzb3"),jsonStr.get("ndjjxyzb4"),jsonStr.get("ndjjxyzb5"),jsonStr.get("ndjjxyzb6"),
				jsonStr.get("ndshxyzb1"),jsonStr.get("ndshxyzb2"),jsonStr.get("ndshxyzb3"),jsonStr.get("ndshxyzb4"),jsonStr.get("ndshxyzb5"),jsonStr.get("ndshxyzb6"),
				jsonStr.get("ndstxyzb1"),jsonStr.get("ndstxyzb2"),jsonStr.get("ndstxyzb3"),jsonStr.get("ndstxyzb4"),jsonStr.get("ndstxyzb5"),jsonStr.get("ndstxyzb6"),
				jsonStr.get("ndkcxyxzb1"),jsonStr.get("ndkcxyxzb2"),jsonStr.get("ndkcxyxzb3"),jsonStr.get("ndkcxyxzb4"),jsonStr.get("ndkcxyxzb5"),jsonStr.get("ndkcxyxzb6"),
				
				jsonStr.get("shgz1"),jsonStr.get("shgz2"),jsonStr.get("shgz3"),jsonStr.get("shgz4"),
				jsonStr.get("shgz5"),jsonStr.get("shgz6"),jsonStr.get("shgz7"),jsonStr.get("shgz8"),jsonStr.get("qtwt")
				);
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("__________in_"+map.get("guid")+map.get("xmmc")+map.get("yjwcse")+map.get("wcsj")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				if(Validate.isNull(map.get("xmmc"))){
					continue;
				}else {
					bmyssbDao.insertCzzcjxmb(map, pd, guid);//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	
	/**
	 * dm=8 日常公用支出预算表 Rcgyzfysb
	 * @param pd
	 * @return
	 */
	@Transactional
	public int doAddRcgyzfysb(PageData pd,String pzid) {
		String guid = pd.getString("guid");
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"), new TypeToken<HashMap<String,Object>>(){}.getType());
		bmyssbDao.doDeleteRcgyzfysb(json.get("tbbm"));
		System.out.println("json====="+json);
		List<Map<String,Object>> txryxxList = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : txryxxList) {
			System.out.println("_______map____"+map.get("guid")+map.get("xmmc")+map.get("yjwcse")+map.get("wcsj")+map.get("bz"));
			if(Validate.isNull(map.get("guid"))) {
				System.err.println("1___________");
				if(Validate.isNull(map.get("fymc"))){
					System.err.println("2___________");
					continue;
				}else {
					System.err.println("3___________");
					bmyssbDao.insertRcgyzfysb(map, pd,json.get("tbbm"));//添加
				}
			}else {
				//bmyssbDao.insertTxryup(map, pd,guid);//更新
			}
			
		}
		return 1;
	}
	public int doDeleteAll(String userDwbh,int year) {
		int result = bmyssbDao.doDeleteAll(userDwbh,year);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"单位基础信息");
		}
		return result;
	}
	
	public int checkIsAdd(String dwbh,int year) {
		if(bmyssbDao.checkIsAdd(dwbh,year)>0) {
			return 1; 
		}else {
			return 0;
		}
	}
	public int checkIsAdd2(String dwbh,int year) {
		if(bmyssbDao.checkIsAdd2(dwbh,year)>0) {
			return 1; 
		}else {
			return 0;
		}
	}

	public List getList(List list, String ryid) {
		List lists = new ArrayList<String>();
		lists.add(ryid);
		if (list.size() == 0) {
			return lists;
		}
		for (int i = 0, len = list.size(); i < len; i++) {
			Map map = (Map) list.get(i);
			String people = Validate.isNullToDefaultString(map.get("RYID"), "");
			if (!people.equals(ryid)) {
				lists.add(people);
			}
		}
		return lists;
	}

	/**
	 * 提交
	 * 
	 * @param guid
	 * @return
	 */
	@Transactional
	public int submit(String guid, String mkbh) {
		int m = 0;
		Map<String, Object> variables = new HashMap<String, Object>();
		// 开始创建流程
		identityService.setAuthenticatedUserId(LUser.getGuid());
		ProcessInstance ps = null;
		// 开始为各个节点的操作人赋值
		Map map = bmyssbDao.getZbInfoByGuid(guid);
		// 申请人
		variables.put("pass", true);
		variables.put("sqr", LUser.getGuid());
		// 领导信息
		Map dwMap = bmyssbDao.getDwldSql();
		// 1 部门领导
		String bmfzr = Validate.isNullToDefaultString(dwMap.get("BMFZR"), "");
		List bmfzrList = bmyssbDao.getBsqrList(mkbh, bmfzr);
		List bmfzrs = getList(bmfzrList, bmfzr);
		variables.put("bmlds", bmfzrs);
		// 2 业务部门
		// 系部负责人
		List<Map> xblist = bmyssbDao.getxbfzr();
		List list = new ArrayList<>();
		for (int i = 0; i < xblist.size(); i++) {
			list.add(xblist.get(i).get("guid"));
		}
		variables.put("ywbms", list);
		// 3 财务处
		List cwc = bmyssbDao.getCwc();
		variables.put("cwcs", cwc);
		// 4 财务处长
		// String cwcz=bmyssbDao.getcwcz();
		List<Map> cwlist = bmyssbDao.getcwcz();
		List Cwlist = new ArrayList<>();
		for (int j = 0; j < cwlist.size(); j++) {
			Cwlist.add(cwlist.get(j).get("guid"));
		}
		variables.put("cwczs", Cwlist);
		variables.put("tjxz", true);
		// 5 此处需要判断 院长
		String yz = Validate.isNullToDefaultString(bmyssbDao.getXxxz(), "");
		List yzList = bmyssbDao.getBsqrList(mkbh, yz);
		List yzs = getList(yzList, yz);
		variables.put("yzs", yzs);
		// 启动对应的流程--bmys
		ps = workflowService.startProcess(variables, "bmys");
		// 获取任务节点
		String procinstid = Validate.isNullToDefaultString(ps.getId(), "");
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		// 执行任务
		workflowService.completeTask(task, variables);
		// 任务执行完毕，更新主表的信息
		if (bmyssbDao.updateProcinstid(guid, procinstid) > 0) {
			String shzt = "001";
			m = bmyssbDao.updateSHXX(guid, shzt, "");// 其他提交给部门负责人审核
		}
		return m;
	}

	/**
	 * 审批通过
	 * 
	 * @param leave
	 */
	@Transactional
	public void approveLeaveInfo(OA_SHYJB shyjb, String guid, String procinstid, String ZCYSHZ,String shyj) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		System.err.println("procinstid==>  " + procinstid);
		String taskDefKey = bmyssbDao.getTaskNodeId(task.getId());
		if ("bmld".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "002",shyj);// 1 部门领导通过,业务部门负责人审核
		}
		if ("ywbm".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "003",shyj);// 2 业务部门负责人都通过，财务处 审核
		}
		if ("cwc".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "004",shyj);// 3 财务处通过 ,财务处长审核
		}
		if ("cwcz".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "005",shyj);// 4 财务处长通过，根据金额 判断是否需要 院长审核
		}
		// 获取 自定义金额
		// String zdyje=bmyssbDao.getzdyje();
		Float zdyje = Float.parseFloat(bmyssbDao.getzdyje());
		Float ZCYSHZ1 = Float.parseFloat(ZCYSHZ);
		if (zdyje < ZCYSHZ1) {
			variables.put("tjxz", true);
		} else {
			variables.put("tjxz", false);
		}

		if ("yz".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "006",shyj);// 院长通过
		}
		variables.put("pass", true);
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.PASS);
		shyjb.setProcinstid(procinstid);
		 shyjb.setShyj(shyj);
		bmyssbDao.doAddShyj(shyjb);
	}

	/**
	 * 退回审批退回
	 * 
	 * @param leave
	 */
	@Transactional
	public void rejectleaveinfo(OA_SHYJB shyjb, String guid, String procinstid, String ZCYSHZ,String shyj) {
		Map<String, Object> variables = new HashMap<String, Object>();
		Task task = workflowService.queryUserTaskByInstanceId(LUser.getGuid(), procinstid);
		String taskDefKey = bmyssbDao.getTaskNodeId(task.getId());
		Map<String, Object> var = runtimeService.getVariables(procinstid);
		variables.put("pass", false);
		if ("bmld".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "012",shyj);// 部门负责任审核退回
		}
		if ("ywbm".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "013",shyj);//
		}
		if ("cwc".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "014",shyj);//
		}
		if ("cwcz".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "015",shyj);//
		}
		if ("yz".equals(taskDefKey)) {
			bmyssbDao.doStatus(guid, "016",shyj);// 院长审核退回
		}
		workflowService.completeTask(task, variables);
		shyjb.setTaskid(task.getId());
		shyjb.setShzt(WorkflowContant.REJECT);
		shyjb.setProcinstid(procinstid);
		 shyjb.setShyj(shyj);
		bmyssbDao.doAddShyj(shyjb);
	}
}
