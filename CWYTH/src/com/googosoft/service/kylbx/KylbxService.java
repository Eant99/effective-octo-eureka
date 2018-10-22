package com.googosoft.service.kylbx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.kylbx.kylbxExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.controller.wsbx.ccyw.ccywsqExportExcel;
import com.googosoft.dao.kylbx.KylbxDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 单位信息service
 * @author master
 */
@Service("kylbxService")
public class KylbxService extends BaseService{
	
	@Resource(name="kylbxDao")
	public KylbxDao kylbxDao;
	
	
	/**
	 * 新增
	 * @param dwb
	 * @return
	 */
	public int doAdd(PageData pd){
		int result = kylbxDao.doAdd(pd);
		//if(result>0){
			//doAddOplog(OplogFlag.ADD,"单位基础信息",dwb.getDwbh());
		//}
		return result;
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectById(PageData pd,String dwbh) {
		return kylbxDao.getObjectById(pd,dwbh);
	}
	public List getObjectDyById(PageData pd,String dwbh) {
		return kylbxDao.getObjectDyById(pd,dwbh);
	}
	public String getflagById(PageData pd,String dwbh) {
		return kylbxDao.getflagById(pd,dwbh);
	}
	/**
	 * 修改
	 * @param dwb
	 * @return
	 */
	public int doUpdate(PageData pd,String dwbh) {
		int result = kylbxDao.doUpdate(pd,dwbh);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"单位基础信息");
		}
		return result;
	}
		
	public boolean goFhPage(PageData pd,String dwbh){
		return kylbxDao.goFhPage(pd,dwbh);
	}
	/**
	 * 删除
	 * @param dwb
	 * @return
	 */
	public int doDelete(String dwbh) {
		int result = kylbxDao.doDelete(dwbh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"单位基础信息",dwbh);
		}
		return result;
	}
	
	public List insertJcsj(String file) {
		return kylbxDao.insertJcsj(file);
	}
	//
	public List getPrintInfoByIds(PageData pd) {
		return kylbxDao.getPrintInfoByIds(pd);
	}
	public Map<?,?> getSinglePrintInfoByIds(String guid) {
		return kylbxDao.getSinglePrintInfoByIds(guid);
	}
	public Map<?,?> getPrintInfoById(String guid) {
		return kylbxDao.getPrintInfoById(guid);
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getSqsp(String guid) {
		return kylbxDao.getSqsp(guid);
	}
	//查询项目信息list
	public List getxmxxlist(String rcbxguid){
		return kylbxDao.getxmxxlist(rcbxguid);
	}
	public Object getYsLx(PageData pd,String rootPath) {
		String leaf = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		boolean bool = true;
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		Map map = new HashMap();
		//List list = treeDao.getSchoolTree();
		List list = kylbxDao.bmysMenu();
		if(list.size()>0){
			String dm = "",mc = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
				mc = Validate.isNullToDefault(map.get("MC"), "").toString();
				children.add(new ExtTreeNode(dm, mc, bool , true, false,href+dm, target,leaf));
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
	/**
	 * 获取会计科目设置树
	 */
	public Object getKmszNodezj(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String icon1 = rootPath+"/static/plugins/ext/resources/images/default/s.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		if(!"1".equals(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&dm=";
		} else {
			href = href + "?dm=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list=null;
		
		if(jb.length()>2) {
			
			list=kylbxDao.kmszMenuzj1(jb);

		}else {
			 list = kylbxDao.kmszMenuzj(jb);
		}
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			String isleaf="";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				System.out.println(map);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                isleaf=Validate.isNullToDefault(map.get("ISLEAF"), "").toString();           
                if(isleaf.equals("1")) {
    	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target));
                }else {   
    				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
                }
              
               }
			  		
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	public Object expExcelwdbx2(String realfile, String shortfileurl,String guid, String searchValue,String sql) {
		List<Map<String, Object>> dwList = this.kylbxDao.getList(searchValue,guid,sql);
		String Title = "我的报销业务";
		String[] title = new String[] { "序号", "审核状态","单据编号", "报销人", "所在部门", "报销总结额（元）" ,"报销日期", "类型" };
		Map<String, Object> dataMap = WdbxywExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	
	/**
	 * 导出教师信息Excel   wzd
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue, String rybh, String sql) {
		List<Map<String, Object>> dwList = this.kylbxDao.getJsList(guid,searchValue,rybh,sql);
		String Title = "日常报销汇总";
		String[] title = new String[] { "序号", "审核状态", "单据编号","申请人", "所在部门","报销总金额（元）","报销日期","类型" };
		Map<String, Object> dataMap = kylbxExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	
}
