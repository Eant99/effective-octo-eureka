package com.googosoft.controller.pjgl.rcyw;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.pjgl.rcyw.PjgmService;
import com.googosoft.util.PageData;
/**
 * 向票据购买Controller
 * @author 张春燕
 * @param session
 * @return
 */
@Controller
@RequestMapping(value = "/pjgm")
public class PjgmController extends BaseController {
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="pjgmService")
	private PjgmService pjgmService;//单例
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	/**
	 * 选择票据账户页面跳转
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/goPjzhPage")
	@ResponseBody
	public ModelAndView goPjzhPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);
		mv.setViewName("pjgl/rcyw/pjgm/xzpjzh");
		return mv;
	}
	/**
	 * 查询票据账户列表信息
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getPjzh")
	@ResponseBody
	public Object getPjzh(){
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("(  select t.GUID,lx.PJLXBH as PJLX,t.ZHMC,t.SSZT from cw_pjzhb t left join cw_pjlxb lx on lx.guid = t.pjlx where sfqy='1' ) b ");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("B.GUID");
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findWindowList(pd, pageList,"ZHLXMC");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 向票据购买表保存信息
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		int result=0;
		if("C".equals(operateType))//新增
		{  
			result = pjgmService.doAdd(pd,loginId,rybh);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',operateType:'C'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 检查票据是否被领用
	 * @author jiatong 2018年3月25日12:46:55
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/doCheckused",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doCheckused(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		boolean sfsy = pjgmService.doCheckused(guid);
		Gson gson = new Gson();
		return gson.toJson(sfsy);
	}
	
	/**
	 * 获取票据购买信息列表数据
	 * "
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/getPjgmList")
	@ResponseBody
	public Object getPjgmList(HttpSession session){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		sqltext.append("  GID,KMMC,GMRQ,GMR,PJZH,PJQZ,PJZS,PJQH,QJZH  ");
		tablename.append(" ( select a.GID,a.KMMC,to_char(a.GMRQ,'yyyy-MM-dd') GMRQ,a.GMR,a.PJQZ,a.PJZS,a.PJQH,a.QJZH,b.zhmc as pjzh from CW_PJGM a left join cw_pjzhb b on a.pjzh=b.guid) K");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GID");
		//设置WHERE条件
		PageData pd = this.getPageData();
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 删除票据信息包括总表和明细表
	 * @author jatong 2018年3月25日12:52:23
	 * @param session
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
    	int k = pjgmService.doDelete(guid);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    	}
	}
	
}
