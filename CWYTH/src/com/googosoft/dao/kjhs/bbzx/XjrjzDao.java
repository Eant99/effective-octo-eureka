package com.googosoft.dao.kjhs.bbzx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("xjrjzDao")
public class XjrjzDao extends BaseDao {
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
	
	/**
	 * 获得sql语句
	 * @param pxfs
	 * @return
	 */
	public String getSql(String treesearch,String kmbh,String zy,String pzbh,String pzrq1,String pzrq2){
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select k.PZRQ,k.pzbhguid,k.PZH,k.pzlxmc,k.ZY,xh,");
		sqltext.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sqltext.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sqltext.append(" decode(k.fx,'0','借','1','贷','平')fx,");
		sqltext.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye");		
		sqltext.append(" from cwpt_xjrjz k ");
		sqltext.append(" where login='"+LUser.getGuid()+"' ");
		if(Validate.noNull(kmbh)){
			sqltext.append(" and k.kmbh='"+kmbh+"' ");
		}
		if(Validate.noNull(zy)){
			sqltext.append(" and zy like '%"+zy+"%' ");
		}
		if(Validate.noNull(pzbh)){
			sqltext.append(" and k.PZH='"+pzbh+"' ");
		}
		if(Validate.noNull(pzrq1)){
			sqltext.append(" and (CASE LENGTH(k.PZRQ) WHEN 7 THEN PZRQ || '-01' ELSE PZRQ END) >= '"+pzrq1+"'");
		}
		if(Validate.noNull(pzrq2)){
			sqltext.append(" and (CASE LENGTH(k.PZRQ) WHEN 7 THEN PZRQ || '-01' ELSE PZRQ END)<= '"+pzrq2+"'");
		}
		//sqltext.append(" and K.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//sql.append(" and K.kmmc='"+CommonUtil.getEndText(treesearch)+"'");
		sqltext.append(" ORDER BY  case when length(pzrq)<9 then to_date(pzrq,'yyyy-mm') else to_date(pzrq,'yyyy-mm-dd') end ASC,pzh,xh");
		return sqltext.toString();
	}
	
	public List<Map<String, Object>> getPageList(String treesearch,String kmbh,String zy,String pzbh,String pzrq1,String pzrq2){
		String sql=getSql(treesearch,kmbh,zy,pzbh,pzrq1,pzrq2);	
		return db.queryForList(sql);
	}

	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		String sql = "SELECT DISTINCT KMJC AS JB FROM CW_KJKMSZB WHERE 1=1 AND KMJC<>'root' ORDER BY TO_NUMBER(KMJC)";
		List list = db.queryForList(sql);
		return list;
	}
	
	public  boolean runPro(String proName,List list) throws SQLException{
		try {
			super.db.batchUpdateByProcedure(proName,list);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	
	public List daochu( String sszt,String kmbh) {
		String sql = " select * " +
				" FROM CWPT_KCXJRJZ K WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"' AND K.SSZT='"+sszt+"' AND K.KMBH='"+kmbh+"' ORDER BY SUBSTR(K.KMBH,0,4) ";
		List list = db.queryForList(sql);
		return list;
	}
	
	
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_MXZ";
		List parInList = new ArrayList<String>();
		boolean bool = false;
		if(list.size()==0){
			return bool;
		}
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			//科目
			String startKjkm = Validate.isNullToDefaultString(map.get("startKjkm"), "");
			//是否包含记账
			String jzpz = Validate.isNullToDefaultString(map.get("jzpz"), "0");
			//科目级次
			String startJc = Validate.isNullToDefaultString(map.get("startJc"), "");
			String endJc = Validate.isNullToDefaultString(map.get("endJc"), "");
			//排序方式
			String pxfs = Validate.isNullToDefaultString(map.get("pxfs"), "");
			//会计期间
			String year = Validate.isNullToDefaultString(map.get("year"), "");
			String startMonth = Validate.isNullToDefaultString(map.get("startMonth"), "");
			String endMonth = Validate.isNullToDefaultString(map.get("endMonth"), "");
			parInList.add(year+"-"+startMonth);
			parInList.add(year+"-"+endMonth);
			//parInList.add(startKjkm);
			parInList.add(startJc);
			parInList.add(endJc);
			parInList.add(pxfs);
			parInList.add(jzpz);
			parInList.add(sszt);
			parInList.add(LUser.getGuid());
			
		}
		try {
			bool = db.batchUpdateByProcedure(procName, parInList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		String sql = "delete from cwpt_xjrjz where login='" + LUser.getGuid()+ "'";
		db.execute(sql);
	}
}
