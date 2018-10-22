package com.googosoft.controller.fzgn.sjdr;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.fzgn.sjdr.ZC_ZJB_DRMX;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.sjdr.yssjzlService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value="/yssjzl")
public class yssjzlController extends BaseController{
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="yssjzlService")
	private yssjzlService yssjzlService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	/**
	 * 获取列表数据
	 * @throws Exception 
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception{
		PageData pd = this.getPageData();
		String type = Validate.isNullToDefault(pd.getString("type"), "")+"";
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" A.yqbh,A.yqmc,A.flh,A.flmc,A.dj,A.ZZJ,A.syfx,A.jldw,A.xz,A.jfkm,A.zcly,A.jzlx,A.jzxs,");
		sqltext.append(" (select '('||d.bmh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.sydw) as sydw,");
		sqltext.append(" (select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=a.syr) as syr,A.sccj,");
		sqltext.append(" (select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=a.cgr) as cgr,");//采购人
		sqltext.append(" (select '('||ddh||')'||mc from zc_sys_ddb b where b.ddbh=a.bzxx) as bzxx,A.czr,");
		sqltext.append(" A.dzrrq,A.gzrq,A.tph,");
		sqltext.append(" A.rzrq,A.zjjt,A.ljzj,A.cgzzxs,A.xmh,A.pzh,A.cqly,");
		sqltext.append(" A.ccrq,A.qsrq,A.synx,");
		sqltext.append(" A.fw_fzrq,A.tddj,A.htbh,A.xss,A.qsxz,A.fgxs,A.cqxs,a.td_qsnx,");
		sqltext.append(" A.fw_qszm,A.jsh,A.zmmj,A.jcnf,A.fwyt,A.zj,A.dsmj,A.dxmj,A.fw_zrjs,A.yt,A.gzyq,A.fjzj,");
		sqltext.append(" A.fw_zymj,A.zyjz,A.xh,A.fw_cjmj,A.wbje,A.fw_czmj,A.czjz,A.fw_gnmj,A.fw_jymj,A.symj,");
		sqltext.append(" (select '('||d.dm||')'||mc from gx_sys_dmb d where d.dm = A.xk and zl='" + Constant.XKML + "') xk,");
		sqltext.append(" A.fw_xzmj,A.xzjz,A.cch,A.fw_qtmj,A.td_qtmj,A.qtjz,A.jkdj,A.zyyt,A.bz,A.xklb,A.td_rzkm,A.td_qszm,");
		sqltext.append(" A.td_qsxz,A.djh,A.td_dymj,A.td_fzrq,A.td_ftmj,A.td_yt,");
		sqltext.append(" A.td_zymj,A.td_czmj,A.td_cjmj,A.cjjz,A.td_jymj,A.jyjz,A.gg,A.fjs,A.wbzl,A.fzr,");
		sqltext.append(" (select '('||d.dm||')'||mc from gx_sys_dmb d where d.dm = A.GBM and zl='" + Constant.GB + "') gbm,");
		sqltext.append(" A.sykh,A.dabh,A.kyxm,A.zdsys,A.bxjzrq,A.pp,");
		sqltext.append(" A.jt_pql,A.jt_clcd,A.jt_bzqk,A.jt_syxz,A.syxz,A.fzrq,");
		sqltext.append(" A.wx_pgjz,A.wx_djjg,A.wx_djrq,A.wx_zlh,A.wx_pzwh,");
		sqltext.append(" A.wx_gljg,A.wx_fmmc,A.wx_ntxe,A.sb_gl,A.guid");
		pageList.setSqlText(sqltext.toString());
		//表名
		pageList.setTableName("zc_zjb_dr A");
		//主键
		pageList.setKeyId("guid");
		//设置WHERE条件
		StringBuffer sqlwhere = new StringBuffer();
		if("fw".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,4) = '0101'");
		}else if("gzw".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,4) = '0102'");
		}else if("td".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,4) = '0201'");
		}else if("plant".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,4) = '0202'");
		}else if("car".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,4) = '0413'");
		}else if("ptsb".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,2) in ('03','04','05','06','07','08','09','12','14','15') and substr(A.flh,1,4) <> '0413'");
		}else if("ww".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,2) = '10'");
		}else if("books".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,2) = '11'");
		}else if("jj".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,2) = '13'");
		}else if("animals".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,2) = '16'");
		}else if("wxzc".equals(type)){
			sqlwhere.append(" and substr(A.flh,1,2) = '17'");
		}
		pageList.setStrWhere(sqlwhere.toString());
		//页面数据
		pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	/**
	 * 列表页面
	 * @return
	 */
	@RequestMapping(value="/getPage")
	@ResponseBody
	public ModelAndView getPage(){
		ModelAndView mv = this.getModelAndView();
		String mkbh = this.getPageData().getString("mkbh");
		//页面select选择信息
		List xh = dictService.getDictBySplit(Constant.XH);//建筑结构
		List jfkm = dictService.getDictBySplit(Constant.JFKM);//经费来源
		List zcly = dictService.getDictBySplit(Constant.ZCLY);//资产来源
		List jldw = dictService.getDictBySplit(Constant.JLDW);//计量单位
		List xz = dictService.getDictBySplit(Constant.XZ);//现状
		List wbzl = dictService.getDictBySplit(Constant.WBZL);//外币种类
		List zj = dictService.getDictBySplit(Constant.ZJ);//建筑类型
		List xklb = dictService.getDictBySplit(Constant.XKLB);//学科类别
		List jzlx = dictService.getDictBySplit(Constant.JZLX);//记账类型
		List cllx = dictService.getDictBySplit(Constant.CLLX);//车辆类型
		List clbz = dictService.getDictBySplit(Constant.CLBZ);//车辆编制
		List syxz = dictService.getDictBySplit(Constant.SYXZ);//车辆用途
		List fgxs = dictService.getDictBySplit(Constant.FGXS);//房管形式
		List jzxs = dictService.getDictBySplit(Constant.JZXS);//价值类型
		List zjjt = dictService.getDictBySplit(Constant.ZJJT);//折旧状态
		List cgzzxs = dictService.getDictBySplit(Constant.CGZZXS);//采购组织形式(植物)
		List td_qsxz = dictService.getDictBySplit(Constant.TD_QSXZ);//土地权属性质
		List td_rzkm = dictService.getDictBySplit(Constant.TD_RZKM);//土地入账科目
		List cqxs = dictService.getDictBySplit(Constant.CQXS);//房屋产权形式
		List qsxz = dictService.getDictBySplit(Constant.QSXZ);//房屋、构筑物类代表权属性质
		List jt_bzqk = dictService.getDictBySplit(Constant.JT_BZQK);//交通编制情况
		List jt_syxz = dictService.getDictBySplit(Constant.JT_SYXZ);//车辆使用性质
		List tddj = dictService.getDictBySplit(Constant.TDDJ);//完损状况
		List fwyt = dictService.getDictBySplit(Constant.FWYT);//房屋状况
		List syfx = dictService.getDictBySplit(Constant.SYFX);//使用方向
		List rj_lx = dictService.getDictBySplit(Constant.RJ_LX);//软件类型
		List rj_sqxklx = dictService.getDictBySplit(Constant.RJ_SQXKLX);//授权许可类型
		List wx_xcfs = dictService.getDictBySplit(Constant.WX_XCFS);//形成方式
		List jt_ppxz = dictService.getDictBySplit(Constant.JT_PPXZ);//品牌性质
		List zdsys = dictService.getDictBySplit(Constant.ZDSYS);//重点实验室
		mv.addObject("xhlist", xh);
		mv.addObject("jfkmlist", jfkm);
		mv.addObject("zclylist", zcly);
		mv.addObject("jldwlist", jldw);
		mv.addObject("xzlist", xz);
		mv.addObject("wbzllist", wbzl);
		mv.addObject("zjlist", zj);
		mv.addObject("xklblist", xklb);
		mv.addObject("jzlxlist", jzlx);
		mv.addObject("cllxlist", cllx);
		mv.addObject("clbzlsit", clbz);
		mv.addObject("syxzlist", syxz);
		mv.addObject("fgxslist", fgxs);
		mv.addObject("jzxslist", jzxs);
		mv.addObject("zjjtlist", zjjt);
		mv.addObject("cgzzxslist", cgzzxs);
		mv.addObject("td_qsxzlist", td_qsxz);
		mv.addObject("td_rzkmlist", td_rzkm);
		mv.addObject("cqxslist", cqxs);
		mv.addObject("qsxzlist", qsxz);
		mv.addObject("jt_bzqklist", jt_bzqk);
		mv.addObject("jt_syxzlist", jt_syxz);
		mv.addObject("tddjlist", tddj);
		mv.addObject("fwytlist", fwyt);
		mv.addObject("syfxlist", syfx);
		mv.addObject("rj_lxlist", rj_lx);
		mv.addObject("rj_sqxklxlist", rj_sqxklx);
		mv.addObject("wx_xcfslist", wx_xcfs);
		mv.addObject("jt_ppxzlist", jt_ppxz);
		mv.addObject("zdsyslist", zdsys);
		mv.addObject("mkbh", mkbh);
		mv.setViewName("fzgn/sjdr/yssjzl/yssjzl_list");
		return mv;
	}
	/**
	 * 页面信息保存
	 * @param ysd
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(ZC_ZJB_DRMX zjb) throws ParseException{
		if(yssjzlService.doAdd(zjb)){
			return  "{\"success\":true,\"msg\":\"信息保存成功！\"}";
		}else{
			return  "{\"success\":false,\"msg\":\"信息保存失败！\"}";
		}
	}
	/**
	 * 删除信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDelete(){
		if(yssjzlService.doDelete(this.getPageData().getString("id"))>0){
			return "{\"success\":true,\"msg\":\"信息删除成功！\"}";
		}else{
			return "{\"success\":false,\"msg\":\"信息删除失败！\"}";
		}
	}
	/**
	 * 清空信息
	 * @return
	 */
	@RequestMapping(value="/doDel",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doDel(){
		if(yssjzlService.doDel()>0){
			return "{\"success\":true,\"msg\":\"信息删除成功！\"}";
		}else{
			return "{\"success\":false,\"msg\":\"信息删除失败！\"}";
		}
	}
	/**
	 * 入账
	 * @return
	 */
	@RequestMapping(value="/doRz",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doRz(){
		if(yssjzlService.DoRz(this.getPageData().getString("id"))){
			return "{\"success\":true,\"msg\":\"信息操作成功！\"}";
		}else{
			return "{\"success\":false,\"msg\":\"信息操作失败！\"}";
		}
	}
	/**
	 * 验证
	 * @return
	 */
	@RequestMapping(value="/docheck",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object docheck(ZC_ZJB_DRMX zjb){
		return yssjzlService.docheck(zjb);
	}
	/**
	 * 审核验证
	 * @return
	 */
	@RequestMapping(value="/doShcheck",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doShcheck(){
		String id = this.getPageData().getString("id");
		String type = this.getPageData().getString("type");
		String tj = this.getPageData().getString("tj");
		return yssjzlService.doShcheck(type,id,tj);
	}
}
