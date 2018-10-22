package com.googosoft.service.systemset.cssz;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.systemset.cssz.YwsmDao;
import com.googosoft.pojo.systemset.cssz.ZC_YWSM;
import com.googosoft.pojo.systemset.cssz.ZC_YWSMSET;
import com.googosoft.service.base.BaseService;


@Service("ywsmService")
public class YwsmService extends BaseService{

	@Resource(name="ywsmDao")
	public YwsmDao ywsmDao;
	
	public Map getObjectById(String id) {
		return ywsmDao.getObjectById(id);
	}

	public List getMkb() {
		return ywsmDao.getMkb();
	}

	/**
	 * 业务说明增加
	 * @param ywsm
	 * @param content
	 * @return
	 */
	public int doAdd(ZC_YWSM ywsm, String content) {
		Date jdrq = new Date();
		String jdr = LUser.getRybh();
		ywsm.setJdr(jdr);
		ywsm.setJdrq(jdrq);
		boolean flag = ywsmDao.doCheckMkbh(ywsm);
		if(flag){
			return ywsmDao.doAdd(ywsm, content);
		}else {
			return 0;
		}
	}

	/**
	 * 业务说明修改
	 * @param ywsm
	 * @param content
	 * @return
	 */
	public int doUpdate(ZC_YWSM ywsm, String content,String id) {
		return ywsmDao.doUpdate(ywsm, content,id);
	}

	public int doDelete(String id) {
		int result=ywsmDao.doDelete(id);
		if (result > 0){ 
			String ids [] =  id.split(",");
			for (int i=0;i<ids.length;i++){
				doAddOplog(OplogFlag.DEL,"业务说明设置",ids[i]);
			}
		}
		return result;
	}
	
	public int ywSet(String mkbh,String wdxx) {
		if(wdxx.equals("1")){
			return ywsmDao.insertSet(mkbh);
		}else{
			return ywsmDao.deleteSet(mkbh);
		}
	}
	
	public String findWdxx(String mkbh) {
		return ywsmDao.findWdxx(mkbh);
	}
	
	/**
	 * 需要业务说明的模块，点击业务说明按钮弹窗
	 * @param mkbh
	 * @return
	 */
	public Map getObjectByMkbh(String mkbh) {
		return ywsmDao.getObjectByMkbh(mkbh);
	}
	/**是否提示说明
	 * @return
	 */
	public Object upSfts(ZC_YWSMSET ywsmset) {
		return ywsmDao.upSfts(ywsmset);
	}

	public int findSfts(ZC_YWSMSET ywsmset) {
		return ywsmDao.findSfts(ywsmset);
	}
}
