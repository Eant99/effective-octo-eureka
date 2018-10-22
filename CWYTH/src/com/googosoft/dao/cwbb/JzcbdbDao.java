package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.criteria.CriteriaBuilder.In;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("jzcbdbDao")
public class JzcbdbDao extends BaseDao {
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
	 * 判断表中是否已经存在需要的数据
	 * 
	 * @param sszt
	 * @param year
	 * @param jzpz
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public int check(String sszt, String year, String jzpz) {
		StringBuffer sql = new StringBuffer();
		String nd = (Integer.parseInt(year)-1)+"";
		sql.append(" SELECT COUNT(0) FROM CW_JZCBDB T ");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.JZPZ='" + jzpz + "'");
		sql.append(" AND T.ND='" + year + "'");
		sql.append(" AND T.ZTBH='" + sszt + "'");
		int m = db.queryForInt(sql.toString());
		return m;
	}
	
	public List getDatasList(String sszt, String year, String jzpz){
		StringBuffer sql = new StringBuffer();
		String byear = (Integer.parseInt(year))+"";
		sql.append(" SELECT");
		sql.append(" T.XMBH AS XMMC,");
		sql.append(" DECODE(NVL(T.LJYY,0.00),0.00,'',TO_CHAR(ROUND(T.LJYY,2),'FM999,999,999,990.00')) AS BNLJYY,");
		sql.append(" DECODE(NVL(T.ZYJJ,0.00),0.00,'',TO_CHAR(ROUND(T.ZYJJ,2),'FM999,999,999,990.00')) AS BNZYJJ,");
		sql.append(" DECODE(NVL(T.QYFTZ,0.00),0.00,'',TO_CHAR(ROUND(T.QYFTZ,2),'FM999,999,999,990.00')) AS BNQYFTZ,");
		sql.append(" DECODE(NVL(T.JZCHJ,0.00),0.00,'',TO_CHAR(ROUND(T.JZCHJ,2),'FM999,999,999,990.00')) AS BNJZCHJ,");
		sql.append(" DECODE(NVL(TT.LJYY,0.00),0.00,'',TO_CHAR(ROUND(TT.LJYY,2),'FM999,999,999,990.00')) AS SNLJYY,");
		sql.append(" DECODE(NVL(TT.ZYJJ,0.00),0.00,'',TO_CHAR(ROUND(TT.ZYJJ,2),'FM999,999,999,990.00')) AS SNZYJJ,");
		sql.append(" DECODE(NVL(TT.QYFTZ,0.00),0.00,'',TO_CHAR(ROUND(TT.QYFTZ,2),'FM999,999,999,990.00')) AS SNQYFTZ,");
		sql.append(" DECODE(NVL(TT.JZCHJ,0.00),0.00,'',TO_CHAR(ROUND(TT.JZCHJ,2),'FM999,999,999,990.00')) AS SNJZCHJ,");
		sql.append(" T.BH AS BH,T.HC AS HC");
		sql.append(" FROM CW_JZCBDB T");
		sql.append(" LEFT JOIN CW_JZCBDB TT ON TT.BH=T.BH AND TT.ND='"+byear+"'");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.JZPZ='" + jzpz + "'");
		sql.append(" AND T.ND='" + year + "'");
		sql.append(" AND T.ZTBH='" + sszt + "'");
		sql.append(" AND TT.JZPZ='" + jzpz + "'");
		sql.append(" AND TT.ND='" + byear + "'");
		sql.append(" AND TT.ZTBH='" + sszt + "'");
		sql.append(" ORDER BY T.HC");
		List list = db.queryForList(sql.toString());
		return list;
	}
	
	
	public List getDatasListByPro(String sszt, String year, String jzpz,String bzdw){
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwyth_jzcbd(?,?,?,?,?,?)}";
		list.add(year);
		list.add(jzpz);
		list.add(bzdw);
		list.add(sszt);
		list.add(LUser.getGuid());
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
			String ztbh = Validate.isNullToDefaultString(map.get("SSZT"), "");
			String bzdw = Validate.isNullToDefaultString(map.get("BZDW"), ""); 
			String nd = Validate.isNullToDefaultString(map.get("YEAR"), ""); 
			String hc = Validate.isNullToDefaultString(map.get("HC"), ""); 
			String xmbh = Validate.isNullToDefaultString(map.get("XMBH"), ""); 
			String bnljyy = Validate.isNullToDefaultString(map.get("BNLJYY"), "").replace(",", ""); 
			String bnzyjj = Validate.isNullToDefaultString(map.get("BNZYJJ"), "").replace(",", ""); 
			String bnqyftz = Validate.isNullToDefaultString(map.get("BNQYFTZ"), "").replace(",", ""); 
			String bnjzchj = Validate.isNullToDefaultString(map.get("BNJZCHJ"), "").replace(",", ""); 
			String snljyy = Validate.isNullToDefaultString(map.get("SNLJYY"), "").replace(",", ""); 
			String snzyjj = Validate.isNullToDefaultString(map.get("SNZYJJ"), "").replace(",", ""); 
			String snqyftz = Validate.isNullToDefaultString(map.get("SNQYFTZ"), "").replace(",", ""); 
			String snjzchj = Validate.isNullToDefaultString(map.get("SNJZCHJ"), "").replace(",", ""); 
			String czr = Validate.isNullToDefaultString(LUser.getGuid(), ""); 
			String jzpz = Validate.isNullToDefaultString(map.get("JZPZ"), ""); 
			String bh = Validate.isNullToDefaultString(map.get("BH"), ""); 
			String bnd = (Integer.parseInt(nd)-1)+"";
			StringBuffer sql = new StringBuffer();
			StringBuffer sql1 = new StringBuffer();
			if(i==0){
				String del = "delete from Cw_jzcbdb where nd in('"+nd+"','"+bnd+"') and ztbh='"+ztbh+"'";
				sqlList.add(del);
			}
			//本年
			sql.append(" INSERT INTO CW_JZCBDB");
			sql.append(" (GUID,ZTBH,BZDW,ND,HC,XMBH,LJYY,ZYJJ,QYFTZ,JZCHJ,CZR,JZPZ,BH)");
			sql.append(" VALUES(sys_guid(),");
			sql.append(" '"+ztbh+"',");
			sql.append(" '"+bzdw+"',");
			sql.append(" '"+nd+"',");
			sql.append(" '"+hc+"',");
			sql.append(" '"+xmbh+"',");
			sql.append(" '"+bnljyy+"',");
			sql.append(" '"+bnzyjj+"',");
			sql.append(" '"+bnqyftz+"',");
			sql.append(" '"+bnjzchj+"',");
			sql.append(" '"+czr+"',");
			sql.append(" '"+jzpz+"',");
			sql.append(" '"+bh+"'");
			sql.append(" )");
			//上年
			sql1.append(" INSERT INTO CW_JZCBDB");
			sql1.append(" (GUID,ZTBH,BZDW,ND,HC,XMBH,LJYY,ZYJJ,QYFTZ,JZCHJ,CZR,JZPZ,BH)");
			sql1.append(" VALUES(sys_guid(),");
			sql1.append(" '"+ztbh+"',");
			sql1.append(" '"+bzdw+"',");
			sql1.append(" '"+bnd+"',");
			sql1.append(" '"+hc+"',");
			sql1.append(" '"+xmbh+"',");
			sql1.append(" '"+snljyy+"',");
			sql1.append(" '"+snzyjj+"',");
			sql1.append(" '"+snqyftz+"',");
			sql1.append(" '"+snjzchj+"',");
			sql1.append(" '"+czr+"',");
			sql1.append(" '"+jzpz+"',");
			sql1.append(" '"+bh+"'");
			sql1.append(" )");
			sqlList.add(sql.toString());
			sqlList.add(sql1.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	
	/**
	 * 删除临时表的数据
	 */
	public void delete(){
		String del = "delete from CW_CWPT_JZCBDB where login='"+LUser.getGuid()+"'";
		db.update(del);
	}
	
	/**
	 * 导出的时候走的保存
	 * @param list
	 * @return
	 */
	public boolean doSaveByExp(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		String del = "delete from CW_CWPT_JZCBDB where login='"+LUser.getGuid()+"'";
		sqlList.add(del);
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String nd = Validate.isNullToDefaultString(map.get("YEAR"), ""); 
			String hc = Validate.isNullToDefaultString(map.get("HC"), ""); 
			String xmbh = Validate.isNullToDefaultString(map.get("XMMC"), ""); 
			String bnljyy = Validate.isNullToDefaultString(map.get("BNLJYY"), "").replace(",", ""); 
			String bnzyjj = Validate.isNullToDefaultString(map.get("BNZYJJ"), "").replace(",", ""); 
			String bnqyftz = Validate.isNullToDefaultString(map.get("BNQYFTZ"), "").replace(",", ""); 
			String bnjzchj = Validate.isNullToDefaultString(map.get("BNJZCHJ"), "").replace(",", ""); 
			String snljyy = Validate.isNullToDefaultString(map.get("SNLJYY"), "").replace(",", ""); 
			String snzyjj = Validate.isNullToDefaultString(map.get("SNZYJJ"), "").replace(",", ""); 
			String snqyftz = Validate.isNullToDefaultString(map.get("SNQYFTZ"), "").replace(",", ""); 
			String snjzchj = Validate.isNullToDefaultString(map.get("SNJZCHJ"), "").replace(",", ""); 
			String czr = Validate.isNullToDefaultString(LUser.getGuid(), ""); 
			StringBuffer sql = new StringBuffer();
			//本年
			sql.append(" INSERT INTO CW_CWPT_JZCBDB");
			sql.append(" (XMMC,BNLJYY,BNZYJJ,BNQYFTZ,BNJZCHJ,SNLJYY,SNZYJJ,SNQYFTZ,SNJZCHJ,LOGIN,YEAR,HC)");
			sql.append(" VALUES(");
			sql.append(" '"+xmbh+"',");
			sql.append(" '"+bnljyy+"',");
			sql.append(" '"+bnzyjj+"',");
			sql.append(" '"+bnqyftz+"',");
			sql.append(" '"+bnjzchj+"',");
			sql.append(" '"+snljyy+"',");
			sql.append(" '"+snzyjj+"',");
			sql.append(" '"+snqyftz+"',");
			sql.append(" '"+snjzchj+"',");
			sql.append(" '"+czr+"',");
			sql.append(" '"+nd+"',");
			sql.append(" '"+hc+"'");
			sql.append(" )");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	
	/**
	 * 更新本年度
	 * @param list
	 */
	public boolean updateBn(List list){
		ArrayList<String> sqlList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			StringBuffer sql = new StringBuffer();
			Map map = (Map) list.get(i);
			String hc = Validate.isNullToDefaultString(map.get("HC"), ""); 
			String bnljyy = Validate.isNullToDefaultString(map.get("BNLJYY"), "").replace(",", ""); 
			String bnzyjj = Validate.isNullToDefaultString(map.get("BNZYJJ"), "").replace(",", ""); 
			String bnqyftz = Validate.isNullToDefaultString(map.get("BNQYFTZ"), "").replace(",", ""); 
			String bnjzchj = Validate.isNullToDefaultString(map.get("BNJZCHJ"), "").replace(",", ""); 
			sql.append(" UPDATE CW_CWPT_JZCBDB t");
			sql.append(" SET T.BNLJYY='"+bnljyy+"',");
			sql.append(" t.bnzyjj='"+bnzyjj+"',");
			sql.append(" t.bnqyftz='"+bnqyftz+"',");
			sql.append(" t.bnjzchj='"+bnjzchj+"'");
			sql.append(" WHERE T.HC="+hc+" AND LOGIN='"+LUser.getGuid()+"'");
			sqlList.add(sql.toString());
		}
		return db.batchUpdate(sqlList);
	}
	
	/**
	 * 更新上年度
	 * @param list
	 */
	public boolean updateSn(List list){
		ArrayList<String> sqlList = new ArrayList<String>();
		for(int i=0;i<list.size();i++){
			StringBuffer sql = new StringBuffer();
			Map map = (Map) list.get(i);
			String hc = Validate.isNullToDefaultString(map.get("HC"), ""); 
			String snljyy = Validate.isNullToDefaultString(map.get("SNLJYY"), "").replace(",", ""); 
			String snzyjj = Validate.isNullToDefaultString(map.get("SNZYJJ"), "").replace(",", ""); 
			String snqyftz = Validate.isNullToDefaultString(map.get("SNQYFTZ"), "").replace(",", ""); 
			String snjzchj = Validate.isNullToDefaultString(map.get("SNJZCHJ"), "").replace(",", ""); 
			sql.append(" UPDATE CW_CWPT_JZCBDB t");
			sql.append(" set t.SNLJYY='"+snljyy+"',");
			sql.append(" t.snzyjj='"+snzyjj+"',");
			sql.append(" t.snqyftz='"+snqyftz+"',");
			sql.append(" t.snjzchj='"+snjzchj+"'");
			sql.append(" WHERE T.HC="+hc+" AND LOGIN='"+LUser.getGuid()+"'");
			sqlList.add(sql.toString());
		}
		return db.batchUpdate(sqlList);
	}

	public List<Map<String, Object>> getList(String searchValue) {
		// TODO Auto-generated method stub
		return null;
	}

	public List<Map<String, Object>> getList(String searchValue, String sql) {
		return db.queryForList(sql);
	}
	
}
