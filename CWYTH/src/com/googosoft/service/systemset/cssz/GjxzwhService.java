package com.googosoft.service.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.GjxzwhDao;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_TOOLDOWN;
import com.googosoft.service.base.BaseService;
/**工具下载维护
 * @author sxl
 */
@Service("gjxzwhService")
public class GjxzwhService extends BaseService {
@Resource(name="gjxzwhDao")
private GjxzwhDao gjxzwhDao;

public int doAdd(ZC_SYS_TOOLDOWN xzwxb) {
	xzwxb.setGuid(xzwxb.getGuid());
	//判断用户是否存在
	boolean flag=gjxzwhDao.doCheckGsbh(xzwxb);
	if(flag){
		xzwxb.setZjr(LUser.getRybh());
		int i=gjxzwhDao.doAdd(xzwxb);
		if(i>0){
			doAddOplog(OplogFlag.ADD,"工具下载维护",xzwxb.getGuid());
		}
		return i;
	}else{
		return 0;
	}
		
}
/**查询一条详细信息
 * @param guid
 * @return
 */
public Map getObjectById(String guid){
	return gjxzwhDao.getObjectById(guid);
}
/**删除维修工具信息
 * @param gsbh
 * @return
 */
public int doDel(String guid){
	int result=gjxzwhDao.doDel(guid);
	if(result>0){
		String guids [] =  guid.split(",");
		for (int i=0;i<guids.length;i++){
			doAddOplog(OplogFlag.DEL,"工具下载维护",guids[i]);
		}
	}
	return result;
}
/**修改工具下载维护信息
 * @param xzwxb
 * @return
 */
public int doUpdate(ZC_SYS_TOOLDOWN xzwxb) {
	int i=gjxzwhDao.doUpdate(xzwxb);
	if(i>0){
		doAddOplog(OplogFlag.UPD,"工具下载维护",xzwxb.getGuid());
	}
	return i;

}
/**
 * 查询附件列表
 */
public String getFjList(String guid,String realPath) {
	StringBuffer fjView = new StringBuffer();
	StringBuffer fjConfig = new StringBuffer();
	List<Map> list = gjxzwhDao.getFjList(guid);
	if(list.size()>0){
		for(Map map : list){
			fjView.append("'"+realPath+"/"+map.get("PATH")+"',");
			fjConfig.append("{caption:\""+map.get("FNAME")+"\",showDelete:true,key:\""+realPath+"/"+map.get("PATH")+"@"+map.get("GUID")+"\",size:\""+map.get("DJLX")+"\"},");
		}
		fjView.deleteCharAt(fjView.length()-1);
		fjConfig.deleteCharAt(fjConfig.length()-1);
	}
	return fjView+"#"+fjConfig;
}
}
