package com.googosoft.controller.system.tree;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.system.tree.TreeService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/tree")
public class TreeController extends BaseController{
	
	@Resource(name="treeService")
	private TreeService treeService;
	
	
	/**
	 * 获取单位人员树
	 * @return
	 */
	@RequestMapping(value="/ryTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ryTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){//当前登录人的权限单位
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();//如果带有all，则将当前登录人置为000000（超级管理员）
				}
				return treeService.getPowerNode(pd,loginrybh);//获取当前登录人下的权限单位
			}else{
			    return treeService.getDwRyNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/dwTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dwTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		String mkbh = pd.getString("mkbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getDwNodeByMkbh(pd,dwbh,rootPath,mkbh);
			}
		}else{
			return "";
		}
	}
	@RequestMapping(value="/dwTrees",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dwTrees(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		String xxTree = "all";
		System.err.println("============="+dwbh);
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerDwNode(pd,loginrybh,rootPath,xxTree);
			}else{
				return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/dwTree2",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dwTree2(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = "000000";
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/dwTree4",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dwTree4(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = "000000";
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/alldwTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object alldwTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getAllDwNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取单位树
	 * @return
	 */
	@RequestMapping(value="/dw1Tree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object dw1Tree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			if(dwbh.equals("root")){
				return treeService.getPowerDwNode(pd,dwbh,rootPath);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取实验室单位树
	 * @return
	 */
	@RequestMapping(value="/sysTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object sysTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerSysNode(pd,loginrybh,rootPath);
//			}else if((SystemSet.Dwbh()).equals(dwbh)){
			}else if(dwbh.equals(SystemSet.Dwbh())){
				return treeService.getSysDwTreeNode(pd,dwbh,rootPath);
			}else{
				return treeService.getSysTreeNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取可以多选的单位树
	 * @return
	 */
	@RequestMapping(value="/multiSelDwTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object multiSelDwTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getPowerDwNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取地点树
	 * @param rybh 人员编号
	 * @return
	 */
	@RequestMapping(value="/ddTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ddTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String ddbh = pd.getString("ddbh");
		if(menu.equals("get-xjdw")){
			if(ddbh.equals("root")){
				return treeService.getPowerDdNode(pd,rootPath);
			}else{
			    return treeService.getDdNode(pd,ddbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取财政十大类树
	 */
	@RequestMapping(value="/czTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object czTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String dm = pd.getString("zcdm");
		if(menu.equals("get-flxx")){
			if(dm.equals("root")){
				return treeService.getPowerCzNode(pd,rootPath);
			}else{
			    return treeService.getCzNode(pd,dm,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 财政分类树
	 * @return
	 */
	@RequestMapping(value="/clflTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String czflTree(){
		String pid = this.getPageData().getString("parentid");
		List list = new ArrayList();
		list = treeService.getCzflList(pid);
		Map map = new HashMap();
		StringBuffer json = new StringBuffer();
		json.append("[");
		if(list.size() > 0){
			for(int i = 0; i < list.size(); i++){
				map = (Map)list.get(i);
				json.append("{");
				json.append("id:'" + map.get("BZDM") + "',");
				json.append("text:'("+map.get("FLH")+")"+map.get("FLMC")+"',");
				json.append("bh:'" + map.get("FLH") + "',");
				json.append("mc:'" + map.get("FLMC") + "',");
				if("0".equals(map.get("CNT")+"")){
					json.append("leaf:true,");
				}else{
					json.append("leaf:false,");
				}
				json.append("expanded:false");
				json.append("},");
			}
			json.delete(json.length() - 1, json.length());
		}
		json.append("]");
		return json.toString();
	}
	/**
	 * 资产分类树
	 * @return
	 */
	@RequestMapping(value="/zcflTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String zcflTree(){
		String flh = this.getPageData().getString("flh");
		String dmjc = this.getPageData().getString("dmjc");
		List list = treeService.getZcflList(flh,dmjc);
		Map map = new HashMap();
		StringBuffer json = new StringBuffer();
		json.append("[");
		for(int i=0;i<list.size();i++){
			map=(Map)list.get(i);
			flh = map.get("FLH")+"";
			String bool = (Integer.parseInt(map.get("CNT")+"")==0 ? "leaf:true" : "leaf:false");
			if(i<list.size()-1){
			    json.append("{id:'"+map.get("GID")+"',flh:'"+flh+"',text:'["+flh+"]"+map.get("FLMC")+"',dmjc:'" + map.get("DMJC") + "',flbm:'" + map.get("FLBM") + "',ffldm:'" + map.get("FFLDM") + "',czflmc:'" + Validate.isNullToDefault(map.get("CZFLMC"),"") + "',czbzdm:'" + Validate.isNullToDefault(map.get("CZBZDM"),"") + "',sfmj:'" + Validate.isNullToDefault(map.get("SFMJ"),"0") + "'," + bool + ",expand:false},");
			}else{
				json.append("{id:'"+map.get("GID")+"',flh:'"+flh+"',text:'["+flh+"]"+map.get("FLMC")+"',dmjc:'" + map.get("DMJC") + "',flbm:'" + map.get("FLBM") + "',ffldm:'" + map.get("FFLDM") + "',czflmc:'" + Validate.isNullToDefault(map.get("CZFLMC"),"") + "',czbzdm:'" + Validate.isNullToDefault(map.get("CZBZDM"),"") +"',sfmj:'" + Validate.isNullToDefault(map.get("SFMJ"),"0") + "'," + bool + ",expand:false}");
			}
		}
		json.append("]");
		return json.toString();
	}
	/**
	 * 学科分类树
	 * @return
	 */
	@RequestMapping(value="/xkTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xkTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String dm = pd.getString("dm");
		if(menu.equals("get-xkdm")){
			if(dm.equals("root")){
				return treeService.getPowerDmNode(pd,rootPath);
			}else{
			    return treeService.getDmNode(pd,dm,rootPath);
			}
		}else{
			return "";
		}
}
	/**
	 * 国别分类树
	 * @return
	 */
	@RequestMapping(value="/gbTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String gbTree(HttpServletResponse response) throws java.io.IOException{
		String pid = this.getPageData().getString("parentid");
		List list = treeService.getgbList(pid);
		StringBuffer json = new StringBuffer();
		json.append("[");
		if(list.size()>0){
		for(int i=0;i<list.size();i++){
			Map map =(Map)list.get(i);
			json.append("{");
			json.append("id:'" + map.get("DM") + "',");
			json.append("text:'("+map.get("DM")+")"+map.get("MC")+"',");
			json.append("bh:'" + map.get("DM") + "',");
			json.append("mc:'" + map.get("MC") + "',");
			if("0".equals(map.get("CNT")+"")){
				json.append("leaf:true,");
			}else{
				json.append("leaf:false,");
			}
			json.append("expanded:false");
			json.append("},");
		}
		json.delete(json.length() - 1, json.length());
	}
	json.append("]");
	return json.toString();
}
	/**
	 * 审核记录查询都用此树
	 * 获取业务审核树
	 * @return
	 */
	@RequestMapping(value="/shxxTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object shxxTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String mkbh = pd.getString("mkbh");
		String mk = pd.getString("mk");
		if("get-shxx".equals(menu)){
			String loginrybh = LUser.getRybh();
			if(mkbh.equals("root")){//
				return treeService.getPowerNode(pd,loginrybh,rootPath);
			}else{
			    return treeService.getTreeNode(pd,loginrybh,mkbh,rootPath,mk);
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 审核树点击查询按钮
	 * 通过模块名称查询模块编号
	 */
	@RequestMapping(value="/findMkbhByMkmc",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object findMkbhByMkmc(){
		return treeService.findMkbhByMkmc(getPageData());
	}
	/**
	 * 业务审核树
	 * @param id
	 * @param name
	 * @param level
	 * @param searchInput
	 * @return
	 */
	@RequestMapping(value="/shTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object shTree(String id,String name,String level,String searchInput){
		String loginrybh = LUser.getRybh();
		List<Map> list = treeService.shTree(id,name,level,searchInput,loginrybh);
		StringBuffer json = new StringBuffer();
		if(list.size()>0){
			json.append("[");
			for (int i = 0,len = list.size(); i < len; i++) {
				Map map = list.get(i);
				String url = Validate.isNullToDefaultString(map.get("URL"),"/shcshb/goYwshPage");
				String ljf = "";
				if(url.contains("?")){
					ljf = "&";
				}
				else{
					ljf = "?";
				}
				json.append("{\"id\":\""+map.get("MKBH")+"\",\"name\":\""+map.get("MKMC")+"("+map.get("HJ")+")\",\"isParent\":\""+
				map.get("ISPARENT")+"\",\"pId\":\""+id+"\",\"href\":\""+
				this.getRequest().getContextPath()+ url + ljf + "mkbh="+map.get("MKBH")+"&mkmc=" + map.get("MKMC") + "\"},");
				//this.getRequest().getContextPath()+"/shcshb/goYwshPage?mkbh="+map.get("MKBH")+"\"},");
			}
			return json.substring(0, json.length()-1)+"]";
		}else{
			return "[]";
		}
	}
	/**
	 * 数据字典单位树
	 * @return
	 */
	@RequestMapping(value="/sjzdTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object sjzdTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-sjzd".equals(menu)){
			if("root".equals(jb)){//
				return treeService.getSjzdNode(pd,"0",rootPath);
			}else{
			    return treeService.getSjzdNode(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 成本对象单位树
	 * @return
	 */
	@RequestMapping(value="/cbdxTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cbdxTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-sjzd".equals(menu)){
			if("root".equals(jb)){//
				return treeService.getCbdxNode(pd,"0",rootPath);
			}else{
			    return treeService.getCbdxNode2(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 二级单位人员树（资产调拨分单位内和单位间，用到）
	 * @return
	 */
	@RequestMapping(value="/ejdwryTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ejdwryTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		String ejdw = pd.getString("ejdw");//二级单位
		String flag = pd.getString("flag");//区分单位内和单位间的标志
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){//当前登录人的权限单位
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();//如果带有all，则将当前登录人置为000000（超级管理员）
				}
				return treeService.getEjdwNode(pd,loginrybh,ejdw,flag);//获取当前登录人下的权限单位
			}else{
//			    return treeService.getDwRyNode(pd,dwbh,rootPath);
				 return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	
	/**
	 * 二级单位树（资产变动分单位内和单位间，用到了）
	 * @return
	 */
	@RequestMapping(value="/ejdwTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ejdwTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		String ejdw = pd.getString("ejdw");//资产的二级单位
		String flag = pd.getString("flag");//区分单位内和单位间的标志
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return treeService.getEjdwNode(pd,loginrybh,ejdw,flag);
			}else{
			    return treeService.getDwNode(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}

	/**
	 * 获取分类树，显示财政6还是教育16根据传入的zjff决定，默认是教育16
	 * @return
	 */
	@RequestMapping(value="/flTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object flTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String loginrybh = LUser.getRybh();
		if("all".equals(pd.getString("type"))){
			loginrybh = SystemSet.AdminRybh();
		}
		return treeService.getKbflNode(pd,loginrybh,rootPath);
	}
	
	/**
	 * 分类树点击查询按钮
	 * 通过(flh)flmc查询flh
	 */
	@RequestMapping(value="/getFlh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getFlh(){
		PageData pd = this.getPageData();
		String flh = treeService.findFlhByFlmc(pd);
		return flh;
	}
	/**
	 * 新树获取单位信息
	 * @return
	 */
	@RequestMapping(value="/getDwJson",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getDwJson(String id,String name,String level,String searchInput){
		List<Map> list = treeService.getDwJson(id,name,level,searchInput);
		StringBuffer json = new StringBuffer();
		if(list.size()>0){
			json.append("[");
			for (int i = 0,len = list.size(); i < len; i++) {
				Map map = list.get(i);
				json.append("{\"id\":\""+map.get("ID")+"\",\"name\":\""+map.get("NAME")+"\",\"isParent\":\""+map.get("ISPARENT")+"\",\"pId\":\""+id+"\"},");
			}
			return json.substring(0, json.length()-1)+"]";
		}else{
			return "[]";
		}
	}
	/**
	 * 目录分类树
	 * @return
	 */
	@RequestMapping(value="/mlTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String mlTree(HttpServletResponse response) throws java.io.IOException{
		String pid = this.getPageData().getString("parentid");
		List list = treeService.getmlList();
		StringBuffer json = new StringBuffer();
		json.append("[");
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map =(Map)list.get(i);
				json.append("{");
				json.append("id:'" + map.get("BH") + "',");
				json.append("text:'"+map.get("MC")+"',");
				json.append("bh:'" + map.get("BH") + "',");
				json.append("mc:'" + map.get("MC") + "',");
				if("0".equals(map.get("CNT")+"")){
					json.append("leaf:false,");
				}else{
					json.append("leaf:true,");
				}
				json.append("expanded:false");
				json.append("},");
			}
			json.delete(json.length() - 1, json.length());
		}
		json.append("]");
		return json.toString();
	}
	
	
	
	@RequestMapping(value = "/school", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object schoolTree(HttpServletResponse response)
			throws java.io.IOException {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		if ("get-xx".equals(menu)) {
			return treeService.getSChool(pd, rootPath);
		} else {
			return "";
		}
	} 
}
