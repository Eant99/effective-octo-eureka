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
import com.googosoft.service.base.PageService;
import com.googosoft.service.tubiao.JzgjcxxfxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 教职工基础信息分析---控制层
 * @author RC  2016-10-28
 *
 */
@Controller
@RequestMapping(value="/jzgjcxxfx")
public class JzgjcxxfxController extends BaseController {
	@Resource(name="jzgjcxxfxService")
	private JzgjcxxfxService jzgjcxxfxService;
	
	@Resource(name="pageService")
	PageService pageService;
	/**
	 * 跳转教职工概况图表页面
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/gotopage")
	public ModelAndView gotopage() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("tubiao/jzgcwxq/jzgjcxx_fx");
		return mv;
	}
	/**
	 * 获取教职工基础信息分析数据
	 * @return
	 */
	@RequestMapping(value="/getJzgjcxxfx",produces="text/html;charset=UTF-8")
	@ResponseBody
	public Object getJzgjcxxfx(Zhcx obj){
	    return jzgjcxxfxService.getJzg_gk(obj);
	}
	
	/**
	 * 
	 * @Title: getJzgmxWindow 
	 * @Description: 职工明细弹窗类
	 * @date 2017-5-25 下午2:14:32
	 * @return Object 
	 *
	 */
    @RequestMapping("/jzgmx")
    @ResponseBody
    public Object getJzgmxWindow(){
        PageData pd = this.getPageData();
        String zc = pd.getString("zc");
        String mz = pd.getString("mz");
        String xb = pd.getString("xb");
        String jgs = pd.getString("jgs");
        String jgms = pd.getString("jgms");
        String tjrq = pd.getString("rq");
        StringBuffer sqltext = new StringBuffer();
        sqltext.append(" id, jzgh, xm, xb,mz, zzmm, whcd, szx,zcmc, csrq, jg ");
        PageList pageList = new PageList();
        pageList.setSqlText(sqltext.toString());
        pageList.setKeyId("ID");
        StringBuffer strWhere = new StringBuffer();
        if(Validate.noNull(zc)){
            strWhere.append(" and zcmc ='"+zc+"' ");
        }
        if(Validate.noNull(mz)){
            strWhere.append(" and mz ='"+mz+"' ");
        }
        if(Validate.noNull(xb)){
            strWhere.append(" and xb ='"+xb+"' ");
        }
        if(Validate.noNull(jgs)){
            strWhere.append(" and jgs ='"+jgs+"' ");
        }
        if(Validate.noNull(jgms)){
            strWhere.append(" and jgms ='"+jgms+"' ");
        }
        if(Validate.noNull(tjrq)){
            strWhere.append(" and jxny <= substr('"+tjrq+"',0,7) and ( lzrq is null or lzrq > to_date('"+tjrq+"','yyyy-mm-dd')) ");
        }
        pageList.setStrWhere(strWhere.toString());
        pageList.setTableName("gx_jzgxx_fx ");
        pageList = pageService.findWindowList(pd,pageList,"F");
        Gson gson = new Gson();
        PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
        return pageInfo.toJson();
    }
    
    /**
     * 
     * @Title: getJzgmxMap 
     * @Description: 获取教职工市级信息
     * @date 2017-6-1 下午4:32:31
     * @return Object 
     *
     */
    @RequestMapping(value="/getJzgmxMap",produces="text/html;charset=UTF-8")
    @ResponseBody
    public Object getJzgmxMap(Zhcx obj){
        PageData pd = this.getPageData();
        
        return jzgjcxxfxService.getJzg_mx_map(obj, pd);
    }
    
    
    
    /************************************************新旧方法分界线*****************************************************/
    
    
    /**
     * 教职工职称分析
     * @return
     */
    @RequestMapping("/zcmx")
    @ResponseBody
    public Object getZcWindow(){
        PageData pd = this.getPageData();
        String lb = pd.getString("lb");
        String ndqj1 = pd.getString("ndqj1");
        String ndqj2 = pd.getString("ndqj2");
        StringBuffer sqltext = new StringBuffer();
        sqltext.append(" ID,jgh,xm,(select mc from gx_dmb d where d.zl = 'xb' and d.dm = x.xbm) xb,(select mc from gx_dmb d where d.zl = 'whcd' and d.dm = x.whcdm) whcd ");
        PageList pageList = new PageList();
        pageList.setSqlText(sqltext.toString());
        pageList.setKeyId("ID");
        if(Validate.noNull(ndqj1) && Validate.noNull(ndqj2)){
            if(Validate.noNull(lb)){
                pageList.setStrWhere("and zcm in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy'))>="+ndqj1+" and to_number(to_char(rzrq,'yyyy')) <="+ndqj2+"");
            }else{
                pageList.setStrWhere(" ");
            }
        }
        if(Validate.isNull(ndqj1) && Validate.noNull(ndqj2)){
            if(Validate.noNull(lb)){
                pageList.setStrWhere("and zcm in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy')) <="+ndqj2+"");           
            }else{
                pageList.setStrWhere(" ");
            }
        }
        if(Validate.noNull(ndqj1) && Validate.isNull(ndqj2)){
            if(Validate.noNull(lb)){
                pageList.setStrWhere("and zcm in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy'))>="+ndqj1+" ");           
            }else{
                pageList.setStrWhere(" ");
            }
        }
        pageList.setTableName("gx_jzgjbxxb x");
        pageList = pageService.findWindowList(pd,pageList,"F");
        Gson gson = new Gson();
        PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
        return pageInfo.toJson();
    }
	
	/**
	 * 民族分析
	 * @return
	 */
	@RequestMapping("/mzmx")
	@ResponseBody
	public Object getMzWindow(){
		PageData pd = this.getPageData();
		String lb = pd.getString("lb");
		String ndqj1 = pd.getString("ndqj1");
		String ndqj2 = pd.getString("ndqj2");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" ID,jgh,xm,(select mc from gx_dmb d where d.zl = 'xb' and d.dm = x.xbm) xb,(select mc from gx_dmb d where d.zl = 'whcd' and d.dm = x.whcdm) whcd ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("ID");
		/*if(Validate.noNull(lb)){
			pageList.setStrWhere("and mzm in(select dm from gx_dmb where mc='"+lb+"' )");
		}else{
			pageList.setStrWhere(" ");
		}*/
		if(Validate.noNull(ndqj1) && Validate.noNull(ndqj2)){
			if(Validate.noNull(lb)){
				pageList.setStrWhere("and mzm in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy'))>="+ndqj1+" and to_number(to_char(rzrq,'yyyy')) <="+ndqj2+"");
			}else{
				pageList.setStrWhere(" ");
			}
		}
		if(Validate.isNull(ndqj1) && Validate.noNull(ndqj2)){
			if(Validate.noNull(lb)){
				pageList.setStrWhere("and mzm in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy')) <="+ndqj2+"");			
			}else{
				pageList.setStrWhere(" ");
			}
		}
		if(Validate.noNull(ndqj1) && Validate.isNull(ndqj2)){
			if(Validate.noNull(lb)){
				pageList.setStrWhere("and mzm in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy'))>="+ndqj1+" ");			
			}else{
				pageList.setStrWhere(" ");
			}
		}
		pageList.setTableName("gx_jzgjbxxb x");
		pageList = pageService.findWindowList(pd,pageList,"F");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	@RequestMapping("/jgmx")
	@ResponseBody
	public Object getJgWindow(){
		PageData pd = this.getPageData();
		String lb = pd.getString("lb");
		String ndqj1 = pd.getString("ndqj1");
		String ndqj2 = pd.getString("ndqj2");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" ID,jgh,xm,(select mc from gx_dmb d where d.zl = 'xb' and d.dm = x.xbm) xb,(select mc from gx_dmb d where d.zl = 'whcd' and d.dm = x.whcdm) whcd ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("ID");
		/*if(Validate.noNull(lb)){
			pageList.setStrWhere("and jg in(select dm from gx_dmb where mc='"+lb+"' )");
		}else{
			pageList.setStrWhere(" ");
		}*/
		if(Validate.noNull(ndqj1) && Validate.noNull(ndqj2)){
			if(Validate.noNull(lb)){
				pageList.setStrWhere("and jg in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy'))>="+ndqj1+" and to_number(to_char(rzrq,'yyyy')) <="+ndqj2+"");
			}else{
				pageList.setStrWhere(" ");
			}
		}
		if(Validate.isNull(ndqj1) && Validate.noNull(ndqj2)){
			if(Validate.noNull(lb)){
				pageList.setStrWhere("and jg in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy')) <="+ndqj2+"");			
			}else{
				pageList.setStrWhere(" ");
			}
		}
		if(Validate.noNull(ndqj1) && Validate.isNull(ndqj2)){
			if(Validate.noNull(lb)){
				pageList.setStrWhere("and jg in(select dm from gx_dmb where mc='"+lb+"' ) and to_number(to_char(rzrq,'yyyy'))>="+ndqj1+" ");			
			}else{
				pageList.setStrWhere(" ");
			}
		}
		pageList.setTableName("gx_jzgjbxxb x");
		pageList = pageService.findWindowList(pd,pageList,"F");
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
}
