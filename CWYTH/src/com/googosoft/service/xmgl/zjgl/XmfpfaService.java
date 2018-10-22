package com.googosoft.service.xmgl.zjgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.xmgl.xm.XmDao;
import com.googosoft.dao.xmgl.xmsb.XmsbDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("xmfpfaService")
public class XmfpfaService {
	@Resource(name="xmDao")
	private XmDao xm;
	/**
	 * 获取树
	 * @param pd
	 * @param jb
	 * @param rootPath
	 * @return
	 */
	public Object getXmNode(PageData pd, String sjfl,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String leaf = rootPath+"/static/plugins/ext/resources/images/default/tree/leaf.gif";
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		boolean bool = false;
		bool = xm.checkIsLeaf(sjfl);
		if (href.indexOf("?") > 0) {
			href = href + "&guid=";
		} else {
			href = href + "?flmc=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = xm.xmTree(sjfl);
		Map map = new HashMap();
		if(list.size()>0){
			String guid = "",flmc = "",fljb = "";
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				guid = Validate.isNullToDefault(map.get("GUID"), "").toString();
				flmc = Validate.isNullToDefault(map.get("XMMC"), "").toString();
				fljb = Validate.isNullToDefault(map.get("ZL"), "").toString();
				bool = xm.checkIsLeaf(guid);
				if(bool){
					children.add(new ExtTreeNode(guid, flmc, bool , true, false, href.length() > 0 ? href + guid + "&fljb="+fljb : href, target,leaf));
				}else{
					children.add(new ExtTreeNode(guid, flmc, bool , true, false, href.length() > 0 ? href + guid + "&fljb="+fljb : href, target,icon));
				}
				
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	

}
