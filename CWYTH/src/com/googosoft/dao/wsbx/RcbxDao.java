package com.googosoft.dao.wsbx;

import groovy.sql.Sql;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.wsbx.CW_CJKB;
import com.googosoft.pojo.wsbx.CW_DGZFB;
import com.googosoft.pojo.wsbx.CW_FJXXB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.pojo.wsbx.CW_LYEB;
import com.googosoft.pojo.wsbx.Cw_DSZFB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("rcbxDao")
public class RcbxDao extends BaseDao {
	private Logger logger = Logger.getLogger(RcbxDao.class);
	/**
	 * 获取经济科目字典树下级
	 */
	public List jjszMenuzj(String dm) {

		String sql = "";
		if(Validate.noNull(dm)){
			sql = "select t.guid,t.kmbh,t.kmmc,t.l,t.k,T.KMJC from cw_jjkmb t WHERE T.k='"+dm+"'";
		}else{
			sql = "select t.guid,t.kmbh,t.kmmc,t.l,t.k,T.KMJC from cw_jjkmb t WHERE T.k is null and l is null";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}

	public String getjsxm(String rybh){
		String sql = "select t.xm from gx_sys_ryb t where t.guid='"+rybh+"'  ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 是否存在下级树
	 * 
	 * @param kmbh
	 * @return
	 */
	public int getCount(String kmbh) {
		String sql = "select count(0) from cw_jjkmb t WHERE T.k='" + kmbh + "'";
		int count = Integer.parseInt(Validate.isNullToDefaultString(
				db.queryForSingleValue(sql), "0"));
		return count;
	}
	/**
	 * 学生银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXsyhxx(String dqdlrguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,yhmc,khyhh");
		sql.append(" from cw_yhzhb t where t.jsbh=?");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString(),new Object[] {dqdlrguid});
		return list;
	}
	/**
	 * 教师银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getJsyhxx(String dqdlrguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,khyh,khyhzh");
		sql.append(" from cw_jsyhzhb t where t.jsbh=?");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString(),new Object[] {dqdlrguid});
		return list;
	}
	/**
	 * 校外人员银行卡信息
	 * @param wlbh
	 * @return
	 */
	public List getXwryyhxx(String dqdlrguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,yhmc,khyhh");
		sql.append(" from cw_xwryxxb t where t.guid=?");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString(),new Object[] {dqdlrguid});
		return list;
	}
	/**
	 * 对私登录人 银行
	 * @param wlbh
	 * @return
	 */
	public List getdlrYh(String dqdlrguid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,khyh,khyhzh");
		sql.append(" from cw_jsyhzhb t where t.jsbh=?");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString(),new Object[] {dqdlrguid});
		return list;
	}
	/**
	 *  检查发票号 是否有重复的
	 * @author 作者：Administrator
	 * @version 创建时间:2018-2-9下午5:10:16
	 */
	public List checkFpxx() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from cw_fpxxb");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	/**
	 * 费用列表
	 * 
	 * @return
	 */
	public List getFyList(String xmguid) {
		StringBuffer sql = new StringBuffer();
		
		sql.append(" select fyfl,fymc,guid,bz as ms,''as bz,''as bxje,''as fjzs from Cw_fykmdzb t");
		sql.append(" where not exists(select 1 from Cw_rcbxmxb r where r.fymc=t.guid and r.zbid='"
				+ xmguid + "')  and t.zt<>'0' and t.fyfl='1'");
		sql.append(" union");
		sql.append(" select f.fyfl,f.fymc,f.guid,f.bz as ms,t.bz as bz,");
		sql.append(" decode(nvl(t.bxje,0),0,'0.00',(to_char(round(t.bxje,2),'fm99999999999990.00')))bxje,");
		sql.append(" to_char(t.fjzs)fjzs");
		sql.append(" from Cw_rcbxmxb t");
		sql.append(" left join Cw_fykmdzb f on f.guid=t.fymc");
		sql.append(" where zbid='" + xmguid + "' and  f.zt<>'0'");
		sql.append(" group by f.fyfl,f.fymc,f.guid,f.bz,t.bxje,t.fjzs,t.bz");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		System.err.println("查询详细 "+sql);
		return list;
	}

	/**
	 * 新增
	 * 
	 * @param jsxx
	 * @return
	 */
	public int doAdd(Rcbxmx rcbxmx) {
		/*String delSql = "delete from CW_RCBXMXB where zbid=?";
		db.update(delSql, new Object[]{rcbxmx.getZbid()});*/
		String sql = "insert into CW_RCBXMXB(guid,fymc,fjzs,bxje,bz,zbid,xmmc,syje,xmguid,zfcgje,zfcgsyje,fzfcgje,fzfcgsyje)" + "VALUES(sys_guid(),'"+rcbxmx.getFymc()+"','"+rcbxmx.getFjzs()+"','"+rcbxmx.getBxje()+"','"+rcbxmx.getBz()+"','"+ rcbxmx.getZbid()+"','"+rcbxmx.getXmmc()+"','"+rcbxmx.getSyje()+"','"+rcbxmx.getXmguid()+"','"+rcbxmx.getZfcgje()+"','"+rcbxmx.getZfcgsyje()+"','"+rcbxmx.getFzfcgje()+"','"+rcbxmx.getFzfcgsyje()+"') ";
		System.err.println("报销金额明细 sql== "+sql);
		return db.update(sql);
	}

	public void deleteFymx(String zbid) {
		String delSql = "delete from CW_RCBXMXB where zbid=?";
		int m = db.update(delSql, zbid);
	}

	/**
	 * 上一步回显使用
	 * 
	 * @return
	 */
	public List getFyListByXmid(String zbId) {
		String sql = "select fyfl,fymc,guid,bz from Cw_fykmdzb where zbid='"
				+ zbId + "'";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}

	/**
	 * 上一步回显使用
	 * 
	 * @return
	 */
	public List getFyListBySelect() {
		String sql = "select fyfl,fymc,guid,bz,kmbh from Cw_fykmdzb where zt='1' and fyfl='1'";
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql);
		return list;
	}

	/**
	 * 新增
	 * 
	 * @param jsxx
	 * @return
	 */
	public int doAddzb(Rcbxzb rcbx) {
		Map map = getBxzbById(rcbx.getGuid());
		StringBuffer sqlJkje = new StringBuffer();
		sqlJkje.append(" select sum( nvl(zcje,0) ) AS zcje from CW_JKYWB_MXB m,cw_jkywb t   WHERE m.jkid=t.gid AND jfbh=?  and t.jkr=? ");
//		sqlJkje.append(" and  t.gid in(select bxid  from cw_pzlrbxdzb  where pzid in (select guid from cw_pzlrzb where pzzt != '00') and bxtype = 'jkbx') ");
		String jkje = Validate.isNullToDefaultString(db.queryForSingleValue(sqlJkje.toString(),new Object[] {rcbx.getXmguid(),LUser.getGuid()}), "0.00");//查询当前登录人从该项目借款金额，且该项目钱款已支付
		String bxzje = Validate.isNullToDefaultString(rcbx.getBxzje(), "0.00");
		BigDecimal bxzjes = new BigDecimal(bxzje);
		BigDecimal jkjes = new BigDecimal(jkje);
		//若借款，报销金额需减去借款金额
		BigDecimal bxje = bxzjes.subtract(jkjes);
		String bxjes = bxje+"";
		if(map.isEmpty()||Validate.isNull(map)||map.size()==0){
			String delSql = "delete from Cw_rcbxzb where guid=?";
			int m = db.update(delSql, rcbx.getGuid());
			String djbh = GenAutoKey.createKeyforth("CW_RCBXZB","RC", "djbh");
			String sql = "insert into Cw_rcbxzb(guid,djbh,bxr,szbm,xmmc,fjzzs,bxzje,bxzjes,sfgwk,sfcjk,sfdgzf,sfdszf,bxsy,bz,czr,czrq,sfxy)"
					+ "VALUES(?,'"+djbh+"',?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,(select nvl(sfxy,'0') from gx_sys_dwb where dwbh='"+LUser.getDwbh()+"')) ";
			sql = String.format(sql, SystemSet.gxBz);
			rcbx.setSzbm(LUser.getDwbh());
			Object[] obj = new Object[] { rcbx.getGuid(), 
					rcbx.getBxr(), rcbx.getSzbm(), rcbx.getXmguid(), rcbx.getFjzzs(),
					bxjes,rcbx.getBxzje(), rcbx.getSfgwk(),
					rcbx.getSfcjk(), rcbx.getSfdgzf(), rcbx.getSfdszf(),
					rcbx.getBxsy(), rcbx.getBz(), rcbx.getCzr() };
			return db.update(sql, obj);
		}else{
			StringBuffer sql = new StringBuffer();
			sql.append(" update");
			sql.append(" CW_RCBXZB t set");
			sql.append(" xmmc=?,fjzzs=?,bxzje=?,bxzjes=?,bxsy=?,bz=?,czr=?,czrq=sysdate");
			sql.append(" where t.guid=?");
			String sqls = sql.toString();
			sqls = String.format(sqls, SystemSet.gxBz);
			Object[] obj = new Object[] {
					rcbx.getXmguid(),
					rcbx.getFjzzs(),
//					rcbx.getBxzje(),
					bxjes,
					rcbx.getBxzje(),
					rcbx.getBxsy(),
					rcbx.getBz(),
					rcbx.getCzr(),
					rcbx.getGuid()
			};
			return db.update(sqls, obj);
		}
		
	}

	/**
	 * 主表信息回显
	 * 
	 * @param zbid
	 * @return
	 */
	public Map<String, Object> getBxzbById(String zbid) {
		String sql = "select to_char(t.czrq,'yyyy')as year,to_char(t.czrq,'mm')as month,to_char(t.czrq,'dd')as day,guid,djbh,szbm,xmmc,(select xmmc from cw_xmjbxxb x where x.guid=t.xmmc and rownum<=1)xmname," +
				"(select '('||dwbh||')'||mc from gx_sys_dwb d where d.dwbh=t.szbm and rownum<=1)as dwname,"+
				"(select '('||rybh||')'|| xm from gx_sys_ryb r where r.guid=t.bxr and rownum<=1)as bxrname,"+
				"fjzzs,decode(nvl(t.bxzje,0),0,'0.00',(to_char(round(t.bxzje,2),'fm99999999999990.00')))bxzje," +
				"decode(nvl(t.bxzjes,0),0,'0.00',(to_char(round(t.bxzjes,2),'fm99999999999990.00')))bxzjes," +
				" sfgwk,sfcjk,sfdgzf,sfdszf,sflye,bxsy,bz,shyj,shzt from Cw_rcbxzb t where guid='"
				+ zbid + "'";
		System.err.println("主表信息回显==> "+sql);
		return db.queryForMap(sql);
	}

	/**
	 * 先删后增的通用删除
	 * 
	 * @param table
	 * @param col
	 * @param id
	 */
	public void deleteDatas(String table, String col, String id) {
		String delSql = "delete from " + table + " where " + col + " =?";
		int m = db.update(delSql, id);
	}

	/**
	 * 新增冲借款
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddCjk(CW_CJKB cjk) {
		String sql = "insert into CW_CJKB(guid,jkdh,jkr,szbm,jkje,cjkje,bxlx,jkid)"
				+ "VALUES(sys_guid(),?,?,?,?,?,'1',?) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] { cjk.getJkdh(), cjk.getJkr(),
				cjk.getSzbm(), cjk.getJkje(), cjk.getCjkje(),
				cjk.getJkid(), };
		return db.update(sql, obj);
	}

	/**
	 * 新增对公支付
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddDgzf(CW_DGZFB dgzf) {
		String je = dgzf.getJe();
		String sql;
		Object[] obj;
		if("0".equals(je)){
			sql = "insert into CW_DGZFB(guid,zfdh,dfdw,dfdq ,dfyh ,dfzh, bxlx )"
					+ "VALUES(sys_guid(),?,?,?,?,?,'1') ";
			sql = String.format(sql, SystemSet.gxBz);
			obj = new Object[] { dgzf.getZfdh(), dgzf.getDfdw(),
					dgzf.getDfdq(), dgzf.getDfyh(), dgzf.getDfzh()};
		}else{
			sql = "insert into CW_DGZFB(guid,zfdh,dfdw,dfdq ,dfyh ,dfzh, je, bxlx )"
					+ "VALUES(sys_guid(),?,?,?,?,?,?,'1') ";
			sql = String.format(sql, SystemSet.gxBz);
			obj = new Object[] { dgzf.getZfdh(), dgzf.getDfdw(),
					dgzf.getDfdq(), dgzf.getDfyh(), dgzf.getDfzh(), dgzf.getJe()};
			
		}
		return db.update(sql, obj);
	}

	/**
	 * 新增对私支付
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddDszf(Cw_DSZFB dszf) {
		String sql = "insert into Cw_DSZFB(guid,zfdh,ryxz,ryxm,klx,dfzh,je,bxlx,zhbguid)"
				+ "VALUES(sys_guid(),?,?,?,?,?,?,'1',?) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] { dszf.getZfdh(), dszf.getRyxz(),
				dszf.getRyxm(), dszf.getKlx(), dszf.getDfzh(), dszf.getJe(),dszf.getZhbguid()};
		return db.update(sql, obj);
	}
	/***
	 * 是否完全保存
	 * @author 作者：默认1
	 * @version 创建时间:2018-4-20下午2:03:51
	 */
	public int updatesfwqbc(String zbid) {
		String sql = " update CW_RCBXZB a set a.sfwqbc = '' where guid = '"+zbid+"' ";
		return db.update(sql);
	}
	
	
	public Map checkIsSubmit(String zbid) {
		String sql = " select a.sfwqbc from CW_RCBXZB a where a.guid = '"+zbid+"' ";
		return db.queryForMap(sql);
	}
	
	/**
	 * 新增附件信息
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddFjxx(CW_FJXXB fjxx) {
		String sql = "insert into Cw_DSZFB(guid,fjbh,fjxh,fjmc,cfdd,mkbh)"
				+ "VALUES(sys_guid(),?,?,?,?,?,) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] { fjxx.getFjbh(), fjxx.getFjxh(),
				fjxx.getFjmc(), fjxx.getCfdd(), fjxx.getMkbh(), };
		return db.update(sql, obj);
	}

	/**
	 * 新增公务卡信息
	 * 
	 * @param cjk
	 * @return
	 */
	public int doAddGwk(CW_GWKB gwk) {
		String skje = gwk.getSkje();
		System.err.println("sjskje="+gwk.getSjskje());
		String sql;
		Object[] obj;
		if("0".equals(skje)){
			sql = "insert into CW_GWKB(guid ,skdh ,szbm ,ryxm ,skrq ,sjskje,kh ,bxlx )"
					+ "VALUES(sys_guid(),?,?,?,to_date(?,'yyyy-MM-dd'),?,?,'1') ";
			sql = String.format(sql, SystemSet.gxBz);
			obj = new Object[] { gwk.getSkdh(), gwk.getSzbm(),
					gwk.getRyxm(), gwk.getSkrq(),gwk.getSjskje(), gwk.getKh()};
		}else{
			sql = "insert into CW_GWKB(guid ,skdh ,szbm ,ryxm ,skrq ,sjskje,skje ,kh ,bxlx )"
					+ "VALUES(sys_guid(),?,?,?,to_date(?,'yyyy-MM-dd'),?,decode('"+skje+"','0','','"+skje+"'),?,'1') ";
			sql = String.format(sql, SystemSet.gxBz);
			obj = new Object[] { gwk.getSkdh(), gwk.getSzbm(),
					gwk.getRyxm(), gwk.getSkrq(),gwk.getSjskje(), gwk.getKh()};
		}
		return db.update(sql, obj);
	}
	
	/**
	 * 零余额保存
	 * @author 作者：PR
	 * @date 2018-9-11  下午2:57:43
	 */
	public int doAddLye(CW_LYEB lye) {
		String sql;
		Object[] obj;
		sql = "insert into CW_LYEB(guid ,hm ,yh ,yhkh ,je ,bxid ) VALUES(sys_guid(),?,?,?,?,?) ";
		obj = new Object[] { 
				lye.getHm(),
				lye.getYh(),
				lye.getYhkh(),
				lye.getJe(),
				lye.getBxid()
		};
		
		return db.update(sql, obj);
	}

	public List getCxjkListHx(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" distinct(t.guid)guid,d.mc as dwmc,d.dwbh as szbm,decode(nvl(t.cjkje,0),0,'0.00',(to_char(round(t.cjkje,2),'fm99999999999990.00')))cjkje,t.jkr,'('||r.rybh||')'||r.xm as jkrxm,cw.djbh,decode(nvl(cw.jkzje,0),0,'0.00',(to_char(round(cw.jkzje,2),'fm99999999999990.00')))jkje");
		sql.append(" from Cw_cjkb t");
		sql.append(" left join gx_sys_ryb r on r.guid=t.jkr or r.rybh=t.jkr");
		sql.append(" left join gx_sys_dwb d on d.dwbh=r.dwbh");
		sql.append(" left join CW_JKYWB cw on cw.djbh=t.jkid");
		sql.append(" where 1=1");
		sql.append(" and jkdh='" + zbid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}

	public List getCxjkLists() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  a.guid, a.djbh,a.jkr, a.szbm,to_char(nvl(a.qkje,a.jkje),'fm99999999999990.00') as jkje,");
		sql.append(" '"+LUser.getXm()+"'as jkrxm,'" + LUser.getDwmc() + "'as szdw");
		sql.append(" from CW_YZFSQSPB a");
		sql.append(" where (a.jkr = '"+LUser.getGuid()+"'");
		sql.append(" or a.jkr = '"+LUser.getRybh()+"')");
		sql.append(" and nvl(a.qkje,a.jkje)>0");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}

	public Map<String, Object> getCxjkMap(String guid) {
//		StringBuffer sql = new StringBuffer();
//		sql.append(" select decode(nvl(A.jkje,0),0,'0.00',(to_char(round(A.jkje,2),'fm99999999999990.00')))jkje,");
//		sql.append(" a.guid, a.djbh,a.jkr,a.szbm,'" + LUser.getRyxm()
//				+ "'as jkrxm,'" + LUser.getDwmc() + "'as szdw from(");
//		sql.append(" select t.guid,t.djbh,t.jkr,t.szbm,to_number(t.jkje)as jkje");
//		sql.append(" from CW_YZFSQSPB t where t.jkr='" + LUser.getGuid()
//				+ "' or t.jkr='" + LUser.getRybh() + "'");
//		sql.append(" and not exists(select 1 from Cw_cjkb c where c.jkid=t.guid)");
//		sql.append(" union");
//		sql.append(" select  t.guid,t.djbh,t.jkr,t.szbm,");
//		sql.append(" (select to_number(nvl(c.jkje,0))-to_number(nvl(c.cjkje,0)) from  Cw_cjkb c left join CW_RCBXZB r on r.guid=c.jkdh and r.shzt='8' where c.jkid=t.jkdh)as jkje");
//		sql.append(" from CW_YZFSQSPB t where t.jkr='" + LUser.getGuid()
//				+ "' or t.jkr='" + LUser.getRybh() + "'");
//		sql.append(" )a");
//		sql.append(" where 1=1");
//		sql.append(" and nvl(a.jkje,0)>0");
		StringBuffer sql = new StringBuffer();
//		sql.append(" select * from(");
//		sql.append(" select  a.guid, a.djbh,a.jkr, a.szbm,  to_number(nvl(a.jkje, 0)) - to_number(nvl(c.cjkje, 0))jkje,");
//		sql.append(" '"+LUser.getRyxm()+"'as jkrxm,'" + LUser.getDwmc() + "'as szdw");
//		sql.append(" from (select t.guid, t.djbh, t.jkr, t.szbm, t.jkje");
//		sql.append(" from CW_YZFSQSPB t");
//		sql.append(" where t.jkr = '"+LUser.getGuid()+"'");
//		sql.append(" or t.jkr = '"+LUser.getRybh()+"') a");
//		sql.append(" left join CW_YZFSQSPB y on y.djbh = a.djbh");
//		sql.append(" left join Cw_cjkb c on c.jkid = a.djbh");
//		sql.append(" left join CW_RCBXZB r on r.guid = c.jkdh and r.shzt = '8'");
//		sql.append(" where y.guid ='" + guid + "'");
//		sql.append(" )aa where aa.jkje>0");
		sql.append(" select  a.guid, a.djbh,a.jkr, a.szbm,nvl(a.qkje,a.jkje) as jkje,");
		sql.append(" '"+LUser.getXm()+"'as jkrxm,'" + LUser.getDwmc() + "'as szdw");
		sql.append(" from CW_YZFSQSPB a");
		sql.append(" where (a.jkr = '"+LUser.getGuid()+"'");
		sql.append(" or a.jkr = '"+LUser.getRybh()+"')");
		sql.append(" and nvl(a.qkje,a.jkje)>0");
		sql.append(" and a.guid='"+guid+"'");
		Map map = new HashMap<String, Object>();
		map = db.queryForMap(sql.toString());
		return map;
	}

	/**
	 * 公务卡list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getGekList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,t.skdh,to_char(t.skrq,'yyyy-MM-dd')as skrq,t.sjskje,t.kh,");
		sql.append(" decode(nvl(t.skje,0),0,'0.00',(to_char(round(t.skje,2),'fm99999999999990.00')))skje,");
		sql.append(" (select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.ryxm or r.guid=t.ryxm)ryxm");
		sql.append(" from cw_gwkb t where 1=1 and t.skdh='" + zbid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 查询零余额表
	 * @author 作者：PR
	 * @date 2018-9-11  下午3:32:42
	 */
	public List getLyeList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select ");
		sql.append(" t.guid,t.hm,t.yh,t.yhkh,");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je ");
		sql.append(" from cw_lyeb t where 1=1 and t.bxid='" + zbid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}

	/**对公转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDgList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select  (select m.khyh from Cw_Wldwmxb m where m.guid=t.dfyh)yhname,");
		sql.append(" t.guid,t.zfdh,t.dfdq,t.dfzh,w.dwmc,w.guid,w.wlbh,t.dfyh,");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
		sql.append(" from CW_DGZFB t");
		sql.append(" left join Cw_wldwb w on w.WLBH=t.DFDW");
		sql.append(" where 1=1 and t.zfdh='" + zbid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	

	/**
	 * 往来单位list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getWldwList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.dwmc,dwdz,t.guid,t.lxr,t.bgdh,t.wlbh");
		sql.append(" from CW_WLDWB t where 1=1");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}

	/**
	 * 往来单位银行账号
	 * 
	 * @param wlbh
	 * @return
	 */
	public List getWldwYh(String wlbh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" t.guid,khyh,khyhzh");
		sql.append(" from Cw_wldwmxb t where 1=1");
		sql.append(" and t.wlbh=(select guid from CW_WLDWB where wlbh='"+wlbh+"')");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	
	/**对私转账list
	 * 
	 * @param zbid
	 * @return
	 */
	public List getDsList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" '('||w.rybh||')'||w.xm as ryxm,zhbguid,");
		sql.append(" t.guid,t.zfdh,t.ryxz,t.dfzh,a.khyh AS klx,");
		sql.append(" decode(nvl(t.je,0),0,'0.00',(to_char(round(t.je,2),'fm99999999999990.00')))je");
		sql.append(" from CW_DSZFB t ");
		sql.append(" left join gx_sys_ryb w on w.rybh=t.ryxm ");
		sql.append(" LEFT JOIN cw_jsyhzhb a ON  t.ZHBGUID=a.guid ");
		sql.append(" where 1=1 and t.zfdh='" + zbid + "'");
		List list = new ArrayList<Map<String, Object>>();
		list = db.queryForList(sql.toString());
		return list;
	}
	
	/**
	 * 查询项目负责人
	 * @param xmguid
	 * @return
	 */
	public String getXmFzr(String xmguid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" '('||r.rybh||')'||r.xm as xmfzr");
		sql.append(" from CW_JFSZB t");
		sql.append(" left join gx_sys_ryb r on r.rybh=t.fzr");
		sql.append(" where 1=1");
		sql.append(" and t.xmbh='"+xmguid+"' or t.guid='"+xmguid+"'");
		String xmfzr = Validate.isNullToDefaultString(db.queryForSingleValue(sql.toString()), "");
		return xmfzr;
	}
	
	/**
	 * 更新主表的结算方式
	 * @param rczb
	 * @return
	 */
	public int updateBxzbById(Rcbxzb rczb){
		StringBuffer sql = new StringBuffer();
		sql.append(" update");
		sql.append(" CW_RCBXZB t set");
		sql.append(" sfgwk=?,sfcjk=?,sfdgzf=?,sfdszf=?,sflye=? ");
		sql.append(" where t.guid=?");
		String sqls = sql.toString();
		sqls = String.format(sqls, SystemSet.gxBz);
		Object[] obj = new Object[] {
				rczb.getSfgwk(),
				rczb.getSfcjk(),
				rczb.getSfdgzf(),
				rczb.getSfdszf(),
				rczb.getSflye(),
				rczb.getGuid()
		};
		return db.update(sqls, obj);
	}
	
	/**
	 * 回显第四步的时候使用
	 * @param zbid
	 * @return
	 */
	public Map<String,Object> getZbMapById(String zbid){
		String sql = "select sfgwk,sfcjk,sfdgzf,sfdszf  from CW_RCBXZB t where t.guid='"+zbid+"' and rownum=1";
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql);
		return map;
	}
	
	/**
	 * 更新审核信息
	 * @param guid
	 * @param shzt
	 * @param shyj
	 * @param shr
	 * @return
	 */
	public int updateShById(String guid,String shzt,String shyj,String shr){
		String sql = "update CW_RCBXZB t set t.shzt='"+shzt+"',t.shyj='"+shyj+"',shr='"+shr+"' where t.guid in('"+guid+"')";
		int m = db.update(sql);
		return m;
	}
	
	/**
	 * 删除主表信息,通过触发器delete_rcbx删除关联信息
	 * @param guid
	 * @return
	 */
	public int deleteZbInfoByGuid(String guid){
		String sql1 = "DELETE cw_fpxxb WHERE zbid in('"+guid+"')";
		String sql2 = "delete from CW_RCBXZB t where t.guid in('"+guid+"')";
		List<String> sql = new ArrayList<String>();
		sql.add(sql1);
		sql.add(sql2);
		boolean b = false;
		try {
			b= db.batchUpdate(sql);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		if(b){
		 return 1;
		}else{
			return -1;
		}
	}
	/**
	 * 校验角色
	 * @param rybh
	 * @param role
	 * @return
	 */
	public boolean getJsBh(String rybh,String role){
		String sql = "select jsbh from zc_sys_jsryb where rybh='"+rybh+"'";
		List list = db.queryForList(sql);
		boolean bool = false;
		if(list.size()==0){
			return bool;
		}
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String jsbh = Validate.isNullToDefaultString(map.get("JSBH"), "");
			if(jsbh.equals(role)){
				bool = true;
			}
		}
		return bool;
	}
	
	/**
	 * 财务预审
	 * @param rybh
	 * @return
	 */
	public String findCwys(String rybh) {
		String sql = " select  G.XM AS RYMC\r\n" + 
				"      \r\n" + 
				"  from ZC_SYS_JSRYB D\r\n" + 
				"  left join ZC_SYS_JSB Z on D.JSBH = Z.JSBH\r\n" + 
				"  left join GX_SYS_RYB G on D.RYBH = G.RYBH\r\n" + 
				"  left join GX_SYS_DWB DW on G.DWBH = DW.DWBH\r\n" + 
				" where 1 = 1\r\n" + 
				"   and D.JSBH = '05' and rownum=1 \r\n" + 
				" order by RYGH asc\r\n" + 
				" ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 申请性质，判断审批流程
	 * @param guid
	 * @return
	 */
	public Map<String,Object> getBxrStatus(String guid){
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.bxzje, dw.sfxy,d.dwld as sfld,dw.fgld,dw.dwld,r.rybh,SFKYLBX");
		sql.append(" from CW_RCBXZB t");
		sql.append(" left join gx_sys_ryb r on r.guid = t.bxr");
		sql.append(" left join gx_sys_dwb d on d.dwld=r.rybh");
		sql.append(" left join gx_sys_dwb dw on dw.dwbh = r.dwbh");
		sql.append(" where t.guid='"+guid+"' ");
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql.toString());
		return map;
	}
	
	/**
	 * 审核
	 * @param guid
	 * @return
	 */
	public int check(String guid,String shzt,String shyj){
		String sql = "update CW_RCBXZB t set t.shzt='"+shzt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"' where t.guid='"+guid+"'";
		int a = db.update(sql);
		return a;
	}
	
	/**
	 * 查询校长
	 * @return
	 */
	public String getSchoolMaster(){
		String ry = "select xzxm from CW_XXXXB t";
		String rybh = Validate.isNullToDefaultString(db.queryForSingleValue(ry),"");
		if(rybh.equals(")")&&rybh.length()>2){
			rybh = rybh.substring(1,rybh.indexOf(")"));
		}
		String sql = "select r.guid from gx_sys_ryb r where r.rybh='"+rybh+"'";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql),"");
	}

	/**
	 * 判断当前登录人是不是部门领导
	 * 
	 * @param role
	 * @return
	 */
	public boolean checkSfLd(String role){
		if("部门领导".equals(role)){
			String sql = "select dwld from gx_sys_dwb where dwld='"+LUser.getRybh()+"'";
			if(Validate.noNull(db.queryForSingleValue(sql))){
				return true;
			}
		}else{
			String sql = "select fgld from gx_sys_dwb where fgld='"+LUser.getRybh()+"'";
			if(Validate.noNull(db.queryForSingleValue(sql))){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 更新流程id
	 * @param gid
	 * @return
	 */
	public int doUpdateProcinstId(String ywid,String procinstid) {
		String sql = "UPDATE CW_RCBXZB SET PROCINSTID='"+procinstid+"' WHERE guid"+CommonUtils.getInsql(ywid);
		Object[] obj = ywid.split(",");
		int i = 0;
		try{  
			i = db.update(sql, obj);
		}catch(DataAccessException e){  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 更新业务表审核状态
	 * @param 
	 * @return
	 */
	public int doStatus(String ywid,String zt,String shyj) {
		String sql = "UPDATE CW_RCBXZB SET shzt='"+zt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"' WHERE guid"+CommonUtils.getInsql(ywid);
		Object[] obj = ywid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 更新个人经费设置
	 * @param 
	 * @return
	 */
	public int updateJfsz(Object xmguid,Object bcbxje) {
		String sql = "UPDATE CW_JFSZB SET syje=syje-to_number('"+bcbxje+"') WHERE xmbh='"+xmguid+"' or guid='"+xmguid+"'";
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		System.err.println("个人经费设置 "+sql);
		return i;
	}
	/**
	 * 更新借款表
	 * @param guid
	 */
	public void updateQkje(String guid) {
		String sql = "select sum(nvl(t.cjkje,0))jkje,jkid from cw_cjkb t where t.jkdh='"+guid+"' group by jkid";
		List list = db.queryForList(sql);
		if(list.size()>0){
			for(int i=0;i<list.size();i++){
				Map map = (Map)list.get(i);
				String jkje = Validate.isNullToDefaultString(map.get("JKJE"), "0");
				String jkid = Validate.isNullToDefaultString(map.get("JKID"), "");
				String update = "update CW_YZFSQSPB t set t.qkje=(nvl(t.qkje,t.jkje)-to_number('"+jkje+"')) where t.djbh='"+jkid+"'";
				db.update(update);
			}
		}
	}
	/**
	 * 获取流程节点
	 * @param taskId
	 * @return
	 */
	public String getTaskNodeId(String taskId){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
	buffer.append(" where id_ ='"+taskId+"'");
	String  nodeId = null;
	try {
		nodeId = db.queryForSingleValue(buffer.toString())+"";
	} catch (DataAccessException e) {
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	}
    return nodeId;
}
	public String getFinId(String procinstid){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
		buffer.append(" where PROC_INST_ID_ ='"+procinstid+"'");
		String  nodeId = null;
		try {
			nodeId = db.queryForSingleValue(buffer.toString())+"";
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		}
	    return nodeId;
	}
	/**
	 * 获取下一节点
	 * @param taskId
	 * @return
	 */
	public String getNextTaskNodeId(String taskId){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
	buffer.append(" where id_ ='"+taskId+"'");
	String  nodeId = null;
	try {
		nodeId = db.queryForSingleValue(buffer.toString())+"";
	} catch (DataAccessException e) {
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	}
    return nodeId;
}
	/**
	 * 获取日常报销金额和是否学院
	 * @param guid
	 * @return
	 */
	public Map getRcbxById(String guid){
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT * from CW_RCBXZB t  ");
	buffer.append(" where guid='"+guid+"'");
	String  nodeId = null;
	Map map=new HashMap<>();
	try {
		map = db.queryForMap(buffer+"");
	} catch (DataAccessException e) {
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	}
    return map;
}
	/**
	 * 插入审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doAddShyj(OA_SHYJB shyjb) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				LUser.getGuid(), 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	public List getPrintJj(String guid){
		String sql = " select fyfl, fymc, guid, bz as ms, '' as bz, '0' as bxje, '' as fjzs from Cw_fykmdzb t where not exists (select 1 from Cw_rcbxmxb r where r.fymc = t.guid and r.zbid = '"+guid+"') and t.zt <> '0' "
				+ "union select f.fyfl,f.fymc, f.guid,f.bz as ms, t.bz as bz, decode(nvl(t.bxje, 0),0,  '0.00', (to_char(round(t.bxje, 2), 'fm99999999999990.00'))) bxje,"
				+ " to_char(t.fjzs) fjzs from Cw_rcbxmxb t left join Cw_fykmdzb f on f.guid = t.fymc where zbid = '"+guid+"' and f.zt <> '0'"
				+ " group by f.fyfl, f.fymc, f.guid, f.bz, t.bxje, t.fjzs, t.bz ";
		return db.queryForList(sql);
	}
	//日常报销-院领导审批意见
	public Map<String,Object> getPrintYx(String guid){
		String sql = "select * from(select (select r.xm from gx_sys_ryb r where r.guid=t.rybh) xm,shyj,to_date(jdsj,'yyyy-mm-dd hh24:mi:ss') jdsj,t.rybh  from OA_SHYJB t where t.PROCINSTID=(select PROCINSTID from CW_RCBXZB where guid='"+guid+"')  order by t.jdsj desc) where rownum=1";
		return db.queryForMap(sql);
	}
	//日常报销-部门负责人审批意见
	public Map<String,Object> getPrintBm(String guid){
		String sql = "select * from(select (select r.xm from gx_sys_ryb r where r.guid=t.rybh) xm,shyj,to_date(jdsj,'yyyy-mm-dd hh24:mi:ss') jdsj,t.rybh from OA_SHYJB t where t.PROCINSTID=(select PROCINSTID from CW_RCBXZB where guid='"+guid+"') and shzt='4' order by t.jdsj desc) where rownum=1";
		return db.queryForMap(sql);
	}
	//日常报销-会计审核意见 
	public Map<String,Object> getPrintKj(String guid){
		String sql = "select * from(select (select r.xm from gx_sys_ryb r where r.guid=t.rybh) xm,shyj,to_date(jdsj,'yyyy-mm-dd hh24:mi:ss') jdsj,t.rybh from OA_SHYJB t where t.PROCINSTID=(select PROCINSTID from CW_RCBXZB where guid='"+guid+"') and shzt in('2','3','16') order by t.jdsj desc) where rownum=1";
		return db.queryForMap(sql);
	}
	
	//差旅费-院领导审批意见
	public Map<String,Object> getPrintYxclf(String guid){
		String sql = "select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_"
				+ " where m.task_def_key_ = 'dwldsh' and m.proc_inst_id_ = (select PROCINSTID from CW_clfBXZB where guid = '"+guid+"') and rownum=1";
		return db.queryForMap(sql);
	}
	//差旅费-部门负责人审批意见 
	public Map<String,Object> getPrintBmclf(String guid){
		String sql = " select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_"
				+ " where m.task_def_key_ = 'bmfzrsh' and m.proc_inst_id_ = (select PROCINSTID from CW_clfBXZB where guid = '"+guid+"') and rownum=1";
		return db.queryForMap(sql);
	}
	//差旅费-财务负责人审核意见----
	public Map<String,Object> getPrintCwcclf(String guid){
		String sql = " select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_"
				+ " where m.task_def_key_ = 'cwfzrsh' and m.proc_inst_id_ = (select PROCINSTID from CW_clfBXZB where guid = '"+guid+"') and rownum=1";
		return db.queryForMap(sql);
	}
	//差旅费-会计审核意见 
	public Map<String,Object> getPrintKjclf(String guid){
		String sql = " select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh,m.proc_inst_id_ from oa_shyjb n left join act_hi_taskinst m"
				+ "  on n.taskid = m.id_ where m.task_def_key_ = 'cwys' and m.proc_inst_id_ = (select PROCINSTID from CW_clfBXZB"
				+ " where guid = '"+guid+"') and rownum = 1 ";
		return db.queryForMap(sql);
	}
	//差旅费-部门分管领导审核意见----
	public Map<String,Object> getPrintBmfgldclf(String guid){
		String sql = " select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh,m.proc_inst_id_ from oa_shyjb n left join act_hi_taskinst m"
				+ "  on n.taskid = m.id_ where m.task_def_key_ = 'bmfgldsh' and m.proc_inst_id_ = (select PROCINSTID from CW_clfBXZB"
				+ " where guid = '"+guid+"') and rownum = 1 ";
		return db.queryForMap(sql);
	}
	//差旅费-校长审核意见----
	public Map<String,Object> getPrintBmXzclf(String guid){
		String sql = " select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh,m.proc_inst_id_ from oa_shyjb n left join act_hi_taskinst m"
				+ "  on n.taskid = m.id_ where m.task_def_key_ = 'xzsh' and m.proc_inst_id_ = (select PROCINSTID from CW_clfBXZB"
				+ " where guid = '"+guid+"') and rownum = 1 ";
		return db.queryForMap(sql);
	}
	
	//公务接待-报销院系领导意见
	public Map<String,Object> getPrintGWJDYx(String guid){
		String sql = "select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_"
	+ " where m.task_def_key_ = 'dwldsh' and m.proc_inst_id_ = (select PROCINSTID from cw_gwjdfbxzb where guid = '"+guid+"') and rownum=1";
		return db.queryForMap(sql);
	}
	//公务接待-部门负责人意见
	public Map<String,Object> getPrintGWJDBm(String guid){
		String sql = "  select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh from oa_shyjb n left join act_hi_taskinst m on n.taskid = m.id_"
				+ " where m.task_def_key_ = 'bmfzrsh' and m.proc_inst_id_ = (select PROCINSTID from cw_gwjdfbxzb where guid = '"+guid+"') and rownum=1 ";
		return db.queryForMap(sql);
	}
	//公务接待-会计审核意见
	public Map<String,Object> getPrintGWJDKj(String guid){
		String sql = " select shyj, to_date(jdsj, 'yyyy-mm-dd hh24:mi:ss') as jdsj, rybh,m.proc_inst_id_ from oa_shyjb n left join act_hi_taskinst m"
				+ "  on n.taskid = m.id_ where m.task_def_key_ = 'cwys' and m.proc_inst_id_ = (select PROCINSTID from cw_gwjdfbxzb"
				+ " where guid = '"+guid+"') and rownum = 1 ";
		return db.queryForMap(sql);
	}
	
	/**
	 * 根据项目id查询经费类型
	 * @param guid
	 * @return
	 */
	public String getJflxByGuid(String guid){
		String sql = "select jflx from CW_JFSZB where xmbh='"+guid+"' or guid='"+guid+"'";
		sql = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "");
		return sql;
	}
	public String getry(String value) {
		
		value = "%"+value+"%";
		String sql = "select '('||r.rybh||')'||r.xm ryxx1 from gx_sys_ryb r where '('||r.rybh||')'||r.xm like ? ";
		StringBuffer suggestXx = new StringBuffer(); 
		List list = db.queryForList(sql,new Object[]{value});
		return suggestXx.toString();
	}
	/**
	 * 往来单位银行账号
	 * 
	 * @param wlbh
	 * @return
	 */
    public List getdwjc(String dwmc) {
    		StringBuffer sql = new StringBuffer();
    		sql.append(" select");
    		sql.append(" t.*,m.* from Cw_wldwmxb t left join cw_wldwb m on m.guid = t.wlbh");
    		sql.append(" where 1 = 1 and t.wlbh = (select guid from CW_WLDWB where dwmc ='"+dwmc+"')");
    		List list = new ArrayList<Map<String, Object>>();
    		list = db.queryForList(sql.toString());
    		return list;
	}
	//删除 日常报销 项目名下表
    public void deleteXmmx(String zbid) {
		String delSql = "delete from CW_RCBXXMMXB where zbid=?";
		int m = db.update(delSql, zbid);
	}
    //删除发票信息
    public void deleteFpxx(String zbid) {
    	String delSql = "delete cw_fpxxb where zbid=?";
    	int m = db.update(delSql, zbid);
    }
	//项目明细表 新增
    public int doAddXmmx(Rcbxmx rcbxmx,String zbid) {
    	String sql = "select count(*) from CW_RCBXXMMXB where zbid='"+zbid+"' and xmguid='"+rcbxmx.getXmguid()+"'";
    	int i = db.queryForInt(sql);
    	if(i>0){
    		String sql1=" update CW_RCBXXMMXB set bcbxje='"+rcbxmx.getBcbxje()+"' where zbid='"+zbid+"' and xmguid='"+rcbxmx.getBcxmguid()+"' ";
    		db.update(sql1);
    	}else{
    		String sql2 = "insert into CW_RCBXXMMXB values(sys_guid(),'"+rcbxmx.getSyje()+"','"+rcbxmx.getBxje()+"','"+rcbxmx.getXmmc()+"','"+zbid+"','"+rcbxmx.getXmguid()+"')";
    		db.update(sql2);
    	}
    	return 1;
    	
	}
    //发票信息 新增
    public int doAddFpxx(Rcbxmx rcbxmx,String zbid) {
    	String sql=" insert into cw_fpxxb values(sys_guid(),'"+zbid+"','"+rcbxmx.getFpxx1()+"','"+rcbxmx.getFpxx2()+"','"+rcbxmx.getFpxx3()+"','"+rcbxmx.getFpxx4()+"','"+rcbxmx.getFpxx5()+"') ";
    	return db.update(sql);
    }
    //项目回显 list
    public List getXmhxList(String zbid) {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT guid,xmmc,xmguid,to_char(syje,'FM999999999999999999999999999.00') as syje,"
				+ "t.fjzs,t.bz,t.fymc as fyid,(select kmmc from CW_JJKMB km where km.kmbh=t.fymc)as fymc, "
				+ "to_char(bxje,'FM999999999999.00') as bxje,ZFCGJE, " +
				" ZFCGSYJE,FZFCGJE,FZFCGSYJE " +
				" FROM cw_rcbxmxb t WHERE zbid = '"+zbid+"'");
		List xmlist = new ArrayList<Map<String, Object>>();
		xmlist = db.queryForList(sql.toString());
		return xmlist;
	}
    //保存项目信息 
    public int bcxmxx(String xmguids,String xmmcs,String moneys,String zbid,String zfcgsyje,String fzfcgsyje) {
    	String xmguid [] = xmguids.split(",");
		String xmmc [] = xmmcs.split(",");
		String money [] = moneys.split(",");
		String zfcgsyje1 [] = zfcgsyje.split(",");
		String fzfcgsyje1 [] = fzfcgsyje.split(",");
		String sql2="";
		String sql3="";
		int a=0 ;
		//根据项目guid，若查询到值执行update，否则执行insert
		for(int i=0;i<xmguid.length;i++) {
			String sql = "select count(*) from CW_RCBXMXB where zbid='"+zbid+"' and xmguid='"+xmguid[i]+"'";
	    	int j = db.queryForInt(sql);
	    	if(j>0){
	    		String sql1=" update CW_RCBXMXB set xmmc='"+xmmc[i]+"',syje='"+money[i]+"',zfcgsyje='"+Validate.isNullToDefault(zfcgsyje1[i], 0.00)+"',fzfcgsyje='"+Validate.isNullToDefault(fzfcgsyje1[i], 0.00)+"' where zbid='"+zbid+"' and xmguid='"+xmguid[i]+"' ";
	    		db.update(sql1);
	    	}else{
	    		sql3="delete from CW_RCBXMXB where zbid='"+zbid+"' ";
				sql2=" insert into CW_RCBXMXB(guid,xmmc,syje,zbid,xmguid,zfcgsyje,fzfcgsyje) VALUES(sys_guid(),'"+xmmc[i]+"','"+money[i]+"','"+zbid+"','"+xmguid[i]+"','"+Validate.isNullToDefault(zfcgsyje1[i], 0.00)+"','"+Validate.isNullToDefault(fzfcgsyje1[i], 0.00)+"' )";
				  db.update(sql3);
				a=db.update(sql2);
			}
		}
    	return a;
    }
    /**
	 * 删除日产日常报销 + 删除 退回凭证
	 * @param gsbh
	 * @return
	 */
	public int doDelete(String guid,String type) {
		String[] sqls = new String[10];
		if("rcbx".equals(type)){
					sqls = new String[6];
					String zbzd = "guid ,djbh,bxr ,szbm ,xmmc,fjzzs, bxzje ,sfkylbx,sfgwk,sfcjk,sfdgzf,sfdszf,bxsy, bz, shzt,shyj ,shr ,shrq, czrq,czr,procinstid ,checkshzt ,sfxy ,sfwqbc  ";
					sqls[0] = "insert into cw_rcbxzb_beifen ("+zbzd +") select "+zbzd+" from cw_rcbxzb where guid='"+guid+"'";
					String mxzd = "guid ,fymc ,bz , fjzs,bxje ,zbid,xmmc ,syje ,xmguid ,zfcgje ,zfcgsyje,fzfcgje ,fzfcgsyje ";
					sqls[1] = "insert into cw_rcbxmxb_beifen ("+mxzd+") select "+mxzd+" from cw_rcbxmxb where zbid = '"+guid+"'";
					String fpzd = "guid ,zbid ,fph1 ,fph2 , fph3 ,fph4 ,fph5 ";
					sqls[2] = "insert into cw_fpxxb_beifen ("+fpzd+") select "+fpzd+" from cw_fpxxb where zbid = '"+guid+"'";
					//查询是否冲借款  吧冲的钱 再 变成 需要还的钱
					String djbh  = "select jkid from CW_CJKB  where jkdh = '"+guid+ "'";
					String res = db.queryForSingleValue(djbh);
					if(Validate.noNull(res)){
						String fjk = "update CW_JKYWB set syje = syje + (select cjkje from CW_CJKB  where jkdh = '"+guid+ "') where djbh ='"+res+"'";
					    db.execute(fjk);
					}
					sqls[3] = "DELETE cw_rcbxmxb WHERE zbid = '"+guid+"'";
					sqls[4] = "DELETE cw_rcbxzb WHERE guid = '"+guid+"'";
					sqls[5] = "DELETE cw_fpxxb WHERE zbid = '"+guid+"'";
		}else if("clfbx".equals(type)){
				    sqls = new String[6];
				    String mxzd = "guid,djbh,rylx,xm,bxjb,kssj,jssj,cfdd,mddd,fjzs,hjje,fjp,hcp,czcp,qcp,qtfy,hyfy,pxfy,jsshbzts,xsshbzts,zsfje,snjtbzf,jsbzbz,snjtbzbz,zsfbzbz,provinceid,cityid,zbguid,xh,jsshbzje,xsshbzje ";
				    sqls[0] = "insert into cw_clfbxmxb_beifen ("+mxzd+") select "+mxzd+" from cw_clfbxmxb where zbguid='"+guid+"'";
				    String zbzd = "guid,djbh,sqr,kssj,jssj,cfdd,mddd,jflx,ktmc,sfgwk,sfcjk,sfdgzf,sfdszf,bxzje,fjzzs,bz,shzt,shyj,shr,shrq,czrq,czr,u1,u2,u3,u4,u5,u6,u7,u8,u9,u10,xmmc,cclx,ccrs,ccts,sfkylbx,ccsy,sfxy,procinstid,ccywguid,zfcgbxje,fzfcgbxje,checkshzt,sfwqbc"; 
				    sqls[1] = "insert into cw_clfbxzb_beifen ("+zbzd+") select "+zbzd+" from cw_clfbxzb where guid = '"+guid+"'";
				    String fpzd = "guid ,zbid ,fph1 ,fph2 , fph3 ,fph4 ,fph5";
				    sqls[2] = "insert into cw_fpxxb_beifen ("+fpzd+") select "+ fpzd+" from cw_fpxxb where zbid = '"+guid+"'";
				   //查询是否冲借款  吧冲的钱 再 变成 需要还的钱
					String djbh  = "select jkid from CW_CJKB  where jkdh = '"+guid+ "'";
					String res = db.queryForSingleValue(djbh);
					if(Validate.noNull(res)){
						String fjk = "update CW_JKYWB set syje = syje + (select cjkje from CW_CJKB  where jkdh = '"+guid+ "') where djbh ='"+res+"'";
					    db.execute(fjk);
					}
				    sqls[3] = "DELETE cw_clfbxmxb WHERE zbguid ='"+guid+"'";
					sqls[4] = "DELETE cw_clfbxzb WHERE guid='"+guid+"'";
					sqls[5] = "DELETE cw_fpxxb WHERE zbid ='"+guid+"'";
		}else if("gwjdbx".equals(type)){
		    sqls = new String[2];
		    String zbzd = "guid,djbh,bxry,szbm,bxje,jdcs,jdrq,lbry,ptry,jdsy,gwhdxm,shzt,shyj,shr,shrq,czr,czrq,bxryid,sfxy,sfcjk,sfdgzf,sfdszf,sfgwk,procinstid,lbdw,jdbm,checkshzt,fyfjzs,sfwqbc";
		    sqls[0] = "insert into cw_gwjdfbxzb_beifen ("+zbzd+") select "+zbzd+" from cw_gwjdfbxzb where guid='"+guid+"'";
		   //查询是否冲借款  吧冲的钱 再 变成 需要还的钱
			String djbh  = "select jkid from CW_CJKB  where jkdh = '"+guid+ "'";
			String res = db.queryForSingleValue(djbh);
			if(Validate.noNull(res)){
				String fjk = "update CW_JKYWB set syje = syje + (select cjkje from CW_CJKB  where jkdh = '"+guid+ "') where djbh ='"+res+"'";
			    db.execute(fjk);
			}
		    sqls[1] = "DELETE cw_gwjdfbxzb WHERE guid ='"+guid+"'";
		}else if("jk".equals(type)){
		    sqls = new String[2];
		    String jkzd = "jkr,djbh,szbm,jksj,jkzje,jksy,gid,shzt,shyj,zffs,shr,shrq,czr,czrq,sxfy,sqrxm,procinstid,sqrq,fjzs,syje,jbr";
		    sqls[0] = "insert into CW_JKYWB_beifen ("+jkzd+") select "+jkzd+" from CW_JKYWB where guid='"+guid+"'";
		    sqls[1] = "DELETE CW_JKYWB WHERE guid ='"+guid+"'";
		}
		try {
			db.batchUpdate(sqls);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return 1;
	}
    public List findxmlist(String zbid) {
    	List list =null;
    	String sql="select xmguid from  CW_RCBXXMMXB where zbid='"+zbid+"'  ";
    	try {
			list=db.queryForList(sql);
		} catch (Exception e) {
		}
    	return list;
    }
    //删除项目信息 
    public int bcxmxxsc(String zbid) {
    	String sql="delete from CW_RCBXXMMXB where zbid='"+zbid+"' ";
    	return db.update(sql);
    }
    //查询 报销总额 list
    public List<Map<String,Object>> getbxzelist(String guid) {
    	StringBuffer sql = new StringBuffer();
		sql.append(" select guid,bcbxje,xmguid from CW_RCBXXMMXB where zbid='"+guid+"' ");
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		list = db.queryForList(sql.toString());
		return list;
    }
    //获取发票信息
	public List getFpxx(String zbid) {
		String sql = "select * from cw_fpxxb where zbid='"+zbid+"'";
		return db.queryForList(sql);
	}
	/**
	 *验证实体
	 * @param 
	 * @return
	 */
	public Map<String, Object> getObjectByIdFyid(String fyid) {
		String sql="select count(*) as kmgs from CW_JJKMPZB p where kmbh=?";
		return db.queryForMap(sql,new Object[]{fyid});
	}
	
	/**
	 * 根据主表id查询项目
	 * @param zbid
	 * @return
	 */
	public String getXmGuidByZbId(String zbid){
		String sql = "select xmguid from cw_rcbxmxb where zbid=?";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql,new Object[]{zbid}), "");
	}

	public String getDjbh(String zbid) {
		String sql =  " select djbh from   Cw_rcbxzb t where guid = '"+zbid+"'";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 审核意见
	 * @param type
	 * @return
	 */
	public List getShyjListByLoginId(String type){
		String sql = "select * from cw_shyjb where shlx='"+type+"' and czr='"+LUser.getGuid()+"'";
		return db.queryForList(sql);
	}
	 /**
     * 修改保存  日常报销 凭证 修改信息
     * @param fjzzs
     * @param bxsy
     * @param guid
     * @return
     */
	public int updatePzthRC(Rcbxzb rcbx) {
		String sql = " update Cw_rcbxzb set fjzzs = ?,bxsy = ? where guid = ?";
		Object[] obj = new Object[]{rcbx.getFjzzs(),rcbx.getBxsy(),rcbx.getGuid()};
		return db.update(sql,obj);
	}
	public int doUpdateRcBxmx(String fymc, String fjzs, String bz, String guid) {
		String sql = "update CW_RCBXMXB set fymc=?,fjzs=?,bz=? where guid = ?";
		Object[] obj = new Object[]{fymc,fjzs,bz,guid};
		return db.update(sql, obj);
	}
	/**
     * 凭证复核 修改状态
     * @param guid
     * @return
     */
	public int RcbxSubmit(String guid) {
		String sql = " update Cw_rcbxzb set shzt = '8' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 * 获取申请人所在单位是否学院
	 * @param guid
	 * @return
	 */
	public String getSfxy(String guid) {
		String sql = "select sfxy from GX_SYS_dwb where dwbh in (select szbm from cw_rcbxzb where guid ='"+guid+"')";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 批量审核获取申请人所在单位是否学院
	 * @param guid
	 * @return
	 */
	public String getSfxypl(String guid) {
		String sql = "select * from GX_SYS_dwb where dwbh in (select szbm from cw_rcbxzb where guid in ('"+guid+"'))";
		String sql1 = "select * from GX_SYS_dwb where dwbh in (select szbm from cw_rcbxzb where guid in ('"+guid+"')) and sfxy = '0'";
		List list1 = db.queryForList(sql);
		List list2 = db.queryForList(sql1);
		String flag = "";
		if(list1.size()==list2.size()){
			flag = "0";
		}else if(list2.size()==0){
			flag = "1";
		}else{
			flag = "2";
		}
		return flag;
	}
}
