package com.googosoft.dao.pjgl.rcyw;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.PageData;

@Repository("pjlbDao")
public class PjlbDao extends BaseDao{
	private Logger logger = Logger.getLogger(PjlbDao.class);
	
	public List getPjlbListById(String pjzh) {
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.gid,decode(t.zt,'00','未使用','10','已领用','20','已开票','30','已报销','40','已核销','50','作废')as ztmc , ");
		sb.append(" t.PJH,CPR,t.CPZH,t.HCDD,t.CPYH,t.SKR,t.SKZH,t.HRDD,t.SKYH,t.JE,decode(t.SFDY,'00','否','01','是')sfdymc ,t.CPRQ ");
		sb.append(" from CW_PJ t where 1=1 and t.pjzh = '"+pjzh+"' ");
		List list=null;
		try {  
			list=db.queryForList(sb.toString());
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return list;
	}
	/*
	 * 
	 * */
	public Map getPjytMapById(String guid) {
		
		StringBuffer sb=new StringBuffer();
		sb.append(" select t.gid,(select kmbh from CW_PJGM a where a.GID=t.GMGID) as kmbh,");
		sb.append(" (select kmmc from Cw_kjkmszb where kmbh =(select kmbh from CW_PJGM a where a.GID=t.GMGID)) as kmmc,t.lyrq,t.lybm,t.lyr,t.pjzh,z.zhmc,t.pjh,t.pjyt,t.yjje, ");
		sb.append(" (select d.mc from gx_sys_dwb d where d.dwbh=t.lybm ) as dwmc, (select r.xm from gx_sys_ryb r where r.rybh=t.lyr ) as ryxm ");
		sb.append(" from cw_pj t ");
		sb.append(" left join cw_pjzhb z on z.guid = t.pjzh ");
		sb.append(" where 1=1 and t.gid = ? ");
		
		Object[] obj = new Object[]{guid};
		Map map = null;
		try {  
			map = db.queryForMap(sb.toString(), obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		}
		return map;
	}
	public int editPjlx(PageData pd) {
			String sql = "update CW_PJ t set t.zt ='10',t.lybm=?,t.lyr=?,t.lyrq=to_date(to_char(?),'yyyy-MM-dd HH24:mi'),t.pjyt=?,t.yjje=? where t.gid=?";
			sql=String.format(sql, SystemSet.gxBz);	
			
			Object[] obj = new Object[]{
					pd.getString("lybm"),
					pd.getString("lyr"),
					pd.getString("lyrq"),
					pd.getString("pjyt"),
					pd.getString("yjje"),
					pd.getString("guid")
			};
			
			int i = 0;
			try{
				i = db.update(sql,obj);
			}catch (DataAccessException e) {  
				SQLException sqle = (SQLException) e.getCause();  
			    logger.error("数据库操作失败\n" + sqle);  
			    return -1;
			}
			return i;
	}
	public int editPjZf(String gid) {
		String sql = "update CW_PJ t set t.zt ='50' where t.gid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{gid};
		
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
}
	//票据报销
	public int editBx(String gid) {
		String sql = "update CW_PJ t set t.zt ='30' where t.gid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{gid};
		
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
}
	/**
	 * 查询票据类型
	 * @param guid
	 * */
	public String getPjlxById(String guid) {
		String sql=" select z.pjlx from cw_pj p left join cw_pjzhb z on z.guid=p.pjzh where p.gid ='"+guid+"' ";
		String pjlx=null;
		try{
			pjlx=db.queryForSingleValue(sql);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		}
		return pjlx;
	}
	public int doDelete(String gid) {
		String sql = "delete from CW_PJ t where t.gid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{gid};
		
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	//普通发票保存
	public int doSavePtfp(PageData pd) {
		
		String sql = "update CW_PJ t set t.zt='20',t.cprq=to_date(to_char(?),'yyyy-MM-dd HH24:mi'),t.PJH=?,t.FPDM=?,t.JE=?,t.SKR=?,t.CPR=?,t.SHDWSBM=?,t.sHDWDH=?,t.sHDWDZ=?,t.CPYH=?,t.CPZH=?,t.KPR=?,t.ZDR=? where t.gid=?";
		sql=String.format(sql, SystemSet.gxBz);	
		
		Object[] obj = new Object[]{
				pd.getString("cprq"),
				pd.getString("pjh"),
				pd.getString("fpdm"),
				pd.getString("je"),
				pd.getString("skr"),
				pd.getString("cpr"),
				pd.getString("shdwsbm"),
				pd.getString("shdwdh"),
				pd.getString("shdwdz"),
				pd.getString("cpyh"),
				pd.getString("cpzh"),
				pd.getString("kpr"),
				pd.getString("zdr"),
				pd.getString("guid")
		};
		
		int i = 0;
		try{
			i = db.update(sql,obj);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	//收据保存
		public int doSaveSjfp(PageData pd) {
			
			String sql = "update CW_PJ t set t.zt='20',t.cprq=to_date(to_char(?),'yyyy-MM-dd HH24:mi'),t.PJH=?,t.JE=?,t.SKR=?," +
					"t.YT=?,t.FJXX=?,t.NYR=to_date(to_char(?),'yyyy-MM-dd HH24:mi'),t.KPR=?,t.ZDR=? where t.gid=?";
			sql=String.format(sql, SystemSet.gxBz);	
			
			Object[] obj = new Object[]{
					pd.getString("cprq"),
					pd.getString("pjh"),
					pd.getString("je"),
					pd.getString("skr"),
					pd.getString("yt"),
					pd.getString("fjxx"),
					pd.getString("nyr"),
					pd.getString("kpr"),
					pd.getString("zdr"),
					pd.getString("guid")
			};
			
			int i = 0;
			try{
				i = db.update(sql,obj);
			}catch (DataAccessException e) {  
				SQLException sqle = (SQLException) e.getCause();  
			    logger.error("数据库操作失败\n" + sqle);  
			    return -1;
			}
			return i;
		}
		//收据保存
				public int doSaveZyfp(PageData pd) {
					
					String sql = "update CW_PJ t set t.zt='20',t.cprq=to_date(to_char(?),'yyyy-MM-dd HH24:mi'),t.PJH=?,t.FPDM=?," +
							"t.JE=?,t.SKR=?,t.CPR=?,t.ZDR=? where t.gid=?";
					sql=String.format(sql, SystemSet.gxBz);	
					
					Object[] obj = new Object[]{
							pd.getString("cprq"),
							pd.getString("pjh"),
							pd.getString("fpdm"),
							pd.getString("je"),
							pd.getString("skr"),
							pd.getString("cpr"),
							pd.getString("shdwsbm"),
							pd.getString("shdwdh"),
							pd.getString("shdwdz"),
							pd.getString("cpyh"),
							pd.getString("cpzh"),
							pd.getString("kpr"),
							pd.getString("zdr"),
							pd.getString("guid")
					};
					
					int i = 0;
					try{
						i = db.update(sql,obj);
					}catch (DataAccessException e) {  
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
					    return -1;
					}
					return i;
				}
				public int doSaveZzsfp(PageData pd) {
						
						String sql = "update CW_PJ t set t.zt='20',t.cprq=to_date(to_char(?),'yyyy-MM-dd HH24:mi'),t.PJH=?,t.FPDM=?,t.JE=?,t.SKR=?," +
								"t.GHDWSBM=?,t.GHDWDH=?,t.GHDWDZ=?,t.SKYH=?,t.SKZH=?,t.CPR=?," +
								"t.SHDWSBM=?,t.SHDWDH=?,t.SHDWDZ=?,t.CPYH=?,t.CPZH=?,t.KPR=?,t.ZDR=? where t.gid=?";
						sql=String.format(sql, SystemSet.gxBz);	
						
						Object[] obj = new Object[]{
								pd.getString("cprq"),
								pd.getString("pjh"),
								pd.getString("fpdm"),
								pd.getString("je"),
								pd.getString("skr"),
								pd.getString("ghdwsbm"),
								pd.getString("ghdwdh"),
								pd.getString("ghdwdz"),
								pd.getString("skyh"),
								pd.getString("skzh"),
								pd.getString("cpr"),
								pd.getString("shdwsbm"),
								pd.getString("shdwdh"),
								pd.getString("shdwdz"),
								pd.getString("cpyh"),
								pd.getString("cpzh"),
								pd.getString("kpr"),
								pd.getString("zdr"),
								pd.getString("guid")
						};
						
						int i = 0;
						try{
							i = db.update(sql,obj);
						}catch (DataAccessException e) {  
							SQLException sqle = (SQLException) e.getCause();  
						    logger.error("数据库操作失败\n" + sqle);  
						    return -1;
						}
						return i;
					}
				public Map getPjxxById(String id) {
					String sql="select * from cw_pj t where t.gid = '"+id+"'";
					Map map=null;
					try{
						map=db.queryForMap(sql);
					}catch (DataAccessException e) {  
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
					}
					return map;
				}
				public List getPjytList() {
					String sql="select t.guid,t.pjytmc from cw_pjytb t where t.sfqy= '1'";
					List list=null;
					try{
						list=db.queryForList(sql);
					}catch (DataAccessException e) {  
						SQLException sqle = (SQLException) e.getCause();  
					    logger.error("数据库操作失败\n" + sqle);  
					}
					return list;
				}
}
