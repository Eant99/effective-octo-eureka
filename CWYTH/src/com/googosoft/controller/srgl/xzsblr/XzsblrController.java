package com.googosoft.controller.srgl.xzsblr;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.srgl.xzsblr.CW_GRSRZB;
import com.googosoft.pojo.wsbx.CW_CJKB;
import com.googosoft.pojo.wsbx.CW_DGZFB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.pojo.wsbx.Cw_DSZFB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.kylbx.KylbxService;
import com.googosoft.service.srgl.xzsblr.XzsblrService;
import com.googosoft.service.wsbx.RcbxService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;
import com.googosoft.websocket.message.MessageType;
import com.googosoft.websocket.message.YshMessage;

/**
 * 日常报销控制类
 * 
 * @author googosoft
 * 
 */
@Controller
@RequestMapping(value = "/xzsblr")
public class XzsblrController extends BaseController {
	@Resource(name = "pageService")
	private PageService pageService;

	@Resource(name = "xzsblrService")
	private XzsblrService xzsblrService;
	
	@Resource(name = "dictService")
	private DictService dictService;
	
	@Resource(name = "rcbxService")
	private RcbxService rcbxService;
	
	@Resource(name="fileService")
	private FileService fileService;//单例
	@Resource(name="kylbxService")
	private KylbxService kylbxService;//单例
	@Autowired
	private EchoService echoService;
	
	@Resource(name="gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;

	/**
	 * 跳转日常报销列表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goPageList")
	public ModelAndView goPageList() {
		ModelAndView mv = this.getModelAndView();
//		PageData pd = this.getPageData();
//		String dwbh = pd.getString("dwbh");
//		String rybh = pd.getString("rybh");
//		
//		List<Map<String, Object>> shztList = dictService.getDict(Constant.DJSHZT);//审核状态
//		List<Map<String, Object>> zffsList = dictService.getDict(Constant.ZFFSDM);//支付方式
//		
//		mv.addObject("mkbh", pd.getString("mkbh"));
//		mv.addObject("zffsList", zffsList);
//		mv.addObject("shztList", shztList);
//		mv.addObject("shztdm", pd.get("shztdm"));
//		mv.addObject("dwbh", dwbh);
//		mv.addObject("rybh", rybh);
		mv.setViewName("srgl/xzsblr/xzsblr_list");
		return mv;
	}
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String ymbz = pd.getString("ymbz");
		String shzt = pd.getString("shzt");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("(select k.guid,k.fflsh,k.shzt,k.checkshzt,k.fffa,k.procinstid,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
		sql.append(" k.ffpc,k.zy,k.ffzje,case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,k.ffry,");
		sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=k.SHZT)shztdm,");
		sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,k.ffry as rylxdm,");
		sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC");
		sql.append(" FROM cw_grsrzb K where K.jbr='"+CommonUtils.getRybh()+"' " );
		if("mb".equals(ymbz)){
			sql.append(	" and k.mbbz='1'");
		}
		StringBuffer strWhere = new StringBuffer();
		if("00".equals(shzt)) {
			strWhere.append(" and checkshzt='00' ");
		}else if("11".equals(shzt)) {
			strWhere.append(" and checkshzt='11' ");
		}else if("99".equals(shzt)) {
			strWhere.append(" and checkshzt='99'");
		}else {
			strWhere.append(" and checkshzt='00' ");
		}
		sql.append(	") B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("guid");
		pageList.setStrWhere(strWhere.toString());
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取日常报销列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getShPageList")
	@ResponseBody
	public Object getShPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		ModelAndView mv = new ModelAndView();
		String status = Validate.isNullToDefaultString(pd.getString("status"), "0");
		mv.addObject("status",status);
//		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"00");
		String ymbz = pd.getString("ymbz");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		if(Validate.noNull(status)&&"0".equals(status)){
			sql.append("(select k.guid,k.fflsh,k.shzt,k.fffa,k.jbr,K.PROCINSTID,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
			sql.append(" k.ffpc,k.zy,k.ffzje,case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,k.ffry,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=k.SHZT)shztdm,k.checkshzt,");
			sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,k.ffry as rylxdm,k.jsbz,");
			sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC,K.XMBH");
			sql.append(" FROM cw_grsrzb K left join ACT_RU_TASK B ON K.PROCINSTID = B.PROC_INST_ID_ where B.TASK_DEF_KEY_ <>'sqr' and B.assignee_='"+LUser.getGuid()+"'" );
		}else{			
			sql.append("(select k.guid,k.fflsh,k.shzt,k.fffa,k.jbr,K.PROCINSTID,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
			sql.append(" k.ffpc,k.zy,k.ffzje,case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,k.ffry,");
			sql.append(" (SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.LCSH+"' AND T.DM=k.SHZT)shztdm,k.checkshzt,");
			sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,k.ffry as rylxdm,k.jsbz,");
			sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC,K.XMBH");
			sql.append(" FROM cw_grsrzb K left join act_hi_actinst B ON K.PROCINSTID = B.PROC_INST_ID_ where  B.END_TIME_ IS NOT NULL and B.ACT_ID_ <>'sqr' and B.assignee_='"+LUser.getGuid()+"'" );
		}
		if("mb".equals(ymbz)){
			sql.append(	" and k.mbbz='1'");
		}
		sql.append(	" and k.checkshzt <> '00'");
		sql.append(	") B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("guid");
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取凭证生成列表页面（全部人员类型，审核状态为99的如果rylx为xnry则需要经过核算）
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLbPageList")
	@ResponseBody
	public Object getLbPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
//		String shzt = Validate.isNullToDefaultString(pd.get("shzt"),"00");
		String ymbz = pd.getString("ymbz");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" (select k.guid,k.fflsh,k.shzt,k.fffa,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
		sql.append(" k.ffpc,k.zy,k.ffzje, case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,");
		sql.append(" case k.shzt when '99' then '已通过' end as shztdm,");
		sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD') LRSJ,k.ffry as rylxdm,k.jsbz,");
		sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH) AS XMMC");
		sql.append(" FROM cw_grsrzb K where k.shzt ='99' and k.ffry='xnry' and k.jsbz='1'");
		sql.append(" union ");
		sql.append("select k.guid,k.fflsh,k.shzt,k.fffa,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
		sql.append(" k.ffpc,k.zy,k.ffzje, case k.ffry when 'xs' then '学生' when 'xwry' then '校外人员' when 'xnry' then '校内人员' end as rylx,");
		sql.append(" case k.shzt when '99' then '已通过' end as shztdm,");
		sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD') LRSJ,k.ffry as rylxdm,k.jsbz,");
		sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH) AS XMMC");
		sql.append(" FROM cw_grsrzb K where k.shzt ='99' and k.ffry <> 'xnry' ");
		sql.append(	") B ");
		pageList.setSqlText(" B.* ");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("b.fflsh");
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取核算列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getHsPageList")
	@ResponseBody
	public Object getHsPageList() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String ymbz = pd.getString("ymbz");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("(select k.guid,k.fflsh,k.shzt,k.checkshzt,k.fffa,(select mc from gx_sys_dmb where zl = 'zffs' and dm=k.fffs) as fffs,");
		sql.append(" k.ffpc,k.zy,k.ffzje,case k.ffry when 'xnry' then '校内人员' when 'xs'then '学生' when 'xwry' then '校外人员' end as rylx,k.ffry,");
		sql.append(" case  k.checkshzt when '99' then '已通过' end as shztdm,");
		sql.append(" TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,k.ffry as rylxdm,k.jsbz,");
		sql.append(" (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC");
		sql.append(" FROM cw_grsrzb K where K.jbr='"+CommonUtils.getRybh()+"' and k.checkshzt = '99' " );
		if("mb".equals(ymbz)){
			sql.append(	" and k.mbbz='1'");
		}
		sql.append(	") B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("guid");
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 学生银行卡号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/xsyhxx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object xsyhxx(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String dqdlrguid = pd.getString("dqdlrguid");
		List list = rcbxService.getXsyhxx(dqdlrguid);
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	/**
	 * 教师银行卡号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/jsyhxx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object jsyhxx(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String dqdlrguid = pd.getString("dqdlrguid");
		List list = rcbxService.getJsyhxx(dqdlrguid);
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	/**
	 * 校外人员银行卡号
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/xwryyhxx",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object xwryyhxx(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String dqdlrguid = pd.getString("dqdlrguid");
		List list = rcbxService.getXwryyhxx(dqdlrguid);
		Gson gson = new Gson();
		String datas = gson.toJson(list);
		return datas;
	}
	/**
	 * 编辑经济科目
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		String rylx = pd.getString("rylx");
		String procinstid = pd.getString("procinstid");
		mv.addObject("procinstid",procinstid);
		List zffs = dictService.getDict(Constant.ZFFS);
		mv.addObject("zffs",zffs);
		
		if("C".equals(operateType)){
			String mbbz = "0";
			Map map = new HashMap<>();
			String ffpc = xzsblrService.getFfpc();
			String guid = UuidUtil.get32UUID();
			String lsh = pd.getString("lsh");
			map.put("mbbz", mbbz);
			map.put("ffpc", ffpc);
			map.put("fflsh", lsh);
			mv.addObject("map",map);
			mv.addObject("guid",guid);
		}else if("U".equals(operateType)||"L".equals(operateType)||"S".equals(operateType)){
			String guid = pd.getString("guid");
			Map map = xzsblrService.getAll(guid);
			List list = new ArrayList<>();
			List yhklist = new ArrayList<>();
			if("xs".equals(rylx)){
				list = xzsblrService.getAllxsxx(guid);
				yhklist = xzsblrService.getXxyhk();
			}else if("xnry".equals(rylx)){
				list = xzsblrService.getAlljsxx(guid);
				yhklist = xzsblrService.getJsyhk();
			}else{
				list = xzsblrService.getAllxwryxx(guid);
				yhklist = xzsblrService.getXwryyhk();
			}
			mv.addObject("yhklist",yhklist);
			mv.addObject("map",map);
			mv.addObject("srmx",list);
			mv.addObject("guid",guid);
		}else if("T".equals(operateType)){
			String guid = pd.getString("guid");
			String lsh = pd.getString("lsh");
			Map map = xzsblrService.getAllmb(guid);
			map.put("fflsh", lsh);
			List list = new ArrayList<>();
			List yhklist = new ArrayList<>();
			if("xs".equals(rylx)){
				list = xzsblrService.getAllxsxx(guid);
				yhklist = xzsblrService.getXxyhk();
			}else if("xnry".equals(rylx)){
				list = xzsblrService.getAlljsxx(guid);
				yhklist = xzsblrService.getJsyhk();
			}else{
				list = xzsblrService.getAllxwryxx(guid);
				yhklist = xzsblrService.getXwryyhk();
			}
			mv.addObject("yhklist",yhklist);
			mv.addObject("map",map);
			mv.addObject("srmx",list);
			mv.addObject("guid",guid);
		}
		String ymbz = pd.getString("ymbz");
		mv.addObject("ymbz",ymbz);
		if("xs".equals(rylx)){
			mv.setViewName("srgl/xzsblr/xzsblr_addxs");
		}else if("xnry".equals(rylx)){
			mv.setViewName("srgl/xzsblr/xzsblr_addxnry");
		}else{
			mv.setViewName("srgl/xzsblr/xzsblr_addxwry");
		}
		List jjkm = xzsblrService.getJjkm();
		mv.addObject("jjkm",jjkm);
		mv.addObject("rylx",rylx);
		mv.addObject("operateType",operateType);
		return mv;
	}	
	/**
	 * 随机生成32位的guid
	 * @return
	 */
	@RequestMapping(value="/getGuid")
	@ResponseBody
	public String getGuid(){
		return get32UUID();
	}
	/**
	 * 添加个人收入信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSave(CW_GRSRZB grsrzb){
		String operateType = this.getPageData().getString("operateType");
		String rybhs = this.getPageData().getString("rybhs");
		String ffjes = this.getPageData().getString("ffjes");
		String yhkhs = this.getPageData().getString("yhkhs");
		String fffs = this.getPageData().getString("fffs");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = xzsblrService.doAdd(grsrzb,rybhs,ffjes,yhkhs); 
			if(i==1){
				if(Validate.isNull(grsrzb.getGuid())){
					b = "{\"success\":\"true\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
				}else{
					b = "{\"success\":\"true\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
				}
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i = xzsblrService.doUpdate(grsrzb,rybhs,ffjes,yhkhs);
			if(i==1){
				b = "{\"success\":\"true\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("T".equals(operateType)){
			i = xzsblrService.doTqmb(grsrzb,rybhs,ffjes,yhkhs,fffs);
			if(i==1){
				b = "{\"success\":\"true\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"gid\":\""+grsrzb.getGuid()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}
		else{
			b = "{\"success\":\"false\",\"dwbh\":\"\",\"msg\":\"operateType参数不能为空！！！\"}";
		}
		return b ;
	}
	/**
	 * 保存模版
	 */
	@RequestMapping(value="/doSavemb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSavemb(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int k = xzsblrService.doSavemb(guid);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"模版保存成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"模版保存失败！\"}";
		}
		return b;
	}
	/**
	 * 提交操作
	 */
	@RequestMapping(value="/submit",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object submit(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int k = xzsblrService.submit(guid);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"提交成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"提交失败！\"}";
		}
		return b;
	}
	/**
	 * 通过操作
	 */
	@RequestMapping(value="/pass",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object pass(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int k = xzsblrService.pass(guid);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"通过成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"通过失败！\"}";
		}
		return b;
	}
	/**
	 * 核算操作
	 */
	@RequestMapping(value="/doAccount",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doAccount(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int k = xzsblrService.doAccount(guid);
		if(k>0){
			b= "核算成功！";
		}else{
			b= "核算失败！";
		}
		return b;
	}
	/**
	 * 退回操作
	 */
	@RequestMapping(value="/nopass",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object nopass(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int k = xzsblrService.nopass(guid);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"退回成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"退回失败！\"}";
		}
		return b;
	}
	/**
	 * 撤销提交
	 */
	@RequestMapping(value="/chexiao",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object chexiao(){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int k = xzsblrService.chexiao(guid);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"撤销成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"撤销失败！\"}";
		}
		return b;
	}
	/**
	 * 删除模版
	 */
	@RequestMapping(value="/doDelmb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doDelmb(){
		String guid = this.getPageData().getString("guid");
		int i = xzsblrService.doDelmb(guid);
		if(i > 0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	/**
	 * 删除薪资申报录入信息
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doDelete(){
		String guid = this.getPageData().getString("guid");
		int i = xzsblrService.doDelete(guid);
		if(i > 0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
} 
