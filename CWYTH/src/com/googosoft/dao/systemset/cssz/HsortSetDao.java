package com.googosoft.dao.systemset.cssz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.ZC_HSORTSET_EXTEND;
import com.googosoft.util.CommonUtils;

@Repository("hsortsetDao")
public class HsortSetDao extends BaseDao{
	private Logger logger = Logger.getLogger(HsortSetDao.class);
	
	/**
	 * 修改，根据primary key  获取信息
	 */
	public Map getObjectById(String zbh) {
		String sql = "SELECT A.ZBH,A.ZMC,A.BLW,A.FLMC FROM ZC_HSORTSET A WHERE ZBH = ?";
		return db.queryForMap(sql,new Object[]{zbh});
	}
	public List getZclb() {
		String sql = "select dlh,mc,zbh from ZC_HSORT";
		return db.queryForList(sql);
	}
	/**
	 * 新增 资产编号信息
	 */
	public int doAdd(ZC_HSORTSET_EXTEND hsortset,String dlhArr,String dlhOld) {
		String sql1 = "INSERT INTO ZC_HSORTSET (ZBH,ZMC,BLW,FLMC) VALUES(?,?,?,?) ";
		String sql2 = "update zc_hsort set zbh='"+hsortset.getZbh()+"' where dlh in('"+dlhArr+"')";
		String sql3 = "delete ZC_HSORTSET c where c.zbh in( select t.zbh from ZC_HSORTSET t where t.zbh not in(select z.zbh from ZC_HSORT z where z.zbh is not null))";
		int i=0; 
		try{
			i = db.update(sql1,new Object[]{
					hsortset.getZbh(),
					hsortset.getZmc(),
					hsortset.getBlw(),
					hsortset.getFlmc()});
			
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		int j=0;
		try{
			j = db.update(sql2);
			db.update(sql3);
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		if(i==1&&j>=1){
			return 1;
		}else{
			return 0;
		}
	}
	/**
	 * 修改资产编号信息
	 */
	public int doUpdate(ZC_HSORTSET_EXTEND hsortset,String dlhArr,String dlhOld) {
		String sql0 = "delete ZC_HSORTSET c where c.zbh in( select t.zbh from ZC_HSORTSET t where t.zbh not in(select z.zbh from ZC_HSORT z where z.zbh is not null))";
		String sql1 = "UPDATE ZC_HSORTSET SET ZMC=?, BLW=?,FLMC=? WHERE ZBH=?";
		String sql3 = "update zc_hsort set zbh='' where dlh in('"+dlhOld.replace(",","','")+"')";
		String sql2 = "update zc_hsort set zbh='"+hsortset.getZbh()+"' where dlh in('"+dlhArr+"')";
		int i = 0;
		try{
			
			i = db.update(sql1,new Object[]{
					hsortset.getZmc(),
					hsortset.getBlw(),
					hsortset.getFlmc(),
					hsortset.getZbh()});
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		int j = 0;
		try{
			    db.update(sql3);
			j = db.update(sql2);
			db.update(sql0);
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		if(i==1&&j>=1){
			return 1;
		}else{
			return 0;
		}
	}
	public int doDelete(String zbh) {
		String sql = "DELETE ZC_HSORTSET WHERE ZBH"+CommonUtils.getInsql(zbh);
		String sql2 = "update ZC_HSORT set zbh='' WHERE ZBH"+CommonUtils.getInsql(zbh);
		Object[] obj = zbh.split(",");
		int i = 0;
		try{
			i = db.update(sql,obj);
			db.update(sql2,obj);
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 检查主键zbh  是否重复
	 */
	public String checkZbh(ZC_HSORTSET_EXTEND hsortset) {
		String sql = "select count(zbh) from ZC_HSORTSET where zbh=?";
		return db.queryForObject(sql, new Object[]{hsortset.getZbh()},String.class);
	}
	public Map getDlhById(String zbh) {
		String sql = "select wm_concat(dlh) as dlh from ZC_HSORT t  WHERE ZBH = ?";
		return db.queryForMap(sql,new Object[]{zbh});
	}
}
