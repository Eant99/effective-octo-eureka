package com.googosoft.controller.wsbx.pzthgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.wsbx.CW_CLFBXZB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.wsbx.CcbxService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.service.wsbx.jkgl.JksqService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/bxpzth")
public class BxpzthController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "dictService")
	private DictService dictService;
	
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	@Resource(name = "ccbxService")
	private CcbxService ccbxService;
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;
	@Resource(name="jksqService")
	private JksqService jksqService;//分页单例
	
	@Resource(name="fileService")
	private FileService fileService;//单例
	/**
	 * 跳转日常报销列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gorcbx")
	public ModelAndView goRcbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.setViewName("wsbx/pzthgl/pzthrcbx_list");
		return mv;
	}
	/**
	 * 获取 日常报销 列表 数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getRcbxthPageList")
	@ResponseBody
	public Object getRcbxthPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		StringBuffer sql = new StringBuffer();
		sql.append("(select t.guid,t.djbh, t.shzt,t.bxr,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxr) as sqr,");
		sql.append(" t.XMMC AS XMGUID,t.PROCINSTID as PROCINSTID,to_char(t.czrq,'yyyy-mm-dd') as sqrq,");
		sql.append(" (select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = t.szbm) as szbm,t.bxzje as bxje,");
		sql.append(" (select wm_concat(xmmc) from CW_RCBXMXB m where m.zbid=t.guid) as bxxm ");
		sql.append("  from cw_rcbxzb t )K ");
		pageList.setSqlText(" * ");
		pageList.setTableName(sql.toString());
		pageList.setKeyId("guid");
		String strWhere = "  and k.shzt = '80' ";// and k.bxr='"+LUser.getGuid()+"'
		pageList.setStrWhere(strWhere);
		pageList.setHj1("");
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * go 日常报销编辑
	 */
	@RequestMapping(value="/goRcbxEdit")
	public ModelAndView goRcbxEdit(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		//此处生成申请主表的主键id
		String guid = pd.getString("guid");
		String operateType = pd.getString("operateType");
		mv.addObject("guid", guid);
		mv.addObject("operateType", operateType);
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);	
		//获取 日常报销 主表信息
		Map zbxx = rcbxService.getBxzbById(guid);
		//获取发票信息
		List fpxxList = rcbxService.getFpxx(guid);
		//项目回显 list
		List xmhxlist = rcbxService.getXmhxList(guid);
		mv.addObject("zbxx", zbxx);
		mv.addObject("fplist", fpxxList);
		mv.addObject("xmlist", xmhxlist);
		mv.setViewName("wsbx/pzthgl/pzthrcbx_edit");
		return mv;
	}
	/**
	 * 删除日常报销 + 删除退回凭证
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doDelete(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String type = pd.getString("type");
		String b = "";
		int k = rcbxService.doDelete(guid,type);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		return b;
	}
	/**
	 * 保存  日常 编辑的信息
	 */
	@RequestMapping(value = "/doUpdateRC", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateRC(Rcbxzb rcbx){
		int result = 0;
		result = rcbxService.updatePzthRC(rcbx);
		if (result > 0) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	@RequestMapping(value = "/doUpdateRcBxmx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateRcBxmx(){
		PageData pd = this.getPageData();
		int result = 0;
		Gson gson = new Gson();
		String jsonStr = pd.getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		if (list.size()>0) {
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				 String fymc = Validate.isNullToDefaultString(map.get("fyid"), "");
				 String fjzs = Validate.isNullToDefaultString(map.get("fjzs"), "");
				 String bz = Validate.isNullToDefaultString(map.get("bz"), "");
				 String guid = Validate.isNullToDefaultString(map.get("guid"), "");
				 result += rcbxService.doUpdateRcBxmx(fymc,fjzs,bz,guid);
			}
		}
		if (result == list.size()) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	/**
	 *  日常报销  复核
	 */
	@RequestMapping(value = "/RcbxSubmit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object RcbxSubmit(){
		int result = 0;
		String guid = this.getPageData().getString("guid");
		result = rcbxService.RcbxSubmit(guid);
		if (result > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_CHECK);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_CHECK);
		}
	}
	/**
	 * 跳转 差旅费 报销列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goclfbx")
	public ModelAndView goCLbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.setViewName("wsbx/pzthgl/pzthclfbx_list");
		return mv;
	}
	/**
	 * 获取 差旅费  列表 数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getclfbxPageList")
	@ResponseBody
	public Object getclfbxPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		StringBuffer sql = new StringBuffer();
		sql.append(" (select t.guid,t.djbh,t.shzt,t.sqr as sqrguid,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.sqr) as sqr,czrq as sqrq,");
		sql.append("(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.sqr) as szbm,");
		sql.append(" t.CCYWGUID,t.XMMC AS XMGUID,t.PROCINSTID,to_number(t.bxzje) as bxje," );
		sql.append(" (SELECT C.MC FROM GX_SYS_DMB C  WHERE C.ZL = 'cclx'  AND C.DM = t.CCLX) AS CCLX,");
		sql.append("(select wm_concat(xmmc) from cw_ccsqspb_xm xm left join xminfos s on s.guid=xm.jfbh where xm.ccsqid=t.ccywguid) as bxxm " );
		sql.append(" from cw_clfbxzb t )K ");
		pageList.setSqlText("*");
		pageList.setTableName(sql.toString());
		pageList.setKeyId("guid");
		String strWhere = "  and k.shzt = '80'";//  and k.sqrguid='"+LUser.getGuid()+"'
		pageList.setStrWhere(strWhere);
		pageList.setHj1("");
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * go 差旅费  报销编辑
	 */
	@RequestMapping(value="/goClfbxEdit")
	public ModelAndView goClfbxEdit(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String operateType = pd.getString("operateType");
		mv.addObject("guid", guid);
		mv.addObject("operateType", operateType);
		//出差类型
		List<Map<String, Object>> cclx = dictService.getDict("cclx");
		mv.addObject("cclxList",cclx);
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		Map map = ccbxService.getZbPage(guid);
		//查询 项目信息 
		String ccywguid = pd.getString("ccywguid");
		List xmlist=ccbxService.getxmxxlist(ccywguid);
		mv.addObject("xmlist", xmlist);
		List<Map<String, Object>> fpList = ccbxService.getFpListByZbguid(guid);
		List<Map<String, Object>> mxList = ccbxService.getMxListByZbguid(guid);
		List<Map<String, Object>> ryList = ccbxService.getRyListByZbguid(guid);	
		mv.addObject("clfbxzb",map);
		mv.addObject("fpList",fpList);
		mv.addObject("mxList",mxList);
		mv.addObject("ryList",ryList);	
		mv.setViewName("wsbx/pzthgl/pzthclfbx_edit");
		return mv;
	}
	/**
	 * 保存  差旅费 编辑的信息
	 */
	@RequestMapping(value = "/doUpdateCLFMX", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateCLFMX(){
		int result = 0;
		PageData pd = this.getPageData();
		Gson gson = new Gson();
		String jsonStr = pd.getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		if (list.size()>0) {
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				 String fjzs = Validate.isNullToDefaultString(map.get("fjzs"), "");
				 String guid = Validate.isNullToDefaultString(map.get("guid"), "");
				 result += ccbxService.doUpdateCLFMX(fjzs,guid);
			}
		}
		if (result == list.size()) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	@RequestMapping(value = "/doUpdateCLF", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateCLF(CW_CLFBXZB clfbxzb){
		int result = 0;
		result = ccbxService.doUpdateCLF(clfbxzb);
		if (result > 0) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	/**
	 *  差旅费   复核
	 */
	@RequestMapping(value = "/ClfbxSubmit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object ClfbxSubmit(){
		int result = 0;
		String guid = this.getPageData().getString("guid");
		result = ccbxService.ClfbxSubmit(guid);
		if (result > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_CHECK);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_CHECK);
		}
	}
	/**
	 * 跳转 公务接待 报销列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gogwjdbx")
	public ModelAndView goGwjdbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.setViewName("wsbx/pzthgl/pzthgwjdbx_list");
		return mv;
	}
	/**
	 * 跳转 公务接待 报销列表 数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getgwjdPageList")
	@ResponseBody
	public Object getgwjdPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		StringBuffer sql = new StringBuffer();
		sql.append( " ( select t.guid,t.djbh,t.shzt,t.bxryid,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxryid) as sqr,");
		sql.append(" t.PROCINSTID,to_char(t.czrq,'yyyy-mm-dd') as sqrq," );
		sql.append(" t.JDCS,to_char(t.JDRQ,'yyyy-mm-dd') as JDRQ, ");
		sql.append(" (select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = t.SZBM) as szbm," );
		sql.append(" t.bxje from cw_gwjdfbxzb t ) K" );
		
		pageList.setSqlText("*");
		pageList.setTableName(sql.toString());
		pageList.setKeyId("guid");
		String strWhere = "  and k.shzt = '80' "; // and k.bxryid='"+LUser.getGuid()+"'
		pageList.setStrWhere(strWhere);
		pageList.setHj1("");
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * go 公务接待  报销编辑
	 */
	@RequestMapping(value="/goGwjdbxEdit")
	public ModelAndView goGwjdbxEdit(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String operateType = pd.getString("operateType");
		mv.addObject("guid", guid);
		mv.addObject("operateType", operateType);
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		Map<String, Object> gwjdmx = gwjdfbxsqService.getGwjdmxMapById(pd);
		mv.addObject("gwjdmx",gwjdmx);
		mv.setViewName("wsbx/pzthgl/pzthgwjdbx_edit");
		 return mv;
	}
	/**
	 * 保存  公务接待 编辑的信息
	 */
	@RequestMapping(value = "/doUpdateGWJD", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateGWJD(){
		int result = 0;
		PageData pd = this.getPageData();
		String fjzs = pd.getString("fyfjzs");
		String jdsy = pd.getString("jdsy");
		String guid = pd.getString("guid");
		result = gwjdfbxsqService.doUpdateGWJD(fjzs,jdsy,guid);
		if (result >0) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	/**
	 *  公务接待   复核
	 */
	@RequestMapping(value = "/GwjdbxSubmit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object GwjdbxSubmit(){
		int result = 0;
		String guid = this.getPageData().getString("guid");
		result = gwjdfbxsqService.GwjdbxSubmit(guid);
		if (result > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_CHECK);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_CHECK);
		}
	}
	/**
	 * 跳转 借款 报销列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gojkbx")
	public ModelAndView goJkbxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("mkbh", pd.getString("mkbh"));
		mv.setViewName("wsbx/pzthgl/pzthjkbx_list");
		return mv;
	}
	/**
	 * 跳转 借款 报销列表 数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getJkbxPageList")
	@ResponseBody
	public Object getJkbxPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		StringBuffer sql = new StringBuffer();
		sql.append( " ( select t.gid,t.djbh,t.shzt,t.jkr,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.jkr) as sqr,");
		sql.append(  "to_char(t.jksj,'yyyy-mm-dd') as sqrq,   " );
		sql.append( "(SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=t.SZBM)AS SZBM,  " );
		sql.append( " TO_CHAR(t.JKSJ,'YYYY-MM-DD') as JKSJ,jkzje " );
		sql.append(" from CW_JKYWB t ) K " );
		
		pageList.setSqlText("*");
		pageList.setTableName(sql.toString());
		pageList.setKeyId("gid");
		String strWhere = "  and k.shzt = '80'";// and k.jkr='"+LUser.getGuid()+"'
		pageList.setStrWhere(strWhere);
		pageList.setHj1("");
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * go 借款  报销编辑
	 */
	@RequestMapping(value="/goJkbxEdit")
	public ModelAndView goJkbxEdit(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String operateType = pd.getString("operateType");
		mv.addObject("guid", guid);
		mv.addObject("operateType", operateType);
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		String zffs = this.getPageData().getString("zffs");
		Map map = jksqService.getObjectById(guid);
		//对公支付
		List dgList = jksqService.getDgList(guid);
		mv.addObject("dgList",dgList);
		//对私支付
		List dsList = jksqService.getDsList(guid);
		mv.addObject("dsList",dsList);
		List<Map<String,Object>> dlryhklist=gwjdfbxsqService.getdlryhklist();
		mv.addObject("dlryhklist", dlryhklist);
		mv.addObject("xmxxlist",jksqService.getXmxxListById(guid));
		mv.addObject("jksq", map);
		mv.addObject("guid",guid);
		mv.setViewName("wsbx/pzthgl/pzthjkbx_edit");
		 return mv;
	}
	/**
	 * 保存  借款 编辑的信息
	 */
	@RequestMapping(value = "/doUpdateJK", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdateJK(){
		int result = 0;
		PageData pd = this.getPageData();
		String fjzs = pd.getString("fjzs");
		String jksy = pd.getString("jksy");
		String gid = pd.getString("gid");
		result = jksqService.doUpdateJK(fjzs,jksy,gid);
		if (result >0) {
			return "{\"success\":true,\"msg\":\"信息保存成功！\"}"; 
		} else {
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	/**
	 *  借款   复核
	 */
	@RequestMapping(value = "/JKbxSubmit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object JKbxSubmit(){
		int result = 0;
		String gid = this.getPageData().getString("gid");
		result = jksqService.JKbxSubmit(gid);
		if (result > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_CHECK);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_CHECK);
		}
	}
	/**
	 * 判断是否 冲过 这个 借款
	 */
	@RequestMapping(value = "/checksfcjk", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object checksfcjk(){
		int result = 0;
		String gid = this.getPageData().getString("gid");
		result = jksqService.checksfcjk(gid);
		if (result > 0) {
			return "{\"success\":true,\"msg\":\"已经冲过此条借款单据，不能删除！\"}";
		} else {
			return "{\"success\":flase,\"msg\":\"可以删除\"}";
		}
	}

}
