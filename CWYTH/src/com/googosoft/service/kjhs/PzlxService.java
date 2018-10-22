package com.googosoft.service.kjhs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.kjhs.PzlxDao;
import com.googosoft.pojo.kjhs.CW_PZKMDZB;
import com.googosoft.pojo.kjhs.CW_PZLXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWMXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


@Service("pzlxService")
public class PzlxService extends BaseService{
	@Resource(name="pzlxDao")
	public PzlxDao pzlxDao;

	public int delete(PageData pd) {
		return pzlxDao.delete(pd);
	}
	/**
	 * 验证类型名称是否重复
	 */
	public boolean getObjectById(String lxmc,String guid) {
		return pzlxDao.getObjectById(guid,lxmc);
	}
	/**
	 * 验证凭证字是否重复
	 */
	public boolean getObjectByPzz(String guid, String pzz) {
		return pzlxDao.getObjectByPzz(guid, pzz);
	}
	/**
	 * 凭证类型增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd(CW_PZLXB pzlxb) {
		
			int i = pzlxDao.doAdd(pzlxb);
			
			return i;
		
	}
	/**
	 * 凭证科目增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAdd1(CW_PZKMDZB pzkmdzb) {
		
		int i = pzlxDao.doAdd1(pzkmdzb);
		
		return i;	
    }
	  /**
   	 * 获取凭证类型编辑信息
   	 * @param
   	 * @return
   	 */
   	public Map<?, ?> getpzlxById(String guid) {
   		return pzlxDao.getpzlxById(guid);
   	}
	/**
	 * 获取凭证类型科目对照表借方编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbJfById(String guid) {
		return pzlxDao.getpzkmdzbJfById(guid);
	}
	/**
	 * 获取凭证类型科目对照表贷方编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbDfById(String guid) {
		return pzlxDao.getpzkmdzbDfById(guid);
	}
	/**
	 * 获取凭证类型科目对照表凭证必有编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbPzbyById(String guid) {
		return pzlxDao.getpzkmdzbPzbyById(guid);
	}
	/**
	 * 获取凭证类型科目对照表凭证必无编辑信息
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getpzkmdzbPzbwById(String guid) {
		return pzlxDao.getpzkmdzbPzbwById(guid);
	}
	/**
	 *凭证类型编辑操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doEdit(CW_PZLXB pzlxb) {
		
			int i = pzlxDao.doEdit(pzlxb);
			
			return i;
		
	}
	/**
	 * 凭证科目对照信息删除
	 * @param 
	 * @return
	 */
	public int doDeletePzkmdzb(String guid,CW_PZKMDZB pzkmdzb) {
		int result = pzlxDao.doDeletePzkmdzb(guid,pzkmdzb);
		return result;
	}
	/**
	 * 凭证科目编辑删除
	 * @param 
	 * @return
	 */
	public int doDeletePzkm(String pzlxm) {
		int result = pzlxDao.doDeletePzkm(pzlxm);
		return result;
	}
	/**
	 * 凭证类型删除
	 * @param 
	 * @return
	 */
	public int doDelete(String guid) {
		int result = pzlxDao.doDelete(guid);
		return result;
	}
	/**
	 * 凭证模板批量删除
	 * @param 
	 * @return
	 */
	public int doDeletes(String guid) {
		int result = pzlxDao.doDeletes(guid);
		return result;
	}
	
	
	/**
	 * 获取会计科目设置树
	 */
	public Object getKmszNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
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
		List list = pzlxDao.kmszMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                count=Integer.parseInt(map.get("COUNT")+"");
                if(count<=0) {
    	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
                }else {
    				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
                }
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
		List list = pzlxDao.kmszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
                mc = map.get("MC").toString();
                jb = map.get("JB").toString();
                count=Integer.parseInt(map.get("COUNT")+"");
                if(count<=0) {
    	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
                }else {
    				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
                }
                }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取单条科目信息
	 * 
	 */
	public Map getkmszObjectById(String id) {
		return pzlxDao.getkmszObjectById(id);
	}
	/**
	 * 根据主键获取列表详细信息 操作(修改)
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		return pzlxDao.getObjectById(dmxh);
	}
	/**
	 * 获取功能科目设置树
	 */
	public Object getgnkmNode(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
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
		List list = pzlxDao.kmszMenu(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
               mc = map.get("MC").toString();
               jb = map.get("JB").toString();
               count=Integer.parseInt(map.get("COUNT")+"");
               if(count<=0) {
   	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
               }else {
   				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	/**
	 * 获取功能科目设置树
	 */
	public Object getgnkmNodezj(PageData pd, String jb,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
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
		List list = pzlxDao.kmszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String dm = "",mc = "",zl = "";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				dm = Validate.isNullToDefault(map.get("DM"), "").toString();
               mc = map.get("MC").toString();
               jb = map.get("JB").toString();
               count=Integer.parseInt(map.get("COUNT")+"");
               if(count<=0) {
   	            children.add(new ExtTreeNode(dm, mc, true , true, false, href.length() > 0 ? href + dm : href, target,icon));
               }else {
   				children.add(new ExtTreeNode(dm, mc, false , true, false, href.length() > 0 ? href + dm + "&jb="+jb : href, target,icon));
               }
               }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}

}
