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
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("xmjfyeDao")
public class XmjfyeDao extends BaseDao{

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
	 * 明细帐数据信息存储过程
	 * @param params
	 * @return
	 */
	public boolean getResultMxz(String bmbh, String sszt, String xmbh,
			String year, String StartMonth, String endMonth,String jzpz,String kjzd,PageData pd,String bz) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_MXZ_XM";
		List parInList = new ArrayList<String>();
		boolean bool = false;
//			String kjzd = "0001";
			//排序方式
			String pxfs = "1";
			parInList.add(year);
			parInList.add(year+"-"+StartMonth);
			parInList.add(year+"-"+endMonth);
			parInList.add(bmbh);
			parInList.add(xmbh);
			parInList.add(jzpz);
			parInList.add(sszt);
			parInList.add(kjzd);
			parInList.add(LUser.getRybh());
			System.err.println(parInList);
			try {
				bool = db.batchUpdateByProcedure(procName, parInList);
			} catch (SQLException e) {
				e.printStackTrace();
			}
			return bool;
	}
	public List<Map<String, Object>> getMxzPageList(String bmbh,String xmbh,String kjzd,PageData pd) {
		String sql=getSql(bmbh,xmbh,kjzd,pd);
		return db.queryForList(sql);
	}
	/**
	 * 获得sql语句
	 * @param pxfs
	 * @return
	 */
	public String getSql(String bmbh,String xmbh,String kjzd,PageData pd){
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from(");
		sql.append(" SELECT");
		sql.append(" T.GUID,T.PZBHGUID,");
		sql.append(" T.KMBH,");
		sql.append(" decode(t.kmbh,'','','('||T.KMBH||')'||T.KMMC) as KMMC,");
		sql.append(" T.PZH,t.pzlxmc,t.wldwmc,");
		sql.append(" T.ZY,");
		sql.append(" T.JJFL,");
		sql.append(" decode(t.jjfl,'','','('||T.JJFL||')'||C.KMMC) as JJKMMC,");
		sql.append(" T.BMBH,");
		sql.append(" T.XMBH,");
		sql.append(" D.MC AS BMMC ,");
//		sql.append(" X.XMMC,");
		sql.append(" T.XH,");
		sql.append(" t.PZRQ,");
		sql.append(" DECODE(NVL(T.JFJE,0.00),0.00,'',TO_CHAR(ROUND(T.JFJE,2),'FM999,999,999,990.00'))JFJE,");
		sql.append(" DECODE(NVL(T.DFJE,0.00),0.00,'',TO_CHAR(ROUND(T.DFJE,2),'FM999,999,999,990.00'))DFJE,");
		sql.append(" DECODE(NVL(T.QCYE,0.00),0.00,'',TO_CHAR(ROUND(T.QCYE,2),'FM999,999,999,990.00'))QCYE,");
		sql.append(" DECODE(NVL(T.YE,0.00),0.00,'',TO_CHAR(ROUND(T.YE,2),'FM999,999,999,990.00'))YE,");
		sql.append(" DECODE(T.FX,'0','借','1','贷','平')FX");
		sql.append(" FROM CWPT_MXZ T left join gx_sys_dwb d on d.dwbh=t.bmbh  ");
		sql.append(" left join cw_jjkmb c on c.kmbh=t.jjfl  WHERE T.LOGIN='"+LUser.getRybh()+"' ");
		//		if(Validate.noNull(bmbh)){
//			sql.append(" AND exists  (select null from ");
//			sql.append("(select K.KMBH from CW_KJKMSZB K where k.kjzd='"+kjzd+"') b ");
//			sql.append("where b.KMBH=T.KMBH)");
//		}
		sql.append(")t");
		sql.append(" GROUP BY T.GUID,t.wldwmc,T.PZBHGUID,T.KMBH,T.KMMC, T.PZH,t.pzlxmc, T.ZY, T.JJFL, T.BMBH,T.XMBH,PZRQ,JFJE,DFJE,QCYE,YE,FX,xh,BMMC,JJKMMC ");
		sql.append(" ORDER BY pzrq,pzh,xh");
		return sql.toString();
	}
}
