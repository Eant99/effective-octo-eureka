package com.googosoft.dao.systemset.cdgl;


import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.kylbx.KylbxDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("cdglDao")
public class CdglDao extends BaseDao{

	private Logger logger = Logger.getLogger(KylbxDao.class);
	/**
	 * 获取下级菜单
	 * @param mkbh
	 * @return
	 */
	public List GetXjcd(String mkbh) {
		String sql="";
		if(mkbh.equals("root")){
			 sql=" select b.*,(SELECT COUNT(1) FROM zc_sys_mkb A WHERE substr(a.mkbh,0,2)=b.mkbh and length(a.mkbh)=4) as count from zc_sys_mkb b   where  b.mkbh like '__' and b.url is null  and b.xh is not null and b.qxbz='1'   ";	
		}else{
			 sql=" select b.*,(SELECT COUNT(1) FROM zc_sys_mkb A WHERE A.mkbh like b.mkbh||'__') as count from zc_sys_mkb b  where  mkbh like '"+mkbh+"__' ";
		}
		return db.queryForList(sql,new Object[]{});
	}
/**
 * 获取现有的所有一级菜单模块编号
 * @return
 */
	public List<Map<String, Object>> getYjMkbh() {
		String sql = " select mkbh from zc_sys_mkb   where  mkbh like '__' ";
//		Map<String, Object> map = new HashMap<>();
//		try {
//			map = db.queryForMap(sql);
//		} catch (Exception e) {
//			return null;
//		}
		return db.queryForList(sql);
	}
	/**
	 * 获取次级模块编号
	 * @return
	 */
	public List<Map<String, Object>> getCjMkbh(String mkbh) {
		String sql = " select mkbh from zc_sys_mkb   where  mkbh like '"+mkbh+"__' ";
//		Map<String, Object> map = new HashMap<>();
//		try {
//			map = db.queryForMap(sql);
//		} catch (Exception e) {
//			return null;
//		}
		return db.queryForList(sql);
	}
	/**
	 * 保存
	 * @param map
	 * @return
	 */
	public int doSave(PageData pd,Map<String, Object> map) {
		String operateType = (String) map.get("operateType");
		String mkbh = (String) map.get("mkbh");
		String mkmc = (String) map.get("mkmc");
		String url = (String) map.get("url");
		String xh = (String) map.get("xh");
		String icon = (String) map.get("icon");//菜单样式
		String xtbz = (String) map.get("xtbz");//系统标志
		String qxbz = Validate.isNullToDefaultString(pd.getString("qxbz"),"0")  ;//权限标志
		String sql="";
		if("U".equals(operateType)){
			sql = " update zc_sys_mkb x  set x.mkmc='"+mkmc+"',x.url='"+url+"',x.xh='"+xh+"',x.icon='"+icon+"',x.xtbz='"+xtbz+"',x.qxbz='"+qxbz+"' where x.mkbh='"+mkbh+"'  ";
		}else{
			sql = " insert into  zc_sys_mkb x (x.mkbh,x.mkmc,x.url,x.xh,x.qxbz,x.icon,x.xtbz) values('"+mkbh+"','"+mkmc+"','"+url+"','"+xh+"','"+qxbz+"','"+icon+"','"+xtbz+"')  ";
		}
		return db.update(sql);
	}
	/**
	 * 删除
	 * @param kmbh
	 * @return
	 */
	public int doDel(String mkbh) {
		String sql=" DELETE from zc_sys_mkb where mkbh in ('"+mkbh+"')";
		int result = 0;
		try {
			result = db.update(sql);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();// 事务回滚
		}
		return result;
	}
	/**
	 * 修改回显
	 * @param mkbh
	 * @return
	 */
	public Map<String, Object> getByMkbh(String mkbh) {
		String sql=" select * from zc_sys_mkb where mkbh ='"+mkbh+"' ";
		return db.queryForMap(sql);
	}

}
