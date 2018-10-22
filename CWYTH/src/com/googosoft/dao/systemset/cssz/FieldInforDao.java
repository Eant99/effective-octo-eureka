package com.googosoft.dao.systemset.cssz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;

@Repository("fieldInforDao")
public class FieldInforDao extends BaseDao{
	private Logger logger = Logger.getLogger(FieldInforDao.class);
	/**
	 * 验收单必填项保存
	 */	
	public boolean doSave(String selectItems){
		List sqls = new ArrayList();
		sqls.add("delete from zc_fieldinfor");
		
		List pars = new ArrayList();
		pars.add(null);
		
		if(Validate.noNull(selectItems)){
			String arr[] = selectItems.split(",");
			for(int i = 0; i < arr.length; i++){
				String str = arr[i];
				String pararr[] = str.split("\\|");
				sqls.add("insert into zc_fieldinfor(ctlname,ctlreg,wordnmae,modetype,fieldname,tablename,dtype) values(?,?,?,?,?,?,?)");
				//K|yshd|hdrq|stri|[货到日期]
				pars.add(new Object[]{pararr[2],"1",pararr[2],pararr[0],pararr[4],pararr[1],pararr[3]});
			}
		}
		return db.batchUpdate(sqls, pars);
	}
	/**
	 * 获取必填项信息
	 * @return
	 */
	public List getPageList(){
		String sql = "select A.ctlname,A.ctlreg,A.wordnmae,A.dtype,A.modetype,A.fieldname,A.tablename from zc_fieldinfor A ";
		return db.queryForList(sql);
	}
	/**
	 * 获取必填项信息
	 * @return
	 */
	public List getPageList(String modetype,String tablename,String flh){
		String sql = "select A.ctlname,A.ctlreg,A.wordnmae,A.dtype,A.modetype,A.fieldname,A.tablename from zc_fieldinfor A where 1 = 1 ";
		if(Validate.noNull(modetype)){
			if("zjb".equals(tablename)){
				sql += " and modetype = '" + modetype + "' ";
			}else{
				sql += " and modetype in ('00','" + modetype + "') ";
			}
			if( !"17010000".equals(flh) && modetype.equals("12")){
				sql += " and CTLNAME <> 'qsrq'";
			}
		}
		if(Validate.noNull(tablename)){
			sql += " and tablename = '" + tablename + "' ";
		}
		return db.queryForList(sql);
	}
	/**
	 * 判断是否是必填项
	 * @param ctlname
	 * @param modetype
	 * @return
	 */
	public List getList(String ctlname,String modetype){
		List list = new ArrayList();
		String sql = "select A.ctlname,A.ctlreg,A.wordnmae,A.dtype,A.modetype,A.fieldname,A.tablename from zc_fieldinfor A where ctlname=? and modetype=?";
		try {  
			list = db.queryForList(sql,new Object[]{ctlname,modetype});
		}catch (DataAccessException e){
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return list;
	}
//	public boolean red(String string,String flh) {
//		String sql ="select * from ZC_FIELDINFOR where CTLNAME='"+string+"' and WORDNMAE ='"+string+"' and  MODETYPE='05' and TABLENAME='zjb'";
//		List list  = db.queryForList(sql);
//		if(list.size()>=1){
//			return true;
//		}else{
//			return false;
//		}
//	}
}

