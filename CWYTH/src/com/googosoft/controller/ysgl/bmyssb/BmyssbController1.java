package com.googosoft.controller.ysgl.bmyssb;

import java.text.SimpleDateFormat;
import java.util.Date;
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
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.bmyssb.BmyssbService1;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping("ysgl/bmyssb1")
public class BmyssbController1 extends BaseController {

	@Resource(name = "dictService")
	DictService dictService;

	@Resource(name = "pageService")
	private PageService pageService;// 单例

	@Resource(name = "bmyssbService1")
	private BmyssbService1 bmyssbService;// 单例

	// 初始化登录人员信息
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId", LUser.getGuid());
		mv.addObject("szbm", LUser.getDwmc());
		mv.addObject("ryxm", "(" + LUser.getRybh() + ")" + LUser.getRyxm());
		mv.addObject("today", DateUtil.getDay());
	}

	// 跳转到部门预算申报列表页面
	@RequestMapping("/goBmyssbPage")
	public ModelAndView goBmyssbPage() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.addObject("shztList", dictService.getDict(Constant.bmysshzt));
		mv.setViewName("ysgl/bmyssb1/bmyssb_list");
		return mv;
	}

	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		StringBuffer tablename = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(new Date()));
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"), "0");
		String userDwbh = LUser.getDwbh();
		// String bmmc = bmyssbService.getBmmcByDwbh(userDwbh);
		PageList pageList = new PageList();
		sqltext.append(" * ");
		System.err.println("_______shzt________"+shzt);
		
		tablename.append(
				" (select 'E3A85BE7EEF54604873BF914C63CC6A1'guid, '(104)纪委办公室（监察室）' bmmc, '1111' PROCINSTID, '1' shztsz, '(104)纪委办公室（监察室）' bmmcs,\r\n"
					+ " '2015'nd, '2018-03-11' sqsj, '2018-03-11' bzrq, '11111' jbr, '(2013384)李伟' jbrmc, '2013384' dwfzr, '(2013384)李伟' dwfzrmc, '2013384' cwfzr,\r\n"
					+ " '(2013384)李伟' cwfzrmc, to_char('55', 'FM999999999999990.0000') sryshz, to_char('100', 'FM999999999999999990.0000') zcyshz, to_char('23', 'FM99999999999999990.0000') jyje  from dual  " );
		
//		tablename.append(
//			" (  select distinct f.guid,f.bmmc,f.PROCINSTID,f.shzt as shztsz,(select d.mc from gx_sys_dwb d where d.dwbh=f.bmmc)bmmcs,f.nd,to_char(f.sqsj,'yyyy-mm-dd')sqsj,to_char(f.bzrq,'yyyy-mm-dd')bzrq,f.jbr,\r\n"
//				+ "(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=f.jbr)jbrmc,j.dwfzr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=j.dwfzr)dwfzrmc,\r\n"
//				+ "j.cwfzr ,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=j.cwfzr)cwfzrmc,to_char((select sum(yjwcse) from cw_srysb s where s.tbbm='" + userDwbh
//				+ "'),'FM999999999999990.0000')sryshz,\r\n" + "to_char(((select nvl(sum(z.NAPZJS),0) from cw_zxywfzcysb z where z.TBBM = '" + userDwbh
//				+ "')+(select nvl(sum(z.NAPZJS),0) from cw_wxzlfzcysb z where z.TBBM = '" + userDwbh + "')+\r\n" + "(select nvl(sum(z.CGYSJE),0) from cw_zfcgysb z where z.TBBM = '"
//				+ userDwbh + "')+(select nvl(sum(z.zjze),0) from cw_czzcjxmbsqb z where z.TBBM = '" + userDwbh + "')\r\n"
//				+ "+(select nvl(sum(z.nysze),0) from cw_rcgyzcysb z where z.TBBM = '" + userDwbh + "')),'FM999999999999999990.0000')zcyshz,\r\n"
//				+ "to_char(((select nvl(sum(z.NYSJYAPS),0) from cw_zxywfzcysb z where z.TBBM = '" + userDwbh
//				+ "')+(select nvl(sum(z.NAPZJS),0) from cw_wxzlfzcysb z where z.TBBM = '" + userDwbh + "')+\r\n"
//				+ "(select nvl(sum(z.nysjyaps),0) from cw_rcgyzcysb z where z.TBBM = '" + userDwbh + "')),'FM99999999999999990.0000')jyje\r\n"
//				+ "from cw_fmb f left join cw_jbxxb j on j.dwmc=f.bmmc left join cw_srysb s on s.tbbm=f.bmmc ");
		if("03".equals(shzt)){
//			tablename.append(" LEFT JOIN ACT_RU_TASK ACT ON F.PROCINSTID = ACT.PROC_INST_ID_  where f.bmmc='" + userDwbh + "' AND ACT.ASSIGNEE_='"+LUser.getGuid()+"'  and f.shzt  in ('006')   ");
//			tablename.append(" LEFT JOIN ACT_RU_TASK ACT ON F.PROCINSTID = ACT.PROC_INST_ID_  where f.bmmc='" + userDwbh + "' AND f.bmfzr = '"+LUser.getRybh()+"' and f.shzt  in ('006')   ");
		}else if("02".equals(shzt)) {
//			tablename.append(" LEFT JOIN ACT_RU_TASK ACT ON F.PROCINSTID = ACT.PROC_INST_ID_  where f.bmmc='" + userDwbh + "' AND ACT.ASSIGNEE_='"+LUser.getGuid()+"'   and f.shzt not in ('0','006','012') ");
//			tablename.append(" LEFT JOIN ACT_RU_TASK ACT ON F.PROCINSTID = ACT.PROC_INST_ID_  where f.bmmc='" + userDwbh + "' AND f.bmfzr = '"+LUser.getRybh()+"'   and f.shzt not in ('0','006','012') ");
		}else if("0".equals(shzt)) {
//			tablename.append(" LEFT JOIN ACT_HI_ACTINST ACT ON F.PROCINSTID = ACT.PROC_INST_ID_ AND ACT.END_TIME_ IS NOT NULL  where f.bmmc='" + userDwbh + "'   and f.shzt in ('0')  ");
//			tablename.append(" LEFT JOIN ACT_HI_ACTINST ACT ON F.PROCINSTID = ACT.PROC_INST_ID_ AND ACT.END_TIME_ IS NOT NULL  where f.bmmc='" + userDwbh + "'   and f.shzt in ('0')  ");
		}else {
//			tablename.append(" LEFT JOIN ACT_HI_ACTINST ACT ON F.PROCINSTID = ACT.PROC_INST_ID_ AND ACT.END_TIME_ IS NOT NULL  where f.bmmc='" + userDwbh + "' AND ACT.ASSIGNEE_='"+LUser.getGuid()+"'  and f.shzt in ('0')  ");
//			tablename.append(" LEFT JOIN ACT_HI_ACTINST ACT ON F.PROCINSTID = ACT.PROC_INST_ID_ AND ACT.END_TIME_ IS NOT NULL  where f.bmmc='" + userDwbh + "' AND f.bmfzr = '"+LUser.getRybh()+"'  and f.shzt in ('0')  ");
		}
		tablename.append(")k");
		pageList.setSqlText(sqltext.toString());
		// 设置表名
		pageList.setTableName(tablename.toString());
		// 设置表主键名
		pageList.setKeyId("GUID");// 主键
		// 设置WHERE条件
//		pageList.setStrWhere(" and k.bmmc='" + userDwbh +"'  ");
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "", pageList.getTotalList().get(0).get("NUM") + "",
			gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();

	}

	@RequestMapping(value = "/getPageLists")
	@ResponseBody
	public Object getxdPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treeid");
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" (  select t.guid,b.bmmc,(select d.mc from gx_sys_dwb d where d.dwbh=b.bmmc) bmmcs,to_char(b.sqsj,'yyyy-mm-dd') sqsj,"
			+ "(select sum(yjwcse) from cw_srysb s where s.guid=t.srysb_id)sryshz  " + " from cw_bmyssbzb t left join cw_fmb b on b.guid=t.fmb_id  ");
		if (Validate.noNull(dwbh)) {
			tablename.append(" and  ");
		}
		tablename.append(")k");

		pageList.setSqlText(sqltext.toString());
		// 设置表名
		pageList.setTableName(tablename.toString());
		// 设置表主键名
		pageList.setKeyId("GUID");// 主键
		// 设置WHERE条件
		pageList.setStrWhere("");
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "", pageList.getTotalList().get(0).get("NUM") + "",
			gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();

	}

	/**
	 * 获取部门预算申报树
	 * 
	 * @return
	 */
	@RequestMapping(value = "/bmysTree", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object qxdwTree() {
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		// 获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if (menu.equals("get-xjdw")) {
			String loginrybh = LUser.getRybh();
			if (dwbh.equals("root")) {
				return bmyssbService.getPowerDwNode(pd, loginrybh, rootPath);
			} else {
				return bmyssbService.getDwNode(pd, dwbh, rootPath);
			}
		} else {
			return "";
		}
	}

	/**
	 * 部门预算申请表
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(new Date()));
		System.err.println("_______year____" + year);

		String dm = pd.getString("dm");
		String userDwbh = LUser.getDwbh();
		String userRybh = LUser.getRybh();
		String bmmc = bmyssbService.getBmmcByDwbh(userDwbh);
		String rymc = bmyssbService.getRymcByRybh(userRybh);
		String ck = pd.getString("ck");
		String sh = pd.getString("sh");
		mv.addObject("sh", sh);
		mv.addObject("ck", ck);
		// 接收 审核 所需 id
		String guid = this.getPageData().getString("guid");
		String procinstid = this.getPageData().getString("procinstid");
		String SRYSHZ = this.getPageData().getString("SRYSHZ");// 收入预算汇总金额
		String ZCYSHZ = this.getPageData().getString("ZCYSHZ");// 支出预算汇总金额
		mv.addObject("guid", guid);
		mv.addObject("procinstid", procinstid);
		mv.addObject("SRYSHZ", SRYSHZ);
		mv.addObject("ZCYSHZ", ZCYSHZ);
		String src = "";
System.err.println("!!!!!!!!!!!!!!!!!!!!!!!!!!!dm==="+dm);		
		if ("1".equals(dm)) {
			Map<?, ?> map = bmyssbService.getObjectById(pd, userDwbh);
			mv.addObject("info", map);
			src = "ysgl/bmyssb1/fm_list";
		} else if ("2".equals(dm)) {
			Map<?, ?> map2 = bmyssbService.getObjectById(pd, userDwbh);
			mv.addObject("info", map2);
			src = "ysgl/bmyssb1/jbxx_list";
		} else if ("3".equals(dm)) {
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/srys_list";
		} else if ("4".equals(dm)) {
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/zxywfzcys_list";
		} else if ("5".equals(dm)) {
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/wxzlfzcys_list";
		} else if ("6".equals(dm)) {
			src = "ysgl/bmyssb1/zfcgys_list";
		}else if("7".equals(dm)){
			List list = bmyssbService.getxlkinfo();
			mv.addObject("xlklist", list);
			Map<?, ?>  map = bmyssbService.getObjectById(pd,userDwbh);
			mv.addObject("info", map);
			src = "ysgl/bmyssb1/czzcjxmbsq_list";
		} else if ("8".equals(dm)) {
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/rcgyzcysb_list";
		} else if ("9".equals(dm)) {// - 办公家具
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/zfcgysbgjj_list";
		} else if ("10".equals(dm)) {// -通用设备采购
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/zfcgystysb_list";
		} else if ("11".equals(dm)) {// -专用设备采购
			List list = bmyssbService.getObjectsById(pd, userDwbh);
			mv.addObject("list", list);
			src = "ysgl/bmyssb1/zfcgyszysb_list";
		}
		mv.addObject("dm", dm);
		mv.addObject("bmmc", bmmc);
		mv.addObject("rymc", rymc);
		mv.addObject("userDwbh", userDwbh);
		mv.addObject("userRybh", userRybh);
		mv.addObject("year", year);
		mv.addObject("operateType", "C");
		mv.setViewName(src);
		return mv;
	}

	@RequestMapping(value = "/goEditPage")
	public ModelAndView goEditPage() {
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		// mv.addObject("jflxList",dictService.getDict(Constant.JFLX));
		// mv.addObject("guid", pd.getString("dwbh"));
		String userDwbh = LUser.getDwbh();
		Map<?, ?> map = bmyssbService.getObjectById(pd, userDwbh);
		mv.addObject("info", map);
		String dm = pd.getString("dm");
		String src = "ysgl/bmyssb1/fm_list";
		if ("2".equals(dm)) {
			Map<?, ?> map2 = bmyssbService.getObjectById(pd, pd.getString("bzid"));
			mv.addObject("info", map2);
			src = "ysgl/bmyssb1/jbxx_list";
		} else if ("3".equals(dm)) {
			List list3 = bmyssbService.getObjectsById(pd, pd.getString("bzid"));
			mv.addObject("info", list3);
			src = "ysgl/bmyssb1/srys_list";
		} else if ("4".equals(dm)) {
			src = "ysgl/bmyssb1/zxywfzcys_list";
		} else if ("5".equals(dm)) {
			src = "ysgl/bmyssb1/wxzlfzcys_list";
		} else if ("6".equals(dm)) {
			src = "ysgl/bmyssb1/zfcgys_list";
		} else if ("7".equals(dm)) {
			src = "ysgl/bmyssb1/czzcjxmbsq_list";
		} else if ("8".equals(dm)) {
			src = "ysgl/bmyssb1/rcgyzcysb_list";
		} else if ("9".equals(dm)) {// - 办公家具
			src = "ysgl/bmyssb1/zfcgysbgjj_list";
		} else if ("10".equals(dm)) {// -通用设备采购
			src = "ysgl/bmyssb1/zfcgystysb_list";
		} else if ("11".equals(dm)) {// -专用设备采购
			src = "ysgl/bmyssb1/zfcgyszysb_list";
		}
		mv.setViewName(src);
		mv.addObject("operateType", "U");
		return mv;
	}

	@RequestMapping(value = "/doSave", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave() {
		PageData pd = this.getPageData();
		String operateType = pd.getString("operateType");
		int result = 0;
		if ("C".equals(operateType)) {
			// 生成单位编号
			result = bmyssbService.doAdd(pd);
			if (result == 1) {
				return "{success:'true', msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else if ("U".equals(operateType)) {
			result = bmyssbService.doUpdate(pd);
			if (result == 1) {
				return "{success:'true',msg:'信息保存成功！'}";
			} else {
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		} else {
			return MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}

	@RequestMapping(value = "/doDeleteAll", produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object doDeleteAll() {
		PageData pd = this.getPageData();
		String guid = pd.getString("guid");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(new Date()));

		String dm = pd.getString("dm");
		String userDwbh = LUser.getDwbh();
		// 删除单位时验证该单位下是否有人员或下级单位或资产
		int k = bmyssbService.doDeleteAll(userDwbh, year);
		if (k > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
		} else {
			return MessageBox.show(false, MessageBox.FAIL_DELETE);
		}
	}

	@RequestMapping(value = "/checkIsAdd", produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkIsAdd() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int k = bmyssbService.checkIsAdd(LUser.getDwbh(), Integer.parseInt(sdf.format(new Date())));
		if (k > 0) {
			return "1";
		} else {
			return "0";
		}
	}
	@RequestMapping(value="/checkIsAdd2",produces = "text/html;charset=utf-8")
	@ResponseBody
	public String checkIsAdd2() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int k = bmyssbService.checkIsAdd2(LUser.getDwbh(),Integer.parseInt(sdf.format(new Date())));
		if(k>0){
			return "1";
		}else{
			return "0";
    	}
	}

	@RequestMapping(value = "/tosecond")
	public ModelAndView tosecond() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(new Date()));
		System.err.println("_______year____" + year);

		String dm = pd.getString("dm");
		String userDwbh = LUser.getDwbh();
		String userRybh = LUser.getRybh();
		String bmmc = bmyssbService.getBmmcByDwbh(userDwbh);
		String rymc = bmyssbService.getRymcByRybh(userRybh);
		String src = "ysgl/bmyssb1/zfcgxmjgcs_list";
		if ("9".equals(dm)) {// - 办公家具
			Map map = bmyssbService.getMxObjectById(pd, userDwbh);
			List list = bmyssbService.getMxObjectsById(pd, userDwbh);
			mv.addObject("map", map);
			mv.addObject("list", list);
		} else if ("10".equals(dm)) {// -通用设备采购
			Map map = bmyssbService.getMxObjectById(pd, userDwbh);
			List list = bmyssbService.getMxObjectsById(pd, userDwbh);
			mv.addObject("map", map);
			mv.addObject("list", list);
		} else if ("11".equals(dm)) {// -专用设备采购
			Map map = bmyssbService.getMxObjectById(pd, userDwbh);
			List list = bmyssbService.getMxObjectsById(pd, userDwbh);
			mv.addObject("map", map);
			mv.addObject("list", list);
		}
		mv.addObject("dm",Integer.parseInt(dm)+3);
		mv.addObject("bmmc", bmmc);
		mv.addObject("rymc", rymc);
		mv.addObject("userDwbh", userDwbh);
		mv.addObject("userRybh", userRybh);
		mv.addObject("year", year);
		mv.addObject("operateType", "C");
		mv.setViewName(src);
		return mv;
	}

	@RequestMapping(value="/getLengthBydm",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getLengthBydm() {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		List klist = bmyssbService.getLengthBydm(this.getPageData().getString("dm"));
		Gson gson = new Gson();
		String str = gson.toJson(klist);
		return str;
	}
	// 创建 提交流程
	/**
	 * 提交
	 * 
	 * @return
	 */
	@RequestMapping(value = "/submit", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object submit(String mkbh) {
		String guid = this.getPageData().getString("guid");
		// String guidAry[] = guids.split(",");
		int i = 0;
		bmyssbService.submit(guid, mkbh);
		if (i > 0) {
			return MessageBox.show(true, MessageBox.SUCCESS_SUBMIT);
		} else {
			return MessageBox.show(false, MessageBox.SUCCESS_SUBMIT);
		}
	}

}
