package com.googosoft.controller.kjhs;

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
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.KjkmszService;
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;
@Controller
@RequestMapping(value="/kjkmsz")
public class KjkmszController extends BaseController{
	@Resource(name="kjkmszService")
	private KjkmszService kmszService; //单例
	
	@Resource(name="dictService")
	private DictService dictService;//数据字典单例
	
	@Resource(name="pageService")
	private PageService pageService;//分页单例
	
	/**
	 * 获取科目弹窗信息列表数据
	 */
	@RequestMapping(value="/kmxx")
	@ResponseBody
	public Object kmxx(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		String dm=pd.getString("dm");
		sqltext.append("GUID,fyfl,kmmc,kmlbdm");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setTableName("Cw_kjkmsz k ");//表名
		if(Validate.noNull(dm)){
			pageList.setStrWhere(pageList.getStrWhere()+" AND k.kmjdm like '"+dm+"%' or k.guid='"+dm+"'");
			
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
	 */
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
		mv.setViewName("kjhs/kmsz/window");
		mv.addObject("treesearch", treesearch);
		return mv;
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
				return kmszService.getgnkmNodezj(pd,"1",rootPath);
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
				return kmszService.getjjkmNodezj(pd,"1",rootPath);
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
	
//	@RequestMapping(value="/goCopyPage")
//	public ModelAndView goEditPage(){
//		PageData pd = this.getPageData();
//		String operateType = pd.getString("operateType");
//        
//		ModelAndView mv = this.getModelAndView();
//		@SuppressWarnings("rawtypes")
//		Map map = new HashMap();
//		if("U".equals(operateType)){
//
//			map = kmszService.getObjectById(pd.getString("dmxh"));
//			mv.addObject("dmb", map);
//		}else {
//		}
//		mv.setViewName("kjhs/kmsz/kmfz_edit");
//		mv.addObject("operateType",operateType);
//		return mv;
//	}
	/**
	 * 编辑
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage1(){
		PageData pd = this.getPageData();
		String kmsx = pd.getString("kmsx");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String kmjc = pd.getString("kmjc");
		String sfmj = pd.getString("sfmj");
		String mkbh = pd.getString("mkbh");
		String kjzd = pd.getString("kjzd");
		ModelAndView mv = this.getModelAndView();
		String flag = pd.getString("flag");
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		mv.addObject("kmsxList",dictService.getDict("kmsx"));
		mv.addObject("hslbList",dictService.getDict("hslb"));
		if(operateType.equals("C")){
			String kmbh = pd.getString("kmbh");
			String yefx = pd.getString("yefx");
			String scbh = kmszService.scbh(kmbh,kjzd);
			Map<String,String> map = new HashMap<String,String>();
			mv.addObject("scbh",scbh);
			mv.addObject("yefx",yefx);
		}else if(operateType.equals("U")||operateType.equals("L")){
			String sfkjzd=pd.getString("sfkjzd");
			Map<?, ?>  map = kmszService.getObjectById(pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("sfkjzd", sfkjzd);
			mv.addObject("sfmj",pd.getString("sfmj"));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.addObject("dm", dm);
		mv.addObject("flag", flag);
		mv.addObject("jb", jb);
		mv.addObject("aType", "tj");
		mv.addObject("sfmj",sfmj);
		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc);
		mv.addObject("mkbh",mkbh);
		mv.addObject("kjzd",kjzd);
		mv.setViewName("kjhs/kmsz/km_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 查看
	 * @return
	 */
	@RequestMapping(value="/goLookPage")
	public ModelAndView goLookPage(){
		PageData pd = this.getPageData();
		String kmsx = pd.getString("kmsx");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String kmjc = pd.getString("kmjc");
		String sfmj = pd.getString("sfmj");
		ModelAndView mv = this.getModelAndView();
		String flag = pd.getString("flag");
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		mv.addObject("kmsxList",dictService.getDict("kmsx"));
		mv.addObject("hslbList",dictService.getDict("hslb"));
		if(operateType.equals("C")){
			Map<String,String> map = new HashMap<String,String>();
//			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			
		}else if(operateType.equals("U")||operateType.equals("L")){
			String sfkjzd=pd.getString("sfkjzd");
			Map<?, ?>  map = kmszService.getObjectById(pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("sfkjzd", sfkjzd);
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.addObject("dm", dm);
		mv.addObject("flag", flag);
		mv.addObject("jb", jb);
		mv.addObject("aType", "tj");
		mv.addObject("sfmj",sfmj);
		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc);
		mv.setViewName("kjhs/kmsz/km_look");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 编辑2
	 * @return
	 */
	@RequestMapping(value="/goEditPage2")
	public ModelAndView goEditPage2(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		String kmbh = pd.getString("kmbh");
		String kmzye = pd.getString("kmzye");
		String bdje = pd.getString("bdje");
		String kmsx = pd.getString("kmsx");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String guid = pd.getString("id");
		String guid1 = pd.getString("guid");
		String kmjc = pd.getString("kmjc");
		String kjzd = pd.getString("kjzd");
		String sfmjc = pd.getString("sfmjc");
		int kmjc1=0;
		if("root".equals(kmjc)) {
			kmjc1=1;
		}else {
			kmjc1 = Integer.parseInt(kmjc)+1;
		}
		String sfmjsj = pd.getString("sfmjsj");
		String sfmjxj = pd.getString("sfmjxj");
		String flag = pd.getString("flag");
		mv.addObject("kmsxList",dictService.getDict("kmsx"));
		mv.addObject("hslbList",dictService.getDict("hslb"));
		if(operateType.equals("C")){
			String scbh = kmszService.scxjbh(kmbh, kjzd);
			mv.addObject("scbh",scbh);
			Map<String,String> map = new HashMap<String,String>();
			String yefx = pd.getString("yefx");
			mv.addObject("yefx",yefx);	
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = kmszService.getObjectById(pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
//			if(operateType.equals("L")){
//				String dwxzmap = dictService.getMcByDm(Constant.DWXZ,map.get("DWXZ")+"");
//				mv.addObject("dwxzmap", dwxzmap);
//				String syslxmap = dictService.getMcByDm(Constant.SYSLX,map.get("SYSLX")+"");
//				mv.addObject("syslxmap", syslxmap);
//				String syslbmap = dictService.getMcByDm(Constant.SYSLB,map.get("SYSLB")+"");
//				mv.addObject("syslbmap", syslbmap);
//				String sysjbmap = dictService.getMcByDm(Constant.SYSJB,map.get("SYSJB")+"");
//				mv.addObject("sysjbmap", sysjbmap);
//				String sysgsmap = dictService.getMcByDm(Constant.SYSGS,map.get("SYSGS")+"");
//				mv.addObject("sysgsmap", sysgsmap);
//				String ssxkmap = dictService.getMcByDm(Constant.SSXK,map.get("SSXK")+"");
//				mv.addObject("ssxkmap", ssxkmap);
//				String dwbbmap = dictService.getMcByDm(Constant.DWBB,map.get("DWBB")+"");
//				mv.addObject("dwbbmap", dwbbmap);
//			}
			mv.addObject("dwb", map);
		
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.addObject("dm", dm);
		mv.addObject("flag", flag);
		mv.addObject("jb", jb);
		mv.addObject("aType", "xj");
		mv.addObject("sfmjsj",sfmjsj);
		mv.addObject("sfmjxj",sfmjxj);
		mv.addObject("guid1",guid1);
		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc1);
		mv.addObject("kmzye",kmzye);
		mv.addObject("bdje",bdje);
		mv.addObject("kmbh",kmbh);
		mv.addObject("sfmjc",sfmjc);
		mv.addObject("kjzd",kjzd);
		mv.addObject("mkbh",pd.getString("mkbh"));
		mv.setViewName("kjhs/kmsz/km_edit");
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
	     
	    List zdlist = kmszService.getzd();
	      
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
		mv.setViewName("kjhs/kmsz/gnkm_list");
		mv.addObject("treesearch", treesearch);
		System.out.println("yefx-------"+yefx);
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
	 * 获取会计科目信息列表的页面
	 * @return
	 */
	@RequestMapping(value="/goKmszPage1")
	public ModelAndView goDdbPage1(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		System.err.println("__________"+dm+"_____"+jb);
		String treesearch = pd.getString("treesearch");
		mv.setViewName("kjhs/kmsz/gnkm_list1");
		mv.addObject("treesearch", treesearch);
		mv.addObject("dm", dm);
		mv.addObject("jb", jb);
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
		mv.setViewName("kjhs/kmsz/gnkmsz_list");
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
	public Object getJjkmPageList() throws Exception{
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		// 设置WHERE条件
		String strWhere = "";
		if(Validate.noNull(treeDm)){
			strWhere += " and j.k='"+treeDm+"' or j.l='"+treeDm+"' or kmbh='"+treeDm+"'";
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
	public Object getPageList( HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		 Calendar date = Calendar.getInstance();
		 String jn=String.valueOf(date.get(Calendar.YEAR));//今年
		 String kmnd = pd.getString("kmnd");
		 String kjzd = Validate.isNullToDefaultString(CommonUtil.getKjzd(session), "all");
		 String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select distinct to_char(kmnd,'yyyy') as kmnd,KJZD, c.sfkjzd,c.sfmj,c.sjfl, c.jb, c.bz,c.guid, c.kmbh, c.kmmc,  c.kmsx,  (select d.mc from gx_sys_dmb d where d.zl='kmsx' and d.dm=c.kmsx)as kmsxmc, c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where 1=1 and sjfl !='root' and sszt='"+sszt+"' ");
		if("all".equals(kjzd)) {
			
		}else {
			tablename.append("and kjzd='"+kjzd+"'");
		}
		if(Validate.noNull(dm)){
			tablename.append(" start with c.jb='"+jb+"' and sszt='"+sszt+"' and kjzd='"+kjzd+"' connect by prior jb=sjfl and sjfl!='root' ");
		}
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			tablename.append(" and c.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		tablename.append(" ) k ");
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
	 * 添加单位信息
	 * @param dwb
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(KMSZ kmsz,HttpSession session){
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("guid");
		String zjf = pd.getString("zjf");
		String kmbh = pd.getString("kmbh");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String kmmc = pd.getString("kmmc");
		String mkbh = pd.getString("mkbh");
		String kjzd = pd.getString("kjzd");
		boolean result=false;
		if("C".equals(operateType))//新增
		{  
		
			boolean checkkmbh=kmszService.doCheckKmbh1(kmbh,kjzd,sszt);
			if(checkkmbh==false)
			{
				return  "{success:false,msg:'科目编号不可重复!'}";
			}
			//判断科目名称是否重复
//			boolean checkkmmc=kmszService.doCheckmmc1(kmmc,kjzd);
//			if(checkkmmc==false)
//			{
//				return  "{success:false,msg:'科目名称不可重复!'}";
//			}
			//判断助记符是否重复
			boolean checkzjf=kmszService.doCheckkjZjf2(zjf,kjzd);
			if(checkzjf==false)
			{
				return  "{success:false,msg:'助记符不可重复!'}";
			}
	
			result = kmszService.doAdd(pd,dm,session);
			if(result){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
			
		}
		else if("U".equals(operateType))//修改
		{
			
			/*if(!kmsz.getKmbh().equals(kmszService.getObjectByIdByKmsz(kmsz.getGuid()).get("KMBH")+""))
			{
				boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
				if(checkbmh==false)
				{
					return "{success:false,msg:'科目编号不可重复！'}";
				}
			}*/
		
			//判断科目名称是否重复
			boolean checkkmmc=kmszService.doCheckmmc2(kmmc,kjzd,dwbh);
			if(checkkmmc==false && 1==3)
			{
				return  "{success:false,msg:'科目名称不可重复!'}";
			}
			boolean checkzjf=kmszService.doCheckZjf3(zjf,dwbh,kjzd);// 修改
			if(checkzjf==false)
			{
				return  "{success:false,msg:'助记符不可重复!'}";
			}
			result = kmszService.doUpdate(pd,dwbh,sszt);
			System.err.println("_____________11_"+result);
			if(result)
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
	@RequestMapping(value="/doSavexj",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavexj(KMSZ kmsz,HttpSession session){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String sszt = Constant.getztid(session);
		String dwbh = pd.getString("guid");
		String zjf = pd.getString("zjf");
		String kmmc = pd.getString("kmmc");
		String zje = pd.getString("zje");
		String kmbh = pd.getString("kmbh");
		String kjzd = CommonUtil.getKjzd(session);
		boolean result=false;
		if("C".equals(operateType))//新增
		{  
			//判断部门号是否重复
			boolean checkbmh=kmszService.doCheckKmbh1(kmbh,kjzd,sszt);
			if(checkbmh==false)
			{
				return  "{success:false,msg:'科目编号不可重复!'}";
			}
			//判断科目名称是否重复
//			boolean checkkmmc=kmszService.doCheckmmc1(kmmc,kjzd);
//			if(checkkmmc==false)
//			{
//				return  "{success:false,msg:'科目名称不可重复!'}";
//			}
			//判断助记符是否重复
			boolean checkzjf=kmszService.doCheckkjZjf2(zjf,kjzd);
			if(checkzjf==false)
			{
				return  "{success:false,msg:'助记符不可重复!'}";
			}
			//生成单位编号
			result = kmszService.doAddxj(pd,session);
			if(result){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			
			/*if(!kmsz.getKmbh().equals(kmszService.getObjectByIdByKmsz(kmsz.getGuid()).get("KMBH")+""))
			{
				boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
				if(checkbmh==false)
				{
					return "{success:false,msg:'科目编号不可重复！'}";
				}
			}*/
			//result = kmszService.doUpdate(pd,dwbh);
			System.err.println("_____________11_"+result);
			if(result)
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
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(HttpSession session) {
		boolean i = false;
		PageData pd = this.getPageData();
		String sjfl = pd.getString("sjfl");
		String dwbh = pd.getString("dwbh");
		String kmbh = pd.getString("kmbh");
		String kmmc = pd.getString("kmmc");
		String kjzd = pd.getString("kjzd");
		String mkbh = pd.getString("mkbh");
		String sszt = Constant.getztid(session);
				
		if(kmszService.getCountByGuid(dwbh)>0){
			
			return MessageBox.show(false,"存在末级科目，无法删除");
		}
		 i = kmszService.doDelete(dwbh,sjfl,kmbh,kmmc,kjzd,mkbh,sszt);
		if (i) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	
	//---------------------------功能科目设置--------------------------------------
	@RequestMapping(value="/getGnkmszPageList")
	@ResponseBody
	public Object getGnkmszPageList(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		 Calendar date = Calendar.getInstance();
		 String jn=String.valueOf(date.get(Calendar.YEAR));//今年
		 String kmnd = pd.getString("kmnd");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String kmjc = pd.getString("kmjc");
		String bhww = pd.getString("bhww");
		String kmmc = pd.getString("kmmc");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select c.guid, c.kmbh,c.kmmc, c.kmsx,(select d.mc from gx_sys_dmb d where d.zl = 'kmsx' and d.dm = c.kmsx) as kmsxmc,"
				+ " c.zjf, c.yefx, (case c.yefx when '1' then '贷方' else '借方' end) as yefxmc, c.kmjc,c.qyf, (case c.qyf when '1' then '是' else '否' end) as qyfmc,"
				+ " c.sssjkm, c.jb,c.czr, to_char(czrq,'yyyy') as czrq from Cw_Gnkmb c where 1 = 1 and sszt='"+sszt+"'");
		if(Validate.noNull(kmnd)){
			tablename.append("and kmnd='"+kmnd+"'");
		}else {
			tablename.append("and kmnd='"+jn+"'");
		}
		if(Validate.noNull(bhww)){
			tablename.append(" and c.kmbh in(select kmbh from Cw_Gnkmb s start with s.kmbh='"+bhww+"' connect by prior  s.kmbh=s.sssjkm) ");
		}
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			tablename.append(" and c.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		tablename.append(" ) k ");
//		if(Validate.noNull(dm)){
//			tablename.append(" where c. ");
//		}
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
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	
	/**
	 * 同级编辑页面
	 * @return
	 */
	@RequestMapping(value="/goGnkmszEditPage")
	public ModelAndView goGnkmszEditPage(){
		PageData pd = this.getPageData();
		String kmsx = pd.getString("kmsx");
		String dm = pd.getString("dm");
		String xj = pd.getString("aType");
		String kmbh = pd.getString("kmbh");
		String kmjc = pd.getString("kmjc");
		String sfmj = pd.getString("sfmj");
		ModelAndView mv = this.getModelAndView();
		String flag = pd.getString("flag");
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		mv.addObject("kmsxList",dictService.getDict("kmsx"));
		mv.addObject("hslbList",dictService.getDict("hslb"));
		if(operateType.equals("C")){
			Map<String,String> map = new HashMap<String,String>();
			String yefx = pd.getString("yefx");
			mv.addObject("yefx",yefx);
			int km = Integer.parseInt(kmjc)+1;
			kmjc = String.valueOf(km);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = kmszService.getinfoById(pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.addObject("dm", dm);
		mv.addObject("flag", flag);
		mv.addObject("kmbh", kmbh);
//		mv.addObject("aType", "tj");
		mv.addObject("aType", xj);
		mv.addObject("sfmj",sfmj);
		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc);
		System.out.println("kmsx-------"+kmsx);
		mv.setViewName("kjhs/kmsz/gnkmsztj_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 查看页面
	 * @return
	 */
	@RequestMapping(value="/goGnkmszLookPage")
	public ModelAndView goGnkmszLookPage(){
		PageData pd = this.getPageData();
		String kmsx = pd.getString("kmsx");
		String dm = pd.getString("dm");
		String xj = pd.getString("aType");
		String kmbh = pd.getString("kmbh");
		String kmjc = pd.getString("kmjc");
		String sfmj = pd.getString("sfmj");
		ModelAndView mv = this.getModelAndView();
		String flag = pd.getString("flag");
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		mv.addObject("kmsxList",dictService.getDict("kmsx"));
		mv.addObject("hslbList",dictService.getDict("hslb"));
		if(operateType.equals("C")){
			Map<String,String> map = new HashMap<String,String>();
//			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = kmszService.getinfoById(pd.getString("dwbh"));
			mv.addObject("xmflList",dictService.getDict(Constant.XMFL));
			mv.addObject("dwb", map);
			mv.addObject("guid", pd.getString("dwbh"));
		}
		mv.addObject("dm", dm);
		mv.addObject("flag", flag);
		mv.addObject("kmbh", kmbh);
//		mv.addObject("aType", "tj");
		mv.addObject("aType", xj);
		mv.addObject("sfmj",sfmj);
		mv.addObject("kmsx",kmsx);
		mv.addObject("kmjc",kmjc);
		mv.setViewName("kjhs/kmsz/gnkmsz_look");
		mv.addObject("operateType",operateType);
		return mv;
	}
	@RequestMapping(value="/doSavegnkmsz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavegnkmsz(KMSZ kmsz,HttpSession session){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("guid");
		String zjf = pd.getString("zjf");
		String kmbh = pd.getString("kmbh");
		String dm = pd.getString("dm");
		String jb = pd.getString("jb");
		String kmmc = pd.getString("kmmc");
		int result=0;
		if("C".equals(operateType))//新增
		{  
			boolean checkkmbh=kmszService.doCheckKmbh(kmbh);
			if(checkkmbh==false)
			{
				return  "{success:false,msg:'科目编号不可重复!'}";
			}
			//判断科目名称是否重复
			boolean checkkmmc=kmszService.doCheckmmc(kmmc);
			if(checkkmmc==false)
			{
				return  "{success:false,msg:'科目名称不可重复!'}";
			}
			//判断助记符是否重复
			boolean checkzjf=kmszService.doCheckZjf(zjf);
			if(checkzjf==false)
			{
				return  "{success:false,msg:'助记符不可重复!'}";
			}
			//生成单位编号
			result = kmszService.doAddgnkmsz(pd,dm,session);
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
		//	result = kmszService.doUpdate(pd,dwbh);
			boolean checkkmbh=kmszService.doCheckKmbh2(kmbh,dwbh);
			if(checkkmbh==false)
			{
				return  "{success:false,msg:'科目编号不可重复!'}";
			}
			//判断科目名称是否重复
			boolean checkkmmc=kmszService.doCheckmmc2(kmmc,dwbh);
			if(checkkmmc==false)
			{
				return  "{success:false,msg:'科目名称不可重复!'}";
			}
			//判断助记符是否重复
			boolean checkzjf=kmszService.doCheckZjf2(zjf,dwbh);
			if(checkzjf==false)
			{
				return  "{success:false,msg:'助记符不可重复!'}";
			}
			result = kmszService.doUpdategnkm(pd,dwbh);
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

	@RequestMapping(value="/doSavexjkmsz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSavexjkmsz(KMSZ kmsz,HttpSession session){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		String dwbh = pd.getString("guid");
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
			result = kmszService.doAddxjkmsz(pd,session);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			
			/*if(!kmsz.getKmbh().equals(kmszService.getObjectByIdByKmsz(kmsz.getGuid()).get("KMBH")+""))
			{
				boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
				if(checkbmh==false)
				{
					return "{success:false,msg:'科目编号不可重复！'}";
				}
			}*/
			//result = kmszService.doUpdate(pd,dwbh);
			System.err.println("_____________11_"+result);
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
	@RequestMapping(value = "/doDeletekmsz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDeletekmsz() {
		int i = 0;
		String jb = this.getPageData().getString("jb");
		String kmbh = this.getPageData().getString("kmbh");
		String dwbh = this.getPageData().getString("dwbh");
		
		/*if(kmszService.getCountByGuid(dwbh)>0){
			
			return MessageBox.show(false,"存在末级科目，无法删除");
		}*/
		 i = kmszService.doDeletekmsz(dwbh);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
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
		String type = pd.getString("type");
		if(type.equals("3")){
			type="3";
		}else{
			type="1";
		}
		boolean flag = kmszService.doCheck(kmguid,type);
		String b = "";
		if (flag) {
			b = "{\"success\":\"true\"}";
		} else {
			b = "{\"success\":\"false\"}";
		}
		return b;
	}
	
	/**
	 * 判断余额是否为0
	 */
	@RequestMapping(value = "/docheckye")
	@ResponseBody
	public Object docheckye(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String kmnd = pd.getString("kmnd");
		String kmbh = pd.getString("kmbh");
		String flag = kmszService.doCheckye(guid,kmnd,sszt,kjzd);
		String  bdje = kmszService.getbdje(sszt,kmbh);
		
		
		
		return "{\"flag\":\""+flag+"\",\"bdje\":\""+bdje+"\"}";
		//return "{\"success\":\"false\"}";
	}
}

