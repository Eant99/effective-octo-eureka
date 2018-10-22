package com.googosoft.dao.ysgl.bmyshz;

import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;

@Repository("bmyshzDao")
public class BmyshzDao extends BaseDao{
	private Logger logger = Logger.getLogger(BmyshzDao.class);

	
	/**
	 * 
	 * 获得项目分类
	 */
	public List<Map<String, Object>> getXmfl(String qrzt) {
		String sql = "select k.xmfl,d.mc from Cw_srysmxb t\r\n" + 
				"left join Cw_srxmb k on k.guid=t.srxmbh\r\n" + 
				"left join gx_sys_dmb d on d.dm=k.xmfl\r\n" + 
				"left join Cw_srysb s on t.srysbh=s.guid\r\n" + 
				"where d.zl='xmfl' and nvl(s.qrzt,'0')=?\r\n" + 
				"\r\n" + 
				"\r\n" + 
				"group by k.xmfl,d.mc";
		return db.queryForList(sql,qrzt);
		
		
	}
	/**
	 * 修改基本支出确认状态
	 * 
	 */
	public int updateQrzt(String guid) {
		String sql = "update cw_jbzcysb set qrzt ='1' where guid=?";
		
		String sql2 = "select dw.mc as mc, decode(sum(nvl(dynzchz, '0')),'0', '0.0000', to_char(round(sum(dynzchz), 4), 'fm999999999999990.0000')) dynzchz, dw.dwld as zcr,to_char(jb.sbnd,'yyyy') as sbnd ,jb.sbbm from cw_jbzcysb jb\r\n" + 
				"left join gx_sys_dwb dw on dw.dwbh = jb.sbbm where jb.guid=?"
				+ "\r\n" + 
				"group by dw.dwld, jb.sbbm,jb.sbnd,jb.dynzchz,mc";
		List list = db.queryForList(sql2, guid);
		for(int i=0;i<list.size();i++) {
			
			Map map = (Map) list.get(i);
			String zcr = (String) map.get("zcr");
			String dynzchz = (String) map.get("dynzchz");
			String sbbm = (String) map.get("sbbm");
			int sbnd =  Integer.parseInt( (String) map.get("sbnd"));
			String mc = (String) map.get("mc");
			String xmbh = sbbm+"001";
			String xmmc = mc+"部门经费";
			String jflx = "部门经费";
			String xmkssj = (sbnd+1)+"-01-01";
			String xmjssj = (sbnd+1)+"-12-31";
			String insertJfsz = "insert into cw_jfszb(guid,nd,fzr,bmbh,xmbh,xmmc,jflx,kssj,jssj,ysje,syje,sffh)\r\n" + 
					"values(sys_guid(),?,?,?,?,?,'01',?,?,?,?,'0')";
			db.update(insertJfsz, sbnd,zcr,sbbm,xmbh,xmmc,xmkssj,xmjssj,dynzchz,dynzchz); 
	}
		
		int i = db.update(sql,guid);
		return i;
	}
	/**
	 * 修改收入预算表确认状态
	 * 
	 */
	public int updateSrysQrzt(String guid) {
		String sql = "update cw_srysb set qrzt ='1' where guid=?";
		int i = db.update(sql,guid);
		return i;
	}
	/**
	 * 修改项目支出预算表确认状态
	 * 
	 */
	public int updateXmzcQrzt(String guid) {
		String sql = "update cw_xmzcysb set qrzt ='1' where guid=?";
		String sql2 = "select dw.mc as mc, ktmc,ktbh,decode(sum(nvl(dynzchz, '0')), '0', '0.0000', to_char(round(sum(dynzchz), 4), 'fm999999999999990.0000')) dynzchz, ry.rybh as zcr, to_char(xm.sbnd, 'yyyy') as sbnd,xm.sbbm\r\n" + 
				"  from cw_xmzcysb xm\r\n" + 
				"  left join gx_sys_dwb dw on dw.dwbh = xm.sbbm \r\n" + 
				"  left join gx_sys_ryb ry on ry.guid=xm.zcr\r\n" + 
				"  where xm.guid=?\r\n" + 
				" group by ry.rybh, xm.sbbm, xm.sbnd, xm.dynzchz,mc,ktmc,ktbh";
		List list = db.queryForList(sql2, guid);
		for(int i=0;i<list.size();i++) {
			
			Map map = (Map) list.get(i);
			String zcr = (String) map.get("zcr");
		//	System.out.println("zcr======================"+zcr);
			String dynzchz = (String) map.get("dynzchz");
			String sbbm = (String) map.get("sbbm");
			int sbnd =  Integer.parseInt( (String) map.get("sbnd"));
			String mc = (String) map.get("mc");
			String xmbh = (String) map.get("ktbh");
			String xmmc = (String) map.get("ktmc");
		//	System.out.println("ktbh======================"+xmbh);
		//	System.out.println("ktmc======================"+xmmc);
			String jflx = "部门经费";
			String xmkssj = (sbnd+1)+"-01-01";
			String xmjssj = (sbnd+1)+"-12-31";
			String insertJfsz = "insert into cw_jfszb(guid,nd,fzr,bmbh,xmbh,xmmc,jflx,kssj,jssj,ysje,syje,sffh)\r\n" + 
					"values(sys_guid(),?,?,?,?,?,'02',?,?,?,?,'0')";
			db.update(insertJfsz, sbnd,zcr,sbbm,xmbh,xmmc,xmkssj,xmjssj,dynzchz,dynzchz); 
	}
	int i = db.update(sql,guid);
		return i;
	}
	/**
	 * 
	 * 获得基本支出表
	 */
	public List<Map<String, Object>> getJjbzc(String qrzt,String sbnd) {
		if(sbnd.length()==0) {
			String sql = "select  a.kmmc as mc,\r\n" + 
					"       b.kmmc,\r\n" + 
					"       decode(sum(nvl(dynzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dynzc), 4), 'fm999999999999990.0000')) dynzc,\r\n" + 
					"       decode(sum(nvl(denzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(denzc), 4), 'fm999999999999990.0000')) denzc,\r\n" + 
					"       decode(sum(nvl(dsnzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dsnzc), 4), 'fm999999999999990.0000')) dsnzc,\r\n" + 
					"       wm_concat(to_char(k.guid)) guid,\r\n" + 
					"       wm_concat(to_char(d.mc)) dwmc,\r\n" + 
					"       wm_concat(to_char(t.bz))bz\r\n" + 
					"  from Cw_jbzcysmxb t\r\n" + 
					"  left join Cw_jbzcysb k on k.guid = t.jbzcbh\r\n" + 
					"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
					"  left join CW_JJKMB b on b.guid = t.jjkmbh\r\n" + 
					"  left join cw_jjkmb a on a.kmbh  = b.k\r\n" + 
					" where\r\n" + 
					"    nvl(k.qrzt,'0')=? and (to_char(k.sbnd,'yyyy')=? or nvl(k.qrzt,'0')=? )\r\n" + 
					" group by a.kmmc, b.kmmc";
			return db.queryForList(sql,qrzt,sbnd,qrzt);						
		}else {
			String sql = "select  a.kmmc as mc,\r\n" + 
					"       b.kmmc,\r\n" + 
					"       decode(sum(nvl(dynzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dynzc), 4), 'fm999999999999990.0000')) dynzc,\r\n" + 
					"       decode(sum(nvl(denzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(denzc), 4), 'fm999999999999990.0000')) denzc,\r\n" + 
					"       decode(sum(nvl(dsnzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dsnzc), 4), 'fm999999999999990.0000')) dsnzc,\r\n" + 
					"       wm_concat(to_char(k.guid)) guid,\r\n" + 
					"       wm_concat(to_char(d.mc)) dwmc,\r\n" + 
					"       wm_concat(to_char(t.bz)) bz\r\n" + 
					"  from Cw_jbzcysmxb t\r\n" + 
					"  left join Cw_jbzcysb k on k.guid = t.jbzcbh\r\n" + 
					"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
					"  left join CW_JJKMB b on b.guid = t.jjkmbh\r\n" + 
					"  left join cw_jjkmb a on a.kmbh  = b.k\r\n" + 
					" where \r\n" + 
					"   nvl(k.qrzt,'0')=? and to_char(k.sbnd,'yyyy')=?  \r\n" + 
					" group by a.kmmc, b.kmmc";
			return db.queryForList(sql,qrzt,sbnd);
			
		}
		
		
		
	}
	/**
	 * 
	 * 通过申报年度获得基本支出表
	 */
	public List<Map<String, Object>> getJjbzcSbnd(String qrzt,String sbnd) {
		String sql = "select  a.kmmc as mc,\r\n" + 
				"       b.kmmc,\r\n" + 
				"       decode(sum(nvl(dynzc, '0')),\r\n" + 
				"              '0',\r\n" + 
				"              '0.0000',\r\n" + 
				"              to_char(round(sum(dynzc), 4), 'fm999999999999990.0000')) dynzc,\r\n" + 
				"       decode(sum(nvl(denzc, '0')),\r\n" + 
				"              '0',\r\n" + 
				"              '0.0000',\r\n" + 
				"              to_char(round(sum(denzc), 4), 'fm999999999999990.0000')) denzc,\r\n" + 
				"       decode(sum(nvl(dsnzc, '0')),\r\n" + 
				"              '0',\r\n" + 
				"              '0.0000',\r\n" + 
				"              to_char(round(sum(dsnzc), 4), 'fm999999999999990.0000')) dsnzc,\r\n" + 
				"       wm_concat(to_char(k.guid)) guid,\r\n" + 
				"       wm_concat(to_char(d.mc)) dwmc,\r\n" + 
				"       wm_concat(to_char(t.bz)) bz\r\n" + 
				"  from Cw_jbzcysmxb t\r\n" + 
				"  left join Cw_jbzcysb k on k.guid = t.jbzcbh\r\n" + 
				"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
				"  left join CW_JJKMB b on b.guid = t.jjkmbh\r\n" + 
				"  left join cw_jjkmb a on a.kmbh  = b.k\r\n" + 
				" where \r\n" + 
				"   nvl(k.qrzt,'0')=? and to_char(k.sbnd,'yyyy')=?  \r\n" + 
				" group by a.kmmc, b.kmmc";
		return db.queryForList(sql,qrzt,sbnd);
		
		
	}
	/**
	 * 
	 * 获得项目支出表
	 */
	public List<Map<String, Object>> getXmzc(String qrzt,String sbnd) {
		System.out.println("sbnd====================================="+sbnd.length());
		if (sbnd.length()==0) {
			String sql = "select  a.kmmc as mc,\r\n" + 
					"       b.kmmc,\r\n" + 
					"       decode(sum(nvl(dynzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dynzc), 4), 'fm999999999999990.0000')) dynzc,\r\n" + 
					"       decode(sum(nvl(denzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(denzc), 4), 'fm999999999999990.0000')) denzc,\r\n" + 
					"       decode(sum(nvl(dsnzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dsnzc), 4), 'fm999999999999990.0000')) dsnzc,\r\n" + 
					"       wm_concat(to_char(k.guid)) guid,\r\n" + 
					"       wm_concat(to_char(d.mc)) dwmc, wm_concat(to_char(t.bz))bz\r\n" + 
					"  from Cw_xmzcysmxb t\r\n" + 
					"  left join Cw_xmzcysb k on k.guid = t.xmzcbh\r\n" + 
					"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
					"  left join CW_JJKMB b on b.guid = t.jjkmbh\r\n" + 
					"  left join cw_jjkmb a on a.kmbh  = b.k\r\n" + 
					" where \r\n" + 
					"    nvl(k.qrzt,'0')=?\r\n" + 
					" group by a.kmmc, b.kmmc";
			return db.queryForList(sql,qrzt);
			
		}else {
			String sql = "select  a.kmmc as mc,\r\n" + 
					"       b.kmmc,\r\n" + 
					"       decode(sum(nvl(dynzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dynzc), 4), 'fm999999999999990.0000')) dynzc,\r\n" + 
					"       decode(sum(nvl(denzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(denzc), 4), 'fm999999999999990.0000')) denzc,\r\n" + 
					"       decode(sum(nvl(dsnzc, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dsnzc), 4), 'fm999999999999990.0000')) dsnzc,\r\n" + 
					"       wm_concat(to_char(k.guid)) guid,\r\n" + 
					"       wm_concat(to_char(d.mc)) dwmc, wm_concat(to_char(t.bz))bz\r\n" + 
					"  from Cw_xmzcysmxb t\r\n" + 
					"  left join Cw_xmzcysb k on k.guid = t.xmzcbh\r\n" + 
					"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
					"  left join CW_JJKMB b on b.guid = t.jjkmbh\r\n" + 
					"  left join cw_jjkmb a on a.kmbh  = b.k\r\n" + 
					" where to_char(k.sbnd,'yyyy')=?\r\n" + 
					"   and nvl(k.qrzt,'0')=?\r\n" + 
					" group by a.kmmc, b.kmmc";
			return db.queryForList(sql,sbnd,qrzt);
			
		}
		
		
		
	}
	
	
	/**
	 * 
	 * 获得项目分类下一级
	 */
	public List<Map<String, Object>> getXmflXyj(String xmfl,String qrzt,String sbnd) {
		if(sbnd.length()==0) {
			String sql = "select xm.srxmmc,\r\n" + 
					"       xm.srbz,\r\n" + 
					"       t.bz,\r\n" + 
					"       decode(sum(nvl(dynsr, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dynsr), 4), 'fm999999999999990.0000')) dynsr,\r\n" + 
					"       decode(sum(nvl(densr, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(densr), 4), 'fm999999999999990.0000')) densr,\r\n" + 
					"       decode(sum(nvl(dsnsr, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dsnsr), 4), 'fm999999999999990.0000')) dsnsr,\r\n" + 
					"       wm_concat(to_char(k.guid)) guid,\r\n" + 
					"       wm_concat(to_char(d.mc)) dwmc\r\n" + 
					"  from Cw_srysmxb t\r\n" + 
					"  left join Cw_srysb k on t.srysbh = k.guid\r\n" + 
					"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
					"  left join Cw_srxmb xm on xm.guid = t.srxmbh\r\n" + 
					" where \r\n" + 
					"   xm.xmfl = ?\r\n" + 
					"   and nvl(k.qrzt, '0') = ?\r\n" + 
					" group by t.bz, dynsr, densr, dsnsr, xm.srxmmc, xm.srbz";
			return db.queryForList(sql,xmfl,qrzt);
			
			
		}else {
			String sql = "select xm.srxmmc,\r\n" + 
					"       xm.srbz,\r\n" + 
					"       t.bz,\r\n" + 
					"       decode(sum(nvl(dynsr, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dynsr), 4), 'fm999999999999990.0000')) dynsr,\r\n" + 
					"       decode(sum(nvl(densr, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(densr), 4), 'fm999999999999990.0000')) densr,\r\n" + 
					"       decode(sum(nvl(dsnsr, '0')),\r\n" + 
					"              '0',\r\n" + 
					"              '0.0000',\r\n" + 
					"              to_char(round(sum(dsnsr), 4), 'fm999999999999990.0000')) dsnsr,\r\n" + 
					"       wm_concat(to_char(k.guid)) guid,\r\n" + 
					"       wm_concat(to_char(d.mc)) dwmc\r\n" + 
					"  from Cw_srysmxb t\r\n" + 
					"  left join Cw_srysb k on t.srysbh = k.guid\r\n" + 
					"  left join gx_sys_dwb d on d.dwbh = k.sbbm\r\n" + 
					"  left join Cw_srxmb xm on xm.guid = t.srxmbh\r\n" + 
					" where to_char(k.sbnd, 'yyyy')=?\r\n" + 
					"   and xm.xmfl = ?\r\n" + 
					"   and nvl(k.qrzt, '0') = ?\r\n" + 
					" group by t.bz, dynsr, densr, dsnsr, xm.srxmmc, xm.srbz";
			return db.queryForList(sql,sbnd,xmfl,qrzt);
		}
		
		
		
	}

}
