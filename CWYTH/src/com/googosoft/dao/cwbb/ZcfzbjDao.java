package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;








import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;
@Repository("zcfzbjDao")
public class ZcfzbjDao extends BaseDao{
	
	public List<Map<String, Object>> getResultListByPro1(String ksyf, String jsyf,
			String saasdm,String sfjz,String ztgid,String jzpz) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwbb_jsclsbb(?,?,?,?,?,?,?)}";
		list.add(ksyf);
		list.add(jsyf);
		list.add(saasdm);
		list.add(sfjz);
		list.add(ztgid);
		list.add(jzpz);
		System.err.println("=====bbyf="+ksyf+"jsyf="+jsyf+"=====saasdm="+saasdm+"=====sfjz="+sfjz+"=====ztguid="+ztgid+"=====jzpz="+jzpz);
		datas = db.getListByPro(list, proName, datas);
		return datas;
	} 
	/**
	 * 保存
	 * 
	 * @param list
	 * @param sfjz 
	 * @param jzpz 
	 * @return
	 */
	public boolean doSave(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i <list.size(); i++) {
			Map map = (Map) list.get(i);
			String zc = Validate.isNullToDefaultString(map.get("zc"), "");
			String nyr=Validate.isNullToDefaultString(map.get("nyr"), "");
			String bzdw = Validate.isNullToDefaultString(map.get("bzdw"), "");
			String qmye1 = Validate.isNullToDefaultString(map.get("qmye1"), "").replace(",", "");
			String ncye1 = Validate.isNullToDefaultString(map.get("ncye1"), "").replace(",", "");
			String fzhjzc = Validate.isNullToDefaultString(map.get("fzhjzc"), "");
			String qmye2 = Validate.isNullToDefaultString(map.get("qmye2"), "").replace(",", "");
			String ncye2 = Validate.isNullToDefaultString(map.get("ncye2"), "").replace(",", "");
			String bh = Validate.isNullToDefaultString(map.get("bh"), "");
			String bblx = Validate.isNullToDefaultString(map.get("bblx"), "");
			StringBuffer sql =new StringBuffer();
			if (i == 0) {
				String delSql = "DELETE FROM CW_jzcfzb where 1=1 and bblx='"+bblx+"' and nyr='"+nyr+"'";
				sqlList.add(delSql);
			}
			sql.append(" INSERT INTO CW_jzcfzb");
			sql.append(" (guid,zc,nyr,bzdw,qmye1,ncye1,fzhjzc,qmye2,hc,bblx,ncye2)");
			sql.append(" VALUES");
			sql.append(" (");
			sql.append(" SYS_GUID(),");
			sql.append(" '" + zc + "',");
			sql.append(" '" + nyr + "',");
			sql.append(" '" + bzdw + "',");
			sql.append(" '" + qmye1 + "',");
			sql.append(" '" + ncye1 + "',");
			sql.append(" '" + fzhjzc + "',");
			sql.append(" '" + qmye2 + "',");
			sql.append(" '" + bh + "',");
			sql.append(" '"+bblx+"',");
			sql.append(" '" + ncye2 + "'");
			sql.append(" )");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	
	
	/**
	 * 判断Cw_srfyb是否已经存入数据
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public int checkzcfzb(String bblx,String sysDate) {
		String sql = "SELECT COUNT(0) FROM CW_JZCFZB T WHERE T.BBLX='"+bblx+"' AND NYR='" + sysDate+"' order by hc";
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
	public List<Map<String, Object>> getResultList(String bblx, String sysDate) {
		List list = new ArrayList<Map<String, Object>>();
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT");
		sql.append(" t.ztbh, t.guid, t.bzdw,t.nyr,t.zc,t.bblx,"); 
		sql.append("DECODE(NVL(T.QMYE1,0.00),0.00,'',TO_CHAR(ROUND(T.QMYE1,2),'FM999,999,999,990.00')) AS QMYE1, t.fzhjzc,");
		sql.append("DECODE(NVL(T.NCYE1,0.00),0.00,'',TO_CHAR(ROUND(T.NCYE1,2),'FM999,999,999,990.00')) AS NCYE1,t.czr,t.czrq,");
		sql.append("DECODE(NVL(T.QMYE2,0.00),0.00,'',TO_CHAR(ROUND(T.QMYE2,2),'FM999,999,999,990.00')) AS QMYE2,");
		sql.append("DECODE(NVL(T.NCYE2,0.00),0.00,'',TO_CHAR(ROUND(T.NCYE2,2),'FM999,999,999,990.00')) AS NCYE2,");
		sql.append(" T.HC AS BH,");
		sql.append(" X.XXMC AS XXMC ");
		sql.append(" FROM CW_JZCFZB T");
		sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
		sql.append(" WHERE 1=1");
		sql.append(" AND T.BBLX='" + bblx + "'");
		sql.append(" AND T.NYR='" + sysDate + "' order by to_number(t.hc)");
		list = db.queryForList(sql.toString());
		return list;
	}
	/**
	 * 通过存储过程获得新的资产负债表
	 * @param bbnd
	 * @param ksyf
	 * @param jsyf
	 * @param saasdm
	 * @param sfjz
	 * @param type
	 * @param ztgid
	 * @param rq
	 * @param jzpz
	 * @return
	 */
	public List<Map<String, Object>> getResultListByProNew( String ksyf,String jsyf, String saasdm, String sfjz,
			String type, String ztgid, String jzpz) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwbb_jsclsbb_new(?,?,?,?,?,?,?,?)}";
		list.add(ksyf);
		list.add(jsyf);
		list.add(saasdm);
		list.add(sfjz);
		list.add(type);
		list.add(ztgid);
		list.add(jzpz);
		System.err.println("=====bbyf="+ksyf+"jsyf="+jsyf+"=====saasdm="+saasdm+"=====sfjz="+sfjz+"=====type="+type+"=====ztguid="+ztgid+"=====jzpz="+jzpz);
		datas = db.getListByPro(list, proName, datas);
		return datas;
	}
	/**
	 * 保存新的资产负债表
	 * @param list
	 * @param jzpz 
	 * @param sfjz 
	 * @return
	 */
	public boolean doSaveNew(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		List<Object[]> dataList=new ArrayList<Object[]>();
		Object[] obj;
		String sql1="update CW_xzcfzb set sfscbc ='0'";
		db.update(sql1);
		for (int i = 0; i <list.size(); i++) {
			Map map = (Map) list.get(i);
			String zc = Validate.isNullToDefaultString(map.get("zc"), "");
			String ksyf=Validate.isNullToDefaultString(map.get("ksyf"), "");
			String jsyf=Validate.isNullToDefaultString(map.get("jsyf"), "");
			String bzdw = Validate.isNullToDefaultString(map.get("bzdw"), "");
			String qmye1 = Validate.isNullToDefaultString(map.get("qmye1"), "").replace(",", "");
			String ncye1 = Validate.isNullToDefaultString(map.get("ncye1"), "").replace(",", "");
			String fzhjzc = Validate.isNullToDefaultString(map.get("fzhjzc"), "");
			String qmye2 = Validate.isNullToDefaultString(map.get("qmye2"), "").replace(",", "");
			String ncye2 = Validate.isNullToDefaultString(map.get("ncye2"), "").replace(",", "");
			String bh = Validate.isNullToDefaultString(map.get("bh"), "");
			String sfjz = Validate.isNullToDefaultString(map.get("sfjz"), "0");
			String jzpz = Validate.isNullToDefaultString(map.get("jzpz"), "0");
			String ztbh = Validate.isNullToDefaultString(map.get("ztgid"), "");
			StringBuffer sql;
			if (i == 0) {
				sql=new StringBuffer();
				sql.append("DELETE FROM CW_xzcfzb where");
				sql.append(" ksyf=? and jsyf=? and jzpz=? and sfjz=? and ztbh=?");
				obj=new Object[] {
					ksyf,jsyf,jzpz,sfjz,ztbh	
				};
				sqlList.add(sql.toString());
				dataList.add(obj);
			}
			sql=new StringBuffer();
			sql.append(" INSERT INTO CW_xzcfzb");
			sql.append(" (guid,zc,ksyf,jsyf,bzdw,qmye1,ncye1,fzhjzc,qmye2,bh,ncye2,sfjz,jzpz,ztbh,sfscbc)");
			sql.append(" VALUES");
			sql.append(" (sys_guid(),?,?,?,?,?,");
			sql.append("?,?,?,?,?,?,?,?,'1')");
			obj=new Object[] {
				zc,ksyf,jsyf,bzdw,qmye1,ncye1,fzhjzc,qmye2,bh,ncye2,sfjz,jzpz,ztbh
			};
			sqlList.add(sql.toString());
			dataList.add(obj);
		}
		bool = db.batchUpdate(sqlList,dataList);
		return bool;
	}
	/**
	 * 从上一次记录的位置获取新资产负债表
	 * @return
	 */
	public List getZcfzbnewList(String ztgid, String ksyf, String jsyf, String jzpz, String sfjz) {
		StringBuffer sql=new StringBuffer();
		sql.append("select GUID, ZTBH, BZDW, JSYF, BH, ZC, FZHJZC, CZR, CZRQ,");
		sql.append("KSYF, JZPZ, SFJZ,");
		sql.append("to_char(qmye1,'FM999,999,999,990.00') qmye1,to_char(ncye1, 'FM999,999,999,990.00') ncye1,");
		sql.append("to_char(qmye2, 'FM999,999,999,990.00') qmye2, to_char(ncye2, 'FM999,999,999,990.00') ncye2");
		sql.append(" from cw_xzcfzb ");
		sql.append(" where sfscbc='1'");
		sql.append("  order by to_number(bh)");
		Object[] obj=new Object[] {
				ksyf,jsyf,jzpz,sfjz,ztgid		
		};
		return db.queryForList(sql.toString());
	}
	
}
