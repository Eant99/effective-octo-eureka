package com.googosoft.service.fzgn.tzgg;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.tzgg.FbxxDao;
import com.googosoft.pojo.fzgn.tzgg.ZC_SYS_XTXX;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

/**
 * 通知公告service
 * @author googosoft
 *
 */
@Service("fbxxService")
public class FbxxService extends BaseService{
	
	@Resource(name="fbxxDao")
	private FbxxDao fbxxDao;
	
	/**
	 * 通知公告保存
	 * @param xtxx
	 * @param content
	 * @return
	 */
	public int doAdd(ZC_SYS_XTXX xtxx,String content)  {
		xtxx.setGid( UuidUtil.get32UUID());
		String fbr = LUser.getRybh();
		xtxx.setFbr(fbr);
		int result = fbxxDao.doAdd(xtxx,content);
		if(result>0){
			doAddOplog(OplogFlag.ADD,"发布系统消息",xtxx.getGid());
		}
		return result;
	}
	
	/**
	 * 通知公告修改
	 * @param xtxx
	 * @param content
	 * @param gid
	 * @return
	 */
	public int doUpdate(ZC_SYS_XTXX xtxx,String content,String gid)  {
		int result = fbxxDao.doUpdate(xtxx,content,gid);
		if(result>0){
			doAddOplog(OplogFlag.UPD,"发布系统消息",gid);
		}
		return result;
	}
	
	/**
	 * 删除通知公告
	 * @param gid
	 * @return
	 */
	public int doDelete(String gid) {
		int result=fbxxDao.doDelete(gid);
		if (result > 0){ 
			doAddOplog(OplogFlag.DEL,"发布系统消息",gid);
		}
		return result;
	}
	
	/**
	 * 根据主键查询通知公告详情信息
	 * @param gid
	 * @return
	 */
	public Map getObjectById(String gid) {
		return fbxxDao.getObjectById(gid);
	}
	/**
	 * 增加阅读通知信息
	 */
	public int goBjxx(String bh) {
		boolean flag = fbxxDao.doCheckRy(bh);//先检验是否已阅读
		if(flag){
		int result=fbxxDao.goBjxx(bh);
		if (result > 0){ 
			doAddOplog(OplogFlag.ADD,"阅读通知公告信息",bh);
		}
		return result;
	 }else{
			return 0;
		}
	}
}
