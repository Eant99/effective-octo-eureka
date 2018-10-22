package com.googosoft.controller.fzgn.jcsz;

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
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_GWJJB;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.GwjjService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 岗位交接controller层
 * @author RC 2017-08-30
 */
@Controller
@RequestMapping(value="/gwjj")
public class GwjjController extends BaseController{
	
	@Resource(name="gwjjService")
	private GwjjService gwjjService;
	
	@Resource(name="pageService")
	private PageService pageService;

	/**
	 * 跳转到岗位交接列表页面
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/goGwjjListPage")
	public ModelAndView goGwjjListPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fzgn/jcsz/gwjj/gwjj_list");
		return mv;
	}
	
	/**
	 * 获取岗位交接列表数据
	 * @throws Exception 
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" k.gid as gid,k.ywfqr as ywfqrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.ywfqr) as ywfqr,k.ywfqrdh as ywfqrdh,");
		sqltext.append(" k.yqxsyr as yqxsyrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.yqxsyr) as yqxsyr,k.yqxsyrdh as yqxsyrdh,");
		sqltext.append(" k.jgr as jgrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.jgr) as jgr,k.jgrdh as jgrdh,k.gwjjyy as gwjjyy ");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("gx_sys_gwjjb k");
		//主键
		pageList.setKeyId("gid");
		//设置WHERE条件
		pageList.setStrWhere(" and k.jdr = '"+LUser.getRybh()+"' ");
		PageData pd = this.getPageData();
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 跳转到详细信息页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		mv.addObject("operateType", operateType);
		if("C".equals(operateType))
		{
			Map<String,String> map = new HashMap<String, String>();
			map.put("YWFQR", LUser.getXm());
			map.put("YWFQRDH", LUser.getLxdh());
			mv.addObject("gwjjb",map);
		}
		else if("L".equals(operateType))
		{
			String gid = pd.getString("gid");
			Map map = gwjjService.getObjectById(gid);//获取单据信息
			List jsxxList = gwjjService.getJsxx(gid);//获取角色信息
			mv.addObject("gwjjb",map);
			mv.addObject("jsxxList",jsxxList);
		}
		mv.setViewName("fzgn/jcsz/gwjj/gwjj_edit");
		return mv;
	}
	
	/**
	 * 保存岗位交接信息
	 * @param gwjjb
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_GWJJB gwjjb)
	{
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		String jsxx = pd.getString("jsxxs");
		if("C".equals(operateType))
		{
			if(gwjjService.doAdd(gwjjb, jsxx))
			{
				return MessageBox.show(true, "移交成功！");
			}
			else
			{
				return MessageBox.show(false, "移交失败！");
			}
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 导出人员信息Excel
	 * @return
	 */
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object expExcel(){
		PageData pd = this.getPageData();
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		//下载时文件名
		String filedisplay = pd.getString("xlsname") + ".xls";
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select k.gid as gid,k.ywfqr as ywfqrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.ywfqr) as ywfqr,k.ywfqrdh as ywfqrdh,");
		sqltext.append(" k.yqxsyr as yqxsyrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.yqxsyr) as yqxsyr,k.yqxsyrdh as yqxsyrdh,");
		sqltext.append(" k.jgr as jgrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.jgr) as jgr,k.jgrdh as jgrdh,k.gwjjyy as gwjjyy ");
		sqltext.append(" from GX_SYS_GWJJB K  where 1=1 and k.jdr = '"+LUser.getRybh()+"'");
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and k.gid in ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" order by k.ywfqr ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("ywfqr");
		m.setShowname("业务发起人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("ywfqrdh");
		m.setShowname("业务发起人电话");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("yqxsyr");
		m.setShowname("原权限所有人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("yqxsyrdh");
		m.setShowname("原权限所有人电话");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jgr");
		m.setShowname("接岗人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jgrdh");
		m.setShowname("接岗人电话");
		mlist.add(m);
		m = null;

		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
}
