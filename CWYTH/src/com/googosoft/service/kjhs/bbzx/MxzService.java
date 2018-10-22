package com.googosoft.service.kjhs.bbzx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.MxzExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.kjhs.bbzx.MxzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("mxzService")
public class MxzService extends BaseService {
	@Resource(name = "mxzDao")
	private MxzDao dao;

	/**
	 * 查询月份
	 * 
	 * @return
	 */
	
	
	public List<Map<String, Object>> getMonth() {
		return dao.getMonth();
	}

	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd){
		return dao.getPageList(kmbh,kjzd,treesearch,pd);
	}
	
	public String kmmc(String kmbh) {
		return dao.kmmc(kmbh);
	}
	public List<Map<String, Object>> getJjPageList(String kmbh,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return dao.getJjPageList(kmbh,kjkm,pznd,startMonth,endMonth,pzzt, bmbh, xmbh, jfjel, jfjeh, zy);
	}
	public List<Map<String, Object>> getGrPageList(String ryid,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return dao.getGrPageList(ryid,kjkm,pznd,startMonth,endMonth,pzzt, bmbh, xmbh, jfjel, jfjeh, zy);
	}
	public List<Map<String, Object>> getXmPageList(String treexmbh,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return dao.getXmPageList(treexmbh,kjkm,pznd,startMonth,endMonth,pzzt,bmbh,xmbh,jfjel,jfjeh,zy);
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return dao.getKjkmJb();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		dao.deleteKmyeb();
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt,String kjzd,String kmbh, PageData pd) {
		return dao.getResult(list, sszt,kjzd,kmbh,pd);
	}
	
	
	
	public List<Map<String, Object>> daochu( String sszt,String kmbh) {
		return dao.daochu(sszt,kmbh);
	}
	
	
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "明细账";
		if(Validate.noNull(foo)){
			Title = "明细账";
		}
		String[] title = new String[] { "凭证日期","凭证类型","凭证号", "摘要", "经济分类", "部门编号", "项目编号", "借方金额","贷方金额","方向","余额"  };
		Map<String, Object> dataMap = MxzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
	
	public List kmbhList(String kmbh) {
		return dao.kmbhList(kmbh);
	}
	
	/**
	 * 获取部门下的项目（部门项目树）
	 * @param 
	 * @return
	 */
	public Object getPowerNode(PageData pd, String loginrybh,String Href,String rootPath) {
		String Target=pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=dao.PowerModels(loginrybh);
		Map map=new HashMap();
//		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&bmh=";
		} else {
			Href = Href + "?bmh=";
		}
		if(dList.size()>0){
			String dwbh="",bmh="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				dwbh = Validate.isNullToDefault(map.get("DWBH"), "")+"";
                bmh = map.get("BMH")+"";
                mc = map.get("MC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0) {
               	 List LRybs = dao.GetModels(dwbh);
                    xjcount =LRybs.size();
				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, true, false, false, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"(" + bmh + ")"+mc+"&bmh="+map.get("BMH") : Href, Target));
	               }else{
	            	 children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, true, true, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc="+"("+ bmh +")"+mc+"&bmh="+map.get("BMH") : Href, Target));
	               }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	public Object getDwRyNode(PageData pd, String sjdw,String rootPath) {
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&xmbh=";
		} else {
			Href = Href + "?xmbh=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  dao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				List dwcount = dao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = dao.GetModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, false, false, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc=123&bmh="+map.get("BMH"):Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, false, false, false, Href.length() > 0 ? Href + map.get("DWBH").toString()+"&qc=123&bmh="+map.get("BMH") : Href, Target));
				}
			}
		}
		dList = dao.GetModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				String xmbhbmbh =  map.get("XMBH").toString()+","+map.get("BMBH").toString();
				children.add(new ExtTreeNode(map.get("XMBH").toString(),
						"(" + map.get("XMBH").toString() + ")" + map.get("XMMC").toString(), true, false,
						Href.length() > 0 ? Href + map.get("XMBH").toString()+"&bmbh="+ map.get("BMBH").toString()+"&xmbhbmbh="+xmbhbmbh: Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/vector.gif"));
			}
		}

		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResultNew(String sszt, String kmbh, String year,
			String startMonth, String endMonth, PageData pd) {
		return dao.getResultNew(sszt,kmbh,year,startMonth,endMonth,pd);
	}
	
}
