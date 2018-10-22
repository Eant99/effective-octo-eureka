package com.googosoft.service.xmgl.xmsp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.constant.shzt.XmShzt;
import com.googosoft.dao.xmgl.xmsp.XmspDao;
import com.googosoft.service.base.BaseService;
/**
 * 
 * @author fugangjie
 *
 * 2018年1月27日-下午4:06:20
 */
@Service
public class XmspService extends BaseService{

	@Autowired
	XmspDao xmspDao;
	
	/**
	 * 根据guid查询项目审批详细信息
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getXmspInfo(String guid){
		return xmspDao.getXmspInfo(guid);
	}
	/**
	 * 项目初审通过，更新审核状态为：复审中
	 * @param guid
	 * @return
	 */
	public int doXmcsPass(String guid,String shyj,String xmpm) {
		return xmspDao.updateCsShzt(guid, XmShzt.FSZ,shyj,xmpm);
	}
	/**
	 * 项目初审退回，更新审核状态为：初审退回
	 * @param guid
	 * @return
	 */
	public int doXmcsReject(String guid,String shyj) {
		return xmspDao.updateCsShzt(guid, XmShzt.CSTH,shyj,"");
	}
	/**
	 * 项目复审通过，更新审核状态为：审核通过
	 * @param guid
	 * @return
	 */
	public int doXmfsPass(String guid,String shyj,String xmpm,String sszt) {
		xmspDao.updateFsShzt(guid, XmShzt.SHTG,shyj,xmpm);
		return xmspDao.insertXmjbxx(guid,sszt);
	}
	/**
	 * 项目复审退回，更新审核状态为：复审退回
	 * @param guid
	 * @return
	 */
	public int doXmfsReject(String guid,String shyj) {
		return xmspDao.updateFsShzt(guid, XmShzt.FSTH,shyj,"");
	}
	/**
	 * 保存调整后排名
	 * @param data	前端页面封装的json数据
	 * @return
	 */
	public int doTzhpmSave(String data) {
		Gson gson = new Gson();
		Map<String, Object> json = gson.fromJson(data, new TypeToken<HashMap<String,Object>>(){}.getType());
		@SuppressWarnings("unchecked")
		List<Map<String,Object>> list = (List<Map<String, Object>>) json.get("list");
		for (Map<String, Object> map : list) {
			String guid = map.get("guid")+"";
			String tzhpm = map.get("tzhpm")+"";
			xmspDao.updateTzhpm(guid,tzhpm);
		}
		return 1;
	}
	/**
	 * 导出项目初审信息
	 * @param realfile
	 * @param shortfileurl
	 * @param foo
	 * @param list
	 * @return
	 */
	public Object exportCsExcel(String realfile, String shortfileurl,String foo,List<Map<String, Object>> list) {
		String[] title = new String[] { "审核状态","项目编号","项目名称","项目类型","服务专业","服务学科","预算金额","是否上年度重新论证项目","申报单位","申报人","申报日期"};
		Map<String, Object> dataMap = XmspExportExcel.exportCsExcel(realfile,shortfileurl, title, foo,list );
		return dataMap;
	}
	/**
	 * 导出项目复审信息
	 * @param realfile
	 * @param shortfileurl
	 * @param foo
	 * @param list
	 * @return
	 */
	public Object exportFsExcel(String realfile, String shortfileurl,String foo,List<Map<String, Object>> list) {
		String[] title = new String[] { "审核状态","项目编号","项目名称","项目类型","项目初审排名","服务专业","服务学科","预算金额","是否上年度重新论证项目","申报单位","初审人","初审日期"};
		Map<String, Object> dataMap = XmspExportExcel.exportFsExcel(realfile,shortfileurl, title, foo,list );
		return dataMap;
	}
}
