package com.googosoft.service.kjhs.zjz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.LyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.YhckrjzExportExcel;
import com.googosoft.dao.kjhs.bbzx.XjrjzDao;
import com.googosoft.dao.kjhs.rjz.KcxjrjzDao;
import com.googosoft.dao.kjhs.rjz.LyerjzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("lyerjzService")
public class LyerjzService extends BaseService {
	@Resource(name = "lyerjzDao")
	private LyerjzDao dao;

	public boolean runPro(String proName,List list) throws SQLException{
		return dao.runPro(proName,list);
	}
	
	
	
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		return dao.getMonth();
	}

	
	public List getKmmj() {
		return dao.getKmmj();
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return dao.getKjkmJb();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		dao.deleteKmyeb();
	}
	public List<Map<String, Object>> daochu( String sszt,String kmbh) {
		return dao.daochu(sszt,kmbh);
	}
	
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "零余额日记账";
		if(Validate.noNull(foo)){
			Title = "零余额日记账";
		}
		String[] title = new String[] { "凭证日期", "凭证编号", "摘要", "借方金额", "贷方金额", "方向", "余额" };
		Map<String, Object> dataMap = LyeExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	public List<Map<String, Object>> getPageList(String sql) {
		return dao.getPageList(sql);
	}


}
