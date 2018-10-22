package com.googosoft.service.fzgn.jcsz;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.RyjxbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYJXB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

@Service("ryjxbService")
public class RyjxbService extends BaseService{
	@Resource(name="ryjxbDao")
	private RyjxbDao ryjxbDao;
	/**
	 * 新增
	 * @return
	 */
	public int doAddRyjx(GX_SYS_RYJXB ryjxb){
		ryjxb.setGuid(UuidUtil.get32UUID());
		int result = ryjxbDao.doAddRyjx(ryjxb);
		if(result>0)
		{
			doAddOplog(OplogFlag.ADD,"人员档案维护-进修情况",ryjxb.getRybh());
		}
		return result;
	}
	/**
	 * 获取实体
	 * @param guid
	 * @return
	 */
	public Map getObjectById(String guid) 
	{
		return ryjxbDao.getObjectById(guid);
	}	
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYJXB ryjxb)
	{
	   int result = ryjxbDao.doUpdate(ryjxb);
		if(result>0)
		{
			doAddOplog(OplogFlag.UPD,"人员档案维护-进修情况",ryjxb.getRybh());
		}
		return result;
	 }	
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid)
	{
		int result =ryjxbDao.doDelete(guid);
		if(result>0)
		{
			doAddOplog(OplogFlag.DEL,"人员档案维护-进修情况",guid.split(","));
		}
		return result;
	}
}
