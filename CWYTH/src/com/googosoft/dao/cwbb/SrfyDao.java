package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("srfyDao")
public class SrfyDao extends BaseDao {
	/**
	 * 查询学校信息
	 * 
	 * @return
	 */
	public Map<String, Object> getBzdw() {
		String sql = "SELECT GUID,XXDM,XXMC FROM CW_XXXXB WHERE ROWNUM=1";
		Map map = new HashMap<String, Object>();
		map = db.queryForMap(sql);
		return map;
	}

	/**
	 * 判断Cw_srfyb是否已经存入数据
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public int checkSrfy(String bblx, String sysDate, String sszt, String jzpz) {
		String sql = "SELECT COUNT(0) FROM CW_SRFYB T WHERE T.ZTBH='" + sszt
				+ "' AND NY='" + sysDate + "' AND BBLX='" + bblx
				+ "' AND JZPZ='" + jzpz + "'";
		int result = Integer.parseInt(Validate.isNullToDefaultString(
				db.queryForSingleValue(sql), "0"));
		return result;
	}

	/**
	 * 数据集合
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public List<Map<String, Object>> getResultList(String bblx, String sysDate,
			String sszt, String jzpz) {
		List list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" T.READ AS READ, ");
		sql.append(" T.FLAG AS FLAG, ");
		sql.append(" T.GUID AS GUID,");
		sql.append(" T.NY AS DATES,");
		sql.append(" T.HC AS XH,");
		sql.append(" X.XXMC AS XXMC,");
		sql.append(" T.XMBH AS XMMC,");
		sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,990.00')) AS BYS,");
		sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,990.00')) AS BNLJS");
		sql.append(" FROM CW_SRFYB T");
		sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.BBLX='" + bblx + "'");
		sql.append(" AND T.NY='" + sysDate + "'");
		sql.append(" AND T.ZTBH='" + sszt + "'");
		sql.append(" AND T.JZPZ='" + jzpz + "'");
		sql.append(" ORDER BY HC");
		list = db.queryForList(sql.toString());
		return list;
	}

	/**
	 * 调用存储过程返回list
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @param jzpz
	 * @param bzdw
	 * @return
	 */
	public List<Map<String, Object>> getResultListByPro(String bblx,
			String sysDate, String sszt, String jzpz, String bzdw) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwyth_srfy(?,?,?,?,?,?,?)}";
		list.add(sysDate);
		list.add(jzpz);
		list.add(bzdw);
		list.add(sszt);
		list.add(LUser.getGuid());
		list.add(bblx);
		datas = db.getListByPro(list, proName, datas);
		return datas;
	}

	/**
	 * 保存
	 * 
	 * @param list
	 * @return
	 */
	public boolean doSave(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String ztbh = Validate.isNullToDefaultString(map.get("ztbh"), "");
			String bzdw = Validate.isNullToDefaultString(map.get("bzdw"), "");
			String ny = Validate.isNullToDefaultString(map.get("ny"), "");
			String hc = Validate.isNullToDefaultString(map.get("hc"), "");
			String bblx = Validate.isNullToDefaultString(map.get("bblx"), "");
			String xmbh = Validate.isNullToDefaultString(map.get("xmbh"), "");
			String read = Validate.isNullToDefaultString(map.get("read"), "");
			String bys = Validate.isNullToDefaultString(map.get("bys"), "").replace(",", "");
			String bnljs = Validate.isNullToDefaultString(map.get("bnljs"), "").replace(",", "");
			String czr = Validate.isNullToDefaultString(LUser.getGuid(), "");
			String jzpz = Validate.isNullToDefaultString(map.get("jzpz"), "");
			String flag = Validate.isNullToDefaultString(map.get("flag"), "");
			StringBuffer sql = new StringBuffer();
			if (i == 0) {
				String delSql = "DELETE FROM CW_SRFYB WHERE ZTBH='" + ztbh
						+ "' AND BZDW='" + bzdw + "' AND BBLX='" + bblx
						+ "' AND JZPZ='" + jzpz + "'";
				sqlList.add(delSql);
			}
			sql.append(" INSERT INTO CW_SRFYB");
			sql.append(" (GUID,ZTBH,BZDW,NY,HC,BBLX,XMBH,BYS,BNLJS,JZPZ,CZR,READ,flag)");
			sql.append(" VALUES");
			sql.append(" (");
			sql.append(" SYS_GUID(),");
			sql.append(" '" + ztbh + "',");
			sql.append(" '" + bzdw + "',");
			sql.append(" '" + ny + "',");
			sql.append(" '" + hc + "',");
			sql.append(" '" + bblx + "',");
			sql.append(" '" + xmbh + "',");
			sql.append(" '" + bys + "',");
			sql.append(" '" + bnljs + "',");
			sql.append(" '" + jzpz + "',");
			sql.append(" '" + czr + "',");
			sql.append(" '"+read+"',");
			sql.append(" '"+flag+"')");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}

	//导出
	public List<Map<String, Object>> getList() {
//		StringBuffer sql = new StringBuffer();
//		if(result>0){
//			sql.append(" SELECT");
//			sql.append(" T.XMBH AS XMMC,");
//			sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,000.00')) AS BYS,");
//			sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,000.00')) AS BNLJS");
//			sql.append(" FROM CW_SRFYB T");
//			sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
//			sql.append(" WHERE 1=1");
//			sql.append(" AND T.BBLX='" + bblx + "'");
//			sql.append(" AND T.NY='" + sysDate + "'");
//			sql.append(" AND T.ZTBH='" + sszt + "'");
//			sql.append(" AND T.JZPZ='"+jzpz+"'");
//			sql.append(" ORDER BY HC");
//		}else{
//			sql.append(" SELECT");
//			sql.append(" T.XMMC AS XMMC,");
//			sql.append(" DECODE(NVL(T.BYS,0.00),0.00,'',TO_CHAR(ROUND(T.BYS,2),'FM999,999,999,000.00')) AS BYS,");
//			sql.append(" DECODE(NVL(T.BNLJS,0.00),0.00,'',TO_CHAR(ROUND(T.BNLJS,2),'FM999,999,999,000.00')) AS BNLJS");
//			sql.append(" FROM TEMP_XMXXB T");
//			sql.append(" WHERE 1=1 AND T.LOGIN='"+LUser.getGuid()+"'");
//		}
//		return db.queryForList(sql.toString());
		
		String sql = " select  replace(replace(xmmc,'<b>',''),'</b>','') as xmmc ,bys,bnljs from   temp_xmxxb ";
		return db.queryForList(sql);
		
	}
}
