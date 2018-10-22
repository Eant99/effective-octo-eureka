package com.googosoft.dao.system.tree;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.service.base.PageService;
import com.googosoft.util.Logger;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("treeDao")
public class TreeDao extends BaseDao{
	
	private Logger logger = Logger.getLogger(TreeDao.class);
	
	@Resource(name = "pageService")
	private PageService pageService;// 分页单例	
	/**
	 * 获取所有权限单位
	 * @param rybh
	 * @return
	 */
	public List powerModels(String rybh) {
		//此sql语句的大体含义：查询某个人权限表下的所有单位，如果权限表中不存在此人，则默认是人所在单位的及其下属单位的信息
		String sql = "select dwb.dwbh,dwb.bmh,dwb.mc,dwb.pxxh,"
					+ "(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh) xjcount,dwb.dwzt,dwb.dwjc " 
					+ "from "+TnameU.GLQXB+" t " 
					+ "inner join gx_sys_dwb dwb on dwb.dwbh = t.dwbh and t.rybh = ? and exists (select 1 from "+TnameU.GLQXB+" where rybh = ? and zl='2') " 
					+ "union " 
					+ "select dwb.dwbh,bmh,mc,dwb.pxxh,(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh) xjcount,dwb.dwzt,dwb.dwjc " 
					+ "from gx_sys_dwb dwb " 
					+ "inner join gx_sys_ryb ryb on dwb.dwbh = ryb.dwbh and ryb.rybh = ? "
					+ "and not exists (select 1 from "+TnameU.GLQXB+" where rybh = ? and zl='2') and dwzt='1' order by pxxh,bmh ";
		return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh});
	}
	public List powerModelsnew(String rybh,String xxTree) {
		//此sql语句的大体含义：查询某个人权限表下的所有单位，如果权限表中不存在此人，则默认是人所在单位的及其下属单位的信息
		rybh="000000";
		String sql = "select dwb.dwbh,dwb.bmh,dwb.mc,"
				+ "(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh) xjcount,dwb.dwzt,dwb.dwjc " 
				+ "from "+TnameU.GLQXB+" t " 
				+ "inner join gx_sys_dwb dwb on dwb.dwbh = t.dwbh and t.rybh = ? and exists (select 1 from "+TnameU.GLQXB+" where rybh = ? and zl='2') " 
				+ "union " 
				+ "select dwb.dwbh,bmh,mc,(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh) xjcount,dwb.dwzt,dwb.dwjc " 
				+ "from gx_sys_dwb dwb " 
				+ "inner join gx_sys_ryb ryb on dwb.dwbh = ryb.dwbh and ryb.rybh = ? "
				+ "and not exists (select 1 from "+TnameU.GLQXB+" where rybh = ? and zl='2') and dwzt='1' order by bmh ";
		return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh});
	}
	/**
	 * 获取所有单位
	 * @param rybh
	 * @return
	 */
	public List allModels(String rybh) {
		String sql = "select t.dwbh,t.bmh,t.mc,t.dwzt,t.dwjc,"
					+" (select count(1) from gx_sys_dwb a where a.sjdw = t.dwbh and a.dwzt = '1') xjcount"
					+"  from gx_sys_dwb t"
					+"  where dwzt = '1'"
					+"  and dwjc = '1'"
					+"  order by bmh";
		return db.queryForList(sql);
	}
	/**
	 * 
	 * @param dwbh
	 * @return
	 */
	public List dwpowerModels(String dwbh) {
		//此sql语句的大体含义：查询某个人权限表下的所有单位，如果权限表中不存在此人，则默认是人所在单位的及其下属单位的信息
		String sql ="select dwbh,mc,(select count(1) from gx_sys_dwb s where )as xjcount from gx_sys_dwb y where y.sjdw='"+dwbh+"'  ";
		return db.queryForList(sql);
	}
	/**
	 * 
	 * @param dwbh
	 * @return
	 */
	public List dwpowerModels1(String dwbh) {
		//此sql语句的大体含义：查询某个人权限表下的所有单位，如果权限表中不存在此人，则默认是人所在单位的及其下属单位的信息
		String sql ="select dwbh,mc,(select count(1) from gx_sys_dwb s where ) as xjcount from gx_sys_dwb y where y.sjdw='000000'  ";
		return db.queryForList(sql);
	}
	/**
	 * 获取实验室标识下的单位树
	 * @param rybh
	 * @return
	 */
	public List powerSysModels(String loginrybh) {
		String sql ="select  dwb.dwbh as dwbh,dwb.bmh as bmh,dwb.mc as mc,(select count(1) from gx_sys_dwb a where a.sjdw=dwb.dwbh) as xjcount,dwb.dwzt as dwzt from "+TnameU.GLQXB+" inner join gx_sys_dwb dwb on dwb.dwbh="+TnameU.GLQXB+".dwbh and "+TnameU.GLQXB+".rybh=?  and exists(select 1 from "+TnameU.GLQXB+" QXB where rybh=? AND zl = '2') union  select dwb.dwbh,bmh,mc,(select count(1) from gx_sys_dwb a where a.sjdw=dwb.dwbh) as xjcount,dwb.dwzt from gx_sys_dwb dwb inner join gx_sys_ryb ryb on dwb.dwbh=ryb.dwbh and ryb.rybh=? and NOT exists(select 1 from "+TnameU.GLQXB+" QXB where rybh=? AND zl = '2') order by bmh";
		return db.queryForList(sql,new Object[]{loginrybh,loginrybh,loginrybh,loginrybh});
	}
	/**
	 * 获取某个单位下的人员信息
	 * @param dwbh 单位编号
	 * @return
	 */
	public List getRyModels(String dwbh) {
		String sql = "SELECT RYBH,RYGH,XM FROM GX_SYS_RYB WHERE DWBH=? AND RYBH<>'000000'";
		return db.queryForList(sql,new Object[]{dwbh});
	}
	/**
	 * 获取某个单位下的单位信息
	 * @param sjdw 上级单位
	 * @return
	 */
	public List getDwModels(String sjdw) {
		String sql = "select dwbh,bmh,mc,(select count(1) from gx_sys_dwb a where a.sjdw=dwb.dwbh) xjcount,dwjc from gx_sys_dwb dwb where sjdw=? and dwzt='1' order by pxxh,bmh";
		return db.queryForList(sql,new Object[]{sjdw});
	}
	/**
	 * 获取某个单位下的单位信息\
	 * 传模块编号的特殊处理
	 * @param sjdw 上级单位
	 * @return
	 */
	public List getDwModelsByMkbh(String sjdw,String mkbh) {
		String sql = "select dwbh,bmh,mc,(select count(1) from gx_sys_dwb a where a.sjdw=dwb.dwbh) xjcount,dwjc from gx_sys_dwb dwb where sjdw=? and dwzt='1' ";
		if("040901".equals(mkbh)) {
			sql+=" and dwbh='110' order by pxxh,bmh ";
		}else {
			sql+=" order by pxxh,bmh ";
		}
		return db.queryForList(sql,new Object[]{sjdw});
	}
	/**
	 * 根据单位编号获取所有下级
	 * @param sjdw
	 * @return
	 */
	public List getSysDwModels(String sjdw) {
		String sql = "select dwbh,bmh,mc,(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh) xjcount,sysbz from gx_sys_dwb dwb where sjdw = ? and dwzt = '1' order by bmh,pxxh";
		return db.queryForList(sql,new Object[]{sjdw});
	}
	/**
	 * 获取某个单位下的单位信息
	 * @param sjdw
	 * @return
	 */
	public List getSysModels(String sjdw) {
		String sql = "select dwbh,bmh,mc,(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh) xjcount,sysbz from gx_sys_dwb dwb where sjdw = ? and dwzt = '1' and exists (select dwbh from gx_sys_dwb d where d.sysbz = '0'  and d.dwzt = '1' start with d.dwbh = dwb.dwbh connect by prior d.dwbh = d.sjdw) order by bmh,pxxh";
		return db.queryForList(sql,new Object[]{sjdw});
	}
	/**
	 * 地点信息
	 * @param rybh 人员编号
	 * @return
	 */
	public List powerDdModels() {
		String sql = "select ddbh,ddh,mc,sjdd,dwbh,(select count(1) from zc_sys_ddb a where a.sjdd = zc_sys_ddb.ddbh) xjcount from zc_sys_ddb where sjdd='000000' and nvl(ddzt,'1')='1' order by ddh";
		return db.queryForList(sql);
	}
	/**
	 * 获取某个地点信息下的直属子节点
	 * @param sjdd
	 * @return
	 */
	
	public List getDdModels(String sjdd) {
		String sql = "select ddbh,ddh,mc,(select count(1) from zc_sys_ddb a where a.sjdd=ddb.ddbh) xjcount from zc_sys_ddb ddb where sjdd=? order by ddh,pxxh";
		return db.queryForList(sql,new Object[]{sjdd});
	}
	/**
	 * 获取财政分类树
	 * @param pid
	 * @return
	 */
	public List getCzflList(String pid) {
		String sql = "select bzdm,flh,flmc,sjdm,ffldm,(select count(*) from zc_flb_czbn f where sjdm = c.bzdm) cnt from zc_flb_czbn c where sjdm = ? order by flh";
		return db.queryForList(sql,new Object[]{pid});
	}
	/**
	 * 获取学科分类树
	 * @param pid
	 * @return
	 */
	public List getxkList(){
		String sql = "select zl,dm,mc from gx_sys_dmb where zl='45' and length(dm) = 3 order by dm";
		return db.queryForList(sql);
	}
	/**
	 * 获取学科直属信息
	 * @return
	 */
	public List getxkxxList(String dm){
		String sql = "select zl,dm,mc from gx_sys_dmb where zl='45' and dm like '"+dm+"%' and dm != '"+dm+"' order by dm";
		return db.queryForList(sql);
	}
	/**
	 * 获取国别分类树
	 * @param pid
	 * @return
	 */
	public List getgbList(String pid){
		String sql = "select  t.zl,t.dm,t.mc,(select count(*) from gx_sys_dmb where substr(dm,0,3) = ? and dm != ?) cnt from gx_sys_dmb t where zl='02' order by dm";
		return db.queryForList(sql,new Object[]{pid,pid});
	}
	/**
	 * 资产分类树
	 * @param flh
	 * @param dmjc
	 * @return
	 */
	public List getZcflList(String flh, String dmjc) {
		String sql="select sys_guid() gid,flh,flmc,dmjc,ffldm,substr(flh,1,to_number(dmjc)*2) flbm," +
				"(select '('||flh||')'||flmc from zc_flb_czbn c where c.flh = y.ffldm) czflmc," +
				"(select bzdm from zc_flb_czbn c where c.flh = y.ffldm) czbzdm," +
				" (select count(*)from zc_flb_jyb j where j.dmjc = (to_number(y.dmjc) + 1) and substr(j.flh, 1, (to_number(y.dmjc) * 2)) = substr(y.flh, 1, (to_number(y.dmjc) * 2))) cnt,sfmj" +
				" from zc_flb_jyb y where 1=1 ";
		try{
			if(!"00000000".equals(flh)){
				sql += " and rpad(substr(flh,1,?),8,'0') = ? ";
				sql += " and dmjc = ? order by flh ";
				return db.queryForList(sql,new Object[]{Integer.parseInt(dmjc)*2,flh,Integer.parseInt(dmjc)+1});
			}else{
				sql += " and dmjc = ? order by flh ";
				return db.queryForList(sql,new Object[]{Integer.parseInt(dmjc)+1});
			}
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("查询数据库信息失败\n" + sqle);  
		    return null;
		}
	}
	/**
	 * 业务审核、审核记录查询都用此树
	 * 人员权限获取菜单
	 * @param loginrybh 人员编号
	 * @param jc 级次
	 * @param sjmkbh 上级模块编号
	 * @return
	 */
	public  List initLevelMenu(String loginrybh,int jc,String sjmkbh){
		//db.getYwshProcedure(loginrybh);//提交的时候单据已经插入到初始化表里了，不需要在这里重新插入
		String findMenu = "";
		if(loginrybh.equalsIgnoreCase(SystemSet.AdminRybh())){
//			if(jc==1){
//				findMenu="select a.*,"+jc+" as jc from (Select A.Mkbh,A.Mkmc,A.Url,A.xh ,icon from %S A where length(A.mkbh)=2*"+jc+" and  nvl(qxbz,'0')='1'  and A.mkbh in (select mkbh from %S)) a ";
//			}else if(jc==2){
				findMenu="select a.*,"+jc+" as jc from (Select A.Mkbh,A.Mkmc,A.Url,A.xh ,icon from %S A where length(A.mkbh)=2*"+jc+" and nvl(qxbz,'0')='1' and SUBSTR(A.MKBH,0,(2*" + jc + " - 2))='"+sjmkbh+"' and A.mkbh in (select mkbh from %S)) a ";
//			}else if(jc==3){
//				findMenu="select a.*,"+jc+" as jc from (Select A.Mkbh,A.Mkmc,A.Url,A.xh ,icon from %S A where length(A.mkbh)=2*"+jc+" and nvl(qxbz,'0')='1' and SUBSTR(A.MKBH,0,4)='"+sjmkbh+"' and A.mkbh in (select mkbh from %S)) a ";
//			}
			findMenu=String.format(findMenu, new String[]{TnameU.MKB,"zc_admin_mkb"});
		}
		else{
			findMenu ="select a.*,"+jc+" as jc from (select a.mkbh,a.mkmc,a.url,a.xh,a.icon from %s a where length(a.mkbh)=2*"+jc+" and nvl(qxbz,'0')='1' and (exists(select mkbh from %s b where substr(mkbh,1,2*"+jc+")=a.mkbh and rybh='"+loginrybh.trim()+"') or exists(select mkbh from %s c where substr(c.mkbh,1,2*"+jc+")=a.mkbh and  jsbh in (select a.jsbh from %s a,%s b where a.jsbh=b.jsbh and rybh='"+loginrybh+"' and nvl(zt,0)=1 union select jsbh from %s where jsbh='00' and nvl(zt,0)=1)))) a ";
//			if(jc==1){//一级菜单（一级菜单不要了，直接显示二级）
//				findMenu+=" where length(mkbh)=2 and mkbh='05' ";
//			}else if(jc==2){//二级菜单
				findMenu+=" where length(mkbh)=2*" + jc + " and substr(mkbh,1,(2*" + jc + "-2))='" + sjmkbh + "' and substr(mkbh,1,4) not in ('0507','0510') ";
//			}else{//三级菜单
//				findMenu+=" where length(mkbh)=6 and substr(mkbh,1,4)='"+sjmkbh+"' ";
//			}
			findMenu=String.format(findMenu, new String[] {TnameU.MKB,TnameU.CZQXB,TnameU.JSCZQXB, TnameU.JSRYB,TnameU.JSB,TnameU.JSB});
		}
		findMenu += " order by xh ";
		return db.queryForList(findMenu);
	}
	
	/**
	 * 审核树点击查询按钮
	 * 通过模块名称查询模块编号
	 * @return
	 */
	public String findMkbhByMkmc(PageData pd) {
		String sql = "select mkbh from "+TnameU.MKB+" where mkmc = ? and length(mkbh) = 6 and substr(mkbh,1,2) = ? ";
		String rybh = LUser.getRybh();
		if(rybh.equalsIgnoreCase(SystemSet.AdminRybh())){
			sql += " and mkbh in (select mkbh from zc_admin_mkb) ";
		}
		else{
			sql += " and mkbh in (select mkbh from "+TnameU.CZQXB+" where xtbz = '" + SystemSet.XTBZ + "' and rybh = '" + rybh + "' union all select mkbh from "+TnameU.JSCZQXB+" where jsbh in (select jsbh from "+TnameU.JSRYB+" where rybh = '" + rybh + "') and xtbz = '" + SystemSet.XTBZ + "') ";
		}
		String mkbh = "";
		try {
			mkbh = db.queryForSingleValue(sql,new Object[]{pd.getString("mkmc"),MenuFlag.SHCD});
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
		    logger.error("数据库操作失败\n" + sqle);
		    mkbh = "F";
		}
		return mkbh;
	}
	
	/**
	 * 获取具有审核权限的数量
	 * @param mkbh
	 * @param loginrybh
	 * @return
	 */
	public  String getDjcountByProcedure(String mkbh,String loginrybh){
		String sql = "select nvl(count(*),0) hj from ZC_SYS_SHCSHB where rybh=? and mkbh like '"+mkbh+"%' and sfdqjd = '1'";
		String djs = db.queryForSingleValue(sql, new Object[]{loginrybh});
		return "("+djs+")";
	}
	
	/**
	 * 获取数据字典树
	 * @param jb
	 * @return
	 */
	public  List sjzdMenu(String jb){
		String sql = "select dm,mc,zl,jb,(select count(1) FROM gx_sys_dmb a where a.jb=b.dm ) as xjcount from gx_sys_dmb b where jb = '"+jb+"' and zl='"+Constant.DMBZL+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取数据字典树
	 * @param jb
	 * @return
	 */
	public  List cbdxMenu(String jb){
		String sql = "select '01' as dm,'学生' mc,'00' zl,'1' jb,'1' as xjcount from dual union select '02' as dm,'老师' mc,'00' zl,'1' jb,'1' as xjcount from dual";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取数据字典树
	 * @param jb
	 * @return
	 */
	public  List cbdxMenu2(String jb){
		String sql = "select '011' as dm,'专业' mc,'001' zl,'2' jb,'0' as xjcount from dual union select '012' as dm,'学历层次' mc,'002' zl,'2' jb,'0' as xjcount from dual union select '013' as dm,'学历类别' mc,'003' zl,'2' jb,'0' as xjcount from dual";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取功能科目字典树
	 */
	public  List kmszMenuByChild(String jb,String zl){
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.GNZL+"' union\r\n" + 
				"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+jb+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取科目字典树
	 */
	public  List kmsz(String jb){
		String sql = "select dm,mc,zl,jb from cw_kjkmsz where KMJDM = '"+jb+"' ";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取二级单位人员树（资产调拨分单位内和单位间，用到）
	 * @param rybh
	 * @param ejdw 资产的二级单位
	 * @param flag 区分单位内和单位间的标志
	 * @return
	 */
	public List powerModels(String rybh, String ejdw, String flag) {
		String sql = "select dwb.dwbh,dwb.bmh,dwb.mc,(select count(1) from gx_sys_dwb a where a.sjdw = dwb.dwbh and dwb.dwzt='1') xjcount,dwb.dwzt from gx_sys_dwb dwb where dwb.dwzt='1' ";
		if(StateManager.BDXM_DWNDB.equals(flag)) 
			sql += " and dwb.dwbh = ? ";
		else if(StateManager.BDXM_DWJDB.equals(flag)) 
			sql += " and dwb.dwbh <> ? and dwb.dwjc = '" + Constant.DWJC_EJ + "' " + pageService.getQxsql(rybh, "DWB.DWBH", "D")+" ";
		return db.queryForList(sql,new Object[]{ejdw});
	}
	
	/**
	 * 根据传入的zjff获取分类树
	 * @param pd
	 * @param rybh
	 * @return
	 */
	public List getKbflNode(PageData pd, String rybh){
		String sjjd = "";
		if("root".equals(pd.getString("bzdm"))){
			if("1".equals(pd.getString("zjff"))){//财政6大类
				sjjd = " sjdm = '00000000' and dmjc = '0' ";
			}
			else{//财政部6大类
				sjjd = " sjdm = bzdm and dmjc = '1' ";
			}
		}
		else{
			sjjd = " sjdm = '" + pd.getString("bzdm") + "' and bzdm <> sjdm ";
		}
		String tablename = "",dmjc = "",flh = "",qxsql = pageService.getQxsql(rybh, "flh", "F");
		if("1".equals(pd.getString("zjff"))){//财政部6大类
			tablename = " zc_flb_czbn ";
			dmjc = "(dmjc + 1)";
			flh = "substr(flh,1,((dmjc + 1) * 2) - 1)";
			if(Validate.noNull(qxsql)){
				qxsql = " and flh in (select ffldm from zc_flb_jyb where 1 = 1" + qxsql + ")";
			}
		}
		else{
			tablename = " zc_flb_jyb ";
			dmjc = "dmjc";
			flh = "substr(flh,1,dmjc * 2)";
		}
		StringBuilder sql = new StringBuilder();
		sql.append("select bzdm," + flh + " flh,flh yflh,flmc,nvl(sfwxzc,'0') sfwxzc," + dmjc + " dmjc,");
		sql.append("(select count(bzdm) from " + tablename + " l where l.sjdm = f.bzdm) cnt from ");
		sql.append(tablename + " f ");
		sql.append(" where " + sjjd);
		sql.append(qxsql);
		sql.append(" order by bzdm");
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 分类树点击查询按钮
	 * 通过(flh)flmc查询flh
	 * @return
	 */
	public String findFlhByFlmc(PageData pd) {
		String tablename = "";
		if("1".equals(pd.getString("zjff"))){
			tablename = " zc_flb_czbn ";
		}
		else{
			tablename = " zc_flb_jyb ";
		}
		String sql = "select flh from " + tablename + " where trim('('||flh||')'||flmc) = ?";
		String flh = "";
		try {
			flh = db.queryForSingleValue(sql,new Object[]{pd.getString("flxx")});
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
		    logger.error("数据库操作失败\n" + sqle);
		    flh = "F";
		}
		return flh;
	}
	/**
	 * 财政10大类树
	 */
	public List powerCzModels() {
		String sql = "SELECT DMXH,'dmb_'||DM AS DM,MC,(SELECT COUNT(1) FROM ZC_FLB_CZBO A WHERE A.DLH = GX_SYS_DMB.DM) AS XJCOUNT   FROM GX_SYS_DMB WHERE ZL='08' ORDER BY DM";
		return db.queryForList(sql);
	}
	public List getCzModels(String dlh) {
		String sql = "SELECT ZCDM,MC,DMJB FROM ZC_FLB_CZBO  WHERE DLH=?  AND LENGTH(ZCDM)= '2' ORDER BY ZCDM";
		return db.queryForList(sql,new Object[]{dlh.substring(4)});
	}
	/**
	 * 获取根节点信息
	 */
	public List getDwRoot() {
		String sql = "select d.dwbh as id, '(' || d.bmh || ')' || d.mc as name, "
				+ "case (select count(w.sjdw) from gx_sys_dwb w where w.sjdw=d.dwbh) when 0 then 'false' else 'true' end isParent "
				+ "from GX_SYS_DWB d  where d.sjdw = '000000'";
		return db.queryForList(sql);
	}
	/**
	 * 获取根节点信息
	 */
	public List getDwSearch(String searchInput) {
		String sql = "select d.dwbh as id, '(' || d.bmh || ')' || d.mc as name, "
				+ " 'false' as isParent "
				+ "from GX_SYS_DWB d  where '(' || d.bmh || ')' || d.mc like ? ";
		return db.queryForList(sql,new Object[]{"%"+searchInput+"%"});
	}
	/**
	 * 获取子节点信息
	 * @param id
	 * @return
	 */
	public List getDwJson(String id) {
		String sql = "select d.dwbh as id, '(' || d.bmh || ')' || d.mc as name, "
				+ "case (select count(w.sjdw) from gx_sys_dwb w where w.sjdw=d.dwbh) when 0 then 'false' else 'true' end isParent "
				+ "from GX_SYS_DWB d  where d.sjdw = ? ";
		List list = db.queryForList(sql,new Object[]{id});
		return list;
	}
	
	/**
	 * 查询使用的sql
	 * @param searchInput
	 * @param loginrybh
	 * @return
	 */
	public List shTree(String searchInput,String loginrybh) {
		
		String sql ="SELECT A.* FROM (SELECT A.MKBH,A.MKMC,A.URL,A.XH,A.ICON,NVL((select count(distinct dbh) from ZC_SYS_SHCSHB Z WHERE z.sfdqjd = '1' and Z.RYBH=? AND Z.MKBH LIKE %s),0) AS HJ "
				+ " FROM %S A WHERE A.MKMC LIKE ? AND NVL(QXBZ,'0')='1' "
				+ "AND (EXISTS(SELECT MKBH FROM %S B WHERE (SUBSTR(MKBH,1,4)=A.MKBH or mkbh = a.mkbh) AND RYBH=?) "
				+ "OR EXISTS(SELECT MKBH FROM %S C WHERE (SUBSTR(C.MKBH,1,4)=A.MKBH OR C.MKBH=A.MKBH)"
				+ " AND  JSBH IN (SELECT A.JSBH FROM %S A,%S B WHERE A.JSBH=B.JSBH AND RYBH=? "
				+ "AND NVL(ZT,0)=1 UNION SELECT JSBH FROM %S WHERE JSBH='00' AND NVL(ZT,0)=1)))) A ";
		sql = String.format(sql, new String[]{" A.MKBH||'%' ",TnameU.MKB,TnameU.CZQXB,TnameU.JSCZQXB,
				TnameU.JSRYB, TnameU.JSB,TnameU.JSB});
		return db.queryForList(sql,new Object[]{loginrybh,"%"+searchInput+"%",loginrybh,loginrybh});
	}
	/**
	 * 第一此进入使用的sql
	 * @param loginrybh
	 * @return
	 */
	public List shTree(String loginrybh) {
		String sql ="SELECT A.* FROM (SELECT A.MKBH,A.MKMC,A.URL,A.XH,A.ICON,'true' as isParent,NVL((SELECT count(distinct dbh) FROM ZC_SYS_SHCSHB Z WHERE z.sfdqjd = '1' and Z.RYBH=? AND Z.MKBH LIKE %S),0) AS HJ "
				+ "FROM %S A WHERE LENGTH(A.MKBH)=4 and mklx='3' AND NVL(QXBZ,'0')='1' "
				+ "AND (EXISTS(SELECT MKBH FROM %S B WHERE SUBSTR(MKBH,1,4)=A.MKBH AND RYBH=?) "
				+ "OR EXISTS(SELECT MKBH FROM %S C WHERE SUBSTR(C.MKBH,1,4)=A.MKBH"
				+ " AND JSBH IN (SELECT A.JSBH FROM %S A,%S B WHERE A.JSBH=B.JSBH AND RYBH=? "
				+ "AND NVL(ZT,0)=1 UNION SELECT JSBH FROM %S WHERE JSBH='00' AND NVL(ZT,0)=1)))) A "
				+ " WHERE LENGTH(MKBH)=4 and mkbh like ? ORDER BY XH ";
		sql = String.format(sql, new String[]{" A.MKBH||'%' ",TnameU.MKB,TnameU.CZQXB,TnameU.JSCZQXB,
				TnameU.JSRYB, TnameU.JSB,TnameU.JSB});
		return db.queryForList(sql,new Object[]{loginrybh,loginrybh,loginrybh,MenuFlag.SHCD + "%"});
	}

	/**
	 * 获取下级单位使用的sql
	 * @param id
	 * @param name
	 * @param level
	 * @param loginrybh
	 * @return
	 */
	public List shTree(String id, String name, String level,String loginrybh) {
		int length = id.length()+2;
		String sql ="select a.* from (select a.mkbh,a.mkmc,a.url,a.xh,a.icon,"+(length==6?"'false'":"'true'")+" as isparent,nvl((select count(distinct dbh) from zc_sys_shcshb z where z.sfdqjd = '1' and z.rybh=? and z.mkbh like %s),0) as hj "
				+ "from %s a where length(a.mkbh)="+length+" and nvl(qxbz,'0')='1' "
				+ "and (exists(select mkbh from %s b where substr(mkbh,1,"+length+")=a.mkbh and rybh=?) "
				+ "or exists(select mkbh from %s c where substr(c.mkbh,1,2)=a.mkbh"
				+ " and jsbh in (select a.jsbh from %s a,%s b where a.jsbh=b.jsbh and rybh=? "
				+ "and nvl(zt,0)=1 union select jsbh from %s where jsbh='00' and nvl(zt,0)=1)))) a "
				+ "where length(mkbh) = "+length+" and mkbh like ? order by xh ";
		sql = String.format(sql, new String[]{" a.mkbh||'%' ",TnameU.MKB,TnameU.CZQXB,TnameU.JSCZQXB,
				TnameU.JSRYB, TnameU.JSB,TnameU.JSB});
		return db.queryForList(sql,new Object[]{loginrybh,loginrybh,loginrybh,id+"%"});
	}
	/**
	 * 获取目录分类树
	 * @param pid
	 * @return
	 */
	public List getmlList(){
		String sql = "select t.bh as bh,t.mc as mc,(select count(*) from ZC_BZML) as cnt from ZC_BZML t ,ZC_BZXX x  where t.bh=x.mlbh  and x.sfxs ='1' order by zt,bh";
		return db.queryForList(sql);
	}
	public List getXxBySjjd(String sjjd, String mkbh) {
		String sql = "select jdbh,mkbh,sjjd,mc,text,ljlj,jdbz,tjbz,tjshow,(select count(*) from mkb_menu_cx x where x.sjjd=t.jdbh) as XJCOUNT from mkb_menu_cx t where xsbz = '1'  and mkbh =? and sjjd=? order by mkbh,pxxh";
		return db.queryForList(sql, new Object[]{mkbh,sjjd});
	}
	
	public List getShMenuList(String pid){
		String qxsql = "select mkbh from zc_sys_czqxb c where rybh = '" + LUser.getRybh() + "' " +
					" union " +
					"select mkbh from zc_sys_jsqxb where jsbh in (select r.jsbh from (select jsbh from zc_sys_jsryb where rybh = '" + LUser.getRybh() + "') r inner join (select jsbh from zc_sys_jsb where zt = '1') j on r.jsbh = j.jsbh) ";
		
		String sql = "select mkbh,mkmc,url,nvl((select count(*) from zc_sys_mkb k where mkbh like m.mkbh||'%' and length(k.mkbh) = length(m.mkbh) + 2 and mklx = '3' and qxbz = '1' and mkbh in (select substr(mkbh,1,length(k.mkbh)) from (" + qxsql + "))),'0') cnt,nvl((select count(dbh) from zc_sys_shcshb where substr(mkbh,1,length(m.mkbh)) = m.mkbh and rybh = ? and sfdqjd = '1' and mkbh in (select mkbh from (" + qxsql + ") "+(pid.equals(MenuFlag.SHCD)?"intersect select mkbh from zc_sys_mkb k where mkbh like m.mkbh||'%' and length(k.mkbh) = length(m.mkbh) + 2 and mklx = '3' and qxbz = '1' and mkbh in (select substr(mkbh,1,length(k.mkbh)) from (" + qxsql + "))":"")+"  )),0) sl from zc_sys_mkb m where mkbh like ? and length(mkbh) = length(?) + 2 and mklx = '3' and qxbz = '1' and mkbh in (select substr(mkbh,1,length(?) + 2) from (" + qxsql + ")) order by xh,mkbh ";
		try{
			return db.queryForList(sql, new Object[]{LUser.getRybh(),pid+"%",pid,pid});
		}catch(Exception e){
			return new ArrayList();
		}
	}
	/**
	 * 返回学校list
	 * @return
	 */
	public List getSchoolTree(){
		String sql = "SELECT DM XXBH,MC XXMC FROM GX_SYS_DMB T WHERE ZL='31'";
		return db.queryForList(sql);
	}
}