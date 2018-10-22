package com.googosoft.controller.tubiao;


import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.tubiao.Zhcx;
import com.googosoft.service.base.PageService;
import com.googosoft.service.tubiao.JzgcwxqService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 教职工财务校情分析
 * @author Administrator
 *
 */
@Controller
@RequestMapping("/jzgcwxq")
public class JzgcwxqController extends BaseController{
	@Resource(name="jzgcwxqService")
	private JzgcwxqService jzgcwxqService;//单例
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	/**
	 * 跳转教职工收入图表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gotopage")
	public ModelAndView gotopage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("tubiao/jzgcwxq/jzgcwxq_fx");
		return mv;
	}
	
	/**
	 * 
	 * @Title: getJzggzzc 
	 * @Description: 工资月度支出情况
	 * @date 2017-6-14 下午5:03:52
	 * @param @param obj
	 * @param @throws Exception   
	 * @return Object 
	 *
	 */
	@RequestMapping(value="/getJzggzzc",produces="text/html;charset=UTF-8")
    @ResponseBody
    public Object getJzggzzc(Zhcx obj) throws Exception{
        return jzgcwxqService.getJzggzzc(obj);
    }
	
	/**
	 * 
	 * @Title: getJzggzzcmx 
	 * @Description: 工资明细
	 * @date 2017-6-14 下午5:32:58
	 * @param @param obj
	 * @return Object 
	 *
	 */
	@RequestMapping(value="/getJzggzzcmx",produces="text/html;charset=UTF-8")
    @ResponseBody
    public Object getJzggzzcmx(String mon, Zhcx obj) {
        PageData pd = this.getPageData();
        StringBuffer sqltext = new StringBuffer();
        sqltext.append(" id, jgh, xm, zc, nddm,yddm,gwgz,xjgz,wsf,ycxzf,dsznbt,wcbt3,shjt,ycxbt,jcxjx,yfhj,gjj,gjjbx,yilbx, yanglbx,sybx,wyf,sqgz,sds,kkhj,sfgz ");
        PageList pageList = new PageList();
        pageList.setSqlText(sqltext.toString());
        pageList.setKeyId("id");
        pageList.setStrWhere(" ");
        pageList.setOrderBy(" ");
        StringBuffer tableName = new StringBuffer();
        tableName.append(" ( select id, jgh, xm, (select t.zcmc from gx_jzgxx_fx t where t.jzgh=jgh) as zc,  ");
        tableName.append(" to_number(nddm) as nddm, to_number(yddm) as yddm,gwgz,xjgz,wsf,ycxzf,dsznbt,wcbt3,shjt,ycxbt,jcxjx,yfhj,gjj,gjjbx,yilbx, ");
        tableName.append(" yanglbx,sybx,wyf,sqgz,sds,kkhj,sfgz from gx_jzzggz_fx where 1=1 ");
        if(Validate.noNull(obj.getNd())){
            tableName.append(" and to_number(nddm)="+obj.getNd());
        }
        if(Validate.noNull(mon)){
            tableName.append(" and to_number(yddm)||'月'='"+mon+"' ");
        }
        tableName.append(" ) ");
        pageList.setTableName(tableName.toString());
        pageList = pageService.findWindowList(pd,pageList,"GZ");
        Gson gson = new Gson();
        PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
        return pageInfo.toJson();
    }
	
	/**
	 * 获取分析数据
	 * @param obj
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getJzgcwxq",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getJzgcwxq(Zhcx obj) throws Exception{	    
		return jzgcwxqService.getJzgcwxq(obj);
	}
	
	/**
	 * 获取单位信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(Zhcx obj){
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT, ");
		sql.append("TO_CHAR(T.GWGZ,'FM9999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM9999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM9999990.00') AS XZFT,");
		sql.append("TO_CHAR(T.WYBT,'FM9999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM9999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM9999990.00') AS JCJX,");
		sql.append("TO_CHAR(T.JLJX1,'FM9999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM9999990.00') AS BSBT,");
		sql.append("TO_CHAR(T.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM9999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM9999990.00') AS SYBT,");
		sql.append("TO_CHAR(T.JHBT,'FM9999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM9999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM9999990.00') AS HTBT,");
		sql.append("TO_CHAR(T.QTBT,'FM9999990.00') AS QTBT,");
		sql.append("TO_CHAR(T.BGZ,'FM9999990.00') AS BGZ,TO_CHAR(T.JKF,'FM9999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM9999990.00') AS FZGZL,");
		sql.append("TO_CHAR(T.ZSJL,'FM9999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM9999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM9999990.00') AS KHJ," );
		sql.append("TO_CHAR(T.DHF,'FM9999990.00') AS DHF,TO_CHAR(T.BT,'FM9999990.00') AS BT,TO_CHAR(T.ZFBT,'FM9999990.00') AS ZFBT," );
		sql.append("TO_CHAR(T.YFHJ,'FM999999.00') AS YFHJ," );
		sql.append("TO_CHAR(T.JIANGKEF,'FM9999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM9999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM9999990.00') AS JSX," );
		sql.append("TO_CHAR(T.QNJSX,'FM9999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM9999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM9999990.00') AS QNJSX2," );
		sql.append("TO_CHAR(T.QNJSX3,'FM9999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM9999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM9999990.00') AS BXBT2014N1D10YJSJS," );
		sql.append("TO_CHAR(T.JSJS,'FM9999990.00') AS JSJS," );
		sql.append("TO_CHAR(T.ZFJJ,'FM9999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM9999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM9999990.00') AS YLBX," );
		sql.append("TO_CHAR(T.BGJJ,'FM9999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM9999990.00') AS DKS,TO_CHAR(T.BNSE,'FM9999990.00') AS BNSE," );
		sql.append("TO_CHAR(T.SNSE,'FM9999990.00') AS SNSE,TO_CHAR(T.BS,'FM9999990.00') AS BS,TO_CHAR(T.FZ,'FM9999990.00') AS FZ," );
		sql.append("TO_CHAR(T.SYJ,'FM9999990.00') AS SYJ," );
		sql.append("TO_CHAR(T.NQF,'FM9999990.00') AS NQF,TO_CHAR(T.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(T.WYF,'FM9999990.00') AS WYF," );
		sql.append("TO_CHAR(T.SBJ,'FM9999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(T.KK,'FM9999990.00') AS KK," );
		sql.append("TO_CHAR(T.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(T.JK,'FM9999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM9999990.00') AS AXYRJ," );
		sql.append("TO_CHAR(T.KKHJ,'FM9999990.00') AS KKHJ,TO_CHAR(T.SFHJ,'FM9999990.00') AS SFHJ," );
		sql.append("TO_CHAR(TO_DATE(T.GZYF,'YYYY.MM'),'YYYY') AS YEAR,TO_CHAR(TO_DATE(T.GZYF,'YYYY.MM'),'MM') AS MON,T.GZYF,T.BH," );
		sql.append("T.XH,TO_CHAR(T.JTF,'FM9999990.00') AS JTF,TO_CHAR(T.NZJ,'FM9999990.00') AS NZJ," );
		sql.append("TO_CHAR(T.NZJDKS,'FM9999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM9999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM9999990.00') AS KSHJ," );
		sql.append("T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM9999990.00') AS BKYLBX," );
		sql.append("TO_CHAR(T.BKSYJ,'FM9999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM9999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM9999990.00') AS BKSBJ," );
		sql.append("T.SFDY,T.RDQK ");
		pageList.setSqlText(sql.toString());
		//设置表名
		pageList.setTableName(" CW_XZB t ");
		//设置表主键名
		pageList.setKeyId("GUID");
		//设置WHERE条件
		String strWhere=" AND substr(GZYF,0,4)='"+obj.getNd()+"' ";		
		pageList.setStrWhere(strWhere);
		//设置合计值字段名
		pageList.setHj1(" ");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
}
