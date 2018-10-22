package com.googosoft.dao.kjhs;


import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("bmmxz1Dao")
public class BmMxz1Dao extends BaseDao {
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
	/**
	 * 获得sql语句
	 * @param pxfs
	 * @return
	 */
	public String getSql(String kmbh,String kjzd,String treesearch,PageData pd){				
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from(");
		sql.append(" SELECT");
		sql.append(" T.GUID,T.PZBHGUID,");
		sql.append(" '('||T.KMBH||')'||T.KMMC as KMMC,");
		sql.append(" '('||T.wldwbh||')'||T.wldwmc as wldwmc,");
		sql.append(" T.PZH,t.pzlxmc,");
		sql.append(" T.ZY,");
		sql.append(" T.JJFL,");
		sql.append("  ('('||T.JJFL||')'||(SELECT KMMC  FROM CW_JJKMB WHERE KMBH=T.JJFL)) AS JJFLMC,");
		sql.append("( '('||T.XMBH||')'||(SELECT XMMC FROM cw_xmjbxxb WHERE BMBH=T.BMBH AND XMBH=T.XMBH) )AS XMMC,");
		sql.append(" T.BMBH,T.KMBH,");
		sql.append(" (select  '('||dwbh||')'||mc  from gx_sys_dwb where dwbh = t.BMBH) bmmc,");
		sql.append(" T.XMBH,");
		sql.append(" T.XH,");
		//sql.append(" case when t.zy='期末余额' then TO_CHAR(T.PZRQ,'YYYY-MM') else TO_CHAR(T.PZRQ,'YYYY-MM-dd') end PZRQ,");
		sql.append(" t.PZRQ,");
		sql.append(" DECODE(NVL(T.JFJE,0.00),0.00,'',TO_CHAR(ROUND(T.JFJE,2),'FM999,999,999,990.00'))JFJE,");
		sql.append(" DECODE(NVL(T.DFJE,0.00),0.00,'',TO_CHAR(ROUND(T.DFJE,2),'FM999,999,999,990.00'))DFJE,");
		sql.append(" DECODE(NVL(T.QCYE,0.00),0.00,'',TO_CHAR(ROUND(T.QCYE,2),'FM999,999,999,990.00'))QCYE,");
		sql.append(" DECODE(NVL(T.YE,0.00),0.00,'',TO_CHAR(ROUND(T.YE,2),'FM999,999,999,990.00'))YE,");
		sql.append(" DECODE(T.FX,'0','借','1','贷','平')FX");
		sql.append(" FROM CWPT_MXZ T WHERE T.LOGIN='"+LUser.getRybh()+"'");
		if(Validate.noNull(kmbh)&&!getSfmj(kmbh)){
			sql.append(" AND exists  (select null from ");
			sql.append("(select K.KMBH from CW_KJKMSZB K where k.kjzd='"+kjzd+"'  start with K.jb = '"+kmbh+"' connect by prior jb = sjfl and sjfl != 'root'order by KMBH asc) b ");
			sql.append("where b.KMBH=T.KMBH)");
		}
		if(Validate.noNull(treesearch)){
			sql.append(" and T.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		String zy = pd.getString("zy");
		String km2 = pd.getString("km2");
		String km1 = pd.getString("km1");
		String km = pd.getString("km");
		String PZBH1 = pd.getString("PZBH1");
		String PZBH2 = pd.getString("PZBH2");
		String PZRQ1 = pd.getString("PZRQ1");
		String PZRQ2 = pd.getString("PZRQ2");
		String JFJE1 = pd.getString("JFJE1");
		String JFJE2 = pd.getString("JFJE2");
		String DFJE1 = pd.getString("DFJE1");
		String DFJE2 = pd.getString("DFJE2");
		if(Validate.noNull(zy)){
			sql.append(" and zy like '%"+zy+"%'");
		}
		if(Validate.noNull(km)){
			sql.append(" and kmbh = '"+km+"'");
		}
		if(Validate.noNull(km1)){
			sql.append(" and to_number(kmbh) >= '"+km1+"'");
		}
		if(Validate.noNull(km2)){
			sql.append(" and to_number(kmbh) <= '"+km2+"'");
		}
		if(Validate.noNull(PZBH1)){
			sql.append(" and to_number(PZH) >= '"+PZBH1+"'");
		}
		if(Validate.noNull(PZBH2)){
			sql.append(" and to_number(PZH) <= '"+PZBH2+"'");
		}
		if(Validate.noNull(PZRQ1)){
			sql.append(" and (CASE LENGTH(PZRQ) WHEN 7 THEN PZRQ || '-01' ELSE PZRQ END) >= '"+PZRQ1+"'");
		}
		if(Validate.noNull(PZRQ2)){
			sql.append(" and (CASE LENGTH(PZRQ) WHEN 7 THEN PZRQ || '-01' ELSE PZRQ END)<= '"+PZRQ2+"'");
		}
		if(Validate.noNull(JFJE1)){
			sql.append(" and JFJE >= '"+JFJE1+"'");
		}
		if(Validate.noNull(JFJE2)){
			sql.append(" and JFJE <= '"+JFJE2+"'");
		}
		if(Validate.noNull(DFJE1)){
			sql.append(" and DFJE >= '"+DFJE1+"'");
		}
		if(Validate.noNull(DFJE2)){
			sql.append(" and DFJE <= '"+DFJE2+"'");
		}
		sql.append(" )t");
		sql.append(" GROUP BY T.GUID,t.wldwmc,T.PZBHGUID,T.KMBH,T.KMMC, T.PZH,t.pzlxmc, T.ZY, T.JJFL, JJFLMC, XMMC,T.BMBH,T.XMBH,PZRQ,JFJE,DFJE,QCYE,YE,FX,bmmc,xh");
		sql.append(" order by xh ");
		System.err.println("***"+sql.toString());
		return sql.toString();
	}	
	/**
	 * 判断科目是不是末级科目
	 * @param kmbh
	 * @return
	 */
	public boolean getSfmj(String kmbh){
		String sql = "select sfmj from CW_KJKMSZB t where t.kmbh=?";
		sql = Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{kmbh}),"");
		if("0".equals(sql)){
			return false;
		}
		return true;
	}
	/**
	 * 获得页面数据信息
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd) {
		String sql=getSql(kmbh,kjzd,treesearch,pd);
		return db.queryForList(sql);
	}
	/**
	 * 查询级别list
	 * 
	 * @return
	 */
	public List getKjkmJb() {
		String sql = "SELECT DISTINCT KMJC AS JB FROM CW_KJKMSZB WHERE 1=1 AND KMJC<>'root' ORDER BY TO_NUMBER(KMJC)";
		List list = db.queryForList(sql);
		return list;
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(List list,String sszt,String kjzd,String kmbh,PageData pd) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_MXZ_WLDWNEW";
		if(Validate.isNull(kmbh)){
			procName = "PRO_CWYTH_MXZ_WLDWNEW_BM";
		}
		List parInList = new ArrayList<String>();
		boolean bool = false;
		if(list.size()==0){
			return bool;
		}
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			//科目
			String startKjkm = Validate.isNullToDefaultString(map.get("startKjkm"), "");
			if(startKjkm!=""&&startKjkm.contains("(")){
				startKjkm = startKjkm.substring(1,startKjkm.indexOf(")"));
			}	
			//是否包含记账
			String jzpz = Validate.isNullToDefaultString(map.get("jzpz"), "0");
			//科目级次
			String startJc = "1";
			String endJc = "1";
			//排序方式
			String pxfs = "1";
			//会计期间
			String year = Validate.isNullToDefaultString(map.get("year"), "");
			//判断v_wlbh是哪个  type=1 v_wlbh 是wlbh， type=2 v_wlbh是rybh  type=3 v_wlbh是xmbh  type=4 v_wlbh是bmbh、
			String type = "4";
			//往来单位编号
			String wlbh = Validate.isNullToDefaultString(map.get("wlbh"), "");
			String startMonth = Validate.isNullToDefaultString(map.get("startMonth"), "");
			String endMonth = Validate.isNullToDefaultString(map.get("endMonth"), "");
			
			parInList.add(year+"-"+startMonth);
			parInList.add(year+"-"+endMonth);
			//parInList.add(startKjkm);
			parInList.add(startJc);
			parInList.add(endJc);
			parInList.add(pxfs);
			parInList.add(jzpz);
			parInList.add(sszt);
			parInList.add(LUser.getRybh());
			parInList.add(kjzd);
			parInList.add(kmbh);
			parInList.add(type);
			parInList.add(wlbh);
			
			//综合查询拼接条件
			String zy = pd.getString("zy");
			String km2 = pd.getString("km2");
			String km = pd.getString("km");
			String km1 = pd.getString("km1");
			String PZBH1 = pd.getString("PZBH1");
			String PZBH2 = pd.getString("PZBH2");
			String PZRQ1 = pd.getString("PZRQ1");
			String PZRQ2 = pd.getString("PZRQ2");
			String JFJE1 = pd.getString("JFJE1");
			String JFJE2 = pd.getString("JFJE2");
			String DFJE1 = pd.getString("DFJE1");
			String DFJE2 = pd.getString("DFJE2");
			StringBuffer sql = new StringBuffer();
			if(Validate.noNull(zy)){
				sql.append(" and zy like '%"+zy+"%'");
			}
			if(Validate.noNull(km)){
				sql.append(" and to_number(kmbh) = '"+km+"'");
			}
			if(Validate.noNull(km1)){
				sql.append(" and to_number(kmbh) >= '"+km1+"'");
			}
			if(Validate.noNull(km2)){
				sql.append(" and to_number(kmbh) <= '"+km2+"'");
			}
			if(Validate.noNull(PZBH1)){
				sql.append(" and to_number(zb.PZBH )>= '"+PZBH1+"'");
			}
			if(Validate.noNull(PZBH2)){
				sql.append(" and to_number(zb.PZBH )<= '"+PZBH2+"'");
			}
			if(Validate.noNull(PZRQ1)){
				sql.append(" and pzrq >= to_date('"+PZRQ1+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(PZRQ2)){
				sql.append(" and pzrq<= to_date('"+PZRQ2+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(JFJE1)){
				sql.append(" and JFJE >= '"+JFJE1+"'");
			}
			if(Validate.noNull(JFJE2)){
				sql.append(" and JFJE <= '"+JFJE2+"'");
			}
			if(Validate.noNull(DFJE1)){
				sql.append(" and DFJE >= '"+DFJE1+"'");
			}
			if(Validate.noNull(DFJE2)){
				sql.append(" and DFJE <= '"+DFJE2+"'");
			}
			parInList.add(sql.toString());
			System.err.println(sql.toString()+"++++++====");
			System.err.println(parInList+"========");
		}
		try {
			bool = db.batchUpdateByProcedure(procName, parInList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResultnew(String bmbh ,String sszt,String kmbh,String nian,String StartMonth,String EndMonth,PageData pd) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_MXZ_WLDWNEW";
		List parInList = new ArrayList<String>();
		boolean bool = false;
			//科目
			//是否包含记账
			String jzpz = "1";
			//科目级次
			String startJc = "1";
			String endJc = "1";
			String kjzd = "0001";
			//排序方式
			String pxfs = "1";
			String year = Validate.isNullToDefaultString(nian, "");
			//判断v_wlbh是哪个  type=1 v_wlbh 是wlbh， type=2 v_wlbh是rybh  type=3 v_wlbh是xmbh  type=4 v_wlbh是bmbh、
			String type = "4";
			//往来单位编号
			String wlbh = Validate.isNullToDefaultString(bmbh, "");
			String startMonth = Validate.isNullToDefaultString(StartMonth, "");
			String endMonth = Validate.isNullToDefaultString(EndMonth, "");
			parInList.add(year+"-"+startMonth);
			parInList.add(year+"-"+endMonth);
			//parInList.add(startKjkm);
			parInList.add(startJc);
			parInList.add(endJc);
			parInList.add(pxfs);
			parInList.add(jzpz);
			parInList.add(sszt);
			parInList.add(LUser.getRybh());
			parInList.add(kjzd);
			parInList.add(kmbh);
			parInList.add(type);
			parInList.add(wlbh);
			
			//综合查询拼接条件
			String zy = pd.getString("zy");
			String km2 = pd.getString("km2");
			String km = pd.getString("kjkm");
			String km1 = pd.getString("km1");
			String PZBH1 = pd.getString("PZBH1");
			String PZBH2 = pd.getString("PZBH2");
			String PZRQ1 = pd.getString("PZRQ1");
			String PZRQ2 = pd.getString("PZRQ2");
			String JFJE1 = pd.getString("JFJE1");
			String JFJE2 = pd.getString("JFJE2");
			String DFJE1 = pd.getString("DFJE1");
			String DFJE2 = pd.getString("DFJE2");
			StringBuffer sql = new StringBuffer();
			if(Validate.noNull(zy)){
				sql.append(" and zy like '%"+zy+"%'");
			}
			if(Validate.noNull(km)){
				sql.append(" and to_number(kmbh) = '"+km+"'");
			}
			if(Validate.noNull(km1)){
				sql.append(" and to_number(kmbh) >= '"+km1+"'");
			}
			if(Validate.noNull(km2)){
				sql.append(" and to_number(kmbh) <= '"+km2+"'");
			}
			if(Validate.noNull(PZBH1)){
				sql.append(" and to_number(PZH) >= '"+PZBH1+"'");
			}
			if(Validate.noNull(PZBH2)){
				sql.append(" and to_number(PZH) <= '"+PZBH2+"'");
			}
			if(Validate.noNull(PZRQ1)){
				sql.append(" and pzrq >= to_date('"+PZRQ1+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(PZRQ2)){
				sql.append(" and pzrq<= to_date('"+PZRQ2+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(JFJE1)){
				sql.append(" and JFJE >= '"+JFJE1+"'");
			}
			if(Validate.noNull(JFJE2)){
				sql.append(" and JFJE <= '"+JFJE2+"'");
			}
			if(Validate.noNull(DFJE1)){
				sql.append(" and DFJE >= '"+DFJE1+"'");
			}
			if(Validate.noNull(DFJE2)){
				sql.append(" and DFJE <= '"+DFJE2+"'");
			}
			parInList.add(sql.toString());
		try {
			bool = db.batchUpdateByProcedure(procName, parInList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		String sql = "delete from cwpt_mxz where login='" + LUser.getGuid()+ "'";
		db.execute(sql);
	}
	public String kmmc(String kmbh) {
		String sql = "select distinct(d.kmmc) from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		String kmmc = db.queryForSingleValue(sql);
		String bb="("+kmbh+")"+kmmc;
		return bb;
	}
	public List kmbhList(String kmbh) {
		String sql = "select to_char(t.pzrq,'yyyy-mm-dd') as pzrq,t.pzbh,b.zy,b.jfje,b.dfje,decode(b.jdfx,'1','贷方','0','借方') as jdfx,d.kmmc,d.Qmye from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		List list = db.queryForList(sql);
		return list;
	}

	/**
	 * 权限model   000
	 * @param rybh 人员编号
	 * @return
	 */
	public List PowerModels(String rybh) {
		String sql = "SELECT DWB.DWBH AS DWBH,DWB.BMH AS BMH, DWB.MC AS MC,"
					+ "(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT AS DWZT " 
					+ "FROM "+TnameU.GLQXB+" T " 
					+ "INNER JOIN %SDWB DWB ON DWB.DWBH = T.DWBH AND T.RYBH = ? AND EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') " 
					+ "UNION " 
					+ "SELECT DWB.DWBH,BMH,MC,(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT " 
					+ "FROM %SDWB DWB " 
					+ "INNER JOIN %SRYB RYB ON DWB.DWBH = RYB.DWBH AND RYB.RYBH = ? "
					+ "AND NOT EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') ORDER BY BMH ";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh});
	}
	
	/**
	 * 获取某个单位下的单位信息   000
	 * @param sjdw 上级单位
	 * @return
	 */
	public List GetDwModels(String sjdw) {
		String sql = "Select dwbh,bmh,mc,(select count(1) from %Sdwb a where a.sjdw=dwb.dwbh) as xjcount from %Sdwb dwb where sjdw=? order by pxxh,bmh";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
	}

	public List GetModels(String dwbh) {
		String sql = "select t.*,(select dw.mc from gx_sys_dwb dw where dw.dwbh=t.bmbh) from cw_xmjbxxb t where t.bmbh='"+dwbh+"'";
		return db.queryForList(sql);
	}
}
