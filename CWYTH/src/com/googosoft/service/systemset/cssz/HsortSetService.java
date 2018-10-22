package com.googosoft.service.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.HsortSetDao;
import com.googosoft.pojo.systemset.cssz.ZC_HSORTSET_EXTEND;
import com.googosoft.service.base.BaseService;

@Service("hsortsetService")
public class HsortSetService extends BaseService{
	
	@Resource(name="hsortsetDao")
	private HsortSetDao hsortsetDao;
	
	/**
	 * 根据主键获取修改页面的详细信息
	 */
	public Map getObjectById(String zbh) {
		return hsortsetDao.getObjectById(zbh);
	}
	
	/**
	 * 资产类别 : 表hsort
	 */
	public List getZclb() {
		return hsortsetDao.getZclb();
	}

	public int doAdd(ZC_HSORTSET_EXTEND hsortset,String dlhArr,String dlhOld) {
		//验证组编号是否重复
		if(hsortsetDao.checkZbh(hsortset).equals("0")){
		    int result = hsortsetDao.doAdd(hsortset,dlhArr,dlhOld);
			if(result>0){
				doAddOplog(OplogFlag.ADD,"资产编号保留位设置",hsortset.getZbh());
			}
		    return result;
		}else{
			return 2;
		}
	}

	public int doUpdate(ZC_HSORTSET_EXTEND hsortset,String dlhArr,String dlhOld) {
		 int result = hsortsetDao.doUpdate(hsortset,dlhArr,dlhOld);
		 if(result>0){
			 doAddOplog(OplogFlag.UPD,"资产编号保留位设置",hsortset.getZbh());
		 }
		 return result;
	}

	public int doDelete(String zbh) {
		int result = hsortsetDao.doDelete(zbh);
		if(result>0){
			String zbhs [] =  zbh.split(",");
			for (int i=0;i<zbhs.length;i++){
				doAddOplog(OplogFlag.DEL,"资产编号保留位设置",zbhs[i]);
			}
		}
		return result;
	}

	public Map getDlhById(String zbh) {
		return hsortsetDao.getDlhById(zbh);
	}
}
