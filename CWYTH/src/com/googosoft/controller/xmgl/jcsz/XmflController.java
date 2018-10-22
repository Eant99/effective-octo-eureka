package com.googosoft.controller.xmgl.jcsz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.jcsz.XmflService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value = "/xmfl")
public class XmflController extends BaseController {
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="xmflService")
	private XmflService xmflService;//单例
	/**
	 * 获取项目分类列表页面
	 * @return
	 */
	@RequestMapping(value="/goXmflbPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flbh = pd.getString("flbh");
		//从数据字典中获取单位性质下拉框内容
		List dwxz = dictService.getDict(Constant.DWXZ);
		mv.setViewName("/xmgl/jcsz/xmfl/xmfl_list");
		mv.addObject("dwxzlist", dwxz);
		mv.addObject("flbh",flbh);
		return mv;
	}
	/**
	 * 获取项目分类列表数据
	 * @return
	 * @throws Exception
	 */
	
	@RequestMapping(value = "/getPageListes")
	@ResponseBody
	public Object getPageListes() throws Exception {	
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		
		String datas = SendHttpUtil.sendPost(SystemSet.address+"/xmflsz/getPageList", "");
		String strnum = SendHttpUtil.sendPost(SystemSet.address+"/xmfl/getCountFlow", "");
		//int num=Integer.parseInt(strnum);
		strnum=strnum.substring(8,strnum.indexOf("}"));
		PageInfo pageInfo = new PageInfo((String) pd.get("draw"), strnum, strnum,datas);
		return pageInfo.toJson();
		
	}
	/**
	 * 获取项目分类列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageLists")
	@ResponseBody
	public Object getPageLists(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("guid,flbh,flmc,(select a.flmc from Cw_xmflszb a where a.flbh=t.sjfl ) as sjfl,bz ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("Cw_xmflszb t");
		//设置表主键名
		pageList.setKeyId("guid");
		//设置WHERE条件
		PageData pd = this.getPageData();
		String flbh = pd.getString("treeflbh");
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		if(Validate.noNull(flbh)){
			
			pageList.setStrWhere(" and t.sjfl='"+flbh+"' or t.flbh='"+flbh+"' "); //根据点击左侧树展示右侧列表
		}
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获项目分类树
	 * @return
	 */
	@RequestMapping(value="/xmflTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xmflTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String flbh = pd.getString("flbh");
		if(menu.equals("get-xmfl")){
			String loginrybh = LUser.getRybh();
				return xmflService.getPowerDwNode(pd, loginrybh, rootPath,flbh);
		}else{
			return "";
		}
	}
	/**
	 * 跳转项目分类编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		mv.setViewName("/xmgl/jcsz/xmfl/xmfl_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 获取详细信息（增加、修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goXmflszPage")
	public ModelAndView goXmflszPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("C")) {
			String guid = GenAutoKey.createRybh("Cw_Xmflszb", "guid", "32");
			Map<String, String> map = new HashMap<String, String>();
			map.put("guid", guid);
			mv.addObject("info", map);
		} else if (operateType.equals("U") || operateType.equals("L")) {
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");
			String datas = SendHttpUtil.sendPost("http://localhost/apis/xmflsz/goEditPage", "guid="+guid);
			HashMap map = SendHttpUtil.getResultToMap(datas);
			System.out.println(datas);
			System.out.println(map);
			mv.addObject("xmfl", map);
			mv.addObject("guid", this.getPageData().getString("guid"));
		}
		mv.setViewName("xmgl/jcsz/xmfl/xmfl_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}

}
