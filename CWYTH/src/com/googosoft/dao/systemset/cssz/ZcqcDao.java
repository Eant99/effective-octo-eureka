package com.googosoft.dao.systemset.cssz;

import java.sql.SQLException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

@Repository("zcqcDao")
public class ZcqcDao extends BaseDao{
	private Logger logger = Logger.getLogger(ZcqcDao.class);
	/**
	 * 添加资产自查新过程
	 */
	public int doSave(String flag,String qcbh) {
		String sql ="";
		if("zcqc".equals(flag)){
			sql = "insert into zc_zcqczt(qcbh,qcms,ksrq,zt)values(?,TO_char(SYSDATE, 'yyyy-mm-dd') || '资产清查', SYSDATE, '2')";
		}else{
			sql = "insert into zc_zczczt(qcbh,qcms,ksrq,zt)values(?,TO_char(SYSDATE, 'yyyy-mm-dd') || '资产自查', SYSDATE, '2')";
		}
		int i = 0;
		try{
			i = db.update(sql,new Object[]{qcbh});
			if("zcqc".equals(flag)){
				getZcqcProcedure(qcbh);
			}else{
				getZczcProcedure(qcbh);
			}
		}catch (DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n"+sqle);
			return -1;
		}
		return i;
	}
	/**
	 * 开始清查
	 */
	public int doStart(String flag,String qcbh) {
		String sql = "update "+("zcqc".equals(flag)?"zc_zcqczt":"zc_zczczt")+" set zt='1' where qcbh='"+qcbh+"'";
		int i = 0;
		try{
			i = db.update(sql);
			if("zczc".equals(flag)){
				db.update("update zc_zczcb set qczt='0' where qcbh='"+qcbh+"'");
			}
		}catch (DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n"+sqle);
			return -1;
		}
		return i;
	}
	/**
	 * 结束清查
	 */
	public int doEnd(String flag,String qcbh) {
		String sql = "update "+("zcqc".equals(flag)?"zc_zcqczt":"zc_zczczt")+" set jsrq=sysdate,zt='0' where qcbh='"+qcbh+"'";
		int i = 0;
		try{
			i = db.update(sql);
			if("zcqc".equals(flag)){
				db.update("update zc_zcqcb set qcqk='3' where nvl(qcqk,'0')='0' and qcbh='"+qcbh+"' ");
			}
		}catch (DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n"+sqle);
			return -1;
		}
		return i;
	}
	/**
	 * 	添加新过程，判断是否存在已添加的，或者正在进行的数据，有，不进行添加
	 */
	public int doCheck(String flag) {
		String sql="select count(1) from "+(flag.equals("zcqc")?"ZC_ZCQCZT":"ZC_ZCZCZT")+" where zt in('1','2')";
		String count  = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return 0;
		}else{
			return 1;
		}
	}
	/**
	 * 资产自查设置
	 */
	public void getZczcProcedure(final String qcbh){
		String procName = "{CALL PROC_SYS_COPYTOZCZCB('"+qcbh+"','')}"; //"+qcbh+" 变量      '"+qcbh+"'字符串变量
		db.execute(procName);
	}
	/**
	 * 资产清查设置
	 */
	public void getZcqcProcedure(final String qcbh){
		String procName = "{CALL PROC_SYS_COPYTOZCQCB('"+qcbh+"','')}";
		db.execute(procName);
	}
}
