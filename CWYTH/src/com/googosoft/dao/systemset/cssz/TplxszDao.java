package com.googosoft.dao.systemset.cssz;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.PageDao;
import com.googosoft.pojo.systemset.cssz.ZC_PICTYPESET;
import com.googosoft.util.CommonUtils;

/**
 * 图片类型设置Dao
 * Create by 刘帅 on 2016-10-25 17:40
 */
@Repository("tplxszDao")
public class TplxszDao extends BaseDao{
	
	private Logger logger = Logger.getLogger(TplxszDao.class);
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	/**
	 * 图片类型设置（添加）：
	 * @param tpb
	 * @return
	 */
	public int doAdd(ZC_PICTYPESET tpb){
		String djlx=tpb.getDjlx();
		String sql="select t.mkmc  from "+TnameU.MKB+" t where t.mkbh=?";
		String djmc=db.queryForSingleValue(sql, new Object[]{djlx});
		String sql_insert="insert into %SPICTYPESET values(?,?,?,?,?,?,?)";
		sql_insert=String.format(sql_insert, SystemSet.zcBz);
		int i =0;
		try {  
			i =db.update(sql_insert,new Object[]{
					tpb.getLxbh(),
					tpb.getLxmc(),
					tpb.getDjlx(),
					djmc,
					tpb.getSfbxsc(),
					tpb.getSctj(),
					tpb.getBz()
			});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 图片类型设置（查看）：获取图片类型设置 详细信息
	 * @param lxbh
	 * @return
	 */
	public Map getTplxszxx(String lxbh){
		String sql ="select t.lxbh,t.djmc,t.djlx,t.lxmc,t.bz,t.sfbxsc from %SPICTYPESET t where t.lxbh=? order by djlx,lxbh";
		sql=String.format(sql, SystemSet.zcBz);
		return db.queryForMap(sql, new Object[]{lxbh});
	}
	/**
	 * 图片类型设置（（修改）
	 * @param jsbh
	 * @return
	 */
	public int doUpdate(ZC_PICTYPESET tpb){
		String a =tpb.getLxbh();
		String djlx=tpb.getDjlx();
		String sql="select t.mkmc from "+TnameU.MKB+" t where t.mkbh=?";
		String djmc=db.queryForSingleValue(sql, new Object[]{djlx});
		String sql_update="update %SPICTYPESET set lxmc=?,djlx=?,djmc=?,sfbxsc=?,sctj=?,bz=? where lxbh=?";
		sql_update=String.format(sql_update, SystemSet.zcBz);
		int i=0;
		try {  
			i =db.update(sql_update,new Object[]{
					tpb.getLxmc(),
					tpb.getDjlx(),
					djmc,
					tpb.getSfbxsc(),
					tpb.getSctj(),
					tpb.getBz(),
					a
			});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 图片类型设置（删除）：删除图片类型设置信息
	 * @param lxbh
	 * @return
	 */
	public int doDelete(String lxbh){
		String sql="delete %SPICTYPESET where lxbh "+CommonUtils.getInsql(lxbh);
		sql=String.format(sql, SystemSet.zcBz);
		Object[] obj =lxbh.split(",");
		int i =0;
		try {  
			i =db.update(sql,obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 *  图片类型设置：获取单据名称  下拉选数据
	 * @return
	 */
	public List<Map<String, Object>> getDjmc(){
		String value ="1";
		String sql="select t.MKBH,t.MKMC from %SMKB t where t.tplx='1' and  qxbz='1' order by mkbh";
		sql=String.format(sql, SystemSet.sysBz);
		return db.queryForList(sql);
	}
	/*
	 * 判断要删除的图片类型有没有在用，有，不能删除
	 */
	public int doCheck(String lxbh) {
		String sql="select count(1) from ZC_XGWD where djlx "+CommonUtils.getInsql(lxbh);
		Object[] obj =lxbh.split(",");
		String str="0";
		try {  
			str   =db.queryForObject(sql,obj, String.class);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		if(str.equals("0")){
			return 0;
		}else{
			return 1;
		}
		
	}
}
