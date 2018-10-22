package com.googosoft.dao.kjhs;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.activiti.bpmn.model.BooleanDataObject;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.fzgn.jcsz.DwbDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.pojo.kjhs.KMSZ;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

import oracle.net.aso.e;
@Repository("kjkmszDao")
public class KjkmszDao  extends BaseDao{
	private Logger logger = Logger.getLogger(DwbDao.class);
	
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
	public Map getinfoById(String dmxh) {
		String sql = " select c.guid, c.kmbh,c.kmmc, c.kmsx,(select d.mc from gx_sys_dmb d where d.zl = 'kmsx' and d.dm = c.kmsx) as kmsxmc,"
				+ " c.zjf, c.yefx, (case c.yefx when '1' then '贷方' else '借方' end) as yefxmc, c.kmjc,c.qyf, (case c.qyf when '1' then '是' else '否' end) as qyfmc,"
				+ " c.sssjkm, c.jb,c.czr, c.czrq from Cw_Gnkmb c where 1 = 1 and c.guid=? ";
		return db.queryForMap(sql,new Object[]{dmxh});
	}
	public Map getObjectById(String dmxh) {
		String sql = " select c.guid, c.kmbh, c.kmmc,c.kjzd,  c.kmsx,  c.zjf, c.yefx,(case c.yefx when '1' then '贷方' else '借方'end) as yefxmc, c.hslb,c.bz,"
				+ " (select d.mc from gx_sys_dmb d where d.zl='hslb' and d.dm = c.hslb) as hslbmc, c.kmjc, c.qyf,(case c.qyf when '1' then '是' else '否'end) as qyfmc,"
				+ " c.sfwyh, (case c.sfwyh when '1' then '是' else '否'end) as sfwyhmc,c.sfjjflkm,(case c.sfjjflkm when '1' then '是' else '否'end) as sfjjflkmmc,"
				+ " c.sfgnflkm,(case c.sfgnflkm when '1' then '是' else '否'end) as sfgnflkmmc, c.bmhs,(case c.bmhs when '1' then '是' else '否'end) as bmhsmc, c.xmhs,"
				+ " (case c.xmhs when '1' then '是' else '否'end) as xmhsmc, c.czr, c.czrq,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=c.dfdw) dfdw,c.wldwfz,c.grfz from Cw_kjkmszb c where c.guid=? ";
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
		String sql = "select count(1) from Cw_gnkmb where zjf= '"+kmbh+"' ";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断zjf是否重复  功能科目设置修改
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckZjf2(String kmbh,String guid){
		String sql = "select count(1) from Cw_gnkmb where zjf= '"+kmbh+"' and guid != '"+guid+"' ";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断科目名称是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckKmmc(String kmmc){
		String sql = "select count(1) from Cw_gnkmb where kmmc= '"+kmmc+"' ";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断科目名称是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckKmmc1(String kmmc,String kjzd ){
		String sql = "select count(1) from Cw_kjkmszb where kmmc= '"+kmmc+"' and kjzd='"+kjzd+"' ";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断科目名称是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckKmmc2(String kmmc,String kjzd,String guid ){
		String sql = "select count(1) from Cw_kjkmszb where kmmc= '"+kmmc+"' and kjzd='"+kjzd+"' and guid !='"+guid+"'";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断科目名称是否重复    功能科目设置修改
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckKmmc2(String kmmc,String guid){
		String sql = "select count(1) from Cw_gnkmb where kmmc= '"+kmmc+"' and guid != '"+guid+"' ";
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
		String sql = "select count(1) from Cw_gnkmb where kmbh= '"+kmbh+"' ";
		System.err.println("kmbh="+kmbh);
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 判断项目编号是否重复    功能科目设置修改
	 * @param kmbh
	 * @return
	 */
	public boolean doCheckXmbh2(String kmbh,String guid ){
		String sql = "select count(1) from Cw_gnkmb where kmbh= '"+kmbh+"' and guid !='"+guid+"' ";
		System.err.println("kmbh="+kmbh);
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 科目余额初始 判断zjf是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckkjZjf2(String zjf,String kjzd){
		String sql = "select count(1) from Cw_kjkmszb where zjf= '"+zjf+"' and kjzd='"+kjzd+"' ";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	/**
	 * 科目余额初始 判断zjf是否重复     修改
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckZjf3(String kmbh,String guid,String kjzd){
		String sql = "select count(1) from Cw_kjkmszb where zjf= '"+kmbh+"' and guid != '"+guid+"' and kjzd='"+kjzd+"'";
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	public String scbh(String kmbh,String kjzd) {
	    String bhsql = "select '"+kmbh.substring(0,kmbh.length()-2)+"'||to_char(max(substr(kmbh, -2))+1,'FM909' ) as kmbh from cw_kjkmszb where kmbh like '"+kmbh.substring(0,kmbh.length()-2)+"%' and kjzd='"+kjzd+"' and kmbh != '"+kmbh.substring(0,kmbh.length()-2)+"'";
		
		String scbh = db.queryForSingleValue(bhsql);
		return scbh;
		
	}
	/**
	 * 增加
	 * @param dwb
	 * @return
	 */
	public boolean doAdd(PageData pd,String dm,HttpSession session) {
		
		//生成编号
//		String bhsql = "select '"+pd.getString("kmbh2").substring(0,pd.getString("kmbh2").length()-2)+"'||to_char(max(substr(kmbh, -2))+1,'FM909' ) as kmbh from cw_kjkmszb where kmbh like '"+pd.getString("kmbh2").substring(0,pd.getString("kmbh2").length()-2)+"%' and kjzd='"+pd.getString("kjzd")+"' and kmbh != '"+pd.getString("kmbh2").substring(0,pd.getString("kmbh2").length()-2)+"'";
//		
//		String kmbh = db.queryForSingleValue(bhsql);
		//String kmbh="123";
		String sszt = Constant.getztid(session);
		///String kjzd = CommonUtil.getKjzd(session);//获取使用的会计制度
		String guid = GenAutoKey.get32UUID();
		String sql3 = "insert into cw_ywjlb values(sys_guid(),sysdate,?,?,?,?,?,?,?)";
		Object[] obj3 = new Object[]{
			pd.getString("mkbh"),
			pd.getString("kmbh"),
			pd.getString("kmmc"),
			"增加科目编号为"+pd.getString("kmbh")+"，科目名称为"+pd.getString("kmmc")+"的科目",
			pd.getString("kjzd"),
			Constant.getztid(session),
			LUser.getGuid()
		};
		System.err.println("____________________________________________________________________________");
		String sql = "insert into Cw_kjkmszb(guid,kmbh,kmmc,kmsx,zjf,yefx,hslb,kmjc,qyf,sfwyh,sfjjflkm,sfgnflkm,bmhs,xmhs,czr,bz,sjfl,jb,czrq,sfmj,kmnd,sszt,kjzd,DFDW,WLDWFZ,GRFZ) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?,?,?,?,?)";
		Object[] obj = new Object[]{
				guid,
				//pd.getString("kmbh"),
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
				dm,
				pd.getString("kmbh"),
				pd.getString("sfmj"),
				sszt,
				pd.getString("kjzd"),
				CommonUtil.getBeginText(pd.getString("dfdw")),
				pd.getString("wldwfz"),
				pd.getString("grfz")
		};
		String lsguid = GenAutoKey.get32UUID();
		
		String lssql = "insert into Cw_kjkmszb_ls(guid,kmbh,kmmc,kmsx,zjf,yefx,hslb,kmjc,qyf,sfwyh,sfjjflkm,sfgnflkm,bmhs,xmhs,czr,bz,sjfl,jb,czrq,sfmj,kmnd,sszt,kjzd) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?,?)";
		Object[] lsobj = new Object[]{
				lsguid,
				//pd.getString("kmbh"),
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
				dm,
				pd.getString("kmbh"),
				pd.getString("sfmj"),
				sszt,
				pd.getString("kjzd")
		};
		String sfjjflkm = pd.getString("sfjjflkm");
		String bmhs = pd.getString("bmhs");
		String xmhs = pd.getString("xmhs");
		int sffz=0;
		if("1".equals(sfjjflkm)||"1".equals(bmhs)||"1".equals(xmhs)) {
			sffz=1;
		}
		// Calendar date = Calendar.getInstance();
			// String jn = String.valueOf(date.get(Calendar.YEAR));
	//	     int jn=Integer.valueOf(date.get(Calendar.YEAR));//今年
		String sql2="insert into cw_kmyeb(guid,kmbh,kmmc,sfmj,sffz,nd,sszt,kmsx,kmid,kjzd) values(sys_guid(),?,?,?,?,to_char(sysdate,'yyyy'),?,?,?,?)";
		Object[] obj1 = new Object[]{
			//	pd.getString("kmbh"),
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("sfmj"),
				sffz,
				sszt,
				pd.getString("kmsx"),
				guid,
				pd.getString("kjzd")
		};
		ArrayList list = new ArrayList<>();
		list.add(sql);
		list.add(sql2);
		list.add(sql3);
		list.add(lssql);
		boolean a = false;
		try {  
			
			a=db.batchUpdate(list, obj,obj1,obj3,lsobj);
//			i = db.update(sql2, obj2);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return false;
		}
		return a;
	}
	public String scxjbh(String kmbh,String kjzd) {
		String sql8 = "select '"+kmbh+"'||to_char(max(substr(kmbh, -2))+1,'FM909' ) as kmbh from cw_kjkmszb where kmbh like '"+kmbh+"%' and kjzd='"+kjzd+"' and kmbh != '"+kmbh+"'";
		String scbh = db.queryForSingleValue(sql8);
		if(scbh.equals(kmbh)) {
			scbh = scbh+"01";
		}
		return scbh;
	}
	/*
	 * 添加下级
	 */
	public boolean doAddxj(PageData pd,HttpSession session) {
		String sszt = Constant.getztid(session);
		//科目编号
//				String sql8 = "select '"+pd.getString("km")+"'||to_char(max(substr(kmbh, -2))+1,'FM909' ) as kmbh from cw_kjkmszb where kmbh like '"+pd.getString("km")+"%' and kjzd='"+pd.getString("kjzd")+"' and kmbh != '"+pd.getString("km")+"'";
//				String kmbh = db.queryForSingleValue(sql8);
//				if(kmbh.equals(pd.getString("km"))) {
//					kmbh = kmbh+"01";
//				}
		//会计科目历史表
		String sql10 = "insert into cw_ywjlb values(sys_guid(),sysdate,?,?,?,?,?,?,?)";
		Object[] obj10 = new Object[] {
				pd.getString("mkbh"),
				pd.getString("kmbh"),
				pd.getString("kmmc"),
                "增加科目编号为"+pd.getString("kmbh")+"，科目名称为"+pd.getString("kmmc")+"的科目",
                pd.getString("kjzd"),
                sszt,
                LUser.getGuid()
                
		};
		
	
		String zje = pd.getString("type");
		String sql4="update cw_kjkmszb set sfmj='0' where guid=?";
		String sql5="update cw_kmyeb set sfmj='0' where kmid=? and kjzd=? and nd=to_char(sysdate,'yyyy')";
		//db.update(sql4, new Object[]{pd.getString("guid1")}
		Object[] obj4 = new Object[]{pd.getString("guid1")};
		Object[] obj5 = new Object[]{pd.getString("guid1"),pd.getString("kjzd")};	
		
		String guid = GenAutoKey.get32UUID();
		
		String sql = "insert into Cw_kjkmszb(guid,kmbh,kmmc,kmsx,zjf,yefx,hslb,kmjc,qyf,sfwyh,sfjjflkm,sfgnflkm,bmhs,xmhs,czr,bz,sjfl,jb,czrq,sfmj,kmnd,sszt,kjzd,dfdw,wldwfz,grfz) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?,?,?,?,?)";
		Object[] obj = new Object[]{
				guid,
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
				pd.get("jb"),
				pd.getString("kmbh"),
				1,
				sszt,
				pd.getString("kjzd"),
				CommonUtil.getBeginText(pd.getString("dfdw")),
				pd.getString("wldwfz"),
				pd.getString("grfz")
		};
		String lsguid = GenAutoKey.get32UUID();//历史表guid
		
		String lssql = "insert into Cw_kjkmszb_ls(guid,kmbh,kmmc,kmsx,zjf,yefx,hslb,kmjc,qyf,sfwyh,sfjjflkm,sfgnflkm,bmhs,xmhs,czr,bz,sjfl,jb,czrq,sfmj,kmnd,sszt,kjzd) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?,?)";
		Object[] lsobj = new Object[]{
				lsguid,
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
				pd.get("jb"),
				pd.getString("kmbh"),
				1,
				sszt,
				pd.getString("kjzd")
		};
		String sfjjflkm = pd.getString("sfjjflkm");
		String bmhs = pd.getString("bmhs");
		String xmhs = pd.getString("xmhs");
		int sffz=0;
		if("1".equals(sfjjflkm)||"1".equals(bmhs)||"1".equals(xmhs)) {
			sffz=1;
		}
		String guid2 = GenAutoKey.get32UUID();//科目余额表guid
		String sql2="insert into cw_kmyeb(guid,kmbh,kmmc,sfmj,sffz,nd,sszt,kmsx,kmid,kjzd) values(?,?,?,?,?,to_char(sysdate,'yyyy'),?,?,?,?)";
		Object[] obj1 = new Object[]{
				guid2,
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				1,
				sffz,
				sszt,
				pd.getString("kmsx"),
				guid,
				pd.getString("kjzd")
		};
		String sql6 = "select * from cw_kjkmszb where 1=2";
		Object[] obj6 = null;
		
		String sql7 = "select * from cw_kjkmszb where 1=2";
		Object[] obj7 = null;
		
		String sql9 =  "select * from cw_kjkmszb where 1=2";
		Object[] obj9 = null;
		if("zje".equals(zje)) {
			//金额转换，将上级的金额改为0 ，下级继承上级金额
			 sql6 = "update cw_kmyeb set kmzye = 0 where kmid=? and kjzd=? and nd=to_char(sysdate,'yyyy') and 1!=1";//开始时此处要将上级金额改为0，后来不要求改为0 ，暂时保留
			 obj6 = new Object[] {
					 pd.getString("guid1"),pd.getString("kjzd")
			 };
			 sql7 = "update cw_kmyeb set kmzye=? where guid=?";
			 obj7 = new Object[] {
				pd.getString("kmzye"),guid2	 
			 };
			 //修改凭证录入明细表
			 sql9 = "update cw_pzlrmxb set kmbh=? where kmbh=?";
			 obj9 = new Object[] {
					 pd.getString("kmbh"),pd.getString("km")
			 };
			 
		}
		String fysql="update cw_fykmdzb set kmbh = ? where kmbh = ?";
		Object[] fyobj = new Object[] {
				pd.getString("kmbh"),pd.getString("km")
		};
		
		String zfsql="update cw_zffsdzb set kmbh = ? where kmbh = ?";
		Object[] zfobj = new Object[] {
				pd.getString("kmbh"),pd.getString("km")
		};
		ArrayList list = new ArrayList<>();
		list.add(sql);
		list.add(sql2);
		list.add(sql4);
		list.add(sql5);
		list.add(sql6);
		list.add(sql7);
		list.add(sql9);
		list.add(sql10);
		list.add(lssql);
		list.add(fysql);
		list.add(zfsql);
	
		boolean i = false;
		try {  
		   i=db.batchUpdate(list,obj,obj1,obj4,obj5,obj6,obj7,obj9,obj10,lsobj,fyobj,zfobj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return i;
		}
		return i;
	}
	
	/**
	 * 修改
	 * @param dwb 单位表
	 * @return
	 */
	public boolean doUpdate(PageData pd,String dwbh,String sszt){
		//会计科目历史表
		String dlr = LUser.getGuid();
		String sql3 = "insert into cw_ywjlb values(sys_guid(),sysdate,?,?,?,?,?,?,?)";
		Object[] obj3 = new Object[] {
				pd.getString("mkbh"),
				pd.getString("kmbh1"),
				pd.getString("kmmc1"),
				"将科目编号为"+pd.getString("kmbh1")+"，科目名称为"+pd.getString("kmmc1")+"的科目修改为科目编号为"+pd.getString("kmbh")+"，科目名称为"+pd.getString("kmmc")+"的科目",
				pd.getString("kjzd"),
				sszt,
				dlr
				
		};
		//修改科目余额表
		String sfjjflkm = pd.getString("sfjjflkm");
		String bmhs = pd.getString("bmhs");
		String xmhs = pd.getString("xmhs");
		String wldwfz = pd.getString("wldwfz");
		String grfz = pd.getString("grfz");
		int sffz=0;
		if("1".equals(sfjjflkm)||"1".equals(bmhs)||"1".equals(xmhs)||"1".equals(wldwfz)||"1".equals(grfz)) {
			sffz=1;
		}
		String sql1 = "update cw_kmyeb set kmbh=?,kmmc=?,kmsx=?,sffz=? where kmid=?";
		Object[] obj1 = new Object[] {
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmsx"),
				sffz,
				dwbh
		
		};
		//修改科目设置表
		String sql = "update Cw_kjkmszb set kmbh=?,kmmc=?,kmsx=?,zjf=?,yefx=?,"
					+ "hslb=?,qyf=?,kmjc=?,bz=?,sfjjflkm=?,"
					+ "sfgnflkm=?,bmhs=?,xmhs=?,sfwyh=?,dfdw=?,wldwfz=?,grfz=? where guid=?";
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
				CommonUtil.getBeginText(pd.getString("dfdw")),
				pd.getString("wldwfz"),
				pd.getString("grfz"),
				dwbh
		};
		String lsguid = GenAutoKey.get32UUID();
		
		String lssql = "insert into cw_kjkmszb_ls" + 
				"  select sys_guid(),t.kmbh,t.kmmc,t.kmsx,t.zjf, t.yefx, t.hslb, t.kmjc, t.qyf, t.sfwyh,t.sfjjflkm, t.sfgnflkm,  t.bmhs, t.xmhs,  t.czr, t.czrq," + 
				" t.bz, t.mbbid, t.sjfl,t.jb, t.kmnd,t.sfmj, t.sszt, t.sfkjzd,t.kjzd" + 
				" from cw_kjkmszb t where guid = ?";
		Object[] lsobj = new Object[]{dwbh};
		
		String uplssql = "update Cw_kjkmszb_ls set kmbh=?,kmmc=?,kmsx=?,zjf=?,yefx=?,"
				+ "hslb=?,qyf=?,kmjc=?,bz=?,sfjjflkm=?,"
				+ "sfgnflkm=?,bmhs=?,xmhs=?,sfwyh=? ,czr=?,czrq=sysdate where guid=?";
	    Object[] uplsobj = new Object[]{
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
			LUser.getGuid(),
			dwbh
	};

		ArrayList list = new ArrayList<>();
		list.add(sql);
		list.add(sql1);
		list.add(sql3);
		list.add(lssql);
		list.add(uplssql);
		boolean a = false;
		try {  
			a = db.batchUpdate(list,obj,obj1,obj3,lsobj,uplsobj);
			
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return a;
		}
		return a;
	}
	/**
	 * 修改功能科目
	 * @param dwb 单位表
	 * @return
	 */
	public int doUpdategnkm(PageData pd,String dwbh){
		String sql = "update Cw_gnkmb set kmbh=?,kmmc=?,kmsx=?,zjf=?,yefx=?,"
					+ "qyf=?,kmjc=? where guid=?";
		Object[] obj = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmsx"),
				pd.getString("zjf"),
				pd.getString("yefx"),
				pd.getString("qyf"),
				pd.getString("kmjc"),
	
				dwbh
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
	public boolean doDelete(String dwbh,String sjfl,String kmbh,String kmmc,String kjzd,String mkbh,String sszt) {
		//历史表增数据
		String sql5= "insert into cw_ywjlb values(sys_guid(),sysdate,'"+mkbh+"','"+kmbh+"','"+kmmc+"','删除科目科目编号为"+kmbh+"，科目名称为"+kmmc+"的科目，','"+kjzd+"','"+sszt+"','"+LUser.getGuid()+"')";
		
		String sql1 = "select count(1) from cw_kjkmszb where sjfl='"+sjfl+"'";
		Map map = db.queryForMap(sql1);
		String count = String.valueOf(map.get("count(1)")) ;
		ArrayList list = new ArrayList();
		String sql2="update cw_kjkmszb set kmbh='1001' where 1!=1 and kmbh='1001'";
		String sql3="update cw_kjkmszb set kmbh='1001' where 1!=1 and kmbh='1001'";
		if("1".equals(count)) {
			//该上级没有末级，修改上级的是否末级为是末级
			 sql2 = "update cw_kjkmszb set sfmj='1' where jb='"+sjfl+"' and kjzd='"+kjzd+"'";//修改会计科目
			 sql3 = "update cw_kmyeb set sfmj='1' where kmbh ='"+sjfl+"' and kjzd='"+kjzd+"'";//修改科目余额表
			
		}
		//删除会计科目和科目余额表
		String sql = "DELETE Cw_kjkmszb WHERE guid = '"+dwbh+"'";
		String sql4 = "DELETE Cw_kmyeb WHERE kmid = '"+dwbh+"'";
		list.add(sql2);
		list.add(sql3);
		list.add(sql);
		list.add(sql4);
		list.add(sql5);
		
	    boolean a =	db.batchUpdate(list);
		//return db.update(sql);
		return a;
	}
	public int doDeletekmsz(String dwbh) {
		String sql = "DELETE Cw_gnkmb WHERE guid in ('"+dwbh+"')";
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
	/**
	 * 
	 * @param dwbh
	 * @return
	 */
	public int getCountByGuid(String guid) {
		String sql = "select count(0) from cw_kjkmszb where sjfl =(select jb from cw_kjkmszb where guid='"+guid+"')";
		int count = Integer.parseInt(Validate.isNullToDefaultString(db.queryForSingleValue(sql), ""));
		return count;
	}
	//------------------------------------
	/**
	 * 
	 * @param pd
	 * @param dm
	 * @param session
	 * @return  同级
	 */
	public int doAddgnkmsz(PageData pd,String dm,HttpSession session) {
		//查询上级编号
		String atype=pd.getString("aType");
		if(atype.equals("tj")){
			String bh = pd.getString("bh");
			String sjSql = "select sssjkm from Cw_gnkmb where kmbh='"+bh+"' "; 
			String sj = db.queryForSingleValue(sjSql);
			String sszt = Constant.getztid(session);
			String sql = "insert into Cw_gnkmb(guid,kmbh,kmmc,SSKJKMBH,jb,kmsx,zjf,yefx,qyf,kmjc,czr,czrq,sszt,sssjkm,KMND) "
					+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,sysdate,?,?,to_char(sysdate,'yyyy'))";
			Object[] obj = new Object[]{
					pd.getString("kmbh"),
					pd.getString("kmmc"),
					"",
					"",
					pd.getString("kmsx"),
					pd.getString("zjf"),
					pd.getString("yefx"),
					pd.getString("qyf"),
					pd.getString("kmjc"),
					pd.getString("czr"),
					sszt,
					Validate.isNullToDefaultString(sj, "")
			};
			int i = 0;
			try {  
				i = db.update(sql, obj);
			} catch (DataAccessException e) {
			    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
			    return -1;
			}
			return i;
		}else{
			String sszt = Constant.getztid(session);
			String sql = "insert into Cw_gnkmb(guid,kmbh,kmmc,SSKJKMBH,jb,kmsx,zjf,yefx,qyf,kmjc,czr,czrq,sszt,sssjkm,kmnd) "
					+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,sysdate,?,?,to_char(sysdate,'yyyy'))";
			Object[] obj = new Object[]{
					pd.getString("kmbh"),
					pd.getString("kmmc"),
					"",
					"",
					pd.getString("kmsx"),
					pd.getString("zjf"),
					pd.getString("yefx"),
					pd.getString("qyf"),
					(Integer.parseInt(pd.getString("kmjc"))),
					pd.getString("czr"),
					sszt,
					pd.getString("bh")
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
	}
	public int doAddxjkmsz(PageData pd,HttpSession session) {
		String sszt = Constant.getztid(session);
		String sql4="update cw_kjkmszb set sfmj='0' where guid=?";
		db.update(sql4, new Object[]{pd.getString("guid1")}
				
		);
		
		String sql = "insert into Cw_gnkmb(guid,kmbh,kmmc,SSKJKMBH,jb,kmsx,zjf,yefx,qyf,kmjc,czr,czrq,sszt) "
				+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,sysdate,?)";
		Object[] obj = new Object[]{
				pd.getString("kmbh"),
				pd.getString("kmmc"),
				pd.getString("kmjc"),
				CommonUtil.getNewFlownum("Cw_gnkmb", "jb", 4, " and z.SSKJKMBH='"+pd.getString("kmjc")+"' "),
				pd.getString("kmsx"),
				pd.getString("zjf"),
				pd.getString("yefx"),
				
				pd.getString("qyf"),
				pd.getString("kmjc"),
				pd.getString("czr"),
				
				sszt
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
	public boolean doCheck(String kmguid,String type) {
		String sql = ("select count(1) from cw_sys_ywjlb  where ywid='"+kmguid+"' and kmlx='"+type+"'" );
		System.err.println("++++++"+sql);
		int count = Integer.parseInt(db.queryForSingleValue(sql));
			if(count>0){
				return true;
			}else{
				return false;
			}
	}
	public String doCheckye(String guid,String kmnd,String sszt, String kjzd) {
	
		String sql = "select nvl(kmzye,'0') from cw_kmyeb where kmid='"+guid+"'  and nd=to_char(sysdate,'yyyy') and sszt='"+sszt+"' and kjzd='"+kjzd+"'";
		String kmzye1 = db.queryForSingleValue(sql);
		//if(kmzye1==null || kmzye1.length()==0) {
	//		kmzye1="0";
	//	}
	//	int kmzye = Integer.parseInt(kmzye1);
		return kmzye1;
	}
	/**
	 * 得到变动金额
	 * @return
	 */
	public String getbdje(String sszt,String kmbh) {
		String sql = "select nvl(jfje,0)+nvl(dfje,0) as zje from cw_pzlrmxb where pzbh in (select guid from cw_pzlrzb where pznd=to_char(sysdate,'yyyy')) and kmbh ='"+kmbh+"'";
	    return db.queryForSingleValue(sql);
	}
	/**
	 * 得到会计制度信息
	 */
	public List getzd() {
		String sql = "select guid,zdm from cw_kjzdqy_zdb";
		return db.queryForList(sql);
	}
	/**
	 * 判断项目编号是否重复
	 * @param kmbh
	 * @return
	 */
	public boolean doCheckXmbh1(String kmbh,String kjzd,String sszt ){
		
		String sql = "select count(1) from Cw_kjkmszb where kmbh= '"+kmbh+"' and kjzd = '"+kjzd+"'and sszt='"+sszt+"'";
		System.err.println("kmbh="+kmbh);
		String count = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}

}
