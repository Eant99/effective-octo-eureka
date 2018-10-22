package com.googosoft.dao.kjhs.bbzx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import oracle.jdbc.OracleTypes;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.bbzx.Params;

@Repository("dwwlyebDao")
public class DwwlyebDao extends BaseDao {

	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		String login = LUser.getGuid();
		System.err.println("login=="+login);
		String procName = "PRO_CWYTH_KMYE_DW";
		String kmbh1 = params.getKmbh1();
		String kmbh2 = params.getKmbh2();
		String kmbh3 = params.getKmbh3();
//		List parInList = new ArrayList<String>();
//		parInList.add(params.getYear());
//		parInList.add(params.getStartMonth());
//		parInList.add(params.getEndMonth());
//		parInList.add(params.getStartKjkm());
//		parInList.add(params.getEndKjkm());
//		parInList.add(params.getStartJc());
//		parInList.add(params.getEndJc());
//		parInList.add(params.getNokm());
//		parInList.add(params.getJzpz());
//		parInList.add(params.getGs());
//		parInList.add(login);
//		parInList.add(params.getSszt());
//		parInList.add(params.getKjzd());//会计制度
//		System.err.println("parInList======="+parInList);
		Map<String,String> parInMap = new HashMap<String,String>();
		parInMap.put("v_year", params.getYear());
		parInMap.put("v_startmonth", params.getStartMonth());
		parInMap.put("v_endmonth", params.getEndMonth());
		parInMap.put("v_treebh", params.getTreebh());
//		parInMap.put("v_startkjkm", params.getStartKjkm());
//		parInMap.put("v_endkjkm", params.getEndKjkm());
//		parInMap.put("v_startjc", params.getStartJc());
//		parInMap.put("v_endjc", params.getEndJc());
//		parInMap.put("v_nokm", params.getNokm());
		parInMap.put("v_jzpz", params.getJzpz());
//		parInMap.put("v_gs", params.getGs());
		parInMap.put("v_login", login);
		parInMap.put("v_sszt", params.getSszt());
		parInMap.put("v_kjzd", params.getKjzd());
		parInMap.put("kmbh1", params.getKmbh1().replace(",", "','"));//多科目查找时的科目编号
		parInMap.put("kmbh2", params.getKmbh2());//科目区间--开始科目编号
		parInMap.put("kmbh3", params.getKmbh3());//科目区间--结束科目编号
		parInMap.put("v_dwmc", params.getNokm());//单位名称条件
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
