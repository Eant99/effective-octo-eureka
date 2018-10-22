package com.googosoft.service.pjgl.pjsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.pjgl.pjsz.PjytExportExcel;
import com.googosoft.controller.ysgl.xmsz.XmxxExportExcel;
import com.googosoft.dao.pjgl.pjsz.PjzhDao;
import com.googosoft.util.PageData;


@Service("pjzhService")
public class PjzhService {
	
	@Resource(name="pjzhDao")
	private PjzhDao pjzhDao;
	

	/**
	 * 查询数据信息
	 * @param guid
	 * @return
	 */
	public Map getPjytMapById(String guid) {
		return pjzhDao.getPjytMapById(guid);
	}
	/**
	 * 新增票据用途信息
	 * @param pd
	 * @param sszt
	 * @return
	 */
	public int addPjyt(PageData pd, String sszt) {
		return pjzhDao.addPjyt(pd,sszt);
	}
	/**
	 * 修改票据用途信息
	 * @param pd
	 * @return
	 */
	public int editPjyt(PageData pd) {
		return pjzhDao.editPjyt(pd);
	}
	/**
	 * 删除票据用途信息
	 * @param pd
	 * @return
	 */
	public int doDelete(String guid) {
		return pjzhDao.doDelete(guid);
	}

	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String sszt) {
		List<Map<String, Object>> list = this.pjzhDao.getList(searchValue,guid,sszt);
		String Title = "票据账号信息";
		String[] title = new String[] { "序号", "账户名称", "票据类型","是否启用" };
		Map<String, Object> dataMap = new PjytExportExcel().exportExcel2(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	public int checkSY(String guid) {
		return pjzhDao.checkSY(guid);
	}


}
