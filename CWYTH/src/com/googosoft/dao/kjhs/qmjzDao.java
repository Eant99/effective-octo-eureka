package com.googosoft.dao.kjhs;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.kjhs.CW_QMJZB;
import com.googosoft.pojo.wsbx.jcsz.CW_WLDWSZ;
import com.googosoft.util.Validate;

@Repository("qmjzDao")
public class qmjzDao extends BaseDao{
	private Logger logger = Logger.getLogger(qmjzDao.class);
	
	/**
	 * 期末结账本月结账信息
	 * @param dmb
	 * @return
	 */
	public int doEdit(CW_QMJZB qmjzb) {
		String jzry = LUser.getGuid();
		String sql = "update CW_QMJZB set  sfjz='1',jzcs='1',jzrq=sysdate,jzry='"+jzry+"' where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{qmjzb.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	public List getSsxt() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select distinct(ztnd) from cw_qmjzb order by ztnd desc ");
		
		return db.queryForList(sql.toString());
	}
	
	
	/**
	 * 添加下月结账信息
	 * @param dmb
	 * @return
	 */
	public int doAdd(CW_QMJZB qmjzb) {
		String sql = "insert into cw_qmjzb(guid,ztnd,kjqj,sfjz,jzcs,sszt)values(?,?,?,'0','0',?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{qmjzb.getGuid(),qmjzb.getZtnd(),qmjzb.getKjqj(),qmjzb.getSszt()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 *验证实体
	 * @param 
	 * @return
	 */
	public List<Map<String, Object>>  getObjectById(String kjqj) {
		String sql="select p.pzzt from cw_pzlrzb p where kjqj= ?";
		return db.queryForList(sql,new Object[]{kjqj});
	}
	
	/**
	 * 验证凭证编号是否断号
	 * @param kjqj
	 * @param ztnd
	 * @return
	 */
	public boolean checkPzbh(String kjqj,String ztnd){
		String pzbhSql = "select max(to_number(pzbh)) from CW_PZLRZB where kjqj='"+kjqj+"' and pznd='"+ztnd+"'";
		String maxPzbh = Validate.isNullToDefaultString(db.queryForSingleValue(pzbhSql), "0");
		String lengthSql = "select count(0) from(select pzbh,kjqj,pznd from CW_PZLRZB where kjqj='"+kjqj+"' and pznd='"+ztnd+"' group by pzbh,kjqj,pznd)";
		String lengthPzbh = Validate.isNullToDefaultString(db.queryForSingleValue(lengthSql), "0");
		if(Integer.parseInt(maxPzbh)==Integer.parseInt(lengthPzbh)){
			return true;
		}
		return false;
	}
	/**
	 *验证实体
	 * @param 
	 * @return
	 */
	public Map<String, Object> getObjectByIdJz(String kjqj,String ztnd) {
		System.out.println("ztnd===========ztnd======"+ztnd);
		String sql="select nvl(jzcs,'0') as jzcs from cw_qmjzb p where kjqj= ? and ztnd=?";
		return db.queryForMap(sql,new Object[]{kjqj,ztnd});
	}
	/**
	 * 反结账
	 * @param dmb
	 * @return
	 */
	public int doEditFjz(CW_QMJZB qmjzb) {
		
		String sql = "update CW_QMJZB set  sfjz='0',jzrq='',jzry='' where guid=?";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{qmjzb.getGuid()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 
	 * @param nd 年度
	 * @param ztnd 账套年度
	 * @param kjzd 会计制度
	 * @return
	 */
	public int insertKmye(int nd,String ztnd,String kjzd) {
		
		String sql = "insert into cw_kmyeb select sys_guid(),kmbh,kmmc,sfmj,sffz,'"+nd+"' as nd,sszt,car,czrq,kmzye,kmsx,kmid,kjzd from cw_kmyeb where kjzd='"+kjzd+"' and nd='"+ztnd+"'";
		db.update(sql);
		return 1;
	}

	public int updateKmye(int nd,String sszt) {
		String sql = "update cw_kmyeb km set (kmzye) =(select sum(ncye) from cw_kmyemxb mx where mx.nd='"+nd+"' and mx.sszt ='"+sszt+"' and mx.kmbh = km.kmbh group by kmyebh) where kmbh in" + 
				"(select kmyebh from cw_kmyemxb where nd='"+nd+"' and sszt ='"+sszt+"' group by kmyebh) and km.nd='"+nd+"' and sszt ='"+sszt+"'";
		db.update(sql);
		return 1;
	}
        /**
         * 
         * @param nd 年度
         * @param sszt 所属账套
         * @param ztnd 账套年度
         * @param kjzd 会计制度
         * @return
         */
        public int insertKmyemx(int nd,String sszt,String ztnd,String kjzd) {
        	String dlr = LUser.getGuid();
        	String sql = "insert into cw_kmyemxb (guid,kmbh,kmmc,jjkm,bmbh,xmbh,ncye,nd,sszt,czr,czrq,kmyebh,dwfz,grfz,grdh,dwbh) select sys_guid(),pz.kmbh as kmbh1, (select zb.kmmc from cw_kjkmszb zb where pz.kmbh=zb.kmbh) as kmmc,(select guid from cw_jjkmb jj where jj.kmbh = pz.jjfl) as jjkm,bmbh," + 
        			"(select guid from cw_xmlxb xm where xm.xmlxbh = pz.xmbh and sszt = '"+sszt+"')as xmbh," + 
        			"  case when kj.yefx='1' then nvl(dfje, 0)-nvl(jfje, 0) else nvl(jfje, 0)- nvl(dfje, 0) end as ncye,'"+nd+"', '"+sszt+"','"+dlr+"',sysdate, pz.kmbh,wl.dwmc,ry.xm,t.jsdh,t.dfdw from cw_pzlrmxb pz left join cw_fzlrb t on t.kmbh=pz.guid left join gx_sys_ryb ry on ry.rybh=t.zrr  "
        			+ "   left join cw_kjkmszb kj on kj.kmbh = pz.kmbh and kj.kjzd='"+kjzd+"' and kj.sszt='"+sszt+"' and to_char(kj.kmnd,'yyyy')='"+ztnd+"'"
        			+ "   left join cw_kmyeb km on km.kmbh=pz.kmbh and km.sszt='B9BA12A24DBE4EA89763AFDE76B8C61A' and km.nd='"+nd+"' left join cw_wldwb wl on wl.wlbh=t.dfdw  ";
        	db.update(sql);
        	return 1;
        }
        public int deleteKmyemx(int nd,String sszt) {
        	String sql = "delete cw_kmyemxb where nd='"+nd+"' and sszt='"+sszt+"'";
        	db.update(sql);
        	return 1;
        }
        
        /**
         * 插入项余额表信息
         */
       public int  insertXmncyeb(int nd) {
    	   StringBuffer sql=new StringBuffer();
    	   sql.append(" insert into cw_xmncyeb select sys_guid(),bmbh,xmbh,to_char(syje,'fm999999999990.00'),'"+nd+"'as kjnd,kgrq,wgrq ");
    	   sql.append(" from cw_xmjbxxb where substr(kgrq,0,4)<'"+nd+"' and substr(wgrq,0,4)>='"+nd+"'");
    	   return db.update(sql.toString());
	   }
       /**
        * 获取期末结账信息
        */
       public Map<String, Object> getQmjzxxByGud(String guid) {
    	   StringBuffer sql=new StringBuffer();
    	   sql.append("select ztnd,kjqj from cw_qmjzb where guid='"+guid+"'");
    	   return db.queryForMap(sql.toString());
   	  }
       /**
        * 反结账删除Cw_XMNCYEB信息
        */
       public void deleteXmNcyeb(Map<String, Object> qmjzMap) {
    	   String ztnd=qmjzMap.get("ZTND")+"";
    	   String kjqj=qmjzMap.get("KJQJ")+"";
    	   int nd = Integer.parseInt(ztnd)+1;//上一年+1
		   if("12".equals(kjqj)) {
			   StringBuffer sql=new StringBuffer();
	    	   sql.append("delete from cw_xmncyeb where kjnd='"+nd+"'");
	    	   db.update(sql.toString());
		   }
   	  }
}
