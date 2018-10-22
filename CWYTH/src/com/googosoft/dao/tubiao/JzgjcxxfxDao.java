package com.googosoft.dao.tubiao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.tubiao.Zhcx;
import com.googosoft.util.Const;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 教职工基础信息分析---数据连接层
 * @author RC  2016-10-28
 *
 */
@Repository("jzgjcxxfxDao")
public class JzgjcxxfxDao extends BaseDao{
	@Resource(name="jdbcTemplate")
	public DBHelper dao;
	
  /**
     * 
     * @Title: getJzg_gk_hz 
     * @Description: 获取教职工概况汇总
     * @date 2017-5-22 下午4:47:17
     * @return String 
     *
     */
	public Map getJzg_gk_hz(Zhcx obj){
	    String sql = " select count(1) as zrs, sum(decode(x.XBM,'"+Constant.BOY+"',1,0)) as boys,  sum(decode(x.XBM,'"+Constant.GIRL+"',1,0)) as girls  from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj);
	    
	    return this.db.queryForMap(sql);
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_dwrs 
	 * @Description: 获取教职工各系人数情况
	 * @date 2017-5-22 下午5:20:54
	 * @return List 
	 *
	 */
	public List<Map<String, Object>> getJzg_gk_dwrs(Zhcx obj){
	    String sql = " select (select mc from gx_sys_dwb where dwbh=x.szdw)as szx, count(1) as zrs, sum(decode(x.XBM,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.XBM,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.szdw order by count(1) desc ";
	    
	    return this.db.queryForList(sql);
	}
	
	/**
	 * 
	 * @Title: getJzg_gk_ssb 
	 * @Description: 获取当前师生比
	 * @date 2017-5-22 下午7:25:10
	 * @return List<Map<String,Object>> 
	 *
	 */
	public List<Map<String, Object>> getJzg_gk_ssb(Zhcx obj){
	    StringBuffer sql = new StringBuffer();
	    sql.append(" select a.xssl as xsrs, b.jssl as jsrs,round(a.xssl/b.jssl, 1) as ssb, a.szx from  ");
	    sql.append(" ( select count(1) as xssl, (select mc from gx_sys_dwb where dwbh=s.szx) as szx from cw_xsxxb s ");
//	    sql.append( this.getJzg_gk_ssb_dqzx(obj) );  //当前在校学生  上半年  sznj+xz >= sysdate   下半年  sznj+xz >= sysdate+1year
	    sql.append(" group by s.szx ) a ");
	    sql.append(" left join ( select count(1) as jssl, (select mc from gx_sys_dwb where dwbh=x.szdw)as szx from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.szdw ) b on a.szx = b.szx ");
	    
	    return this.db.queryForList(sql.toString());
	}
	
	/*
	 * @Title: getJzg_gk_ssb_dqzx 
     * @Description: 获取当前在校对比时间
	 */
//	public String getJzg_gk_ssb_dqzx(Zhcx obj){
//	    StringBuffer str = new StringBuffer();
//	    if(Validate.noNull(obj.getRq())){
//	        int year = Integer.parseInt(obj.getRq().substring(0,4));
//	        int mon = Integer.parseInt(obj.getRq().substring(5,7));
//	        if(mon<9 && mon >=3){
//	           year -= 1;
//	        }
//	        str.append(" and to_number(s.sznj+s.xz) >= "+year);
//	    }
//	    
//	    return str.toString();
//	}
	
	/**
	 * 
	 * @Title: getJzg_gk_zzmm 
	 * @Description: 获取政治面貌比例信息
	 * @date 2017-5-22 下午7:42:42
	 * @return List<Map<String,Object>> 
	 *
	 */
	public List<Map<String, Object>> getJzg_gk_zzmm(Zhcx obj){
        String sql = " select (select mc from gx_sys_dmb where zl='109' and dm=x.zzmmm) as zzmm, count(1) as zrs, sum(decode(x.xbm,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.xbm,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.zzmmm x.zzmmm ";
        
        return this.db.queryForList(sql);
    }
	
	/**
	 * 
	 * @Title: getJzg_gk_whcd 
	 * @Description: 获取文化程度比例信息
	 * @date 2017-5-22 下午7:43:22
	 * @return List<Map<String,Object>> 
	 *
	 */
	public List<Map<String, Object>> getJzg_gk_whcd(Zhcx obj){
        String sql = " select (select mc from gx_sys_dmb where zl='28' and dm=x.whcd)as whcd, count(1) as zrs, sum(decode(x.xbm,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.xb,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.whcd order by x.whcd desc ";
        
        return this.db.queryForList(sql);
    }
	
	/**
	 * 
	 * @Title: getJzg_gk_bz 
	 * @Description: 获取编制比例信息
	 * @date 2017-5-22 下午7:44:02
	 * @return List<Map<String,Object>> 
	 *
	 */
	public List<Map<String, Object>> getJzg_gk_bz(Zhcx obj){
//        String sql = " select sum(decode(x.sfzb,'"+Constant.SF_YES+"',1,0)) as zbrs, sum(decode(x.sfzb,'"+Constant.SF_NO+"',1,0)) as bzbrs  from gx_jzgxx_fx x where 1=1 "+this.getStrWhere(obj);
        String sql = "select case x.sfzb when '"+Constant.SF_YES+"' then '在编' else '不在编' end as bzlb, count(1) as zrs, sum(decode(x.xb,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.xb,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj) +" group by case x.sfzb when '"+Constant.SF_YES+"' then '在编' else '不在编' end ";
        return this.db.queryForList(sql);
    }
	
	/**
	 * 
	 * @Title: getJzg_gk_zc 
	 * @Description: 获取职称比例信息
	 * @date 2017-5-22 下午7:45:00
	 * @return List<Map<String,Object>> 
	 *
	 */
	public List<Map<String, Object>> getJzg_gk_zc(Zhcx obj){
        String sql = " select (select mc from gx_sys_dmb where zl='28' and dm=x.zc) as zcmc, count(1) as zrs from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.zc order by x.zc";
        
        return this.db.queryForList(sql);
    }
	
	/**
	 * 
	 * @Title: getJzg_gk_mz 
	 * @Description: 获取民族比例信息
	 * @date 2017-5-22 下午7:45:27
	 * @return List<Map<String,Object>> 
	 *
	 */
   public List<Map<String, Object>> getJzg_gk_mz(Zhcx obj){
        String sql = " select (select mc from gx_sys_dmb where zl='105' and dm=x.mzm) as mz, count(1) as zrs, sum(decode(x.xbm,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.xbm,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.mzm ";
        
        return this.db.queryForList(sql);
    }
	
   /**
    * 
    * @Title: getJzg_gk_jg 
    * @Description: 获取教职工籍贯信息
    * @date 2017-5-23 下午1:17:58
    * @return List<Map<String,Object>> 
    *
    */
   public List<Map<String, Object>> getJzg_gk_jg(Zhcx obj){
       String sql = " select x.jg as jgs, count(1) as zrs, sum(decode(x.xbm,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.xbm,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.jg ";
       
       return this.db.queryForList(sql);
   }
   
   /**
    * 
    * @Title: getJzg_gk_gj 
    * @Description: 获取教职工国籍信息
    * @date 2017-5-23 下午1:18:41
    * @return List<Map<String,Object>> 
    *
    */
   public List<Map<String, Object>> getJzg_gk_gj(Zhcx obj){
       String sql = " select (select mc from gx_sys_dmb where zl='106' and dm=x.gjdqm) as gj, count(1) as zrs, sum(decode(x.xbm,'"+Constant.BOY+"',1,0)) as boys, sum(decode(x.xbm,'"+Constant.GIRL+"',1,0)) as girls from cw_jzgxxb x where 1=1 "+this.getStrWhere(obj)+" group by x.gjdqm ";
       
       return this.db.queryForList(sql);
   }
   
   /**
    * 
    * @Title: getJzg_gk_nl 
    * @Description: 获取教职工年龄信息
    * @date 2017-5-23 下午2:28:38
    * @return List<Map<String,Object>> 
    *
    */
   public List<Map<String, Object>> getJzg_gk_nl(Zhcx obj){
       StringBuffer sql = new StringBuffer();
       sql.append(" select case when nl >= 60 then '60岁以上' when nl >55 then '56~60岁' when nl >50 then '51~55岁' when nl >45 then '46~50岁' when nl >40 then '41~45岁' when nl >35 then '36~40岁' else '35岁以下' end nld, ");
       sql.append(" count(1) zrs, sum(decode(xb,'"+Constant.XB_BOY+"',1,0)) as boys, sum(decode(xb,'"+Constant.XB_GIRL+"',1,0)) as girls ");
       sql.append(" from ( select x.xb, to_char(sysdate,'yyyy')-substr(x.csrq,0,4) as nl  from gx_jzgxx_fx x where 1=1 "+this.getStrWhere(obj)+" ) a  ");
       sql.append(" group by case when nl >= 60 then '60岁以上' when nl >55 then '56~60岁' when nl >50 then '51~55岁' when nl >45 then '46~50岁' when nl >40 then '41~45岁' when nl >35 then '36~40岁' else '35岁以下' end ");
       sql.append(" order by nld ");
       
       return this.db.queryForList(sql.toString());
   }
   
   /**
    * 
    * @Title: getJzg_jgmx 
    * @Description: 获取教职工市级信息
    * @date 2017-6-1 下午4:38:01
    * @param pd
    * @return List<Map<String,Object>> 
    *
    */
   public List<Map<String, Object>> getJzg_jgmx(Zhcx obj, PageData pd){
       StringBuffer sql = new StringBuffer();
       sql.append(" select x.jgms, count(1) as zrs, sum(decode(x.xb,'"+Constant.XB_BOY+"',1,0)) as boys, sum(decode(x.xb,'"+Constant.XB_GIRL+"',1,0)) as girls from gx_jzgxx_fx x where x.jgs='"+pd.getString("jgs")+"' "+this.getStrWhere(obj)+" group by x.jgms ");
       
       return this.db.queryForList(sql.toString());
   }
   
   
   public String getStrWhere(Zhcx obj){
       StringBuffer strWhere = new StringBuffer();
       if(Validate.noNull(obj.getRq())){
           strWhere.append(" and x.LXSJ <= substr('"+obj.getRq()+"',0,7) and (x.lzrq is null or to_date(x.lzrq,'yyyy-mm-dd') > to_date('"+obj.getRq()+"','yyyy-mm-dd')) ");
       }
       
       return strWhere.toString();
   }
   
   
   
   /************************************************新旧方法分界线*****************************************************/
   
   
	/**
	 * 获取教职工基础信息分析数据
	 * @return
	 */
	public Map getJzgjcxxfx(Zhcx obj){
		Map map = new HashMap();
		List parList;
		List list;
		String proName = "pro_jzgjcxxfx";
		//当前人数分析
		String sql = "select count(t.jgh) as zrs,count(t1.jgh) as boys,count(t2.jgh) as girls from gx_jzgjbxxb t left join gx_jzgjbxxb t1 on t1.jgh=t.jgh and t1.xbm='1' left join gx_jzgjbxxb t2 on t2.jgh=t.jgh and t2.xbm='2'";
		Map map1 = dao.queryForMap(sql);
		if(Validate.noNull(map1)){
			map.put("dq", map1);
		}
		map1 = null;
		
		String sql1 = "select yxmc,sum(nvl(jzgrs,0)) as jzgrs,sum(nvl(xsrs,0)) as xsrs,case nvl(sum(nvl(jzgrs,0)),0) when 0 then 0 else  trunc((sum(nvl(jzgrs, 0)) / nvl(sum(nvl(xsrs, 0)), 0) * 100),2) end as ssb from (";
		sql1 += "select d.mc as yxmc,count(t.jgh) as jzgrs,0 as xsrs from gx_jzgjbxxb t left join gx_dwb d on d.dwbh=t.dwh group by d.mc ";
		sql1 += " union all select d.mc as yxmc,0 as jzgrs,count(t.xh) as xsrs from gx_xjjbsjb t left join gx_dwb d on d.dwbh=t.yxsh group by d.mc ";
		sql1 += " ) group by yxmc ";
		try{
			//单位
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("dwmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("dw", list);
			}
			parList = null;
			list = null;
			
			//文化程度
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("whcdmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("whcd", list);
			}
			parList = null;
			list = null;
			
			//编制类别
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("bzlbmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("bzlb", list);
			}
			parList = null;
			list = null;
			
			//职称
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("zcmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("zc", list);
			}
			parList = null;
			list = null;
			
			//政治面貌
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("zzmmmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("zzmm", list);
			}
			parList = null;
			list = null;

			//民族
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("mzmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("mz", list);
			}
			parList = null;
			list = null;

			//国家
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("gjmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("gj", list);
			}
			parList = null;
			list = null;

			//年龄，获取最大年龄和最小年龄，在service层对年龄进行分段
			String sql2 = "select min(to_number(to_char(sysdate,'yyyy'))-to_number(to_char(t.csrq,'yyyy'))) as minage,max(to_number(to_char(sysdate,'yyyy'))-to_number(to_char(t.csrq,'yyyy'))) as maxage from gx_jzgjbxxb t";
			map1 = dao.queryForMap(sql2);
			if(Validate.noNull(map1)){
				map.put("age", map1);
			}
			map1 = null;

			//籍贯
			parList = new ArrayList();
			parList.add(obj.getNdqj1());
			parList.add(obj.getNdqj2());
			parList.add("jgmc");
			list = dao.queryForListByProcedure(proName,parList,"?,?,?,?");
			if(list.size() > 0){
				map.put("jg", list);
			}
			parList = null;
			list = null;
			
			list = dao.queryForList(sql1);
			if(list.size() > 0){
				map.put("ssb", list);
			}
			list = null;
			
		}
		catch(SQLException e){
			e.printStackTrace();
		}
		return map;
	}
	
	/**
	 * 获取各年龄段的教职工总人数、男性教职工人数、女性教职工人数
	 * @param minage  年龄段开始
	 * @param maxage  年龄段结束
	 * @return
	 */
	public Map getAges(int minage, int maxage){
		String ksrq = (Integer.parseInt(DateUtil.getYear())-maxage)+"";//开始出生日期
		String jsrq = (Integer.parseInt(DateUtil.getYear())-minage)+"";//结束出生日期
		String sql = "select count(t.jgh) as zrs,count(t1.jgh) as boys,count(t2.jgh) as girls from gx_jzgjbxxb t ";
		sql += " left join gx_jzgjbxxb t1 on t1.jgh=t.jgh and t1.xbm='"+Constant.BOY+"' and to_char(t1.csrq,'yyyy') between '"+ksrq+"' and '"+jsrq+"' ";
		sql += " left join gx_jzgjbxxb t2 on t2.jgh=t.jgh and t2.xbm='"+Constant.GIRL+"' and to_char(t2.csrq,'yyyy') between '"+ksrq+"' and '"+jsrq+"' ";
		sql += " where to_char(t.csrq,'yyyy') between '"+ksrq+"' and '"+jsrq+"' ";
		Map map = dao.queryForMap(sql);
		return map;
	}
}
