package com.googosoft.controller.kjzdqy;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjzdqy.KjzdqyService;
import com.googosoft.util.PageData;
/**
 * 会计制度启用
 * @author wangzhiduo
 * @date 2018-1-2上午9:37:06
 */
@Controller
@RequestMapping(value ="/kjzdqy")
public class KjzdqyController extends BaseController{

	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="kjzdqyService")
	private KjzdqyService kjzdqyService;//单例
	
	/**
	 *  跳转会计制度启用列表页面
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/kjzdqy_list")
	public ModelAndView goGwjdmxEditPage(HttpSession session) {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List map = this.kjzdqyService.getZd();
		mv.addObject("list", map);
		String kjzd = CommonUtil.getKjzd(session);//获取使用的会计制度
		mv.addObject("kjzd", kjzd);
		mv.setViewName("kjzdqy/kjzd_list");
		return mv;
	}
	/**
	 *  跳转新旧会计制度转换
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/xjkjzdzh")
	public ModelAndView goXjkjzdzh() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		List<Map<String, Object>> list=null;
		list = kjzdqyService.getKjzdzh();//获取会计制度转换数据
		mv.addObject("list", list);
		mv.addObject("guid", guid);
		mv.setViewName("kjzdqy/xjkjzdzh");
		return mv;
	}
	/**
	 *  会计制度转换-新增
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/kjzdzh_add")
	public ModelAndView kjzdzh_add() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		 String operateType = pd.getString("operateType");
		 if("U".equals(operateType)) { 
			 String guid = pd.getString("guid");
			Map<String, Object> map=null;
			 map = kjzdqyService.getKjzdzhByid(guid);//修改回显
			 mv.addObject("map", map);
		 } 
		 mv.addObject("operateType", operateType);
		mv.setViewName("kjzdqy/kjzdzh_add");
		return mv;
	}

	/**
	 * 会计制度启用设置列表数据查询
	 * @return
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append(" T.GUID,T.ZT, T.ZD,K.ZTMC ,D.ZDM");
//		sqltext.append(" GUID,ZT,DECODE(ZD,0,'2014旧版会计制度',1,'2017新版会计制度') AS ZD ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("T.GUID");// 主键
		pageList.setTableName(" CW_KJZDQY T LEFT JOIN CW_ZTXXB K ON K.GUID = T.ZT LEFT  JOIN CW_KJZDQY_ZDB D ON D.GUID=T.ZD ");// 表名
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);// 引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 会计制度启用设置保存按钮
	 * @return
	 */
	@RequestMapping(value="/doSave")
	@ResponseBody
	public Object doSave(){
		PageData pd = this.getPageData();
		String zdm = pd.getString("zdm");
		String guid = pd.getString("guid");
		boolean bl = kjzdqyService.doSave(zdm,guid);
		StringBuffer msg = new StringBuffer();//存放信息
		if(bl){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	/**
	 * 新旧会计科目转换数据
	 * @return
	 */
	@RequestMapping(value = "/getKjkmList")
	@ResponseBody
	public Object getKjkmList() {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append(" T.GUID,T.YKMBH, T.YKMMC,T.XKMBH ,T.XKMMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("T.GUID");// 主键
		pageList.setTableName(" CW_KJKMSZB_X T ");// 表名
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);// 引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取制度
	 * @return
	 */
	@RequestMapping(value = "/getZd")
	@ResponseBody
	public List getZd() {
		List map = this.kjzdqyService.getZd();
		return map;
	}
	
	  /**
	   * 删除会计制度转换
	   * @return
	   */
	@RequestMapping(value="/doDelKjkm",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
    	int k = kjzdqyService.doDelKjkm(guid);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}

	/**
	 * 保存新增huo修改的会计制度
	 */
	@RequestMapping(value = "/doSaveKjzd", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveKjzd() throws Exception {
		PageData pd = this.getPageData();
		String b = "";
		int i = 0;
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"),new TypeToken<HashMap<String, Object>>() {}.getType());
		List<Map<String, Object>> List = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : List) {
			i = kjzdqyService.doSaveKjzd(map);
		}
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	/**
	 * 执行
	 */
	@RequestMapping(value = "/doZx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional  
	public Object doZx() throws Exception {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String ztid = pd.getString("ztid");
		//先将科目余额插入
		int i = kjzdqyService.doZx1();
		//根据替换数据转换金额
		boolean j = kjzdqyService.doZx2();
		//将会计制度启用为新版
		int k = kjzdqyService.doZx3(ztid);
		//修改报表菜单显示
		int m = kjzdqyService.doZx4();
		StringBuffer msg = new StringBuffer();//存放信息
		if(i>0 && j){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	
	/**
	 * 获取联想输入查询信息
	 * @return
	 */
	@RequestMapping(value="/getLxsr",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSuggest(){
		PageData pd = this.getPageData();
		String userId = LUser.getRybh();
//		System.out.println(pd.getString("inputvalue")+"==========哈哈==========="+pd.getString("menu"));
		return kjzdqyService.getSuggest(pd.getString("inputvalue"),pd.getString("menu"));
	}

	/**
	 * 获取原科目名称
	 */
	@RequestMapping(value = "/getKmmc", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getYkmmc() throws Exception {
		PageData pd = this.getPageData();
		String ykmbh = pd.getString("ykmbh");
		String xkmbh = pd.getString("xkmbh");
		return kjzdqyService.getKmmc(ykmbh,xkmbh);
	}
}
