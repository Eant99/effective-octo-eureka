package com.googosoft.service.fzgn.wxzf;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.fzgn.wxzf.XfxxExportExcel;
import com.googosoft.dao.base.PageDao;
import com.googosoft.dao.fzgn.wxzf.WxzfDao;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.PageData;
@Service("wxzfService")
public class WxzfService extends BaseService{

	@Resource(name="wxzfDao")
	private WxzfDao dao;
	@Resource(name="pageDao")
	public PageDao pageDao;
	
	public Map getZfxxByGuid(String guid) {
		return dao.getZfxxByGuid(guid);
	}
	
	public int updateZfzt(String guid) {
		return dao.updateZfzt(guid);
	}

	public List getResult(PageData pd) {
		return dao.getResult(pd);
	}
	public String ExpExcel(List list,String file,String filename,List<M_largedata> mlist,List<List<M_largedata>> tlist){
		try {
//			pageDao.ExpExcel(sql,file,filedisplay,mlist,mlist1,mlist2);
			pageDao.ExpExcel(list, file, filename,mlist,tlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 消费记录查询统计导出
	 * @author 作者：BiMing
	 * @version 创建时间:2018-4-14下午3:18:03
	 */
	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String sszt,String zfzt,String zffs,String ywlx,String cbsmc,String xfddmc,String kssj,String jssj) {
		List<Map<String, Object>> list = this.dao.getList(searchValue,guid,sszt,zfzt,zffs,ywlx,cbsmc,xfddmc,kssj,jssj);
		String Title = "消费记录统计查询";
		String[] title = new String[] { "序号", "承包商名称", "窗口名称","所属校区","总计金额（元"};
		Map<String, Object> dataMap = XfxxExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
}
