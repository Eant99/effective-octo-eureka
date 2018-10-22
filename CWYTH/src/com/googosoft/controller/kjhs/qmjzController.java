package com.googosoft.controller.kjhs;

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
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.CW_QMJZB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.KmszService;
import com.googosoft.service.kjhs.qmjzService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

  @Controller
  @RequestMapping(value="/qmjz")
public class qmjzController extends BaseController {
	  @Resource(name="qmjzService")
		private qmjzService qmjzService;
	  @Resource(name="pageService")
		private PageService pageService;//单例
	  @Resource(name="dictService")
		private DictService dictService;//单例
		@Resource(name="kmszService")
		
		private KmszService kmszService; //单例
	//初始化登录人员信息
		public void iniLogin(ModelAndView mv) {		
			mv.addObject("loginId",LUser.getGuid());			
			mv.addObject("ryxm",LUser.getRyxm());			
		}
	  
	 //跳转列表页面  
	  @RequestMapping(value = "/goQmjzListPage")
		public ModelAndView goQmjzListPage() {
			ModelAndView mv = this.getModelAndView();
			PageData pd = this.getPageData();
			iniLogin(mv);
			List ssxt = qmjzService.getSsxt();//
			mv.addObject("ssxtlist", ssxt);
			mv.setViewName("kjhs/qmjz/qmjz_list");
			return mv;
		}
	  
	  //获取列表数据
	  @RequestMapping(value = "/getQmjzPageList")
		@ResponseBody
		public Object getQmjzPageList() throws Exception {		
		    PageData pd = this.getPageData();
			StringBuffer sqltext = new StringBuffer();//查询字段
			StringBuffer tablename = new StringBuffer();
			String dqnd = pd.getString("dqnd");
			String strWhere="";
			PageList pageList = new PageList();
			sqltext.append(" * ");
			tablename.append(" ( select  K.GUID,K.ZTND,K.KJQJ,DECODE(K.SFJZ,'1','是','否')SFJZ,to_char(K.JZRQ,'yyyy-mm-dd') as JZRQ,"
					+ "(SELECT '('||J.XH||')'||J.XM FROM CW_JZGXXB J WHERE J.GUID = K.JZRY) AS JZRY,jzcs, (SELECT '('||J.XH||')'||J.XM FROM CW_JZGXXB J WHERE J.GUID = K.JZRY) AS JZRY1" 
				    + " from CW_QMJZB K where 1=1");			
			tablename.append(")A");
			String searchValue = pd.getString("search[value]");
			if(Validate.isNullOrEmpty(searchValue)|| "[]".equals(searchValue)){
				String ss =kmszService.getTime();
				 strWhere = " AND A.ZTND IN ('"+ss+"') ";
				
			}
			pageList.setSqlText(sqltext.toString());
			//设置表名
			pageList.setTableName(tablename.toString());
			//设置表主键名
			pageList.setKeyId("GUID");//主键
			//设置WHERE条件
			pageList.setStrWhere(strWhere); 
			pageList.setHj1("");//合计
			
		    pageList = pageService.findPageList(pd,pageList);
			Gson gson = new Gson();
			PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
			return pageInfo.toJson();	
		}
	  /**
		 * 判断当前会计期间是否记账
		 * @return
		 */
		@RequestMapping(value="/doSelect",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String doSelect(){
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			String kjqj = pd.getString("kjqj");
			String ztnd = pd.getString("ztnd");
			mv.addObject("kjqj",kjqj);
			List<Map<String, Object>>  list = qmjzService.getObjectById(kjqj);
			
			System.out.println("list==="+list);
			//String pzzt = (String) map.get("pzzt");
			String a ="";
			for(int i=0;i<list.size();i++) {
				Map map = list.get(i);
				String pzzt = (String) map.get("pzzt");
				a +=pzzt +",";
			}
			System.out.println("a==="+a);
			boolean bool = qmjzService.checkPzbh(kjqj, ztnd);
			if(bool){
				if(a.contains("00")||a.contains("01")) {
					return "00";
				}else if(a.contains("99")) {
					return "99";
				}else {
					return "02";
				}
			}else{
				return "22";
			}
			
		}
		 /**
		 * 判断当前会计期间是否结过账
		 * @return
		 */
		@RequestMapping(value="/doSelectJz",produces = "text/html;charset=UTF-8")
		@ResponseBody
		public String doSelectJz(){
			PageData pd = this.getPageData();
			ModelAndView mv = this.getModelAndView();
			String kjqj = pd.getString("kjqj");
			String ztnd=pd.getString("ztnd");
			mv.addObject("kjqj",kjqj);
			Map<?, ?> map = qmjzService.getObjectByIdJz(kjqj,ztnd);
			String jzcs = String.valueOf(map.get("jzcs")) ;
			//int jzcs = Integer.parseInt(map.get("jzcs"))
			return jzcs ;
           
		}
	  /**
		 * 结账本月账单信息
		 * @param dmb
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/editQmjz",produces = "text/html;charset=UTF-8")
		@ResponseBody
		
		public Object editQmjz(CW_QMJZB qmjzb,HttpSession session)throws Exception{
			PageData pd = this.getPageData();
		
			String b = "";
			int i;
			//String guid = this.get32UUID();
			String guid = pd.getString("guidLast");
			//String jzrq = pd.getString("jzrq");
			//String jzry = pd.getString("jzry");
			String jzyf = pd.getString("jzyf");
			String jzcs = pd.getString("jzcs");
			
			String ztnd = pd.getString("ztnd");
			String kjzd = CommonUtil.getKjzd(session);
			String sszt = Constant.getztid(session);
			qmjzb.setGuid(guid);
		
			i = qmjzService.doEdit(qmjzb,jzyf,jzcs,ztnd,kjzd,sszt);
				if(i==1){
					b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
				}else{
					b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
				}
			
			return b;
		}
		/**
		 * 添加下月结账信息
		 * @param dmb
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/addQmjz",produces = "text/html;charset=UTF-8")
		@ResponseBody
		
		public Object addQmjz(CW_QMJZB qmjzb,HttpSession session)throws Exception{
			PageData pd = this.getPageData();
			String sszt = Constant.getztid(session);
			String b = "";
			int i;
			String guid = this.get32UUID();
			String ztnd = pd.getString("ztnd");
			String kjqj = pd.getString("kjqj");
			
			qmjzb.setGuid(guid);
			qmjzb.setZtnd(ztnd);
			qmjzb.setKjqj(kjqj);
			qmjzb.setSszt(sszt);
			i = qmjzService.doAdd(qmjzb);
				if(i==1){
					b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
				}else{
					b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
				}
			
			return b;
		}
		 /**
		 * 反结账
		 * @param dmb
		 * @return
		 * @throws Exception
		 */
		@RequestMapping(value="/editFjz",produces = "text/html;charset=UTF-8")
		@ResponseBody
		
		public Object editFjz(CW_QMJZB qmjzb)throws Exception{
		
			PageData pd = this.getPageData();
			String b = "";
			int i;
			//String guid = this.get32UUID();
			String guid = pd.getString("guid");
			System.out.println("guid==="+guid);
			
			qmjzb.setGuid(guid);
			i = qmjzService.doEditFjz(qmjzb);
				if(i==1){
					b = "{\"success\":\"true\",\"msg\":\"信息保存成功！\"}";
				}else{
					b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
				}
			
			return b;
		}
		

}
