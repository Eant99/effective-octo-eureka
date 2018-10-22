package com.googosoft.dao.kjhs.bbzx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.bbzx.Zjrbb;
import com.googosoft.util.Validate;

@Repository("zjrbbDao")
public class ZjrbbDao extends BaseDao {
	/**
	 * 查询月份
	 * 
	 * @return
	 */
	public List<Map<String, Object>> getMonth() {
		String sql = "SELECT SUBSTR('0'||TO_CHAR(ROWNUM),-2,2) MONTH FROM DUAL CONNECT BY ROWNUM<=12";
		return db.queryForList(sql);
	}
	
	public String getSql(String kmbh){
		
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select distinct * from");
		sqltext.append(" (select GUID,KMBH,KMMC,");
		sqltext.append(" case when k.fx = '0' then '借方' when k.fx = '1' then '贷方' end as FX,");
		sqltext.append(" decode(nvl(k.qcye,0.00),0.00,'',to_char(round(k.qcye,2),'fm999,999,999,990.00'))qcye,");
		sqltext.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,");
		sqltext.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,");
		sqltext.append(" case when k.fx = '0' then '借方' when k.fx = '1' then '贷方' end as fxx,");
		sqltext.append(" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00'))ye,");
		sqltext.append(" k.jfsl,k.dfsl ");	
		sqltext.append(" from cwpt_zjrbb k ");
		if(kmbh!=""){
			sqltext.append(" where login='"+LUser.getGuid()+"' ");
			sqltext.append(" union all");
			sqltext.append(" (");
			sqltext.append(" select 'B673A80E6702408F8CA3BFBFFBEEEECB',null,'合计',null,decode(nvl((a.qcye - b.qcye), 0.00), 0.00, '',to_char(round((a.qcye - b.qcye), 2), 'fm999,999,999,990.00')) qcye,");
			sqltext.append(" decode(nvl((a.jfje - b.jfje), 0.00), 0.00, '',to_char(round((a.jfje - b.jfje), 2), 'fm999,999,999,990.00')) jfje,decode(nvl((a.dfje - b.dfje), 0.00), 0.00, '',to_char(round((a.dfje - b.dfje), 2), 'fm999,999,999,990.00')) dfje,null,");
			sqltext.append(" decode(nvl((a.ye - b.ye), 0.00), 0.00, '',to_char(round((a.ye - b.ye), 2), 'fm999,999,999,990.00')) ye,null,null from "); 
			sqltext.append("(select sum (t.qcye) qcye,sum (t.jfje) jfje,sum (t.dfje) dfje,sum (t.ye) ye from temp_cwpt_zjrbb t where t.fx='0' ) a, ");
			sqltext.append(" (select sum (t.qcye) qcye,sum (t.jfje) jfje,sum (t.dfje) dfje,sum (t.ye) ye ");
			sqltext.append(" from cwpt_zjrbb t where t.fx='1' )b) ");
			sqltext.append(" ) order by KMBH ");
		}else{
			sqltext.append(" where login='"+LUser.getGuid()+"' and k.kmbh='"+kmbh+"') ");			
		}
		return sqltext.toString();
	}
	public List<Map<String, Object>> getPageList(String kmbh) {
		String sql =getSql(kmbh);
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
	public  boolean runPro(String proName,List list) throws SQLException{
		try {
			super.db.batchUpdateByProcedure("pro_cwyth_zjrbb",list);
			return true;
		} catch (Exception e) {
			return false;
		}	
	}
	/**
	 * 判断存储过程时候执行完毕
	 * 
	 * @param params
	 * @return
	 */
	public boolean getResult(Zjrbb zjrbb,String kmbhh) {
		
		boolean bool = false;
		String procName = "PRO_CWYTH_ZJRBB";
		List parInList = new ArrayList<String>();
		parInList.add(zjrbb.getLxsj());
		String kmbh = "";
		if(zjrbb.getZj_kjkm().indexOf('(')>-1){
			kmbh = zjrbb.getZj_kjkm().substring(zjrbb.getZj_kjkm().indexOf('(')+1,zjrbb.getZj_kjkm().indexOf(')'));
		}else{
			kmbh=zjrbb.getZj_kjkm();
		}
		parInList.add(kmbh);
		parInList.add(zjrbb.getZj_cnr());
		parInList.add(zjrbb.getJsfs());
		parInList.add(zjrbb.getBhqbjzpz());
		parInList.add(zjrbb.getXsszmx());
		parInList.add(zjrbb.getBhyfhwjzpz());
		parInList.add(zjrbb.getZxscnwfhpz());
		parInList.add(zjrbb.getHzxssbhsykm());
		parInList.add(zjrbb.getSszt());
		parInList.add(zjrbb.getLogin());
		parInList.add(zjrbb.getKjzd());
		StringBuffer sql = new StringBuffer();
		//综合查询拼接条件
		if(Validate.noNull(kmbhh.trim())){
			sql.append(" and kmbh like '%"+kmbhh+"%'");
		}
		parInList.add(sql.toString());
		
			try {
				bool = db.batchUpdateByProcedure(procName, parInList);
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		return bool;
	}

	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteKmyeb() {
		String sql = "delete from cwpt_xjrjz where login='" + LUser.getGuid()+ "'";
		db.execute(sql);
	}
	/**
	 * 页面初始的时候，删除原始数据
	 */
	public void deleteZjrbb() {
		String sql = "delete from CWPT_ZJRBB where login='" + LUser.getGuid()+ "'";
		db.execute(sql);
	}
	
	
	public List daochu() {
		String sql = " select * from (select distinct * from (select GUID,KMBH,KMMC,case when k.fx = '0' then '借方' when k.fx = '1' then '贷方' end as FX,decode(nvl(k.qcye,0.00),0.00,'',to_char(round(k.qcye,2),'fm999,999,999,990.00'))qcye,  " +
				" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm999,999,999,990.00'))jfje,decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm999,999,999,990.00'))dfje,case when k.fx = '0' then '借方' when k.fx = '1' then '贷方' end as fxx, " +
				" decode(nvl(k.ye,0.00),0.00,'',to_char(round(k.ye,2),'fm999,999,999,990.00')) ye,k.jfsl,k.dfsl from cwpt_zjrbb k where login='"+LUser.getGuid()+"' union all ( select 'B673A80E6702408F8CA3BFBFFBEEEECB',null,'合计',null,decode(nvl((a.qcye - b.qcye), 0.00), 0.00, '',to_char(round((a.qcye - b.qcye), 2), 'fm999,999,999,990.00')) qcye," +
				" decode(nvl((a.jfje - b.jfje), 0.00), 0.00, '',to_char(round((a.jfje - b.jfje), 2), 'fm999,999,999,990.00')) jfje,decode(nvl((a.dfje - b.dfje), 0.00), 0.00, '',to_char(round((a.dfje - b.dfje), 2), 'fm999,999,999,990.00')) dfje,null,decode(nvl((a.ye - b.ye), 0.00), 0.00, '',to_char(round((a.ye - b.ye), 2), 'fm999,999,999,990.00')) ye,null,null from " +
				" (select sum (t.qcye) qcye,sum (t.jfje) jfje,sum (t.dfje) dfje,sum (t.ye) ye from temp_cwpt_zjrbb t where t.fx='0' ) a,(select sum (t.qcye) qcye,sum (t.jfje) jfje,sum (t.dfje) dfje,sum (t.ye) ye" +
				" from cwpt_zjrbb t where t.fx='1' )b)  )) ";
		List list = db.queryForList(sql);
		return list;
	}
	
	public String kmmc(String kmbh) {
		String sql = "select distinct(d.kmmc) from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join cwpt_zjrbb d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		String kmmc = db.queryForSingleValue(sql);
		String bb="("+kmbh+")"+kmmc;
		return bb;
	}
	
	public List kmbhList(String kmbh) {
		String sql = "select to_char(t.pzrq,'yyyy-mm-dd') as pzrq,t.guid,t.pzbh,b.zy,b.jfje,b.dfje,decode(b.jdfx,'1','贷方','0','借方') as jdfx,d.kmmc,d.zrje,d.jrye from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join cwpt_zjrbb d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		List list = db.queryForList(sql);
		return list;
	}
	/**
	 * 得到cw_kmyeb中的当前所求日期的会计制度
	 */
	public String getkjzd(String lxsj) {
		String sql = "select distinct kjzd from cw_kmyeb where nd=substr('"+lxsj+"',0,4) and rownum='1'";//rownum=1
		return db.queryForSingleValue(sql);
	}
	
	
	
}
