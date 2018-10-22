package com.googosoft.dao.base;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.pojo.system.ZC_XGWD;

@Repository("fileDao")
public class FileDao extends BaseDao{
	private Logger logger = Logger.getLogger(FileDao.class);
	
	/**
	 * 保存附件
	 * @param fj
	 * @return
	 */
	public int doAdd(ZC_XGWD fj){
		String sql = " insert into ZC_XGWD (guid,djlx,dbh,filename,rybh,scsj,path) "
				+ " values (?,?,?,?,?,sysdate,?) ";
		Object[] obj = new Object[]{
				fj.getGuid(),
				fj.getDjlx(),
				fj.getDbh(),
				fj.getFilename(),
				fj.getRybh(),
				fj.getPath()
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
	/**
	 * 删除附件
	 * @param id
	 * @return
	 */
	public int doDelete(String id) {
		String sql = "DELETE FROM ZC_XGWD WHERE GUID = ?";
		int i = 0;
		try {  
			i = db.update(sql, new Object[]{id});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 从附件表取附件信息
	 * @param czbgbh
	 * @return
	 */
	public List getFjList(String djbh,String mkbh) {
		
			String sql = " select k.guid,k.djlx,k.filename,k.path from zc_xgwd k where k.dbh = ? ";
			return db.queryForList(sql,new Object[]{djbh});
		
	}
	/**
	 * 根据模块编号从图片类型设置表中查单据类型
	 * @param mkbh
	 * @return
	 */
	public List getDjlxList(String mkbh) {
		if(MenuFlag.ZCJZ_GLY.equals(mkbh)||MenuFlag.ZCJZ_LYR.equals(mkbh)){//只用于资产建账
			String sql = "select k.lxbh,k.lxmc,k.sfbxsc from zc_pictypeset k where k.djlx in ('"+MenuFlag.ZCJZ_LYR+"','"+MenuFlag.ZCJZ_GLY+"') order by lxbh";
			return db.queryForList(sql);
		}else{
			String sql = "select k.lxbh,k.lxmc,k.sfbxsc from zc_pictypeset k where k.djlx = ? order by lxbh";
			return db.queryForList(sql,mkbh);
		}
	}
	/**
	 * 必须上传信息验证
	 * @param mkbh
	 * @param lxbh
	 * @return
	 */
	public boolean dobxscfj(String mkbh,String lxbh){
		String sql = "select count(*) from zc_pictypeset where sfbxsc='1' and djlx=? and lxbh in ('"+lxbh+"')";
		String i = db.queryForSingleValue(sql, new Object[]{mkbh});
		String sql1 = "select count(*) from zc_pictypeset where sfbxsc='1' and djlx=?";
		String j = db.queryForSingleValue(sql1, new Object[]{mkbh});
		if(i.equals(j)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 必须上传文件的类型编号和名称
	 * @param djlx
	 * @return
	 */
	public List dobxscfjlx(String djlx){
		String sql = "select lxbh,lxmc from zc_pictypeset where sfbxsc='1' and ";
		if(MenuFlag.ZCJZ_GLY.equals(djlx)||MenuFlag.ZCJZ_LYR.equals(djlx)){//只用于资产建账
			sql += " djlx in ('" + MenuFlag.ZCJZ_LYR + "',?) ";
		}
		else{
			sql += " djlx = ? ";
		}
		return db.queryForList(sql,new Object[]{djlx});
	}
	
	/**
	 * 已上传的lxbh
	 * @param dbh
	 * @return
	 */
	public String findLxbh(String dbh){
		String sql = "select djlx from zc_xgwd where dbh=?";
		Object[] obj = new Object[]{dbh};
		List<Map<String, Object>> list = null;
		list = db.queryForList(sql, obj);
		StringBuffer lxbh = new StringBuffer();
		for(int i= 0; i<list.size();i++){
			if(i!=0){
				lxbh.append(",");
			}
			lxbh.append(list.get(i).get("djlx"));
		}
		return lxbh.toString();
	}
}
