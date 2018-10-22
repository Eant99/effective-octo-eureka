package com.googosoft.service.systemset.xtsz;

import java.sql.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.xtsz.BzxxDao;
import com.googosoft.pojo.systemset.xtsz.ZC_BZXX;
import com.googosoft.service.base.BaseService;

@Service("bzxxService")
public class BzxxService extends BaseService{
	
	@Resource(name="bzxxDao")
	private BzxxDao bzxxDao;
	
	public Map getObjectById(String id) {
		return bzxxDao.getObjectById(id);
	}
	
	public Map getObjectByBh(String bzdm) {
		return bzxxDao.getObjectByBh(bzdm);
	}
	
	public List getMlbh() {
		return bzxxDao.getMlbh();
	}
	
	/**
	 * 帮助信息增加
	 * @param bzxx
	 * @param content
	 * @return
	 */
	public int doAdd(ZC_BZXX bzxx, String content) {
		Date jdrq = new Date(0);
		String jdr = LUser.getRybh();
		bzxx.setJdr(jdr);
		bzxx.setJdrq(jdrq);
		boolean flag = bzxxDao.doCheckMlbh(bzxx);
		if(flag){
			return bzxxDao.doAdd(bzxx, content);
		}else {
			return 0;
		}
	}

	/**
	 * 修改帮助信息
	 */
	public int doUpdate(ZC_BZXX bzxx,String content,String id){
	   int i = bzxxDao.doUpdate(bzxx,content,id);
	   if(i>0){
			doAddOplog(OplogFlag.UPD,"帮助信息维护",bzxx.getId());
		}
		return i;
	 }
	/**
	 * 删除帮助信息
	 */
	public int doDelete(String id) {
		int result=bzxxDao.doDelete(id);
		if (result > 0){ 
			doAddOplog(OplogFlag.DEL,"帮助信息维护",id);
		}
		return result;
	}
}
