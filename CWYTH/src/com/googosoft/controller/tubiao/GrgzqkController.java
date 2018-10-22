package com.googosoft.controller.tubiao;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.tubiao.Zhcx;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.tubiao.GrgzqkService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/grgzqk")
public class GrgzqkController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	DictService dictService;
	
	@Resource(name="grgzqkService")
	private GrgzqkService grgzqkService;//单例
	
	@RequestMapping(value = "/gotopage")
	public ModelAndView gotopage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("tubiao/grgzqk/grgzqk_list");
		return mv;

	}
	//=====fuzhi 
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
        return grgzqkService.getJzggzzc(obj);
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
        sqltext.append(" id, jgh, xm, nddm,yddm,gwgz,xjgz,wsf,ycxzf,dsznbt,wcbt3,shjt,ycxbt,jcxjx,yfhj,gjj,gjjbx,yilbx, yanglbx,sybx,wyf,sqgz,sds,kkhj,sfgz ");
        PageList pageList = new PageList();
        pageList.setSqlText(sqltext.toString());
        pageList.setKeyId("id");
        pageList.setStrWhere(" ");
        pageList.setOrderBy(" ");
        StringBuffer tableName = new StringBuffer();
        tableName.append(" ( select id, jgh, xm, ");
        //(select t.zcmc from gx_jzgxx_fx t where t.jzgh=jgh) as zc, 
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
		return grgzqkService.getJzgcwxq(obj);
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
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("xm,to_number(nddm) nddm,to_number(yddm) yddm,gwgz,xjgz,wsf,ycxzf,dsznbt,wcbt3,shjt,ycxbt,jcxjx,yfhj,gjj,gjjbx,yilbx,yanglbx,sybx,wyf,sqgz,sds,kkhj,sfgz");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("gx_jzzggz_fx t ");
		//设置表主键名
		pageList.setKeyId("id");
		//设置WHERE条件
		String strWhere="AND t.xm='"+obj.getXm()+"' and t.nddm='"+obj.getNd()+"' ";		
		pageList.setStrWhere(strWhere);
		//设置合计值字段名
		pageList.setHj1("####");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
   //====fuzhiqu
}
