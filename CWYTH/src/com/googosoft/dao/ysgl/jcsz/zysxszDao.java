package com.googosoft.dao.ysgl.jcsz;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;

@Repository("zysxszDao")
public class zysxszDao extends BaseDao{

	/**
	 * 删除
	 * @param guid   
	 * @return 
	 */
	public int doDelete(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "delete from cw_zysxb where guid in ('"+guid+"')";
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	
	/**
	 * 修改
	 * @param guid   
	 * @return 
	 */
	public int updateZysx(PageData pd,String check) {
		String sql = "UPDATE CW_ZYSXB SET "
				+ "MKBH = ?,MKMC = ?,SFXS = ?,ZYSXNR = ? where guid = ?";
//		Object[] obj = {
//				pd.getString("mkbh"),//CommonUtil.getBeginText(pd.getString("mbbh")),
//				pd.getString("mc"),
//				pd.getString("xm"),
//				pd.getString("zysx"),
//				pd.getString("guid")
//		};
		
		
		Connection conn;
		try {
//			String  content = pd.getString("check");
			conn = db.getDataSource().getConnection();
			PreparedStatement st = null;
			String content = pd.getString("content");
			byte[] b = content.getBytes("UTF-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, pd.getString("mkbh"));
			st.setString(2, pd.getString("mc"));
			st.setString(3, pd.getString("xm"));
			st.setBinaryStream(4,  blob.getBinaryStream(),b.length);
			st.setString(5, pd.getString("guid"));
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
		
		
//		int i = 0;
//		try {  
//			i = db.update(sql, obj);
//		} catch (DataAccessException e) {
//		    return -1;
//		}
//		return i;
	}
	
	/**
	 * 增加
	 * @param guid   
	 * @return 
	 */
	public int insertZysx(PageData pd,String guid) {
		String sql = "insert into cw_zysxb (guid,mkbh,mkmc,sfxs,zysxnr,czrq)" 
				+ " values(?,?,?,?,?,sysdate)";
		System.err.println("!!!!!!!!!!!!!!!!!!!!"+pd.getString("mc"));
//		
//		System.out.println("!!!!!!!!!!!!!!!!!!!!"+pd.getString("zysx"));
//		
//		Object[] obj = {
//				guid,
//				pd.getString("mkbh"),
//				pd.getString("mc"),
//				pd.getString("xm"),
//				pd.getString("zysx"),
//		};
//		
//		int i = 0;
//		try {  
//			i = db.update(sql, obj);
//		} catch (DataAccessException e) {
//		    return -1;
//		}
//		return i;
		
		
		
		Connection conn;
		try {
			String  content = pd.getString("zysx");
			conn = db.getDataSource().getConnection();
			PreparedStatement st = null;
			byte[] b = content.getBytes("UTF-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, guid);
			st.setString(2, pd.getString("mkbh"));
			st.setString(3, pd.getString("mc"));
			st.setString(4, pd.getString("xm"));
			st.setBinaryStream(5, blob.getBinaryStream(),b.length);
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
		
		
	}
	
	/**
	 * 查看信息
	 * @param guid
	 * @return
	 */
	public Map<String, Object> selectzysxMapById(String guid){
		String sql = "select t.guid,t.mkbh,t.mkmc,t.sfxs,t.zysxnr from CW_ZYSXB t where t.guid='"+guid+"'";
		
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
				System.err.println(CommonUtils.BlobToString(rs.getBlob("ZYSXNR")));
				map.put("zysxnr", CommonUtils.BlobToString(rs.getBlob("ZYSXNR")));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		
		
	}
	
	
	/**
	 * 展示
	 * @param guid   
	 * @return 
	 */
	public int Xs(String guid) {
		String sql = "UPDATE CW_ZYSXB SET "
				+ "SFXS = ? where guid in ('"+guid+"')";
		Object[] obj = {
				1
		};
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
	/**
	 * 不展示
	 * @param guid   
	 * @return 
	 */
	public int Bxs(String guid) {
		String sql = "UPDATE CW_ZYSXB SET "
				+ "SFXS = ? where guid in ('"+guid+"')";
		Object[] obj = {
				0
		};
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	
}
