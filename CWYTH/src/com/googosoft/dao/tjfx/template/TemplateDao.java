package com.googosoft.dao.tjfx.template;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.tjfx.Zhcx;
import com.googosoft.util.Validate;

@Repository("templateDao")
public class TemplateDao {
	@Resource(name="jdbcTemplate")
	public DBHelper dao;
	
	/**
	 * 获取统计分析信息
	 * @return
	 * @throws Exception 
	 */
	public Map getTemplateList(Zhcx obj){
		Map<String, List> map = new HashMap(); 
		List list;
		String sql = "";
		if(Validate.isNull(obj.getRqqj1())){
			sql = " select rybh,count(rybh) cnt from zc_sys_oplog where czrq <= to_date(?,'yyyy-mm-dd') group by rybh order by rybh ";
			list = dao.queryForList(sql,new Object[]{
					obj.getRqqj2()
			});
		}
		else{
			sql = " select rybh,count(rybh) cnt from zc_sys_oplog where czrq between to_date(?,'yyyy-mm-dd') and to_date(?,'yyyy-mm-dd') + 1 group by rybh order by rybh ";
			list = dao.queryForList(sql,new Object[]{
					obj.getRqqj1(),obj.getRqqj2()
			});
		}
		if(list.size() > 0){
			map.put("syr", list);
		}
		
		list = dao.queryForList(" select czrq,count(czrq) cnt from zc_sys_oplog group by czrq order by czrq ");
		if(list.size() > 0){
			map.put("czrq", list);
		}
		
		list = dao.queryForList(" select nvl(djlx,'其他') djlx,count(djlx) cnt from zc_sys_oplog group by djlx order by djlx ");
		if(list.size() > 0){
			map.put("djlx", list);
		}
		
		list = dao.queryForList(" select czjq,count(czjq) cnt from zc_sys_oplog group by czjq order by czjq ");
		if(list.size() > 0){
			map.put("ip", list);
		}
		
		list = dao.queryForList(" select czrq from (select to_char(czrq,'yyyy-mm-dd') czrq,rybh from zc_sys_oplog) group by czrq order by czrq ");
		if(list.size() > 0){
			map.put("ryip", list);
		}
		
		list = dao.queryForList(" select czrq,rybh,count(rybh) cnt from (select to_char(czrq,'yyyy-mm-dd') czrq,rybh from zc_sys_oplog) group by czrq,rybh order by czrq,rybh ");
		if(list.size() > 0){
			map.put("ryip_ry", list);
		}
		
		list = dao.queryForList(" select czrq,czjq,count(czjq) cnt from (select to_char(czrq,'yyyy-mm-dd') czrq,czjq from zc_sys_oplog) group by czrq,czjq order by czrq,czjq ");
		if(list.size() > 0){
			map.put("ryip_ip", list);
		}
		
		list = dao.queryForList(" select (select mc from gx_sys_dmb d where d.zl='dqdm' and d.dm = t.zsdqdm) as dq,count(zsdqdm) as cnt from gx_zslqfxb t group by t.zsdqdm ");
		if(list.size() > 0){
			map.put("qyfb", list);
		}
		
		return map;
	}
}
