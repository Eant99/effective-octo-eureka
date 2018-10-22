package com.googosoft.controller.wsbx.jkyw;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

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
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.service.kjhs.zffs.ZffsService;
import com.googosoft.util.PageData;
@Controller
@RequestMapping(value="/jkyw")
public class JkywController extends BaseController{
	@Resource(name="dictService")
	private DictService dictService;//数据字典单例
	@Resource(name="kmszService")
	private KmszService kmszService; //单例
	@Resource(name="pageService")
	private PageService pageService;//分页单例
	@Resource(name="zffsService")
	private ZffsService zffsService;//支付方式
	/**
	 * 获取借款业务信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goJkywPage")
	public ModelAndView goJkywPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/jkyw/jkyw_list");
		return mv;
	}
	/**
	 * 获取借款业务审核信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goJkywShPage")
	public ModelAndView goJkywShPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/jkyw/jkywsh_list");
		return mv;
	}
	/**
	 * 获取借款业务信息列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,jkr,bmmc,xmmc,decode(nvl(jkje,0),0,'0.00',(to_char(round(jkje,2),'fm99999999999990.00'))) as jkje,to_char(sqrq,'yyyy-mm-dd') as sqrq ,to_char(bxrq,'yyyy-mm-dd') as bxrq,zffs,decode(shzt,'1','正常','0','禁用') zt ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GuID");//主键
		pageList.setTableName("CW_JKYWB t");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 跳转Edit页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
//		if(operateType.equals("U")||operateType.equals("L")){
//			map = txlService.getObjectById(pd.getString("id"));
//		}
		List zffs = zffsService.getZffs();
		System.out.println("==============zffs========="+zffs.size());
		String rymc =LUser.getRyxm();
		String bmmc =LUser.getDwmc();
		mv.addObject("rymc",rymc);
		mv.addObject("bmmc",bmmc);
		mv.addObject("txl", map);
		mv.addObject("gid", pd.getString("id"));
		mv.addObject("zffslist",zffs);
		mv.setViewName("wsbx/jkyw/jkyw_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 跳转add页面
	 * @return
	 */
	@RequestMapping(value="/goAddPage")
	public ModelAndView goAddPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String stepNum = pd.getString("stepNum");
		if("2".equals(stepNum)) {
			mv.addObject("pageUrl",pd.getString("pageUrl"));
			mv.addObject("treeJson",pd.getString("treeJson"));
		}
		mv.setViewName("wsbx/jkyw/jkyw_add_step"+stepNum);
		return mv;
	}
	/**
	 * 跳转查看页面
	 * @return
	 */
	@RequestMapping(value="/goViewPage")
	public ModelAndView goSearchPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		List zffs = zffsService.getZffs();
		mv.setViewName("wsbx/jkyw/jkyw_view");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 
	 */
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String url = "wsbx/jkyw/check1";
		if(!"first".equals(type)){
			url = "wsbx/jkyw/check2";
		}
		mv.setViewName(url);
		return mv;
	}
	@RequestMapping(value="/goShPage")
	public ModelAndView sh(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/jkyw/jkyw_sh");
		return mv;
	}
	@RequestMapping(value="/check1")
	public ModelAndView check1(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String url = "wsbx/jkyw/check3";
		if(!"first".equals(type)){
			url = "wsbx/jkyw/check4";
		}
		mv.setViewName(url);
		return mv;
	}
	//注意事项弹窗
		@RequestMapping("goAlertPage")
		public ModelAndView goAlertPage() {
			ModelAndView mv = this.getModelAndView();
			mv.setViewName("/wsbx/jkyw/alert");
			return mv;
		}
	//借款业务信息
	@RequestMapping("goJkywxxPage")
	public ModelAndView goJkywxxPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("/wsbx/jkyw/jkywxx");
		return mv;
	}
	//添加汇款信息界面
	@RequestMapping("/goTjhkxxPage")
	public ModelAndView goTjhkxxPage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("wsbx/jkyw/tjhkxx");
		return mv;
	}
	/**
	 * 经济科目设置单位树
	 * 
	 */
	@RequestMapping(value="/JjszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object JjszTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		System.out.println("jb=========="+jb);
		if("get-jjkm".equals(menu)){
			if("root".equals(jb)){//
				return kmszService.getjjkmNodezjff(pd,"1",rootPath);
			}else{			
			    //return kmszService.getgnkmNodezj(pd,jb,rootPath);
				return kmszService.getjjkmNodezjff(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	
}
