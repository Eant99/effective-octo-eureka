package com.googosoft.controller.bzjgl;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.MessageBox;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.controller.base.BaseController;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.DictService;
import com.googosoft.service.base.PageService;
import com.googosoft.service.fzgn.jcsz.XsxxService;
import com.googosoft.service.ysgl.grjfsz.GrjfszService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Controller
@RequestMapping(value ="/xsbzjtj")
public class XsbzjtjController extends BaseController {
	
	@Resource(name="pageService")
	private PageService pageService;//单例
	
	@Resource(name="xsxxService")
	private XsxxService xsxxService;
	
	@Resource(name="grjfszService")
	private GrjfszService grjfszService;//单例

	@Resource(name="dictService")
	DictService dictService;
	
	/**
	 * 跳转到学生补助金统计信息列表页面
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/goListPage")
	public ModelAndView goListPage() {
		ModelAndView mv = this.getModelAndView();
		PageData pd = this.getPageData();
		String dwbh = pd.getString("dwbh");
		String qc = pd.getString("qc");
		String bmh = pd.getString("bmh");
		mv.setViewName("bzjgl/xsbzjtj/xsbzjtj_list");
		mv.addObject("dwbh",dwbh);
		mv.addObject("bmh",bmh);
		mv.addObject("qc",qc);
		return mv;

	}
	
	/**
	 * 获得学生补助金统计信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getPageList")
	@ResponseBody
	public Object getPageList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String dwbh = pd.getString("treedwbh");
		String sffh = pd.getString("search[value]");
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( SELECT distinct M.XH,X.XM,(select count(XH) from CW_XSBZMXB t WHERE t.xh=x.xh) as bzcs,");
		tablename.append("(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.bmh=(select yxbh from CW_XJXXB b where b.xh = x.guid))AS SZYX,");
		tablename.append("(SELECT ZYMC FROM CW_ZYXXB Z WHERE Z.ZYBH = (select zybh from CW_XJXXB b where b.xh=x.guid))AS SZZY,");
		tablename.append(" TRIM(TO_CHAR(nvl((select sum(ffje) from CW_XSBZMXB t where t.xh=x.xh),0),'99999999999990.99')) as FFJE FROM CW_XSBZZB Z LEFT JOIN CW_XSBZMXB M ON M.FABH=Z.FABH LEFT JOIN CW_XSXXB X ON M.XH=X.XH) K");
//		if(Validate.noNull(dwbh)){
//			tablename.append("AND g.bmbh=(selecT BMH FROM GX_SYS_DWB B WHERE B.DWBH='"+dwbh+"') ");//根据左侧树查询右侧列表
//		}
//		if(Validate.isNull(sffh)){
//			tablename.append(" and g.sffh = '0' ");
//		}
//		tablename.append(")k");

		pageList.setSqlText(sqltext.toString());
		//设置表名
		pageList.setTableName(tablename.toString());
		//设置表主键名
		pageList.setKeyId("xh");//主键
		//设置WHERE条件
		pageList.setStrWhere(""); 
		pageList.setHj1("");//合计
	    pageList = pageService.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo.toJson();
		
	}
	/**
	 * 获得学生补助金统计信息下的各个方案详情
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/getBzmxList")
	@ResponseBody
	public Object getBzmxList() throws Exception {		
		PageData pd = this.getPageData();
		StringBuffer sqltext = new StringBuffer();//查询字段
		StringBuffer tablename = new StringBuffer();
		String xh = pd.getString("xh");
		PageList pageList = new PageList();
		sqltext.append(" * ");
		tablename.append(" ( SELECT M.GUID,M.XH,Z.FABH,Z.FAMC,(SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=Z.XMBH)AS XMBH,(select '('||t.rybh||')'||t.xm from GX_SYS_RYB T where T.RYBH=Z.JBR) JBR,(SELECT '('||bzbh||')'||bzmc  from cw_bzbzszb t where t.sfqy='1' and t.bzbh=m.bzbh ) AS BZBH,M.YHMC,M.YHKH,Z.BZ,Z.ZY,(SELECT MC FROM GX_SYS_DMB WHERE ZL = 'zffs' AND DM=Z.FFFS) AS FFFS,");
		tablename.append("(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.bmh=(select yxbh from CW_XJXXB b where b.xh = x.guid))AS SZYX,");
		tablename.append("(SELECT ZYMC FROM CW_ZYXXB Z WHERE Z.ZYBH = (select zybh from CW_XJXXB b where b.xh=x.guid))AS SZZY,");
		tablename.append(" TRIM(TO_CHAR(nvl(M.FFJE,0),'99999999999990.99')) as FFJE,TO_CHAR(Z.FFSJ,'YYYY-MM-DD HH24:MI:SS') AS FFSJ FROM CW_XSBZZB Z LEFT JOIN CW_XSBZMXB M ON M.FABH=Z.FABH LEFT JOIN CW_XSXXB X ON M.XH=X.XH");
		if(Validate.noNull(xh)){
			tablename.append(" where m.xh="+xh);
		}
		tablename.append( ") K");
//		if(Validate.noNull(dwbh)){
//			tablename.append("AND g.bmbh=(selecT BMH FROM GX_SYS_DWB B WHERE B.DWBH='"+dwbh+"') ");//根据左侧树查询右侧列表
//		}
//		if(Validate.isNull(sffh)){
//			tablename.append(" and g.sffh = '0' ");
//		}
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
	/**
	 * 导出学生补助金统计信息
	 * @return
	 * @throws Exception
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
		sqltext.append(" SELECT distinct M.XH,X.XM,(select count(XH) from CW_XSBZMXB t WHERE t.xh=x.xh) as bzcs,");
		sqltext.append("(select '('||d.bmh||')'||to_char(d.mc) from GX_SYS_DWB d where d.bmh=(select yxbh from CW_XJXXB b where b.xh = x.guid))AS SZYX,");
		sqltext.append("(SELECT ZYMC FROM CW_ZYXXB Z WHERE Z.ZYBH = (select zybh from CW_XJXXB b where b.xh=x.guid))AS SZZY,");
		sqltext.append(" TRIM(TO_CHAR(nvl((select sum(ffje) from CW_XSBZMXB t where t.xh=x.xh),0),'99999999999990.99')) as FFJE FROM CW_XSBZZB Z LEFT JOIN CW_XSBZMXB M ON M.FABH=Z.FABH LEFT JOIN CW_XSXXB X ON M.XH=X.XH ");
		String xhList = pd.getString("xhList");
		if(Validate.noNull(xhList)){
			sqltext.append(" where M.XH in ('"+xhList+"') ");
		}
		sqltext.append(ToSqlUtil.jsonToSql(searchJson));
		//部门号 单位名称  单位简称   单位地址 单位性质  成立日期 单位领导 上级单位 单位状态
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		m.setName("XH");
		m.setShowname("学号");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("XM");
		m.setShowname("姓名");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZYX");
		m.setShowname("所属院系");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("SZZY");
		m.setShowname("专业");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("FFJE");
		m.setShowname("发放总金额");
		mlist.add(m);
		m = null;
		
		m = new M_largedata();
		m.setName("BZCS");
		m.setShowname("发放次数");
		mlist.add(m);
		m = null;

		//导出方法
		pageService.ExpExcel(sqltext.toString(),realfile,filedisplay,mlist);
		return "{\"url\":\"excel\\\\"+file+".xls\"}";
	}
	
	/**
	 *  导入上传
	 */
	@RequestMapping(value="/uploadt")
	public ModelAndView Uploadt(MultipartFile imageFile) throws IllegalStateException, IOException{
		PageData pd = this.getPageData();
		ModelAndView mv = this.getModelAndView();
    	String pictureFile_name =  imageFile.getOriginalFilename();
		String newFileName = this.get32UUID()+pictureFile_name.substring(pictureFile_name.lastIndexOf("."));
		String realPath = this.getRequest().getSession().getServletContext().getRealPath("/").replaceAll("\\\\", "/");
		//上传文件
		String file = realPath+"WEB-INF/file/excel/"+newFileName;
		File uploadPic = new File(file);
		if(!uploadPic.exists()){
			uploadPic.mkdirs();
		}
		//向磁盘写文件
		imageFile.transferTo(uploadPic);
		List listbt = new ArrayList();
		listbt = grjfszService.insertJcsj(file);
		mv.addObject("listbt", listbt);
		mv.addObject("file", file);
		String pname = pd.getString("pname");
		System.out.println("========"+pname);
		mv.addObject("bool","true");
		mv.addObject("pname",pname);
 		mv.setViewName("fzgn/jcsz/jsxxsz/txl_imp");
		return mv;
	}
}
