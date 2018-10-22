package com.googosoft.dao.ysgl.xmsz;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.service.ysgl.XmlxDao;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

@Repository("xmxxDao")
public class XmxxDao extends BaseDao{
	private Logger logger = Logger.getLogger(XmxxDao.class);
	
	public List<Map<String, Object>> selectXmxxList(){
		String sql = "SELECT A.BMBH,ROWNUM AS \"_XH\",A.XMBH,A.XMMC,A.SFQY,\r\n" + 
				"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.XMDL+"' AND C.DM = A.XMDL) AS XMDL,\r\n" + 
				"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.XMLB+"' AND C.DM = A.XMLB) AS XMLB,\r\n" + 
				"(SELECT C.MC FROM GX_SYS_DMB C WHERE C.ZL = '"+Constant.XMSX+"' AND C.DM = A.XMSX) AS XMSX,\r\n" + 
				"(SELECT C.XMLXMC FROM CW_XMLXB C WHERE C.GUID = A.XMLX) AS XMLX,\r\n" + 
				"(SELECT C.XM FROM GX_SYS_RYB C WHERE C.GUID = A.FZR) AS FZR,\r\n" + 
				"(SELECT C.MC FROM GX_SYS_DWB C WHERE C.GUID = A.GKBM) AS GKBM\r\n" + 
				"FROM CW_XMJBXXB A";
		return db.queryForList(sql);
	}
	public Map<String, Object> selectXmxxMapById(String guid){
		String sql = " select x.guid,to_char(x.zfcgje,'FM9999999999999999990.00')zfcgje,to_char(x.fzfcgje,'FM9999999999999999990.00')fzfcgje,(select '('||dwbh||')'|| mc from gx_sys_dwb dw where dw.dwbh=x.bmbh) as bmbh,  x.xmbh,  x.xmdl, (select D.MC from gx_sys_dmb d  where d.zl = '250' and d.dm = x.xmdl) xmdlmc,"
				+ "  x.xmlb,(select D.MC from gx_sys_dmb d where d.zl = '251' and d.dm = x.xmlb) xmlbmc,  x.xmmc, x.xmlx,x.xmlxid,"
				+ "(select '('||t.xmlxbh||')'|| t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,x.xmjfsfyxcb,x.cbbl,x.sfjxjjflfykz,"
				+ " (select D.MC from gx_sys_dmb d  where d.zl = 'XMLX' and d.dm = x.xmlx) xmlxmc,x.fzr,"
				+ " ('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"
				+ "  x.xmsx, (select D.MC from gx_sys_dmb d where d.zl = 'XMSX' and d.dm = x.xmsx) xmsxmc,"
				+ "  x.gkbm, ('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ "  x.sfqy, (case sfqy when '0' then '未启用' when '1' then '已启用' else '' end) as sfqymc,"
				+ "  x.sfwg, x.kgrq, x.wgrq,  x.sfczzc,x.sfgk,x.gkxxm,x.yslx,(select d.mc from gx_sys_dmb d where d.zl='YSLX'and d.dm=x.yslx)yslxmc,to_char(x.ysje,'FM999999999999999990.00')ysje, to_char(x.syje,'FM999999999999999990.00')syje,"
				+ "  s.srkmbh, ('('||s.srkmbh||')'||(select b.kmmc from CW_KJKMSZ b where b.guid=s.srkmbh))srkmmc, z.zckmbh,"
				+ "  ('('||z.zckmbh||')'||(select b.kmmc from CW_KJKMSZ b where b.guid=z.zckmbh))zckmmc, j.jjfl,"
				+ " '('||(select xmbh from cw_xmjbxxb b where x.jfbh=b.guid)||')'||(select xmmc from cw_xmjbxxb b where x.jfbh=b.guid) as sjxm,x.jfbh,"
				+ " ('('||j.jjfl||')'||(select b.kmmc from CW_JJKMb b where b.kmbh=j.jjfl))jjflmc,x.kjkmyxjz,x.jjkmyxjz from Cw_xmjbxxb x  left join Cw_xmkzxxb c"
				+ "  on c.xmbh = x.xmbh left join Cw_xmsrbnew s on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ "  left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.guid='"+guid+"' ";
		return db.queryForMap(sql);
	}
	public int updateXmxx(PageData pd) {
		String sql = "UPDATE CW_XMJBXXB A SET "
				+ "A.BMBH = ?,A.XMBH = ?,A.XMDL = ?,A.XMLB = ?,A.XMMC = ?,A.XMLX = ?,A.FZR = ?,A.XMSX = ?,A.GKBM = ?,A.SFQY = ?,A.SFWG=?,A.KGRQ=?,A.WGRQ=?  where guid=?";
		Object[] obj = {
				pd.getString("mbbh"),
				pd.getString("xmbh"),
				pd.getString("xmdl"),
				pd.getString("xmlb"),
				pd.getString("xmmc"),
				pd.getString("xmlx"),
				pd.getString("fzr"),
				pd.getString("xmsx"),
				pd.getString("gkbm"),
				pd.getString("sfqy"),
				pd.getString("sfwg"),
				pd.getString("kgrq"),
				pd.getString("wgrq"),
				pd.getString("guid")
				
		};
		return db.update(sql, obj);
	}
	public int insertXmjbxx(PageData pd) {
		String sql = "insert into cw_xmjbxxb values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {
				pd.getString("bmbh"),
				pd.getString("xmbh"),
				pd.getString("xmdl"),
				pd.getString("xmlb"),
				pd.getString("xmmc"),
				pd.getString("fzr"),
				pd.getString("fzr"),
				pd.getString("xmsx"),
				pd.getString("gkbm"),
				pd.getString("sfqy"),
				pd.getString("czr"),
				""
		};
		return db.update(sql,obj);
	}
	public int insertXmkzxx(PageData pd) {
		String sql= "insert into cw_xmkzxxb values(sys_guid(),?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = {
				pd.getString("srkm"),
				pd.getString("zckm"),
				pd.getString("jjflzckm"),
				pd.getString("sfczzc"),
				pd.getString("yslx"),
				pd.getString("sfgk"),
				pd.getString("gkxxm"),
				pd.getString("czr"),
				"",
				pd.getString("xmbh")
		};
		return db.update(sql,obj);
	}
	public int deleteXmxx(PageData pd) {
		String sql = "delete from cw_xmjbxxb where guid in (?)";
		Object guid = pd.getString("guid");
		return db.update(sql, guid);
	}
	public boolean checkXmbhExist(PageData pd) {
		String xmbh = pd.getString("xmbh");
		String slq = "select * from cw_xmjbxxb where xmbh = ?";
		if(db.queryForList(slq,xmbh).size() > 0) {
			return true;
		}
		return false;
	}
	public boolean doCheckXmmc(String bmh){
		String sql = "select count(1) from Cw_xmjbxxb where  xmmc= ? ";
		String count = db.queryForObject(sql,new Object[]{bmh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	public boolean doCheckXmmcU(String bmh){
		String sql = "select count(1) from Cw_xmjbxxb where  xmmc= ? ";
		String count = db.queryForObject(sql,new Object[]{bmh}, String.class);
		if("1".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	public int doAdd(PageData pd,String rybh,HttpSession session,String zfcgje,String fzfcgje){
		String sszt = Constant.getztid(session);
		String xmlxid = pd.getString("xmlxid");// GenAutoKey.makeCkbh("Cw_xmlxb", "xmlxbh", "2");
		String jfbh = pd.getString("jfbh");
		String sfmj;
		String sqlSfmj;
		if(Validate.noNull(jfbh)) {
			sqlSfmj = "update cw_xmjbxxb set sfmj='0' where guid=?";
			db.update(sqlSfmj,new Object[] {jfbh});
			sfmj="1";
		}else {
			sfmj="1";
		}
		//String ss=GenAutoKey.makeCkbh("Cw_xmjbxxb", "xmbh", "2");
		String sql = "insert into Cw_xmjbxxb(guid, bmbh,xmbh,xmdl,xmlb,xmmc, xmlx,xmlxid,fzr,xmsx,gkbm,sfqy, ysje,syje,sfczzc,yslx,sfgk, gkxxm,sfwg,KGRQ,wgrq,czr,sszt,czrq,KJKMYXJZ,JJKMYXJZ,XMJFSFYXCB,CBBL,SFJXJJFLFYKZ,ZFCGJE,ZFCGSYJE,FZFCGJE,FZFCGSYJE,jfbh,sfmj) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,?,?,?,?,?,?,?,?,?,?)";
		Object[] obj = new Object[]{
				pd.getString("guid"),
				CommonUtil.getBeginText(pd.getString("bmbh")),		
				
				//GenAutoKey.makeCkbh("Cw_xmjbxxb", "xmbh", "2"),
				pd.getString("xmbh"),
				pd.getString("xmdl"),
				pd.getString("xmlb"),
				pd.getString("xmmc"),
				
				getguid(CommonUtil.getBeginText(pd.getString("xmlx"))),
				pd.getString("xmlxid"),
				CommonUtil.getBeginText(pd.getString("fzr")),
				pd.getString("xmsx"),
				CommonUtil.getBeginText(pd.getString("gkbm")),
				pd.getString("sfqy"),
				
				pd.getString("ysje"),
				pd.getString("syje"),
				pd.getString("sfczzc"),
				pd.getString("yslx"),
				pd.getString("sfgk"),
				
				pd.getString("gkxxm"),
				pd.getString("sfwg"),
				pd.getString("kgrq"),
				pd.getString("wgrq"),
				rybh,
				sszt,
                //pd.getString("sfky"), 是否科研
				pd.getString("kjkmyxjz"),
				pd.getString("jjkmyxjz"),
				pd.getString("xmjfsfyxcb"),
				pd.getString("cbbl"),
				pd.getString("sfjxjjflfykz"),
				zfcgje,
				zfcgje,
				fzfcgje,
				fzfcgje,
				pd.getString("jfbh"),
				sfmj,
		};
		String czr = LUser.getGuid();
		db.update("insert into cw_sys_ywjlb(ywid,kmlx,sszt,guid,czid,czr,czsj) select a.guid,a.xmlb,a.sszt,sys_guid(),'"+pd.getString("guid")+"','"+czr+"',sysdate from CW_XMLXB a where a.guid='"+xmlxid+"' ");
		//1
		String guid = GenAutoKey.get32UUID();


		String sql1 = "insert into Cw_xmkzxxb(guid,xmbh,sfczzc,yslx,sfgk,gkxxm,czr,sszt,czrq) "
				+ "values(?,?,?,?,?,?,?,?,sysdate)";
		Object[] obj1 = new Object[]{
				guid,
				pd.getString("guid"),
				pd.getString("sfczzc"),
				pd.getString("yslx"),
				pd.getString("sfgk"),
				pd.getString("gkxxm"),
				rybh,
				sszt
				
		};
//		//2
//		String sql2 = "insert into Cw_xmsrbnew(guid,xmxxbh,srkmbh,czr,sszt) "
//				+ "values(sys_guid(),?,?,?,?)";
//		Object[] obj2 = new Object[]{
//				pd.getString("xmbh"),
//				CommonUtil.getBeginText(pd.getString("srkm")),
//				rybh,
//				sszt
//		};
//		//3
//		String sql3 = "insert into Cw_xmzcbnew(guid,xmxxbh,zckmbh,czr,sszt) "
//				+ "values(sys_guid(),?,?,?,?)";
//		Object[] obj3 = new Object[]{
//				pd.getString("xmbh"),
//				CommonUtil.getBeginText(pd.getString("zckm")),
//				rybh,
//				sszt
//		};
//		//4
//		String sql4 = "insert into Cw_xmjjflbnew(guid,xmxxbh,jjfl,czr,sszt) "
//				+ "values(sys_guid(),?,?,?,?)";
//		Object[] obj4 = new Object[]{
//				pd.getString("xmbh"),
//				CommonUtil.getBeginText(pd.getString("jjflzckm")),
//				rybh,
//				sszt
//		};
//		
		
		
		int i = 0;   
		try {  
			i = db.update(sql, obj);
			i = db.update(sql1, obj1);  
			//i = db.update(sql2, obj2);
		//	i = db.update(sql3, obj3);
		//	i = db.update(sql4, obj4);
		//	if(i > 0){ 
	//			db.insertRydwqx(LUser.getRybh());//赋权限，当前登录人对应单位下所有的单位权限
		//	}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	public int doUpdate(PageData pd,String dwbh,String xmfzr){
		String xmlxid = pd.getString("xmlxid");
		String czr = LUser.getGuid();
		String jfbh = pd.getString("jfbh");
		String sqlSfmj;
		if(Validate.noNull(jfbh)) {
			sqlSfmj = "update cw_xmjbxxb set sfmj='0' where guid=?";
			db.update(sqlSfmj,new Object[] {jfbh});
		}
		String sql = "update Cw_xmjbxxb set bmbh=?,xmbh=?,xmdl=?,xmlb=?,xmmc=?  ,xmlx=?,xmlxid=?,fzr=?,xmsx=?,gkbm=?,sfqy=?  ,ysje=?,syje=?,sfczzc=?,yslx=?,sfgk=?,"
				+ "gkxxm=?,sfwg=?,kgrq=?,wgrq=?,kjkmyxjz=?,jjkmyxjz=?,xmjfsfyxcb=?,cbbl=?,sfjxjjflfykz=?,ZFCGJE=?,ZFCGSYJE=?,FZFCGJE=?,FZFCGSYJE=?,jfbh=?,sfmj=(select nvl(sfmj,1) from cw_xmjbxxb where guid = ?) where guid = ?";
		Object[] obj = new Object[]{
				CommonUtil.getBeginText(pd.getString("bmbh")),
				
				pd.getString("xmbh"),
				pd.getString("xmdl"),
				pd.getString("xmlb"),
				pd.getString("xmmc"),
				
				getguid(CommonUtil.getBeginText(pd.getString("xmlx"))),
				pd.getString("xmlxid"),
				CommonUtil.getBeginText(pd.getString("fzr")),
				pd.getString("xmsx"),
				CommonUtil.getBeginText(pd.getString("gkbm")),
				pd.getString("sfqy"),
				
				pd.getString("ysje"),
				pd.getString("syje"),
				pd.getString("sfczzc"),
				pd.getString("yslx"),
				pd.getString("sfgk"),
				
				pd.getString("gkxxm"),
				pd.getString("sfwg"),
				pd.getString("kgrq"),
				pd.getString("wgrq"),
				//pd.getString("sfky"), 是否科研
				pd.getString("kjkmyxjz"),
				pd.getString("jjkmyxjz"),
				pd.getString("xmjfsfyxcb"),
				pd.getString("cbbl"),
				pd.getString("sfjxjjflfykz"),
				pd.getString("zfcgje"),
				pd.getString("zfcgje"),
				pd.getString("fzfcgje"),
				pd.getString("fzfcgje"),
				pd.getString("jfbh"),
				pd.getString("guid"),
				pd.getString("guid"),
				
		};
		db.update("update cw_sys_ywjlb set ywid = '"+xmlxid+"',kmlx='',czr='"+czr+"',czsj=sysdate where czid = '"+pd.getString("guid")+"'");
		String sql2 = "update Cw_xmkzxxb set xmbh=?,sfczzc=?,yslx=?,sfgk=?,gkxxm=?  where xmbh = ?";
		Object[] obj2 = new Object[]{
				pd.getString("xmbh"),
				pd.getString("sfczzc"),
				pd.getString("yslx"),
				pd.getString("sfgk"),
				pd.getString("gkxxm"),
				pd.getString("xmbh")
		};
		
//		String sql3 = "update Cw_xmsrbnew set xmxxbh=?,srkmbh=?  where xmxxbh = ?";
//		Object[] obj3 = new Object[]{
//				pd.getString("xmbh"),
//				CommonUtil.getBeginText(pd.getString("srkm")),
//				pd.getString("xmbh")
//		};
//		
		String sql4 = "update Cw_xmzcbnew set xmxxbh=?,zckmbh=?  where xmxxbh = ?";
		Object[] obj4 = new Object[]{
				pd.getString("xmbh"),
				CommonUtil.getBeginText(pd.getString("zckm")),
				pd.getString("xmbh")
		};
		String sql5 = "update Cw_xmzcbnew set xmxxbh=?,zckmbh=?  where xmxxbh = ?";
		Object[] obj5 = new Object[]{
				pd.getString("xmbh"),
				CommonUtil.getBeginText(pd.getString("jjflzckm")),
				pd.getString("xmbh")
		};
		
		System.err.println(xmfzr+"____________"+pd.getString("fzr"));
		if(!xmfzr.equals(pd.get("fzr"))) {
			String sql6 = "insert into Cw_fzrbgjlb(xmid,oldfzr,newfzr,jdr)values(?,?,?,?) ";
			Object[] obj6 = new Object[]{
					pd.getString("guid"),
					xmfzr,
					CommonUtil.getBeginText(pd.getString("fzr")),
					LUser.getRybh()
			};
			db.update(sql6,obj6);
		}
		
		
		
	    int i = 0;
		try {  
			i = db.update(sql, obj);
			i = db.update(sql2, obj2);
//			i = db.update(sql3, obj3);
			i = db.update(sql4, obj4);
			i = db.update(sql5, obj5);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	public int doAddFzrbgjl(String xmid,String newfzr) {
		int result = 0;
		String sql = "insert into Cw_fzrbgjlb(xmid,oldfzr,newfzr,jdr)values(?,(select fzr from Cw_xmjbxxb where guid=?),?,?) ";
		Object[] obj = new Object[]{
				xmid,
				xmid,
				newfzr,
				LUser.getRybh()
		};
		result = db.update(sql,obj);
		return result;
	}
	public int doChangeFzr(String xmid,String newfzr) {
		int result = 0;
		result = db.update("update Cw_xmjbxxb t set t.fzr='"+newfzr+"' where t.guid='"+xmid+"'");
		return result;
	}
	public int doDeletekz(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmkzxxb WHERE xmbh in  ('"+guid+"')");
		
		return result;
	}
	
	public String getguid(String bh) {
		String sql=" select t.guid from cw_XMLXB t  where t.xmlxbh='"+bh+"' ";
		
		return db.queryForSingleValue(sql);
	}
	public int doDeletesr(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmsrbnew WHERE xmxxbh in  ('"+guid+"')");
		return result;
	}public int doDeletezc(String guid) {
		int result = 0;		
		result = db.update( "DELETE Cw_xmzcbnew WHERE xmxxbh in  ('"+guid+"')");	
		return result;
	}public int doDeletejjfl(String guid) {
		int result = 0;		
		result = db.update( "DELETE Cw_xmjjflbnew WHERE xmxxbh in  ('"+guid+"')");	
		return result;
	}public int doDeletejbxx(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmjbxxb WHERE guid in ('"+guid+"')");
		         db.update("delete cw_sys_ywjlb where czid in ('"+guid+"')");
		return result;
	}
	/**
	 *  验证项目类型是否正被使用
	 */
	public boolean selectXmmc(String guid) {
		boolean flag = true;
		String sqlRc = "select count(*) from CW_RCBXMXB where xmguid = ?";
		String sqlCc = "select count(*) from cw_ccsqspb where jfbh = ?";
		String sqlJk = "select count(*) from CW_JKYWB_MXB where jfbh = ?";
		String sqlGr = "select count(*) from CW_GRSRZB where xmbh=?";
		String sqlPz = "select count(*) from cw_pzlrmxb where xmbh=(select xmbh from cw_xmjbxxb where guid =?)";	
		int rc = db.queryForInt(sqlRc,new Object[] {guid});
		int cc = db.queryForInt(sqlCc,new Object[] {guid});
		int jk = db.queryForInt(sqlJk,new Object[] {guid});
		int gr = db.queryForInt(sqlGr,new Object[] {guid});
		int pz = db.queryForInt(sqlPz,new Object[] {guid});
		if(rc>0||cc>0||jk>0||gr>0||pz>0) {
			flag=false;
		}
		if("2540A79A4BC84598965938E9B0CA6957".equals(guid)) {
			flag=false;
		}
		return flag;
		/*String sql = "select count(0) from cw_sys_ywjlb where  ywid in ('"+guid+"')";
		int aa = db.queryForInt(sql);
		if(aa == 0) {
			return true;
		}
		return false;*/
	}
	/**
	 * 得到sr科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getsrkm(String xmlxbh,String kjzd) {
		String sql = " select guid,xmlxbh,srkmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=sr.srkmbh and kj.kjzd=?) as kmmc from cw_xmsrb sr where xmlxbh=?";
		return db.queryForList(sql,new Object[] {kjzd,xmlxbh});
		
	}
	/**
	 * 联想得到sr科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getsrkm1(String xmbh,String sszt,String kjzd) {
		String sql = " select guid,xmlxbh,srkmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=sr.srkmbh and kj.kjzd=?) as kmmc from cw_xmsrb sr where xmlxbh=(select guid from cw_xmlxb where xmlxbh =? and sszt = ?)";
		return db.queryForList(sql,new Object[] {kjzd,CommonUtil.getBeginText(xmbh),sszt});
		
	}
	/**
	 * 得到项目扩展信息
	 * @param guid
	 * @return
	 */
	public Map getxmlxxx(String guid) {
		String sql = " select * from cw_xmlxb  where guid=?";
		return db.queryForMap(sql,new Object[] {guid});
		
	}
	/**
	 * 联想得到项目扩展信息
	 * @param guid
	 * @return
	 */
	public Map getxmlxxx1(String xmbh,String sszt) {
		String sql = " select * from cw_xmlxb  where xmlxbh=? and sszt =?";
		return db.queryForMap(sql,new Object[] {CommonUtil.getBeginText(xmbh),sszt});
		
	}
	/**
	 * 得到zc科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getzckm(String xmlxbh,String kjzd) {
		String sql = "select guid,xmlxbh,zckmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=zc.zckmbh and kj.kjzd=?) as kmmc from cw_xmzcb zc where xmlxbh=? ";
		return db.queryForList(sql,new Object[] {kjzd,xmlxbh});
		
	}
	/**
	 * 联想得到zc科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getzckm1(String xmbh,String sszt,String kjzd) {
		String sql = "select guid,xmlxbh,zckmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=zc.zckmbh and kj.kjzd=?) as kmmc from cw_xmzcb zc where xmlxbh=(select guid from cw_xmlxb where xmlxbh=? and sszt=?) ";
		return db.queryForList(sql,new Object[] {kjzd,CommonUtil.getBeginText(xmbh),sszt});
		
	}
	/**
	 * 得到sr科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getjjflkm(String xmlxbh) {
		String sql = "select guid,xmlxbh,jjfl,(select kmmc from cw_jjkmb jj where jj.kmbh=jjfl.jjfl) as kmmc from cw_xmjjflb jjfl where xmlxbh=? ";
		return db.queryForList(sql,new Object[] {xmlxbh});
		
	}
	/**
	 * 联想得到jj科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getjjflkm1(String xmbh,String sszt) {
		String sql = "select guid,xmlxbh,jjfl,(select kmmc from cw_jjkmb jj where jj.kmbh=jjfl.jjfl) as kmmc from cw_xmjjflb jjfl where xmlxbh=(select guid from cw_xmlxb where xmlxbh=? and sszt =?) ";
		return db.queryForList(sql,new Object[] {CommonUtil.getBeginText(xmbh),sszt});
		
	}
	  /**
		 * 增加支出
		 */
	    public Object addzc(HttpSession session,Map map){
	    	 String guid=(String) map.get("guid");
	      	 if(guid==null ||guid.length()==0) {
	            	  guid=GenAutoKey.get32UUID();
	   
	      	 }

	    	String czr = LUser.getGuid();
			String sszt = Constant.getztid(session);
			String sql ="insert into cw_xmzcbnew(guid,xmxxbh,zckmbh,czr,czrq,sszt) values(?,?,?,?,sysdate,?)";
			db.update(sql, new Object[] {guid,map.get("xmbh"),map.get("kmbh"),czr,sszt});
			
			return 0;
			
		}
	    /**
	   	 * 增加经济分类
	   	 */
	       public Object addjjfl(HttpSession session,Map map){
	    	   String czr = LUser.getGuid();
	    	   String guid=(String) map.get("guid");
	    	 if(guid==null ||guid.length()==0) {
	          	  guid=GenAutoKey.get32UUID();
	 
	    	 }

	   		String sszt = Constant.getztid(session);
	   		String sql ="insert into cw_xmjjflbnew(guid,xmxxbh,jjfl,czr,czrq,sszt) values(?,?,?,?,sysdate,?)";
	   		db.update(sql, new Object[] {guid,map.get("xmbh"),map.get("kmbh"),czr,sszt});

	   		
	   		return 0;
	   		
	   	}
	       /**
	   	 * 增加收入
	   	 */
	       public Object addsr(HttpSession session,String srkmbhs,String srkmmcs,String yslxs){
	    	String srkmbh[] = srkmbhs.split(",",-1);
	   		String srkmmc[] = srkmmcs.split(",",-1);
	   		String yslx[] = yslxs.split(",",-1);
	   		String czr = LUser.getGuid();
	   		String sszt = Constant.getztid(session);
	   		for(int i=0;i<srkmbh.length;i++) {
	   			String guid=GenAutoKey.get32UUID();
		   		String sql ="insert into cw_xmsrbnew(guid,xmxxbh,srkmbh,czr,czrq,sszt,yslx) values(?,?,?,?,sysdate,?,?)";
		   		db.update(sql, new Object[] {guid,srkmmc[i],srkmbh[i],czr,sszt,yslx[i]});
	   		}
	   		return 0;
	   		
	   	}
       /**
	   	 * 修改收入
	   	 */
	       public Object updatesr(HttpSession session,String srkmbhs,String srkmmcs,String yslxs){
	    	String srkmbh[] = srkmbhs.split(",",-1);
	   		String srkmmc[] = srkmmcs.split(",",-1);
	   		String yslx[] = yslxs.split(",",-1);
	   		for(int i=0;i<srkmbh.length;i++) {
		   		String sql ="update cw_xmsrbnew set srkmbh=? where xmxxbh=? and yslx=?";
		   		db.update(sql, new Object[] {srkmbh[i],srkmmc[i],yslx[i]});
	   		}
	   		return 0;
	   		
	   	}
	       /**
	   	 * 得到sr科目信息
	   	 * @param guid
	   	 * @return
	   	 */
	   	public List<Map<String, Object>> getsrkmById(String guid,String kjzd) {
	   		String sql = " select distinct(select srkmbh from cw_xmsrbnew sr where xmxxbh = ? and yslx='01') jbzcsrkmbh," 
	   					+" (select (select kmmc from cw_kjkmszb kj where kj.kmbh = sr.srkmbh and kjzd = '"+kjzd+"') as kmmc " 
	   					+" from cw_xmsrbnew sr where xmxxbh = ? and yslx='01') jbzckmmc," 
	   					+" (select srkmbh from cw_xmsrbnew sr where xmxxbh = ? and yslx='02') xmzcsrkmbh," 
	   					+" (select (select kmmc from cw_kjkmszb kj where kj.kmbh = sr.srkmbh and kjzd = '"+kjzd+"') as kmmc " 
	   					+" from cw_xmsrbnew sr where xmxxbh = ? and yslx='02') xmzckmmc " 
	   					+" from cw_xmsrbnew sr where sr.xmxxbh = ?";
	   		return db.queryForList(sql,new Object[] {guid,guid,guid,guid,guid});
	   		
	   	}
	   	/**
	   	 * 得到zc科目信息
	   	 * @param guid
	   	 * @return
	   	 */
	   	public List<Map<String, Object>> getszckmById(String guid,String kjzd) {
	   		String sql = "select guid,xmxxbh,zckmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=zc.zckmbh and kjzd='"+kjzd+"') as kmmc from cw_xmzcbnew zc where xmxxbh=? ";
	   		return db.queryForList(sql,new Object[] {guid});
	   		
	   	}
	   	/**
	   	 * 得到sr科目信息
	   	 * @param guid
	   	 * @return
	   	 */
	   	public List<Map<String, Object>> getjjflkmById(String guid) {
	   		String sql = "select guid,xmxxbh,jjfl,(select kmmc from cw_jjkmb jj where jj.kmbh=jjfl.jjfl) as kmmc from cw_xmjjflbnew jjfl where xmxxbh=? ";
	   		return db.queryForList(sql,new Object[] {guid});
	   		
	   	}
	    /**
	     * 删除收入
	     */
	    public int deletesr(String xmlxbh){
	    	 String sql1 = "delete  cw_xmsrbnew where xmxxbh='"+xmlxbh+"'";
	    	
	  	    // Object obj1 = new Object[] {xmlxbh};
	  	    
	  	   return  db.update(sql1);
	    	
	    	
	    }
	    /**删除支出
	     * 
	     * @param xmlxbh
	     * @return
	     */
	    public int deletezc(String xmlxbh){
	    	
	    	 String sql2 = "delete cw_xmzcbnew where xmxxbh='"+xmlxbh+"'";
	    	return  db.update(sql2);
	    }
	    /**
	     * 删除经济分类
	     * @param xmlxbh
	     * @return
	     */
	     public int deletejjfl(String xmlxbh){
	  	     String sql3="delete  cw_xmjjflbnew where xmxxbh='"+xmlxbh+"'";
	  	    // Object obj1 = new Object[] {xmlxbh};
	  	     return db.update(sql3);
	    	 
	    }
	     public List getxx(String guid) {
	 		String sql1 = "select xmbh,sszt,guid from cw_xmjbxxb where guid in ('"+guid+"')";
	 		return db.queryForList(sql1);
	 	}
	 	public int updatebh(String sszt,String xmbh) {
	 		String sql = "update cw_xmjbxxb set xmbh=to_char(xmbh-1,'FM909') where xmbh>'"+xmbh+"' and sszt = '"+sszt+"'";
	 		return db.update(sql);
	 	}
}





















