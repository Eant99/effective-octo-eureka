package com.googosoft.controller.systemset.cssz;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.ZcqcService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/zcqc")
public class ZcqcController extends BaseController{
	@Resource(name="zcqcService")
	private ZcqcService zcqcService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	/**
	 * 跳转资产自查设置页面
	 * @return
	 */
	@RequestMapping(value="/goZcqcPage")
	public ModelAndView goZcqcPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String flag= pd.getString("flag");
		if("zcqc".equals(flag)){
			mv.setViewName("systemset/cssz/zcqc");
		}else{
			mv.setViewName("systemset/cssz/zczc");
		}
		return mv;
	}
	/**
	 * 获取资产自查设置列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append(" K.QCBH,K.QCMS,TO_CHAR(K.KSRQ,'YYYY-MM-DD')AS KSRQ,TO_CHAR(k.JSRQ,'YYYY-MM-DD')as jsrq,DECODE(K.ZT,1,'正在进行...',0,'已结束',2,'已添加')as ztmc,k.zt as zt ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("K.QCBH");// 主键
		//pageList.setStrWhere(" and zt in('1','2')");// where条件
		if("zcqc".equals(pd.getString("flag"))){
			pageList.setTableName("ZC_ZCQCZT K ");// 表名
		}else{
			pageList.setTableName("ZC_ZCZCZT K ");// 表名
		}
		pageList.setHj1("");// 合计
		pageList.setOrderBy(" k.qcbh desc ");
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "", pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 添加资产自查新过程
	 * @return
	 */
	@RequestMapping(value="doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(){
		PageData pd = this.getPageData();
		String flag = pd.getString("flag");
		String qcbh="";
		if("zcqc".equals(flag)){
			qcbh = AutoKey.makeCkbh("zc_zcqczt", "qcbh", "6");
		}else{
			qcbh = AutoKey.makeCkbh("zc_zczczt", "qcbh", "6");
		}
		int i = zcqcService.doSave(flag,qcbh);
		if(i>0){
			return "{\"success\":\"true\",\"msg\":\"添加新过程成功！\"}";   
		}else{
			return "{\"success\":\"false\",\"msg\":\"添加新过程失败！\"}";
		}
	}
	/**
	 * 开始清查
	 * @return
	 */
	@RequestMapping(value="doStart",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doStart(){
		PageData pd = this.getPageData();
		String flag = pd.getString("flag");
		String qcbh = pd.getString("qcbh");
		int i = zcqcService.doStart(flag,qcbh);
		if(i!=0){
			return "{\"success\":\"true\",\"msg\":\"开始清查过程成功！\"}";   
		}else{
			return "{\"success\":\"false\",\"msg\":\"开始清查过程失败！\"}";
		}
	}
	/**
	 * 结束清查
	 * @return
	 */
	@RequestMapping(value="doEnd",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doEnd(){
		PageData pd = this.getPageData();
		String flag = pd.getString("flag");
		String qcbh = pd.getString("qcbh");
		int i = zcqcService.doEnd(flag,qcbh);
		if(i>0){
			return "{\"success\":\"true\",\"msg\":\"结束清查过程成功！\"}";   
		}else{
			return "{\"success\":\"false\",\"msg\":\"结束清查过程失败！\"}";
		}
	}
	/**
	 * 	添加新过程，判断是否存在已添加的，或者正在进行的数据，有，不进行添加
	 */
	@RequestMapping(value="doCheck",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doCheck(){
		PageData pd =  this.getPageData();
		String flag = pd.getString("flag");
		int i =  zcqcService.doCheck(flag);
		if(i==0){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
}
