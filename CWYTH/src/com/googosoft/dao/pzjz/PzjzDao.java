package com.googosoft.dao.pzjz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("pzjzDao")
public class PzjzDao extends BaseDao{
	private Logger logger = Logger.getLogger(PzjzDao.class);
	/**
	 *  记账
	 * @param pd
	 * @param type
	 * @param userid
	 * @return
	 */
	public int doDeal(PageData pd,String type,String userid) {
		String guid = pd.getString("guid");
		String jzr = "";
		String sql = "update Cw_pzlrzb set pzzt=? WHERE guid in ('"+guid+"')";
		String sql1 = "update Cw_pzlrzb set JZR=?,jzrq=sysdate WHERE guid in ('"+guid+"')";
		String sql2 = "update Cw_pzlrzb set JZR=?,jzrq=sysdate WHERE guid in ('"+guid+"')";
		Object[] obj = new Object[]{"02"};
		Object[] obj2 = new Object[]{"01"};
		Object[] obj3 = new Object[]{userid};
		Object[] obj4 = new Object[]{jzr};
		int i = 0;
		try {  
			if("jz".equals(type)){
				i = db.update(sql, obj);
				i = db.update(sql1, obj3);
			}else if("fjz".equals(type)){
				i = db.update(sql, obj2);
				db.update(sql2,obj4);
			}
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 
	 */
	public  List getPrintInfoByIds(PageData pd){
		String guids = pd.getString("guid");
		String sql = " select distinct t.jzr,s.MC,(select r.xm from gx_sys_ryb r where r.guid=t.jzr)jzrmc, t.zdr,(select r.xm from gx_sys_ryb r where r.guid=t.zdr)zdrmc,"
				+ " t.fhr,(select r.xm from gx_sys_ryb r where r.guid=t.fhr)fhrmc,t.guid,m.zy,m.kmbh,(select b.kmmc from cw_jjkmb b where b.kmbh=m.kmbh)kmmc,(select (to_char(b.jfjehj，'FM999999999990.00')） from CW_PZLRZB b where b.guid in ("+guids+")) as jfjehj,(select (to_char(b.dfjehj，'FM999999999990.00')） from CW_PZLRZB b where b.guid in ("+guids+")) as dfjehj from CW_PZLRZB t"
				+ " left join cw_pzlrmxb m on m.pzbh = t.guid left join GX_SYS_DWB s on s.dwbh=m.bmbh where t.guid in ("+guids+")";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  String getdwmcByIds(String pzbh){
		String sql = " select s.MC from CW_PZLRZB t"
				+ " left join cw_pzlrmxb m on m.pzbh = t.guid left join GX_SYS_DWB s on s.dwbh=m.bmbh where t.pzbh in ("+pzbh+")";
		List list = db.queryForList(sql);
		Map temp =(Map)list.get(0);
		String dwmc1 =(String) temp.get("MC");
		return dwmc1;
	}
	public Map getInfo(PageData pd) {
		Map map = db.queryForMap(" select t.jzr,(select r.xm from gx_sys_ryb r where r.guid=t.jzr)jzrmc,"
				+ " t.zdr,(select r.xm from gx_sys_ryb r where r.guid=t.zdr)zdrmc,"
				+ " t.fhr,(select r.xm from gx_sys_ryb r where r.guid=t.fhr)fhrmc from CW_PZLRZB t where t.guid in ("+pd.getString("guid")+") "
				+ " ");
		return map;
	}
	
	/**
	 * 查询凭证录入
	 * */
	public Map<String, Object> selectPzlrzb(String pznd,String kjqj,String pzbh,String sszt){
	//	String sql = "select 	(select mc from gx_sys_dmb where zl = 'pzly' and dm = a.pzly)as pzlymc ," + 
	//			"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
	//			"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
	//			"				guid, pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
	//			+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and a.pzz = '"+pzz+"' and sszt = '"+sszt+"'";
//		String sql = "select 	(select mc from gx_sys_dmb where zl = 'pzly' and dm = a.pzly)as pzlymc ," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
//				"				(select '('||rybh||')'||xm from gx_sys_ryb where guid = a.cn) as cnr," + 
//				"				guid,pzzt,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
//				+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"'";
//		return db.queryForMap(sql);
		String sql = "select 	(select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ," + 
				"				(select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt," + 
				"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
				"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
				"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
				"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
				"				guid, pzbh,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
				+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"'";
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> selectPzlrmxAndFzlr(String pznd,String kjqj,String pzbh,String sszt){
		/*String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and "
				+ "pzz = '"+pzz+"' and sszt = '"+sszt+"')";*/
//		String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
//				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
//				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
//				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
//				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
//				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
//				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
//				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"')";
//		return db.queryForList(sql);
		String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' )";
		return db.queryForList(sql);
	}
	
	//获取凭证编号列表
		public List<String> getPzbhList(String pznd,String kjqj,String sszt){
		//	String sql = "select pzbh from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"' order by pzbh asc";
			String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' order by pzbh asc";
			return db.queryForList(sql,String.class);
		}
		//获取凭证字列表
		public List<Map<String, Object>> getPzzList(String sszt){
			String sql = "select lxbh,pzz from cw_pzlxb where sszt = '"+sszt+"' order by lxbh";
			return db.queryForList(sql);
		}
		//获取账套会计期间
		public Map<String, Object> getZtXx(String ztid){
			String sql = "select kjnd,qjs from cw_ztxxb where guid = '"+ztid+"'";
			return db.queryForMap(sql);
		}
		
		//查询复核人
		public Object getFhr(String pzbh,String sszt,String pznd,String kjqj){
		//	String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and pzz = '"+pzz+"' and sszt = '"+sszt+"'";
			String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' ";
			return db.queryForObject(sql, String.class);
		}
		//查询借方必有,借方必无，
		public Map<String, Object> getBybwkm(String pzz,String sszt ,String pznd,String kjqj) {
			String sql = "select distinct \r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Jf') as jfbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Df') as dfbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzby') as pzbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzbw') as pzbwkm\r\n" + 
					"from cw_pzlxb t join cw_pzkmdzb s on t.guid = s.pzlxm where t.lxbh = '"+pzz+"' and t.sszt = '"+sszt+"'and pznd = '"+pznd+"' and kjqj = '"+kjqj+"'";
			return db.queryForMap(sql);
		}
		
		//获取最大的凭证编号
		public String getMaxPzbh(String pznd,String kjqj,String sszt) {
		//	String sql = "select max(pzbh) from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"'";
			String sql = "select max(pzbh) from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' ";
			return db.queryForSingleValue(sql);
		}
		
		/**
		 * 获取交互数据
		 * */
		public Map<String, Object> getKjkmInfo(String kmbh){
			String sql = "select a.kmmc,a.yefx,a.sfjjflkm,a.bmhs,a.xmhs,b.kmzye as kmye from cw_kjkmszb a left join cw_kmyeb b "
					+ " on a.kmbh = b.kmbh where a.kmbh = '"+kmbh+"' and a.sfmj = '1'";
			return db.queryForMap(sql);
		}
		public Map<String, Object> getJjkmInfo(String kmbh){
			String sql = "select a.kmmc as zcjjflkm from cw_jjkmb a where a.kmbh = '"+kmbh+"' ";
			return db.queryForMap(sql);
		}
		public Map<String, Object> getBmInfo(String bmbh){
			String sql = "select a.mc as bmmc from gx_sys_dwb a where a.dwbh = '"+bmbh+"'";
			return db.queryForMap(sql);
		}
		public Map<String, Object> getXmInfo(String xmbh,String bmbh){
			String strWhere = "";
			if(! Validate.isNullOrEmpty(bmbh)) {
				strWhere += " and a.bmbh = '"+bmbh+"'";
			}
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.xmmc,");
			sql.append(" wm_concat(distinct to_char(k1.kmmc)) as srkmmc,");
			sql.append(" wm_concat(distinct to_char(k1.kmmc)) as srkm,");
			sql.append(" wm_concat(distinct to_char(k2.kmmc)) as zckmmc,");
			sql.append(" wm_concat(distinct to_char(k2.kmmc)) as zckm,");
			sql.append(" a.syje,r.xm as fzr,");
			sql.append(" DECODE(NVL(A.SYJE,0),0,'0.00',TO_CHAR(ROUND(A.SYJE,2),'FM99999999990.00'))AS XMYE,");
			sql.append(" r.xm as fzrxm,a.gkxxm as xmgkxxm,");
			sql.append(" a.gkxxm,b.bz,b.xmlxmc as xmlx,");
			sql.append(" b.xmlxmc");
			sql.append(" from cw_xmjbxxb a");
			sql.append(" left join cw_xmlxb b  on a.xmlb = b.xmlxbh");
			sql.append(" left join cw_xmsrb c  on b.guid = c.xmlxbh");
			sql.append(" left join cw_xmzcb d on b.guid = d.xmlxbh");
			sql.append(" left join cw_kjkmszb k1 on k1.kmbh=c.srkmbh");
			sql.append(" left join cw_kjkmszb k2 on k2.kmbh=d.zckmbh");
			sql.append(" left join gx_sys_ryb r on r.rybh=a.fzr");
			sql.append(" where a.xmbh = '"+xmbh+"' ");
			sql.append(strWhere);
			sql.append(" group by a.xmmc,a.syje,a.gkxxm,b.xmlxmc,r.xm,b.bz");
			return db.queryForMap(sql.toString());
		}
		
		public String pzbhwfh(String guid) {
			String sql = "select min(pzbh) from cw_pzlrzb where pzzt = '00'";
			return db.queryForSingleValue(sql);
		}
		
		//获取期末结账会计期间
		public Map<String, Object> getKjqj(String ztid){
			String sql = "select * from (select * from cw_qmjzb where sszt= '"+ztid+"' and sfjz = '0' order by ztnd,to_number(kjqj)) where rownum <= 1";
			return db.queryForMap(sql);
		}
		/**
		 * 获取当前会计区间
		 * @param sszt
		 * @return
		 */
		public String getDqkjqj(String sszt) {
			String sql=" select min(kjqj)-2 from cw_qmjzb where sfjz=0 and sszt='"+sszt+"' ";
			return db.queryForSingleValue(sql);
		}
		public String getDqkjqjYear(String sszt) {
			String sql=" select ztnd from cw_qmjzb where sfjz=0 and sszt='"+sszt+"' ";
			return db.queryForSingleValue(sql);
		}
		public Map getDqkjqjMin(String sszt) {
			String sql="select to_char(min(mon),'yyyy-mm-dd') as stime,to_char(last_day(min(mon)),'yyyy-mm-dd') as etime from (select to_date(ztnd||'-'||kjqj,'yyyy-mm') mon from CW_QMJZB where sfjz = '0' and sszt='"+sszt+"') ";
			return db.queryForMap(sql);
		}
}
