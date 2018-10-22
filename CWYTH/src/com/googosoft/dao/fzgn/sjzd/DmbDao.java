package com.googosoft.dao.fzgn.sjzd;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.sjzd.GX_SYS_DMB;
import com.googosoft.util.CommonUtils;

/**
 * 数据字典dao
 * @author googosoft
 *
 */
@Repository("dmbDao")
public class DmbDao extends BaseDao{
	private Logger logger = Logger.getLogger(DmbDao.class);
	
	/**
	 * 添加数据字典信息（保存）
	 * @param dmb
	 * @return
	 */
	public int doAdd(GX_SYS_DMB dmb) {
		String sql = "insert into %SDMB(dmxh,dm,mc,jb,bz,zl,dmsx,ms,sjqc)values(?,?,?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{dmb.getDmxh(),dmb.getDm(),dmb.getMc(),dmb.getJb(),dmb.getBz(),dmb.getZl(),dmb.getDmsx(),dmb.getMs(),dmb.getSjqc()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改数据字典信息
	 * @param dmb
	 * @return
	 */
	public int doUpdate(GX_SYS_DMB dmb) {
		String sql = "UPDATE %SDMB SET DM=?,MC=?,JB=?,BZ=?,DMSX=?,MS=?,ZL=?,SJQC=? WHERE DMXH=?";
		sql=String.format(sql, SystemSet.gxBz);
		int i = 0;
		try{
			i = db.update(sql,new Object[]{dmb.getDm(),dmb.getMc(),dmb.getJb(),dmb.getBz(),dmb.getDmsx(),dmb.getMs(),dmb.getZl(),dmb.getSjqc(),dmb.getDmxh()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 保存时验证代码是否重复（增加和修改都要验证）
	 * @param dmb
	 * @param operateType
	 * @return
	 */
	public boolean doCheckDm(GX_SYS_DMB dmb, String operateType){
		String a = "";
		if("C".equals(operateType)){
			String sql = "select count(*) from %SDMB where (DM = ? or mc=?) and zl = ?";
			sql=String.format(sql, SystemSet.gxBz);
			a = db.queryForObject(sql, new Object[]{dmb.getDm(), dmb.getMc(),dmb.getZl()},String.class);
		}else if("U".equals(operateType)){
			String sql = "select count(*) from %SDMB where (DM = ? or mc=?) and DM <> ?  and zl = ?";
			sql=String.format(sql, SystemSet.gxBz);
			a = db.queryForObject(sql, new Object[]{dmb.getDm(),dmb.getMc(), dmb.getYzdm(),dmb.getZl()},String.class);
		}
		if("0".equals(a)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 根据主键获取数据字典的详细信息  进行修改操作等
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		String sql = "SELECT A.DM,A.MC,A.JB,A.BZ,A.DMXH,A.ZL,A.DMSX,A.MS,(SELECT '('||KK.DM||')'||KK.MC FROM GX_SYS_DMB KK WHERE KK.DM=A.ZL AND KK.ZL='00') AS SJQC FROM %SDMB A WHERE A.DMXH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dmxh});
	}
	/**
	 * 删除数据字典信息
	 * @param dmxh
	 * @return
	 */
	public int doDelete(String dmxh) {
		String sql = "DELETE %SDMB WHERE DMXH "+CommonUtils.getInsql(dmxh);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = dmxh.split(",");
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 检查代码名称是否重复
	 */
	public int checkMc(GX_SYS_DMB dmb,String mc) {
		ArrayList a=new ArrayList<>();	
		String sql = "select dm from %SDMB  where mc=?";
		sql=String.format(sql, SystemSet.gxBz);
		a = (ArrayList) db.queryForList(sql, new Object[]{mc}, String.class);		
		if(a.size()==0) {	
			return 0;
		}else {
			return 1;
		}
	}
}