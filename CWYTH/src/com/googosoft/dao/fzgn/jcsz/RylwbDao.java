package com.googosoft.dao.fzgn.jcsz;

import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYLWB;

@Repository("rylwbDao")
public class RylwbDao  extends BaseDao{
	private Logger logger = Logger.getLogger(RylwbDao.class);
	/**
	 * 添加
	 * @param rylwb
	 * @return
	 */
	public int doAdd(GX_SYS_RYLWB rylwb)
	{
		String sql="insert into %SRYLWB(guid,rybh,rq,lwtm,lwjb,sfkw) values(?,?,?,?,?,?) ";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				rylwb.getGuid(),
				rylwb.getRybh(),
				rylwb.getRq(),
				rylwb.getLwtm(),
				rylwb.getLwjb(),
				rylwb.getSfkw()
				};
		return  db.update(sql, obj);

		
	}	
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid)
	{
		String sql="delete %SRYLWB where guid "+ToSqlUtil.getInsql(guid);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj =guid.split(",");
		return db.update(sql, obj);
	}
	
	/**
	 * 获取实体
	 * @return
	 */
	public Map<String,Object> getObjectById(String guid) 
	{
		String sql = "SELECT A.GUID,A.RYBH,TO_DATE(A.RQ,'YYYY-MM-DD') AS RQ,A.LWTM,A.LWJB,A.SFKW"+ " FROM %SRYLWB A WHERE GUID=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYLWB rylwb)
	{
		String sql = "update %SRYLWB set rybh=?,rq=?,lwtm=?,lwjb=?,sfkw=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				rylwb.getRybh(),
				rylwb.getRq(),
				rylwb.getLwtm(),
				rylwb.getLwjb(),
				rylwb.getSfkw(),
				rylwb.getGuid()
				};
		return db.update(sql, obj);
	}
	
}
