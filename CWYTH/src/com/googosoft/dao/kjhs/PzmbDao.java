package com.googosoft.dao.kjhs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.Cw_pzmb;
import com.googosoft.pojo.kjhs.Cw_pzmbmx;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("pzmbDao")
public class PzmbDao extends BaseDao{
	
	/**
	 * 添加凭证模板
	 * @param dmb
	 * @return
	 */
	public int doAddpzmb(Cw_pzmb pzmb) {
		String mbbh = GenAutoKey.makeCkbh("Cw_pzmbzb", "mbbh", "2");
		String sql = "insert into cw_pzmbzb(guid,mbbh,mbnr,bz,pzzy,sszt) values(?,'"+mbbh+"',?,?,?,?)";
		int i = 0; 
		try{
			i = db.update(sql,new Object[]{pzmb.getGuid(),pzmb.getMbnr(),pzmb.getBz(),pzmb.getPzzy(),pzmb.getSszt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause(); 
		    return -1;
		}
		return i;
	}
	/**
	 * 修改凭证模板
	 * @param dmb
	 * @return
	 */
	public int doupdatepzmb(Cw_pzmb pzmb) {
		String sql = "update cw_pzmbzb set mbbh=?,mbnr=?,bz=?,pzzy=? where guid=?";
		int i = 0;
		try{
			i = db.update(sql,new Object[]{pzmb.getMbbh(),pzmb.getMbnr(),pzmb.getBz(),pzmb.getPzzy(),pzmb.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause(); 
		    return -1;
		}
		return i;
	}
	/**
	 * 验证凭证模板是否重复
	 */
	public boolean getObjectByMbbh(String guid,String mbbh) {
		String sql = "select count(0) from cw_pzmbzb where mbbh='"+mbbh+"' and guid not in ('"+guid+"')";
		int aa = db.queryForInt(sql);
		if(aa == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 修改凭证模板明细
	 * @param dmb
	 * @return
	 */
	public int doupdatepzmbmx(Cw_pzmbmx pzmbmx) {
		String sql = "update cw_pzmbmxb set mbbh=?,kmbh=? where guid=?";
		int i = 0;
		try{
			i = db.update(sql,new Object[]{pzmbmx.getMbbh(),pzmbmx.getKmbh(),pzmbmx.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause(); 
		    return -1;
		}
		return i;
	}
	/**
	 * 添加凭证模板
	 * @param dmb
	 * @return
	 */
	public int doAddpzmbmx(Cw_pzmbmx pzmbmx) {
		String sql = "insert into cw_pzmbmxb(guid,mbbh,kmbh,sszt,zy,indexnew) values(?,?,?,?,?,?)";
		int i = 0;
		try{
			i = db.update(sql,new Object[]{pzmbmx.getGuid(),pzmbmx.getMbbh(),pzmbmx.getKmbh(),pzmbmx.getSszt(),pzmbmx.getZy(),pzmbmx.getXh()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause(); 
		    return -1;
		}
		return i;
	}
	/**
	 * 删除凭证模板明细
	 * 
	 */
	public int dodeletepzmbmx(String mbbh) {
		String sql="delete cw_pzmbmxb where mbbh=?";
		int i=0;
		i=db.update(sql,new Object[] {mbbh});
		return i;
	}
	/**
	 * 获得凭证模板编辑信息
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getpzmbById(String guid) {
		String sql="select mbbh,mbnr,bz,pzzy from cw_pzmbzb where guid=?";
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 获得凭证模板明细编辑信息
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getpzmbmxById(String guid,String kjzd) {
		String sql="select guid,kmbh,mbbh,(select kj.kmmc from cw_kjkmszb kj where kj.kmbh=pz.kmbh and kj.kjzd='"+kjzd+"' )as kmmc,zy from cw_pzmbmxb pz where  mbbh=?";
		return db.queryForList(sql,new Object[]{guid});
		
	}
	/**
	 * 凭证模板批量删除
	 * @param guid
	 * @return
	 */
	public int doDeletes(String guid) {	
		String sql = "DELETE CW_pzmbzb WHERE guid in ('"+guid+"')";
		int i=0;
	    i=db.update(sql);	
		return i;
		
	}
	public int dodelete(String guid) {
		String sql2 = "DELETE CW_pzmbmxb WHERE mbbh in ('"+guid+"')";
		int i=0;
		i=db.update(sql2);	
		return i;
	}
	public List<Map<String, Object>> getList(String searchValue, String guid,String sszt) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  *  from  (select rownum as xh,guid,mbbh,mbnr,bz,"
				+ "(select wm_concat(to_char(m.zy)) from cw_pzmbmxb m where m.mbbh = a.guid) as zy,"
				+ "(select wm_concat(to_char(m.kmbh)) from cw_pzmbmxb m where m.mbbh = a.guid) as kmbh,"
				+ "(select wm_concat(to_char(m.kmmc)) from cw_kjkmszb m left join cw_pzmbmxb b on b.kmbh = m.kmbh where  b.mbbh = a.guid) as kmmc"
				+ " from cw_pzmbzb a where  sszt='"+sszt+"')a  where 1=1 ");
//		if(Validate.noNull(guid)){
//			sql.append(" and a.guid "+CommonUtils.getInsql(guid)+" ");
//		}
		
		if(Validate.noNull(guid)){
			sql.append(" and  a.guid in ('"+guid.trim()+"') ");
		}
		sql.append(" order by mbbh");
//		Object[] guid2 = guid.split(",");
//		return db.queryForList(sql.toString(),guid2);
		return db.queryForList(sql.toString());
	}
	public List getxx(String guid) {
 		String sql1 = "select mbbh,sszt,guid from cw_pzmbzb where guid in ('"+guid+"')";
 		return db.queryForList(sql1);
 	}
 	public int updatebh(String sszt,String mbbh) {
 		String sql = "update cw_pzmbzb set mbbh=to_char(mbbh-1,'FM909') where mbbh>'"+mbbh+"' and sszt = '"+sszt+"'";
 		return db.update(sql);
 	}

}
