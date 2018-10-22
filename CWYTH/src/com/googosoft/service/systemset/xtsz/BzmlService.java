package com.googosoft.service.systemset.xtsz;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.xtsz.BzmLDao;
import com.googosoft.dao.systemset.xtsz.BzxxDao;
import com.googosoft.pojo.systemset.xtsz.ZC_BZML;
import com.googosoft.service.base.BaseService;

@Service("bzmlService")
public class BzmlService extends BaseService{
	
	@Resource(name="bzmlDao")
	private BzmLDao bzmlDao;
	
	@Resource(name="bzxxDao")
	private BzxxDao bzxxDao;
	/**
	 * 获取帮助目录的详细信息
	 */
	public Map getObjectById(String bh) {
		return bzmlDao.getObjectById(bh);
	}
	
	/**
	 * 添加目录信息
	 */
	public int doAdd(ZC_BZML bzml){
	   int i = bzmlDao.doAdd(bzml);
	   if(i>0){
			doAddOplog(OplogFlag.ADD,"帮助目录维护",bzml.getBh());
		}
		return i;
	 }
	/**
	 * 修改目录信息
	 */
	public int doUpdate(ZC_BZML bzml){
	   int i = bzmlDao.doUpdate(bzml);
	   if(i>0){
			doAddOplog(OplogFlag.UPD,"帮助目录维护",bzml.getBh());
		}
		return i;
	 }
	/**
	 * 删除目录维护信息
	 */
	public int doDelete(String bh) {
		boolean flag = bzxxDao.doCheckBzxx(bh);
		if(flag){
			int result=bzmlDao.doDelete(bh);
			if(result>0){
				doAddOplog(OplogFlag.DEL,"帮助目录维护",bh);
			}
			return result;
		}else{
			return 0;
		}
		
	}
}
