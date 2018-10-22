package com.googosoft.dao.pjgl.rcyw;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;

@Repository("pjdyDao")
public class PjdyDao extends BaseDao{
	private Logger logger = Logger.getLogger(PjdyDao.class);
	 /**
     *  获取   增值税专用发票  信息
     * @param gid
     * @return
     */
	public Map getZzsXX(String gid) {
		String sql = " select to_char(CPRQ,'yyyy-mm-dd HH24:mm:ss') as cprq,pjh, FPDM, je, skr,GHDWSBM,"
				+" GHDWDH,GHDWDZ, SKYH,SKZH,CPR,SHDWSBM,SHDWDH,SHDWDZ, CPYH,CPZH,KPR,ZDR "
				+ " from cw_pj where gid = '"+ gid +"'";
		return db.queryForMap(sql);
	}
	 /**
     *  获取   收据  发票  信息
     * @param gid
     * @return
     */
	public Map getSjXX(String gid) {
		String sql = " select to_char(CPRQ,'yyyy-mm-dd HH24:mm:ss') as cprq, PJH, je,SKR, YT,FJXX,"
				+ "  to_char(NYR,'yyyy-mm-dd') as NYR, KPR,ZDR "
				+ "  from cw_pj where gid = '"+ gid +"'";
		return db.queryForMap(sql);
	}
	/**
     *  获取   普通  发票  信息
     * @param gid
     * @return
     */
	public Map getPtfpXX(String gid) {
		String sql = " select to_char(CPRQ,'yyyy-mm-dd HH24:mm:ss') as cprq, pjh, fpdm,je,skr, cpr,"
				+ " shdwsbm,shdwdh,shdwdz,cpyh,cpzh, kpr,zdr  "
				+ " from cw_pj where gid = '"+ gid +"'";
		return db.queryForMap(sql);
	}
	/**
     *  获取   专用支票   信息
     * @param gid
     * @return
     */
	public Map getZyzpXX(String gid) {
		String sql = " select to_char(CPRQ,'yyyy-mm-dd HH24:mm:ss') as cprq, pjh,  fpdm,je,skr, "
				+ " ghdwsbm,ghdwdh,ghdwdz,skyh, skzh,cpr, shdwsbm, shdwdh, shdwdz, cpyh,cpzh, kpr,zdr "
				+ " from cw_pj where gid = '"+ gid +"'";
		return db.queryForMap(sql);
	}
	/**
	 * 修改
	 * @param pd
	 * @return
	 */
	public int doSaveSj(PageData pd) {
		String cprq = pd.getString("cprq");
		String nyr =  pd.getString("nyr");
		String sql = " update cw_pj set cprq=to_date('"+cprq+"','yyyy-mm-dd HH24:mi:ss'), PJH=?, je=?,SKR=?, YT=?,"
				+ " fjxx=? , NYR=to_date('"+nyr+"','yyyy-mm-dd'), KPR=?,ZDR=? "
				+ "   where gid = ?";
		Object[] obj = {
				
				pd.getString("pjh"),
				pd.getString("je"),
				pd.getString("skr"),
				pd.getString("yt"),
				pd.getString("fjxx"),
				
				pd.getString("kpr"),
				pd.getString("zdr"),
				pd.getString("gid"),
		};
		return db.update(sql, obj);
	}
	public int doSavePtfp(PageData pd) {
		String cprq = pd.getString("cprq");
		
		String sql = " update cw_pj set cprq=to_date('"+cprq+"','yyyy-mm-dd HH24:mi:ss'), pjh=?, fpdm=?,je=?,skr=?, cpr=?,"
				+ " shdwsbm=?,shdwdh=?,shdwdz=?,cpyh=?,cpzh=?, kpr=?,zdr=?  "
				+ "   where gid = ?";
		Object[] obj = {
				pd.getString("pjh"),
				pd.getString("fpdm"),
				pd.getString("je"),
				pd.getString("skr"),
				pd.getString("cpr"),
				
				pd.getString("shdwsbm"),
				pd.getString("shdwdh"),
				pd.getString("shdwdz"),
				pd.getString("cpyh"),
				pd.getString("cpzh"),
				pd.getString("kpr"),
				pd.getString("zdr"),
				pd.getString("gid"),
		};
		return db.update(sql, obj);
	}
	public int doSaveZyfp(PageData pd) {
		
		String cprq = pd.getString("cprq");
		
		String sql = " update cw_pj set cprq=to_date('"+cprq+"','yyyy-mm-dd HH24:mi:ss'), pjh=?, fpdm=?,je=?,skr=?, "
				+ " kpr=?,zdr=? "
				+ "  where gid = ? ";
		Object[] obj = {
				pd.getString("pjh"),
				pd.getString("fpdm"),
				pd.getString("je"),
				pd.getString("skr"),
				
				
				pd.getString("kpr"),
				pd.getString("zdr"),
				pd.getString("gid"),
		};
		return db.update(sql, obj);
	}
	public int doSaveZzs(PageData pd) {
		String cprq = pd.getString("cprq");
		
		String sql = " update cw_pj set cprq=to_date('"+cprq+"','yyyy-mm-dd HH24:mi:ss'),pjh=?, FPDM=?, je=?, skr=?,GHDWSBM=?,"
				+" GHDWDH=?,GHDWDZ=?, SKYH=?,SKZH=?,CPR=?,SHDWSBM=?,SHDWDH=?,SHDWDZ=?, CPYH=?,CPZH=?,KPR=?,ZDR =?"
				+ " where gid = ?";
		Object[] obj = {
				pd.getString("pjh"),
				pd.getString("fpdm"),
				pd.getString("je"),
				pd.getString("skr"),
				pd.getString("ghdwsbm"),
				pd.getString("ghdwdh"),
				
				pd.getString("ghdwdz"),
				pd.getString("skyh"),
				pd.getString("skzh"),
				pd.getString("cpr"),
				pd.getString("shdwsbm"),
				pd.getString("shdwdh"),
				pd.getString("shdwdz"),
				
				pd.getString("cpyh"),
				pd.getString("cpzh"),
				pd.getString("kpr"),
				pd.getString("zdr"),
				pd.getString("gid"),
		};
		return db.update(sql, obj);
	}

}
