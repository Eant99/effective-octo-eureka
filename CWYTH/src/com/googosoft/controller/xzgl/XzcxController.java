package com.googosoft.controller.xzgl;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xzgl.XzglService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/xzcx")
public class XzcxController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="xzglService")
	private XzglService xzglService;
	
	/**
	 * 跳转到工资查询列表页面
	 * @return
	 */
	@RequestMapping(value="/goGzcxPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goGzcxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();	
		String zt = Validate.isNullToDefaultString(pd.getString("zt"), "0");
		String url = "";
		String gzyf = "";
		if("0".equals(zt)){
			url = "xzgl/xzcx/gzcx_list";
			gzyf = xzglService.getLastMon();
		}else{
			url = "xzgl/xzcx/gzcx_list2";
			gzyf = xzglService.getLastMonLz();
		}
		mv.addObject("gzyf", gzyf);
		mv.setViewName(url);  
		return mv;
	}
	/**
	 * 查询个人薪资
	 * @return
	 */
	@RequestMapping(value="/goGrxzcxPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goGrxzcxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("xzgl/xzcx/grxz_list");
		return mv;
	}
	/**
	 * 跳转到部门工资汇总列表页面
	 * @return
	 */
	@RequestMapping(value="/goGzhzPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goGzhzPage(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("gzyf", xzglService.getLastMon());
		mv.setViewName("xzgl/xzcx/gzhz_list");  
		return mv;
	}
	/**
	 * 跳转到工资发放页面
	 * @return
	 */
	@RequestMapping(value="/goGzffcxPage",produces = "text/html;charset=UTF-8")
	public ModelAndView goGzffcxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.addObject("gzyf", xzglService.getLastMon());
		mv.setViewName("xzgl/xzcx/gzffcx_list");  
		return mv;
	}
	/**
	 * 获得部门工资汇总查询列表数据
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGzhzPageList")
	@ResponseBody
	public Object getGzhzPageList(HttpSession session) throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String bmmc = Validate.isNullToDefaultString(pd.getString("bmmc"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		if(Validate.noNull(gzyf)){
			sqltext.append("GZYF,");
		}
		sqltext.append("BM,RS,GWGZ,XJGZ,XZFT,WYBT,DSZF,JCJX,JLJX1,BSBT,GWBT,BGZ,ZFJJ,YLBX,DKS,FZ,SYJ,SBJ,YLJ,KK,KKHJ,SFHJ,YFHJ ");
		tablename.append(" (SELECT T.BM,COUNT(1) AS RS, ");
		if(Validate.noNull(gzyf)){
			tablename.append("T.GZYF,");
		}
		tablename.append("  TO_CHAR(sum(nvl(T.YFHJ, 0)), 'FM99999999990.00') AS YFHJ,  ");
		tablename.append("  TO_CHAR(sum(nvl(T.GWGZ, 0)), 'FM99999999990.00') AS GWGZ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.XJGZ, 0)), 'FM99999999990.00') AS XJGZ, ");
		tablename.append("  TO_CHAR(sum(nvl(T.XZFT, 0)), 'FM99999999990.00') AS XZFT, ");
		tablename.append(" TO_CHAR(sum(nvl(T.WYBT, 0)), 'FM99999999990.00') AS WYBT, ");
		tablename.append("  TO_CHAR(sum(nvl(T.DSZF, 0)), 'FM99999999990.00') AS DSZF, ");
		tablename.append(" TO_CHAR(sum(nvl(T.JCJX, 0)), 'FM99999999990.00') AS JCJX, ");
		tablename.append(" TO_CHAR(sum(nvl(T.JLJX1, 0)), 'FM99999999990.00') AS JLJX1, ");
		tablename.append(" TO_CHAR(sum(nvl(T.BSBT, 0)), 'FM99999999990.00') AS BSBT, ");
		tablename.append(" TO_CHAR(sum(nvl(T.GWBT, 0)), 'FM99999999990.00') AS GWBT, ");
		tablename.append(" TO_CHAR(sum(nvl(T.BGZ, 0)), 'FM99999999990.00') AS BGZ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.ZFJJ, 0)), 'FM99999999990.00') AS ZFJJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.YLBX, 0)), 'FM99999999990.00') AS YLBX, ");
		tablename.append(" TO_CHAR(sum(nvl(T.DKS, 0)), 'FM99999999990.00') AS DKS, ");
		tablename.append(" TO_CHAR(sum(nvl(T.FZ, 0)), 'FM99999999990.00') AS FZ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.SYJ, 0)), 'FM99999999990.00') AS SYJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.SBJ, 0)), 'FM99999999990.00') AS SBJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.YLJ, 0)), 'FM99999999990.00') AS YLJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.KK, 0)), 'FM99999999990.00') AS KK, ");
		tablename.append(" TO_CHAR(sum(nvl(T.KKHJ, 0)), 'FM99999999990.00') AS KKHJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.SFHJ, 0)), 'FM99999999990.00') AS SFHJ");
		tablename.append(" from CW_XZB T  GROUP BY T.BM");
		if(Validate.noNull(gzyf)){
			tablename.append(", T.GZYF ");
		}
		tablename.append(" union ");
		tablename.append(" SELECT '合计' as BM,sum(count(*)) as rs, ");
		if(Validate.noNull(gzyf)){
			tablename.append("'' as GZYF, ");
		}
		tablename.append("   TO_CHAR(sum(sum(nvl(T.YFHJ, 0))), 'FM99999999990.00') AS YFHJ, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.GWGZ, 0))), 'FM99999999990.00') AS GWGZ, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.XJGZ, 0))), 'FM99999999990.00') AS XJGZ, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.XZFT, 0))), 'FM99999999990.00') AS XZFT, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.WYBT, 0))), 'FM99999999990.00') AS WYBT, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.DSZF, 0))), 'FM99999999990.00') AS DSZF, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.JCJX, 0))), 'FM99999999990.00') AS JCJX, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.JLJX1, 0))), 'FM99999999990.00') AS JLJX1, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.BSBT, 0))), 'FM99999999990.00') AS BSBT, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.GWBT, 0))), 'FM99999999990.00') AS GWBT, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.BGZ, 0))), 'FM99999999990.00') AS BGZ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.ZFJJ, 0))), 'FM99999999990.00') AS ZFJJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.YLBX, 0))), 'FM99999999990.00') AS YLBX, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.DKS, 0))), 'FM99999999990.00') AS DKS, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.FZ, 0))), 'FM99999999990.00') AS FZ, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.SYJ, 0))), 'FM99999999990.00') AS SYJ, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.SBJ, 0))), 'FM99999999990.00') AS SBJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.YLJ, 0))), 'FM99999999990.00') AS YLJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.KK, 0))), 'FM99999999990.00') AS KK, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.KKHJ, 0))), 'FM99999999990.00') AS KKHJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.SFHJ, 0))), 'FM99999999990.00') AS SFHJ ");
		tablename.append(" from CW_XZB T  where 1=1 ");
		if(Validate.noNull(gzyf)){
			tablename.append(" and t.gzyf like '%"+gzyf+"%' ");
		}
		if(Validate.noNull(bmmc)){
			tablename.append(" and t.bm like '%"+bmmc+"%' ");
		}
		tablename.append("GROUP BY T.BM");
		if(Validate.noNull(gzyf)){
			tablename.append(", T.GZYF ");
		}
		tablename.append(") T");
//		tablename.append("(SELECT T.BM, T.GZYF, COUNT(1) AS RS, ");
//		tablename.append("TO_CHAR(sum(nvl(T.YFHJ,0)),'FM9999990.00') AS YFHJ, ");
//		tablename.append("TO_CHAR(sum(nvl(T.GWGZ,0)),'FM9999990.00') AS GWGZ, TO_CHAR(sum(nvl(T.XJGZ,0)),'FM9999990.00') AS XJGZ, ");
//		tablename.append("TO_CHAR(sum(nvl(T.XZFT,0)),'FM9999990.00') AS XZFT, TO_CHAR(sum(nvl(T.WYBT,0)),'FM9999990.00') AS WYBT, ");
//		tablename.append("TO_CHAR(sum(nvl(T.DSZF,0)),'FM9999990.00') AS DSZF, TO_CHAR(sum(nvl(T.JCJX,0)),'FM9999990.00') AS JCJX, ");
//		tablename.append("TO_CHAR(sum(nvl(T.JLJX1,0)),'FM9999990.00') AS JLJX1, TO_CHAR(sum(nvl(T.BSBT,0)),'FM9999990.00') AS BSBT, ");
//		tablename.append("TO_CHAR(sum(nvl(T.GWBT,0)),'FM9999990.00') AS GWBT, TO_CHAR(sum(nvl(T.BGZ,0)),'FM9999990.00') AS BGZ, ");
//		tablename.append("TO_CHAR(sum(nvl(T.ZFJJ,0)),'FM9999990.00') AS ZFJJ, TO_CHAR(sum(nvl(T.YLBX,0)),'FM9999990.00') AS YLBX, ");
//		tablename.append("TO_CHAR(sum(nvl(T.DKS,0)),'FM9999990.00') AS DKS, TO_CHAR(sum(nvl(T.FZ,0)),'FM9999990.00') AS FZ, ");
//		tablename.append("TO_CHAR(sum(nvl(T.SYJ,0)),'FM9999990.00') AS SYJ, TO_CHAR(sum(nvl(T.SBJ,0)),'FM9999990.00') AS SBJ, ");
//		tablename.append("TO_CHAR(sum(nvl(T.YLJ,0)),'FM9999990.00') AS YLJ, TO_CHAR(sum(nvl(T.KK,0)),'FM9999990.00') AS KK, ");
//		tablename.append("TO_CHAR(sum(nvl(T.KKHJ,0)),'FM9999990.00') AS KKHJ, TO_CHAR(sum(nvl(T.SFHJ,0)),'FM9999990.00') AS SFHJ ");
//		tablename.append("from CW_XZB T where T.SHZT LIKE '%" + pd.getString("shzt") + "%' GROUP BY T.BM, T.GZYF) T");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("T.GWGZ");//主键
		//设置WHERE条件
		String strWhere = "  ";
		if(Validate.noNull(gzyf)){
			strWhere += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null)";
		}
		if(Validate.noNull(bmmc)){
			strWhere += " and (t.bm like '%"+bmmc+"%' or t.bm is null)";
		}
		pageList.setStrWhere(strWhere); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	/**
	 * 获得在职薪资列表数据
	 * 获得工资发放查询列表数据
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {		
	    PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		String flag = pd.getString("flag");
		sqltext.append(" T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT, " );
		sqltext.append("TO_CHAR(T.GWGZ,'FM9999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM9999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM9999990.00') AS XZFT," );
		sqltext.append("TO_CHAR(T.WYBT,'FM9999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM9999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM9999990.00') AS JCJX," );
		sqltext.append("TO_CHAR(T.JLJX1,'FM9999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM9999990.00') AS BSBT," );
		sqltext.append("TO_CHAR(T.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM9999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM9999990.00') AS SYBT," );
		sqltext.append("TO_CHAR(T.JHBT,'FM9999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM9999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM9999990.00') AS HTBT," );
		sqltext.append("TO_CHAR(T.QTBT,'FM9999990.00') AS QTBT," );
		sqltext.append("TO_CHAR(T.BGZ,'FM9999990.00') AS BGZ,TO_CHAR(T.JKF,'FM9999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM9999990.00') AS FZGZL," );
		sqltext.append("TO_CHAR(T.ZSJL,'FM9999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM9999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM9999990.00') AS KHJ," );
		sqltext.append("TO_CHAR(T.DHF,'FM9999990.00') AS DHF,TO_CHAR(T.BT,'FM9999990.00') AS BT,TO_CHAR(T.ZFBT,'FM9999990.00') AS ZFBT," );
		sqltext.append("TO_CHAR(T.YFHJ,'FM999999.00') AS YFHJ," );
		sqltext.append("TO_CHAR(T.JIANGKEF,'FM9999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM9999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM9999990.00') AS JSX," );
		sqltext.append("TO_CHAR(T.QNJSX,'FM9999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM9999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM9999990.00') AS QNJSX2," );
		sqltext.append("TO_CHAR(T.QNJSX3,'FM9999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM9999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM9999990.00') AS BXBT2014N1D10YJSJS," );
		sqltext.append("TO_CHAR(T.JSJS,'FM9999990.00') AS JSJS," );
        sqltext.append("TO_CHAR(T.ZFJJ,'FM9999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM9999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM9999990.00') AS YLBX," );
        sqltext.append("TO_CHAR(T.BGJJ,'FM9999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM9999990.00') AS DKS,TO_CHAR(T.BNSE,'FM9999990.00') AS BNSE," );
        sqltext.append("TO_CHAR(T.SNSE,'FM9999990.00') AS SNSE,TO_CHAR(T.BS,'FM9999990.00') AS BS,TO_CHAR(T.FZ,'FM9999990.00') AS FZ," );
        sqltext.append("TO_CHAR(T.SYJ,'FM9999990.00') AS SYJ," );
        sqltext.append("TO_CHAR(T.NQF,'FM9999990.00') AS NQF,TO_CHAR(T.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(T.WYF,'FM9999990.00') AS WYF," );
        sqltext.append("TO_CHAR(T.SBJ,'FM9999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(T.KK,'FM9999990.00') AS KK," );
        sqltext.append("TO_CHAR(T.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(T.JK,'FM9999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM9999990.00') AS AXYRJ," );
        sqltext.append("TO_CHAR(T.KKHJ,'FM9999990.00') AS KKHJ," );
        sqltext.append("TO_CHAR(T.SFHJ,'FM9999990.00') AS SFHJ,T.GZYF,T.BH," );
        sqltext.append("T.XH,TO_CHAR(T.JTF,'FM9999990.00') AS JTF,TO_CHAR(T.NZJ,'FM9999990.00') AS NZJ," );
        sqltext.append("TO_CHAR(T.NZJDKS,'FM9999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM9999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM9999990.00') AS KSHJ," );
        sqltext.append("T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM9999990.00') AS BKYLBX," );
        sqltext.append("TO_CHAR(T.BKSYJ,'FM9999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM9999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM9999990.00') AS BKSBJ," );
        sqltext.append("T.SFDY,T.RDQK ");
		tablename.append(" CW_XZB T");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("T.GUID");//主键
		//设置WHERE条件
		if(Validate.noNull(flag)) {
			pageList.setStrWhere(" and t.rybh ='"+LUser.getRybh()+"'"); 
		}else {
			pageList.setStrWhere(" and t.shzt like '%"+pd.getString("shzt")+"%'"); 
		}
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
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
		PageList pageList = new PageList();
		sqltext.append("t.GUID,t.RYBH,t.XM,t.BM,t.RYLB,DECODE(t.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT," +
				"TO_CHAR(t.JBGZ,'FM9999990.00') AS JBGZ,TO_CHAR(t.ZJLTF,'FM9999990.00') AS ZJLTF,TO_CHAR(t.YZWBT,'FM9999990.00') AS YZWBT," +
				"TO_CHAR(t.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(t.XZFT,'FM9999990.00') AS XZFT,TO_CHAR(t.HZBT,'FM9999990.00') AS HZBT," +
				"TO_CHAR(t.TXTGBF,'FM9999990.00') AS TXTGBF,TO_CHAR(t.SHBT,'FM9999990.00') AS SHBT,TO_CHAR(t.XZBT,'FM9999990.00') AS XZBT," +
				"TO_CHAR(t.WJBT,'FM9999990.00') AS WJBT,TO_CHAR(t.TXHL,'FM9999990.00') AS TXHL,TO_CHAR(t.JHBT,'FM9999990.00') AS JHBT," +
				"TO_CHAR(t.QTBT,'FM9999990.00') AS QTBT,TO_CHAR(t.LTBT,'FM9999990.00') AS LTBT,TO_CHAR(t.YZBT,'FM9999990.00') AS YZBT," +
				"TO_CHAR(t.JCJX,'FM9999990.00') AS JCJX,TO_CHAR(t.JTF,'FM9999990.00') AS JTF,TO_CHAR(t.BT,'FM9999990.00') AS BT," +
				"TO_CHAR(t.ZFBT,'FM9999990.00') AS ZFBT,TO_CHAR(t.BGZ,'FM9999990.00') AS BGZ," +
				"TO_CHAR(t.WYBT,'FM9999990.00') AS WYBT," +
				"TO_CHAR(t.JKF,'FM9999990.00') AS JKF,TO_CHAR(t.GJF,'FM9999990.00') AS GJF,TO_CHAR(t.DHF,'FM9999990.00') AS DHF," +
				"TO_CHAR(t.YFHJ,'FM9999990.00') AS YFHJ,TO_CHAR(t.FZ,'FM9999990.00') AS FZ,TO_CHAR(t.SBJ,'FM9999990.00') AS SBJ," +
				"TO_CHAR(t.NQF,'FM9999990.00') AS NQF,TO_CHAR(t.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(t.WYF,'FM9999990.00') AS WYF," +
				"TO_CHAR(t.JK,'FM9999990.00') AS JK,TO_CHAR(t.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(t.BGJJ,'FM9999990.00') AS BGJJ," +
				"TO_CHAR(t.BS,'FM9999990.00') AS BS,TO_CHAR(t.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(t.SYJ,'FM9999990.00') AS SYJ," +
				"TO_CHAR(t.KKHJ,'FM9999990.00') AS KKHJ,TO_CHAR(t.SFHJ,'FM9999990.00') AS SFHJ,t.GZYF," +
				"t.BH,TO_CHAR(t.JSX,'FM9999990.00') AS JSX,TO_CHAR(t.ZFJJ,'FM9999990.00') AS ZFJJ," +
				"TO_CHAR(t.NZJ,'FM9999990.00') AS NZJ,TO_CHAR(t.KK,'FM9999990.00') AS KK");
		tablename.append(" CW_LZXZB t");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("t.GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(" and t.shzt like '%"+pd.getString("shzt")+"%'"); 
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	/**
	 * 工资查询列表
	 * @param session
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getGzcxPageList")
	@ResponseBody
	public Object getGzcxPageList(HttpSession session) throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		tablename.append("(SELECT GUID,RYBH,XM,SFZH,SDXM,STIME,ETIME,TO_CHAR(T.YFHJ,'FM9999990.00')YFHJ,TO_CHAR(T.JFYE,'FM9999990.00')JFYE,TO_CHAR(T.YNJSE,'FM9999990.00')YNJSE,TO_CHAR(G.SL,'FM9999990.00') AS SL,TO_CHAR(G.SSKCS,'FM9999990.00') AS SSKC,TO_CHAR((YNJSE*G.SL/100-G.SSKCS),'FM9999990.00') AS YNSE FROM (");
			tablename.append("SELECT T.GUID,T.RYBH,T.XM,R.SFZH,'工资' AS SDXM,");
				tablename.append("TO_CHAR(TO_DATE(T.GZYF,'YYYY.MM'),'YYYYMMDD') AS STIME,");
				tablename.append("TO_CHAR(LAST_DAY(TO_DATE(T.GZYF,'YYYY.MM')),'YYYYMMDD') AS ETIME,");
				tablename.append("T.YFHJ,'' AS JFYE,");
				tablename.append("CASE WHEN T.YFHJ>3500 THEN (T.YFHJ-3500) ELSE 0 END AS YNJSE ");
			tablename.append("FROM CW_XZB T ");
				tablename.append("LEFT JOIN CW_JZGXXB R ON T.RYBH=R.XH ");
			tablename.append("UNION ALL ");
			tablename.append("SELECT T.GUID,T.RYBH,T.XM,R.SFZH,'工资' AS SDXM,");
				tablename.append("TO_CHAR(TO_DATE(T.GZYF,'YYYY.MM'),'YYYYMMDD') AS STIME,");
				tablename.append("TO_CHAR(LAST_DAY(TO_DATE(T.GZYF,'YYYY.MM')),'YYYYMMDD') AS ETIME,");
				tablename.append("T.YFHJ,'' AS JFYE,");
				tablename.append("CASE WHEN T.YFHJ>3500 THEN (T.YFHJ-3500) ELSE 0 END AS YNJSE ");
			tablename.append("FROM CW_LZXZB T ");
				tablename.append("LEFT JOIN CW_JZGXXB R ON T.RYBH=R.XH ");
		tablename.append(") T ");
			tablename.append("LEFT JOIN CW_GRSDSCELJSLB G ");
				tablename.append("ON TO_NUMBER(NVL(G.QYYNSBZL,'0'))<T.YNJSE AND T.YNJSE<=TO_NUMBER(NVL(G.QYYNSBZH,'999999999999999999'))) T");
		pageList.setSqlText("*");
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("t.GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
	/**
	 * 工资查询--导出个税表
	 * @return
	 */
	@RequestMapping(value="/expGzcxGsb",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expGzcxGsb(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		PageList pageList = new PageList();
		StringBuffer tablename = new StringBuffer();
		tablename.append("(select guid,rybh,xm,sfzh,sdxm,gzyf,stime,etime,to_char(t.yfhj,'fm9999990.00')yfhj,to_char(t.jfye,'fm9999990.00')jfye,to_char(t.ynjse,'fm9999990.00')ynjse,to_char(g.sl,'fm9999990.00') as sl,to_char(g.sskcs,'fm9999990.00') as sskc,to_char((ynjse*g.sl/100-g.sskcs),'fm9999990.00') as ynse from (");
			tablename.append("select t.guid,t.rybh,t.xm,r.sfzh,'工资' as sdxm, t.gzyf,");
				tablename.append("to_char(to_date(t.gzyf,'yyyy.mm'),'yyyymmdd') as stime,");
				tablename.append("to_char(last_day(to_date(t.gzyf,'yyyy.mm')),'yyyymmdd') as etime,");
				tablename.append("t.yfhj,'' as jfye,");
				tablename.append("case when t.yfhj>3500 then (t.yfhj-3500) else 0 end as ynjse ");
			tablename.append("from cw_xzb t ");
				tablename.append("left join cw_jzgxxb r on t.gh=r.xh ");
			tablename.append("where t.shzt = '2' ");
//			tablename.append("union all ");
//			tablename.append("select t.guid,t.rybh,t.xm,r.sfzh,'工资' as sdxm,");
//				tablename.append("to_char(to_date(t.gzyf,'yyyy.mm'),'yyyymmdd') as stime,");
//				tablename.append("to_char(last_day(to_date(t.gzyf,'yyyy.mm')),'yyyymmdd') as etime,");
//				tablename.append("t.yfhj,'' as jfye,");
//				tablename.append("case when t.yfhj>3500 then (t.yfhj-3500) else 0 end as ynjse ");
//			tablename.append("from cw_lzxzb t ");
//				tablename.append("left join cw_jzgxxb r on t.rybh=r.xh ");
//			tablename.append("where t.shzt = '2' ");
		tablename.append(") t ");
			tablename.append("left join cw_grsdsceljslb g ");
				tablename.append("on to_number(nvl(g.qyynsbzl,'0'))<t.ynjse and t.ynjse<=to_number(nvl(g.qyynsbzh,'999999999999999999'))) t");
		pageList.setSqlText("*");
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("t.guid");//主键
		//where条件
		pageList.setStrWhere(CommonUtils.jsonToSql(searchJson)); 
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			pageList.setStrWhere(pageList.getStrWhere() + " and guid in ('"+id.replace(",", "','")+"') ");
		}
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("XM");
		m5.setShowname("姓名");
		mlist.add(m5);
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("SFZH");
		m1.setShowname("证件号码");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("SDXM");
		m2.setShowname("所得项目");
		mlist.add(m2);
		M_largedata m0 = new M_largedata();
		m0.setColtype("String");
		m0.setName("STIME");
		m0.setShowname("所得期间（起）");
		mlist.add(m0);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("ETIME");
		m3.setShowname("所得期间（止）");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("YFHJ");
		m4.setShowname("收入额");
		mlist.add(m4);
		
		m4 = null;
		m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("JFYE");
		m4.setShowname("减费用额");
		mlist.add(m4);
		m4 = null;
		m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("YNJSE");
		m4.setShowname("应纳税所得额");
		mlist.add(m4);
		m4 = null;
		m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("SL");
		m4.setShowname("税率");
		mlist.add(m4);
		m4 = null;
		m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("SSKC");
		m4.setShowname("速算扣除数");
		mlist.add(m4);
		m4 = null;
		m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("YNSE");
		m4.setShowname("应缴纳税额");
		mlist.add(m4);
		m4 = null;
		m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("YNSE");
		m4.setShowname("已扣缴税款");
		mlist.add(m4);
		//导出方法
		String sql = "select " + pageList.getSqlText() + " from " + pageList.getTableName() + " where 1=1 "+ pageList.getStrWhere() + " order by rybh ";
		pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 工资查询--导出银行代发表
	 * @return
	 */
	@RequestMapping(value="/expGzcxYhdf",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expGzcxYhdf(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		String zt=pd.getString("zt");
		PageList pageList = new PageList();
		StringBuffer tablename = new StringBuffer();
		tablename.append("(");
		//判断人员在职还是离职
		if(zt.equals("0")) {
			tablename.append("select t.guid,t.rybh,t.xm,t.sfhj,t.gzyf,");
			tablename.append("(select z.khyh from cw_jsyhzhb z left join cw_jzgxxb r on z.jsbh = r.guid where t.gh=r.xh and rownum <= 1) as khyh, ");
			tablename.append("(select z.khyhzh from cw_jsyhzhb z left join cw_jzgxxb r on z.jsbh = r.guid where t.gh=r.xh and rownum <= 1) as yhzh ");
			tablename.append("from cw_xzb t ");
			tablename.append("where t.shzt = '2' ");}
		else {
			tablename.append("select t.guid,t.rybh,t.xm,t.sfhj,t.gzyf,");
			tablename.append("(select z.khyh from cw_jsyhzhb z left join cw_jzgxxb r on z.jsbh = r.guid where t.gh=r.xh and rownum <= 1) as khyh, ");
			tablename.append("(select z.khyhzh from cw_jsyhzhb z left join cw_jzgxxb r on z.jsbh = r.guid where t.rybh=r.xh and rownum <= 1) as yhzh ");
			tablename.append("from cw_lzxzb t ");
			tablename.append("where t.shzt = '2' ");
			}
		tablename.append(") t ");
		pageList.setSqlText("*");
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("t.guid");//主键
		//where条件
		pageList.setStrWhere(CommonUtils.jsonToSql(searchJson)); 
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			pageList.setStrWhere(pageList.getStrWhere() + " and guid in ('"+id.replace(",", "','")+"') ");
		}
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("XM");
		m5.setShowname("姓名");
		mlist.add(m5);
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("YHZH");
		m1.setShowname("账号");
		mlist.add(m1);
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("KHYH");
		m6.setShowname("银行名称");
		mlist.add(m6);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("SFHJ");
		m2.setShowname("金额");
		mlist.add(m2);
		M_largedata m0 = new M_largedata();
		m0.setColtype("String");
		m0.setName("GZYF");
		m0.setShowname("录入日期");
		mlist.add(m0);
		//导出方法
		String sql = "select " + pageList.getSqlText() + " from " + pageList.getTableName() + " where 1=1 "+ pageList.getStrWhere() + " order by rybh ";
		pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 导出--工资汇总
	 * @return
	 */
	@RequestMapping(value="/expGzhz",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expGzhz(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String strWhere = " ";
		PageList pageList = new PageList();
		StringBuffer sqltext = new StringBuffer();
		StringBuffer tablename = new StringBuffer();
		String bmmc =pd.getString("bmmc");
		String gzyf =pd.getString("gzyf");
		String id1 = pd.getString("id1");
		String id2 = pd.getString("id2");
		sqltext.append("GZYF,BM,RS,GWGZ,XJGZ,XZFT,WYBT,DSZF,JCJX,JLJX1,BSBT,GWBT,BGZ,ZFJJ,YLBX,DKS,FZ,SYJ,SBJ,YLJ,KK,KKHJ,SFHJ,YFHJ ");
		
		tablename.append(" (SELECT T.BM,T.GZYF,COUNT(1) AS RS, ");
		tablename.append("  TO_CHAR(sum(nvl(T.YFHJ, 0)), 'FM99999999990.00') AS YFHJ,  ");
		tablename.append("  TO_CHAR(sum(nvl(T.GWGZ, 0)), 'FM99999999990.00') AS GWGZ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.XJGZ, 0)), 'FM99999999990.00') AS XJGZ, ");
		tablename.append("  TO_CHAR(sum(nvl(T.XZFT, 0)), 'FM99999999990.00') AS XZFT, ");
		tablename.append(" TO_CHAR(sum(nvl(T.WYBT, 0)), 'FM99999999990.00') AS WYBT, ");
		tablename.append("  TO_CHAR(sum(nvl(T.DSZF, 0)), 'FM99999999990.00') AS DSZF, ");
		tablename.append(" TO_CHAR(sum(nvl(T.JCJX, 0)), 'FM99999999990.00') AS JCJX, ");
		tablename.append(" TO_CHAR(sum(nvl(T.JLJX1, 0)), 'FM99999999990.00') AS JLJX1, ");
		tablename.append(" TO_CHAR(sum(nvl(T.BSBT, 0)), 'FM99999999990.00') AS BSBT, ");
		tablename.append(" TO_CHAR(sum(nvl(T.GWBT, 0)), 'FM99999999990.00') AS GWBT, ");
		tablename.append(" TO_CHAR(sum(nvl(T.BGZ, 0)), 'FM99999999990.00') AS BGZ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.ZFJJ, 0)), 'FM99999999990.00') AS ZFJJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.YLBX, 0)), 'FM99999999990.00') AS YLBX, ");
		tablename.append(" TO_CHAR(sum(nvl(T.DKS, 0)), 'FM99999999990.00') AS DKS, ");
		tablename.append(" TO_CHAR(sum(nvl(T.FZ, 0)), 'FM99999999990.00') AS FZ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.SYJ, 0)), 'FM99999999990.00') AS SYJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.SBJ, 0)), 'FM99999999990.00') AS SBJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.YLJ, 0)), 'FM99999999990.00') AS YLJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.KK, 0)), 'FM99999999990.00') AS KK, ");
		tablename.append(" TO_CHAR(sum(nvl(T.KKHJ, 0)), 'FM99999999990.00') AS KKHJ, ");
		tablename.append(" TO_CHAR(sum(nvl(T.SFHJ, 0)), 'FM99999999990.00') AS SFHJ");
		tablename.append(" from CW_XZB T  GROUP BY T.BM, T.GZYF ");
//		int count =xzglService.count("cw_xzb", "", "2", gzyf,"", bmmc);
		//如果两个条件都为空，也就是说未选择行再输出合计信息
		if( (Validate.noNull(gzyf) && Validate.isNull(bmmc) ) || (Validate.isNull(gzyf) && Validate.isNull(bmmc) ) ) {
		tablename.append(" union ");
		tablename.append(" SELECT '合计' as BM,'' as GZYF,COUNT(1) AS RS, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.YFHJ, 0))), 'FM99999999990.00') AS YFHJ, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.GWGZ, 0))), 'FM99999999990.00') AS GWGZ, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.XJGZ, 0))), 'FM99999999990.00') AS XJGZ, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.XZFT, 0))), 'FM99999999990.00') AS XZFT, ");
		tablename.append("   TO_CHAR(sum(sum(nvl(T.WYBT, 0))), 'FM99999999990.00') AS WYBT, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.DSZF, 0))), 'FM99999999990.00') AS DSZF, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.JCJX, 0))), 'FM99999999990.00') AS JCJX, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.JLJX1, 0))), 'FM99999999990.00') AS JLJX1, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.BSBT, 0))), 'FM99999999990.00') AS BSBT, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.GWBT, 0))), 'FM99999999990.00') AS GWBT, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.BGZ, 0))), 'FM99999999990.00') AS BGZ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.ZFJJ, 0))), 'FM99999999990.00') AS ZFJJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.YLBX, 0))), 'FM99999999990.00') AS YLBX, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.DKS, 0))), 'FM99999999990.00') AS DKS, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.FZ, 0))), 'FM99999999990.00') AS FZ, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.SYJ, 0))), 'FM99999999990.00') AS SYJ, ");
		tablename.append("  TO_CHAR(sum(sum(nvl(T.SBJ, 0))), 'FM99999999990.00') AS SBJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.YLJ, 0))), 'FM99999999990.00') AS YLJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.KK, 0))), 'FM99999999990.00') AS KK, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.KKHJ, 0))), 'FM99999999990.00') AS KKHJ, ");
		tablename.append(" TO_CHAR(sum(sum(nvl(T.SFHJ, 0))), 'FM99999999990.00') AS SFHJ ");
		tablename.append(" from CW_XZB T  where T.SHZT LIKE '%" + pd.getString("shzt") + "%'   and t.gzyf like '%"+gzyf+"%' GROUP BY T.BM, T.GZYF");
		}
		tablename.append(") T");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("T.BM");//主键
		//where条件
		//需要两个条件确定唯一性，bm部门和gzyf工资月份
		if(Validate.noNull(id1)){
		strWhere +=" and T.BM in ('"+id1.replace(",", "','")+"') ";
		}
		if(Validate.noNull(id2)) {
		strWhere +=" and T.gzyf in ('"+id2.replace(",", "','")+"') ";
		}
		
		if(Validate.noNull(gzyf)){
			strWhere += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null) ";
		}
		if(Validate.noNull(bmmc)){
			strWhere += " and (t.bm like '%"+CommonUtil.getEndText(bmmc)+"%' or t.bm is null )";
		}
		pageList.setStrWhere(strWhere); 
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setColtype("String");
		m.setName("BM");
		m.setShowname("部门");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("RS");
		m.setShowname("人数");
		mlist.add(m);
		m = null;
		getmlist(mlist);
		//导出方法
		String sql = "select " + pageList.getSqlText() + " from " + pageList.getTableName() + " where 1=1 "+ pageList.getStrWhere() + "  order by RS asc ";
		pageService.ExpExcel(sql,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	/**
	 * 导出--工资发放
	 * @return
	 * @throws SQLException 
	 * @throws FileNotFoundException 
	 * @throws UnsupportedEncodingException 
	 */
	@RequestMapping(value="/expGzffcx",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expGzffcx() throws Exception{
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		PageList pageList = new PageList();
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT, " +
				"TO_CHAR(T.GWGZ,'FM9999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM9999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM9999990.00') AS XZFT," +
				"TO_CHAR(T.WYBT,'FM9999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM9999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM9999990.00') AS JCJX," +
				"TO_CHAR(T.JLJX1,'FM9999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM9999990.00') AS BSBT," +
				"TO_CHAR(T.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM9999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM9999990.00') AS SYBT," +
				"TO_CHAR(T.JHBT,'FM9999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM9999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM9999990.00') AS HTBT," +
				"TO_CHAR(T.QTBT,'FM9999990.00') AS QTBT," +
				"TO_CHAR(T.BGZ,'FM9999990.00') AS BGZ,TO_CHAR(T.JKF,'FM9999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM9999990.00') AS FZGZL," +
				"TO_CHAR(T.ZSJL,'FM9999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM9999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM9999990.00') AS KHJ," +
				"TO_CHAR(T.DHF,'FM9999990.00') AS DHF,TO_CHAR(T.BT,'FM9999990.00') AS BT,TO_CHAR(T.ZFBT,'FM9999990.00') AS ZFBT," +
				"TO_CHAR(T.YFHJ,'FM999999.00') AS YFHJ," +
				"TO_CHAR(T.JIANGKEF,'FM9999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM9999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM9999990.00') AS JSX," +
				"TO_CHAR(T.QNJSX,'FM9999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM9999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM9999990.00') AS QNJSX2," +
				"TO_CHAR(T.QNJSX3,'FM9999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM9999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM9999990.00') AS BXBT2014N1D10YJSJS," +
				"TO_CHAR(T.JSJS,'FM9999990.00') AS JSJS," +
				"TO_CHAR(T.ZFJJ,'FM9999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM9999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM9999990.00') AS YLBX," +
				"TO_CHAR(T.BGJJ,'FM9999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM9999990.00') AS DKS,TO_CHAR(T.BNSE,'FM9999990.00') AS BNSE," +
				"TO_CHAR(T.SNSE,'FM9999990.00') AS SNSE,TO_CHAR(T.BS,'FM9999990.00') AS BS,TO_CHAR(T.FZ,'FM9999990.00') AS FZ," +
				"TO_CHAR(T.SYJ,'FM9999990.00') AS SYJ," +
				"TO_CHAR(T.NQF,'FM9999990.00') AS NQF,TO_CHAR(T.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(T.WYF,'FM9999990.00') AS WYF," +
				"TO_CHAR(T.SBJ,'FM9999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(T.KK,'FM9999990.00') AS KK," +
				"TO_CHAR(T.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(T.JK,'FM9999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM9999990.00') AS AXYRJ," +
				"TO_CHAR(T.KKHJ,'FM9999990.00') AS KKHJ," +
				"TO_CHAR(T.SFHJ,'FM9999990.00') AS SFHJ,T.GZYF,T.BH," +
				"T.XH,TO_CHAR(T.JTF,'FM9999990.00') AS JTF,TO_CHAR(T.NZJ,'FM9999990.00') AS NZJ," +
				"TO_CHAR(T.NZJDKS,'FM9999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM9999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM9999990.00') AS KSHJ," +
				"T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM9999990.00') AS BKYLBX," +
				"TO_CHAR(T.BKSYJ,'FM9999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM9999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM9999990.00') AS BKSBJ," +
				"T.SFDY,T.RDQK ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(" CW_XZB T");
		//设置表主键名
		pageList.setKeyId("t.guid");//主键
		//where条件
		pageList.setStrWhere(" and t.shzt = '2' ");
		pageList.setStrWhere(pageList.getStrWhere() + CommonUtils.jsonToSql(searchJson)); 
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			pageList.setStrWhere(pageList.getStrWhere() + " and guid in ('"+id.replace(",", "','")+"') ");
		}
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setColtype("String");
		m.setName("RYBH");
		m.setShowname("人员编号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setColtype("String");
		m.setName("XM");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("BM");
		m.setShowname("部门");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("XH");
		m.setShowname("排序序号");
		mlist.add(m);
		m = null;
		getmlist(mlist);
		//导出方法
		String sql = "select " + pageList.getSqlText() + " from " + pageList.getTableName() + " where 1=1 "+ pageList.getStrWhere() + " order by rybh ";
		List list = xzglService.getExcelData(sql);
		pageService.ExpExcelforGZ(list,realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	private void getmlist(List<M_largedata> mlist){
		
		M_largedata m = new M_largedata();
		m = new M_largedata();
		m.setColtype("String");
		m.setName("GWGZ");
		m.setShowname("岗位工资");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("XJGZ");
		m.setShowname("薪级工资");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("XZFT");
		m.setShowname("新住房贴");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("WYBT");
		m.setShowname("物业补贴");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("DSZF");
		m.setShowname("独/回");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("JCJX");
		m.setShowname("基础绩效");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("JLJX1");
		m.setShowname("奖励绩效1");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("BSBT");
		m.setShowname("博士补贴");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("GWBT");
		m.setShowname("岗位补贴");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("BGZ");
		m.setShowname("补工资");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("YFHJ");
		m.setShowname("应发合计");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("ZFJJ");
		m.setShowname("住房积金");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("YLBX");
		m.setShowname("医疗保险");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("DKS");
		m.setShowname("代扣税");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("FZ");
		m.setShowname("房租");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("SYJ");
		m.setShowname("失业金");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("SBJ");
		m.setShowname("社保金");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("YLJ");
		m.setShowname("养老金");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("KK");
		m.setShowname("扣款");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("KKHJ");
		m.setShowname("扣款合计");
		mlist.add(m);
		m = null;

		m = new M_largedata();
		m.setColtype("String");
		m.setName("SFHJ");
		m.setShowname("实发合计");
		mlist.add(m);
		m = null;

	}
}
