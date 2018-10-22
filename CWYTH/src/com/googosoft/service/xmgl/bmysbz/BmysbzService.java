package com.googosoft.service.xmgl.bmysbz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.SendHttpUtil;
import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.ysgl.bmysbz.BmysbzDao;
import com.googosoft.service.ysgl.bmysbz.CW_JBZCYSB;
import com.googosoft.service.ysgl.bmysbz.CW_JBZCYSMXB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.service.ysgl.bmysbz.CW_XMZCYSB;
import com.googosoft.service.ysgl.bmysbz.CW_XMZCYSMXB;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("bmysbzService")
public class BmysbzService {
	@Resource(name="bmysbzDao")
	
	public BmysbzDao bmysbzDao;
	
	/**
	 * 预算类型树
	 * @param pd
	 * @param rootPath
	 * @return
	 */
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
		List list = bmysbzDao.bmysMenu();
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
	 * 收入预算增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd(CW_SRYSB srysb) {
		
			int i = bmysbzDao.doAdd(srysb);
			
			return i;
		
	}
	/**
	 * 基本支出预算增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddJbzcys(CW_JBZCYSB jbzcys) {
		
			int i = bmysbzDao.doAddJbzcys(jbzcys);
			
			return i;
		
	}
	/**
	 * 项目支出预算增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddXmzcys(CW_XMZCYSB xmzcysb) {
		
			int i = bmysbzDao.doAddXmzcys(xmzcysb);
			
			return i;
		
	}
	/**
	 * 收入预算编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEdit(CW_SRYSB srysb) {
		
			int i = bmysbzDao.doedit(srysb);
			
			return i;
		
	}
	/**
	 * 基本支出预算编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEditJbzcys(CW_JBZCYSB jbzcysb) {
		
			int i = bmysbzDao.doeditJbzcys(jbzcysb);
			
			return i;
		
	}
	/**
	 * 项目支出预算编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEditXmzcys(CW_XMZCYSB xmzcysb) {
		
			int i = bmysbzDao.doeditXmzcys(xmzcysb);
			
			return i;
		
	}
	/**
	 * 收入预算明细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd1(CW_SRYSMXB srysmxb) {
		
		int i = bmysbzDao.doAdd1(srysmxb);
		
		return i;
	
}
	/**
	 * 基本支出预算明细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddJbzcysmx(CW_JBZCYSMXB jbzcysmx) {
		
		int i = bmysbzDao.doAddJbzcysmx(jbzcysmx);
		
		return i;
	
}
	/**
	 * 基本支出预算明细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public List<Map<String, Object>> getjbzcysmx(String dwbh) {
		return bmysbzDao.getJbzcysmx(dwbh);
	}
	/**
	 * 项目支出预算明细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddXmzcysmx(CW_XMZCYSMXB xmzcysmx) {
		
		int i = bmysbzDao.doAddXmzcysmx(xmzcysmx);
		
		return i;
	
}
	/**
	 * 收入预算明细编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEditSrysmx(CW_SRYSMXB srysmxb) {
		
		int i = bmysbzDao.doEditSrysmx(srysmxb);
		
		return i;
	
}
	/**
	 * 项目支出明细编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEditXmzcmx(CW_XMZCYSMXB xmzcysmx) {
		
		int i = bmysbzDao.doEditXmzcysmx(xmzcysmx);
		
		return i;
	
}
	/**
	 * 收入预算明细编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEditSrysmx(CW_JBZCYSMXB jbzcysmx) {
		
		int i = bmysbzDao.doEditJbzcysmx(jbzcysmx);
		
		return i;
	
}
	/**
	 * 收入预算删除
	 * @param 
	 * @return
	 */
	public int doDelete(String guid,CW_SRYSB srysb) {
		int result = bmysbzDao.doDelete(guid,srysb);
		return result;
	}
	/**
	 * 收入预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteSryxmx(String guid,CW_SRYSMXB srysmxb) {
		int result = bmysbzDao.doDeleteSrysmx(guid,srysmxb);
		return result;
	}
	/**
	 * 基本支出预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteJbzcysxmx(String jbzcbh,CW_JBZCYSMXB jbzcysmx) {
		// = bmysbzDao.doDeleteJbzcysmxguid(jbzcbh,jbzcysmx);
		 int result =bmysbzDao.doDeleteJbzcysmxbh(jbzcbh, jbzcysmx);
		return result;
	}
	/**
	 * 项目支出预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteXmzcysxmx(String guid,CW_XMZCYSMXB xmzcysmx) {
		int result = bmysbzDao.doDeleteXmzcysmxguid(guid,xmzcysmx);
		return result;
	}
	/**
	 * 收入预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteSryxmxys(String srysbh,CW_SRYSMXB srysmxb) {
		int result = bmysbzDao.doDeleteSrysmxbh(srysbh,srysmxb);
		return result;
	}
	/**
	 * 基本支出预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteJbzcysmx(String guid,CW_JBZCYSMXB jbzcysmx) {
		int result = bmysbzDao.doDeleteJbzcysmx(guid,jbzcysmx);
		return result;
	}
	/**
	 * 基本支出预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeleteJbzcysmxjb(String jbzcbh,CW_JBZCYSMXB jbzcysmx) {
		int result = bmysbzDao.doDeleteJbzcysmxbh(jbzcbh,jbzcysmx);
		return result;
	}
	/**
	 * 项目支出预算删除
	 * @param 
	 * @return
	 */
	public int doDeletexmzc(String guid,CW_XMZCYSB xmzcys) {
		int result = bmysbzDao.doDeletexmzc(guid, xmzcys);
		return result;
	}
	/**
	 * 收入预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeletesrysmx(String guid,CW_SRYSMXB srysmxb) {
		int result = bmysbzDao.doDeletesrysmx(guid,srysmxb);
		return result;
	}
	/**
	 * 基本支出预算删除
	 * @param 
	 * @return
	 */
	public int doDeletejbzcys(String guid,CW_JBZCYSB jbzcys) {
		int result = bmysbzDao.doDeleteJbzcys(guid,jbzcys);
		return result;
	
	}
	/**
	 * 项目支出预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeletejXmzcysmx(String guid,CW_XMZCYSMXB xmzcysmx) {
		int result = bmysbzDao.doDeleteXmzcysmx(guid,xmzcysmx);
		return result;
	
	}
	/**
	 * 项目支出预算明细删除
	 * @param 
	 * @return
	 */
	public int doDeletejXmzcysmxxm(String xmzcbh,CW_XMZCYSMXB xmzcysmx) {
		int result = bmysbzDao.doDeleteXmzcysmxbh(xmzcbh,xmzcysmx);
		return result;
	
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid) {
		return bmysbzDao.getObjectById(guid);
	}
	/**
	 * 获取基本支出预算表
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getJbzcysById(String guid) {
		return bmysbzDao.getJbzcbById(guid);
	}
	/**
	 * 获取项目支出预算表
	 * @param
	 * @return
	 */
	public Map<?, ?> getXmzcysById(String guid) {
		return bmysbzDao.getXmzcysById(guid);
	}
	/**
	 * 获取项目预算明细实体类
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getXmysmxById1(String guid) {
		return bmysbzDao.getXmzcysmxId(guid);
	}
	/**
	 * 检查收入预算申报部门是否重复
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getSrysSbbm(String sbbm) {
		return bmysbzDao.getSrysSbbm(sbbm);
	}/**
	 * 检查收入预算申报部门是否重复(编辑)
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getSrysSbbm1(String guid,String sbbm) {
		return bmysbzDao.getSrysSbbm1(guid,sbbm);
	}
	/**
	 * 检查基本支出预算申报部门是否重复
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysSbbm(String sbbm) {
		return bmysbzDao.getJbzcysSbbm(sbbm);
	}
	/**
	 * 检查基本支出预算编辑页面申报部门是否重复
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysSbbm1(String guid,String sbbm) {
		return bmysbzDao.getJbzcysSbbm1(guid,sbbm);
	}
	/**
	 * 项目支出部门编号是否重复
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getXmzcSbbm(String sbbm) {
		return	bmysbzDao.getXmzcSbbm(sbbm);
		 
	}
	/**
	 * 项目支出部门编号是否重复(编辑)
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getXmzcSbbm1(String guid,String sbbm) {
		return	bmysbzDao.getXmzcSbbm1(guid,sbbm);
		 
	}
	
	
	/**
	 * 获取部门预算编制明细实体类
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getObjectById1(String guid) {
		return bmysbzDao.getObjectById1(guid);
	}
	
	/**
	 * 获取基本支出预算表明细实体类
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysmxById1(String guid) {
		return bmysbzDao.getJbzcysmxId(guid);
	}

}
