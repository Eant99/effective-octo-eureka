package com.googosoft.service.kjhs.bbzx;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.CzbzsrzcExcel;
import com.googosoft.controller.cwbb.expExcel.YebExportExcel;
import com.googosoft.dao.kjhs.bbzx.ZjszyebDao;
import com.googosoft.util.PageData;

@Service("zjszyebService")
public class ZjszyebService {
	@Resource(name="zjszyebDao")
	private ZjszyebDao dao;
	@Resource(name="yebExportExcel")
	private YebExportExcel yebExportExcel;
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		return dao.getMonth();
	}
	/**
	 * 获取资金收支余额表Map
	 * @param pd
	 * @param sszt 
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getResultMap(PageData pd, String sszt) {
		return dao.getResultMap(pd,sszt);
	}
	public Object expExcel(String realfile, String shortfileurl, PageData pd, String sszt) {
		List<Map<String, Object>> list =dao.getListCzbzsrzc(pd,sszt);
		String Title = "资金收入支出余额表";
		String[] title = new String[] { "序号","项目", "余额"};
		Map<String, Object> dataMap = yebExportExcel.ZjszExportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
}
