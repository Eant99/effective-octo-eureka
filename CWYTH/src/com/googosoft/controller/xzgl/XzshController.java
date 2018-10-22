package com.googosoft.controller.xzgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xzgl.XzglService;
import com.googosoft.service.xzgl.XzshService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/xzsh")
public class XzshController extends BaseController {

	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="xzshService")
	private XzshService xzshService;
	@Resource(name="xzglService")
	private XzglService xzglService;
	
	
	/**
	 * 跳转到在职人员薪资审核列表页面
	 */
	@RequestMapping(value="/zzxzsh",produces = "text/html;charset=UTF-8")
	public ModelAndView goZzxzsh(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();	
		List list = new ArrayList<Map<String,Object>>();
		mv.addObject("gzyf", xzshService.getLastMon());
		mv.setViewName("xzgl/xzsh/zzxzsh_list");  
		return mv;
	}
	/**
	 * 跳转到退休薪资审核列表页面
	 */
	@RequestMapping(value="/txxzsh",produces = "text/html;charset=UTF-8")
	public ModelAndView goTxxzsh(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();	
		List list = new ArrayList<Map<String,Object>>();
		mv.addObject("gzyf", xzshService.getLastMonLz());
		mv.setViewName("xzgl/xzsh/txxzsh_list");  
		return mv;
	}
	
	/**
	 * 获得在职薪资审核列表数据
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getXzPageList")
	@ResponseBody
	public Object getXzPageList(HttpSession session) throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String strWhere = " AND T.SHZT NOT IN ('0') ";
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		String bmmc = Validate.isNullToDefaultString(pd.getString("bmmc"),"");
		if(rybh.indexOf(")")!=-1){
			rybh = rybh.substring(1,rybh.indexOf(")"));
		}
		if(bmmc.indexOf(")")!=-1){
			bmmc = bmmc.substring(bmmc.indexOf(")")+1);
		}
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		String ryxm = Validate.isNullToDefaultString(pd.getString("ryxm"),"");
		
		sqltext.append(" T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回','合计') AS SHZT, " +
						"TO_CHAR(T.GWGZ,'FM999999999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM999999999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM999999999990.00') AS XZFT," +
						"TO_CHAR(T.WYBT,'FM999999999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM999999999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM999999999990.00') AS JCJX," +
						"TO_CHAR(T.JLJX1,'FM999999999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM999999999990.00') AS BSBT," +
						"TO_CHAR(T.GWBT,'FM999999999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM999999999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM999999999990.00') AS SYBT," +
						"TO_CHAR(T.JHBT,'FM999999999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM999999999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM999999999990.00') AS HTBT," +
						"TO_CHAR(T.QTBT,'FM999999999990.00') AS QTBT," +
						"TO_CHAR(T.BGZ,'FM999999999990.00') AS BGZ,TO_CHAR(T.JKF,'FM999999999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM999999999990.00') AS FZGZL," +
						"TO_CHAR(T.ZSJL,'FM999999999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM999999999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM999999999990.00') AS KHJ," +
						"TO_CHAR(T.DHF,'FM999999999990.00') AS DHF,TO_CHAR(T.BT,'FM999999999990.00') AS BT,TO_CHAR(T.ZFBT,'FM999999999990.00') AS ZFBT," +
						"TO_CHAR(T.YFHJ,'FM99999999999990.00') AS YFHJ," +
						"TO_CHAR(T.JIANGKEF,'FM999999999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM999999999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM999999999990.00') AS JSX," +
						"TO_CHAR(T.QNJSX,'FM999999999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM999999999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM999999999990.00') AS QNJSX2," +
						"TO_CHAR(T.QNJSX3,'FM999999999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM999999999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM999999999990.00') AS BXBT2014N1D10YJSJS," +
						"TO_CHAR(T.JSJS,'FM999999999990.00') AS JSJS," +
						"TO_CHAR(T.ZFJJ,'FM999999999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM999999999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM999999999990.00') AS YLBX," +
						"TO_CHAR(T.BGJJ,'FM999999999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM999999999990.00') AS DKS,TO_CHAR(T.BNSE,'FM999999999990.00') AS BNSE," +
						"TO_CHAR(T.SNSE,'FM999999999990.00') AS SNSE,TO_CHAR(T.BS,'FM999999999990.00') AS BS,TO_CHAR(T.FZ,'FM999999999990.00') AS FZ," +
						"TO_CHAR(T.SYJ,'FM999999999990.00') AS SYJ," +
						"TO_CHAR(T.NQF,'FM999999999990.00') AS NQF,TO_CHAR(T.NQF1,'FM999999999990.00') AS NQF1,TO_CHAR(T.WYF,'FM999999999990.00') AS WYF," +
						"TO_CHAR(T.SBJ,'FM999999999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM999999999990.00') AS SJDCK,TO_CHAR(T.KK,'FM999999999990.00') AS KK," +
						"TO_CHAR(T.YLJ,'FM999999999990.00') AS YLJ,TO_CHAR(T.JK,'FM999999999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM999999999990.00') AS AXYRJ," +
						"TO_CHAR(T.KKHJ,'FM999999999990.00') AS KKHJ," +
						"TO_CHAR(T.SFHJ,'FM999999999990.00') AS SFHJ,T.GZYF,T.BH," +
						"T.XH,TO_CHAR(T.JTF,'FM999999999990.00') AS JTF,TO_CHAR(T.NZJ,'FM999999999990.00') AS NZJ," +
						"TO_CHAR(T.NZJDKS,'FM999999999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM999999999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM999999999990.00') AS KSHJ," +
						"T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM999999999990.00') AS BKYLBX," +
						"TO_CHAR(T.BKSYJ,'FM999999999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM999999999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM999999999990.00') AS BKSBJ," +
						"T.SFDY,T.RDQK ");
		tablename.append("(select  T.GUID, T.RYBH, T.XM,T.BM,T.RYLB,T.RYLX,t.shzt as shzt,T.GWGZ  GWGZ,T.XJGZ  XJGZ,T.XZFT XZFT,T.WYBT WYBT,T.DSZF  DSZF,T.JCJX  JCJX,T.JLJX1 JLJX1,T.BSBT BSBT,T.GWBT GWBT,T.BXQZBBT  BXQZBBT,T.SYBT  SYBT,T.JHBT  JHBT,T.ZJBT  ZJBT,T.HTBT  HTBT,T.QTBT  QTBT,T.BGZ  BGZ,T.JKF  JKF,T.FZGZL  FZGZL,T.ZSJL  ZSJL,T.FDYYJZBBT  FDYYJZBBT,T.KHJ  KHJ,T.DHF  DHF,T.BT  BT,T.ZFBT  ZFBT,T.YFHJ  YFHJ,T.JIANGKEF  JIANGKEF,T.BGZKS  BGZKS,T.JSX JSX,T.QNJSX  QNJSX,T.QNJSX1  QNJSX1,T.QNJSX2 QNJSX2,T.QNJSX3  QNJSX3,T.BJCXJXGZ2014JSJS  BJCXJXGZ2014JSJS,T.BXBT2014N1D10YJSJS  BXBT2014N1D10YJSJS,T.JSJS JSJS,T.ZFJJ  ZFJJ,T.PGJJ  PGJJ,T.YLBX  YLBX,T.BGJJ  BGJJ,T.DKS  DKS,T.BNSE  BNSE,T.SNSE  SNSE,T.BS BS,T.FZ  FZ,T.SYJ SYJ,T.NQF  NQF,T.NQF1  NQF1,T.WYF  WYF,T.SBJ  SBJ,T.SJDCK  SJDCK,T.KK  KK,T.YLJ YLJ,T.JK JK,T.AXYRJ  AXYRJ,T.KKHJ  KKHJ,T.SFHJ  SFHJ,T.GZYF  GZYF,T.BH,T.XH,T.JTF  JTF,T.NZJ  NZJ,T.NZJDKS  NZJDKS,T.GZDKS  GZDKS,T.KSHJ  KSHJ,T.GH,T.SFZB,T.BKYLBX  BKYLBX,T.BKSYJ  BKSYJ,T.BKYLJ  BKYLJ,T.BKSBJ  BKSBJ,T.SFDY,T.RDQK,T.XL from CW_XZB T ");
		
		int count=xzglService.count("CW_XZB", rybh, shzt, gzyf, ryxm, "");
		if(count>0){
			tablename.append(" union ");
			tablename.append("select '1'  GUID, ''  RYBH, ''  XM, ''  BM,''  RYLB,''  RYLX,'4'  SHZT,sum(T.GWGZ)  GWGZ,sum(T.XJGZ)  XJGZ,sum(T.XZFT) XZFT,sum(T.WYBT)  WYBT,sum(T.DSZF)  DSZF,sum(T.JCJX)  JCJX,sum(T.JLJX1)  JLJX1,sum(T.BSBT)  BSBT,sum(T.GWBT)  GWBT,sum(T.BXQZBBT)  BXQZBBT,sum(T.SYBT)  SYBT,sum(T.JHBT)   JHBT,sum(T.ZJBT)  ZJBT,sum(T.HTBT)  HTBT,sum(T.QTBT)  QTBT,sum(T.BGZ)  BGZ,sum(T.JKF)  JKF,sum(T.FZGZL)  FZGZL,sum(T.ZSJL)  ZSJL,sum(T.FDYYJZBBT)  FDYYJZBBT,sum(T.KHJ)  KHJ,sum(T.DHF)  DHF,sum(T.BT)  BT,sum(T.ZFBT)  ZFBT,sum(T.YFHJ)  YFHJ,sum(T.JIANGKEF)  JIANGKEF,sum(T.BGZKS)  BGZKS,sum(T.JSX)  JSX,sum(T.QNJSX)  QNJSX,sum(T.QNJSX1) QNJSX1,sum(T.QNJSX2)  QNJSX2,sum(T.QNJSX3)  QNJSX3,sum(T.BJCXJXGZ2014JSJS)  BJCXJXGZ2014JSJS,sum(T.BXBT2014N1D10YJSJS)  BXBT2014N1D10YJSJS,sum(T.JSJS)  JSJS,sum(T.ZFJJ)  ZFJJ,sum(T.PGJJ)  PGJJ,sum(T.YLBX) YLBX,sum(T.BGJJ)  BGJJ,sum(T.DKS)  DKS,sum(T.BNSE)  BNSE,sum(T.SNSE) AS SNSE,sum(T.BS)  BS,sum(T.FZ) FZ,sum(T.SYJ)  SYJ,sum(T.NQF)  NQF,sum(T.NQF1)  NQF1,sum(T.WYF)  WYF,sum(T.SBJ)  SBJ,sum(T.SJDCK)  SJDCK,sum(T.KK)  KK,sum(T.YLJ)  YLJ,sum(T.JK)  JK,sum(T.AXYRJ)  AXYRJ,sum(T.KKHJ)  KKHJ,sum(T.SFHJ)  SFHJ,''  GZYF,''  BH,''  XH,sum(T.JTF)  JTF,sum(T.NZJ)  NZJ,sum(T.NZJDKS)  NZJDKS,sum(T.GZDKS)  GZDKS,sum(T.KSHJ)  KSHJ,''  GH,''  SFZB,sum(T.BKYLBX)  BKYLBX,sum(T.BKSYJ)  BKSYJ,sum(T.BKYLJ)  BKYLJ,sum(T.BKSBJ)  BKSBJ,''  SFDY,''  RDQK,''  XL  from CW_XZB T where 1=1 AND T.SHZT NOT IN ('0')");
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				tablename.append(" and t.rybh in('"+rybh+"') ");
			}
			if(Validate.noNull(shzt)){
				tablename.append(" and t.shzt like '%"+shzt+"%' ");
			}
			if(Validate.noNull(gzyf)){
				tablename.append(" and t.gzyf like '%"+gzyf+"%'  ");
			}
			if(Validate.noNull(ryxm)){
				tablename.append(" and t.xm like '%"+ryxm+"%'");
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
			rybh = rybh.replace(",", "','");
			strWhere += " and (t.rybh = '"+rybh+"' or t.rybh is null)";
		}
		if(Validate.noNull(shzt)){
			strWhere += " and (t.shzt like '%"+shzt+"%' or t.shzt='4')  ";
		}
		if(Validate.noNull(gzyf)){
			strWhere += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null) ";
		}
		if(Validate.noNull(bmmc)){
			strWhere += " and t.bm = '"+bmmc+"' ";
		}
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	
	/**
	 * 在职薪资审核通过
	 */
	@RequestMapping(value = "/doZztg", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object doZztg() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String gzyf = pd.getString("gzyf");
		String searchJson = pd.getString("searchJson");
		String b = "";
		int i = 0;
		i = xzshService.doZztg(guid,CommonUtils.jsonToSql(searchJson),gzyf);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		}
		return b;
	}
	
	/**
	 * 在职薪资审核退回
	 */
	@RequestMapping(value = "/doZzth", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object doZzth() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String searchJson = pd.getString("searchJson");
		String b = "";
		int i = 0;
		i = xzshService.doZzth(guid, CommonUtils.jsonToSql(searchJson));
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		}
		return b;
	}
	
	/**
	 * 获得离职薪资列表页面数据
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
		String strWhere = " AND L.SHZT NOT IN ('0') ";
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		String bmmc = Validate.isNullToDefaultString(pd.getString("bmbh"),"");
		if(rybh.indexOf(")")!=-1){
			rybh = rybh.substring(1,rybh.indexOf(")"));
		}
		if(bmmc.indexOf(")")!=-1){
			bmmc = CommonUtil.getEndText(bmmc);
		}
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		String ryxm = Validate.isNullToDefaultString(pd.getString("ryxm"),"");
		PageList pageList = new PageList();
		sqltext.append("L.GUID,L.RYBH,L.XM,L.BM,L.RYLB,DECODE(L.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回','合计') AS SHZT," +
				"TO_CHAR(L.JBGZ,'FM999999999990.00') AS JBGZ,TO_CHAR(L.ZJLTF,'FM999999999990.00') AS ZJLTF,TO_CHAR(L.YZWBT,'FM999999999990.00') AS YZWBT," +
				"TO_CHAR(L.GWBT,'FM999999999990.00') AS GWBT,TO_CHAR(L.XZFT,'FM999999999990.00') AS XZFT,TO_CHAR(L.HZBT,'FM999999999990.00') AS HZBT," +
				"TO_CHAR(L.TXTGBF,'FM999999999990.00') AS TXTGBF,TO_CHAR(L.SHBT,'FM999999999990.00') AS SHBT,TO_CHAR(L.XZBT,'FM999999999990.00') AS XZBT," +
				"TO_CHAR(L.WJBT,'FM999999999990.00') AS WJBT,TO_CHAR(L.TXHL,'FM999999999990.00') AS TXHL,TO_CHAR(L.JHBT,'FM999999999990.00') AS JHBT," +
				"TO_CHAR(L.QTBT,'FM999999999990.00') AS QTBT,TO_CHAR(L.LTBT,'FM999999999990.00') AS LTBT,TO_CHAR(L.YZBT,'FM999999999990.00') AS YZBT," +
				"TO_CHAR(L.JCJX,'FM999999999990.00') AS JCJX,TO_CHAR(L.JTF,'FM999999999990.00') AS JTF,TO_CHAR(L.BT,'FM999999999990.00') AS BT," +
				"TO_CHAR(L.ZFBT,'FM999999999990.00') AS ZFBT,TO_CHAR(L.BGZ,'FM999999999990.00') AS BGZ," +
				"TO_CHAR(L.WYBT,'FM999999999990.00') AS WYBT," +
				"TO_CHAR(L.JKF,'FM999999999990.00') AS JKF,TO_CHAR(L.GJF,'FM999999999990.00') AS GJF,TO_CHAR(L.DHF,'FM999999999990.00') AS DHF," +
				"TO_CHAR(L.YFHJ,'FM999999999990.00') AS YFHJ,TO_CHAR(L.FZ,'FM999999999990.00') AS FZ,TO_CHAR(L.SBJ,'FM999999999990.00') AS SBJ," +
				"TO_CHAR(L.NQF,'FM999999999990.00') AS NQF,TO_CHAR(L.NQF1,'FM999999999990.00') AS NQF1,TO_CHAR(L.WYF,'FM999999999990.00') AS WYF," +
				"TO_CHAR(L.JK,'FM999999999990.00') AS JK,TO_CHAR(L.YLJ,'FM999999999990.00') AS YLJ,TO_CHAR(L.BGJJ,'FM999999999990.00') AS BGJJ," +
				"TO_CHAR(L.BS,'FM999999999990.00') AS BS,TO_CHAR(L.SJDCK,'FM999999999990.00') AS SJDCK,TO_CHAR(L.SYJ,'FM999999999990.00') AS SYJ," +
				"TO_CHAR(L.KKHJ,'FM999999999990.00') AS KKHJ,TO_CHAR(L.SFHJ,'FM999999999990.00') AS SFHJ,L.GZYF," +
				"L.BH,TO_CHAR(L.JSX,'FM999999999990.00') AS JSX,TO_CHAR(L.ZFJJ,'FM999999999990.00') AS ZFJJ," +
				"TO_CHAR(L.NZJ,'FM999999999990.00') AS NZJ,TO_CHAR(L.KK,'FM999999999990.00') AS KK,XH");
		tablename.append(" (select L.GUID,L.RYBH,L.XM,L.BM,L.RYLB AS RYLB,L.SHZT AS SHZT,TO_CHAR(L.JBGZ,'FM999999999990.00') AS JBGZ,TO_CHAR(L.ZJLTF,'FM999999999990.00') AS ZJLTF,TO_CHAR(L.YZWBT,'FM999999999990.00') AS YZWBT,TO_CHAR(L.GWBT,'FM999999999990.00') AS GWBT,TO_CHAR(L.XZFT,'FM999999999990.00') AS XZFT,TO_CHAR(L.HZBT,'FM999999999990.00') AS HZBT,TO_CHAR(L.TXTGBF,'FM999999999990.00') AS TXTGBF,TO_CHAR(L.SHBT,'FM999999999990.00') AS SHBT,TO_CHAR(L.XZBT,'FM999999999990.00') AS XZBT,TO_CHAR(L.WJBT,'FM999999999990.00') AS WJBT,TO_CHAR(L.TXHL,'FM999999999990.00') AS TXHL,TO_CHAR(L.JHBT,'FM999999999990.00') AS JHBT,TO_CHAR(L.QTBT,'FM999999999990.00') AS QTBT,TO_CHAR(L.LTBT,'FM999999999990.00') AS LTBT,TO_CHAR(L.YZBT,'FM999999999990.00') AS YZBT,TO_CHAR(L.JCJX,'FM999999999990.00') AS JCJX,TO_CHAR(L.JTF,'FM999999999990.00') AS JTF,TO_CHAR(L.BT,'FM999999999990.00') AS BT,TO_CHAR(L.ZFBT,'FM999999999990.00') AS ZFBT,TO_CHAR(L.BGZ,'FM999999999990.00') AS BGZ,TO_CHAR(L.WYBT,'FM999999999990.00') AS WYBT,TO_CHAR(L.JKF,'FM999999999990.00') AS JKF,TO_CHAR(L.GJF,'FM999999999990.00') AS GJF,TO_CHAR(L.DHF,'FM999999999990.00') AS DHF,TO_CHAR(L.YFHJ,'FM999999999990.00') AS YFHJ,TO_CHAR(L.FZ,'FM999999999990.00') AS FZ,TO_CHAR(L.SBJ,'FM999999999990.00') AS SBJ,TO_CHAR(L.NQF,'FM999999999990.00') AS NQF,TO_CHAR(L.NQF1,'FM999999999990.00') AS NQF1,TO_CHAR(L.WYF,'FM999999999990.00') AS WYF,TO_CHAR(L.JK,'FM999999999990.00') AS JK,TO_CHAR(L.YLJ,'FM999999999990.00') AS YLJ,TO_CHAR(L.BGJJ,'FM999999999990.00') AS BGJJ,TO_CHAR(L.BS,'FM999999999990.00') AS BS,TO_CHAR(L.SJDCK,'FM999999999990.00') AS SJDCK,TO_CHAR(L.SYJ,'FM999999999990.00') AS SYJ,TO_CHAR(L.KKHJ,'FM999999999990.00') AS KKHJ,TO_CHAR(L.SFHJ,'FM999999999990.00') AS SFHJ,L.GZYF,L.BH,TO_CHAR(L.JSX,'FM999999999990.00') AS JSX,TO_CHAR(L.ZFJJ,'FM999999999990.00') AS ZFJJ,TO_CHAR(L.NZJ,'FM999999999990.00') AS NZJ,TO_CHAR(L.KK,'FM999999999990.00') AS KK,(case L.BM WHEN '离休人员' THEN '1' WHEN '退休人员' THEN '2' WHEN '遗属' THEN '3' WHEN '临时工' THEN '4'else '5' END)as xh from  CW_LZXZB L  ");
		int count =xzglService.count("CW_LZXZB", rybh, shzt, gzyf, ryxm,"");
		if(count>0){
			tablename.append(" union ");
			tablename.append(" select '1' as GUID,'' as RYBH,'' as XM,'' as BM,'' as RYLB,'4' AS SHZT,TO_CHAR(sum(L.JBGZ),'FM999999999990.00') AS JBGZ,TO_CHAR(sum(L.ZJLTF),'FM999999999990.00') AS ZJLTF,TO_CHAR(sum(L.YZWBT),'FM999999999990.00') AS YZWBT,TO_CHAR(sum(L.GWBT),'FM999999999990.00') AS GWBT,TO_CHAR(sum(L.XZFT),'FM999999999990.00') AS XZFT,TO_CHAR(sum(L.HZBT),'FM999999999990.00') AS HZBT,TO_CHAR(sum(L.TXTGBF),'FM999999999990.00') AS TXTGBF,TO_CHAR(sum(L.SHBT),'FM999999999990.00') AS SHBT,TO_CHAR(sum(L.XZBT),'FM999999999990.00') AS XZBT,TO_CHAR(sum(L.WJBT),'FM999999999990.00') AS WJBT,TO_CHAR(sum(L.TXHL),'FM999999999990.00') AS TXHL,TO_CHAR(sum(L.JHBT),'FM999999999990.00') AS JHBT,TO_CHAR(sum(L.QTBT),'FM999999999990.00') AS QTBT,TO_CHAR(sum(L.LTBT),'FM999999999990.00') AS LTBT,TO_CHAR(sum(L.YZBT),'FM999999999990.00') AS YZBT,TO_CHAR(sum(L.JCJX),'FM999999999990.00') AS JCJX,TO_CHAR(sum(L.JTF),'FM999999999990.00') AS JTF,TO_CHAR(sum(L.BT),'FM999999999990.00') AS BT,TO_CHAR(sum(L.ZFBT),'FM999999999990.00') AS ZFBT,TO_CHAR(sum(L.BGZ),'FM999999999990.00') AS BGZ,TO_CHAR(sum(L.WYBT),'FM999999999990.00') AS WYBT,TO_CHAR(sum(L.JKF),'FM999999999990.00') AS JKF,TO_CHAR(sum(L.GJF),'FM999999999990.00') AS GJF,TO_CHAR(sum(L.DHF),'FM999999999990.00') AS DHF,TO_CHAR(sum(L.YFHJ),'FM999999999990.00') AS YFHJ,TO_CHAR(sum(L.FZ),'FM999999999990.00') AS FZ,TO_CHAR(sum(L.SBJ),'FM999999999990.00') AS SBJ,TO_CHAR(sum(L.NQF),'FM999999999990.00') AS NQF,TO_CHAR(sum(L.NQF1),'FM999999999990.00') AS NQF1,TO_CHAR(sum(L.WYF),'FM999999999990.00') AS WYF,TO_CHAR(sum(L.JK),'FM999999999990.00') AS JK,TO_CHAR(sum(L.YLJ),'FM999999999990.00') AS YLJ,TO_CHAR(sum(L.BGJJ),'FM999999999990.00') AS BGJJ,TO_CHAR(sum(L.BS),'FM999999999990.00') AS BS,TO_CHAR(sum(L.SJDCK),'FM999999999990.00') AS SJDCK,TO_CHAR(sum(L.SYJ),'FM999999999990.00') AS SYJ,TO_CHAR(sum(L.KKHJ),'FM999999999990.00') AS KKHJ,TO_CHAR(sum(L.SFHJ),'FM999999999990.00') AS SFHJ,'' as GZYF,'' as BH,TO_CHAR(sum(L.JSX),'FM999999999990.00') AS JSX,TO_CHAR(sum(L.ZFJJ),'FM999999999990.00') AS ZFJJ,TO_CHAR(sum(L.NZJ),'FM999999999990.00') AS NZJ,TO_CHAR(sum(L.KK),'FM999999999990.00') AS KK,'' AS XH from  CW_LZXZB L where 1=1 AND L.SHZT NOT IN ('0') ");
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				tablename.append(" and l.rybh = '"+rybh+"' ");
			}
			if(Validate.noNull(shzt)){
				tablename.append(" and l.shzt like '%"+shzt+"%' ");
			}
			if(Validate.noNull(gzyf)){
				tablename.append(" and l.gzyf like '%"+gzyf+"%'  ");
			}
			if(Validate.noNull(ryxm)){
				tablename.append(" and l.xm like '%"+ryxm+"%'");
			}
		}
		tablename.append(" ) l ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("L.GUID");//主键
		//设置WHERE条件
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			strWhere += " and (l.rybh ='"+rybh+"' or l.rybh is null)";
		}
		if(Validate.noNull(shzt)){
			strWhere += " and (l.shzt like '%"+shzt+"%' or l.shzt='4')  ";
		}
		if(Validate.noNull(gzyf)){
			strWhere += " and (l.gzyf like '%"+gzyf+"%' or l.gzyf is null) ";
		}
		if(Validate.noNull(bmmc)){
			strWhere += " and  l.bm = '"+bmmc+"'  ";
		}
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	
	/**
	 * 离职薪资审核通过
	 */
	@RequestMapping(value = "/doTxtg", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object doTxtg() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String gzyf = pd.getString("gzyf");
		String searchJson = pd.get("searchJson") + "";
		String b = "";
		int i = 0;
		i = xzshService.doTxtg(guid, CommonUtils.jsonToSql(searchJson),gzyf);
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		}
		return b;
	}
	
	/**
	 * 离职薪资审核退回
	 */
	@RequestMapping(value = "/doTxth", produces = "text/json;charset=UTF-8")
	@ResponseBody
	public Object doTxth() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		String searchJson = pd.getString("searchJson");
		String b = "";
		int i = 0;
		i = xzshService.doTxth(guid, CommonUtils.jsonToSql(searchJson));
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"操作成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"操作失败！\"}";
		}
		return b;
	}
}
