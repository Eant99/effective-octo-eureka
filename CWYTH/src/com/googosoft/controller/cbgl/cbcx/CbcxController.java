package com.googosoft.controller.cbgl.cbcx;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.wxzf.WxzfService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/cbcx")
public class CbcxController extends BaseController {

	@Resource(name="wxzfService")
	private WxzfService objService;
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	/**
	 * 获取成本查询列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPageList")
	public ModelAndView goSecondPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("zfztList",dictService.getDict(Constant.ZFZT));
		mv.setViewName("cbgl/cbcx/cbcx_list");
		return mv;
	}
	/**
	 * 获取成本查询列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageList pageList = new PageList();
		String zfzt = Validate.isNullToDefaultString(this.getPageData().getString("zfzt"), "01");
		// 设置查询字段名
		StringBuffer sql = new StringBuffer();
		sql.append(" k.*");		
		pageList.setSqlText(sql.toString());
		// 表名
		pageList.setTableName("(select A.* from (select t.guid,xb.rygh,xb.ryxm,(Select MC from GX_SYS_DWB where Dwbh = (select dwbh from gx_sys_ryb"
                              +" where rybh = xb.rygh)) as SSDWMC,CASE xb.type WHEN 'T' THEN '教师' WHEN 'S' THEN '学生' end as rylx,"
                              +" t.xfdd,t.sscbs,to_char(t.xfsj, 'yyyy-mm-dd hh24:mi:ss') as xfsj,to_char(t.xfje, '99999999999990.99') xfje,t.zfzt,"
                              +" B.XFDDBH, B.xfddmc,C.CBSBH,C.CBSMC AS SSCBSMC,CASE t.paymethod WHEN 'ali' THEN '支付宝'"
                              +" WHEN 'wx' THEN '微信' end as zffs"
                              +" from CW_WXZFMXB t"
                              +" left join cw_wxxxb xb on t.payaccount = xb.openid"
                              +" left join cw_xfddgl B on t.xfdd = B.Guid"
                              +" left join Cw_Cbsxx C ON c.guid = t.sscbs"
                              +" where t.paymethod = 'wx'"
                          	  +" ) A"
                          	  +" where 1 = 1"
                          	  +" union"
                          	  +" select B.* from (select t.guid,xb.rygh,xb.ryxm,(Select MC"
                              +" from GX_SYS_DWB where Dwbh = (select dwbh from gx_sys_ryb where rybh = xb.rygh)) as SSDWMC,"
                              +" CASE xb.type WHEN 'T' THEN '教师' WHEN 'S' THEN '学生' end as rylx,"
                              +" t.xfdd,t.sscbs,to_char(t.xfsj, 'yyyy-mm-dd hh24:mi:ss') as xfsj,to_char(t.xfje, '99999999999990.99') xfje,"
                              +" t.zfzt,B.XFDDBH,B.xfddmc,C.CBSBH,C.CBSMC AS SSCBSMC,"
                              +" CASE t.paymethod WHEN 'ali' THEN '支付宝' WHEN 'wx' THEN '微信' end as zffs"
		                      +" from CW_WXZFMXB t left join cw_wxxxb xb on t.payaccount = xB.Aliaccout"
		                      +" left join cw_xfddgl B on t.xfdd = B.Guid left join Cw_Cbsxx C ON c.guid = t.sscbs"
		                      +" where t.paymethod = 'ali' ) B where 1 = 1 )K ");
		// 主键
		pageList.setKeyId("GUID");
		// 设置WHERE条件
		String strWhere = "";
		PageData pd = this.getPageData();
//		if(Validate.noNull(zfzt)){
//			strWhere+=" and k.zfzt='"+zfzt+"'";
//		}
		pageList.setStrWhere(strWhere);
		// 设置合计值字段名
		pageList.setHj1("");
		// 页面数据
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
}
