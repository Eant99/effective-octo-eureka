package com.googosoft.dao.tubiao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.tubiao.Zhcx;

@Repository("grgzqkDao")
public class GrgzqkDao extends BaseDao {
	
	//===
	public List<Map<String, Object>> getJzggzzc(Zhcx obj){
	    String sql = " select t.yddm , sum(nvl(t.yfhj, 0)) gzzc from gx_jzzggz_fx t where t.nddm ='"+obj.getNd()+"' group by t.yddm order by to_number(t.yddm)  ";
	    
	    return this.db.queryForList(sql);
	}
	
	/**
	 * 获取教职工应发工资分布情况
	 * @param obj
	 * @return
	 */
	public Map getJzggzfb(Zhcx obj){
		String kssj2 =obj.getNd();
		String ksnd =obj.getNdqj1().substring(0, 4);
		String ksyd = obj.getNdqj1().substring(5, 7);
		String xm = obj.getXm();
		
		String sql = "select  '岗位工资' lb , gwgz as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"' " +
				"union select  '薪级工资' lb , xjgz as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '卫生费' lb , wsf as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '一次性支付' lb , ycxzf as je from gx_jzzggz_fx  where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '独生子女补贴' lb , dsznbt as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '误餐补贴三' lb , wcbt3 as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '赛罕区津贴' lb , shjt as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '一次性补贴' lb , ycxbt as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'" +
				"union select  '基础性绩效' lb , jcxjx as je from gx_jzzggz_fx where 1=1 AND xm='"+xm+"' and nddm='"+ksnd+"'and yddm='"+ksyd+"'";
		
		String sql2="select yddm,sfgz from gx_jzzggz_fx where nddm='"+kssj2+"'and xm='"+xm+"'";
		Map<String, List> map = new HashMap();
		List list;
		list = db.queryForList(sql);
		if(list.size()>0){
		    map.put("gzfb", list);
		}
		list = null;
		
		list = db.queryForList(sql2);
		if(list.size()>0){
		    map.put("gzbh", list);
		}
		list = null;
		
		return map;
	}
	//===

}
