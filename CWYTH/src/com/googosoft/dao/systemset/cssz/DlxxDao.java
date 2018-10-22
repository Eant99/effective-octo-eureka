package com.googosoft.dao.systemset.cssz;

import java.io.UnsupportedEncodingException;
import java.sql.Blob;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.rowset.serial.SerialBlob;

import org.springframework.stereotype.Component;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.cssz.SYS_XTCSB;
//import com.googosoft.pojo.systemset.cssz.SYS_XTCSB;
import com.googosoft.util.CommonUtils;

/**
 * 登录信息设置
 * @author master
 */
@Component("dlxxDao")
public class DlxxDao extends BaseDao{
	
	
	
	/**
	 * 获取背景图及logo图
	 * @return
	 */
	public List getImgFile() {
		String sql = "SELECT * FROM ZC_XGWD where djlx in('bgImg','logoImg')";
		List<Map<String,Object>> list = db.queryForList(sql);
		return list;
	}
	/**
	 * 获取登录信息
	 * @return
	 */
	public Map getDlxx() {
		String sql = "select dlfs,dxyz,yzmlx,xtmc,jszc,lxdh,email,lxdz,apikey,xtsm from zc_sys_LOGIN where xtbz='01' ";
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			Map<String,Object> map = new HashMap<String,Object>();
			while(rs.next()){
				map.put("DLFS", rs.getString("DLFS"));
				map.put("DXYZ", rs.getString("DXYZ"));
				map.put("YZMLX", rs.getString("YZMLX"));
				map.put("XTMC", rs.getString("XTMC"));
				map.put("JSZC", rs.getString("JSZC"));
				map.put("LXDH", rs.getString("LXDH"));
				map.put("EMAIL", rs.getString("EMAIL"));
				map.put("LXDZ", rs.getString("LXDZ"));
				map.put("APIKEY", rs.getString("APIKEY"));
				map.put("XTSM", CommonUtils.BlobToString(rs.getBlob("XTSM")));
			}
			conn.close();
			return map;
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
	}
	/**
	 * 保存系统参数信息
	 * @param xtcsb 系统参数表
	 * @return
	 */
	public boolean doSave(SYS_XTCSB xtcsb,String content) {
		//登录方式为多个，存到一个字段，需要进行处理
//		String fs = "";
//		for (int i = 0; i < dlfs.length; i++) {
//			fs=fs+dlfs[i]+",";
//			System.err.println("__22__"+dlfs[i]);
//		}
//		if(fs.length()>0)
//		{
//			fs=fs.substring(0,fs.length()-1);
//		}
		Connection conn;
		try {
			conn = db.getDataSource().getConnection();
			String sql = "UPDATE zc_sys_login SET DLFS=?,DXYZ=?,YZMLX=?,XTMC=?,JSZC=?,LXDH=?,EMAIL=?,LXDZ=? where xtbz='01'";
			PreparedStatement st = null;
			byte[] b = content.getBytes("utf-8");
			Blob blob = new SerialBlob(b);
			st = conn.prepareStatement(sql);
			st.setString(1, "1,2");
			st.setString(2, xtcsb.getDxyz());
			st.setString(3, xtcsb.getYzmlx());
			st.setString(4, xtcsb.getXtmc());
			st.setString(5, xtcsb.getJszc());
			st.setString(6, xtcsb.getLxdh());
			st.setString(7, xtcsb.getEmail());
			st.setString(8, xtcsb.getLxdz());
			st.execute();
			conn.close();
			return true ;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 获取系统参数
	 * @return
	 */
	public Map<String, Object> getXtcs() {
		String sql = "SELECT * FROM ZC_SYS_LOGIN WHERE xtbz='"+SystemSet.XTBZ+"' and ROWNUM<=1";
		sql=String.format(sql, SystemSet.sysBz);
		return db.queryForMap(sql);
		
	}
	
	/**
	 * 获取登录页图片
	 * @return
	 */
	public List getLoginImg(String flag){
		String sql = "select path from ZC_XGWD  where djlx=?";
		return db.queryForList(sql,new Object[]{flag});
	}
	
	
	/**
	 * 获取系统图片
	 * @return
	 */
	public Map<String, Object> getTp() {
		String sql = "SELECT * FROM ZC_XGWD where djlx='bgImg' ";
		/*sql=String.format(sql, SystemSet.sysBz);*/
		return db.queryForMap(sql);
		
	}
	/**
	 * 获取注意事项
	 */
	public String getZysx() {
		String sql = "SELECT XTSM FROM ZC_SYS_LOGIN  WHERE xtbz='"+SystemSet.XTBZ+"' AND ROWNUM<=1";
		sql=String.format(sql, SystemSet.sysBz);
		Map map = db.queryForMap(sql);
		byte[] b = (byte[]) map.get("XTSM");
		String ss = "";
		try {
			 ss = CommonUtils.BlobToString(new SerialBlob(b));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ss;
	}

}
