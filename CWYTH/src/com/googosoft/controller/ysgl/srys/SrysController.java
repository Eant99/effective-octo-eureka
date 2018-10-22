package com.googosoft.controller.ysgl.srys;

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

/**
 * 跳转收入预算页面
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/srys/srys")
public class SrysController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@RequestMapping(value = "/goSrysPage")
	public ModelAndView goSrysPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/srys/srys_list");
		return mv;
	}
	@RequestMapping(value = "/goCheck")
	public ModelAndView goCheck() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("ysgl/srys/check_list");
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
		sqltext.append(" DECODE(NVL(SNYSZE,'0'),'0','0.0000',TO_CHAR(ROUND(SNYSZE,4),'FM999999999990.0000'))SNYSZE,");
		sqltext.append(" DECODE(NVL(NSRZE,'0'),'0','0.0000',TO_CHAR(ROUND(SNYSZE,4),'FM999999999990.0000'))NSRZE,");
		sqltext.append(" DECODE(NVL(BNYSZE,'0'),'0','0.0000',TO_CHAR(ROUND(BNYSZE,4),'FM999999999990.0000'))BNYSZE");
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
		String _url = "ysgl/srys/kmxx";
		mv.addObject("controlId", controlId);
		mv.setViewName(_url);
		return mv;
		
	}
}
