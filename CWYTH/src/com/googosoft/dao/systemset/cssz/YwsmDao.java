package com.googosoft.dao.systemset.cssz;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.ZC_YWSM;
import com.googosoft.pojo.systemset.cssz.ZC_YWSMSET;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.UuidUtil;

@Repository("ywsmDao")
public class YwsmDao extends BaseDao{
	private Logger logger = Logger.getLogger(YwsmDao.class);
	/**
	 * 获取需要设置业务说明的模块
	 */
	public List getMkb() {
		String sql = "SELECT MKBH,MKMC FROM "+TnameU.MKB+" WHERE YWSM = '1' and qxbz='1' ORDER BY MKBH ";
		List list =  new ArrayList<>();
		try {  
			list= db.queryForList(sql);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return list;
	}
	
	/**
	 * 判断mkbh是否重复
	 * @param dwbh
	 * @return ture为不重复，false为重复
	 */
	public boolean doCheckMkbh(ZC_YWSM ywsm) {
		String sql = "select count(1) from %Sywsm where mkbh = ?";
		sql=String.format(sql, SystemSet.zcBz);
		String count = db.queryForObject(sql,new Object[]{ywsm.getMkbh()}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 新增信息
	 * @param ywsmb
	 * @return i
	 */
	public int doAdd(ZC_YWSM ywsm, String content) {
		ywsm.setId(UuidUtil.get32UUID()) ;
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "insert into %Sywsm(id,mkbh,mkmc,ywsm,jdr,jdrq) values(?,?,?,?,?,?)";
			sql=String.format(sql, SystemSet.zcBz);
			PreparedStatement st = null;
			Date date = new Date();
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, ywsm.getId());
			st.setString(2, ywsm.getMkbh());
			st.setString(3, ywsm.getMkmc());
			st.setBinaryStream(4, blob.getBinaryStream(),b.length);
			st.setString(5, LUser.getRybh());
			st.setObject(6, new java.sql.Date(date.getTime()));
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}
	
	/**
	 * 修改信息
	 * @param ywsm
	 * @return i
	 */
	public int doUpdate(ZC_YWSM ywsm, String content,String id ){
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "UPDATE %SYWSM SET MKMC=?,YWSM=? WHERE ID ='"+id+"'";
			sql=String.format(sql, SystemSet.zcBz);
			PreparedStatement st = null;
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, ywsm.getMkmc());
			st.setBinaryStream(2, blob.getBinaryStream(),b.length);
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}
	
	/**
	 * 获取某个业务说明的详细信息
	 * @param id
	 * @return
	 */
	public Map getObjectById(String id) {
		String sql = "select id,mkbh,mkmc,ywsm from %Sywsm WHERE id = '"+id+"'";
		sql = String.format(sql, SystemSet.zcBz);
		return db.queryForMap(sql,"ywsm");
	}
	
	/**
	 * 删除信息
	 * @param id
	 * @return i
	 */
	public int doDelete(String id) {
		String sql = "delete %sywsm where id" + ToSqlUtil.getInsql(id);
		sql=String.format(sql, SystemSet.zcBz);
		Object[] obj = id.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	public int deleteSet(String mkbh) {
		String sql = "delete zc_ywsmset where mkbh = '"+mkbh+"' and rybh = '"+LUser.getRybh()+"'";
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	public int insertSet(String mkbh) {
		String sqls[] = new String[2];
		sqls[0] = "delete zc_ywsmset where mkbh = '" + mkbh + "' and rybh = '" + LUser.getRybh() + "'";
		sqls[1] = "insert into zc_ywsmset(mkbh,rybh,sfts) values('" + mkbh + "', '" + LUser.getRybh() + "','1')";
		try {  
			db.batchUpdate(sqls);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return 1;
	}
	public String findWdxx(String mkbh) {
		String sql = "select sfts from zc_ywsmset where mkbh = '"+mkbh+"' and rybh = '" + LUser.getRybh() + "'";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 需要业务说明的模块，点击业务说明按钮弹窗
	 * @param mkbh
	 * @return
	 */
	public Map getObjectByMkbh(String mkbh) {
		String sql = "select * from CW_ZYSXB c where c.mkbh='"+mkbh+"' and c.sfxs='1'";
		
		System.out.println(mkbh+"@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		
//		String sql = "select t.guid,t.mkbh,t.mkmc,t.sfxs,t.zysxnr from CW_ZYSXB t where t.guid='"+guid+"'";
		
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String,Object> map = new HashMap<String,Object>();
			while(rs.next()){
				map.put("guid", rs.getString("GUID"));
				map.put("mkbh", rs.getString("MKBH"));
				map.put("mkmc", rs.getString("MKMC"));
				map.put("sfxs", rs.getString("SFXS"));
				map.put("zysxnr", CommonUtils.BlobToString(rs.getBlob("ZYSXNR")));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
		
		
//		return db.queryForMap(sql);
	}
	/**是否提示说明
	 * @return
	 */
	public Object upSfts(ZC_YWSMSET ywsmset) {
		String sql = "insert into %Sywsmset (rybh,mkbh,sfts) values (?,?,?)";
		sql = String.format(sql, SystemSet.zcBz);
		Object[] obj = new Object[]{
				ywsmset.getRybh(),
				ywsmset.getMkbh(),
				ywsmset.getSfts()};
		return db.update(sql, obj);
	}

	public int findSfts(ZC_YWSMSET ywsmset) {
		String sql="select sfts from %Sywsmset where mkbh=? and rybh=?";
		sql=String.format(sql, SystemSet.zcBz);
		Object[] obj = new Object[]{
				ywsmset.getMkbh(),
				ywsmset.getRybh()
									};
		List lists = this.db.queryForList(sql,obj);
		return lists.size();
	}

}
