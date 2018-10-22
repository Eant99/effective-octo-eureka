package com.googosoft.service.systemset.qxgl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.systemset.qxgl.CzqxbDao;
import com.googosoft.service.base.BaseService;

/**
 * 操作权限信息service
 * @author master
 */
@Service("czqxbService")
public class CzqxbService extends BaseService{

	@Resource(name="czqxbDao")
	public CzqxbDao czqxbDao;

	/**
	 * 检测人员是否有模块操作权限
	 * @param rybh
	 * @param mkbh
	 * @return
	 */
	public boolean doCheckCzqx(String rybh, String mkbh) {
		return czqxbDao.doCheckCzqx(rybh,mkbh);
	}
	public Map getNextRyIfGly() {
		return czqxbDao.getNextRyIfGly();
	}
	/**
	 * 保存操作权限
	 * @param mkbh
	 * @param rybh
	 * @return
	 */
	public int doSave(String mkbh, String rybh) {
		return czqxbDao.doSave(mkbh,rybh);
	}
	/**
	 * 根据rybh获取操作权限
	 * @param rybh
	 * @return
	 */
	public List<Map> getCzqxMenuList(String rybh,String login) {	
		/*获取一级菜单*/
		List<Map> yjcd = czqxbDao.getYjcdList(login);
		String type = "no"+login;
		if(login.equals(SystemSet.AdminRybh())){
			type = login;
		}
		/*循环一级菜单，获取二级菜单*/
		for(int i=0;i<yjcd.size();i++){
			Map map = yjcd.get(i);
			List<Map> ejcd = czqxbDao.getXjcdList(map.get("MKBH")+"",login);
			int sjcdSize = 0;
			/*循环二级菜单，获取三级菜单*/
			for(int j=0;j<ejcd.size();j++){
				Map ejcd_map = ejcd.get(j);
				List<Map> sjcd = czqxbDao.getXjcdList(ejcd_map.get("MKBH")+"",type);
				int ejcdSize = 0;
				/*循环三级级菜单，判断人员是否有该菜单的权限，有权返回true，无权返回false*/
				for(int k=0;k<sjcd.size();k++){
					Map sjcd_map = sjcd.get(k);
					boolean b = czqxbDao.doCheckCzqx(rybh,sjcd_map.get("MKBH")+"");
					sjcd_map.put("operate", b);
					sjcd_map.put("idx", k);
					ejcdSize++;
				}
				if(ejcdSize == 0){
					ejcd.remove(ejcd_map);
					j--;
				}
				else{
					ejcd_map.put("childList", sjcd);
					ejcd_map.put("size",ejcdSize);
					sjcdSize += ejcdSize;
					ejcd_map.put("idx", j);
				}
			}
			if(ejcd.size() == 0){
				yjcd.remove(map);
				i--;
			}
			else{
				map.put("size", sjcdSize);
				map.put("childList", ejcd);
				map.put("idx", i);
			}
		}
		return yjcd;
	}
	/**
	 * 根据rybh查看操作权限
	 * @param rybh
	 * @return
	 */
	public List<Map> getCzqxMenuList1(String rybh,String login) {	
		/*获取一级菜单*/
		List<Map> yjcd = czqxbDao.getYjcdList(login);
		login=rybh; 
		String type = "no"+login;
		if(login.equals(SystemSet.AdminRybh())){
			type = login;
		}
		/*循环一级菜单，获取二级菜单*/
		for(int i=0;i<yjcd.size();i++){
			Map map = yjcd.get(i);
			List<Map> ejcd = czqxbDao.getXjcdList(map.get("MKBH")+"",login);
			
			int sjcdSize = 0;
			/*循环二级菜单，获取三级菜单*/
			for(int j=0;j<ejcd.size();j++){
				Map ejcd_map = ejcd.get(j);
				List<Map> sjcd = czqxbDao.getXjcdList(ejcd_map.get("MKBH")+"",type);
				int ejcdSize = 0;
				/*循环三级级菜单，判断人员是否有该菜单的权限，有权返回true，无权返回false*/
				for(int k=0;k<sjcd.size();k++){
					Map sjcd_map = sjcd.get(k);
					boolean b = czqxbDao.doCheckCzqx(rybh,sjcd_map.get("MKBH")+"");
					sjcd_map.put("operate", b);
					sjcd_map.put("idx", k);
					ejcdSize++;
				}
				if(ejcdSize == 0){
					ejcd.remove(ejcd_map);
					j--;
				}
				else{
					ejcd_map.put("childList", sjcd);
					ejcd_map.put("size",ejcdSize);
					sjcdSize += ejcdSize;
					ejcd_map.put("idx", j);
				}
			}
			if(ejcd.size() == 0){
				yjcd.remove(map);
				i--;
			}
			else{
				map.put("size", sjcdSize);
				map.put("childList", ejcd);
				map.put("idx", i);
			}
		}
		return yjcd;
	}
	/**
	 * 根据mkbh获取有此模块操作权限的人员姓名
	 * @param mkbh
	 * @return
	 */
	public List<Map<String, Object>> getRymc(String mkbh) {
		return czqxbDao.getRymc(mkbh);
	}
	/**
	 * 根据rybh获取操作权限
	 * @param rybh
	 * @return
	 */
	public List<Map> getCzqxMenuList2(String rybh) {	
		/*获取一级菜单*/
		List<Map> yjcd = czqxbDao.getYjcdListOfQx(rybh);
		/*循环一级菜单，获取二级菜单*/
		for(int i=0;i<yjcd.size();i++){
			Map map = yjcd.get(i);
			List<Map> ejcd =czqxbDao.getEjcdListOfQx(map.get("MKBH")+"",rybh);
			int sjcdSize = 1;
			/*循环二级级菜单，获取三级菜单*/
			for(int j=0;j<ejcd.size();j++){
				Map ejcd_map = ejcd.get(j);
				List<Map> sjcd = czqxbDao.getSjcdListOfQx(ejcd_map.get("MKBH")+"",rybh);
				ejcd_map.put("childList", sjcd);
				ejcd_map.put("size",sjcd.size()+1);
				sjcdSize = sjcdSize +(sjcd.size()==0?0:sjcd.size()+1);
			}
			map.put("size", sjcdSize);
			map.put("childList", ejcd);
		}
		return yjcd;
	}

}
