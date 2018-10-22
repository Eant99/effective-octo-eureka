package com.googosoft.controller.ysgl.zcys;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;
@Controller
@RequestMapping(value = "/zcys/zcys")
public class ZcysController  extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@RequestMapping(value = "/goZcysPage")
	public ModelAndView goZcysPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/zcys/zcys_list");
		return mv;
	}
	@RequestMapping(value = "/goCheck")
	public ModelAndView goCheck() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/zcys/check_list");
		return mv;
	}
	/**
	 * 列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getChangeList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("GUID ,SHZT ,DJBH ,DWMC ,TBR ,SHYJ,");
		sqltext.append(" TO_CHAR(BZND,'YYYY')BZND,");
		sqltext.append(" TO_CHAR(TBSJ,'YYYY-MM-DD')TBSJ,");
		sqltext.append(" DECODE(NVL(SNYSZE,'0'),'0','0.0000',TO_CHAR(ROUND(SNYSZE,2),'FM999999999990.0000'))SNYSZE,");
		sqltext.append(" DECODE(NVL(NSRZE,'0'),'0','0.0000',TO_CHAR(ROUND(SNYSZE,2),'FM999999999990.0000'))NSRZE,");
		sqltext.append(" DECODE(NVL(BNYSZE,'0'),'0','0.0000',TO_CHAR(ROUND(BNYSZE,2),'FM999999999990.0000'))BNYSZE");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");// 主键
		// pageList.setStrWhere(pageService.getQxsql(LUser.getRybh(), "B.DWBH",
		// "D")+" AND B.BDBGBH=D.BDBGBH AND D.FJBH='"+yqbh+"'");// where条件
		pageList.setTableName("CW_SRYSSBXXZB A ");
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);// 引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()), gson.toJson(pageList
						.getGroupList()));
		return pageInfo.toJson();
	}
	/**
	 * 窗口
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/kmxx")
	public ModelAndView goWindowPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String type = pd.getString("type");
		String _url = "ysgl/zcys/kmxx";
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
		
	}
	@RequestMapping(value="/xmxx")
	public ModelAndView goWindowXmxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String _url = "ysgl/zcys/xmxx";
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
		
	}
}
