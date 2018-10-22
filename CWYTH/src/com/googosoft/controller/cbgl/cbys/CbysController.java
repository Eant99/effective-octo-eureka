package com.googosoft.controller.cbgl.cbys;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.cbgl.cbys.CbysService;
import com.googosoft.service.fzgn.wxzf.CbsglService;
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("/cbys")
public class CbysController extends BaseController {
	@Resource(name="cbsglService")
	private CbsglService objService;
	
	@Resource(name="dictService")
	private DictService dictService;
	
	@Resource(name = "pageService")
	private PageService pageService;
	
	@Resource(name="fileService")
	private FileService fileService;
	
	@Resource(name="kmszService")
	private KmszService kmszService;
	
	@Resource(name="cbysService")
	private CbysService cbysService;
	
	/**
	 * 获取成本要素列表页面
	 * @return
	 */
	@RequestMapping(value = "/goPageList")
	public ModelAndView goPageList() {
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("cbgl/cbys/Cbys_list");
		return mv;		
	}
	/**
	 * 获取经济科目设置页面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getCbysPageList")
	@ResponseBody
	public Object getCbysPageList(HttpSession session) throws Exception{
		String sszt = Constant.getztid(session);
		PageData pd = this.getPageData();
		String treeDm = pd.getString("treeDm");
		Calendar date = Calendar.getInstance();
		String jn=String.valueOf(date.get(Calendar.YEAR));//今年
		
		String kmnd = Validate.isNullToDefaultString(pd.getString("kmnd"), jn);
		
		String treesearch = pd.getString("treesearch");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("guid,j.kmnd,j.KMBH,j.KMMC,KMJC,L,K,SM,DECODE(QYF,'1','是','否')QYF");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("guid");//主键
		String strWhere = "and sszt ='"+sszt+"'";
		if(Validate.noNull(treeDm)){
			strWhere += " and (j.k='"+treeDm+"' or j.l='"+treeDm+"' or kmbh='"+treeDm+"')";
		}
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			strWhere +=" and j.kmmc='"+CommonUtil.getEndText(treesearch)+"' ";	//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		pageList.setStrWhere(strWhere);
		pageList.setTableName("CW_JJKMB j");//表名
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 编辑经济科目
	 * @return
	 */
	@RequestMapping(value="/goEditCbysPage")
	public ModelAndView goEditCbysPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = cbysService.getObjectByIdByKmsz(pd.getString("guid"));
		}
		String kmjc = pd.getString("kmjc");
		String dm = pd.getString("dm");
		String l = pd.getString("l");
		String k = pd.getString("k");
		String type = pd.getString("type");
		String guid = GenAutoKey.get32UUID();
		if("tj".equals(type)){
			mv.addObject("l", l);
			mv.addObject("k", k);
			map.put("l", l);
			map.put("k", k);
			map.put("guid", guid);
			map.put("kmjc", kmjc);
		}else if("xj".equals(type)){
			mv.addObject("k", dm);
			mv.addObject("l", k);
			map.put("l", k);
			map.put("k", dm);
			map.put("guid",guid);
			map.put("kmjc", kmjc);
		}
		mv.addObject("kmjc", kmjc);
		mv.addObject("dm", dm);
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		
		mv.setViewName("cbgl/cbys/Cbys_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("guid");
		String kmbh = this.getPageData().getString("kmbh");
		if(cbysService.getCountByKmbh(kmbh)>0){
			return MessageBox.show(false,"存在末级科目，无法删除");
		}
		int i = cbysService.doDel(guid);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
	/**
	 * 添加单位信息
	 * @param dwb
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(KMSZ kmsz,HttpSession session){
		String operateType = this.getPageData().getString("operateType");
		
		int result=0;
		if("C".equals(operateType))//新增
		{  
			//判断部门号是否重复
			boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
			if(checkbmh==false)
			{
				return  "{success:false,msg:'要素编号不可重复!'}";
			}
			//生成单位编号
			result = kmszService.doAdd(kmsz,session);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			
			if(!kmsz.getKmbh().equals(kmszService.getObjectByIdByKmsz(kmsz.getGuid()).get("KMBH")+""))
			{
				boolean checkbmh=kmszService.doCheckKmbh(kmsz.getKmbh());
				if(checkbmh==false)
				{
					return "{success:false,msg:'科目编号不可重复！'}";
				}
			}
			result = kmszService.doUpdate(kmsz);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	/**
	 * 查看经济科目
	 * @return
	 */
	@RequestMapping(value="/goLookCbysPage")
	public ModelAndView goLookJjPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map map = new HashMap();
		String operateType = pd.getString("operateType");
		if(operateType.equals("U")||operateType.equals("L")){
			map = kmszService.getObjectByIdByKmsz(pd.getString("guid"));
		}
		String kmjc = pd.getString("kmjc");
		String dm = pd.getString("dm");
		String l = pd.getString("l");
		String k = pd.getString("k");
		String type = pd.getString("type");
		String guid = GenAutoKey.get32UUID();
		if("tj".equals(type)){
			mv.addObject("l", l);
			mv.addObject("k", k);
			map.put("l", l);
			map.put("k", k);
			map.put("guid", guid);
			map.put("kmjc", kmjc);
		}else if("xj".equals(type)){
			mv.addObject("k", dm);
			mv.addObject("l", k);
			map.put("l", k);
			map.put("k", dm);
			map.put("guid",guid);
			map.put("kmjc", kmjc);
		}
		mv.addObject("kmjc", kmjc);
		mv.addObject("dm", dm);
		mv.addObject("kmsz", map);
		mv.addObject("gid", pd.getString("id"));
		
		mv.setViewName("cbgl/cbys/Cbys_look");
		mv.addObject("operateType",operateType);
		return mv;
	}
	/**
	 * 成本要素单位树
	 * 
	 */
	@RequestMapping(value="/CbysTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object CbysTree(HttpServletResponse response) throws java.io.IOException{
		PageData pd = this.getPageData();	
		String rootPath = this.getRequest().getContextPath();
		String menu = pd.getString("menu");
		String jb = pd.getString("dm");
		if("get-jjkm".equals(menu)){
			if("root".equals(jb)){//
				return cbysService.getjjkmNodezj(pd,"",rootPath);
			}else{				
				return cbysService.getjjkmNodezj(pd,jb,rootPath);
			}
		}else{
			return "";
		}
	}
}
