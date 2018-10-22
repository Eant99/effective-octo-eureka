package com.googosoft.controller.fzgn.jcsz;

import java.text.ParseException;
import java.util.ArrayList;
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
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.FileService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.DwbService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * 单位信息控制类
 * @author master
 */
@Controller
@RequestMapping(value="/dwb")
public class DwbController extends BaseController{
	
	@Resource(name="dwbService")
	private DwbService dwbService;//单例
	
	@Resource(name="dictService")
	private DictService dictService;//单例
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="fileService")
	FileService fileService;
	

	/**
	 * ajax获取实时该单位节点下是否还有节点（是否含有下级单位）
	 * @return
	 */
	@RequestMapping(value="/getNewStatus",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getNewStatus(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String count = dwbService.getNewStatus(dwbh);
		if("0".equals(count)){
			return  "{success:'true'}";
		}else{
			return  "{success:'false',msg:'请将该单位下所有节点单位状态禁用后重试!'}";
		}
	}
	
	/**
	 * 获取单位信息列表页面
	 * @return
	 */
	@RequestMapping(value="/goDwbPage")
	public ModelAndView goDwbPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		System.err.println("___1__"+dwbh);
		//从数据字典中获取单位性质下拉框内容
		List dwxz = dictService.getDict(Constant.DWXZ);
		mv.setViewName("fzgn/jcsz/dwb_list");
		mv.addObject("dwxzlist", dwxz);
		mv.addObject("dwbh",dwbh);
		return mv;
	}
	
	/**
	 * 获取单位信息列表数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getPageList")
	@ResponseBody
	public Object getPageList(){
		PageList pageList = new PageList();
		//设置查询字段名
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" K.DWBH,K.MC,K.JC,K.DZ,TO_CHAR(K.JLRQ,'yyyy-MM-dd') AS JLRQ, DECODE(K.SFXY,'0','否','1','是')SFXY,");
		sqltext.append(" DECODE(K.DWZT,'0','禁用','1','正常') AS DWZT,K.DWJC,K.MJBZ,K.PXXH,K.BMH,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.DWBB+"' AND DM=K.DWBB)AS DWBB,");
		sqltext.append(" K.BMSX,K.BZ,K.SYSBZ,K.SYSJB,K.SYSGS,K.SYSLB,K.SYSMJ,K.JLNF,K.SYSLX,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB M WHERE ZL='"+Constant.DWXZ+"' AND M.DM = K.DWXZ) AS DWXZ,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.DWLD) AS DWLD,K.DWLD AS DWLDH,");
		sqltext.append(" (SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=K.FGLD) AS FGLD,");
		sqltext.append(" (SELECT NVL2(C.BMH,'('||C.BMH||')'||TO_CHAR(C.MC),'') FROM GX_SYS_DWB C WHERE C.DWBH=K.SJDW) AS SJDW,K.SJDW AS SJDWH ");
		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName("GX_SYS_DWB K");
		//设置表主键名
		pageList.setKeyId("DWBH");
		//设置WHERE条件
		PageData pd = this.getPageData();
		String dwbh = pd.getString("treedwbh");

		String rybh = LUser.getRybh();//当前登陆者的人员编号
		pageList.setStrWhere(pageService.getDwqxWhereSql(rybh, "K.DWBH", dwbh, true)); //获取管理单位权限
		//设置合计值字段名
		pageList.setHj1("");
		//页面数据
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 添加单位信息
	 * @param dwb
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(value="/doSave",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doSave(GX_SYS_DWB dwb){
		PageData pd = this.getPageData();
		String operateType = this.getPageData().getString("operateType");
		int result=0;
		String rybh = LUser.getRybh();
		String loginId = xsxxService.getLoginIdByRybh(rybh);
		dwb.setCzr(loginId);
		//String pxxh = pd.getString("pxxh");
		if("C".equals(operateType))//新增
		{  
			//判断部门号是否重复
			boolean checkbmh=dwbService.doCheckDwbh(dwb.getBmh());
			if(checkbmh==false)
			{
				return  "{success:false,msg:'部门号不可重复!'}";
			}
			//判断排序序号是否重复
			boolean checkPxxh=dwbService.doCheckPxxh(dwb.getPxxh());
			if(checkPxxh==false)
			{
				return  "{success:false,msg:'排序序号不可重复!'}";
			}
			//生成单位编号
			String guid =this.get32UUID();//生成主键id
			dwb.setGuid(guid);
			dwb.setDwbh(AutoKey.createDwbh("GX_SYS_DWB", "dwbh", "6"));
			System.out.println("......."+dwb);
			result = dwbService.doAdd(dwb);
			if(result==1){
				return  "{success:'true', msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"',operateType:'U'}";
			}else{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else if("U".equals(operateType))//修改
		{
			if(dwb.getBmh().equals(WindowUtil.getXx(dwb.getSjdw(), "D")))//上级单位不能等于自身单位
			{
				return  "{success:false,dwbh:'"+dwb.getDwbh()+"',msg:'上级单位不能选择自己！'}";
			}

			if(!dwb.getBmh().equals(dwbService.getObjectById(dwb.getDwbh()).get("bmh").toString()))
			{
				boolean checkbmh=dwbService.doCheckDwbh(dwb.getBmh());
				if(checkbmh==false)
				{
					return "{success:false,msg:'部门号不可重复！'}";
				}
			}
			if(!dwb.getPxxh().equals(dwbService.getObjectById(dwb.getDwbh()).get("pxxh")+""))
			{
				boolean checkPxxh=dwbService.doCheckPxxh(dwb.getPxxh());
				if(checkPxxh==false)
				{
					return "{success:false,msg:'排序序号不可重复！'}";
				}
			}
			result = dwbService.doUpdate(dwb,pd);
			if(result==1)
			{
				return "{success:'true',msg:'信息保存成功！',dwbh:'"+dwb.getDwbh()+"'}";
			}
			else
			{
				return MessageBox.show(false, MessageBox.FAIL_SAVE);
			}
		}
		else
		{
	        return	MessageBox.show(false, MessageBox.FAIL_OPERATETPYE);
		}
	}
	
	/**
	 * 删除单位信息
	 * @return
	 */
	@RequestMapping(value="/doDelete",produces = "text/html;charset=utf-8")
	@ResponseBody
	
	public Object doDelete(){
		PageData pd = this.getPageData();
		
		String dwbh = pd.getString("dwbh");
		//删除单位时验证该单位下是否有人员或下级单位或资产,如果没有则删除
		int ryOrXjdwOrZc = dwbService.validateForRyOrXjdwOrZc(dwbh);
		/*String[] flag = ryOrXjdwOrZc.split(":");
		if("R".equals(flag[0])){
        	return "{success:false,msg:'您选择的编号为："+flag[1]+" 的单位存在人员，不能删除！'}";
		}else if("W".equals(flag[0])){
        	return "{success:false,msg:'您选择的编号为："+flag[1]+" 的单位存在下级单位，不能删除！'}";
        }else if("C".equals(flag[0])&&1==2){
        	return "{success:false,'msg':'您选择的编号为："+flag[1]+" 的单位存在资产，不能删除！'}";
        }else if("U".equals(flag[0])&&1==2){//如果单位下有已处置的资产，则直接禁用
        	int k = dwbService.jydW(flag[1]);
    		if(k>0){
    			return  MessageBox.show(true, MessageBox.SUCCESS_DELETE);
    		}else{
    			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    		}
        }else{
        	int k = dwbService.doDelete(dwbh);
    		if(k>0){
    			return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
    		}else{
    			return MessageBox.show(false, MessageBox.FAIL_DELETE);
    		}
        }*/
		return MessageBox.show(true, MessageBox.SUCCESS_DELETE);
	}
	
	/**
	 * 跳转单位信息编辑页面（增加、修改、查看）
	 * @return
	 */
	@RequestMapping(value="/goEditPage")
	public ModelAndView goEditPage(){
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
		//获取操作类型参数 C增加 U修改 L查看
		String operateType = pd.getString("operateType");
		//单位现状
		List<Map<String, Object>> dwxz = dictService.getDict(Constant.DWXZ);
		//实验室类型
		List<Map<String, Object>> syslx = dictService.getDict(Constant.SYSLX);
		//实验室类别
		List<Map<String, Object>> syslb = dictService.getDict(Constant.SYSLB);
		//实验室级别
		List<Map<String, Object>> sysjb = dictService.getDict(Constant.SYSJB);
		//实验室归属
		List<Map<String, Object>> sysgs = dictService.getDict(Constant.SYSGS);
		//所属学科
		List<Map<String, Object>> ssxk = dictService.getDict(Constant.SSXK);
		//单位办别
		List<Map<String, Object>> dwbb = dictService.getDict(Constant.DWBB);
		mv.addObject("dwxzlist", dwxz);
		mv.addObject("syslxlist", syslx);
		mv.addObject("syslblist", syslb);
		mv.addObject("sysjblist", sysjb);
		mv.addObject("sysgslist", sysgs);
		mv.addObject("ssxklist", ssxk);
		mv.addObject("dwbblist", dwbb);
		if(operateType.equals("C")){
			//左侧树点击传来的单位编号
			String sjdwbh = pd.getString("dwbh");
			
			String bh = AutoKey.createDwbh("GX_SYS_DWB", "dwbh", "6");
			Map<String,String> map = new HashMap<String,String>();
			//查询上级单位
			if(Validate.noNull(sjdwbh)){
				String sjdwmc = dwbService.getDwxx(sjdwbh);
				map.put("SJDWMC", sjdwmc);
			}
			map.put("DWBH", bh);
			map.put("BMH", bh);
			map.put("SYSBZ", "1");
			map.put("SFXY", "1");
			map.put("DWZT", "1");
			map.put("SYSMJ", "0.00");
			mv.addObject("dwb", map);
		}else if(operateType.equals("U")||operateType.equals("L")){
			Map<?, ?>  map = dwbService.getObjectById(pd.getString("dwbh"));
			if(operateType.equals("L")){
				String dwxzmap = dictService.getMcByDm(Constant.DWXZ,map.get("DWXZ")+"");
				mv.addObject("dwxzmap", dwxzmap);
				String syslxmap = dictService.getMcByDm(Constant.SYSLX,map.get("SYSLX")+"");
				mv.addObject("syslxmap", syslxmap);
				String syslbmap = dictService.getMcByDm(Constant.SYSLB,map.get("SYSLB")+"");
				mv.addObject("syslbmap", syslbmap);
				String sysjbmap = dictService.getMcByDm(Constant.SYSJB,map.get("SYSJB")+"");
				mv.addObject("sysjbmap", sysjbmap);
				String sysgsmap = dictService.getMcByDm(Constant.SYSGS,map.get("SYSGS")+"");
				mv.addObject("sysgsmap", sysgsmap);
				String ssxkmap = dictService.getMcByDm(Constant.SSXK,map.get("SSXK")+"");
				mv.addObject("ssxkmap", ssxkmap);
				String dwbbmap = dictService.getMcByDm(Constant.DWBB,map.get("DWBB")+"");
				mv.addObject("dwbbmap", dwbbmap);
				
			}
			iniFile(mv,this.getPageData().getString("dwbh"));//查询领导签字
			mv.addObject("dwb", map);
		}
		
		mv.setViewName("fzgn/jcsz/dwb_edit");
		mv.addObject("operateType",operateType);
		return mv;
	}
	
	/**
	 * 获得领导签字
	 * @param mv
	 * @param guid
	 */
	public void iniFile(ModelAndView mv,String guid) {
		String[] fjxx = fileService.getFjList(guid,"",this.getRequest().getContextPath()).split("#",-1);//照片附件列表
		mv.addObject("fjView",fjxx[0]);
		mv.addObject("fjConfig",fjxx[1]);
	}

	/**
	 * 跳转单位信息查看页面
	 * @return
	 */
	@RequestMapping(value="/goLookPage")
	public ModelAndView goLookPage(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dwxzlist", dictService.getDict(Constant.DWXZ));
		mv.addObject("syslxlist", dictService.getDict(Constant.SYSLX));
		mv.addObject("syslblist", dictService.getDict(Constant.SYSLB));
		mv.addObject("sysjblist", dictService.getDict(Constant.SYSJB));
		mv.addObject("sysgslist", dictService.getDict(Constant.SYSGS));
		mv.addObject("ssxklist", dictService.getDict(Constant.SSXK));
		mv.addObject("dwbblist", dictService.getDict(Constant.DWBB));
		mv.addObject("dwb", dwbService.getObjectById(pd.getString("dwbh")));
		mv.setViewName("window/dwxx/dwb_win");
		return mv;
	}
	/**
	 * 跳转单位信息查看页面
	 * @return
	 */
	@RequestMapping(value="/goTyDwbWin")
	public ModelAndView goTyDwbWin(){
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		mv.addObject("dwxzlist", dictService.getDict(Constant.DWXZ));
		mv.addObject("syslxlist", dictService.getDict(Constant.SYSLX));
		mv.addObject("syslblist", dictService.getDict(Constant.SYSLB));
		mv.addObject("sysjblist", dictService.getDict(Constant.SYSJB));
		mv.addObject("sysgslist", dictService.getDict(Constant.SYSGS));
		mv.addObject("ssxklist", dictService.getDict(Constant.SSXK));
		mv.addObject("dwbblist", dictService.getDict(Constant.DWBB));
		mv.addObject("dwb", dwbService.getObjectById(pd.getString("dwbh")));
		mv.setViewName("window/dwxx/dwb_tywin");
		return mv;
	}

	
	/**
	 * 跳转到批量赋值页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/goPlfuzhiPage")
	@ResponseBody
	public ModelAndView goPlfuzhiPage(){
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		List<Map<String, Object>> dwxz = dictService.getDict(Constant.DWXZ);
		ModelAndView mv = this.getModelAndView();
		mv.setViewName("fzgn/jcsz/dwb_plfz");
		mv.addObject("ids", dwbh);
		mv.addObject("dwxzlist", dwxz);
		return mv;
	}
	
	/**
	 * 批量赋值单位信息
	 * @return
	 * @throws ParseException 
	 */
	@RequestMapping(value="/doPlfuzhi",produces = "text/html;charset=UTF-8")
	@ResponseBody
	
	public Object doPlfuzhi() throws ParseException{
		PageData pd = this.getPageData();
		String ids = pd.getString("ids");
		String ziduan = pd.getString("ziduan");
		Object zdValue = pd.getString("zdValue");
		int k =0;
		k = dwbService.doPlfuzhi(ids,ziduan,zdValue);
		if(k>0){
			return "{success:true,msg:'批量赋值成功！',ids:'"+ids+"'}";
		}else{
			return "{success:false,msg:'批量赋值失败！',ids:'"+ids+"'}";
		}
	}
	
	/**
	 * 跳转 归档范围  list页面
	 * @return
	 */
	@RequestMapping(value="/goGdfwflPage")
	public ModelAndView goGdfwflPage(){
		ModelAndView mv=this.getModelAndView();
		PageData pd = this.getPageData();
		String treeId=pd.getString("treeId");
		String mkbh=pd.getString("mkbh");
		String controlId=pd.getString("controlId");
		String controlName = pd.getString("controlName");
		mv.addObject("controlName", controlName);
		String pname=pd.getString("pname");
		mv.addObject("pname",pname);
		mv.addObject("controlId",controlId);
		mv.addObject("mkbh",mkbh);
		mv.addObject("treeId", treeId);
		mv.setViewName("/fzgn/jcsz/gdfw/gdfw_list");
		return mv;
	}
	/**
	 * 获取归档范围分类树
	 * @param xzz
	 * @time 2017-11-25
	 */
	@RequestMapping(value="/gdfwflTree",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object gdfwflTree(){
		PageData pd = this.getPageData();
		String rootPath = this.getRequest().getContextPath();
		//获取请求参数
		String menu = pd.getString("menu");
		String type = pd.getString("type");
		String dwbh = pd.getString("dwbh");
		if(menu.equals("get-xjdw")){
			String loginrybh = LUser.getRybh();
			if(dwbh.equals("root")){
				if(type.equals("all")){
					loginrybh = SystemSet.AdminRybh();
				}
				return dwbService.getGdfwflNode(pd,loginrybh,rootPath);
			}else{
			    return dwbService.getGdfwflNodeXj(pd,dwbh,rootPath);
			}
		}else{
			return "";
		}
	}
	/**
	 * 获取  归档范围   list页面数据
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="/getGdflPageList",produces = "text/html;charset=UTF-8")
	@ResponseBody
	public Object getGdflPageList() throws Exception{
		PageData pd = this.getPageData();
		String typeid=pd.getString("treeId");
		PageList pageList = new PageList();
		pageList.setSqlText("k.*");
		StringBuffer tablename = new StringBuffer();// 查询字段
		tablename.append(" (select t.typeid,t.typename,t.createtime,t.remark,typenumber,t.gdtime" +
				",(select a.fieldname from da_metadata a where a.id=t.saveid) as saveids,t.typeparentid  ");
		tablename.append("from da_gdfwfl t  ");
		if(Validate.isNull(typeid)){
			tablename.append(" where 1=1 order by t.typenumber)k");
		}else{
			tablename.append(" where 1=1 and t.typeparentid='"+typeid+"' order by t.typenumber )k ");
		}
		pageList.setKeyId("k.typeid");//主键
		pageList.setTableName(tablename.toString());//表名
		pageList = pageService.findPageList(pd,pageList);//引用传递
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
	}
	
	/**
	 * 导出单位信息Excel
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
		//查询数据的sql语句
		String searchJson = pd.getString("searchJson");
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("select k.dwbh,k.mc,k.jc,k.dz,to_char(k.jlrq,'yyyy-mm-dd') jlrq,DECODE(K.SFXY,'0','否','1','是')SFXY,");
		sqltext.append("k.fgld,decode(k.dwzt,'0','禁用','1','正常') as dwzt,k.dwjc,k.mjbz,k.pxxh,k.bmh,");
		sqltext.append("k.bmsx,k.bz,k.sysbz,k.sysjb,k.sysgs,k.syslb,k.sysmj,k.jlnf,k.syslx,");
		sqltext.append("(select mc from gx_sys_dmb m where zl='"+Constant.DWXZ+"' and m.dm = k.dwxz) dwxz,");
		sqltext.append("(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=k.dwld) dwld,");
		sqltext.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL='"+Constant.DWBB+"' AND DM=K.DWBB)AS DWBB,");
		//sqltext.append(" K.FGLD,DECODE(K.DWZT,'0','禁用','1','正常') AS DWZT,K.DWJC,K.MJBZ,K.PXXH,K.BMH,");
		sqltext.append("(select '('||bmh||')'||mc from gx_sys_dwb c where c.dwbh=k.sjdw) sjdw ");
		sqltext.append(" from gx_sys_dwb k where 1=1 ");
		String dwbh = pd.getString("treedwbh");
		if(Validate.noNull(dwbh)){
			//点击左侧单位树的where条件
			sqltext.append(" and k.dwbh in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw start with dwbh='" + dwbh + "')");
		}else{
			//当前登录人管理单位权限
			sqltext.append(pageService.getQxsql(LUser.getRybh(), "K.DWBH", "D"));
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		String id = pd.getString("id");
		if(Validate.noNull(id)){
			sqltext.append(" and k.dwbh in ('"+id.replace(",", "','")+"') ");
		}
		sqltext.append(" order by k.bmh ");
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("bmh");
		m.setShowname("单位号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("mc");
		m.setShowname("单位名称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jc");
		m.setShowname("单位简称");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("dz");
		m.setShowname("单位地址");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("dwxz");
		m.setShowname("单位类别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("jlrq");
		m.setShowname("建立日期");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("dwld");
		m.setShowname("单位负责人");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sjdw");
		m.setShowname("隶属单位");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("dwbb");
		m.setShowname("单位办别");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("sfxy");
		m.setShowname("是否学院");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("dwzt");
		m.setShowname("单位有效标识");
		mlist.add(m);
		m = null;
		
		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 * 导出单位信息Excel   wzd
	 * @return
	 */
	@RequestMapping("/expExcel2")
	@ResponseBody
	public Object stryexpXsInfo() {
		PageData pd = this.getPageData();
		String rybh = LUser.getRybh();//当前登陆者的人员编号
		String s1 = Constant.DWBB;
		String s2 = Constant.DWXZ;
		String dwbh = pd.getString("id");
		String dwbh1 = pd.getString("dwbh");
		String mc= pd.getString("mc");
		String searchValue = pd.getString("searchJson");
		String shortfileurl = "excel\\" + UuidUtil.get32UUID() + ".xls";
		String realfile = this.getRequest().getServletContext().getRealPath("\\")+ "WEB-INF\\file\\" + shortfileurl;
		return this.dwbService.expExcel2(realfile, shortfileurl, dwbh,searchValue,rybh,s1,s2,dwbh1,mc);
	}

	/**
	 * 单位机构设置
	 * 通过部门号(名称)查询单位编号
	 */
	@RequestMapping(value="/getDwbh",produces = "text/html;charset=utf-8")
	@ResponseBody
	public Object getDwbh(){
		String dwmc = this.getRequestParameterValue("DWMC");
		String dwbh = dwbService.findDwbhByDwmc(dwmc);
		return dwbh ;
	}
}
