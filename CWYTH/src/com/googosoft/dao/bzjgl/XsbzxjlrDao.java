package com.googosoft.dao.bzjgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.ysgl.grjfsz.GrjfszDao;
import com.googosoft.pojo.bzjgl.CW_XSBZMXB;
import com.googosoft.pojo.bzjgl.CW_XSBZZB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Repository("xsbzxjlrDao")
public class XsbzxjlrDao extends BaseDao {
	private Logger logger = Logger.getLogger(GrjfszDao.class);
	//编辑查看
	public Map<?, ?> getObjectById(String guid) {
		String sql = "SELECT K.GUID,K.FABH,K.FAMC,K.ZY,k.XMBH,(SELECT '('||D.XMBH||')'||D.XMMC FROM XMINFOS D WHERE D.guid=K.XMBH)AS XMMC,(SELECT MC FROM GX_SYS_DMB WHERE ZL = 'zffs' AND DM=K.FFFS) AS FFFS,K.JBR,K.BZ, " 
				+ "TO_CHAR(FFJE,'FM999999999999999999999999999999990.00') AS FFJE,TO_CHAR(FFSJ,'YYYY-MM-DD HH24:MI:SS') AS FFSJ FROM CW_XSBZZB K WHERE K.GUID='"+guid+"'";
		return db.queryForMap(sql);
	}
	//删除
	public int doDelete(String fabh) {
		String sql = "DELETE CW_XSBZZB WHERE FABH ='"+fabh+"'";
		String sql1 = "DELETE CW_XSBZMXB WHERE FABH ='"+fabh+"'";
		db.update(sql1);
		return db.update(sql);
	}
	//批量删除
	public int doDelete2(String guid) {
		String sql = "DELETE CW_XSBZZB WHERE GUID IN ('"+guid+"')";
		String sql1 = "DELETE CW_XSBZMXB WHERE FABH IN (select fabh from CW_XSBZZB where guid in ('"+guid+"'))";
		db.update(sql1);
		return db.update(sql);
	}
	//添加
	public int doAdd(CW_XSBZZB xsbzzb, String fabh,String ffzje, String xhs, String bzbhs,String yhmcs,String yhkhs, String ffjes) {
		String xh[] = xhs.split(",",-1);
		String bzbh[] = bzbhs.split(",",-1);
		String yhmc[] = yhmcs.split(",",-1);
		String yhkh[] = yhkhs.split(",",-1);
		String ffje[] = ffjes.split(",",-1);
		System.out.println(yhmc.length);
		System.out.println(ffjes.split(",",-1));
		ArrayList lists=new ArrayList();
		ArrayList objcs=new ArrayList();
		String sql = "INSERT INTO CW_XSBZZB(GUID,FABH,FAMC,ZY,XMBH,FFFS,FFJE,BZ,JBR,FFSJ)"   
				+ "VALUES(SYS_GUID(),?,?,?,?,?,?,?,?,sysdate)";
				Object[] obj = new Object[]{
					xsbzzb.getFabh(),
					xsbzzb.getFamc(),
					xsbzzb.getZy(),
					xsbzzb.getXmbh(),
					xsbzzb.getFffs(),
					xsbzzb.getFfje(),
					xsbzzb.getBz(),
					CommonUtils.getRybh()
				};
		lists.add(sql);
		objcs.add(obj);
		for(int i=0;i<xh.length;i++){
			 System.out.println("@@@1");
				 String jec = Validate.isNullToDefaultString(ffje[i], "0");
				 float jes = Float.valueOf(jec);
				 if(jes > 0){
					 System.out.println("@@@33");
					 String sql0 = "insert into cw_xsbzmxb (GUID,fabh,xh,bzbh,ffje,ffzje,yhmc,yhkh,szsj,jbr)"
							+ " values(SYS_GUID(),?,?,?,?,?,?,?,sysdate,'"+CommonUtils.getRybh()+"')";
					 Object[] obj0 = new Object[] {
							xsbzzb.getFabh(),
							xh[i],
							bzbh[i],
							jes,
							ffzje,
							yhmc[i],
							yhkh[i]
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
	//获得标准编号
	public List getDjlist() {
		String sql = " select bzbh,'('||bzbh||')'||bzmc as bzmc,TO_CHAR(BZJE,'FM99999999999990.00') AS BZJE from cw_bzbzszb where sfqy='1'";
		return db.queryForList(sql);
	}
	//获得学生补助学金录入明细
	public List getBzxjlrmc(String guid) {
		 String sql = "SELECT K.GUID,K.FABH,K.XH,K.BZBH,T.XM,T.SFZH,(SELECT GUID FROM CW_XSXXB M WHERE M.XH=K.XH) AS JSBH,K.YHMC,K.YHKH, " 
		+ "TO_CHAR(K.FFJE,'FM999999999999999999999999999999990.00') AS FFJE FROM CW_XSBZMXB K LEFT JOIN CW_XSXXB T ON K.XH=T.XH  where k.fabh = (select fabh from CW_XSBZZB where guid = '"+guid+"')";
		 return db.queryForList(sql);
	}
	//修改学生补助学金录入信息
	public int doUpdate(CW_XSBZZB xsbzzb, String fabh, String ffzje,
		String xhs, String bzbhs, String yhmcs, String yhkhs, String ffjes) {
		String xh[] = xhs.split(",",-1);
		String bzbh[] = bzbhs.split(",",-1);
		String yhmc[] = yhmcs.split(",",-1);
		String yhkh[] = yhkhs.split(",",-1);
		String ffje[] = ffjes.split(",",-1);
		ArrayList lists=new ArrayList();
		ArrayList objcs=new ArrayList();
		//修改学生补助总表
		String sql = "UPDATE CW_XSBZZB SET ZY=?,XMBH=?,FFFS=?,FFJE=?,BZ=? WHERE GUID = ?";
		Object[] obj = new Object[]{
				xsbzzb.getZy(),
				xsbzzb.getXmbh(),
				xsbzzb.getFffs(),
				xsbzzb.getFfje(),
				xsbzzb.getBz(),
				xsbzzb.getGuid()
		};
		lists.add(sql);
		objcs.add(obj);
		for(int i=0;i<xh.length;i++){
				 String jec = Validate.isNullToDefaultString(ffje[i], "0");
				 float jes = Float.valueOf(jec);
				 if(jes > 0){
					 //先删除学生补助明细表中的数据再将数据插入学生补助明细表
					 String sql0 = "DELETE CW_XSBZMXB WHERE FABH ='"+fabh+"'";
					 db.update(sql0);
					 //将页面中的数据插入学生补助明细表
					 String sql1 = "INSERT INTO CW_XSBZMXB (GUID,FABH,XH,BZBH,FFJE,FFZJE,YHMC,YHKH,SZSJ,JBR)"
							+ " VALUES(SYS_GUID(),?,?,?,?,?,?,?,SYSDATE,'"+CommonUtils.getRybh()+"')";
					 Object[] obj0 = new Object[] {
							fabh,
							xh[i],
							bzbh[i],
							jes,
							ffzje,
							yhmc[i],
							yhkh[i]
							};
					 lists.add(sql1);
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
	 *  获取学生个人银行卡信息
	 * 
	 * @param zbid
	 * @return
	 */
	public List getXxyhk( ) {
		String sql = " select yhmc,khyhh,('('||khyhh||')'||yhmc)as yhxx from cw_yhzhb where jsbh is not null";
		return db.queryForList(sql);
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
}
