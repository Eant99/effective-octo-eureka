package com.googosoft.dao.wsbx.jcsz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWMXB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("wldwszDao")
public class WldwszDao extends BaseDao {
	private Logger logger = Logger.getLogger(WldwszDao.class);
	/**
	 * 添加往来单位设置信息
	 * @param dmb
	 * @return
	 */
	public int doAdd(CW_WLDWSZ wldwsz) {
		String wlbh = GenAutoKey.makeCkbh("Cw_wldwb", "wlbh", "4");
		String sql = "insert into cw_wldwb(guid,wlbh,dwmc,dwjc,dwlx,dwdz,sh,lxr,bgdh,cz,sszt,sfdgzf)values(?,'"+wlbh+"',?,?,?,?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{wldwsz.getGuid(),wldwsz.getDwmc(),wldwsz.getDwjc(),wldwsz.getDwlx(),wldwsz.getDwdz(),wldwsz.getSh(),
					wldwsz.getLxr(),wldwsz.getBgdh(),wldwsz.getCz(),wldwsz.getSszt(),wldwsz.getSfdgzf()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加往来单位明细信息
	 * @param dmb
	 * @return
	 */
	public int doAdd1(CW_WLDWMXB wldwmxb) {
		String sql = "insert into cw_wldwmxb(guid,wlbh,khyh,khyhzh,yhlhh)values(?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{wldwmxb.getGuid(),wldwmxb.getWlbh(),wldwmxb.getKhyh(),wldwmxb.getKhyhzh(),wldwmxb.getYhlhh()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 *往来单位设置实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		//String sql = "select (select '('||d.DWBH||')' || d.mc from gx_sys_dwb d where d.DWBH = a.sbbm) as sbbm1,(select '('||m.rybh||')' || m.xm from gx_sys_ryb m where m.guid = a.sbry) as sbry1,sbbm,sbry,to_char(sbnd,'yyyy') as sbnd,dynsrhz,densrhz,dsnsrhz,csyj,kzfw,qrzt from cw_srysb a where guid = ?";
		String sql="select guid,wlbh,dwmc,dwjc,dwlx,dwdz,sh,lxr,bgdh,cz,sfdgzf from cw_wldwb where guid= ?";
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 往来单位设置明细表息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getObjectById1(String guid) {
		//String sql = "select guid,srysbh,dynsr,densr,dsnsr,(select '('||m.srxmbh||')' ||m.srxmmc from cw_srxmb m where m.guid=a.srxmbh) as srxmbh1,srxmbh from cw_srysmxb a where srysbh = ?";
		String sql = "select guid,wlbh,hm,khyh,khyhzh,yhlhh from cw_wldwmxb where wlbh=?";
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 编辑往来单位设置信息
	 * @param dmb
	 * @return
	 */
	public int doEdit(CW_WLDWSZ wldwsz) {
		String sql = "update CW_WLDWB set  wlbh=?,dwmc=?,dwjc=?,dwlx=?,dwdz=?,lxr=?,sh=?,bgdh=?,sfdgzf=?,cz=? where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{wldwsz.getWlbh(),wldwsz.getDwmc(),wldwsz.getDwjc(),wldwsz.getDwlx(),wldwsz.getDwdz(),wldwsz.getLxr(),
					wldwsz.getSh(),wldwsz.getBgdh(),wldwsz.getSfdgzf(),wldwsz.getCz(),wldwsz.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 往来单位明细删除
	 * @param 
	 * @return 
	 */
	public int doDeleteWldwmx(String guid,CW_WLDWMXB wldwmxb) {
		String sql = "DELETE CW_WLDWMXB WHERE guid"+CommonUtils.getInsql(guid);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {wldwmxb.getGuid()});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 先删后增
	 */
	public int delYhzh(String wlbh) {
		String sql = "delete from cw_wldwmxb where wlbh = '"+wlbh+"'";
		return db.update(sql);
		
	}
	/**
	 * 删除单条往来单位设置信息
	 * @param guid
	 * @return
	 */
	public int doDelete(String guid) {
		String sql = "DELETE from CW_WLDWB WHERE guid = '"+guid+"'";
		String sql1 = "DELETE from CW_WLDWMXB WHERE WLBH = '"+guid+"'";
		
		       db.update(sql1);
		return db.update(sql);
	}
	/**
	 * 批量删除往来单位
	 */
	public int doDeletes(String guid) {
		String sql = "DELETE CW_WLDWB WHERE guid in ('"+guid+"')";
		String sql1 = "DELETE CW_WLDWMXB WHERE WLBH in ('"+guid+"')";
		       db.update(sql);
		return db.update(sql1);
	}
	
	public List<Map<String , Object>> selectListByWldwbh(PageData pd) {
		String sql = "select * from CW_WLDWB where wlbh = ?";
		Object[] obj = {
				pd.getString("wlbh")
		};
		return db.queryForList(sql, obj);
	}
	
	//加上银行卡，合并导出（wzd）主列表
	public List<Map<String, Object>> getList1(String guid, String searchValue) {
		StringBuffer sqltext = new StringBuffer();
//		sqltext.append("  SELECT K.GUID AS GUID,K.WLBH AS WLBH,K.DWMC AS DWMC,K.DWJC AS DWJC,(select B.MC from GX_SYS_DMB B where B.Zl='wldwlx' and B.DM = K.DWLX) as DWLX,K.DWDZ AS DWDZ,K.LXR AS LXR,K.SH AS SH,K.BGDH AS BGDH,K.CZ AS CZ, H.KHYH,H.KHYHZH");
		sqltext.append("  SELECT K.GUID AS GUID,K.WLBH AS WLBH,K.DWMC AS DWMC,K.DWJC AS DWJC,decode(dwlx,01,'供应商',02,'主管部门') as DWLX,K.DWDZ AS DWDZ,K.LXR AS LXR ");
		sqltext.append(" FROM CW_WLDWB K   where 1=1");
//		sqltext.append(" FROM CW_WLDWB K LEFT JOIN CW_WLDWMXB H ON H.WLBH= K.GUID  where 1=1");
		sqltext.append(ToSqlUtil.jsonToSql(searchValue));
		
//		if(Validate.noNull(guid)){
//			sqltext.append(" AND K.GUID "+CommonUtils.getInsql(guid)+" ");
//		}
		
		if(Validate.noNull(guid)){
			sqltext.append(" and  K.guid in ('"+guid.trim()+"') ");
		}
		
		sqltext.append(" ORDER BY K.WLBH ");
		
//		Object[] guid2 = guid.split(",");
//		List<Map<String, Object>> wldw = db.queryForList(sqltext.toString(),guid2);
		List<Map<String, Object>> wldw = db.queryForList(sqltext.toString());
		return wldw;
	}
	//加上银行卡，合并导出（wzd）银行可
//	public List<Map<String, Object>> getList2(String guid) {
//		String sql = "select GUID,WLBH,khyh,khyhzh from cw_wldwmxb where wlbh "+CommonUtils.getInsql(guid)+" ";
//		Object[] guid2 = guid.split(",");
//		return db.queryForList(sql,guid2);
//	}
}
