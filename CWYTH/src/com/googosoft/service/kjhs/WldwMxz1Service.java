package com.googosoft.service.kjhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.MxzExportExcel;
import com.googosoft.controller.cwbb.expExcel.QtmxzExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.kjhs.WldwMxz1Dao;
import com.googosoft.dao.kjhs.bbzx.MxzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("wldwmxz1service")
public class WldwMxz1Service extends BaseService {
	@Resource(name = "wldwmxz1dao")
	private WldwMxz1Dao wldwmxz1dao;

	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		return wldwmxz1dao.getMonth();
	}
	/**
	 * 获得页面数据信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd){
		return wldwmxz1dao.getPageList(kmbh,kjzd,treesearch,pd);
	}
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public String  getWldwmc( String wlbh) {
		return wldwmxz1dao.getWldwmc(wlbh);
	}
//	public String kmmc(String kmbh) {
//		return wldwmxz1dao.kmmc(kmbh);
//	}
	public List<Map<String, Object>> getJjPageList(String kmbh,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return wldwmxz1dao.getJjPageList(kmbh,kjkm,pznd,startMonth,endMonth,pzzt, bmbh, xmbh, jfjel, jfjeh, zy);
	}
	public List<Map<String, Object>> getGrPageList(String ryid,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return wldwmxz1dao.getGrPageList(ryid,kjkm,pznd,startMonth,endMonth,pzzt, bmbh, xmbh, jfjel, jfjeh, zy);
	}
	public List<Map<String, Object>> getXmPageList(String treexmbh,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		return wldwmxz1dao.getXmPageList(treexmbh,kjkm,pznd,startMonth,endMonth,pzzt,bmbh,xmbh,jfjel,jfjeh,zy);
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return wldwmxz1dao.getKjkmJb();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		wldwmxz1dao.deleteKmyeb();
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt,String kjzd,String kmbh,PageData pd,String bz) {
		return wldwmxz1dao.getResult(list, sszt,kjzd,kmbh,pd,bz);
	}
	/**
	 * 由余额表到明细账跳转时判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResultnew(String wlbh, String sszt, String kmbh,
			String year, String StartMonth, String endMonth, PageData pd, String bz) {
		return wldwmxz1dao.getResultnew(wlbh, sszt,kmbh,year,StartMonth,endMonth,pd,bz);
	}
	
	/**
	 * 导出
	 * 
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "明细账";
		if(Validate.noNull(foo)){
			Title = foo;
		}
		String[] title = new String[] { "凭证日期","凭证类型","凭证号", "摘要","往来单位名称","会计科目", "经济科目", "部门编号", "项目编号", "借方金额","贷方金额","方向","余额" };
		if(foo.equals("项目明细账")) {
			title = new String[] { "凭证日期","凭证类型","凭证号", "摘要","部门名称","会计科目", "经济科目", "部门编号", "项目编号", "借方金额","贷方金额","方向","余额" };
		}
		Map<String, Object> dataMap = QtmxzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	
	public List kmbhList(String kmbh) {
		return wldwmxz1dao.kmbhList(kmbh);
	}
	
	/**
	 * 获取部门下的项目（部门项目树）
	 * @param 
	 * @return
	 */
	public Object getPowerNode(PageData pd, String loginrybh) {
		String Target=pd.getString("target");
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=wldwmxz1dao.PowerModels(loginrybh);
		Map map=new HashMap();
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&rybh=";
		} else {
			Href = Href + "?rybh=";
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
                	 List LRybs = wldwmxz1dao.GetModels(dwbh);
                     xjcount =LRybs.size();
				     children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true, xjcount > 0 ? true : false, "", Target));
                }else{
                	children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true, xjcount > 0 ? true : false, "", Target));
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
		List dList =  wldwmxz1dao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				List dwcount = wldwmxz1dao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = wldwmxz1dao.GetModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target));
				}
			}
		}
		dList = wldwmxz1dao.GetModels(sjdw);
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				children.add(new ExtTreeNode(map.get("XMBH").toString(),
						"(" + map.get("XMBH").toString() + ")" + map.get("XMMC").toString(), true, false,
						Href.length() > 0 ? Href + map.get("XMBH").toString()+"&bmbh="+ map.get("BMBH").toString(): Href, Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/vector.gif"));
			}
		}

		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
}
