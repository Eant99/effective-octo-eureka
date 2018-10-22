package com.googosoft.service.systemset.qxgl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.constant.TnameU;
import com.googosoft.controller.systemset.qxgl.JsExportExcel;
import com.googosoft.dao.systemset.qxgl.JsxxDao;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.systemset.qxgl.JSB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;

/**
 * 角色信息Service Create by 刘帅 on 2016-10-20 11:20
 */

@Service("jsxxService")
public class JsxxService extends BaseService{

	@Resource(name = "jsxxDao")
	private JsxxDao jsxxDao;
	
	private static String JSB_TNAME=TnameU.getTname("jsb");//角色表	

	/**
	 * 角色管理（添加）：添加角色
	 * 
	 * @return
	 */
	public boolean doAdd(JSB jsb) {
		jsb.setJsbh(AutoKey.makeCkbh(JSB_TNAME, "jsbh", "2"));
		int i = (int) jsxxDao.doAdd(jsb);
		if (i == 1){
			doAddOplog(OplogFlag.ADD,"角色管理",jsb.getJsbh());
			return true;
		}else{
			return false;
		}
	}
	
	public String getNewStatus(String sjdd) {
		return jsxxDao.getNewStatus(sjdd);
	}

	/**
	 * 角色管理（修改）：修改角色信息
	 * 
	 * @param ryb
	 * @return
	 */
	public boolean doUpdate(JSB jsb) {
		int i = (int) jsxxDao.doUpdate(jsb);
		if (i == 1){
			doAddOplog(OplogFlag.UPD,"角色管理",jsb.getJsbh());
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 角色管理（查看）：获取单个角色详细信息
	 * 
	 * @param jsbh
	 * @return
	 */
	public Map getRyxx(String jsbh) {
		return jsxxDao.getRyxx(jsbh);
	}

	/**
	 * 角色管理（删除）：删除角色
	 * 
	 * @param jsbh
	 * @return
	 */
	public boolean doDelete(String jsbh) {
		int i = (int) jsxxDao.doDelete(jsbh);
		if (i > 0) {
			doAddOplog(OplogFlag.DEL,"角色管理",jsbh);
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 角色管理（操作限制）：一级菜单
	 * @return
	 */
	public List getYjcdList() {
		return jsxxDao.getYjcdList();
	}

	/**
	 * 角色管理（操作限制）：二级菜单
	 * @return
	 */
	public List getEjcdList(String mkbh) {
		return jsxxDao.getEjcdList(mkbh);
	}

	/**
	 * 角色管理（操作限制）：三级菜单
	 * @return
	 */
	public List getSjcdList(String mkbh) {
		return jsxxDao.getSjcdList(mkbh);
	}
	
	/**
	 * 角色管理（操作限制）：角色权限查询
	 * @return
	 */
	public boolean doCheckCzqx(String jsbh, String mkbh) {
		return jsxxDao.doCheckCzqx(jsbh,mkbh);
	}
	/**
	 * 角色管理（操作限制）：保存角色操作权限
	 * @param mkbh
	 * @param rybh
	 * @return
	 */
	public boolean saveJsCzqx(String mkbh, String jsbh) {
		int i = (int) jsxxDao.saveJsCzqx(mkbh,jsbh);
		if(i>0){
			doAddOplog(OplogFlag.UPD,"角色管理：保存角色操作权限",jsbh);
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 角色管理（角色用户）：获取用户信息，单位信息，角色信息
	 * 
	 * @param jsbh
	 * @return
	 */
	public PageInfo getJsRyxx(PageData pd,String jsbh) {
		return jsxxDao.getJsRyxx(pd,jsbh);
	}
	
	/**
	 * 角色管理（角色用户 列表）：增加用户，实现与角色关联
	 * 
	 * @return
	 */
	public int doSaveRy(String rybh,String jsbh) throws Exception{
		boolean flag = jsxxDao.doCheckJsry(rybh, jsbh);
		if(flag){
			int i =(int)jsxxDao.doSaveRy(rybh,jsbh);
			if(i>0){
				doAddOplog(OplogFlag.ADD,"角色管理：增加用户","");
			}
			return i;
		}else{
			return 0;
		}
	}
	
	/**
	 * 角色管理（角色用户 列表）：删除用户，实现与角色解绑
	 * 
	 * @return
	 */
	public boolean doDeleteRy(String rybh,String jsbh) throws Exception{
		int i =(int)jsxxDao.doDeleteRy(rybh,jsbh);
		if(i>0){
			doAddOplog(OplogFlag.DEL,"角色管理",rybh);	
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 启用禁用
	 * @param rybh
	 * @param type
	 * @return
	 */
	public int qyjy(String jsbh,String type){
		int result = jsxxDao.qyjy(jsbh,type);
		String ryzt = "启用";
		if(!type.contains("qy")){
			ryzt = "禁用";
		}
		if(result>0){
			doAddOplog(OplogFlag.UPD,"角色基础信息："+ryzt,jsbh.split(","));
		}
		return result;
	}
	
	/**
	 * 导出用户角色信息
	 * @param realfile
	 * @param shortfileurl
	 * @param guid
	 * @param searchValue
	 * @param jsbh
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl, String guid,
			String searchValue, String jsbh) {
		List<Map<String, Object>> dwList = this.jsxxDao.getList(guid,searchValue,jsbh);
		String Title = "角色人员信息";
		String[] title = new String[] { "序号", "工号", "姓名","单位名称"  };
		Map<String, Object> dataMap = JsExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
	/**
	 * 验证该角色下是否已经存在该人员
	 * @param rybh
	 * @param jsbh
	 * @return true:存在，false：不存在
	 */
	public boolean doCheckJsry(String rybh, String jsbh){
		return !jsxxDao.doCheckJsry(rybh, jsbh);
	}
}
