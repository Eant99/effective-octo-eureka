package com.googosoft.service.xmgl.jcsz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.xmgl.jcsz.XmflDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Service("xmflService")
public class XmflService {
	@Resource(name="xmflDao")
	private XmflDao xmflDao;
	
	/**
	 * 获取权限下的单位（单位人员树）
	 * @param pd
	 * @param loginrybh 登录的人员编号 
	 * @param rootPath
	 * @return
	 */
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath,String sjflbh) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&flbh=";
		} else {
			Href = Href + "?flbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = xmflDao.GetXmfl(sjflbh);
		Map map=new HashMap();
		if(dList.size()>0){
			String flbh="",bmh="",flmc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				flbh = Validate.isNullToDefault(map.get("FLBH"), "")+"";
                flmc = map.get("FLMC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0) {
				     children.add(new ExtTreeNode( flbh, "(" + flbh + ")" + flmc, true, true, false, Href.length() > 0 ? Href + map.get("FLBH").toString() : Href, Target));
                }else{
                	children.add(new ExtTreeNode(flbh, "(" + flbh + ")" + flmc, false, true, false, Href.length() > 0 ? Href + map.get("FLBH").toString() : Href, Target,icon));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	
}
