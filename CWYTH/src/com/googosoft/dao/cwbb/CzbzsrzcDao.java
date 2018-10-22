package com.googosoft.dao.cwbb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

/**
 * 
 * @author wangzhiduo
 * @date 2018-1-2下午7:18:26
 */
@Repository("czbzsrzcbDao")
public class CzbzsrzcDao extends BaseDao{
	
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
	 * 判断Cw_czbzsrzcb是否已经存入数据
	 * 
	 * @param bblx
	 * @param sysDate
	 * @param sszt
	 * @return
	 */
	public int checkCzbz(String bblx, String sysDate, String sszt, String jzpz) {
		String sql = "SELECT COUNT(0) FROM CW_CZBZSRZCB T WHERE T.ZTBH='" + sszt+ "' AND ND='" + sysDate + "' AND JZPZ='" + jzpz + "'";
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
		sql.append(" T.ND AS DATES,");
		sql.append(" T.HC AS XH,");
		sql.append(" T.XMBH AS XMMC,");
//		sql.append(" X.XXMC AS XXMC,");
		sql.append(" DECODE(NVL(T.BNS,0.00),0.00,'',TO_CHAR(ROUND(T.BNS,2),'FM999,999,999,999,990.00')) AS BYS,");//本年数
		sql.append(" DECODE(NVL(T.SNS,0.00),0.00,'',TO_CHAR(ROUND(T.SNS,2),'FM999,999,999,999,990.00')) AS BNLJS");//上年累积数
		sql.append(" FROM CW_CZBZSRZCB T");
//		sql.append(" LEFT JOIN CW_XXXXB X ON X.GUID=T.BZDW");
		sql.append(" WHERE 1=1");
//		sql.append(" AND T.BBLX='" + bblx + "'");
		sql.append(" AND T.ND='" + sysDate + "'");
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
			String sysDate, String sszt, String jzpz, String bzdw ,String kjzd) {
		List list = new ArrayList<Map<String, Object>>();
		List datas = new ArrayList<Map<String, Object>>();
		String proName = "{CALL pro_cwbb_czbzsrzc(?,?,?,?,?,?,?,?)}";
		list.add(sysDate);
		list.add(jzpz);
		list.add(bzdw);
		list.add(sszt);
		list.add(LUser.getGuid());
		list.add(bblx);
		list.add(kjzd);
		datas = db.getListByPro(list, proName, datas);
		System.err.println(sysDate+" "+jzpz+" "+bzdw+" "+sszt+"  "+LUser.getGuid()+" "+bblx+" "+kjzd);
		System.err.println(datas);
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
			String nd = Validate.isNullToDefaultString(map.get("nd"), "");
			String hc = Validate.isNullToDefaultString(map.get("hc"), "");
			String bblx = Validate.isNullToDefaultString(map.get("bblx"), "");
			String xmbh = Validate.isNullToDefaultString(map.get("xmbh"), "");
			String read = Validate.isNullToDefaultString(map.get("read"), "");
			String bns = Validate.isNullToDefaultString(map.get("bns"), "").replace(",", "");
			String sns = Validate.isNullToDefaultString(map.get("sns"), "").replace(",", "");
			String czr = Validate.isNullToDefaultString(LUser.getGuid(), "");
			String jzpz = Validate.isNullToDefaultString(map.get("jzpz"), "");
			String flag = Validate.isNullToDefaultString(map.get("flag"), "");
			StringBuffer sql = new StringBuffer();
			if (i == 0) {
				String delSql = "DELETE FROM CW_CZBZSRZCB WHERE ZTBH='" + ztbh+ "' AND BZDW='" + bzdw + "' AND ND='" + nd + "' AND JZPZ='" + jzpz + "'";
				sqlList.add(delSql);
			}
			sql.append(" INSERT INTO CW_CZBZSRZCB");
			sql.append(" (GUID,ZTBH,BZDW,ND,HC,XMBH,BNS,SNS,JZPZ,CZR,READ,flag)");
			sql.append(" VALUES");
			sql.append(" (");
			sql.append(" SYS_GUID(),");
			sql.append(" '" + ztbh + "',");
			sql.append(" '" + bzdw + "',");
			sql.append(" '" + nd + "',");
			sql.append(" '" + hc + "',");
//			sql.append(" '" + bblx + "',");
			sql.append(" '" + xmbh + "',");
			sql.append(" '" + bns + "',");
			sql.append(" '" + sns + "',");
			sql.append(" '" + jzpz + "',");
			sql.append(" '" + czr + "',");
			sql.append(" '"+read+"',");
			sql.append(" '"+flag+"')");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	/**
	 * 财政补助收入支出导出
	 * @param searchValue
	 * @return
	 */
	public List<Map<String, Object>> getListCzbzsrzc(String searchValue) {
//		String sql = " select  replace(replace(xmmc,'<b>',''),'</b>','') as xmmc ,bys,bnljs from   Cw_czbzsrzcb2 ";
		//上面的表不知道用来做什么的，本模块的所有操作都未涉及，暂时修改使用下面的表数据以恢复正常导出
		String sql = " select  replace(replace(xmbh,'<b>',''),'</b>','') as xmmc ,TO_CHAR(ROUND(BNS,2),'FM999,999,999,999,990.00') AS BYS,TO_CHAR(ROUND(SNS,2),'FM999,999,999,999,990.00') AS BNLJS  from   Cw_czbzsrzcb ";
		return db.queryForList(sql);
	}
	/**
	 * 获取会计制度
	 */
	public String getkjzd(String sszt, String sysDate) {
		String sql = " select distinct kjzd from cw_pzlrzb where sszt='"+sszt+"' and  to_char(pzrq,'yyyy') =substr('"+sysDate+"',0,4) ";
		return db.queryForSingleValue(sql);
	}

}
