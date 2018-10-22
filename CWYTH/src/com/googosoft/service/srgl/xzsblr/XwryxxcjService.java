package com.googosoft.service.srgl.xzsblr;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.srgl.XwryxxcjExportExcl;
import com.googosoft.dao.srgl.xzsblr.XwryxxcjDao;
import com.googosoft.pojo.srgl.xzsblr.CW_XWRYXXB;


@Service("xwryxxcjService")
public class XwryxxcjService {
	
	@Resource(name = "xwryxxcjDao")
	private XwryxxcjDao xwryxxcjDao;


	public int doAdd(CW_XWRYXXB xwry) {
		return xwryxxcjDao.doAdd(xwry);
	}
	public int doDelete2(String bzbh) {
		return xwryxxcjDao.doDelete2(bzbh);
	}
	public int doDelete(String bzbh) {
		return xwryxxcjDao.doDelete(bzbh);
	}
	public int doUpdate(CW_XWRYXXB xwry) {
		return xwryxxcjDao.doUpdate(xwry);
	}
	public Map<?, ?> getObjectById(String bzbh) {
		return xwryxxcjDao.getObjectById(bzbh);
	}
	
	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String sszt) {
		List<Map<String, Object>> list = this.xwryxxcjDao.getList(searchValue,guid,sszt);
		String Title = "校外人员信息采集";
		String[] title = new String[] { "序号", "姓名", "性别","出生日期", "身份证件类型","身份证件号" ,"银行名称","开户行账号","联行号"};
		Map<String, Object> dataMap = XwryxxcjExportExcl.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
}
