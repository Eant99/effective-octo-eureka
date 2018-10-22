package com.googosoft.controller.xmgl.xmsp;

import java.util.ArrayList;
import java.util.Arrays;
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
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.constant.shzt.XmShzt;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xmgl.xmsp.XmspService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 
 * @author fugangjie
 *
 * 2018年1月26日-下午5:15:36
 */
@Controller
@RequestMapping(value = "/xmgl/xmsp")
public class XmspController extends BaseController {
	@Autowired
	private PageService pageService;
	@Autowired
	private XmspService xmspService;
	@Autowired
	private FileService fileService;
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	/**
	 * jsp页面基准路径
	 */
	private String baseUrl = "xmgl/xmsp";
	/**
	 * （项目审批里的三个模块放在了同一个controller里，所以用项目审批类型区分）
	 * 项目审批类型：项目初审
	 */
	public static final String SPLX_XMCS = "cs";
	/**
	 * 项目审批类型：项目初复审
	 */
	public static final String SPLX_XMFS = "fs";
	/**
	 * 项目审批类型：项目排名调整
	 */
	public static final String SPLX_XMPMTZ = "tz";
	
	/**
	 * 初始化文件配置信息
	 * @param mv
	 */
	public void iniFile(ModelAndView mv,BaseController controller) {
		//照片附件列表
		String[] fjxx = fileService.getFjList(controller.getPageData().getString("guid"),"",controller.getRequest().getContextPath()).split("#",-1);
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}
	
	/**
	 * 跳转到项目审批列表页面
	 * @return
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goXmcsListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String splx = pd.getString("splx");
		String url = "";
		if(SPLX_XMCS.equals(splx)) {
			url = baseUrl + "/xmcs_list";
		}else if(SPLX_XMFS.equals(splx)) {
			url = baseUrl + "/xmfs_list";
		}else {
			url = baseUrl + "/xmpmtz_list";
		}
		mv.setViewName(url);
		return mv;
	}
	/**
	 * 跳转到查看页面
	 * @return
	 */
	@RequestMapping(value = "/goViewPage")
	public ModelAndView goViewPage() {
		ModelAndView mv = this.getModelAndView();
		mv.addObject("jldwList",dictService.getDict(Constant.JLDW));
		mv.setViewName(this.baseUrl+"/xmsp_view");
		return mv;
	}
	
	/**
	 * 审核
	 * @return
	 */
	@RequestMapping(value="doCheck",produces="text/html;charset=utf-8")
	@ResponseBody
	public Object doCheck(HttpSession session) {
		PageData pd = this.getPageData();
		//获取项目审批类型
		String splx = pd.getString("splx");
		//获取审核结果
		String checkResult = pd.getString("checkResult");
		String guid = pd.getString("guid");
		String shyj = pd.getString("shyj");
		String csxmpm = pd.getString("csxmpm");
		String fsxmpm = pd.getString("fsxmpm");
		String sszt=Constant.getztid(session);
		String msg = "";
		//如果是项目初审
		if(SPLX_XMCS.equals(splx)) {
			if(Constant.PASS.equals(checkResult)) {
				xmspService.doXmcsPass(guid, shyj, csxmpm);
				msg = "审核通过成功！";
			}else if(Constant.REJECT.equals(checkResult)) {
				xmspService.doXmcsReject(guid, shyj);
				msg = "审核退回成功！";
			}
			//如果是项目复审
		}else if(SPLX_XMFS.equals(splx)){
			if(Constant.PASS.equals(checkResult)) {
				xmspService.doXmfsPass(guid, shyj, fsxmpm,sszt);
				msg = "审核通过成功！";
			}else if(Constant.REJECT.equals(checkResult)) {
				xmspService.doXmfsReject(guid, shyj);
				msg = "审核退回成功！";
			}
		}
		return MessageBox.toJson(true, msg);
	}
	/**
	 * 导出excel
	 * @return
	 */
	@RequestMapping(value="/doExportExcel")
	@ResponseBody
	public Object exporCstExcel(HttpSession session){
		PageData pd = this.getPageData();
		//获取参数guid
		String guid_p = pd.getString("guid");
		String splx = pd.getString("splx");
		//从session中获取项目审批列表
		@SuppressWarnings("unchecked")
		List<Map<String, Object>> list = (List<Map<String,Object>>)session.getAttribute("xmspList");
		//如果guid不为空，则导出选中的信息
		if(Validate.noNull(guid_p)) {
			//选中的信息列表
			List<Map<String, Object>> checkedList = new ArrayList<>();
			//将参数转换为数组
			String[] guidArr = guid_p.split("','");
			for (String str : guidArr) {
				for (Map<String, Object> map : list) {
					String guid = map.get("GUID")+"";
					if(guid.equals(str)) {
						checkedList.add(map);
					}
				}
			}
			//list引用选中信息列表
			list = checkedList;
		}
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		if(SPLX_XMCS.equals(splx)) {
			return xmspService.exportCsExcel(realfile, shortfileurl,"项目初审信息",list);
		}else if(SPLX_XMFS.equals(splx)){
			return xmspService.exportFsExcel(realfile, shortfileurl,"项目复审信息",list);
		}else {
			return null;
		}
	}
	/**
	 * 保存调整后排名
	 * @return
	 */
	@RequestMapping(value="/doTzhpmSave",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object doTzhpmSave(){
		xmspService.doTzhpmSave(this.getPageData().getString("data"));
		return MessageBox.toJson(true, "保存成功！");
	}
	
	/**
	 * 获取列表页面数据
	 * @return	json
	 */
	@RequestMapping(value = "/getListPageData")
	@ResponseBody
	public Object getPageList(HttpSession session){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		StringBuilder strWhere = new StringBuilder();
		String splx = pd.getString("splx");
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select b.guid,(select mc from gx_sys_dmb where zl = '"+XmShzt.SHZTZL+"' and dm = shzt) as shztmc,b.shzt,b.xmbh,b.xmmc,to_char(b.jhzxsj,'yyyy-mm-dd') as jhzxsj,to_char(b.jhjssj,'yyyy-mm-dd') as jhjssj,xmpm,xmpm as tzhpm,"
				+ "(select xmlxmc from cw_xmlxb where guid = xmlx) as xmlx,(select zymc from cw_zyxxb where zybh = fwzy ) as fwzy,fwxk,"
				+ "(case when sfsndcxlzxm = '1' then '是' else '否' end) as sfsndcxlzxm,to_char(ysje,'FM999999999.00') as ysje,"
				+ "(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = sbdw) as sbdw,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = sbr) as sbr,"
				+ "to_char(sbrq,'yyyy-MM-dd') as sbrq,(select '('||rybh||')'||xm from gx_sys_ryb where guid = csr) as csr,"
				+ "to_char(csrq,'yyyy-MM-dd') as csrq from cw_xmsbxxb b) A ");
		pageList.setSqlText(sqltext.toString());
		//如果是项目初审，只查询初审中和复审退回的数据
		if(SPLX_XMCS.equals(splx)) {
			strWhere.append(" and (a.shzt = '"+XmShzt.CSZ+"' or a.shzt = '"+XmShzt.FSTH+"')");
			//如果是项目复审，只查询复审中的数据
		}else if(SPLX_XMFS.equals(splx)) {
			strWhere.append(" and a.shzt = '"+XmShzt.FSZ+"' ");
			//如果是调整后排名，只查询审核通过的数据
		}else {
			strWhere.append(" and a.shzt = '"+XmShzt.SHTG+"' ");
		}
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		pageList.setStrWhere(strWhere.toString());
		pageList.setHj1("");
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		//将列表数据放到session中
		session.setAttribute("xmspList", pageList.getContentList());
		return pageInfo.toJson();
	}
	
	/**
	 * 获取详情页面数据
	 * @return	json
	 */
	@RequestMapping(value = "/getDetailPageData")
	@ResponseBody
	public Object getDetailPageData(){
		PageData pd = this.getPageData();
		return xmspService.getXmspInfo(pd.getString("guid"));
	}
	
	
}
