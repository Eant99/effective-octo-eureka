package com.googosoft.dao.kjhs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("ZzDao")
public class ZzDao extends BaseDao{
	
	/**
	 * 执行存储过程插入月总账信息
	 * @param sszt
	 * @param kjzd
	 * @param pznd
	 * @param kmbh
	 * @param bmbh
	 * @param xmbh
	 * @param bhwjzpz
	 * @param qskjqj
	 * @param jskjqj
	 * @return
	 */
	public  boolean executeYzz(String sszt,String kjzd,String pznd,String kmbh,String bmbh,String xmbh,String bhwjzpz,String qskjqj,String jskjqj){
		String sql_ncye = "select nvl(s.kmzye,0.00),t.yefx from cw_kjkmszb t join cw_kmyeb s on t.guid = s.kmid where t.kjzd = '"+kjzd+"' and t.sszt = '"+sszt+"' and t.kmbh = '"+kmbh+"'";
		String sql_qsljfs = "select nvl(sum(jfje),0.00),nvl(sum(dfje),0.00) from cw_pzlrzb t join cw_pzlrmxb s on t.guid = s.pzbh and s.kmbh like '"+kmbh+"%' " + 
				"where t.kjzd = '"+kjzd+"' and t.sszt = '"+sszt+"' and t.pznd = '"+pznd+"' and t.kjqj < "+qskjqj+" ";
		String sql_myfs = "select nvl(sum(jfje),'0.00') as jfje, nvl(sum(dfje),'0.00') as dfje,t.kjqj "
				+ " from cw_pzlrzb t "
				 +" join cw_pzlrmxb s on t.guid = s.pzbh and s.kmbh like '"+kmbh+"%' "
				 + "where t.sszt = '"+sszt+"' and t.kjzd = '"+kjzd+"' and t.pznd = '"+pznd+"' "
		 		+ "and t.kjqj >= "+qskjqj+" and t.kjqj <= "+jskjqj+" ";
		if(Validate.noNull(bmbh)) {
			sql_myfs += " and s.bmbh = '"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)) {
			sql_myfs += " and s.xmbh = '"+xmbh+"' ";
		}
		if("0".equals(bhwjzpz)) {
			sql_myfs += " and t.pzzt in ('02','03') ";
		}
		sql_myfs += " group by t.kjqj order by t.kjqj";
		List<Object> parList = new ArrayList<>();
		parList.add(sql_ncye);
		parList.add(sql_qsljfs);
		parList.add(sql_myfs);
		boolean result = false;
		try {
			result = db.batchUpdateByProcedure("pro_yzz", parList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("存储过程执行错误！");
		}
		return result;
	}
	public List<Map<String,Object>> getYzzList(String pznd){
		pznd += "-";
		String sql = "select guid, to_char(to_date('"+pznd+"'||kjqj,'yyyy-mm'),'yyyy-mm')kjqj,"
				+ " case when to_char(qcye,'fm999,999,999,990.00') = '0.00' then '' else to_char(qcye,'fm999,999,999,990.00') end as qcye,"
				+ " case when to_char(jffs,'fm999,999,999,990.00') = '0.00' then '' else to_char(jffs,'fm999,999,999,990.00') end as jffs,"
				+ " case when to_char(dffs,'fm999,999,999,990.00') = '0.00' then '' else to_char(dffs,'fm999,999,999,990.00') end as dffs,"
				+ " case when to_char(jflj,'fm999,999,999,990.00') = '0.00' then '' else to_char(jflj,'fm999,999,999,990.00') end as jflj,"
				+ " case when to_char(dflj,'fm999,999,999,990.00') = '0.00' then '' else to_char(dflj,'fm999,999,999,990.00') end as dflj,"
				+ " case when to_char(qmye,'fm999,999,999,990.00') = '0.00' then '' else to_char(qmye,'fm999,999,999,990.00') end as qmye"
				+ " from cw_temp_yzzb order by kjqj";
		return db.queryForList(sql);
	}
	/**
	 * 执行存储过程插入日总账信息
	 * @param sszt
	 * @param kjzd
	 * @param pznd
	 * @param kmbh
	 * @param bmbh
	 * @param xmbh
	 * @param bhwjzpz
	 * @param qskjqj
	 * @param jskjqj
	 * @param kjnd
	 * @return
	 */
	public  boolean executeRzz(String sszt,String kjzd,String pznd,String kmbh,String bmbh,String xmbh,String bhwjzpz,String qskjqj,String jskjqj,String kjnd){
		String sql_ncye = "select nvl(s.kmzye,0.00),t.yefx from cw_kjkmszb t join cw_kmyeb s on t.guid = s.kmid where t.kjzd = '"+kjzd+"' and t.sszt = '"+sszt+"' and t.kmbh = '"+kmbh+"'";
		String sql_qsljfs = "select nvl(sum(jfje),0.00),nvl(sum(dfje),0.00) from cw_pzlrzb t join cw_pzlrmxb s on t.guid = s.pzbh and s.kmbh like '"+kmbh+"%' " + 
				"where t.kjzd = '"+kjzd+"' and t.sszt = '"+sszt+"' and t.pznd = '"+pznd+"' and t.kjqj < "+qskjqj+" ";
		String sql_myfs = "select nvl(sum(jfje),0.00) as jfje, nvl(sum(dfje),0.00) as dfje,t.pzrq "
				+ " from cw_pzlrzb t "
				+" join cw_pzlrmxb s on t.guid = s.pzbh and s.kmbh like '"+kmbh+"%' "
				+ "where t.sszt = '"+sszt+"' and t.kjzd = '"+kjzd+"' and t.pznd = '"+pznd+"' "
				+ "and t.kjqj >= "+qskjqj+" and t.kjqj <= "+jskjqj+" ";
		if(Validate.noNull(bmbh)) {
			sql_myfs += " and s.bmbh = '"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)) {
			sql_myfs += " and s.xmbh = '"+xmbh+"' ";
		}
		if("0".equals(bhwjzpz)) {
			sql_myfs += " and t.pzzt in ('02','03') ";
		}
		sql_myfs += " group by t.pzrq order by t.pzrq";
		List<Object> parList = new ArrayList<>();
		parList.add(sql_ncye);
		parList.add(sql_qsljfs);
		parList.add(sql_myfs);
		parList.add(kjnd);
		parList.add(qskjqj);
		boolean result = false;
		try {
			result = db.batchUpdateByProcedure("pro_rzz", parList);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("存储过程执行错误！");
		}
		return result;
	}
	public List<Map<String,Object>> getRzzList(String treesearch){
		String sql = "select guid,pzrq,zy,decode(fx,'0','借方','1','贷方','2','平') as fx,"
				+ " case when to_char(jfje,'fm999,999,999,990.00') = '0.00' then '' else to_char(jfje,'fm999,999,999,990.00') end as jfje,"
				+ " case when to_char(dfje,'fm999,999,999,990.00') = '0.00' then '' else to_char(dfje,'fm999,999,999,990.00') end as dfje,"
				+ " case when to_char(ye,'fm999,999,999,990.00') = '0.00' then '' else to_char(ye,'fm999,999,999,990.00') end as ye"
//				+ " from cw_temp_rzzb where kmmc='"+CommonUtil.getEndText(treesearch)+"' order by xh";
				+ " from cw_temp_rzzb where 1=1 order by xh";
		return db.queryForList(sql);
	}
	/**
	 * 
	 * @param sql
	 * @return
	 */
	public List getXklb(String sql) {
		List list = db.queryForList(sql);
		return list;
	}
	
	public String kmmc(String kmbh) {
		String sql = "select distinct(d.kmmc) from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		String kmmc = db.queryForSingleValue(sql);
		String bb="("+kmbh+")"+kmmc;
		System.err.println("kmmc="+kmmc);
		return bb;
	}
	
	public List kmbhList(String kmbh,String pznd,String kjqj,String pzrq) {
		String sql = "select to_char(t.pzrq,'yyyy-mm-dd') as pzrq,t.pzbh,b.zy,b.jfje,b.dfje,decode(b.jdfx,'1','贷方','0','借方') as jdfx,d.kmmc,d.Qmye from cw_pzlrzb t "
				+ "join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' and t.pznd = '"+pznd+"' and t.kjqj = '"+kjqj+"' ";
		if(Validate.noNull(kjqj)) {
			sql += " and t.pzrq = '"+pzrq+"'";
		}
		List list = db.queryForList(sql);
		return list;
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(String qskjqj,String jskjqj,String kjkm) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_ZZ";
		List parInList = new ArrayList<String>();
		boolean bool = false;
			parInList.add(qskjqj);
			parInList.add(jskjqj);
			System.err.println(qskjqj+jskjqj+"sssssssssssssssssss");
			//parInList.add(startKjkm);
			parInList.add("1");
			parInList.add("1");
			parInList.add("1");
			parInList.add("02','99','00','01");
			parInList.add("B9BA12A24DBE4EA89763AFDE76B8C61A");
			parInList.add(LUser.getGuid());
			parInList.add("0001");
			parInList.add(kjkm);
			parInList.add(kjkm);
		try {
			System.err.println("parInList="+parInList);
			bool = db.batchUpdateByProcedure(procName, parInList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return bool;
	}
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch,PageData pd) {
		String sql=getSql(kmbh,kjzd,treesearch,pd);
		System.err.println("sql====="+sql);
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
		sql.append(" T.KMBH,");
		sql.append(" T.KMMC,");
		sql.append(" T.PZH,t.pzlxmc,");
		sql.append(" T.ZY,");
		sql.append(" T.JJFL,");
		sql.append(" T.BMBH,");
		sql.append(" T.XMBH,");
		sql.append(" T.XH,");
		sql.append(" t.PZRQ,");
		sql.append(" DECODE(NVL(T.JFJE,0.00),0.00,'',TO_CHAR(ROUND(T.JFJE,2),'FM999,999,999,990.00'))JFJE,");
		sql.append(" DECODE(NVL(T.DFJE,0.00),0.00,'',TO_CHAR(ROUND(T.DFJE,2),'FM999,999,999,990.00'))DFJE,");
		sql.append(" DECODE(NVL(T.QCYE,0.00),0.00,'',TO_CHAR(ROUND(T.QCYE,2),'FM999,999,999,990.00'))QCYE,");
		sql.append(" DECODE(NVL(T.YE,0.00),0.00,'',TO_CHAR(ROUND(T.YE,2),'FM999,999,999,990.00'))YE,");
		sql.append(" DECODE(T.FX,'0','借','1','贷','平')FX");
		sql.append(" FROM CWPT_MXZ T WHERE T.LOGIN='"+LUser.getGuid()+"' and T.zy in ('上年结转','上期结转','日合计','月累计','月合计')");
		if(Validate.noNull(kmbh)&&!getSfmj(kmbh)){
			sql.append(" AND exists  (select null from ");
			sql.append("(select K.KMBH from CW_KJKMSZB K where k.kjzd='"+kjzd+"'  start with K.jb = '"+kmbh+"' connect by prior jb = sjfl and sjfl != 'root'order by KMBH asc) b ");
			sql.append("where b.KMBH=T.KMBH )");
		}else if(Validate.noNull(kmbh)&&getSfmj(kmbh)){
			sql.append(" and t.kmbh='"+kmbh+"'");
		}
		if(Validate.noNull(treesearch)){
			sql.append(" and T.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		sql.append(")t");
		sql.append(" GROUP BY T.GUID,T.PZBHGUID,T.KMBH,T.KMMC, T.PZH,t.pzlxmc, T.ZY, T.JJFL, T.BMBH,T.XMBH,PZRQ,JFJE,DFJE,QCYE,YE,FX,xh");
		sql.append("  ORDER BY pzrq,xh");
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
	public List<Map<String,Object>> getPageList2(String sszt,String kjzd,String pznd,String kmbh,String bmbh,String xmbh,String bhwjzpz){
		String sql = "select sum(jfje) as jfje, sum(dfje) as dfje,t.kjqj,(select kmzye from cw_kmyeb where kmbh = '"+kmbh+"') as qcye,"
				+ " (select yefx from cw_kjkmszb where kmbh = '"+kmbh+"') as yefx "
				+ " from cw_pzlrzb t "
				 +" join cw_pzlrmxb s on t.guid = s.pzbh and t.sszt = '"+sszt+"' and t.kjzd = '"+kjzd+"' and t.pznd = '"+pznd+"' and s.kmbh like '"+kmbh+"%'";
		if(Validate.noNull(bmbh)) {
			sql += " and s.bmbh = '"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)) {
			sql += " and s.xmbh = '"+xmbh+"' ";
		}
		if("0".equals(bhwjzpz)) {
			sql += " and t.pzzt in ('02','03') ";
		}
		sql += " group by t.kjqj ";
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
	
	public List getXxList(String jb,String dm){
//		Map<String, Object> map = new HashMap(); 
		String sql = " select kmmc,kmbh,to_char(kmnd,'yyyy-mm-dd') as kmnd,bmhs,kmjc from Cw_kjkmszb  where jb='"+jb+"'";
		List list = db.queryForList(sql);
		return list;
	}	
	
	public List daochu( String sszt,String kmbh) {
		String sql = " select * from  (select distinct * from (select K.PZRQ,k.kmbh,decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje, " +
				" decode(nvl((nvl(k.jfje,'0.00')-nvl(k.dfje,'0.00')),0.00),0.00,'',to_char(round(nvl(k.jfje,'0.00')-nvl(k.dfje,'0.00'),2),'fm999,999,999,990.00'))ye, " +
				" '日合计' as ZY,k.month,case when k.fx = '0' then '借方' when k.fx = '1' then '贷方' end as FX from zzb k left join (select pzrq,kmbh,decode(sum(jfje),0,'',to_char(round(sum(jfje),2),'fm999,999,999,990.00'))jffs," +
				" decode(sum(dfje),0,'',to_char(round(sum(dfje),2),'fm999,999,999,990.00'))dffs from zzb group by pzrq,kmbh)b on b.pzrq = k.pzrq and b.kmbh=k.kmbh where login='"+LUser.getGuid()+"')a " +
				" union all select  substr(pzrq,0,7),kmbh,decode(sum(jfje),0,'',to_char(round(sum(jfje),2),'fm999,999,999,990.00'))jffs,decode(sum(dfje),0,'',to_char(round(sum(dfje),2),'fm999,999,999,990.00'))dffs," +
				" decode(sum(ye),0,'',to_char(round(sum(ye),2),'fm999,999,999,990.00'))ye,'本月合计' as zy,null,case when fx = '0' then '借方' when fx = '1' then '贷方' end as FX from zzb where login='"+LUser.getGuid()+"' where 1=1  and kmbh ='"+kmbh+"'    order by PZRQ desc " +
				" group by substr(pzrq,0,7),fx,kmbh ) q ";
		List list = db.queryForList(sql);
		return list;
	}
	
	public Map getKjqj(HttpSession session){
		String sql = "select * from CW_QMJZB where sfjz=? and sszt=? and rownum=1";
		Map map = db.queryForMap(sql,new Object[]{"0",Constant.getztid(session)});
		return map;
	}

}
