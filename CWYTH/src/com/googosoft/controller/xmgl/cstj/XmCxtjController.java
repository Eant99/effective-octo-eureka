package com.googosoft.controller.xmgl.cstj;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value = "/xmcxtj/cxtj")
public class XmCxtjController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	@Resource(name = "dictService")
	private DictService dictService;// 单例

	
	/**
	 * 获取列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goXmtjPage")
	public ModelAndView goDmbPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		String type = pd.getString("type");
		String url = "xmgl/cxtj/xmtj/xmtj_list";
		mv.setViewName(url);
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 列表页面数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名

		StringBuffer sql = new StringBuffer();
		sql.append(" GUID,");
		sql.append(" XMMC,'是'AS SFFP,FPJE,YFPJE");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_ZJFASZB A");
		// 主键
		pageList.setKeyId("guid");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 列表页面数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXmtjPage")
	@ResponseBody
	public Object getxmflPage() throws Exception {
		PageList pageList = new PageList();
		// 设置查询字段名
		                        
		StringBuffer sql = new StringBuffer();
		sql.append(" GUID,");
		sql.append(" XMBH,XMMC,FLMC,SBDW,FWXK,to_char(JHZXSJ,'yyyy-mm-dd'),GQ,YSJE,SHZT,SFSNDCXLZXM,XMPM,TZHPM,SBR,SBRQ,BZ,FJ,CSR,CSRQ,CSYJ,FSR,FSRQ,FSYJ,TZR,TZRQ");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("CW_XMSBXXB A");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");
		String lrybh = LUser.getRybh();// 获取当前登录人员编号
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
}
