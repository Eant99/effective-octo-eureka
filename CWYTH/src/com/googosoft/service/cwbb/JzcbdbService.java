package com.googosoft.service.cwbb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.LUser;
import com.googosoft.controller.cwbb.expExcel.JzcbdbExportExcel;
import com.googosoft.dao.cwbb.JzcbdbDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("jzcbdbService")
public class JzcbdbService extends BaseService {
	@Resource(name = "jzcbdbDao")
	private JzcbdbDao jzcbdbDao;

	/**
	 * 编制单位信息
	 * 
	 * @return
	 */
	public Map<String, Object> getBzdw() {
		return jzcbdbDao.getBzdw();
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
		return jzcbdbDao.check(sszt, year, jzpz);
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
		return jzcbdbDao.getDatasList(sszt, year, jzpz);
	}

	/**
	 * 数据集的查询
	 * 
	 * @param sszt
	 * @param year
	 * @param jzpz
	 * @return
	 */
	public List getBeforeDatasList(String sszt, String year, String jzpz) {
		return jzcbdbDao.getDatasList(sszt, year, jzpz);
	}

	public List getDatasListByPro(String sszt, String year, String jzpz,
			String bzdw) {
		return jzcbdbDao.getDatasListByPro(sszt, year, jzpz, bzdw);
	}

	/**
	 * 保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean doSave(List list) {
		return jzcbdbDao.doSave(list);
	}

	public boolean doSaveByExp(List list) {
		return jzcbdbDao.doSaveByExp(list);
	}

	/**
	 * 更新本年度
	 * 
	 * @param list
	 */
	public boolean updateBn(List list) {
		return jzcbdbDao.updateBn(list);
	}

	/**
	 * 更新上年度
	 * 
	 * @param list
	 */
	public boolean updateSn(List list) {
		return jzcbdbDao.updateSn(list);
	}
	
	/**
	 * 删除临时表的数据
	 */
	public void delete(){
		jzcbdbDao.delete();
	}

	public Object expExcel(String realfile, String shortfileurl,String searchValue ,String sql) {
		List<Map<String, Object>> dwList = this.jzcbdbDao.getList(searchValue,sql);
		String Title = "净资产流动表";
		String[] title = new String[] { "序号", "项目", "本年累积盈余", "本年专用基金","本年权益法调整" ,"本年净资产合计","上年累积盈余","上年专用基金","上年权益法调整","上年净资产合计" };
		Map<String, Object> dataMap = JzcbdbExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
