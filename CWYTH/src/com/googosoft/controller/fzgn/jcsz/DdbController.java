package com.googosoft.controller.fzgn.jcsz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.ZC_SYS_DDB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.DdbService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 地点信息控制类
 * @author JiaTong
 * 1028整理
 * 1029再次整理
 */
@Controller
@RequestMapping(value="/ddb")
public class DdbController extends BaseController{
	
	@Resource(name="ddbService")
	private DdbService ddbService; //单例
	
	@Resource(name="dictService")
	private DictService dictService;//数据字典单例
	
	@Resource(name="pageService")
	private PageService pageService;//分页单例
	
	/**
	 * ajax获取实时该单位节点下是否还有节点（是否含有下级单位）
	 * @return
	 */
	@RequestMapping(value="/getNewStatus",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getNewStatus(){
		PageData pd = this.getPageData();
		String sjdd = pd.getString("sjdd");
		String count = ddbService.getNewStatus(sjdd);
		if("0".equals(count)){
			return  "{success:'true'}";
		}else{
			return  "{success:'false',msg:'请将该单位下所有节点单位状态禁用后重试!'}";
		}
	}
	
	/**
	 * 获取地点信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody //匹配Ajax
	public Object getPageList(){
		PageData pd = this.getPageData();
		String ddbh= pd.getString("treeddbh");
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" D.DDBH,D.MC,(CASE D.SJDD WHEN '000000' THEN '无' ELSE (SELECT '('||A.DDH||')'||to_char(A.MC) FROM ZC_SYS_DDB A WHERE A.DDBH=D.SJDD) END)AS SJDD,");
		sqltext.append(" D.DWBH AS DW,(SELECT '('||A.DWBH||')'||A.MC FROM GX_SYS_DWB A WHERE A.DWBH=D.DWBH) AS DWBH,");
		sqltext.append(" case D.DDZT when '1' then '正常' when '0' then '禁用' end DDZT,D.DDJC,D.PXXH,D.DDH");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("ddbh");//主键
		//where条件
		if(Validate.noNull(ddbh)){
			pageList.setStrWhere(" and D.ddbh in(select ddbh from zc_sys_ddb connect by prior ddbh=sjdd start with ddbh='" + ddbh + "')");
		}
		pageList.setTableName("ZC_SYS_DDB D");
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 新建、保存地点信息（添加地点信息）
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_SYS_DDB ddb){
		String operateType = this.getPageData().getString("operateType");
		String b = "";
		int i;
		if("C".equals(operateType)){
			i = ddbService.doAdd(ddb);
			if(i==1){
				b = "{\"success\":\"true\",\"ddbh\":\""+ddb.getDdbh()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"ddbh\":\"\",\"msg\":\"地点号不可重复！\"}";
			}else{
				b = "{\"success\":\"false\",\"ddbh\":\""+ddb.getDdbh()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else if("U".equals(operateType)){
			i=ddbService.doUpdate(ddb);
			if(i==1){
				b = "{\"success\":\"true\",\"ddbh\":\""+ddb.getDdbh()+"\",\"msg\":\"信息保存成功！\"}";
			}else if(i==0){
				b = "{\"success\":\"false\",\"ddbh\":\"\",\"msg\":\"地点号不可重复！\"}";
			}else{
				b = "{\"success\":\"false\",\"ddbh\":\""+ddb.getDdbh()+"\",\"msg\":\"信息保存失败！\"}";
			}
		}else{
			b = "{'success':'F','msg':'参数传入有误！'}";
		}
		return b;
	}
	
	/**
	 * 删除地点信息
	 * @param ddb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		PageData pd = this.getPageData();
		String ddbh = pd.getString("ddbh");
		int k = ddbService.doDelete(ddbh);
		if(k>0){
			return "{success:true,msg:'信息删除成功！'}";
		}else if(k==0){
			return "{success:false,msg:'该地点信息存在资产，不能删除！'}";
		}else{
			return "{success:false,msg:'信息删除失败！'}";
		}
	}

	/**
	 * 跳转Edit页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//此处可加来自设置表的一些内容
		String operateType = pd.getString("operateType");
		if(operateType.equals("C")){
			//左侧树点击传来的地点编号
			String sjddbh = pd.getString("ddbh");
			
			String bh = ddbService.getDdbh();
			Map<String,String> map = new HashMap<String,String>();
			map.put("DDBH", bh);
			map.put("DDH", bh);
			//查询上级单位
			if(Validate.noNull(sjddbh)){
				String sjddmc = ddbService.findDdbh(sjddbh);
				map.put("sjddmc", sjddmc);
			}
			mv.addObject("ddb", map);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map map = ddbService.getObjectById(pd.getString("ddbh"));
			mv.addObject("ddb", map);
		}
		mv.setViewName("fzgn/jcsz/ddb_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 跳转Edit页面
	 * @return
	 */
	@RequestMapping(value="/goLookPage")
	public ModelAndView goLookPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.addObject("operateType",pd.getString("operateType"));
		mv.addObject("ddb", ddbService.getObjectById(pd.getString("ddbh")));
		mv.setViewName("window/ddxx/ddb_win");
		return mv;
	}
	
	/**
	 * 跳转Edit页面
	 * @return
	 */
	@RequestMapping(value="/goTyDdbWin")
	public ModelAndView goTyDdbWin(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		mv.addObject("operateType",pd.getString("operateType"));
		mv.addObject("ddb", ddbService.getObjectById(pd.getString("ddbh")));
		mv.setViewName("window/ddxx/ddb_tywin");
		return mv;
	}
	
	/**
	 * 获取地点信息列表页面(改名为 goDdbPage)
	 * @return
	 */
	@RequestMapping(value="/goDdbPage")
	public ModelAndView goDdbPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String ddbh = pd.getString("ddbh");
		mv.setViewName("fzgn/jcsz/ddb_list");
		mv.addObject("ddbh", ddbh);
		return mv;
	}
	
	/**
	 * 所在单位信息
	 * @return
	 */
	@RequestMapping(value="/findSzdw")
	public ModelAndView findSzdw(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		String windowModel = pd.getString("windowModel");
		String from = pd.getString("from");
		mv.setViewName("window/dwxx/dwxxList");
		mv.addObject("pname",pname);
		mv.addObject("controlId",controlId);
		mv.addObject("windowModel", windowModel);
		mv.addObject("from", from);
		return mv;
	}
	/**
	 * 所在单位弹窗
	 * @return
	 */
	@RequestMapping(value="/szdwWin")
	public ModelAndView szdwWin(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String controlId = pd.getString("controlId");
		mv.setViewName("window/dwxx/window");
		mv.addObject("controlId",controlId);
		return mv;
	}
	/**
	 * 上级地点弹窗
	 * @return
	 */
	@RequestMapping(value="/sjddWin")
	public ModelAndView sjddWin(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String controlId = pd.getString("controlId");
		mv.setViewName("window/ddxx/window");
		mv.addObject("controlId",controlId);
		return mv;
	}
	/**
	 * 地点信息信息
	 * @return
	 */
	@RequestMapping(value="/findDdxx")
	public ModelAndView findDdxx(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String pname = pd.getString("pname");
		String controlId = pd.getString("controlId");
		mv.setViewName("window/ddxx/ddxxList");
		mv.addObject("pname",pname);
		mv.addObject("controlId",controlId);
		return mv;
	}
	
	/**
	 * 获取批量赋值地点信息数据
	 */
	@RequestMapping(value="/goPlfuzhiPage")
	public ModelAndView goPlfuzhiPage(){
		ModelAndView mv = this.getModelAndView();
		String ddbh = this.getPageData().getString("ddbh");
		mv.addObject("ddbh", ddbh);
		mv.setViewName("fzgn/jcsz/ddxx_fz");
		return mv;
	}
	/**
	 * 批量赋值地点信息
	 */
	@RequestMapping(value="/doplFuzhi",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doPlFuzhi(){
		PageData pd = this.getPageData();
		String ddbh = pd.getString("ddbh");
		String ziduan = pd.getString("ziduan");
		String zdValue = pd.getString("zdValue");
		if(ziduan.equals("dwbh")){
			zdValue = CommonUtil.getDwXx(zdValue.toString());
		}
		int k = ddbService.doPlFuzhi(ddbh,ziduan,zdValue);
		if(k>0){
			return "{\"success\":\"true\",\"msg\":\"批量赋值成功！\"}";
		}else{
			return "{\"success\":\"false\",\"msg\":\"批量赋值失败！\"}";
		}
	}	
	
	/**
	 * 存放地点设置
	 * 通过地点号(名称)查询地点编号
	 */
	@RequestMapping(value="/getDdbh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getDdbh(){
		String ddmc = Validate.isNullToDefault(this.getRequest().getParameter("DDMC"), "")+"";
		String ddbh = ddbService.findDdbhByDdmc(ddmc);
		return ddbh ;
	}
	
	/**
	 * 导出人员信息Excel
	 * @return
	 */
	@RequestMapping(value="/expExcel",produces ="text/json;charset=UTF-8")
	@ResponseBody
	public Object ExpExcel(){
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
		sqltext.append(" SELECT D.DDBH,D.MC,(CASE D.SJDD WHEN '000000' THEN '无' ELSE (SELECT '('||A.DDH||')'||to_char(A.MC) FROM ZC_SYS_DDB A WHERE A.DDBH=D.SJDD) END)AS SJDD,");
		sqltext.append(" D.DWBH AS DW,(SELECT '('||A.DWBH||')'||A.MC FROM GX_SYS_DWB A WHERE A.DWBH=D.DWBH) AS DWBH,");
		sqltext.append(" case D.DDZT when '1' then '正常' when '0' then '禁用' end DDZT,D.DDJC,D.PXXH,D.DDH");
		sqltext.append(" FROM ZC_SYS_DDB D  where 1=1 ");
		String ddbh = pd.getString("treeddbh");
		//where条件
		if(Validate.noNull(ddbh)){
			sqltext.append(" and D.ddbh in(select ddbh from zc_sys_ddb connect by prior ddbh=sjdd start with ddbh='" + ddbh + "')");
		}
		sqltext.append(CommonUtils.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" AND D.DDBH IN ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" ORDER BY D.DDH ");
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		m1.setColtype("String");
		m1.setName("ddh");
		m1.setShowname("地点号");
		mlist.add(m1);
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("mc");
		m2.setShowname("地点名称");
		mlist.add(m2);
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("dwbh");
		m3.setShowname("所属单位");
		mlist.add(m3);
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("sjdd");
		m4.setShowname("上级地点");
		mlist.add(m4);
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("ddzt");
		m5.setShowname("地点状态");
		mlist.add(m5);
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
}