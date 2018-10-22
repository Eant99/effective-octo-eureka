package com.googosoft.controller.wsbx.jcsz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.googosoft.service.fzgn.jcsz.JsxxsService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/jtbzsz")
public class JtbzszController extends BaseController {
	@Resource(name = "jsxxsService")
	private JsxxsService jsxxsService;
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	@Resource(name = "pageService")
	private PageService pageService;

	/**
	 * 跳转学生列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysTime = sdf.format(new Date());
		String guid = GenAutoKey.createRybh("Cw_jtbzszb", "guid", "32");
		String rybh = LUser.getRybh();
		String loginId = jsxxsService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		mv.addObject("sysTime", sysTime);
		mv.addObject("guid", guid);
		mv.setViewName("wsbx/jcsz/jtbzsz/jtbzsz");
		return mv;
	}

	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" GUID,RYJB,HCJB,LCJB,FJJB,CZR,TO_CHAR(CZRQ,'YYYY-MM-DD HH24:MI:SS')CZRQ  ");
		pageList.setSqlText(sqltext.toString());
		// 表名
		pageList.setTableName("CW_JTBZSZB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = " ";
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		PageData pd = this.getPageData();
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	@RequestMapping(value = "/dict", produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String getDict(HttpServletRequest request,
			HttpServletResponse response) {
		PageData pd = this.getPageData();
		String name = pd.getString("name");
		String params = "hcjb";
		if(Validate.noNull(name)&&name.contains("lcjb")){
			 params = "lcjb";
		}else if(Validate.noNull(name)&&name.contains("fjjb")){
			 params = "fjjb";
		}else if(Validate.noNull(name)&&name.contains("ryjb")){
			 params = "ryjb";
		}
		List<Map<String, Object>> dict = dictService.getDict(params);
		Gson gson = new Gson();
		String dicts = gson.toJson(dict);
		return dicts;
	}
}
