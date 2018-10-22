package com.googosoft.dao.tubiao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.tubiao.Zhcx;
import com.googosoft.util.Validate;

/**
 * 教职工财务校情分析
 * @author Administrator
 *
 */
@Repository("jzgcwxqDao")
public class JzgcwxqDao {
	@Resource(name="jdbcTemplate")
	public DBHelper dao;
	
	
	public List<Map<String, Object>> getJzggzzc(Zhcx obj){
	    String sql = " select t.yddm , sum(nvl(t.yfhj, 0)) gzzc from gx_jzzggz_fx t where t.nddm ='"+obj.getNd()+"' group by t.yddm order by to_number(t.yddm)  ";
	    
	    return this.dao.queryForList(sql);
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
		String rybh = LUser.getRybh();
		
		String sql = "select   '岗位工资' lb , GWGZ  as je from   	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"' " +
				"union select  '薪级工资' lb , XJGZ  as je from   	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '基础绩效' lb , JCJX  as je from   	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '奖励绩效' lb , JLJX1 as je from   	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '住房积金' lb , ZFJJ  as je from  	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '医疗保险' lb , YLBX  as je from 	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '代扣税' lb ,  DKS  as je from    	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '社保金' lb ,  SBJ  as je from  	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'" +
				"union select  '养老金' lb ,  YLJ  as je from   	cw_xzb where 1=1 AND rybh='"+rybh+"' and substr(GZYF,0,4)='"+ksnd+"'and substr(GZYF,6,2)='"+ksyd+"'";
		
		String sql2="select substr(GZYF,6,2) as yddm,SFHJ as sfgz from cw_xzb where substr(GZYF,0,4)='"+kssj2+"'and rybh='"+rybh+"'";
		Map<String, List> map = new HashMap();
		List list;
		list = dao.queryForList(sql);
		if(list.size()>0){
		    map.put("gzfb", list);
		}
		list = null;
		
		list = dao.queryForList(sql2);
		if(list.size()>0){
		    map.put("gzbh", list);
		}
		list = null;
		
		return map;
	}
	
}
