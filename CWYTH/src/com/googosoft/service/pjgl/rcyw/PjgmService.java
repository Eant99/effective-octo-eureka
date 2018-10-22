package com.googosoft.service.pjgl.rcyw;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.pjgl.rcyw.PjgmDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
/**
 * 票据购买service
 * @author 张春燕
 * @param session
 * @return
 */
@Service("pjgmService")
public class PjgmService extends BaseService {
	
	@Resource(name="pjgmDao")
	private PjgmDao pjgmDao;

	/**
	 * 向票据购买总表中插入数据
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	public int doAdd(PageData pd, String loginId, String rybh) {
		return pjgmDao.doAdd(pd,loginId,rybh);
	}
	/**
	 * 删除票据信息包括总表和明细表中的信息
	 * @author 张春燕
	 * @param session
	 * @return
	 */
	@Transactional
	public int doDelete(String guid) {
		if(pjgmDao.doDelete(guid)){
			return 1;
		}
		return -1;
	}
	/**
	 * 检查票据是否被领用
	 * @author jiatong 2018年3月25日12:47:38
	 * @param session
	 * @return
	 */
	public boolean doCheckused(String guid) {
		return pjgmDao.doCheckused(guid);
	}
	
}
