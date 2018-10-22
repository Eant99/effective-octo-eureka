package com.googosoft.controller.fzgn.sjzd;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.googosoft.pojo.fzgn.sjzd.GX_SYS_DMB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.sjzd.DmbService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 数据字典控制类
 * @author googosoft
 *
 */
@Controller
@RequestMapping(value="/dmb")
public class DmbController extends BaseController{
		
	@Resource(name="dmbService")
	private DmbService dmbService;//单例
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	/**
	 * 跳转数据字典列表页面
	 * @return
	 */
	@RequestMapping(value="/goDmbPage")
	public ModelAndView goDmbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String treesearch = pd.getString("treesearch");
		String treeid = pd.getString("tid");
		mv.setViewName("fzgn/sjzd/dmb_list");
		mv.addObject("treesearch", treesearch);
		mv.addObject("treeid",treeid);
		return mv;
	}
	
	/**
	 * 获取数据字典列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("K.DMXH,K.DMSX,K.DM,K.MC,K.JB,K.ZL,K.MS,(SELECT '('||KK.DM||')'||KK.MC FROM GX_SYS_DMB KK WHERE KK.DM=K.ZL AND KK.ZL='00') AS SJQC,CASE K.BZ WHEN '1' THEN '国家标准' WHEN '0' THEN '用户自定义' END AS BZ,K.BZ AS BZS");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dmxh");//主键
		if(Validate.noNull(treeDm)){
			pageList.setStrWhere(pageList.getStrWhere()+" AND k.zl in(select dm from GX_SYS_DMB g where g.jb='"+treeDm+"' and zl=(select zl from GX_SYS_DMB where dm='"+treeDm+"'and zl = '00') union all(select zl from GX_SYS_DMB where zl='"+treeDm+"'))");
		}
		pageList.setStrWhere(pageList.getStrWhere()+" and K.zl !='00' ");//where条件
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			pageList.setStrWhere(pageList.getStrWhere()+" and (k.zl in(select t.dm from GX_SYS_DMB t where  t.dm='"+treesearch.substring(1, treesearch.indexOf(")"))+"') )  ");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		pageList.setTableName("GX_SYS_DMB K ");//表名
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping(value="/getzdPageList")
	@ResponseBody
	public Object getzdPageList() throws Exception{
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("K.DMXH,K.DMSX,K.ZL,K.DM,K.MC,K.MS,K.JB,CASE K.BZ,K.SJQC WHEN '1' THEN '国家标准' WHEN '0' THEN '用户自定义' END AS BZ,K.BZ AS BZS");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dmxh");//主键
		pageList.setStrWhere(pageList.getStrWhere()+" and zl ='00' ");//where条件
		pageList.setTableName("GX_SYS_DMB K");//表名
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 数据字典上级代码列表
	 * @return
	 */
	@RequestMapping(value="/zd1page")
	@ResponseBody
	public Object gozdxxPage(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("K.DMXH,K.DMSX,K.ZL,K.DM,K.MC,K.MS,K.JB,CASE K.BZ WHEN '1' THEN '国家标准' WHEN '0' THEN '用户自定义' END AS BZ,K.BZ AS BZS");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("dmxh");//主键
		pageList.setStrWhere(pageList.getStrWhere()+" and zl ='00' ");//where条件
		pageList.setTableName("GX_SYS_DMB K");//表名
		pageList.setHj1("");//合计
		pageList = pageService.findWindowList(pd,pageList,"ZD");//引用传递联想查询
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
		
		
	}
	/**
	 * 获取数据字典编辑页面信息
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		List<Map<String, Object>> dmsx = dictService.getDict(Constant.DMSX);//代码属性
		ModelAndView mv = this.getModelAndView();
		mv.addObject("dmsxlist", dmsx);
		@SuppressWarnings("rawtypes")
		Map map = new HashMap();
		map = dmbService.getObjectById(pd.getString("dmxh"));//代码详情信息
		mv.addObject("dmb", map);
//		if("U".equals(operateType)){
//			map = dmbService.getObjectById(pd.getString("dmxh"));
//			mv.addObject("dmb", map);
//		}else {
//
//		}
		mv.setViewName("fzgn/sjzd/dmb_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 编辑数据字典信息时的保存操作
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_DMB dmb)throws Exception{
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String b = "";
		int i;
		String zls = pd.getString("zl");
		String zl =  zls.substring(1, zls.indexOf(")"));			
		String jb = pd.getString("jb");			
		if("[1,A,B,C,D,E,F,G]".contains(zl)){
		jb = zl;
		zl = Constant.DMBZL;
	    }
		dmb.setZl(zl);
		dmb.setJb(jb);
		dmb.setSjqc(zls);
		if("C".equals(operateType)){//新增
			String dmxh = this.get32UUID();//获取32位uuid
			dmb.setDmxh(dmxh);//赋值
			i = dmbService.doAdd(dmb, operateType);
			if(i==1){
				b = "{\"success\":\"true\",\"dmxh\":\""+dmb.getDmxh()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"dmxh\":\"\",\"msg\":\"同一类别下代码编号或名称不可重复！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){//修改
			i = dmbService.doUpdate(dmb, operateType);
			if(i==1){
				b = "{\"success\":\"true\",\"dmxh\":\""+dmb.getDmxh()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"dmxh\":\"\",\"msg\":\"同一类别下代码编号或名称不可重复！\"}";
			}else{
				b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{\"success\":\"false\",\"dmxh\":\"\",\"msg\":\"参数传入有误！\"}";
		}
		return b;
	}
	/**
	 * 删除数据字典信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String dmxh = pd.getString("dmxh");
		int b = dmbService.doDelete(dmxh);
		if(b>0){
			return "{\"success\":\"true\",\"msg\":\"删除成功！\"}";    
		}else{
			return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
		}
	}
	
	/**
	 * 校验代码名称是否重复
	 * @throws IOException 
	 */	
	@RequestMapping(value="/checkMc")
	@ResponseBody
	public int checkMc(GX_SYS_DMB dmb) {
		int i=0;
		String mc = this.getPageData().getString("mc");
		i=dmbService.doCheck(dmb,mc);
		return i;
	}
}
