package com.googosoft.controller.xzgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.PageService;
import com.googosoft.service.xzgl.jcsz.GzxmszService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping("/gzxmsz")
public class GzxmszController extends BaseController{
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="gzxmszService")
	GzxmszService gzxmszService;
	/**
	 * 跳转到工资项目设置页面
	 */
	@RequestMapping(value="/gzxm",produces = "text/html;charset=UTF-8")
		public ModelAndView goGzxm(){
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();	
			List list = new ArrayList<Map<String,Object>>();
			mv.setViewName("xzgl/jcsz/gzxmsz");  
			return mv;
		}
	/**
	 * 保存
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
			bool = gzxmszService.doSave(list);
		}
		return gson.toJson(bool);
	}

	/**
	 * 获得工资项目列表页面
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
		sqltext.append(" t.GUID,t.XMJC,t.XMMC ");
		tablename.append(" CW_GZXMB t");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("t.GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere(" and t.xmjc not in ('shzt','rybh','xm','xl','bm','ryjb','rylx','gzyf','bh','gh','sfzb','sfdy','rdqk','xh')"); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();	
	}
}
