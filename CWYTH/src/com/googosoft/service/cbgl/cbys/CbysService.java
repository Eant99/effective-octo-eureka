package com.googosoft.service.cbgl.cbys;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.cbgl.cbys.CbysDao;
import com.googosoft.dao.kjhs.KmszDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("cbysService")
public class CbysService extends BaseService {
	
	@Resource(name="cbysDao")
	public CbysDao cbysDao;
	
	/**
	 * 获取成本要素详细信息
	 */
	public Map<String, Object> getObjectByIdByKmsz(String guid) {
		
		return cbysDao.getObjectByIdByKmsz(guid);
	}
	/**
	 * 删除成本要素详细信息
	 */
	public int doDel(String kmbh) {
		int result = cbysDao.doDel(kmbh);
		return result;
	}
	public int getCountByKmbh(String kmbh) {
		return cbysDao.getCountByKmbh(kmbh);
	}
	/**
	 * 获取经济科目设置树
	 */
	public Object getjjkmNodezj(PageData pd, String jb,String rootPath) {
		//String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";	
		String target = pd.getString("target");
		String href = pd.getString("pageUrl");
		String isAll = Validate.isNullToDefaultString(pd.getString("isAll"), "");
		boolean bool = false;
		if(Validate.isNull(jb)){
			bool = true;
		}
		if (href.indexOf("?") > 0) {
			href = href + "&kmbh=";
		} else {
			href = href + "?kmbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List list = cbysDao.jjszMenuzj(jb);
		Map map = new HashMap();
		if(list.size()>0){
			String kmbh = "",kmmc = "",kmjc="",l="",k="";
			int count=0;
			for(int i = 0;i < list.size();i++)
			{
				map = (Map)list.get(i);
				kmbh = Validate.isNullToDefault(map.get("KMBH"), "").toString();
				System.err.println("isall==="+isAll);
				System.err.println("kmbh==="+kmbh);
				if((isAll.equals("0") && kmbh.equals("301"))||(isAll.equals("0") && kmbh.equals("309")))
				{
					System.err.println("AAAAAAAAA==="+kmbh);
				}
				else
				{
					kmmc = Validate.isNullToDefaultString(map.get("KMMC"), "");
	                kmjc = Validate.isNullToDefaultString(map.get("KMJC"),"");
	                count= cbysDao.getCount(kmbh);
	                l = Validate.isNullToDefaultString(map.get("L"),"");
	                k = Validate.isNullToDefaultString(map.get("K"),"");
	               if(count>0) {
	          	      children.add(new ExtTreeNode(kmbh, kmmc, false , true, false, href.length() > 0 ? href + kmbh +"&kmjc="+kmjc+"&l="+l+"&k="+k+"&kmnd="+map.get("KMND"): href, target));
	
	               }else {
	   					children.add(new ExtTreeNode(kmbh, kmmc, true , true, false, href.length() > 0 ? href + kmbh + "&kmjc="+kmjc+"&l="+l+"&k="+k+"&kmnd="+map.get("KMND"): href, target));
	               }             
			   }
            }
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	
	}
	
}
