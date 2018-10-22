package com.googosoft.service.kjhs.bbzx;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.ZjrbbExportExcel;
import com.googosoft.dao.kjhs.bbzx.XjrjzDao;
import com.googosoft.dao.kjhs.bbzx.ZjrbbDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.pojo.kjhs.bbzx.Zjrbb;
import com.googosoft.service.base.BaseService;
import com.googosoft.util.Validate;

@Service("zjrbbService")
public class ZjrbbService extends BaseService {
	@Resource(name = "zjrbbDao")
	private ZjrbbDao dao;

	public boolean runPro(String proName,List list) throws SQLException{
		return dao.runPro(proName,list);
	}
	
	public List<Map<String, Object>> getPageList(String kmbh) {
		return dao.getPageList(kmbh);
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
	
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteZjrbb() {
		dao.deleteZjrbb();
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
//	public boolean getResult(List list) {
//		return dao.getResult(list);
//	}
	/**
	 * 导出
	 */
	public List<Map<String, Object>> daochu( ) {
		return dao.daochu();
	}
	
	public Object expExcel(String realfile, String shortfileurl,String searchValue,String foo,List list) {
		String Title = "资金日报表";
		if(Validate.noNull(foo)){
			Title = "资金日报表";
		}
		String[] title = new String[] { "科目编号", "科目名称", "方向", "昨日余额", "借方", "贷方", "方向","今日余额","借方笔数","贷方笔数" };
		Map<String, Object> dataMap = ZjrbbExportExcel.exportExcel(realfile,shortfileurl, title, Title,list );
		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	
	
	public String kmmc(String kmbh) {
		return dao.kmmc(kmbh);
	}
	
	public List kmbhList(String kmbh) {
		return dao.kmbhList(kmbh);
	}
	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public boolean getResult(Zjrbb zjrbb,String kmbhh){
		return dao.getResult(zjrbb,kmbhh);
	}
	/**
	 * 得到cw_kmyeb的会计制度
	 */
	public String getkjzd(String lxsj) {
		return dao.getkjzd(lxsj);
	}
}
