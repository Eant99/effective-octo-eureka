package com.googosoft.controller.systemset.xtsz;

import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.xtsz.ZC_BZML;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.xtsz.BzmlService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;

/**
 * 帮助目录维护控制类
 */
@Controller
@RequestMapping(value="/bzml")
public class BzmlController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="bzmlService")
	private BzmlService bzmlService;//单例
	
	/**
	 * 获取目录维护列表页面
	 */
	@RequestMapping(value="/goMlxxPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/xtsz/bzml_list");
		return mv;
	}
	
	/**
	 * 获取帮助目录维护列表数据
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("K.BH,K.MC,K.ZT ");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("BH");//主键
		pageList.setTableName("ZC_BZML K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 获取维护消息编辑页面
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			Map map = bzmlService.getObjectById(pd.getString("bh"));
			mv.addObject("mlxx", map);
		}else if (operateType.equals("C")){
			String bh = AutoKey.makeCkbh("ZC_BZML", "BH", "6");
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("bh", bh);
			mv.addObject("mlxx", map);
		}
		mv.setViewName("systemset/xtsz/bzml_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 目录维护信息保存
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(ZC_BZML bzml){
		String operateType = this.getPageData().getString("operateType");
		String bh = this.getPageData().getString("bh");
		String mc = this.getPageData().getString("mc");
		String zt = this.getPageData().getString("zt");
		bzml.setBh(bh);
		bzml.setMc(mc);
		bzml.setZt(zt);
		String b ="";
		int i;
		if("C".equals(operateType)){
			i = bzmlService.doAdd(bzml);
			if(i==1){
				b= "{\"success\":\"true\",\"bh\":\""+bzml.getBh()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b= "{\"success\":\"false\",\"bh\":\""+bzml.getBh()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i = bzmlService.doUpdate(bzml);
			if(i==1){
				b= "{\"success\":\"true\",\"bh\":\""+bzml.getBh()+"\",\"msg\":\"信息保存成功！\"}";
			}else{
				b= "{\"success\":\"false\",\"bh\":\""+bzml.getBh()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{\"success\":\"false\",\"bh\":\"\"\"msg\":\"参数传入有误！\"}";
		}
		return b;
	}
	
	/**
	 * 删除目录维护信息
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doDelete(){
		PageData pd = this.getPageData();
		String bh = pd.getString("bh");
		int k = bzmlService.doDelete(bh);
		if(k>0){
			return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		}else if(k==0){
			return "{\"success\":\"false\",\"msg\":\"该目录下已有帮助信息，请先删除帮助信息！\"}";
		}
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
	}

