package com.googosoft.service.kjhs.zjz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.RjzExportExcel;
import com.googosoft.controller.cwbb.expExcel.YhckrjzExportExcel;
import com.googosoft.dao.kjhs.rjz.YhckrjzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("yhckrjzService")
public class YhckrjzService extends BaseService {
	@Resource(name = "yhckrjzDao")
	private YhckrjzDao dao;

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

	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return dao.getKjkmJb();
	}
	
	
	public List getKmmj() {
		return dao.getKmmj();
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		dao.deleteKmyeb();
	}
	
	public List<Map<String, Object>> daochu( String sszt,String kmbh,String kjqj,String kkjqj,String kmbhstart,String startJc,String qbjzpz,String yfhjzpz  ) {
		return dao.daochu(sszt,kmbh,kjqj,kkjqj,kmbhstart,startJc,qbjzpz,yfhjzpz);
	}
	
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "银行存款日记账";
		if(Validate.noNull(foo)){
			Title = "银行存款日记账";
		}
		String[] title = new String[] { "凭证日期", "凭证类型","凭证编号", "摘要", "借方金额", "贷方金额", "方向", "余额" };
		Map<String, Object> dataMap = YhckrjzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	public List<Map<String, Object>> getPageList(String sql) {
		return dao.getPageList(sql);
	}

	

}
