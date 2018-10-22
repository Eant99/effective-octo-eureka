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
import com.googosoft.dao.kjhs.bbzx.XmMxz1Dao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("xmmxz1Service")
public class XmMxz1Service extends BaseService {
	@Resource(name = "xmmxz1Dao")
	private XmMxz1Dao xmmxz1Dao;

	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		return xmmxz1Dao.getMonth();
	}
	/**
	 * 获得页面数据
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd){
		return xmmxz1Dao.getPageList(kmbh,kjzd,treesearch,pd);
	}
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd,String sszt){
		return xmmxz1Dao.getPageList(kmbh,kjzd,treesearch,pd,sszt);
	}
	
	
	public String kmmc(String kmbh) {
		return xmmxz1Dao.kmmc(kmbh);
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return xmmxz1Dao.getKjkmJb();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		xmmxz1Dao.deleteKmyeb();
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt,String kjzd,String kmbh,PageData pd) {
		return xmmxz1Dao.getResult(list, sszt,kjzd,kmbh,pd);
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResultnew(String xmbh ,String sszt,String kmbh,String year,String StartMonth,String endMonth,PageData pd) {
		return xmmxz1Dao.getResultnew(xmbh,sszt,kmbh,year,StartMonth,endMonth,pd);
	}
	public boolean getResultNew(String xmbh ,String sszt,String year,String StartMonth,String endMonth,PageData pd) {
		return xmmxz1Dao.getResultNew(xmbh,sszt,year,StartMonth,endMonth,pd);
	}
	/**
	 * 导出
	 * 
	 * @param params
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "项目明细账";
		if(Validate.noNull(foo)){
			Title = "明细账";
		}
		String[] title = new String[] { "凭证日期","凭证类型","凭证号", "摘要","项目名称", "会计科目","经济科目", "部门编号", "项目编号", "借方金额","贷方金额","方向","余额"  };
		Map<String, Object> dataMap = QtmxzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	public List kmbhList(String kmbh) {
		return xmmxz1Dao.kmbhList(kmbh);
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
		List dList=xmmxz1Dao.PowerModels(loginrybh);
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
                	 List LRybs = xmmxz1Dao.GetModels(dwbh);
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
	/**
	 * 获取单位项目树
	 * @param
	 * @return
	 */
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
		List dList =  xmmxz1Dao.GetDwModels(sjdw);
		String dwbh, bmh, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				dwbh = map.get("DWBH").toString();
				bmh = map.get("BMH").toString();
				mc = map.get("MC").toString();
				List dwcount = xmmxz1Dao.GetModels(dwbh);
				xjcount = Integer.parseInt(map.get("XJCOUNT").toString()) + dwcount.size();
				if (xjcount <= 0) {
					dwcount = xmmxz1Dao.GetModels(dwbh);
					xjcount = dwcount.size();
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target,rootPath+ "/static/plugins/ext/resources/images/default/tree/folder.gif"));
				} else {
					children.add(new ExtTreeNode("D" + dwbh, "(" + bmh + ")" + mc, xjcount > 0 ? false : true, true,
							false, "", Target));
				}
			}
		}
		dList = xmmxz1Dao.GetModels(sjdw);
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

