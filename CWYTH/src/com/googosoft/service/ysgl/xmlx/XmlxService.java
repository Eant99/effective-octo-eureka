package com.googosoft.service.ysgl.xmlx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.ysgl.xmsz.XmxxExportExcel;
import com.googosoft.service.base.BaseService;
import com.googosoft.service.ysgl.XmlxDao;
import com.googosoft.util.PageData;

@Service("xmlxService")
public class XmlxService extends BaseService{

	@Resource(name="xmlxDao")
	public XmlxDao xmlxDao;

	
	/**
	 * 新增
	 * @param dwb
	 * @return
	 */
	public int doAdd(PageData pd,String rybh,HttpSession session){
		int result = xmlxDao.doAdd(pd,rybh,session);
		System.err.println("______"+result);
		return result;
	}
	/**
	 * 修改
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public int doUpdate(PageData pd,String guid,HttpSession session) {
		int result = xmlxDao.doUpdate(pd,guid,session);
//		if(result>0){
//			doAddOplog(OplogFlag.UPD,"单位基础信息");
//		}
		return result;
	}
	@Transactional
	public int doDelete(String dwbh) {
		System.out.println("dwbh===="+dwbh);
		
		String array[] = dwbh.split("','");
		for(int i=0;i<array.length;i++) {
		
			List list1 = xmlxDao.getxx(array[i]);
			for(int j=0;j<list1.size();j++) {
				Map map1 = (Map) list1.get(j);
				String sszt = (String) map1.get("sszt");
				String guid = (String) map1.get("guid");
				String xmlxbh = (String) map1.get("xmlxbh");
				xmlxDao.doDeletexmlx(guid);
				xmlxDao.doDeletejjfl(guid);
				xmlxDao.doDeletexmsr(guid);
				xmlxDao.doDeletexmzc(guid);
				xmlxDao.updatebh(sszt, xmlxbh);
			}
			
		}
//		xmlxDao.doDeletexmlx(dwbh);
//		xmlxDao.doDeletejjfl(dwbh);
//		xmlxDao.doDeletexmsr(dwbh);
//		xmlxDao.doDeletexmzc(dwbh);
		
		return 1;
	}
	/**
	 *  验证项目类型是否正被使用
	 */
	public boolean selectXmlx(String guid) {
		return xmlxDao.selectXmlx(guid);
	}
	//验证项目名称是否被使用
	public boolean checkXmmcExist(PageData pd) {
		return xmlxDao.checkXmmcExist(pd);
	}
	
	/**
	 * 判断项目类型编号的重复性
	 * @param 项目类型编号
	 * @return
	 */
	public boolean doCheckDwbh(String bmh) {
		boolean result = xmlxDao.doCheckDwbh(bmh);
		return result;
	}
	
	
	public String doCheckfjbh(String bmh) {
		String result = xmlxDao.doCheckfjbh(bmh);
		System.err.println("result="+result);
		return result;
	}
	/**
	 * 获取实体类
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getObjectById(String dwbh) {
		return xmlxDao.getObjectById(dwbh);
	}
	
	/**
	 * 获取插入的信息
	 * @param dwbh
	 * @return
	 */
	public Map<?, ?> getInfoById(String guid) {
		return xmlxDao.getInfoById(guid);
	}
	/**
	 * 获取收入科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getsrkmById(String guid,String kjzd) {
		return xmlxDao.getsrkmById(guid,kjzd);
	}
	/**
	 * 获取支出科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getzckmById(String guid,String kjzd) {
		return xmlxDao.getszckmById(guid,kjzd);
	}
	/**
	 * 获取经济分类科目的信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getjjflkmById(String guid) {
		return xmlxDao.getjjflkmById(guid);
	}
	
	public List<Map<String, Object>> getfjszById(String guid) {
		return xmlxDao.getfjszById(guid);
	}
	/**
	 * 通过dbwh获取（bmh）mc格式
	 * @param dwbh
	 * @return
	 */
	public String getDwxx(String dwbh){
		return xmlxDao.getDwxx(dwbh);
	}
	/**
	 * 增加三种科目
	 */
	public Object addmorekm(HttpSession session,List list) {
		//xmlxDao.addmorekm(session,list);
		return list;
		
	}
	/**
	 * 增加收入
	 * @param session
	 * @param map
	 * @return
	 */
	public Object addsr(HttpSession session,Map map) {
		xmlxDao.addsr(session,map);
		return map;
		
	}
	/**
	 * 增加支出
	 * @param session
	 * @param map
	 * @return
	 */
	public Object addzc(HttpSession session,Map map) {
		xmlxDao.addzc(session,map);
		return map;
		
	}
	/**
	 * 增加经济分类
	 * @param session
	 * @param map
	 * @return
	 */
	public Object addjjfl(HttpSession session,Map map) {
		xmlxDao.addjjfl(session,map);
		return map;
		
	}
	
	public Object addfjsz(HttpSession session,Map map,int j) {
		xmlxDao.addfjsz(session,map,j);
		return map;
		}
	
		public Object addfjsz1(HttpSession session,Map map,int j) {
			System.err.println("=====service1=======");
			xmlxDao.addfjsz1(session,map,j);
			return map;
		
	}
	/**
	 * 删除sr表
	 */
	public int deletesr(String xmlxbh) {
		return xmlxDao.deletesr(xmlxbh);
		
	}
	/**
	 * 删除zc表
	 */
	public int deletezc(String xmlxbh) {
		return xmlxDao.deletezc(xmlxbh);
		
	}
	/**
	 * 删除jjfl表
	 */
	public int deletejjfl(String xmlxbh) {
		return xmlxDao.deletejjfl(xmlxbh);
	}
	/**
	 * 删除fjsz表
	 */
	public int deletefjsz(String xmlxbh) {
		return xmlxDao.deletefjsz(xmlxbh);
	}
	/**
	 * 业务记录表细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddjwjl(Map map,String kjzd) {
		System.out.println("1111111");
		return xmlxDao.doAddjwjl(map,kjzd);
    }
	
	public int doAddjwj2(Map map,String kjzd) {
		System.out.println("222222");
		return xmlxDao.doAddjwj2(map,kjzd);
    }
	
	public int doAddfjszb(Map map) {
		return xmlxDao.doAddfjszb(map);
	}
	
	/**
	 * 业务记录表细增加操作
	 * @param 
	 * @param
	 * @return
	 */
	public int doAddjwjl1(Map map) {
		
		int i = xmlxDao.doAddjwjl1(map);
		
		return i;
	
    }
	public Object expExcel(String realfile, String shortfileurl,String searchValue,PageData pd,String sszt) {
		List<Map<String, Object>> list = this.xmlxDao.getList(searchValue,pd,sszt);
		String Title = "项目信息";
		String[] title = new String[] { "序号", "项目编号", "部门名称","项目名称", "项目大类" ,"项目类别", "项目类型","负责人","项目属性","归口部门","是否启用" };
		Map<String, Object> dataMap = XmxxExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	public Object expExcel3(String realfile, String shortfileurl,String searchValue, String guid,String sszt) {
		List<Map<String, Object>> list = this.xmlxDao.getList3(searchValue,guid,sszt);
		String Title = "项目执行情况信息";
		String[] title = new String[] { "序号", "项目编号", "部门名称","项目名称", "项目大类" ,"项目类别", "预算金额","政府采纳金额","非政府采纳金额","政府采购执行比例(%)","非政府采购执行比例(%)" };
		Map<String, Object> dataMap = XmxxExportExcel.exportExcel3(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
}
