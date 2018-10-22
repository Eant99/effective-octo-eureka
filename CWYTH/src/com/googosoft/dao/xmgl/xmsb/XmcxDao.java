package com.googosoft.dao.xmgl.xmsb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

 @Repository("xmcxDao")
public class XmcxDao extends BaseDao{
	 
	 /**
		 * 项目申报主表的详细信息
		 * @param guid
		 * @param ywid
		 * @return
		 */
		public Map getMapXmsbByGuid(String guid){
			String sql = "select t.guid,t.xmpm,t.xmlx,t.xmbh,t.xmmc,(select '('||zybh||')'||zymc from cw_zyxxb a where a.zybh=t.fwzy) as fwzy,"
					+ "(select '('||rybh||')'||xm from gx_sys_ryb r where r.guid=t.csr) as csr,to_char(csrq,'yyyy-mm-dd') as csrq,"
					+ "(select '('||rybh||')'||xm from gx_sys_ryb r where r.guid=t.fsr) as fsr,to_char(fsrq,'yyyy-mm-dd') as fsrq,"
					+ "t.fwxk,to_char(t.jhzxsj,'yyyy-mm-dd') as jhzxsj,to_char(t.jhjssj,'yyyy-mm-dd') as jhjssj,to_char(t.ysje,'FM999999999999.00') as ysje,(select '('||dwbh||')'||mc from gx_sys_dwb b where b.dwbh=t.sbdw) as sbdw,"
					+ "(select '('||rybh||')'||xm from gx_sys_ryb c where c.rybh=t.sbr) as sbr,to_char(t.sbrq,'yyyy-mm-dd') as sbrq,t.sfsndcxlzxm,t.yjmbxy,t.xmjszynr,"
					+ "t.xmzyysgc,t.xmsszycs, '('||x.xmlxbh||')'||x.xmlxmc xmlxmc from cw_xmsbxxb t left join cw_xmlxb x on x.guid=t.xmlx where t.guid=?";
			Map map = new HashMap<String,Object>();
			map = db.queryForMap(sql, new Object[]{guid});
			return map;
		}

	public List PowerModels(String loginrybh) {
		// TODO Auto-generated method stub
		return null;
	}

	public List GetModels(String dwbh) {
		// TODO Auto-generated method stub
		return null;
	}

	public List GetDwModels(String sjdw) {
		// TODO Auto-generated method stub
		return null;
	}

}
