package com.googosoft.service.fzgn.grsds;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.dao.fzgn.grsds.GrsdsceljDao;
import com.googosoft.service.base.BaseService;


/**
 * 个人所得税超额累进税率表service
 * @author googosoft
 *
 */
@Service("grsdsceljService")
public class GrsdsceljService extends BaseService {

	@Resource(name="grsdsceljDao")
	private GrsdsceljDao grsdsceljDao;
	
	
	/**
	 * 个人所得税超额累进税率表列表
	 * @return
	 */
	public List getPageList() {
		return grsdsceljDao.getPageList();
	}
	
	/**
	 * 个人所得税超额累进税率表保存
	 * @param qyynsjcs
	 * @param qyynsbzls
	 * @param qyynsbzhs
	 * @param sls
	 * @param sskcss
	 * @return
	 */
	@Transactional
	public int dogrsdsSave(String [] qyynsjcs,String [] qyynsbzls,String[] qyynsbzhs,String[] sls,String[] sskcss) {
		return grsdsceljDao.dogrsdsSave(qyynsjcs,qyynsbzls,qyynsbzhs,sls,sskcss);
	}
}
