package com.googosoft.dao.srgl.xzsblr;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.ysgl.grjfsz.GrjfszDao;
import com.googosoft.pojo.srgl.xzsblr.CW_XWRYXXB;
import com.googosoft.util.Validate;

@Repository("xwryxxcjDao")
public class XwryxxcjDao  extends BaseDao{
	private Logger logger = Logger.getLogger(GrjfszDao.class);

	public int doAdd(CW_XWRYXXB xwry) {
		ArrayList lists=new ArrayList();
		ArrayList objcs=new ArrayList();
		String sql = "INSERT INTO CW_XWRYXXB (XH,XM,XBM,CSRQ,CSDM,JG,MZM,GJDQM,SFZJLXM,SFZH,LXFS,YX,CZR,CZRQ,BZ,YHDM,YHMC,KHYHH,LHH) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = new Object[]{
				 xwry.getXH(),
				 xwry.getXM(),
				 xwry.getXBM(),
				 xwry.getCSRQ(),
				 xwry.getCSDM(),
				 xwry.getJG(),
				 xwry.getMZM(),
				 xwry.getGJDQM(),
				 xwry.getSFZJLXM(),
				 xwry.getSFZH(),
				 xwry.getLXFS(),
				 xwry.getYX(),
				 xwry.getCZR(),
				 xwry.getCZRQ(),
				 xwry.getBZ(),
				 xwry.getYHDM(),
				 xwry.getYHMC(),
				 xwry.getKHYHH(),
				 xwry.getLHH()
		};
		lists.add(sql);
		objcs.add(obj);
		int i = 0;
		boolean k=false;
		try {  
				k = db.batchUpdate(lists, objcs);
			if(k){
				i=1;
			}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	public int doDelete2(String bzbh){
		String sql = "DELETE FROM CW_XWRYXXB WHERE GUID IN('"+bzbh+"')";
		return db.update(sql);
	}
	
	public int doDelete(String bzbh){
		String sql = "DELETE FROM CW_XWRYXXB WHERE GUID = '"+bzbh+"'";
		return db.update(sql);
	}
	/**
	 * 修改补助标准设置表信息
	 * @param bzbh  
	 * @return 
	 */
	public int doUpdate(CW_XWRYXXB xwry) {
		String sql = "update CW_XWRYXXB set XM=?,XBM=?,CSRQ=?,CSDM=?,JG=?,MZM=?,GJDQM=?,SFZJLXM=?," +
				"SFZH=?,LXFS=?,YX=?,CZR=?,CZRQ=?,BZ=?,YHDM=?,YHMC=?,KHYHH=?,LHH=? where guid = ?";
		Object[] obj = new Object[]{
				 xwry.getXM(),
				 xwry.getXBM(),
				 xwry.getCSRQ(),
				 xwry.getCSDM(),
				 xwry.getJG(),
				 xwry.getMZM(),
				 xwry.getGJDQM(),
				 xwry.getSFZJLXM(),
				 xwry.getSFZH(),
				 xwry.getLXFS(),
				 xwry.getYX(),
				 xwry.getCZR(),
				 xwry.getCZRQ(),
				 xwry.getBZ(),
				 xwry.getYHDM(),
				 xwry.getYHMC(),
				 xwry.getKHYHH(),
				 xwry.getLHH(),
				 xwry.getGUID()
		};
	    int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	public Map<?, ?> getObjectById(String bzbh) {
		
		String sql = "select GUID,XH,XM,XBM,to_date(csrq,'yyyy-MM-dd') as csrq,CSDM," +
				"JG,(select  b.mc from gx_sys_dmb b where zl='105' and b.dm=L.MZM) as MZM," +
				"(select  b.mc from gx_sys_dmb b where zl='02' and b.dm=L.GJDQM) as  GJDQM,SFZJLXM,SFZH,LXFS,YX,CZR," +
				"BZ,YHDM,YHMC,KHYHH,LHH from CW_XWRYXXB  L where guid='"+bzbh+"'";
		return db.queryForMap(sql);
	}
	
	public List<Map<String, Object>> getList(String searchValue,String guid,String sszt) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select GUID,XH,XM,(select b.MC from gx_sys_dmb b where b.zl = '20' and b.dm = t.xbm) as xbm,to_date(csrq,'yyyy-MM-dd') as csrq,(select b.MC from gx_sys_dmb b where b.zl = '111' and b.dm = t.SFZJLXM) as SFZJLXM,SFZH,YHMC,KHYHH,LHH");
		sql.append(" from CW_XWRYXXB t where 1=1 ");
	//	sql.append(" where t.sszt = '"+sszt+"' ");
		if(Validate.noNull(guid)){
			sql.append(" and t.guid in ('"+guid+"') ");
		}
		return db.queryForList(sql.toString());
	}
}
