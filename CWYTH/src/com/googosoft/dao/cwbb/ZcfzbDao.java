package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("zcfzbDao")
/**
 * 
 * @author Administrator
 *
 */
public class ZcfzbDao extends BaseDao {
	public List<Map<String, Object>> getResultListByPro(String bbnd, String bbyf,
			String saasdm,String sfjz,String type,String ztgid,String rq) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwbb_sclsbb(?,?,?,?,?,?,?,?)}";
		list.add(bbnd);
		list.add(bbyf);
		list.add(saasdm);
		list.add(sfjz);
		list.add(type);
		list.add(ztgid);
		list.add(rq);
		datas = db.getListByPro(list, proName, datas);
		return datas;
	} 

	/**
	 * 编制单位信息
	 * @return
	 */
	public Map<String, Object> getBzdw() {
		String sql = "SELECT GUID,XXDM,XXMC FROM CW_XXXXB WHERE ROWNUM=1";
		Map map = new HashMap<String, Object>();
		map = db.queryForMap(sql);
		return map;
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
		for (int i = 0; i <list.size(); i++) {
			Map map = (Map) list.get(i);
			String zc = Validate.isNullToDefaultString(map.get("zc"), "");
			String qmye1 = Validate.isNullToDefaultString(map.get("qmye1"), "").replace(",", "");
			String ncye1 = Validate.isNullToDefaultString(map.get("ncye1"), "").replace(",", "");
			String fzhjzc = Validate.isNullToDefaultString(map.get("fzhjzc"), "");
			String qmye2 = Validate.isNullToDefaultString(map.get("qmye2"), "").replace(",", "");
			String ncye2 = Validate.isNullToDefaultString(map.get("ncye2"), "").replace(",", "");
			String bh = Validate.isNullToDefaultString(map.get("bh"), "");
			String bblx = Validate.isNullToDefaultString(map.get("bblx"), "");
//			int bh=i+1;
//			String bh1=bh+"";
			StringBuffer sql =new StringBuffer();
//			String bh = Validate.isNullToDefaultString(map.get("bh"), "");
			if (i == 0) {
				String delSql = "DELETE FROM CW_zcfzb where 1=1 and bblx='"+bblx+"' ";
				sqlList.add(delSql);
			}
			//String sql="update  cwbb_zcfzb set qmye1='"+qmye1+"',ncye1='"+ncye1+"',qmye2='"+qmye2+"',ncye2='"+ncye2+"'where bh='"+bh+"'";
			sql.append(" INSERT INTO CW_zcfzb");
			sql.append(" (guid,zc,qmye1,ncye1,fzhjzc,qmye2,hc,bblx,ncye2)");
			sql.append(" VALUES");
			sql.append(" (");
			sql.append(" SYS_GUID(),");
			sql.append(" '" + zc + "',");
			sql.append(" '" + qmye1 + "',");
			sql.append(" '" + ncye1 + "',");
			sql.append(" '" + fzhjzc + "',");
			sql.append(" '" + qmye2 + "',");
			sql.append(" '" + bh + "',");
			sql.append(" '"+bblx+"',");
			sql.append(" '" + ncye2 + "'");
			sql.append(" )");
			sqlList.add(sql.toString());
			System.err.println("__++++++"+sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	/**
	 * 导出查询资产负债临时表
	 * @param searchValue
	 * @return
	 */
	public List<Map<String, Object>> getListZcfzb(String searchValue) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  REPLACE((REPLACE(ZC, '<b>','')),'</b>','') AS ZC, QMYE1,NCYE1, REPLACE((REPLACE(FZHJZC, '<b>','')),'</b>','') AS FZHJZC,QMYE2,NCYE2 FROM TEMP_CWBB_ZCFZB_FZ T ");
		return db.queryForList(sql.toString());
	}
	/**
	 * 导出查询       旧资产负债临时表
	 * @param searchValue
	 * @return
	 */
	public List<Map<String, Object>> getListJzcfzb(String searchValue) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT  REPLACE((REPLACE(ZC, '<b>','')),'</b>','') AS ZC, QMYE1,NCYE1, REPLACE((REPLACE(FZHJZC, '<b>','')),'</b>','') AS FZHJZC,QMYE2,NCYE2 FROM TEMP_CWBB_JZCFZB_FZ T ");
		return db.queryForList(sql.toString());
	}
	public List<Map<String, Object>> getList2(String searchValue, String sql) {
		return db.queryForList(sql);
	}
	/**
	 * 查询月份
	 * @return
	 */
	public List<Map<String, Object>> getMonths() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
}
