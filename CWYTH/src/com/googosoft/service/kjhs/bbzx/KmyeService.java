package com.googosoft.service.kjhs.bbzx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.base.PageDao;
import com.googosoft.dao.kjhs.bbzx.KmyeDao;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("kmyeService")
public class KmyeService extends BaseService {
	@Resource(name = "kmyeDao")
	private KmyeDao dao;
	
	@Resource(name="pageDao")
	public PageDao pageDao;

	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		return dao.getMonth();
	}
	
	public List<Map<String, Object>> getPageList(String sql) {
		return dao.getPageList(sql);
	}

	
	//查询模版名称
	
	public String getMbmc() {
		return dao.getMbmc();
	}
	

	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return dao.getKjkmJb();
	}
	
	
	
	
	public List kmbhList(String bh,String sszt,String starttime,String endtime) {
		return dao.kmbhList(bh,sszt,starttime, endtime);
	}
	public List bmbhList(String bh) {
		return dao.bmbhList(bh);
	}
	public List xmbhList(String bh) {
		return dao.xmbhList(bh);
	}
	public String kmmc(String kmbh) {
		return dao.kmmc(kmbh);
	}
	
	
	/**
	 * 获取会计科目设置树
	 */
	public Object getKmszNodezj(PageData pd, String dm,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String icon1 = rootPath+"/static/plugins/ext/resources/images/default/s.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"root".equals(dm)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&kmbh=";
		} else {
			href = href + "?kmbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = dao.getKjkmList(dm);
		Map map = new HashMap();
		if(list.size()>0){
			String kmbh = "",kmmc = "",jb = "";
			String isleaf="";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				kmbh = Validate.isNullToDefault(map.get("KMBH"), "").toString();
                kmmc = Validate.isNullToDefaultString(map.get("KMMC"), "");
                jb = Validate.isNullToDefaultString(map.get("JB"), "");
                children.add(new ExtTreeNode(kmbh, kmmc, true , true, false, href.length() > 0 ? href + kmbh +"&jb="+jb : href, target));
               }
			  		
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		Map<String, List<Map<String,Object>>> map = dao.getResult(params);
		return map;
	}
	
	public List getPrintInfo(String sszt){
		return dao.getPrintInfo(sszt);
	}
	
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		dao.deleteKmyeb();
	}
	/**
	 * 计算合计金额
	 * @return
	 */
	public Map<String,String> computer(){
		return dao.computer();
	}
	
	
	public List getXxList(HttpSession session)  {
		
		return  dao.getXxList(session);
	}
	
	
	public List<Map<String, Object>> daochu( String sszt,String kmbh) {
		return dao.daochu(sszt,kmbh);
	}
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "科目余额表";
		if(Validate.noNull(foo)){
			Title = "科目余额表";
		}
		String[] title = new String[] { "科目编号", "科目名称", "期初余额方向", "期初余额", "本期发生借方", "本期发生贷方", "期末余额方向","期末余额"  };
		Map<String, Object> dataMap = KmyeExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	/**
	 * 列表信息导出（含合并单元格，只适用合并两行的）
	 * @param sql
	 * @param file
	 * @param filedisplay
	 * @param mlist 第一行标题
	 * @param mlist1 第二行标题
	 * @param mlist2 列表数据
	 * @return
	 */
	public String ExpExcel(List list,String file,String filename,List<M_largedata> mlist,List<List<M_largedata>> tlist){
		try {
//			pageDao.ExpExcel(sql,file,filedisplay,mlist,mlist1,mlist2);
			pageDao.ExpExcel(list, file, filename,mlist,tlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	public Object bmexpExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "科目余额表";
		if(Validate.noNull(foo)){
			Title = "科目余额表";
		}
		String[] title = new String[] { "科目编号", "科目名称", "部门名称","期初余额方向", "期初余额", "本期发生借方", "本期发生贷方", "期末余额方向","期末余额"  };
		Map<String, Object> dataMap = KmyeExportExcel.bmexportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	public Object xmexpExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "科目余额表";
		if(Validate.noNull(foo)){
			Title = "科目余额表";
		}
		String[] title = new String[] { "科目编号", "科目名称", "部门名称","项目名称","期初余额方向", "期初余额", "本期发生借方", "本期发生贷方", "期末余额方向","期末余额"  };
		Map<String, Object> dataMap = KmyeExportExcel.xmexportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	/**
	 * 判断是否是末级经济科目
	 * @param jjbh
	 * @return
	 */
	public boolean checkEndJjkm(String jjbh) {
		return dao.checkEndJjkm(jjbh);
	}
	
}
