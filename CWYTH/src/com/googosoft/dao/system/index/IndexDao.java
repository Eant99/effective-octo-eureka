package com.googosoft.dao.system.index;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;
@Repository("indexDao")
public class IndexDao extends BaseDao{
	/**
	 * 首页搜索
	 * @param keyword
	 * @return
	 */
	public int getAssetsNum(String keyword){
		String sql="select count(*) from zc_zjb where ztbz='99' and xz not in(select dm from gx_sys_dmb where zl='41') and yqmc like '%"+keyword+"%'";
			return db.queryForObject(sql,Integer.class);
	}
	
	public String getBzdm() {
		String sql = "SELECT BH FROM (SELECT BH FROM ZC_BZML ORDER BY ZT,BH) WHERE ROWNUM = 1";
		return db.queryForSingleValue(sql);
	}

	/**
	 * 
	 * 获得公告通知表
	 */
	public List<Map<String, Object>> getTzgg(String js) {
		String sql="";
		if(js.equals("xz")){
			 sql = " select W.GID,W.TITLE,TO_CHAR(W.FBSJ,'yyyy-MM-dd') AS FBSJ,(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=W.FBR) AS FBR from ZC_SYS_XTXX W where rownum<=8 and sfzs ='1' order by FBSJ desc";
		}else{
			 sql = " select W.GID,W.TITLE,TO_CHAR(W.FBSJ,'yyyy-MM-dd') AS FBSJ,(SELECT '('||RYGH||')'||XM FROM GX_SYS_RYB B WHERE B.RYBH=W.FBR) AS FBR from ZC_SYS_XTXX W where rownum<=5 and sfzs ='1' order by FBSJ desc";
		}
		
	    return db.queryForList(sql);
			
		}
	/**
	 * 
	 * 获得当前登陆人操作权限
	 */
	public List<Map<String, Object>> getCzqx(String rybh) {
		
		
		String sql = "select distinct substr(mkbh,0,2) as mkbh,yh  from zc_sys_czqxb t where rybh='"+rybh+"' order by to_number(yh)  ";
	    return db.queryForList(sql);
			
		}
	/**
	 * 获取被授权的首页办事大厅模块数量
	 * @param rybh
	 * @param filter 过滤掉的模块编号，如：“'13','14'”
	 * @return
	 */
	public List<Map<String, Object>> getBsdtMkList(String rybh,String filter){
//      		String sql = " select distinct substr(t.mkbh,0,2) as yjmkbh,t.mkbh ,t.sfwj,t.url,t.mkmc ,t.sytz,t.xh from "
//				+ "zc_sys_mkb t where length(t.mkbh)=2 "+
//				 " and substr(t.mkbh,1,2) in (select distinct substr(mkbh,0,2) as mkbh from zc_sys_czqxb where rybh = '"+rybh+"') and t.mkbh not in(03,56,57,55,10)";
//		String strWhere = Validate.isNullOrEmpty(filter) ? "":" and t.mkbh not in ('"+filter+"') ";
//		String order = " order by t.xh asc";
//		sql = sql + strWhere + order;
		StringBuffer sql = new StringBuffer();
		if("000000".equals(rybh)) {
			sql.append(" select * from(");
			sql.append(" select distinct substr(t.mkbh, 0, 2) as yjmkbh,");
			sql.append(" t.mkbh,t.sfwj,t.url,t.mkmc,t.sytz,t.xh,t.qxbz");
			sql.append(" from zc_sys_mkb t");
			sql.append(" where length(t.mkbh) = 2");
			sql.append(" union");
			sql.append(" select distinct substr(t.mkbh, 0, 2) as yjmkbh,");
			sql.append(" t.mkbh,t.sfwj,t.url,t.mkmc,t.sytz,t.xh,t.qxbz");
			sql.append(" from zc_sys_mkb t");
			sql.append(" where length(t.mkbh) = 2");
			sql.append(" union");
			sql.append(" select distinct substr(t.mkbh, 0, 2) as yjmkbh,");
			sql.append(" t.mkbh,t.sfwj,t.url,t.mkmc,t.sytz,t.xh,t.qxbz");
			sql.append(" from zc_sys_mkb t");
			sql.append(" where length(t.mkbh) = 2");
			sql.append(")a where a.mkbh not in(03,56,57,55,10) and a.qxbz='1'");
			if(Validate.noNull(filter)){
				sql.append(" and a.mkbh not in('"+filter+"')");
			}
			sql.append(" order by a.xh asc");
		}else {
			sql.append(" select * from(");
			sql.append(" select distinct substr(t.mkbh, 0, 2) as yjmkbh,");
			sql.append(" t.mkbh,t.sfwj,t.url,t.mkmc,t.sytz,t.xh,t.qxbz");
			sql.append(" from zc_sys_mkb t");
			sql.append(" where length(t.mkbh) = 2");
			sql.append(" and substr(t.mkbh, 1, 2) in (select distinct substr(mkbh, 0, 2) as mkbh from zc_sys_czqxb where rybh = '"+rybh+"')");
			sql.append(" union");
			sql.append(" select distinct substr(t.mkbh, 0, 2) as yjmkbh,");
			sql.append(" t.mkbh,t.sfwj,t.url,t.mkmc,t.sytz,t.xh,t.qxbz");
			sql.append(" from zc_sys_mkb t");
			sql.append(" where length(t.mkbh) = 2");
			sql.append(" and substr(t.mkbh, 1, 2) in (select distinct substr(mkbh, 0, 2) as mkbh from zc_sys_jsqxb j where exists (select 1 from zc_sys_jsryb s where s.jsbh=j.jsbh and s.rybh='"+rybh+"'))");
			sql.append(" union");
			sql.append(" select distinct substr(t.mkbh, 0, 2) as yjmkbh,");
			sql.append(" t.mkbh,t.sfwj,t.url,t.mkmc,t.sytz,t.xh,t.qxbz");
			sql.append(" from zc_sys_mkb t");
			sql.append(" where length(t.mkbh) = 2");
			sql.append(" and substr(t.mkbh, 1, 2) in (select distinct substr(mkbh, 0, 2) as mkbh from zc_sys_jsqxb j where j.jsbh='00')");
			sql.append(")a where a.mkbh not in(03,56,57,55,10) and a.qxbz='1'");
			if(Validate.noNull(filter)){
				sql.append(" and a.mkbh not in('"+filter+"')");
			}
			sql.append(" order by a.xh asc");
		}
	    return db.queryForList(sql.toString());	
	}
	/**
	 * 
	 * 获得今日报销
	 */
	public Map getjrbx() {
	    Map map =new HashMap();
	    Map gwmap;
	    Map clmap;
	    Map rcmap;
		String sql = "select nvl(sum(T.bxje),0) as BXZJE,count(1) as sjs from Cw_gwjdfbxzb t where t.shzt = '8' and to_char(t.CZRQ, 'yyyy-mm-dd') =to_char(sysdate, 'yyyy-mm-dd')";
		String sql1 = "select nvl(sum(m.bxzje),0) as BXZJE,count(1) as sjs from Cw_clfbxzb m where m.shzt = '8' and m.czrq = to_char(sysdate, 'yyyy-mm-dd')";
		String sql2 = "select nvl(sum(n.bxzje),0) as BXZJE,count(1) as sjs from Cw_rcbxzb n where n.shzt = '8' and to_char(n.CZRQ, 'yyyy-mm-dd') =to_char(sysdate, 'yyyy-mm-dd')";
		gwmap=db.queryForMap(sql);
		clmap=db.queryForMap(sql1);
		rcmap=db.queryForMap(sql2);
		map.put("gw",gwmap.get("BXZJE"));
		map.put("gwsjs",gwmap.get("SJS"));
		map.put("cl",clmap.get("BXZJE"));
		map.put("clsjs",clmap.get("SJS"));
		map.put("rc",rcmap.get("BXZJE"));
		map.put("rcsjs",rcmap.get("SJS"));
		return map;
			
		}	
	/**
	 * 
	 * @return
	 */
	public Map getWsbx(String sjqj){
		Map map =new HashMap();
		Map gwmap;
	    Map clmap;
	    Map rcmap;
		String jsqj = "";
		String jsqj1 = "";
		if(sjqj.equals("jr")){
			 jsqj="to_char(t.CZRQ, 'yyyy-mm-dd') =to_char(sysdate, 'yyyy-mm-dd')";
			 jsqj1="m.czrq = to_char(sysdate, 'yyyy-mm-dd')";
		}
		if(sjqj.equals("bz")){
			 jsqj="trunc(sysdate,'day')< = t.CZRQ and t.CZRQ< = trunc(sysdate,'day')+(INTERVAL '6' DAY)";
			 jsqj1="trunc(sysdate,'day')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< = trunc(sysdate,'day')+(INTERVAL '6' DAY)";
		}
		if(sjqj.equals("by")){
			 jsqj="trunc(sysdate,'month')< = t.CZRQ and t.CZRQ< = last_day(sysdate)";
			 jsqj1="trunc(sysdate,'month')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< = last_day(sysdate)";
		}
		if(sjqj.equals("bjd")){
			 jsqj="trunc(sysdate,'q')< = t.CZRQ and t.CZRQ<  trunc(trunc(sysdate,'q'))+(INTERVAL '3' MONTH)";
			 jsqj1="trunc(sysdate,'q')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< trunc(trunc(sysdate,'q'))+(INTERVAL '3' MONTH)";
		}
		if(sjqj.equals("bn")){
			 jsqj=" trunc(sysdate,'year')< = t.CZRQ and t.CZRQ< trunc(trunc(sysdate,'year'))+(INTERVAL '1' YEAR)";
			 jsqj1="trunc(sysdate,'year')< = to_date(m.CZRQ,'yyyy-mm-dd') and to_date(m.CZRQ,'yyyy-mm-dd')< trunc(trunc(sysdate,'year'))+(INTERVAL '1' YEAR)";
		}
		String sql = "select nvl(sum(T.bxje),0) as BXZJE,count(1) as sjs from Cw_gwjdfbxzb t where t.shzt = '8' and "+jsqj;
		String sql1 = "select nvl(sum(m.bxzje),0) as BXZJE,count(1) as sjs from Cw_clfbxzb m where m.shzt = '8' and "+jsqj1;
		String sql2 = "select nvl(sum(t.bxzje),0) as BXZJE,count(1) as sjs from Cw_rcbxzb t where t.shzt = '8' and "+jsqj;
		System.err.println(sql+"___"+sql1+"___"+sql2);
		gwmap=db.queryForMap(sql);
		clmap=db.queryForMap(sql1);
		rcmap=db.queryForMap(sql2);
		map.put("gw",gwmap.get("BXZJE"));
		map.put("gwsjs",gwmap.get("SJS"));
		map.put("cl",clmap.get("BXZJE"));
		map.put("clsjs",clmap.get("SJS"));
		map.put("rc",rcmap.get("BXZJE"));
		map.put("rcsjs",rcmap.get("SJS"));
	
	return map;	
	}
	
	/**
	 * 
	 * 获得我的报销
	 */
	public Map getwdbx() {
	    Map map =new HashMap();
	    String rybh=CommonUtils.getRybh();
	    Map gwmap;
	    Map clmap;
	    Map rcmap;
		String sql = "select sum(T.bxje) as BXZJE from Cw_gwjdfbxzb t where t.shzt = '8' and substr(t.BXRY,2,6)='"+rybh+"'";
		String sql1 = "select sum(m.bxzje) as BXZJE from Cw_clfbxzb m where m.shzt = '8' and (select guid from gx_sys_ryb where rybh='"+rybh+"') = m.sqr";
		String sql2 = "select sum(n.bxzje) as BXZJE from Cw_rcbxzb n where n.shzt = '8' and (select guid from gx_sys_ryb where rybh='"+rybh+"') = n.bxr";
		gwmap=db.queryForMap(sql);
		clmap=db.queryForMap(sql1);
		rcmap=db.queryForMap(sql2);
		map.put("gw",gwmap.get("BXZJE"));
		map.put("cl",clmap.get("BXZJE"));
		map.put("rc",rcmap.get("BXZJE"));
		return map;
			
		}
	/**
	 * 
	 * 项目执行
	 */
	public Map getxm() {
	    Map map;
	    String rybh=CommonUtils.getRybh();
		String sql = "select to_char(sum(t.ysje)/10000,'FM9999999999999990.00') as yszje,to_char(sum(t.syje)/10000,'FM9999999999999990.00') as syzje,to_char(sum(t.ysje-t.syje)/10000,'FM9999999999999990.00') as zxzje,to_char((sum(t.ysje-t.syje)/sum(t.ysje))*100,'FM9999999999999990.00') as zxbl from CW_XMJBXXB t ";
		map=db.queryForMap(sql);
		return map;
			
		}
	public List getXzList(String rygh){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT, ");
		sql.append("TO_CHAR(T.GWGZ,'FM9999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM9999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM9999990.00') AS XZFT,");
		sql.append("TO_CHAR(T.WYBT,'FM9999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM9999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM9999990.00') AS JCJX,");
		sql.append("TO_CHAR(T.JLJX1,'FM9999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM9999990.00') AS BSBT,");
		sql.append("TO_CHAR(T.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM9999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM9999990.00') AS SYBT,");
		sql.append("TO_CHAR(T.JHBT,'FM9999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM9999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM9999990.00') AS HTBT,");
		sql.append("TO_CHAR(T.QTBT,'FM9999990.00') AS QTBT,");
		sql.append("TO_CHAR(T.BGZ,'FM9999990.00') AS BGZ,TO_CHAR(T.JKF,'FM9999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM9999990.00') AS FZGZL,");
		sql.append("TO_CHAR(T.ZSJL,'FM9999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM9999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM9999990.00') AS KHJ," );
		sql.append("TO_CHAR(T.DHF,'FM9999990.00') AS DHF,TO_CHAR(T.BT,'FM9999990.00') AS BT,TO_CHAR(T.ZFBT,'FM9999990.00') AS ZFBT," );
		sql.append("TO_CHAR(T.YFHJ,'FM999999.00') AS YFHJ," );
		sql.append("TO_CHAR(T.JIANGKEF,'FM9999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM9999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM9999990.00') AS JSX," );
		sql.append("TO_CHAR(T.QNJSX,'FM9999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM9999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM9999990.00') AS QNJSX2," );
		sql.append("TO_CHAR(T.QNJSX3,'FM9999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM9999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM9999990.00') AS BXBT2014N1D10YJSJS," );
		sql.append("TO_CHAR(T.JSJS,'FM9999990.00') AS JSJS," );
		sql.append("TO_CHAR(T.ZFJJ,'FM9999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM9999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM9999990.00') AS YLBX," );
		sql.append("TO_CHAR(T.BGJJ,'FM9999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM9999990.00') AS DKS,TO_CHAR(T.BNSE,'FM9999990.00') AS BNSE," );
		sql.append("TO_CHAR(T.SNSE,'FM9999990.00') AS SNSE,TO_CHAR(T.BS,'FM9999990.00') AS BS,TO_CHAR(T.FZ,'FM9999990.00') AS FZ," );
		sql.append("TO_CHAR(T.SYJ,'FM9999990.00') AS SYJ," );
		sql.append("TO_CHAR(T.NQF,'FM9999990.00') AS NQF,TO_CHAR(T.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(T.WYF,'FM9999990.00') AS WYF," );
		sql.append("TO_CHAR(T.SBJ,'FM9999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(T.KK,'FM9999990.00') AS KK," );
		sql.append("TO_CHAR(T.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(T.JK,'FM9999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM9999990.00') AS AXYRJ," );
		sql.append("TO_CHAR(T.KKHJ,'FM9999990.00') AS KKHJ,TO_CHAR(T.SFHJ,'FM9999990.00') AS SFHJ," );
		sql.append("TO_CHAR(TO_DATE(T.GZYF,'YYYY.MM'),'YYYY') AS YEAR,TO_CHAR(TO_DATE(T.GZYF,'YYYY.MM'),'MM') AS MON,T.GZYF,T.BH," );
		sql.append("T.XH,TO_CHAR(T.JTF,'FM9999990.00') AS JTF,TO_CHAR(T.NZJ,'FM9999990.00') AS NZJ," );
		sql.append("TO_CHAR(T.NZJDKS,'FM9999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM9999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM9999990.00') AS KSHJ," );
		sql.append("T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM9999990.00') AS BKYLBX," );
		sql.append("TO_CHAR(T.BKSYJ,'FM9999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM9999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM9999990.00') AS BKSBJ," );
		sql.append("T.SFDY,T.RDQK ");
		sql.append(" FROM CW_XZB T ");
		sql.append("WHERE GH=? and shzt='2' ORDER BY GZYF DESC");
		return db.queryForList(sql.toString(), new Object[]{rygh});
	}
	/**
	 * 得到所有一级菜单
	 * @param rybh
	 * @return
	 */
	public List getYjmk(String filter) {
		String sql = " select distinct substr(t.mkbh,0,2) as yjmkbh,t.mkbh ,t.url,t.mkmc ,t.sytz from zc_sys_mkb t where length(t.mkbh)=2 and t.mkbh not in(03,56,57,55,10)";
		String strWhere = Validate.isNullOrEmpty(filter) ? "":" and t.mkbh not in ('"+filter+"') ";
		sql = sql + strWhere;
	    return db.queryForList(sql);
	}
	/**
	 * 查询事前审批下启用的模块数
	 * @return
	 */
	public String getSqspmk() {
		String sql =  "select count(1) from zc_sys_mkb where mkbh like '13__' and qxbz = '1'";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 *验证实体
	 * @param 
	 * @return
	 */
	public Map<String, Object> getDscpz() {
		String sql="select count(*) as dscpzsl from (select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxr) as sqr," + 
				"to_char(t.czrq,'yyyy-mm-dd') as sqrq," + 
				"(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = t.szbm) as szbm,'日常报销' as bxlx,t.bxzje as bxje," + 
				"(select wm_concat(xmmc) from CW_RCBXMXB m where m.zbid=t.guid) as bxxm" + 
				" from cw_rcbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" + 
				" union" + 
				" select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.sqr) as sqr,czrq as sqrq," + 
				"(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.sqr) as szbm," + 
				"'差旅费报销' as bxlx,to_number(t.bxzje) as bxje," + 
				"(select wm_concat(xmmc) from cw_ccsqspb_xm xm left join xminfos s on s.guid=xm.jfbh where xm.ccsqid=t.ccywguid) as bxxm " + 
				"from cw_clfbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb)" + 
				" union " + 
				"select t.guid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.bxryid) as sqr,to_char(t.czrq,'yyyy-mm-dd') as sqrq," + 
				"(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.bxryid) as szbm," + 
				"'公务接待费报销' as bxlx,t.bxje,'' from cw_gwjdfbxzb t where t.shzt = '8' and t.guid not in (select bxid from cw_pzlrbxdzb) " + 
				"union " + 
		        "select t.gid,t.djbh,(select '('||rybh||')'||xm from gx_sys_ryb where guid = t.jkr) as sqr,to_char(t.jksj,'yyyy-mm-dd') as sqrq,   " + 
		        "(select '('||b.dwbh||')'||b.mc from gx_sys_ryb a left join gx_sys_dwb b on a.dwbh = b.dwbh where a.guid = t.jkr) as szbm,  " + 
		        "'借款' as bxlx,jkzje," + 
				"(select wm_concat(xmmc) from XMINFOS A left join CW_JKYWB_MXB k on k.jfbh = a.guid where k.jkid=t.gid)" + 
				" from CW_JKYWB t where t.shzt='8' and t.gid not in (select bxid from cw_pzlrbxdzb))";
		return db.queryForMap(sql,new Object[]{});
	}

	public boolean doUpdateTzxx(String guid) {
		// TODO Auto-generated method stub
		return db.update("update CW_XTXX set xxzt='1' where guid=?", new Object[]{guid})>0;
	}
     /**
      * 我的项目
      * @param rybh
      * @return
      */
	public Object getWdxmList(String rybh) {
		StringBuffer sqltext = new StringBuffer();
		sqltext.append("  select guid,xmbh,xmmc,bmmc,xmdlmc,xmlbmc,xmfzr,ysje,syje,to_char((nvl(zcje1,0)+nvl(zcje2,0)),'FM9999999999999999999999999999999999999990.00')as zcje,to_char((nvl(dshje1,0)+nvl(dshje2,0)),'FM999999999999999999999999999999999990.00')as dshje ");
		sqltext.append(" from " );
		sqltext.append(" (select distinct guid, xmbh,xmmc,(select '('||RYBH||')'|| xm from gx_sys_ryb ry where ry.rybh=A.fzr) as xmfzr,to_char(ysje,'FM999999999999999999999999999999990.00') as ysje,to_char(syje,'FM999999999999999999999999999999990.00') as syje," );
		sqltext.append( " (select sum(r.BXJE) from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt='8' )as zcje1," );
		sqltext.append( " (select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=a.bmbh  ) as bmmc," );
		sqltext.append(" (select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=a.xmdl) xmdlmc," );
		sqltext.append( " (select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=a.xmlb) xmlbmc," );
		sqltext.append( " (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt='8' )as zcje2," );
		sqltext.append( " (select sum(r.BXJE) from cw_rcbxmxb r left join cw_rcbxzb b on r.zbid=b.guid where r.xmguid=a.guid and b.shzt not in('8') )as dshje1," );
		sqltext.append(" (select sum(m.bcbxje) from cw_ccsqspb_xm m left join cw_clfbxzb b on m.ccsqid=b.ccywguid where m.jfbh=a.guid and b.shzt not in('8') )as dshje2" );
		sqltext.append( " from cw_xmjbxxb A where a.fzr='"+rybh+"')K");
		return db.queryForList(sqltext.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	}
	
	

