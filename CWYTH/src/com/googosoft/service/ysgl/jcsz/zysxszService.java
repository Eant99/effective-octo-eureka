package com.googosoft.service.ysgl.jcsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.ysgl.grjfsz.GrjfszDao;
import com.googosoft.dao.ysgl.jcsz.zysxszDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("zysxszService")
public class zysxszService extends BaseService{
	
	@Resource(name="zysxszDao")
	public zysxszDao zysxszDao;

	/**
	 * 删除
	 * @param pd
	 * @return 
	 */
	public int doDelete(PageData pd) {
		return zysxszDao.doDelete(pd);
	}
	
	/**
	 * 修改
	 * @param pd   
	 * @return 
	 */
	public int editZysx(PageData pd,String check) {
		return zysxszDao.updateZysx(pd,check);
	}
	
	/**
	 * 增加
	 * @param pd   
	 * @return 
	 */
	public int addZysx(PageData pd,String guid) {
		return zysxszDao.insertZysx(pd,guid);
	}
	
	/**
	 * 结转信息（查看）：获取结转的详细信息
	 * 
	 * @param jzbh
	 * @return
	 */
	public Map<String, Object> getzysxMapById(String guid){
		return zysxszDao.selectzysxMapById(guid);
	}
	
	/**
	 * 展示
	 * @param pd
	 * @return 
	 */
	public int Xs(String guid) {
		return zysxszDao.Xs(guid);
	}
	
	/**
	 * 不展示
	 * @param pd
	 * @return 
	 */
	public int Bxs(String guid) {
		return zysxszDao.Bxs(guid);
	}
}
