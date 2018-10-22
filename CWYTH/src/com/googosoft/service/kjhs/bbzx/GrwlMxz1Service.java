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
import com.googosoft.controller.cwbb.expExcel.QtmxzExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.kjhs.WldwMxz1Dao;
import com.googosoft.dao.kjhs.bbzx.GrwlMxz1Dao;
import com.googosoft.dao.kjhs.bbzx.MxzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("grwlmxz1Service")
public class GrwlMxz1Service extends BaseService {
	@Resource(name = "grwlmxz1Dao")
	private GrwlMxz1Dao grwlmxz1Dao;

	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		return grwlmxz1Dao.getMonth();
	}
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public String  getWlrymc( String dm) {
		return grwlmxz1Dao.getWlrymc(dm);
	}
	/**
	 * 获得页面数据信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd){
		return grwlmxz1Dao.getPageList(kmbh,kjzd,treesearch,pd);
	}
	
	public String kmmc(String kmbh) {
		return grwlmxz1Dao.kmmc(kmbh);
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return grwlmxz1Dao.getKjkmJb();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		grwlmxz1Dao.deleteKmyeb();
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt,String kjzd,String kmbh,PageData pd) {
		return grwlmxz1Dao.getResult(list, sszt,kjzd,kmbh,pd);
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResultNew(String rybh, String sszt, String kmbh,
			String year, String startMonth, String endMonth, PageData pd) {
		return grwlmxz1Dao.getResultNew(rybh, sszt,kmbh,year,startMonth,endMonth,pd);
	}
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "个人往来明细账";
		if(Validate.noNull(foo)){
			Title = "明细账";
		}
		String[] title = new String[] { "凭证日期","凭证类型","凭证号", "摘要","往来人员姓名","会计科目","经济科目", "部门编号", "项目编号", "借方金额","贷方金额","方向","余额"  };
		Map<String, Object> dataMap = QtmxzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	public List kmbhList(String kmbh) {
		return grwlmxz1Dao.kmbhList(kmbh);
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
		List dList=grwlmxz1Dao.PowerModels(loginrybh);
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
                	 List LRybs = grwlmxz1Dao.GetModels(dwbh);
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
		List dList =  grwlmxz1Dao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				List dwcount = grwlmxz1Dao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = grwlmxz1Dao.GetModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target));
				}
			}
		}
		dList = grwlmxz1Dao.GetModels(sjdw);
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
