package com.googosoft.service.fzgn.jcsz;

import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.fzgn.jcsz.RyzzbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYZZB;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.UuidUtil;
/**
 * 人员著作情况service
 * @author master
 */
@Service("ryzzbService")
public class RyzzbService extends BaseService{
	
	@Resource(name="ryzzbDao")
	private RyzzbDao ryzzbDao;
	

	
	/**
	 * 添加
	 * @return
	 */
	public int doAddRylw(GX_SYS_RYZZB ryzzb){
		ryzzb.setGuid(UuidUtil.get32UUID());
	   int result = ryzzbDao.doAddRyzz(ryzzb);
		if(result>0){
				doAddOplog(OplogFlag.ADD,"人员档案维护-著作情况",ryzzb.getRybh());
			}
			return result;
	   }
	/**
	 * 获取实体
	 * @param guid
	 * @return
	 */
	public Map getObjectById(String guid) {
		return ryzzbDao.getObjectById(guid);
	}
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid){
		int result =ryzzbDao.doDelete(guid);
		if(result>0){
			doAddOplog(OplogFlag.DEL,"人员档案维护-著作情况",guid.split(","));
		}
		return result;
	}
	
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYZZB ryzzb){
	   int result = ryzzbDao.doUpdate(ryzzb);
		if(result>0){
				doAddOplog(OplogFlag.UPD,"人员档案维护-著作情况",ryzzb.getRybh());
			}
			return result;
	   }

}
