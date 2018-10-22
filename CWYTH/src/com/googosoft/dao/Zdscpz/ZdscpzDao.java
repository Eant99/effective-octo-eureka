package com.googosoft.dao.Zdscpz;

import java.util.ArrayList;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.Zdscpz.Zdscpz;
import com.googosoft.util.PageData;
import com.googosoft.util.WindowUtil;

/**
 * 凭证自动化dao
 * @author googosoft
 *
 */
@Repository("zdscpzDao")
public class ZdscpzDao extends BaseDao {
	
	/**
	 * 凭证自动化信息查询
	 * @return
	 */
	public Map<String, Object> getByGid() {
		String sql = "select zdscpz,zdlrpz,sfyxscpz,pzdjsfbc,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb where guid = zdr) as zdr,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb where guid = fhr) as fhr,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb where guid = jzr) as jzr"
				+ " from CW_ZDSCPZ  ";
		return db.queryForMap(sql);
	}
	
	public Map<String, Object> getGjc() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '01' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getCzc() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '02' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getCtqc() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '03' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getHc() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '04' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getGt1() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '05' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getLc() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '06' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getFj() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'jtgj' and dm = '07' ";
		return db.queryForMap(sql);
	}
	public Map<String, Object> getPzsc() {
		String sql = "select dm,mc,ms from gx_sys_dmb where zl = 'pzscsz' and dm = '01' ";
		return db.queryForMap(sql);
	}
	
	
	/**
	 * 凭证自动化保存修改
	 * @param dto
	 * @return
	 */
	public int doSave(Zdscpz dto) {
		String sql = "UPDATE CW_ZDSCPZ SET  zdscpz=?,zdlrpz=?,sfyxscpz=?,PZDJSFBC=?,zdr =(select guid from gx_sys_ryb where rybh = ?),fhr =(select guid from gx_sys_ryb where rybh =  ?),"
				+ "jzr = (select guid from gx_sys_ryb where rybh = ?) ";
		return db.update(sql,new Object[]{dto.getZdscpz(),dto.getZdlrpz(),dto.getSfyxscpz(),dto.getPzdjsfbc(),WindowUtil.getText(dto.getZdr()),WindowUtil.getText(dto.getFhr()),WindowUtil.getText(dto.getJzr())});
	}
	
	public int doSave5(PageData pd) {
		String a1 = pd.getString("a1");
		String a2 = pd.getString("a2");
		String a3 = pd.getString("a3");
		String a4 = pd.getString("a4");
		String a5 = pd.getString("a5");
		String a6 = pd.getString("a6");
		String a7 = pd.getString("a7");
		System.err.println("______________"+a1+"__"+a2+"__"+a3+"__"+a4+"__"+a5+"__"+a6+"__"+a7);
		ArrayList list  = new ArrayList();
		if("1".equals(a1)) {
			String sql = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='01' ";
			list.add(sql);
		}else if("0".equals(a1)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='01' ";
			list.add(sql);
		}
		
		if("1".equals(a2)) {
			String sql2 = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='02' ";
			list.add(sql2);
		}else if("0".equals(a2)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='02' ";
			list.add(sql);
		}
		
		if("1".equals(a3)) {
			String sql3 = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='03' ";
			list.add(sql3);
		}else if("0".equals(a3)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='03' ";
			list.add(sql);
		}
		
		if("1".equals(a4)) {
			String sql4 = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='04' ";
			list.add(sql4);
		}else if("0".equals(a4)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='04' ";
			list.add(sql);
		}
		
		if("1".equals(a5)) {
			String sql5 = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='05' ";
			list.add(sql5);
		}else if("0".equals(a5)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='05' ";
			list.add(sql);
		}
		
		if("1".equals(a6)) {
			String sql6 = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='06' ";
			list.add(sql6);
		}else if("0".equals(a6)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='06' ";
			list.add(sql);
		}
		
		if("1".equals(a7)) {
			String sql7 = "update gx_sys_dmb d set d.ms = '1' where zl = 'jtgj' and dm='07' ";
			list.add(sql7);
		}else if("0".equals(a7)) {
			String sql = "update gx_sys_dmb d set d.ms = '0' where zl = 'jtgj' and dm='07' ";
			list.add(sql);
		}
		
//		return db.update(sql);
		Boolean bool = db.batchUpdate(list);
		if(bool) {
			return 1;
		}else {
			return 0;
		}
	}
     /**
      * 借款科目
      * @return
      */
	public Map getJkkm() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='jkkmbh' and d.dm = '01' ";
		return db.queryForMap(sql);
	}
	/**
	 * 薪资项目
	 * @return
	 */
	public Map getXzxm(String sszt) {
		StringBuffer sqltext=new StringBuffer();
		sqltext.append("select * from");
		sqltext.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbhmc,x.bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
				+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
				+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
				+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
				+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
				+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
				+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
				+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"') k");
		sqltext.append(" where k.bmbh = (select t.ms from gx_sys_dmb t where t.zl='xzbmbh' and t.dm='01') " + 
				"   and k.xmbh =  (select t.ms from gx_sys_dmb t where t.zl='xzxmbh' and t.dm='01') " + 
				" order by XMBH asc");
		return db.queryForMap(sqltext.toString());
	}

	public  Map getXxjjhj() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='xxjjhj' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXxjjhd() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='xxjjhd' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXxjjzj() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='xxjjzj' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXxjjzd() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='xxjjzd' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXyjjj() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='xyjjj' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXyjjd() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join Cw_kjkmszb k on k.kmbh = d.ms where d.zl='xyjjd' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getGlf() {
		String sql = "select d.ms as kmbh,k.kmmc  from GX_SYS_DMB d left join cw_jjkmb k on k.kmbh = d.ms where d.zl='glf' and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXxxm() {
		String sql = "select d.ms as xmbh,k.xmmc,k.bmbh  from GX_SYS_DMB d left join cw_xmjbxxb k on k.xmbh = d.ms and d.mc=k.bmbh where d.zl='xxxm'  and d.dm = '01' ";
		return db.queryForMap(sql);
	}

	public Map getXyxm() {
		String sql = "select d.ms as xmbh,k.xmmc,k.bmbh  from GX_SYS_DMB d left join cw_xmjbxxb k on k.xmbh = d.ms and d.mc=k.bmbh where d.zl='xyxm'  and d.dm = '01' ";
		return db.queryForMap(sql);
	}

}
