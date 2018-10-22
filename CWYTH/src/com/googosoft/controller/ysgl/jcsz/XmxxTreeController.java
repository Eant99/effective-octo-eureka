package com.googosoft.controller.ysgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.xmsz.Xmxx2Service;
import com.googosoft.service.ysgl.xmsz.XmxxService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/xmxxt")
public class XmxxTreeController extends BaseController{
	@Resource(name = "pageService")
	private PageService pageService;//获取页面

	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="xmxx2Service")
	private Xmxx2Service xmxxService;//单例
	
	/**
	 * 跳转项目信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goXmxxEditPage")
	public ModelAndView goXmxxEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		String operateType = pd.getString("operateType");
		String guid = pd.getString("dwbh");
		if(operateType.equals("C")){
			mv.addObject("ssxmfl", dictService.getDictBySplit(Constant.XMFL));
			mv.addObject("xmxz", dictService.getDictBySplit(Constant.XMXZ));
			
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = xmxxService.getObjectById(pd.getString("dwbh"));
			mv.addObject("ssxmfl", dictService.getDictBySplit(Constant.XMFL));
			mv.addObject("xmxz", dictService.getDictBySplit(Constant.XMXZ));
			mv.addObject("dwb", map);
			mv.addObject("guid", guid);
		}
		mv.addObject("operateType", operateType);
		mv.setViewName("ysgl/jcsz/xmxx/xmxx_edit");
		return mv;
	}

    /**
	 * 获取项目信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goXmxxPage")
	public ModelAndView goXmxxPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		//从数据字典中获取单位性质下拉框内容
		mv.setViewName("ysgl/jcsz/xmxx/xmxx_list");
		mv.addObject("dwbh",dwbh);
		return mv;
	}
	
	@RequestMapping(value="/goXmxxTree")
	public ModelAndView goXmxxTree(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		//从数据字典中获取单位性质下拉框内容
		mv.setViewName("ysgl/jcsz/xmxx/xmxxTree");
		mv.addObject("dwbh",dwbh);
		return mv;
	}
	/**
	 * 获取项目信息列表数据
	 * 
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( select b.guid,b.xmbh,b.xmmc,(select d.mc from gx_sys_dmb d where d.zl='xmfl' and d.dm = b.ssxmfl) ssxmflmc,"
				+ "(select a.xmmc from cw_xmxxb a where a.guid=b.sjxm) sjxm,(case b.ywsx when '1' then '有' else '无' end) ywsxmc,to_char(b.sxje,'FM9999999999.0000') sxje,(case b.sfyxcz when '1' then '是' else ' 否' end) sfyxczmc,"
				+ "b.czbl,b.txbfb,b.xmnd,(select '('||b.ssxb||')'||d.mc from gx_sys_dwb d where d.dwbh=b.ssxb) ssxb,(select d.mc from gx_sys_dmb d where d.zl='xmxz' and d.dm = b.xmxz) xmxzmc,"
				+ "(case b.sfqd when '1' then '是' else ' 否' end) sfqdmc,to_char(b.qysj,'yyyy-mm-dd') qysj,to_char(b.wcsj,'yyyy-mm-dd') wcsj "
				+ ", b.kzfw from cw_xmxxb b ) k");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("GUID");//主键
		//设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");//合计
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 保存项目信息
	 * 
	 * @param ryb
	 * @return
	 */
	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave() {
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result = 0;
		if ("C".equals(operateType))//新增保存 
		{
			// 判断工号是否重复，与人员表中的用户名验证，因为工号是作为用户名登录相同的
			int flag = xmxxService.getObjectById(pd);
			if (flag!=0) {
				return MessageBox.show(false, "该项目名称已经存在，请重新输入！");
			}
			result = xmxxService.doAdd(pd);
			if (result > 0) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}else if ("U".equals(operateType))// 修改
		{
			result = xmxxService.updateXmxx(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
		
	}
	/**
	 * 删除
	 * 
	 * @return
	 */
	@RequestMapping(value = "/doDelete", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete() {
		String guid = this.getPageData().getString("dwbh");
		PageData pd = this.getPageData();
		int i = xmxxService.doDelete(pd);
		if (i > 0) {
		
			return "{\"success\":\"true\",\"msg\":\"信息删除成功！\"}";
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

	//导出
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
		String searchValue = pd.getString("searchJson");
		System.out.println("searchValue============="+searchValue);
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" SELECT");
		sqltext.append(" K.GUID AS GUID,K.XMBH AS XMBH,K.XMMC AS XMMC,(select d.mc from gx_sys_dmb d where d.zl='xmfl' and d.dm = k.ssxmfl) AS SSXMFL,(select a.xmmc from cw_xmxxb a where a.guid=k.sjxm) AS SJXM,(case K.YWSX when '1' then '有' else '无' end) AS YWSX,K.SXJE AS SXJE,(case K.SFYXCZ when '1' then '是' else ' 否' end) SFYXCZ,K.CZBL AS CZBL,K.TXBFB AS TXBFB,K.XMND AS XMND,K.SSXB AS SSXB,(select d.mc from gx_sys_dmb d where d.zl='xmxz' and d.dm = k.xmxz) AS XMXZ,(case K.SFQD when '1' then '是' else ' 否' end) SFQD,to_char(K.QYSJ,'yyyy-mm-dd') AS QYSJ,to_char(K.WCSJ,'yyyy-mm-dd') AS WCSJ,K.KZFW AS KZFW ");
		sqltext.append(" FROM CW_XMXXB K  where 1=1");
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		String guid = pd.getString("guid");
		if(Validate.noNull(guid)){
			sqltext.append(" AND K.GUID IN ('"+guid+"') ");
		}
//		if(Validate.noNull(searchValue)) {
//			sqltext.append(" and '"+searchValue+"'");
//		}
		//sqltext.append(" ORDER BY K.GUID ");
		
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m1 = new M_largedata();
		
		m1.setColtype("String");
		m1.setName("XMBH");
		m1.setShowname("项目编号");
		mlist.add(m1);
		
		M_largedata m2 = new M_largedata();
		m2.setColtype("String");
		m2.setName("XMMC");
		m2.setShowname("项目名称");
		mlist.add(m2);
		
		M_largedata m3 = new M_largedata();
		m3.setColtype("String");
		m3.setName("SSXMFL");
		m3.setShowname("所属项目分类");
		mlist.add(m3);
		
		M_largedata m4 = new M_largedata();
		m4.setColtype("String");
		m4.setName("SJXM");
		m4.setShowname("上级项目");
		mlist.add(m4);
		
		M_largedata m5 = new M_largedata();
		m5.setColtype("String");
		m5.setName("YWSX");
		m5.setShowname("有无上限");
		mlist.add(m5);
		
		M_largedata m6 = new M_largedata();
		m6.setColtype("String");
		m6.setName("SXJE");
		m6.setShowname("上限金额(万元)");
		mlist.add(m6);
		
		M_largedata m7 = new M_largedata();
		m7.setColtype("String");
		m7.setName("SFYXCZ");
		m7.setShowname("是否允许超支");
		mlist.add(m7);
		
		M_largedata m8 = new M_largedata();
		m8.setColtype("String");
		m8.setName("CZBL");
		m8.setShowname("超支比例");
		mlist.add(m8);
		
		M_largedata m9 = new M_largedata();
		m9.setColtype("String");
		m9.setName("TXBFB");
		m9.setShowname("提醒百分比(%)");
		mlist.add(m9);
		
		M_largedata m10 = new M_largedata();
		m10.setColtype("String");
		m10.setName("XMND");
		m10.setShowname("项目年度");
		mlist.add(m10);
		
		M_largedata m11 = new M_largedata();
		m11.setColtype("String");
		m11.setName("SSXB");
		m11.setShowname("所属系部");
		mlist.add(m11);
		
		M_largedata m12 = new M_largedata();
		m12.setColtype("String");
		m12.setName("XMXZ");
		m12.setShowname("项目性质");
		mlist.add(m12);
		
		M_largedata m13 = new M_largedata();
		m13.setColtype("String");
		m13.setName("SFQD");
		m13.setShowname("是否启动");
		mlist.add(m13);
		
		M_largedata m14 = new M_largedata();
		m14.setColtype("String");
		m14.setName("QYSJ");
		m14.setShowname("启用时间");
		mlist.add(m14);
		
		M_largedata m15 = new M_largedata();
		m15.setColtype("String");
		m15.setName("WCSJ");
		m15.setShowname("完成时间");
		mlist.add(m15);
		
		M_largedata m16 = new M_largedata();
		m16.setColtype("String");
		m16.setName("KZFW");
		m16.setShowname("开支范围");
		mlist.add(m16);
		
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}	


	
	
}
