package com.googosoft.controller.kjhs;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.PzlxNewService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value = "/pzlxNew")
public class PzlxnewController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="pzlxnewService")
	private PzlxNewService pzlxnewService;//单例
	
	/**
	 * 凭证类型列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/pzlxNew/pzlxNew_list");
		return mv;
	}
	
	/**
	 * 凭证类型列表页面数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object kmxx(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" GUID,PZLXBH,PZLXMC ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName("cw_pzlxbnew k ");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取详细信息页面（增加、修改）
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goEditPage")
	public ModelAndView goEditPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("C")) {
			String guid = GenAutoKey.get32UUID();
			mv.addObject("guid", guid);
		} else if (operateType.equals("U")) {
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");		
			Map pzlx = pzlxnewService.getMapByGuid(guid);
			mv.addObject("pzlx", pzlx);
			mv.addObject("guid",guid);
			String[] pzbhss = (pzlx.get("pzbh")+"").split(",");
			String pzbhs = (pzlx.get("pzbh")+"");
			mv.addObject("pzbhs",pzbhs);
			mv.addObject("length",pzbhss.length);
		}
		mv.setViewName("kjhs/pzlxNew/pzlxNew_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 保存凭证类型信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave() {
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType))//新增保存 
		{
			// 判断凭证类型编号是否重复
			int flag1 = pzlxnewService.getBhById(pd);
			if (flag1!=0) {
				return MessageBox.show(false, "该凭证类型编号已经存在，请重新输入！");
			}
			result = pzlxnewService.doAdd(pd);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if ("U".equals(operateType)){ //修改
			String guid = pd.getString("guid");
			// 判断凭证类型编号是否重复
			int flag1 = pzlxnewService.getUpdBhById(pd);
			   if (flag1!=0) {
				  return MessageBox.show(false, "该凭证类型编号已经存在，请重新输入！");
				}
			result = pzlxnewService.updPzlx(pd,guid);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
    	}		
	}
    /**
     * 删除凭证类型信息
     * @return
     */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		int i = pzlxnewService.delete(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

}
