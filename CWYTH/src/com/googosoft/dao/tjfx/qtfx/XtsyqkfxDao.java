package com.googosoft.dao.tjfx.qtfx;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.OplogFlag;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.tjfx.Zhcx;
import com.googosoft.util.Validate;

@Repository("xtsyqkfxDao")
public class XtsyqkfxDao {
	@Resource(name="jdbcTemplate")
	public DBHelper dao;
	
	/**
	 * 获取统计分析信息
	 * @return
	 * @throws Exception 
	 */
	public Map getXtsyqkfxList(Zhcx obj){
		Map<String, List> map = new HashMap(); 
		List list;
		String where = " and to_date(to_char(czrq,'yyyy-mm-dd'),'yyyy-mm-dd') <= to_date('" + obj.getRqqj2() + "','yyyy-mm-dd') ";
		if(Validate.noNull(obj.getRqqj1())){
			where += " and to_date(to_char(czrq,'yyyy-mm-dd'),'yyyy-mm-dd') >= to_date('" + obj.getRqqj1() + "','yyyy-mm-dd') ";
		}
		
		String sql = " select (select '('||rygh||')'||xm from gx_sys_ryb r where r.rybh = l.rybh) xm,cnt from (select rybh,count(rybh) cnt from zc_sys_oplog where 1 = 1 " + where + " group by rybh order by cnt desc) l where rownum <= 10 order by cnt desc ";
		list = dao.queryForList(sql);
		if(list.size() > 0){
			map.put("syr", list);
		}

		sql = " select czjq,cnt from (select czjq,count(czjq) cnt from zc_sys_oplog where 1 = 1 " + where + " group by czjq order by cnt desc) where rownum <= 10 order by cnt desc ";
		list = dao.queryForList(sql.toString());
		if(list.size() > 0){
			map.put("ip", list);
		}
		
		sql = " select d.mc djlx,nvl(l.cnt,0) cnt from (select djlx,count(djlx) cnt from zc_sys_oplog where nvl(djlx,'$') <> '$' " + where + " group by djlx order by djlx) l left join (select mc,dm from zc_oplog_dmb where bz = '1') d on l.djlx = d.dm order by d.dm ";
		list = dao.queryForList(sql);
		if(list.size() > 0){
			map.put("djlx", list);
		}
		
		sql = " select " + OplogFlag.CasLink + ",count(czlx) cnt from zc_sys_oplog where nvl(czlx,'$') <> '$' " + where + " group by czlx order by czlx ";
		list = dao.queryForList(sql);
		if(list.size() > 0){
			map.put("czlx", list);
		}
		
		sql = " select czrq,count(czrq) cnt from (select to_char(czrq,'hh24:mi:ss') czrq from zc_sys_oplog where 1 = 1 " + where + ") group by czrq order by czrq ";
		list = dao.queryForList(sql);
		if(list.size() > 0){
			map.put("sjd", list);
		}
		
		return map;
	}
}
