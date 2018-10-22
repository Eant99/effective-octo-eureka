/**
 * 
 */
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

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("bmxmxxtjdao")
public class BmxmxxtjDao extends BaseDao {
	
	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		System.err.println(params);
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_KMYE_XMJFYEB";
		Map<String,String> parInMap = new HashMap<String,String>();
		parInMap.put("v_year", params.getYear());
		parInMap.put("v_startmonth", params.getStartMonth());
		parInMap.put("v_endmonth", params.getEndMonth());
		parInMap.put("v_bmbh", params.getBmbh());
		parInMap.put("v_xmbh", params.getXmbh());
		parInMap.put("v_sszt", params.getSszt());
		parInMap.put("v_kjzd", params.getKjzd());
		parInMap.put("v_jzpz", params.getJzpz());
		parInMap.put("v_treebh", params.getTreebh());
		parInMap.put("v_types", params.getTypes());
		parInMap.put("v_login", login);
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
	 * 获取bm
	 * @return
	 */
	public List getbmbhList(String login,String bmbh) {
		StringBuffer sql=new StringBuffer();
		sql.append("select bmbh ,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh= bmbh) as dwmc,SUM(qcye) as qcye,SUM(qmye)as qmye from cw_bmxmxxtj where 1=1  ");
		if (Validate.noNull(bmbh)) {
			sql.append(" and bmbh='"+bmbh+"' ");
			
		} 
		sql.append(" group by bmbh ");
		return db.queryForList(sql.toString());
	}
	/**
	 * 获取bm
	 * @return
	 */
	public List getXmdlList(String bmbh,String login,String xmdl) {
		StringBuffer sql=new StringBuffer();
		sql.append("select xmdl ,(select mc from gx_sys_dmb   where zl='250' and dm=xmdl) as xmdlmc,SUM(qcye) as qcye,SUM(qmye)as qmye from cw_bmxmxxtj where 1=1 and bmbh='"+bmbh+"' ");
		if (Validate.noNull(xmdl)) {
			sql.append(" and xmdl='"+xmdl+"' ");
			
		} 
		sql.append(" group by xmdl ");
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 获取bm
	 * @return
	 */
	public List getXmxxList(String bmbh,String login,String xmdl) {
		StringBuffer sql=new StringBuffer();
		sql.append("select xmmc ,xmbh,qcye as  qcye,qmye as qmye,CONCAT(ROUND((qcye-qmye)/decode(qcye,0,1,qcye)*100,2),'%') as zxbz from cw_bmxmxxtj where 1=1 and bmbh='"+bmbh+"' ");
	
			sql.append(" and xmdl='"+xmdl+"' ");
		sql.append(" order by xmbh ");
		return db.queryForList(sql.toString());
	}
	/**
	 * 
	 * @author  wangguanghua
	 * @version  2018-9-13上午10:22:37
	 * 获取项目大类
	 */
	public List getPrintInfo(){
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from gx_sys_dmb where zl='250'");
	
		List list = db.queryForList(sql.toString());
		return list;
	}
	
}
