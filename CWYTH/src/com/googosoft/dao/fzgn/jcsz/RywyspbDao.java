package com.googosoft.dao.fzgn.jcsz;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYWYSPB;
@Repository("rywyspbDao")
public class RywyspbDao extends BaseDao{
	/**
	 * 新增
	 * @param rywyspb
	 * @return
	 */
	public int doAdd(GX_SYS_RYWYSPB rywyspb){
		String sql="insert into %SRYWYSPB(guid,rybh,yz,sp) values(?,?,?,?) ";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
                rywyspb.getGuid(),
                rywyspb.getRybh(),
                rywyspb.getYz(),
                rywyspb.getSp()
				};
		return db.update(sql, obj);
	}
	/**
	 * 获取实体信息
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "SELECT A.GUID,A.RYBH,A.YZ,A.SP "+ " FROM %SRYWYSPB A WHERE GUID=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,guid);
	}
	/**
	 * 修改
	 * @return
	 */
	public int doUpdate(GX_SYS_RYWYSPB rywyspb){
		String sql = "update %SRYWYSPB set rybh=?,yz=?,sp=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
               rywyspb.getRybh(),
               rywyspb.getYz(),
               rywyspb.getSp(),
               rywyspb.getGuid()
				};
		return db.update(sql, obj);

	}
	/**
	 * 删除
	 * @return
	 */
	public int doDelete(String guid){
		String sql="delete %SRYWYSPB where guid =?";
		sql=String.format(sql, SystemSet.gxBz);
		String[] params =guid.split(","); 
		int result=0;
        for(int i=0;i<params.length;i++)
        {  
        	result+= db.update(sql, params[i]);
        }
		return result;
	}

}
