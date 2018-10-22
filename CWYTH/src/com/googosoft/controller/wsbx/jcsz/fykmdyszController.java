package com.googosoft.controller.wsbx.jcsz;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.wsbx.jcsz.Cw_fykmdzb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.PzmbService;
import com.googosoft.service.wsbx.FykmdyszService;
import com.googosoft.service.ysgl.xmlx.XmlxService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/fykmdysz")
public class fykmdyszController extends BaseController {
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="fykmdyszService")
	private FykmdyszService fykmdyszService;//单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xmlxService")
	private XmlxService xmlxService;//单例
	
	
	/**
	 * 获取费用科目对应设置列表页面
	 * @return
	 */
	@RequestMapping(value="/goFykmdyszPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String fyfl = pd.getString("fyfl");
		//从数据字典中获取单位性质下拉框内容
		List dwxz = dictService.getDict(Constant.DWXZ);
		mv.setViewName("wsbx/jcsz/fykmdysz/fykmdysz_list");
		mv.addObject("dwxzlist", dwxz);
		mv.addObject("fyfl",fyfl);
		return mv;
	}
	/**
	 * 获取费用科目对应设置列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session){
		String kjzd = CommonUtil.getKjzd(session);
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		String sszt = Constant.getztid(session);
		sqltext.append(" GUID,decode(fyfl,'1','日常报销','2','差旅费','3','预支付','4','公务接待费','') as fyfl,FYMC,(select a.FYMC from CW_FYKMDZB a where a.guid=K.SJFL ) as SJFL,decode(jdfx,'0','贷方','1','借方','') as jdfx,( select KMMC from cw_kjkmszb kj WHERE kj.kmbh=K.KMBH and sszt='"+sszt+"' and kjzd='"+kjzd+"' ) AS KMMC,fybh,KMBH ");
		pageList.setSqlText(sqltext.toString());
		//设置表名，费用科目对（应设）置表？
		pageList.setTableName(" CW_FYKMDZB K ");
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		PageData pd = this.getPageData();
    	String fyfls = pd.getString("fyfls");
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		StringBuffer tjtext = new StringBuffer();
		tjtext.append(" and k.zt='1' ");
	//	tjtext.append("and kjzd = '"+kjzd+"'");
		if(Validate.noNull(fyfls)){
			tjtext.append(" and (K.sjfl='"+fyfls+"' or K.guid='"+fyfls+"')");
		}
		pageList.setStrWhere(tjtext+""); //根据点击左侧树展示右侧列表
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	
	/**
	 * 获取费用科目对应设置列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getTreePageList")
	@ResponseBody
	public Object getTreePageList(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" GUID,decode(fyfl,'1','日常报销','2','差旅费','3','预支付','4','公务接待费','') as fyfl,FYMC,(select a.FYMC from CW_FYKMDZB a where a.guid=K.SJFL ) as SJFL,decode(jdfx,'0','贷方','1','借方','') as jdfx,( select KMMC from cw_kjkmsz WHERE FYFL=K.KMBH ) AS KMBH,fybh ");
		//设置表名
		pageList.setSqlText(sqltext.toString());
		pageList.setTableName(" CW_FYKMDZB K ");
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		PageData pd = this.getPageData();
    	String fyfls = pd.getString("fyfls");
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		StringBuffer tjtext = new StringBuffer();
		if(Validate.noNull(fyfls)){
			tjtext.append(" and K.sjfl='"+fyfls+"' or K.guid='"+fyfls+"'");
			
			
		}
		pageList.setStrWhere(tjtext+""); //根据点击左侧树展示右侧列表
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    //pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"FYKM");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取费用科目对应设置树
	 * @return
	 */
	@RequestMapping(value="/fykmdyszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object fykmdyszTree(){
		
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String fyfl = pd.getString("fyfl");
		if(menu.equals("get-fykmdysz")){
			String loginrybh = LUser.getRybh();
				return fykmdyszService.getPowerDwNode(pd, loginrybh, rootPath,fyfl);
		}else{
			return "";
		}
	}
	/**
	 * 跳转项目分类编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(HttpSession session){
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String sszt = Constant.getztid(session);
		String kjzd=CommonUtil.getKjzd(session);
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = fykmdyszService.getObjectById(pd.getString("guid"),sszt,kjzd);
			mv.addObject("fykmdysz", map);
		}
		
		if("903F854DA5A14D4BA9241B2F6C5DC302".equals(guid)) {
			System.out.println("#############################################  1  ###########################");
			mv.setViewName("wsbx/jcsz/fykmdysz/fykmdysz_edit1");
		}else if("9280E9E047964099A6E5577046023E03".equals(guid)){
			System.out.println("#############################################  2  ###########################");
			mv.setViewName("wsbx/jcsz/fykmdysz/fykmdysz_edit1");
		}else {
			System.out.println("#############################################  3  ###########################");
			mv.setViewName("wsbx/jcsz/fykmdysz/fykmdysz_edit");
		}
		
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 跳转批量修改
	 * @return
	 */
	@RequestMapping(value="/goUpdatePage")
	public ModelAndView goUpdatePage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String guid = this.getPageData().getString("guid");
		String operateType = pd.getString("operateType");
		mv.setViewName("wsbx/jcsz/fykmdysz/plfz");
		mv.addObject("operateType",operateType);
		mv.addObject("guid",guid);
		return mv;
	}
	/**
	 * 获取费用科目对应设置树
	 * @return
	 */
	@RequestMapping(value="/window")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String text_id=pd.getString("text_id");
		mv.setViewName("wsbx/jcsz/fykmdysz/window");
		mv.addObject("controlId", controlId);
		mv.addObject("text_id", "text_id");
		return mv;
	}
	/**
	 * 添加费用科目信息
	 * @param CW_FYKMDZB
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(Cw_fykmdzb fykmdzb,HttpSession session){
		String operateType = this.getPageData().getString("operateType");
		String kjzd = CommonUtil.getKjzd(session);
		int result=0;
		String rybh = LUser.getRybh();
//		String loginId = xsxxService.getLoginIdByRybh(rybh);
		fykmdzb.setCzr(rybh);
		if("C".equals(operateType))//新增
		{  
			
			//生成单位编号
			String guid =this.get32UUID();//生成主键id
			fykmdzb.setGuid(guid);
			fykmdzb.setZt("1");

			result = fykmdyszService.doAdd(fykmdzb);
			
			String czr = LUser.getGuid();
			String guid2 = this.get32UUID();
			Map map1 = new HashMap<>();
			map1.put("guid", guid2);
			map1.put("ywid", fykmdzb.getKmbh());
			map1.put("czid",czr );
			map1.put("czr", czr);
			map1.put("kmlx", "1");
			map1.put("zbid", guid);
			
			xmlxService.doAddjwjl(map1,kjzd);
			
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			
			
			result = fykmdyszService.doUpdate(fykmdzb);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 批量修改科目信息
	 * @param CW_FYKMDZB
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doplSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doplSave(Cw_fykmdzb fykmdzb){
		String operateType = this.getPageData().getString("operateType");
		String guid=this.getPageData().getString("guid");
		int result=0;
			result = fykmdyszService.doplUpdate(fykmdzb,guid);
			if(result>=1)
			{
				return "{success:'true',msg:'批量修改成功！'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		int i = fykmdyszService.doDel(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	
	/**
	 * 导出信息Excel
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
		// 数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("select GUID,decode(fyfl,'1','日常报销','2','差旅费','3','预支付','4','公务接待费','') as fyfl,FYMC,(select a.FYMC from CW_FYKMDZB a where a.guid=K.SJFL ) as SJFL,decode(jdfx,'0','贷方','1','借方','') as jdfx,( select KMMC from cw_kjkmsz WHERE FYFL=K.KMBH ) AS kmmc,kmbh,fybh ");
	
		sqltext.append(" from CW_FYKMDZB k where 1=1 and k.zt='1' ");
		String sjfl = pd.getString("sjfl");
		if(Validate.noNull(sjfl)){
			//点击左侧单位树的where条件
			sqltext.append(" and K.sjfl='"+sjfl+"' or K.guid='"+sjfl+"'  ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and k.guid in ('"+id.replace(",", "','")+"') ");
		}
//		sqltext.append(" order by k.bmh ");
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("fyfl");
		m.setShowname("费用分类");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("fymc");
		m.setShowname("费用名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sjfl");
		m.setShowname("上级费用分类");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jdfx");
		m.setShowname("借贷方向");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("kmbh");
		m.setShowname("科目编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("kmmc");
		m.setShowname("科目名称");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 导出费用科目对应设置Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String s1 = Constant.ZY;
		String guid = pd.getString("id");
		String fyfls = pd.getString("fyfls");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.fykmdyszService.expExcel(realfile, shortfileurl, guid,searchValue,fyfls);
	}
	
	/**
	 * 获取会计科目树
	 * @return
	 */
	@RequestMapping(value="/kjkmWindow")
	public ModelAndView kjkmWindow(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String treesearch = pd.getString("treesearch");
		mv.setViewName("wsbx/jcsz/fykmdysz/kjkmWindow");
		mv.addObject("treesearch", treesearch);
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1", controlId1);
		return mv;
	}
	/**
	 * 获取科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/kmxxList")
	public ModelAndView goDdbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		String controlId2 = pd.getString("controlId2");
		String kmsx = pd.getString("kmsx"); 
		String jb = pd.getString("jb");
		String sfmj = pd.getString("sfmj");
		String id = pd.getString("id");
		String kmnd = pd.getString("kmnd");
		String opre = pd.getString("opre");
		String kmjc = pd.getString("kmjc");
		String treesearch = pd.getString("treesearch");
	
		mv.addObject("treesearch", treesearch);
		mv.addObject("dm", dm);
		mv.addObject("jb", jb);
		mv.addObject("sfmj", sfmj);
		mv.addObject("id", id);

		mv.addObject("kmnd",kmnd);
		mv.addObject("opre",opre);

		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc);
		
		
		mv.addObject("dm", dm);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		mv.addObject("controlId2", controlId2);
		mv.setViewName("wsbx/jcsz/fykmdysz/kmxxList");
		return mv;
	}
	/**
	 * 获取会计科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList1")
	@ResponseBody
	public Object getPageList1(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		PageData pd = this.getPageData();
		 Calendar date = Calendar.getInstance();
		 String jn=String.valueOf(date.get(Calendar.YEAR));//今年
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		//String kmnd = pd.getString("kmnd");
		/*if (kmnd.length()==0 ||kmnd==null) {
			kmnd=jn;
		}*/
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select distinct c.kmnd, c.sfmj, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and sszt = '"+sszt+"' and kjzd='"+kjzd+"' and c.sjfl <> 'root'");
		if(Validate.noNull(dm)){
			tablename.append(" start with c.jb='"+jb+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");

		}
		tablename.append(" ) k ");
//		if(Validate.noNull(dm)){
//			tablename.append(" where c. ");
//		}/
//		if(Validate.noNull(jb)){
//			tablename.append(" where c. ");
//		}
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	   // pageList = pageService.findPageList(pd,pageList);
		pageList = pageService.findWindowList(pd,pageList,"WWW");//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 判断是否有流程
	 */
	@RequestMapping(value = "/doCheck")
	@ResponseBody
	@Transactional 
	public Object doCheck() throws Exception{
		PageData pd = this.getPageData();
		String kmbh = pd.getString("kmbh");
		boolean flag = fykmdyszService.doCheck(kmbh);
		String b = "";
		if (flag) {
			b = "{\"success\":\"true\"}";
		} else {
			b = "{\"success\":\"false\"}";
		}
		return b;
	}
	
}

	

