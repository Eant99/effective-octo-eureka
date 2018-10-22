package com.googosoft.controller.wsbx.ccyw;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.base.ProvincesService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.wsbx.ccyw.CcywsqService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;

/**
 * 出差业务申请controller
 * @author fugangjie
 */
@Controller
@RequestMapping("wsbx/ccyw/ccywsq")
public class CcywsqController extends BaseController{
	
	@Resource(name="ccywsqService")
	CcywsqService ccywsqService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	@Resource(name="fileService")
	FileService fileService;
	@Autowired
	private EchoService echoService;
	
	@Resource(name="provincesService")
	private ProvincesService provincesService;
	
	
	/**
	 * 初始化数据字典下拉框
	 * @param mv
	 */
	public void iniSelect(ModelAndView mv) {
		mv.addObject("cclxList",dictService.getDict(Constant.CCLX));
		mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
		mv.addObject("jtgjList",dictService.getDict(Constant.JTGJ));
	}
	/**
	 * 初始化登录人员信息
	 * @param mv
	 */
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("szbm",LUser.getDwmc());
		mv.addObject("ryxm","("+LUser.getRybh()+")"+LUser.getRyxm());
		mv.addObject("today",DateUtil.getDay());
	}
	/**初始化文件配置信息
	 * 
	 * @param mv
	 */
	public void iniFile(ModelAndView mv) {
		//照片附件列表
		String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}
	
	/**
	 * 初始化签名信息
	 * @param mv
	 */
	public void iniQianming(ModelAndView mv) {
		//照片附件列表
		String[] fjxx = fileService.getFjList((String)ccywsqService.getLoginUserLd().get("dwld"),"",this.getRequest().getContextPath()).split("#",-1);
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
		//照片附件列表
		String[] fjxx2 = fileService.getFjList((String)ccywsqService.getLoginUserLd().get("fgld"),"",this.getRequest().getContextPath()).split("#",-1);
		mv.addObject("fjView2",fjxx2[0]);
		mv.addObject("fjConfig2",fjxx2[1]);
	}
	/**
	 * 跳转到出差业务申请列表页面
	 * @return
	 */
	@RequestMapping("/goCcywsqPage")
	public ModelAndView goCcywsqPage() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.addObject("shztList",dictService.getDict(Constant.SHZTDM));
		mv.setViewName("wsbx/ccyw/ccywsq/ccywsq_list");
		return mv;
	}
	
	/**
	 * 获取列表数据
	 * @return
	 */
	@RequestMapping(value="getCcywsqPageData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getCcywsqPageData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "01");
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		StringBuffer  tableName = new StringBuffer();
		sqltext.append(" * ");
		tableName.append("  (select SQRQ,GUID,SQRXM,SZBM,DJBH ,ccts,PROCINSTID,SHZTDM,CCLX,shzt,xmmc,CHECKSHZT FROM  ");
		tableName.append("  (select  A.CHECKSHZT,TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ, A.GUID, A.SQRXM,  A.SZBM,  A.DJBH,  A.CCTS, A.PROCINSTID,  A.SHZT AS SHZTDM, ");
		tableName.append("  (SELECT C.MC FROM GX_SYS_DMB C  WHERE C.ZL = 'cclx'  AND C.DM = A.CCLX) AS CCLX,(SELECT C.MC  FROM GX_SYS_DMB C  WHERE C.ZL = '11033'   AND C.DM = A.SHZT) AS SHZT, ");
		tableName.append("  (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B  ON S.JFBH = B.guid  WHERE S.CCSQID = A.GUID) AS xmmc from ");
		tableName.append("  CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M  ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B  ON A.GUID = M.CCSQID where 1 = 1 ");
		tableName.append("   and (A.SQR = '"+LUser.getGuid()+"' or A.czr='"+LUser.getGuid()+"')");
		if("00".equals(shzt)){
			tableName.append(" and CHECKSHZT='00'  " );
		}else if("11".equals(shzt)){
			tableName.append(" and CHECKSHZT='11'  " );
		}else if("99".equals(shzt)){
			tableName.append(" and CHECKSHZT='99'  " );
		}else{
			tableName.append(" and 1=1  " );
		}
		tableName.append( " ) L  " );
		tableName.append("  group by sqrq, l.GUID, SQRXM,  SZBM,  DJBH,  CCTS, PROCINSTID ,CCLX,shzt,xmmc,SHZTDM,CHECKSHZT )Q");
		StringBuffer strWhere = new StringBuffer();
		//设置条件
		pageList.setSqlText(sqltext.toString());
		
		pageList.setTableName(tableName.toString());
		pageList.setKeyId("GUID");
		pageList.setStrWhere(strWhere.toString());
		// 设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 当前登录人被授权的项目
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getjFPageList")
	@ResponseBody
	public Object getPageListByLoginXm() throws Exception {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String xmguid = pd.getString("xmguid");
		String type =pd.getString("type");
		String ryid = Validate.isNullToDefaultString(pd.getString("ryid"), LUser.getGuid());
		Map userMap = CommonUtil.getRyxx(ryid);
		String rybh = userMap.get("RYBH")+"";
		String dwbh = userMap.get("DWBH")+"";
		String fromPage = Validate.isNullToDefaultString(pd.getString("fromPage"), "");
		String xmguids = xmguid.replace(",", "','");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		//当前登录人的id
		sql.append("(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.JFLX+"' and B.dm = A.jflx) AS JFLXMC,"
				+ "(select B.mc from gx_sys_dmb B where B.zl = '"+Constant.XMDL+"' and B.dm = A.xmdl) AS XMDLMC,"
				+ "(select '('||b.rybh||')'||b.xm from gx_sys_ryb b where b.rybh = a.fzr) as fzrxm,"
				+ "A.*");
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName(" XMINFOS A ");
		// 主键
		pageList.setKeyId("A.GUID");
		// 设置WHERE条件
		StringBuffer strWhere = new StringBuffer();
		if("xmxx".equals(type)){
			if(Validate.noNull(guid)){
				strWhere.append(" and a.guid not in ('"+guid+"') ");
			}
		}else{
			if("jksqEdit".equals(fromPage)){
				strWhere.append(" and A.ye<> 0 and jflx='01' and bmbh='"+dwbh+"' ");
			}else{
				strWhere.append(" and A.ye<> 0 and (((bsqr='"+ryid+"' or bsqr='"+rybh+"') and jflx = '02') or (jflx='01' and bmbh='"+dwbh+"')) and A.xmdl not in('1','2') ");
			}
			if(Validate.noNull(guid)){
				strWhere.append(" and a.guid not in ('"+guid+"') ");
			}
			if(Validate.noNull(xmguid)){
				strWhere.append(" and a.guid in('"+xmguids+"')");
			}
			strWhere.append("  and a.guid not in (select nvl(jfbh, '0') from XMINFOS) ");
		}
//		String strWhere =" and A.ye<> 0 and ((bsqr='"+loginId+"' or bsqr='"+LUser.getRybh()+"') and jflx = '02') or (jflx='01' and bmbh='"+LUser.getDwbh()+"')  and a.guid not in ('"+guid+"')";
		pageList.setStrWhere(strWhere.toString());
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		//pageList = pageService.findPageList(pd, pageList);
		pageList = pageService.findWindowList(pd,pageList,"JFB");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 联想输入得到项目信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getxmxx")
	@ResponseBody
	public Object getxmxx(HttpSession session) throws Exception {
	
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String xmbh = pd.getString("xmmc");
		
		Map map = ccywsqService.getxmxx(xmbh);
		
		return map;
	}
	/**
	 * 教师信息弹窗
	 * @return
	 */
	@RequestMapping("/goJsxxPage")
	public ModelAndView goJsxxPage() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.setViewName("wsbx/ccyw/ccywsq/jsxx_list");
		return mv;
	}
	/**
	 * 跳转到出差业务申请添加页面
	 */
	@RequestMapping("/goCcywsqAddPage")
	public ModelAndView goCcywsqAddPage() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		iniSelect(mv);
		iniFile(mv);
//		String djbh = GenAutoKey.createKeyforth("cw_ccsqspb", "CC", "djbh");
//		mv.addObject("djbh",djbh);
		mv.addObject("guid",GenAutoKey.get32UUID());
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		mv.setViewName("wsbx/ccyw/ccywsq/ccywsq_add");
		return mv;
	}
	
	
	/**
	 * 经费设置弹窗（多选）
	 * @return
	 */
	@RequestMapping("/jfszList")
	public ModelAndView jfsz() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.addObject("flag",this.getPageData().getString("flag"));
		mv.addObject("guid",this.getPageData().getString("guid"));
		mv.addObject("xmguid",this.getPageData().getString("xmguid"));
		mv.addObject("type",this.getPageData().getString("type"));
		mv.setViewName("wsbx/ccyw/ccywsq/jfsz_list");
		return mv;
	}
	/**
	 * 经费设置弹窗（单选）
	 * @return
	 * 2018年3月12日16:40:57
	 * @author jiatong
	 */
	@RequestMapping("/jfszdxList")
	public ModelAndView jfszdx() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.addObject("flag",this.getPageData().getString("flag"));
		mv.addObject("guid",this.getPageData().getString("guid"));
		mv.addObject("xmguid",this.getPageData().getString("xmguid"));
		mv.addObject("type",this.getPageData().getString("type"));
		System.err.println("guid="+this.getPageData().getString("guid"));
		System.err.println("xmguid="+this.getPageData().getString("xmguid"));
		mv.setViewName("wsbx/ccyw/ccywsq/jfszdx_list");
		return mv;
	}
	/**
	 * 页面跳转
	 * @return
	 */
	@RequestMapping("/pageSkip")
	public ModelAndView pageSkip() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniLogin(mv);
		String pageName = pd.getString("pageName");
		mv.setViewName("wsbx/ccyw/ccywsq/"+pageName);
		return mv;
	}
	/**
	 * 跳转到出差业务申请编辑页面
	 */
	@RequestMapping("/goCcywsqEditPage")
	public ModelAndView goCcywsqEditPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniLogin(mv);
		iniSelect(mv);
		iniFile(mv);
		Map<String, Object> ccywsq = ccywsqService.getCcywsqMapById(pd.getString("guid"));
		mv.addObject("ccywsq",ccywsq);
		String[] jtgj = (ccywsq.get("jtgj")+"").split(",");
		String jtgjcl = (ccywsq.get("jtgj")+"");
		mv.addObject("jtgjcl", jtgjcl);
		mv.addObject("length",jtgj.length);
		mv.addObject("txryList",ccywsqService.getTxryListById(pd.getString("guid")));
		mv.addObject("xmxxlist",ccywsqService.getXmxxListById(pd.getString("guid")));
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		mv.addObject("CitiesList",provincesService.getCitiesByProvince(ccywsq.get("provinceid")+""));
		mv.setViewName("wsbx/ccyw/ccywsq/ccywsq_edit");
		return mv;
	}
	public void test() {
		 List<Map> lis = new ArrayList<Map>();  
	     Map<Integer, String> map = new HashMap<Integer, String>();  
	     Map<Integer, Integer> ma = new HashMap<Integer, Integer>();  
		map.put(1, "aaa");  
        map.put(2, "bbb");  
        map.put(3, "ccc");  
  
        ma.put(1, 1);  
        ma.put(2, 2);  
        ma.put(3, 3);  
  
        lis.add(map);  
        lis.add(ma);  
	  
        for (Map m : lis) {  
            Iterator<Map.Entry<Object, Object>> it = m.entrySet().iterator();  
            while (it.hasNext()) {  
                Map.Entry<Object, Object> entry = it.next();  
                System.out.println(entry.getKey() + " " + entry.getValue());  
            }  
        }  
	    
	}
	/**
	 * 跳转到出差业务申请查看页面
	 * @return
	 */
	@RequestMapping("/goCcywsqViewPage")
	public ModelAndView goCcywsqViewPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniLogin(mv);
		iniSelect(mv);
		iniFile(mv);
		Map<String, Object> ccywsq = ccywsqService.getCcywsqMapById(pd.getString("guid"));
		String[] jtgj = (ccywsq.get("jtgj")+"").split(",");
		String jtgjcl = (ccywsq.get("jtgj")+"");
		mv.addObject("ccywsq",ccywsq);
		mv.addObject("jtgjcl", jtgjcl);
		mv.addObject("length",jtgj.length);
		mv.addObject("txryList",ccywsqService.getTxryListById(pd.getString("guid")));
		mv.addObject("xmxxlist",ccywsqService.getXmxxListById(pd.getString("guid")));
		mv.setViewName("wsbx/ccyw/ccywsq/ccywsq_view");
		return mv;
	}
	/**
	 * 打印页面
	 * @return
	 */
	@RequestMapping("/goPrintPage")
	public ModelAndView goPrintPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniQianming(mv);
		Map<String, Object> ccywsq = ccywsqService.getCcywsqMapById(pd.getString("guid"));
		ccywsq.put("ryxm",((String)ccywsq.get("sqrxm")));
		ccywsq.put("sqrmc", ((String)ccywsq.get("sqrmc")));
		ccywsq.put("szbmmc", WindowUtil.getEndText((String)ccywsq.get("szbm")));
		mv.addObject("ccywsq",ccywsq);
		mv.addObject("txryList",ccywsqService.getTxryListById(pd.getString("guid")));
		mv.addObject("bmldsh",ccywsqService.getBmldsh(pd.getString("procinstid")));
		mv.addObject("fgldsh",ccywsqService.getFgldsh(pd.getString("procinstid")));
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
		String time=sdf.format(new Date());
		mv.addObject("time",time);
		mv.setViewName("wsbx/ccyw/ccywsq/PrintSample");
		return mv;
	}
	/**
	 * 添加出差业务申请
	 * @return
	 */
	@RequestMapping(value="/ccywsqAdd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object ccywsqAdd() {
		if(ccywsqService.addCcywsq(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}	
	/**
	 * 编辑出差业务申请
	 * @return
	 */
	@RequestMapping(value="/ccywsqEdit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object ccywsqEdit() {
		if(ccywsqService.editCcywsq(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}
	
	/**
	 * 编辑出差业务申请
	 * @return
	 */
	@RequestMapping(value="/ccywsqEditBybzr",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object ccywsqEditBybzr() {
		if(ccywsqService.editCcywsqBybzr(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"保存成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"保存失败，请稍后重试！\"}";
		}
	}
	
	/**
	 * 删除出差业务申请信息
	 * @return
	 */
	@RequestMapping(value="/ccywsqDelete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object delete() {
		if(ccywsqService.deleteCcywsq(this.getPageData()) > 0) {
			return "{\"success\":true,\"msg\":\"删除成功！\"}";
		}else {
			return "{\"success\":false,\"msg\":\"删除失败，请稍后重试！\"}";
		}
	}
	/**
	 * 提交
	 * @return
	 */
	@RequestMapping(value="/submit",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object submit() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		if(Validate.isNull(guid)){
			return "{\"success\":false,\"msg\":\"提交失败，请稍后重试！\"}";
		}
		String guids[] = guid.split(",");
		int m = 0;
		DshInfoMap msgMap = new DshInfoMap();
		for(int i=0;i<guids.length;i++){
			String id = Validate.isNullToDefaultString(guids[i],"");
			if(Validate.noNull(id)){
				String procinstid =  ccywsqService.submitBySqr(id);
				if(Validate.noNull(procinstid)) {
					m++;
					//从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getCcywsqDshxxMsg(id);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
		}
		if(m>0){
			EchoUtil.batchSendDshxxMsg(msgMap);
			return "{\"success\":true,\"msg\":\"提交成功！\"}";
		}else{
			return "{\"success\":false,\"msg\":\"提交失败，请稍后重试！\"}";
		}
	}
	/**
	 * 检查项目编号是否存在
	 * @return
	 */
	@RequestMapping("/checkDjbhExist")
	@ResponseBody
	public Object checkCcywsqbhExist() {
		if(ccywsqService.checkDjbhExist(this.getPageData())) {
			return "{\"exist\":true}";
		}else {
			return "{\"exist\":false}";
		}
	}
	
	/**
	 * 查看该谁审核
	 * @return
	 */
	@RequestMapping("/checkWhoSh")
	@ResponseBody
	public Map checkWhoSh() {
		return ccywsqService.checkWhoSh(this.getPageData());
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		String shzt=pd.getString("shzt");
		String id=pd.getString("id");
		//设置查询字段名
		StringBuffer sqltext=new StringBuffer();
		sqltext.append("select * from");
		sqltext.append("  (select SQRQ,GUID,SQRXM,SZBM,DJBH ,ccts,PROCINSTID,SHZTDM,CCLX,shzt,xmmc as ktmc,CHECKSHZT FROM  ");
		sqltext.append("  (select  A.CHECKSHZT,TO_CHAR(A.SQRQ, 'YYYY-MM-DD') SQRQ, A.GUID, A.SQRXM,  A.SZBM,  A.DJBH,  A.CCTS, A.PROCINSTID,  A.SHZT AS SHZTDM, ");
		sqltext.append("  (SELECT C.MC FROM GX_SYS_DMB C  WHERE C.ZL = 'cclx'  AND C.DM = A.CCLX) AS CCLX,(SELECT C.MC  FROM GX_SYS_DMB C  WHERE C.ZL = '11033'   AND C.DM = A.SHZT) AS SHZT, ");
		sqltext.append("  (SELECT WM_CONCAT(B.XMMC) FROM CW_CCSQSPB_XM S LEFT JOIN CW_JFSZB B  ON S.JFBH = B.guid  WHERE S.CCSQID = A.GUID) AS xmmc from ");
		sqltext.append("  CW_CCSQSPB A LEFT JOIN CW_CCSQSPB_XM M  ON A.GUID = M.CCSQID LEFT JOIN CW_JFSZB B  ON A.GUID = M.CCSQID where 1 = 1 ");
		sqltext.append("   and (A.SQR = '"+LUser.getGuid()+"' or A.czr='"+LUser.getGuid()+"')");
		//判断审核状态
		if("00".equals(shzt)){
			sqltext.append(" and CHECKSHZT='00'  " );
		}else if("11".equals(shzt)){
			sqltext.append(" and CHECKSHZT='11'  " );
		}else if("99".equals(shzt)){
			sqltext.append(" and CHECKSHZT='99'  " );
		}else{
			sqltext.append(" and 1=1  " );
		}
		if(Validate.noNull(id)){
			sqltext.append(" and a.guid in ('"+id+"')  " );
		}
		sqltext.append( " ) L  " );
		sqltext.append("  group by sqrq, l.GUID, SQRXM,  SZBM,  DJBH,  CCTS, PROCINSTID ,CCLX,shzt,xmmc,SHZTDM,CHECKSHZT )Q");
		//导出方法
		System.err.println(sqltext.toString());
		ccywsqService.expExcelNew(realfile,filedisplay,sqltext.toString());
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 导出教师人员信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		//当前登陆者的人员编号
		String rybh = LUser.getRybh();
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "01");
		String s1 = Constant.CCLX;
		String s2 = Constant.JFLX;
		String s3 = Constant.SHZTDM;
		String guid = pd.getString("id");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.ccywsqService.expExcel(realfile, shortfileurl, guid,searchValue,rybh,s1,s2,s3,shzt);
	}
	
	/**
	 * 跳转到出差业务申请查看页面
	 * @return
	 */
	@RequestMapping("/goCcywsqViewPageByBzy")
	public ModelAndView goCcywsqViewPageByBzy() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		iniLogin(mv);
		iniSelect(mv);
		iniFile(mv);
		Map<String, Object> ccywsq = ccywsqService.getCcywsqMapById(pd.getString("guid"));
		String[] jtgj = (ccywsq.get("jtgj")+"").split(",");
		String jtgjcl = (ccywsq.get("jtgj")+"");
		mv.addObject("ccywsq",ccywsq);
		mv.addObject("jtgjcl", jtgjcl);
		mv.addObject("length",jtgj.length);
		mv.addObject("txryList",ccywsqService.getTxryListById(pd.getString("guid")));
		mv.addObject("xmxxlist",ccywsqService.getXmxxListById(pd.getString("guid")));
		mv.addObject("ProvicesList",provincesService.getProvicesList());
		mv.addObject("CitiesList",provincesService.getCitiesByProvince(ccywsq.get("provinceid")+""));
		mv.setViewName("wsbx/ccyw/ccywsq/ccywsq_viewBybzy");
		return mv;
	}
}
