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
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYJXB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.RyjxbService;
import com.googosoft.util.PageData;
/**
 * 人员进修情况控制类
 * @author master
 */
@Controller
@RequestMapping(value="/ryjxb")
public class RyjxbController extends BaseController{

	@Resource(name="ryjxbService")
	private RyjxbService ryjxbService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
		
	/**
	 * 跳转到列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goJxqkListPage")
	public ModelAndView goJxqkListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("rybh",rybh);
		mv.addObject("xm",xm);
		mv.setViewName("fzgn/jcsz/ryjxb_list");
		return mv;
	}
	
    /**
     * 获取列表数据
     */
	@RequestMapping(value="/getRyjxList")
	@ResponseBody
	public Object getRyjxList(){
		PageList pageList = new PageList();
		//查询字段
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.JXNR+"' AND M.DM = T.JXNR) AS JXNR," );
		sqltext.append("T.GUID,T.RYBH, ");
		sqltext.append(" DECODE(T.JXZL,'1','国内进修','2','国外进修')AS JXZL, ");
		sqltext.append("TO_CHAR(T.JXRQ,'YYYY-MM-DD') AS JXRQ,T.JXSJ ");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("GX_SYS_RYJXB T");
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
	 * 获取详细信息
	 * @return
	 */
	@RequestMapping(value="/goRyjxPage")
	public ModelAndView goRybPage(){
		ModelAndView mv = this.getModelAndView();
		List jxnr = dictService.getDict(Constant.JXNR);
		mv.addObject("jxnrList",jxnr);
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		mv.addObject("rybh",rybh);
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		if(operateType.equals("U")||operateType.equals("L"))
		{
			Map map = ryjxbService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("ryjxb",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
		}
		else if(operateType.equals("C"))
		{
			Map<String,String> map = new HashMap<String, String>();
			mv.addObject("ryjxb",map);
		}
		mv.addObject("operateType", operateType);
	 	mv.setViewName("fzgn/jcsz/ryjxb_edit");
		return mv;
	}
	
	/**
	 * 人员进修信息保存
	 * @param rywyspb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_RYJXB ryjxb){
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		if("C".equals(operateType))
		{
			result = ryjxbService.doAddRyjx(ryjxb);
			if(result==1)
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
			result = ryjxbService.doUpdate(ryjxb);
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
	 * 删除人员进修情况信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete()
	{
		String guid = this.getPageData().getString("guid");
		int result =ryjxbService.doDelete(guid);
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