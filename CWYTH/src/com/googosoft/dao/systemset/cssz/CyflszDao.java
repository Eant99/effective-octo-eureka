package com.googosoft.dao.systemset.cssz;

import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.PageDao;
import com.googosoft.pojo.systemset.cssz.ZC_CYFLSZB;
import com.googosoft.util.CommonUtils;

@Repository("cyflszDao")
public class CyflszDao extends BaseDao{
private Logger logger = Logger.getLogger(CyflszDao.class);
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	
	public int doDelete(String gid) {
		String sql = "DELETE zc_cyflszb WHERE GID"+CommonUtils.getInsql(gid);
		sql=String.format(sql, SystemSet.gxBz);
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
	
	public int doAdd(ZC_CYFLSZB tpb) {
		String sql = "insert into zc_cyflszb(gid,mc,flh,flmc,pxxh,lx,szrq,szr) "
				+ "values(?,?,?,?,?,?,sysdate,'"+LUser.getRybh()+"')";
		Object[] obj = new Object[]{
				tpb.getGid(),
				tpb.getMc(),
				tpb.getFlh(),
				tpb.getFlmc(),
				tpb.getPxxh(),
				tpb.getLx()
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
	
	public Map getCyflszxx(String gid){
		String sql ="select t.gid,t.mc,t.flh,t.flmc,t.pxxh,t.lx from zc_cyflszb t  where t.gid=?";
		return db.queryForMap(sql, new Object[]{gid});
	}

	public int doUpdate(ZC_CYFLSZB tpb){
		String sql_update="update zc_cyflszb set mc=?,flh=?,flmc=?,pxxh=?,lx=? where gid=?";
		int i=0;
		try {  
			i =db.update(sql_update,new Object[]{
					tpb.getMc(),
					tpb.getFlh(),
					tpb.getFlmc(),
					tpb.getPxxh(),
					tpb.getLx(),
					tpb.getGid()
			});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	
	public boolean doCheckmc(String mc,String lx){
		String sql = "select count(1) from zc_cyflszb where  mc= ? and lx= ?";
		String count = db.queryForObject(sql,new Object[]{mc,lx}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	public boolean doCheck(String lx,String operateType){
		String sql = "select count(*) from zc_cyflszb where lx=?";
		String count = db.queryForSingleValue(sql,new Object[]{lx});
		if(operateType.equals("U")){
			return true;
		}else{
			if(Integer.valueOf(count)>=5){
				return false;
			}else{
				return true;
			}
		}
		
	}

	public Map findPage() {
		String sql = "select ksdh from %Sxtb A ";
		 sql=String.format(sql, SystemSet.sysBz);
		return db.queryForMap(sql);
	}
}