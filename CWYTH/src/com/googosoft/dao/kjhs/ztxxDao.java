package com.googosoft.dao.kjhs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.wsbx.FykmdyszDao;
import com.googosoft.util.DateUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("ztxxDao")
public class ztxxDao extends BaseDao{
	private Logger logger = Logger.getLogger(FykmdyszDao.class);
	private String sql;
	
	public List<Map<String, Object>> selectSrxmList(){
		String sql = "SELECT A.*,ROWNUM AS \"_XH\",(SELECT '('||B.DM||')'||B.MC FROM GX_SYS_DMB B WHERE B.ZL = '"+Constant.XMFL+"' AND B.DM = A.XMFL) AS XMFLMC FROM CW_SRXMB A order by \"_XH\"";
		return db.queryForList(sql);
	}
	//查询账套信息
	public Map<String, Object> selectztxxMapById(String guid){
		Object[] obj = {guid};
		String sql = "select * from CW_ZTXXB where guid = ?";
		return db.queryForMap(sql,obj);
	}
	
	//编辑账套信息
	public int updateSrxm(PageData pd) {
		String sql = "UPDATE CW_ZTXXB SET "
				+ "SRXMBH = ?,SRXMMC = ?,XMFL = ?,SRBZ = ?,BZ = ?,CZR = ?,CZRQ = to_date(?,'yy-mm-dd hh24:mi:ss') where guid = ?";
		Object[] obj = {
				pd.getString("srxmbh"),
				pd.getString("srxmmc"),
				pd.getString("xmfl"),
				pd.getString("srbz"),
				pd.getString("bz"),
				pd.getString("czr"),
				DateUtil.getTime(),
				pd.getString("guid")
//				String sql = "insert into CW_ZTXXB(guid,ztmc,kjnd,qjs,qyrq,czrq) "
//				+ "values(sys_guid(),?,?,?,?,sysdate)";
//		Object[] obj = new Object[]{
//				pd.getString("ztmc"),
//				pd.getString("kjnd"),
//				pd.getString("qjs"),
////				pd.getString("kssj"),
		};
		return db.update(sql, obj);
	}
	public int insertSrxm(PageData pd) {
		String sql = "insert into cw_srxmb values(sys_guid(),?,?,?,?,?,?,to_date(?,'yy-mm-dd hh24:mi:ss'))";
		Object[] obj = {
				pd.getString("srxmbh"),
				pd.getString("srxmmc"),
				pd.getString("xmfl"),
				pd.getString("srbz"),
				pd.getString("bz"),
				pd.getString("czr"),
				DateUtil.getTime()

		};
		return db.update(sql,obj);
	}
	/**
	 *验证账套名称是否存在
	 * @param 
	 * @return
	 */
	public boolean getObjectById(String ztmc,String guid){
		String sql="";
		if(Validate.isNull(guid)) {
		  sql= "select count(0) from cw_ztxxb where ztmc='"+ztmc+"' ";
		}else {
		  sql = "select count(0) from cw_ztxxb where ztmc='"+ztmc+"' and guid not in ('"+guid+"')";		 
		}
		   int aa = db.queryForInt(sql);
		   if(aa==0) {
			  return true;			   
		   } 
		     return false;
		       
		
		
		
	}
//	public Map<String, Object> getObjectById(String kjqj) {
//		String sql="select p.pzzt from cw_pzlrzb p where kjqj= ?";
//		return db.queryForMap(sql,new Object[]{kjqj});
//	}
	//删除账套信息
	public int deleteztxx(PageData pd) {
		String guid = pd.getString("guid");
		String sql = "delete from cw_ztxxb where guid in ('"+guid+"')";
		String sql1 = "delete from cw_qmjzb where sszt in ('"+guid+"')";
		       db.update(sql);
		return db.update(sql1);
	}
	public boolean checkSrxmbhExist(PageData pd) {
		String srxmbh = pd.getString("srxmbh");
		String slq = "select * from cw_srxmb where srxmbh = ?";
		if(db.queryForList(slq,srxmbh).size() > 0) {
			return true;
		}
		return false;
	}
	//新增账套信息
	public boolean doAdd(PageData pd,String guid) {
		String qyrq=pd.getString("qyrq");
        String ztnd=qyrq.substring(0,4);
        String kjqj=qyrq.substring(5,7);
        
		String sql = "insert into CW_ZTXXB(guid,ztmc,kjnd,qjs,qyrq,czrq) "
				+ "values('"+guid+"','"+pd.getString("ztmc")+"','"+pd.getString("kjnd")+"','"+pd.getString("qjs")+"','"+pd.getString("qyrq")+"',sysdate)";
	
		String sql2 = "insert into CW_qmjzb(guid,ztnd,kjqj,sfjz,jzrq,jzry,czrq,sszt) "
				+ "values(sys_guid(),'"+ztnd+"','"+kjqj+"','0','','',sysdate,'"+guid+"')";
	List list=new ArrayList();
	list.add(sql);
	list.add(sql2);
		int i = 0;
		boolean bool;
		try {  
			
			bool=db.batchUpdate(list);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    bool=false;
		}
		return bool;
	}
	/**
	 * 删除
	 * 
	 * @param 
	 * @return
	 */
//	public int deleteztxxById(String guid) {
//		final Object[] params = guid.split(",");
//		String wstr = CommonUtils.getInsql(guid);
//		String DELSQL = "DELETE FROM Cw_fykmdzb WHERE guid " + wstr;
//		int result = 0;
//		try {
//			
//			int i = db.update(DELSQL, params);
//		} catch (DataAccessException e) {
//			SQLException sqle = (SQLException) e.getCause();
//			logger.error("数据库操作失败\n" + sqle);
//			result = -1;
//			TransactionAspectSupport.currentTransactionStatus()
//					.setRollbackOnly();// 事务回滚
//		}
//		return result;
//	}
	public int doUpdate(PageData pd) {
		String sql = "UPDATE CW_ZTXXB SET ztmc=?,kjnd=?,qjs=?,qyrq=? where guid = ?";
		Object[] obj = {
				pd.getString("ztmc"),
				pd.getString("kjnd"),
				pd.getString("qjs"),
				pd.getString("qyrq"),
				pd.getString("guid")
		
		};
		return db.update(sql, obj);
	
}

}

