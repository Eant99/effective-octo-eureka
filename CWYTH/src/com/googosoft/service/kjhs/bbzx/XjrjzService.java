package com.googosoft.service.kjhs.bbzx;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.RjzExportExcel;
import com.googosoft.dao.kjhs.bbzx.XjrjzDao;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("xjrjzService")
public class XjrjzService extends BaseService {
	@Resource(name = "xjrjzDao")
	private XjrjzDao dao;

	public boolean runPro(String proName,List list) throws SQLException{
		return dao.runPro(proName,list);
	}
	
	
	public List<Map<String, Object>> getPageList(String treesearch,String kmbh,String zy,String pzbh,String pzrq1,String pzrq2) {
		return dao.getPageList(treesearch,kmbh,zy,pzbh,pzrq1,pzrq2);
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
		String Title = "日记账";
		if(Validate.noNull(foo)){
			Title = "日记账";
		}
		String[] title = new String[] { "凭证日期","凭证类型", "凭证编号", "摘要", "借方金额", "贷方金额", "方向", "余额" };
		Map<String, Object> dataMap = RjzExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt) {
		return dao.getResult(list, sszt);
	}
}
