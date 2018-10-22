package com.googosoft.dao.xmgl.xmcx;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;

@Repository("xmcxDao1")
public class XmcxDao extends BaseDao{

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
	 * 获取某个单位下的人员信息
	 * @param dwbh 单位编号
	 * @return
	 */
	public List GetModels(String sjdw) {
		String sql = "SELECT RYBH,RYGH,XM FROM %SRYB WHERE DWBH=? AND RYBH<>'000000' ";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
	}
	/**
	 * 获取某个单位下的人员信息
	 * @param dwbh 单位编号
	 * @return
	 */
	public List GetTjModels(String sjdw) {
		String sql = "SELECT RYBH,RYGH,XM FROM %SRYB WHERE DWBH=? AND RYBH<>'000000' and rybh in (select fzr from Cw_xmjbxxb )";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
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
/**
 *获取项目信息详情
 * @param guid
 * @return
 */
	public Map<String, Object> selectXmxxMapById(String guid){
		String sql = " select x.guid,  (select '('||dwbh||')'|| mc from gx_sys_dwb dw where dw.dwbh=x.bmbh) as bmbh,  x.xmbh,  x.xmdl, (select D.MC from gx_sys_dmb d  where d.zl = 'XMDL' and d.dm = x.xmdl) xmdlmc,"
				+ "  x.xmlb,(select D.MC from gx_sys_dmb d where d.zl = 'XMLB' and d.dm = x.xmlb) xmlbmc,  x.xmmc, x.xmlx,x.xmlxid,"
				+ "(select '('||t.xmlxbh||')'|| t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,x.xmjfsfyxcb,x.cbbl,x.sfjxjjflfykz,"
				+ " (select D.MC from gx_sys_dmb d  where d.zl = 'XMLX' and d.dm = x.xmlx) xmlxmc,x.fzr,"
				+ " ('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"
				+ "  x.xmsx, (select D.MC from gx_sys_dmb d where d.zl = 'XMSX' and d.dm = x.xmsx) xmsxmc,"
				+ "  x.gkbm, ('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ "  x.sfqy, (case sfqy when '0' then '未启用' when '1' then '已启用' else '' end) as sfqymc,"
				+ "  x.sfwg, x.kgrq, x.wgrq,  x.sfczzc,x.sfgk,x.gkxxm,x.yslx,(select d.mc from gx_sys_dmb d where d.zl='YSLX'and d.dm=x.yslx)yslxmc,to_char(x.ysje,'FM9999999999999990.00')ysje, to_char(x.syje,'FM9999999999999990.00')syje,"
				+ "  s.srkmbh, ('('||s.srkmbh||')'||(select b.kmmc from CW_KJKMSZ b where b.guid=s.srkmbh))srkmmc, z.zckmbh,"
				+ "  ('('||z.zckmbh||')'||(select b.kmmc from CW_KJKMSZ b where b.guid=z.zckmbh))zckmmc, j.jjfl,"
				+ " ('('||j.jjfl||')'||(select b.kmmc from CW_JJKMb b where b.kmbh=j.jjfl))jjflmc,x.sfky,x.kjkmyxjz,x.jjkmyxjz from Cw_xmjbxxb x  left join Cw_xmkzxxb c"
				+ "  on c.xmbh = x.xmbh left join Cw_xmsrbnew s on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ "  left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.guid='"+guid+"' ";
		return db.queryForMap(sql);
	}

	/**
   	 * 得到sr科目信息
   	 * @param guid
   	 * @return
   	 */
	public List<Map<String, Object>> getsrkmById(String guid,String kjzd) {
   		String sql = " select distinct(select srkmbh from cw_xmsrbnew sr where xmxxbh = ? and yslx='01') jbzcsrkmbh," 
   					+" (select (select kmmc from cw_kjkmszb kj where kj.kmbh = sr.srkmbh and kjzd = '"+kjzd+"') as kmmc " 
   					+" from cw_xmsrbnew sr where xmxxbh = ? and yslx='01') jbzckmmc," 
   					+" (select srkmbh from cw_xmsrbnew sr where xmxxbh = ? and yslx='02') xmzcsrkmbh," 
   					+" (select (select kmmc from cw_kjkmszb kj where kj.kmbh = sr.srkmbh and kjzd = '"+kjzd+"') as kmmc " 
   					+" from cw_xmsrbnew sr where xmxxbh = ? and yslx='02') xmzckmmc " 
   					+" from cw_xmsrbnew sr where sr.xmxxbh = ?";
   		return db.queryForList(sql,new Object[] {guid,guid,guid,guid,guid});
   		
   	}

   	/**
   	 * 得到zc科目信息
   	 * @param guid
   	 * @return
   	 */
   	public List<Map<String, Object>> getszckmById(String guid,String kjzd) {
   		String sql = "select guid,xmxxbh,zckmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=zc.zckmbh and kjzd='"+kjzd+"') as kmmc from cw_xmzcbnew zc where xmxxbh=? ";
   		return db.queryForList(sql,new Object[] {guid});
   		
   	}

   	/**
   	 * 得到sr科目信息
   	 * @param guid
   	 * @return
   	 */
   	public List<Map<String, Object>> getjjflkmById(String guid) {
   		String sql = "select guid,xmxxbh,jjfl,(select kmmc from cw_jjkmb jj where jj.kmbh=jjfl.jjfl) as kmmc from cw_xmjjflbnew jjfl where xmxxbh=? ";
   		return db.queryForList(sql,new Object[] {guid});
   		
   	}

	
}
