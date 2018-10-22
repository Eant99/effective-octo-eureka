package com.googosoft.dao.fzgn.wxzf;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("wxzfDao")
public class WxzfDao extends BaseDao{
	private Logger logger = Logger.getLogger(WxzfDao.class);
	/**
	 * 根据承包商id获取支付信息
	 * @param guid
	 * @return
	 */
	public Map getZfxxByGuid(String guid) {
		String sql = "select to_char(sum(xfje),99999999999990.99)as xfze,to_char(sum(0.95*xfje),99999999999990.99)as zfje from CW_WXZFMXB where zfzt='01' and sscbs='"+guid+"'";
		return db.queryForMap(sql);
	}
	public int updateZfzt(String guid) {
		
		final Object[] params = guid.split(",");
		String wstr = CommonUtils.getInsql(guid);
		String sql = "update CW_WXZFMXB set zfzt='03' where sscbs " + wstr;		
		int result = 0;
		try {			
			result = db.update(sql, params);
			
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();// 事务回滚
		}
		return result;
		
	}
	public List getResult(PageData pd) {
		StringBuffer sql=new StringBuffer();
		String guid=Validate.isNullToDefaultString(pd.getString("guid"), "");
		String cbsmc=Validate.isNullToDefaultString(pd.getString("CBSMC"), "");
		String paymethod=Validate.isNullToDefaultString(pd.getString("paymethod"), "");
		String zfzt=Validate.isNullToDefaultString(pd.getString("zfzt"), "");
		if(Validate.noNull(guid)) {
		sql.append("select x.* from (select  Z.*,rownum as xuhao from "
				+"(SELECT D.ZFZTMC,D.zfzt,D.paymethod,zlwzmc,D.zffs,D.cbsbh,D.cbsmc,TO_CHAR(SUM(D.xfje), '99999999999990.99')xfje,TO_CHAR((0.95*SUM(D.xfje)), '99999999999990.99') AS ZFZE FROM "
				  +"(SELECT (CASE T.zfzt WHEN '01' THEN '未支付' WHEN '02' THEN '支付中' WHEN '03' THEN '已支付' end)ZFZTMC,T.zfzt,"
				  +"T.sscbs,c.cbsbh,c.cbsmc,X.xfddbh,X.xfddmc,"
				  +"t.paymethod,(select mc from gx_sys_dmb where zl = 'xq' and dm = x.zlwz) zlwzmc,(CASE t.paymethod WHEN 'wx' THEN '微信' WHEN 'ali' THEN '支付宝' END)zffs,t.xfje"
				  +" from Cw_Wxzfmxb t "
				  +" left join cw_xfddgl x on x.guid=t.xfdd"
				  +" LEFT JOIN CW_CBSXX C ON C.GUID = T.SSCBS where c.sftj='1')D GROUP BY  ZFZT, PAYMETHOD, CBSBH, CBSMC, ZLWZMC)Z  )X where xuhao in ('"+guid+"')");}
		else {
			sql.append("select x.* from (select Z.*,rownum as xuhao from "
					+"(SELECT D.ZFZTMC,D.zfzt,D.paymethod,zlwzmc,D.zffs,D.cbsbh,D.cbsmc,TO_CHAR(SUM(D.xfje), '99999999999990.99')xfje,TO_CHAR((0.95*SUM(D.xfje)), '99999999999990.99') AS ZFZE FROM "
					  +"(SELECT (CASE T.zfzt WHEN '01' THEN '未支付' WHEN '02' THEN '支付中' WHEN '03' THEN '已支付' end)ZFZTMC,T.zfzt,"
					  +"T.sscbs,c.cbsbh,c.cbsmc,X.xfddbh,X.xfddmc,"
					  +"t.paymethod,(select mc from gx_sys_dmb where zl = 'xq' and dm = x.zlwz) zlwzmc,(CASE t.paymethod WHEN 'wx' THEN '微信' WHEN 'ali' THEN '支付宝' END)zffs,t.xfje"
					  +" from Cw_Wxzfmxb t "
					  +" left join cw_xfddgl x on x.guid=t.xfdd"
					  +" LEFT JOIN CW_CBSXX C ON C.GUID = T.SSCBS where c.sftj='1')D GROUP BY  ZFZT, PAYMETHOD, CBSBH, CBSMC,ZLWZMC)Z )X where 1=1");}
		if(Validate.noNull(cbsmc)) {
			sql.append(" AND CBSMC LIKE '%"+cbsmc+"%' ");
		}
		if(Validate.noNull(paymethod)) {
			sql.append(" AND PAYMETHOD LIKE '%"+paymethod+"%' ");
		}
		if(Validate.noNull(zfzt)) {
			sql.append(" AND ZFZT LIKE '%"+zfzt+"%' ");
		}
		sql.append(" ORDER BY ROWNUM ASC");
		return db.queryForList(sql.toString());
	}
	/**
	 * 消费记录查询统计导出
	 * @author 作者：BiMing
	 * @version 创建时间:2018-4-14下午3:17:40
	 */
	public List<Map<String, Object>> getList(String searchValue,String guid,String sszt,String zfzt,String zffs,String ywlx,String cbsmc,String xfddmc,String kssj,String jssj) {
		StringBuffer sqltext = new StringBuffer();//查询字段
		sqltext.append( " select * from (select * from ( select a.*,to_char(ROWNUM) AS XH from (" );
		sqltext.append( " select Round(sum(xfje),2)  as ze,k.xfddmc,(select mc from gx_sys_dmb where zl = 'xq' and dm=k.zlwz) zlwzmc,s.cbsmc ");
		sqltext.append( " from cw_wxzfmxb t" );
		sqltext.append( " left join cw_xfddgl k on t.xfdd = k.guid " );
		sqltext.append( " left join cw_cbsxx s on t.sscbs = s.guid where 1=1" );
		if(Validate.noNull(zfzt)){
			sqltext.append( " and t.zfzt='"+zfzt+"' " );  //
		} 
		if(Validate.noNull(zffs)){
			sqltext.append( " and t.paymethod='"+zffs+"' " );
		}
		if(Validate.noNull(ywlx)){
			sqltext.append( " and k.ywlx='"+ywlx+"' " );
		}
		if(Validate.noNull(cbsmc)){
			sqltext.append( " and s.cbsmc='"+cbsmc+"' " );
		}
		if(Validate.noNull(xfddmc)){
			sqltext.append( " and k.xfddmc like '%"+xfddmc+"%' " );
		}
		if(Validate.noNull(kssj)&&Validate.noNull(jssj)){
			sqltext.append( " and '"+kssj+"'<=to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')" );
			sqltext.append( " and to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')<='"+jssj+"'" );
		}
		sqltext.append( " group by k.xfddmc,s.cbsmc,k.zlwz ORDER BY (XFDDMC) " );
		sqltext.append( " ) A " );
		sqltext.append( " UNION " );
		sqltext.append( " select sum(xfje) as ze, '-' as xfddmc,null as zlwzmc,'合计' as cbsmc,'1188899' AS xh ");
		sqltext.append(	" from cw_wxzfmxb t" );
		sqltext.append( " left join cw_xfddgl k on t.xfdd = k.guid " );
		sqltext.append( " left join cw_cbsxx s on t.sscbs = s.guid where 1=1" );
		if(Validate.noNull(zfzt)){
			sqltext.append( " and t.zfzt='"+zfzt+"' " );  //
		} 
		if(Validate.noNull(zffs)){
			sqltext.append( " and t.paymethod='"+zffs+"' " );
		}
		if(Validate.noNull(ywlx)){
			sqltext.append( " and k.ywlx='"+ywlx+"' " );
		}
		if(Validate.noNull(cbsmc)){
			sqltext.append( " and s.cbsmc='"+cbsmc+"' " );
		}
		if(Validate.noNull(xfddmc)){
			sqltext.append( " and k.xfddmc like '%"+xfddmc+"%' " );
		}
		if(Validate.noNull(kssj)&&Validate.noNull(jssj)){
			sqltext.append( " and '"+kssj+"'<=to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')" );
			sqltext.append( " and to_char(t.xfsj,'yyyy-mm-dd hh24:mi:ss')<='"+jssj+"'" );
		}
		sqltext.append(	" ) k ORDER BY to_number(XH))k ");
		if(Validate.noNull(guid)){
			sqltext.append("where k.xfddmc in('"+guid+"')");
		}
		return db.queryForList(sqltext.toString());
	}

}
