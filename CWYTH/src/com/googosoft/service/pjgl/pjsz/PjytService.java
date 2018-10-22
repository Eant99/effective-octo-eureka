package com.googosoft.service.pjgl.pjsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.controller.pjgl.pjsz.PjytExportExcel;
import com.googosoft.controller.ysgl.xmsz.XmxxExportExcel;
import com.googosoft.dao.pjgl.pjsz.PjytDao;
import com.googosoft.util.PageData;

@Service("pjytService")
public class PjytService {
	
	@Resource(name="pjytDao")
	private PjytDao pjytDao;


	/**
	 * 查询数据信息
	 * @param guid
	 * @return
	 */
	public Map getPjytMapById(String guid) {
		return pjytDao.getPjytMapById(guid);
	}
	/**
	 * 新增票据用途信息
	 * @param pd
	 * @param sszt
	 * @return
	 */
	public int addPjyt(PageData pd, HttpSession session, String sszt) {
		return pjytDao.addPjyt(pd,session,sszt);
	}
	/**
	 * 修改票据用途信息
	 * @param pd
	 * @return
	 */
	public int editPjyt(PageData pd) {
		return pjytDao.editPjyt(pd);
	}
	/**
	 * 删除票据用途信息
	 * @param pd
	 * @return
	 */
	public int doDelete(String guid) {
		return pjytDao.doDelete(guid);
	}

	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String sszt) {
		List<Map<String, Object>> list = this.pjytDao.getList(searchValue,guid,sszt);
		String Title = "票据用途信息";
		String[] title = new String[] { "序号", "票据用途编码", "票据用途名称","是否启用", "备注" };
		Map<String, Object> dataMap = PjytExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
	public int checkSY(String guid) {
		return pjytDao.checkSY(guid);
	}
	public boolean doCheckDwbh(String guid) {
		return pjytDao.doCheckDwbh(guid);
	}

}
