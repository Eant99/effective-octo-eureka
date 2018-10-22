package com.googosoft.dao.kjhs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("wldwmxzDao")
public class WldwMxzDao extends BaseDao {
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
	public String getSql(String kmbh,String kjzd,String treesearch){				
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
		//sql.append(" case when t.zy='期末余额' then TO_CHAR(T.PZRQ,'YYYY-MM') else TO_CHAR(T.PZRQ,'YYYY-MM-dd') end PZRQ,");
		sql.append(" t.PZRQ,");
		sql.append(" DECODE(NVL(T.JFJE,0.00),0.00,'',TO_CHAR(ROUND(T.JFJE,2),'FM999,999,999,990.00'))JFJE,");
		sql.append(" DECODE(NVL(T.DFJE,0.00),0.00,'',TO_CHAR(ROUND(T.DFJE,2),'FM999,999,999,990.00'))DFJE,");
		sql.append(" DECODE(NVL(T.QCYE,0.00),0.00,'',TO_CHAR(ROUND(T.QCYE,2),'FM999,999,999,990.00'))QCYE,");
		sql.append(" DECODE(NVL(T.YE,0.00),0.00,'',TO_CHAR(ROUND(T.YE,2),'FM999,999,999,990.00'))YE,");
		sql.append(" DECODE(T.FX,'0','借','1','贷','平')FX");
		sql.append(" FROM CWPT_MXZ T WHERE T.LOGIN='"+LUser.getGuid()+"' ");
		if(Validate.noNull(kmbh)&&!getSfmj(kmbh)){
			sql.append(" AND exists  (select null from ");
			sql.append("(select K.KMBH from CW_KJKMSZB K where k.kjzd='"+kjzd+"'  start with K.jb = '"+kmbh+"' connect by prior jb = sjfl and sjfl != 'root'order by KMBH asc) b ");
			sql.append("where b.KMBH=T.KMBH)");
		}else{
			sql.append(" and t.kmbh='"+kmbh+"'");
		}
		if(Validate.noNull(treesearch)){
			System.out.println(treesearch);
			sql.append(" and T.kmmc='"+CommonUtil.getEndText(treesearch)+"'");//where条件//treesearch.substring(1, treesearch.indexOf(")"))
		}
		sql.append(")t");
		sql.append(" GROUP BY T.GUID,T.PZBHGUID,T.KMBH,T.KMMC, T.PZH,t.pzlxmc, T.ZY, T.JJFL, T.BMBH,T.XMBH,PZRQ,JFJE,DFJE,QCYE,YE,FX,xh");
		sql.append("  ORDER BY xh");
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
	public List<Map<String, Object>> getPageList(String kmbh,String kjzd,String treesearch) {
		String sql=getSql(kmbh,kjzd,treesearch);
		System.err.println("sql====="+sql);
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
	public boolean getResult(List list,String sszt,String kjzd,String kmbh) {
		String login = LUser.getGuid();
		String procName = "PRO_CWYTH_MXZ";
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
			String startJc = Validate.isNullToDefaultString(map.get("startJc"), "");
			String endJc = Validate.isNullToDefaultString(map.get("endJc"), "");
			//排序方式
			String pxfs = Validate.isNullToDefaultString(map.get("pxfs"), "");
			//会计期间
			String year = Validate.isNullToDefaultString(map.get("year"), "");
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
			parInList.add(LUser.getGuid());
			parInList.add(kjzd);
			parInList.add(kmbh);
			
		}
		try {
			System.err.println("parInList="+parInList);
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
	
	
	public List daochu( String sszt,String kmbh) {
		String sql = " select * from  (select KMBH,KMJC,KMMC,KMYEFX,SFMJ,DECODE(BMMC,'','','('||BMBH||')'||BMMC) AS BMMC,DECODE(XMMC,'','','('||XMBH||')'||XMMC) AS XMMC,DECODE(NVL(QCFX,'B'),'0',TO_CHAR(ROUND(ABS(QCYE),2),'FM999,999,999,990.00'))QCFX,DECODE(NVL(QCFX,'B'),'1',TO_CHAR(ROUND(ABS(QCYE),2),'FM999,999,999,990.00'))QCYE," +
				"  DECODE(NVL(BQJF,'0'),'0','',TO_CHAR(ROUND(ABS(BQJF),2),'FM999,999,999,990.00'))BQJF,DECODE(NVL(BQDF,'0'),'0','',TO_CHAR(ROUND(ABS(BQDF),2),'FM999,999,999,990.00'))BQDF,DECODE(NVL(QMFX,'B'),'0',TO_CHAR(ROUND(ABS(QMYE),2),'FM999,999,999,990.00'))QMFX,DECODE(NVL(QMFX,'0'),'1',TO_CHAR(ROUND(ABS(QMYE),2),'FM999,999,999,990.00'))QMYE" +
				" FROM DUAL_KMYEB K WHERE 1=1 AND K.LOGIN='"+LUser.getGuid()+"' AND K.SSZT='"+sszt+"' AND K.KMBH='"+kmbh+"' ORDER BY SUBSTR(K.KMBH,0,4),KMJC)K ";
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
	
	public List kmbhList(String kmbh) {
		String sql = "select to_char(t.pzrq,'yyyy-mm-dd') as pzrq,t.pzbh,b.zy,b.jfje,b.dfje,decode(b.jdfx,'1','贷方','0','借方') as jdfx,d.kmmc,d.Qmye from cw_pzlrzb t left join cw_pzlrmxb b on t.guid = b.pzbh  left join DUAL_KMYEB d on b.kmbh = d.kmbh where b.kmbh='"+kmbh+"' ";
		List list = db.queryForList(sql);
		return list;
	}
	
	public List<Map<String, Object>> getJjPageList(String kmbh,String kjkm,String pznd,String startMonth,String endMonth,String dm,String pzzt,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {//kj.kmmc
		String sql="select to_char(zb.pzrq,'yyyy-mm-dd') pzrq,zb.guid pzbhguid,zb.pzzt,zb.pzbh pzz,zb.pzlxmc,t.zy,kj.kmbh,bmbh,t.xmbh,(select mc from GX_SYS_DWB where dwbh=t.bmbh) as bmmc,(select km.kmmc from cw_kjkmszb km where km.kmbh=t.kmbh )kjkm,"+
	               "(select kmb.kmmc from cw_jjkmb kmb where kmb.kmbh = t.jjfl)jjkm,(select xmmc from CW_XMJBXXB where xmbh = t.xmbh and bmbh=t.bmbh)xmmc,to_char(nvl(t.jfje,0),'99999999999990.99')jfje,to_char(nvl(t.dfje,0),'99999999999990.99')dfje,to_char(nvl(t.jfje,0)-nvl(t.dfje,0),'99999999999990.99')ye,w.dwmc ,w.wlbh "+
	               "from CW_PZLRMXB t left join cw_pzlrzb zb on t.pzbh=zb.guid left join CW_KJKMSZB kj on t.guid = kj.kmbh left join cw_fzlrb f on  t.guid=f.kmbh left join cw_wldwb  w on w.wlbh = f.dfdw  where 1=1 and f.dfdw is not null";
		if(Validate.noNull(dm)){
			sql +=" and w.wlbh = '"+dm+"'";
		}
		if(Validate.noNull(kmbh)){
			sql +=" and t.kmbh like '"+kmbh+"%'";
		}
		if(Validate.noNull(pzzt)){
			sql +=" and zb.pzzt in ("+pzzt+")";
		}
		if(Validate.noNull(startMonth)&&Validate.noNull(endMonth)){
			sql +=" and zb.pznd='"+pznd+"' and zb.kjqj >= "+startMonth+" and zb.kjqj <="+endMonth;
		}
		if(Validate.noNull(bmbh)){
			sql +=" and t.bmbh='"+bmbh+"'";
		}
		if(Validate.noNull(xmbh)){
			sql +=" and t.xmbh='"+xmbh+"'";
		}
		if(Validate.noNull(jfjel) && !jfjel.equals("undefined")){
			sql +=" and t.jfje>="+jfjel;
		}
		if(Validate.noNull(jfjeh) && !jfjeh.equals("undefined") ){
			sql +=" and t.jfje<="+jfjeh;
		}
		if(Validate.noNull(zy)){
			sql +=" and t.zy like '%"+zy+"%'";
		}

		System.err.println("sql====="+sql);
		return db.queryForList(sql);
	}
	
	public  List jjszMenuzj(String dm){
		String sql = "";
		if(Validate.noNull(dm)){
			sql = "select t.guid,t.wlbh,t.dwmc from cw_wldwb t WHERE T.k='"+dm+"'  and t.qyf='1' ";
		}else{
			sql = "select t.guid,t.kmnd,t.kmmc,t.l,t.k,T.KMJC from cw_wldwb t WHERE T.k is null and l is null and t.qyf='1' ";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}
	
	public int getCount(String kmbh){
		String sql = "select count(0) from cw_jjkmb t WHERE T.k='"+kmbh+"' and t.qyf='1' ";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0"));
		return count;
	}
	
	public  List bdbxMenu(){
		String sql = "select t.wlbh dm,t.dwmc mc from cw_wldwb t where nvl(t.sfdgzf,00) <> '01'";		
		List menuList = db.queryForList(sql);
		return menuList;
	}
	
	
}
