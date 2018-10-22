package com.googosoft.service.cwbb;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.controller.cwbb.expExcel.XjllbExportExcel;
import com.googosoft.dao.cwbb.XjllbDao;
import com.googosoft.service.base.BaseService;

@Service("XjllbService")
public class XjllbService extends BaseService {
	@Resource(name = "XjllbDao")
	private XjllbDao XjllbDao;

	/**
	 * 编制单位信息
	 * 
	 * @return
	 */
	public Map<String, Object> getBzdw() {
		return XjllbDao.getBzdw();
	}
	
	
	/**
	 * 调用存储过程返回list
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @param jzpz
	 * @param bzdw
	 * @return
	 *///year, jzpz, sszt, bzdw,LUser.getRybh()
	public List<Map<String, Object>> getResultListByPro(String year,String jzpz,String sszt,Map bzdw) {
		return XjllbDao.getResultListByPro(year, jzpz, sszt, bzdw);
	} 
	
	/**
	 * 保存
	 * @param list
	 * @return
	 */
	public boolean doSave(List list){
		return XjllbDao.doSave(list);
	}
	
	/**
	 * 检查表中是否已经存在数据
	 * 
	 * @param sszt
	 * @param year
	 * @param jzpz
	 * @return
	 */
	public int check(String sszt, String year, String jzpz) {
		return XjllbDao.check(sszt, year, jzpz);
	}
	
	/**
	 * 数据集的查询
	 * 
	 * @param sszt
	 * @param year
	 * @param jzpz
	 * @return
	 */
	public List getDatasList(String sszt, String year, String jzpz) {
		return XjllbDao.getDatasList(sszt, year, jzpz);
	}
	public List getDatasListByPro(String year,String jzpz,String sszt,Map bzdw) {
		return XjllbDao.getDatasListByPro(year, jzpz, sszt, bzdw);
	}


	public Object expExcel(String realfile, String shortfileurl,String searchValue) {
		List<Map<String, Object>> list = this.XjllbDao.getList();
		String Title = "现金流量表";
		String[] title = new String[] { "序号", "项目","本年金额","上年金额" };
		Map<String, Object> dataMap = XjllbExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}

	
	
}
