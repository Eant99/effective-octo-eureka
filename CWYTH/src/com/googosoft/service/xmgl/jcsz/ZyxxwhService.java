package com.googosoft.service.xmgl.jcsz;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.OplogFlag;
import com.googosoft.controller.kjhs.PzmbExportExcel;
import com.googosoft.controller.xmgl.jcsz.zyxxwhExportExcel;
import com.googosoft.dao.xmgl.jcsz.ZyxxwhDao;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_ZYXXB;

@Service("zyxxwhService")
public class ZyxxwhService {
	@Resource(name="zyxxwhDao")
	private ZyxxwhDao zyxxwhDao;
	
	
	/**
	 * 新增
	 * 
	 * @return
	 */
	public int doAdd(CW_ZYXXB zyxxb) {
		int result = zyxxwhDao.doAdd(zyxxb);
		
		return result;
	}
	/**
	 * 获得专业信息维护数据
	 */
	public Map getObjectById(String guid) {
		return zyxxwhDao.getObjectById(guid);
	}
	/**
	 * 新增
	 * 
	 * @return
	 */
	public int doEdit(CW_ZYXXB zyxxb) {
		int result = zyxxwhDao.doEdit(zyxxb);
		
		return result;
	}
	public String doDel(String guid) {
//		return zyxxwhDao.doDel(guid);
		
		String str1 = "",str2 = "";
		String newguid = zyxxwhDao.docheckisdelete(guid);//得到可以删除的guid
		System.err.println("___1____"+newguid);
		String notguid = zyxxwhDao.dochecknotdelete(guid);//得到不能删除的guid
		System.err.println("___2____"+notguid);
		String mldm = zyxxwhDao.seledmbyid(notguid);//根据不能删除的guid得到不能删除的mldm
		System.err.println("___3____"+mldm);
		int cgscnum = zyxxwhDao.getcgscnum(guid);//得到成功删除数量
		System.err.println("___4____"+cgscnum);
		zyxxwhDao.doDel(newguid);
		str2 = "成功删除"+cgscnum+"条";
		if(!"".equals(notguid)) {//不能显示的目录代码不是空，则显示
			str1 = "编号为"+mldm+"的不允许删除,";
			return str1+str2;
		}else {
			return str2;
		}
	}
	public int doqy(String guid) {
		return zyxxwhDao.doqy(guid);
	}
	public int dojy(String guid) {
		return zyxxwhDao.dojy(guid);
	}
	/**
	 * 专业编号是否重复
	 */
	public int checkbh(String zybh) {
		return zyxxwhDao.checkbh(zybh);
	}
	/**
	 * 专业编号是否重复
	 */
	public int checkbh1(String zybh,String guid) {
		return zyxxwhDao.checkbh1(zybh,guid);
	}
	/**
	 * 修改状态
	 */
	public int updZt(String guid,String zt) {
		return zyxxwhDao.updZt(guid,zt);
	}
	/**
	 * 导出
	 * @param realfile
	 * @param shortfileurl
	 * @param searchValue
	 * @param guid
	 * @return
	 */
	public Object expExcel(String realfile, String shortfileurl,String searchValue, String guid,String dwbh) {
		List<Map<String, Object>> dwList = this.zyxxwhDao.getList(searchValue,guid,dwbh);
		String Title = "专业信息";
		String[] title = new String[] { "序号","专业编号", "专业名称","所属院系", "学制", "专业状态"   };
		Map<String, Object> dataMap =zyxxwhExportExcel.exportExcel(realfile,shortfileurl, title, Title,dwList );
		return dataMap;
	}
}
