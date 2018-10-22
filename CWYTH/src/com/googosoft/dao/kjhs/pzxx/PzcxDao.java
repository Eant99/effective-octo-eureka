package com.googosoft.dao.kjhs.pzxx;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.fzgn.jcsz.DwbDao;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("pzcxDao")
public class PzcxDao  extends BaseDao{
	private Logger logger = Logger.getLogger(DwbDao.class);
	
	public int doDeal(PageData pd,String type,String guid,HttpSession session) {
		String sszt = Constant.getztid(session);
		String newpzbh = CommonUtil.getNewcxFlownum("Cw_pzlrzb", "PZBH", 4, pd.getString("pzz"));
		ArrayList<String> sqlList = new ArrayList<String>();
		String sql = " insert into Cw_pzlrzb(guid,pzz,pzlxmc,pzbh,pzrq,fjzs,zdr,fhr,fhrq,jzr,jzrq,cn,cnrq,jfjehj,dfjehj,pzzt,fhyj,czr,Czrq,kjqj,pzly,sszt,sfcx,cxid,pznd,kjzd)"
				+ " select '"+guid+"',z.pzz,z.pzlxmc,'"+newpzbh+"',"
				+ " to_date((select j.ztnd||'-'||j.kjqj||'-'||extract(day from sysdate) from cw_qmjzb j where j.sfjz='0' and sszt='"+sszt+"' and rownum =1 ),'yyyy-mm-dd'),"
				+ "z.fjzs,"
				+ " z.zdr,z.fhr,z.fhrq,z.jzr,z.jzrq,z.cn,z.cnrq,-1*z.JFJEHJ,-1*z.DFJEHJ,"
				+ " '00' as pzzt,z.FHYJ,z.CZR,z.CZRQ,z.KJQJ,z.PZLY,z.SSZT,'1','"+pd.getString("guid")+"', z.pznd,z.kjzd from Cw_pzlrzb z "
				+ " where z.guid='"+pd.getString("guid")+"'";
		sqlList.add(sql);
//		String sql2 = " insert into CW_PZLRMXB (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sfjz,czr,czrq,sszt,kjzd)"
//				+ " select sys_guid(),'"+guid+"',"+"'冲销'||"+"z.ZY,z.KMBH,z.JJFL,z.BMBH,"
//				+ " z.XMBH,z.JDFX,-1*z.JFJE,-1*z.DFJE,z.SFJZ,z.CZR,z.CZRQ,z.sszt,z.kjzd from CW_PZLRMXB z "
//				+ " where z.pzbh='"+pd.getString("guid")+"' ";
		String search = "select * from CW_PZLRMXB where pzbh='"+pd.getString("guid")+"'";
		List list = db.queryForList(search);
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			String mxid = Validate.isNullToDefaultString(map.get("GUID"), "");
			String newMxid = GenAutoKey.get32UUID();
			String sql2 = " insert into CW_PZLRMXB (guid,pzbh,zy,kmbh,jjfl,bmbh,xmbh,jdfx,jfje,dfje,sfjz,czr,czrq,sszt,kjzd,xmguid)"
			+ " select '"+newMxid+"','"+guid+"',"+"'冲销'||"+"z.ZY,z.KMBH,z.JJFL,z.BMBH,"
			+ " z.XMBH,z.JDFX,-1*z.JFJE,-1*z.DFJE,z.SFJZ,z.CZR,z.CZRQ,z.sszt,z.kjzd,xmguid from CW_PZLRMXB z "
			+ " where guid='"+mxid+"'";
			sqlList.add(sql2);
			//复制凭证展示表 
			StringBuffer insertZs = new StringBuffer();
			insertZs.append(" INSERT INTO CW_PZZSB(GUID,KMMC,ZCJJFLKM,KMYE,BMMC,XMMC,SRKM,ZCKM,XMYE,FZR,XMGKXXM,XMLX,BZ)");
			insertZs.append(" SELECT '"+newMxid+"',KMMC,ZCJJFLKM,KMYE,BMMC,XMMC,SRKM,ZCKM,XMYE,FZR,XMGKXXM,XMLX,BZ FROM CW_PZZSB WHERE GUID='"+mxid+"'");
			sqlList.add(insertZs.toString());
			StringBuffer insertFz = new StringBuffer();
			insertFz.append(" INSERT INTO CW_FZLRB(GUID,KMBH,WLDC,ZRR,WLDW,JSFS,JSDH,DFDW,YSLX,YSLY,BZ,CZR,CZRQ,DQSJ,gnkm,khyh,hm,zh)");
			insertFz.append(" SELECT SYS_GUID(),'"+newMxid+"',WLDC,ZRR,WLDW,JSFS,jsdh,DFDW,YSLX,YSLY,BZ,czr,SYSDATE,DQSJ,gnkm,khyh,hm,zh FROM CW_FZLRB WHERE KMBH='"+mxid+"'");
			sqlList.add(insertFz.toString());
		}
		
		String sql3 = "delete from Cw_pzlrzb where guid = '"+pd.getString("guid")+"'";
		
		int i = 0;
		try {  
			if("cx".equals(type)){
//				i = db.update(sql);
//				i = db.update(sql2);
				if(db.batchUpdate(sqlList)){
					i = 1;
				}else{
					i=0;
				}
			}else if("qxcx".equals(type)){
				i = db.update(sql3);
			}
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public String checkIsCx(String guid) {
		String i = "";
		String sql = " select count(1) from Cw_pzlrmxb m where m.pzbh = '"+guid+"' and m.zy like '冲销%' ";
		i = db.queryForSingleValue(sql);
		return i;
	}
	
	/**
	 * 判断是否已经冲销
	 * @param guid
	 * @return
	 */
	public String checkIsOrNoCx(String guid) {
		String i = "";
		String sql = " select count(1) from CW_PZLRZB t  where t.cxid = '"+guid+"' and t.sfcx='1' ";
		i = db.queryForSingleValue(sql);
		return i;
	}
	/**
	 * 取消冲销
	 * @param guid
	 * @return
	 */
	public String checkIsQxCx(String guid) {
		String i = "";
		String sql = " select count(1) from Cw_pzlrzb z left join cw_pzlrmxb m on m.pzbh = z.guid "
				+ "where z.guid = '"+guid+"' and m.zy like '冲销%' ";
		i = db.queryForSingleValue(sql);
		return i;
	}
	public  List getPrintInfoByIds(PageData pd){
		String guids = pd.getString("guid");
		String sql = " select t.jzr,(select r.xm from gx_sys_ryb r where r.guid=t.jzr)jzrmc, t.zdr,(select r.xm from gx_sys_ryb r where r.guid=t.zdr)zdrmc,"
				+ " t.fhr,(select r.xm from gx_sys_ryb r where r.guid=t.fhr)fhrmc,t.guid,m.zy,m.kmbh,(select b.kmmc from cw_jjkmb b where b.kmbh=m.kmbh)kmmc,(select (to_char(b.jfjehj，'FM999999999990.00')） from CW_PZLRZB b where b.guid in ("+guids+")) as jfjehj,(select (to_char(b.dfjehj，'FM999999999990.00')） from CW_PZLRZB b where b.guid in ("+guids+")) as dfjehj from CW_PZLRZB t"
				+ " left join cw_pzlrmxb m on m.pzbh = t.guid where t.guid in ("+guids+")";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  String getdwmcByIds(String pzbh){
		String sql = " select s.MC from CW_PZLRZB t"
				+ " left join cw_pzlrmxb m on m.pzbh = t.guid left join GX_SYS_DWB s on s.dwbh=m.bmbh where t.pzbh in ("+pzbh+")";
		List list = db.queryForList(sql);
		Map temp =(Map)list.get(0);
		String dwmc1 =(String) temp.get("MC");
		return dwmc1;
	}
	public Map getInfo(PageData pd) {
		Map map = db.queryForMap(" select t.jzr,(select r.xm from gx_sys_ryb r where r.guid=t.jzr)jzrmc,"
				+ " t.zdr,(select r.xm from gx_sys_ryb r where r.guid=t.zdr)zdrmc,"
				+ " t.fhr,(select r.xm from gx_sys_ryb r where r.guid=t.fhr)fhrmc from CW_PZLRZB t where t.guid in ("+pd.getString("guid")+") "
				+ " ");
		return map;
	}
	
	/**
	 * 根据主键获取数据字典的详细信息  进行修改操作等
	 * @param dm
	 * @return
	 */
	public Map getObjectById(String dmxh) {
		String sql = " select c.guid, c.kmbh, c.kmmc,  c.kmsx,  c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq from Cw_kjkmszb c where c.guid=? ";
		return db.queryForMap(sql,new Object[]{dmxh});
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

		//System.out.println("==============gnszMenu============"+jb);
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取功能科目字典树下级
	 */
	public  List gnszMenuzj(String jb){
	  //String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_gnkmb b where b.kmbh=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.GNZL+"' union\r\n" + 
			//	"select t.guid as dm,t.czr as mc,t.kmbh as zl,t.sskjkmbh as jb, 0 as count from cw_gnkmb t WHERE T.KMBH='"+jb+"'";
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_gnkmb b where b.kmbh=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.GNZL+"' union\r\n" + 
				"select t.kmbh as dm,t.kmmc as mc,t.sssjkm as zl,t.sskjkmbh as jb, 0 as count from cw_gnkmb t WHERE T.SSSJKM='"+jb+"'";		     
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
	/**
	 * 获取经济科目字典树下级
	 */
	public  List jjszMenuzj(String dm){
		
	  //String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_jjkmb b where b.kmbh=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.JJZL+"' union\r\n" + 
	//			"select t.guid as dm,t.czr as mc,t.kmbh as zl,t.sskjkmbh as jb, 0 as count from cw_jjkmb t WHERE T.KMBH='"+jb+"'";
//		String sql = "select t.bz as l,t.zl as k,t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_jjkmb b where b.kmbh=t.dm) as count,'2' as kmjc from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.JJZL+"' union\r\n" + 
//				"select t.l,t.l,t.guid as dm,t.kmmc as mc,t.kmbh as zl,t.sssjkm as jb, 0 as count,kmjc as kmjc from cw_jjkmb t WHERE T.SSSJKM='"+jb+"'";
		String sql = "select t.guid,t.kmbh,t.kmmc,t.l,t.k,T.KMJC from cw_jjkmb t WHERE T.k='"+dm+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public int getCount(String kmbh){
		String sql = "select count(0) from cw_jjkmb t WHERE T.k='"+kmbh+"'";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), "0"));
		return count;
	}
	/**
	 * 判断zjf是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckZjf(String kmbh){
		String sql = "select count(1) from Cw_kjkmszb where zjf= '"+kmbh+"' ";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断项目编号是否重复
	 * @param kmbh
	 * @return
	 */
	public boolean doCheckXmbh(String kmbh){
		String sql = "select count(1) from Cw_kjkmszb where kmbh= '"+kmbh+"' ";
		String count = db.queryForSingleValue(sql);
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
	public int doAdd(PageData pd,String dm) {
		String sql = "insert into Cw_kjkmszb(guid,kmbh,kmmc,kmsx,zjf,yefx,hslb,kmjc,qyf,sfwyh,sfjjflkm,sfgnflkm,bmhs,xmhs,czr,bz,sjfl,jb,czrq) "
				+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		Object[] obj = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmsx"),
				pd.getString("zjf"),
				pd.getString("yefx"),
				
				pd.getString("hslb"),
				pd.getString("kmjc"),
				pd.getString("qyf"),
				pd.getString("sfwyh"),
				pd.getString("sfjjflkm"),
				
				pd.getString("sfgnflkm"),
				pd.getString("bmhs"),
				pd.getString("xmhs"),
				pd.getString("czr"),
				
				pd.getString("zjf"),
				dm,
				pd.getString("kmbh")
		};
		/*String sql2 = "insert into gx_sys_dmb(dmxh,zl,dm,mc,jb,bz,ms,dmsx,sjqc) values(sys_guid(),?,?,?,?,?,?,?,?)";
		Object[] obj2 = new Object[]{
				114,
				pd.getString("zjf"),
				pd.getString("kmmc"),
				pd.getString("jb"),
				"1",
				"",
				"",
				""
		};*/
		
		/*String sql3 = "insert into cw_kjkmsz(fyfl,kmmc,zl,jb,isleaf) values(?,?,?,?,?)";
		Object[] obj3 = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmmc"),
				"1",
				"1"
		};*/
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
//			i = db.update(sql2, obj2);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/*
	 * 添加下级
	 */
	public int doAddxj(PageData pd) {
		String sql = "insert into Cw_kjkmszb(guid,kmbh,kmmc,kmsx,zjf,yefx,hslb,kmjc,qyf,sfwyh,sfjjflkm,sfgnflkm,bmhs,xmhs,czr,bz,sjfl,jb,czrq) "
				+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		Object[] obj = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmsx"),
				pd.getString("zjf"),
				pd.getString("yefx"),
				
				pd.getString("hslb"),
				pd.getString("kmjc"),
				pd.getString("qyf"),
				pd.getString("sfwyh"),
				pd.getString("sfjjflkm"),
				
				pd.getString("sfgnflkm"),
				pd.getString("bmhs"),
				pd.getString("xmhs"),
				pd.getString("czr"),
				pd.getString("bz"),
				pd.getString("jb"),
				pd.getString("zjf")
		};
		String sql2 = "insert into gx_sys_dmb(dmxh,zl,dm,mc,jb,bz,ms,dmsx,sjqc) values(sys_guid(),?,?,?,?,?,?,?,?)";
		Object[] obj2 = new Object[]{
				114,
				pd.getString("zjf"),
				pd.getString("kmmc"),
				pd.getString("dm"),
				"1",
				"",
				"",
				""
		};
		
		String sql3 = "insert into cw_kjkmsz(fyfl,kmmc,zl,jb,isleaf) values(?,?,?,?,?)";
		Object[] obj3 = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmmc"),
				"1",
				""
		};
		
		int i = 0;
		try {  
			i = db.update(sql, obj);
//			i = db.update(sql2, obj2);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 修改
	 * @param dwb 单位表
	 * @return
	 */
	public int doUpdate(PageData pd,String dwbh){
		String sql = "update Cw_kjkmszb set kmbh=?,kmmc=?,kmsx=?,zjf=?,yefx=?,"
					+ "hslb=?,qyf=?,kmjc=?,bz=?,sfjjflkm=?,"
					+ "sfgnflkm=?,bmhs=?,xmhs=?,sfwyh=? where guid=?";
		Object[] obj = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmsx"),
				pd.getString("zjf"),
				pd.getString("yefx"),
				
				pd.getString("hslb"),
				pd.getString("qyf"),
				pd.getString("kmjc"),
				pd.getString("bz"),
				pd.getString("sfjjflkm"),
				
				pd.getString("sfgnflkm"),
				pd.getString("bmhs"),
				pd.getString("xmhs"),
				pd.getString("sfwyh"),
				dwbh
		};
	    int i = 0;
		try {  
			i = db.update(sql, obj);
			System.err.println("__112_"+i);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 单位信息实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectByIdByKmsz(String guid) {
		String sql = "select guid,kmbh,kmmc,l,k,kmjc,qyf,sm from CW_JJKMB where guid=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{guid});
	}
	/**
	 * 删除
	 * @param guid
	 * @return
	 */
	public int doDelete(String dwbh) {
		String sql = "DELETE Cw_kjkmszb WHERE guid in ('"+dwbh+"')";
		return db.update(sql);
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
	
	public int qxcx(String guid1,String pzbh,String sszt) {
//		String sql5 = "update cw_pzlrzb set "
//				+"pzbh = replace(lpad(pzbh - 1,4),' ','0') where pzbh > ?";
		
		String sql5 = "update cw_pzlrzb set "
				+"pzbh = to_char(pzbh-1,'FM9990999') where sszt = ? and pzbh > ?";
		
	Object[] obj = {
			sszt,
			pzbh
	};
	
	int i = 0;
	try {  
		
			i = db.update(sql5,obj);
		
	} catch (DataAccessException e) {  
		SQLException sqle = (SQLException) e.getCause();  
	    logger.error("数据库操作失败\n" + sqle);  
	    return -1;
	}
	return i;
	}
	
	/**
	 * 查询凭证录入
	 * */
	public Map<String, Object> selectPzlrzb(String pznd,String kjqj,String pzbh,String sszt){
		String sql = "select 	(select mc from gx_sys_dmb where zl = '"+Constant.PZLY+"' and dm = a.pzly)as pzlymc ," + 
				"				(select mc from gx_sys_dmb where zl = '"+Constant.PZZT+"' and dm = a.pzzt) as pzzt," + 
				"				(select xm from gx_sys_ryb where guid = a.zdr) as zdr," + 
				"				(select xm from gx_sys_ryb where guid = a.fhr) as fhr," + 
				"				(select xm from gx_sys_ryb where guid = a.jzr) as jzr," + 
				"				(select xm from gx_sys_ryb where guid = a.cn) as cnr," + 
				"				guid, pzbh,pzrq,fjzs,jfjehj as jfzje,dfjehj as dfzje"
				+ "				from cw_pzlrzb a where a.pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"'";
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> selectPzlrmxAndFzlr(String pznd,String kjqj,String pzbh,String sszt){
		String sql = "select a.pzbh,a.zy,a.kmbh,a.jjfl,a.bmbh,a.xmbh,a.jfje,a.dfje,"
				+ "b.wldc,(select '('||rybh||')'||xm from gx_sys_ryb where rybh = b.zrr) as zrr,"
				+ "(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.wldw) as wldw,"
				+ "b.jsdh,(select '('||wlbh||')'||dwmc from cw_wldwb where wlbh = b.dfdw) as dfdw,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZFFSDM+"' and dm = b.jsfs) as jsfs,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLX+"' and dm = b.yslx) as yslx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.ZCLX+"' and dm = b.zclx) as zclx,"
				+ "(select mc from gx_sys_dmb where zl = '"+Constant.YSLY+"' and dm = b.ysly) as ysly,"
				+ "(select '('||kmbh||')'||kmmc from CW_GNKMB where  KMBH = b.GNKM) as GNKM,"
				+ "c.kmmc,c.zcjjflkm,c.kmye,c.bmmc,c.xmmc,c.srkm,c.zckm,c.xmye,c.fzr,c.xmgkxxm,c.xmlx,c.bz "
				+ " from cw_pzlrmxb a left join cw_fzlrb b on a.guid = b.kmbh left join cw_pzzsb c on a.guid = c.guid"
				+ " where a.pzbh = (select guid from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' )";
		return db.queryForList(sql);
	}
	
	//获取凭证编号列表
		public List<String> getPzbhList(String pznd,String kjqj,String sszt){
		//	String sql = "select pzbh from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"' order by pzbh asc";
			String sql = "select pzbh from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' order by pzbh asc";
			return db.queryForList(sql,String.class);
		}
		//获取凭证字列表
		public List<Map<String, Object>> getPzzList(String sszt){
			String sql = "select lxbh,pzz from cw_pzlxb where sszt = '"+sszt+"' order by lxbh";
			return db.queryForList(sql);
		}
		//获取账套会计期间
		public Map<String, Object> getZtXx(String ztid){
			String sql = "select kjnd,qjs from cw_ztxxb where guid = '"+ztid+"'";
			return db.queryForMap(sql);
		}
		
		//查询复核人
		public Object getFhr(String pzbh,String pznd,String kjqj,String sszt){
		//	String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and pzz = '"+pzz+"' and sszt = '"+sszt+"'";
			String sql = "select fhr from cw_pzlrzb where pzbh = '"+pzbh+"' and sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' ";
			return db.queryForObject(sql, String.class);
		}
		//查询借方必有,借方必无，
		public Map<String, Object> getBybwkm(String pzz,String sszt) {
			String sql = "select distinct \r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Jf') as jfbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Df') as dfbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzby') as pzbykm,\r\n" + 
					"(select wm_concat(a.kmbh) from cw_kjkmszb a join cw_pzkmdzb b on a.guid = b.kmbh where b.xzlx = 'Pzbw') as pzbwkm\r\n" + 
					"from cw_pzlxb t join cw_pzkmdzb s on t.guid = s.pzlxm where t.lxbh = '"+pzz+"' and t.sszt = '"+sszt+"'";
			return db.queryForMap(sql);
		}
		
		//获取最大的凭证编号
		public String getMaxPzbh(String pznd,String kjqj,String sszt) {
		//	String sql = "select max(pzbh) from cw_pzlrzb where pzz = '"+pzz+"' and sszt = '"+sszt+"'";
			String sql = "select max(pzbh) from cw_pzlrzb where sszt = '"+sszt+"' and pznd = '"+pznd+"' and kjqj = '"+kjqj+"' ";
			return db.queryForSingleValue(sql);
		}
		
		/**
		 * 获取交互数据
		 * */
		public Map<String, Object> getKjkmInfo(String kmbh){
			String sql = "select a.kmmc,a.yefx,a.sfjjflkm,a.bmhs,a.xmhs,b.kmzye as kmye from cw_kjkmszb a left join cw_kmyeb b "
					+ " on a.kmbh = b.kmbh where a.kmbh = '"+kmbh+"' and a.sfmj = '1'";
			return db.queryForMap(sql);
		}
		public Map<String, Object> getJjkmInfo(String kmbh){
			String sql = "select a.kmmc as zcjjflkm from cw_jjkmb a where a.kmbh = '"+kmbh+"' ";
			return db.queryForMap(sql);
		}
		public Map<String, Object> getBmInfo(String bmbh){
			String sql = "select a.mc as bmmc from gx_sys_dwb a where a.dwbh = '"+bmbh+"'";
			return db.queryForMap(sql);
		}
		public Map<String, Object> getXmInfo(String xmbh,String bmbh){
			String strWhere = "";
			if(! Validate.isNullOrEmpty(bmbh)) {
				strWhere += " and a.bmbh = '"+bmbh+"'";
			}
			StringBuffer sql = new StringBuffer();
			sql.append(" select a.xmmc,");
			sql.append(" wm_concat(distinct to_char(k1.kmmc)) as srkmmc,");
			sql.append(" wm_concat(distinct to_char(k1.kmmc)) as srkm,");
			sql.append(" wm_concat(distinct to_char(k2.kmmc)) as zckmmc,");
			sql.append(" wm_concat(distinct to_char(k2.kmmc)) as zckm,");
			sql.append(" a.syje,r.xm as fzr,");
			sql.append(" DECODE(NVL(A.SYJE,0),0,'0.00',TO_CHAR(ROUND(A.SYJE,2),'FM99999999990.00'))AS XMYE,");
			sql.append(" r.xm as fzrxm,a.gkxxm as xmgkxxm,");
			sql.append(" a.gkxxm,b.bz,b.xmlxmc as xmlx,");
			sql.append(" b.xmlxmc");
			sql.append(" from cw_xmjbxxb a");
			sql.append(" left join cw_xmlxb b  on a.xmlb = b.xmlxbh");
			sql.append(" left join cw_xmsrb c  on b.guid = c.xmlxbh");
			sql.append(" left join cw_xmzcb d on b.guid = d.xmlxbh");
			sql.append(" left join cw_kjkmszb k1 on k1.kmbh=c.srkmbh");
			sql.append(" left join cw_kjkmszb k2 on k2.kmbh=d.zckmbh");
			sql.append(" left join gx_sys_ryb r on r.rybh=a.fzr");
			sql.append(" where a.xmbh = '"+xmbh+"' ");
			sql.append(strWhere);
			sql.append(" group by a.xmmc,a.syje,a.gkxxm,b.xmlxmc,r.xm,b.bz");
			return db.queryForMap(sql.toString());
		}
		
		public String pzbhwfh(String guid) {
			String sql = "select min(pzbh) from cw_pzlrzb where pzzt = '00'";
			return db.queryForSingleValue(sql);
		}

		//获取期末结账会计期间
		public Map<String, Object> getKjqj(String ztid){
			String sql = "select * from (select * from cw_qmjzb where sszt= '"+ztid+"' and sfjz = '0' order by ztnd,to_number(kjqj)) where rownum <= 1";
			return db.queryForMap(sql);
		}
}
