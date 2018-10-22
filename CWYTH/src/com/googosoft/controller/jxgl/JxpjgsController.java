package com.googosoft.controller.jxgl;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value="/jxpjgs")
public class JxpjgsController extends BaseController{
	
	 @Resource(name="dictService")
	 private DictService dictService;//单例
	 @Resource(name="pageService")
	 private PageService pageService;//单例
	 
	  /**
			 * 获取列表页面
			 * @return
			 */
			@RequestMapping(value="/goListPage")
			public ModelAndView goDwbPage(){
				ModelAndView mv = this.getModelAndView();
				PageData pd = this.getPageData();
				mv.setViewName("jxgl/jxpjgs/jxpjgs_list");		
				return mv;
			}
			/**
			 * 获取项目分类列表数据
			 * @return
			 * @throws Exception
			 */
			@RequestMapping(value = "/getPageList")
			@ResponseBody
			public Object getPageList() throws Exception {
				PageList pageList = new PageList();
				PageData pd = this.getPageData();
				// 设置查询字段名
				StringBuffer sql = new StringBuffer();
				pageList.setSqlText("*");
				sql.append("(select '111' guid, '01' xmbh,'项目一' xmmc,'财务部' ssbm,'80' zhdf,'1' pm from CW_JXZBSDB where 1=1");
				sql.append(" )A");
				// 表名
				pageList.setTableName(sql.toString());
				// 主键
				pageList.setKeyId("GUID");
				// 设置WHERE条件
				String strWhere = " ";
				pageList.setStrWhere(strWhere);
				// 设置合计值字段名
				pageList.setHj1("");
				// 页面数据
				pageList = pageService.findPageList(pd, pageList);
				Gson gson = new Gson();
				PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM")+ "", pageList.getTotalList().get(0).get("NUM") + "",gson.toJson(pageList.getContentList()));
				return pageInfo.toJson();
			}

}
