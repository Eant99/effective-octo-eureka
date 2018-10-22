package com.googosoft.service.systemset.xtsz;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.xtsz.YxszDao;
import com.googosoft.pojo.systemset.cssz.ZC_HSORTSET_EXTEND;
import com.googosoft.pojo.systemset.cssz.ZC_SYS_XTB;
import com.googosoft.service.base.BaseService;

@Service("yxszService")
public class YxszService extends BaseService{
	
	@Resource(name="yxszDao")
	private YxszDao yxszDao;
	
	public Map getObjectById() {
		return yxszDao.getObjectById();
	}
	
	public int doUpdate(ZC_SYS_XTB xtb) {
		 int result = yxszDao.doUpdate(xtb);
		 if(result>0){
			 doAddOplog(OplogFlag.UPD,"邮箱信息设置",xtb.getGuid());
		 }
		 return result;
	}

}
