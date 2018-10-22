package com.googosoft.service.fzgn.tzgg;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.tzgg.TzxxDao;
import com.googosoft.pojo.fzgn.tzgg.ZC_SYS_TZXX;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

@Service("tzxxService")
public class TzxxService extends BaseService{
	
	@Resource(name="tzxxDao")
	private TzxxDao tzxxDao;
	
	public int doAdd(ZC_SYS_TZXX xtxx,String content)  {
		xtxx.setDwbh(CommonUtil.getDwXx(xtxx.getDwbh()));
		xtxx.setGid( UuidUtil.get32UUID());
		String fbr = LUser.getRybh();
		xtxx.setFbr(fbr);
		int result = tzxxDao.doAdd(xtxx,content);
		if(result>0){
			doAddOplog(OplogFlag.ADD,"发布系统消息",xtxx.getGid());
		}
		return result;
	}
	
	public int doUpdate(ZC_SYS_TZXX xtxx,String content,String gid)  {
		xtxx.setDwbh(CommonUtil.getDwXx(xtxx.getDwbh()));
		int result = tzxxDao.doUpdate(xtxx,content,gid);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"发布系统消息",gid);
		}
		return result;
	}
	
	public int doDelete(String gid) {
		int result=tzxxDao.doDelete(gid);
		if (result > 0){ 
			doAddOplog(OplogFlag.DEL,"发布系统消息",gid);
		}
		return result;
	}
	public Map getObjectById(String gid) {
		return tzxxDao.getObjectById(gid);
	}
	/**
	 * 增加阅读通知信息
	 */
	public int goBjxx(String bh) {
		boolean flag = tzxxDao.doCheckRy(bh);//先检验是否已阅读
		if(flag){
		int result=tzxxDao.goBjxx(bh);
		if (result > 0){ 
			doAddOplog(OplogFlag.ADD,"阅读通知公告信息",bh);
		}
		return result;
	 }else{
			return 0;
		}
	}
}
