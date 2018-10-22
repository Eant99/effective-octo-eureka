package com.googosoft.service.xmgl.cgmlsz;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.controller.fzgn.jcsz.expExcel.XsExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.xmgl.jcsz.CgmlszDao;
import com.googosoft.pojo.xmgl.jcsz.Cw_cgmlszb;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Service("cgmlszService")
public class CgmlszService extends BaseService{
	
	@Resource(name="cgmlszDao")
	private CgmlszDao cgmlszDao;
	
	/**
	 * 获取权限下的单位（单位人员树）
	 * @param pd
	 * @param loginrybh 登录的人员编号 
	 * @param rootPath
	 * @return
	 */
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath,String sjfyfl) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		
		if (Href.indexOf("?") > 0) {
			Href = Href + "&fyfl=";
		} else {
			Href = Href + "?fyfl=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = cgmlszDao.GetCgmlsz(sjfyfl);
		Map map=new HashMap();
		if(dList.size()>0){
			String id="",bmh="",mlmc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				id = Validate.isNullToDefault(map.get("guid"), "")+"";
				System.out.println("=========id========"+id);
                mlmc = map.get("MLMC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0) {
				     children.add(new ExtTreeNode( id,  mlmc, true, true, false, Href.length() > 0 ? Href + map.get("guid").toString() : Href, Target));
                }else{
                	children.add(new ExtTreeNode(id, mlmc, false, true, false, Href.length() > 0 ? Href + map.get("guid").toString() : Href, Target,icon));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}

	/**
	 * 新增
	 * @param 
	 * @return
	 */
	public int doAdd(Cw_cgmlszb cgmlszb){
		
		int result = cgmlszDao.doAdd(cgmlszb);
		return result;
	}
	
	/**
	 * 获取实体类
	 * @param Cw_cgmlszb
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid) {
		return cgmlszDao.getObjectByGuId(guid);
	}
	
	/**
	 * 由采购分类得到CG_EDIT页面中的上级目录
	 * @PARAM CGFL
	 * @RETURN
	 */
	public Map<?, ?> getSjmlByGuid(String guid) {
		return cgmlszDao.getSjmlByGuid(guid);
	}
	
	/**
	 * 修改
	 * @param Cw_cgmlszb
	 * @return
	 */
	public int doUpdate(Cw_cgmlszb cgmlszb) {
			
			int result = cgmlszDao.doUpdate(cgmlszb);
			
			return result;
	}
	
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue) {
		List<Map<String, Object>> xsList = this.cgmlszDao.getXsList(guid,searchValue);
		String Title = "采购目录信息";
		String[] title = new String[] { "序号", "目录代码", "目录名称", "上级目录"};
		Map<String, Object> dataMap = XsExportExcel.exportExcel(realfile,shortfileurl, title, Title,xsList );
		return dataMap;
	}
	
	/**
	 * 删除
	 * 
	 * @param Cw_cgmlszb
	 * @return
	 */
	public String doDel(String guid) {
		String str1 = "",str2 = "";
		String newguid = cgmlszDao.docheckisdelete(guid);//得到可以删除的guid
		String notguid = cgmlszDao.dochecknotdelete(guid);//得到不能删除的guid
		String mldm = cgmlszDao.seledmbyid(notguid);//根据不能删除的guid得到不能删除的mldm
		int cgscnum = cgmlszDao.getcgscnum(guid);//得到成功删除数量
		cgmlszDao.doDel(newguid);
		str2 = "成功删除"+cgscnum+"条";
		if(!"".equals(notguid)) {//不能显示的目录代码不是空，则显示
			str1 = "编号为"+mldm+"的不允许删除,";
			return str1+str2;
		}else {
			return str2;
		}
	}
	
	/**
	 * 判断目录的重复性
	 * @param dwb
	 * @return
	 */
	public boolean doCheckMldm(String mldm) {
		boolean result = cgmlszDao.doCheckMldm(mldm);
		return result;
	}
	
}
