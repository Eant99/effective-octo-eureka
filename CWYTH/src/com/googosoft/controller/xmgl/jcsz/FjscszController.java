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
@RequestMapping(value = "/fjscsz")
public class FjscszController extends BaseController {
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
	@RequestMapping(value="/gofjscszPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flbh = pd.getString("flbh");
		//从数据字典中获取单位性质下拉框内容
		List dwxz = dictService.getDict(Constant.DWXZ);
		mv.setViewName("/xmgl/jcsz/fjsclx/fjsclx_list");
		mv.addObject("dwxzlist", dwxz);
		mv.addObject("flbh",flbh);
		return mv;
	}
	/**
	 * 获取项目分类列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("t.GUID,t.FJLX,t.SJZT,T.SJFL,T.SFBC");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("CW_FJSCSZB t");
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
	@RequestMapping(value="/getPageList2")
	@ResponseBody
	public Object getPageList2(){
		String datas = SendHttpUtil.sendPost("http://localhost:8081/fjsclx/fjsclx_list", "");
		List<Map<String, Object>> list = SendHttpUtil.getResultToList(datas);
		PageInfo pageInfo = new PageInfo("1", ""+list.size(), ""+list.size(), datas);
		return pageInfo.toJson();
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
		mv.setViewName("/xmgl/jcsz/fjsclx/fjsclx_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 项目类型
	 */
	@RequestMapping(value="/window")
	public ModelAndView goWindowTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String _url = "/xmgl/jcsz/fjsclx/xxmTree";
		//页面路径
		mv.addObject("pageUrl",pd.getString("pageUrl"));
		//树值路径
		mv.addObject("treeJson",pd.getString("treeJson"));
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
	}
}
