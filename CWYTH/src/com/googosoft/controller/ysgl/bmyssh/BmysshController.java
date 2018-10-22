package com.googosoft.controller.ysgl.bmyssh;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.shzt.YsglShzt;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.bmyssb.BmyssbService;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value = "ysgl/bmyssh")
public class BmysshController extends BaseController {

	@Resource(name = "dictService")
	DictService dictService;
	@Resource(name = "pageService")
	private PageService pageService;// 单例
	@Resource(name = "bmyssbService")
	private BmyssbService bmyssbService;// 单例

	// 初始化登录人员信息
	public void iniLogin(ModelAndView mv) {
		mv.addObject("loginId", LUser.getGuid());
		mv.addObject("szbm", LUser.getDwmc());
		mv.addObject("ryxm", "(" + LUser.getRybh() + ")" + LUser.getRyxm());
		mv.addObject("today", DateUtil.getDay());
	}

	// 跳转到部门预算申报列表页面
	@RequestMapping("/goBmysshPage")
	public ModelAndView goBmyssbPage() {
		ModelAndView mv = this.getModelAndView();
		iniLogin(mv);
		mv.addObject("shztList", dictService.getDict(Constant.bmysshzt));
		mv.setViewName("ysgl/bmyssh/bmyssh_list");
		return mv;
	}

	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getZcxgshPageList() throws Exception {
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();// 查询字段
		String shzt = Validate.isNullToDefaultString(pd.get("shzt"), "00");
		System.err.println("shzt"+shzt);
		StringBuffer tablename = new StringBuffer();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
		int year = Integer.parseInt(sdf.format(new Date()));
		String userDwbh = LUser.getDwbh();
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(
			" (  select distinct f.guid,f.bmmc,f.shzt as shztsz,f.shzt as  shzt,DM.MC AS SHZTMC,F.PROCINSTID,(select d.mc from gx_sys_dwb d where d.dwbh=f.bmmc)bmmcs,f.nd,to_char(f.sqsj,'yyyy-mm-dd')sqsj,to_char(f.bzrq,'yyyy-mm-dd')bzrq,f.jbr,\r\n"
				+ "(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=f.jbr)jbrmc,j.dwfzr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=j.dwfzr)dwfzrmc,\r\n"
				+ "j.cwfzr ,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=j.cwfzr)cwfzrmc,to_char((select sum(yjwcse) from cw_srysb s where s.tbbm='" + userDwbh  
				+ "'),'FM999999999999990.0000')sryshz,\r\n" + "to_char(((select nvl(sum(z.NAPZJS),0) from cw_zxywfzcysb z where z.TBBM = '" + userDwbh
				+ "')+(select nvl(sum(z.NAPZJS),0) from cw_wxzlfzcysb z where z.TBBM = '" + userDwbh + "')+\r\n" + "(select nvl(sum(z.CGYSJE),0) from cw_zfcgysb z where z.TBBM = '"
				+ userDwbh + "')+(select nvl(sum(z.zjze),0) from cw_czzcjxmbsqb z where z.TBBM = '" + userDwbh + "')\r\n"
				+ "+(select nvl(sum(z.nysze),0) from cw_rcgyzcysb z where z.TBBM = '" + userDwbh + "')),'FM999999999999999990.0000')zcyshz,\r\n"
				+ "to_char(((select nvl(sum(z.NYSJYAPS),0) from cw_zxywfzcysb z where z.TBBM = '" + userDwbh
				+ "')+(select nvl(sum(z.NAPZJS),0) from cw_wxzlfzcysb z where z.TBBM = '" + userDwbh + "')+\r\n"
				+ "(select nvl(sum(z.nysjyaps),0) from cw_rcgyzcysb z where z.TBBM = '" + userDwbh + "')),'FM99999999999999990.0000')jyje\r\n"
				+ "from cw_fmb f left join cw_jbxxb j on j.dwmc=f.bmmc left join cw_srysb s on s.tbbm=f.bmmc  ");
		tablename.append(" LEFT JOIN GX_SYS_DMB DM ON DM.ZL='"+YsglShzt.SHZTZL+"' AND DM.DM=F.SHZT");
		if("00".equals(shzt)){
			tablename.append(" LEFT JOIN ACT_RU_TASK ACT ON F.PROCINSTID = ACT.PROC_INST_ID_");
		}else{
			tablename.append(" LEFT JOIN ACT_HI_ACTINST ACT ON F.PROCINSTID = ACT.PROC_INST_ID_ AND ACT.END_TIME_ IS NOT NULL AND ACT_ID_<>'sqr'");
		}
		if("00".equals(shzt)){
			tablename.append(" WHERE  ACT.ASSIGNEE_='"+LUser.getGuid()+"'");
			tablename.append(" and F.shzt not in ('"+YsglShzt.WTJ+"','"+YsglShzt.bmfzrth+"') ");
		}else{
			tablename.append(" WHERE  ACT.ASSIGNEE_='"+LUser.getGuid()+"'");
			tablename.append(" and F.shzt not in ('"+YsglShzt.WTJ+"') ");
		}
		tablename.append(")k ");
		pageList.setSqlText(sqltext.toString());
		// 设置表名
		pageList.setTableName(tablename.toString());
		// 设置表主键名
		pageList.setKeyId("GUID");// 主键
		// 设置WHERE条件
//		pageList.setStrWhere("");
		pageList.setHj1("");// 合计
		pageList = pageService.findPageList(pd, pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw") + "", pageList.getTotalList().get(0).get("NUM") + "", pageList.getTotalList().get(0).get("NUM") + "",
			gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();

	}

	// 审核
	@RequestMapping(value = "/doApprove", produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object submit(OA_SHYJB shyjb) {
		
		String shyj = this.getPageData().getString("shyj");//审核意见
		String tjyld = this.getPageData().getString("tjyld");
		String SRYSHZS=this.getPageData().getString("SRYSHZ");//收入预算汇总金额
		String ZCYSHZS=this.getPageData().getString("ZCYSHZ");//支出预算汇总金额
		String guids = this.getPageData().getString("guid");
		String procinstids = this.getPageData().getString("procinstid");
		String pass = this.getPageData().getString("pass");
		String guidAry[] = guids.split(",");
		String procinstidAry[] = procinstids.split(",");
		String SRYSHZAry[]=SRYSHZS.split(",");
		String ZCYSHZAry[]=ZCYSHZS.split(",");
		String forward = "";
		if ("false".equals(pass)) {// 审核不通过
			if(guidAry.length>0&&procinstidAry.length==guidAry.length){
				for(int j=0;j<guidAry.length;j++){
					String guid = Validate.isNullToDefaultString(guidAry[j], "");
					String procinstid = Validate.isNullToDefaultString(procinstidAry[j], "");
					String SRYSHZ = Validate.isNullToDefaultString(SRYSHZAry[j], "");
					String ZCYSHZ = Validate.isNullToDefaultString(ZCYSHZAry[j], "");
					if (Validate.noNull(guids)) {
						bmyssbService.rejectleaveinfo( shyjb,guid, procinstid,ZCYSHZ,shyj);
					}
				}
			}
			forward = "{success:true, msg:'退回成功！'}";
		} else {
			// 通过
			if(guidAry.length>0&&procinstidAry.length==guidAry.length){
				for(int j=0;j<guidAry.length;j++){
					String guid = Validate.isNullToDefaultString(guidAry[j], "");
					String procinstid = Validate.isNullToDefaultString(procinstidAry[j], "");
					String SRYSHZ = Validate.isNullToDefaultString(SRYSHZAry[j], "");
					String ZCYSHZ = Validate.isNullToDefaultString(ZCYSHZAry[j], "");
					if (Validate.noNull(guid)) {
						bmyssbService.approveLeaveInfo(shyjb,guid, procinstid,ZCYSHZ,shyj);
					}
					forward = "{success:true, msg:'通过成功！'}";
				}
			}
		}
		return forward;
	}
	//跳转到审核页面
			@RequestMapping("/check")
			public ModelAndView check() {
				ModelAndView mv = this.getModelAndView();
				PageData pd = this.getPageData();
				String check = Validate.isNullToDefaultString(pd.getString("check"), "");
				String guid = Validate.isNullToDefaultString(pd.getString("guid"), "");
				String procinstid = Validate.isNullToDefaultString(pd.getString("procinstid"), "");
				String SRYSHZ = Validate.isNullToDefaultString(pd.getString("SRYSHZ"), "");
				String ZCYSHZ = Validate.isNullToDefaultString(pd.getString("ZCYSHZ"), "");
				mv.addObject("guid",guid);
				mv.addObject("procinstid",procinstid);
				mv.addObject("SRYSHZ",SRYSHZ);
				mv.addObject("ZCYSHZ",ZCYSHZ);
				mv.setViewName("ysgl/bmyssh/check/"+check);
				return mv;
			}
}
