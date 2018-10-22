package com.googosoft.dao.kjhs.szjz;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;

@Repository("szjzDao")
public class SzjzDao extends BaseDao{

	
	/**
	 * 通过转入科目编号得到转出科目，通过转出科目到凭证录入明细表获得金额
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getJeByKmbh(String zjid,String nd,String pzrq,String sszt,String kjzd,String zrkmbh) {
		String sql="select kj.kmbh,kj.yefx,  nvl( a.jfje,0) as jfje, nvl(a.dfje,0) as dfje, nvl( km.kmzye,0) as kmzye "
				+ " from cw_kmyeb km " 
				+ " left join cw_kjkmszb kj on kj.kmbh = km.kmbh "
				+ " left join cw_jzmbmxb jz on jz.zckmbh = kj.guid "
				+ " left join ("
				+ " select kmbh,jdfx,to_char(sum(jfje)) as jfje,to_char(sum(dfje)) as dfje  from cw_pzlrmxb "
				+ " where pzbh in (select guid from cw_pzlrzb zb where to_char(zb.pzrq,'yyyy-mm-dd')=? and zb.sszt=?) "
				+ " group by kmbh,jdfx "
				+ " ) a on a.kmbh=km.kmbh "  
				+ " where jz.zjid = ? and jz.zrkmbh = ? and kj.sszt=? and kj.kjzd=?"
				+ " and km.nd=? and km.sszt=? and km.kjzd=? "; 
		List<Map<String,Object>> list = db.queryForList(sql,new Object[]{pzrq,sszt,zjid,zrkmbh,sszt,kjzd,nd,sszt,kjzd});
		return list;
	}
	/**
	 * 通过jzid获得zrkmbh
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getZrkmByid(String guid,String sszt) {
		String sql="select distinct  kj.kmbh as kmbh,jz.sszt,jz.zjid,jz.zrkmbh,kj.yefx from cw_jzmbmxb jz " + 
				"left join cw_kjkmszb kj on kj.guid = jz.zrkmbh where  jz.zjid=? and jz.sszt=?";
		return db.queryForList(sql,new Object[]{guid,sszt});
	}
	/**
	 * 获得结转模板明细信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getjzmbmxbyguid(String guid) {
		String sql="select guid,(select kmbh from cw_kjkmszb kj where kj.guid = jz.zrkmbh) zrkmbh,(select kmbh from cw_kjkmszb kj where kj.guid = jz.zckmbh) zckmbh from cw_jzmbmxb jz where zjid=?";
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 获得期末记帐
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> queryQmjz(String sszt) {
		String sql="select * from cw_qmjzb where sfjz='0' and kjqj = (select min(kjqj) from cw_qmjzb where sfjz='0') and sszt='"+sszt+"'";
		return db.queryForMap(sql);
	}
	/**
	 * 增加凭证录入明细
	 * @param dwbh
	 * @return
	 */
	public int doAddpzlrmx(Map map) {
		if("1".equals(map.get("jdfx"))) {
			//贷方增加
			String sql="insert into cw_pzlrmxb(guid,pzbh,zy,kmbh,jdfx,jfje,sfjz,czr,czrq,sszt,kjzd) values(?,?,?,?,'1',?,'1',?,sysdate,?,?)";
			db.addOrUpdateOrDelete(sql, new Object[]{map.get("guid"),map.get("pzbh"),map.get("zy"),map.get("kmbh"),map.get("jfje"),
					map.get("czr"),map.get("sszt"),map.get("kjzd")});
		}else {
			//借方增加
			String sql="insert into cw_pzlrmxb(guid,pzbh,zy,kmbh,jdfx,dfje,sfjz,czr,czrq,sszt,kjzd) values(?,?,?,?,'0',?,'1',?,sysdate,?,?)";
			db.addOrUpdateOrDelete(sql, new Object[]{map.get("guid"),map.get("pzbh"),map.get("zy"),map.get("kmbh"),map.get("dfje"),
					map.get("czr"),map.get("sszt"),map.get("kjzd")});
		}
		return 0;			
	}
	/**
	 * 增加凭证录入明细
	 * @param dwbh
	 * @return
	 */
	public int doAddpzlrmx1(Map map) {
		if("1".equals(map.get("jdfx"))) {
			//贷方增加
			String sql="insert into cw_pzlrmxb(guid,pzbh,zy,kmbh,jdfx,dfje,sfjz,czr,czrq,sszt,kjzd) values(?,?,?,?,'1',?,'1',?,sysdate,?,?)";
			db.addOrUpdateOrDelete(sql, new Object[]{map.get("guid"),map.get("pzbh"),map.get("zy"),map.get("kmbh"),map.get("dfje"),
					map.get("czr"),map.get("sszt"),map.get("kjzd")});
		}else {
			//借方增加
			String sql="insert into cw_pzlrmxb(guid,pzbh,zy,kmbh,jdfx,jfje,sfjz,czr,czrq,sszt,kjzd) values(?,?,?,?,'0',?,'1',?,sysdate,?,?)";
			db.addOrUpdateOrDelete(sql, new Object[]{map.get("guid"),map.get("pzbh"),map.get("zy"),map.get("kmbh"),map.get("jfje"),
					map.get("czr"),map.get("sszt"),map.get("kjzd")});
		}
		return 0;					
	}
	/**
	 * 增加凭证录入明细
	 * @param dwbh
	 * @return
	 */
	public int doAddpzlrmxbyjdfx(Map map) {
		if("1".equals(map.get("jdfx"))) {
			//贷方增加
			String sql="insert into cw_pzlrmxb(guid,pzbh,zy,kmbh,jdfx,dfje,sfjz,czr,czrq,sszt,kjzd) values(?,?,?,?,'1',?,'1',?,sysdate,?,?)";
			db.addOrUpdateOrDelete(sql, new Object[]{map.get("guid"),map.get("pzbh"),map.get("zy"),map.get("kmbh"),map.get("dfje"),
					map.get("czr"),map.get("sszt"),map.get("kjzd")});
		}else {
			//借方增加
			String sql="insert into cw_pzlrmxb(guid,pzbh,zy,kmbh,jdfx,jfje,sfjz,czr,czrq,sszt,kjzd) values(?,?,?,?,'0',?,'1',?,sysdate,?,?)";
			db.addOrUpdateOrDelete(sql, new Object[]{map.get("guid"),map.get("pzbh"),map.get("zy"),map.get("kmbh"),map.get("jfje"),
					map.get("czr"),map.get("sszt"),map.get("kjzd")});
		}
		return 0;					
	}
	/**
	 * 凭证录入主表增加数据
	 */
	public int doAddpzlrzb(Map map) {
		String sysTime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		String sql="insert into cw_pzlrzb(guid,pzbh,pzrq,zdr,pzzt,kjqj,pzly,sszt,fjzs,pznd,kjzd,PZZ,PZLXMC, pzscsj) values(?,?,to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,?,?,?,?)";
		int a = db.update(sql, new Object[] {map.get("guid"),map.get("pzbh"),map.get("pzrq"),map.get("zdr"),map.get("pzzt"),map.get("kjqj"),map.get("pzly"),map.get("sszt"),0,map.get("pznd"),map.get("kjzd"),map.get("pzz"),map.get("pzlxmc"),sysTime});
		return a;
		
	}
	/**
	 * 收支结转明细增加数据
	 */
	public int addszjzmx(Map map) {
		String sql="insert into cw_szjzmxb(guid,jzmb,zrkmbh,zckmbh,pzh,pzlx,pzrq,jznd,jzyf,mxguid,sszt,czr,zbguid) values(?,?,?,?,?,?,sysdate,?,?,?,?,?,?)";
		db.addOrUpdateOrDelete(sql, new Object[] {map.get("guid"),map.get("jzmb"),map.get("zrkmbh"),map.get("zckmbh"),map.get("pzh"),map.get("pzlx"),map.get("jznd"),map.get("jzyf"),map.get("mxguid"),map.get("sszt"),map.get("czr"),map.get("zbguid")});
		return 0;
		
	}
	/**
	 * 凭证录入主表增加金额
	 */
	public int addJe(BigDecimal jfjehj,BigDecimal dfjehj,String guid) {
		String sql="update cw_pzlrzb set jfjehj=?,dfjehj=? where guid=?";
		return db.update(sql,jfjehj,dfjehj,guid);
	}
	/**
	 * 凭证录入明细表sfjz 改为1
	 */
	public int updateSfjz(String kmbh) {
		String sql="update cw_pzlrmxb set sfjz='1' where kmbh=?";
		return db.update(sql,kmbh);
	}
	public int getjeinkmye(String kmbh) {
		String sql = "select kmzye from cw_kmyeb where kmbh = '"+kmbh+"'";
		Map map = db.queryForMap(sql);
		int kmye=0;
		String kmzye = (String) map.get("kmzye");
		if(kmzye==null||kmzye.length()==0) {
			kmzye="";
		}else {
			kmye = Integer.parseInt(kmzye);
		}
		return kmye;
	}
	/**
	 * 得到收支结转的数据
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getszjz(String sszt) {
		String sql="select t.guid,t.mbbh,t.mbmc, t.pzlxbh as pzlx,sszt,t.sfbhwjzpz,(select mc from gx_sys_dmb dm where dm.dm=t.zy and zl='zy') as zy,t.sszt,t.czr,t.czrq,t.pzlxbh,t.pzlxmc from cw_jzmbb t where sszt='"+sszt+"' order by mbbh";
		return db.queryForList(sql);
	}
	/**
	 * 删除主表数据
	 * @param dwbh
	 * @return
	 */
	public int deletezb(String guid) {
		String sql="delete cw_pzlrzb where guid=?";
		return db.update(sql,guid);
	}
	/**
	 * 删除明细表数据
	 * @param dwbh
	 * @return
	 */
	public int deletemx(String guid) {
		String sql="delete cw_pzlrmxb where pzbh=?";
		return db.update(sql,guid);
	}
	/**
	 * 删除明细表数据
	 * @param dwbh
	 * @return
	 */
	public int deleteszjz(String guid) {
		String sql="delete cw_szjzmxb where zbguid=?";
		return db.update(sql,guid);
	}
	/**
	 * 通过凭证号删除数据（cw_pzlrzb,cw_pzlrmxb,cw_szjzmxb）
	 */
	public boolean dodelete(String zbguid,String pzh,String jzyf) {
		String sql1="delete cw_pzlrmxb where pzbh =?";//删除cw_pzlrmxb
        Object[] obj1 = new Object[] {zbguid};
        
        String sql2="delete cw_pzlrzb where guid=?";//删除cw_pzlrzb
        Object[] obj2 = new Object[] {zbguid};
        
        String sql3 = "delete cw_szjzmxb where zbguid=?";//删除cw_szjzmxb
        Object[] obj3 = new Object[] {zbguid};
        String sql4 = "update cw_pzlrzb set pzbh=to_char(pzbh-1,'FM9990999') where pzbh>? and kjqj=?";
        Object[] obj4 = new Object[] {pzh,jzyf};
        
        ArrayList list = new ArrayList();
		list.add(sql1);
		list.add(sql2);
		list.add(sql3);
		list.add(sql4);
		return db.batchUpdate(list, obj1,obj2,obj3,obj4);
		//return true;
		
	}
	/**
	 * 批量删除
	 */
	public boolean dopldelete(PageData pd) {
		String guid = pd.getString("guid");
		String length = pd.getString("length");
		String pzh = pd.getString("pzh");
		String kjqj = pd.getString("kjqj");
		String sql1="delete cw_pzlrmxb where pzbh in ('"+guid+"')";//删除cw_pzlrmxb
        //Object[] obj1 = new Object[] {zbguid};
        
       
       // Object[] obj2 = new Object[] {zbguid};
        
        String sql3 = "delete cw_szjzmxb where zbguid in ('"+guid+"')";//删除cw_szjzmxb
        //Object[] obj3 = new Object[] {zbguid};
       // String sql4 = "update cw_pzlrzb set pzbh=pzbh-'"+length+"' where pzbh>'"+pzh+"' and kjqj='"+kjqj+"' ";
        
        String sql4 = "select kjqj,pzbh,guid from cw_pzlrzb where guid in ('"+guid+"')";   
        
        List list1 = db.queryForList(sql4);
        
        for(int i=0;i<list1.size();i++) {
        	Map map = (Map) list1.get(i);
        	String guid1 = (String) map.get("guid") ;
        	String kjqj1 = String.valueOf(map.get("kjqj")) ;
        	String pzbh1 = (String) map.get("pzbh");
        	String sql2="delete cw_pzlrzb where guid = '"+guid1+"'";//删除cw_pzlrzb
        	String sql5="update cw_pzlrzb set pzbh=to_char(pzbh-1,'FM9990999') where pzbh>'"+pzbh1+"' and kjqj='"+kjqj1+"'";
        	ArrayList list3 = new ArrayList<>();
        	list3.add(sql2);
        	list3.add(sql5);
        	
        	db.batchUpdate(list3);
        }
        
        
        ArrayList list = new ArrayList();
		list.add(sql1);
		//list.add(sql2);
		list.add(sql3);
		
		return db.batchUpdate(list);
		//return true;
		
	}
	/**
	 * 获得凭证录入状态
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getzdlrpz() {
		String sql="select nvl(zdlrpz,'0') as pzlrpz from cw_zdscpz";
		return db.queryForMap(sql);
	}

}
