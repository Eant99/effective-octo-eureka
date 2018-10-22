package com.googosoft.service.systemset.cdgl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.systemset.cdgl.CdglDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("cdglService")
public class CdglService extends BaseService{
	
	@Resource(name="cdglDao")
	public CdglDao cdglDao;
/**
 * 获取菜单
 * @param pd
 * @param loginrybh
 * @param rootPath
 * @param mkbh
 * @return
 */
	public Object getCdNode(PageData pd, String loginrybh, String rootPath,String mkbh) {
//		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/s.gif";
//		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&mkbh=";
		} else {
			Href = Href + "?mkbh=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = cdglDao.GetXjcd(mkbh);
		Map map=new HashMap();
		if(dList.size()>0){
			String id="",bmh="",mkmc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				mkbh = Validate.isNullToDefault(map.get("mkbh"), "")+"";
				mkmc = map.get("mkmc")+"";
                xjcount=Integer.parseInt(map.get("COUNT")+"");
                if(xjcount<=0) {
				     children.add(new ExtTreeNode( mkbh,  mkmc, true, true, false, Href.length() > 0 ? Href + map.get("mkbh").toString() : Href, Target));
                }else{
//                	children.add(new ExtTreeNode(mkbh, mkmc, false, true, false, Href.length() > 0 ? Href + map.get("mkbh").toString() : Href, Target,icon));
                	children.add(new ExtTreeNode(mkbh, mkmc, false, true, false, Href.length() > 0 ? Href + map.get("mkbh").toString() : Href, Target));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 生成一级菜单编号
	 * @return
	 */
	public String getYjMkbh() {
		List<Map<String,Object>> list = cdglDao.getYjMkbh();
		boolean flag = true;
		String mkbh = new String();
		if(list.size()!=0){//如果没有一级菜单，不进行判断
		while(flag){
			mkbh = String.valueOf((int)(Math.random()*90+10));
			for (Map<String, Object> map : list) {
				if(!(map.get("mkbh").equals(mkbh))){
					flag = false;
			    }
			}
		}
		}else{
			mkbh = String.valueOf((int)(Math.random()*90+10));
		}
		return mkbh;
	}
	/**
	 * 生成次级菜单编号
	 * @param mkbh 
	 * @return
	 */
	public String getCjMkbh(String mkbh) {
		StringBuffer sb = new StringBuffer();
		sb.append(mkbh);
		String cjbh = new String();//次级编号
		boolean flag = true;
		List<Map<String,Object>> list = cdglDao.getCjMkbh(mkbh);
		if(list.size()!=0){//如果没有次级菜单，不进行判断
			while(flag){
				cjbh = String.valueOf((int)(Math.random()*90+10));
				for (Map<String, Object> map : list) {
					if(!(map.get("mkbh").equals(cjbh))){
						flag = false;
					}
				}
			}			
		}else{
			cjbh = String.valueOf((int)(Math.random()*90+10));
		}
		return sb.append(cjbh).toString();
	}
	/**
	 * 保存
	 * @param map
	 * @return
	 */
	public int doSave(PageData pd,Map<String, Object> map) {
		return cdglDao.doSave(pd,map);
	}
	/**
	 * 删除
	 * @param kmbh
	 * @return
	 */
	public int doDel(String mkbh) {
		int result = cdglDao.doDel(mkbh);
		if (result > 0) {
			doAddOplog(OplogFlag.DEL, "操作成功！", mkbh);
		}
		return result;
	}
	/**
	 * 修改回显
	 * @param mkbh
	 * @return
	 */
	public Map<String, Object> getByMkbh(String mkbh) {
		return cdglDao.getByMkbh(mkbh);
	}

}
