package com.googosoft.dao.fzgn.jcsz;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYZZB;
import com.googosoft.util.CommonUtils;                        

@Repository("ryzzbDao")
public class RyzzbDao extends BaseDao{
	/**
	 * 新增
	 * @param ryzzb
	 * @return
	 */
	public int doAddRyzz(GX_SYS_RYZZB ryzzb){
		String sql="insert into %SRYZZB(guid,rybh,rq,zzjb,zzmc) values(?,?,?,?,?) ";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
               ryzzb.getGuid(),
               ryzzb.getRybh(),
               ryzzb.getRq(),
               ryzzb.getZzjb(),
               ryzzb.getZzmc()
				}; 
		return db.update(sql, obj);

	}
	/**
	 * 获取实体
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "SELECT A.GUID,A.RYBH,A.RQ,A.ZZJB,A.ZZMC "+ " FROM %SRYZZB A WHERE GUID=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid){
		String sql="delete %SRYZZB where guid "+CommonUtils.getInsql(guid);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj =guid.split(",");
		return db.update(sql, obj);
	}
	
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYZZB ryzzb){
		String sql = "update %SRYZZB set rybh=?,rq=?,zzjb=?,zzmc=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				ryzzb.getRybh(),
				ryzzb.getRq(),
				ryzzb.getZzjb(),
				ryzzb.getZzmc(),
				ryzzb.getGuid()
				};
		return db.update(sql, obj);
	}

}
