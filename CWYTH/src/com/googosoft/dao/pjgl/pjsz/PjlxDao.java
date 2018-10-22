package com.googosoft.dao.pjgl.pjsz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.mbxz.MbxzDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("pjlxDao")
public class PjlxDao  extends BaseDao{

	private Logger logger = Logger.getLogger(PjlxDao.class);

	/**
	 * 票据用途信息查询
	 * @param guid
	 * @return
	 */
	public Map getPjlxMapById(String guid) {
		String sql = " select GUID,PJLXBH,PJLXMC,SSZT from  CW_PJLXB where 1=1 and  GUID=? ";
		Object[] obj = new Object[]{guid};
		Map map = null;
		try {  
			map = db.queryForMap(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return map;
	}
	/**
	 * 新增票据用途信息
	 * @param guid
	 * @return
	 */
	public int addPjlx(PageData pd, String sszt) {
		String sql = " insert into CW_PJLXB (GUID,PJLXBH,PJLXMC,SSZT) "
				+ " values (?,?,?,?) ";
		Object[] obj = new Object[]{
				pd.getString("guid"),
				pd.getString("pjlxbh"),
				pd.getString("pjlxmc"),
				sszt
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改票据用途信息
	 * @param guid
	 * @return
	 */
	public int editPjlx(PageData pd) {
		String sql = "update CW_PJLXB set PJLXBH =?,PJLXMC=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{
				pd.getString("pjlxbh"),
				pd.getString("pjlxmc"),
				pd.getString("guid")
		};
		
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public int doDelete(String guid) {

		String guids=guid.replace(",", "','");
		String sql="delete from CW_PJLXB t where t.guid in('"+guids+"')";
		int i = 0;
		try{
			i = db.update(sql);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public List<Map<String, Object>> getList(String searchValue,String guid,String sszt) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select x.guid, (select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh = x.bmbh) as bmbh, x.xmbh,  x.xmdl, ");
		sql.append("  (select D.MC from gx_sys_dmb d where d.zl = '250'  and d.dm = x.xmdl) xmdlmc, ");
		sql.append("   x.xmlb,(select D.MC from gx_sys_dmb d where d.zl = '251' and d.dm = x.xmlb) xmlbmc, x.xmmc, ");
		sql.append("  (select t.xmlxmc from cw_XMLXB t where t.guid = x.xmlx) as xmlx, (select D.MC from gx_sys_dmb d where d.zl = 'XMLX' ");
		sql.append("  and d.dm = x.xmlx) xmlxmc,  x.fzr, ('(' || x.fzr || ')' || ");
		sql.append("  (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc, ");
		sql.append("   x.xmsx,(case xmsx when '01' then '部门经费'  when '02' then '个人经费' else ''  end) xmsxmc,");
		sql.append("  x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,");
		sql.append("  x.sfqy, (case sfqy  when '0' then  '未启用' when '1' then '已启用'  else '' end) as sfqymc");
		sql.append("  from Cw_xmjbxxb x left join Cw_xmkzxxb c on c.xmbh = x.xmbh");
		sql.append("  left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z  on z.xmxxbh = x.xmbh");
		sql.append("  left join Cw_xmjjflbnew j  on j.xmxxbh = x.xmbh");
		sql.append(" where x.sszt = '"+sszt+"' ");
		if(Validate.noNull(guid)){
			sql.append(" and x.guid in ('"+guid+"') ");
		}
		return db.queryForList(sql.toString());
	}

}
