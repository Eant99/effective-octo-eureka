package com.googosoft.service.ysgl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.omg.CORBA.OBJECT_NOT_EXIST;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

//import com.lowagie.text.List;


@Repository("xmlxDao")
public class XmlxDao extends BaseDao{
	private Logger logger = Logger.getLogger(XmlxDao.class);

	
	/**
	 * 增加
	 * @param dwb
	 * @return
	 */
	public int doAdd(PageData pd,String rybh,HttpSession session){
		String sszt = Constant.getztid(session);
		String xmlxbh = GenAutoKey.makeCkbh("Cw_xmlxb", "xmlxbh", "2");
		String sql = "insert into Cw_xmlxb(guid,xmlxbh,xmlxmc,yslx,sfczzc,xmlb,xmszsm,bz,czr,sszt,fjbh) "
				+ "values(?,'"+xmlxbh+"',?,?,?,?,?,?,?,?,?)";
		Object[] obj = new Object[]{
				pd.getString("guid"),
				pd.getString("xmlxmc"),
				pd.getString("yslx"),
				pd.getString("sfczzc"),
				pd.getString("xmlb"),
				pd.getString("xmszsm"),
				pd.getString("bz"),
				rybh,
				sszt,
				pd.getString("fjbh")
		};
		int i = 0;
		try {  
			i=db.update(sql,obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public int doUpdate(PageData pd,String guid,HttpSession session){
		String sql = "update Cw_xmlxb set xmlxbh=?,xmlxmc=?,yslx=?,sfczzc=?,xmlb=?,xmszsm=?,bz=? where guid = ?";
		Object[] obj = new Object[]{
				pd.getString("xmlxbh"),
				pd.getString("xmlxmc"),
				pd.getString("yslx"),
				pd.getString("sfczzc"),
				pd.getString("xmlb"),
				pd.getString("xmszsm"),
				pd.getString("bz"),
				guid
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
	 * 判断xmlxbh是否重复
	 * @param xmlxbh
	 * @return true为不重复，false为重复doCheckfjbh
	 */
	public boolean doCheckDwbh(String bmh){
		String sql = "select count(1) from Cw_xmlxb where  xmlxbh= ? ";
		String count = db.queryForObject(sql,new Object[]{bmh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	
	public String doCheckfjbh(String bmh){
		String sql = "select count(1) from Cw_fjszb where  fjbh in('"+bmh+"') ";
		String count = db.queryForSingleValue(sql);
				if("0".equals(count)){
			return "0";
		}else{
			return "1";
		}
	}
	/**
	 * 删除收入
	 * @param guid
	 * @return
	 */
	
	public int doDeletesr(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmsrb WHERE xmlxbh in (select c.xmlxbh from Cw_xmlxb c where c.guid in ('"+guid+"'))");
		result = db.update( "DELETE Cw_xmzcb WHERE xmlxbh in (select c.xmlxbh from Cw_xmlxb c where c.guid in ('"+guid+"'))");
		result = db.update( "DELETE Cw_xmjjflb WHERE xmlxbh in (select c.xmlxbh from Cw_xmlxb c where c.guid in ('"+guid+"'))");
		result = db.update( "DELETE Cw_xmlxb WHERE guid in ('"+guid+"')");
		return result;
	}
	public int doDeletexmsr(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmsrb WHERE xmlxbh in  ('"+guid+"')");
		return result;
	}
	public int doDeletexmzc(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmzcb WHERE xmlxbh in  ('"+guid+"')");
		return result;
	}
	public int doDeletejjfl(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmjjflb WHERE xmlxbh in  ('"+guid+"')");
		return result;
	}
	public int doDeletexmlx(String guid) {
		int result = 0;
		result = db.update( "DELETE Cw_xmlxb WHERE guid in ('"+guid+"')");
		return result;
	}
	/**
	 *  验证项目类型是否正被使用
	 */
	public boolean selectXmlx(String guid) {
		String sql = "select count(0) from cw_sys_ywjlb where  ywid in ('"+guid+"')";
		int aa = db.queryForInt(sql);
		if(aa == 0) {
			return true;
		}
		return false;
	}
	/**
	 * 单位信息实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectById(String dwbh) {
		String sql = "select a.dwbh,a.bmh,a.mc,'('||a.bmh||')'||a.mc as dw,nvl(a.jc,'') jc,nvl(a.dz,'') dz,a.dwxz,a.jlrq,a.dwld,"
				+ "nvl((select '('||r.rygh||')'||to_char(r.xm) from %sryb r where r.rybh=a.dwld),'') dwldmc,"
				+ "nvl((select lxdh from %sryb r where r.rybh=a.dwld),'') dwlddh,"
				+ "a.fgld,nvl((select '('||r.rygh||')'||to_char(r.xm) from %sryb r where r.rybh=a.fgld),'') fgldmc,"
				+ "nvl((select lxdh from %sryb r where r.rybh=a.fgld),'') fglddh,"
				+ "a.sjdw,(case a.sjdw when '000000' then '' else (select '('||d.bmh||')'||to_char(d.mc) from %sdwb d where d.dwbh=a.sjdw) end) sjdwmc,"
				+ "a.dwzt,(case a.dwzt when '1' then '正常' when '0' then '禁用' end) dwztmc,a.dwjc,a.mjbz,a.pxxh,a.bmsx,a.bz,a.sysbz,(case a.sysbz when '1' then '否' when '0' then '是' end) sysbzmc,a.sysmj,"
				+ "a.syslx,(select mc from %sdmb where zl='"+Constant.SYSLX+"' and dm=a.syslx) syslxmc,"
				+ "a.syslb,(select mc from %sdmb where zl='"+Constant.SYSLB+"' and dm=a.syslb) syslbmc,"
				+ "a.sysjb,(select mc from %sdmb where zl='"+Constant.SYSJB+"' and dm=a.sysjb) sysjbmc,"
				+ "a.ssxk,(select mc from %sdmb where zl='"+Constant.SSXK+"' and dm=a.ssxk) ssxkmc,"
				+ "a.sysgs,(select mc from %sdmb where zl='"+Constant.SYSGS+"' and dm=a.sysgs) sysgsmc,"
				+"a.dwbb,a.sfxy,a.dwywmc,a.dwywjc,a.sxrq,a.sfst,a.czr,a.czrq,"
				+ "a.jlnf from %sdwb a where dwbh = ?";
		sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dwbh});
	}
	
	/**
	 * 得到实体类
	 * @param guid
	 * @return
	 */
	public Map getInfoById(String guid) {
		String sql = " select guid,xmlxbh,xmlxmc,yslx,xmlb,(select mc from gx_sys_dmb dm where dm.dm=xm.yslx and zl='YSLX' ) yslxmc,sfczzc,xmszsm,bz from cw_xmlxb xm WHERE GUID=?";
		return db.queryForMap(sql,new Object[] {guid});
	}
	
	/**
	 * 得到sr科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getsrkmById(String guid,String kjzd) {
		String sql = " select guid,xmlxbh,srkmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=sr.srkmbh and kjzd='"+kjzd+"') as kmmc from cw_xmsrb sr where xmlxbh=? order by srkmbh";
		return db.queryForList(sql,new Object[] {guid});
		
	}
	/**
	 * 得到zc科目信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getszckmById(String guid,String kjzd) {
		String sql = "select guid,xmlxbh,zckmbh,(select kmmc from cw_kjkmszb kj where kj.kmbh=zc.zckmbh and kjzd='"+kjzd+"') as kmmc from cw_xmzcb zc where xmlxbh=? order by zckmbh";
		return db.queryForList(sql,new Object[] {guid});
		
	}
	/**
	 * 得到经济分类信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getjjflkmById(String guid) {
		String sql = "select guid,xmlxbh,jjfl,(select kmmc from cw_jjkmb jj where jj.kmbh=jjfl.jjfl) as kmmc from cw_xmjjflb jjfl where xmlxbh=? order by jjfl";
		return db.queryForList(sql,new Object[] {guid});
		
	}
	/**
	 * 得到附件设置信息
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getfjszById(String guid) {
		String sql = " select b.guid,b.xmlxbh,b.xmmc,b.xmlb,b.sfbt,b.fjbh,b.sjzt,rownum xh from cw_fjszb b where b.xmlxbh = ? order by b.fjbh";
		return db.queryForList(sql,new Object[] {guid});
	}
	
	
	/**
	 * 通过dwbh获取（bmh）mc格式
	 * @param dwbh
	 * @return
	 */
	public String getDwxx(String dwbh){
		String sql = "select '('||d.bmh||')'||d.mc from %Sdwb d where d.dwbh = ?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql, new Object[]{dwbh});
	}
	/**
	 * 通过dwbh获取（bmh）mc格式
	 * @param dwbh
	 * @return
	 */
	public Object addmorekm(HttpSession session,Map map){
//		String czr = LUser.getGuid();
//		String sszt = Constant.getztid(session);
//		String sql ="insert into cw_xmsrb(guid,xmlxbh,srkmbh,czr,czrq,sszt) values(sys_guid(),?,?,?,sysdate,?)";
//		db.update(sql, new Object[] {map.get("xmlxbh"),map.get("kmbh"),czr,sszt});
//	
		
		return 0;
		
	}
	
    /**
     * 删除收入
     */
    public int deletesr(String xmlxbh){
    	 String sql1 = "delete  cw_xmsrb where xmlxbh='"+xmlxbh+"'";
    	
  	    // Object obj1 = new Object[] {xmlxbh};
  	    
  	   return  db.update(sql1);
    	
    	
    }
    /**删除支出
     * 
     * @param xmlxbh
     * @return
     */
    public int deletezc(String xmlxbh){
    	
    	 String sql2 = "delete cw_xmzcb where xmlxbh='"+xmlxbh+"'";
    	return  db.update(sql2);
    }
    /**
     * 删除经济分类
     * @param xmlxbh
     * @return
     */
     public int deletejjfl(String xmlxbh){
  	     String sql3="delete  cw_xmjjflb where xmlxbh='"+xmlxbh+"'";
  	    // Object obj1 = new Object[] {xmlxbh};
  	     return db.update(sql3);
    	 
    }
     /**
      * 删除附件设置表
      * @param xmlxbh
      * @return
      */
     public int deletefjsz(String xmlxbh){
    	 String sql4="delete  cw_fjszb where xmlxbh='"+xmlxbh+"'";
    	 // Object obj1 = new Object[] {xmlxbh};
    	 return db.update(sql4);
    	 
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
		String sql ="insert into cw_xmzcb(guid,xmlxbh,zckmbh,czr,czrq,sszt) values(?,?,?,?,sysdate,?)";
		db.update(sql, new Object[] {guid,map.get("xmlxbh"),map.get("kmbh"),czr,sszt});
		
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
		String sql ="insert into cw_xmjjflb(guid,xmlxbh,jjfl,czr,czrq,sszt) values(?,?,?,?,sysdate,?)";
		db.update(sql, new Object[] {guid,map.get("xmlxbh"),map.get("kmbh"),czr,sszt});
		return 0;
   	}
    /**
     * 
     * 增加附件设置
     * @param session
     * @param map
     * @return
     */
    public Object addfjsz(HttpSession session,Map map,int j){
	    String czr = LUser.getGuid();
	    String guid=(String) map.get("guid");
		 if(guid==null ||guid.length()==0) {
	      	  guid=GenAutoKey.get32UUID();
		 }
		 System.err.println("___j__"+j);
		String sql = "insert into Cw_fjszb(guid,xmlxbh,xmmc,xmlb,sfbt,fjbh,sjzt) values(sys_guid(),?,?,?,?,?,?)";
		String str = "";
		String str1 = "";
		for(int i = 1;i<=j;i++) {
			System.err.println("___cs_"+i);
			if(Validate.noNull(map.get("sfczzc_"+i))) {
				str = map.get("sfczzc_"+i)+"";
				System.err.println("_____str___"+str);
			}
			if(Validate.noNull(map.get("sjzt_"+i))) {
				str1 = map.get("sjzt_"+i)+"";
				System.err.println("_____str___"+str1);
			}
		}
		db.update(sql, new Object[] {map.get("xmlxbh"),map.get("xmmc"),map.get("xmlb"),str,map.get("fjbh"),str1});
	
		return 0;
   	}
    public Object addfjsz1(HttpSession session,Map map,int j){
    	System.err.println("--------dao1+-+++----");
    	String czr = LUser.getGuid();
    	String guid=(String) map.get("guid");
    	if(guid==null ||guid.length()==0) {
    		guid=GenAutoKey.get32UUID();
    	}
    	System.err.println("___j__"+j);
    	String sql = "insert into Cw_fjszb(guid,xmlxbh,xmmc,xmlb,sfbt,fjbh,sjzt) values(sys_guid(),?,?,?,?,?,?)";
    	db.update(sql, new Object[] {map.get("xmlxbh"),map.get("xmmc"),map.get("xmlb"),map.get("sfczzc_"+j),map.get("fjbh"),map.get("sjzt_"+j)});
    	return 0;
    }
       /**
   	 * 增加收入
   	 */
       public Object addsr(HttpSession session,Map map){
       	   String guid=(String) map.get("guid");
         	 if(guid==null ||guid.length()==0) {
               	  guid=GenAutoKey.get32UUID();
      
         	 }
   		String czr = LUser.getGuid();
   		String sszt = Constant.getztid(session);
   		String sql ="insert into cw_xmsrb(guid,xmlxbh,srkmbh,czr,czrq,sszt) values(?,?,?,?,sysdate,?)";
   		db.update(sql, new Object[] {guid,map.get("xmlxbh"),map.get("kmbh"),czr,sszt});
   		
   		return 0;
   		
   	}
       /**
   	 * 添加收入预算明细信息
   	 * @param dmb
   	 * @return
   	 */
   	public int doAddjwjl(Map map,String kjzd) {
   		String sql = "insert into cw_sys_ywjlb(guid,ywid,czid,czr,czsj,kmlx,zbid,sszt) values(?,(select guid from cw_kjkmszb where kmbh=? and kjzd='"+kjzd+"'),?,?,sysdate,?,?,?)";
   		sql=String.format(sql, SystemSet.gxBz);		
   		int i = 0;
   		try{
   			i = db.update(sql,new Object[]{map.get("guid"),map.get("ywid"),map.get("czid"),map.get("czr"),map.get("kmlx"),map.get("zbid"),map.get("sszt")});
   		}catch (DataAccessException e) {  
   			SQLException sqle = (SQLException) e.getCause();  
   		  //  logger.error("数据库操作失败\n" + sqle);  
   		    return -1;
   		}
   		return i;
   	}
   	public int doAddjwj2(Map map,String kjzd) {
   		String sql = "insert into cw_sys_ywjlb(guid,ywid,czid,czr,czsj,kmlx,zbid,sszt) values(?,(select guid from cw_kjkmszb where kmbh=? and kjzd='"+kjzd+"'),?,?,sysdate,?,?,?)";
   		sql=String.format(sql, SystemSet.gxBz);		
   		int i = 0;
   		try{
   			i = db.update(sql,new Object[]{map.get("guid"),map.get("ywid"),map.get("czid"),map.get("czr"),map.get("kmlx"),map.get("zbid"),map.get("sszt")});
   		}catch (DataAccessException e) {  
   			SQLException sqle = (SQLException) e.getCause();  
   		  //  logger.error("数据库操作失败\n" + sqle);  
   		    return -1;
   		}
   		return i;
   	}
   	public int doAddfjszb(Map map) {
   		String sql = "insert into Cw_fjszb(guid,wid,xmmc,xmlb,sfbt) values(?,?,?,?,?)";
   		int i = 0;
   		try{
   			i = db.update(sql,new Object[]{map.get("guid"),map.get("wid"),map.get("xmmc"),map.get("xmlb"),map.get("sfczzc")});
   		}catch (DataAccessException e) {  
   			SQLException sqle = (SQLException) e.getCause();  
   		    return -1;
   		}
   		return i;
   	}
    /**
   	 * 添加收入预算明细信息
   	 * @param dmb
   	 * @return
   	 */
   	public int doAddjwjl1(Map map) {
   		String sql = "insert into cw_sys_ywjlb(guid,ywid,czid,czr,czsj,kmlx,zbid,sszt) values(?,(select guid from cw_jjkmb where kmbh=?),?,?,sysdate,?,?,?)";
   		sql=String.format(sql, SystemSet.gxBz);		
   		int i = 0;
   		try{
   			i = db.update(sql,new Object[]{map.get("guid"),map.get("ywid"),map.get("czid"),map.get("czr"),map.get("kmlx"),map.get("zbid"),map.get("sszt")});
   		}catch (DataAccessException e) {  
   			SQLException sqle = (SQLException) e.getCause();  
   		  //  logger.error("数据库操作失败\n" + sqle);  
   		    return -1;
   		}
   		return i;
   	}
	public List<Map<String, Object>> getList3(String searchValue,String guid,String sszt) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc, ");
		sql.append("  x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc, ");
		sql.append("  decode(nvl(ysje,0),0,'0.00',(to_char(round(ysje,2),'fm999999999999999999990.00')))ysje,decode(nvl(zfcgje,0),0,'0.00',(to_char(round(zfcgje,2),'fm999999999999999999990.00')))zfcgje,decode(nvl(fzfcgje,0),0,'0.00',(to_char(round(fzfcgje,2),'fm999999999999999999990.00')))fzfcgje,trunc(((zfcgje-zfcgsyje)/zfcgje)* 100,2) AS zfcgbl,trunc(((fzfcgje-fzfcgsyje)/fzfcgje)* 100,2) AS fzfcgbl, ");
		sql.append("  (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc, ");
		sql.append("  x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc, ");
		sql.append("  x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,");
		sql.append("  x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,");
		sql.append("  x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc");
		sql.append("  from Cw_xmjbxxb x left join Cw_xmkzxxb c on c.xmbh = x.xmbh");
		sql.append("  left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z  on z.xmxxbh = x.xmbh");
		sql.append("  left join Cw_xmjjflbnew j  on j.xmxxbh = x.xmbh");
		sql.append(" where x.sszt = '"+sszt+"' and x.fzr='"+LUser.getRybh()+"'");
		if(Validate.noNull(guid)){
			sql.append(" and x.guid in ('"+guid+"') ");
		}
		return db.queryForList(sql.toString());
	}
	public List<Map<String, Object>> getList(String searchValue,PageData pd,String sszt) {
		StringBuffer sql = new StringBuffer();
		String guid=pd.getString("id");
		String xmbh=pd.getString("xmbh");
		String xmmc=pd.getString("xmmc");
		String bmmc=pd.getString("bmmc");
//		sql.append(" select x.guid, (select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh = x.bmbh) as bmbh, x.xmbh,  x.xmdl, ");
//		sql.append("  (select D.MC from gx_sys_dmb d where d.zl = '250'  and d.dm = x.xmdl) xmdlmc, ");
//		sql.append("   x.xmlb,(select D.MC from gx_sys_dmb d where d.zl = '251' and d.dm = x.xmlb) xmlbmc, x.xmmc, ");
//		sql.append("  (select t.xmlxmc from cw_XMLXB t where t.guid = x.xmlx) as xmlx, (select D.MC from gx_sys_dmb d where d.zl = 'XMLX' ");
//		sql.append("  and d.dm = x.xmlx) xmlxmc,  x.fzr, ('(' || x.fzr || ')' || ");
//		sql.append("  (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc, ");
//		sql.append("   x.xmsx,(case xmsx when '01' then '部门经费'  when '02' then '个人经费' else ''  end) xmsxmc,");
//		sql.append("  x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,");
//		sql.append("  x.sfqy, (case sfqy  when '0' then  '未启用' when '1' then '已启用'  else '' end) as sfqymc");
//		sql.append("  from Cw_xmjbxxb x left join Cw_xmkzxxb c on c.xmbh = x.xmbh");
//		sql.append("  left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z  on z.xmxxbh = x.xmbh");
//		sql.append("  left join Cw_xmjjflbnew j  on j.xmxxbh = x.xmbh");
//		sql.append("  select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh, x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,");
//		sql.append("  x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"); 
//		sql.append(" decode(nvl(ysje,0),0,'0.00',(to_char(round(ysje,2),'fm999999999999999999990.00')))ysje,decode(nvl(zfcgje,0),0,'0.00',(to_char(round(zfcgje,2),'fm999999999999999999990.00')))zfcgje,decode(nvl(fzfcgje,0),0,'0.00',(to_char(round(fzfcgje,2),'fm999999999999999999990.00')))fzfcgje,decode(zfcgje,0,0,trunc(((zfcgje - zfcgsyje) / zfcgje) * 100, 2)) AS zfcgbl,decode(fzfcgje,0,trunc(((fzfcgje - fzfcgsyje) / fzfcgje) * 100, 2)) AS fzfcgbl,");
//		sql.append("  (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc," );
//				+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
//		sql.append("  x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"	);		
//		sql.append(" x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc," );
//				+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
//		sql.append("  x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,");
//		sql.append("  x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc ");
//		sql.append("  from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh");
//		sql.append("  left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh");
//		sql.append("  left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"' and x.fzr='"+LUser.getRybh()+"' ");
		sql.append("select * from");
		sql.append(" ( select x.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=x.bmbh  ) as bmbh,('('||dw.dwbh||')'||dw.mc) as bmmc ,x.xmbh,x.xmdl,(select D.MC from gx_sys_dmb d where d.zl='250' and d.dm=x.xmdl)xmdlmc,"
				+ " x.xmlb,(select D.MC from gx_sys_dmb d where d.zl='251' and d.dm=x.xmlb)xmlbmc,x.xmmc,"
				+ " (select t.xmlxmc from cw_XMLXB t  where t.guid=x.xmlx) as xmlx,(select D.MC from gx_sys_dmb d where d.zl='XMLX'and d.dm=x.xmlx)xmlxmc,"
//				+ " x.fzr,((select r.xm from gx_sys_ryb r where r.rybh=x.fzr ))fzrmc,"
				+ " x.fzr,('(' || x.fzr || ')' || (select r.xm from gx_sys_ryb r where r.rybh = x.fzr)) fzrmc,"			
				+ "x.xmsx,(case xmsx when '01' then '部门经费' when '02' then '个人经费' else '' end)xmsxmc,"
//				+ " x.gkbm,((select d.mc from gx_sys_dwb d where d.dwbh=x.gkbm ))gkbmmc,"
				+ " x.gkbm,('(' || x.gkbm || ')' || (select d.mc from gx_sys_dwb d where d.dwbh = x.gkbm)) gkbmmc,"
				+ " x.sfqy,(case sfqy when '0'then '未启用' when '1' then '已启用' else '' end)as sfqymc "
				+ " from Cw_xmjbxxb x left join Cw_xmkzxxb c  on c.xmbh = x.xmbh"
				+ " left join Cw_xmsrbnew s  on s.xmxxbh = x.xmbh left join Cw_xmzcbnew z on z.xmxxbh = x.xmbh"
				+ " left join gx_sys_dwb dw on dw.dwbh=x.bmbh  "
				+ " left join Cw_xmjjflbnew j on j.xmxxbh = x.xmbh where x.sszt='"+sszt+"' and x.fzr = '"+LUser.getRybh()+"' ) k where 1=1");
		if(Validate.noNull(guid)){
			sql.append("  and guid in ('"+guid+"') ");
		}
		//增加搜索框筛选条件
		if(Validate.noNull(xmbh)) {
			sql.append(" and xmbh like '%"+xmbh+"%'");
		}
		if(Validate.noNull(xmmc)) {
			sql.append(" and xmmc like '%"+xmmc+"%'");
		}
		if(Validate.noNull(bmmc)) {
			sql.append(" and bmbh like '%"+bmmc+"%'");
		}
		sql.append(" order by xmbh asc");
		return db.queryForList(sql.toString());
	}
	public List getxx(String guid) {
		String sql1 = "select xmlxbh,sszt,guid from cw_xmlxb where guid in ('"+guid+"')";
		return db.queryForList(sql1);
	}
	public int updatebh(String sszt,String xmlxbh) {
		String sql = "update cw_xmlxb set xmlxbh=to_char(xmlxbh-1,'FM909') where xmlxbh>'"+xmlxbh+"' and sszt = '"+sszt+"'";
		return db.update(sql);
	}
	public boolean checkXmmcExist(PageData pd) {
		String xmmc = pd.getString("xmmc");
		String sql = "select * from cw_xmlxb where xmlxmc = ?";
		if(db.queryForList(sql,xmmc).size() > 0) {
			return true;
		}
		return false;
	}
}
