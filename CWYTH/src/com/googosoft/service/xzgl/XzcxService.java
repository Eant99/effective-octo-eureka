package com.googosoft.service.xzgl;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Service;
import com.googosoft.dao.xzgl.XzglDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;

@Service("xzcxService")
public class XzcxService extends BaseService{

	@Resource(name="xzglDao")
	private XzglDao xzglDao;

	/**
	 * 在职薪资查询
	 * @param pd 
	 * @return
	 */
	public List<Map<String, Object>> getXzList(PageData pd) {
		return xzglDao.getXzList(pd);
	}

	/**
	 * 清空薪资表
	 * @return
	 */
	public int doDelXz() {
		return xzglDao.doDelXz();
	}
	/**
	 * 在职薪资导入
	 * @param file
	 * @return
	 */
	public String insertXz(String file) {
		return xzglDao.insertXz(file);
	}

	/**
	 * 在职薪资保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return xzglDao.doSave(list);
	}

	/**
	 * 离职薪资查询
	 * @param pd 
	 * @return
	 */
	public  List<Map<String, Object>> getLzxzList(PageData pd) {
		return xzglDao.getLzxzList(pd);
	}

	/**
	 *  离职薪资导入
	 * @param file
	 * @return
	 */
	public String insertLzxz(String file) {
		return xzglDao.insertLzxz(file);
	}

	/**
	 * 离职薪资保存
	 * @param list
	 * @return
	 */
	public boolean doSaveLz(List list) {
		return xzglDao.doSaveLz(list);
	}

	/**
	 * 清空离职临时表
	 * @return
	 */
	public int doDelLzxz() {
		return xzglDao.doDelLzxz();
	}

	/**
	 * 在职薪资核算
	 * @return
	 */
	public int doHs() {
//		return xzglDao.doHs();
		return 0;
	}
	/**
	 * 在职薪资提交
	 * @return
	 */
//	public int doTj(String guid) {
//		return xzglDao.doTj(guid);
//	}

	/**
	 * 在职薪资导入数据确认
	 * @param file
	 * @return
	 */
	public List<Map<String, Object>> getXzImpeQr(String file) {
		return xzglDao.getXzImpeQr(file);
	}
	/**
	 * 离职薪资导入数据确认
	 * @param file
	 * @return
	 */
	public List<Map<String, Object>> getLzxzImpeQr(String file) {
		return xzglDao.getLzxzImpeQr(file);
	}

	/**
	 * 离职薪资提交
	 */
//	public int doLzTj(String guid) {
//		return xzglDao.doLzTj(guid);
//	}

	
	
}
