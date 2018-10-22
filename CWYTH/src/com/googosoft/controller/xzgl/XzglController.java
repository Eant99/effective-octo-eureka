package com.googosoft.controller.xzgl;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.transaction.Transactional;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xzgl.XzglService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/xzgl")
public class XzglController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="xzglService")
	private XzglService xzglService;
	
	@Resource(name="dictService")
	private DictService dictService;
	
	/**
	 * 在职薪资管理
	 * @return
	 */
	@RequestMapping(value="/xzgl_list",produces = "text/html;charset=UTF-8")
	public ModelAndView goZzxzgl(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();	
		List list = new ArrayList<Map<String,Object>>();
//		list = xzglService.getXzList(pd);
//		mv.addObject("list",list);
//		mv.addObject("rybh",pd.getString("rybh"));
		List<Map<String,Object>> gzwhlist=xzglService.getGzwhList();
		mv.addObject("gzwhlist",gzwhlist);
		Map yfMap = xzglService.getffyf();
		mv.addObject("yfMap",yfMap); 
		mv.setViewName("xzgl/xzgl_list2");  
		return mv;
	}
	/**
	 * 跳转到批量赋值页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goPlfuzhiPage")
	@ResponseBody
	public ModelAndView goPlfuzhiPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		
		String rybh = pd.getString("rybh");
		String shzt = pd.getString("shzt");
		String gzyf = pd.getString("gzyf");
		String xm = pd.getString("xm11");
		String bm = pd.getString("bm11");
		
		mv.addObject("rybh", rybh);
		mv.addObject("shzt", shzt);
		mv.addObject("gzyf", gzyf);
		mv.addObject("xm", xm);
		mv.addObject("bm", bm);
		List czfslist = xzglService.getList();
		mv.setViewName("fzgn/jcsz/dwb_plfz2");
		mv.addObject("ids", dwbh);
		mv.addObject("czfslist", czfslist);
		return mv;
	}
	
	@RequestMapping(value="/goPlfuzhitPage")
	@ResponseBody
	public ModelAndView goPlfuzhitPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String rybh = pd.getString("rybh");
		String shzt = pd.getString("shzt");
		String gzyf = pd.getString("gzyf");
		mv.addObject("rybh", rybh);
		mv.addObject("shzt", shzt);
		mv.addObject("gzyf", gzyf);
		List czfslist = xzglService.getListy();
		mv.setViewName("fzgn/jcsz/dwb_plfz3");
		mv.addObject("ids", dwbh);
		mv.addObject("czfslist", czfslist);
		return mv;
	}
	
	@RequestMapping(value="/doPlfuzhi",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doPlfuzhi() throws ParseException{
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		String ziduan = pd.getString("ziduan");
		Object zdValue = pd.getString("zdValue");
		int k =0;
		List idlist = xzglService.cxidlist(pd);
		String ids2 = "";
		for (int i = 0; i < idlist.size(); i++) {
			Map map = (Map) idlist.get(i);
			if("1".equals(map.get("guid"))) {
				continue;
			}
			ids2 = ids2 + map.get("guid")+",";
		}
		if(idlist.size()>0 && ids2.length()>1) {
			ids2 = ids2.substring(0,ids2.length()-1);
		}
		k = xzglService.doPlfuzhi(ids,ziduan,zdValue,ids2);
		if(k>0){
			return "{success:true,msg:'批量赋值成功！',ids:'"+ids+"'}";
		}else{
			return "{success:false,msg:'批量赋值失败！',ids:'"+ids+"'}";
		}
	}
	
	@RequestMapping(value="/doPlfuzhiy",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doPlfuzhiy() throws ParseException{
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		String ziduan = pd.getString("ziduan");
		Object zdValue = pd.getString("zdValue");
		String gzyf = pd.getString("gzyf");
		int k =0;
		List idlist = xzglService.cxidlist2(pd);
		String ids2 = "";
		for (int i = 0; i < idlist.size(); i++) {
			Map map = (Map) idlist.get(i);
			if("1".equals(map.get("guid"))) {
				continue;
			}
			ids2 = ids2 + map.get("guid")+",";
		}
		if(idlist.size()>0) {
			ids2 = ids2.substring(0,ids2.length()-1);
		}
		k = xzglService.doPlfuzhiy(ids,ziduan,zdValue,ids2,gzyf);
		if(k>0){
			return "{success:true,msg:'批量赋值成功！',ids:'"+ids+"'}";
		}else{
			return "{success:false,msg:'批量赋值失败！',ids:'"+ids+"'}";
		}
	}
	
	/**
	 * 跳转专业列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/col")
	public ModelAndView goColPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String controlId = pd.getString("controlId");
		mv.addObject("controlId", controlId);
		String window_url  = "kjhs/pzxx/cnqz/cnqzc2";
		mv.setViewName(window_url);
		return mv;
	}
	/**
	 * 在职薪资列表
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		this.getPageList(pd,pageList);
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	/**
	 * 获取列表和导出时，给pageList的sqltext、where、tablename赋值的公用方法
	 * @param pd
	 * @param pageList
	 * @return
	 */
	public PageList getPageList(PageData pd,PageList pageList){		
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String strWhere = " ";
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		if(rybh.indexOf(")")!=-1){
			rybh = rybh.substring(1,rybh.indexOf(")"));
		}
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		String xm = Validate.isNullToDefaultString(pd.getString("xm11"),"");
		String bm = Validate.isNullToDefaultString(pd.getString("bm11"),"");
		sqltext.append(" T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT, 0, '未提交', 1, '待审核', 2, '审核通过', 3, '退回', 5, '已核算','合计') AS SHZT, " +
				"TO_CHAR(T.GWGZ,'FM999999999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM999999999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM999999999990.00') AS XZFT," +
				"TO_CHAR(T.WYBT,'FM999999999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM999999999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM999999999990.00') AS JCJX," +
				"TO_CHAR(T.JLJX1,'FM999999999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM999999999990.00') AS BSBT," +
				"TO_CHAR(T.GWBT,'FM999999999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM999999999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM999999999990.00') AS SYBT," +
				"TO_CHAR(T.JHBT,'FM999999999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM999999999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM999999999990.00') AS HTBT," +
				"TO_CHAR(T.QTBT,'FM999999999990.00') AS QTBT," +
				"TO_CHAR(T.BGZ,'FM999999999990.00') AS BGZ,TO_CHAR(T.JKF,'FM999999999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM999999999990.00') AS FZGZL," +
				"TO_CHAR(T.ZSJL,'FM999999999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM999999999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM999999999990.00') AS KHJ," +
				"TO_CHAR(T.DHF,'FM999999999990.00') AS DHF,TO_CHAR(T.BT,'FM999999999990.00') AS BT,TO_CHAR(T.ZFBT,'FM999999999990.00') AS ZFBT," +
				"TO_CHAR(T.YFHJ,'FM999999999990.00') AS YFHJ," +
				"TO_CHAR(T.JIANGKEF,'FM999999999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM999999999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM999999999990.00') AS JSX," +
				"TO_CHAR(T.QNJSX,'FM999999999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM999999999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM999999999990.00') AS QNJSX2," +
				"TO_CHAR(T.QNJSX3,'FM99999999999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM999999999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM999999999990.00') AS BXBT2014N1D10YJSJS," +
				"TO_CHAR(T.JSJS,'FM999999999990.00') AS JSJS," +
				"TO_CHAR(T.ZFJJ,'FM999999999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM999999999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM999999999990.00') AS YLBX," +
				"TO_CHAR(T.BGJJ,'FM999999999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM999999999990.00') AS DKS,TO_CHAR(T.BNSE,'FM999999999990.00') AS BNSE," +
				"TO_CHAR(T.SNSE,'FM999999999990.00') AS SNSE,TO_CHAR(T.BS,'FM999999999990.00') AS BS,TO_CHAR(T.FZ,'FM999999999990.00') AS FZ," +
				"TO_CHAR(T.SYJ,'FM999999999990.00') AS SYJ," +
				"TO_CHAR(T.NQF,'FM999999999990.00') AS NQF,TO_CHAR(T.NQF1,'FM999999999990.00') AS NQF1,TO_CHAR(T.WYF,'FM999999999990.00') AS WYF," +
				"TO_CHAR(T.SBJ,'FM999999999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM999999999990.00') AS SJDCK,TO_CHAR(T.KK,'FM999999999990.00') AS KK," +
				"TO_CHAR(T.YLJ,'FM999999999990.00') AS YLJ,TO_CHAR(T.JK,'FM999999999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM999999999990.00') AS AXYRJ," +
				"TO_CHAR(T.KKHJ,'FM999999999990.00') AS KKHJ," +
				"TO_CHAR(T.SFHJ,'FM999999999990.00') AS SFHJ,T.GZYF AS GZYF,T.BH," +
				"T.XH,TO_CHAR(T.JTF,'FM999999999990.00') AS JTF,TO_CHAR(T.NZJ,'FM999999999990.00') AS NZJ," +
				"TO_CHAR(T.NZJDKS,'FM999999999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM999999999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM999999999990.00') AS KSHJ," +
				"T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM999999999990.00') AS BKYLBX," +
				"TO_CHAR(T.BKSYJ,'FM999999999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM999999999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM999999999990.00') AS BKSBJ," +
				"T.SFDY,T.RDQK,T.XL ");
		int count=xzglService.count("cw_xzb", rybh, shzt, gzyf, xm, bm);
		tablename.append("(select  T.GUID, T.RYBH, T.XM,T.BM,T.RYLB,T.RYLX,t.shzt as shzt,T.GWGZ  GWGZ,T.XJGZ  XJGZ,T.XZFT XZFT,T.WYBT WYBT,T.DSZF  DSZF,T.JCJX  JCJX,T.JLJX1 JLJX1,T.BSBT BSBT,T.GWBT GWBT,T.BXQZBBT  BXQZBBT,T.SYBT  SYBT,T.JHBT  JHBT,T.ZJBT  ZJBT,T.HTBT  HTBT,T.QTBT  QTBT,T.BGZ  BGZ,T.JKF  JKF,T.FZGZL  FZGZL,T.ZSJL  ZSJL,T.FDYYJZBBT  FDYYJZBBT,T.KHJ  KHJ,T.DHF  DHF,T.BT  BT,T.ZFBT  ZFBT,T.YFHJ  YFHJ,T.JIANGKEF  JIANGKEF,T.BGZKS  BGZKS,T.JSX JSX,T.QNJSX  QNJSX,T.QNJSX1  QNJSX1,T.QNJSX2 QNJSX2,T.QNJSX3  QNJSX3,T.BJCXJXGZ2014JSJS  BJCXJXGZ2014JSJS,T.BXBT2014N1D10YJSJS  BXBT2014N1D10YJSJS,T.JSJS JSJS,T.ZFJJ  ZFJJ,T.PGJJ  PGJJ,T.YLBX  YLBX,T.BGJJ  BGJJ,T.DKS  DKS,T.BNSE  BNSE,T.SNSE  SNSE,T.BS BS,T.FZ  FZ,T.SYJ SYJ,T.NQF  NQF,T.NQF1  NQF1,T.WYF  WYF,T.SBJ  SBJ,T.SJDCK  SJDCK,T.KK  KK,T.YLJ YLJ,T.JK JK,T.AXYRJ  AXYRJ,T.KKHJ  KKHJ,T.SFHJ  SFHJ,T.GZYF  GZYF,T.BH,T.XH,T.JTF  JTF,T.NZJ  NZJ,T.NZJDKS  NZJDKS,T.GZDKS  GZDKS,T.KSHJ  KSHJ,T.GH,T.SFZB,T.BKYLBX  BKYLBX,T.BKSYJ  BKSYJ,T.BKYLJ  BKYLJ,T.BKSBJ  BKSBJ,T.SFDY,T.RDQK,T.XL from CW_XZB T ");
		if(count>0){
			tablename.append(" union ");
			tablename.append("select '1'  GUID, ''  RYBH, ''  XM, ''  BM,''  RYLB,''  RYLX,''  SHZT,sum(T.GWGZ)  GWGZ,sum(T.XJGZ)  XJGZ,sum(T.XZFT) XZFT,sum(T.WYBT)  WYBT,sum(T.DSZF)  DSZF,sum(T.JCJX)  JCJX,sum(T.JLJX1)  JLJX1,sum(T.BSBT)  BSBT,sum(T.GWBT)  GWBT,sum(T.BXQZBBT)  BXQZBBT,sum(T.SYBT)  SYBT,sum(T.JHBT)   JHBT,sum(T.ZJBT)  ZJBT,sum(T.HTBT)  HTBT,sum(T.QTBT)  QTBT,sum(T.BGZ)  BGZ,sum(T.JKF)  JKF,sum(T.FZGZL)  FZGZL,sum(T.ZSJL)  ZSJL,sum(T.FDYYJZBBT)  FDYYJZBBT,sum(T.KHJ)  KHJ,sum(T.DHF)  DHF,sum(T.BT)  BT,sum(T.ZFBT)  ZFBT,sum(T.YFHJ)  YFHJ,sum(T.JIANGKEF)  JIANGKEF,sum(T.BGZKS)  BGZKS,sum(T.JSX)  JSX,sum(T.QNJSX)  QNJSX,sum(T.QNJSX1) QNJSX1,sum(T.QNJSX2)  QNJSX2,sum(T.QNJSX3)  QNJSX3,sum(T.BJCXJXGZ2014JSJS)  BJCXJXGZ2014JSJS,sum(T.BXBT2014N1D10YJSJS)  BXBT2014N1D10YJSJS,sum(T.JSJS)  JSJS,sum(T.ZFJJ)  ZFJJ,sum(T.PGJJ)  PGJJ,sum(T.YLBX) YLBX,sum(T.BGJJ)  BGJJ,sum(T.DKS)  DKS,sum(T.BNSE)  BNSE,sum(T.SNSE) AS SNSE,sum(T.BS)  BS,sum(T.FZ) FZ,sum(T.SYJ)  SYJ,sum(T.NQF)  NQF,sum(T.NQF1)  NQF1,sum(T.WYF)  WYF,sum(T.SBJ)  SBJ,sum(T.SJDCK)  SJDCK,sum(T.KK)  KK,sum(T.YLJ)  YLJ,sum(T.JK)  JK,sum(T.AXYRJ)  AXYRJ,sum(T.KKHJ)  KKHJ,sum(T.SFHJ)  SFHJ,''  GZYF,''  BH,''  XH,sum(T.JTF)  JTF,sum(T.NZJ)  NZJ,sum(T.NZJDKS)  NZJDKS,sum(T.GZDKS)  GZDKS,sum(T.KSHJ)  KSHJ,''  GH,''  SFZB,sum(T.BKYLBX)  BKYLBX,sum(T.BKSYJ)  BKSYJ,sum(T.BKYLJ)  BKYLJ,sum(T.BKSBJ)  BKSBJ,''  SFDY,''  RDQK,''  XL  from CW_XZB T where 1=1 ");
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				tablename.append(" and (t.rybh = '"+rybh+"' or t.rybh is null) " );
			}
			if(Validate.noNull(shzt)){
				tablename.append(" and t.shzt like '%"+shzt+"%'");
			}
			if(Validate.noNull(gzyf)){
				tablename.append(" and t.gzyf like '%"+gzyf+"%' ");
			}
			if(Validate.noNull(xm)){
				tablename.append(" and t.xm like '%"+xm+"%'");
			}
			if(Validate.noNull(bm)){
				tablename.append(" and t.bm like '%"+CommonUtil.getEndText(bm)+"%' ");
			}
		}
		tablename.append(" ) T ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("T.GUID");//主键
		//设置WHERE条件
		if(Validate.noNull(rybh)){
//			rybh = rybh.replace(",", "','");
			strWhere += " and (t.rybh = '"+rybh+"' or t.rybh is null)";
		}
		if(Validate.noNull(shzt)){
			strWhere += " and (t.shzt like '%"+shzt+"%' or t.shzt is null)  ";
		}
		if(Validate.noNull(gzyf)){
			strWhere += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null) ";
		}
		if(Validate.noNull(xm)){
			strWhere += " and (t.xm like '%"+xm+"%' or t.xm is null) ";
		}
		if(Validate.noNull(bm)){
			strWhere += " and (t.bm like '%"+CommonUtil.getEndText(bm)+"%' or t.bm is null )";
		}
		pageList.setStrWhere(strWhere); 
		//sqlwhere.append(ToSqlUtil.getLongInSql(pd.getString("id"), pageList.getKeyId()));
		return pageList;
	}
	/**
	 * 获得离职薪资列表数据
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getLzPageList")
	@ResponseBody
	public Object getLzPageList(HttpSession session) throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String strWhere = " ";
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		String bmmc = Validate.isNullToDefaultString(pd.getString("bmbh"),"");
		if(rybh.indexOf(")")!=-1){
			rybh = rybh.substring(1,rybh.indexOf(")"));
		}
		if(bmmc.indexOf(")")!=-1){
			bmmc = bmmc.substring(bmmc.indexOf(")")+1);
		}
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		PageList pageList = new PageList();
		sqltext.append("L.GUID,L.RYBH,L.XM,L.BM,L.RYLB,DECODE(L.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回','合计') AS SHZT," +
				"TO_CHAR(L.JBGZ,'FM99999999999990.00') AS JBGZ,TO_CHAR(L.ZJLTF,'FM99999999999990.00') AS ZJLTF,TO_CHAR(L.YZWBT,'FM99999999999990.00') AS YZWBT," +
				"TO_CHAR(L.GWBT,'FM99999999999990.00') AS GWBT,TO_CHAR(L.XZFT,'FM99999999999990.00') AS XZFT,TO_CHAR(L.HZBT,'FM99999999999990.00') AS HZBT," +
				"TO_CHAR(L.TXTGBF,'FM99999999999990.00') AS TXTGBF,TO_CHAR(L.SHBT,'FM99999999999990.00') AS SHBT,TO_CHAR(L.XZBT,'FM99999999999990.00') AS XZBT," +
				"TO_CHAR(L.WJBT,'FM99999999999990.00') AS WJBT,TO_CHAR(L.TXHL,'FM99999999999990.00') AS TXHL,TO_CHAR(L.JHBT,'FM99999999999990.00') AS JHBT," +
				"TO_CHAR(L.QTBT,'FM99999999999990.00') AS QTBT,TO_CHAR(L.LTBT,'FM99999999999990.00') AS LTBT,TO_CHAR(L.YZBT,'FM99999999999990.00') AS YZBT," +
				"TO_CHAR(L.JCJX,'FM99999999999990.00') AS JCJX,TO_CHAR(L.JTF,'FM99999999999990.00') AS JTF,TO_CHAR(L.BT,'FM99999999999990.00') AS BT," +
				"TO_CHAR(L.ZFBT,'FM99999999999990.00') AS ZFBT,TO_CHAR(L.BGZ,'FM99999999999990.00') AS BGZ," +
				"TO_CHAR(L.WYBT,'FM99999999999990.00') AS WYBT," +
				"TO_CHAR(L.JKF,'FM99999999999990.00') AS JKF,TO_CHAR(L.GJF,'FM99999999999990.00') AS GJF,TO_CHAR(L.DHF,'FM99999999999990.00') AS DHF," +
				"TO_CHAR(L.YFHJ,'FM99999999999990.00') AS YFHJ,TO_CHAR(L.FZ,'FM99999999999990.00') AS FZ,TO_CHAR(L.SBJ,'FM99999999999990.00') AS SBJ," +
				"TO_CHAR(L.NQF,'FM99999999999990.00') AS NQF,TO_CHAR(L.NQF1,'FM99999999999990.00') AS NQF1,TO_CHAR(L.WYF,'FM99999999999990.00') AS WYF," +
				"TO_CHAR(L.JK,'FM99999999999990.00') AS JK,TO_CHAR(L.YLJ,'FM99999999999990.00') AS YLJ,TO_CHAR(L.BGJJ,'FM99999999999990.00') AS BGJJ," +
				"TO_CHAR(L.BS,'FM99999999999990.00') AS BS,TO_CHAR(L.SJDCK,'FM99999999999990.00') AS SJDCK,TO_CHAR(L.SYJ,'FM99999999999990.00') AS SYJ," +
				"TO_CHAR(L.KKHJ,'FM99999999999990.00') AS KKHJ,TO_CHAR(L.SFHJ,'FM99999999999990.00') AS SFHJ,L.GZYF," +
				"L.BH,TO_CHAR(L.JSX,'FM99999999999990.00') AS JSX,TO_CHAR(L.ZFJJ,'FM99999999999990.00') AS ZFJJ," +
				"TO_CHAR(L.NZJ,'FM99999999999990.00') AS NZJ,TO_CHAR(L.KK,'FM99999999999990.00') AS KK,"+
				"xh"
				);
		int count=xzglService.count("CW_LZXZB", rybh, shzt, gzyf, "", "");
			tablename.append("(select L.GUID, L.RYBH,L.XM,L.BM,L.RYLB,L.SHZT AS SHZT,TO_CHAR(L.JBGZ, 'FM999999999990.00') AS JBGZ,TO_CHAR(L.ZJLTF, 'FM999999999990.00') AS ZJLTF,TO_CHAR(L.YZWBT, 'FM999999999990.00') AS YZWBT,TO_CHAR(L.GWBT, 'FM999999999990.00') AS GWBT,TO_CHAR(L.XZFT, 'FM999999999990.00') AS XZFT,TO_CHAR(L.HZBT, 'FM999999999990.00') AS HZBT,TO_CHAR(L.TXTGBF, 'FM99999999999990.00') AS TXTGBF,TO_CHAR(L.SHBT, 'FM99999999999990.00') AS SHBT,TO_CHAR(L.XZBT, 'FM999999999990.00') AS XZBT,TO_CHAR(L.WJBT, 'FM999999999990.00') AS WJBT,TO_CHAR(L.TXHL, 'FM999999999990.00') AS TXHL,TO_CHAR(L.JHBT, 'FM999999999990.00') AS JHBT,TO_CHAR(L.QTBT, 'FM999999999990.00') AS QTBT,TO_CHAR(L.LTBT, 'FM999999999990.00') AS LTBT,TO_CHAR(L.YZBT, 'FM999999999990.00') AS YZBT,TO_CHAR(L.JCJX, 'FM999999999990.00') AS JCJX,TO_CHAR(L.JTF, 'FM999999999990.00') AS JTF,TO_CHAR(L.BT, 'FM999999999990.00') AS BT,TO_CHAR(L.ZFBT, 'FM999999999990.00') AS ZFBT,TO_CHAR(L.BGZ, 'FM999999999990.00') AS BGZ,TO_CHAR(L.WYBT, 'FM999999999990.00') AS WYBT,TO_CHAR(L.JKF, 'FM999999999990.00') AS JKF,TO_CHAR(L.GJF, 'FM999999999990.00') AS GJF,TO_CHAR(L.DHF, 'FM999999999990.00') AS DHF,TO_CHAR(L.YFHJ, 'FM999999999990.00') AS YFHJ,TO_CHAR(L.FZ, 'FM999999999990.00') AS FZ,TO_CHAR(L.SBJ, 'FM999999999990.00') AS SBJ,TO_CHAR(L.NQF, 'FM999999999990.00') AS NQF,TO_CHAR(L.NQF1, 'FM999999999990.00') AS NQF1,TO_CHAR(L.WYF, 'FM999999999990.00') AS WYF,TO_CHAR(L.JK, 'FM999999999990.00') AS JK,TO_CHAR(L.YLJ, 'FM999999999990.00') AS YLJ,TO_CHAR(L.BGJJ, 'FM999999999990.00') AS BGJJ,TO_CHAR(L.BS, 'FM999999999990.00') AS BS,TO_CHAR(L.SJDCK, 'FM999999999990.00') AS SJDCK,TO_CHAR(L.SYJ, 'FM999999999990.00') AS SYJ,TO_CHAR(L.KKHJ, 'FM999999999990.00') AS KKHJ,TO_CHAR(L.SFHJ, 'FM999999999990.00') AS SFHJ,L.GZYF,L.BH,TO_CHAR(L.JSX, 'FM999999999990.00') AS JSX,TO_CHAR(L.ZFJJ, 'FM999999999990.00') AS ZFJJ,TO_CHAR(L.NZJ, 'FM999999999990.00') AS NZJ,TO_CHAR(L.KK, 'FM999999999990.00') AS KK,(case L.BM WHEN '离休人员' THEN '1' WHEN '退休人员' THEN '2' WHEN '遗属' THEN '3' WHEN '临时工' THEN '4'else '5' END)as xh from CW_LZXZB L");
			if(count>0){
			tablename.append(" union ");
			tablename.append(" select '1' as GUID,'' as RYBH,'' as XM,'' as BM,'' as RYLB,'' AS SHZT,TO_CHAR(sum(L.JBGZ), 'FM999999999990.00') AS JBGZ,TO_CHAR(sum(L.ZJLTF), 'FM999999999990.00') AS ZJLTF,TO_CHAR(sum(L.YZWBT), 'FM999999999990.00') AS YZWBT,TO_CHAR(sum(L.GWBT), 'FM999999999990.00') AS GWBT,TO_CHAR(sum(L.XZFT), 'FM999999999990.00') AS XZFT,TO_CHAR(sum(L.HZBT), 'FM999999999990.00') AS HZBT,TO_CHAR(sum(L.TXTGBF), 'FM99999999999990.00') AS TXTGBF,TO_CHAR(sum(L.SHBT), 'FM99999999999990.00') AS SHBT,TO_CHAR(sum(L.XZBT), 'FM999999999990.00') AS XZBT,TO_CHAR(sum(L.WJBT), 'FM999999999990.00') AS WJBT,TO_CHAR(sum(L.TXHL), 'FM999999999990.00') AS TXHL,TO_CHAR(sum(L.JHBT), 'FM999999999990.00') AS JHBT,TO_CHAR(sum(L.QTBT), 'FM999999999990.00') AS QTBT,TO_CHAR(sum(L.LTBT), 'FM999999999990.00') AS LTBT,TO_CHAR(sum(L.YZBT), 'FM999999999990.00') AS YZBT,TO_CHAR(sum(L.JCJX), 'FM999999999990.00') AS JCJX,TO_CHAR(sum(L.JTF), 'FM999999999990.00') AS JTF,TO_CHAR(sum(L.BT), 'FM999999999990.00') AS BT,TO_CHAR(sum(L.ZFBT), 'FM999999999990.00') AS ZFBT,TO_CHAR(sum(L.BGZ), 'FM999999999990.00') AS BGZ,TO_CHAR(sum(L.WYBT), 'FM999999999990.00') AS WYBT,TO_CHAR(sum(L.JKF), 'FM999999999990.00') AS JKF,TO_CHAR(sum(L.GJF), 'FM999999999990.00') AS GJF,TO_CHAR(sum(L.DHF), 'FM999999999990.00') AS DHF,TO_CHAR(sum(L.YFHJ), 'FM999999999990.00') AS YFHJ,TO_CHAR(sum(L.FZ), 'FM999999999990.00') AS FZ,TO_CHAR(sum(L.SBJ), 'FM999999999990.00') AS SBJ,TO_CHAR(sum(L.NQF), 'FM999999999990.00') AS NQF,TO_CHAR(sum(L.NQF1), 'FM999999999990.00') AS NQF1,TO_CHAR(sum(L.WYF), 'FM999999999990.00') AS WYF,TO_CHAR(sum(L.JK), 'FM999999999990.00') AS JK,TO_CHAR(sum(L.YLJ), 'FM999999999990.00') AS YLJ,TO_CHAR(sum(L.BGJJ), 'FM999999999990.00') AS BGJJ,TO_CHAR(sum(L.BS), 'FM999999999990.00') AS BS,TO_CHAR(sum(L.SJDCK), 'FM999999999990.00') AS SJDCK,TO_CHAR(sum(L.SYJ), 'FM999999999990.00') AS SYJ,TO_CHAR(sum(L.KKHJ), 'FM999999999990.00') AS KKHJ,TO_CHAR(sum(L.SFHJ), 'FM999999999990.00') AS SFHJ,'' as GZYF,'' as BH,TO_CHAR(sum(L.JSX), 'FM999999999990.00') AS JSX,TO_CHAR(sum(L.ZFJJ), 'FM999999999990.00') AS ZFJJ,TO_CHAR(sum(L.NZJ), 'FM999999999990.00') AS NZJ,TO_CHAR(sum(L.KK), 'FM999999999990.00') AS KK,'' as xh from CW_LZXZB L where 1=1 ");
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				tablename.append("and l.rybh = '"+rybh+"' ");
			}
			if(Validate.noNull(shzt)){
				tablename.append(" and l.shzt like '%"+shzt+"%'  ");
			}
			if(Validate.noNull(gzyf)){
				tablename.append("and l.gzyf like '%"+gzyf+"%' ");
			}
		}
		tablename.append(")L  ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("L.GUID");//主键
		//设置WHERE条件
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			strWhere += " and (l.rybh = '"+rybh+"' or l.rybh is null)";
		}
		if(Validate.noNull(bmmc)){
			strWhere += "and l.bm = ('"+bmmc+"') " ;
		}
		if(Validate.noNull(shzt)){
			strWhere += " and (l.shzt like '%"+shzt+"%' or l.rybh is null)  " ;
		}
		if(Validate.noNull(gzyf)){
			strWhere += " and (l.gzyf like '%"+gzyf+"%' or l.gzyf is null)";
		}
//		pageList.setOrderBy(" to_number(xh) ");
//		pageList.setOrderDir(" asc ");
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	/**
	 *  在职薪资导入上传
	 */
	@RequestMapping(value="/upload")
	public ModelAndView Upload(MultipartFile imageFile) throws IllegalStateException, IOException{
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
//		int i = xzglService.doDelXz();//清空cw_xzb
//		boolean bool = xzglService.insertXz(file);//导入数据
//		mv.addObject("bool",bool);
		mv.addObject("file", file);
		mv.addObject("commonWin", "T");// 弹窗标志
		String pname = pd.getString("pname");
		mv.addObject("pname",pname);
 		mv.setViewName("xzgl/xzgl_imp");
		return mv;
	}
	/**
	 * 在职薪资导入数据确认
	 */
	@RequestMapping(value = "/getImpXzQr")
	@ResponseBody
	public ModelAndView getImpPageQr() {
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = xzglService.getXzImpeQr(file);
		mv.addObject("list", list);
		mv.addObject("file", file);
		mv.setViewName("xzgl/xzgl_impqr");
		return mv;
	}
	/**
	 * 在职薪资数据导入
	 */
	@RequestMapping(value = "/doInsertXz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doInsertXz() throws Exception {
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		String error = xzglService.insertXz(file);
		mv.addObject("error", Validate.isNullToDefault(error, "导入失败！！"));
		return error;
	}
	/**
	 * 在职薪资保存
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave() {
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = false;
		if (list.size()>0) {
			bool = xzglService.doSave(list);
		}
		return gson.toJson(bool);
	}
	/**
	 * 在职薪资核算
	 */
	@RequestMapping(value = "/doHs", produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doHs() {
		PageData pd = this.getPageData();
		String gzyf = pd.getString("gzyf");
		String rybh = pd.getString("rybh");
		String bm = pd.getString("bm");
		String b = "";
		int i = 0;
			i = xzglService.doHs(gzyf,rybh,bm);
		if (i > 0) {
			b = "{\"success\":\"true\",\"msg\":\"信息核算成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息核算失败！\"}";
		}
		return b;
	}
	/**
	 * 在职薪资--复制上月工资--检查输入框年月是否有工作简历
	 */
	@RequestMapping(value = "/checkGzyf", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object checkGzyf() {
		PageData pd = this.getPageData();
		String gzyf = pd.getString("gzyf");
		String type = pd.getString("type");
		String b = "";
		int i = 0;
		boolean boo = xzglService.checkGzyf(gzyf,type);
		if (boo) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		}
		return b;
	}
	/**
	 * 在职薪资--复制上月工资
	 */
	@RequestMapping(value = "/doFzsygz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doFzsygz() {
		PageData pd = this.getPageData();
		String gzyf = pd.getString("gzyf");
		String b = "";
		int i = 0;
		i = xzglService.doFzsygz(gzyf);
	    String lzry = "";
	    String returnlzry = "";
	    lzry = xzglService.getLzry(gzyf);
	    if (lzry.length()!=0 && lzry!=null) {
	    	returnlzry = lzry+" 已不在职";
		}
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\",\"lzry\":\""+returnlzry+"\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\",\"lzry\":\""+returnlzry+"\"}";
		}
		return b;
	}
	/**
	 * 离职薪资--复制上月工资
	 */
	@RequestMapping(value = "/doFzsygzLz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doFzsygzLz() {
		PageData pd = this.getPageData();
		String gzyf = pd.getString("gzyf");
		String b = "";
		int i = 0;
		i = xzglService.doFzsygzLz(gzyf);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		}
		return b;
	}
	/**
	 * 在职薪资提交
	 */
	@RequestMapping(value = "/doTj", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doTj() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String gzyf = pd.getString("gzyf");
		String rybh = pd.getString("rybh");
		String bm = pd.getString("bm");
		String b = "";
		int i = 0;
		i = xzglService.doTj(guid,gzyf,rybh,bm);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"信息提交成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息提交失败！\"}";
		}
		return b;
	}
	/**
	 * 离职薪资提交
	 */
	@RequestMapping(value = "/doLzTj", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doLzTj() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String gzyf = pd.getString("gzyf");
		String b = "";
		int i = 0;
		i = xzglService.doLzTj(guid,gzyf);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"信息提交成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息提交失败！\"}";
		}
		return b;
	}
	/**
	 * 在职薪资 获取列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getXzPageList")
	@ResponseBody
	public Object  getXzPageList() throws Exception {
		PageData pd = this.getPageData();
		String bh = pd.getString("bh");
		String treesearch=pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" *  ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId(" b.guid ");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("CW_XZZB b ");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 跳转到离职薪资管理页面
	 * @return
	 */
	@RequestMapping(value="/lzxz_list",produces = "text/html;charset=UTF-8")
	public ModelAndView goLzxzgl(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		List list = new ArrayList<Map<String,Object>>();
		list = xzglService.getLzxzList(pd);
		Map yfMap = xzglService.getffyf();
		mv.addObject("yfMap",yfMap); 
		mv.addObject("rybh",pd.getString("rybh"));
		mv.setViewName("xzgl/lzxzgl_list2");  
		return mv;
	}
	/**
	 * 在职薪资 获取列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getLzxzPageList")
	@ResponseBody
	public Object  getLzxzPageList() throws Exception {
		PageData pd = this.getPageData();
		String bh = pd.getString("bh");
		String treesearch=pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" *  ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId(" b.guid ");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("CW_LZXZZB b ");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 离职薪资保存
	 * @return
	 */
	@RequestMapping(value = "/doSaveLz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveLz() {
		Gson gson = new Gson();
		String jsonStr = this.getPageData().getString("jsonStr");
		String ajson = jsonStr.substring(8,jsonStr.length()-1);
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		boolean bool = false;
		if (list.size()>0) {
			bool = xzglService.doSaveLz(list);
		}
		return gson.toJson(bool);
	}
	/**
	 *  离职薪资导入上传
	 */
	@RequestMapping(value="/upload_lz")
	public ModelAndView Upload2(MultipartFile imageFile) throws IllegalStateException, IOException{
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
//		int i = xzglService.doDelLzxz();//清空cw_lzxzb
//		boolean bool = xzglService.insertLzxz(file);//导入数据
//		mv.addObject("bool",bool);
		mv.addObject("file", file);
		mv.addObject("commonWin", "T");// 弹窗标志
		String pname = pd.getString("pname");
		mv.addObject("pname",pname);
		mv.setViewName("xzgl/lzxzgl_imp");
		return mv;
	}
	/**
	 * 离职薪资导入数据确认
	 */
	@RequestMapping(value = "/getImpLzxzQr")
	@ResponseBody
	public ModelAndView getImpLzxzQr() {
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		list = xzglService.getLzxzImpeQr(file);
		mv.addObject("list", list);
		mv.addObject("file", file);
		mv.setViewName("xzgl/lzxzgl_impqr");
		return mv;
	}
	/**
	 * 离职薪资数据导入
	 */
	@RequestMapping(value = "/doInsertLzxz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doInsertLzxz() throws Exception {
		String file = this.getPageData().getString("file");
		ModelAndView mv = this.getModelAndView();
		String error = xzglService.insertLzxz(file);//导入数据
		mv.addObject("error", Validate.isNullToDefault(error, "导入失败！！"));
		return error;
	}
	/** 重写人员弹窗功能  	  弹出人员信息树，进行人员信息的添加，如果当前发放月份存在当前人员则不能添加；
	 * 部门人员信息弹出窗
	 */
	@RequestMapping(value="/rypage")
	public ModelAndView goRyxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String gzyf=pd.getString("gzyf");
		//控件id
		String controlId = pd.getString("controlId");
		String model= Validate.isNullToDefault(pd.getString("model"), "")+"";
		mv.addObject("controlId",controlId);
		mv.addObject("gzyf",gzyf);
		mv.addObject("model",model);
		mv.setViewName("xzgl/xzwindow");
		return mv;
	}
	//查询是否有rybh 当前月份
	@RequestMapping(value = "/tianjia", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object tianjia(HttpServletRequest request,HttpServletResponse response){
		PageData pd = this.getPageData();
		String gzyf = pd.getString("gzyf");
		String rybh = pd.getString("rybh");
		String txry=pd.getString("txry");
		String b = "";
		List list = xzglService.getcount(rybh,txry,gzyf);
		if(list.size()>0) {
			b="1";
		}else {
			b="0";
		}
		return b;
	}
	/**
	 * @Description: 描述:验证是否为在职人员
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-14下午1:25:55
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/zzryCheck", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object zzryCheck(HttpServletRequest request,HttpServletResponse response) {
		String rybh=this.getPageData().getString("rybh");
		String b="";
		int a=xzglService.zzryCheck(rybh);
		if(a>0) {
			b="true";
		}else {
			b="false";
		}
		return b ;
	}
	/**
	 * @Description: 描述:验证是否为退休人员
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-14下午1:25:55
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "/txryCheck", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object txryCheck(HttpServletRequest request,HttpServletResponse response) {
		String rybh=this.getPageData().getString("rybh");
		String b="";
		int a=xzglService.txryCheck(rybh);
		if(a>0) {
			b="true";
		}else {
			b="false";
		}
		return b ;
	}
	//添加人员信息
	@RequestMapping(value = "/addryxx", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addryxx(HttpServletRequest request,HttpServletResponse response) {
		String rybh=this.getPageData().getString("rybh");
		String txry=this.getPageData().getString("txry");
		String gzyf=this.getPageData().getString("gzyf");
		String xl=this.getPageData().getString("xl");
		String dwbh=this.getPageData().getString("dwbh");
		String b="";
		int a=xzglService.addryxx(rybh,txry,gzyf,xl,dwbh);
		if(a>0) {
			b="1";
		}else {
			b="0";
		}
		return b ;
	}
	/**
	 *  导出数据
	 */
//	@RequestMapping(value="/doExp", produces = "text/html;charset=UTF-8")
//	@ResponseBody
//	public String doExp(){PageData pd = this.getPageData();
//	PageList pagelist = new PageList();
//	return pageService.doExp(this.getRequest(), this.getPageList(pd,pagelist), pd);}
	/**
	 * 删除
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int i = 0;
		i = xzglService.doDelete(guid);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"信息提交成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息提交失败！\"}";
		}
		return b;
	}
	/**
	 * 删除_退休人员薪资生成
	 */
	@RequestMapping(value = "/doDelete_txry", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete_txry() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String b = "";
		int i = 0;
		i = xzglService.doDelete_txry(guid);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"信息提交成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息提交失败！\"}";
		}
		return b;
	}
	/**
	 *  导出数据
	 */
	@RequestMapping(value="/doExp", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String doExp(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname");// + ".xls";
		//查询数据的sql语句
		String sql = "";
		PageList pagelist = new PageList();
		List<M_largedata> mlist = CommonUtil.doExp(pd.getString("columns"));
//		yshdService.getPageList(pd,pagelist);
		String searchJson = ToSqlUtil.jsonToSql(pd.getString("searchJson"));
		if(Validate.noNull(searchJson)){
			pagelist.setStrWhere(pagelist.getStrWhere() + " " + searchJson + " ");//查询条件语句
		}
		String ordercol = pd.getString("ordercol");
		if(Validate.noNull(ordercol)){
			pagelist.setOrderBy(ordercol);//查询条件语句
		}else{
			pagelist.setOrderBy("ysdh");
		}
		sql = "select " + pagelist.getSqlText() + " from " + pagelist.getTableName() + " where 1 = 1 " + pagelist.getStrWhere() +" order by "+(Validate.isNull(pagelist.getOrderBy()) ? "" : pagelist.getOrderBy());
		//导出方法                                                   
		String errmsg = pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		if(Validate.isNull(errmsg)){
			return "{\"success\":true,\"url\":\"excel\\\\"+file+".xls\"}";
		}else{
			return "{\"success\":false,\"msg\":\""+errmsg+"\"}";
		}
	}
	/**
	 * 在职薪资数据导出
	 * @return
	 */
    @RequestMapping("/ZzryexpExcel")
	@ResponseBody
    public Object ZzryexpExcel() {
          PageData pd = this.getPageData();
          String searchValue = pd.getString("searchJson");
          String guid = pd.getString("id");
          String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
          String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
          String floderPath = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel";
          File file = new File(floderPath);
          if(!file.exists()||!file.isDirectory()){
        	  file.mkdir();
          }
          return this.xzglService.ZzryexpExcel(realfile, shortfileurl, guid,pd);
    }
    
   /**
    * 
    * 
    * @author  wangguanghua
    * @version  2018-9-13下午3:19:28
    * 导出的方法  实现模板下载
    */
    @RequestMapping("/ZzryexpExcelnew")
	@ResponseBody
    public Object ZzryexpExcelnew() {
          PageData pd = this.getPageData();
          System.err.println("texts+++++++++++++++"+pd.getString("texts"));
          String searchValue = pd.getString("searchJson");
          String guid = pd.getString("id");
          String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
          String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
          String floderPath = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel";
          File file = new File(floderPath);
          if(!file.exists()||!file.isDirectory()){
        	  file.mkdir();
          }
          return this.xzglService.ZzryexpExcelnew(realfile, shortfileurl, guid,pd);
    }
    
    
    /**
     * 退休薪资数据导出
     * @return
     */
    @RequestMapping("/LzryexpExcel")
    @ResponseBody
    public Object LzryexpExcel() {
    	PageData pd = this.getPageData();
    	String searchValue = pd.getString("searchJson");
    	String guid = pd.getString("id");
    	String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
    	String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
    	String floderPath = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel";
        File file = new File(floderPath);
        if(!file.exists()||!file.isDirectory()){
      	  file.mkdir();
        }
    	return this.xzglService.LzryexpExcel(realfile, shortfileurl, guid,pd);
    }
    /**
     * 退休薪资数据导出
     * @return
     */
    @RequestMapping("/LzryexpExcelnew")
    @ResponseBody
    public Object LzryexpExcelnew() {
    	PageData pd = this.getPageData();
    	String searchValue = pd.getString("searchJson");
    	String guid = pd.getString("id");
    	String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
    	String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
    	String floderPath = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\excel";
        File file = new File(floderPath);
        if(!file.exists()||!file.isDirectory()){
      	  file.mkdir();
        }
    	return this.xzglService.LzryexpExcelnew(realfile, shortfileurl, guid,pd);
    }
    /**
	 * 在职薪资数据导入模板
	 * @return
	 */
    @RequestMapping("/Zzxzdrmb")
	@ResponseBody
    public Object Zzxzdrmb() {
          PageData pd = this.getPageData();
          String searchValue = pd.getString("searchJson");
          String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
          String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
          return this.xzglService.Zzxzdrmb(realfile, shortfileurl);
    }
}
