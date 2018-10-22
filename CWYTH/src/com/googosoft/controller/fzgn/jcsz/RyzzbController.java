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
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYZZB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.RyzzbService;
import com.googosoft.util.PageData;
/**
 * 人员著作情况控制类
 * @author master
 */
@Controller
@RequestMapping(value="/ryzzb")
public class RyzzbController extends BaseController{
	
	@Resource(name="ryzzbService")
	private RyzzbService ryzzbService;
	
	@Resource(name="pageService")
	private PageService pageService;
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	/**
	 * 获取人员著作情况页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goZzqkListPage")
	public ModelAndView goZzqkListPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		String xm = CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		mv.addObject("rybh",rybh);
		mv.setViewName("fzgn/jcsz/ryzzb_list");
		return mv;
	}
    /**
     * 著作情况表信息
     */
	@RequestMapping(value="/getZzqkList")
	@ResponseBody
	public Object getLwqkList()
	{
		PageList pageList = new PageList();
		//查询字段
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" T.GUID AS GUID,T.RYBH AS RYBH," );
		sqltext.append(" TO_CHAR(T.RQ,'YYYY-MM-DD') AS RQ, ");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.ZZJB+"' AND M.DM = T.ZZJB) AS ZZJB, ");
		sqltext.append(" T.ZZMC AS ZZMC " );
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("GX_SYS_RYZZB T");
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
	 * 获取个人著作情况详细信息
	 * @return
	 */
	@RequestMapping(value="/goRyzzbPage")
	public ModelAndView goRyzzbPage()
	{
		ModelAndView mv = this.getModelAndView();
		List zzjb = dictService.getDict(Constant.ZZJB);//著作级别
		mv.addObject("zzjbList",zzjb);
		String operateType = this.getPageData().getString("operateType");
		PageData pd = this.getPageData();
		String rybh = pd.getString("rybh");
		mv.addObject("rybh",rybh);
		String xm =  CommonUtil.getXm(rybh);
		mv.addObject("xm",xm);
		if(operateType.equals("U")||operateType.equals("L"))
		{
			Map map = ryzzbService.getObjectById(this.getPageData().getString("guid"));
			mv.addObject("ryzzb",map);
			mv.addObject("guid",this.getPageData().getString("guid"));
		}else if(operateType.equals("C"))
		{
			Map<String,String> map = new HashMap<String, String>();
			mv.addObject("ryzzb",map);
		}
		mv.setViewName("fzgn/jcsz/ryzzb_edit");
		mv.addObject("operateType", operateType);
		return mv;
	}
	
	/**
	 * 保存
	 * @param ryzzb
	 * @return
	 */
	@RequestMapping(value="/saveRyzz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object saveRyzz(GX_SYS_RYZZB ryzzb){
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		if("C".equals(operateType))
		{
			result = ryzzbService.doAddRylw(ryzzb);
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
			result = ryzzbService.doUpdate(ryzzb);
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
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		String guid = this.getPageData().getString("guid");
		int i =ryzzbService.doDelete(guid);
		if(i>0){
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		}else{
			return MessageBox.show(true, MessageBox.FAIL_DELETE);
		}
	}
}
