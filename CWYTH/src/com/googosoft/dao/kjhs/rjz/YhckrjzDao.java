package com.googosoft.dao.kjhs.rjz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;

@Repository("yhckrjzDao")
public class YhckrjzDao extends BaseDao {
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
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		String sql = "SELECT DISTINCT KMJC AS JB FROM CW_KJKMSZB WHERE 1=1 AND KMJC<>'root' ORDER BY TO_NUMBER(KMJC)";
		List list = db.queryForList(sql);
		return list;
	}
	
	
	public List getKmmj() {
		String sql = "select * from (select   c.kmbh,  c.kmmc,  c.czrq, '(' ||c.kmbh||')'||c.kmmc as kmbhmc from Cw_kjkmszb c where 1 = 1 and sjfl != 'root' and sszt = 'B9BA12A24DBE4EA89763AFDE76B8C61A' and kjzd = '0001') k" +
				" where 1 = 1 and k.kmbh like '%1002%'  and k.kmbh != '1002' order by KMBH asc ";
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
	
	public List daochu( String sszt,String kmbh,String kjqj,String kkjqj,String kmbhstart,String startJc,String qbjzpz,String yfhjzpz) {
		String sql = " select * " +
				" FROM CWPT_YHCKRJZ K WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"' AND K.SSZT='"+sszt+"' AND K.KMBH='"+kmbh+"' and to_char(pzrq,'yyyy-mm')>='"+kjqj+"' and to_char(pzrq,'yyyy-mm')<='"+kkjqj+"' ORDER BY SUBSTR(K.KMBH,0,4) ";
		List list = db.queryForList(sql);
		return list;
	}
	
	public List<Map<String, Object>> getPageList(String sql) {
		return db.queryForList(sql);
	}
	
	
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		String sql = "delete from cwpt_yhckrjz where login='" + LUser.getGuid()
				+ "'";
		db.execute(sql);
	}
}
