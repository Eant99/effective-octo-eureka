package com.googosoft.dao.systemset.qxgl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.Const;
import com.googosoft.util.Validate;

@Repository("glqxbDao")
public class GlqxbDao extends BaseDao{
	
	private Logger logger = Logger.getLogger(GlqxbDao.class);
	
	@Resource(name="systemSet")
	private SystemSet systemSet;	
	/**
	 * 获取某个单位下的人员信息
	 * @param dwbh 单位编号
	 * @return
	 */
	public List GetModels(String sjdw) {
		String sql = "SELECT RYBH,RYGH,XM FROM %SRYB WHERE DWBH=? AND RYBH<>'000000'";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
	}
	
	/**
	 * 获取某个单位下的单位信息   000
	 * @param sjdw 上级单位
	 * @return
	 */
	public List GetDwModels(String sjdw) {
		String sql = "Select dwbh,bmh,mc,(select count(1) from %Sdwb a where a.sjdw=dwb.dwbh) as xjcount from %Sdwb dwb where sjdw=? order by dwbh";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
	}
	/**
	 * 获取某个单位下的单位信息  111
	 * @param sjdw 上级单位
	 * @return
	 */
	public List GetDwModels1(String sjdw) {
		String sql = "Select dwbh,bmh,mc,(select count(1) from %Sdwb a where a.sjdw=dwb.dwbh) as xjcount from %Sdwb dwb where sjdw=? order by pxxh,bmh";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
	}
	
	/**
	 * 获取某个地点信息下的直属子节点
	 * @param sjdd
	 * @return
	 */
	public List GetDdModels(String sjdd) {
		String sql = "Select ddbh,ddh,mc,(select count(1) from %Sddb a where a.sjdd=ddb.ddbh) as xjcount from %Sddb ddb where sjdd=? order by ddh,pxxh";
		sql=String.format(sql, SystemSet.sysBz, SystemSet.sysBz);
		return db.queryForList(sql,new Object[]{sjdd});
	}
	/**
	 * 权限model   000
	 * @param rybh 人员编号
	 * @return
	 */
	public List PowerModels(String rybh) {
		String sql = "SELECT DWB.DWBH AS DWBH,DWB.BMH AS BMH, DWB.MC AS MC,"
					+ "(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT AS DWZT " 
					+ "FROM "+TnameU.GLQXB+" T " 
					+ "INNER JOIN %SDWB DWB ON DWB.DWBH = T.DWBH AND T.RYBH = ? AND EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') " 
					+ "UNION " 
					+ "SELECT DWB.DWBH,BMH,MC,(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT " 
					+ "FROM %SDWB DWB " 
					+ "INNER JOIN %SRYB RYB ON DWB.DWBH = RYB.DWBH AND RYB.RYBH = ? "
					+ "AND NOT EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') ORDER BY DWBH ";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh});
	}
	/**
	 * 权限model   111
	 * @param rybh 人员编号
	 * @return
	 */
	public List PowerModels1(String rybh) {
		String sql = "SELECT DWB.DWBH AS DWBH,DWB.BMH AS BMH, DWB.MC AS MC,"
				+ "(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT AS DWZT " 
				+ "FROM "+TnameU.GLQXB+" T " 
				+ "INNER JOIN %SDWB DWB ON DWB.DWBH = T.DWBH AND T.RYBH = ? AND EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') " 
				+ "UNION " 
				+ "SELECT DWB.DWBH,BMH,MC,(SELECT COUNT(1) FROM %SDWB A WHERE A.SJDW = DWB.DWBH) AS XJCOUNT,DWB.DWZT " 
				+ "FROM %SDWB DWB " 
				+ "INNER JOIN %SRYB RYB ON DWB.DWBH = RYB.DWBH AND RYB.RYBH = ? "
				+ "AND NOT EXISTS (SELECT 1 FROM "+TnameU.GLQXB+" WHERE RYBH = ? AND ZL='2') ORDER BY BMH ";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh});
	}
	
	/**
	 * 权限model
	 * @param rybh 人员编号
	 * @return
	 */
	public List PowerDdModels(String rybh) {
		String sql = "SELECT DDBH,DDH,MC,SJDD,DWBH,(SELECT COUNT(1) FROM %SDDB A "
				+ "WHERE A.SJDD = %SDDB.DDBH) AS XJCOUNT FROM %SDDB WHERE SJDD='000000' AND NVL(DDZT,'1')='1' ORDER BY DDH";
		sql=String.format(sql, SystemSet.sysBz, SystemSet.sysBz, SystemSet.sysBz);
		return db.queryForList(sql,new Object[]{});
	}

	/**
	 * 通过人员编号获取已授权信息
	 */
	public List findListByRybh(String rybh) {
		String sql = "SELECT T.DWBH AS RYBH,D.BMH AS BMH,D.MC AS DWMC,T.ZL AS ZL,T.Dwbh AS DWBH,TO_CHAR(T.CZRQ,'yyyy-mm-dd hh24:mi:ss') AS SQRQ,T.CZR AS CZY,T.XTBZ AS XTBZ FROM "+TnameU.GLQXB+" T LEFT JOIN %SDWB D ON T.DWBH = D.DWBH WHERE T.ZL='2' AND T.RYBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{rybh});
	}
	
	/**
	 * 通过下级单位查询该单位的所有上级单位
	 */
	public List findSjdwByXjdw(String xjdw) {
		String sql = "SELECT T.DWBH,T.BMH,T.MC FROM %SDWB T START WITH T.DWBH=? CONNECT BY PRIOR T.SJDW=T.DWBH";
		sql=String.format(sql, SystemSet.gxBz);
		return  db.queryForList(sql,new Object[]{xjdw});
	}
	
	/**
	 * 判断所选单位是否是根节点
	 */
	public int checkRootNodeByDwbh(String dwbh) {
		String sql = "SELECT count(1) FROM %SDWB T WHERE T.SJDW='000000' AND T.DWBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		int  s = 0;
		s = db.queryForObject(sql,new Object[]{dwbh}, Integer.class);
		return s;
	}
	
	/**
	 * 判断单位是否是父节点
	 */
	public int checkParNodeByDwbh(String sjdw,String dwbh) {
		String sql = "SELECT count(1) FROM %SDWB T WHERE T.SJDW=? AND T.DWBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		int  s = 0;
		s = Integer.parseInt(db.queryForObject(sql,new Object[]{sjdw,dwbh}, String.class));
		return s;
	}
	
	/**
	 * 判断所选单位是否是根节点
	 */
	public Map findRootNodeMapByDwbh(String dwbh) {
		String sql = "SELECT T.DWBH,T.BMH,T.MC FROM %SDWB T WHERE T.SJDW='000000' AND T.DWBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dwbh});
	}
	
	/**
	 * 查出左边所选单位同级的所有单位编号
	 */
	public List findTongjiDwbh(String dwbh) {
		String sql = "SELECT T.DWBH FROM %Sdwb T WHERE T.SJDW=(SELECT T.SJDW FROM %Sdwb T WHERE T.DWBH=?) AND T.DWBH <>? ORDER BY T.DWBH";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{dwbh,dwbh});
	}
	
	/**
	 * 查出左边所选单位的所有下级单位编号（不包括本身）
	 */
	public List findXiajiDwbh(String dwbh) {
		String sql = "SELECT T.DWBH AS XJDW FROM %SDWB T WHERE DWBH<>? START WITH T.DWBH=? CONNECT BY T.SJDW = PRIOR T.DWBH";
		sql=String.format(sql, SystemSet.gxBz);
		return  db.queryForList(sql,new Object[]{dwbh,dwbh});
	}
	
	/**
	 * 通过下级单位查询该单位的上级单位
	 */
	public String findSjdwByDwbh(String dwbh) {
		String sql = "SELECT T.SJDW FROM %SDWB T WHERE T.DWBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return  db.queryForObject(sql,new Object[]{dwbh}, String.class);
	}
	
	/**
	 * 通过单位编号查询Map
	 */
	public Map findMapByDwbh(String dwbh) {
		String sql = "SELECT T.DWBH,T.BMH,T.MC FROM %SDWB T WHERE T.DWBH=? ";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dwbh});
	}
	
	/**
	 * 保存管理单位权限信息
	 * @param rybh
	 * @param mkbh
	 * @return
	 */
	public int doSave(String qxsz,String rybh,String flag) {
		Date czrq =new Date();
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		try {
			String zl = "";
			if("D".equals(flag)){
				zl = "2";
				
				sqllist.add("delete from zc_sys_rydwqxb where rybh = ?");
				parlist.add(new Object[]{rybh});
				
				if(Validate.noNull(qxsz)){
					sqllist.add("insert into zc_sys_rydwqxb(rybh,dwbh) select ?,dwbh from gx_sys_dwb start with dwbh in ('" + ToSqlUtil.pointToSql(qxsz) + "')  connect by prior dwbh = sjdw");
					parlist.add(new Object[]{rybh});
				}
				else{
					sqllist.add("insert into zc_sys_rydwqxb(rybh,dwbh) select ?,dwbh from gx_sys_dwb start with dwbh = (select dwbh from gx_sys_ryb where rybh = ?) connect by prior dwbh = sjdw");
					parlist.add(new Object[]{rybh,rybh});
				}
			}
			else{
				zl = "1";
			}
			
			String sqls = "DELETE FROM "+TnameU.GLQXB+" WHERE RYBH =? AND ZL=?";
			sqllist.add(sqls);
			parlist.add(new Object[]{rybh,zl});
			if(Validate.noNull(qxsz)){
				String resources[] =  qxsz.split(",");
				sqls = "INSERT INTO "+TnameU.GLQXB+"(RYBH,ZL,DWBH,CZR,CZRQ,XTBZ) VALUES(?,?,?,?,?,?)";
				for (int i=0;i<resources.length;i++){
					sqllist.add(sqls);
					parlist.add(new Object[]{rybh,zl,resources[i],LUser.getRybh(),czrq,systemSet.XTBZ});
				}
			}
			return db.batchUpdate(sqllist, parlist) ? 1 : 0;
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
	}
	
	public List findZclbTree(String rybh) {
		String sql = "select t.dwbh,d.flh,d.flmc,(case t.dwbh when '00' then '全部分类' else '('||d.flh||')'||translate(d.flmc using char_cs) end) flbhmc,(case t.dwbh when '00' then '00' else d.bzdm end) bzdm,t.zl,"
				+ "to_char(t.czrq,'yyyy-mm-dd hh24:mi:ss') sqrq,t.czr czy,t.xtbz "
				+ "from (select dwbh,zl,czrq,czr,xtbz from " + TnameU.GLQXB + " where zl = '1' and xtbz = '01' and rybh = ?) t left join zc_flb_jyb d on t.dwbh = d.bzdm";
		return db.queryForList(sql,new Object[]{rybh});
	}
	
	public List getZclbModels() {
		String sql = "select bzdm,flh,flmc from zc_flb_jyb where dmjc = '1' order by flh";
		return db.queryForList(sql);
	}
	
	/**
	 * 获取分类权限
	 * @param rybh
	 * @return
	 */
	public String findFlqxFromGlqxb(String rybh){
		List list = new ArrayList();
		String flqx = "00";//00代表管理所有分类（自定义）
		list = db.queryForList("select dwbh from "+TnameU.GLQXB+" where zl='"+Constant.FLQX+"' and rybh='" + rybh + "'");
		List dlList = db.queryForList("SELECT BZDM,FLH,FLMC FROM ZC_FLB_JYB WHERE DMJC='1' ORDER BY FLH");
		if(list.size() == 0 || list.size() == dlList.size()){//全部分类权限（没有单独分配或分配了全部大类都属于全部权限）
			flqx = "00";
		}
		else{//不是全部分类权限
			for(int i = 0; i < list.size(); i++){
				if("00".equals(Validate.isNullToDefault(((Map)list.get(i)).get("DWBH"), ""))){
					return "00";
				}
			}
			flqx = "99";
		}
		return flqx;
    }
	/**
	 * 根据rybh获取分类号
	 * @return
	 */
	public String getFlhByRybh(String rybh){
		String sql = "select substr(dwbh,0,2) as bh from "+TnameU.GLQXB+" where rybh = ? and zl='1'";
		List list = db.queryForList(sql,rybh);
		StringBuffer flhs = new StringBuffer();
		if(list.size()==0){//如果没有单独分配权限，默认为管理全部分类
			flhs.append("00','");
		}else{
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				flhs.append(map.get("BH")+"','");
			}
			flhs.substring(0, flhs.length()-3);
		}
		return flhs+"";
	}
	/**
	 * 根据人员编号查询"+TnameU.GLQXB+"里存储的单位编号
	 * @param rybh
	 * @return 单位编号组成的字符串
	 */
	public String findShDwbhByRybh(String rybh){
		String sql = "select dwbh as bh from "+TnameU.GLQXB+" where zl='2' and  rybh = ?";
		List list = new ArrayList();
		try{  
			list = db.queryForList(sql, new Object[]{rybh});
		}catch (DataAccessException e){  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		StringBuffer dwbhs = new StringBuffer();
		if(list.size()==0){
			String dwbhsql = "select dwbh from %Sryb where rybh=?";
			dwbhsql=String.format(dwbhsql, SystemSet.gxBz);
			String dwbh = db.queryForSingleValue(dwbhsql, new Object[]{rybh});
			dwbhs.append(dwbh);
		}else{
			for (int i = 0; i < list.size(); i++) {
				Map map = (Map)list.get(i);
				dwbhs.append(Validate.isNullToDefault(map.get("BH"), ""));
				if(i != list.size()-1){
					dwbhs.append("','");
				}
			}
		}
		return dwbhs.toString();
	}
	
	
	
	/**
	 * 获取人员权限的快捷语句
	 * @param rybh 人员编号
	 * @param colName 列名
	 * @param flag D  单位权限sql   F  资产分类权限sql
	 * @return
	 */
	public String getQxsql(String rybh,String colName,String flag){
		if(rybh.equals(SystemSet.AdminRybh())){
			return "";
		}
		String sql="",result="";
		if("D".equals(flag)){
			sql="select dwbh from " + TnameU.GLQXB + " where zl = '" + Constant.DWQX + "' and xtbz = '" + SystemSet.XTBZ + "' and rybh = '" + rybh + "' and dwbh = '" + SystemSet.Dwbh() + "'";
		    result = db.queryForSingleValue(sql);
			if(Validate.noNull(result)){//如果当前登录人有最顶级的单位权限，那么不控制单位权限
				return "";
			}
			sql="select count(dwbh) from " + TnameU.GLQXB + " where zl = '" + Constant.DWQX + "' and xtbz = '" + SystemSet.XTBZ + "' and rybh = '" + rybh + "'";
			result = db.queryForSingleValue(sql);
			if(Integer.parseInt(result) == 0){//没有分配单位权限的，可以管辖所在单位以及下级单位
				return " and " + colName + " in (select dwbh from " + SystemSet.gxBz + "dwb connect by prior dwbh = sjdw and dwzt = '1' start with dwbh = (select dwbh from " + SystemSet.gxBz + "ryb where rybh = '" + rybh + "')) ";
			}else{//分配了权限的，只能管辖分配权限下状态正常的单位
				return " and " + colName + " in (select z.dwbh from (select dwbh from " + TnameU.RYDWQXB + " z where z.rybh = '" + rybh + "') z inner join (select dwbh from " + SystemSet.gxBz + "dwb d where d.dwzt = '1') d on z.dwbh = d.dwbh) ";
			}
		}else if("F".equals(flag)){
			String flqx = findFlqxFromGlqxb(rybh);
			if("99".equals(flqx)){//不是管理所有分类权限
				return " and " + colName + " in (select flh from (select bzdm,flh,flmc,jldw,dmjc,(case sjdm when bzdm then '0000000000' else sjdm end) sjdm,zcdm,zjff,synx,jczl,zgzl,ygzl,ffldm,fflmc,sfmj,sfwxzc,billtype from zc_flb_jyb) start with bzdm in (select dwbh from "+TnameU.GLQXB+" where zl='"+Constant.FLQX+"' and xtbz='"+SystemSet.XTBZ+"' and rybh = '"+rybh+"') connect by prior bzdm = sjdm) ";
			}else{
				return "";
			}
		}else if("C".equals(flag)){
			String flqx = findFlqxFromGlqxb(rybh);
			if("99".equals(flqx)){//不是管理所有分类权限
				return " and " + colName + " in (select ffldm from (select bzdm,flh,flmc,jldw,dmjc,(case sjdm when bzdm then '0000000000' else sjdm end) sjdm,zcdm,zjff,synx,jczl,zgzl,ygzl,ffldm,fflmc,sfmj,sfwxzc,billtype from zc_flb_jyb) start with bzdm in (select dwbh from "+TnameU.GLQXB+" where zl='"+Constant.FLQX+"' and xtbz='"+SystemSet.XTBZ+"' and rybh = '"+rybh+"') connect by prior bzdm = sjdm) ";
			}else{
				return "";
			}
		}
		return "";
	}
	
}
