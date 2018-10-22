package com.googosoft.dao.fzgn.jcsz;

import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.ZC_SYS_DDB;
import com.googosoft.util.CommonUtils;

@Repository("ddbDao")
public class DdbDao extends BaseDao{
	private Logger logger = Logger.getLogger(DdbDao.class);	
	
	/**
	 * 
	 * @return
	 */
	public String getNewStatus(String sjdd) {
		String sql = "select count(1) from ZC_SYS_DDB t where t.sjdd='"+sjdd+"'and t.ddzt='1'";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 增加地点信息
	 * @param ddb
	 * @return
	 * 10/28整理
	 */
	public int doAdd(ZC_SYS_DDB ddb) {
		String sql = "insert into %SDDB(ddbh,mc,sjdd,dwbh,ddzt,ddjc,pxxh,ddh) values(?,?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.sysBz);
		Object[] obj = new Object[]{
				ddb.getDdbh(),
				ddb.getMc(),
				ddb.getSjdd(),
				ddb.getDwbh(),
				ddb.getDdzt(),
				ddb.getDdjc(),
				ddb.getPxxh(),
				ddb.getDdh()	
		};
		int i = 0;
		//1028新增异常捕获
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
	 * 修改地点信息 注意，顺序一定要相同，
	 * @param ddb 地点表
	 * @return  
	 */
	public int doUpdate(ZC_SYS_DDB ddb){
		String sql = "update %SDDB set mc=?,sjdd=?,dwbh=?,ddzt=?,pxxh=?,ddjc=?,ddh=? WHERE DDBH=?";
		sql=String.format(sql, SystemSet.sysBz);
		Object[] obj = new Object[]{
				ddb.getMc(),
				ddb.getSjdd(),
				ddb.getDwbh(),
				ddb.getDdzt(),
				ddb.getPxxh(),
				ddb.getDdjc(),
				ddb.getDdh(),
				ddb.getDdbh()
		};
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
	 * 删除地点信息
	 * @param ddbh
	 * @return
	 */
	public int doDelete(String ddbh) {
		String sql = "DELETE %SDDB WHERE DDBH"+CommonUtils.getInsql(ddbh);
		sql=String.format(sql, SystemSet.sysBz);
		Object[] obj = ddbh.split(",");
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
	 * 获取某个地点的详细信息
	 * @param ddbh
	 * @return
	 */
	public Map getObjectById(String ddbh) {
		String sql = "select d.ddbh,d.mc,d.sjdd,(case d.sjdd when '000000' then '无' else (select '('||a.ddh||')'||to_char(a.mc) from zc_sys_ddb a where a.ddbh=d.sjdd) end) sjddmc," 
				+ "d.dwbh,(select '('||a.dwbh||')'||a.mc from %sdwb a where a.dwbh=d.dwbh) dwmc,d.ddzt,(case d.ddzt when '1' then '正常' when '0' then '禁用' end) ddztmc,d.ddjc,d.pxxh," 
				+ "d.ddh from %sddb d where ddbh = ?";			
		sql=String.format(sql, SystemSet.gxBz, SystemSet.sysBz);
		return db.queryForMap(sql,new Object[]{ddbh});
	}
	
	/**
	 * 判断ddbh是否重复
	 * @param ddbh
	 * @return boolean
	 */
	public boolean doCheckDdbh(ZC_SYS_DDB ddb){
		String sql = "select count(*) from %SDDB where ddbh = ?";
		String sql2 = "select count(*) from ZC_SYS_DDB where ddh = ?";
		sql=String.format(sql, SystemSet.sysBz);
		String count = db.queryForObject(sql,new Object[]{ddb.getDdbh()}, String.class);
		String count2 = db.queryForObject(sql,new Object[]{ddb.getDdh()}, String.class);
		return "0".equals(count)&&"0".equals(count2)?true:false;
	}
	
	/**
	 * 判断ddh是否重复
	 * @param ddbh
	 * @return boolean
	 */
	public boolean doCheckDdh(ZC_SYS_DDB ddb){
		String sql = "select count(*) from %SDDB where ddh = ? and ddbh <> ? ";
		sql=String.format(sql, SystemSet.sysBz);
		String count = db.queryForObject(sql,new Object[]{ddb.getDdh(),ddb.getDdbh()}, String.class);
		return "0".equals(count)?true:false;
	}
	/**
	 * 判断ddh是否重复
	 * @param ddbh
	 * @return boolean
	 */
	public boolean doCheckDdh(String ddh){
		String sql = "select count(*) from %SDDB where ddh = ?";
		sql=String.format(sql, SystemSet.sysBz);
		String count = db.queryForObject(sql,new Object[]{ddh}, String.class);
		return "1".equals(count)?true:false;
	}
	/**
	 * 批量赋值单位信息
	 */
	public int doPlFuzhi(String ddbh,String ziduan,String zdValue) {
		if(ddbh.contains(",")){
			ddbh = ddbh.replace(",", "','");
		}
		String sql = "update %SDDB set "+ziduan+" = ? where  DDBH in ('"+ddbh+"')";
		sql=String.format(sql, SystemSet.sysBz);
		int i = db.update(sql, new Object[]{zdValue});
		return i;
	}
	
	/**
	 * 查询上级地点级次+1(这个方法是保存单位级次用的、已经+1了，切记切记)
	 * @param sjdd
	 * @return
	 */
	public Long findSjddjc(String sjdd) {
		Long s;
		if(sjdd.equals("000000")){
			return Long.parseLong("1");
		}
		String sql = "SELECT (DDJC+1) as ddjc  FROM %SDDB WHERE DDBH=?";
		sql=String.format(sql, SystemSet.sysBz);
	    s = db.queryForObject(sql, new Object[]{sjdd},Long.class);
		return s;
	}
	
	/**
	 * 存放地点设置
	 * 通过地点号(地点名称)查询地点编号
	 */
	public String findDdbhByDdmc(String words) {
		String sql = " SELECT DDBH FROM %SDDB WHERE trim('('||DDH||')'||MC) = ?";
		sql=String.format(sql, SystemSet.sysBz);
		String ddbh = "";
		try {
			ddbh = db.queryForObject(sql,new Object[]{words},String.class);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    ddbh = "F";
		}
		return ddbh;
	}
	/**
	 * 判断zjb（主机表）中是否有使用该地点编号
	 * @param ddbh
	 * @return ture为无帮助信息可以删除，false为有帮助信息不能删除
	 */
	public boolean doCheckDdbh(String ddbh) {
		String sql = "select count(1) from %Szjb where bzxx = ?";
		sql=String.format(sql, SystemSet.zcBz);
		String count = db.queryForObject(sql,new Object[]{ddbh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 通过地点编号查询地点名称
	 * @param ryxm
	 * @return
	 */
	public String findDdbh(String ddbh) {
		String sql = "SELECT '(' || D.DDH || ')' || D.MC AS DDXX from ZC_SYS_DDB D WHERE d.ddbh= ?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql, new Object[]{ddbh});
	}
}

