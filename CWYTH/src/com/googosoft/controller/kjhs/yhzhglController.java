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
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.kjhs.Cw_yhzhgl;
import com.googosoft.service.base.PageService;
import com.googosoft.service.kjhs.YhzhglService;
import com.googosoft.util.PageData;

@Controller
@RequestMapping(value = "/yhzhgl")
public class yhzhglController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	@Resource(name="yhzhglService")
	private YhzhglService yhzhglService;//单例
	//跳转银行账号管理列表页面
	@RequestMapping(value = "/goyhzhglListPage")
	public ModelAndView goXsxxListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.setViewName("kjhs/yhzhgl/yhzhgl_list");
		
		return mv;

	}

	/**
	 * 添加银行账号管理表信息
	 * @param dmb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/addYhzhb",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object addYhzhb(Cw_yhzhgl yhzhb,HttpSession session)throws Exception{
		PageData pd = this.getPageData();
		String sszt = Constant.getztid(session);
		int m = 0;
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		int size = list.size();
		yhzhglService.doDeleteAll();
		for (int i=0;i<size;i++) {
			Map map = (Map) list.get(i);//将list转为map
			String guid = this.get32UUID();//创建主键
			String kmbh = (String) map.get("kmbh");
			String yhmc = (String) map.get("yhmc");
			String khyhh = (String) map.get("khyhh");
			String lhh = (String) map.get("lhh");
		    //将字段放入yhzhb
		    yhzhb.setGuid(guid);
		    yhzhb.setKmbh(kmbh);
		    yhzhb.setYhmc(yhmc);
		    yhzhb.setKhyhh(khyhh);
		    yhzhb.setLhh(lhh);
		    yhzhb.setSszt(sszt);
		   
			//增加
			if(yhzhglService.doAdd(yhzhb)>0){
				m++;
			}
		}
		if(m==size){
			return MessageBox.toJson(true, "保存成功");
		}else{
			return MessageBox.toJson(false, "保存失败");
		}
	}
	
	/**
	 * 获取银行账号列表数据
	 * @return
	 * @throws Exception
	 */
	/*@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append(" K.GUID, K.YHDM,K.YHDM AS YHDM1, K.YHMC,K.YHMC AS YHMC1, K.KHYHH, K.LHH");
		PageList pageList = new PageList();
		pageList.setSqlText(sqltext.toString());
		pageList.setKeyId("GUID");//主键
		pageList.setStrWhere("");
		pageList.setTableName("CW_YHZHB K");//表名
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}*/
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList(HttpSession session) throws Exception {		
	    PageData pd = this.getPageData();
	    String kjzd = CommonUtil.getKjzd(session);
	    String sszt = Constant.getztid(session);
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treedwbh");
		PageList pageList = new PageList();
		sqltext.append(" * ");
//		tablename.append(" ( select  K.GUID, K.YHDM,K.YHDM AS YHDM1, K.YHMC,K.YHMC AS YHMC1, K.KHYHH, K.LHH" + 
//				         " from CW_YHZHB K where 1=1  ");
//		tablename.append(")A");
		tablename.append(" ( select s.guid as sguid, B.GUID as guid,B.KMBH,B.KMMC,S.YHMC,S.YHMC as YHMC1,S.KHYHH,S.LHH FROM CW_KJKMSZB B ");
		tablename.append("  left join CW_YHZHB S ON S.KMBH=B.KMBH WHERE B.KMBH LIKE '1002%' and B.KMBH !='1002' and b.kjzd='"+kjzd+"' and b.sszt='"+sszt+"'");
		tablename.append(")A");
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

}
