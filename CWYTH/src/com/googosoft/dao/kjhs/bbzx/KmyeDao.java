package com.googosoft.dao.kjhs.bbzx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpSession;

import oracle.jdbc.OracleTypes;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.bbzx.Params;
import com.googosoft.util.Validate;

@Repository("kmyeDao")
public class KmyeDao extends BaseDao {
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
	
	public List<Map<String, Object>> getPageList(String sql) {
		return db.queryForList(sql);
	}
	//查询模版名称
	public String getMbmc() {
		String sql = "select mbmc from cw_mbb where gid='001'";
		return db.queryForSingleValue(sql);
	}

	/**
	 * 会计科目树
	 * 
	 * @param kmbh
	 * @return
	 */
	public List getKjkmList(String kmbh) {
		String sql = "SELECT KMBH,KMMC,KMJC JB FROM CW_KJKMSZB WHERE 1=1 ";
		if (Validate.noNull(kmbh) && "root".equals(kmbh)) {
			sql += " AND KMJC='root' OR KMJC IS NULL";
		}
		if (Validate.noNull(kmbh) && !"root".equals(kmbh)) {
			sql += " AND KMJC='1' AND KMBH LIKE '" + kmbh + "%'";
		}
		sql += " ORDER BY TO_NUMBER(KMBH)";
		List list = db.queryForList(sql);
		return list;
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
	
	
	public List kmbhList(String kmbh,String sszt,String starttime,String endtime) {
		String sql = "select distinct(to_char(t.pzrq, 'yyyy-mm-dd')) as pzrq, t.pzbh, b.zy,t.guid, DECODE(NVL(b.jfje, '0'),  '0','',TO_CHAR(ROUND(ABS(b.jfje), 2), 'FM999,999,999,990.00')) as jfje," +
				" DECODE(NVL(b.dfje, '0'), '0','',  TO_CHAR(ROUND(ABS( b.dfje), 2), 'FM999,999,999,990.00')) as dfje," +
				" decode(b.jdfx, '1', '贷方', '0', '借方') as jdfx," +
				" d.kmmc, DECODE(NVL(d.Qmye, '0'), '0', '',TO_CHAR(ROUND(ABS(d.Qmye), 2), 'FM999,999,999,990.00')) as Qmye from cw_pzlrzb t left join cw_pzlrmxb b " +
				" on t.guid = b.pzbh left join DUAL_KMYEB d  on b.kmbh = d.kmbh where t.sszt='"+sszt+"' and b.kmbh like '"+kmbh+"%'  " +
						" and to_char(t.pzrq, 'yyyy-mm') >='"+starttime+"'  and to_char(t.pzrq, 'yyyy-mm') <='"+endtime+"' " +
						"order by pzrq,pzbh";
		List list = db.queryForList(sql);
		
		return list;
	}
	public List bmbhList(String bh) {
		String sql = "select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,zb.guid pzbhguid,zb.pzbh pzz,zb.pzlxmc,t.zy,kj.kmmc,kj.kmbh,(select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc," +
				" (select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,to_char(nvl(t.jfje,0),'99999999999990.99')jfje," +
				" to_char(nvl(t.dfje,0),'99999999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye " +
				" from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.kmbh=kj.kmbh " +
				" where t.bmbh like '"+bh+"%' order by pzrq,pzbh";
		List list = db.queryForList(sql);
		
		return list;
	}
	public List xmbhList(String bh) {
		String sql = "select distinct(to_char(t.pzrq, 'yyyy-mm-dd')) as pzrq, t.pzbh, b.zy,t.guid, DECODE(NVL(b.jfje, '0'),  '0','',TO_CHAR(ROUND(ABS(b.jfje), 2), 'FM999,999,999,990.00')) as jfje," +
				" DECODE(NVL(b.dfje, '0'), '0','',  TO_CHAR(ROUND(ABS( b.dfje), 2), 'FM999,999,999,990.00')) as dfje," +
				" decode(b.jdfx, '1', '贷方', '0', '借方') as jdfx," +
				" d.kmmc, DECODE(NVL(d.Qmye, '0'), '0', '',TO_CHAR(ROUND(ABS(d.Qmye), 2), 'FM999,999,999,990.00')) as Qmye from cw_pzlrzb t left join cw_pzlrmxb b " +
				" on t.guid = b.pzbh left join DUAL_KMYEB d  on b.kmbh = d.kmbh where b.kmbh like '"+bh+"%' order by pzrq,pzbh";
		List list = db.queryForList(sql);
		
		return list;
	}
	
	
//	public String kmmc(String kmbh) {
//		String sql = "select distinct(d.kmmc) from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
//		String kmmc = db.queryForSingleValue(sql);
//		String bb="("+kmbh+")"+kmmc;
//		System.err.println("kmmc="+kmmc);
//		return bb;
//	}
	
	public String kmmc(String kmbh) {
		return db.queryForSingleValue("select '('||kmbh||')'||kmmc from cw_kjkmszb where kmbh=?", new Object[]{kmbh})+"";
	}
	
	
	public List daochu( String sszt,String kmbh) {
		String sql = " select * from  (select KMBH,KMJC,KMMC,KMYEFX,SFMJ,DECODE(BMMC,'','','('||BMBH||')'||BMMC) AS BMMC,DECODE(XMMC,'','','('||XMBH||')'||XMMC) AS XMMC,DECODE(NVL(QCFX,'B'),'0',TO_CHAR(ROUND(ABS(QCYE),2),'FM999,999,999,990.00'))QCFX,DECODE(NVL(QCFX,'B'),'1',TO_CHAR(ROUND(ABS(QCYE),2),'FM999,999,999,990.00'))QCYE," +
				"  DECODE(NVL(BQJF,'0'),'0','',TO_CHAR(ROUND(ABS(BQJF),2),'FM999,999,999,990.00'))BQJF,DECODE(NVL(BQDF,'0'),'0','',TO_CHAR(ROUND(ABS(BQDF),2),'FM999,999,999,990.00'))BQDF,DECODE(NVL(QMFX,'B'),'0',TO_CHAR(ROUND(ABS(QMYE),2),'FM999,999,999,990.00'))QMFX,DECODE(NVL(QMFX,'0'),'1',TO_CHAR(ROUND(ABS(QMYE),2),'FM999,999,999,990.00'))QMYE" +
				" FROM DUAL_KMYEB K WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"' AND K.SSZT='"+sszt+"' AND K.KMBH='"+kmbh+"' ORDER BY SUBSTR(K.KMBH,0,4),KMJC)K ";
		List list = db.queryForList(sql);
		return list;
	}
	
	/**
	 * 判断存储过程时候执行完毕
	 * @param params
	 * @return
	 */
	public Map<String, List<Map<String,Object>>> getResult(Params params){
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_KMYE_"+params.getGs();
		Map<String,String> parInMap = new HashMap<String,String>();
		parInMap.put("v_year", params.getYear());
		parInMap.put("v_startmonth", params.getStartMonth());
		parInMap.put("v_endmonth", params.getEndMonth());
		parInMap.put("v_treebh", params.getTreebh());
		System.err.println(params.getGs()+"v_treebh================================================="+params.getTreebh());
		if("xm".equals(params.getGs())){
			parInMap.put("v_bmh", params.getBmh());
		}
		parInMap.put("v_jzpz", params.getJzpz());
		parInMap.put("v_login", login);
		parInMap.put("v_sszt", params.getSszt());
		parInMap.put("v_kjzd", params.getKjzd());
		parInMap.put("v_StartJc", params.getStartJc());
		parInMap.put("v_EndJc", params.getEndJc());
		System.err.println("v_StartJc=========================="+params.getStartJc()+"v_EndJc============================================================"+params.getEndJc());
		parInMap.put("kmbh1", params.getKmbh1().replace(",", "','"));//多科目查找时的科目编号
		parInMap.put("kmbh2", params.getKmbh2());//科目区间--开始科目编号
		parInMap.put("kmbh3", params.getKmbh3());//科目区间--结束科目编号
		Set<Entry<String,String>> parInSet = parInMap.entrySet();
		parInMap = null;
		
		Map<String,Integer> parOutMap = new HashMap<String,Integer>();
		parOutMap.put("my_cursor",OracleTypes.CURSOR);
		parOutMap.put("my_cursor2", OracleTypes.CURSOR);
		Set<Entry<String,Integer>> parOutSet = parOutMap.entrySet();
		parOutMap = null;
		Map map = null;
		try {
			map = db.queryForMapByProcedure(procName, parInSet, parOutSet);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(map.isEmpty()){
			map.put("mxList", new ArrayList());
			map.put("zjList", new ArrayList());
		}
		else{
			map.put("mxList", (List)map.get("my_cursor"));
			map.put("zjList", (List)map.get("my_cursor2"));
		}
		return map; 
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		String sql = "delete from dual_kmyeb where login='" + LUser.getGuid()+ "'";
		db.execute(sql);
	}
	
	
	/**
	 * 计算合计金额
	 * @return
	 */
	public Map<String,String> computer(){
		String login = LUser.getGuid();
		String qcjSql = "select to_char(sum(abs(qcye)),'fm999,999,999,990.00') from dual_kmyeb where  length(kmbh)=4 and login='"+login+"' and qcfx='0'";
		String qcdSql = "select to_char(sum(abs(qcye)),'fm999,999,999,990.00') from dual_kmyeb where length(kmbh)=4 and login='"+login+"' and qcfx='1'";
		String bqjSql = "select to_char(sum(abs(bqjf)),'fm999,999,999,990.00') from dual_kmyeb where length(kmbh)=4 and login='"+login+"'";
		String bqdSql = "select to_char(sum(abs(bqdf)),'fm999,999,999,990.00') from dual_kmyeb where length(kmbh)=4 and login='"+login+"'";
		String qmjSql = "select to_char(sum(abs(qmye)),'fm999,999,999,990.00') from dual_kmyeb where length(kmbh)=4 and login='"+login+"' and qmfx='0'";
		String qmdSql = "select to_char(sum(abs(qmye)),'fm999,999,999,990.00') from dual_kmyeb where length(kmbh)=4 and login='"+login+"' and qmfx='1'";
		String qcj = Validate.isNullToDefaultString(db.queryForSingleValue(qcjSql), "");
		String qcd = Validate.isNullToDefaultString(db.queryForSingleValue(qcdSql), "");
		String bqj = Validate.isNullToDefaultString(db.queryForSingleValue(bqjSql), "");
		String bqd = Validate.isNullToDefaultString(db.queryForSingleValue(bqdSql), "");
		String qmj = Validate.isNullToDefaultString(db.queryForSingleValue(qmjSql), "");
		String qmd = Validate.isNullToDefaultString(db.queryForSingleValue(qmdSql), "");
		Map map = new HashMap<String,String>();
		map.put("qcj", getZeroToNull(qcj));
		map.put("qcd", getZeroToNull(qcd));
		map.put("bqj", getZeroToNull(bqj));
		map.put("bqd", getZeroToNull(bqd));
		map.put("qmj", getZeroToNull(qmj));
		map.put("qmd", getZeroToNull(qmd));
		return map;
	}
	
	
	public String getZeroToNull(String num){
		if("000.00".equals(num)||"0.00".equals(num)||"0".equals(num)){
			return "";
		}
		return num;
	}
	
	/**
	 * 打印信息
	 * @param sszt
	 * @return
	 */
	public List getPrintInfo(String sszt){
		StringBuffer sql = new StringBuffer();
		sql.append(" select KMBH,KMJC,KMMC,KMYEFX,SFMJ,");
		sql.append(" '('||BMBH||')'||BMMC AS BMMC,");
		sql.append(" '('||XMBH||')'||XMMC AS XMMC,");
		sql.append(" DECODE(NVL(QCFX,'B'),'1','贷','0','借','A','平','')QCFX,");
		sql.append(" DECODE(NVL(QMFX,'B'),'1','贷','0','借','A','平','')QMFX,");
		sql.append(" DECODE(NVL(QCYE,'0'),'0','',TO_CHAR(ROUND(ABS(QCYE),2),'FM999,999,999,990.00'))QCYE,");
		sql.append(" DECODE(NVL(BQJF,'0'),'0','',TO_CHAR(ROUND(ABS(BQJF),2),'FM999,999,999,990.00'))BQJF,");
		sql.append(" DECODE(NVL(BQDF,'0'),'0','',TO_CHAR(ROUND(ABS(BQDF),2),'FM999,999,999,990.00'))BQDF,");
		sql.append(" DECODE(NVL(QMYE,'0'),'0','',TO_CHAR(ROUND(ABS(QMYE),2),'FM999,999,999,990.00'))QMYE");
		sql.append(" FROM DUAL_KMYEB K");
		sql.append(" WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"'");
		sql.append(" AND K.SSZT='"+sszt+"'");
		sql.append(" ORDER BY SUBSTR(K.KMBH,0,4),KMJC");
		List list = db.queryForList(sql.toString());
		return list;
	}
	
	public List getXxList(HttpSession session){
//		
		String kjzd = CommonUtil.getKjzd(session);
		String sszt = Constant.getztid(session);
		StringBuffer sql = new StringBuffer();
//		sql.append(" select  min(k.kmbh) as kmbh1,max(k.kmbh)as kmbh2 from CW_KJKMSZB k where 1=1 AND K.KMJC = '1' AND TO_CHAR(K.KMND, 'YYYY') = '2017' ");
		sql.append(" select '(' || s.KMBH || ')' || s.KMMC AS KMXX from ");
		sql.append(" (select K.KMBH, K.KMMC, K.JB, K.KMJC, '(' || K.KMBH || ')' || K.KMMC AS KMXX ");
		sql.append(" from CW_KJKMSZB K ");
		sql.append(" where 1 = 1 ");
		sql.append(" AND K.KMJC = '1' and k.sszt='"+sszt+"' and k.kjzd='"+kjzd+"'");
//		sql.append(" AND TO_CHAR(K.KMND, 'YYYY') = '2017' ");
		sql.append(" order by KMBH asc) s ");
		sql.append(" where rownum='1' ");
		sql.append(" union ");
		sql.append(" select '(' || d.KMBH || ')' || d.KMMC AS KMXX from ");
		sql.append(" (select K.KMBH, K.KMMC, K.JB, K.KMJC, '(' || K.KMBH || ')' || K.KMMC AS KMXX ");
		sql.append(" from CW_KJKMSZB K ");
		sql.append(" where 1 = 1 ");
		sql.append(" AND K.KMJC = '1' and k.sszt='"+sszt+"' and k.kjzd='"+kjzd+"'");
//		sql.append(" AND TO_CHAR(K.KMND, 'YYYY') = '2017' ");
		sql.append(" order by KMBH desc ) d ");
		sql.append(" where rownum='1'  ");
		List list = db.queryForList(sql.toString());
		System.err.println("list+++++++++++++++++++++++++++="+list);
		return list;
	}
	/**
	 * 判断是否是末级经济科目
	 * @param jjbh
	 * @return
	 */
	public boolean checkEndJjkm(String jjbh) {
		String sql="select count(*) from cw_jjkmb where kmbh like '"+jjbh+"%'";
		String count=db.queryForSingleValue(sql);
		if(count.equals("1")) {
			return true;
		}
		return false;
	}
	
	
	
}
