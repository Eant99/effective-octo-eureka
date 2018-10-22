package com.googosoft.controller.fzgn.jcsz;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYLWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.RylwbService;
import com.googosoft.util.PageData;
/**
 * 人员论文情况控制类
 * @author master
 */
@Controller
@RequestMapping(value="/rylwb")
public class RylwbController extends BaseController{
	
	@Resource(name="rylwbService")
	private RylwbService rylwbService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	/**
	 * 跳转人员论文情况页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goLwqkListPage")
	public ModelAndView goLwqkListPage() throws Exception
	{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		mv.addObject("rybh",rybh);
		mv.setViewName("fzgn/jcsz/rylwb_list");
		return mv;
	}
	/**
	 * 人员论文情况列表
	 */
	@RequestMapping(value="/getLwqkList")
	@ResponseBody
	public Object getLwqkList() throws Exception
	{
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append("T.GUID AS GUID, T.RYBH AS RYBH,T.RQ AS RQ,T.LWTM AS LWTM,");
		sqltext.append("(SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.LWJB+"' AND M.DM = T.LWJB) AS LWJB,");
		sqltext.append("T.SFKW AS SFKW");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("AND T.RYBH='"+rybh+"'");//where条件
		pageList.setTableName("GX_SYS_RYLWB T");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 跳转编辑页面
	 * @return
	 */
	@RequestMapping(value="/goRylwbPage")
	public ModelAndView goRylwbPage()
	{
		ModelAndView mv = this.getModelAndView();
		List<Map<String, Object>>  lwjb = dictService.getDict(Constant.LWJB);
		mv.addObject("lwjbList",lwjb);
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		mv.addObject("rybh",rybh);
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		if(operateType.equals("U")||operateType.equals("L"))
		{
			Map<String, Object> map = rylwbService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("rylwb",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
		}
		else if(operateType.equals("C"))
		{
			Map<String,String> map = new HashMap<String, String>();
			mv.addObject("rylwb",map);
		}
		mv.setViewName("fzgn/jcsz/rylwb_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 人员论文信息保存
	 * @param rylwb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_RYLWB rylwb){
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		if("C".equals(operateType))
		{
			result = rylwbService.doAdd(rylwb);
			if(result>0)
			{
				return MessageBox.show(true, MessageBox.SUCCESS_SAVE);
				
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))
		{
			result= rylwbService.doUpdate(rylwb);
			if(result>0)
			{
				return MessageBox.show(true, MessageBox.SUCCESS_SAVE);
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 删除人员论文情况信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete()
	{
		String guid = this.getPageData().getString("guid");
		int result =rylwbService.doDelete(guid);
		if(result>0)
		{
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

}
