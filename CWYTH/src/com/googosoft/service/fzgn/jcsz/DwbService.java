package com.googosoft.service.fzgn.jcsz;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.controller.fzgn.jcsz.expExcel.DwbExportExcel;
import com.googosoft.controller.systemset.qxgl.ExtTreeNode;
import com.googosoft.dao.fzgn.jcsz.DwbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**
 * 单位信息service
 * @author master
 */
@Service("dwbService")
public class DwbService extends BaseService{
	
	@Resource(name="dwbDao")
	public DwbDao dwbDao;
	
	
	/**
	 * 新增
	 * @param dwb
	 * @return
	 */
	public int doAdd(GX_SYS_DWB dwb){
		dwb.setSjdw(WindowUtil.getXx(dwb.getSjdw(), "D"));//(0000035)山东截取，对应的部门号0000035
		dwb.setDwld(WindowUtil.getXx(dwb.getDwld(), "R"));
		dwb.setFgld(WindowUtil.getXx(dwb.getFgld(), "R"));
		dwb.setSj(WindowUtil.getXx(dwb.getSj(), "R"));
		dwb.setDwjc(dwbDao.getDwjc(dwb.getSjdw()));//单位级次
		int result = dwbDao.doAdd(dwb);
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
	public Map<?, ?> getObjectById(String dwbh) {
		return dwbDao.getObjectById(dwbh);
	}
	
	/**
	 * 修改
	 * @param dwb
	 * @return
	 */
	public int doUpdate(GX_SYS_DWB dwb,PageData pd) {
			dwb.setDwld(WindowUtil.getXx(dwb.getDwld(), "R"));		
			dwb.setFgld(WindowUtil.getXx(dwb.getFgld(), "R"));	
			if("000000".equals(dwb.getSjdw())){
				dwb.setSjdw("000000");
			}else{
				dwb.setSjdw(WindowUtil.getXx(dwb.getSjdw(), "D"));			   
			}
			int result = dwbDao.doUpdate(dwb,pd);
			if(result>0){
				doAddOplog(OplogFlag.UPD,"单位基础信息",dwb.getDwbh());
			}
			return result;
	}
	/**
	 * 删除
	 * @param dwb
	 * @return
	 */
	public int doDelete(String dwbh) {
		int result = dwbDao.doDelete(dwbh);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"单位基础信息",dwbh);
		}
		return result;
	}
	
	
	/**
	 * 判断部门号的重复性
	 * @param dwb
	 * @return
	 */
	public boolean doCheckDwbh(String bmh) {
		boolean result = dwbDao.doCheckDwbh(bmh);
		return result;
	}
	
	/**
	 * 判断排序序号的重复性
	 * @param dwb
	 * @return
	 */
	public boolean doCheckPxxh(String Pxxh) {
		boolean result = dwbDao.doCheckPxxh(Pxxh);
		return result;
	}
	
	/**
	 * 删除时，如果单位下有已处置的资产，则禁用该单位
	 * @param dwbh
	 * @return
	 */
	public int jydW(String dwbh){
		int i = dwbDao.jyDw(dwbh);
		if(i>0){
			doAddOplog(OplogFlag.DEL,"单位基础信息：禁用",dwbh);
		}
		return i;
	}
	/**
	 * 删除单位时验证该单位下是否有人员或下级单位或资产,如果没有则直接删除
	 * @param DWBHS
	 * @return
	 */
	public int validateForRyOrXjdwOrZc(String DWBHS){
		return dwbDao.validateForRyOrXjdwOrZc(DWBHS);
	}

	/**
	 * 单位信息批量赋值
	 * @param ids
	 * @param ziduan
	 * @param zdValue
	 * @return
	 * @throws ParseException
	 */
	public int doPlfuzhi(String ids,String ziduan,Object zdValue) throws ParseException  {
		if(ziduan.equals("sjdw")){
			zdValue = WindowUtil.getXx(zdValue+"", "D");
		}else if(ziduan.equals("dwld")){
			zdValue = WindowUtil.getXx(zdValue+"", "R");
		}else if(ziduan.equals("jlrq")){
			zdValue = new SimpleDateFormat("yyyy-MM-dd").parse(zdValue+"");
		}
		return dwbDao.doPlfuzhi(ids, ziduan, zdValue);
		
	}
	
	/**
	 * 单位机构设置
	 * 通过部门号(名称)查询单位编号
	 * @param dwmc (bmh)mc格式
	 * @return
	 */
	public String findDwbhByDwmc(String dwmc) {
		String  dwbh = dwbDao.findDwbhByDwmc(dwmc);
		if(Validate.isNull(dwbh)){
			dwbh="F";
		}
		return dwbh ;
	}
	/**
	 * 通过dbwh获取（bmh）mc格式
	 * @param dwbh
	 * @return
	 */
	public String getDwxx(String dwbh){
		return dwbDao.getDwxx(dwbh);
	}
	/**
	 * 通过obj获得下级节点的数量
	 * @return
	 */
	public String getNewStatus(String dwbh) {
		return dwbDao.getNewStatus(dwbh);
	}
	
	/**
	 * 导出单位信息Excel   wzd
	 * @return
	 */
	public Object expExcel2(String realfile, String shortfileurl, String dwbh,String searchValue,String rybh,String s1,String s2,String dwbh1,String mc) {
		List<Map<String, Object>> dwList = this.dwbDao.getList(dwbh,searchValue,rybh,s1,s2,dwbh1,mc);
		String Title = "单位机构信息";
		String[] title = new String[] { "序号", "单位号", "单位名称", "单位简称", "单位地址" ,"单位类别", "建立日期","单位负责人","隶属单位","单位办别","是否学院" ,"单位有效标识" };
		Map<String, Object> dataMap = DwbExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	
	/**
	 * 获取归档范围分类树(初)
	 * @return
	 */
	public Object getGdfwflNode(PageData pd, String loginrybh,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String Target=pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&treeId=";
		} else {
			Href = Href + "?treeId=";
		}
		ExtTreeNode node = new ExtTreeNode(SystemSet.TopDwFlag());
		List<ExtTreeNode> children=new ArrayList<ExtTreeNode>();
		List dList=dwbDao.PowerGdfwflFirst(loginrybh);
		Map map=new HashMap();
		if(dList.size()>0){
			String id="",mc="";
			int xjcount=0; 
			for(int i=0;i<dList.size();i++){
				map=(Map)dList.get(i);
				id = Validate.isNullToDefault(map.get("TYPEID"), "")+"";
                mc = map.get("TYPENAME")+"";
                xjcount=Integer.parseInt(map.get("XJCOUNT")+"");
                if(xjcount<=0) {
                	children.add(new ExtTreeNode("D" + id,  mc, true, true, false, Href.length() > 0 ? Href + map.get("TYPEID")+"" : Href, Target));
                }else{
                	children.add(new ExtTreeNode("D" + id, mc, false, true, false, Href.length() > 0 ? Href + map.get("TYPEID")+"" : Href, Target,icon));
                }
			}
			node.setChildren(children);
		}
		return node.GetChildrenJsonString();
	}
	/**
	 * 获取上级分类下的所有分类
	 * @param pd
	 * @param sjdw
	 * @param rootPath
	 * @return
	 */
	public Object getGdfwflNodeXj(PageData pd, String sjdw,String rootPath) {
		String icon = rootPath+"/static/plugins/ext/resources/images/default/tree/folder.gif";
		String iconry=rootPath+"/static/plugins/ext/resources/images/default/tree/vector.gif";
		String Target = pd.getString("target");
		String Href = pd.getString("pageUrl");
		if (Href.indexOf("?") > 0) {
			Href = Href + "&treeId=";
		} else {
			Href = Href + "?treeId=";
		}
		sjdw = sjdw.substring(1);
		ExtTreeNode node = new ExtTreeNode(sjdw);
		List<ExtTreeNode> children = new ArrayList<ExtTreeNode>();
		List dList =  dwbDao.PowerGdfwflSecond(sjdw);
		String id, mc;
		int xjcount = 0;
		Map map = new HashMap();
		if (dList.size() > 0) {
			for (int i = 0; i < dList.size(); i++) {
				map = (Map) dList.get(i);
				id = map.get("TYPEID")+"";
				mc = map.get("TYPENAME")+"";
				xjcount = Integer.parseInt(map.get("XJCOUNT")+"");
				if(xjcount <= 0){
   				  	children.add(new ExtTreeNode("D" + id,  mc, true, true, false, Href.length() > 0 ? Href + map.get("TYPEID")+"" : Href, Target));
				}else{
					children.add(new ExtTreeNode("D" + id,  mc, false, true,false , Href.length() > 0 ? Href + map.get("TYPEID")+"" : Href, Target,icon));
				}
			}
		}
		node.setChildren(children);
		return node.GetChildrenJsonString();
	}
	
	
}
