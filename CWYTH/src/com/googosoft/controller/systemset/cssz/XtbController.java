package com.googosoft.controller.systemset.cssz;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB_EXTEND;
import com.googosoft.service.base.DictService;
import com.googosoft.service.systemset.cssz.XtbService;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
/**
 * 系统运行参数设置
 * @author master
 */
@Controller
@RequestMapping(value="/xtb")
public class XtbController extends BaseController{
	
	@Resource(name="xtbService")
	private XtbService xtbService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value="/goXtbPage")
	public ModelAndView goXtbPage(){
		Map map = xtbService.getXtcs(); //只需要查询一条数据，不要根据guid 查询
		Map xtsz_map = xtbService.getXtsz();
		ModelAndView mv = this.getModelAndView();
		mv.addObject("xtb", map);
		mv.addObject("xtsz_map", xtsz_map);
		List jfkm = dictService.getDict(Constant.JFKM);//经费来源
		List jldw = dictService.getDict(Constant.JLDW);//计量单位
		List jzlx = dictService.getDict(Constant.JZLX);//记账类型
		List xklb = dictService.getDict(Constant.XKLB);//学科类别
		List xz = dictService.getDict(Constant.XZ);//现状
		List zcly = dictService.getDict(Constant.ZCLY);//资产来源
		List xh = dictService.getDict(Constant.XH);//建筑结构
		List zj = dictService.getDict(Constant.ZJ);//建筑类型
		List wszk = dictService.getDict(Constant.WSZK);//完损状况
		List wbzl = dictService.getDict(Constant.WBZL);//外币种类
		List syfx = dictService.getDict(Constant.SYFX);//使用方向
		List cgzzxs = dictService.getDict(Constant.CGZZXS);//采购组织形式(植物)
		List czfs = dictService.getDict(Constant.CZFS);//处置方式
		mv.setViewName("systemset/cssz/xtb_edit");
		mv.addObject("jfkmlist",jfkm);
		mv.addObject("jldwlist",jldw);
		mv.addObject("jzlxlist",jzlx);
		mv.addObject("xklblist",xklb);
		mv.addObject("xzlist",xz);
		mv.addObject("zclylist",zcly);
		mv.addObject("xhlist",xh);
		mv.addObject("zjlist",zj);
		mv.addObject("wszklist",wszk);
		mv.addObject("wbzllist",wbzl);
		mv.addObject("syfxlist",syfx);
		mv.addObject("cgzzxslist",cgzzxs);
		mv.addObject("czfslist",czfs);
		return mv;
	}
	/**
	 * 添加系统参数
	 * @param sjzdb
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	@Transactional
	public Object doSave(ZC_SYS_XTB_EXTEND xtbextend)throws Exception{
		String b = "";
		String guid = UuidUtil.get32UUID(); //自动生成32uuid 
		xtbextend.setGuid(guid);  //实体类设置guid
		int i=xtbService.doAdd(xtbextend);
		if(i==1){								//页面写上数据了，浏览器向服务器传输数据的时候  可以通过实体类get数据
			b = "{\"success\":\"true\",\"guid\":\""+xtbextend.getGuid()+"\",\"msg\":\"信息保存成功！\"}";
		}else if(i==0){
			b = "{\"success\":\"false\",\"guid\":\"\",\"msg\":\"guid不可重复！\"}";
		}else{
			b = "{\"success\":\"false\",\"msg\":\"信息保存失败！\"}";
		}
		return b;
	}
	
	/**
	 * 获取验收单打印预览列表页面
	 */
	@RequestMapping(value="/goPrintPage")
	public ModelAndView goPrintPage(){
		PageData pd = this.getPageData();
		String djlx = pd.getString("djlx");
		String sftd = pd.getString("sftd");
		String mbbh = pd.getString("mbbh");
		ModelAndView mv = this.getModelAndView();
		mv.addObject("djlx",djlx);
		mv.addObject("sftd",sftd);
		mv.addObject("mbbh",mbbh);
		mv.setViewName("systemset/cssz/print_look");
		return mv;
	}
}
