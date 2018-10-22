/**
 * 
 */
package com.googosoft.controller.wsbx;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.wsbx.Cw_Shyj;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.wsbx.ShyjService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * @author lifei
 * @date 2018-4-3
 * @title ShyjController.java
 */
@Controller
@RequestMapping(value="/wsbx/shyj")
public class ShyjController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	@Resource(name = "ShyjService")
	private ShyjService ShyjService;// 单例

	/**
	 * 获取项目类型列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("wsbx/shyj/shyj_list");
		return mv;
	}
	
	/**
	 * 获取单位信息列表数据
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("*");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("CW_SHYJB");
		String strWhere = " and czr='"+LUser.getGuid()+"'";
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		pageList.setStrWhere(strWhere);
		PageData pd = this.getPageData();
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * C增加 U修改 L查看
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		String guid = Validate.isNullToDefaultString(pd.getString("guid"), "");
		if(operateType.equals("C")){
			guid = this.get32UUID();//主表guid
		}else if(operateType.equals("U")||operateType.equals("L")){
			//数据库值
			Map<?, ?>  map = ShyjService.getMapINfoByGuid(guid);
			mv.addObject("map", map);
		}
		mv.addObject("guid",guid);
		mv.setViewName("wsbx/shyj/shyj_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 保存
	 * @param dwb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSave(Cw_Shyj po){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		System.out.println("----------"+po.toString());
		if("C".equals(operateType))//新增
		{
			result = ShyjService.doAdd(po);
			if(result==1){
				return  "{\"success\":true, \"msg\":'信息保存成功！'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			result = ShyjService.doUpdate(po);
			if(result==1)
			{
				return "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	
	/**
	 * 删除
	 * @param guid
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(String guid){
		PageData pd = this.getPageData();
    	int k = ShyjService.doDelete(guid);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
}
