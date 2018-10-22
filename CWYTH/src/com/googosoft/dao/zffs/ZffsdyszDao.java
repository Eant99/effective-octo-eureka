package com.googosoft.dao.zffs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.wsbx.jcsz.Cw_zffsdzb;
import com.googosoft.util.Validate;

@Repository("zffsdyszDao")
public class ZffsdyszDao extends BaseDao{
	
	/**
	 * 费用科目对应设置
	 * @param Cw_fykmdzb
	 * @return
	 */
	public Map<String, Object> getObjectByGuId(String guid,String sszt,String kjzd) {
		String sql = "select guid,kmbh,bz,jdfx,zffs,(select kmmc from cw_kjkmszb kj where kj.kmbh=t.kmbh and kj.sszt=? and kj.kjzd=? ) as kmmc from cw_zffsdzb t where t.guid=?";
		return db.queryForMap(sql,new Object[]{sszt,kjzd,guid});
	}
	/**
	 * 增加支付信息
	 * @param zffsb
	 * @return
	 */	
	public int doAdd(Cw_zffsdzb zffsb) {
		String sql = "insert into cw_zffsdzb(guid,zffs,jdfx,bz,kmbh,czr,czrq,sszt,kjzd) values(?,?,?,?,?,?,sysdate,?,?)";
		Object[] obj = new Object[] {
				zffsb.getGuid(),
				zffsb.getZffs(),
				zffsb.getJdfx(),
				zffsb.getBz(),
				zffsb.getKmbh(),
				zffsb.getCzr(),
				zffsb.getSszt(),
				zffsb.getKjzd()
		};
		int i = db.update(sql,obj);
		if(i>0){
			addOtherKjzd(zffsb);
		}
		return i;
	}
	/**
	 * 增加其他的会计制度
	 * @param zffsb
	 */
	public void addOtherKjzd(Cw_zffsdzb zffsb){
		List list = new ArrayList<Map<String,Object>>();
		String kjzd = Validate.isNullToDefaultString(zffsb.getKjzd(), "");
		String sszt = Validate.isNullToDefaultString(zffsb.getSszt(), "");
		String search = "select distinct guid from cw_kjzdqy_zdb t where guid<>? " +
				" and guid is not null and guid not in(select kjzd from cw_zffsdzb z where sszt=? and zffs=?)";
		list = db.queryForList(search,new Object[]{kjzd,sszt,zffsb.getZffs()});
		if(list.size()==0){
			return;
		}
		ArrayList<String> sqlList = new ArrayList<String>();
		ArrayList<Object[]> objList = new ArrayList<Object[]>();
		for(int i=0,len=list.size();i<len;i++){
			Map map = (Map)list.get(i);
			String zd = Validate.isNullToDefaultString(map.get("GUID"), "");
			StringBuffer sql = new StringBuffer();
			sql.append(" insert into cw_zffsdzb(guid,zffs,czr,czrq,sszt,kjzd)");
			sql.append(" values(");
			sql.append(" sys_guid(),?,?,sysdate,?,?");
			sql.append(" )");
			sqlList.add(sql.toString());
			Object[] obj = new Object[]{
				zffsb.getZffs(),zffsb.getCzr(),sszt,zd
			};
			objList.add(obj);
		}
		db.batchUpdate(sqlList, objList);
	}
	
	/**
	 * 检查该支付方式在所属账套和会计制度是否存在存在返回false
	 * @param zffs
	 * @param kjzd
	 * @param sszt
	 * @return
	 */
	public boolean checkZffs(String zffs,String kjzd,String sszt,String guid){
		String sql = "select guid from CW_ZFFSDZB where zffs=? and kjzd=? and sszt=?";
		if(Validate.noNull(guid)){
			sql += " and guid<>'"+guid+"'";
		}
		List list = db.queryForList(sql, new Object[]{zffs,kjzd,sszt});
		if(list.size()>0){
			return false;
		}
		return true;
	}
	
	/**
	 * 修改支付信息
	 * @param zffsb
	 * @return
	 */	
	public int doupdate(Cw_zffsdzb zffsb) {
		String sql = "update Cw_zffsdzb set kmbh=?,bz=?,jdfx=?,zffs=? where guid=?";
		
		Object[] obj = new Object[] {
				zffsb.getKmbh(),
				zffsb.getBz(),
				zffsb.getJdfx(),
				zffsb.getZffs(),
				zffsb.getGuid()
		};
		return db.update(sql,obj);
	}
	/**
	 * 删除费用科目对应设置
	 * 
	 * @param 
	 * @return
	 */
	public int doDel(String guid) {
		String sql="delete CW_zffsdzb where guid in ('"+guid+"')";
		return  db.update(sql);
	}
	
	public List<Map<String, Object>> getJsList(String guid, String searchValue,
			String rybh, String s1) {
		StringBuffer sql = new StringBuffer();
		sql.append("select guid,rownum as xh,");
		sql.append("(select mc from gx_sys_dmb dm where dm.dm=K.zffs and dm.zl='zffs') as zffs1,zffs, ");
		sql.append("(case jdfx when '0' then '借方' else '贷方' end) as jdfx,");
		sql.append("(select kmmc from cw_kjkmszb kj where kj.kmbh=K.kmbh) as kmmc from Cw_zffsdzb k ");
		sql.append("WHERE 1=1  ");
		if(Validate.noNull(guid)){
			sql.append(" and k.guid in ('"+guid.trim()+"') ");
		}
		sql.append("  order by JDFX asc ");
		
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}

}
