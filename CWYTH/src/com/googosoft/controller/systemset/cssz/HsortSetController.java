package com.googosoft.controller.systemset.cssz;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.cssz.ZC_HSORTSET_EXTEND;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.HsortSetService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/hsortset")
public class HsortSetController extends BaseController{
	
	@Resource(name="hsortsetService")
	private HsortSetService hsortsetService;//单例
	
	@Resource(name="pageService")
	private PageService pageService;
	
	/**
	 * 获取资产编号保留位设置    页面
	 */
	@RequestMapping(value="goHsortSetPage")
	public ModelAndView goHsortSetPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/zcbh/hsortset_list");
		return mv;
	}
	/**
	 * 获取资产编号保留位设置     列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("K.ZBH,K.ZMC,K.BLW,(select wm_concat(mc) from ZC_HSORT t where t.zbh=K.zbh) as FLMC ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("zbh");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("ZC_HSORTSET K");//表名
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "", pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取资产编号保留位设置     编辑页面
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		ModelAndView mv = this.getModelAndView();
		List zclb_list = hsortsetService.getZclb();
		mv.addObject("zclb_list",zclb_list);
		if("U".equals(operateType)||"L".equals(operateType)){
			Map map = hsortsetService.getObjectById(pd.getString("zbh"));
			Map mapDlh = hsortsetService.getDlhById(pd.getString("zbh"));
			mv.addObject("hsortset", map);
			mv.addObject("hsort", mapDlh);
		}
		mv.setViewName("systemset/cssz/zcbh/hsortset_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 添加资产编号保留位设置 信息
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_HSORTSET_EXTEND hsortset){
		String operateType = this.getPageData().getString("operateType");
		String dlhArr = this.getPageData().getString("dlhArr");
		String dlhOld = this.getPageData().getString("dlhOld");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = hsortsetService.doAdd(hsortset,dlhArr,dlhOld);
			if(i==1){
				b = "{\"success\":\"true\",\"zbh\":\""+hsortset.getZbh()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==2){
				b = "{\"success\":\"false\",\"zbh\":\"\",\"msg\":\"组编号不可重复！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i = hsortsetService.doUpdate(hsortset,dlhArr,dlhOld);
			if(i==1){
				b = "{\"success\":\"true\",\"zbh\":\""+hsortset.getZbh()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"zbh\":\"\",\"msg\":\"组编号不可重复！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{\"success\":\"false\",\"zbh\":\"\",\"msg\":\"参数传入有误！\"}";
		}
		return b;
	}
	/**
	 * 删除资产编号保留位设置
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String zbh = pd.getString("zbh");
		int b = hsortsetService.doDelete(zbh);
		if(b>0){
			return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";   
		}else{
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
	}
}
