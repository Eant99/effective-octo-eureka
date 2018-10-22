package com.googosoft.controller.kjhs.szjz;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import java.math.BigDecimal;
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
import com.googosoft.pojo.kjhs.CW_PZLXB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.pzxx.PzlrService;
import com.googosoft.service.kjhs.szjz.SzjzService;
import com.googosoft.service.ysgl.xmlx.XmlxService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value = "/szjz")
public class szjzController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="szjzService")
	private SzjzService szjzService;//单例
	@Resource(name="pzlrService")
	private PzlrService pzlrService;//单例
	@Resource(name="xmlxService")
	private XmlxService xmlxService;//单例
	
	
	/**
	 * 列表页面
	 */
	@RequestMapping(value="/goszjzlist")
	public ModelAndView goMainKjkmszTree(){
		ModelAndView mv = this.getModelAndView();
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
		mv.addObject("nlist",list);
	    mv.setViewName("kjhs/szjz/szjz_list");
		return mv;
	}
	/**
	 * 获取结转模板列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {	
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" * ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName(" (select t.guid,t.mbbh,t.mbmc,(select mc from gx_sys_dmb dm where dm.dm=t.zy and zl='zy') as pzzy,"
				+ "(select wm_concat(to_char(m.kmbh)) from cw_kjkmszb m left join CW_JZMBMXB b on b.zckmbh = m.guid where b.zjid = t.guid) AS ZCKM,"
				+ "(select wm_concat(distinct TO_CHAR(M.KMBH)) from cw_kjkmszb m left join CW_JZMBMXB b on b.zrkmbh = m.guid where b.zjid = t.guid) AS ZRKM,"
				+ "t.sfbhwjzpz,t.sszt,t.czr,t.czrq,t.pzlxbh,t.pzlxmc from cw_jzmbb t where sszt='"+sszt+"') K" );//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取结转模板列表数据
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping(value = "/doszjz")
	@ResponseBody
	public Object doszjz(HttpSession session) throws Exception {	
		String sszt = Constant.getztid(session);
		String kjzd = CommonUtil.getKjzd(session);
		PageData pd = this.getPageData();
		int length =Integer.parseInt(pd.getString("length")) ;//一共有几条模板
		if(length==0) {
			return length;
		}
		//从结转模板表中查找数据
		List<Map<String, Object>> jzlist = szjzService.getszjz(sszt);
		String dlr = LUser.getGuid();
		int t = 0;
		if(jzlist.size()>0) {//结转模板有数据
			for(int i=0;i<jzlist.size();i++) {
				Map jzmap = jzlist.get(i);//结转模板第1条数据
				String guid = (String) jzmap.get("guid");//结转模板第一条数据的guid  
				String sszt1 = (String) jzmap.get("sszt"); // guid sszt1 得到 zrlist
				String pzlx = (String) jzmap.get("pzlxbh");//结转模板第一条数据的lxbh,凭证录入主表中的
				String zy = (String) jzmap.get("zy");//结转模板第一条数据的摘要，凭证录入明细表中的zy
				
				Map map3 =  szjzService.queryQmjz(sszt);//获得期末结转表中的数据    133-145 map3 生成 pzrq
				//凭证录入主表数据
		        String year = (String) map3.get("ztnd");
		        String month = String.valueOf(map3.get("kjqj")) ;
		        Calendar now = Calendar.getInstance(); 
		        int day = now.get(Calendar.DAY_OF_MONTH);
		        String pzrq = year+"-"+month+"-"+day;
		        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM"); 
		        now.setTime(sdf.parse(pzrq));
		        int ts = now.getActualMaximum(Calendar.DAY_OF_MONTH);
		        if(day>ts) {
		        	pzrq = year+"-"+month+"-"+ts;
		        }
		        String zbguid = this.get32UUID();//凭证录入主表guid
				String czr = LUser.getGuid();//操作人
		        String pzz = (String) jzmap.get("pzlxbh"); //凭证类型编号 01 02
		        String pzlxmc = (String) jzmap.get("pzlxmc"); //凭证类型名称  记 转 付 
		       //获取自动生成的凭证编号
				String pzbh;
				String sfbsc =  pzlrService.CheckSfbsc()  ;//是否补充删除凭证号  1是补充  0 是自动追加
				if(sfbsc.equals("1")){
					pzbh = GenAutoKey.makePzbh("CW_PZLRZB","PZBH","4",sszt,pzlx);
				}else{  //（规则为已存在的最大凭证编号加一）
					pzbh= pzlrService.getAutoPzbh(year,month, 4,sszt,pzlx);
				}
		        Map zdmap = szjzService.getzdlrpz(); // 是否自动 保存凭证
				Map map4 = new HashMap<>(); // 存添加到 cw_pzlrzb 中的信息
				map4.put("guid", zbguid);
				map4.put("pzbh", pzbh);
				map4.put("pzrq", pzrq);
				map4.put("zdr", czr);
				if("0".equals(zdmap.get("PZLRPZ"))) {
					map4.put("pzzt","00");
				}else {
					map4.put("pzzt","02");
				}
				map4.put("pzz",pzz);
				map4.put("pzlxmc", pzlxmc);
				map4.put("kjqj", map3.get("kjqj"));
				map4.put("pzly", 3);
				map4.put("sszt", sszt);
				map4.put("pznd", year);
				map4.put("kjzd", kjzd);
				t = szjzService.doAddpzlrzb(map4);//凭证录入主表添加数据
				
				//通过 cw_jzmbb “guid” sszt1 获得cw_jzmbmxb “zjid” 中的 转入科目   相关信息
				//这个地方可以添加条件  已结转 的  不查询
				List<Map<String, Object>> zrlist = szjzService.getZrkmByid(guid,sszt1);
				BigDecimal jfjehj = new BigDecimal("0.00");  //借方金额合计
				BigDecimal dfjehj = new BigDecimal("0.00");  //贷方金额合计
				if(zrlist.size()>0) {
					for(int a=0;a<zrlist.size();a++) {					
						Map zrmap = zrlist.get(a);//得到第一个...转入科目对应的转出科目
						String zjid = (String) zrmap.get("zjid");//==cw_jzmbb guid
					    String kmbh = (String) zrmap.get("kmbh");//转入科目编号 
					    String zrkmbh = (String) zrmap.get("zrkmbh");//转入科目编号 guid
					    String zryefx = (String) zrmap.get("yefx");//转入科目余额方向
					    //zclist 转出科目 科目编号kmbh 借贷方向yefx 借方金额jfje 贷方金额dfje  科目总金额kmzye
					    List<Map<String, Object>> zclist = szjzService.getJeByKmbh(zjid,year,pzrq,sszt,kjzd,zrkmbh);
					    //System.out.println(zclist);
					    //本凭证模板明细信息  zrkmbh, zckmbh 可能多行
					    List<Map<String, Object>> mbmxlist = szjzService.getpzmbmxByguid(guid);
					    BigDecimal dfze = new BigDecimal("0.00");//转出的贷方总额
					    BigDecimal jfze = new BigDecimal("0.00");//转出的借方总额
					    int size = zclist.size();
					    for(int b=0;b<zclist.size();b++) {	
					    	 BigDecimal pzmxjfje = new BigDecimal("0.00");//凭证录入明细表中借方金额
					    	 BigDecimal pzmxdfje = new BigDecimal("0.00");//凭证录入明细表中贷方金额
					    	 Map zcmap = zclist.get(b);//得到第一条转出科目的信息
					    	 String mxguid = this.get32UUID();//凭证录入明细表guid
					    	 String zckmbh = (String) zcmap.get("kmbh");//得到第一条数据的转出科目编号
					    	 String yefx = (String) zcmap.get("yefx");//得到第一条数据的余额反向
					    	 String zcjfje = zcmap.get("jfje")+"" ;//得到第一条数据的借方金额
					    	 String zcdfje = zcmap.get("dfje")+"";//得到第一条数据的贷方金额
					    	 String zckmye = zcmap.get("kmzye")+"" ;//得到第一条数据的科目余额					    	
					    	 BigDecimal dfje=new BigDecimal(zcdfje);
					    	 BigDecimal jfje=new BigDecimal(zcjfje);
					    	 BigDecimal kmye=new BigDecimal(zckmye);
					    	 //System.out.println("dfje="+dfje+"jfje="+jfje+"kmye="+kmye);
					    	 //同方向 加 反方向 减    转出 放在 相反方向  转入 放在 相同方向
					    	 if("1".equals(yefx)) {//转出科目是贷方    放在明细的借方上
						    	    //我要转出的钱 = 现在的钱+在别人那的钱-借别人的钱
						    		pzmxjfje = kmye.add(dfje).subtract(jfje);					    		
						    		dfze = dfze.add(pzmxjfje); //贷方总额 所有转出科目是贷方的钱  明细的对应转入科目金额
						    		jfjehj = jfjehj.add(pzmxjfje);//明细表借方金额合计 放在主表的借方合计上
						    		  //System.out.println(jfjehj+"**"+dfze);
						    		if(pzmxjfje.intValue()==0) {
						    			size--;
						    		}else{
						    			//增加一条凭证录入明细的转出科目相关信息
							    		Map dfzcmap = new HashMap();
							    		dfzcmap.put("guid", mxguid);
							    		dfzcmap.put("pzbh",zbguid);
							    		dfzcmap.put("zy", zy);
							    		dfzcmap.put("kmbh", zckmbh);
							    		dfzcmap.put("jdfx", "1");
							    		dfzcmap.put("jfje", pzmxjfje);
							    		dfzcmap.put("sfjz", "1");
							    		dfzcmap.put("sszt", sszt);
							    		dfzcmap.put("kjzd", kjzd);
						    			//szjzService.doAddpzlrmx(dfzcmap);
							    		szjzService.doAddpzlrmx(dfzcmap);
						    			//业务记录表中添加数据
						    			String ywguid = this.get32UUID();
						    		    Map ywmap = new HashMap<>();
						    		    ywmap.put("guid", ywguid);
						    		    ywmap.put("ywid", zckmbh);
						    		    ywmap.put("czid", czr);
						    		    ywmap.put("czr", czr);
						    		    ywmap.put("kmlx", "1");
						    		    ywmap.put("zbid", zbguid);
						    		    ywmap.put("sszt",sszt);
						    		    xmlxService.doAddjwj2(ywmap,kjzd);
						    		   //收支结转明细表增加数据		
						    		    String zjmxguid = this.get32UUID(); 
										Map mbmxmap= mbmxlist.get(b);
										Map zjmxmap = new HashMap<>();
										zjmxmap.put("guid", zjmxguid);
										zjmxmap.put("jzmb", guid); // Cw_Jzmbb guid
										zjmxmap.put("zrkmbh", mbmxmap.get("zrkmbh"));
										zjmxmap.put("zckmbh", mbmxmap.get("zckmbh"));
										zjmxmap.put("pzh", pzbh);
										zjmxmap.put("pzlx", pzlx);
										zjmxmap.put("pzrq", map3.get("jzrq"));
										zjmxmap.put("jznd", map3.get("ztnd"));
										zjmxmap.put("jzyf", map3.get("kjqj"));
										zjmxmap.put("mxguid", mxguid);//凭证录入明细表  mxguid
										zjmxmap.put("sszt", sszt);
										zjmxmap.put("czr", dlr);
										zjmxmap.put("zbguid", zbguid);//凭证录入主表  zbguid
										szjzService.addszjzmx(zjmxmap);	
						    		}//else
					    	 }else{//转出科目是借方	
					    		   //我要转出的钱 = 现在的钱+在别人那的钱-借别人的钱
						    		pzmxdfje = kmye.add(jfje).subtract(dfje);
						    		jfze = jfze.add(pzmxdfje); //借方总额 所有转出科目是借方的钱  明细对应转入科目的金额
						    		dfjehj = dfjehj.add(pzmxdfje);//贷方加起来放在主表的贷方合计上
						    		  //System.out.println(jfze+"**"+dfjehj);
						    		if(pzmxdfje.intValue()==0) {
						    			size--;
						    		}else {
						    			//增加凭证录入转出科目相关信息
							    		Map jfzcmap = new HashMap();
							    		jfzcmap.put("guid", mxguid);
							    		jfzcmap.put("pzbh",zbguid);
							    		jfzcmap.put("zy", zy);
							    		jfzcmap.put("kmbh", zckmbh);
							    		jfzcmap.put("jdfx", "0");
							    		jfzcmap.put("dfje", pzmxdfje);
							    		jfzcmap.put("sfjz", "1");
							    		jfzcmap.put("sszt", sszt);
							    		jfzcmap.put("kjzd", kjzd);
							    		//szjzService.doAddpzlrmx(jfzcmap);
							    		szjzService.doAddpzlrmx(jfzcmap);
						    			//业务记录表中添加数据
						    			String ywguid = this.get32UUID();
						    		    Map ywmap = new HashMap<>();
						    		    ywmap.put("guid", ywguid);
						    		    ywmap.put("ywid", zckmbh);
						    		    ywmap.put("czid", czr);
						    		    ywmap.put("czr", czr);
						    		    ywmap.put("kmlx", "1");
						    		    ywmap.put("zbid", zbguid);
						    		    ywmap.put("sszt",sszt);
						    		    xmlxService.doAddjwj2(ywmap,kjzd);
						    		    //收支结转明细表增加数据					    		    
						    		    String guid6 = this.get32UUID(); 
										Map map7= mbmxlist.get(b);
										Map map6 = new HashMap<>();
										map6.put("guid", guid6);
										map6.put("jzmb", guid);
										map6.put("zrkmbh", map7.get("zrkmbh"));
										map6.put("zckmbh", map7.get("zckmbh"));
										map6.put("pzh", pzbh);
										map6.put("pzlx", pzlx);
										map6.put("pzrq", map3.get("jzrq"));
										map6.put("jznd", map3.get("ztnd"));
										map6.put("jzyf", map3.get("kjqj"));
										map6.put("mxguid", mxguid);
										map6.put("sszt", sszt);
										map6.put("czr", dlr);
										map6.put("zbguid", zbguid);
										szjzService.addszjzmx(map6);	
						    	     }//else
					    	 }//else
					    }//for  zclist
					    //凭证录入明细表中 存一条  转入科目相关信息
				        if(size>0) {
				    	    Map pzmxzrmap = new HashMap();
						    String zrguid = this.get32UUID();
						    if("1".equals(zryefx)) {//转入科目 是 贷方
						    	 dfze = jfze.add(dfze); //对应转出科目 转过来的所有钱
						    	 pzmxzrmap.put("dfje", dfze);
						    	 pzmxzrmap.put("guid", zrguid);
						    	 pzmxzrmap.put("pzbh", zbguid);
						    	 pzmxzrmap.put("zy", zy);
						    	 pzmxzrmap.put("kmbh",kmbh );
						    	 pzmxzrmap.put("jdfx", "1");
						    	 pzmxzrmap.put("sfjz", "1");
						    	 pzmxzrmap.put("sszt",sszt);
						    	 pzmxzrmap.put("kjzd",kjzd);
								 if(dfze.intValue()==0) {
									 
								 }else{
									//szjzService.doAddpzlrmx(pzmxzrmap);
									szjzService.doAddpzlrmxbyjdfx(pzmxzrmap);
								 }
								//业务记录表中添加数据
				    			String ywguid = this.get32UUID();
				    		    Map ywmap = new HashMap<>();
				    		    ywmap.put("guid", ywguid);
				    		    ywmap.put("ywid", kmbh);
				    		    ywmap.put("czid", czr);
				    		    ywmap.put("czr", czr);
				    		    ywmap.put("kmlx", "1");
				    		    ywmap.put("zbid", zbguid);
				    		    ywmap.put("sszt",sszt);
				    		    xmlxService.doAddjwj2(ywmap,kjzd);
								//更新主表的  借贷方金额合计
				    		    jfjehj = jfjehj.add(jfze);
				    		    dfjehj = dfjehj.add(dfze);
				    		    //System.out.println(jfjehj+"**"+dfjehj);
								szjzService.addJe(jfjehj,dfjehj,zbguid); 
						    }else if("0".equals(zryefx)){//转入科目 是 借方
						    	jfze = dfze.add(jfze);
						    	pzmxzrmap.put("jfje",jfze);
						    	pzmxzrmap.put("guid", zrguid);
						    	pzmxzrmap.put("pzbh", zbguid);
						    	pzmxzrmap.put("zy", zy);
						    	pzmxzrmap.put("kmbh",kmbh );
						    	pzmxzrmap.put("jdfx", "0");
						    	pzmxzrmap.put("sfjz", "1");
						    	pzmxzrmap.put("sszt", sszt);
						    	pzmxzrmap.put("kjzd", kjzd);
								 if(jfze.intValue()==0) {
									
								 }else {
									 szjzService.doAddpzlrmxbyjdfx(pzmxzrmap);
								 } 
								//业务记录表中添加数据
				    			String ywguid = this.get32UUID();
				    		    Map ywmap = new HashMap<>();
				    		    ywmap.put("guid", ywguid);
				    		    ywmap.put("ywid", kmbh);
				    		    ywmap.put("czid", czr);
				    		    ywmap.put("czr", czr);
				    		    ywmap.put("kmlx", "1");
				    		    ywmap.put("zbid", zbguid);
				    		    ywmap.put("sszt",sszt);
				    		    xmlxService.doAddjwj2(ywmap,kjzd);
				    		    //更新主表的  借贷方金额合计	
				    		    jfjehj = jfjehj.add(jfze);
				    		    dfjehj = dfjehj.add(dfze);
				    		    //System.out.println(jfjehj+"**"+dfjehj);
								szjzService.addJe(jfjehj,dfjehj,zbguid); 
						    }
				        }else {
				        	length--;
					    	//所有的转出科目的金额都为0
					    	//借方贷方金额为0 ，不存在凭证，删除此凭证
							szjzService.deletezb(zbguid);
							//删除此凭证所在的明细
							szjzService.deletemx(zbguid);
							//删除收支结转明细表
							szjzService.deleteszjz(zbguid);
					    }
					}//for  zrlist
				}else {
					//没有转入科目
				}
			}//for  jzlist
		}else {//结转模板没有数据
			
		}
	
		return t;
	}
	/**
	 * 获取结转模板列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getFjZPageList")
	@ResponseBody
	public Object getFjZPageList(HttpSession session) throws Exception {	
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" * ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("ZBGUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName(" (select distinct TO_CHAR(PZRQ,'yyyy-mm-dd') as pzrq,jzyf,ZBGUID,(select pzbh from cw_pzlrzb lr where lr.guid=zbguid ) as pzh,(select '('||rybh||')'||xm from gx_sys_ryb ry where ry.guid = sz.czr ) as czr from cw_szjzmxb sz where sz.sszt='"+sszt+"' group by pzrq,pzh,czr,zbguid,jzyf) K" );//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 反结转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/dofjz")
	@ResponseBody
	public Object dofjz() throws Exception{
		PageData pd = this.getPageData();
		String pzh = pd.getString("pzh");
		String jzyf = pd.getString("jzyf");
		String zbguid = pd.getString("zbguid");
		//通过凭证号删除凭数据
		boolean a = szjzService.dodelete(zbguid,pzh,jzyf);
		//System.out.println("a====="+a);
		if(a) {
			return "success";
		}else {
			return "false";
		}
	}
	/**
	 * 反结转
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/doplfjz")
	@ResponseBody
	public Object doplfjz() throws Exception{
		PageData pd = this.getPageData();
		//String zbguid = pd.getString("zbguid");
		//通过凭证号删除凭数据
		boolean a = szjzService.dopldelete(pd);
		if(a) {
			return "success";
		}else {
			return "false";
		}
	}
public static void main(String[] args) {
	BigDecimal dfje=new BigDecimal("100.02");
	BigDecimal dfje1=new BigDecimal("100.02");
	BigDecimal dfje2=new BigDecimal("100.02");
	
	System.out.println(dfje);
}

}
