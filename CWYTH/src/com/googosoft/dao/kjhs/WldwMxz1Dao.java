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

@Repository("wldwmxz1dao")
public class WldwMxz1Dao extends BaseDao {
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}

	public String getWldwmc(String wlbh) {
		String sql = "select dwmc from CW_WLDWB b where b.wlbh='"+wlbh+"' ";
		String dwmc = db.queryForSingleValue(sql);
		String bb="("+wlbh+")"+dwmc;
		
		return bb;
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
		sql.append(" T.KMBH,");
		sql.append(" '('||T.KMBH||')'||T.KMMC as KMMC,");
		sql.append(" T.PZH,t.pzlxmc,t.wldwmc,");
		sql.append(" T.ZY,");
		sql.append(" T.JJFL,");
		sql.append(" T.BMBH,");
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
		sql.append("  )t");
		sql.append(" GROUP BY T.GUID,t.wldwmc,T.PZBHGUID,T.KMBH,T.KMMC, T.PZH,t.pzlxmc, T.ZY, T.JJFL, T.BMBH,T.XMBH,PZRQ,JFJE,DFJE,QCYE,YE,FX,xh");
		sql.append("  ORDER BY pzrq,pzh,xh ");
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
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd) {
		String sql=getSql(kmbh,kjzd,treesearch,pd);
		return db.queryForList(sql);
	}
	public List<Map<String, Object>> getJjPageList(String kmbh,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		String sql="select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,zb.guid pzbhguid,zb.pzbh pzz,zb.pzlxmc,t.zy,kj.kmmc,kj.kmbh,(select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc,"+
	               "(select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,to_char(nvl(t.jfje,0),'99999999999990.99')jfje,to_char(nvl(t.dfje,0),'99999999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye "+
	               "from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.kmbh=kj.kmbh where 1=1 and jjfl is not null";
		if(Validate.noNull(kmbh)){
			sql +=" and t.jjfl like '"+kmbh+"%'";
		}
		if(Validate.noNull(pzzt)){
			sql +=" and zb.pzzt in ("+pzzt+")";
		}
		if(Validate.noNull(startMonth)&&Validate.noNull(endMonth)){
			sql +=" and zb.pznd='"+pznd+"' and zb.kjqj >= "+startMonth+" and zb.kjqj <="+endMonth;
		}
		if(Validate.noNull(kjkm)){
			sql +=" and t.kmbh='"+kjkm+"'";
		}
		if(Validate.noNull(bmbh)){
			sql +=" and t.bmbh='"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)){
			sql +=" and t.xmbh='"+xmbh+"'";
		}
		if(Validate.noNull(jfjel)){
			sql +=" and t.jfje>="+jfjel;
		}
		if(Validate.noNull(jfjeh)){
			sql +=" and t.jfje<="+jfjeh;
		}
		if(Validate.noNull(zy)){
			sql +=" and t.zy like '%"+zy+"%'";
		}
		sql+=" order by zb.pzrq desc";
		return db.queryForList(sql);
	}
	public List<Map<String, Object>> getGrPageList(String ryid,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		String sql = "select to_char(zb.pzrq, 'yyyy-mm-dd') pzrq, zb.pzlxmc pzlxm, zb.pzbh pzz, t.zy,fz.zrr,(select xm from gx_sys_ryb where rybh=fz.zrr) as xm," + 
				 " (select kmmc from CW_KJKMSZB where kmbh=t.kmbh)kjkm,(select kmmc from CW_JJKMB where kmbh=t.jjfl)jjkm,(select mc from gx_sys_dwb where dwbh=t.bmbh )bmmc ,(select xmmc from cw_xmjbxxb where xmbh=t.xmbh and bmbh=t.bmbh) xmmc," + 
				 " to_char(nvl(t.jfje,0),'999999990.99')jfje, to_char(nvl(t.dfje,0),'999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'999999990.99')ye" + 
				 " from cw_pzlrmxb t" + 
				 " left join cw_pzlrzb zb on t.pzbh = zb.guid" + 
				 " left join cw_fzlrb fz on t.guid=fz.kmbh" +
				 " where 1=1 and fz.zrr is not null";
		if(Validate.noNull(ryid)){
			sql +=" and fz.zrr like '"+ryid+"%'";
		}
		if(Validate.noNull(pzzt)){
			sql +=" and zb.pzzt in ("+pzzt+")";
		}
		if(Validate.noNull(startMonth)&&Validate.noNull(endMonth)){
			sql +=" and zb.pznd='"+pznd+"' and zb.kjqj >= "+startMonth+" and zb.kjqj <="+endMonth;
		}
//		if(Validate.noNull(kjkm)){
//			sql +=" and t.kmbh='"+kjkm+"' and zb.pznd='"+pznd+"' and zb.kjqj between "+startMonth+" and "+endMonth;
//		}
		if(Validate.noNull(kjkm)){
			sql +=" and t.kmbh='"+kjkm+"'";
		}
		if(Validate.noNull(bmbh)){
			sql +=" and t.bmbh='"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)){
			sql +=" and t.xmbh='"+xmbh+"'";
		}
		if(Validate.noNull(jfjel)){
			sql +=" and t.jfje>="+jfjel;
		}
		if(Validate.noNull(jfjeh)){
			sql +=" and t.jfje<="+jfjeh;
		}
		if(Validate.noNull(zy)){
			sql +=" and t.zy like '%"+zy+"%'";
		}
		sql+=" order by zb.pzrq desc";
		return db.queryForList(sql);
	}
	public List<Map<String, Object>> getXmPageList(String treexmbh,String kjkm,String pznd,String startMonth,String endMonth,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		String sql = "select to_char(zb.pzrq, 'yyyy-mm-dd') pzrq, zb.pzlxmc pzlxm, zb.pzbh pzz,zb.pzzt, t.zy,(select kmmc from CW_JJKMB where kmbh=t.jjfl)jjkm,(select mc from gx_sys_dwb where dwbh=t.bmbh )bmmc ,(select xmmc from cw_xmjbxxb where xmbh=t.xmbh and bmbh=t.bmbh) xmmc," +
		" to_char(nvl(t.jfje,0),'999999990.99')jfje, to_char(nvl(t.dfje,0),'999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'999999990.99')ye" +
		" from cw_pzlrmxb t" +
		" left join cw_pzlrzb zb on t.pzbh = zb.guid" +
		" left join cw_fzlrb fz on t.guid=fz.kmbh"+
		" where 1=1 and t.xmbh is not null";
		if(Validate.noNull(treexmbh)){
			sql +=" and t.xmbh like '"+treexmbh+"%'";
		}
		if(Validate.noNull(pzzt)){
			sql +=" and zb.pzzt in ("+pzzt+")";
		}
		if(Validate.noNull(startMonth)&&Validate.noNull(endMonth)){
			sql +=" and zb.pznd='"+pznd+"' and zb.kjqj >= "+startMonth+" and zb.kjqj <="+endMonth;
		}
//		if(Validate.noNull(kjkm)){
//			sql +=" and t.kmbh='"+kjkm+"' and zb.pznd='"+pznd+"' and zb.kjqj between "+startMonth+" and "+endMonth;
//		}
		if(Validate.noNull(kjkm)){
			sql +=" and t.kmbh='"+kjkm+"'";
		}
		if(Validate.noNull(bmbh)){
			sql +=" and t.bmbh='"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)){
			sql +=" and t.xmbh='"+xmbh+"'";
		}
		if(Validate.noNull(jfjel)){
			sql +=" and t.jfje>="+jfjel;
		}
		if(Validate.noNull(jfjeh)){
			sql +=" and t.jfje<="+jfjeh;
		}
		if(Validate.noNull(zy)){
			sql +=" and t.zy like '%"+zy+"%'";
		}
		sql+=" order by zb.pzrq desc";
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
	public boolean getResult(List list,String sszt,String kjzd,String kmbh,PageData pd,String bz) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_MXZ_WLDWNEW";
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
			String type = "";
			if("jjkm".equals(bz)){
				type = "5";
			}else{
				type = "1";
			}
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
			parInList.add(startKjkm);
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
			if(Validate.noNull(zy.trim())){
				sql.append(" and zy like '%"+zy+"%'");
			}
//			if(Validate.noNull(km)&&!"undefined".equals(km)){
//				sql.append(" and to_number(t.kmbh) like '"+km+"%'");
//			}
			if(Validate.noNull(km1)&&!"undefined".equals(km1)){
				sql.append(" and to_number(t.kmbh) >= '"+km1+"'");
			}
			if(Validate.noNull(km2)&&!"undefined".equals(km2)){
				sql.append(" and to_number(t.kmbh) <= '"+km2+"'");
			}
			if(Validate.noNull(PZBH1)){
				sql.append(" and to_number(ZB.PZBH) >= '"+PZBH1+"'");
			}
			if(Validate.noNull(PZBH2)){
				sql.append(" and to_number(ZB.PZBH) <= '"+PZBH2+"'");
			}
			if(Validate.noNull(PZRQ1)){
				sql.append(" and pzrq >= to_date('"+PZRQ1+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(PZRQ2)){
				sql.append(" and pzrq<= to_date('"+PZRQ2+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(JFJE1)){
				sql.append(" and JFJE >= "+JFJE1+"");
			}
			if(Validate.noNull(JFJE2)){
				sql.append(" and JFJE <= "+JFJE2+"");
			}
			if(Validate.noNull(DFJE1)){
				sql.append(" and DFJE >= "+DFJE1+"");
			}
			if(Validate.noNull(DFJE2)){
				sql.append(" and DFJE <= "+DFJE2+"");
			}
			parInList.add(sql.toString());
			
		}
		try {
			bool = db.batchUpdateByProcedure(procName, parInList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	/**
	 * 由余额表到明细账跳转时判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResultnew(String wlbh, String sszt, String kmbh,
			String nian, String startMonth, String endMonth, PageData pd,String bz) {
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
			String type = "";
			if("jjkm".equals(bz)){
				type = "5";
			}else{
				type = "1";
			}
			//往来单位编号
			String wlbh1 = Validate.isNullToDefaultString(wlbh, "");
			String startMonth1 = Validate.isNullToDefaultString(startMonth, "");
			String endMonth1 = Validate.isNullToDefaultString(endMonth, "");
			parInList.add(year+"-"+startMonth1);
			parInList.add(year+"-"+endMonth1);
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
			parInList.add(wlbh1);//往来单位编号
			
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
			if(Validate.noNull(zy.trim())){
				sql.append(" and zy like '%"+zy+"%'");
			}
			if(Validate.noNull(km)&&!"undefined".equals(km)){
				sql.append(" and to_number(t.kmbh) = '"+km+"'");
			}
			if(Validate.noNull(km1)&&!"undefined".equals(km1)){
				sql.append(" and to_number(t.kmbh) >= '"+km1+"'");
			}
			if(Validate.noNull(km2)&&!"undefined".equals(km2)){
				sql.append(" and to_number(t.kmbh) <= '"+km2+"'");
			}
			if(Validate.noNull(PZBH1)){
				sql.append(" and to_number(ZB.PZBH) >= '"+PZBH1+"'");
			}
			if(Validate.noNull(PZBH2)){
				sql.append(" and to_number(ZB.PZBH) <= '"+PZBH2+"'");
			}
			if(Validate.noNull(PZRQ1)){
				sql.append(" and pzrq >= to_date('"+PZRQ1+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(PZRQ2)){
				sql.append(" and pzrq<= to_date('"+PZRQ2+"','yyyy-MM-dd')");
			}
			if(Validate.noNull(JFJE1)){
				sql.append(" and JFJE >= "+JFJE1+"");
			}
			if(Validate.noNull(JFJE2)){
				sql.append(" and JFJE <= "+JFJE2+"");
			}
			if(Validate.noNull(DFJE1)){
				sql.append(" and DFJE >= "+DFJE1+"");
			}
			if(Validate.noNull(DFJE2)){
				sql.append(" and DFJE <= "+DFJE2+"");
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
		String sql = "delete from cwpt_mxz where login='" + LUser.getGuid()
				+ "'";
		db.execute(sql);
	}
	
	public String kmmc(String kmbh) {
		String sql = "select distinct(d.kmmc) from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		String kmmc = db.queryForSingleValue(sql);
		String bb="("+kmbh+")"+kmmc;
		System.err.println("kmmc="+kmmc);
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
