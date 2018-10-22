package com.googosoft.dao.kjhs;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.servlet.http.HttpSession;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.systemset.qxgl.GlqxbDao;
import com.googosoft.pojo.kjhs.Cw_kmyemxb;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("kmszDao")
public class KmszDao  extends BaseDao{
	private Logger logger = Logger.getLogger(GlqxbDao.class);
	
	public Map getkmszObjectById(String id) {
//	String sql = "select d.gid,d.rybh,(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=d.rybh) as ryb,d.xm,d.bgdd,d.zw,d.bddh,d.moblie,d.qq,d.email,d.zt," 
//			+ "(case d.zt when '1' then '正常' when '0' then '禁用' end) ztmc,d.pxxh " 
//			+ " from ZC_TXL d where gid = ?";	
	String sql="select fyfl,kmnd,kmmc,zkmsxdm,kmjdm,kmlbdm,kmfc,zjf,yefx from cw_kjkmsz";
	sql=String.format(sql);
	return db.queryForMap(sql,new Object[]{id});
}
	/**
	 * 根据主键获取数据字典的详细信息  进行修改操作等
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		String sql = "SELECT A.DM,A.MC,A.JB,A.BZ,A.DMXH,A.ZL,A.DMSX,A.MS FROM %SDMB A WHERE A.DMXH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dmxh});
	}
	
	public List getSsxt() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct(ztnd) from cw_qmjzb order by ztnd asc ");
		
		return db.queryForList(sql.toString());
	}
	
	
	public String getTime() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select max(ztnd) from cw_qmjzb order by ztnd asc ");
		
		return db.queryForSingleValue(sql.toString());
	}
	
	
	/**
	 * 获取会计科目字典树
	 */
	public  List kmszMenu(String jb){
		//String sql = "select dm,mc,zl,jb from gx_sys_dmb where jb = '"+jb+"' and zl='"+Constant.ZCZL+"'";
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from gx_sys_dmb b where b.zl='"+Constant.ZCZL+"' and b.jb=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"'";
		//String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from gx_sys_dmb b where b.zl='"+Constant.ZCZL+"' and b.dm=t.kmjdm) as count from cw_kjkmsz t where t.kmjdm ='"+jb+"' and t.zl='"+Constant.ZCZL+"'";
		//String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
			//	"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+jb+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取会计科目字典树下级
	 */
	public  List kmszMenuzj(String dm){
		//String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
			//	"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+jb+"'";
	//	String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+dm+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
	//			"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+dm+"'";
	
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count,'' as ISLEAF from gx_sys_dmb t where t.jb ='"+dm+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
		"select t.jb as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count,t.isleaf as ISLEAF from cw_kjkmsz t WHERE t.kmjdm ='"+dm+"' ";
					
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  List kmszMenuzj1(String jb){
	
		
		String sql = "select t.jb as JB,t.aid as aid,t.kmmc as MC,t.guid,t.isleaf as ISLEAF from cw_kjkmsz t where t.aid='"+jb+"'";
					
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取功能科目字典树
	 */
	public  List gnszMenu(String jb){
		//String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from gx_sys_dmb b where b.zl='"+Constant.GNZL+"' and b.jb=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.GNZL+"'";
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from gx_sys_dmb b where b.zl='"+Constant.ZCZL+"' and b.jb=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"'";

		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取功能科目字典树下级
	 */
	public  List gnszMenuzj(String jb){
	  //String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_gnkmb b where b.kmbh=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.GNZL+"' union\r\n" + 
			//	"select t.guid as dm,t.czr as mc,t.kmbh as zl,t.sskjkmbh as jb, 0 as count from cw_gnkmb t WHERE T.KMBH='"+jb+"'";
//		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_gnkmb b where b.kmbh=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.GNZL+"' union\r\n" + 
//				"select t.kmbh as dm,t.kmmc as mc,t.sssjkm as zl,t.sskjkmbh as jb, 0 as count from cw_gnkmb t WHERE T.SSSJKM='"+jb+"'";
//		String sql="  select (SELECT COUNT(1) FROM CW_GNKMB A WHERE A.SSKJKMBH = t.jb) AS XJCOUNT,GUID,jb, kmjc, kmmc, SSKJKMBH, KMBH from CW_GNKMB T where SSKJKMBH = '"+jb+"' ";
//
//		List menuList = db.queryForList(sql);
//		return menuList;
			String sql = "";
		if(jb.equals("root")){
			sql = "select (SELECT COUNT(1) FROM CW_GNKMB A WHERE A.SSSJKM = t.KMBH) AS XJCOUNT,GUID,jb,KMND, kmjc, kmmc, SSKJKMBH, KMBH,YEFX,KMSX from CW_GNKMB T where SSSJKM IS NULL and t.kmjc='1' ";
		}else{
			sql = "select (SELECT COUNT(1) FROM CW_GNKMB A WHERE A.SSSJKM = t.KMBH) AS XJCOUNT,GUID,jb,KMND, kmjc, kmmc, SSKJKMBH, KMBH,YEFX,KMSX from CW_GNKMB T where SSSJKM = '"+jb+"' ";
		}
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取经济科目字典树
	 */
	public  List jjszMenu(String jb){
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from gx_sys_dmb b where b.zl='"+Constant.JJZL+"' and b.jb=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.JJZL+"'";
		
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  List jjszMenuzj(String dm){
		
		  //String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_jjkmb b where b.kmbh=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.JJZL+"' union\r\n" + 
		//			"select t.guid as dm,t.czr as mc,t.kmbh as zl,t.sskjkmbh as jb, 0 as count from cw_jjkmb t WHERE T.KMBH='"+jb+"'";
//			String sql = "select t.bz as l,t.zl as k,t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_jjkmb b where b.kmbh=t.dm) as count,'2' as kmjc from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.JJZL+"' union\r\n" + 
//					"select t.l,t.l,t.guid as dm,t.kmmc as mc,t.kmbh as zl,t.sssjkm as jb, 0 as count,kmjc as kmjc from cw_jjkmb t WHERE T.SSSJKM='"+jb+"'";
			String sql = "";
			if(Validate.noNull(dm)){
				sql = "select t.guid,t.kmnd,t.kmbh,t.kmmc,t.l,t.k,T.KMJC from cw_jjkmb t WHERE T.k='"+dm+"'  and t.qyf='1' ORDER BY t.kmbh";
			}else{
				sql = "select t.guid,t.kmnd,t.kmbh,t.kmmc,t.l,t.k,T.KMJC from cw_jjkmb t WHERE T.k is null and l is null and t.qyf='1' ORDER BY t.kmbh";
			}
			List menuList = db.queryForList(sql);
			return menuList;
		}
	public int getCount(String kmbh){
		String sql = "select count(0) from cw_jjkmb t WHERE T.k='"+kmbh+"' and t.qyf='1' ";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0"));
		return count;
	}
	
	/*public  List kmszMenuByTwo(String jb,String zl){
		String sql = "select dm,mc,zl,jb from gx_sys_dmb where jb = '"+jb+"' and zl='"+zl+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}*/
	public int updatenczye(String guid,String kmzye,String sszt) {
		int i;
		String sql = "update cw_kmyeb set kmzye=?,sszt=? where guid =?";
		i = db.update(sql,new Object[]{kmzye,sszt,guid});
		return i;
	}
	public int dodeletekmyemx(String guid,String nd) {
		
		String sql="delete cw_kmyemxb where kmyebh=? and nd =?";
		return db.update(sql,new Object[] {guid,nd});
	}
	public int doAddkmyemx(Cw_kmyemxb kmyemx) {
		System.err.println("kmyemx.getGrfzcx()="+kmyemx.getGrfzcx());
		System.err.println("kmyemx.getWldwfzdh()="+kmyemx.getWldwfzdh());
		System.err.println("kmyemx.getWldwfz()="+kmyemx.getWldwfz());
		System.err.println("kmyemx.getGrfz()="+kmyemx.getGrfz());
		String sql = "insert into cw_kmyemxb(guid,jjkm,bmbh,xmbh,ncye,kmyebh,kmbh,kmmc,sszt,nd,grfz,dwfz,dwbh,grdh) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		
		return db.update(sql,new Object[] {kmyemx.getGuid(),kmyemx.getJjkm(),kmyemx.getBmbh(),kmyemx.getXmbh(),kmyemx.getNcye(),kmyemx.getKmyebh(),kmyemx.getKmbh(),kmyemx.getKmmc(),kmyemx.getSszt(),kmyemx.getNd(),kmyemx.getGrfz(),kmyemx.getWldwfz(),kmyemx.getWldwfzdh(),kmyemx.getGrfzcx()});
	}
	public List GetFykmdysz(String sjfyfl,HttpSession session) {

		String sql="";
		
		if(sjfyfl.equals("root")){
			 sql=" select (SELECT COUNT(1) FROM CW_KJKMSZB A WHERE A.SJFL = t.jb) AS XJCOUNT,kmjc,kjzd,GUID,jb,kmsx,to_char(kmnd,'yyyy') as kmnd, kmjc,sfmj, kmmc,yefx, SJFl, KMBH from CW_KJKMSZB T where sjfL = 'root' and kjzd='"+CommonUtil.getKjzd(session)+"' and sszt='"+Constant.getztid(session)+"' order by kmbh";	
		}else{
			 sql="  select (SELECT COUNT(1) FROM CW_KJKMSZB A WHERE A.SJFL = t.jb) AS XJCOUNT,kmjc,kjzd,GUID,jb,kmsx,to_char(kmnd,'yyyy') as kmnd, kmjc,sfmj, kmmc,yefx, SJFl, KMBH from CW_KJKMSZB T where sjfL = '"+sjfyfl+"' and kjzd='"+CommonUtil.getKjzd(session)+"' and sszt='"+Constant.getztid(session)+"' order by kmbh";
		}
		return db.queryForList(sql,new Object[]{});
	}
	/**
	 * 删除
	 */
	public int deleteSrxm(PageData pd) {
		String guid = pd.getString("guid");
		System.err.println("______"+guid);
		String sql = "delete from CW_JJKMB where guid in ('"+guid+"')";
		return db.update(sql);
	}
	/**
	 * 单位信息实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectByIdByKmsz(String guid) {
		String sql = "select guid,kmbh,kmmc,yslx,l,k,kmjc,qyf,sm from CW_JJKMB where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 
	 * @param dwbh
	 * @return
	 */
	public int getCountByKmbh(String kmbh) {
		String sql = "select count(0) from CW_JJKMB where k='"+kmbh+"'";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), ""));
		return count;
	}
	
	/**
	 * 删除
	 * @param guid
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String DELSQL = "DELETE FROM CW_JJKMB WHERE GUID " + wstr;
		DELSQL = String.format(DELSQL, SystemSet.gxBz);
		int result = 0;
		try {
			result = db.update(DELSQL, params);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			
			//logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();// 事务回滚
		}
		return result;
	}
	/**
	 * 判断KMBH是否重复
	 * @param bmh
	 * @return true为不重复,false为重复
	 */
	public boolean doCheckKmbh(String kmbh){
		String sql = "select count(1) from CW_JJKMB where  KMBH= ? ";
		sql=String.format(sql, SystemSet.gxBz);
		String count = db.queryForObject(sql,new Object[]{kmbh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 增加
	 * @param dwb
	 * @return
	 */
	public int doAdd(KMSZ kmsz,HttpSession session) {
		String sszt = Constant.getztid(session);
		String sql = "insert into CW_JJKMB(guid,kmbh,kmmc,czr,l,k,kmjc,qyf,sm,czrq,sszt,kmnd,yslx) "
				+ "values(sys_guid(),?,?,?,?,?,?,?,?,sysdate,?,to_char(sysdate,'yyyy'),?)";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				kmsz.getKmbh(),
				kmsz.getKmmc(),
				LUser.getGuid(),
				kmsz.getL(),
				kmsz.getK(),
				kmsz.getKmjc(),
				
				kmsz.getQyf(),
				kmsz.getSm(),
				sszt,
				kmsz.getYslx()
				
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		   // logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改
	 * @param dwb 单位表
	 * @return
	 */
	public int doUpdate(KMSZ kmsz){
		String sql = "update CW_JJKMB set kmmc=?,czr=?,qyf=?,sm=?,yslx=?,czrq=sysdate where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				kmsz.getKmmc(),
				LUser.getGuid(),
				kmsz.getQyf(),
				kmsz.getSm(),
				kmsz.getYslx(),
				kmsz.getGuid()
		};
	    int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		   // logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 导入科目
	 */
	public int drkm(String kjzd) {
		Calendar date = Calendar.getInstance();
        String year = String.valueOf(date.get(Calendar.YEAR));
//		String sql = "select kmbh, guid,kmmc,sfmj,to_char(kmnd,'yyyy-mm-dd') as kmnd,sfjjflkm,bmhs,xmhs,kmsx,sszt from cw_kjkmszb";
//		List list = db.queryForList(sql);
//		
//		//String sql2 = "Truncate Table cw_kmyeb";
//		//db.update(sql2);
//		for(int i=0;i<list.size();i++) {
//			int sffz=0;
//			Map map = (Map) list.get(i);
//			String guid = (String) map.get("guid");
//			String kmbh = (String) map.get("kmbh");
//			String kmmc = (String) map.get("kmmc");
//			String sfmj = (String) map.get("sfmj");
//			String kmsx = (String) map.get("kmsx");
//			String sszt = (String) map.get("sszt");
//			
//			String nd1 = (String) map.get("kmnd");
//	
//			String sfjjflkm1 = (String) map.get("sfjjflkm");
//			int sfjjflkm = Integer.parseInt(sfjjflkm1);
//			String bmhs1 = (String) map.get("bmhs");
//			int bmhs = Integer.parseInt(bmhs1);			
//			String xmhs1 = (String) map.get("xmhs");
//			int xmhs = Integer.parseInt(xmhs1);
//			System.out.println("sfjjflkm========"+sfjjflkm);
//			System.out.println("bmhs========"+bmhs);
//
//			System.out.println("xmhs========"+xmhs);
//
//			if(sfjjflkm==1 || bmhs==1 ||xmhs==1) {
//				sffz=1;
//			}
//			System.out.println("sffz========"+sffz);
//			String sql2 = "select count(0) from cw_kmyeb where kmbh ='"+kmbh+"'";
//			Map map1 = db.queryForMap(sql2);
//		    String m = String.valueOf(map1.get("count(0)")) ;
//			
//			if("1".equals(m)) {
//				
//			}else {
				String sql3 = "insert into cw_kmyeb(guid,kmbh,kmmc,sfmj,nd,sffz,kmsx,sszt,kmid,kjzd) select sys_guid(),kmbh,kmmc,sfmj,(select distinct substr(qyrq,1,4)  from cw_ztxxb) a,'0',kmsx,sszt,guid ,'"+kjzd+"' from cw_kjkmszb";
				String sql = "update cw_kmyeb set sffz='1' where kmbh in (select kmbh from cw_kjkmszb t where t.sfjjflkm='1' or t.bmhs='1' or t.xmhs='1' or t.wldwfz='1' or t.grfz='1' )";
				ArrayList list = new ArrayList<>();
				list.add(sql3);
				list.add(sql);
				db.batchUpdate(list);
			//}
			
			
		return 0;
	}
     public List<Map<String, Object>> getkm(String nd,String zkmsx,String sszt,String kjzd){
    	// System.out.println("zkmsx=================="+zkmsx.length());
    	 String sql;
    	 if("zkmsx".equals(zkmsx)) {
        	  sql="select rownum as xh, decode(nvl(kmzye,''),0,'0.00',(to_char(round(kmzye,2),'fm99999999999990.00')))kmzye, guid, sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"' and kj.sszt='"+sszt+"') as bhkm,kmbh,kmmc,(case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"' and kj.sszt='"+sszt+"') when '1' then '贷方' else '借方' end)yefx,(case (select GRFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"' and kj.sszt='"+sszt+"')  when '1' then '1' else '0' end) GRFZ,(case (select WLDWFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"' and kj.sszt='"+sszt+"')  when '1' then '1' else '0' end) wldwfz, sffz,nd from CW_KMYEB K where nd='"+nd+"' and kjzd='"+kjzd+"' and sszt='"+sszt+"' and kmsx is not null order by kmbh  ";
//        	  sql="select rownum as xh, decode(nvl(kmzye,''),0,'0.00',(to_char(round(kmzye,2),'fm99999999999990.00')))kmzye, guid, sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"') as bhkm,kmbh,kmmc,(case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"') when '1' then '贷方' else '借方' end)yefx,(case (select GRFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"')  when '1' then '1' else '0' end) GRFZ,(case (select WLDWFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"')  when '1' then '1' else '0' end) wldwfz, sffz,nd from CW_KMYEB K where nd='2018' and kjzd='"+kjzd+"' and sszt='"+sszt+"' and kmsx is not null order by kmbh  ";

    	 }else {
    		  sql="select rownum as xh, decode(nvl(kmzye,''),0,'0.00',(to_char(round(kmzye,2),'fm99999999999990.00')))kmzye, guid, sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"' and kj.sszt='"+sszt+"') as bhkm,kmbh,kmmc,(case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"' and kj.sszt='"+sszt+"') when '1' then '贷方' else '借方' end)yefx,(case (select GRFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"' and kj.sszt='"+sszt+"')  when '1' then '1' else '0' end) GRFZ,(case (select WLDWFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"' and kj.sszt='"+sszt+"')  when '1' then '1' else '0' end) wldwfz, sffz,nd from CW_KMYEB K where nd='"+nd+"' and kjzd='"+kjzd+"' and sszt='"+sszt+"' and kmsx='"+zkmsx+"' order by kmbh  ";
//    		  sql="select rownum as xh, decode(nvl(kmzye,''),0,'0.00',(to_char(round(kmzye,2),'fm99999999999990.00')))kmzye, guid, sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"') as bhkm,kmbh,kmmc,(case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh and kj.kjzd='"+kjzd+"') when '1' then '贷方' else '借方' end)yefx,(case (select GRFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"')  when '1' then '1' else '0' end) GRFZ,(case (select WLDWFZ from cw_kjkmszb kj  where kj.kmbh = k.kmbh  and kj.kjzd = '"+kjzd+"')  when '1' then '1' else '0' end) wldwfz, sffz,nd from CW_KMYEB K where nd='2018' and kjzd='"+kjzd+"' and sszt='"+sszt+"' and kmsx='"+zkmsx+"' order by kmbh  ";

    	}
    	 
    	 
         return db.queryForList(sql);
     }
     public Map getfz(String kmyeguid) {
    	 String sql="select sfjjflkm,bmhs,xmhs,(case (select wldwfz from cw_kjkmszb where kmbh='"+kmyeguid+"') when '1' then '1' else '0'  end) wldwfz,(case (select grfz from cw_kjkmszb where  kmbh='"+kmyeguid+"') when '1' then '1' else '0'  end) grfz from cw_kjkmszb where kmbh='"+kmyeguid+"'";
    	 return db.queryForMap(sql);
     }
     public List getcountkmye() {
    	// Calendar date = Calendar.getInstance();
	   //  int jn=Integer.valueOf(date.get(Calendar.YEAR));//今年
    	 String sql="select * from cw_kmyeb";
    	 return db.queryForList(sql);
    	 
    	 
     }
     public boolean doCheck(String kmguid) {
 		String sql = ("select count(1) from cw_sys_ywjlb  where kmlx=2 and ywid"+CommonUtils.getInsql(kmguid));
 		Object[] obj = kmguid.split(",");
 		System.err.println("++++++"+sql);
 		int count = Integer.parseInt(db.queryForSingleValue(sql,obj));
 			if(count>0){
 				return true;
 			}else{
 				return false;
 			}
 	}

	// 科目余额明细 导入数据确认
	public List<Map<String, Object>> insertKmyemx(String file) {
		Workbook rwb;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int rows = rs.getRows();// 得到所有的行
			String[] num = new String[rows];
//			for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
//				Map<String, Object> map = new HashMap<String, Object>();
				//模板一
				if(rs.getCell(1, 1).getContents().equals("经济科目") && rs.getCell(1, 1).getContents().equals("部门名称") && rs.getCell(1, 1).getContents().equals("项目名称")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql = "SELECT COUNT(*) FROM CW_JJKMB T WHERE T.KMMC='"+ rs.getCell(1, i).getContents() + "'";
					int a = Integer.parseInt(db.queryForSingleValue(sql));// 判断经济科目是否存在
					String sql2 = "SELECT COUNT(*) FROM GX_SYS_DWB T WHERE T.MC='"+ rs.getCell(2, i).getContents() + "'";
					int b = Integer.parseInt(db.queryForSingleValue(sql2));// 判断填写单位是否存在
					String sql3 = "SELECT COUNT(*) FROM CW_XMJBXXB T WHERE T.XMMC='"+ rs.getCell(3, i).getContents() + "'";
					int c = Integer.parseInt(db.queryForSingleValue(sql3));// 判断项目是否存在
					if (i != 1 && a == 0) {
						map.put("JJKM",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的经济科目不存在)</font>");
					} else {
						map.put("JJKM", rs.getCell(1, i).getContents());
					}
					if (i != 1 && b == 0) {
						map.put("BMBH",rs.getCell(2, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的部门不存在)</font>");
					} else {
						map.put("BMBH", rs.getCell(2, i).getContents());
					}
					if (i != 1 && c == 0) {
						map.put("XMBH",rs.getCell(3, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的项目不存在)</font>");
					} else {
						map.put("XMBH", rs.getCell(3, i).getContents());
					}
					map.put("NCYE", rs.getCell(4, i).getContents());
					list.add(map);
					}
				}else 
					//模板二
				if(rs.getCell(1, 1).getContents().equals("经济科目") && rs.getCell(1, 1).getContents().equals("部门名称")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql = "SELECT COUNT(*) FROM CW_JJKMB T WHERE T.KMMC='"+ rs.getCell(1, i).getContents() + "'";
					int a = Integer.parseInt(db.queryForSingleValue(sql));// 判断经济科目是否存在
					String sql2 = "SELECT COUNT(*) FROM GX_SYS_DWB T WHERE T.MC='"+ rs.getCell(2, i).getContents() + "'";
					int b = Integer.parseInt(db.queryForSingleValue(sql2));// 判断填写单位是否存在
					if (i != 1 && a == 0) {
						map.put("JJKM",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的经济科目不存在)</font>");
					} else {
						map.put("JJKM", rs.getCell(1, i).getContents());
					}
					if (i != 1 && b == 0) {
						map.put("BMBH",rs.getCell(2, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的部门不存在)</font>");
					} else {
						map.put("BMBH", rs.getCell(2, i).getContents());
					}
					map.put("NCYE", rs.getCell(3, i).getContents());
					list.add(map);
					}
				}else 
				//模板3
				if(rs.getCell(1, 1).getContents().equals("经济科目") && rs.getCell(1, 1).getContents().equals("项目名称")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql = "SELECT COUNT(*) FROM CW_JJKMB T WHERE T.KMMC='"+ rs.getCell(1, i).getContents() + "'";
					int a = Integer.parseInt(db.queryForSingleValue(sql));// 判断经济科目是否存在
					String sql3 = "SELECT COUNT(*) FROM CW_XMJBXXB T WHERE T.XMMC='"+ rs.getCell(2, i).getContents() + "'";
					int c = Integer.parseInt(db.queryForSingleValue(sql3));// 判断项目是否存在
					if (i != 1 && a == 0) {
						map.put("JJKM",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的经济科目不存在)</font>");
					} else {
						map.put("JJKM", rs.getCell(1, i).getContents());
					}
					if (i != 1 && c == 0) {
						map.put("XMBH",rs.getCell(2, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的项目不存在)</font>");
					} else {
						map.put("XMBH", rs.getCell(2, i).getContents());
					}
					map.put("NCYE", rs.getCell(3, i).getContents());
					list.add(map);
					}
				}else 
				//模板4
				if(rs.getCell(1, 1).getContents().equals("部门名称") && rs.getCell(1, 1).getContents().equals("项目名称")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql2 = "SELECT COUNT(*) FROM GX_SYS_DWB T WHERE T.MC='"+ rs.getCell(1, i).getContents() + "'";
					int b = Integer.parseInt(db.queryForSingleValue(sql2));// 判断填写单位是否存在
					String sql3 = "SELECT COUNT(*) FROM CW_XMJBXXB T WHERE T.XMMC='"+ rs.getCell(2, i).getContents() + "'";
					int c = Integer.parseInt(db.queryForSingleValue(sql3));// 判断项目是否存在
					if (i != 1 && b == 0) {
						map.put("BMBH",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的部门不存在)</font>");
					} else {
						map.put("BMBH", rs.getCell(1, i).getContents());
					}
					if (i != 1 && c == 0) {
						map.put("XMBH",rs.getCell(2, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的项目不存在)</font>");
					} else {
						map.put("XMBH", rs.getCell(2, i).getContents());
					}
					map.put("NCYE", rs.getCell(3, i).getContents());
					list.add(map);
					}
				}else 
				//模板5
				if(rs.getCell(1, 1).getContents().equals("经济科目")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql = "SELECT COUNT(*) FROM CW_JJKMB T WHERE T.KMMC='"+ rs.getCell(1, i).getContents() + "'";
					int a = Integer.parseInt(db.queryForSingleValue(sql));// 判断经济科目是否存在
					if (i != 1 && a == 0) {
						map.put("JJKM",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的经济科目不存在)</font>");
					} else {
						map.put("JJKM", rs.getCell(1, i).getContents());
					}
					map.put("NCYE", rs.getCell(2, i).getContents());
					list.add(map);
					}
				}else 
				//模板6
				if(rs.getCell(1, 1).getContents().equals("部门名称")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql2 = "SELECT COUNT(*) FROM GX_SYS_DWB T WHERE T.MC='"+ rs.getCell(1, i).getContents() + "'";
					int b = Integer.parseInt(db.queryForSingleValue(sql2));// 判断填写单位是否存在
					if (i != 1 && b == 0) {
						map.put("BMBH",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的部门不存在)</font>");
					} else {
						map.put("BMBH", rs.getCell(1, i).getContents());
					}
					map.put("NCYE", rs.getCell(2, i).getContents());
					list.add(map);
					}
				}else 
				//模板7
				if(rs.getCell(1, 1).getContents().equals("项目名称")){
					for (int i = 1; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String sql3 = "SELECT COUNT(*) FROM CW_XMJBXXB T WHERE T.XMMC='"+ rs.getCell(1, i).getContents() + "'";
					int c = Integer.parseInt(db.queryForSingleValue(sql3));// 判断项目是否存在
					if (i != 1 && c == 0) {
						map.put("XMBH",rs.getCell(1, i).getContents()+ "&nbsp&nbsp&nbsp<font color='red'>(填写的项目不存在)</font>");
					} else {
						map.put("XMBH", rs.getCell(1, i).getContents());
					}
					map.put("NCYE", rs.getCell(2, i).getContents());
					list.add(map);
					}
				}
//				list.add(map);
//			}
			rwb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	//数据导入
	public String doInsert(String file, String sszt, String kmyebh, String kmmc)
			throws Exception {
		Workbook rwb;
		String info = "";// 错误或者成功信息保存在此
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int rows = rs.getRows();// 得到所有的行
			int sucessrows = rows;
			String result = new String();
			int j = 1;
			// 错误信息储存list
			LinkedList<String> wz_list = new LinkedList<String>();
			List<String> stuList = new ArrayList<String>();
				//模板一
				if(rs.getCell(1, 1).getContents().equals("经济科目") && rs.getCell(1, 2).getContents().equals("部门名称") && rs.getCell(1, 3).getContents().equals("项目名称")){
					for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
						Map<String, Object> map = new HashMap<String, Object>();
					String jjkm = db.queryForSingleValue("select guid from CW_JJKMB where kmmc='"+ rs.getCell(1, i).getContents() + "'");
					map.put("JJKM", jjkm);//经济科目
					String bmbh = db.queryForSingleValue("select dwbh from GX_SYS_DWB where mc='"+ rs.getCell(2, i).getContents() + "'");
					map.put("BMBH", bmbh);//部门编号
					String xmbh = db.queryForSingleValue("select guid from CW_XMJBXXB where cmmc='"+ rs.getCell(3, i).getContents() + "'");
					map.put("XMBH", xmbh);//项目编号
					map.put("NCYE", rs.getCell(4, i).getContents());//年次余额
					map.put("KMYEBH", kmyebh );//科目余额编号
					map.put("KMBH", kmyebh );//科目编号
					map.put("KMMC", kmmc );//科目名称
					map.put("SSZT", sszt );
					StringBuffer sql = new StringBuffer();
					sql.append("INSERT INTO CW_KMYEMXB(GUID,JJKM,BMBH,XMBH,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
					sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
					sql.append("'" + map.get("JJKM") + "',");
					sql.append("'" + map.get("BMBH") + "',");
					sql.append("'" + map.get("XMBH") + "',");
					sql.append("'" + map.get("NCYE") + "',");
					sql.append("'" + map.get("KMYEBH") + "',");
					sql.append("'" + map.get("KMBH") + "',");
					sql.append("'" + map.get("KMMC") + "',");
					sql.append("'" + map.get("SSZT") + "'");
					sql.append(")");
					stuList.add(sql.toString());
					}
				}else 
					//模板2
					if(rs.getCell(1,1).getContents().equals("经济科目") && rs.getCell(1, 2).getContents().equals("部门名称")){
						for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
							Map<String, Object> map = new HashMap<String, Object>();
						String jjkm = db.queryForSingleValue("select guid from CW_JJKMB where kmmc='"+ rs.getCell(1, i).getContents() + "'");
						map.put("JJKM", jjkm);//经济科目
						String bmbh = db.queryForSingleValue("select dwbh from GX_SYS_DWB where mc='"+ rs.getCell(2, i).getContents() + "'");
						map.put("BMBH", bmbh);//部门编号
						map.put("NCYE", rs.getCell(3, i).getContents());//年次余额
						map.put("KMYEBH", kmyebh );//科目余额编号
						map.put("KMBH", kmyebh );//科目编号
						map.put("KMMC", kmmc );//科目名称
						map.put("SSZT", sszt );
						StringBuffer sql = new StringBuffer();
						sql.append("INSERT INTO CW_KMYEMXB(GUID,JJKM,BMBH,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
						sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
						sql.append("'" + map.get("JJKM") + "',");
						sql.append("'" + map.get("BMBH") + "',");
						sql.append("'" + map.get("NCYE") + "',");
						sql.append("'" + map.get("KMYEBH") + "',");
						sql.append("'" + map.get("KMBH") + "',");
						sql.append("'" + map.get("KMMC") + "',");
						sql.append("'" + map.get("SSZT") + "'");
						sql.append(")");
						stuList.add(sql.toString());
						}
					}else 
						//模板3
						if(rs.getCell(1, 1).getContents().equals("经济科目") && rs.getCell(1, 2).getContents().equals("项目名称")){
							for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
								Map<String, Object> map = new HashMap<String, Object>();
							String jjkm = db.queryForSingleValue("select guid from CW_JJKMB where kmmc='"+ rs.getCell(1, i).getContents() + "'");
							map.put("JJKM", jjkm);//经济科目
							String xmbh = db.queryForSingleValue("select guid from CW_XMJBXXB where cmmc='"+ rs.getCell(2, i).getContents() + "'");
							map.put("XMBH", xmbh);//项目编号
							map.put("NCYE", rs.getCell(3, i).getContents());//年次余额
							map.put("KMYEBH", kmyebh );//科目余额编号
							map.put("KMBH", kmyebh );//科目编号
							map.put("KMMC", kmmc );//科目名称
							map.put("SSZT", sszt );
							StringBuffer sql = new StringBuffer();
							sql.append("INSERT INTO CW_KMYEMXB(GUID,JJKM,XMBH,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
							sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
							sql.append("'" + map.get("JJKM") + "',");
							sql.append("'" + map.get("XMBH") + "',");
							sql.append("'" + map.get("NCYE") + "',");
							sql.append("'" + map.get("KMYEBH") + "',");
							sql.append("'" + map.get("KMBH") + "',");
							sql.append("'" + map.get("KMMC") + "',");
							sql.append("'" + map.get("SSZT") + "'");
							sql.append(")");
							stuList.add(sql.toString());
							}
						}else 
							//模板4
							if(rs.getCell(1, 1).getContents().equals("部门名称") && rs.getCell(1, 2).getContents().equals("项目名称")){
								for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
									Map<String, Object> map = new HashMap<String, Object>();
								String bmbh = db.queryForSingleValue("select dwbh from GX_SYS_DWB where mc='"+ rs.getCell(1, i).getContents() + "'");
								map.put("BMBH", bmbh);//部门编号
								String xmbh = db.queryForSingleValue("select guid from CW_XMJBXXB where cmmc='"+ rs.getCell(2, i).getContents() + "'");
								map.put("XMBH", xmbh);//项目编号
								map.put("NCYE", rs.getCell(3, i).getContents());//年次余额
								map.put("KMYEBH", kmyebh );//科目余额编号
								map.put("KMBH", kmyebh );//科目编号
								map.put("KMMC", kmmc );//科目名称
								map.put("SSZT", sszt );
								StringBuffer sql = new StringBuffer();
								sql.append("INSERT INTO CW_KMYEMXB(GUID,BMBH,XMBH,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
								sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
								sql.append("'" + map.get("BMBH") + "',");
								sql.append("'" + map.get("XMBH") + "',");
								sql.append("'" + map.get("NCYE") + "',");
								sql.append("'" + map.get("KMYEBH") + "',");
								sql.append("'" + map.get("KMBH") + "',");
								sql.append("'" + map.get("KMMC") + "',");
								sql.append("'" + map.get("SSZT") + "'");
								sql.append(")");
								stuList.add(sql.toString());
								}
							}else 
								//模板5
								if(rs.getCell(1, 1).getContents().equals("经济科目")){
									for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
										Map<String, Object> map = new HashMap<String, Object>();
									String jjkm = db.queryForSingleValue("select guid from CW_JJKMB where kmmc='"+ rs.getCell(1, i).getContents() + "'");
									map.put("JJKM", jjkm);//经济科目
									map.put("NCYE", rs.getCell(8, i).getContents());//年次余额
									map.put("KMYEBH", kmyebh );//科目余额编号
									map.put("KMBH", kmyebh );//科目编号
									map.put("KMMC", kmmc );//科目名称
									map.put("SSZT", sszt );
									StringBuffer sql = new StringBuffer();
									sql.append("INSERT INTO CW_KMYEMXB(GUID,JJKM,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
									sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
									sql.append("'" + map.get("JJKM") + "',");
									sql.append("'" + map.get("NCYE") + "',");
									sql.append("'" + map.get("KMYEBH") + "',");
									sql.append("'" + map.get("KMBH") + "',");
									sql.append("'" + map.get("KMMC") + "',");
									sql.append("'" + map.get("SSZT") + "'");
									sql.append(")");
									stuList.add(sql.toString());
									}
								}else if(rs.getCell(1, 1).getContents().equals("部门名称")){//模板6
										for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
											Map<String, Object> map = new HashMap<String, Object>();
										String bmbh = db.queryForSingleValue("select dwbh from GX_SYS_DWB where mc='"+ rs.getCell(1, i).getContents() + "'");
										map.put("BMBH", bmbh);//部门编号
										map.put("NCYE", rs.getCell(2, i).getContents());//年次余额
										map.put("KMYEBH", kmyebh );//科目余额编号
										map.put("KMBH", kmyebh );//科目编号
										map.put("KMMC", kmmc );//科目名称
										map.put("SSZT", sszt );
										StringBuffer sql = new StringBuffer();
										sql.append("INSERT INTO CW_KMYEMXB(GUID,BMBH,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
										sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
										sql.append("'" + map.get("BMBH") + "',");
										sql.append("'" + map.get("NCYE") + "',");
										sql.append("'" + map.get("KMYEBH") + "',");
										sql.append("'" + map.get("KMBH") + "',");
										sql.append("'" + map.get("KMMC") + "',");
										sql.append("'" + map.get("SSZT") + "'");
										sql.append(")");
										stuList.add(sql.toString());
										}
									}else 
										//模板7
										if( rs.getCell(1, 1).getContents().equals("项目名称")){
											for (int i = 2; i < rows; i++) {// 第一行是列名,不需要导入数据库;获得的所有行比实际数据多一行。
												Map<String, Object> map = new HashMap<String, Object>();
											String xmbh = db.queryForSingleValue("select guid from CW_XMJBXXB where cmmc='"+ rs.getCell(1, i).getContents() + "'");
											map.put("XMBH", xmbh);//项目编号
											map.put("NCYE", rs.getCell(2, i).getContents());//年次余额
											map.put("KMYEBH", kmyebh );//科目余额编号
											map.put("KMBH", kmyebh );//科目编号
											map.put("KMMC", kmmc );//科目名称
											map.put("SSZT", sszt );
											StringBuffer sql = new StringBuffer();
											sql.append("INSERT INTO CW_KMYEMXB(GUID,XMBH,NCYE,KMYEBH,KMBH,KMMC,SSZT) values(");
											sql.append("'"+ UUID.randomUUID().toString().trim().toUpperCase().replaceAll("-", "") + "',");
											sql.append("'" + map.get("XMBH") + "',");
											sql.append("'" + map.get("NCYE") + "',");
											sql.append("'" + map.get("KMYEBH") + "',");
											sql.append("'" + map.get("KMBH") + "',");
											sql.append("'" + map.get("KMMC") + "',");
											sql.append("'" + map.get("SSZT") + "'");
											sql.append(")");
											stuList.add(sql.toString());
											}
				
			}
			try {
				db.batchUpdate(stuList);
				System.err.println(stuList+"*********************");
				if (wz_list.size() > 0) {
					info = "导入成功" + (sucessrows - 1) + "行！";
					info += "第";
					for (int i = 0, len = wz_list.size(); i < len; i++) {
						if (i == 0) {
							info += wz_list.get(i);
						} else {
							info += "、" + wz_list.get(i);
						}
						// 控制错误信息的数量,不能让他数量爆炸的全部输出
						if (i > 20) {
							break;
						}
					}
					info += "行数据导入失败！<br/>";
				} else {
					info = "导入成功" + (sucessrows - 2) + "行！";
				}
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				info = "数据库操作失败！";
				logger.error("数据库操作失败\n" + sqle);
			}
			rwb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	// 食堂人员,获取树上传下的所有食堂名称
	public String getSyst(String treeSql) {
		String sql = "select stmc from hq_stxxb where ssxxid in (" + treeSql
				+ ")";
		List list = db.queryForList(sql);
		Map map = db.queryForMap(sql);
		list.toString();
		map.toString();
		return map.toString();
	}
	//导出 wzd
	public List<Map<String, Object>> getList(String searchValue, String nd,String zkmsx) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select rownum as xh,  nd, kmsx as zkmsx, decode(nvl(kmzye, ''),  0, '0.00', (to_char(round(kmzye, 2), 'fm99999999999990.00'))) kmzye, ");
		sql.append("  guid, sfmj,(select kmbh from cw_kjkmszb kj where kj.kmbh = k.kmbh) as bhkm, ");
		sql.append("   kmbh, kmmc, (case (select yefx from cw_kjkmszb kj where kj.kmbh = k.kmbh) when '1' then '贷方'else '借方'  end) yefx,sffz  from CW_KMYEB K  where 1 = 1 ");
		if(Validate.noNull(zkmsx)) {
			sql.append(" and kmsx='"+zkmsx+"'");
		}
		if(Validate.noNull(nd)){
			sql.append(" and nd='"+nd+"' order by kmbh");
		}
	return db.queryForList(sql.toString());
	}
}
