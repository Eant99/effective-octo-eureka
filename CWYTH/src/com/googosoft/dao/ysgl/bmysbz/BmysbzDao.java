package com.googosoft.dao.ysgl.bmysbz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.service.ysgl.bmysbz.CW_JBZCYSB;
import com.googosoft.service.ysgl.bmysbz.CW_JBZCYSMXB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSB;
import com.googosoft.service.ysgl.bmysbz.CW_SRYSMXB;
import com.googosoft.service.ysgl.bmysbz.CW_XMZCYSB;
import com.googosoft.service.ysgl.bmysbz.CW_XMZCYSMXB;
import com.googosoft.util.CommonUtils;

@Repository("bmysbzDao")
public class BmysbzDao extends BaseDao{
	private Logger logger = Logger.getLogger(BmysbzDao.class);
	
	//部门预算树
	public  List bmysMenu(){
		String sql = "select t.dm,t.mc from GX_SYS_DMB t where zl='"+Constant.BMYSBZ+"'";		
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 添加收入预算信息
	 * @param dmb
	 * @return
	 */
	public int doAdd(CW_SRYSB srysb) {
		String sql = "insert into cw_srysb(guid,sbry,sbbm,sbnd,dynsrhz,densrhz,dsnsrhz,csyj,kzfw,qrzt)values(?,?,?,to_date(?,'yyyy'),?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{srysb.getGuid(),srysb.getSbry(),srysb.getSbbm(),srysb.getSbnd(),srysb.getDynsrhz(),srysb.getDensrhz(),srysb.getDsnsrhz(),
					srysb.getCsyj(),srysb.getKzfw(),srysb.getQrzt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加基本支出预算信息
	 * @param dmb
	 * @return
	 */
	public int doAddJbzcys(CW_JBZCYSB jbzcys) {
		String sql = "insert into CW_JBZCYSB(guid,sbry,sbbm,sbnd,dynzchz,denzchz,dsnzchz,csyj,kzfw,qrzt)values(?,?,?,to_date(?,'yyyy'),?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{jbzcys.getGuid(),jbzcys.getSbry(),jbzcys.getSbbm(),jbzcys.getSbnd(),jbzcys.getDynzchz(),jbzcys.getDenzchz(),jbzcys.getDsnzchz(),
					jbzcys.getCsyj(),jbzcys.getKzfw(),jbzcys.getQrzt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加项目支出预算信息
	 * @param dmb
	 * @return
	 */
	public int doAddXmzcys(CW_XMZCYSB xmzcysb) {
		String sql = "insert into CW_XMZCYSB(guid,sbry,sbbm,sbnd,ktbh,ktmc,zcr,xmlx,ktkssj,ktjssj,dynzchz,denzchz,dsnzchz,csyj,kzfw)values(?,?,?,to_date(?,'yyyy'),?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{xmzcysb.getGuid(),xmzcysb.getSbry(),xmzcysb.getSbbm(),xmzcysb.getSbnd(),xmzcysb.getKtbh(),xmzcysb.getKtmc(),
					xmzcysb.getZcr(),xmzcysb.getXmlx(),xmzcysb.getKtkssj(),xmzcysb.getKtjssj(),xmzcysb.getDynzchz(),xmzcysb.getDenzchz(),xmzcysb.getDsnzchz(),
					xmzcysb.getCsyj(),xmzcysb.getKzfw()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 编辑收入预算信息
	 * @param dmb
	 * @return
	 */
	public int doedit(CW_SRYSB srysb) {
		String sql = "update CW_SRYSB set  sbry=?,sbbm=?,sbnd=to_date(?,'yyyy'),dynsrhz=?,densrhz=?,dsnsrhz=?,csyj=?,kzfw=?,qrzt=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{srysb.getSbry(),srysb.getSbbm(),srysb.getSbnd(),srysb.getDynsrhz(),srysb.getDensrhz(),srysb.getDsnsrhz(),
					srysb.getCsyj(),srysb.getKzfw(),srysb.getQrzt(),srysb.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 编辑基本支出预算信息
	 * @param dmb
	 * @return
	 */
	public int doeditJbzcys(CW_JBZCYSB jbzcys) {
		String sql = "update CW_JBZCYSB set  sbry=?,sbbm=?,sbnd=to_date(?,'yyyy'),dynzchz=?,denzchz=?,dsnzchz=?,csyj=?,kzfw=?,qrzt=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{jbzcys.getSbry(),jbzcys.getSbbm(),jbzcys.getSbnd(),jbzcys.getDynzchz(),jbzcys.getDenzchz(),jbzcys.getDsnzchz(),
					jbzcys.getCsyj(),jbzcys.getKzfw(),jbzcys.getQrzt(),jbzcys.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 编辑基本支出预算信息
	 * @param dmb
	 * @return
	 */
	public int doeditXmzcys(CW_XMZCYSB xmzcys) {
		String sql = "update CW_XMZCYSB set  sbry=?,sbbm=?,sbnd=to_date(?,'yyyy'),ktmc=?,ktbh=?,zcr=?,xmlx=?,ktkssj=to_date(?,'yyyy-mm-dd'),ktjssj=to_date(?,'yyyy-mm-dd'),dynzchz=?,denzchz=?,dsnzchz=?,csyj=?,kzfw=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{xmzcys.getSbry(),xmzcys.getSbbm(),xmzcys.getSbnd(),xmzcys.getKtmc(),xmzcys.getKtbh(),xmzcys.getZcr(),xmzcys.getXmlx(),xmzcys.getKtkssj(),
					xmzcys.getKtjssj(),xmzcys.getDynzchz(),xmzcys.getDenzchz(),xmzcys.getDsnzchz(),
					xmzcys.getCsyj(),xmzcys.getKzfw(),xmzcys.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加收入预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doAdd1(CW_SRYSMXB srysmxb) {
		String sql = "insert into cw_srysmxb(guid,srysbh,dynsr,densr,dsnsr,srxmbh)values(?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{srysmxb.getGuid(),srysmxb.getSrysbh(),srysmxb.getDynsr(),srysmxb.getDensr(),srysmxb.getDsnsr(),srysmxb.getSrxmbh()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加基本支出预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doAddJbzcysmx(CW_JBZCYSMXB jbzcysmx) {
		String sql = "insert into CW_JBZCYSMXB(guid,jbzcbh,jjkmbh,dynzc,denzc,dsnzc,xmmc,bz)values(?,?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{jbzcysmx.getGuid(),jbzcysmx.getJbzcbh(),jbzcysmx.getJjkm(),jbzcysmx.getDynzc(),jbzcysmx.getDenzc(),
					jbzcysmx.getDsnzc(),jbzcysmx.getXmmc(),jbzcysmx.getBz()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 添加项目支出预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doAddXmzcysmx(CW_XMZCYSMXB xmzcysmx) {
		String sql = "insert into CW_XMZCYSMXB(guid,xmzcbh,jjkmbh,dynzc,denzc,dsnzc,bz)values(?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{xmzcysmx.getGuid(),xmzcysmx.getXmzcbh(),xmzcysmx.getJjkm(),xmzcysmx.getDynzc(),xmzcysmx.getDenzc(),
					xmzcysmx.getDsnzc(),xmzcysmx.getBz()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 编辑收入预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doEditSrysmx(CW_SRYSMXB srysmxb) {
		String sql = "update cw_srysmxb set srysbh=?,dynsr=?,densr=?,dsnsr=?,srxmbh=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{srysmxb.getSrysbh(),srysmxb.getDynsr(),srysmxb.getDensr(),srysmxb.getDsnsr(),srysmxb.getSrxmbh(),srysmxb.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 编辑项目支出预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doEditXmzcysmx(CW_XMZCYSMXB xmzcysmx) {
		String sql = "update CW_XMZCYSMXB set xmzcbh=?,dynzc=?,denzc=?,dsnzc=?,jjkmbh=?,bz=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{
					xmzcysmx.getXmzcbh(),
					xmzcysmx.getDynzc(),
					xmzcysmx.getDenzc(),
					xmzcysmx.getDsnzc(),
					xmzcysmx.getJjkm(),
					xmzcysmx.getBz(),
					xmzcysmx.getGuid()
					});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 编辑基本支出预算明细信息
	 * @param dmb
	 * @return
	 */
	public int doEditJbzcysmx(CW_JBZCYSMXB jbzcysmx) {
		String sql = "update CW_JBZCYSMXB set jbzcbh=?,jjkmbh=?,dynzc=?,denzc=?,dsnzc=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{jbzcysmx.getJbzcbh(),jbzcysmx.getJjkm(),jbzcysmx.getDynzc(),jbzcysmx.getDenzc(),jbzcysmx.getDsnzc(),jbzcysmx.getGuid() });
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 收入预算删除
	 * @param 
	 * @return 
	 */
	public int doDelete(String GUID,CW_SRYSB srysb) {
		String sql = "DELETE CW_SRYSB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {srysb.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 收入明细预算删除
	 * @param 
	 * @return 
	 */
	public int doDeleteSrysmx(String GUID,CW_SRYSMXB srysmxb) {
		String sql = "DELETE CW_SRYSMXB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {srysmxb.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 基本支出明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteJbzcysmxguid(String GUID,CW_JBZCYSMXB jbzcysmx) {
		String sql = "DELETE CW_JBZCYSMXB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {jbzcysmx.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 基本支出明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteXmzcysmxguid(String GUID,CW_XMZCYSMXB  xmzcysmx) {
		String sql = "DELETE CW_XMZCYSMXB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {xmzcysmx.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 收入明细预算删除
	 * @param 
	 * @return 
	 */
	public int doDeleteSrysmxbh(String srysbh,CW_SRYSMXB srysmxb) {
		String sql = "DELETE CW_SRYSMXB WHERE srysbh"+CommonUtils.getInsql(srysbh);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {srysmxb.getSrxmbh()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 基本支出预算明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteJbzcysmx(String GUID,CW_JBZCYSMXB jbzcysmxb) {
		String sql = "DELETE CW_JBZCYSMXB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {jbzcysmxb.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 基本支出预算明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteJbzcysmxbh(String jbzcbh,CW_JBZCYSMXB jbzcysmxb) {
		String sql = "DELETE CW_JBZCYSMXB WHERE jbzcbh"+CommonUtils.getInsql(jbzcbh);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {jbzcysmxb.getJbzcbh()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 项目支出预算删除
	 * @param 
	 * @return 
	 */
	public int doDeletexmzc(String GUID,CW_XMZCYSB xmzcys) {
		String sql = "DELETE CW_XMZCYSB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {xmzcys.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 收入预算明细删除
	 * @param 
	 * @return 
	 */
	public int doDeletesrysmx(String GUID,CW_SRYSMXB srysmxb) {
		String sql = "DELETE CW_SRYSMXB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {srysmxb.getGuid()});
			
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 基本支出预算删除
	 * @param 
	 * @return 
	 */
	public int doDeleteJbzcys(String GUID,CW_JBZCYSB jbzcys) {
		String sql = "DELETE CW_JBZCYSB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {jbzcys.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 项目支出预算明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteXmzcysmx(String GUID,CW_XMZCYSMXB xmzcysmx) {
		String sql = "DELETE CW_XMZCYSMXB WHERE GUID"+CommonUtils.getInsql(GUID);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {xmzcysmx.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 项目支出预算明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteXmzcysmxbh(String xmzcbh,CW_XMZCYSMXB xmzcysmx) {
		String sql = "DELETE CW_XMZCYSMXB WHERE xmzcbh"+CommonUtils.getInsql(xmzcbh);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {xmzcysmx.getXmzcbh()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 收入预算表息实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "select (select '('||d.DWBH||')' || d.mc from gx_sys_dwb d where d.DWBH = a.sbbm) as sbbm1,(select '('||m.rybh||')' || m.xm from gx_sys_ryb m where m.guid = a.sbry) as sbry1,sbbm,sbry,to_char(sbnd,'yyyy') as sbnd,dynsrhz,densrhz,dsnsrhz,csyj,kzfw,qrzt from cw_srysb a where guid = ?";
		//sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 收入预算明细表息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getObjectById1(String guid) {
		String sql = "select guid,srysbh,dynsr,densr,dsnsr,(select '('||m.srxmbh||')' ||m.srxmmc from cw_srxmb m where m.guid=a.srxmbh) as srxmbh1,srxmbh from cw_srysmxb a where srysbh = ?";
		//sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 项目支出申报部门是否重复
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getXmzcSbbm(String sbbm) {
		String sql = "select *\r\n" + 
				"  from cw_xmzcysb  where sbbm=?";
		return db.queryForList(sql,new Object[]{sbbm});
	}
	/**
	 * 项目支出申报部门是否重复(编辑)
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>> getXmzcSbbm1(String guid,String sbbm) {
		String sql = "select * from  (select sbbm from cw_xmzcysb where guid !=? and sbbm= ?)";
		return db.queryForList(sql,new Object[]{guid,sbbm});
	}
	
	/**
	 * 基本支出预算表实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getJbzcbById(String guid) {
		String sql = "select (select '('||m.rybh||')' || m.xm from gx_sys_ryb m where m.guid = a.sbry) as sbry1,sbry,(select '('||d.DWBH||')' || d.mc from gx_sys_dwb d where d.DWBH = a.sbbm) as sbbm1,sbbm,to_char(sbnd,'yyyy') as sbnd,dynzchz,denzchz,dsnzchz,csyj,kzfw,qrzt from cw_jbzcysb a where guid = ?";
		//sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 项目支出预算表实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getXmzcysById(String guid) {
		String sql = "select (select '('||m.rybh||')' || m.xm from gx_sys_ryb m where m.guid = a.sbry) as sbry1,sbry,(select '('||d.DWBH||')' || d.mc from gx_sys_dwb d where d.DWBH = a.sbbm) as sbbm1,sbbm,to_char(sbnd,'yyyy') as sbnd,sbbm,to_char(sbnd,'yyyy') as sbnd,ktmc,ktbh,(select '('||m.rybh||')' || m.xm from gx_sys_ryb m where m.guid = a.zcr) as zcr1,zcr,to_char(ktkssj,'yyyy-mm-dd') as ktkssj,to_char(ktjssj,'yyyy-mm-dd')as ktjssj,dynzchz,denzchz,dsnzchz,csyj,kzfw,qrzt,xmlx,(select mc from gx_sys_dmb dm where zl='xmfl' and dm.dm=a.xmlx ) as xmflmc from cw_xmzcysb a where guid = ?";
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 基本支出预算明细表息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysmxId(String guid) {
		String sql = "select guid,(select '('||m.kmbh||')' || m.kmmc from cw_jjkmb m where m.guid=a.jjkmbh) as jjkmbh1,jjkmbh,dynzc,denzc,dsnzc,bz from cw_jbzcysmxb a where jbzcbh = ?";
		//sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 项目支出预算明细表息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getXmzcysmxId(String guid) {
		String sql = "select guid,(select '('||m.kmbh||')' || m.kmmc from cw_jjkmb m where m.guid=a.jjkmbh) as jjkmbh1,jjkmbh,dynzc,denzc,dsnzc,bz from cw_xmzcysmxb a where xmzcbh = ?";
		//sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 项目支出预算明细表息经济科目
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysmx(String dwbh) {
//		String sql = "select\r\n" + 
//				" '('|| jj.kmbh||')' ||jj.kmmc jjkm,jj.sm AS bz,jj.guid,\r\n" + 
//				"\r\n" + 
//				" jj.kmmc,jjkm.bmbh from cw_jjkmb jj\r\n" + 
//				"left join cw_jjkmybmdyszb jjkm on jjkm.jjkmbh=jj.kmbh\r\n" + 
//				"left join gx_sys_dwb dw on dw.dwbh = jjkm.bmbh\r\n" + 
//				"where jjkm.bmbh=?";
		String sql = "select '(' || jj.kmbh || ')' || jj.kmmc jjkm,\r\n" + 
				"       jj.sm AS bz,\r\n" + 
				"       jj.guid,\r\n" + 
				"       \r\n" + 
				"       jj.kmmc,\r\n" + 
				"       jjkm.bmbh\r\n" + 
				"  from cw_jjkmb jj\r\n" + 
				"  left join cw_jjkmybmdyszb jjkm on jjkm.jjkmbh = jj.kmbh\r\n" + 
				"  left join gx_sys_dwb dw on dw.bmh = jjkm.bmbh\r\n" + 
				" where dw.dwbh =?";
		//sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{dwbh});
	}
	/**
	 * 项目支出预算明细表息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getSrysSbbm(String sbbm) {

		String sql = "select *\r\n" + 
				"  from cw_srysb  where sbbm=?";
		return db.queryForList(sql,new Object[]{sbbm});
	}
	/**
	 * 收入预算（编辑）
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getSrysSbbm1(String guid,String sbbm) {

		String sql = "select * from  (select sbbm from cw_srysb where guid !=? and sbbm=?)";
		return db.queryForList(sql,new Object[]{guid,sbbm});
	}
	/**
	 * 基本支出预算明细表息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysSbbm(String sbbm) {

		String sql = "select *\r\n" + 
				"  from cw_jbzcysb  where sbbm=?";
		return db.queryForList(sql,new Object[]{sbbm});
	}
	
	/**
	 * 基本支出预算编辑页面
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getJbzcysSbbm1(String guid,String sbbm) {

		String sql = "select * from  (select sbbm from cw_jbzcysb where guid !=? and sbbm = ?)";
		return db.queryForList(sql,new Object[]{guid,sbbm});
	}
}
