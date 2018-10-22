package com.googosoft.controller.system.suggest;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.service.system.suggest.SuggestService;
import com.googosoft.util.PageData;
/**
 * 联想输入查询
 * @author master
 */
@Controller
@RequestMapping(value="/suggest")
public class SuggestControl extends BaseController{
	
	@Resource(name="suggestService")
	private SuggestService suggestService;
	
	/**
	 * 获取联想输入查询信息
	 * @return
	 */
	@RequestMapping(value="/getXx",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSuggest(){
		PageData pd = this.getPageData();
		String userId = LUser.getRybh();
		//System.out.println("inputvalue==="+pd.getString("inputvalue")+"---menu====="+pd.getString("menu"));
		//System.out.println("=========="+suggestService.getSuggest(pd.getString("inputvalue"),pd.getString("menu"), userId));
		System.out.println(pd.getString("inputvalue")+"==========哈哈==========="+pd.getString("menu")+"@@@@@@@@@@@"+pd.getString("sjkm"));
		return suggestService.getSuggest(pd.getString("inputvalue"),pd.getString("menu"), userId,pd.getString("sjkm"));
	}
	/**
	 * 为了科目联想输入而加
	 * @return
	 */
	@RequestMapping(value="/getXx2",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSuggest2(HttpSession session){
		PageData pd = this.getPageData();
		String kjzd = CommonUtil.getKjzd(session);
		//System.out.println("inputvalue==="+pd.getString("inputvalue")+"---menu====="+pd.getString("menu"));
		//System.out.println("=========="+suggestService.getSuggest(pd.getString("inputvalue"),pd.getString("menu"), userId));
		System.out.println(pd.getString("inputvalue")+"==========哈哈==========="+pd.getString("menu")+"@@@@@@@@@@@"+pd.getString("sjkm"));
		return suggestService.getSuggest(pd.getString("inputvalue"),pd.getString("menu"), kjzd,pd.getString("sjkm"));
	}
	
	/**
	 * 获取联想输入信息（单位内和单位间调拨用到）
	 * @return
	 */
	@RequestMapping(value="/getXx1",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSuggest1(){
		PageData pd = this.getPageData();
		String userId = LUser.getRybh();
		String ejdw = pd.getString("ejdw");
		String flag = pd.getString("flag");
		return suggestService.getSuggest(pd.getString("inputvalue"),pd.getString("menu"), userId, ejdw,flag);
	}
}
