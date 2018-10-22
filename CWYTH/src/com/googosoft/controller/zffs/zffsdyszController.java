package com.googosoft.controller.zffs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.wsbx.jcsz.Cw_zffsdzb;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.ysgl.xmlx.XmlxService;
import com.googosoft.service.zffs.zffsdyszService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "/zffsdysz")
public class zffsdyszController extends BaseController{
	@Resource(name="zffsdysz")
	private zffsdyszService zffsdysz;//单例
	
	@Resource(name="xmlxService")
	private XmlxService xmlxService;//单例
	
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	//跳转到列表页面
		@RequestMapping(value = "/gozffsdysz")
		public ModelAndView gozffskmdysz() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			List zffs = dictService.getDict(Constant.ZFFS);
			mv.addObject("zffs",zffs);
			mv.setViewName("wsbx/jcsz/zffsdysz/zffsdysz_list");
			return mv;

		}
		
		
		@RequestMapping(value="/getPageList")
		@ResponseBody
		public Object getPageList(HttpSession session){
			String kjzd = CommonUtil.getKjzd(session);
			PageList pageList = new PageList();
			//设置查询字段名
			StringBuffer sqltext = new StringBuffer();
			sqltext.append("guid,(select mc from gx_sys_dmb dm where dm.dm=K.zffs and dm.zl='zffs') as zffs1,zffs,(case jdfx when '0' then '借方' else '贷方' end) as jdfx,(select kmmc from cw_kjkmszb kj where kj.kmbh=K.kmbh and kj.kjzd='"+kjzd+"') as kmmc,kmbh");
			//设置表名
			pageList.setSqlText(sqltext.toString());
			pageList.setTableName(" CW_zffsdzb K ");
			//设置表主键名
			pageList.setKeyId("GUID");
			//设置WHERE条件
			PageData pd = this.getPageData();
		//	sqltext.append(" and kjzd = '"+kjzd+"'");
			//StringBuffer tjtext = new StringBuffer();
			String strWhere = " and kjzd='"+kjzd+"' and sszt='"+Constant.getztid(session)+"'";
			pageList.setStrWhere(strWhere); //根据点击左侧树展示右侧列表
			//设置合计值字段名
			pageList.setHj1("");
			//页面数据
		    pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();
		}
		/**
		 * 跳转增加）
		 * @return
		 */
		@RequestMapping(value="/goAddpage")
		public ModelAndView goAddpage(){
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			List zffs = dictService.getDict(Constant.ZFFS);
            mv.addObject("zffs",zffs);
			mv.setViewName("wsbx/jcsz/zffsdysz/zffsdysz_add");
			return mv;
		}
		/**
		 * 跳转编辑
		 * @return
		 */
		@RequestMapping(value="/goEditPage")
		public ModelAndView goEditPage(HttpSession session){
			PageData pd = this.getPageData();
			String guid = pd.getString("guid");
			ModelAndView mv = this.getModelAndView();
			String sszt = Constant.getztid(session);
			String kjzd=CommonUtil.getKjzd(session);
			List zffs = dictService.getDict(Constant.ZFFS);
            mv.addObject("zffs",zffs);
			//获取操作类型参数 C增加 U修改 L查看
			String operateType = pd.getString("operateType");
			Map<?, ?>  map = zffsdysz.getObjectById(pd.getString("guid"),sszt,kjzd);
		    mv.addObject("zffsdz", map);
			mv.setViewName("wsbx/jcsz/zffsdysz/zffsdysz_eidt");
		
			
			
		
			return mv;
		}
		/**
		 * 添加支付方式信息
		 * @param CW_FYKMDZB
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
		@ResponseBody
		
		public Object doSave(Cw_zffsdzb zffsb,HttpSession session){
			String sszt = Constant.getztid(session);
			String kjzd = CommonUtil.getKjzd(session);
			zffsb.setKjzd(kjzd);
			PageData pd = this.getPageData();
			int result=0;
			String czr = LUser.getRybh();
			String guid =this.get32UUID();//生成主键id
//			String zffs = pd.getString("zffs");
//			String jdfx = pd.getString("jdfx");
//			String bz = pd.getString("bz");
//			String kmbh = pd.getString("kmbh");			
			
			zffsb.setGuid(guid);
			zffsb.setCzr(czr);
//			zffsb.setZffs(zffs);
//			zffsb.setJdfx(jdfx);
//			zffsb.setBz(bz);
//			zffsb.setKmbh(kmbh);
			zffsb.setSszt(sszt);			

		    result = zffsdysz.doAdd(zffsb);
		    
		    
		    String czrid = LUser.getGuid();
			String guid2 = this.get32UUID();
			Map map1 = new HashMap<>();
			map1.put("guid", guid2);
			map1.put("ywid", zffsb.getKmbh());
			map1.put("czid",czrid );
			map1.put("czr", czrid);
			map1.put("kmlx", "1");
			map1.put("zbid", guid);
			
			xmlxService.doAddjwjl(map1,kjzd);
				
				
				
				if(result==1){
					return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
				}else{
					return MessageBox.show(false, MessageBox.FAIL_SAVE);
				}
			
			
		}
		/**
		 * 修改支付方式信息
		 * @param CW_FYKMDZB
		 * @return
		 * @throws Exception 
		 */
		@RequestMapping(value="/doupdate",produces = "text/html;charset=UTF-8")
		@ResponseBody
		
		public Object doupdate(Cw_zffsdzb zffsb){
			PageData pd = this.getPageData();
			int result=0;
			String czr = LUser.getRybh();
			String guid =pd.getString("guid");
			String zffs = pd.getString("zffs");
			String jdfx = pd.getString("jdfx");
			String bz = pd.getString("bz");
			String kmbh = pd.getString("kmbh");
			
			zffsb.setGuid(guid);
			zffsb.setCzr(czr);
			zffsb.setZffs(zffs);
			zffsb.setJdfx(jdfx);
			zffsb.setBz(bz);
			zffsb.setKmbh(kmbh);
			result = zffsdysz.doupdate(zffsb);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
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
			String guid = this.getPageData().getString("guid");
			int i = zffsdysz.doDel(guid);
			if (i > 0) {
				return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
			} else {
				return MessageBox.show(false, MessageBox.FAIL_DELETE);
			}
		}
		/**
		 * 导出信息Excel
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
			// 数据的sql语句
			String searchJson = pd.getString("searchJson");
			StringBuffer sqltext = new StringBuffer();
			sqltext.append(" select guid,rownum as xh,(select mc from gx_sys_dmb dm where dm.dm=K.zffs and dm.zl='zffs') as zffs1,zffs,(case jdfx when '1' then '借方' else '贷方' end) as jdfx,(select kmmc from cw_kjkmszb kj where kj.kmbh=K.kmbh) as kmmc from Cw_zffsdzb k where 1=1");
		
			
			
			sqltext.append(ToSqlUtil.jsonToSql(searchJson));
			String id = pd.getString("id");
			if(Validate.noNull(id)){
				sqltext.append(" and k.guid in ('"+id.replace(",", "','")+"') ");
			}
			sqltext.append(" order by k.jdfx ");
			//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
			List<M_largedata> mlist = new ArrayList<M_largedata>();
			M_largedata m = new M_largedata();
			m.setName("xh");
			m.setShowname("序号");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("zffs1");
			m.setShowname("支付方式");
			mlist.add(m);
			m = null;
			
			m = new M_largedata();
			m.setName("kmmc");
			m.setShowname("科目名称");
			mlist.add(m);
			m = null;
	
			m = new M_largedata();
			m.setName("jdfx");
			m.setShowname("借贷方向");
			mlist.add(m);
			m = null;
			//导出方法
			pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
			return "{\"url\":\"excel\\\\"+file+".xls\"}";
		}
		

		/**
		 * 导出教师人员信息Excel   wzd
		 * @return
		 */
		@RequestMapping("/expExcel2")
		@ResponseBody
		public Object stryexpXsInfo() {
			PageData pd = this.getPageData();
			String rybh = LUser.getRybh();//当前登陆者的人员编号
			String s1 = Constant.ZY;
			String guid = pd.getString("id");
			String searchValue = pd.getString("searchJson");
			String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
			String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
			return this.zffsdysz.expExcel(realfile, shortfileurl, guid,searchValue,rybh,s1);
		}
		
		/**
		 * 验证支付方式是否存在
		 * @param zffsb
		 * @return
		 */
		@RequestMapping(value="/check",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public Object check(String zffs,HttpSession session,String guid){
			PageData pd = this.getPageData();
			String sszt = Constant.getztid(session);
			String kjzd = CommonUtil.getKjzd(session);
			boolean bool = zffsdysz.checkZffs(zffs, kjzd, sszt, guid);
			Gson gson = new Gson();
			return gson.toJson(bool);
		}
}
