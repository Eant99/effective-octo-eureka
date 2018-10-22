package com.googosoft.controller.kjhs;

import java.util.Calendar;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.CyzyszService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/cyzysz")
public class cyzyszController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="cyzyszService")
	private CyzyszService cyzyszService;//单例
	/**
	 * 跳转列表页面
	 * @return
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/cyzysz/cyzysz_list");
		return mv;

	}
    /**
     * 获取列表页面数据
     * @return
     * @throws Exception
     */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID,K.KMBH,K.ZYNR,(CASE SFQY WHEN '1' THEN '是' WHEN '0' THEN '否' END) AS SFQY ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_CYZYSZB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 会计科目设置树
	 */
	@RequestMapping(value = "/mainKjkmszTree")
	public ModelAndView goMainKjkmszTree() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 控件id
		String controlId = pd.getString("controlId");
		String type = pd.getString("type");
		mv.addObject("controlId", controlId);
		mv.setViewName("/kjhs/cyzysz/KjkmTree");
		return mv;
	}
	/**
	 * 获取会计科目信息列表的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goKjkmPage")
	public ModelAndView goDdbPage1() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String treesearch = pd.getString("treesearch");
		mv.addObject("treesearch", treesearch);
		mv.addObject("dm", dm);
		mv.addObject("jb", jb);
		mv.setViewName("kjhs/cyzysz/Kjkm_list");
		return mv;
	}
	/**
	 * 获取会计科目设置页面信息
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getkjkmPageList")
	@ResponseBody
	public Object getkjkmPageList(HttpSession session) throws Exception {
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);//获取使用的会计制度
		PageData pd = this.getPageData();
		Calendar date = Calendar.getInstance();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");

		StringBuffer sqltext = new StringBuffer();// 查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(
				" ( select distinct c.kmnd, c.sfmj, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
						+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
						+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
						+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
						+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and sfmj='1' and kjzd='"+kjzd+"' and sszt='"
						+ sszt + "'");
		if (Validate.noNull(dm)) {
			tablename.append(" start with c.jb='"+jb+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");
		}
		tablename.append(" ) k ");
		// if(Validate.noNull(dm)){
		// tablename.append(" where c. ");
		// }
		// if(Validate.noNull(jb)){
		// tablename.append(" where c. ");
		// }
		pageList.setSqlText(sqltext.toString());
		// 设置表名
		pageList.setTableName(tablename.toString());
		// 设置表主键名
		pageList.setKeyId("GUID");// 主键
		// 设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");// 合计
		pageList = pageService.findWindowList(pd, pageList, "WWW");// 引用传递
		// pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "",
				pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();

	}
	/**
	 * 跳转详细信息页面
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
			Map cyzy = cyzyszService.getMapByGuid(guid);
			mv.addObject("cyzy", cyzy);
			mv.addObject("guid",guid);
		}
		mv.addObject("operateType", operateType);
		mv.setViewName("kjhs/cyzysz/cyzysz_edit");
		return mv;
	}
	/**
	 * 保存常用摘要信息
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(HttpSession session) {
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType))//新增保存 
		{  
			String kjzd = CommonUtil.getKjzd(session);
			String sszt = Constant.getztid(session);
			// 判断序号是否重复
			int flag = cyzyszService.getZyById(pd);
			if (flag!=0) {
				return MessageBox.show(false, "该摘要已经存在，请重新输入！");
			}
			result = cyzyszService.doAdd(pd,kjzd,sszt);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if ("U".equals(operateType)){ //修改
			String guid = pd.getString("guid");
			// 判断摘要是否重复
			int flag = cyzyszService.getUpdZyById(pd);
			   if (flag!=0) {
				  return MessageBox.show(false, "该摘要已经存在，请重新输入！");
			   }
			result = cyzyszService.updCyzysz(pd,guid);
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
		int i = cyzyszService.delete(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

	
}
