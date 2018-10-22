package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;
@Repository("XjllbDao")
public class XjllbDao extends BaseDao{
	/**
	 * 查询学校信息
	 * 
	 * @return
	 */
	public Map<String, Object> getBzdw() {
		String sql = "SELECT * FROM CW_XXXXB WHERE ROWNUM=1";
		Map map = new HashMap<String, Object>();
		map = db.queryForMap(sql);
		return map;
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
	public List<Map<String, Object>> getResultListByPro(String year, String jzpz,String sszt,Map bzdw) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwyth_xjllb(?,?,?,?,?,?)}";
		System.err.println("ddddddddddd"+bzdw.get("guid"));
		System.err.println("year="+year+"***jzpz="+jzpz+"**sszt="+sszt+"**bzdw"+bzdw);
		list.add(year);
		list.add(jzpz);
		list.add(sszt);
		list.add(bzdw.get("guid"));
		list.add(LUser.getRybh());
//		list.add(bblx);
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
		System.err.println("ddddd"+list);
		System.err.println("listteeeeeeeeeeeeeeee"+list);
//		String sql1="select count(*) from CW_SRFYB t where t.nd='"+list.get(2)+"' ";
//		int j = db.queryForInt(sql1);
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String ztbh = Validate.isNullToDefaultString(map.get("ztbh"), "");
			String jzpz = Validate.isNullToDefaultString(map.get("jzpz"), "");
			String xmbh = Validate.isNullToDefaultString(map.get("xmmc"), "");
			String nd = Validate.isNullToDefaultString(map.get("ny"), "");
			String bzdw = Validate.isNullToDefaultString(map.get("bzdw"), ""); 	
			String hc = Validate.isNullToDefaultString(map.get("hc"), "");
			String snje = Validate.isNullToDefaultString(map.get("snje"), "").replace(",", "");
			String bnje = Validate.isNullToDefaultString(map.get("bnje"), "").replace(",", "");
			String tag = Validate.isNullToDefaultString(map.get("tag"), "");
			String czr = Validate.isNullToDefaultString(LUser.getGuid(), "");
			StringBuffer sql = new StringBuffer();
			if (i == 0) {
				String delSql = "DELETE FROM Cw_xjllb WHERE ztbh='" + ztbh+"' AND BZDW='"+bzdw+"' AND ND='"+nd+"' ";
				sqlList.add(delSql);
			}
			sql.append(" INSERT INTO Cw_xjllb");
			sql.append(" (guid,ztbh,jzpz,xmbh,nd,bzdw,snje,bnje,hc,tag,czr)");
			sql.append(" VALUES");
			sql.append(" (sys_guid(),");
			sql.append(" '" + ztbh + "',");
			sql.append(" '" + jzpz + "',");
			sql.append(" '" + xmbh + "',");
			sql.append(" '" + nd + "',");
			sql.append(" '" + bzdw + "',");
			sql.append(" '" + snje + "',");
			sql.append(" '" + bnje + "',");
			sql.append(" '" + hc + "',");
			sql.append(" '" + tag + "',");
			sql.append(" '"+czr+"'");
			sql.append(" )");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
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
		sql.append(" SELECT COUNT(0) FROM Cw_xjllb T ");
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
		sql.append(" distinct T.XMBH XMMC,T.HC,T.CZR,T.ZTBH,T.ND,T.JZPZ,T.TAG,");
		sql.append(" DECODE(NVL(T.BNJE,0.00),0.00,'',TO_CHAR(ROUND(T.BNJE,2),'FM999,999,999,990.00')) AS BNJE,");
		sql.append(" DECODE(NVL(T.SNJE,0.00),0.00,'',TO_CHAR(ROUND(T.SNJE,2),'FM999,999,999,990.00')) AS SNJE");
//		sql.append(" T.BH AS BH,T.HC AS HC");
		sql.append(" FROM Cw_xjllb T");
//		sql.append(" LEFT JOIN CW_JZCBDB TT ON TT.BH=T.BH AND TT.ND='"+byear+"'");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.JZPZ='" + jzpz + "'");
		sql.append(" AND T.ND='" + year + "'");
		sql.append(" AND T.ZTBH='" + sszt + "'");
//		sql.append(" AND TT.JZPZ='" + jzpz + "'");
//		sql.append(" AND TT.ND='" + byear + "'");
//		sql.append(" AND TT.ZTBH='" + sszt + "'");
		sql.append(" ORDER BY T.HC");
		List list = db.queryForList(sql.toString());
		return list;
	}
	
	public List getDatasListByPro(String year, String jzpz,String sszt,Map bzdw){
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwyth_xjllb(?,?,?,?,?,?)}";
		list.add(year);
		list.add(jzpz);
		list.add(sszt);
		list.add(bzdw.get("guid"));
		list.add(LUser.getRybh());
		datas = db.getListByPro(list, proName, datas);
		return datas;
	}




/**
 * 从临时表查询数据导出
 * @return
 */
	public List<Map<String, Object>> getList() {
		String sql = " select  replace(replace(xmmc,'<b>',''),'</b>','') as xmmc ,bnje,snje from   Cw_xjllb2 ";
		return db.queryForList(sql);
		
	}
	
}
