package com.googosoft.controller.systemset.cssz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.cssz.ZC_PICTYPESET;
import com.googosoft.service.base.PageService;
import com.googosoft.service.systemset.cssz.TplxszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;

/**
 * 图片类型设置控制类 Create by 刘帅 on 2016-10-25 17:30
 */
@Controller
@RequestMapping(value = "/tplxsz")
public class TplxszControl extends BaseController {

	@Resource(name = "tplxszService")
	private TplxszService tplxszService;

	@Resource(name = "pageService")
	private PageService pageService;// 单例
	/**
	 * 获取图片类型设置    页面
	 */
	@RequestMapping(value="goTplxszPage")
	public ModelAndView goTplxszPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("systemset/cssz/tplxsz/tplxsz_list");
		return mv;
	}

	/**
	 * 图片类型设置（列表）
	 * 
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		sqltext.append("t.lxbh,t.djmc,t.lxmc,case t.sfbxsc when '1' then '是' else '否' end as sfbxsc");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("t.lxbh");// 主键
		pageList.setStrWhere(" and t.djlx in (select mkbh from "+SystemSet.sysBz+"mkb m where m.qxbz='1')");// where条件
		pageList.setTableName("ZC_PICTYPESET t");// 表名
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "", pageList.getTotalList().get(0).get("NUM") + "", gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}

	/**
	 * 图片类型设置：U/L:获取一天图片类型设置的详细信息 C:添加新的图片类型设置
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getTplxszPage")
	public ModelAndView getTplxszPage() {
		ModelAndView mv = this.getModelAndView();
		String operateType = this.getPageData().getString("operateType");
		 List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		 list = tplxszService.getDjmc();
		if (operateType.equals("U")) {
			Map map =tplxszService.getTplxszxx(this.getPageData().getString("lxbh"));
			mv.addObject("tpb", map);
			mv.addObject("djmclist", list);
			mv.setViewName("systemset/cssz/tplxsz/tplxsz_edit");
			mv.addObject("operateType", operateType);
			return mv;
		} else if (operateType.equals("C")) {
			String lxbh = AutoKey.makeCkbh("ZC_PICTYPESET", "LXBH", "6");
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.put("lxbh", lxbh);
			mv.addObject("tpb", map);
			mv.addObject("djmclist", list);
			mv.setViewName("systemset/cssz/tplxsz/tplxsz_edit");
			mv.addObject("operateType", operateType);
			return mv;
		}else if(operateType.equals("L")){
			Map map =tplxszService.getTplxszxx(this.getPageData().getString("lxbh"));
			mv.addObject("tpb", map);
			mv.setViewName("systemset/cssz/tplxsz/tplxsz_win");
			mv.addObject("operateType", operateType);
			return mv;
		}
		return null;
	}

	/**
	 * 图片类型设置（添加）：修改信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/saveTplxsz", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object saveTplxsz(ZC_PICTYPESET tpb) {
		String operateType = this.getPageData().getString("operateType");
		boolean isSuccess;
		switch (operateType) {
			case "C":// 添加图片设置信息
				isSuccess = tplxszService.doAdd(tpb);
				if (isSuccess)
					return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
				else
					return "{\"success\":\"false\",\"msg\":\"保存失败！\"}";
			case "U":// 修改图片设置信息
				 isSuccess = tplxszService.doUpdate(tpb);
				 if (isSuccess)
				 return "{\"success\":\"true\",\"msg\":\"保存成功！\"}";
				 else
				 return "{\"success\":\"false\",\"msg\":\"保存失败！\"}";
			default:
				return "{\"success\":\"false\",\"msg\":\"参数传入有误！\"}";
		}
	}

	/**
	 * 图片类型设置： 删除设置
	 */
	@RequestMapping(value = "/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String lxbh = this.getPageData().getString("lxbh");
		
		//判断要删除的图片类型有没有在用，有，不能删除
		if(tplxszService.doCheck(lxbh)){
			return "{\"success\":\"true\",\"msg\":\"所选择的图片类型已使用，不能删除！\"}";
		}else{
			boolean b = false;
			b = tplxszService.doDelete(lxbh);
			if (b) {
				return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
			} else {
				return "{\"success\":\"false\",\"msg\":\"信息删除失败！\"}";
			}
		}
		
	}
}
