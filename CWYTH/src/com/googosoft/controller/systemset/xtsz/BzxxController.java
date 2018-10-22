package com.googosoft.controller.systemset.xtsz;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import org.springframework.transaction.annotation.Transactional;
import com.googosoft.pojo.systemset.xtsz.ZC_BZXX;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.xtsz.BzxxService;


/**
 * 帮助帮助信息维护控制类
 */
@Controller
@RequestMapping(value="/bzxx")
public class BzxxController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="bzxxService")
	private BzxxService bzxxService;//单例
	
	/**
	 * 获取帮助信息维护列表页面
	 */
	@RequestMapping(value="/goBzxxPage")
	public ModelAndView goBzxxPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/xtsz/bzxx_list");
		return mv;
	}
	
	/**
	 * 获取帮助信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getBzxxJson")
	@ResponseBody
	public Object getPageList(){
		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.ID,(select bh||'('||mc||')' from zc_bzml where bh = K.MLBH) AS MLBH,K.YWMC,(select rygh||'('||xm||')' from GX_SYS_RYB where rybh=K.JDR) as JDR,K.JDRQ,DECODE(K.SFXS,'1','是','0','否')AS SFXS ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("id");//主键
		pageList.setStrWhere("");//where条件
		pageList.setTableName("ZC_BZXX K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取帮助信息
	 * @return
	 */
	@RequestMapping(value="/getBzxx")
	public ModelAndView getBzxx(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		String id = pd.getString("id");
		/*获取需要设置帮助信息的目录编号*/
		List Ml_list = bzxxService.getMlbh(); 
		mv.addObject("Ml_list", Ml_list);
		if(operateType.equals("C")){
			Map map = new HashMap();
			mv.addObject("bzxx", map);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map map = bzxxService.getObjectById(pd.getString("id"));
			mv.addObject("Content",map.get("BZXX"));
			mv.addObject("bzxx", map);
		}
		mv.setViewName("systemset/xtsz/bzxx_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 保存帮助信息
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_BZXX bzxx) throws Exception{
		HttpServletRequest request = this.getRequest();
		String content = request.getParameter("content");
		String mlbh = request.getParameter("mlbh");
		String ywmc = request.getParameter("ywmc");
		String id = request.getParameter("id");
		bzxx.setMlbh(mlbh);
		bzxx.setYwmc(ywmc);
		String operateType = request.getParameter("operateType");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = bzxxService.doAdd(bzxx, content);
			if(i==1){
				b = "{\"success\":\"true\",\"id\":\""+bzxx.getId()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"该目录已存在帮助信息！\"}";
			}else{
				b = "{\"success\":\"false\",\"id\":\""+bzxx.getId()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i = bzxxService.doUpdate(bzxx, content,id);
			if(i==1){
				b = "{\"success\":\"true\",\"id\":\""+bzxx.getId()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b = "{\"success\":\"false\",\"id\":\""+bzxx.getId()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{\"success\":\"false\",\"id\":\"\",\"msg\":\"参数传入有误！\"}";
		}
		return b ;
	}
	
	/**
	 * 删除帮助信息
	 * @param bzxx
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doDelete(){
		
		PageData pd = this.getPageData();
		String id = pd.getString("id");
		String b = "";
		int k = bzxxService.doDelete(id);
		if(k>0){
			b= "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
		return b;
	}
	
	/**
	 * 获取帮助信息目录列表页面
	 */
	@RequestMapping(value="/goBzmlPage")
	public ModelAndView goBzmlPage(){		
		ModelAndView mv = this.getModelAndView();
		String bh = AutoKey.makeCkbh("ZC_BZML", "BH", "6");
		Map<Object, Object> map = new HashMap<Object, Object>();
		map.put("bh", bh);
		mv.addObject("mlxx", map);
		mv.setViewName("systemset/xtsz/bzxx_mllist");
		return mv;
	}
	public String getUrl(){
		PageData pd = this.getPageData();
		HttpServletRequest request = this.getRequest();
		StringBuffer url = request.getRequestURL();
		String newurl = url.substring(0,request.getRequestURL().length()-request.getServletPath().length());
		String http = newurl+"/";
		return http;
	}
}
