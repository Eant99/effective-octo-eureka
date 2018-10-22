package com.googosoft.dao.bmyssb;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.hysgl.OA_SHYJB;
import com.googosoft.pojo.sbwx.jcsz.ZC_JDZJINFOR;
import com.googosoft.util.AutoKey;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.util.WindowUtil;

/**鉴定专家信息Dao
 * @author sxl
 */
@Repository("bmyssbDao")
public class BmyssbDao extends BaseDao {
	
	private Logger logger = Logger.getLogger(BmyssbDao.class);
	
	public List PowerModels() {
		String sql = " select dwbh,bmh,mc,(select count(1) from cw_bmyssb a where a.sjdw=t.dwbh) as xjcount,dm from cw_bmyssb t WHERE 1=1 and t.jc=1 ";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	
	public String getxxxx() {
		return db.queryForSingleValue(" select xxmc from cw_xxxxb where rownum = 1 ");
	}
	
	public List GetDwModels(String sjdw) {
		String sql = "Select dwbh,bmh,mc,(select count(1) from cw_bmyssb a where a.sjdw=b.dwbh) as xjcount,dm from cw_bmyssb b where sjdw=? order by bmh";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{sjdw});
	}
	

	/**
	 * 编号查询检查是否重复
	 */
	public boolean doCheckZjbh(ZC_JDZJINFOR zjb){
		String sql="select count(*) from ZC_JDZJINFOR where zjbh=?";
		String a=db.queryForObject(sql, new Object[]{zjb.getZjbh()},String.class);
		if("0".equals(a)){
			return true;
		}else{
			return false;
		}
	}
	
	public int doAddfmb(PageData pd){
		db.update(" delete from CW_FMB f where f.bmmc= '"+CommonUtil.getBeginText(pd.getString("bmmc"))+"'");
		String guid = AutoKey.createDwbh("CW_FMB", "guid", "32");
		String sql1="insert into CW_FMB(GUID,BMMC,YSBM,SQSJ,BMFZR,JBR,BZRQ,nd)values(?,?,?,to_date(?,'yyyy-mm-dd'),?,?,to_date(?,'yyyy-mm-dd'),to_char(sysdate,'yyyy'))";
		Object[] obj1=new Object[]{
				guid,
				CommonUtil.getBeginText(pd.getString("bmmc")),
				pd.getString("ysbm"),
				pd.getString("sqsj"),
				CommonUtil.getBeginText(pd.getString("bmfzr")),
				CommonUtil.getBeginText(pd.getString("jbr")),
				pd.getString("bzrq")
				};
		int i=0;
		try{
			//批量执行sql语句
			ArrayList list  = new ArrayList();
			list.add(sql1);
//			list.add(sql2);
			if(db.batchUpdate(list,obj1)) {
				i=1;
			}
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return i;
	}
	public int doAddjbxxb(PageData pd,String bzid){
		db.update(" delete from CW_JBXXB f where f.dwmc= '"+CommonUtil.getBeginText(pd.getString("dwmc"))+"'");
		String guid = AutoKey.createDwbh("CW_JBXXB", "guid", "32");
		String sql1="insert into CW_JBXXB(GUID,DWMC,DWFZR,CWFZR,TBR,DHHM,JSRS,JZJSRS,WPJSRS,ZYS,BJS,XSRS,nd)values(?,?,?,?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyy'))";
		Object[] obj1=new Object[]{
				guid,
				CommonUtil.getBeginText(pd.getString("dwmc")),
				CommonUtil.getBeginText(pd.getString("dwfzr")),
				CommonUtil.getBeginText(pd.getString("cwfzr")),
				CommonUtil.getBeginText(pd.getString("tbr")),
				pd.getString("dhhm"),
				pd.getString("jsrs"),
				pd.getString("jzjsrs"),
				pd.getString("wpjsrs"),
				pd.getString("zys"),
				pd.getString("bjs"),
				pd.getString("xsrs")
		};
		int i=0;
		try{
			//批量执行sql语句
			ArrayList list  = new ArrayList();
			list.add(sql1);
			if(db.batchUpdate(list,obj1)) {
				i=1;
			}
		}catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return i;
	}
	//----------------------------------- dm=3 收入预算表的删除和添加---------------------------
	public void doDelete(String tbbm) {
		String sqldelete = "DELETE from CW_SRYSB WHERE TBBM in ('"+tbbm+"')";
		db.update(sqldelete);
	}
	public int insertTxrysx(Map<String, Object> infoMap,PageData pd,String bzid,Object tbbm) {
		String guid = GenAutoKey.get32UUID();//AutoKey.createDwbh("CW_srysb", "guid", "32");
		String sql = "INSERT into CW_srysb(GUID,XMMC,YJWCSE,WCSJ,BZ,tbbm,nd,xmid)values(?,?,?,to_date(?,'yyyy-mm-dd'),?,?,to_char(sysdate,'yyyy'),?)";
		Object[] obj=new Object[]{
				guid,
				infoMap.get("xmmc"),
				infoMap.get("yjwcse"),
				infoMap.get("wcsj"),
				infoMap.get("bz"),
				WindowUtil.getText(tbbm+""),
				infoMap.get("xmid")
				};
		int i = 0;
		ArrayList list  = new ArrayList();
		list.add(sql);
		if(db.batchUpdate(list,obj)) {
			i = 1;
		}
		return i;
	}
	
	//----------------------------------- dm=4 专项业务费支出预算表的删除和添加---------------------------
	public void doDeleteZxywfzcysb(String tbbm) {
		String sqldelete = "DELETE from cw_zxywfzcysb WHERE TBBM ='"+CommonUtil.getBeginText(tbbm)+"'";
		db.update(sqldelete);
	}
	public int insertZxywfzcysb(Map<String, Object> infoMap,PageData pd,Object tbbm) {
		String guid =  GenAutoKey.get32UUID();
		String sql = "INSERT into cw_zxywfzcysb(GUID,TBBM,XMMC,JYSM,XMQZSJ,XMJSSJ,XMZTZ,NAPZJS,NYSJYAPS,XMFKSJYQ,JSGC,ZXYWFLHJ,nd,xmid)"
				+ "values(?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?,?,to_char(sysdate,'yyyy'),?)";
		Object[] obj=new Object[]{
				guid,
				CommonUtil.getBeginText(tbbm+""),
				infoMap.get("xmmc"),
				infoMap.get("JYSM"),
				infoMap.get("XMQZSJ"),
				infoMap.get("XMJSSJ"),
				infoMap.get("XMZTZ"),
				infoMap.get("NAPZJS"),
				infoMap.get("NYSJYAPS"),
				infoMap.get("XMFKSJYQ"),
				infoMap.get("jsgc"),
				infoMap.get("ZXYWFLHJ"),
				infoMap.get("xmid")
		};
		int i = 0;
		ArrayList list  = new ArrayList();
		list.add(sql);
		if(db.batchUpdate(list,obj)) {
			i = 1;
		}
		return i;
	}
	
	//-----------------------------------dm=5 维修租赁费 支出预算表 的删除和添加---------------------------
	public void doDeleteWxzlfzcysb(Object tbbm) {
		String sqldelete = "DELETE from cw_wxzlfzcysb WHERE TBBM = '"+CommonUtil.getBeginText(tbbm+"")+"'";
		db.update(sqldelete);
	}
	public int insertWxzlfzcysb(Map<String, Object> infoMap,PageData pd,Object tbbm) {
		String guid = GenAutoKey.get32UUID();//AutoKey.createDwbh("cw_wxzlfzcysb", "guid", "32");
		String sql = "INSERT into cw_wxzlfzcysb(GUID,TBBM,XMMC,XMZYYJJJYSM,XMQZSJ,XMJSSJ,XMZTZ,NAPZJS,NYSJYAPS,XMFKSJYQ,WXZLFLHJ,nd,xmid)"
				+ "values(?,?,?,?,to_date(?,'yyyy-mm-dd'),to_date(?,'yyyy-mm-dd'),?,?,?,?,?,to_char(sysdate,'yyyy'),?)";
		Object[] obj=new Object[]{
				guid,
				CommonUtil.getBeginText(tbbm+""),
				infoMap.get("XMMC"),
				infoMap.get("XMZYYJJJYSM"),
				infoMap.get("XMQZSJ"),
				infoMap.get("XMJSSJ"),
				infoMap.get("XMZTZ"),
				infoMap.get("NAPZJS"),
				infoMap.get("NYSJYAPS"),
				infoMap.get("XMFKSJYQ"),
				infoMap.get("ZXYWFLHJ"),
				infoMap.get("XMID")
		};
		int i = 0;
		ArrayList list  = new ArrayList();
		list.add(sql);
		if(db.batchUpdate(list,obj)) {
			i = 1;
		}
		return i;
	}
	
	//----------------------------------- dm=6 政府采购预算表 的删除和添加---------------------------
		public void doDeleteZfcgysb(String tbbm) {
			String sqldelete = "DELETE from CW_ZFCGYSB WHERE tbbm in ('"+tbbm+"') and type='bgjj'";
			db.update(sqldelete);
		}
		public int insertZfcgysb(Map<String, Object> infoMap,PageData pd,String bzid,Object tbbm) {
			String guid = AutoKey.createDwbh("CW_ZFCGYSB", "guid", "32");
			String sql = "INSERT into CW_ZFCGYSB(GUID,TBBM,PMBM,PMMC,SBFL,DWXYZJSL,PZXEZJSL,JHGZS,DJ,CGYSJE,GHSJYQ,BZ,type,nd)"
					+ "values(?,?,?,?,?,?,?,?,?,?,?,?,'bgjj',to_char(sysdate,'yyyy'))";
			Object[] obj=new Object[]{
					guid,
					CommonUtil.getBeginText(tbbm+""),
					infoMap.get("pmbm"),
					infoMap.get("pmmc"),
					infoMap.get("sbfl"),
//					infoMap.get("ggyq"),
					infoMap.get("dwxyzjsl"),
					infoMap.get("pzxezjsl"),
					infoMap.get("jhpzs"),
					infoMap.get("dj"),
					infoMap.get("cgysje"),
					infoMap.get("ghsjyq"),
					infoMap.get("bz")
					};
		
			int i = 0;
			ArrayList list  = new ArrayList();
			list.add(sql);
			
			if(db.batchUpdate(list,obj)) {
				i = 1;
			}
			return i;
		}
		//----------------------------------- dm=10 政府采购预算表 的删除和添加---------------------------
				public void doDeleteZfcgysb10(String tbbm) {
					String sqldelete = "DELETE from CW_ZFCGYSB WHERE tbbm in ('"+tbbm+"') and type='tysb'";
					db.update(sqldelete);
				}
				public int insertZfcgysb10(Map<String, Object> infoMap,PageData pd,String bzid,Object tbbm) {
					String guid = AutoKey.createDwbh("CW_ZFCGYSB", "guid", "32");
					String sql = "INSERT into CW_ZFCGYSB(GUID,TBBM,PMBM,PMMC,SBFL,DWXYZJSL,PZXEZJSL,JHGZS,DJ,CGYSJE,GHSJYQ,BZ,type,nd)"
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,'tysb',to_char(sysdate,'yyyy'))";
					Object[] obj=new Object[]{
							guid,
							CommonUtil.getBeginText(tbbm+""),
							infoMap.get("PMBM"),
							infoMap.get("PMMC"),
							infoMap.get("SBFL"),
//							infoMap.get("ggyq"),
							infoMap.get("DWXYZJSL"),
							infoMap.get("PZXEZJSL"),
							infoMap.get("JHPZS"),
							infoMap.get("DJ"),
							infoMap.get("CGYSJE"),
							infoMap.get("GHSJYQ"),
							infoMap.get("BZ")
							};
				
					int i = 0;
					ArrayList list  = new ArrayList();
					list.add(sql);
					
					if(db.batchUpdate(list,obj)) {
						i = 1;
					}
					return i;
				}
		
		//----------------------------------- dm=11 专用设备  的删除和添加---------------------------
			public void doDeleteZysb(String tbbm) {
				String sqldelete = "DELETE from CW_ZFCGYSB WHERE tbbm in ('"+tbbm+"') and type='zysb'";
				db.update(sqldelete);
			}
			public int insertZysb(Map<String, Object> infoMap,PageData pd,String bzid,Object tbbm) {
				String guid = AutoKey.createDwbh("CW_ZFCGYSB", "guid", "32");
				String sql = "INSERT into CW_ZFCGYSB(GUID,TBBM,PMBM,PMMC,SBFL,DWXYZJSL,PZXEZJSL,JHGZS,DJ,CGYSJE,GHSJYQ,BZ,type,nd)"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,'zysb',to_char(sysdate,'yyyy'))";
				Object[] obj=new Object[]{
						guid,
						CommonUtil.getBeginText(tbbm+""),
						infoMap.get("PMBM"),
						infoMap.get("PMMC"),
						infoMap.get("SBFL"),
//							infoMap.get("ggyq"),
						infoMap.get("DWXYZJSL"),
						infoMap.get("PZXEZJSL"),
						infoMap.get("JHPZS"),
						infoMap.get("DJ"),
						infoMap.get("CGYSJE"),
						infoMap.get("GHSJYQ"),
						infoMap.get("BZ")
						};
			
				int i = 0;
				ArrayList list  = new ArrayList();
				list.add(sql);
				
				if(db.batchUpdate(list,obj)) {
					i = 1;
				}
				return i;
			}
			
			//----------------------------------- dm9,10,11+3 政府采购明细  的删除和添加---------------------------
			
			public int insertZfcgmxb(PageData pd, String bzid,Object jsonByZb,Object jsonByZb1,Object jsonByZb2,Object jsonByZb3,Object jsonByZb4,Object jsonByZb5,Object xmid,String type) {
				String sql="insert into CW_ZFCGMXB(GUID,CGDW,XMMC,XMQQDYFZR,DWYSBM,"
						+ "LXFS,TBBM,ND,TYPE,xmid)"
						+ "values(sys_guid(),?,?,?,?,?,?,to_char(sysdate,'yyyy'),?,?)";
				Object[] obj=new Object[]{
						jsonByZb,
						jsonByZb1,
						jsonByZb2,
						jsonByZb3,
						jsonByZb4,
						CommonUtil.getBeginText(jsonByZb5+""),
						type,xmid
						
						};
				int i=0;
				try{
					i=db.update(sql,obj);
				}catch (DataAccessException e) {
					SQLException sqle = (SQLException) e.getCause();
					logger.error("数据库操作失败\n" + sqle);
					return -1;
				}
				return i;
				
			}
			public int insertZfcgmxmxb(Map<String, Object> infoMap,PageData pd, Object tbbm,String type) {
				String sql="insert into CW_ZFCGMXMXB(GUID,XH,FXMC,GDQJ,JGCSQJ,"
						+ "ZXDW,LXR,DH,TYPE,TBBM,ND)"
						+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyy'))";
				Object[] obj=new Object[]{
						infoMap.get("xh"),
						infoMap.get("flmc"),
						infoMap.get("gdqj"),
						infoMap.get("jgcsqj"),
						infoMap.get("zxdw"),
						infoMap.get("lxr"),
						infoMap.get("dh"),
						type,
						LUser.getDwbh()
					
				
						
				};
				int i=0;
				try{
					i=db.update(sql,obj);
				}catch (DataAccessException e) {
					SQLException sqle = (SQLException) e.getCause();
					logger.error("数据库操作失败\n" + sqle);
					return -1;
				}
				return i;
				
			}
			public int insertZfcgmxb2(PageData pd, String bzid,Object jsonByZb,Object jsonByZb1,Object jsonByZb2,Object jsonByZb3,Object jsonByZb4,Object jsonByZb5) {
				String sql="insert into CW_ZFCGMXB(GUID,CGDW,XMMC,XMQQDYFZR,DWYSBM,"
						+ "LXFS,TBBM,ND,TYPE,xmid)"
						+ "values(sys_guid(),?,?,?,?,?,?,to_char(sysdate,'yyyy'),?,?)";
				Object[] obj=new Object[]{
						jsonByZb,
						jsonByZb1,
						jsonByZb2,
						jsonByZb3,
						jsonByZb4,
						CommonUtil.getBeginText(jsonByZb5+""),
						"tysb"
						
				};
				int i=0;
				try{
					i=db.update(sql,obj);
				}catch (DataAccessException e) {
					SQLException sqle = (SQLException) e.getCause();
					logger.error("数据库操作失败\n" + sqle);
					return -1;
				}
				return i;
				
			}
			public int insertZfcgmxmxb2(Map<String, Object> infoMap,PageData pd, Object tbbm) {
				System.err.println("---------tbbm"+tbbm);
				System.err.println("infoMap=========="+infoMap);
				String sql="insert into CW_ZFCGMXMXB(GUID,XH,FXMC,GDQJ,JGCSQJ,"
						+ "ZXDW,LXR,DH,TYPE,TBBM,ND)"
						+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyy'))";
				System.err.println("	LUser.getDwid()==="+LUser.getDwid());
				Object[] obj=new Object[]{
						infoMap.get("xh"),
						infoMap.get("flmc"),
						infoMap.get("gdqj"),
						infoMap.get("jgcsqj"),
						infoMap.get("zxdw"),
						infoMap.get("lxr"),
						infoMap.get("dh"),
						"tysb",
						LUser.getDwbh()
						
						
						
				};
				int i=0;
				try{
					i=db.update(sql,obj);
				}catch (DataAccessException e) {
					SQLException sqle = (SQLException) e.getCause();
					logger.error("数据库操作失败\n" + sqle);
					return -1;
				}
				return i;
				
			}
			public int insertZfcgmxb3(PageData pd, String bzid,Object jsonByZb,Object jsonByZb1,Object jsonByZb2,Object jsonByZb3,Object jsonByZb4,Object jsonByZb5) {
				String sql="insert into CW_ZFCGMXB(GUID,CGDW,XMMC,XMQQDYFZR,DWYSBM,"
						+ "LXFS,TBBM,ND,TYPE)"
						+ "values(sys_guid(),?,?,?,?,?,?,to_char(sysdate,'yyyy'),?)";
				Object[] obj=new Object[]{
						jsonByZb,
						jsonByZb1,
						jsonByZb2,
						jsonByZb3,
						jsonByZb4,
						CommonUtil.getBeginText(jsonByZb5+""),
						"zysb"
						
				};
				int i=0;
				try{
					i=db.update(sql,obj);
				}catch (DataAccessException e) {
					SQLException sqle = (SQLException) e.getCause();
					logger.error("数据库操作失败\n" + sqle);
					return -1;
				}
				return i;
				
			}
			public int insertZfcgmxmxb3(Map<String, Object> infoMap,PageData pd, Object tbbm) {
				System.err.println("---------tbbm"+tbbm);
				System.err.println("infoMap=========="+infoMap);
				String sql="insert into CW_ZFCGMXMXB(GUID,XH,FXMC,GDQJ,JGCSQJ,"
						+ "ZXDW,LXR,DH,TYPE,TBBM,ND)"
						+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyy'))";
				System.err.println("	LUser.getDwid()==="+LUser.getDwid());
				Object[] obj=new Object[]{
						infoMap.get("xh"),
						infoMap.get("flmc"),
						infoMap.get("gdqj"),
						infoMap.get("jgcsqj"),
						infoMap.get("zxdw"),
						infoMap.get("lxr"),
						infoMap.get("dh"),
						"zysb",
						LUser.getDwbh()
						
						
						
				};
				int i=0;
				try{
					i=db.update(sql,obj);
				}catch (DataAccessException e) {
					SQLException sqle = (SQLException) e.getCause();
					logger.error("数据库操作失败\n" + sqle);
					return -1;
				}
				return i;
				
			}
			
			
			public int insertZfzgmx(Map<String, Object> infoMap,PageData pd,String bzid,Object tbbm) {
				String guid = AutoKey.createDwbh("CW_ZFCGYSB", "guid", "32");
				String sql = "INSERT into CW_ZFCGYSB(GUID,TBBM,PMBM,PMMC,SBFL,DWXYZJSL,PZXEZJSL,JHGZS,DJ,CGYSJE,GHSJYQ,BZ,type,nd)"
						+ "values(?,?,?,?,?,?,?,?,?,?,?,?,'zysb',to_char(sysdate,'yyyy'))";
				Object[] obj=new Object[]{
						guid,
						CommonUtil.getBeginText(tbbm+""),
						infoMap.get("PMBM"),
						infoMap.get("PMMC"),
						infoMap.get("SBFL"),
//							infoMap.get("ggyq"),
						infoMap.get("DWXYZJSL"),
						infoMap.get("PZXEZJSL"),
						infoMap.get("JHPZS"),
						infoMap.get("DJ"),
						infoMap.get("CGYSJE"),
						infoMap.get("GHSJYQ"),
						infoMap.get("BZ")
						};
			
				int i = 0;
				ArrayList list  = new ArrayList();
				list.add(sql);
				
				if(db.batchUpdate(list,obj)) {
					i = 1;
				}
				return i;
			}
		
		
	//-----------------------------------dm=7 财政支出绩效目标申报表 的删除和添加---------------------------
		public void doDeleteCzzcjxmb(String guid) {
			String sqldelete = "DELETE from CW_CZZCJXMBSQB WHERE tbbm ='"+guid+"' and nd=to_char(sysdate,'yyyy')";
			db.update(sqldelete);
		}
		public int insertCzzcjxmb(Map<String, Object> infoMap,PageData pd,String guid) {
			String sql = "INSERT into CW_XMSSJDJHB(GUID,XMSSNR,KSSJ,WCSJ,FKEY)"
					+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?)";
			Object[] obj=new Object[]{
					infoMap.get("XMSSNR"),
					infoMap.get("KSSJ"),
					infoMap.get("WCSJ"),
					pd.getString("guid")
					};
			return db.update(sql,obj);
		}
		public int doAddczzcjx(PageData pd,Object jsonStr,Object tbbm,Object zgbm,Object xmssdw,Object xmfzr,
				Object zgbmbm,Object lxdh,Object xmlb,Object xmlx,Object kssj,
				Object jssj,Object zjze,Object czbk,Object sysr,Object jyxsr,Object qt,
				Object csyjjsm,Object xmdwzngs,Object xmgkzynrjyt,Object xmlxdyj,
				Object xmsbdkxxhbyx,Object cqmb,Object ndmb,
				Object slzb1,Object slzb2,Object slzb3,Object slzb4,Object slzb5,Object slzb6,
				Object zlzb1,Object zlzb2,Object zlzb3,Object zlzb4,Object zlzb5,Object zlzb6,
				Object sxzb1,Object sxzb2,Object sxzb3,Object sxzb4,Object sxzb5,Object sxzb6,
				Object cbzb1,Object cbzb2,Object cbzb3,Object cbzb4,Object cbzb5,Object cbzb6,
				
				Object jjxyzb1,Object jjxyzb2,Object jjxyzb3,Object jjxyzb4,Object jjxyzb5,Object jjxyzb6,
				Object shxyzb1,Object shxyzb2,Object shxyzb3,Object shxyzb4,Object shxyzb5,Object shxyzb6,
				Object stxyzb1,Object stxyzb2,Object stxyzb3,Object stxyzb4,Object stxyzb5,Object stxyzb6,
				Object kcxyxzb1,Object kcxyxzb2,Object kcxyxzb3,Object kcxyxzb4,Object kcxyxzb5,Object kcxyxzb6,
				
				Object ndslzb1,Object ndslzb2,Object ndslzb3,Object ndslzb4,Object ndslzb5,Object ndslzb6,
				Object ndzlzb1,Object ndzlzb2,Object ndzlzb3,Object ndzlzb4,Object ndzlzb5,Object ndzlzb6,
				Object ndsxzb1,Object ndsxzb2,Object ndsxzb3,Object ndsxzb4,Object ndsxzb5,Object ndsxzb6,
				Object ndcbzb1,Object ndcbzb2,Object ndcbzb3,Object ndcbzb4,Object ndcbzb5,Object ndcbzb6,
				
				Object ndjjxyzb1,Object ndjjxyzb2,Object ndjjxyzb3,Object ndjjxyzb4,Object ndjjxyzb5,Object ndjjxyzb6,
				Object ndshxyzb1,Object ndshxyzb2,Object ndshxyzb3,Object ndshxyzb4,Object ndshxyzb5,Object ndshxyzb6,
				Object ndstxyzb1,Object ndstxyzb2,Object ndstxyzb3,Object ndstxyzb4,Object ndstxyzb5,Object ndstxyzb6,
				Object ndkcxyxzb1,Object ndkcxyxzb2,Object ndkcxyxzb3,Object ndkcxyxzb4,Object ndkcxyxzb5,Object ndkcxyxzb6,
				
				Object shgz1,Object shgz2,Object shgz3,Object shgz4,
				Object shgz5,Object shgz6,Object shgz7,Object shgz8,Object qtwt,Object xmid
				){
			String guid = GenAutoKey.get32UUID();//AutoKey.createDwbh("CW_CZZCJXMBSQB", "guid", "32");
			String sql="insert into CW_CZZCJXMBSQB"
					+ "(GUID,XMMC,XMLB,ZGBM,ZGBMBM,"
					+ "XMSSDW,XMFZR,LXDH,XMLX,KSSJ,WCSJ,"
					+ "ZJZE,CZBK,SYSR,JYXSR,QT,"
					+ "CSYJJSM,XMDWZNMS,XMGKZYNRJYT,XMLXDYJ,XMSBDKXXHBYX,"
					+ " CQMB,NDMB,"
					+ " SLZB1,SLZB2,SLZB3,SLZB4,SLZB5,SLZB6,"
					+ " zlzb1,zlzb2,zlzb3,zlzb4,zlzb5,zlzb6,"
					+ " sxzb1,sxzb2,sxzb3,sxzb4,sxzb5,sxzb6,"
					+ " cbzb1,cbzb2,cbzb3,cbzb4,cbzb5,cbzb6,"
					+ " jjxyzb1,jjxyzb2,jjxyzb3,jjxyzb4,jjxyzb5,jjxyzb6,"
					+ " shxyzb1,shxyzb2,shxyzb3,shxyzb4,shxyzb5,shxyzb6,"
					+ " stxyzb1,stxyzb2,stxyzb3,stxyzb4,stxyzb5,stxyzb6,"
					+ " kcxyxzb1,kcxyxzb2,kcxyxzb3,kcxyxzb4,kcxyxzb5,kcxyxzb6,"
					+ " ndslzb1,ndslzb2,ndslzb3,ndslzb4,ndslzb5,ndslzb6,"
					+ " ndzlzb1,ndzlzb2,ndzlzb3,ndzlzb4,ndzlzb5,ndzlzb6,"
					+ " ndsxzb1,ndsxzb2,ndsxzb3,ndsxzb4,ndsxzb5,ndsxzb6,"
					+ " ndcbzb1,ndcbzb2,ndcbzb3,ndcbzb4,ndcbzb5,ndcbzb6,"
					+ " ndjjxyzb1,ndjjxyzb2,ndjjxyzb3,ndjjxyzb4,ndjjxyzb5,ndjjxyzb6,"
					+ " ndshxyzb1,ndshxyzb2,ndshxyzb3,ndshxyzb4,ndshxyzb5,ndshxyzb6,"
					+ " ndstxyzb1,ndstxyzb2,ndstxyzb3,ndstxyzb4,ndstxyzb5,ndstxyzb6,"
					+ " ndkcxyxzb1,ndkcxyxzb2,ndkcxyxzb3,ndkcxyxzb4,ndkcxyxzb5,ndkcxyxzb6,"
					+ " shgz1,shgz2,shgz3,shgz4,shgz5,shgz6,shgz7,shgz8,qtwt,nd,tbbm,xmid"
					+ " )"
					+ "values(?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,?,?,?,"
					+ "?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,"
					+ "?,?,?,?,?,?,?,?,?,to_char(sysdate,'yyyy'),?,?"
					+ ")";
			Object[] obj=new Object[]{
					guid,
					jsonStr,
					xmlb,
					zgbm,
					zgbmbm,
					xmssdw,
					xmfzr,
					lxdh,
					xmlx,
					kssj,
					jssj,
					zjze,
					czbk,
					sysr,
					jyxsr,
					qt,
					csyjjsm,
					xmdwzngs,
					xmgkzynrjyt,
					xmlxdyj,
					xmsbdkxxhbyx,
					cqmb,ndmb,
					slzb1,slzb2,slzb3,slzb4,slzb5,slzb6,
					zlzb1,zlzb2,zlzb3,zlzb4,zlzb5,zlzb6,
					sxzb1,sxzb2,sxzb3,sxzb4,sxzb5,sxzb6,
					cbzb1,cbzb2,cbzb3,cbzb4,cbzb5,cbzb6,
					
					jjxyzb1,jjxyzb2,jjxyzb3,jjxyzb4,jjxyzb5,jjxyzb6,
					shxyzb1,shxyzb2,shxyzb3,shxyzb4,shxyzb5,shxyzb6,
					stxyzb1,stxyzb2,stxyzb3,stxyzb4,stxyzb5,stxyzb6,
					kcxyxzb1,kcxyxzb2,kcxyxzb3,kcxyxzb4,kcxyxzb5,kcxyxzb6,
					
					ndslzb1,ndslzb2,ndslzb3,ndslzb4,ndslzb5,ndslzb6,
					ndzlzb1,ndzlzb2,ndzlzb3,ndzlzb4,ndzlzb5,ndzlzb6,
					ndsxzb1,ndsxzb2,ndsxzb3,ndsxzb4,ndsxzb5,ndsxzb6,
					ndcbzb1,ndcbzb2,ndcbzb3,ndcbzb4,ndcbzb5,ndcbzb6,
					
					ndjjxyzb1,ndjjxyzb2,ndjjxyzb3,ndjjxyzb4,ndjjxyzb5,ndjjxyzb6,
					ndshxyzb1,ndshxyzb2,ndshxyzb3,ndshxyzb4,ndshxyzb5,ndshxyzb6,
					ndstxyzb1,ndstxyzb2,ndstxyzb3,ndstxyzb4,ndstxyzb5,ndstxyzb6,
					ndkcxyxzb1,ndkcxyxzb2,ndkcxyxzb3,ndkcxyxzb4,ndkcxyxzb5,ndkcxyxzb6,
					
					shgz1,shgz2,shgz3,shgz4,shgz5,shgz6,shgz7,shgz8,qtwt,CommonUtil.getBeginText(tbbm+""),xmid
					};
			int i=0;
			try{
				i=db.update(sql,obj);
			}catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				logger.error("数据库操作失败\n" + sqle);
				return -1;
			}
			return i;
		}
	
	//----------------------------------dm=8  日常公用支出预算表的删除和添加---------------------------
	public void doDeleteRcgyzfysb(Object tbbm) {
		String sqldelete = "DELETE from CW_RCGYZCYSB WHERE TBBM = '"+CommonUtil.getBeginText(tbbm+"")+"'";
		db.update(sqldelete);
	}
	public int insertRcgyzfysb(Map<String, Object> infoMap,PageData pd,Object tbbm) {
		String guid = AutoKey.createDwbh("CW_RCGYZCYSB", "guid", "32");
		String sql = "INSERT into CW_RCGYZCYSB(GUID,FYMC,ZYSM,NYSZE,NYSJYAPS,JSGC,tbbm,nd)"
				+ "values(?,?,?,?,?,?,?,to_char(sysdate,'yyyy'))";
		Object[] obj=new Object[]{
				guid,
				infoMap.get("fymc"),
				infoMap.get("zysm"),
				infoMap.get("nysze"),
				infoMap.get("nysjyaps"),
				infoMap.get("jsgc"),
				CommonUtil.getBeginText(tbbm+"")
				};
		
		int i = 0;
		ArrayList list  = new ArrayList();
		list.add(sql);
		if(db.batchUpdate(list,obj)) {
			i = 1;
		}
		return i;
	}
		
		
	//删除所有
	public int doDeleteAll(String userDwbh,int year) {
		String sqldelete = "DELETE from cw_fmb WHERE BMMC ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete2 = "DELETE from cw_jbxxb WHERE DWMC ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete3 = "DELETE from cw_srysb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete4 = "DELETE from cw_zxywfzcysb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete5 = "DELETE from cw_wxzlfzcysb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete6 = "DELETE from cw_zfcgysb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete7 = "DELETE from cw_czzcjxmbsqb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete8 = "DELETE from cw_rcgyzcysb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete9 = "DELETE from cw_zfcgmxb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		String sqldelete10 = "DELETE from cw_zfcgmxmxb WHERE TBBM ='"+userDwbh+"' and nd='"+year+"'";
		ArrayList list  = new ArrayList();
		list.add(sqldelete);
		list.add(sqldelete2);
		list.add(sqldelete3);
		list.add(sqldelete4);
		list.add(sqldelete5);
		list.add(sqldelete6);
		list.add(sqldelete7);
		list.add(sqldelete8);
		list.add(sqldelete9);
		list.add(sqldelete10);
		int flag = 0;
		Boolean bool = db.batchUpdate(list);
		if(bool) {
			flag =1;
		}
		return flag;
	}
		
	public String getBmmcByDwbh(String userDwbh) {
		return db.queryForSingleValue(" select ('('||d.dwbh||')'||d.mc) bmmc from gx_sys_dwb d where d.dwbh='"+userDwbh+"' ");
	}
	
	public String getRymcByRybh(String userRybh) {
		return db.queryForSingleValue(" select ('('||d.rybh||')'||d.xm) rymc from gx_sys_ryb d where d.rybh='"+userRybh+"' ");
	}
	
	//----------------------------- dm=4-------------------------------------
	public int doUpdatefmb(PageData pd) {
		String sql="update CW_FMB set bmmc=?,SQSJ=?,ysbm=?,bzrq=to_date(?,'yyyy-mm-dd'),bmfzr=?,jbr=to_date(?,'yyyy-mm-dd') where guid=?";
		Object[] obj=new Object[]{
				pd.getString("bmmc"),
				pd.getString("sqsj"),
				pd.getString("ysbm"),
				pd.getString("bzrq"),
				pd.getString("bmfzr"),
				pd.getString("jbr"),
				pd.getString("guid")
				};
		int i=0;
		try{
			i=db.update(sql,obj);
		}catch(DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return i;
	}
	public int doUpdatejbxxb(PageData pd) {
		String sql="update CW_FMB set DWMC=?,DWFZR=?,CWFZR=?,TBR=?,DHHM=?,JSRS=?,JZJSRS=?,WPJSRS=?,ZYS=?,BJS=?,XSRS=? where guid=?";
		Object[] obj=new Object[]{
				pd.getString("DWMC"),
				pd.getString("DWFZR"),
				pd.getString("CWFZR"),
				pd.getString("TBR"),
				pd.getString("DHHM"),
				pd.getString("JSRS"),
				pd.getString("JZJSRS"),
				pd.getString("WPJSRS"),
				pd.getString("ZYS"),
				pd.getString("BJS"),
				pd.getString("XSRS"),
				pd.getString("guid")
				};
		int i=0;
		try{
			i=db.update(sql,obj);
		}catch(DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return i;
	}
	public int doUpdatesrysb(PageData pd) {
		String sql="update CW_FMB set XMMC=?,YJWCSE=?,WCSJ=?,BZ=? where guid=?";
		Object[] obj=new Object[]{
				CommonUtil.getBeginText(pd.getString("xmmc")),
				pd.getString("yjwcse"),
				pd.getString("wcsj"),
				pd.getString("bz"),
				pd.getString("guid")
				};
		int i=0;
		try{
			i=db.update(sql,obj);
		}catch(DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			return -1;
		}
		return i;
	}
	
	public List getxlkinfo() {
		return db.queryForList(" select b.dm,b.mc from gx_sys_dmb b where b.zl='yszb' ");
	}
	public List getLengthBydm(String dm) {
		return db.queryForList(" select b.ejzb from cw_yszbb b where b.dm='"+dm+"'"); 
	}
	public void doSubmit(String dwbh) {
		int num = db.update("update cw_fmb b set b.shzt='1' where b.nd=to_char(sysdate,'yyyy') and b.bmmc='"+dwbh+"'");
	}
//----------------------------------------政府支出回显
	public Map getMxObjectById(String guid) {
		String sql=" select T.GUID,T.CGDW,T.XMMC,T.XMQQDYFZR,T.DWYSBM,T.LXFS,xmid "+
				"  from cw_zfcgmxb t " + 
				" where t.tbbm = '"+guid+"' and type='bgjj'" ;
		return db.queryForMap(sql);
	}
	public List getMxObjectsById(String guid) {
		String sql=" select t.guid,t.xh,t.fxmc,t.gdqj,t.jgcsqj,t.zxdw,t.lxr,t.dh "+
				"  from cw_zfcgmxmxb t " + 
				" where t.tbbm = '"+guid+"' and type='bgjj'" ;
		return db.queryForList(sql);
	}
	//(通用设备)
	public Map getMxObjectById2(String guid) {
		String sql=" select T.GUID,T.CGDW,T.XMMC,T.XMQQDYFZR,T.DWYSBM,T.LXFS,xmid "+
				"  from cw_zfcgmxb t " + 
				" where t.tbbm = '"+guid+"' and type='tysb'" ;
		return db.queryForMap(sql);
	}
	public List getMxObjectsById2(String guid) {
		String sql=" select t.guid,t.xh,t.fxmc,t.gdqj,t.jgcsqj,t.zxdw,t.lxr,t.dh "+
				"  from cw_zfcgmxmxb t " + 
				" where t.tbbm = '"+guid+"' and type='tysb'" ;
		return db.queryForList(sql);
	}
	public Map getMxObjectById3(String guid) {
		String sql=" select T.GUID,T.CGDW,T.XMMC,T.XMQQDYFZR,T.DWYSBM,T.LXFS,xmid "+
				"  from cw_zfcgmxb t " + 
				" where t.tbbm = '"+guid+"' and type='zysb'" ;	
		return db.queryForMap(sql);
	}
	public List getMxObjectsById3(String guid) {
		String sql=" select t.guid,t.xh,t.fxmc,t.gdqj,t.jgcsqj,t.zxdw,t.lxr,t.dh "+
				"  from cw_zfcgmxmxb t " + 
				" where t.tbbm = '"+guid+"' and type='zysb'" ;
		return db.queryForList(sql);
	}
	
	
	
	public Map getObjectById1(String guid,String year) {
		String sql=" select t.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=t.bmmc) bmmc,to_char(t.sqsj,'yyyy-mm-dd') sqsj,"
				+ " t.ysbm,to_char(t.bzrq,'yyyy-mm-dd') bzrq,"
				+ "(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.bmfzr) bmfzr,"
				+ "(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.jbr)jbr from cw_fmb t "
				+ "where t.bmmc='"+guid+"' and t.nd='"+year+"'";
		return db.queryForMap(sql);
	}
	public Map getObjectById2(String guid,String year) {
		String sql=" select t.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=t.dwmc)dwmc,\r\n" + 
				"       t.dwfzr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.dwfzr)dwfzrmc,\r\n" + 
				"       t.cwfzr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.cwfzr)cwfzrmc,\r\n" + 
				"       t.tbr,(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.tbr)tbrmc,\r\n" + 
				"       t.dhhm,t.jsrs,t.jzjsrs,t.wpjsrs,t.zys,t.bjs,t.xsrs\r\n" + 
				"  from cw_jbxxb t " + 
				" where t.dwmc = '"+guid+"' and t.nd='"+year+"'";
		return db.queryForMap(sql);
	}
	public List getObjectById3(String guid,String year) {
		String sql=" select t.guid,t.bz,t.xmmc,to_char(t.wcsj,'yyyy-mm-dd')wcsj,to_char(t.yjwcse,'FM99999999999999.0000') yjwcse,t.tbbm,t.hj,xmid "+
				"  from cw_srysb t " + 
				" where t.tbbm = '"+guid+"'" ;
		return db.queryForList(sql);
	}
	public List getZxywfzcysById(String guid,String year){
		String sql = " SELECT t.xmmc,t.jysm,to_char(t.xmqzsj,'yyyy-mm-dd')xmqzsj,t.xmztz,\r\n" + 
				"t.napzjs,t.nysjyaps,t.xmfksjyq,t.jsgc,t.zxywflhj,to_char(t.xmjssj,'yyyy-mm-dd')xmjssj,xmid FROM cw_zxywfzcysb t WHERE t.TBBM='"+guid+"' and t.nd='"+year+"'";
		List list = db.queryForList(sql);
		return list;
	}
	public List getWxzlfById(String guid,String year) {
		String sql=" select t.tbbm,t.xmmc,t.xmzyyjjjysm,to_char(t.xmqzsj,'yyyy-mm-dd')xmqzsj,t.xmztz,t.napzjs,t.nysjyaps,t.xmfksjyq,t.wxzlflhj,to_char(t.xmjssj,'yyyy-mm-dd')xmjssj,t.xmid from cw_wxzlfzcysb t WHERE t.TBBM='"+guid+"' and t.nd='"+year+"'";
		return db.queryForList(sql);
	}
	public Map getObjectById6(String guid,String year) {
		String sql=" select t.guid,(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=t.bmmc) bmmc,to_char(t.sqsj,'yyyy-mm-dd') sqsj,"
				+ "(select '('||d.dwbh||')'||d.mc from gx_sys_dwb d where d.dwbh=t.ysbm) ysbm,to_char(t.bzrq,'yyyy-mm-dd') bzrq,"
				+ "(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.bmfzr) bmfzr,"
				+ "(select '('||r.rybh||')'||r.xm from gx_sys_ryb r where r.rybh=t.jbr)jbr from cw_fmb t where t.guid=? and t.nd=?";
		return db.queryForMap(sql,new Object[]{guid,year});
	}
	public Map getObjectById7(String guid,String year) {
		String sql=" select * from CW_CZZCJXMBSQB t where t.tbbm=? and t.nd=to_char(sysdate,'yyyy')";
		return db.queryForMap(sql,new Object[]{guid});
	}
	public List getBmyssbById(String guid) {
		String sql= "select t.guid,t.fymc,t.zysm,t.nysze,t.nysjyaps,t.jsgc,t.tbbm from cw_rcgyzcysb t " ;
		return db.queryForList(sql);
	}
	public List getObjectById9(String guid) {
		String sql=" select t.guid,t.bz,t.ghsjyq,t.tbbm,t.pmbm,t.pmmc,t.sbfl,t.dwxyzjsl,t.pzxezjsl,t.dj,t.cgysje,t.jhgzs "+
				"  from CW_ZFCGYSB t " + 
				" where t.tbbm = '"+guid+"' and type='bgjj'" ;
		return db.queryForList(sql);
	}
	public List getObjectById10(String guid) {
		String sql=" select t.guid,t.bz,t.ghsjyq,t.tbbm,t.pmbm,t.pmmc,t.sbfl,t.dwxyzjsl,t.pzxezjsl,t.dj,t.cgysje,t.jhgzs "+
				"  from CW_ZFCGYSB t " + 
				" where t.tbbm = '"+guid+"' and type='tysb'" ;
		return db.queryForList(sql);
	}
	public List getObjectById11(String guid) {
		String sql=" select t.guid,t.bz,t.ghsjyq,t.tbbm,t.pmbm,t.pmmc,t.sbfl,t.dwxyzjsl,t.pzxezjsl,t.dj,t.cgysje,t.jhgzs "+
				"  from CW_ZFCGYSB t " + 
				" where t.tbbm = '"+guid+"' and type='zysb'" ;
		return db.queryForList(sql);
	}
	
	public int checkIsAdd(String dwbh,int year) {
		return db.queryForInt(" select count(1) from cw_fmb f full join cw_jbxxb j on f.BMMC=j.DWMC where f.BMMC='"+dwbh+"' and f.nd='"+year+"' ");
	}
	
	public int checkIsAdd2(String dwbh,int year) {
		return db.queryForInt(" select count(1) from cw_fmb f full join cw_jbxxb j on f.BMMC=j.DWMC where f.BMMC='"+dwbh+"' and f.nd='"+year+"' and shzt is not null and shzt<>0 ");
	}
	
	public void doDeleteZfcgmxb(String tbbm) {
		String sqldelete = "DELETE from CW_ZFCGMXB WHERE tbbm in ('"+LUser.getDwbh()+"') and type='bgjj'";
		db.update(sqldelete);
	}
	public void doDeleteZfcgmxmxb(String tbbm) {
		String sqldelete = "DELETE from CW_ZFCGMXMXB WHERE tbbm in ('"+LUser.getDwbh()+"') and type='bgjj'";
		db.update(sqldelete);
	}
	public void doDeleteZfcgmxb2(String tbbm) {
		String sqldelete = "DELETE from CW_ZFCGMXB WHERE tbbm in ('"+LUser.getDwbh()+"') and type='tysb'";
		db.update(sqldelete);
	}
	public void doDeleteZfcgmxmxb2(String tbbm) {
		String sqldelete = "DELETE from CW_ZFCGMXMXB WHERE tbbm in ('"+LUser.getDwbh()+"') and type='tysb'";
		db.update(sqldelete);
	}
	public void doDeleteZfcgmxb3(String tbbm) {
		String sqldelete = "DELETE from CW_ZFCGMXB WHERE tbbm in ('"+LUser.getDwbh()+"') and type='zysb'";
		db.update(sqldelete);
	}
	public void doDeleteZfcgmxmxb3(String tbbm) {
		String sqldelete = "DELETE from CW_ZFCGMXMXB WHERE tbbm in ('"+LUser.getDwbh()+"') and type='zysb'";
		db.update(sqldelete);
	}
	
	
	
	
	
	
	
	
	
	/**
	 * 删除鉴定专家
	 */
	public int doDel(String zjbh){
		String sql="delete ZC_JDZJINFOR where zjbh"+CommonUtils.getInsql(zjbh);
		Object[] obj=zjbh.split(",");
		int i=0;
		try{
		i=db.update(sql,obj);
		}catch(DataAccessException e){
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}

	// 提交 流程
	/**
	 * 查询主表的信息
	 * 
	 * @param guid
	 * @return
	 */
	public Map getZbInfoByGuid(String guid) {
		String sql = "SELECT * FROM cw_fmb WHERE GUID=?";
		return db.queryForMap(sql, new Object[] { guid });
	}

	/**
	 * 单位领导
	 * 
	 * @return
	 */
	public Map getDwldSql() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" r1.guid as bmfzr,");
		sql.append(" r2.guid as fgld");
		sql.append(" from gx_sys_dwb d");
		sql.append(" left join gx_sys_ryb r1 on r1.rybh=d.DWLD");
		sql.append(" left join gx_sys_ryb r2 on r2.rybh=d.FGLD");
		sql.append(" where d.dwbh='" + LUser.getDwbh() + "'");
		return db.queryForMap(sql.toString());
	}

	// 查询授权人列表
	public List getBsqrList(String mkbh, String ryid) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String sysDate = sdf.format(new Date());
		String sql = "SELECT RYID FROM SQSHZB WHERE MKBH=? AND CZR=? AND (? BETWEEN SQKSSJ AND SQJSSJ)";
		return db.queryForList(sql, new Object[] { mkbh, ryid, sysDate });
	}

	// 查询校长
	public String getXxxz() {
		String xzsql = "select xzxm from CW_XXXXB t";
		String xzxm = Validate.isNullToDefaultString(db.queryForSingleValue(xzsql), "000000");
		if (xzxm.contains(")") && xzxm.length() > 2) {
			xzxm = xzxm.substring(1, xzxm.indexOf(")"));
		}
		String sql = "select r.guid as guid from gx_sys_ryb r where r.rybh='" + xzxm + "'";
		return Validate.isNullToDefaultString(db.queryForSingleValue(sql), "000000");
	}

	/**
	 * 更新主表的流程id
	 * 
	 * @param guid
	 * @return
	 */
	public int updateProcinstid(String guid, String procinstid) {
		String sql = "update cw_fmb set Procinstid=? where guid=?";
		return db.update(sql, new Object[] { procinstid, guid });
	}

	/**
	 * 更新主表的审核信息
	 * 
	 * @param guid
	 * @param shzt
	 * @param shyj
	 * @return
	 */
	public int updateSHXX(String guid, String shzt, String shyj) {
		String sql = "update cw_fmb set shzt=?,shyj=?,shr=?,shrq=sysdate where guid=?";
		return db.update(sql, new Object[] { shzt, shyj, LUser.getGuid(), guid });
	}

	// 财务处人员id
	public List<String> getCwc() {
		String sql = " SELECT GUID FROM gx_sys_ryb WHERE DWBH ='000104' ";
		return db.queryForList(sql, String.class);
	}
	// 财务处长
//	public String getcwcz() {
//		String sql = " select dwld  from Gx_Sys_Dwb t where t.dwbh in ('000104') ";
//		return db.queryForSingleValue(sql);
//	}
	public List<Map<String,Object>> getcwcz() {
		String sql = " select ry.guid from (select dwld  from Gx_Sys_Dwb t where t.dwbh in ('000104'))K left join Gx_Sys_ryb ry on ry.rybh=K.dwld ";
		List<Map<String,Object>> list=null;
		try {
			list=db.queryForList(sql);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	/**
	 * 查询审核预算的财务负责人
	 * @return
	 */
	public String getCwfzrByys(){
		String sql = "select guid from gx_sys_ryb where rybh in(select rybh from ZC_SYS_JSRYB where rownum=1 and jsbh='12')";
		return db.queryForObject(sql, String.class);
	}
	//系部负责人 单位领导人员编号
	public List<Map<String,Object>> getxbfzr() {
		StringBuffer sql=new StringBuffer();
		sql.append("  select ry.guid as guid from (select dw.dwld as dwld from (  select substr(ssxb,2,6) ssxb from(  ");
		sql.append(" select c.xmmc as xmmc,x.ssxb as ssxb from cw_srysb c left join cw_xmxxb x on x.xmmc=c.xmmc  ");
		sql.append(" union select b.xmmc as xmmc,x.ssxb as ssxb from cw_zxywfzcysb b left join cw_xmxxb x on x.xmmc=b.xmmc");
		sql.append("  union select to_char(w.xmmc) as xmmc,x.ssxb as ssxb from CW_CZZCJXMBSQB w left join cw_xmxxb x on x.xmmc=w.xmmc  ");
		sql.append(" union select z.xmmc as xmmc,x.ssxb as ssxb from cw_wxzlfzcysb z left join cw_xmxxb x on x.xmmc=z.xmmc)c )c left join gx_sys_dwb dw on dw.dwbh=c.ssxb where ssxb is not null )L left join gx_sys_ryb  ry on ry.rybh=L.dwld ");
		List<Map<String,Object>> list=null;
		try {
			list=db.queryForList(sql.toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	
	public String getTaskNodeId(String taskId) {
		StringBuffer buffer = new StringBuffer();
		buffer.append(" SELECT TASK_DEF_KEY_ from ACT_RU_TASK t  ");
		buffer.append(" where id_ ='" + taskId + "'");
		String nodeId = null;
		try {
			nodeId = db.queryForSingleValue(buffer.toString()) + "";
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
		}
		return nodeId;
	}
	/**
	 * 更新业务表审核状态
	 * @param 
	 * @return
	 */
	public int doStatus(String ywid,String shzt,String shyj) {
		String sql = "UPDATE cw_fmb SET shzt='"+shzt+"',shyj='"+shyj+"',shr='"+LUser.getGuid()+"' WHERE guid"+CommonUtils.getInsql(ywid);
		Object[] obj = ywid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}

	public String getzdyje() {
		String sql = "select bmys from cw_sqsppz";
		String zdyje="";
		zdyje=db.queryForSingleValue(sql);
		return zdyje;
	}
	/**
	 * 插入审核意见信息
	 * @param ddbh
	 * @return
	 */
	public int doAddShyj(OA_SHYJB shyjb) {
		String sql = "insert into OA_SHYJB (gid, rybh, procinstid, shyj, taskid, shzt,jdsj) values(sys_guid(), ?, ?, ?, ?, ?,to_char(sysdate,'yyyy-mm-dd hh24:Mi:ss'))";
		Object[] obj = new Object[]{
				LUser.getGuid(), 
				shyjb.getProcinstid(), 
				shyjb.getShyj(), 
				shyjb.getTaskid(), 
				shyjb.getShzt()
		};
		int i = db.update(sql, obj);
		return i;
	}
	
	/**
	 * 审核的时候查看详细
	 * @param procinstid
	 * @return
	 */
	public Map getDwbhByProcinstid(String procinstid){
		String sql = "select bmmc,bmfzr from cw_fmb where procinstid=?";
		return db.queryForMap(sql,new Object[]{procinstid});
	}
	
	/**
	 * 根据人员查询单位领导
	 * @param rybh
	 * @return
	 */
	public String getDwldByRybh(String rybh){
		String sql = "select dwld from gx_sys_dwb where dwbh in(select dwbh from gx_sys_ryb where rybh=? and rownum=1)";
		return db.queryForObject(sql, new Object[]{rybh}, String.class);
	}
	
}


