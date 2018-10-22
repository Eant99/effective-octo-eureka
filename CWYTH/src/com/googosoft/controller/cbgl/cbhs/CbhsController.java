package com.googosoft.controller.cbgl.cbhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.jzmb.JzmbService;
import com.googosoft.service.ysgl.xmlx.XmlxService;
import com.googosoft.service.ysgl.xmsz.XmxxService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;


@Controller
@RequestMapping(value="/cbhs")
public class CbhsController extends BaseController{

	@Resource(name="xmxxService")
	XmxxService xmxxService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name = "jzmbService")
	private JzmbService jzmbService;
	@Resource(name = "xmlxService")
	private XmlxService xmlxService;
	/**
	 * 跳转到项目信息列表页面
	 * @return
	 */
	@RequestMapping("/goPageList")
	public ModelAndView goPageList() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("cbgl/cbhs/cbhs_list");
		return mv;
	}
	//获取列表数据
	//获取列表数据
		@RequestMapping(value="getListpage",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getListpage(HttpSession session) {
			String sszt = Constant.getztid(session);
			PageData pd = this.getPageData();
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			PageList pageList = new PageList();
			sqltext.append(" * ");
			tablename.append(" ( select '123' as guid,'完全成本法' as CBHSMX ,'已核算' as HSZT from dual union select '345' as guid,'作业成本法' as CBHSMX ,'未核算' as HSZT from dual) k");
			pageList.setSqlText(sqltext.toString());
			//设置表名
			pageList.setTableName(tablename.toString());
			//设置表主键名
			pageList.setKeyId("k.GUID");//主键
			//设置WHERE条件
			pageList.setStrWhere("");
			pageList.setHj1("");//合计
			pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
	/**
	 * 编辑经济科目
	 * @return
	 */
	@RequestMapping(value="/goEditJjPage")
	public ModelAndView goEditJjPage(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		ModelAndView mv = this.getModelAndView();
		mv.addObject("guid",guid);
		mv.setViewName("cbgl/cbhs/cbhs_edit");
		return mv;
	}	
	
}
