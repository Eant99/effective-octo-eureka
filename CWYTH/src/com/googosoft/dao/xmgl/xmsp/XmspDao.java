package com.googosoft.dao.xmgl.xmsp;

import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;

/**
 * 
 * @author fugangjie
 *
 * 2018年1月27日-下午4:04:02
 */
@Repository
public class XmspDao extends BaseDao{

	/**
	 * 根据guid查询项目项目审批信息
	 * @param guid 业务表guid
	 * @return	项目审批信息
	 */
	public Map<String, Object> getXmspInfo(String guid){
		String sql = "select b.xmbh,b.xmmc,(select xmlxmc from cw_xmlxb where guid = b.xmlx) as xmlx,"
				+ "(select zymc from cw_zyxxb where zybh = b.fwzy ) as fwzy,fwxk,to_char(b.sbrq,'yyyy-MM-dd') as sbrq,"
				+ "b.xmpm,to_char(b.jhzxsj,'yyyy-MM-dd') as jhzxsj,to_char(b.jhjssj,'yyyy-MM-dd') as jhjssj,to_char(b.ysje,'FM999999999.00') as ysje,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.sbr) as sbr,(select '('||dwbh||')'||mc from gx_sys_dwb where dwbh = b.sbdw) as sbdw,sfsndcxlzxm,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb where guid = b.csr) as csr,to_char(b.csrq,'yyyy-MM-dd') as csrq,b.csyj,b.xmpm,"
				+ "b.yjmbxy,b.xmjszynr,b.xmzyysgc,b.xmsszycs,"
				+ "(select mlmc from cw_cgmlszb where guid = s.cgml) as cgml,s.wpmc,s.jldw,"
				+ "s.sl,to_char(s.dj,'FM999999999.00') as dj,to_char(s.je,'FM999999999.00') as je,"
				+ "s.sfjk,s.bz"
				+ " from cw_xmsbxxb b join cw_xmsbmxb s on b.guid = s.zbid where b.guid = ?";
		Object[] obj = {guid};
		return db.queryForMap(sql,obj);
	}
	/**
	 * 更新初审审核状态
	 * @param guid	业务表guid
	 * @param shzt	审核状态代码
	 * @param shyj	审核意见
	 * @param xmpm	项目排名
	 * @return	受影响的行数
	 */
	public int updateCsShzt(String guid,String shzt,String shyj,String xmpm) {
		String sql = "update cw_xmsbxxb set shzt = ?,csr = ?,csrq = sysdate,csyj = ?,xmpm = ? where guid = ?";
		Object[] obj = {shzt,LUser.getGuid(),shyj,xmpm,guid};
		return db.update(sql,obj);
	}
	/**
	 * 更新复审审核状态
	 * @param guid	业务表guid
	 * @param shzt	审核状态代码
	 * @param shyj	审核意见
	 * @param xmpm	项目排名
	 * @return	受影响的行数
	 */
	public int updateFsShzt(String guid,String shzt,String shyj,String xmpm) {
		String sql = "update cw_xmsbxxb set shzt = ?,fsr = ?,fsrq = sysdate,fsyj = ?,xmpm = ? where guid = ?";
		Object[] obj = {shzt,LUser.getGuid(),shyj,xmpm,guid};
		return db.update(sql,obj);
	}
	/**
	 * 根据项目申报guid向项目基本信息表插入一条数据
	 * @param guid	项目申报guid
	 * @return	受影响的行数
	 */
	public int insertXmjbxx(String guid,String sszt) {
		String xmbh=GenAutoKey.makeCkbh("Cw_xmjbxxb", "xmbh", "2");
		String sql = " insert into cw_xmjbxxb (guid,bmbh,xmbh,xmmc,xmlx,sfqy,czr,czrq,ysje,syje,sfwg,fzr,sszt,gkbm,xmsx)\r\n" + 
				" select sys_guid() as guid,sbdw as bmbh,'"+xmbh+"',xmmc,xmlx,'1' as sfqy,? as czr,sysdate as czrq,ysje,0 as syje,'0' as sfwg,sbr,'"+sszt+"',sbdw,'02' " + 
				" from cw_xmsbxxb where guid = ?";
		Object[] obj = {LUser.getGuid(),guid};
		return db.update(sql,obj);
	}
	/**
	 * 更新调整后排名
	 * @param guid
	 * @param tzhpm
	 * @return
	 */
	public int updateTzhpm(String guid,String tzhpm) {
		String sql = "update cw_xmsbxxb set xmpm = ? where guid = ?";
		Object[] obj = {tzhpm,guid};
		return db.update(sql,obj);
	}
}
