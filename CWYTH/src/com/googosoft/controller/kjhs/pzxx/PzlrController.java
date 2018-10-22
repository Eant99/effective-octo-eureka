package com.googosoft.controller.kjhs.pzxx;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzcxService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.service.systemset.qxgl.JsxxService;
import com.googosoft.service.zdscpz.ZdscpzService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;
/**凭证录入控制类
 * 
 * @author fugangjie
 *
 * 2018年1月26日-上午9:47:58
 */
@Controller
@RequestMapping("kjhs/pzxx/pzlr")
public class PzlrController extends BaseController{
	@Resource(name="pzlrService")
	PzlrService pzlrService;
	@Resource(name="dictService")
	DictService dictService;
	@Resource(name="pageService")
	PageService pageService;
	@Autowired
	ZdscpzService zdscpzService;
	@Resource(name="fileService")
	private FileService fileService;//单例
	@Resource(name = "pzcxService")
	private PzcxService pzcxService;
	@Resource(name = "jsxxService")
	private JsxxService jsxxService;
	/**
	 * 初始化当前登录人信息
	 * @param mv
	 */
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId",LUser.getGuid());
		mv.addObject("dwmc",LUser.getDwmc());
		mv.addObject("rybh",LUser.getRybh());
		mv.addObject("ryxm",LUser.getRyxm());
	}
	/**
	 * 初始化数据字典下拉框
	 * @param mv
	 */
	public void iniSelect(ModelAndView mv) {
		mv.addObject("jsfsList",dictService.getDict(Constant.ZFFSDM));
		mv.addObject("yslxList",dictService.getDict(Constant.YSLX));
		mv.addObject("zclxList",dictService.getDict(Constant.ZCLX));
		mv.addObject("yslyList",dictService.getDict(Constant.YSLY));
	}
	/**
	 * 加载页面详细信息数据
	 * @param mv
	 * @param pd
	 */
	public void iniView(ModelAndView mv,String guid) {
		Map<String, Object> zbMap = pzlrService.getPzlrMain(guid);
		List<Map<String, Object>> mxList = pzlrService.getPzlrDetail(guid);
		mv.addObject("zbMap",zbMap);
		mv.addObject("mxList",mxList);
	}
	public void iniPrint(ModelAndView mv,String guid,String kjzd) {
		String[] arrays = guid.split(",");
		Map<String, Object> zbMap ;
		List<Map<String, Object>> mxList;
		List finalList = new ArrayList();
		for(int i=0;i<arrays.length;i++){
			Map finalMap = new HashMap();
			zbMap = pzlrService.getPzlrMain(arrays[i]);
			mxList = pzlrService.getPzlrMx(arrays[i], kjzd);
			finalMap.put("zbMap", zbMap);
			finalMap.put("mxList", mxList);
			finalList.add(finalMap);
		}
		mv.addObject("finalList",finalList);
		//添加装订边距
		mv.addObject("dyzdbj", CommonUtil.getDyzdbj());
		//页面宽度
		mv.addObject("ymkd",297-19-CommonUtil.getDyzdbj());
	}
	/**
	 * 初始化凭证信息
	 * @param mv
	 * @param pd
	 * @param session
	 * @return
	 */
	public String iniPzInfo(ModelAndView mv,PageData pd,HttpSession session) {
		String copy = pd.getString("copy"); //复制凭证  copy= yes
		String draw = pd.getString("draw");
		//分页类型
		String type = pd.getString("type");
		String pznd = "";
		String kjqj = "";
		//所属账套
		String sszt = Constant.getztid(session);
		//凭证编号，先从参数中找，再从session中找，如果都没找到，说明是首次加载
		String pzbh = pd.getString("pzbh");
		String pzlx=Validate.isNullToDefaultString(pd.getString("pzlx"), pzlrService.getPzlx());
		
		if(Validate.isNull(pzbh)) {
			pzbh = session.getAttribute("pzbh")+"";
		}
		//如果编号为空，是首次加载页面，则把未记账的日期放到session中
		//获取期末结账表中未结账的日期
		Map<String, Object> map = pzlrService.getWjzDate(sszt,pzlx);
		pznd = ""+map.get("ztnd");
		kjqj = ""+map.get("kjqj");
		session.setAttribute("pznd", pznd);
		session.setAttribute("kjqj", kjqj);
		
		if(Validate.noNull(draw)) {
			//默认是最后一页
			type = "last";
		}

		//查询数据库中已经存在的凭证编号，没有会计制度的限制
		List<String> pzbhList = pzlrService.getPzbhList(pznd,kjqj,sszt,pzlx);
		//查询凭证类型列表
		List<Map<String, Object>> pzlxList= pzlrService.getPzlxList();
		//获取自动生成的凭证编号
		String autoPzbh;
		String sfbsc =  pzlrService.CheckSfbsc()  ;//是否补充删除凭证号  1是补充  0 是自动追加
		if(sfbsc.equals("1")){
			autoPzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzlx);
		}else{  //（规则为已存在的最大凭证编号加一）
			autoPzbh= pzlrService.getAutoPzbh(pznd,kjqj, 4,sszt,pzlx);
		}
		String maxPzbh = pzlrService.getAutoPzbh(pznd,kjqj, 4,sszt,pzlx);
		String pageName = "pzlrView";
		if(type != null) {
			switch (type) {
			case "first":
				//从凭证编号列表中获得第一张
				pzbh=pzlrService.getPzbhList(pznd, kjqj, sszt, pzlx).get(0);
				break;
			case "last":
                if("yes".equals(copy)){
					pzbh = pzlrService.autoFill(pzbh, 4, "0");
				}else{
				   pzbh = autoPzbh;
				}
				break;
			case "prev":
				if(!"0001".equals(pzbh)) {
					String pzbhh = pzbh;
					for(int i=Integer.parseInt(pzbhh);i>0;i--){
						pzbhh = ""+(Integer.parseInt(pzbhh) - 1);
						pzbhh = pzlrService.autoFill(pzbhh, 4, "0");
						if(pzlrService.docheck( pzbhh,  pzlx,  CommonUtil.getKjzd(session), sszt)){
							break;
						}
					}
					pzbh = pzbhh;
				}
				break;
			case "next":
				if(!maxPzbh.equals(pzbh)) {
					String pzbhh = pzbh;
					for(int i=Integer.parseInt(pzbhh);i<Integer.parseInt(autoPzbh);i++){
						pzbhh = ""+(Integer.parseInt(pzbhh) + 1);
						pzbhh = pzlrService.autoFill(pzbhh, 4, "0");
						if(pzlrService.docheck( pzbhh,  pzlx,  CommonUtil.getKjzd(session),  sszt)){
							break;
						}
					}
					pzbh = pzbhh;
					
				}
				break;
			case "self":
				  //pzbh = ""+(Integer.parseInt(autoPzbh) - 1);
				  pzbh = pzlrService.autoFill(pzbh, 4, "0");
				break;
			default:
				break;
			}
		}	
		//如果分页计算后的凭证号等于自动生成的凭证号，则进入新增页面
		if(autoPzbh.equals(pzbh)||"add".equals(pd.getString("cmd"))) {
			pageName = "pzlrAdd";
			pzbh = autoPzbh;
			//如果当前前台得到凭证编号与最大的凭证编号不一样，且当前的执行指令不是增加，则执行下面代码
		}else {
			if(pzbhList.contains(pzbh)) {
				Map<String, Object> pzzbMap = pzlrService.getPzlrMain(pznd,kjqj, pzbh,sszt,pzlx);
				mv.addObject("zbMap",pzzbMap);
				session.setAttribute("pzzbGuid", pzzbMap.get("guid"));
				List mxList = pzlrService.getPzlrDetail(pznd, kjqj, pzbh, sszt,pzlx);
				mv.addObject("mxList",mxList);
				//凭证状态
				String pzzt = pzzbMap.get("pzzt")+"";
				if("已保存".equals(pzzt)) {
					pageName = "pzlrEdit";
				}
			}else {
				pzbh = autoPzbh;
				pageName = "pzlrAdd";
			}
		}
		session.setAttribute("pzbh", pzbh);
		session.setAttribute("pzlx", pzlx);
		mv.addObject("pzbh",pzbh);
		mv.addObject("pzz",pzlx);
		mv.addObject("sszt",sszt);
		if( !autoPzbh.equals("0001")){
		    pzbhList.add(autoPzbh);
		}
		//凭证编号列表
		mv.addObject("jsdhList","jsdhList");
		mv.addObject("pzbhList",pzbhList);
		//凭证类型列表
		mv.addObject("pzlxList",pzlxList);
		//是否允许删除凭证
		mv.addObject("sfyxscpz",zdscpzService.getByGid().get("sfyxscpz"));
		
		return pageName;
	}
	/**
	 * 页面之间的跳转
	 * @param session
	 * @return
	 */
	@RequestMapping("/pageSkip")
	public ModelAndView pageSkip(HttpSession session) {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String pageName = Validate.isNullToDefaultString(pd.getString("pageName"), "");
		String kmbh = pd.getString("kmbh");
		String ymbz = pd.getString("ymbz");
		String isAll=Validate.isNullToDefaultString(pd.getString("isAll"), "");
		String guid = Validate.isNullToDefaultString(pd.getString("guid"), session.getAttribute("pzzbGuid")+"");
		boolean sfkscpz = jsxxService.doCheckJsry(LUser.getRybh(), Constant.JSBH_PZSC) ;//是否可删除凭证
		switch (pageName) {
		case "pzlr":
			iniLogin(mv);
			iniSelect(mv);	
			mv.addObject("uploadId", GenAutoKey.get32UUID());
			pageName = iniPzInfo(mv,pd,session);
			break;
		case "print":
			iniPrint(mv, guid,CommonUtil.getKjzd(session));
			break;
		case "pzlrck":
			String pzzt = pd.getString("pzzt");
			mv.addObject("pzzt",pzzt);
			String zbpzlxmc = pd.getString("zbpzlxmc");
			mv.addObject("zbpzlxmc",zbpzlxmc);
			String zy = pd.getString("zy");
			mv.addObject("zy",zy);
			String pzrqs = pd.getString("pzrqs");
			mv.addObject("pzrqs",pzrqs);
			String pzrqf = pd.getString("pzrqf");
			mv.addObject("pzrqf",pzrqf);
			String bhl = pd.getString("bhl");
			mv.addObject("bhl",bhl);
			String bhh = pd.getString("bhh");
			mv.addObject("bhh",bhh);
			String kjkm1 = pd.getString("kjkm1");
			mv.addObject("kjkm1",kjkm1);
			String kjkm2 = pd.getString("kjkm2");
			mv.addObject("kjkm2",kjkm2);
			String zdr = pd.getString("zdr");
			String txt_zdr = pd.getString("txt_zdr");
			String txt_fhr = pd.getString("txt_fhr");
			String txt_jzr = pd.getString("txt_jzr");
			String fhr = pd.getString("fhr");
			String jzr = pd.getString("jzr");
			mv.addObject("zdr",zdr);
			mv.addObject("fhr",fhr);
			mv.addObject("jzr",jzr);
			mv.addObject("txt_zdr",txt_zdr);
			mv.addObject("txt_fhr",txt_fhr);
			mv.addObject("txt_jzr",txt_jzr);
			String txt_jfhjje1 = pd.getString("txt_jfhjje1");
			mv.addObject("txt_jfhjje1",txt_jfhjje1);
			String txt_jfhjje2 = pd.getString("txt_jfhjje2");
			mv.addObject("txt_jfhjje2",txt_jfhjje2);
			iniSelect(mv);
			iniView(mv, guid);
			break;
		default:
			break;
		}
		mv.addObject("kmbh", kmbh);
		mv.addObject("isAll", isAll);
		mv.addObject("ymbz", ymbz);
		mv.addObject("sfkscpz", sfkscpz);
		mv.setViewName("kjhs/pzxx/pzlr/"+pageName);
		return mv;
	}
	/**
	 * 获取报销信息列表数据
	 * @return
	 */
	@RequestMapping(value="getBxxxData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getBxxxPageData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//设置查询字段名
		String sqltext = "*";
		String tableName = "(select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxr) as sqr," + 
				"to_char(t.czrq,'yyyy-mm-dd') as sqrq," + 
				"(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = t.szbm) as szbm,'日常报销' as bxlx,t.bxzje as bxje," + 
				"(select wm_concat(xmmc) from CW_RCBXMXB m where m.zbid=t.guid) as bxxm" + 
				" from cw_rcbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" + 
				" union" + 
				" select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.sqr) as sqr,czrq as sqrq," + 
				"(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.sqr) as szbm," + 
				"'差旅费报销' as bxlx,to_number(t.bxzje) as bxje," + 
				"(select wm_concat(xmmc) from cw_ccsqspb_xm xm left join cw_xmjbxxb s on s.guid=xm.jfbh where xm.ccsqid=t.ccywguid) as bxxm " + 
				"from cw_clfbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" + 
				" union " + 
				"select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxryid) as sqr,to_char(t.czrq,'yyyy-mm-dd') as sqrq," + 
				"(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.bxryid) as szbm," + 
				"'公务接待费报销' as bxlx,t.bxje,'' from cw_gwjdfbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb) " + 
				"union " + 
		        "select t.gid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.jkr) as sqr,to_char(t.jksj,'yyyy-mm-dd') as sqrq,   " + 
		        "(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.jkr) as szbm,  " + 
		        "'借款' as bxlx,jkzje as bxje," + 
				"(select wm_concat(xmmc) from cw_xmjbxxb A left join CW_JKYWB_MXB k on k.jfbh = a.guid where k.jkid=t.gid)" + 
				" from CW_JKYWB t where t.shzt='8' and t.gid not in (select bxid from cw_pzlrbxdzb)" +
				" union " +
				" select t.guid,t.fflsh as djbh," +
				" (select '(' || rybh || ')' || xm from gx_sys_ryb where rybh = t.jbr) as sqr," +
				" to_char(t.lrsj, 'yyyy-mm-dd') as sqrq," +
				" (select '(' || b.dwbh || ')' || b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.rybh = t.jbr) as szbm," +
				" '个人收入发放' as bxlx," +
//				" t.ffzje as bxje," +
				"(select sum(sfje) from cw_grsrmxb where fflsh=t.fflsh) as bxje,"+
				" (select  '('||D.XMBH||')'||D.XMMC FROM cw_xmjbxxb D WHERE D.guid=t.XMBH)AS bxxm" +
				" from cw_grsrzb t  where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" +
				") a";
		//设置条件
		pageList.setSqlText(sqltext);
		pageList.setTableName(tableName);
		pageList.setKeyId("djbh");
		String strWhere = "";
		String bxlx = Validate.isNullToDefaultString(pd.getString("bxlx"), "all");
		String djbh = Validate.isNullToDefaultString(pd.getString("djbh"), "");
		String sqr = Validate.isNullToDefaultString(pd.getString("sqr"), "");
		if("all".equals(bxlx)){
			strWhere += " and 2=2";
		}else if("00".equals(bxlx)){
			strWhere += " and bxlx='日常报销'";
		}else if("01".equals(bxlx)){
			strWhere += " and bxlx='差旅费报销'";
		}else if("02".equals(bxlx)){
			strWhere += " and bxlx='公务接待费报销'";
		}else if("03".equals(bxlx)){
			strWhere += " and bxlx='借款'";
		}else if("04".equals(bxlx)){
			strWhere += " and bxlx='个人收入发放'";
		}
		if(Validate.noNull(djbh)){
			strWhere += " and djbh like'%"+djbh+"%'";
		}
		if(Validate.noNull(sqr)){
			strWhere += " and sqr like'%"+sqr+"%'";
		}
		pageList.setStrWhere(strWhere);
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取报销信息列表数据
	 * @return
	 */
	@RequestMapping(value="getXzxxData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getXzxxData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		//设置查询字段名
		String sqltext = "*";
		String tableName = "(select 'zzry_'||gzyf as gid,gzyf,count(1) as rs,'zzry' as lxbh,'在职人员' as lxmc,sum(nvl(YFHJ,0)) as YFHJ from cw_xzb t where gzyf is not null and shzt='2' and t.gid not in (select bxid from cw_pzlrbxdzb) group by gzyf " + 
				" union" + 
				" select 'lzry_'||gzyf as gid,gzyf,count(1) as rs,'lzry' as lxbh,'退休人员' as lxmc,sum(nvl(YFHJ,0)) as YFHJ from cw_lzxzb t where gzyf is not null and shzt='2' and t.gid not in (select bxid from cw_pzlrbxdzb) group by gzyf) a";
		//设置条件
		pageList.setSqlText(sqltext);
		pageList.setTableName(tableName);
		pageList.setKeyId("gid");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取科研管理经费信息
	 * @return
	 */
	@RequestMapping(value="getGlfData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getGlfData() {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String bxlx = pd.getString("bxlx");
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" f.guid,x.xmmc as xmmc,decode(f.xmlx,'1','横向','0','纵向') as xmlx,r.xm,b.mc,to_char(F.XYJJ,'FM99999999999999999.00') as XYJJ,to_char(F.XXJJ,'FM9999999999999.00') AS XXJJ ");
		StringBuffer tableName = new StringBuffer();
		tableName.append(" CW_KYGLF F LEFT JOIN CW_XMJBXXB X on x.guid = f.xmguid  LEFT JOIN GX_SYS_RYB R ON R.RYBH=F.FZR LEFT JOIN GX_SYS_DWB B ON B.DWBH=F.SZBM ");
		//设置条件
		pageList.setSqlText(sqltext.toString());
		pageList.setTableName(tableName.toString());
		pageList.setKeyId("F.guid");
		StringBuffer strWhere = new StringBuffer();
		if("1".equals(bxlx)) {
			strWhere.append(" and zt='1' ");
		}else {
			strWhere.append(" and zt='0' or zt is null ");
		}
		pageList.setStrWhere(strWhere.toString());
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取会计科目列表数据
	 * @return
	 */
	@RequestMapping(value="getKjkmData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getKjkmPageData(HttpSession session) {
		PageList pageList = new PageList();
		PageData pd = this.getPageData();
		String kjzd = CommonUtil.getKjzd(session);
		String sszt = Constant.getztid(session);
		//设置查询字段名
//		String sqltext = " guid,kmbh,kmmc,(select mc from gx_sys_dmb where zl = '"+Constant.KMSX+"' and dm = kmsx) as kmsx,yefx";
//		String tableName = " cw_kjkmszb ";
//		String strWhere = " and sfmj = '1' ";
//		String bh = Validate.isNullToDefaultString(pd.getString("bh"), "");
//		if(Validate.noNull(bh)) {
////			strWhere +=" and sjfl='"+bh+"' or sjfl in (select kmbh from cw_kjkmszb l where l.sjfl='"+bh+"') and sjfl!='root'";
//			strWhere +=" start with kmbh='"+bh+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root'  ";
//		}
		String sqltext = "A.*";
		String tableName = "( select guid,kmbh,kmmc,sfmj,(select mc from gx_sys_dmb where zl = '"+Constant.KMSX+"' and dm = kmsx) as kmsx,yefx from cw_kjkmszb where 1=1";
		//tableName += " where sfmj = '1'" ;
		String bh = Validate.isNullToDefaultString(pd.getString("bh"), "");
		if(Validate.noNull(sszt)){
			tableName +=" and sszt='"+sszt+"'";
		}
		if(Validate.noNull(bh)) {
			tableName +=" start with kmbh='"+bh+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root'  ";
	} 
		tableName +=") A";	
		String strWhere ="";
		//设置条件
		pageList.setSqlText(sqltext);
		pageList.setTableName(tableName);
		pageList.setKeyId("guid");
		pageList.setStrWhere(strWhere);
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 根据报销单生成凭证信息
	 * @param session
	 * @return
	 */
	@RequestMapping(value="getPzJsonByBx",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getPzJsonByBx(HttpSession session) {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String type = pd.getString("type");
		String kjzd = CommonUtil.getKjzd(session);
		String sszt = Constant.getztid(session);
		List<Map<String, Object>> list = pzlrService.getPzJsonByBx(type,guid,sszt,kjzd);
		Gson gson = new Gson();
		return "{\"size\":"+list.size()+",\"guid\":\""+guid+"\",\"data\":"+gson.toJson(list)+"}";
	}

	/**
	 * 保存报销生成凭证
	 * @param session
	 * @return
	 */
	@RequestMapping(value="doadd",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doadd(HttpSession session) {
		PageData pd = this.getPageData();
		String pzlx = pd.getString("pzlx");  //值为 01
		Gson gson = new Gson();
		List list = gson.fromJson(pd.getString("json"), new TypeToken<List>(){}.getType());//将json转为list
		String kjzd = CommonUtil.getKjzd(session); //当前账套  会计制度
		String sszt = Constant.getztid(session);   //账套id 
		
		int flag= pzlrService.doZf(list,kjzd,sszt,pzlx);
		if(flag>0)
			return "{\"success\":true,\"msg\":\"凭证生成成功！\"}";
		else
			return "{\"success\":false,\"msg\":\"凭证生成失败！\"}";
	}
	/**
	 * 保存薪资生成凭证
	 * @param session
	 * @return
	 */
	@RequestMapping(value="doaddxz",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doaddxz(HttpSession session) {
		PageData pd = this.getPageData();
		String rylx = pd.getString("rylx");
		String gzyf = pd.getString("gzyf");
		String pzlx = pd.getString("pzlx");
		String kjzd = CommonUtil.getKjzd(session);
		String sszt = Constant.getztid(session);
		String str = pzlrService.docheck(rylx, gzyf, pzlx, kjzd, sszt);
		if(Validate.noNull(str))
			return str;
		pzlrService.autoCreatePzlrByXz(rylx, gzyf, pzlx, kjzd, sszt);
		return "{\"success\":true,\"msg\":\"凭证生成成功！\"}";
	}
	/**
	 * 根据凭证模板生成凭证信息
	 * @return
	 */
	@RequestMapping(value="getPzJsonByMb",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getPzJsonByMb() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		List<Map<String, Object>> list = pzlrService.getPzJsonByMb(guid);
		Gson gson = new Gson();
		return "{\"size\":"+list.size()+",\"data\":"+gson.toJson(list)+"}";
	}
	/**
	 * 获取交互数据
	 * @param session
	 * @return
	 */
	@RequestMapping(value="getEchoData",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getEchoJson(HttpSession session) {
		PageData pd = this.getPageData();
		pd.put("kjzd",CommonUtil.getKjzd(session));
		Map<String, Object> map = pzlrService.getEchoData(pd,session);
		Gson gson = new Gson();
		return gson.toJson(map);
	}
	/**
	 * 获取联想输入
	 * @param session
	 * @return
	 */
	@RequestMapping(value="getSuggestInfo",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getSuggestInfo(HttpSession session) {
		PageData pd = this.getPageData();
		pd.put("kjzd",CommonUtil.getKjzd(session));
		return pzlrService.getSuggestInfo(pd);
	}
	/**
	 * 保存
	 * @param session
	 * @return
	 */
	@RequestMapping(value="doSave",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(HttpSession session) {
		PageData pd = this.getPageData();
		String type = pd.getString("type");
		String pzz=pd.getString("pzz");
		if("ADD".equals(type)) {
			if(pzlrService.addPzlr(pd,session) > 0) {
				
				return "{\"pzz\":\""+pzz+"\",\"success\":true}";
			}else {
				return "{\"pzz\":\""+pzz+"\",\"success\":false}";
			}
		}else{ //update  
			int i = pzlrService.updatePzlr(pd,session) ;
			if(i > 0) {
				return "{\"pzz\":\""+pzz+"\",\"success\":true}";
			}else {
				return "{\"pzz\":\""+pzz+"\",\"success\":false}";
			}
		}
	}
	/**
	 * 判断 是否是  报销凭证生成
	 */
	@RequestMapping(value="doCheckBxpz",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doCheckBxpz(HttpSession session) {
		String guid  = session.getAttribute("pzzbGuid")+"";
		int b;
		b = pzlrService.doCheckBxpz(guid);
		if(b>0){
			return "{\"success\":true}";
		}else{
			return "{\"success\":false}";
		}
	}
	/**
	 * 确认退回 修改报销单的状态
	 */
	@RequestMapping(value="ChangeBxzt",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object ChangeBxzt(HttpSession session) {
		String guid  = session.getAttribute("pzzbGuid")+"";
		//查询CW_PZLRBXDZB中的bxid  type
		Map bxmap = pzlrService.getBxpzxx(guid);
		String bxid = (String) bxmap.get("bxid");
		String type = (String) bxmap.get("bxtype");
		int i=0;
		switch (type) {
		case "clfbx":
			i = pzlrService.updateCLbxpz(bxid);
			break;
		case "gwjdbx":
			i = pzlrService.updateJDbxpz(bxid);
			break;
		case "rcbx":
			i = pzlrService.updateRCbxpz(bxid);
			break;
		case "jkbx":
			i = pzlrService.updateJKbxpz(bxid);
			break;
		case "srsblr":
			i = pzlrService.updateSRbxpz(bxid);
			break;
		default:
			break;
		}
		if(i>0){
		    return MessageBox.toJson(true, "退回成功");
		}else{		   
			return MessageBox.toJson(false, "退回失败");
		}
	}
	/**
	 * 删除
	 * @param session
	 * @return
	 */
	@RequestMapping(value="doDelete",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doDel(HttpSession session) {
		PageData pd = this.getPageData();
		String pzly = pd.getString("pzly");
		if(pzlrService.deletePz(session,"del",pzly)){
			return MessageBox.toJson(true, "删除成功！");
		}else {
			return MessageBox.toJson(false, "删除失败！");
		}
	}
	@RequestMapping(value="doDeleteAndTh",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object doDeleteAndTh(HttpSession session) {
		PageData pd = this.getPageData();
		String pzly = pd.getString("pzly");
		if(pzlrService.deletePz(session,"delAndth",pzly)) {
			return MessageBox.toJson(true, "删除和退回成功！");
		}else {
			return MessageBox.toJson(false, "删除和退回失败！");
		}
	}
	/**
	 * 获取经济科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goJjkmPage")
	public ModelAndView goJjkmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/pzxx/pzlr/jjkm");
		String kmnd = pd.getString("kmnd");
		String isAll = Validate.isNullToDefaultString(pd.getString("isAll"), "");
		
		
		 Calendar date = Calendar.getInstance();
			// String jn = String.valueOf(date.get(Calendar.YEAR));
		     int jn=Integer.valueOf(date.get(Calendar.YEAR));//今年
		     int mn=jn+1;
		     int hn=jn+2;
		     int qn=jn-1;
		     int qnn=jn-2;
		     
		     Map map = new HashMap();
		     Map map1 = new HashMap();
		     Map map2= new HashMap();
		     Map map3 = new HashMap();
		     Map map4 = new HashMap();
		     map.put("kmnd", qnn);
		     map1.put("kmnd", qn);
		     map2.put("kmnd", jn);
		     map3.put("kmnd", mn);
		     map4.put("kmnd", hn);
		     ArrayList<Map<Integer,Object>> list = new ArrayList<>();
		     list.add(map);
		     list.add( map1);
		     list.add( map2);
		     list.add( map3);
		     list.add( map4);
		
		     mv.addObject("isAll",isAll);
			mv.addObject("kmnd",kmnd);
	        mv.addObject("jn",jn);
	        mv.addObject("nlist",list);
			mv.addObject("treesearch", treesearch);
			return mv;
	}
	
	@RequestMapping(value="/goJjkmPagedx")
	public ModelAndView goJjkmPage2(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/pzxx/pzlr/jjkmdx");
		String kmnd = pd.getString("kmnd");
		
		 Calendar date = Calendar.getInstance();
			// String jn = String.valueOf(date.get(Calendar.YEAR));
		     int jn=Integer.valueOf(date.get(Calendar.YEAR));//今年
		     int mn=jn+1;
		     int hn=jn+2;
		     int qn=jn-1;
		     int qnn=jn-2;
		     
		     Map map = new HashMap();
		     Map map1 = new HashMap();
		     Map map2= new HashMap();
		     Map map3 = new HashMap();
		     Map map4 = new HashMap();
		     map.put("kmnd", qnn);
		     map1.put("kmnd", qn);
		     map2.put("kmnd", jn);
		     map3.put("kmnd", mn);
		     map4.put("kmnd", hn);
		     ArrayList<Map<Integer,Object>> list = new ArrayList<>();
		     list.add(map);
		     list.add( map1);
		     list.add( map2);
		     list.add( map3);
		     list.add( map4);
		
		mv.addObject("kmnd",kmnd);
        mv.addObject("jn",jn);
        mv.addObject("nlist",list);
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取经济科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getJjkmPageList")
	@ResponseBody
	public Object getJjkmPageList(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		String kmnd = pd.getString("kmnd");
		Calendar date = Calendar.getInstance();
		 String jn=String.valueOf(date.get(Calendar.YEAR));//今年
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,j.kmnd,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		StringBuffer strWhere = new StringBuffer();
		strWhere.append("and sszt ='"+sszt+"'");
		// 设置WHERE条件
		if(Validate.noNull(kmnd)){
			strWhere.append(" and kmnd='"+kmnd+"' ");
		}else {
			strWhere.append(" and kmnd='"+jn+"' ");
		}
		if(Validate.noNull(treeDm)){
			strWhere.append(" and (j.k like '"+treeDm+"%' or j.l='"+treeDm+"' or kmbh='"+treeDm+"')" );
		}
		strWhere.append( " and qyf = '1' " );
		String isAll = Validate.isNullToDefaultString(pd.getString("isAll"), "");
		if(Validate.noNull(isAll)){
			strWhere.append( " and  substr(j.kmbh,1,3) not in('301','309')" );
		}
		//项目设置的基本支出或项目支出 -xmguid
		String xmguid = pd.getString("xmguid");
//		if(Validate.noNull(xmguid)){
//			strWhere.append(" and  yslx in ( ");
//			strWhere.append(" select yslx from Cw_Xmsrbnew where srkmbh is not null ");
//			strWhere.append(" and xmxxbh='"+xmguid+"' )" );
//		}
		pageList.setStrWhere(strWhere.toString());
		pageList.setTableName(" CW_JJKMB j ");//表名
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	//获取列表数据
		@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getXmxxPageData(HttpSession session) {
			String sszt = Constant.getztid(session);
			PageData pd = this.getPageData();
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			String strWhere = "";
			PageList pageList = new PageList();
			String bmbh = pd.getString("bmbh");
			sqltext.append(" * ");
			tablename.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbhmc,x.bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
					+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
					+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
					+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
					+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
					+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
					+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
					+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
					+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
					+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"') k");
			pageList.setSqlText(sqltext.toString());
			//设置表名
			pageList.setTableName(tablename.toString());
			if(!Validate.isNullOrEmpty(bmbh)) {
				strWhere  += " and k.bmbh = '"+bmbh+"'";
			}
				strWhere += "  and k.guid not in (select nvl(jfbh, '0') from XMINFOS) ";
			//设置表主键名
			pageList.setKeyId("GUID");//主键
			//设置WHERE条件
			pageList.setStrWhere(strWhere);
			pageList.setHj1("");//合计
			pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
		/**
		 * 增加管理费信息
		 * @author BiMing
		 * @Time 2018年10月12日下午5:14:56
		 */
		@RequestMapping(value="/doGlfSave",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object doGlfSave(CW_JSYHZHB jsyhzhb)throws Exception{
			PageData pd = this.getPageData();
			String json = pd.getString("json");	//得到前台的json
			String ajson = json.substring(8,json.length()-1);//截取json,让gson用
			Gson gson = new Gson();		
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			int i =pzlrService.insertGlf(list);
			String b;
			if(i>0) {
				b="success";
			}else {
				b="false";
			}
			return b;
		}
		/**
		 * 删除管理费信息
		 * @author BiMing
		 * @Time 2018年10月12日下午5:14:56
		 */
		@RequestMapping(value="/DeleteKyglxm",produces = "text/html;charset=utf-8")
		@ResponseBody
		public Object DeleteKyglxm(){
			PageData pd = this.getPageData();
			String guids = pd.getString("guid");
	    	int k = pzlrService.DeleteKyglxm(guids);
			if(k>0){
				return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
			}else{
				return MessageBox.show(false, MessageBox.FAIL_DELETE);
	    	}
		}
		/**
		 * 检查 是否 =已 增加  了项目
		 * @author BiMing
		 * @Time 2018年10月12日下午5:14:56
		 */
		@RequestMapping(value="/CheckKyglxm",produces = "text/html;charset=utf-8")
		@ResponseBody
		public Object CheckKyglxm(){
			PageData pd = this.getPageData();
			String xmguids = pd.getString("xmguid");
	    	int k = pzlrService.CheckKyglxm(xmguids);
			if(k>0){ 
				return MessageBox.toJson(true, "已添加过！页面不可操作");
			}else{
				return MessageBox.toJson(false, "可以添加");
	    	}
		}
		/**
		 * 导出管理费信息
		 * @author BiMing
		 * @Time 2018年10月12日下午5:14:56
		 */
		@RequestMapping("/expKyglxm")
		@ResponseBody
		public Object expKyglxm() {
			PageData pd = this.getPageData();
			// searchValue 此处只有一个条件  = bxlx
			String searchValue = pd.getString("searchJson");
			String id = pd.getString("id");
			StringBuffer sql = new StringBuffer();
			sql.append(" select f.guid,x.xmmc as xmmc,decode(f.xmlx,'1','横向','0','纵向') as xmlx,r.xm,b.mc,to_char(F.XYJJ,'FM99999999999999999.00') as XYJJ,to_char(F.XXJJ,'FM9999999999999.00') AS XXJJ ");
			sql.append("  from CW_KYGLF F LEFT JOIN CW_XMJBXXB X ON X.bmbh=F.szbm and x.xmbh=f.xmbh  LEFT JOIN GX_SYS_RYB R ON R.RYBH=F.FZR LEFT JOIN GX_SYS_DWB B ON B.DWBH=F.SZBM ");

			//sql.append(ToSqlUtil.jsonToSql(searchValue));
			if("1".equals(searchValue)) {
				sql.append(" where zt='1' ");
			}else {
				sql.append(" where zt='0' or zt is null ");
			}
			if(!"".equals(id)){
				sql.append(" and K.GUID in ('"+id+"') ");
			}
			
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
			
			return pzlrService.expKyglxm(realfile, shortfileurl,sql.toString());
		}
		/**
		 * 单据上传跳转页面
		 * @param session
		 * @return
		 */
		@RequestMapping("/upload")
		public ModelAndView upload(HttpSession session) {
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			//凭证录入主表的id用于上传的id
			String uploadId = pd.getString("uploadId");
			String type = Validate.isNullToDefaultString(pd.getString("type"), "");
			mv.addObject("uploadId", uploadId);
			mv.setViewName("kjhs/pzxx/pzlr/upload");
			String[] fjxx = fileService.getFjList(uploadId,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
			mv.addObject(type, type);
			return mv;
		}
		/**
		 * 凭证录入附件查看页面
		 * @return
		 */
		@RequestMapping(value="/goFjckPage",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public ModelAndView goFjckPage(HttpSession session) {
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			//凭证录入主表的id用于上传的id
			String uploadId = pd.getString("uploadId");
			String type = Validate.isNullToDefaultString(pd.getString("type"), "");
			mv.addObject("uploadId", uploadId);
			mv.setViewName("kjhs/pzxx/pzlr/fjck");	
			String[] fjxx = fileService.getFjList(uploadId,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
			mv.addObject(type, type);
			return mv;
		}
			
		//获得领导签字
		public void iniFile(ModelAndView mv,String guid) {
			String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
			mv.addObject("fjView",fjxx[0]);
			mv.addObject("fjConfig",fjxx[1]);
		}
		
		
		/**
		 * 复制凭证
		 * @param session
		 * @return
		 */
		@RequestMapping(value="/selectCopy",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object selectCopy(HttpSession session,String dqnd,String dqqj) {
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");
			String pzrq = Validate.isNullToDefaultString(pd.getString("pzrq"), new SimpleDateFormat("yyyy-MM-dd").format(new Date()));
			//获取凭证类型
			String pzz = Validate.isNullToDefaultString(pzlrService.getPzzByGuid(guid), "");
			String msg = "";
			if(Validate.isNull(guid)||"undefined".equals(guid)||(pzlrService.selectPzInfo(guid))==0) {
				return "{\"success\":false}";
			}else {
				// 生成凭证号
				String sszt = Constant.getztid(session);
				String autoPzbh;
				String sfbsc =  pzlrService.CheckSfbsc()  ;//是否补充删除凭证号  1是补充  0 是自动追加
				if(sfbsc.equals("1")){
					autoPzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzz);
				}else{  //（规则为已存在的最大凭证编号加一）
					autoPzbh= pzlrService.getAutoPzbh(dqnd,dqqj, 4,sszt,pzz);
				}
				msg = pzlrService.insertCopyPzInfoByGuid(guid,pzrq,session,dqnd,dqqj,autoPzbh);
				if(Validate.isNull(msg)){				
					return "{\"success\":true,\"pzz\":\""+pzz+"\",\"pzbh\":\""+autoPzbh+"\"}";
				}
			}
			return "{\"success\":false,\"msg\":\""+msg+"\"}";
		} 
		
		/**
		 * 通过摘要带出科目
		 * @param session
		 * @return
		 */
		@RequestMapping(value="/checkZy",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object checkZy(HttpSession session) {
			PageData pd = this.getPageData();
			String zy = pd.getString("zy");
			String kmbh = Validate.isNullToDefaultString(pzlrService.getKmbhZy(session, zy),"");
			if(Validate.noNull(kmbh)){
				return "{\"success\":true,\"kmbh\":\""+kmbh+"\"}";
			}
			return "{\"success\":false}";
		} 
		
		/**
		 * 复制凭证成为模板
		 * @param session
		 * @return
		 */
		@RequestMapping(value="/savePzToMb",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object savePzToMb() {
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");
			String param = pd.getString("param");
			boolean bool = false;
			if(Validate.isNull(guid)||"undefined".equals(guid)||(pzlrService.selectPzInfo(guid))==0) {
				return "{\"success\":false}";
			}else {
				bool = pzlrService.insertCopyPzInfoByGuidToMb(guid, param);
				if(bool){
					return "{\"success\":true}";
				}
			}
			return "{\"success\":false}";
		}
		
		@RequestMapping(value="/wldw_list")
		@ResponseBody
		public Object goBjxxPage(HttpSession session){
			PageList pageList = new PageList();
			PageData pd = this.getPageData();
			String dqzt =  Constant.getztid(session);
			// 设置查询字段名
			StringBuffer sqltext = new StringBuffer();
			sqltext.append(" guid, wlbh, dwmc, je");
			pageList.setSqlText(sqltext.toString());
			// 表名
			StringBuffer sqltab = new StringBuffer();
			sqltab.append(" (select a.guid,a.wlbh,a.dwmc,");
			sqltab.append(" (b.ncye + nvl((case m.jdfx when '0' then sum(nvl(m.jfje, 0.00)) - sum(nvl(m.dfje, 0.00)) ");
			sqltab.append("  when '1' then sum(nvl(m.dfje, 0.00)) -sum(nvl(m.jfje, 0.00)) end), ");
			sqltab.append("  0.00)) as je ");
			sqltab.append(" from CW_WLDWB a ");
			sqltab.append(" left join cw_kmyemxb b on a.guid = b.dwfz and a.sszt = b.sszt ");
			sqltab.append(" left join cw_pzlrmxb m on m.guid = a.guid ");
			sqltab.append(" where a.SSZT = '"+dqzt+"' and nvl(a.sfdgzf, 00) <> '01' ");
			sqltab.append(" group by a.guid,a.wlbh,a.dwmc,b.ncye,m.jdfx ) A ");
			pageList.setTableName(sqltab.toString());
			// 主键
			pageList.setKeyId("GUID");
			// 设置WHERE条件
			String strWhere="";
			
			
			pageList.setStrWhere(strWhere);
			//页面数据
			//pageList = pageService.findPageList(pd,pageList);
			pageList = pageService.findWindowList(pd,pageList,"WLDWS");//引用传递
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
		
		/**
		 * 通过摘要带出科目
		 * @param session
		 * @return
		 */
		@RequestMapping(value="/wldw",produces="text/html;charset=UTF-8")
		@ResponseBody
		public ModelAndView wldw(HttpSession session) {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			String kmbh= pd.getString("kmbh");
			String controlId = Validate.isNullToDefaultString(pd.getString("controlId"),"");
			mv.addObject("controlId", controlId);
			mv.addObject("kmbh", kmbh);
			mv.setViewName("kjhs/pzxx/pzlr/wldw_list");
			return mv;
		} 
		/**
		 * 查询kmbh是否有必填辅助信息
		 * @param session
		 * @return
		 */
		@RequestMapping(value="/checkKmbh",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object checkKmbh(HttpSession session) {
			PageData pd = this.getPageData();
			String kmbh = pd.getString("kmbh");
			Map map = pzlrService.getKmFzInfoByKmbh(session, kmbh);
			boolean grfz = true;
			boolean wlfz = true;
			boolean gnkm = true;
			if(Validate.isNull(map.get("GRFZ"))||"0".equals(map.get("GRFZ")+"")){
				grfz = false;
			}
			if(Validate.isNull(map.get("WLDWFZ"))||"0".equals(map.get("WLDWFZ")+"")){
				wlfz = false;
			}
			if(Validate.isNull(map.get("SFGNFLKM"))||"0".equals(map.get("SFGNFLKM")+"")){
				gnkm = false;
			}
			return "{\"grfz\":\""+grfz+"\",\"wlfz\":\""+wlfz+"\",\"gnkm\":\""+gnkm+"\"}";
		}
		
		/**
		 * 会计科目设置树
		 */
		@RequestMapping(value="/mainKjkmszTree")
		public ModelAndView goMainKjkmszTree(){
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			//页面路径
			mv.addObject("pageUrl",pd.getString("pageUrl"));
			//树值路径
			mv.addObject("treeJson",pd.getString("treeJson"));
			mv.setViewName("kjhs/pzxx/pzlr/mainKjkmszTree");
			return mv;
		}
		
		/**
		 * 获取会计科目信息列表的页面
		 * @return
		 */
		@RequestMapping(value="/goKmszPage")
		public ModelAndView goDdbPage(){
			
			
			
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			 Calendar date = Calendar.getInstance();
			// String jn = String.valueOf(date.get(Calendar.YEAR));
		     int jn=Integer.valueOf(date.get(Calendar.YEAR));//今年
		     int mn=jn+1;
		     int hn=jn+2;
		     int qn=jn-1;
		     int qnn=jn-2;
		     
		     Map map = new HashMap();
		     Map map1 = new HashMap();
		     Map map2= new HashMap();
		     Map map3 = new HashMap();
		     Map map4 = new HashMap();
		     map.put("kmnd", qnn);
		     map1.put("kmnd", qn);
		     map2.put("kmnd", jn);
		     map3.put("kmnd", mn);
		     map4.put("kmnd", hn);
		     ArrayList<Map<Integer,Object>> list = new ArrayList<>();
		     list.add(map);
		     list.add( map1);
		     list.add( map2);
		     list.add( map3);
		     list.add( map4);
		     
		    List zdlist = pzlrService.getzd();
		      
		    String kmsx = pd.getString("kmsx"); 
			String dm = pd.getString("dm");
			String jb = pd.getString("jb");
			String sfmj = pd.getString("sfmj");
			String id = pd.getString("id");
			String kmnd = pd.getString("kmnd");
			String kjzd = pd.getString("kjzd");
			String yefx = pd.getString("yefx");
			String opre = pd.getString("opre");
			String kmjc = pd.getString("kmjc");
			String treesearch = pd.getString("treesearch");
			mv.setViewName("kjhs/pzxx/pzlr/gnkm_list");
			mv.addObject("treesearch", treesearch);
			mv.addObject("dm", dm);
			mv.addObject("jb", jb);
			mv.addObject("sfmj", sfmj);
			mv.addObject("id", id);
			mv.addObject("jn",jn);
			mv.addObject("kmnd",kmnd);
			mv.addObject("opre",opre);
			mv.addObject("nlist",list);
			mv.addObject("kmsx",kmsx);
			mv.addObject("kmjc",kmjc);
			mv.addObject("zdlist",zdlist);
			mv.addObject("kjzd",kjzd);
			mv.addObject("yefx",yefx);
			mv.addObject("mkbh",pd.getString("mkbh"));
			//mv.addObject("mkbh","080101");
			return mv;
		}
		
		/**
		 * 获取会计科目设置页面信息
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/gnkm_list")
		@ResponseBody
		public Object getPageList( HttpSession session) throws Exception{
			String sszt = Constant.getztid(session);
			PageData pd = this.getPageData();
			Calendar date = Calendar.getInstance();
			StringBuffer tablename = new StringBuffer();
			PageList pageList = new PageList();
			tablename.append(" k.guid, k.kmbh,k.kmmc, k.kmsx,(select d.mc from gx_sys_dmb d where d.zl = 'kmsx' and d.dm = k.kmsx) as kmsxmc,"
				+ " k.zjf, k.yefx, (case k.yefx when '1' then '贷方' else '借方' end) as yefxmc, k.kmjc,k.qyf, (case k.qyf when '1' then '是' else '否' end) as qyfmc,"
				+ " k.sssjkm, k.jb,k.czr, to_char(czrq,'yyyy') as czrq ");
			pageList.setSqlText(tablename.toString());
			//设置表名
			pageList.setTableName(" CW_GNKMB k");
			//设置表主键名
			pageList.setKeyId("GUID");//主键
			//设置WHERE条件
			pageList.setStrWhere(" and sszt='"+sszt+"' "); 
//		    pageList = pageService.findPageList(pd,pageList);
		    pageList = pageService.findWindowList(pd,pageList,"WWW");//引用传递
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
			
		}
		
		/**
		 * 获取项目 可用金额
		 * @param xmbh
		 * @param bmbh
		 * @return
		 */
		@RequestMapping(value="/getXmkyje",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getXmkyje(String xmbh,String bmbh,HttpSession session) {
			BigDecimal kyje = pzlrService.getXmkyje(xmbh, bmbh,session);
			return "{\"kyje\":\""+kyje+"\"}";
		}
		
		/**
		 * 验证单据必填
		 * @param guid
		 * @return
		 */
		@RequestMapping(value="/checkDj",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object checkDj(String guid) {
			boolean bool = pzlrService.checkDj(guid);
			if(bool){
				return MessageBox.toJson(true, "不必传");
			}else{
				return MessageBox.toJson(false, "单据必传且未上传");
			}
		}
		
		/**
		 * 查询开户银行信息
		 * @param type
		 * @param hm
		 * @return
		 */
		@RequestMapping(value="/getKhyh",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getKhyh(String type,String hm) {
			Gson gson = new Gson();
			List list = pzlrService.getKhyh(type, hm);
			if(list==null||list.size()==0){
				list = new ArrayList<Map<String,Object>>();
			} 
			return gson.toJson(list);
		}
		
		/**
		 * 查询结算单号
		 * @param type
		 * @param hm
		 * @return
		 */
		@RequestMapping(value="/getJsdh",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getJsdh(String type,String hm) {
			Gson gson = new Gson();
			List list = pzlrService.getJsdh(type, hm,"");
			if(list==null||list.size()==0){
				list = new ArrayList<Map<String,Object>>();
			} 
			return gson.toJson(list);
		}
		
		
		/**
		 * 检查还款金额是否超出借款金额
		 * @param wldc
		 * @param money
		 * @return
		 */
		@RequestMapping(value="/checkWldc",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object checkWldc(String wldc,String money,String pzid) {
			Gson gson = new Gson();
			boolean bool = pzlrService.checkWldc(wldc, money,pzid);
			if(bool){
				return "{\"success\":true}"; 
			}
			return "{\"success\":false}"; 
		}
		
		/**
		 * 查询往来对冲的借款单号list
		 * @param zrr
		 * @param dfdw
		 * @return
		 */
		@RequestMapping(value="/getWldc",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getWldc(String zrr,String dfdw){
			List list = new ArrayList<Map<String,Object>>();
			String pzid = Validate.isNullToDefaultString(this.getPageData().getString("pzid"),"");
			if(Validate.noNull(zrr)){
				list = pzlrService.getJsdh("zrr",zrr,pzid);
			}
			if(Validate.noNull(dfdw)){
				list = pzlrService.getJsdh("dfdw",dfdw,pzid);
			}
			Gson gson = new Gson();
			return gson.toJson(list);
		}
		
		/**
		 * 检查借款单号是否重复
		 * @param zrr
		 * @param dfdw
		 * @return
		 */
		@RequestMapping(value="/checkJkdh",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object checkJkdh(String zclx,String pzid){
			boolean bool = pzlrService.checkJkdh(zclx, pzid);
			if(bool){
				return "{\"success\":true}"; 
			}
			return "{\"success\":false}"; 
		}
		/**
		 * 判断经济科目是否末级
		 * @return
		 */
		@RequestMapping(value="/doSelectSfmj",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String doSelectSfmj(){
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			String kmbh = pd.getString("kmbh");
			mv.addObject("kmbh",kmbh);
			Map<?, ?> map = pzlrService.getObjectByIdSfmj(kmbh);
			String sfmj = String.valueOf(map.get("sfmj")) ;
			//int jzcs = Integer.parseInt(map.get("jzcs"))
			return sfmj ;
           
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
		 * 银行录入明细弹窗
		 * @param mxid
		 * @param zbid
		 * @return
		 */
		@RequestMapping(value="/WindowMx")
		@ResponseBody
		public ModelAndView WindowMx(String mxid,String zbid){
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			String je = pd.getString("je");
			List list = new ArrayList<Map<String,Object>>();
			list = pzlrService.getYhmxIdByZBidAndMxId(zbid, mxid);
			mv.addObject("list", list);
			mv.addObject("zbid", zbid);
			mv.addObject("mxid", mxid);
			mv.addObject("je", je);
			mv.setViewName("kjhs/pzxx/pzlr/WindowMx");
			return mv;
		}
		
		/**
		 * 银行录入明细弹窗
		 * @param mxid
		 * @param zbid
		 * @return
		 */
		@RequestMapping(value="/btn_lookMx")
		@ResponseBody
		public ModelAndView btn_lookMx(String mxid,String zbid){
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			List list = new ArrayList<Map<String,Object>>();
			list = pzlrService.getYhmxIdByZBidAndMxIdView(zbid, mxid);
			mv.addObject("list", list);
			mv.addObject("zbid", zbid);
			mv.addObject("mxid", mxid);
			mv.setViewName("kjhs/pzxx/pzlr/btn_lookMx");
			return mv;
		}
		
		/**
		 * 保存
		 * @param zbid
		 * @param mxid
		 * @return
		 */
		@RequestMapping(value = "/saveYhmx", produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object saveYhmx(String zbid,String mxid) {
			int result = 0;
			Gson gson = new Gson();
			String jsonStr = this.getPageData().getString("jsonStr");
			String ajson = jsonStr.substring(8,jsonStr.length()-1);
			List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			PageData pd = this.getPageData();
			if(list!=null&&list.size()>0){
				for(int i=0;i<list.size();i++){
					Map map = (Map)list.get(i);
					result += pzlrService.saveYhmx(map, zbid, mxid,i);
				}
				if(result>0){
					return "{\"success\":true}";
				}
			}
			return "{\"success\":false}"; 
		}
		
		/**
		 *  银行导入上传
		 */
		@RequestMapping(value="/uploadMx")
		public ModelAndView uploadMx(MultipartFile imageFile) throws IllegalStateException, IOException{
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
	    	String pictureFile_name =  imageFile.getOriginalFilename();
			String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
			String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
			//上传文件
			String file = realPath+"WEB-INF/file/excel/"+newFileName;
			File uploadPic = new File(file);
			if(!uploadPic.exists()){
				uploadPic.mkdirs();
			}
			//向磁盘写文件
			imageFile.transferTo(uploadPic);
			List listbt = new ArrayList();
			listbt = pzlrService.insertJcsj(file,pd.getString("zbid"),pd.getString("mxid"));
			mv.addObject("listbt", listbt);
			mv.addObject("file", file);
			String pname = pd.getString("pname");
			if(Validate.noNull(listbt) && listbt.size()>0){
				mv.addObject("bool","true");
			}else{
				mv.addObject("bool","false");
			}
			mv.addObject("pname",pname);
	 		mv.setViewName("kjhs/pzxx/pzlr/txl_imp");
			return mv;
		}
		/**
		 * 导入管理费
		 * @author BiMing
		 * @Time 2018年10月11日下午3:56:58
		 */
		@RequestMapping(value="/uploadGlf")
		public ModelAndView uploadGlf(MultipartFile imageFile) throws IllegalStateException, IOException{
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			String pictureFile_name =  imageFile.getOriginalFilename();
			String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
			String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
			//上传文件
			String file = realPath+"WEB-INF/file/excel/"+newFileName;
			File uploadPic = new File(file);
			if(!uploadPic.exists()){
				uploadPic.mkdirs();
			}
			//向磁盘写文件
			imageFile.transferTo(uploadPic);
			String err = pzlrService.insertGlf(file);
			mv.addObject("file", file);
			String pname = pd.getString("pname");
			if(err.length()<0){
				mv.addObject("bool","true");
			}else{
				mv.addObject("bool","false");
			}
			mv.addObject("error",err);
			mv.addObject("pname",pname);
			mv.setViewName("kjhs/pzxx/pzlr/txl_glfimp");
			return mv;
		}
		
		/**
		 * 导出信息Excel
		 * @return
		 */
		@RequestMapping("/expExcel2")
		@ResponseBody
		public Object stryexpXsInfo() {
			PageData pd = this.getPageData();
			String zbid = pd.getString("zbid");
			String mxid = pd.getString("mxid");
			String searchValue = pd.getString("searchJson");
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
			File uploadPic = new File(realfile);
//        	if(!uploadPic.exists()){
//        		uploadPic.mkdirs();
//        	}
			return this.pzlrService.expExcel(realfile, shortfileurl,zbid,mxid);
		}
		
		@RequestMapping(value="/doUpdateOfPrint",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object doUpdateOfPrint() {
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");
			boolean flag = pzlrService.doUpdateOfPrint(guid);
			return "{\"success\":"+flag+"}";
		}
		/**
		 *  获取当前分录 的银行 明细 的  总金额
		 */
		@RequestMapping(value="/getSumMxje",produces="text/html;charset=UTF-8")
		@ResponseBody
		public Object getSumMxje() {
			PageData pd = this.getPageData();
			String mxid = pd.getString("mxid");
			String sumje = pzlrService.getSumMxje(mxid);
			return "{\"sumje\":"+sumje+"}";
		}
		/** 往来单位 跳转到增加页面
		 * 
		 * @return
		 */
		@RequestMapping(value="/goWldwAddPage")
		public ModelAndView goSkdwAddPage(){
			//定义主键guid
			String guid =this.get32UUID();
			ModelAndView mv = this.getModelAndView();
			//传guid到页面
			mv.addObject("guid",guid);
			mv.setViewName("kjhs/pzxx/pzlr/wldw_add");		
			return mv;
		}
}
