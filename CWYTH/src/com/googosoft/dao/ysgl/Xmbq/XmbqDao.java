package com.googosoft.dao.ysgl.Xmbq;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.ysgl.CW_XMBQMXB;
import com.googosoft.util.PageData;

@Repository("xmbqDao")
public class XmbqDao extends BaseDao{
	
	/**
	 * 判断序号是否重复（增加）
	 * 
	 * @param pd
	 * @return
	 */
	public int getXhById(PageData pd) {
		String sql = "select count(1) from cw_xmbqzb b where b.bqbh = '"+pd.getString("bqbh")+"'";
		return db.queryForInt(sql);
	}
	
	/**
	 * 新增保存项目标签主表
	 * 
	 * @param pd
	 * @return
	 */

	public int doAdd(PageData pd) {
		String sql = "insert into cw_xmbqzb(guid,bqbh,bqmc,bz) values(?,?,?,?)";
		Object[] obj = {
			    pd.getString("guid"),
			    pd.getString("bqbh"),
			    pd.getString("bqmc"),
				pd.getString("bz")
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 删除明细表信息
	 * @param zbid
	 * @return
	 */
	public int doDelMxb(String zbid) {
		String sql="delete cw_xmbqmxb where zbid=?";
		int i=0;
		i=db.update(sql,new Object[] {zbid});
		return i;
	}
	
	/**
	 * 保存项目标签明细表
	 * @param xmbqmxb
	 * @return
	 */
	public int doSaveMxb(CW_XMBQMXB xmbqmxb) {
		String sql = "insert into cw_xmbqmxb(guid,zbid,xmid,xmbh,xmmc,bmmc) values(?,?,?,?,?,?)";
		int i = 0;
		try{
			i = db.update(sql,new Object[]{xmbqmxb.getGuid(),xmbqmxb.getZbid(),xmbqmxb.getXmid(),xmbqmxb.getXmbh(),xmbqmxb.getXmmc(),xmbqmxb.getBmmc()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause(); 
		    return -1;
		}
		return i;
	}
	
	/**
	 * 获得项目标签编辑信息
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getXmbqById(String guid) {
		String sql="select guid,bqbh,bqmc,bz from cw_xmbqzb where guid=?";
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 获得项目标签明细编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getXmbqmxById(String guid) {
		String sql="select guid,xmid,zbid,xmmc,xmbh,bmmc from cw_xmbqmxb where zbid=?";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	
	/**
	 * 编辑保存项目标签主表
	 * @param pd
	 * @param guid
	 * @return
	 */
	public int updXmbqzb(PageData pd,String guid) {
		String sql="update cw_xmbqzb set bqbh=?,bqmc=?,bz=? where guid='"+guid+"'";
		Object[] obj= {
			pd.getString("bqbh"),
			pd.getString("bqmc"),
			pd.getString("bz")
		};
		return db.update(sql,obj);
	}
	
	/**
	 * 删除项目标签信息
	 * @param guid
	 * @return
	 */
	public int delete(String guid) {
		String sql1 = "delete from cw_xmbqzb where guid in ('"+guid+"')";
		String sql2 = "delete from cw_xmbqmxb where zbid in ('"+guid+"')";
		       db.update(sql2);
		return db.update(sql1);
    }
	
	/**
	 * 验证标签编号是否重复
	 */
	public boolean getObjectByBqbh(String guid,String bqbh) {
		String sql = "select count(0) from cw_xmbqzb where bqbh='"+bqbh+"' and guid not in ('"+guid+"')";
		int aa = db.queryForInt(sql);
		if(aa == 0) {
			return true;
		}
		return false;
	}

}
