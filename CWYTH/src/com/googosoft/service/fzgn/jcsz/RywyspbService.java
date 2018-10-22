package com.googosoft.service.fzgn.jcsz;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.RywyspbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYWYSPB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

@Service("rywyspbService")
public class RywyspbService extends BaseService{
	@Resource(name="rywyspbDao")
	private RywyspbDao rywyspbDao;
	

	/**
	 * 新增
	 * @return
	 */
	public int doAdd(GX_SYS_RYWYSPB rywyspb){
		rywyspb.setGuid(UuidUtil.get32UUID());
	   int i = rywyspbDao.doAdd(rywyspb);
		if(i>0){
				doAddOplog(OplogFlag.ADD,"人员档案维护-外语水平",rywyspb.getRybh());
			}
			return i;
	   }
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYWYSPB rywyspb){
	   int i = rywyspbDao.doUpdate(rywyspb);
		if(i>0){
				doAddOplog(OplogFlag.UPD,"人员档案维护-外语水平",rywyspb.getRybh());
			}
			return i;
	   }
	/**
	 * 获取实体类
	 * @param guid
	 * @return
	 */
	public Map getObjectById(String guid) {
		return rywyspbDao.getObjectById(guid);
	}

	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid){
		int i =rywyspbDao.doDelete(guid);
		if(i>0){
			doAddOplog(OplogFlag.DEL,"人员档案维护-外语水平",guid.split(","));
		}
		return i;
	}
	

}
