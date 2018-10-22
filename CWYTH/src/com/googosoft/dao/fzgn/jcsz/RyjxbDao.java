package com.googosoft.dao.fzgn.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYJXB;
@Repository("ryjxbDao")
public class RyjxbDao extends BaseDao{
	/**
	 * 新增
	 * @param ryjxb
	 * @return
	 */
	public int doAddRyjx(GX_SYS_RYJXB ryjxb){
		String sql="insert into %SRYJXB(guid,rybh,jxnr,jxzl,jxrq,jxsj) values(?,?,?,?,?,?) ";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
              ryjxb.getGuid(),
              ryjxb.getRybh(),
              ryjxb.getJxnr(),
              ryjxb.getJxzl(),
              ryjxb.getJxrq(),
              ryjxb.getJxsj()
				};
			return db.update(sql, obj);
	}
	
	/**
	 * 获取实体
	 * @return
	 */
	public Map<String,Object> getObjectById(String guid) {
		String sql = "SELECT T.JXNR,T.GUID,T.RYBH,T.JXZL,T.JXRQ,T.JXSJ "+ " FROM %SRYJXB T WHERE GUID=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYJXB ryjxb){
		String sql = "update %SRYJXB set rybh=?,jxnr=?,jxzl=?，jxrq=?,jxsj=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				ryjxb.getRybh(),
				ryjxb.getJxnr(),
				ryjxb.getJxzl(),
				ryjxb.getJxrq(),
				ryjxb.getJxsj(),
				ryjxb.getGuid()
				};
		return db.update(sql, obj);
	}
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid){
		
		String sql="delete %SRYJXB where guid =?";
		sql=String.format(sql, SystemSet.gxBz);
		String[] params =guid.split(",");
		List<Object[]> batchArgs =new ArrayList<Object[]>();
		for(int i=0;i<params.length;i++){
			Object[] param = new Object[]{
					params[i]
				};
			batchArgs.add(param);
		}
		return db.batchUpdate(sql, batchArgs).length;
	}
}
