package com.googosoft.dao.base;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.pojo.ModelVo;
import com.googosoft.pojo.OA_KEY;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.util.CommonUtils;


@Repository("modelDao")
public class ModelDao  {
	private Logger logger = Logger.getLogger(ModelDao.class);
	@Autowired
	JdbcTemplate jdbcTemplate;
	
	
	
	public boolean updateModelSaasByid(final ModelVo mv) {
		String sql ="update ACT_RE_MODEL set saas=? where id_=?";
		int rows=jdbcTemplate.update(sql, new PreparedStatementSetter() {
			@Override
			public void setValues(PreparedStatement ps) throws SQLException {
				ps.setString(1, CommonUtils.getSaas());
				ps.setString(2, mv.getGid());
			
			}
		});
		if(rows>0)
			return true;
		return false;
		
	}
	/**
	 * 插入审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doAddShyj(OA_SHYJB shyjb) {
		String sql = "insert into " + SystemSet.oaBz + "SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				CommonUtils.getRybh(), 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
				
		};
		int i = jdbcTemplate.update(sql, obj);
		return i;
	}
	
	/**
	 * 删除操作
	 * @param documentid
	 * @return
	 */
	public int doDelete(String modelid) {
		String sql = "DELETE "+SystemSet.oaBz+"KEY  WHERE MODELID"+CommonUtils.getInsql(modelid);
		Object[] obj = modelid.split(",");
		int i = 0;
		try {  
			i = jdbcTemplate.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	public boolean saveModelInfo(final OA_KEY key) {
		Map map=findModel(key.getModelid());
		String sql="";
		Object[] obj=null;
		if((map.get("COUNT")+"").equals("1")){
			sql ="update OA_KEY set key=?,lx=?,saas=?,jdr=?,jdsj=to_char(sysdate,'yyyy-mm-dd HH24:mi:ss'),sfbs='' where modelid=?";
			 obj = new Object[]{
					 key.getKey(),
					 key.getLx(),
					 CommonUtils.getSaas(),
					 CommonUtils.getRybh(),
					 key.getModelid()
				};
			
		}else{
			 sql ="insert into OA_KEY(key,lx,saas,jdr,jdsj,modelid,sfbs) values(?,?,?,?,to_char(sysdate,'yyyy-mm-dd HH24:mi:ss'),?,?)";
			 obj = new Object[]{
				 key.getKey(),
				 key.getLx(),
				 CommonUtils.getSaas(),
				 CommonUtils.getRybh(),
				 key.getModelid(),
				 ""
			};
		}
		int i = jdbcTemplate.update(sql, obj);
		if(i>0)
			return true;
		return false;
	}
	public Map findMxlx(String id) {
		StringBuffer sql = new StringBuffer();
		sql.append("select META_INFO_ from ACT_RE_MODEL  where id_='"+id+"' ");
		Map map=jdbcTemplate.queryForMap(sql.toString());
		return map;
	}
	public Map findModel(String modelid) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) as count from OA_KEY  where modelid='"+modelid+"'");
		Map map=jdbcTemplate.queryForMap(sql.toString());
		return map;
	}
	public Map findKey(String lx) {
		StringBuffer sql = new StringBuffer();
		sql.append("select key as key from OA_KEY  where lx='"+lx+"' and saas='"+CommonUtils.getSaas()+"' and sfbs='1' and bssj=(select max(bssj) from oa_key where lx='"+lx+"' and saas='"+CommonUtils.getSaas()+"' and sfbs='1')" );
		Map map=jdbcTemplate.queryForMap(sql.toString());
		return map;
	}
	public void bushu(String modelid){
		String sql="update "+SystemSet.oaBz+"KEY  set sfbs='1',bssj=to_char(sysdate,'yyyymmddHH24miss') where modelid="+modelid;
		jdbcTemplate.update(sql);
	}
	
}
