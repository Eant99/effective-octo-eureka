package com.googosoft.service.systemset.cssz;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.CyflszDao;
import com.googosoft.pojo.systemset.cssz.ZC_CYFLSZB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.AutoKey;

@Service("cyflszService")
public class CyflszService extends BaseService{
	
	@Resource(name = "cyflszDao")
	private CyflszDao cyflszDao;
	/**
	 * 常用分类删除
	 */
	public int doDelete(String gid) {
		int result = cyflszDao.doDelete(gid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"常用分类设置",gid);
		}
		return result;
	}
	public boolean doAdd(ZC_CYFLSZB tpb) {
		tpb.setGid(AutoKey.makeCkbh("ZC_CYFLSZB", "GID", "4"));
		int i = (int) cyflszDao.doAdd(tpb);
		if (i == 1){
			doAddOplog(OplogFlag.ADD,"常用分类设置",tpb.getGid());
			return true;
		}else{
			return false;
		}
	}
	public Map getCyflszxx(String gid){
		return cyflszDao.getCyflszxx(gid);
	}
	public boolean doUpdate(ZC_CYFLSZB tpb) {
		int i = (int) cyflszDao.doUpdate(tpb);
		if(i == 1){
			doAddOplog(OplogFlag.UPD,"常用分类设置",tpb.getGid());
			return true;
		}else{
			return false;
		}
	}
	public boolean doCheckmc(String mc,String lx) {
		return cyflszDao.doCheckmc(mc,lx);
	}
	public boolean doCheck(String lx,String operateType) {
		return cyflszDao.doCheck(lx,operateType);
	}
	public Map findPage() {
		return cyflszDao.findPage();
	}
}