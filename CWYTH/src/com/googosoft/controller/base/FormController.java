package com.googosoft.controller.base;


import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.sql.rowset.serial.SerialException;











import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;










import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.googosoft.constant.Constant;
import com.googosoft.constant.GlobalContants;
import com.googosoft.constant.SystemSet;
import com.googosoft.pojo.Form;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.FormService;
import com.googosoft.service.base.PageService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;


@Controller
@RequestMapping("form")
public class FormController extends BaseController {
	@Autowired
	FormService formService;
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	
	
	
	/**
	 *表单列表页面
	 */   
	@RequestMapping(value="/getFormsPage")
	public ModelAndView getFormsPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("form/form_list");
		return mv;
	}
	/**
	 *待办页面
	 */   
	@RequestMapping(value="/getFormsDbPage")
	public ModelAndView getFormsDbPage(){
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("form/wddb_list");
		return mv;
	}
	/**
	 * 删除
	 * @return
	 */
	@RequestMapping(value="/deleteForm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object deleteForm(){
		PageData pd = this.getPageData();
		String gid = pd.getString("gid");
		String[] gids=gid.split(",");
		boolean k=false;
		for(int i=0;i<gids.length;i++){
			 k= formService.deleteFormInfo(gids[i]);
		}
		String b = "";
		if(k){
			b= "{\"success\":\"true\",\"msg\":\"表单删除成功！\"}";
		}else{
			b= "{\"success\":\"false\",\"msg\":\"表单删除失败！\"}";
		}
		return b;
	}
	/**
	 * 公文表格列表数据
	 * @author 作者：Administrator
	 * @version 创建时间:2017-9-9下午4:26:58
	 */
	@RequestMapping(value="/getWddbPageList")
	@ResponseBody
	public Object getWddbPageList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		PageList pageList = new PageList();
		pageList.setSqlText("k.*");
		StringBuffer tablename = new StringBuffer();// 查询字
		String saas=CommonUtils.getSaas();
		String userId = CommonUtils.getRybh();
		tablename.append(" (select distinct res.id_,RES.proc_inst_id_ ,res.name_ as taskname,(select r.xm from oa_sys_ryb r where r.rybh=res.assignee_)as xm,arp.name_ as lcmc,to_char(res.create_time_,'yyyy-mm-dd hh24:Mi:ss') as createtime,res.form_key_  "
				+ "from ACT_RU_TASK RES  left join act_re_procdef arp  on arp.id_ =res.proc_def_id_  left join act_re_model m on m.deployment_id_ = arp.deployment_id_   "
				+ "WHERE RES.ASSIGNEE_ ='"+CommonUtils.getRybh()+"' and res.form_key_!=' ' and m.saas='"+CommonUtils.getSaas()+"'  ) k ");
		pageList.setKeyId("ID_");//主键
		pageList.setTableName(tablename.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		//下面是分页，暂时不用管
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 *表单列表页面
	 */  
	@RequestMapping(value="/getList")
	@ResponseBody
	public Object getList(){
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		PageList pageList = new PageList();
	
		pageList.setKeyId("gid");// 主键
		String rybh = CommonUtils.getRybh();
		sqltext.append(" t.gid,t.name,t.displayname,t.createtime,t.creator,t.originalhtml,t.parsehtml  ");
		pageList.setStrWhere(" and CREATOR = '"+rybh+"'");
		pageList.setSqlText(sqltext.toString());
		pageList.setTableName(" "+SystemSet.oaBz+"FORM t");
		pageList.setStrWhere(" and t.saas='"+CommonUtils.getSaas()+"'");
		pageList = pageService.findPageList(pd, pageList);// 引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList
				.getTotalList().get(0).get("NUM")
				+ "", pageList.getTotalList().get(0).get("NUM") + "",
				gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	@RequestMapping(value="editForm")
	public String editForm(ModelMap modelMap,String cmd,Form form){
		String forward = "";
		ModelAndView mv = this.getModelAndView();
		
//		modelMap.put("id", form.getId());
		switch (cmd) {
		case GlobalContants.UPDATE:
			modelMap.put("form", formService.findFormInfoById(form.getGid()));
			modelMap.put("cmd", cmd);
			forward="form/form_edit";
			break;
		case GlobalContants.ADD:
			modelMap.put("cmd", cmd);
			forward="form/form_edit";
			break;
		case GlobalContants.DESIGN:
			modelMap.put("form", formService.findFormInfoById(form.getGid()));
			modelMap.put("cmd", cmd);
			forward="form/designForm";
			break;
		case GlobalContants.VIEW:
			modelMap.put("form", formService.findFormInfoById(form.getGid()));
			modelMap.put("cmd", cmd);
			forward="form/viewform";
			break;
		}
		return forward;

	}
	@RequestMapping(value="saveForm",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public String saveForm(ModelMap modelMap, String cmd,Form form,HttpSession session,String pass) {
		String forward="";
		boolean flag=false;
		switch (cmd) {
			case GlobalContants.ADD:
				flag=formService.saveFormInfo(form);
				if(flag){
			   forward= "{success:'true',msg:'表单信息保存成功！'}";
				}else{
					forward="{success:'true',msg:'表单信息保存失败！'}";
				}
				break;
			case GlobalContants.UPDATE:
				flag=formService.updateFormByid(form);
				if(flag){
					   forward= "{success:'true',msg:'表单信息保存成功！'}";
						}else{
							forward="{success:'true',msg:'表单信息保存失败！'}";
						}
				break;
		}
		return forward;
	}
	@RequestMapping("savesForm")
	@ResponseBody
	@SuppressWarnings("unchecked")
	public Boolean savesForm(String  type,String formid,String parse_form,ModelMap modelMap) throws JsonParseException, JsonMappingException, IOException, SerialException, SQLException{
		   //ObjectMapper类用序列化与反序列化映射器  
        ObjectMapper mapper = new ObjectMapper();  
        Form model = null;
        model = formService.findFormInfoById(formid);
		Map<String, Object> map = JSONObject.parseObject(parse_form,Map.class);
		for(String key:map.keySet()){
			System.out.println("==========="+key+":"+map.get(key));
		}
		Map<String, Object> datas = (Map<String, Object>)map.get("add_fields");
		Map<String, String> nameMap = formService.process(model, datas);
		String template = (String)map.get("template");
		String parseHtml = (String)map.get("parse");
		if(!nameMap.isEmpty()) {
			for(Map.Entry<String, String> entry : nameMap.entrySet()) {
				template = template.replaceAll(entry.getKey(), entry.getValue());
				parseHtml = parseHtml.replaceAll(entry.getKey(), entry.getValue());
			}
		}
		model.setOriginalhtml(template);
		model.setParsehtml(parseHtml);
		formService.updateFormInfo(model);
		modelMap.put("form", formService.findFormInfoById(model.getGid()));
		return true;
	}

	
}
