package com.googosoft.dao.xmgl.jcsz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.xmgl.jcsz.Cw_cgmlszb;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("cgmlszDao")
public class CgmlszDao extends BaseDao{
	
	private Logger logger = Logger.getLogger(CgmlszDao.class);
	
	/**
	 * 权限model
	 * @param rybh 人员编号
	 * @return
	 */
	public List GetCgmlsz(String sjfyfl) {

		String sql="";
		
		if(sjfyfl.equals("root")){
			 sql=" select (SELECT COUNT(1) FROM cw_cgmlszb A WHERE A.SJML = t.guid) AS XJCOUNT,GUID,MLDM,MLMC,SJML from  cw_cgmlszb  T where sjml is null ";	
		}else{
			 sql=" select (SELECT COUNT(1) FROM cw_cgmlszb A WHERE A.SJML = t.guid) AS XJCOUNT,GUID,MLDM,MLMC,SJML from  cw_cgmlszb  T where sjml='"+sjfyfl+"' ";
		}
		System.out.println("打印sql========================"+sjfyfl);
		return db.queryForList(sql,new Object[]{});
	}

	/**
	 * 增加
	 * @param 
	 * @return
	 */
	public int doAdd(Cw_cgmlszb cgmlszb) {
		
		String sql = "insert into CW_CGMLSZB(GUID,MLDM,MLMC,SJML,BZ,CZR) "
				+ "values(?,?,?,?,?,?)";
		
		Object[] obj = new Object[]{
				cgmlszb.getGuid(),
				cgmlszb.getMldm(),
				cgmlszb.getMlmc(),
				cgmlszb.getSjml(),
				cgmlszb.getBz(),
				cgmlszb.getCzr()
		};
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 采购目录设置
	 * @param
	 * @return
	 */
	public Map<String, Object> getObjectByGuId(String guid) {
		String sql = "select guid, mldm, mlmc, (select '(' || k.mldm || ')' || to_char(k.mlmc) from Cw_cgmlszb k where k.guid = t.sjml) as sjmlmc, bz from Cw_cgmlszb t where t.guid= ? ";
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 由采购分类得到cg_edit页面中的上级目录
	 * @param
	 * @return
	 */
	public Map<String, Object> getSjmlByGuid(String guid) {
		String sql = "select '(' || mldm || ')' || to_char(mlmc) as sjmlmc, guid as sjml from Cw_cgmlszb where guid = ? ";
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 修改
	 * @param Cw_cgmlszb 
	 * @return
	 */
	public int doUpdate(Cw_cgmlszb cgmlszb){
		String sql = "update Cw_cgmlszb set mldm=?,mlmc=?,sjml=?,bz=? where guid=?";
		Object[] obj = new Object[]{
				cgmlszb.getMldm(),
				cgmlszb.getMlmc(),
				cgmlszb.getSjml(),
				cgmlszb.getBz(),
				cgmlszb.getGuid()
		};
		
	    int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	public List<Map<String, Object>> getXsList(String guid, String searchValue) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select A.GUID AS GUID,  A.XH AS XH,  A.XM AS XM,  A.CSRQ AS CSRQ, (SELECT MC FROM GX_SYS_DMB WHERE ZL = '103'  ");
		sql.append(" from CW_XSXXB A where 1=1 ");
		if(Validate.noNull(guid)){
			sql.append(" and  a.guid in ('"+guid.trim()+"') ");
		}
		sql.append("  order by XH asc ");
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}
	
	/*
	 * 返回允许删除的dm
	 */
	public String docheckisdelete(String guid) {
		String candel = "";
		String[] gid = guid.split(",");
		for(int i = 0;i<gid.length;i++) {
			String flag = db.queryForSingleValue("select count(1) from cw_xmsbmxb b where b.cgml='"+gid[i]+"'");
			String flag2 = db.queryForSingleValue("select count(1) from cw_cgmlszb b where b.sjml='"+gid[i]+"'");
			if(Integer.parseInt(flag)==0 && Integer.parseInt(flag2)==0) {
				candel = candel+gid[i];
				if(i<gid.length-1) {
					candel+=',';
				}
			}
		}
		return candel;
	}
	/*
	 * 返回不允许删除的dm
	 */
	public String dochecknotdelete(String guid) {
		String candel = "";
		String[] gid = guid.split(",");
		for(int i = 0;i<gid.length;i++) {
			String flag = db.queryForSingleValue("select count(1) from cw_xmsbmxb b where b.cgml='"+gid[i]+"'");
			String flag2 = db.queryForSingleValue("select count(1) from cw_cgmlszb b where b.sjml='"+gid[i]+"'");
			if(Integer.parseInt(flag)!=0 || Integer.parseInt(flag2)!=0) {
				candel = candel+gid[i];
				if(i<gid.length-1) {
					candel+=',';
				}
			}
		}
		return candel;
	}
	public String seledmbyid(String guid) {
		if(Validate.noNull(guid)) {
			if(",".equals(guid.substring(guid.length()-1,guid.length()))) {
				guid = guid.substring(0,guid.length() - 1);
			}
		}
		final Object[] params = guid.split(",");
		return db.queryForSingleValue(" select wm_concat(t.mldm) dm from cw_cgmlszb t where t.guid "+CommonUtils.getInsql(guid)+" ",params);
	}
	public int getcgscnum(String guid) {
		int num = 0;
		String[] gid = guid.split(",");
		for(int i = 0;i<gid.length;i++) {
			String flag = db.queryForSingleValue("select count(1) from cw_xmsbmxb b where b.cgml='"+gid[i]+"'");
			String flag2 = db.queryForSingleValue("select count(1) from cw_cgmlszb b where b.sjml='"+gid[i]+"'");
			if(Integer.parseInt(flag)==0 && Integer.parseInt(flag2)==0) {
				num++;
			}
		}
		return num;
	}
	/**
	 * 删除采购目录信息
	 * 
	 * @param 
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String DELSQL = "DELETE FROM Cw_cgmlszb WHERE guid " + wstr;
		int result = 0;
		try {
			result = db.update(DELSQL, params);
			
		} catch (DataAccessException e) {
			
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 事务回滚
		}
		return result;
	}
	
	/**
	 * 判断mldm是否重复
	 * @param mldm
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckMldm(String mldm){
		
		String sql = "select count(1) from CW_CGMLSZB where  mldm= ? ";
		String count = db.queryForObject(sql,new Object[]{mldm}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
}

