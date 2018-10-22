package com.googosoft.controller.kjhs;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
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
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.Cw_kmyemxb;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.tzgg.TxlService;
import com.googosoft.service.jzmb.JzmbService;
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
@Controller
@RequestMapping(value="/kmsz")
public class KmszController extends BaseController{
	@Resource(name="kmszService")
	
	private KmszService kmszService; //单例
	
	@Resource(name="dictService")
	private DictService dictService;//数据字典单例
	
	@Resource(name="pageService")
	private PageService pageService;//分页单例
	
	@Resource(name="jzmbService")
	private JzmbService jzmbService;//分页单例
	
	/**
	 * 获取科目弹窗信息列表数据
	 */
	@RequestMapping(value="/kmxx")
	@ResponseBody
	public Object kmxx(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("guid,kmmc,kmbh,kmsx");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName("Cw_kjkmszb k ");//表名
		
		
		if(Validate.noNull(dm)){
			pageList.setStrWhere(pageList.getStrWhere()+" start with k.jb='"+dm+"' connect by prior k.jb=k.sjfl");
			
		}

		
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取科目信息列表的页面
	 * @return
	 *//*
	@RequestMapping(value="/kmxxList")
	public ModelAndView gokmxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		mv.setViewName("kjhs/kmsz/kmxxList");
		mv.addObject("dm", dm);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		return mv;
	}*/
	
	
	
	/**
	 * 获取科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/kmxxList")
	public ModelAndView gokmxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		String kmbh=pd.getString("txt_kmbh");
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
		mv.addObject("kmbh", kmbh);
		mv.addObject("pname", pname);
		mv.addObject("controlId", controlId);
		mv.setViewName("kjhs/kmsz/kmxxList");

		return mv;
	}
	
	/**
	 * 获取科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/window")
	public ModelAndView window(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		String txt_kmbh=pd.getString("txt_kmbh");
		mv.setViewName("kjhs/kmsz/window");
		mv.addObject("treesearch", treesearch);
		mv.addObject("txt_kmbh", txt_kmbh);
		return mv;
	}
	@RequestMapping(value="/fykmdyszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object fykmdyszTree(HttpSession session){
		
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String jump=pd.getString("jump");
		
		if(menu.equals("get-kmsz")){
			String loginrybh = LUser.getRybh();
			if("root".equals(dm)){//
				return kmszService.getPowerDwNode(pd, loginrybh, rootPath,dm,session);
			}else{
				return 	kmszService.getPowerDwNode(pd, loginrybh, rootPath,dm,session);
			}
			
			
		}else {
			return "";
		}
	}
	
	
	/**
	 * 会计科目设置单位树
	 * 
	 */
	@RequestMapping(value="/KmszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object kmszTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		//String dm=pd.getString("jb");

		if("get-kmsz".equals(menu)){			
			if("root".equals(jb)){//		
				return kmszService.getKmszNodezj(pd,"1",rootPath);
			}else{
				
			    return kmszService.getKmszNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 功能科目设置单位树
	 * 
	 */
	@RequestMapping(value="/GnszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object GnszTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-gnkm".equals(menu)){
			if("root".equals(jb)){//
				return kmszService.getgnkmNodezj(pd,"root",rootPath);
			}else{
				return kmszService.getgnkmNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 经济科目设置单位树
	 * 
	 */
	@RequestMapping(value="/JjszTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object JjszTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-jjkm".equals(menu)){
			if("root".equals(jb)){//
				return kmszService.getjjkmNodezj(pd,"",rootPath);
			}else{				
				return kmszService.getjjkmNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	
	
	/**
	 * 获取科目设置页面编辑信息(复制)
	 */
	
	@RequestMapping(value="/goCopyPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");

		ModelAndView mv = this.getModelAndView();
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		if("U".equals(operateType)){

			map = kmszService.getObjectById(pd.getString("dmxh"));
			mv.addObject("dmb", map);
		}else {
		}
		mv.setViewName("kjhs/kmsz/kmfz_edit");
		mv.addObject("operateType",operateType);
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
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/gnkm_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 功能科目设置----
	 * @return
	 */
	@RequestMapping(value="/goGnkmszPage")
	public ModelAndView goGnkmszPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		Calendar date1 = Calendar.getInstance();
	     int jns=Integer.valueOf(date1.get(Calendar.YEAR));//今年
	     int mn=jns+1;
	     int hn=jns+2;
	     int qn=jns-1;
	     int qnn=jns-2;
	     
	     Map map = new HashMap();
	     Map map1 = new HashMap();
	     Map map2= new HashMap();
	     Map map3 = new HashMap();
	     Map map4 = new HashMap();
	     map.put("kmnd", qnn);
	     map1.put("kmnd", qn);
	     map2.put("kmnd", jns);
	     map3.put("kmnd", mn);
	     map4.put("kmnd", hn);
	     ArrayList<Map<Integer,Object>> list = new ArrayList<>();
	     list.add(map);
	     list.add( map1);
	     list.add( map2);
	     list.add( map3);
	     list.add( map4);
	     mv.addObject("nlist",list);
		mv.addObject("dm", pd.getString("dm"));
		mv.addObject("kmjc", pd.getString("kmjc"));
		mv.addObject("kmbh", pd.getString("kmbh"));
		mv.addObject("jn",jns);
		mv.addObject("",jns);
		mv.addObject("kmnd",pd.getString("kmnd"));
		String treesearch = pd.getString("treesearch");
		String kmsx = pd.getString("kmsx");
		String yefx = pd.getString("yefx");
		mv.addObject("kmsx",kmsx);
		mv.addObject("yefx",yefx);
		mv.setViewName("kjhs/kmsz/gnkmsz_list");
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 获取功能科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goGnkmPage")
	public ModelAndView goGnkmPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/Kjkm_list");
		mv.addObject("treesearch", treesearch);
		return mv;
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
		mv.setViewName("kjhs/kmsz/Jjkm_list");
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
	 * 获取经济科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goJjkmWindowPage")
	public ModelAndView goJjkmWindowPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/Jjkm_Windowlist");
		mv.addObject("controlId",pd.getString("controlId"));
		mv.addObject("treesearch", treesearch);
		return mv;
	}
	/**
	 * 编辑会计科目
	 * @return
	 */
	@RequestMapping(value="/goEditKmPage")
	public ModelAndView goEditKmPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = kmszService.getkmszObjectById(pd.getString("id"));
		}
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		mv.setViewName("kjhs/kmsz/km_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 编辑经济科目
	 * @return
	 */
	@RequestMapping(value="/goEditJjPage")
	public ModelAndView goEditJjPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = kmszService.getObjectByIdByKmsz(pd.getString("guid"));
		}
		String kmjc = pd.getString("kmjc");
		String dm = pd.getString("dm");
		String l = pd.getString("l");
		String k = pd.getString("k");
		String type = pd.getString("type");
		String guid = GenAutoKey.get32UUID();
		if("tj".equals(type)){
			mv.addObject("l", l);
			mv.addObject("k", k);
			map.put("l", l);
			map.put("k", k);
			map.put("guid", guid);
			map.put("kmjc", kmjc);
		}else if("xj".equals(type)){
			mv.addObject("k", dm);
			mv.addObject("l", k);
			map.put("l", k);
			map.put("k", dm);
			map.put("guid",guid);
			map.put("kmjc", kmjc);
		}
		mv.addObject("kmjc", kmjc);
		mv.addObject("dm", dm);
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		
		mv.setViewName("kjhs/kmsz/Jjkm_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 查看经济科目
	 * @return
	 */
	@RequestMapping(value="/goLookJjPage")
	public ModelAndView goLookJjPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = kmszService.getObjectByIdByKmsz(pd.getString("guid"));
		}
		String kmjc = pd.getString("kmjc");
		String dm = pd.getString("dm");
		String l = pd.getString("l");
		String k = pd.getString("k");
		String type = pd.getString("type");
		String guid = GenAutoKey.get32UUID();
		if("tj".equals(type)){
			mv.addObject("l", l);
			mv.addObject("k", k);
			map.put("l", l);
			map.put("k", k);
			map.put("guid", guid);
			map.put("kmjc", kmjc);
		}else if("xj".equals(type)){
			mv.addObject("k", dm);
			mv.addObject("l", k);
			map.put("l", k);
			map.put("k", dm);
			map.put("guid",guid);
			map.put("kmjc", kmjc);
		}
		mv.addObject("kmjc", kmjc);
		mv.addObject("dm", dm);
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		
		mv.setViewName("kjhs/kmsz/Jjkm_look");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 编辑功能科目
	 * @return
	 */
	@RequestMapping(value="/goEditGnPage")
	public ModelAndView goEditGnPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = kmszService.getkmszObjectById(pd.getString("id"));
		}
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		mv.setViewName("kjhs/kmsz/Gnkm_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}


	/**
	 * 获取功能科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getKjkmPageList")
	@ResponseBody
	public Object getKjkmPageList() throws Exception{
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("G.KMBH,G.KMMC,G.SSSJKM,G.SSKJKMBH");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		if(Validate.noNull(treesearch)){
			pageList.setStrWhere(pageList.getStrWhere()+" and (g.zl in(select t.kmbh from cw_gnkmb t where  t.kmbh='"+treesearch.substring(1, treesearch.indexOf(")"))+"') )  ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))

		}
//		if(Validate.noNull(treeDm)){
//			pageList.setStrWhere(pageList.getStrWhere()+" AND k.zl in(select dm from GX_SYS_DMB g where g.jb='"+treeDm+"' and zl=(select zl from GX_SYS_DMB where dm='"+treeDm+"'and zl = '00') union all(select zl from GX_SYS_DMB where zl='"+treeDm+"'))");
//		}
		
		if(Validate.noNull(treeDm)){
			pageList.setStrWhere(pageList.getStrWhere()+" AND kmbh like '"+treeDm+"%' or guid='"+treeDm+"'");		}
		pageList.setTableName("CW_GNKMB g");//表名
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
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
		Calendar date = Calendar.getInstance();
		String jn=String.valueOf(date.get(Calendar.YEAR));//今年
		
		String kmnd = Validate.isNullToDefaultString(pd.getString("kmnd"), jn);
		
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,j.kmnd,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		String strWhere = "and sszt ='"+sszt+"'";
		if(Validate.noNull(treeDm)){
			strWhere += " and (j.k='"+treeDm+"' or j.l='"+treeDm+"' or kmbh='"+treeDm+"')";
		}
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			strWhere +=" and j.kmmc='"+CommonUtil.getEndText(treesearch)+"' ";	//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		pageList.setStrWhere(strWhere);
		pageList.setTableName("CW_JJKMB j");//表名
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取会计科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("M.guid,M.DMXH,M.fyfl,M.KMND,M.KMMC,M.ZKMSXDM,M.KMJDM,M.KMLBDM,M.KMFC,M.ZJF,M.YEFX");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
//		if(Validate.noNull(treesearch)){
//			pageList.setStrWhere(pageList.getStrWhere()+" and (m.zl in(select t.fyfl from CW_KJKMSZ t where  t.fyfl='"+treesearch.substring(1, treesearch.indexOf(")"))+"') )  ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
//
//		}
//		if(Validate.noNull(treeDm)){
//			pageList.setStrWhere(pageList.getStrWhere()+" AND k.zl in(select dm from GX_SYS_DMB g where g.jb='"+treeDm+"' and zl=(select zl from GX_SYS_DMB where dm='"+treeDm+"'and zl = '00') union all(select zl from GX_SYS_DMB where zl='"+treeDm+"'))");
//		}
		
		if(Validate.noNull(treeDm)){
			pageList.setStrWhere(pageList.getStrWhere()+" and kmjdm like '"+treeDm+"%' or jb='"+treeDm+"' or aid='"+treeDm+"'");
			
		}
		pageList.setTableName("CW_KJKMSZ m");//表名
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 功能科目设置单位树
	 * 
	 */
	@RequestMapping(value="/GnkmTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object GnkmTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-gnkm".equals(menu)){
			
			if("root".equals(jb)){//
				return kmszService.getgnkmNode(pd,"1",rootPath);
			}else{
			    return kmszService.getgnkmNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 余额初始
	 * @return
	 */
	@RequestMapping(value="/kmyecs")
	public ModelAndView kmyecs(HttpSession session){
		String sszt = Constant.getztid(session);
		ModelAndView mv = this.getModelAndView();
		String kjzd = CommonUtil.getKjzd(session);//获取使用的会计制度
		PageData pd = this.getPageData();
		List list1 = kmszService.getcountkmye();
		if(list1.size()==0) {
			kmszService.drkm(kjzd);
			
		}
		
		 Calendar date = Calendar.getInstance();
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
		
	     String jns = String.valueOf(jn);
		List<Map<String, Object>> kmsxList = dictService.getDict(Constant.ZKMSXDM);// 总科目属性代码
		List<Map<String, Object>> zkmList=null;
		String nd = pd.getString("nd");
		String zkmsx = pd.getString("zkmsx");
		
		
		if(zkmsx==null||"null".equals(zkmsx)) {
			zkmsx="zkmsx";
		}
		
		if(nd==null) {
			String ss =kmszService.getTime();
			nd=ss;
			System.err.println("sssssssssssssss="+ss);
			 zkmList = kmszService.getkm(ss,zkmsx,sszt,kjzd);
		}else {
			 zkmList = kmszService.getkm(nd,zkmsx,sszt,kjzd);
		}
		List ssxt = kmszService.getSsxt();//
		mv.addObject("ssxtlist", ssxt);
		mv.setViewName("kjhs/kmsz/kmyecs_list");
		mv.addObject("nd",nd);
		mv.addObject("zkmsx",zkmsx);
		mv.addObject("jn",jns);
		mv.addObject("nlist",list);
		mv.addObject("zkmList",zkmList);
		mv.addObject("kmsxList", kmsxList);
		
		return mv;
	}
	/**
	 * 科目余额明细余额明细
	 * @return
	 */
	@RequestMapping(value="/kmyemxList",produces = "text/html;charset=UTF-8")
	public ModelAndView kmyemxList(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String kmyeguid = pd.getString("kmyeguid");
		String kmmc = pd.getString("kmmc");
		String id = pd.getString("id");
		String nd = pd.getString("nd");

		Map fzlist = kmszService.getfz(kmyeguid);
		mv.setViewName("kjhs/kmsz/kmyemx_list");
		mv.addObject("kmmc",kmmc);
		mv.addObject("map",fzlist);
		mv.addObject("kmyeguid", kmyeguid);
		mv.addObject("nd",nd);
		mv.addObject("id", id);
		
		return mv;
	}
	/**
	 * 获取科目余额初始页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getkmye")
	@ResponseBody
	public Object getkmye() throws Exception{
		PageData pd = this.getPageData();
		String nd = pd.getString("nd");
		System.err.println("nd="+nd);
		String zkmsx = pd.getString("zkmsx");

		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" decode(nvl(kmzye,''),0,'0.00',(to_char(round(kmzye,2),'fm99999999999990.00')))kmzye, guid,(select sfmj from cw_kjkmszb kj where kj.kmbh=k.kmbh ) as sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh ) as bhkm,kmbh,kmmc,(case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh) when '1' then '贷方' else '借方' end)yefx"
				+ ", (select count(*) from cw_kjkmszb kj where '1' in ((select sfjjflkm from cw_kjkmszb kj1 where kj1.kmbh = k.kmbh), (select xmhs from cw_kjkmszb kj1 where kj1.kmbh = k.kmbh),(select bmhs from cw_kjkmszb kj1 where kj1.kmbh = k.kmbh)))sffz");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键				
		pageList.setTableName("CW_KMYEB K");//表名
		
		
		if(Validate.noNull(zkmsx)){
			pageList.setStrWhere(pageList.getStrWhere()+"and kmbh in(select kmbh from cw_kjkmszb kj where kj.kmsx ='"+zkmsx+"' or kj.kmbh='"+zkmsx+"')" );
			
		}
		if(Validate.noNull(nd)){
			pageList.setStrWhere(pageList.getStrWhere()+"and nd ='"+nd+"' " );
			
		}else{
			pageList.setStrWhere(pageList.getStrWhere()+"and nd ='2017' " );
		}
		pageList = pageService.findPageList(pd, pageList);
		
		
		
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取科目余额明细页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getkmyemxList")
	@ResponseBody
	public Object getkmyemxList() throws Exception{
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String kmyeguid= pd.getString("kmyeguid");
		String nd = pd.getString("nd");
		System.err.println("nddddddd="+nd);
		sqltext.append("  *  ");
	
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键				
		pageList.setTableName(" ( select guid,   (select kmbh from cw_kjkmszb kj where kj.guid = mx.kmbh) as kmbh, kmmc,(select kmmc from cw_jjkmb jj where jj.guid = mx.jjkm) as jjkmmc,  jjkm," +
				"  (select b.xm from Gx_sys_ryb b where b.rybh = mx.grfz) as GRFZS, GRFZ, grdh,  (select dwmc from CW_WLDWB dw where dw.guid = mx.DWFZ) as DWFZS,DWFZ, DWBH, (select xmmc from cw_xmjbxxb xm where xm.guid = mx.xmbh) as xmmc, xmbh, " +
				"  (select mc from gx_sys_dwb dw where dw.dwbh = mx.bmbh) as bmmc, bmbh, " +
				"   decode(nvl(ncye, 0),  0, '0.00', (to_char(round(ncye, 2), 'fm99999999999990.00'))) ncye from CW_KMYEMXB mx where 1 = 1 and kmyebh = '"+kmyeguid+"' and nd = '"+nd+"'  )");//表名
		
//		pageList.setStrWhere(pageList.getStrWhere()+"and kmyebh='"+kmyeguid+"' and nd ='"+nd+"' " );

		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 部门信息弹出窗
	 */
	@RequestMapping(value="/dwpage")
	public ModelAndView dwpage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String a = pd.getString("a");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1", controlId1);
		mv.addObject("a", a);
		mv.setViewName("kjhs/kmsz/dwTree");
		return mv;
	}
	
	/**
	 * 个人信息弹出窗
	 */
	@RequestMapping(value="/grpage")
	public ModelAndView grpage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String a = pd.getString("a");
		mv.addObject("controlId",controlId);
		mv.addObject("controlId1", controlId1);
		mv.addObject("a", a);
		mv.setViewName("kjhs/kmsz/mainGrTree");
		return mv;
	}
	
	/**
	 * 项目信息弹出窗
	 */
	@RequestMapping(value="/getxmbh")
	public ModelAndView getxmbh(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");
		String bmbh = pd.getString("bmbh");
		mv.addObject("bmbh",bmbh);
		mv.addObject("controlId",controlId);//名称
		mv.addObject("controlId1", controlId1);//guid
		mv.setViewName("kjhs/kmsz/xmlist");
		return mv;
	}
	/**
	 * 经济科目弹出窗
	 */
	@RequestMapping(value="/jjkmTree")
	public ModelAndView jjkmTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		String controlId1 = pd.getString("controlId1");

		mv.addObject("controlId",controlId);
		mv.addObject("controlId1",controlId1);
		
		mv.setViewName("kjhs/kmsz/jjkmWindwo");
		return mv;
	}
	/**
	 * 添加科目余额
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/updatekmye",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object updatekmye(HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i=0;
		int c;
		String json = pd.getString("json");		
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
//		Map<String, Object> map = gson.fromJson(json, new TypeToken<Map<String,Object>>(){}.getType());//将json转为list
//		List<Map<String,Object>> list2 = (List<Map<String, Object>>) map.get("list");
		String kmzye2="";
		String sszt = Constant.getztid(session);
		for (int j=0;j<list.size();j++) {
			Map map = (Map) list.get(j);//将list转为map
		
			String guid = (String) map.get("guid");
			
			String  kmzye =  Validate.isNullToDefaultString(map.get("kmzye"), "0.00");
			if(kmzye!=null&&kmzye.length()>0) {
				// kmzye2 = kmzye1.substring(0, kmzye1.indexOf("."));
//				 Float kmzye = Float.parseFloat(kmzye1);
					
				 i=kmszService.updatenczye(guid, kmzye,sszt);		
				 
			}else {
				kmzye2="0";
			}
			

			
			
		}
		if(i==1){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		
//		srysb.setQrzt(qrzt);
//		c=bmszService.doDelete(guid, srysb);
//		List list = bmszService.getSrysSbbm(sbbm);
//		int j = list.size();
//		if(j!=0) {
//			b = "{\"success\":\"false\",\"msg\":\"当前部门已经存在！\"}";
//
//		}else {
//			i= bmszService.doAdd(srysb);
//			if(i==1){
//				b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
//			}else{
//				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
//			}
//		}
		
	    
		
		return b;
	}
	/**
	 * 添加科目余额明细
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/editkmyemx",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object editkmyemx(Cw_kmyemxb kmyemx,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String b = "";
		int i=0;
		String sszt = Constant.getztid(session);
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		String kmyeguid  = pd.getString("kmyebh");
		String kmmc= pd.getString("kmmc");
		String nd = pd.getString("nd");
		kmszService.dodeletekmyemx(kmyeguid,nd);
		for (int j=0;j<list.size();j++) {
			Map map = (Map) list.get(j);//将list转为map
			String guid = (String) map.get("guid");
			//String kmbh = (String) map.get("kmbh");
			String xmbh = (String) map.get("xmmc");
			String jjkm = (String) map.get("jjkm");
			String bmbh = (String) map.get("bmbh");
			String grfz = (String) map.get("grfz");
			String grfzcx = (String) map.get("grfzcx");
			String wldwfzdh = (String) map.get("wldwfzdh");
			String wldwfz = (String) map.get("wldwfz");
			System.err.println("wldwfzdh="+wldwfzdh+"wldwfz+"+wldwfz);
			System.err.println("grfzcx="+grfzcx+"grfz+"+grfz);
			String ncye = (String) map.get("ncye");
			if(guid==null || guid.length()==0) {
				
				guid=this.get32UUID();
			}	
			if(xmbh=="undefined") {
				xmbh="";
			
			}
			if(jjkm=="undefined") {
				jjkm="";
				
			}
			if(bmbh=="undefined") {
				bmbh="";
				
			}

			kmyemx.setGuid(guid);
			kmyemx.setXmbh(xmbh);
			kmyemx.setJjkm(jjkm);
			kmyemx.setBmbh(bmbh);
			kmyemx.setNcye(ncye);
			kmyemx.setKmyebh(kmyeguid);
			kmyemx.setKmmc(kmmc);
			kmyemx.setKmbh(kmyeguid);
			kmyemx.setSszt(sszt);
			kmyemx.setNd(nd);
			kmyemx.setGrfz(grfz);
			kmyemx.setGrfzcx(grfzcx);
			kmyemx.setWldwfzdh(wldwfzdh);
			kmyemx.setWldwfz(wldwfz);
			
			
			i=kmszService.doAddkmyemx(kmyemx);
			
			Map map2 = new HashMap();
			String czr = LUser.getGuid();
			String guid3 = this.get32UUID();
			map2.put("guid", guid3);
			map2.put("ywid", jjkm);
			map2.put("czid",czr);
			map2.put("czr", czr);
			map2.put("kmlx", "2");
			map2.put("zbid", kmyeguid);
			jzmbService.doAddjwjl(map);
			
			
			
			
		}
		//b="success";
		if(i==1){
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	/**
	 * 科目余额导出
	 * @return
	 */
	@RequestMapping(value="/expkmyeExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcelXmzcys(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchValue = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("select rownum as xh,nd,kmsx as zkmsx, decode(nvl(kmzye,''),0,'0.00',(to_char(round(kmzye,2),'fm99999999999990.00')))kmzye, "
				+ "guid, sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh ) as bhkm,kmbh,kmmc,(case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh) when '1' then '贷方' else '借方' end)yefx, "
				+ "sffz from CW_KMYEB K  where 1=1 ");
		//sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		String guid = pd.getString("id");
		String nd = pd.getString("nd");
		String zkmsx = pd.getString("zkmsx");
		if(zkmsx==null||zkmsx.length()==0) {
			zkmsx="";
		}

		if(Validate.noNull(zkmsx)) {
			sqltext.append(" and kmsx='"+zkmsx+"'");
		}
		if(Validate.noNull(nd)){
			sqltext.append(" and nd='"+nd+"' order by kmbh");
		}
	

		List<M_largedata> mlist = new ArrayList<M_largedata>();

		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("kmbh");
		m2.setShowname("科目编号");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("kmmc");
		m3.setShowname("科目名称");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("yefx");
		m4.setShowname("方向");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("kmzye");
		m5.setShowname("年初余额");
		mlist.add(m5);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	@RequestMapping("/expExcels")
	@ResponseBody
	public Object Info() {
		PageData pd = this.getPageData();
		String nd = pd.getString("nd");
		String zkmsx = pd.getString("zkmsx");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.kmszService.expExcel(realfile, shortfileurl,searchValue,nd,zkmsx);
	}
	
	//删除
			@RequestMapping(value="/hhhh")
			@ResponseBody
			public Object delete() {
				String guid = this.getPageData().getString("guid");
				if(kmszService.deleteSrxm(this.getPageData()) > 0) {
					return "{\"success\":true}";
				}else {
					return "{\"success\":false}";
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
				String kmbh = this.getPageData().getString("kmbh");
				if(kmszService.getCountByKmbh(kmbh)>0){
					return MessageBox.show(false,"存在末级科目，无法删除");
				}
				int i = kmszService.doDel(guid);
				if (i > 0) {
					return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
				} else {
					return MessageBox.show(false, MessageBox.FAIL_DELETE);
				}
			}
			/**
			 * 添加单位信息
			 * @param dwb
			 * @return
			 * @throws Exception 
			 */
			@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
			@ResponseBody
			
			public Object doSave(KMSZ kmsz,HttpSession session){
				String operateType = this.getPageData().getString("operateType");
				
				int result=0;
				if("C".equals(operateType))//新增
				{  
					//判断部门号是否重复
					boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
					if(checkbmh==false)
					{
						return  "{success:false,msg:'科目编号不可重复!'}";
					}
					//生成单位编号
					result = kmszService.doAdd(kmsz,session);
					if(result==1){
						return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
					}else{
						return MessageBox.show(false, MessageBox.FAIL_SAVE);
					}
				}
				else if("U".equals(operateType))//修改
				{
					
					if(!kmsz.getKmbh().equals(kmszService.getObjectByIdByKmsz(kmsz.getGuid()).get("KMBH")+""))
					{
						boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
						if(checkbmh==false)
						{
							return "{success:false,msg:'科目编号不可重复！'}";
						}
					}
					result = kmszService.doUpdate(kmsz);
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
			//获取列表数据
			@RequestMapping(value="getXmxxPageData",produces="text/html;charset=UTF-8")
			@ResponseBody
			public Object getXmxxPageData() {
				PageData pd = this.getPageData();
				StringBuffer sqltext = new StringBuffer();//查询字段
				StringBuffer tablename = new StringBuffer();
				String bmbh = pd.getString("bmbh");
				PageList pageList = new PageList();
				sqltext.append(" * ");
				tablename.append(" ( select x.guid,x.bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
						+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
						+ " x.xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
						+ " x.fzr,('('||x.fzr||')'||(select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
						+ "x.xmsx,(select D.MC from gx_sys_dmb d where d.zl='252' and d.dm=x.xmsx)xmsxmc,"
						+ " x.gkbm,('('||x.gkbm||')'||(select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
						+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
						+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
						+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
						+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where bmbh='"+bmbh+"') k");
				pageList.setSqlText(sqltext.toString());
				//设置表名
				pageList.setTableName(tablename.toString());
				//设置表主键名
				pageList.setKeyId("GUID");//主键
				//设置WHERE条件
				pageList.setStrWhere("");
				pageList.setHj1("");//合计
				pageList = pageService.findPageList(pd,pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}
			/**
			 * 导入科目
			 * @return
			 */
			@RequestMapping(value = "/drkm", produces = "text/html;charset=UTF-8")
			@ResponseBody
			public Object drkm() {
				String b="";
				int i =0;
//				i=kmszService.drkm();				
				return b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";				
			}
			/**
			 * 判断
			 */
			@RequestMapping(value = "/doCheck")
			@ResponseBody
			@Transactional 
			public Object doCheck() throws Exception{
				PageData pd = this.getPageData();
				String kmguid = pd.getString("kmguid");
				boolean flag = kmszService.doCheck(kmguid);
				String b = "";
				if (flag) {
					b = "{\"success\":\"true\"}";
				} else {
					b = "{\"success\":\"false\"}";
				}
				return b;
			}

	/**
	 * 科目余额明细导入数据页面
	 * @return
	 */
	@RequestMapping(value = "/getImpPage")
	public ModelAndView getImpPage() {
		ModelAndView mv = this.getModelAndView();
		String mblx = this.getPageData().getString("mblx");
		String kmyebh = this.getPageData().getString("kmyebh");//科目余额编号，科目编号
		String kmmc = this.getPageData().getString("kmmc");//科目名称
		mv.addObject("mblx",mblx);
		mv.addObject("kmyebh",kmyebh);
		mv.addObject("kmmc",kmmc);
		mv.setViewName("kjhs/kmsz/kmyemx_imp/kmyemc_imp");
		return mv;
	}
	/**
	 * 科目余额明细 数据页面导入文件上传
	 */
	@RequestMapping(value = "/kmyemx_upload" )
	public ModelAndView Upload(String sclx, MultipartFile imageFile)
			throws IllegalStateException, IOException {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		// 原始文件名称
		String error = "";
		String pictureFile_name = imageFile.getOriginalFilename();
		if (Validate.isNull(pictureFile_name)) {
			error = "请选择文件上传！";
			mv.addObject("error", error);
		} else {
			// 新文件名称
			String n = pictureFile_name.trim().substring(
					pictureFile_name.indexOf("."));
			if ("xlsx".equals(n)) {
				error = "请将.xlsx文件另存为.xls文件!";
				mv.addObject("error", error);
			}
			if (!".xls".equals(n)) {
				error = "请选择.xls格式的文件!";
				mv.addObject("error", error);
			}
			if (Validate.isNull(error)) {
				String newFileName = this.get32UUID()+ pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
				String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
				// 上传文件
				String file = realPath + "WEB-INF/file/excel/" + newFileName;
				File uploadPic = new File(file);
				if (!uploadPic.exists()) {
					uploadPic.mkdirs();
				}
				// 向磁盘写文件
				imageFile.transferTo(uploadPic);
				
				String kmyebh = this.getPageData().getString("kmyebh");//科目余额编号，科目编号
				String kmmc = this.getPageData().getString("kmmc");//科目名称
				mv.addObject("kmyebh",kmyebh);
				mv.addObject("kmmc",kmmc);
				
				mv.addObject("file", file);
				mv.addObject("sclx", sclx);
				mv.addObject("commonWin", "T");// 弹窗标志
			}
		}
		mv.setViewName("kjhs/kmsz/kmyemx_imp/kmyemc_imp");
		return mv;
	}
	/**
	 *科目余额明细 导入数据确认页面
	 */
	@RequestMapping(value = "/getImpPageQr")
	@ResponseBody
	public ModelAndView getImpPageQr() {
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = kmszService.insertKmyemx(file);
		
		String kmyebh = this.getPageData().getString("kmyebh");//科目余额编号，科目编号
		String kmmc = this.getPageData().getString("kmmc");//科目名称
		mv.addObject("kmyebh",kmyebh);
		mv.addObject("kmmc",kmmc);
		mv.addObject("list", list);
		mv.addObject("file", file);
		mv.setViewName("kjhs/kmsz/kmyemx_imp/kmyemc_impqr");
		return mv;
	}
	/**
	 * 科目余额明细数据导入
	 */
	@RequestMapping(value = "/doInsert", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doInsertJcsjWzb( HttpSession session) throws Exception {
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		String kmyebh = this.getPageData().getString("kmyebh");//科目余额编号，科目编号
		String kmmc = this.getPageData().getString("kmmc");//科目名称
		String sszt = Constant.getztid(session);//应该是所属状态
		String error = kmszService.doInsert(file,sszt,kmyebh,kmmc);
		mv.addObject("error", Validate.isNullToDefault(error, "导入失败！！"));
		return error;
	}

}
