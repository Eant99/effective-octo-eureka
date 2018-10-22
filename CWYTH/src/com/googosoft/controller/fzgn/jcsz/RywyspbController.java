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
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYWYSPB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.RywyspbService;
import com.googosoft.util.PageData;
/**
 * 人员外语水平控制类
 * @author master
 */
@Controller
@RequestMapping(value="/rywyspb")
public class RywyspbController extends BaseController{

	@Resource(name="rywyspbService")
	private RywyspbService rywyspbService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	
	/**
	 * 获取人员外语水平情况页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goWyspListPage")
	public ModelAndView goWyspListPage() throws Exception{
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		mv.addObject("rybh",rybh);
		mv.setViewName("fzgn/jcsz/rywyspb_list");
		return mv;
	}
    /**
     * 外语水平列表信息
     */
	@RequestMapping(value="/getWyspList")
	@ResponseBody
	public Object getWyspList() throws Exception{
		PageData pd = this.getPageData();
		PageList pageList = new PageList();
		//查询字段
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" T.GUID AS GUID,T.RYBH AS RYBH," );
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.WYSP+"' AND M.DM = T.SP) AS SP, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.WYYZ+"' AND M.DM = T.YZ) AS YZ ");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("GX_SYS_RYWYSPB T");
		//主键
		pageList.setKeyId("GUID");
		//where条件
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
	 * 获取单个人员详细信息
	 * @return
	 */
	@RequestMapping(value="/goRywyspbPage")
	public ModelAndView goRywyspbPage(){
		ModelAndView mv = this.getModelAndView();
		List<?> wyyz = dictService.getDict(Constant.WYYZ);//外语语种
		List<?> wysp = dictService.getDict(Constant.WYSP);//外语水平
		mv.addObject("wyyzList",wyyz);
		mv.addObject("wyspList",wysp);
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		mv.addObject("rybh",rybh);
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		if(operateType.equals("U")||operateType.equals("L"))
		{
			Map<?, ?> map = rywyspbService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("rywyspb",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
		}
		else if(operateType.equals("C"))
		{
			Map<String,String> map = new HashMap<String, String>();
			mv.addObject("rywyspb",map);
		}
		mv.setViewName("fzgn/jcsz/rywyspb_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	
	/**
	 * 人员外语水平信息保存
	 * @param rywyspb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_RYWYSPB rywyspb)
	{
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		if("C".equals(operateType))
		{
			result = rywyspbService.doAdd(rywyspb);
			if(result==1)
			{
				return MessageBox.show(true, MessageBox.SUCCESS_SAVE);
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if("U".equals(operateType))
		{
			result = rywyspbService.doUpdate(rywyspb);
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
	 * 删除人员外语水平信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		String guid = this.getPageData().getString("guid");
		int result =rywyspbService.doDelete(guid);
		if(result>0){
			return  MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}
}
