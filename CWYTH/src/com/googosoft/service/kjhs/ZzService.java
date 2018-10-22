package com.googosoft.service.kjhs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Service;

import com.googosoft.constant.Constant;
import com.googosoft.controller.cwbb.expExcel.KmyeExportExcel;
import com.googosoft.controller.cwbb.expExcel.ZzExportExcel;
import com.googosoft.dao.kjhs.ZzDao;
import com.googosoft.pojo.kjhs.Zzcx;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


@Service("ZzService")
public class ZzService {
	@Resource(name="ZzDao")
	public ZzDao Dao;
	
	public List getXxList(String jb,String dm)  {
		
		return  Dao.getXxList(jb,dm);
	}	

	public List<Map<String,Object>> getYzzList(String sszt,String kjzd,String pznd,String kmbh,String bmbh,String xmbh,String bhwjzpz,String qskjqj,String jskjqj){
//		return Dao.getPageList(kmbh);
		Dao.executeYzz(sszt, kjzd, pznd, kmbh, bmbh, xmbh, bhwjzpz, qskjqj, jskjqj);
		return Dao.getYzzList(pznd);
	}
	public List<Map<String,Object>> getRzzList(String sszt,String kjzd,String pznd,String kmbh,String bmbh,String xmbh,String bhwjzpz,String qskjqj,String jskjqj,String kjnd,String treesearch){
		Dao.executeRzz(sszt, kjzd, pznd, kmbh, bmbh, xmbh, bhwjzpz, qskjqj, jskjqj,kjnd);
		return Dao.getRzzList(treesearch);
	}
	
	public String kmmc(String kmbh) {
		return Dao.kmmc(kmbh);
	}
//	public List kmbhList(String kmbh) {
//		return Dao.kmbhList(kmbh);
//	}
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd){
		return Dao.getPageList(kmbh,kjzd,treesearch,pd);
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(String qskjqj,String jskjqj,String kjkm) {
		return Dao.getResult(qskjqj,jskjqj,kjkm);
	}
	
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		return Dao.getKjkmJb();
	}
	
	public List<Map<String, Object>> daochu( String sszt,String kmbh) {
		return Dao.daochu(sszt,kmbh);
	}
	/**
	 * 导出日总账
	 * @param realfile
	 * @param shortfileurl
	 * @param foo
	 * @param list
	 * @return
	 */
	public Object exportRzzExcel(String realfile, String shortfileurl,String foo,List<Map<String, Object>> list) {
		String Title = "总账";
		if(Validate.noNull(foo)){
			Title = foo;
		}
		String[] title = new String[] { "凭证日期", "摘要", "借方金额", "贷方金额", "方向", "余额"  };
		Map<String, Object> dataMap = ZzExportExcel.exportRzzExcel(realfile,shortfileurl, title, Title,list );
//		System.err.println("dataMap="+dataMap);
		return dataMap;
	}
	/**
	 * 导出月总账
	 * @param realfile
	 * @param shortfileurl
	 * @param foo
	 * @param list
	 * @return
	 */
	public Object exportYzzExcel(String realfile, String shortfileurl,String foo,List<Map<String, Object>> list) {
		String Title = "总账";
		if(Validate.noNull(foo)){
			Title = foo;
		}
		String[] title = new String[] { "会计期间","期初余额","借方发生","贷方发生","借方累计","贷方累计","期末余额"};
		Map<String, Object> dataMap = ZzExportExcel.exportYzzExcel(realfile,shortfileurl, title, Title,list );
		return dataMap;
	}
	
	public Map getKjqj(HttpSession session){
		return Dao.getKjqj(session);
	}
	
	
	
	
	

}
