package com.googosoft.dao.kjhs.bbzx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.bbzx.Params;

@Repository("jjkmyebDao")
public class JjkmyebDao extends BaseDao {

	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		String login = LUser.getGuid();
		System.err.println("login=="+login);
		String procName = "PRO_CWYTH_KMYE_JJ_NEW";
		String kmbh1 = params.getKmbh1();
		String kmbh2 = params.getKmbh2();
		String kmbh3 = params.getKmbh3();
		Map<String,String> parInMap = new HashMap<String,String>();
		parInMap.put("v_year", params.getYear());
		parInMap.put("v_startmonth", params.getStartMonth());
		parInMap.put("v_endmonth", params.getEndMonth());
		parInMap.put("v_startJc", params.getStartJc());
		parInMap.put("v_endJc", params.getEndJc());
		parInMap.put("v_treebh", params.getTreebh());
		parInMap.put("v_jzpz", params.getJzpz());
		parInMap.put("v_login", login);
		parInMap.put("v_sszt", params.getSszt());
		parInMap.put("v_kjzd", params.getKjzd());
		parInMap.put("kmbh1", params.getKmbh1().replace(",", "','"));//多科目查找时的科目编号
		parInMap.put("kmbh2", params.getKmbh2());//科目区间--开始科目编号
		parInMap.put("kmbh3", params.getKmbh3());//科目区间--结束科目编号
		parInMap.put("kmbh", params.getKmbh());//科目编号，单一，双击科目编号事件
		parInMap.put("types", params.getTypes());//查询类别，0：改动之前的查询方式，1：改动之后的查询方式  2018-06-04
		Set<Entry<String,String>> parInSet = parInMap.entrySet();
		parInMap = null;
		
		Map<String,Integer> parOutMap = new HashMap<String,Integer>();
		parOutMap.put("my_cursor",OracleTypes.CURSOR);
		parOutMap.put("my_cursor2", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		parOutMap = null;
		Map map = null;
		try {
			map = db.queryForMapByProcedure(procName, parInSet, parOutSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(map.isEmpty()){
			map.put("mxList", new ArrayList());
			map.put("zjList", new ArrayList());
		}
		else{
			map.put("mxList", (List)map.get("my_cursor"));
			map.put("zjList", (List)map.get("my_cursor2"));
		}
		return map; 
	}
	
	/**
	 * 判断存储过程时候执行完毕__合并后的第一个页面
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult_Hb(Params params){
		String login = LUser.getGuid();
		System.err.println("login=="+login);
		String procName = "PRO_CWYTH_KMYE_JJ_NEW_HB";
		String kmbh1 = params.getKmbh1();
		String kmbh2 = params.getKmbh2();
		String kmbh3 = params.getKmbh3();
		Map<String,String> parInMap = new HashMap<String,String>();
		parInMap.put("v_year", params.getYear());
		parInMap.put("v_startmonth", params.getStartMonth());
		parInMap.put("v_endmonth", params.getEndMonth());
		parInMap.put("v_startJc", params.getStartJc());
		parInMap.put("v_endJc", params.getEndJc());
		parInMap.put("v_treebh", params.getTreebh());
		parInMap.put("v_jzpz", params.getJzpz());
		parInMap.put("v_login", login);
		parInMap.put("v_sszt", params.getSszt());
		parInMap.put("v_kjzd", params.getKjzd());
		parInMap.put("kmbh1", params.getKmbh1().replace(",", "','"));//多科目查找时的科目编号
		parInMap.put("kmbh2", params.getKmbh2());//科目区间--开始科目编号
		parInMap.put("kmbh3", params.getKmbh3());//科目区间--结束科目编号
		parInMap.put("kmbh", params.getKmbh());//科目编号，单一，双击科目编号事件
		parInMap.put("types", params.getTypes());//查询类别，0：改动之前的查询方式，1：改动之后的查询方式  2018-06-04
		Set<Entry<String,String>> parInSet = parInMap.entrySet();
		parInMap = null;
		
		Map<String,Integer> parOutMap = new HashMap<String,Integer>();
		parOutMap.put("my_cursor",OracleTypes.CURSOR);
		parOutMap.put("my_cursor2", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		parOutMap = null;
		Map map = null;
		try {
			map = db.queryForMapByProcedure(procName, parInSet, parOutSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(map.isEmpty()){
			map.put("mxList", new ArrayList());
			map.put("zjList", new ArrayList());
		}
		else{
			map.put("mxList", (List)map.get("my_cursor"));
			map.put("zjList", (List)map.get("my_cursor2"));
		}
		return map; 
	}
}
