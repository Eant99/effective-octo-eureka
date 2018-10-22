package com.googosoft.dao.srgl.xzsblr;

import groovy.sql.Sql;

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
import com.googosoft.pojo.srgl.xzsblr.CW_GRSRZB;
import com.googosoft.pojo.wsbx.CW_CJKB;
import com.googosoft.pojo.wsbx.CW_DGZFB;
import com.googosoft.pojo.wsbx.CW_FJXXB;
import com.googosoft.pojo.wsbx.CW_GWKB;
import com.googosoft.pojo.wsbx.Cw_DSZFB;
import com.googosoft.pojo.wsbx.Rcbxmx;
import com.googosoft.pojo.wsbx.Rcbxzb;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Repository("xzsblrDao")
public class XzsblrDao extends BaseDao {
	private Logger logger = Logger.getLogger(XzsblrDao.class);
	/**
	 * 获取发放批次
	 * 
	 * @param zbid
	 * @return
	 */
	public String getFfpc() {
		String sql = "select (case (select count(*) from cw_xzb where shzt <> '0') when 0 then to_char(sysdate,'yyyy.mm') else (select max(gzyf) from cw_xzb where shzt <> '0') end) from dual";
		return db.queryForSingleValue(sql);
	}
	/**
	 *  获取个人收入经济科目数据
	 * 
	 * @param zbid
	 * @return
	 */
	public List getJjkm() {
		String sql = "select kmbh,kmmc from cw_jjkmb where sfgrsr = '1'";
		return db.queryForList(sql);
	}
	/**
	 *  获取学生个人收入明细表数据
	 * 
	 * @param zbid
	 * @return
	 */
	public List getAllxsxx(String guid) {
		String sql = " select t.xh,t.xm,t.sfzh,to_char(k.ffje,'fm99999999999990.00') as ffje,k.jjkm,k.yhkh,k.rybh from cw_grsrmxb  k  left join cw_xsxxb t on k.rybh=t.guid  where fflsh = (select fflsh from cw_grsrzb where guid = '"+guid+"')";
		return db.queryForList(sql);
	}
	/**
	 *  获取教师个人收入明细表数据
	 * 
	 * @param zbid
	 * @return
	 */
	public List getAlljsxx(String guid) {
		String sql = " select t.xh,t.xm,t.sfzh,to_char(k.ffje,'fm99999999999990.00') as ffje,k.jjkm,k.yhkh,k.rybh,k.bqks,to_char(k.sfje,'fm99999999999990.00') AS sfje from cw_grsrmxb  k  left join cw_jzgxxb t on k.rybh=t.guid  where fflsh = (select fflsh from cw_grsrzb where guid = '"+guid+"')";
		return db.queryForList(sql);
	}
	/**
	 *  获取校外人员个人收入明细表数据
	 * 
	 * @param zbid
	 * @return
	 */
	public List getAllxwryxx(String guid) {
		String sql = " select t.xh,t.xm,t.sfzh,to_char(k.ffje,'fm99999999999990.00') as ffje,k.jjkm,k.yhkh,k.rybh from cw_grsrmxb  k  left join cw_xwryxxb t on k.rybh=t.guid  where fflsh = (select fflsh from cw_grsrzb where guid = '"+guid+"')";
		return db.queryForList(sql);
	}
	/**
	 *  获取学生个人银行卡信息
	 * 
	 * @param zbid
	 * @return
	 */
	public List getXxyhk() {
		String sql = " select yhmc,khyhh,('('||khyhh||')'||yhmc)as yhxx from cw_yhzhb where jsbh is not null";
		return db.queryForList(sql);
	}
	/**
	 *  获取教师个人银行卡信息
	 * 
	 * @param zbid
	 * @return
	 */
	public List getJsyhk() {
		String sql = " select khyh,khyhzh,('('||khyhzh||')'||khyh)as yhxx from cw_jsyhzhb where jsbh is not null";
		return db.queryForList(sql);
	}
	/**
	 *  获取校外人员个人银行卡信息
	 * 
	 * @param zbid
	 * @return
	 */
	public List getXwryyhk() {
		String sql = " select yhmc,khyhh,('('||khyhh||')'||yhmc)as yhxx from cw_xwryxxb";
		return db.queryForList(sql);
	}
	/**
	 *  获取个人收入经济科目
	 * 
	 * @param zbid
	 * @return
	 */
	public Map getAll(String guid) {
		String sql = " select distinct* from (select k.guid,k.fflsh,k.fffa,k.fffs,k.mbbz," 
				   + " k.ffpc,k.zy,TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,K.XMBH,t.jjkm," 
				   + " (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC" 
				   + " FROM cw_grsrzb K left join cw_grsrmxb t on k.fflsh=t.fflsh where K.guid='"+guid+"')";
		return db.queryForMap(sql);
	}
	/**
	 *  获取个人收入模版信息
	 * 
	 * @param zbid
	 * @return
	 */
	public Map getAllmb(String guid) {
		String sql = " select distinct* from (select k.guid,k.fflsh,k.fffa,k.fffs,k.mbbz," 
				   + " k.ffpc,k.zy,TO_CHAR(k.LRSJ,'YYYY-MM-DD')LRSJ,K.XMBH,t.jjkm," 
				   + " (SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC" 
				   + " FROM cw_grsrzb K left join cw_grsrmxb t on k.fflsh=t.fflsh where K.guid='"+guid+"' and k.mbbz='1')";
		return db.queryForMap(sql);
	}
	/**
	 * 增加主表和分表的信息
	 * @param zxxm
	 * @return
	 */
	public int doAdd(CW_GRSRZB grsrzb,String rybhs, String ffjes,String yhkhs) {
		String rybh[] = rybhs.split(",",-1);
		String ffje[] = ffjes.split(",",-1);
		String yhkh[] = yhkhs.split(",",-1);
		String ffpc= grsrzb.getFfpc();
		ArrayList lists=new ArrayList();
		ArrayList objcs=new ArrayList();
		float zjes = Float.valueOf(grsrzb.getFfzje());
		String sql = "INSERT INTO CW_GRSRZB(GUID,FFLSH,FFFA,XMBH,FFFS,FFPC,ZY,FFZJE,JBR,LRSJ,FFRY,SHZT,MBBZ,JSBZ,JBRSSDW,checkshzt)"   
				+ "VALUES(?,?,?,?,?,?,?,?,?,sysdate,?,?,?,0,?,?)";
				Object[] obj = new Object[]{
					grsrzb.getGuid(),
					grsrzb.getFflsh(),
					grsrzb.getFffa(),
					grsrzb.getXmbh(),
					grsrzb.getFffs(),
					grsrzb.getFfpc(),
					grsrzb.getZy(),
					zjes,
					CommonUtils.getRybh(),
					grsrzb.getFfry(),
					"00",
					grsrzb.getMbbz(),
					LUser.getDwbh(),
					"00"
				};
		lists.add(sql);
		objcs.add(obj);
		String sql0 = "";
		Object[] obj0 = {};
		for(int i=0;i<rybh.length;i++){
				 String jec = Validate.isNullToDefaultString(ffje[i], "0");
				 float jes = Float.valueOf(jec);
				 if(jes > 0){
					 sql0 = "insert into cw_grsrmxb (GUID,FFLSH,RYBH,FFJE,JJKM,FFSJ,JBR,yhkh,ffpc,jsbz)"
							+ " values(?,?,?,?,?,sysdate,'"+CommonUtils.getRybh()+"',?,?,0)";
					 obj0 = new Object[] {
							UuidUtil.get32UUID(),
							grsrzb.getFflsh(),
							rybh[i],
							ffje[i],
							grsrzb.getJjkm(),
							yhkh[i],
							ffpc
							};
					 lists.add(sql0);
					 objcs.add(obj0);
				 }
		}
		if(grsrzb.getFffs().equals("01")){//冲借款
			
		}else if(grsrzb.getFffs().equals("02")){//对公支付
			
		}else if(grsrzb.getFffs().equals("03")){//对私支付
			
		}else if(grsrzb.getFffs().equals("04")){//公务卡
			
		}
		int i = 0;
		boolean k=false;
		try {  
				k = db.batchUpdate(lists, objcs);
			if(k){
				i=1;
			}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
		
	}
	/**
	 * 更新主表信息
	 * @param zxxm
	 * @return
	 */
	public int doUpdate(CW_GRSRZB grsrzb,String rybhs, String ffjes,String yhkhs) {
		String sqldelmx = "delete cw_grsrmxb where fflsh = (select fflsh from cw_grsrzb where guid='"+grsrzb.getGuid()+"') ";
		int countdelmx = db.update(sqldelmx);
		String sqldelzb = "delete cw_grsrzb where guid='"+grsrzb.getGuid()+"' ";
		int countdelzb = db.update(sqldelzb);
		String rybh[] = rybhs.split(",",-1);
		String ffje[] = ffjes.split(",",-1);
		String yhkh[] = yhkhs.split(",",-1);
		String ffpc= grsrzb.getFfpc();
		ArrayList lists=new ArrayList();
		ArrayList objcs=new ArrayList();
		float zjes = Float.valueOf(grsrzb.getFfzje());
		if(countdelzb>0&&countdelmx>0){
			String sql = "INSERT INTO CW_GRSRZB(GUID,FFLSH,FFFA,XMBH,FFFS,FFPC,ZY,FFZJE,JBR,LRSJ,FFRY,SHZT,MBBZ,JSBZ,JBRSSDW,checkshzt)"   
					+ "VALUES(?,?,?,?,?,?,?,?,?,sysdate,?,?,?,0,?,?)";
					Object[] obj = new Object[]{
						grsrzb.getGuid(),
						grsrzb.getFflsh(),
						grsrzb.getFffa(),
						grsrzb.getXmbh(),
						grsrzb.getFffs(),
						grsrzb.getFfpc(),
						grsrzb.getZy(),
						zjes,
						CommonUtils.getRybh(),
						grsrzb.getFfry(),
						"00",
						grsrzb.getMbbz(),
						LUser.getDwbh(),
						"00"
					};
			lists.add(sql);
			objcs.add(obj);
			String sql0 = "";
			Object[] obj0 = {};
			for(int i=0;i<rybh.length;i++){
					 String jec = Validate.isNullToDefaultString(ffje[i], "0");
					 float jes = Float.valueOf(jec);
					 if(jes > 0){
						 sql0 = "insert into cw_grsrmxb (GUID,FFLSH,RYBH,FFJE,JJKM,FFSJ,JBR,YHKH,JSBZ,ffpc)"
								+ " values(?,?,?,?,?,sysdate,'"+CommonUtils.getRybh()+"',?,0,?)";
						 obj0 = new Object[] {
								UuidUtil.get32UUID(),
								grsrzb.getFflsh(),
								rybh[i],
								ffje[i],
								grsrzb.getJjkm(),
								yhkh[i],
								ffpc
								};
						 lists.add(sql0);
						 objcs.add(obj0);
					 }
			}
		}
		int i = 0;
		boolean k=false;
		try {  
				k = db.batchUpdate(lists, objcs);
			if(k){
				i=1;
			}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
		
	}
	/**
	 * 更新主表信息
	 * @param zxxm
	 * @return
	 */
	public int doTqmb(CW_GRSRZB grsrzb,String rybhs, String ffjes,String yhkhs,String fffs) {
		String rybh[] = rybhs.split(",",-1);
		String ffje[] = ffjes.split(",",-1);
		String yhkh[] = yhkhs.split(",",-1);
		String ffpc= grsrzb.getFfpc();
		ArrayList lists=new ArrayList();
		ArrayList objcs=new ArrayList();
		float zjes = Float.valueOf(grsrzb.getFfzje());
		String sql = "INSERT INTO CW_GRSRZB(GUID,FFLSH,FFFA,XMBH,FFFS,FFPC,ZY,FFZJE,JBR,LRSJ,FFRY,SHZT,MBBZ,jsbz,JBRSSDW,checkshzt)"   
				+ "VALUES(?,?,?,?,?,?,?,?,?,sysdate,?,?,?,0,?,?)";
				Object[] obj = new Object[]{
					UuidUtil.get32UUID(),
					grsrzb.getFflsh(),
					grsrzb.getFffa(),
					grsrzb.getXmbh(),
					fffs,
					grsrzb.getFfpc(),
					grsrzb.getZy(),
					zjes,
					CommonUtils.getRybh(),
					grsrzb.getFfry(),
					"00",
					"0",
					LUser.getDwbh(),
					"00"
				};
		lists.add(sql);
		objcs.add(obj);
		String sql0 = "";
		Object[] obj0 = {};
		for(int i=0;i<rybh.length;i++){
				 String jec = Validate.isNullToDefaultString(ffje[i], "0");
				 float jes = Float.valueOf(jec);
				 if(jes > 0){
					 sql0 = "insert into cw_grsrmxb (GUID,FFLSH,RYBH,FFJE,JJKM,FFSJ,JBR,yhkh,jsbz,ffpc)"
							+ " values(?,?,?,?,?,sysdate,'"+CommonUtils.getRybh()+"',?,0,?)";
					 obj0 = new Object[] {
							UuidUtil.get32UUID(),
							grsrzb.getFflsh(),
							rybh[i],
							ffje[i],
							grsrzb.getJjkm(),
							yhkh[i],
							ffpc
							};
					 lists.add(sql0);
					 objcs.add(obj0);
				 }
		}
		int i = 0;
		boolean k=false;
		try {  
				k = db.batchUpdate(lists, objcs);
			if(k){
				i=1;
			}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
		
	}
	/**
	 *  保存模版
	 * 
	 * @param zbid
	 * @return
	 */
	public int  doSavemb(String guid) {
		String sql = "update CW_GRSRZB set mbbz = '1' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 * 薪资申报录入信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getMapInfoByXzsbfAndGuid(String guid) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT * FROM CW_GRSRZB WHERE GUID=?");
		Map map = db.queryForMap(sql.toString(), new Object[] { guid });
		return map;
	}
	/**
	 * 查询审核人1
	 * 
	 * @param guid
	 * @return
	 */
	public String getShrList(String rybh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT guid FROM gx_sys_ryb WHERE rybh=?");
		return db.queryForSingleValue(sql.toString(), new Object[] { rybh });
		 
	}
	/**
	 * 查询审核人2
	 * 
	 * @param guid
	 * @return
	 */
	public String getShr2List(String rybh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT guid FROM gx_sys_ryb WHERE rybh=?");
		return db.queryForSingleValue(sql.toString(), new Object[] { rybh });
	}
	/**
	 *  提交操作
	 * 
	 * @param zbid
	 * @return
	 */
	public int  submit(String shzt, String tableName,String procinstid, String shyj, String guid) {
		String sql = "update "+tableName+" set shzt = '"+shzt+"',PROCINSTID = '"+procinstid+"' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 *  通过操作
	 * 
	 * @param zbid
	 * @return
	 */
	public int  pass(String guid) {
		String sql = "update CW_GRSRZB set shzt = '99' where guid = '"+guid+"'";
		return db.update(sql);
	}
	
	/**
	 *  退回操作
	 * 
	 * @param zbid
	 * @return
	 */
	public int  nopass(String guid) {
		String sql = "update CW_GRSRZB set shzt = '55' where guid = '"+guid+"'";
		return db.update(sql);
	}
	
	/**
	 *  核算操作
	 * 
	 * @param zbid
	 * @return
	 */
	public int doAccount(String guid) {
		String login = LUser.getGuid();
		String procName = "PROC_XZFFHS";
		List parInList = new ArrayList<String>();
		boolean bool = false;	
		parInList.add(guid);
		try {
			System.err.println("parInList="+parInList);
			System.err.println("procName="+procName);
			bool = db.batchUpdateByProcedure(procName, parInList);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(bool){
			return 1;
		}
		return -1;
	}
	
	
	
	
	
	/**
	 *  撤销提交
	 * 
	 * @param zbid
	 * @return
	 */
	public int  chexiao(String guid) {
		String sql = "update CW_GRSRZB set shzt = '00' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 * 删除模版
	 * @param guid
	 * @return
	 */
	public int doDelmb(String guid){
		String sql = "update CW_GRSRZB set mbbz = '0' where guid in('"+guid+"')";
		int m = db.update(sql);
		return m;
	}
	/**
	 * 删除薪资申报录入信息
	 * @param guid
	 * @return
	 */
	public int doDelete(String guid){
		String[] sqls = new String[2];
			sqls[0] = "delete cw_grsrmxb where fflsh in (select fflsh from cw_grsrzb where guid"+ CommonUtils.getInsql(guid)+") ";
			sqls[1] = "delete cw_grsrzb where guid"+ CommonUtils.getInsql(guid);
		Object[] obj = guid.split("','");
		try {
			db.batchUpdate(sqls, obj);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return 1;
	}
}
