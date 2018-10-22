package com.googosoft.controller.jzmb;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
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
import com.googosoft.pojo.kjhs.Cw_jzmbmx;
import com.googosoft.pojo.kjhs.Cw_pzmbmx;
import com.googosoft.pojo.wsbx.jcsz.Cw_fykmdzb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.jzmb.JzmbService;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/jzmb")
public class jzmbController extends BaseController {

	@Resource(name = "pageService")
	private PageService pageService;// 单例
	@Resource(name = "dictService")
	private DictService dictService;// 单例
	@Resource(name = "jzmbService")
	private JzmbService jzmbService;// 单例

	/**
	 * 获取结转模板列表
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gojzmbListPage")
	public ModelAndView goJzmbListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("jzmb/jzmb_list");
		return mv;

	}

	/**
	 * 获取结转模板列表数据
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append(" * ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");// 主键
		pageList.setStrWhere("");
		StringBuffer tablename = new StringBuffer();
		tablename.append("( SELECT T.GUID, T.MBBH,T.MBMC, (SELECT LXMC FROM CW_PZLXB PZ WHERE PZ.GUID=T.PZLX) AS PZLX,");
		tablename.append("  T.SFBHWJZPZ,  (SELECT MC FROM GX_SYS_DMB DM WHERE DM.DM=T.ZY AND ZL='zy') AS ZY,");
		tablename.append("  T.SSZT,  T.CZR, T.CZRQ,");
		tablename.append("  (SELECT WM_CONCAT(M.KMBH) FROM CW_KJKMSZB M LEFT JOIN CW_JZMBMXB B ON B.ZCKMBH=M.GUID WHERE B.ZJID=T.GUID) AS ZCKM,");
		tablename.append("  (SELECT WM_CONCAT(distinct TO_CHAR( M.KMBH)) FROM CW_KJKMSZB M LEFT JOIN CW_JZMBMXB B ON B.ZRKMBH=M.GUID WHERE B.ZJID=T.GUID) AS ZRKM");
		tablename.append(" FROM CW_JZMBB T WHERE SSZT='" + sszt + "') K ");
		pageList.setTableName( tablename.toString());	
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "",
				pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	/**
	 * 跳转结转信息编辑页面_增加
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goAddPage")
	public ModelAndView goDmbPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List jzmb = jzmbService.getSrxmMapById(pd.getString("guid"));
		List jzmbzy = jzmbService.getjzmbzyMapById(pd.getString("guid"));
		List pzlxlist = jzmbService.getPzlxMapById();
//		String mbbh = GenAutoKey.makeCkbh("cw_jzmbb", "mbbh", "2");
//		mv.addObject("mbbh", mbbh);
		mv.addObject("jzmblist", jzmb);
		mv.addObject("jzmbzylist", jzmbzy);
		mv.addObject("pzlxlist", pzlxlist);//pzlx
		String guid = this.get32UUID();
		// 获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		if (operateType.equals("C")) {

			Map<String, String> map = new HashMap<String, String>();
		} else if (operateType.equals("U") || operateType.equals("L")) {
			Map map = jzmbService.getjzmbMapById(pd.getString("dwbh"));

			mv.addObject("dwb", map);
			// mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.setViewName("jzmb/jzmb_add");
		mv.addObject("operateType", operateType);
		mv.addObject("guid", guid);
		return mv;
	}

	/**
	 * 跳转结转信息编辑页面_修改
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goEditPage")
	public ModelAndView goEditPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List jzmb = jzmbService.getSrxmMapById(pd.getString("guid"));
		List jzmbzy = jzmbService.getjzmbzyMapById(pd.getString("guid"));
		List jzmxList = null;
		Map map = null;
		mv.addObject("jzmblist", jzmb);
		mv.addObject("jzmbzylist", jzmbzy);
		List pzlxlist = jzmbService.getPzlxMapById();
		mv.addObject("pzlxlist", pzlxlist);//pzlx
		// 获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");

		map = jzmbService.getjzmbMapById(pd.getString("guid"));

		String guid = pd.getString("guid");
		jzmxList = jzmbService.getjzmbmxById(guid);

		mv.addObject("jzmxlist", jzmxList);
		mv.addObject("dwb", map);
		mv.setViewName("jzmb/jzmb_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 跳转查看页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goLookPage")
	public ModelAndView goLookPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List jzmb = jzmbService.getSrxmMapById(pd.getString("guid"));
		List jzmbzy = jzmbService.getjzmbzyMapById(pd.getString("guid"));
		List jzmxList = null;
		Map map = null;
		mv.addObject("jzmblist", jzmb);
		mv.addObject("jzmbzylist", jzmbzy);
		// 获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");

		map = jzmbService.getjzmbMapById(pd.getString("guid"));
		List pzlxlist = jzmbService.getPzlxMapById();
		mv.addObject("pzlxlist", pzlxlist);//pzlx
		String guid = pd.getString("guid");
		jzmxList = jzmbService.getjzmbmxById(guid);

		mv.addObject("jzmxlist", jzmxList);
		mv.addObject("dwb", map);
		mv.setViewName("jzmb/jzmb_look");
		mv.addObject("operateType", operateType);
		return mv;
	}


	/**
	 * 保存方法
	 * 
	 * @return
	 */

	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody

	public Object doSave(Cw_fykmdzb fykmdzb, HttpSession session) {
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result = 0;
		String rybh = LUser.getRybh();
		fykmdzb.setCzr(rybh);
		String sszt = Constant.getztid(session);
		if ("C".equals(operateType))// 新增
		{

			// 生成单位编号
			// String guid =this.get32UUID();//生成主键id
			// fykmdzb.setGuid(guid);
			boolean checkbmh = jzmbService.doCheckDwbh(pd.getString("mbbh"));
			if (checkbmh == false) {
				return "{\"success\":\"false\",\"msg\":\"模板编号不可重复!\"}";

			} else {
				result = jzmbService.addJzmb(pd, session);
				if (result == 1) {
					return "{\"success\":\"true\",\"msg\":\"信息保存成功!\",\"operateType\":\"U\"}";
				} else {
					return MessageBox.show(false, MessageBox.FAIL_SAVE);
				}
			}

		} else if ("U".equals(operateType))// 修改
		{
			result = jzmbService.editJzmb(pd);
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
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		PageData pd = this.getPageData();
		int i = jzmbService.doDelete(pd);
		if (i > 0) {
			// return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
			return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
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
		String controlId1 = pd.getString("controlId1");
		String type = pd.getString("type");
		mv.addObject("controlId", controlId);

		mv.setViewName("jzmb/mainKjkmszTree");
		return mv;
	}

	/**
	 * 获取会计科目信息列表的页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "/goKmszPage1")
	public ModelAndView goDdbPage1() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String treesearch = pd.getString("treesearch");
		mv.setViewName("jzmb/gnkm_list1");
		mv.addObject("treesearch", treesearch);
		mv.addObject("dm", dm);
		mv.addObject("jb", jb);
		return mv;
	}

	/**
	 * 导出结转模板信息Excel
	 * 
	 * @return
	 */
	// 收入项目
	@RequestMapping(value = "/expExcel", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(HttpSession session) {
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		// 临时文件名
		String file = System.currentTimeMillis() + "";
		// 文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\") + "WEB-INF\\file\\excel\\" + file
				+ ".xls";
		// 下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		// 查询数据的sql语句
		String searchValue = pd.getString("searchJson");
		
		StringBuffer sqltext = new StringBuffer();
		
//		sqltext.append("SELECT K.*,ROWNUM AS \"_XH\",(SELECT B.MC FROM GX_SYS_DMB B WHERE B.ZL = '" + Constant.ZY
//				+ "' AND B.DM = K.ZY) AS PZZY,(SELECT C.LXMC FROM CW_PZLXB C WHERE C.LXBH = K.PZLX) AS PZLXMC FROM CW_JZMBB K ");
//		sqltext.append("where 1 = 1");// where条件
		
		sqltext.append(
				"SELECT K.*, "
			  + "		ROWNUM AS \"_XH\", "
			  + "	    (SELECT B.MC "
			  + "		 FROM GX_SYS_DMB B "
			  + "		 WHERE B.ZL='" + Constant.ZY + "' " /** 此处若写成 "     WHERE B.ZL=' " + Constant.ZY + " ' "则无法成功显示。' 和 " 之间不能留空格 */
			  + "		   AND B.DM=K.ZY) AS PZZY, "
			  + "		(SELECT WM_CONCAT(M.KMBH) "
			  + "			FROM CW_KJKMSZB M "
			  + "			LEFT JOIN CW_JZMBMXB B ON B.ZCKMBH=M.GUID "
			  + "			WHERE B.ZJID=K.GUID) AS ZCKM, "
			  + "		(SELECT WM_CONCAT(TO_CHAR(M.KMBH)) "
			  + "			FROM CW_KJKMSZB M "
			  + "			LEFT JOIN CW_JZMBMXB B ON B.ZRKMBH=M.GUID "
			  + "			WHERE B.ZJID=K.GUID) AS ZRKM "
			  + "FROM CW_JZMBB K "
			  + "WHERE 1=1");
		
		String guid = pd.getString("id");
		
		/* 以下if语句用于当用户没有选择全部导出时，根据从页面选择的记录的guid为sql查询添加 k.guid in , , , 条件  */
		if(Validate.noNull(guid)){
			sqltext.append(" and K.guid in ('"+guid.replace(",", "','")+"') ");
		}
		
		sqltext.append(" ORDER BY K.MBBH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("mbbh");
		m1.setShowname("模板编号");
		mlist.add(m1);
		
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("mbmc");
		m2.setShowname("模板名称");
		mlist.add(m2);
		
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("pzzy");
		m3.setShowname("凭证摘要");
		mlist.add(m3);
		
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("zckm");
		m4.setShowname("转出科目");
		mlist.add(m4);
		
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("zrkm");
		m5.setShowname("转入科目");
		mlist.add(m5);
		
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
		String s1 = Constant.ZY;
		String guid = pd.getString("id");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.jzmbService.expExcel(realfile, shortfileurl, guid,searchValue,rybh,s1);
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
	 * 添加收入预算明细信息
	 * 
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/addJzmbmx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addJzmbmx(Cw_jzmbmx jzmbmx, HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		String b = "";
		int i;
		int n=0;
		String sszt = Constant.getztid(session);
		String json = pd.getString("json"); // 得到前台的json
		String ajson = json.substring(8, json.length() - 1);// 截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>() {}.getType());// 将json转为list
		int m =  list.size();
		for (i = 0; i < m; i++) {
			Map map = (Map) list.get(i);// 将list转为map
			String guid = this.get32UUID();// 创建主键
			String zckmbh = (String) map.get("zckmid");
			String srkmid = (String) map.get("srkmid");
			String zjid = (String) map.get("szjzid");
			// 将字段放入srysmx
			jzmbmx.setGuid(guid);
			jzmbmx.setZrkmbh(srkmid);
			jzmbmx.setZjid(zjid);
			jzmbmx.setZckmbh(zckmbh);
			jzmbmx.setSszt(sszt);
			n += jzmbService.doAddmx(jzmbmx);
			
			String guid2 = this.get32UUID();
			String guid3 = this.get32UUID();
			String czid = LUser.getGuid();
			Map map1 = new HashMap();
			map1.put("guid", guid2);
			map1.put("ywid", zckmbh);
			map1.put("czid", czid);
			map1.put("czr", czid);
			map1.put("kmlx", "1");
			map1.put("zbid", zjid);
			map1.put("sszt", sszt);

			Map map2 = new HashMap();
			map2.put("guid", guid3);
			map2.put("ywid", srkmid);
			map2.put("czid", czid);
			map2.put("czr", czid);
			map2.put("kmlx", "1");
			map2.put("zbid", zjid);
			map2.put("sszt", sszt);
			jzmbService.doAddjwjl(map1);
			jzmbService.doAddjwjl(map2);
		}
		if(n==m){
		  b = "success";
		}else{
		  b = "false";
		}
		return b;
	}

	/**
	 * 编辑项目支出预算明细信息
	 * 
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/editjzmbmx", produces = "text/html;charset=UTF-8")
	@ResponseBody

	public Object editjzmbmx(Cw_jzmbmx jzmbmx, HttpSession session) throws Exception {
		PageData pd = this.getPageData();
		String b = "";
		int n = 0;
		String mbbh = pd.getString("mbbh");
		String guid = pd.getString("guid");
		String sszt = Constant.getztid(session);
		jzmbService.dodeletejzmbmx(guid);// 删除结转模板明细
		String json = pd.getString("json"); // 得到前台的json
		String ajson = json.substring(8, json.length() - 1);// 截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>() {}.getType());// 将json转为list
		int m =  list.size();
		for (int j = 0; j < m; j++) {
			Map map = (Map) list.get(j);// 将list转为map
			String guid1 = (String) map.get("guid1");
			if (guid1.length() == 0 || guid1 == null) {
				guid1 = this.get32UUID();
			}
			String zckmbh = (String) map.get("zckmid");
			String srkmbh = (String) map.get("srkmid");
			String szjid = (String) map.get("szjzid");

			jzmbmx.setGuid(guid1);
			jzmbmx.setZckmbh(zckmbh);
			jzmbmx.setZjid(szjid);
			jzmbmx.setZrkmbh(srkmbh);
			jzmbmx.setSszt(sszt);

			n += jzmbService.doAddmx(jzmbmx);

		}
		if(n==m){
		    b = "success";
		}else{
			b = "false";
		}
		return b;
	}
}
