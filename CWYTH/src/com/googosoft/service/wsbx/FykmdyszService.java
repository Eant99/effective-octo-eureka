package com.googosoft.service.wsbx;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.controller.wsbx.jcsz.fykmdyszExportExcel;
import com.googosoft.controller.zffs.zffsdyszExportExcel;
import com.googosoft.dao.fzgn.tzgg.TxlDao;
import com.googosoft.dao.kjhs.KmszDao;
import com.googosoft.dao.wsbx.FykmdyszDao;
import com.googosoft.dao.xmgl.jcsz.XmflDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.wsbx.jcsz.Cw_fykmdzb;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

@Service("fykmdyszService")
public class FykmdyszService {
	@Resource(name="fykmdyszDao")

	private FykmdyszDao fykmdyszDao;

	/**
	 * 获取权限下的单位（单位人员树）
	 * @param pd
	 * @param loginrybh 登录的人员编号 
	 * @param rootPath
	 * @return
	 */
	public Object getPowerDwNode(PageData pd, String loginrybh,String rootPath,String sjfyfl) {
		//String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&fyfl=";
		} else {
			Href = Href + "?fyfl=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList = fykmdyszDao.GetFykmdysz(sjfyfl);
		Map map=new HashMap();
		if(dList.size()>0){
			String id="",bmh="",fymc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				id = Validate.isNullToDefault(map.get("guid"), "")+"";
				System.out.println("=========id========"+id);
                fymc = map.get("FYMC")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0) {
				     children.add(new ExtTreeNode( id,  fymc, true, true, false, Href.length() > 0 ? Href + map.get("guid").toString() : Href, Target));
                }else{
                	children.add(new ExtTreeNode(id, fymc, false, true, false, Href.length() > 0 ? Href + map.get("guid").toString() : Href, Target));
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
	public int doAdd(Cw_fykmdzb fykmdzb){
//		dwb.setSjdw(WindowUtil.getXx(dwb.getSjdw(), "D"));
//		dwb.setDwld(WindowUtil.getXx(dwb.getDwld(), "R"));
//		dwb.setFgld(WindowUtil.getXx(dwb.getFgld(), "R"));
//		dwb.setDwjc(dwbDao.getDwjc(dwb.getSjdw()));//单位级次
		int result = fykmdyszDao.doAdd(fykmdzb);
		//if(result>0){
			//doAddOplog(OplogFlag.ADD,"单位基础信息",dwb.getDwbh());
		//}
		return result;
	}
	/**
	 * 修改
	 * @param Cw_fykmdzb
	 * @return
	 */
	public int doUpdate(Cw_fykmdzb fykmdzb) {
			
			int result = fykmdyszDao.doUpdate(fykmdzb);
			
			return result;
	}
	/**
	 * 批量修改
	 * @param Cw_fykmdzb
	 * @return
	 */
	public int doplUpdate(Cw_fykmdzb fykmdzb,String guid) {
			
			int result = fykmdyszDao.doplUpdate(fykmdzb,guid);
			
			return result;
	}
	/**
	 * 获取实体类
	 * @param Cw_fykmdzb
	 * @return
	 */
	public Map<?, ?> getObjectById(String guid,String sszt,String kjzd) {
		return fykmdyszDao.getObjectByGuId(guid,sszt,kjzd);
	}
	/**
	 * 删除
	 * 
	 * @param Cw_fykmdzb
	 * @return
	 */
	public int doDel(String guid) {
		int result = fykmdyszDao.doDel(guid);
//		if (result > 0) {
//			doAddOplog(OplogFlag.DEL, "教师基础信息：删除", guid);
//		}
		return result;
	}
	public boolean doCheck(String kmbh) {
		return fykmdyszDao.doCheck(kmbh);
	}
	
	/**
	 * 导出费用科目对应设置Excel   wzd
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,String searchValue,String fyfls) {
		List<Map<String, Object>> dwList = this.fykmdyszDao.getJsList(guid,searchValue,fyfls);
		String Title = "费用科目应对设置";
		String[] title = new String[] { "序号", "费用分类", "费用名称","上级费用分类", "借贷方向","科目编号","科目名称" };
		Map<String, Object> dataMap = fykmdyszExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
