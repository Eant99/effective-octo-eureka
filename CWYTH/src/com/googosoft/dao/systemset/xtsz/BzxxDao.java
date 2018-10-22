package com.googosoft.dao.systemset.xtsz;

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
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.xtsz.ZC_BZXX;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.UuidUtil;

@Repository("bzxxDao")
public class BzxxDao extends BaseDao{
	private Logger logger = Logger.getLogger(BzxxDao.class);
	
	/**
	 * 获取需要设置帮助信息的目录模块
	 */
	public List getMlbh() {
		String sql = "SELECT BH,MC FROM %SBZML ORDER BY ZT ";
		sql=String.format(sql, SystemSet.zcBz);
		List list =  new ArrayList<>();
		try {  
			list= db.queryForList(sql);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return list;
	}
	
	/**
	 * 判断mlbh是否重复
	 * @param bh
	 * @return ture为不重复，false为重复
	 */
	public boolean doCheckMlbh(ZC_BZXX bzxx) {
		String sql = "select count(1) from %Sbzxx where mlbh = ?";
		sql=String.format(sql, SystemSet.zcBz);
		String count = db.queryForObject(sql,new Object[]{bzxx.getMlbh()}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断mlbh下是否有帮助信息
	 * @param bh
	 * @return ture为无帮助信息可以删除，false为有帮助信息不能删除
	 */
	public boolean doCheckBzxx(String mlbh) {
		String sql = "select count(1) from %Sbzxx where mlbh = ?";
		sql=String.format(sql, SystemSet.zcBz);
		String count = db.queryForObject(sql,new Object[]{mlbh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 获取某个帮助信息的详细信息
	 * @param id
	 * @return
	 */
	public Map getObjectById(String id) {
		String sql = "select id,mlbh,ywmc,bzxx,sfxs from %Sbzxx WHERE id='"+id+"'";
		sql=String.format(sql, SystemSet.zcBz);
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String,Object> map = new HashMap<String,Object>();
			while(rs.next()){
				map.put("ID", rs.getString("ID"));
				map.put("MLBH", rs.getString("MLBH"));
				map.put("YWMC", rs.getString("YWMC"));
				map.put("BZXX", CommonUtils.BlobToString(rs.getBlob("BZXX")));
				map.put("SFXS", rs.getString("SFXS"));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 获取某个帮助信息的详细信息
	 * @param bh
	 * @return
	 */
	public Map getObjectByBh(String bzdm) {
		String sql = "select K.id,K.mlbh,K.ywmc,K.bzxx,K.jdrq,(select rygh||'('||xm||')' from "+SystemSet.gxBz+"RYB where rybh=K.JDR) as JDR from %Sbzxx K WHERE K.SFXS = '1' and K.mlbh='"+bzdm+"'";
		sql=String.format(sql, SystemSet.zcBz);
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String,Object> map = new HashMap<String,Object>();
			while(rs.next()){
				map.put("ID", rs.getString("ID"));
				map.put("MLBH", rs.getString("MLBH"));
				map.put("YWMC", rs.getString("YWMC"));
				map.put("BZXX", CommonUtils.BlobToString(rs.getBlob("BZXX")));
				map.put("JDR", rs.getString("JDR"));
				map.put("JDRQ", rs.getString("JDRQ"));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 新增信息
	 * @param zc_bzxx
	 * @return i
	 */
	public int doAdd(ZC_BZXX bzxx, String content) {
		bzxx.setId(UuidUtil.get32UUID()) ;
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "insert into %Sbzxx(id,mlbh,ywmc,bzxx,jdr,jdrq,sfxs) values(?,?,?,?,?,?,?)";
			sql=String.format(sql, SystemSet.zcBz);
			PreparedStatement st = null;
			Date date = new Date();
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, bzxx.getId());
			st.setString(2, bzxx.getMlbh());
			st.setString(3, bzxx.getYwmc());
			st.setBinaryStream(4, blob.getBinaryStream(),b.length);
			st.setString(5, LUser.getRybh());
			st.setObject(6, new java.sql.Date(date.getTime()));
			st.setObject(7, bzxx.getSfxs());
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
	 * @param zc_bzxx
	 * @return i
	 */
	public int doUpdate(ZC_BZXX bzxx, String content,String id ){
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "UPDATE %SBZXX SET BZXX=?,SFXS=?,YWMC=? WHERE ID ='"+id+"'";
			sql=String.format(sql, SystemSet.zcBz);
			PreparedStatement st = null;
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setBinaryStream(1, blob.getBinaryStream(),b.length);
			st.setObject(2, bzxx.getSfxs());
			st.setObject(3, bzxx.getYwmc());
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}
	
	/**
	 * 删除帮助信息
	 * @param id
	 * @return i
	 */
	public int doDelete(String id) {
		String sql = "DELETE %SBZXX WHERE ID"+CommonUtils.getInsql(id);
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

}
