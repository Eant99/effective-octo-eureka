package com.googosoft.controller.fzgn.jcsz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.RybService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.systemset.cssz.GrszService;
import com.googosoft.service.systemset.qxgl.CzqxbService;
import com.googosoft.service.systemset.qxgl.GlqxbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 人员信息
 * @author master
 */
@Controller
@RequestMapping(value="/ryb")
public class RybController extends BaseController{
	
	@Resource(name="ryxxService")
	private RybService ryxxService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="glqxbService")
	private  GlqxbService glqxbService;//单例
	
	@Resource(name="czqxbService")
	private  CzqxbService czqxbService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="grszservice")
	private GrszService grszservice;
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;

	/**
	 * 跳转人员列表页面(用户信息维护)
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goRybListPage")
	public ModelAndView goRybListPage(){
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> whcd = dictService.getDict(Constant.WHCD);//文化程度
		List<Map<String, Object>> zyzc = dictService.getDict(Constant.ZYZC);//专业职称
		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);//性别
		mv.addObject("whcdList",whcd);
		mv.addObject("zyzcList",zyzc);
		mv.addObject("sexList",sex);
		mv.setViewName("fzgn/jcsz/ryb_list");
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		mv.addObject("dwbh", dwbh);
		mv.addObject("rybh", rybh);
		return mv;
	}
	
	/**
	 * 获取人员列表数据
	 * @throws Exception 
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" A.GUID,A.rybh as rybh,A.DWBH AS DW,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) as dwbh,");
		sqltext.append(" A.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=A.xb) as xb,to_char(A.csrq,'yyyy-MM-dd') as csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) as whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) as sxzy,");
		sqltext.append(" to_char(A.byrq,'yyyy-MM-dd') as byrq,to_char(A.gzrq,'yyyy-MM-dd') as gzrq,A.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) as zyzc,");
		//sqltext.append(" select q.jsmc from zc_sys_jsb q where q.jsbh=(select w.jsbh from ZC_SYS_JSRYB  w where w.rybh=a.rybh) as jsmc,");	
		
		
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) as zygz,");
		sqltext.append(" to_char(A.drrq,'yyyy-MM-dd') as drrq,to_char(A.txrq,'yyyy-MM-dd') as txrq,A.bz,");
		sqltext.append(" case A.ryzt when '1' then '正常' when '0' then '禁用' end ryzt,A.zzbz,A.pxxh,A.sfzh,");
		
		sqltext.append(" A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm ");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("GX_SYS_RYB A");
		//主键
		pageList.setKeyId("rybh");
		//设置WHERE条件
		String strWhere=" and A.rybh <> '"+SystemSet.AdminRybh()+"' ";
		
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		if(Validate.isNull(dwbh)){
			dwbh=pd.getString("treedwbh");
		}
		String lrybh = LUser.getRybh();//获取当前登录人员编号
	    strWhere+=pageService.getDwqxWhereSql(lrybh, "A.dwbh", dwbh, true);//单位权限
		pageList.setStrWhere(strWhere);
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	
	/**
	 * 获取人员列表数据
	 * @throws Exception 
	 */
	@RequestMapping(value="/getPageList1")
	@ResponseBody
	public Object getPageList1() throws Exception{
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" A.GUID,A.rybh as rybh,A.DWBH AS DW,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) as dwbh,");
		sqltext.append(" A.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=A.xb) as xb,to_char(A.csrq,'yyyy-MM-dd') as csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) as whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) as sxzy,");
		sqltext.append(" to_char(A.byrq,'yyyy-MM-dd') as byrq,to_char(A.gzrq,'yyyy-MM-dd') as gzrq,A.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) as zyzc,");
		//sqltext.append(" select q.jsmc from zc_sys_jsb q where q.jsbh=(select w.jsbh from ZC_SYS_JSRYB  w where w.rybh=a.rybh) as jsmc,");	
		
		
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) as zygz,");
		sqltext.append(" to_char(A.drrq,'yyyy-MM-dd') as drrq,to_char(A.txrq,'yyyy-MM-dd') as txrq,A.bz,");
		sqltext.append(" case A.ryzt when '1' then '正常' when '0' then '禁用' end ryzt,A.zzbz,A.pxxh,A.sfzh,");
		
		sqltext.append(" A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm ");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("GX_SYS_RYB A");
		//主键
		pageList.setKeyId("rybh");
		//设置WHERE条件
		String strWhere=" and A.rybh <> '"+SystemSet.AdminRybh()+"' ";
		
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		if(Validate.isNull(dwbh)){
			dwbh=pd.getString("treedwbh");
		}
		
		String lrybh = LUser.getRybh();//获取当前登录人员编号
	    strWhere+=pageService.getDwqxWhereSql(lrybh, "A.dwbh", dwbh, true);//单位权限
		pageList.setStrWhere(strWhere);
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
		pageList = pageService.findPageList1(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	
	/**
	 * 保存
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_RYB ryb)
	{
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String rybhs = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybhs);
		ryb.setCzr(loginId);
		if("C".equals(operateType))
		{
			//判断部门号是否重复
			boolean checkbmh=ryxxService.checkRyb(ryb.getRygh());
			if(checkbmh)
			{
				return MessageBox.show(false, "人员工号不能重复，请重新输入！");
			}
			String rybh =GenAutoKey.createRybh("gx_sys_ryb", "rybh", "6");//生成人员编号
			ryb.setRybh(rybh);
			String guid =this.get32UUID();//生成主键id
			ryb.setGuid(guid);
			result = ryxxService.doAdd(ryb);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！',rybh:'"+ryb.getRybh()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))
		{
			if(!ryb.getRygh().equals(ryxxService.getObjectById(ryb.getRybh()).get("rygh").toString()))
			{
				boolean checkrygh=ryxxService.checkRyb(ryb.getRygh());
				if(checkrygh)
				{
					return MessageBox.show(false, "人员工号不能重复，请重新输入！");
				}
			}
			result = ryxxService.doUpdate(ryb);
			if(result>0)
			{
				return "{success:'true',msg:'信息保存成功！',rybh:'"+ryb.getRybh()+"'}";
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
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		String rybh = this.getPageData().getString("rybh");
		//验证该人名下是否有资产
		String hasZc = ryxxService.validateForZJB(rybh);
		String[] flag = hasZc.split(":");
		if("C".equals(flag[0])&&1==2){
        	return "{success:false,msg:'您选择的编号为："+flag[1]+" 的人员名下存在资产，不能删除！'}";
        }else if("U".equals(flag[0])&&1==2){//如果名下有已处置的资产，则直接禁用
        	int i = ryxxService.doJyRy(flag[1]);
    		if(i>0){
    			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
    		}else{
    			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    		}
        }else{
        	int i = ryxxService.doDel(rybh);
    		if(i>0){
    			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
    		}else{
    			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    		}
        }
	}
	/**
	 * 获取单个人员详细信息（增加、修改）
	 * @return
	 */
	@RequestMapping(value="/goRybPage")
	public ModelAndView goRybPage(){
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> whcd = dictService.getDict(Constant.WHCD);//文化程度
		List<Map<String, Object>> zyzc = dictService.getDict(Constant.ZYZC);//专业职称
		List<Map<String, Object>> zygz = dictService.getDict(Constant.ZYGZ);//主要工作
		List<Map<String, Object>> sxzy = dictService.getDict(Constant.SXZY);//所学专业
		List<Map<String, Object>> sex = dictService.getDict(Constant.SEX);//性别
		List<Map<String, Object>> zzzt = dictService.getDict(Constant.ZZZT);//在职状态
		mv.addObject("whcdList",whcd);
		mv.addObject("zyzcList",zyzc);
		mv.addObject("zygzList", zygz);
		mv.addObject("sxzyList", sxzy);
		mv.addObject("sexList", sex);
		mv.addObject("zzztList", zzzt);
		String operateType = this.getPageData().getString("operateType");
		if(operateType.equals("C"))
		{
			String rybh =GenAutoKey.createRybh("gx_sys_ryb", "rybh", "6");
			Map<String,String> map = new HashMap<String, String>();
			map.put("rybh", rybh);
			map.put("rygh", rybh);
			map.put("dwbh", CommonUtil.bmhTomc(this.getPageData().getString("dwbh")));
			mv.addObject("ryb",map);
		}
		else if(operateType.equals("U")||operateType.equals("L"))
		{
			Map map = ryxxService.getObjectById(this.getPageData().getString("rybh"));
			mv.addObject("ryb",map);
			mv.addObject("rybh",this.getPageData().getString("rybh"));
		}
		mv.setViewName("fzgn/jcsz/ryb_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	
	
    /**
	 * 获取管理权限设置右侧页面
	 * @return
     * @throws Exception 
	 */
    @RequestMapping(value="/goGlqxPage")
	public ModelAndView goGlqxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		mv.addObject("rybh",rybh);
		mv.addObject("ryxx", CommonUtil.getXm(rybh));
		List glqxTree = glqxbService.findListByRybh(rybh);
		List zclbTree = glqxbService.findZclbTree(rybh);
		mv.addObject("glqxTree", glqxTree);
		mv.addObject("zclbTree", zclbTree);
		mv.setViewName("fzgn/jcsz/glqxb_list");
		return mv;
	}
	/**
	 * 获取操作权限设置列表页面
	 * @return
	 */
	@RequestMapping(value="/goCzqxPage")
	public ModelAndView goCzqxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		/*获取菜单*/
		List<Map> menu_list = czqxbService.getCzqxMenuList(rybh,LUser.getRybh());
		mv.addObject("menu_list", menu_list);
		/*根据人员编号获取人员工号和姓名*/
		Map<String, Object> rymap = ryxxService.getObjectById(rybh);
		mv.addObject("rymap", rymap);
		mv.setViewName("fzgn/jcsz/czqxb_list");
		return mv;
	}
	/**
	 * 批量赋值页面操作
	 * @return
	 */
	@RequestMapping(value="/goPlfzPage")
	@ResponseBody
	public ModelAndView goPlfzPage(){
		String rybh = this.getPageData().getString("rybh");
		ModelAndView mv = this.getModelAndView();
		List whcd = dictService.getDict(Constant.WHCD);//文化程度
		List zyzc = dictService.getDict(Constant.ZYZC);//专业职称
		mv.addObject("whcdList",whcd);
		mv.addObject("zyzcList",zyzc);
		mv.addObject("rybh",rybh);
		mv.setViewName("fzgn/jcsz/ryb_plfz");
		return mv;
	}
	/**
	 * 批量赋值数据操作
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/doPlfz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doPlfz() throws ParseException{
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		String ziduan = pd.getString("ziduan");
		Object zdValue = pd.getString("zdValue");
		String selXx = pd.getString("selxx");
		if("drrq".equals(selXx)){
			zdValue = new SimpleDateFormat("yyyy-MM-dd").parse(zdValue+"");
		}
		if(ziduan.equals("dwbh")){
			zdValue = CommonUtil.getDwXx(zdValue.toString());
		}
		int i =ryxxService.doPlfz(ids, ziduan, zdValue);
		if(i>0){
			return "{\"success\":\"true\",\"msg\":\"批量赋值成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"批量赋值失败！\"}";
		}
	}	
	/**
	 * 导出人员信息Excel
	 * @return
	 */
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select A.rybh as rybh,A.DWBH AS DW,(select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.dwbh) as dwbh,");
		sqltext.append(" A.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=A.xb) as xb,to_char(A.csrq,'yyyy-MM-dd') as csrq,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.WHCD+"' and dm=a.whcd) as whcd,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.SXZY+"' and dm=a.sxzy) as sxzy,");
		sqltext.append(" to_char(A.byrq,'yyyy-MM-dd') as byrq,to_char(A.gzrq,'yyyy-MM-dd') as gzrq,A.sysgl,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYZC+"' and dm=a.zyzc) as zyzc,");
		sqltext.append(" (select mc from gx_sys_dmb where zl='"+Constant.ZYGZ+"' and dm=a.zygz) as zygz,");
		sqltext.append(" to_char(A.drrq,'yyyy-MM-dd') as drrq,to_char(A.txrq,'yyyy-MM-dd') as txrq,A.bz,");
		sqltext.append(" case A.ryzt when '1' then '正常' when '0' then '禁用' end ryzt,A.zzbz,A.pxxh,A.sfzh,");
		sqltext.append(" A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm ");
		sqltext.append(" FROM GX_SYS_RYB A  where 1=1 and A.rybh <> '"+SystemSet.AdminRybh()+"'");
		String dwbh = pd.getString("treedwbh");
		if(Validate.noNull(dwbh)){//左侧单位树筛选
			sqltext.append(" and A.dwbh in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh + "' )");
		}else {//单位权限
			sqltext.append(pageService.getQxsql(LUser.getRybh(), "A.dwbh", "D"));
		}
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" AND A.RYBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY A.RYGH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("rygh");
		m.setShowname("工号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xm");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("xb");
		m.setShowname("性别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("whcd");
		m.setShowname("文化程度");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("drrq");
		m.setShowname("调入日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("zyzc");
		m.setShowname("专业职称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("dwbh");
		m.setShowname("所在单位");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ryzt");
		m.setShowname("状态");
		mlist.add(m);
		m = null;

		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 通用人员弹窗查看页面
	 */
	@RequestMapping(value="/goRybLook")
	public ModelAndView goRybLook()
	{
		ModelAndView mv = this.getModelAndView();
		String rybh = this.getPageData().getString("rybh");
		Map map = grszservice.getGrxx(rybh);
		mv.addObject("map",map);
		mv.addObject("rybh",rybh); 
		mv.setViewName("fzgn/jcsz/ryb_win");
		return mv;
	}
	/**
	 * 通用人员弹窗查看页面
	 */
	@RequestMapping(value="/goTyRybWin")
	public ModelAndView goTyRybWin()
	{
		ModelAndView mv = this.getModelAndView();
		String rybh = this.getPageData().getString("rybh");
		Map map = grszservice.getGrxx(rybh);
		mv.addObject("map",map);
		mv.addObject("rybh",rybh); 
		mv.setViewName("fzgn/jcsz/ryb_tywin");
		return mv;
	}
	/**
	 * 通过(rygh)xm获取该人的所在单位(bmh)mc
	 * @param ryghmc
	 * @return
	 */
	@RequestMapping(value="/getDwxxByRyxx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String getDwxxByRyxx(String ryghmc){
		return ryxxService.getDwxxByRyxx(ryghmc);
	}
	/**
	 * 通过人员姓名查询人员编号
	 * @return
	 */
	@RequestMapping(value="/getRybh")
	@ResponseBody
	public Object getRybh(){
		String ryxm = Validate.isNullToDefault(this.getRequest().getParameter("RYMC"), "")+"";
		String rybh = ryxxService.findRybhByRyxm(ryxm);
		return rybh;
	}
	
	/**
	 * 通过rybh查询角色信息
	 * @return
	 */
	@RequestMapping(value="/getJsxx")
	@ResponseBody
	public Object getJsxx(){
		return ryxxService.getJsxx(this.getPageData().getString("rybh"));
	}
	/**
	 * 通用人员弹窗查看页面
	 */
	@RequestMapping(value="/csmm",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String cShmm(HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer msg = new StringBuffer();//存放错误信息
		String rybh = this.getPageData().getString("rybh");
		int result = 0;
		result = ryxxService.doCshmm(rybh);
		if(result>0){
			msg.append("密码初始化成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("密码初始化失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	
	/**
	 * 启用禁用操作按钮
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value="/qyjy",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public String qyjy(HttpServletRequest request,HttpServletResponse response)
	{
		StringBuffer msg = new StringBuffer();//存放错误信息
		String rybh = this.getPageData().getString("rybh");
		String type = this.getPageData().getString("type");
		int result = 0;
		result = ryxxService.qyjy(rybh,type);
		if(result>0){
			msg.append("操作成功");
			return "{\"success\":true,\"msg\":\"" + msg.toString() + "\"}";
		}else{
			msg.append("操作失败");
			return "{\"success\":false,\"msg\":\"" + msg.toString() + "\"}";
		}
	}
	
	/**
	 * 密码修改功能
	 * @param rybh
	 * @param passwordn
	 * @return
	 */
	@RequestMapping(value="/updpwd",produces = "text/xml;charset=UTF-8")
	@ResponseBody
	public Object  updpwd(String rybh,String passwordn)
	{
		ModelAndView mv = this.getModelAndView();
		StringBuffer msg = new StringBuffer();//存放错误信息
		int result = 0;
		result = ryxxService.doUpdPwd(passwordn, rybh);
		Gson gson = new Gson();
		if(result>0){
			return gson.toJson(true);
		}else{
			return gson.toJson(false);
		} 
	}
	
	/**
	 * 通用人员弹窗查看页面
	 */
	@RequestMapping(value="/goSzxxPage")
	public ModelAndView goSzxxPage()
	{
		ModelAndView mv = this.getModelAndView();
		String rybh = this.getPageData().getString("rybh");
		mv.addObject("rybh",rybh); 
		mv.setViewName("fzgn/jcsz/szxx");
		return mv;
	}
}
