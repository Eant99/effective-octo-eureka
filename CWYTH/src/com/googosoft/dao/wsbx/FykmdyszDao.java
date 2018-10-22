package com.googosoft.dao.wsbx;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.fzgn.jcsz.DwbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.wsbx.jcsz.Cw_fykmdzb;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;
@Repository("fykmdyszDao")
public class FykmdyszDao  extends BaseDao{
	private Logger logger = Logger.getLogger(FykmdyszDao.class);

	/**
	 * 权限model
	 * @param rybh 人员编号
	 * @return
	 */
	public List GetFykmdysz(String sjfyfl) {

		String sql="";
		
		if(sjfyfl.equals("root")){
			 sql=" select (SELECT COUNT(1) FROM Cw_fykmdzb A WHERE A.SJFL = t.guid) AS XJCOUNT,GUID,FYFL,FYMC,SJFl,JDFX,KMBH from  Cw_fykmdzb  T where sjfL='root' ";	
		}else{
			 sql=" select (SELECT COUNT(1) FROM Cw_fykmdzb A WHERE A.SJFL = t.guid) AS XJCOUNT,GUID,FYFL,FYMC,SJFL,JDFX,KMBH from  Cw_fykmdzb  T where sjfL='"+sjfyfl+"' ";
		}
		System.out.println("打印sql========================"+sjfyfl);
		return db.queryForList(sql,new Object[]{});
	}
	
	/**
	 * 增加
	 * @param 
	 * @return
	 */
	public int doAdd(Cw_fykmdzb fykmdzb) {
		String sql = "insert into CW_FYKMDZB(guid,fyfl,fymc,sjfl,jdfx,bz,czr,czrq,fybh,kmbh,zt) "
				+ "values(?,?,?,?,?,?,?,sysdate,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				fykmdzb.getGuid(),
				fykmdzb.getFyfl(),
				fykmdzb.getFymc(),
				fykmdzb.getSjfl(),
				fykmdzb.getJdfx(),
				fykmdzb.getBz(),
				fykmdzb.getCzr(),
				fykmdzb.getFybh(),
				fykmdzb.getKmbh(),
				fykmdzb.getZt()
				
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
//			if(i > 0){ 
//				db.insertRydwqx(LUser.getRybh());
//			}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改
	 * @param Cw_fykmdzb 
	 * @return
	 */
	public int doUpdate(Cw_fykmdzb fykmdzb){
		String sql = "update Cw_fykmdzb set fyfl=?,fymc=?,sjfl=?,jdfx=?,bz=?,fybh=?,kmbh=? where guid=?";
		Object[] obj = new Object[]{
				fykmdzb.getFyfl(),
				fykmdzb.getFymc(),
				fykmdzb.getSjfl(),
				fykmdzb.getJdfx(),
				fykmdzb.getBz(),
				fykmdzb.getFybh(),
				fykmdzb.getKmbh(),
				fykmdzb.getGuid()
				
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
	 * 批量修改
	 * @param Cw_fykmdzb 
	 * @return
	 */
	public int doplUpdate(Cw_fykmdzb fykmdzb,String guid){
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String sql = "update Cw_fykmdzb set fyfl='"+fykmdzb.getFyfl()+"',sjfl='"+fykmdzb.getSjfl()+"',jdfx='"+fykmdzb.getJdfx()+"',fybh='"+fykmdzb.getFybh()+"',kmbh='"+fykmdzb.getKmbh()+"' where guid "+wstr;
	    int i = 0;
		try {  
			i = db.update(sql, params);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 费用科目对应设置
	 * @param Cw_fykmdzb
	 * @return
	 */
	public Map<String, Object> getObjectByGuId(String guid,String sszt,String kjzd) {
		String sql = "select t.guid,t.fyfl,t.fymc,t.sjfl,t.jdfx,t.bz,t.fybh,t.kmbh,k.kmmc as kmmc,sj.fymc as sjfyfl from Cw_fykmdzb t left join cw_kjkmszb k on t.kmbh=k.kmbh and k.sszt='"+sszt+"' and k.kjzd='"+kjzd+"' left join Cw_fykmdzb sj on sj.guid=t.sjfl   where t.guid= ? ";
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 删除费用科目对应设置
	 * 
	 * @param 
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String DELSQL = "DELETE FROM Cw_fykmdzb WHERE guid " + wstr;
		int result = 0;
		try {
			
			int i = db.update(DELSQL, params);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();// 事务回滚
		}
		return result;
	}
	public boolean doCheck(String kmbh) {
		String sql = ("select count(1) from Cw_kjkmszb  where sjfl='"+kmbh+"'" );
		System.err.println("++++++"+sql);
		int count = Integer.parseInt(db.queryForSingleValue(sql));
			if(count>0){
				return true;
			}else{
				return false;
			}
	}
	
	public List<Map<String, Object>> getJsList(String guid, String searchValue,String fyfls) {
		StringBuffer sql = new StringBuffer();
		sql.append("select k.guid,");
		sql.append("decode(fyfl,'1','日常报销','2','差旅费','3','预支付','4','公务接待费','') as fyfl,FYMC, ");
		sql.append("(select a.FYMC from CW_FYKMDZB a where a.guid=K.SJFL ) as SJFL,");
		sql.append("decode(jdfx,'0','贷方','1','借方','') as jdfx, ");
		sql.append("( select KMMC from cw_kjkmszb kj WHERE kj.kmbh=K.KMBH ) AS kmmc,kmbh from CW_FYKMDZB K ");
//		sql.append("where 1=1 and k.zt='1' and  K.guid "+CommonUtils.getInsql(guid)+" ");
		sql.append("where 1=1 and k.zt='1'  ");
		
		if(Validate.noNull(fyfls)){
//			sql.append(" and K.sjfl='"+fyfls+"'  ");
			sql.append(" and K.sjfl='"+fyfls+"' or K.guid='"+fyfls+"'  ");
		}
		if(Validate.noNull(guid)){
			sql.append(" and  k.guid in ('"+guid.trim()+"') ");
		}
		
//		Object[] guid2 = guid.split(",");
//		return db.queryForList(sql.toString(),guid2);
		return db.queryForList(sql.toString());
	}
	
}
