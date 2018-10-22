/**
 * 
 */
package com.googosoft.service.wsbx;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.wsbx.ShyjDao;
import com.googosoft.pojo.wsbx.Cw_Shyj;

/**
 * @author lifei
 * @date 2018-4-3
 * @title ShyjDao.java
 */
@Service("ShyjService")
public class ShyjService extends BaseDao {

	@Resource(name = "ShyjDao")
	public ShyjDao ShyjDao;

	/**
	 * 增加
	 * 
	 * @param dwb
	 * @return
	 */
	public int doAdd(Cw_Shyj po) {
		return ShyjDao.doAdd(po);
	}

	/**
	 * 修改
	 * 
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public int doUpdate(Cw_Shyj po) {
		return ShyjDao.doUpdate(po);
	}

	/**
	 * 删除
	 * 
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public int doDelete(String guid) {
		return ShyjDao.doDelete(guid);
	}
	
	/**
	 * 信息查询
	 * @param guid
	 * @return
	 */
	public Map getMapINfoByGuid(String guid){
		return ShyjDao.getMapINfoByGuid(guid);
	}

}
