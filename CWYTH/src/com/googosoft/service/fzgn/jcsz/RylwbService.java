package com.googosoft.service.fzgn.jcsz;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.RylwbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYLWB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

@Service("rylwbService")
public class RylwbService extends BaseService {
	
	@Resource(name="rylwbDao")
	private RylwbDao rylwbDao;
	
	/**
	 * 新增
	 * @return
	 */
	public int doAdd(GX_SYS_RYLWB rylwb)
	{
		rylwb.setGuid(UuidUtil.get32UUID());
	    int count = rylwbDao.doAdd(rylwb);
		if(count>0)
		{
			doAddOplog(OplogFlag.ADD,"人员档案维护-论文情况",rylwb.getRybh());
		}
	    return count;
	}
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid)
	{
		int count =rylwbDao.doDelete(guid);
		if(count>0)
		{
			doAddOplog(OplogFlag.DEL,"人员档案维护-论文情况",guid.split(","));
		}
		return count;
	}
	/**
	 * 获取实体
	 * @param guid
	 * @return
	 */
	public Map<String,Object> getObjectById(String guid) {
		return rylwbDao.getObjectById(guid);
	}
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYLWB rylwb)
	{
	   int count = rylwbDao.doUpdate(rylwb);
		if(count>0)
		{
			doAddOplog(OplogFlag.UPD,"人员档案维护-论文情况",rylwb.getRybh());
		}
		return count;
	 }
}
