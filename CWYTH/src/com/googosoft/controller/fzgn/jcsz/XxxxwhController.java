package com.googosoft.controller.fzgn.jcsz;


import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.fzgn.clbx.GX_CLBX;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_XXXXWH;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.fzgn.jcsz.XxxxwhService;
import com.googosoft.util.PageData;
/**
 * 学校信息维护类
 */
@Controller
@RequestMapping(value="/xxxxwh")
public class XxxxwhController extends BaseController{

	@Resource(name="xxxxwhService")
	private XxxxwhService xxxxwhService;
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	/**
	 * 学校信息保存
	 * @param xxxxwh
	 * @return
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doSave(GX_SYS_XXXXWH xxxxwh)
	{
		
		int result=0; 
		result = xxxxwhService.doAdd(xxxxwh);
		if(result==1)
		{
			return MessageBox.show(true, MessageBox.SUCCESS_SAVE);
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}

	}
	
	/**
	 * 跳转学校信息维护页面
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		Map<?, ?>  map = xxxxwhService.getObjectById("001");
		mv.addObject("xxxxwh", map);
		mv.addObject("flg", "1");
		mv.setViewName("fzgn/jcsz/xxxxwh/xxxxwh");
		return mv;
		
	}
	
	/**
	 * 跳转平台设置：差旅报销标准页面
	 * @return
	 */
	@RequestMapping(value="/goClbxEditPage")
	public ModelAndView goClbxEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		List  Clbxlist = xxxxwhService.getClbxlist();
		if(Clbxlist.size()>=1){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map1 = (Map)Clbxlist.get(0);
				mv.addObject("map1",map1);
			}
		}
		if(Clbxlist.size()>=2){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map2 = (Map)Clbxlist.get(1);
				mv.addObject("map2",map2);
			}
		}
		if(Clbxlist.size()>=3){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map3 = (Map)Clbxlist.get(2);
				mv.addObject("map3",map3);
			}
		}
		if(Clbxlist.size()>=4){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map4 = (Map)Clbxlist.get(3);
				mv.addObject("map4",map4);
			}
		}
		if(Clbxlist.size()>=5){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map5 = (Map)Clbxlist.get(4);
				mv.addObject("map5",map5);
			}
		}
		if(Clbxlist.size()>=6){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map6 = (Map)Clbxlist.get(5);
				mv.addObject("map6",map6);
			}
		}
		if(Clbxlist.size()>=7){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map7 = (Map)Clbxlist.get(6);
				mv.addObject("map7",map7);
			}
		}
		if(Clbxlist.size()>=8){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map8 = (Map)Clbxlist.get(7);
				mv.addObject("map8",map8);
			}
		}
		if(Clbxlist.size()>=9){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map9 = (Map)Clbxlist.get(8);
				mv.addObject("map9",map9);
			}
		}
		if(Clbxlist.size()>=10){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map10 = (Map)Clbxlist.get(9);
				mv.addObject("map10",map10);
			}
		}
		if(Clbxlist.size()>=11){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map11 = (Map)Clbxlist.get(10);
				mv.addObject("map11",map11);
			}
		}
		if(Clbxlist.size()>=12){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map12 = (Map)Clbxlist.get(11);
				mv.addObject("map12",map12);
			}
		}
		if(Clbxlist.size()>=13){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map13 = (Map)Clbxlist.get(12);
				mv.addObject("map13",map13);
			}
		}
		if(Clbxlist.size()>=14){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map14 = (Map)Clbxlist.get(13);
				mv.addObject("map14",map14);
			}
		}
		if(Clbxlist.size()>=15){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map15 = (Map)Clbxlist.get(14);
				mv.addObject("map15",map15);
			}
		}
		if(Clbxlist.size()>=16){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map16 = (Map)Clbxlist.get(15);
				mv.addObject("map16",map16);
			}
		}
		if(Clbxlist.size()>=17){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map17 = (Map)Clbxlist.get(16);
				mv.addObject("map17",map17);
			}
		}
		if(Clbxlist.size()>=18){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map18 = (Map)Clbxlist.get(17);
				mv.addObject("map18",map18);
			}
		}
		if(Clbxlist.size()>=19){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map19 = (Map)Clbxlist.get(18);
				mv.addObject("map19",map19);
			}
		}
		if(Clbxlist.size()>=20){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map20 = (Map)Clbxlist.get(19);
				mv.addObject("map20",map20);
			}
		}
		if(Clbxlist.size()>=21){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map21 = (Map)Clbxlist.get(20);
				mv.addObject("map21",map21);
			}
		}
		if(Clbxlist.size()>=22){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map22 = (Map)Clbxlist.get(21);
				mv.addObject("map22",map22);
			}
		}
		if(Clbxlist.size()>=23){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map23 = (Map)Clbxlist.get(22);
				mv.addObject("map23",map23);
			}
		}
		if(Clbxlist.size()>=24){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map24 = (Map)Clbxlist.get(23);
				mv.addObject("map24",map24);
			}
		}
		if(Clbxlist.size()>=25){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map25 = (Map)Clbxlist.get(24);
				mv.addObject("map25",map25);
			}
		}
		if(Clbxlist.size()>=26){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map26 = (Map)Clbxlist.get(25);
				mv.addObject("map26",map26);
			}
		}
		if(Clbxlist.size()>=27){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map27 = (Map)Clbxlist.get(26);
				mv.addObject("map27",map27);
			}
		}
		if(Clbxlist.size()>=28){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map28 = (Map)Clbxlist.get(27);
				mv.addObject("map28",map28);
			}
		}
		if(Clbxlist.size()>=29){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map29 = (Map)Clbxlist.get(28);
				mv.addObject("map29",map29);
			}
		}
		if(Clbxlist.size()>=30){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map30 = (Map)Clbxlist.get(29);
				mv.addObject("map30",map30);
			}
		}
		if(Clbxlist.size()>=31){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map31 = (Map)Clbxlist.get(30);
				mv.addObject("map31",map31);
			}
		}
		if(Clbxlist.size()>=32){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map32 = (Map)Clbxlist.get(31);
				mv.addObject("map32",map32);
			}
		}
		if(Clbxlist.size()>=33){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map33 = (Map)Clbxlist.get(32);
				mv.addObject("map33",map33);
			}
		}
		if(Clbxlist.size()>=34){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map34 = (Map)Clbxlist.get(33);
				mv.addObject("map34",map34);
			}
		}
		if(Clbxlist.size()>=35){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map35 = (Map)Clbxlist.get(34);
				mv.addObject("map35",map35);
			}
		}
		if(Clbxlist.size()>=36){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map36 = (Map)Clbxlist.get(35);
				mv.addObject("map36",map36);
			}
		}
		if(Clbxlist.size()>=37){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map37 = (Map)Clbxlist.get(36);
				mv.addObject("map37",map37);
			}
		}
		if(Clbxlist.size()>=38){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map38 = (Map)Clbxlist.get(37);
				mv.addObject("map38",map38);
			}
		}
		if(Clbxlist.size()>=39){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map39 = (Map)Clbxlist.get(38);
				mv.addObject("map39",map39);
			}
		}
		if(Clbxlist.size()>=40){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map40 = (Map)Clbxlist.get(39);
				mv.addObject("map40",map40);
			}
		}
		if(Clbxlist.size()>=41){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map41 = (Map)Clbxlist.get(40);
				mv.addObject("map41",map41);
			}
		}
		if(Clbxlist.size()>=42){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map42 = (Map)Clbxlist.get(41);
				mv.addObject("map42",map42);
			}
		}
		if(Clbxlist.size()>=43){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map43 = (Map)Clbxlist.get(42);
				mv.addObject("map43",map43);
			}
		}
		if(Clbxlist.size()>=44){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map44 = (Map)Clbxlist.get(43);
				mv.addObject("map44",map44);
			}
		}
		if(Clbxlist.size()>=45){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map45 = (Map)Clbxlist.get(44);
				mv.addObject("map45",map45);
			}
		}
		if(Clbxlist.size()>=46){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map46 = (Map)Clbxlist.get(45);
				mv.addObject("map46",map46);
			}
		}
		if(Clbxlist.size()>=47){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map47 = (Map)Clbxlist.get(46);
				mv.addObject("map47",map47);
			}
		}
		if(Clbxlist.size()>=48){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map48 = (Map)Clbxlist.get(47);
				mv.addObject("map48",map48);
			}
		}
		if(Clbxlist.size()>=49){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map49 = (Map)Clbxlist.get(48);
				mv.addObject("map49",map49);
			}
		}
		if(Clbxlist.size()>=50){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map50 = (Map)Clbxlist.get(49);
				mv.addObject("map50",map50);
			}
		}
		if(Clbxlist.size()>=51){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map51 = (Map)Clbxlist.get(50);
				mv.addObject("map51",map51);
			}
		}
		if(Clbxlist.size()>=52){
			for (int i = 0; i < Clbxlist.size(); i++) {
				Map map52 = (Map)Clbxlist.get(51);
				mv.addObject("map52",map52);
			}
		}
//		mv.addObject("Clbxlist", Clbxlist);
		mv.setViewName("system/clbx/clbx_edit");
		return mv;
		
	}
	
	
	/**
	 * 维护学校信息
	 * @param xxxxwh
	 * @return
	 */
	@RequestMapping(value="/doUpdate",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doUpdate(GX_SYS_XXXXWH xxxxwh){
		//System.out.println(xxxxwh.getGuid());
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		xxxxwh.setCzr(loginId);
		int result=0;
		result = xxxxwhService.doUpdate(xxxxwh);
		if(result==1)
		{
			return "{success:'true',msg:'信息保存成功！',guid:'"+xxxxwh.getGuid()+"'}";
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	
	/**
	 * 差旅信息报销标准保存
	 * @param clbx
	 * @return
	 */
	@RequestMapping(value="/doclbxSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object doclbxSave(GX_CLBX clbx){
		PageData pd = new PageData();
		String[] zsbz1 = this.getRequest().getParameterValues("zsbz1");
		String[] zsbz2 = this.getRequest().getParameterValues("zsbz2");
		String[] wj1 = this.getRequest().getParameterValues("wj1");
		String[] wj2 = this.getRequest().getParameterValues("wj2");
		String[] wj3 = this.getRequest().getParameterValues("wj3");
		String[] sfbl1 = this.getRequest().getParameterValues("sfbl1");
		String[] bz = this.getRequest().getParameterValues("bz");
		String[] jtbz = this.getRequest().getParameterValues("jtbz");
		String[] city = this.getRequest().getParameterValues("city");
		int result=0;
		result = xxxxwhService.doclbxSave(clbx,zsbz1,zsbz2,wj1,wj2,wj3,sfbl1,bz,jtbz,city);
		if(result==1)
		{
			return "{success:'true',msg:'信息保存成功！',guid:'"+clbx.getBz()+"'}";
		}
		else
		{
			return MessageBox.show(false, MessageBox.FAIL_SAVE);
		}
	}
	
	

		
}
