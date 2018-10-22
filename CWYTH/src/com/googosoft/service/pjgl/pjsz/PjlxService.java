package com.googosoft.service.pjgl.pjsz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.googosoft.controller.ysgl.xmsz.XmxxExportExcel;
import com.googosoft.dao.pjgl.pjsz.PjlxDao;
import com.googosoft.util.PageData;

@Service("pjlxService")
public class PjlxService {
	
	@Resource(name="pjlxDao")
	private PjlxDao pjlxDao;


	/**
	 * 查询数据信息
	 * @param guid
	 * @return
	 */
	public Map getPjlxMapById(String guid) {
		return pjlxDao.getPjlxMapById(guid);
	}
	/**
	 * 新增票据用途信息
	 * @param pd
	 * @param sszt
	 * @return
	 */
	public int addPjlx(PageData pd, String sszt) {
		return pjlxDao.addPjlx(pd,sszt);
	}
	/**
	 * 修改票据用途信息
	 * @param pd
	 * @return
	 */
	public int editPjlx(PageData pd) {
		return pjlxDao.editPjlx(pd);
	}
	/**
	 * 删除票据用途信息
	 * @param pd
	 * @return
	 */
	public int doDelete(String guid) {
		return pjlxDao.doDelete(guid);
	}

	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String sszt) {
		List<Map<String, Object>> list = this.pjlxDao.getList(searchValue,guid,sszt);
		String Title = "项目信息";
		String[] title = new String[] { "序号", "项目编号", "部门名称","项目名称", "项目大类" ,"项目类别", "项目类型","负责人","项目属性","归口部门","是否启用" };
		Map<String, Object> dataMap = XmxxExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}

}
