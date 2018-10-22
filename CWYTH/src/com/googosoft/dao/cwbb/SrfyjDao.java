package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("srfyjDao")
public class SrfyjDao extends BaseDao {
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
		String sql = "SELECT COUNT(0) FROM CW_JSRFYB T WHERE T.ZTBH='" + sszt
				+ "' AND NY='" + sysDate + "' AND BBLX='" + bblx
				+ "' AND JZPZ='" + jzpz + "'";
		int result = Integer.parseInt(Validate.isNullToDefaultString(
				db.queryForSingleValue(sql), "0"));
		System.err.println("ssssssssssssssssssssss"+result);
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
		sql.append(" FROM CW_JSRFYB T");
		sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.BBLX='" + bblx + "'");
		sql.append(" AND T.NY='" + sysDate + "'");
		sql.append(" AND T.ZTBH='" + sszt + "'");
		sql.append(" AND T.JZPZ='" + jzpz + "'");
		sql.append(" ORDER BY HC");
		list = db.queryForList(sql.toString());
		System.err.println("月list="+list);
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
			String sysDate, String sszt, String jzpz, String bzdw,String kjzd) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName="";
		if("0".equals(bblx)){
			proName = "{CALL pro_cwyth_jsrfy_nd(?,?,?,?,?,?,?,?)}";
		}else{
			proName = "{CALL pro_cwyth_jsrfy(?,?,?,?,?,?,?,?)}";
		}
		list.add(sysDate);
		list.add(jzpz);
		list.add(bzdw);
		list.add(sszt);
		list.add(LUser.getGuid());
		list.add(bblx);
		list.add(kjzd);
		System.err.println(sysDate+"  "+jzpz+" "+bzdw+" "+sszt+" "+LUser.getGuid()+" "+bblx+"  "+kjzd);
		datas = db.getListByPro(list, proName, datas);
		System.err.println("datas="+datas);
		return datas;
	}
	/**
	 * 获取会计制度
	 */
	public String getkjzd(String sszt, String sysDate) {
		String sql = " select distinct kjzd from cw_pzlrzb where sszt='"+sszt+"' and  to_char(pzrq,'yyyy') =substr('"+sysDate+"',0,4) ";
		return db.queryForSingleValue(sql);
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
				String delSql = "DELETE FROM CW_JSRFYB WHERE ZTBH='" + ztbh
						+ "' AND BZDW='" + bzdw + "' AND BBLX='" + bblx
						+ "' AND JZPZ='" + jzpz + "' AND NY='"+ny+"'";
				sqlList.add(delSql);
			}
			sql.append(" INSERT INTO CW_JSRFYB");
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

	public List<Map<String, Object>> getList(String searchValue, String sql) {
		return db.queryForList(sql);
	}

	public List getSrzcjszbListByPro(String jsyf, String sfjz, String ztgid, String jzpz) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwbb_srzcjszb(?,?,?,?,?,?)}";
		list.add(jsyf);
		list.add("1");
		list.add(sfjz);
		list.add(ztgid);
		list.add(jzpz);
		System.err.println("jsyf="+jsyf+"=====sfjz="+sfjz+"=====ztguid="+ztgid+"=====jzpz="+jzpz);
		datas = db.getListByPro(list, proName, datas);
		return datas;
	}

	public boolean doSaveSrzcjszb(List list, String sfjz, String jzpz) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i <list.size(); i++) {
			Map map = (Map) list.get(i);
			String jsyf=Validate.isNullToDefaultString(map.get("jsyf"), "");
			String bzdw = Validate.isNullToDefaultString(map.get("bzdw"), "");
			String sr = Validate.isNullToDefaultString(map.get("sr"), "");
			String hc1 = Validate.isNullToDefaultString(map.get("hc1"), "");
			String ncyss1 = Validate.isNullToDefaultString(map.get("ncyss1"), "").replace(",", "");
			String qms1 = Validate.isNullToDefaultString(map.get("qms1"), "").replace(",", "");
			String zc1 = Validate.isNullToDefaultString(map.get("zc1"), "");
			String hc2 = Validate.isNullToDefaultString(map.get("hc2"), "");
			String ncyss2 = Validate.isNullToDefaultString(map.get("ncyss2"), "").replace(",", "");
			String qms2 = Validate.isNullToDefaultString(map.get("qms2"), "").replace(",", "");
			String zc2 = Validate.isNullToDefaultString(map.get("zc2"), "");
			String hc3 = Validate.isNullToDefaultString(map.get("hc3"), "");
			String ncyss3 = Validate.isNullToDefaultString(map.get("ncyss3"), "").replace(",", "");
			String qms3 = Validate.isNullToDefaultString(map.get("qms3"), "").replace(",", "");
			String bh = Validate.isNullToDefaultString(map.get("bh"), "");
			StringBuffer sql =new StringBuffer();
			if (i == 0) {
				String delSql = "DELETE FROM CW_SRZCJSZB";
				sqlList.add(delSql);
			}
			sql.append(" INSERT INTO CW_SRZCJSZB");
			sql.append(" (guid,jsyf,bzdw,sr,hc1,ncyss1,qms1,zc1,hc2,ncyss2,qms2,zc2,hc3,ncyss3,qms3,sfjz,jzpz,bh)");
			sql.append(" VALUES");
			sql.append(" (");
			sql.append(" SYS_GUID(),");
			sql.append(" '" + jsyf + "',");
			sql.append(" '" +bzdw + "',");
			sql.append(" '" +sr + "',");
			sql.append(" '" + hc1 + "',");
			sql.append(" '" + ncyss1 + "',");
			sql.append(" '" + qms1 + "',");
			sql.append(" '" + zc1 + "',");
			sql.append(" '" + hc2 + "',");
			sql.append(" '" + ncyss2 + "',");
			sql.append(" '" + qms2 + "',");
			sql.append(" '" + zc2 + "',");
			sql.append(" '" + hc3 + "',");
			sql.append(" '" + ncyss3 + "',");
			sql.append(" '" + qms3 + "',");
			sql.append(" '" + sfjz + "',");
			sql.append(" '" + jzpz + "',");
			sql.append(" '" + bh + "'");
			sql.append(" )");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	/**
	 * 从上一次保存的位置获取收入支出决算总表
	 * @param sfjz 
	 * @param jzpz 
	 * @param jsyf 
	 * @return
	 */
	public List getSrzcjszbList(String jsyf, String jzpz, String sfjz) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT guid,jsyf,bzdw, bh, sr, hc1, to_char(qms1,'FM999,999,999,990.00') qms1,");
		sql.append("to_char(ncyss1,'FM999,999,999,990.00') ncyss1, zc1,hc2,to_char(qms2,'FM999,999,999,990.00') qms2,");
		sql.append("to_char(ncyss2,'FM999,999,999,990.00') ncyss2,");
		sql.append("zc2,hc3,to_char(qms3,'FM999,999,999,990.00') qms3,to_char(ncyss3,'FM999,999,999,990.00') ncyss3,czr,czrq,sfjz,jzpz");
		sql.append("  FROM CW_SRZCJSZB  where jsyf=? and jzpz=? and sfjz=?  order by to_number(bh)");
		Object[] obj=new Object[] {
				jsyf,jzpz,sfjz
		};
		return db.queryForList(sql.toString(),obj);
	}

	
}
