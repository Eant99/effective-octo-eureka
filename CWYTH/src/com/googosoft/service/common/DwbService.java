package com.googosoft.service.common;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.common.DwbDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;
@Service("deptService")
public class DwbService extends BaseService{
	@Resource(name="deptDao")
	private  DwbDao dwbDao;//单例
	/**
	 * 获取条件
	 * @param rybh
	 * @param colName
	 * @param dwbh
	 * @param flag
	 * @return
	 */
	public  String getDwqxWhereSql1(String rybh,String colName,String dwbh,boolean flag){
		//管理默认全部权限
		if(rybh.equals(SystemSet.AdminRybh())&&Validate.isNull(dwbh)){
			return "";
		}
		System.err.println("_dwbh_"+dwbh);
		if("".equals(dwbh)){
			return " and " + colName + " in (select z.dwbh from "+TnameU.RYDWQXB+" z left join gx_sys_dwb d on z.dwbh=d.dwbh where z.rybh = '"+rybh+"') ";
		}else{
			if(flag==true){//是否包含下级
			  return " and t.SZYX1  in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw  start with dwbh='"+dwbh+"') ";	
			}else{
				return " and " + colName + " = '"+dwbh;
			}
		}
	}
	public  String getDwqxWhereSql(String rybh,String colName,String dwbh,boolean flag){
		//管理默认全部权限
		if(rybh.equals(SystemSet.AdminRybh())&&Validate.isNull(dwbh)){
			return "";
		}
		System.err.println("_dwbh_"+dwbh);
		if("".equals(dwbh)){
			return " and " + colName + " in (select z.dwbh from "+TnameU.RYDWQXB+" z left join gx_sys_dwb d on z.dwbh=d.dwbh where z.rybh = '"+rybh+"') ";
		}else{
			if(flag==true){//是否包含下级
			  return " and " + colName + "  in(select dwbh from gx_sys_dwb connect by prior dwbh=sjdw  start with dwbh='"+dwbh+"') ";	
			}else{
				return " and " + colName + " = '"+dwbh;
			}
		}
	}
	/**
	 * 检测人员单位权限
	 * @param rybh
	 * @param dwbh
	 * @return
	 */
	public String doCheckRyDWQX(String rybh,String dwbh)
	{
		return dwbDao.doCheckRyDWQX(rybh, dwbh);
	}
	/**
	 * 根据姓名获取人员编号
	 * @param inputValue
	 * @return
	 */
	public String GetKeyId(String inputValue)
	{	
	      if (inputValue.indexOf("(") < 0)
	      {
	          return "";
	      }
	      return (String)dwbDao.getKeyId(inputValue.substring(1, inputValue.indexOf(")")));
	 }
}
