package com.googosoft.controller.wsbx.jkgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.wsbx.CW_JKYWB;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.echo.EchoService;
import com.googosoft.service.wsbx.gwjdfbx.GwjdfbxsqService;
import com.googosoft.service.wsbx.jkgl.JksqService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.EchoUtil;
import com.googosoft.websocket.info.DshInfo;
import com.googosoft.websocket.info.DshInfoMap;

@Controller
@RequestMapping(value = "/jksq")
public class JksqController extends BaseController {

	@Resource(name = "pageService")
	private PageService pageService;// 分页单例

	@Resource(name = "jksqService")
	private JksqService jksqService;// 分页单例

	@Resource(name = "gwjdfbxsqService")
	GwjdfbxsqService gwjdfbxsqService;

	@Autowired
	private EchoService echoService;

	@Resource(name = "fileService")
	FileService fileService;

	/**
	 * 获取信息列表页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goJksqPage")
	public ModelAndView goRkglPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("shztdm", pd.get("shzt"));
		mv.setViewName("wsbx/jkgl/jksq_list");
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
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"), "01");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append("(select A.GID,A.DJBH,A.SHZT AS SHZTDM,A.PROCINSTID,");
		sql.append(" decode(nvl(A.JKZJE,0),0,'0.00',(to_char(round(A.JKZJE,2),'fm999999999999999999990.00')))JKZJE,");
		sql.append(" TO_CHAR(A.JKSJ,'YYYY-MM-DD')JKSJ,");
		sql.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
		sql.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.JKR)AS JKR,zffs,");
		sql.append(" (SELECT c.mc FROM GX_SYS_DMB C WHERE C.ZL = 'jklc' AND C.DM = A.SHZT) AS SHZT FROM CW_JKYWB A ");
		sql.append(" left join gx_sys_ryb ry on ry.guid=a.gid ");
		sql.append(" where (a.jkr='" + LUser.getGuid() + "') or (a.jbr='" + LUser.getGuid()
				+ "') order by a.djbh desc) B");
		pageList.setSqlText("*");
		// 表名
		pageList.setTableName(sql.toString());
		// 主键
		pageList.setKeyId("gid");
		// 设置WHERE条件
		StringBuffer strWhere = new StringBuffer();
		if ("01".equals(shzt)) {// 未提交
			strWhere.append(" AND SHZTDM in ('01', '04', '06', '09','16','19')");
		} else if ("02".equals(shzt)) {// 审核中
			strWhere.append(" AND SHZTDM in('04','06','05','02','03','09') ");
		} else if ("03".equals(shzt)) {// 已通过
			strWhere.append(" AND SHZTDM in('8','9')");
		} else {
			strWhere.append(" AND 1=1");
		}
		pageList.setStrWhere(strWhere.toString());
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "",
				pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	/**
	 * 跳转编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goEditPage")
	public ModelAndView goEditPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		if (operateType.equals("C")) {
			// 设置借款人
			String guid = UuidUtil.get32UUID();
			mv.addObject("guid", guid);
			mv.addObject("szbm", LUser.getDwmc());// 设置单位名称
			mv.addObject("jkzje", "0.00");
		}
		if (operateType.equals("U")) {
			PageData pd = this.getPageData();
			String guid = pd.getString("gid");
			String zffs = this.getPageData().getString("zffs");
			Map map = jksqService.getObjectById(this.getPageData().getString("gid"));
			// 对公支付
			List dgList = jksqService.getDgList(this.getPageData().getString("gid"));
			mv.addObject("dgList", dgList);
			// 对私支付
			List dsList = jksqService.getDsList(this.getPageData().getString("gid"));
			mv.addObject("dsList", dsList);
			List<Map<String, Object>> dlryhklist = gwjdfbxsqService.getdlryhklist();
			mv.addObject("dlryhklist", dlryhklist);
			mv.addObject("xmxxlist", jksqService.getXmxxListById(this.getPageData().getString("gid")));
			mv.addObject("jksq", map);
			mv.addObject("guid", guid);
			iniFile(mv, this.getPageData().getString("gid"));// 查询领导签字
		}
		String dqdlrguid = LUser.getGuid();
		mv.addObject("dqdlrguid", dqdlrguid);// 当前登录人guid
		String loginInfo = "(" + LUser.getRybh() + ")" + LUser.getRyxm();
		mv.addObject("loginInfo", loginInfo);
		mv.addObject("jkrmc", LUser.getXm());
		mv.addObject("jkr", LUser.getGuid());
		mv.addObject("operateType", operateType);
		mv.setViewName("wsbx/jkgl/jksq_edit");
		return mv;
	}

	/**
	 * 获得附件
	 * 
	 * @param mv
	 * @param guid
	 */
	public void iniFile(ModelAndView mv, String guid) {
		String[] fjxx = fileService.getFjList(guid, "", this.getRequest().getContextPath()).split("#", -1);// 照片附件列表
		mv.addObject("fjView", fjxx[0]);
		mv.addObject("fjConfig", fjxx[1]);
	}

	/**
	 * 对私支付选择项目负责人
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXmfzrPageList")
	@ResponseBody
	public Object getXmfzrPageList() throws Exception {
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		String ccywguid = pd.getString("ccywguid");
		String sqltext = "";
		StringBuffer tableName = new StringBuffer();
		// 设置查询字段名
		sqltext = " guid,ryxm,rybh,xb,XMMC ";
		tableName.append(
				" (select distinct(guid)as guid,ryxm,rybh,xb,XMMC from (select (select guid from gx_sys_ryb where rybh = s.fzr) guid,(select xm from gx_sys_ryb where rybh = s.fzr) ryxm,(select rybh from gx_sys_ryb where rybh = s.fzr) rybh,   ");
		tableName.append(" (select  decode(xb,1,'男',2,'女','未知') from gx_sys_ryb where rybh = s.fzr) xb,S.XMMC ");
		tableName.append("  from XMINFOS s left join CW_JKYWB_MXB x on x.jfbh = s.guid where s.guid in ('" + ccywguid
				+ "') ) b )b");
		pageList.setSqlText(sqltext);
		pageList.setKeyId(" b.guid ");// 主键
		pageList.setTableName(tableName.toString());// 表名
		pageList = pageService.findPageList(pd, pageList);// 引用传递
		// 设置WHERE条件
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "",
				pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();

	}

	/**
	 * 跳转编辑页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goLookPage")
	public ModelAndView goLookPage() {
		ModelAndView mv = this.getModelAndView();
		String gid = this.getPageData().getString("guid");
		String procinstid = this.getPageData().getString("procinstid");
		String operateType = this.getPageData().getString("operateType");
		String flag = this.getPageData().getString("flag");
		Map map = jksqService.getObjectById(this.getPageData().getString("guid"));
		// 对公支付
		List dgList = jksqService.getDgList(this.getPageData().getString("guid"));
		// 对私支付
		List dsList = jksqService.getDsList(this.getPageData().getString("guid"));
		List<Map<String, Object>> dlryhklist = gwjdfbxsqService.getdlryhklist();
		iniFile(mv, this.getPageData().getString("guid"));// 查询领导签字
		mv.addObject("dlryhklist", dlryhklist);
		mv.addObject("xmxxlist", jksqService.getXmxxListById(this.getPageData().getString("guid")));
		mv.addObject("dgList", dgList);
		mv.addObject("dsList", dsList);
		mv.addObject("jksq", map);
		mv.addObject("flag", flag);
		mv.addObject("operateType", operateType);
		mv.addObject("gid", gid);
		mv.addObject("procinstid", procinstid);
		mv.setViewName("wsbx/jkgl/jksq_look");
		return mv;
	}

	/**
	 * 保存
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doSaveMx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave1(CW_JKYWB jksq) {
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		String b = "";
		int i;
		if ("C".equals(operateType)) {// 新增
			String gid = this.getPageData().getString("guid");
			i = jksqService.doAddMx(pd, gid);
			if (i == 1) {
				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			} else {
				b = MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else if ("U".equals(operateType)) {// 修改
			String gid = this.getPageData().getString("guid");
			i = jksqService.doUpdate(pd, gid);
			if (i == 1) {
				return "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			b = MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
		return b;
	}

	/**
	 * 删除
	 * 
	 * @param
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		PageData pd = this.getPageData();
		String gid = pd.getString("gid");
		boolean p = jksqService.doDelete(gid);
		if (p) {
			return "{\"success\":\"true\",\"msg\":\"删除成功！\"}";
		} else {
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}

	}

	// 以下 为借款申请提交流程
	@RequestMapping(value = "/submit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object submit() {
		PageData pd = this.getPageData();
		String guid = pd.getString("gid");
		if (Validate.isNull(guid)) {
			return "{\"success\":false,\"msg\":\"提交失败，请稍后重试！\"}";
		}
		String guids[] = guid.split(",");
		int m = 0;
		DshInfoMap msgMap = new DshInfoMap();
		for (int i = 0; i < guids.length; i++) {
			String id = Validate.isNullToDefaultString(guids[i], "");
			if (Validate.noNull(id)) {
				String procinstid = jksqService.submitBySqr(id);
				if (Validate.noNull(procinstid)) {
					m++;
					// 从task表中查找流程审核人
					String shr = echoService.getShrGuid(procinstid);
					// 如果不是审核通过的单据（通过的会在task表被删除）
					if (Validate.noNull(shr)) {
						if (!msgMap.containsKey(shr)) {
							msgMap.put(shr, new ArrayList<DshInfo>());
						}
						DshInfo shxxMsg = echoService.getJkDshxxMsg(id);
						msgMap.get(shr).add(shxxMsg);
					}
				}
			}
		}
		if (m > 0) {
			EchoUtil.batchSendDshxxMsg(msgMap);
			return "{\"success\":true,\"msg\":\"提交成功！\"}";
		} else {
			return "{\"success\":false,\"msg\":\"提交失败，请稍后重试！\"}";
		}
	}

	/**
	 * 对私支付 银行list ajax
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	/*
	 * @RequestMapping(value="/dszfyhxx",produces = "text/xml;charset=UTF-8")
	 * 
	 * @ResponseBody public Object dszfyhxx(HttpServletRequest
	 * request,HttpServletResponse response){ PageData pd = this.getPageData();
	 * String dqdlrguid = pd.getString("dqdlrguid"); List list =
	 * jksqService.getdlrYh(dqdlrguid); Gson gson = new Gson(); String datas =
	 * gson.toJson(list); return datas; }
	 */

	// 导出Excel
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel() {
		PageData pd = this.getPageData();
		// 临时文件名
		String file = System.currentTimeMillis() + "";
		// 文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\") + "WEB-INF\\file\\excel\\" + file
				+ ".xls";
		// 下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		// 查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		String shzt = pd.getString("shzt");
		String guid = pd.getString("guid");
		StringBuffer sqltext = new StringBuffer();

		sqltext.append("select * from (select A.GID,A.DJBH,A.SHZT AS SHZTDM,A.PROCINSTID,");
		sqltext.append(
				" decode(nvl(A.JKZJE,0),0,'0.00',(to_char(round(A.JKZJE,2),'fm999999999999999999990.00')))JKZJE,");
		sqltext.append(" TO_CHAR(A.JKSJ,'YYYY-MM-DD')JKSJ,");
		sqltext.append(" (SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.DWBH=A.SZBM)AS SZBM,");
		sqltext.append(" (SELECT '('||ryb.rybh||')'||RYB.XM FROM GX_SYS_RYB RYB WHERE RYB.GUID=A.JKR)AS JKR,zffs,");
		sqltext.append(
				" (SELECT c.mc FROM GX_SYS_DMB C WHERE C.ZL = 'jklc' AND C.DM = A.SHZT) AS SHZT FROM CW_JKYWB A ");
		sqltext.append(" left join gx_sys_ryb ry on ry.guid=a.gid ");
		sqltext.append(
				" where (a.jkr='" + LUser.getGuid() + "') or (a.jbr='" + LUser.getGuid() + "') order by a.djbh desc)B");
		sqltext.append(" where 1=1 ");
		if ("01".equals(shzt)) {// 未提交
			sqltext.append(" AND SHZTDM in ('01', '04', '06', '09')");
		} else if ("02".equals(shzt)) {// 审核中
			sqltext.append(" AND SHZTDM in('04','06','05','02','03','09') ");
		} else if ("03".equals(shzt)) {// 已通过
			sqltext.append(" AND SHZTDM in('8')");
		} else {

		}
		if (Validate.noNull(guid)) {
			sqltext.append(" and gid in ('" + guid + "')");
		}
		sqltext.append(" order by djbh asc");
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		System.err.println("sql*****" + sqltext.toString());
		// 部门号 单位名称 单位简称 单位地址 单位性质 成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("SHZT");
		m.setShowname("审核状态");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("DJBH");
		m.setShowname("单据编号");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("JKR");
		m.setShowname("借款人");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("SZBM");
		m.setShowname("所在部门");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("JKSJ");
		m.setShowname("借款时间");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setName("JKZJE");
		m.setShowname("借款总金额（元）");
		mlist.add(m);
		m = null;

		// 导出方法
		pageService.ExpExcel(sqltext.toString(), realfile, filedisplay, mlist);
		return "{\"url\":\"excel\\\\" + file + ".xls\"}";
	}

}
