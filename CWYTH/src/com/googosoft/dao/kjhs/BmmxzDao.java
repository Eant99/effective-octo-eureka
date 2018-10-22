package com.googosoft.dao.kjhs;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("bmmxzDao")
public class BmmxzDao extends BaseDao{

	public List<Map<String, Object>> getBmMxzList(String treebmbh,String pznd,String startMonth,String endMonth,String pzzt,String kmbh,String bmbh,String xmbh,String jfjel,String jfjeh,String zy) {
		
		StringBuffer sqltext = new StringBuffer();
		sqltext.append(" select guid,k.bmbh,");
		sqltext.append("  '('||d.dwbh||')'||d.mc bmmc,");
		sqltext.append(" to_char(z.pzrq,'yyyy-MM-dd')pzrq,");
		sqltext.append(" z.pzlxmc,z.pzbh,z.pzbh as pzz,k.zy,");
		sqltext.append(" decode(nvl(k.jfje,0.00),0.00,'',to_char(round(k.jfje,2),'fm9999999999999.00'))jfje,");
		sqltext.append(" decode(nvl(k.dfje,0.00),0.00,'',to_char(round(k.dfje,2),'fm9999999999999.00'))dfje,");
		sqltext.append(" decode(k.jdfx,'0','借','1','贷','')jdfx,z.kjqj,z.pznd,");
		sqltext.append(" j.kmmc jjfl");
		sqltext.append(" from cw_pzlrmxb k");
		sqltext.append(" left join cw_pzlrzb z on z.guid=k.pzbh");
		sqltext.append(" left join gx_sys_dwb d on d.dwbh=k.bmbh");
		sqltext.append(" left join cw_jjkmb j on j.kmbh=k.jjfl");
		sqltext.append("  where k.bmbh is not null ");
		if(Validate.noNull(treebmbh)){
			sqltext.append(" and k.bmbh like '"+treebmbh+"%'");
		}
		if(Validate.noNull(pzzt)){
			sqltext.append(" and z.pzzt in ("+pzzt+")");
		}
		if(Validate.noNull(startMonth)&&Validate.noNull(endMonth)){
			sqltext.append(" and z.pznd='"+pznd+"' and z.kjqj >= "+startMonth+" and z.kjqj <="+endMonth);
		}
		if(Validate.noNull(kmbh)){
			sqltext.append(" and k.kmbh='"+kmbh+"'");
		}
		if(Validate.noNull(bmbh)){
			sqltext.append(" and k.bmbh='"+bmbh+"'");
		}
		if(Validate.noNull(xmbh)){
			sqltext.append(" and k.xmbh='"+xmbh+"'");
		}
		if(Validate.noNull(jfjel)){
			sqltext.append(" and k.jfje>="+jfjel);
		}
		if(Validate.noNull(jfjeh)){
			sqltext.append(" and k.jfje<="+jfjeh);
		}
		if(Validate.noNull(zy)){
			sqltext.append(" and k.zy like '%"+zy+"%'");
		}
		sqltext.append(" order by pzrq desc");
		System.err.println("sql====="+sqltext.toString());
		return db.queryForList(sqltext.toString());
	}
}
