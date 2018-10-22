package com.googosoft.service.fzgn.jcsz;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.RycgjlbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYCGJLB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;

@Service("rycgjlbService")
public class RycgjlbService extends BaseService{
	
	@Resource(name="rycgjlbDao")
	private RycgjlbDao rycgjlbDao;
	
	/**
	 * 添加人员成果奖励
	 * @return
	 */
	public int doAddRycgjl(GX_SYS_RYCGJLB rycgjlb){
		rycgjlb.setGuid(UuidUtil.get32UUID());
	   int result = rycgjlbDao.doAddRycgjl(rycgjlb);
		if(result>0){
				doAddOplog(OplogFlag.ADD,"人员档案维护-成果奖励",rycgjlb.getRybh());
			}
			return result;
	   }
	
	/**
	 * 删除人员成果奖励信息
	 * @return
	 */
	public int doDelete(String guid){
		int result =rycgjlbDao.doDelete(guid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"人员档案维护-成果奖励",guid.split(","));
		}
		return result;
	}
	/**
	 * 获取实体
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		return rycgjlbDao.getObjectById(guid);
	}
	
	/**
	 * 修改人员成果奖励
	 * @return
	 */
	public int doUpdate(GX_SYS_RYCGJLB rycgjlb){
	   int result = rycgjlbDao.doUpdate(rycgjlb);
	   if(result>0){
		   doAddOplog(OplogFlag.UPD,"人员档案维护-成果奖励",rycgjlb.getRybh());
	   }
	   return result;
	}

}
