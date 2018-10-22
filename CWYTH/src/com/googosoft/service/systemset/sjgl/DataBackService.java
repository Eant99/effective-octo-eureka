package com.googosoft.service.systemset.sjgl;

import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.sjgl.DataBackDao;
import com.googosoft.service.base.BaseService;

@Service("dataBackService")
public class DataBackService extends BaseService{
	@Resource(name="dataBackDao")
	public DataBackDao dataBackDao;
	
	public int getZxbf() {
		int result = dataBackDao.getZxbf();
		if(result > 0){
			doAddOplog(OplogFlag.BF,"数据备份","");
		}
			return result;
	 }
	public int getBfmc(String bfmc){
		int result = dataBackDao.getBfmc(bfmc);
		return result;
	}

}
