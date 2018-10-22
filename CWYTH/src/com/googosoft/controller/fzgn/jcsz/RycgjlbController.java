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
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYCGJLB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.RycgjlbService;
import com.googosoft.service.fzgn.jcsz.RylwbService;
import com.googosoft.util.PageData;
/**
 * 人员成果奖励控制类
 * @author master
 */
@Controller
@RequestMapping(value="/rycgjlb")
public class RycgjlbController extends BaseController{
	
	@Resource(name="rycgjlbService")
	private RycgjlbService rycgjlbService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="rylwbService")
	private RylwbService rylwbService;
	
	/**
	 * 跳转人员成果奖励页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goCgjlListPage")
	public ModelAndView goCgjlListPage()
	{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("rybh",rybh);
		mv.addObject("xm",xm);
		mv.setViewName("fzgn/jcsz/rycgjlb_list");
		return mv;
	}
	@RequestMapping(value="/getCgjlList")
	@ResponseBody
	public Object getLwqkList()
	{
		PageList pageList = new PageList();
		//查询字段
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("T.GUID AS GUID,T.RYBH AS RYBH,");
		sqltext.append("(SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.CG+"' AND M.DM = T.CG) AS CG,");
		sqltext.append("TO_CHAR(T.RQ,'YYYY-MM-DD')AS RQ ,T.HJSM AS HJSM");		
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("GX_SYS_RYCGJLB T");
		//主键
		pageList.setKeyId("GUID");
		//where条件
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		pageList.setStrWhere("AND T.RYBH='"+rybh+"'");
		//合计
		pageList.setHj1("");
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 获取单个人员成果奖励详细信息
	 * @return
	 */
	@RequestMapping(value="/goRycgjlbPage")
	public ModelAndView goRycgjlbPage()
	{
		ModelAndView mv = this.getModelAndView();
		List<?> cg = dictService.getDict(Constant.CG);//成果
		mv.addObject("cgList",cg);
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		mv.addObject("rybh",rybh);
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		if(operateType.equals("U")||operateType.equals("L")){
			Map map = rycgjlbService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("rycgjlb",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
		}else if(operateType.equals("C")){
			Map<String,String> map = new HashMap<String, String>();
			mv.addObject("rycgjlb",map);
		}
		mv.setViewName("fzgn/jcsz/rycgjlb_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	/**
	 * 人员成果奖励信息保存
	 * @param rycgjlb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_RYCGJLB rycgjlb){
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		if("C".equals(operateType))
		{
			result = rycgjlbService.doAddRycgjl(rycgjlb);
			if(result==1)
			{
				return  MessageBox.show(true, MessageBox.SUCCESS_SAVE);
			}
			else
			{
				return  MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))
		{
			result = rycgjlbService.doUpdate(rycgjlb);
			if(result==1)
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
	 * 删除人员成果奖励信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		String guid = this.getPageData().getString("guid");
		int i =rycgjlbService.doDelete(guid);
		if(i>0)
		{
			return  MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}
		else
		{
			return  MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}


}
