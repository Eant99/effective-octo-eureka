package com.googosoft.dao.kjhs.bbzx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;

import oracle.jdbc.OracleTypes;

@Repository("zjszyebDao")
public class ZjszyebDao extends BaseDao{
	/**
	 * 查询月份
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
	/**
	 * 获取资金收支余额表数据
	 * @param pd
	 * @param sszt 
	 * @return
	 */
	public Map<String, List<Map<String, Object>>> getResultMap(PageData pd, String sszt) {
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			Map pageMap=(Map) list.get(0);
			Map<String,String> parInMap = new HashMap<String,String>();
			parInMap.put("v_year",(String) pageMap.get("year"));
			parInMap.put("v_startmonth", (String) pageMap.get("startMonth"));
			parInMap.put("v_endmonth", (String) pageMap.get("endMonth"));
			parInMap.put("v_jzpz", (String) pageMap.get("jzpz"));
			parInMap.put("v_sszt",sszt);
			parInMap.put("v_kjzd", "0001");
			Set<Entry<String,String>> parInSet = parInMap.entrySet();
			parInMap = null;
			Map<String,Integer> parOutMap = new HashMap<String,Integer>();
			parOutMap.put("v_cursor",OracleTypes.CURSOR);
			Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
			parOutMap = null;
			Map map = null;
			try {
				map = db.queryForMapByProcedure("pro_cwyth_zjszyeb", parInSet, parOutSet);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			if(map.isEmpty()){
				map.put("mxList", new ArrayList());
			}
			else{
				map.put("mxList", (List)map.get("v_cursor"));
			}
			return map; 
	}
	/**
	 * 获取打印的数据
	 * @param pd
	 * @param sszt
	 * @return
	 */
	public List<Map<String, Object>> getListCzbzsrzc(PageData pd, String sszt) {
		String json = pd.getString("searchJson");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
			Map pageMap=(Map) list.get(0);
			Map<String,String> parInMap = new HashMap<String,String>();
			parInMap.put("v_year",(String) pageMap.get("year"));
			parInMap.put("v_startmonth", (String) pageMap.get("startMonth"));
			parInMap.put("v_endmonth", (String) pageMap.get("endMonth"));
			parInMap.put("v_jzpz", (String) pageMap.get("jzpz"));
			parInMap.put("v_sszt",sszt);
			parInMap.put("v_kjzd", "0001");
			Set<Entry<String,String>> parInSet = parInMap.entrySet();
			parInMap = null;
			Map<String,Integer> parOutMap = new HashMap<String,Integer>();
			List<Map<String, Object>> datalist=null;
			try {
				datalist = db.queryForListByProcedure("pro_cwyth_zjszyeb", parInSet, "v_cursor");
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return datalist; 
	}
}
