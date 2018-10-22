package com.googosoft.controller.systemset.cdgl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cdgl.CdglService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 菜单管理controller
 * @date 2018-2-5上午9:32:12
 */
@Controller
@RequestMapping(value="/cdgl")
public class CdglController extends BaseController {
	@Resource(name="dictService")
	private DictService dictService;//单例
	@Resource(name="cdglService")
	private CdglService cdglService;//单例
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	
	/**
	 * 跳转菜单管理列表页面
	 * @return
	 */
	@RequestMapping(value="goCdglList")
	public ModelAndView goCdglList(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		String treesearch=pd.getString("treesearch");
		mv.addObject("mkbh",mkbh);
		mv.setViewName("systemset/cdgl/cdgl_list");
		mv.addObject("treesearch",treesearch);
		return mv;
	}
	/**
	 * 功能菜单树
	 * @return
	 */
	@RequestMapping(value="/cdglTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object cdglTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String mkbh = pd.getString("mkbh");
		if(menu.equals("get-xjgn")){
			String loginrybh = LUser.getRybh();
				return cdglService.getCdNode(pd, loginrybh, rootPath,mkbh);
		}else{
			return "";
		}
	}
	
	/**
	 * 获取菜单管理列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object  getPageList() throws Exception {
		PageData pd = this.getPageData();
		String bh = pd.getString("bh");
		String treesearch=pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
//		sqltext.append(" LOGBH,(SELECT'('||RYGH||')'||XM FROM GX_SYS_RYB WHERE RYBH=K.RYBH) AS RYBH,TO_CHAR(CZRQ,'yyyy-mm-dd hh24:mi:ss' ) AS CZRQ,CZNR,CZJQ,ZT,DBH,DJLX,"+OplogFlag.CasLink+",XTBZ,decode(syd,1,'web端',0,'移动端','') as SYD,LLQ  ");
		sqltext.append(" b.mkbh,b.mkmc,b.url,b.xh,decode(b.qxbz,1,'启用',0,'禁用','') as qxbz,b.icon,b.xtbz  ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId(" b.mkbh ");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("zc_sys_mkb b ");//表名
		if(Validate.noNull(bh)){
			pageList.setStrWhere(" and b.mkbh like '"+bh+"%' "); 
		}
		if(Validate.noNull(treesearch)){
			pageList.setStrWhere(" and b.mkmc='"+CommonUtil.getEndText(treesearch)+"' "); 
		}
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 新增菜单
	 * @return
	 */
	@RequestMapping(value="goAddCdgl")
	public ModelAndView goAddCdgl(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
		String operateType = pd.getString("operateType");
		if ("C".equals(operateType)) {
			if (Validate.isNull(mkbh)) {// 生成一级菜单的模块编号
				mkbh = cdglService.getYjMkbh();
			} else {// 生成次级菜单编号
				mkbh = cdglService.getCjMkbh(mkbh);
			}
//			mv.addObject("mkbh", mkbh);
		}
		
		if("U".equals(operateType)){//修改回显
			Map<String, Object> map=null;
			 map = cdglService.getByMkbh(mkbh);
			 mv.addObject("map", map);
		} 
		mv.addObject("mkbh", mkbh);
		mv.addObject("operateType", operateType);
		mv.setViewName("systemset/cdgl/cdgl_edit");
		return mv;
	}
	/**
	 * 保存
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSaveKjzd() throws Exception {
		PageData pd = this.getPageData();
		String b = "";
		int i = 0;
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(pd.getString("json"),new TypeToken<HashMap<String, Object>>() {}.getType());
		List<Map<String, Object>> List = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : List) {
			i = cdglService.doSave(pd,map);
		}
		if (i == 1) {
			b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
		} else {
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	/**
	   * 删除
	   * @return
	   */
	@RequestMapping(value="/doDel",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String mkbh = pd.getString("mkbh");
     	int k = cdglService.doDel(mkbh);
		if(k>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
  	}
	}

}
