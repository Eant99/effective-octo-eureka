package com.googosoft.dao.xmgl.xmsb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("xmsbDao")
public class XmsbDao  extends BaseDao{
	/**
	 * 新增主表信息
	 * 
	 * @param ryb
	 * @return
	 */

	public int doAddZb(PageData pd) {
		String sql = "insert into cw_xmsbxxb(guid,xmlx,xmbh,xmmc,fwzy,fwxk,sbrq,jhzxsj,jhjssj,ysje,sbr,sbdw,sfsndcxlzxm,yjmbxy,xmjszynr,xmzyysgc,xmsszycs,shzt) "
				+ "values(?,?,?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,?,?,?)";
		Object[] obj = {
			    pd.getString("guid"),
			    pd.getString("xmlx"),
			    pd.getString("xmbh"),
				pd.getString("xmmc"),
				CommonUtil.getBeginText(pd.getString("fwzy")),
				pd.getString("fwxk"),
				pd.getString("sbrq"),
				pd.getString("jhzxsj"),
				pd.getString("jhjssj"),
				pd.getString("ysje"),
				CommonUtil.getBeginText(pd.getString("sbr")),
				CommonUtil.getBeginText(pd.getString("sbdw")),
				pd.getString("sfsndcxlzxm"),
				pd.getString("yjmbxy"),
				pd.getString("xmjszynr"),
				pd.getString("xmzyysgc"),
				pd.getString("xmsszycs"),
				"0"
			
		};
		return db.update(sql,obj);
	}
	/**
	 * 新增明细表信息
	 * @param ryb
	 * @return
	 * 
	 */
	public int doAddMxb(PageData pd) {
		String sql="insert into cw_xmsbmxb(guid,zbid) values(?,?)";
		Object[] obj = {
			pd.getString("mxbid"),
			pd.getString("guid"),
//			pd.getString("cgml"),
//			pd.getString("wpmc"),
//			pd.getString("jldw"),
//			pd.getString("sl"),
//			pd.getString("dj"),
//			pd.getString("je"),
//			pd.getString("sfjk"),
//			pd.getString("bz")
		};
		return db.update(sql,obj);
	}
	/**
	 * 项目申报主表的详细信息
	 * @param guid
	 * @param ywid
	 * @return
	 */
	public Map getMapXmsbByGuid(String guid){
		String sql = "select t.guid,t.xmlx,t.xmbh,t.xmmc,(select '('||zybh||')'||zymc from cw_zyxxb a where a.zybh=t.fwzy) as fwzy,"
				+ "t.fwxk,to_char(t.sbrq,'yyyy-mm-dd') as sbrq,"
				+ "to_char(t.jhzxsj,'yyyy-mm-dd') as jhzxsj,to_char(t.jhjssj,'yyyy-mm-dd') as jhjssj,to_char(t.ysje,'FM999999999999.00') as ysje,(select '('||dwbh||')'||mc from gx_sys_dwb b where b.dwbh=t.sbdw) as sbdw,"
				+ "(select '('||rybh||')'||xm from gx_sys_ryb c where c.rybh=t.sbr) as sbr,to_char(t.sbrq,'yyyy-mm-dd') as sbrq,t.sfsndcxlzxm,t.yjmbxy,t.xmjszynr,"
				+ "t.xmzyysgc,t.xmsszycs, '('||x.xmlxbh||')'||x.xmlxmc xmlxmc from cw_xmsbxxb t left join cw_xmlxb x on x.guid=t.xmlx where t.guid=?";
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql, new Object[]{guid});
		return map;
	}
	/**
	 * 编辑项目申报主表
	 * 
	 */
	public int updXmsb(PageData pd,String guid) {
		String sql="update cw_xmsbxxb set xmlx=?,xmbh=?,xmmc=?,fwzy=?,fwxk=?,sbrq=to_date(?,'yyyy-mm-dd'),jhzxsj=to_date(?,'yyyy-mm-dd'),jhjssj=to_date(?,'yyyy-mm-dd'),ysje=?,sbr=?,sbdw=?,sfsndcxlzxm=?,"
				+ "yjmbxy=?,xmjszynr=?,xmzyysgc=?,xmsszycs=? where guid='"+guid+"'";
		Object[] obj= {
			pd.getString("xmlx"),
			pd.getString("xmbh"),
			pd.getString("xmmc"),
			CommonUtil.getBeginText(pd.getString("fwzy")),
			pd.getString("fwxk"),
			pd.getString("sbrq"),
			pd.getString("jhzxsj"),
			pd.getString("jhjssj"),
			pd.getString("ysje"),
			CommonUtil.getBeginText(pd.getString("sbr")),
			CommonUtil.getBeginText(pd.getString("sbdw")),
			pd.getString("sfsndcxlzxm"),
			pd.getString("yjmbxy"),
			pd.getString("xmjszynr"),
			pd.getString("xmzyysgc"),
			pd.getString("xmsszycs")
			
		};
		return db.update(sql,obj);
	}
	/**
	 * 项目申报明细表的详细信息
	 * 
	 */
	public Map getMapMxbByGuid(String zbid) {
		String sql="select guid,zbid,cgml as cgml,(select '('||mldm||')'||mlmc from cw_cgmlszb b where b.guid=a.cgml) as cgmlmc,wpmc,jldw,sl,"
				+ "to_char(dj,'FM999999999999.00') as dj,to_char(je,'FM999999999999.00') as je,sfjk,bz from cw_xmsbmxb a where zbid=?";
		Map map = new HashMap<String,Object>();
		map = db.queryForMap(sql, new Object[]{zbid});
		return map;
	}
	/**
	 * 编辑明细表信息
	 * 
	 */
	public int updMxbxx(PageData pd,String zbid) {
		String sql = "update cw_xmsbmxb set cgml=?,wpmc=?,jldw=?,sl=?,dj=?,je=?,sfjk=?,bz=? where zbid='"+zbid+"'";
		Object[] obj= {
				pd.getString("cgml"),
				pd.getString("wpmc"),
				pd.getString("jldw"),
				pd.getString("sl"),
				pd.getString("dj"),
				pd.getString("je"),
				pd.getString("sfjk"),
				pd.getString("bz"),
		};
		  return db.update(sql,obj);
	}
	
	public List getxx(String guid) {
 		String sql1 = "select guid from cw_xmsbxxb where guid in ('"+guid+"')";
 		return db.queryForList(sql1);
 	}
	/**
	 * 批量删除
	 * @param guid
	 * @return
	 */
	public int doDelZb(String guid) {	
		String sql = "DELETE CW_xmsbxxb WHERE guid in ('"+guid+"')";
		int i=0;
	    i=db.update(sql);	
		return i;	
	}
	public int dodelMxb(String guid) {
		String sql2 = "DELETE CW_xmsbmxb WHERE zbid in ('"+guid+"')";
		int i=0;
		i=db.update(sql2);	
		return i;
	}
	/**
	 * 提交
	 * @param guid
	 * @return
	 */
	public int doUpdate(String guid) {	
		String sql = "UPDATE CW_xmsbxxb SET SHZT='1' WHERE guid in ('"+guid+"')";
		int i=0;
	    i=db.update(sql);	
		return i;	
	}
	/**
	 * 得到附件设置信息
	 * @param zbid
	 * @return
	 */
	public List<Map<String, Object>> getFjxx(String zbid) {
		String sql = " select guid,wid,xmmc,(select d.mc from gx_sys_dmb d where d.zl='"+Constant.XMLB+"' and d.dm=x.xmlb)as xmlb,"
				+ "(case x.sfbt when '1' then '是' when '0' then '否' else '' end )as sfbt from cw_fjszb x where xmlxbh=?";
		return db.queryForList(sql,new Object[] {zbid});
	}
	
	/**
	 * 根据项目类型编号查询项目类型附件信息
	 * @param xmlxbh
	 * @return
	 */
	public List<Map<String, Object>> getFjxxByXmlx(String xmlxbh) {
		String sql = "select * from cw_fjszb x where xmlxbh=? ";
		return db.queryForList(sql,new Object[] {xmlxbh});
		
	}
	public int getObjectById(PageData pd) {
		String sql = "select count(1) from cw_xmsbxxb b where b.xmbh = '"+pd.getString("xmbh")+"'";
		return db.queryForInt(sql);
	}
	
	
	
	
	
	
	
	
	///////////////////////////////////////////////////////////
	/**
	 * 获取
	 * @param jb
	 * @return
	 */
	public List xmsbTree(String sjfl){
		String sql = "";
		if("root".equals(sjfl)){
			sql = "SELECT T.*, T.ROWID FROM CW_XMFLSZB T WHERE SJFL = GUID OR SJFL='root' ";
		}else{
			sql = "SELECT T.*, T.ROWID FROM CW_XMFLSZB T WHERE SJFL = '"+sjfl+"' AND SJFL<>GUID ";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 返回true为子节点，返回false为父节点
	 * @param sjfl
	 * @return
	 */
	public boolean checkIsLeaf(String sjfl){
		if("root".equals(sjfl)){
			return false;
		}
		String sql = "select COUNT(0) from CW_XMFLSZB where sjfl='"+sjfl+"' AND GUID<>'"+sjfl+"'";
		String sum = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0");
		int result = Integer.parseInt(sum);
		if(result>0){
			return false;//是父节点
		}
		return true;
	}
	/**
	 * 获取
	 * @param jb
	 * @return
	 */
	public List cgflTree(String cgfl){
		String sql = "";
		if("root".equals(cgfl)){
			sql = "SELECT T.*, T.ROWID FROM CW_CGMLSZB  T WHERE SJML = GUID OR SJML='0' ";
		}else {
			sql = "SELECT T.*, T.ROWID FROM CW_CGMLSZB T WHERE SJML = '"+cgfl+"' AND SJML<>GUID ";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 返回true为子节点，返回false为父节点
	 * @param sjfl
	 * @return
	 */
	public boolean cgflIsLeaf(String cgfl){
		if("root".equals(cgfl)){
			return false;
		}
		String sql = "SELECT COUNT(0) FROM CW_CGMLSZB WHERE SJML='"+cgfl+"' AND GUID<>'"+cgfl+"'";
		String sum = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0");
		int result = Integer.parseInt(sum);
		if(result>0){
			return false;//是父节点
		}
		return true;
	}
}
