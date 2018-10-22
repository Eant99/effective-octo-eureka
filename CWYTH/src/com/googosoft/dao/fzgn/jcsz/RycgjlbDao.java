package com.googosoft.dao.fzgn.jcsz;


import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYCGJLB;

@Repository("rycgjlbDao")
public class RycgjlbDao extends BaseDao{
	/**
	 * 新增
	 * @param rycgjlb
	 * @return
	 */
	public int doAddRycgjl(GX_SYS_RYCGJLB rycgjlb){
		String sql="insert into %SRYCGJLB(guid,rybh,cg,rq,hjsm) values(?,?,?,?,?) ";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				rycgjlb.getGuid(),
				rycgjlb.getRybh(),
				rycgjlb.getCg(),
				rycgjlb.getRq(),
				rycgjlb.getHjsm()
				};
		return db.update(sql, obj);

	}
	/**
	 * 获取实体
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "SELECT A.GUID,A.RYBH,A.CG,A.RQ,A.HJSM"+ " FROM %SRYCGJLB A WHERE GUID=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYCGJLB rycgjlb){
		String sql = "update %SRYCGJLB set rybh=?,cg=?,rq=?,hjsm=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				rycgjlb.getRybh(),
				rycgjlb.getCg(),
				rycgjlb.getRq(),
				rycgjlb.getHjsm(),
				rycgjlb.getGuid()
				};
		return db.update(sql, obj);
	}
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid){
		String sql="delete %SRYCGJLB where guid "+ToSqlUtil.getInsql(guid);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj =guid.split(",");
		return db.update(sql, obj);
	}

}