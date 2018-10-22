package com.googosoft.dao.fzgn.tzgg;

import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.tzgg.ZC_SYS_TZXX;
import com.googosoft.util.CommonUtils;

@Repository("tzxxDao")
public class TzxxDao extends BaseDao{
	private Logger logger = Logger.getLogger(TzxxDao.class);
	/**
	 * 新增系统发布消息
	 */
	public int doAdd(ZC_SYS_TZXX xtxx,String content) {
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "insert into %STZXX(gid,title,fbr,xx,sfzs,fbsj,dwbh) values(?,?,?,?,?,sysdate,?)";
			sql=String.format(sql, SystemSet.sysBz);
			PreparedStatement st = null;
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, xtxx.getGid());
			st.setString(2, xtxx.getTitle());
			st.setString(3, xtxx.getFbr());
			st.setBinaryStream(4, blob.getBinaryStream(),b.length);
			st.setString(5, xtxx.getSfzs());
			st.setString(6,xtxx.getDwbh());
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}
	
	/**
	 * 修改系统发布消息
	 */
	public int doUpdate(ZC_SYS_TZXX xtxx,String content,String gid){
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "UPDATE %STZXX SET TITLE=?,SFZS=?,XX=?,DWBH=? WHERE GID ='"+gid+"'";
			sql=String.format(sql, SystemSet.sysBz);
			PreparedStatement st = null;
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, xtxx.getTitle());
			st.setString(2, xtxx.getSfzs());
			st.setBinaryStream(3, blob.getBinaryStream(),b.length);
			st.setString(4, xtxx.getDwbh());
			st.execute();
			conn.close();
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			return -1;
		} 
	}
	
	/**
	 * 删除系统消息
	 */
	public int doDelete(String gid) {
		String sql = "DELETE %STZXX WHERE GID"+CommonUtils.getInsql(gid);
		sql=String.format(sql, SystemSet.sysBz);
		Object[] obj = gid.split(",");
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
	 * 获取系统消息内容
	 */
	public Map getObjectById(String gid) {
		String sql = "select b.gid,b.title,b.sfzs,(select '('||dwbh||')'||mc from  "+SystemSet.gxBz+"dwb a where a.dwbh = b.dwbh) as dwbh,(select '('||rygh||')'||xm from  "+SystemSet.gxBz+"ryb where rybh = b.fbr) AS FBR,xx,to_char(b.fbsj,'yyyy-mm-dd') as fbsj from %STZXX b WHERE b.gid='"+gid+"'";
		sql=String.format(sql, SystemSet.sysBz);
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String,Object> map = new HashMap<String,Object>();
			while(rs.next()){
				map.put("GID", rs.getString("GID"));
				map.put("TITLE", rs.getString("TITLE"));
				map.put("FBR", rs.getString("FBR"));
				map.put("FBSJ", rs.getString("FBSJ"));
				map.put("XX", CommonUtils.BlobToString(rs.getBlob("XX")));
				map.put("SFZS", rs.getString("SFZS"));
				map.put("DWBH", rs.getString("DWBH"));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 检验是否已阅读
	 */
	public boolean doCheckRy(String bh){
		String sql = "select count(1) from %STZXX_VIEW where bh = '"+bh+"' and rybh= '"+LUser.getRybh()+"' ";
		sql=String.format(sql, SystemSet.zcBz);
		String count = db.queryForObject(sql, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 增加阅读通知信息
	 */
	public int goBjxx(String bh) {
		String sql = "INSERT INTO %STZXX_VIEW( GID,BH,RYBH,RQ) VALUES(SYS_GUID(), '"+bh+"','"+LUser.getRybh()+"',sysdate) ";
		sql=String.format(sql, SystemSet.zcBz);
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}

}
