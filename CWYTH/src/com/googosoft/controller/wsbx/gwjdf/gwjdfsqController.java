package com.googosoft.controller.wsbx.gwjdf;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.wsbx.gwjdf.CW_GWJDFSQ;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.wsbx.gwjd.GwjdSqService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;

/**
 * 公务接待申请controller
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value = "/gwjdfsq")
public class gwjdfsqController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例

	@Resource(name = "dictService")
	private DictService dictService;
	@Resource(name="gwjdsqService")
	private GwjdSqService gwjdsqService;
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	@Resource(name="fileService")
	private FileService fileService;//单例
	@Autowired
	private EchoService echoService;
	
	/**
	 * 跳转公务接待申请列表页面
	 * @return
	 */
	@RequestMapping("/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("shztList",dictService.getDict(Constant.SHZTDMGW));
		mv.setViewName("wsbx/gwjdf/gwjdfsq_list");
		return mv;
	}
	
	/**
	 * 公务接待业务申请列表数据查询
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String tableName = " CW_GWJDYWSQSPB K ";
		PageList pageList = new PageList();
		sqltext.append("K.GUID,K.PROCINSTID,k.checkshzt,K.SHZT zt,(SELECT MC FROM GX_SYS_DMB\r\n" + 
						" WHERE ZL = '"+Constant.SHZTDMGW+"'\r\n" + 
						" AND DM = K.SHZT) SHZTMC,\r\n" + 
						" K.SHZT,\r\n" + 
						" (select '(' || dw.dwbh || ')' || dw.mc\r\n" + 
						" from gx_sys_dwb dw\r\n" + 
						" where dw.dwbh = k.szbm) as szbm,\r\n" + 
						" K.DJBH,\r\n" + 
						" nvl((select '(' || r.rygh || ')' || to_char(r.xm)\r\n" + 
						" from GX_SYS_RYB r\r\n" + 
						" where r.rybh = K.sqr),\r\n" + 
						" '') SQR,\r\n" + 
						" (select '(' || d.bmh || ')' || to_char(d.mc)\r\n" + 
						" from GX_SYS_DWB d\r\n" + 
						" where d.dwbh = K.jdbm) JDBM,\r\n" + 
						" K.JDRQ,\r\n" + 
						" K.LBDW,\r\n" + 
						" K.JDFD,\r\n" + 
						" K.SQRQ\r\n" );
		String strWhere = " and czr='"+LUser.getGuid()+"'";
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "01");
		if("00".equals(shzt)){
			strWhere += " AND k.checkshzt in ('00') ";
		}else if("11".equals(shzt)){
			strWhere += " and k.checkshzt in ('11') ";
		}else if("99".equals(shzt)){
			strWhere += " and k.checkshzt in ('99') ";
		}else{
			strWhere += " and 1=1 ";
		}
		
		pageList.setSqlText(sqltext.toString());
		//设置表主键名
		pageList.setKeyId("k.GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(strWhere); 
		pageList.setTableName(tableName);
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	public static void main(String[] args) {
	String djbh=GenAutoKey.createKey("cw_gwjdywsqspb", "guid","4");
	System.out.println(djbh);
	}
	/**
	 * 跳转公务接待业务申请编辑页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		String shztm = this.getPageData().getString("shztm");
		String dqzt = this.getPageData().getString("dqzt");
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		mv.addObject("loginId", loginId);
		if(operateType.equals("C"))
		{
			String loginMc = LUser.getXm();//当前登陆者的人员姓名
			String loginDw = LUser.getDwmc();
			String djbh =GenAutoKey.createKeyforth("cw_gwjdywsqspb","GW", "djbh");
			String guid = GenAutoKey.get32UUID();
			Map<String,String> map = new HashMap<String, String>();
			map.put("guid", guid);
			map.put("sqr",loginMc);
			map.put("szbm",loginDw);
			map.put("jdbm",loginDw);
			map.put("djbh", djbh);
			mv.addObject("gwjdfsq",map);
			
			mv.setViewName("wsbx/gwjdf/gwjdfsq_add");
		}
		else if(operateType.equals("U")||operateType.equals("L"))
		{
			Map map = gwjdsqService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("gwjdfsq",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
			
			List list = gwjdsqService.getMxList(this.getPageData().getString("guid"));
			mv.addObject("mxList",list);

			String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
			mv.addObject("dqzt",dqzt);
			mv.setViewName("wsbx/gwjdf/gwjdfsq_edit");
		}
		else if(operateType.equals("V")||operateType.equals("L"))
		{
			Map map = gwjdsqService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("gwjdfsq",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
			
			List list = gwjdsqService.getMxList(this.getPageData().getString("guid"));
			mv.addObject("mxList",list);
			
			String[] fjxx = fileService.getFjList(this.getPageData().getString("guid"),"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
			mv.addObject("dqzt",dqzt);
			
			mv.setViewName("wsbx/gwjdf/gwjdfsq_view");
		}
		
		mv.addObject("shztm", shztm);
		mv.addObject("operateType", operateType);
		return mv;
	}
	
	/**
	 * 审核页面跳转
	 * @return
	 */
	@RequestMapping(value="/check")
	public ModelAndView check(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String url = "wsbx/gwjdf/check1";
		if(!"first".equals(type)){
			url = "wsbx/gwjdf/check2";
		}
		mv.setViewName(url);
		return mv;
	}
	
	/**
	 * 打印
	 * @return
	 */
	@RequestMapping(value="/demoPrint")
	public ModelAndView demoPrint(){
		String guid = this.getPageData().getString("guid");
		ModelAndView mv = this.getModelAndView();
		Map map = gwjdsqService.getObjectById(guid);
		mv.addObject("gwjdfsq",map);
		
		List list = gwjdsqService.getMxList(guid);
		mv.addObject("mxList",list);
		
		List yjList1 = gwjdsqService.getYjList1(guid);
		List yjList2 = gwjdsqService.getYjList2(guid);
		mv.addObject("yjList1",Validate.isNull(yjList1)?null:yjList1.get(0));
		mv.addObject("yjList2",Validate.isNull(yjList2)?null:yjList2.get(0));
		mv.addObject("title","山东农业工程学院公务接待审批单");
		String url = "wsbx/gwjdf/gwjd_print";
		mv.setViewName(url);
		return mv;
	}
	
	
	/**
	 * 保存公务接待业务申请信息
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(CW_GWJDFSQ gwjdfsq)
	{
		String operateType = this.getPageData().getString("operateType");
		boolean result = false;
		String sqr = Validate.isNullToDefaultString(gwjdfsq.getSqr(), "");
		String szbm = Validate.isNullToDefaultString(gwjdfsq.getSzbm(), "");
		String jdbm = Validate.isNullToDefaultString(gwjdfsq.getJdbm(), "");
		String jzbxr = Validate.isNullToDefaultString(gwjdfsq.getJzbxr(), "");
		if(Validate.noNull(sqr)&&sqr.indexOf(")")>0){
			gwjdfsq.setSqr(sqr.substring(1,sqr.indexOf(")")));
		}
		if(Validate.noNull(szbm)&&szbm.indexOf(")")>0){
			gwjdfsq.setSzbm(szbm.substring(1,szbm.indexOf(")")));
		}
		if(Validate.noNull(jdbm)&&jdbm.indexOf(")")>0){
			gwjdfsq.setJdbm(jdbm.substring(1,jdbm.indexOf(")")));
		}
		if(Validate.noNull(jzbxr)&&jzbxr.indexOf(")")>0){
			gwjdfsq.setJzbxr(jzbxr.substring(1,jzbxr.indexOf(")")));
		}
		if("C".equals(operateType))
		{		
			gwjdfsq.setShzt("01");//默认未提交
			SimpleDateFormat tempDate = new SimpleDateFormat("yyyy-MM-dd");    
			String datetime = tempDate.format(new java.util.Date()); 
			gwjdfsq.setSqrq(datetime);
			result = gwjdsqService.doAdd(gwjdfsq);
			if(result)
			{
				return "{success:'true',msg:'信息保存成功！',guid:'"+gwjdfsq.getGuid()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))
		{
			result = gwjdsqService.doUpdate(gwjdfsq);
			if(result)
			{
				return "{success:'true',msg:'信息保存成功！',guid:'"+gwjdfsq.getGuid()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 查看该谁审核
	 * @return
	 */
	@RequestMapping("/checkWhoSh")
	@ResponseBody
	public Map checkWhoSh() {
		return gwjdsqService.checkWhoSh(this.getPageData());
	}

	/**
	 * 删除吃拆事前审批信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		if (gwjdsqService.doDelete(guid)) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}

	}
	
	/**
	 * 信息提交
	 * @return
	 */
	@RequestMapping(value="/submit",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object xxTj(){
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
				String procinstid = gwjdsqService.submitBySqr(id);
				if(Validate.noNull(procinstid)) {
					m++;
					String shr = echoService.getShrGuid(procinstid);
					//如果不是审核通过的单据（通过的会在task表被删除）
					if(Validate.noNull(shr)) {
						if(!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getGwjdsqspDshxxMsg(id);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
		}
		if(m>0){
			EchoUtil.batchSendDshxxMsg(msgMap);
			return "{\"success\":\"true\",\"msg\":\"提交成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"提交失败，请稍后重试！\"}";
		}
	}
	/**
	 * 导出公务接待信息Excel
	 * 
	 * @return
	 */
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel() {
		PageData pd = this.getPageData();
		// 临时文件名
		String file = System.currentTimeMillis() + "";
		// 文件绝对路径
		String realfile = this.getRequest().getServletContext()
				.getRealPath("\\")
				+ "WEB-INF\\file\\excel\\" + file + ".xls";
		// 下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		// 查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		
		sqltext.append("SELECT k.GUID,(SELECT MC FROM GX_SYS_DMB WHERE ZL='11033' AND DM=K.SHZT) SHZTMC,K.SHZT, "
				+ "K.DJBH,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ");
		sqltext.append(" FROM CW_GWJDYWSQSPB K WHERE 1=1 AND SQR = '"+LUser.getRybh()+"'");
		String shztm=pd.getString("shztm");
		if (("all").equals(shztm)) {// 审核状态筛选
			
		} else if(Validate.noNull(shztm)) {
			sqltext.append("and checkshzt= '"+shztm+"'");
		}
		else {// 单位权限
		sqltext.append("and checkshzt='01'");
		}
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if (Validate.noNull(id)) {
			sqltext.append(" AND K.guid IN ('" + id.replace(",", "','") + "') ");
		}
		sqltext.append(" ORDER BY K.guid ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("shztmc");
		m1.setShowname("审核状态");
		mlist.add(m1);

		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("djbh");
		m2.setShowname("单据编号");
		mlist.add(m2);

		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("sqr");
		m3.setShowname("申请人");
		mlist.add(m3);

		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("jdbm");
		m4.setShowname("接待部门");
		mlist.add(m4);
		
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("jdrq");
		m5.setShowname("接待日期");
		mlist.add(m5);

		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("lbdw");
		m6.setShowname("来宾单位");
		mlist.add(m6);
		
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("jdfd");
		m7.setShowname("接待饭店");
		mlist.add(m7);


		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("sqrq");
		m8.setShowname("申请日期");
		mlist.add(m8);

		// 导出方法
		pageService.ExpExcel(sqltext.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}
	
	/**
	 * 导出教师人员信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		// 设置查询字段名
		
		sqltext.append("SELECT k.GUID,(SELECT MC FROM GX_SYS_DMB WHERE ZL = 'shztdmgw' AND DM = K.SHZT) SHZTMC,K.SHZT, "
				+ "K.DJBH,nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=K.sqr),'')SQR,"
				+ "(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.dwbh=K.jdbm) JDBM,K.JDRQ,K.LBDW,K.JDFD,K.SQRQ");
		sqltext.append(" FROM CW_GWJDYWSQSPB K WHERE 1=1 AND SQR = '"+LUser.getRybh()+"'");
		String shztm=pd.getString("shztm");
		if (("all").equals(shztm)) {// 审核状态筛选
			
		} else if(Validate.noNull(shztm)) {
			sqltext.append("and checkshzt= '"+shztm+"'");
		}
		else {// 单位权限
		sqltext.append("and checkshzt='01'");
		}
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if (Validate.noNull(id)) {
			logger.info("+++++++++++++++++++++++++"+id.replace(",", "','"));
			sqltext.append(" and k.guid in ('"+id.trim()+"')");//('"+guid.trim()+"')
		}
		sqltext.append(" ORDER BY K.guid ");
		String guid = pd.getString("guid");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.gwjdsqService.expExcel(realfile, shortfileurl, guid,searchValue,rybh,sqltext.toString());
	}
}
