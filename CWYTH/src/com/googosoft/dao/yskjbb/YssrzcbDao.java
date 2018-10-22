package com.googosoft.dao.yskjbb;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

@Repository("YssrzcbDao")
public class YssrzcbDao  extends BaseDao {
	public List getZcList(){
//		String sql="select * from CW_YSSRZCB t order by xh";
		String sql="select xmmc,to_char(round(bys,2),'fm9999999999999999999999999999990.00')bys," +
				"to_char(round(bnljs,2),'fm9999999999999999999999999999990.00')bnljs from CW_YSSRZCB t order by xh";
		return this.db.queryForList(sql);
	}
	
	public List getYsList(){
		String sql="select xmmc,to_char(round(bys,2),'fm9999999999999999999999999999990.00')bys," +
				"to_char(round(bnljs,2),'fm9999999999999999999999999999990.00')bnljs from CW_YSJZJYB t order by xh";
		return this.db.queryForList(sql);
	}
	
	public List getCzList(){
		           
//		String sql="select * from CW_CZBKB t order by xh";
		StringBuffer sql = new StringBuffer();
		sql.append(" select xmmc,");
		sql.append(" to_char(round(ncjz,2),'fm9999999999999999999999999999990.00')ncjz,");
		sql.append(" to_char(round(ncjy,2),'fm9999999999999999999999999999990.00')ncjy,");
		sql.append(" to_char(round(tzncczbk,2),'fm9999999999999999999999999999990.00')tzncczbk,");
		sql.append(" to_char(round(bngjtr,2),'fm9999999999999999999999999999990.00')bngjtr,");
		sql.append(" to_char(round(bngjsj,2),'fm9999999999999999999999999999990.00')bngjsj,");
		sql.append(" to_char(round(dwnbjz,2),'fm9999999999999999999999999999990.00')dwnbjz,");
		sql.append(" to_char(round(dwnbjy,2),'fm9999999999999999999999999999990.00')dwnbjy,");
		sql.append(" to_char(round(bnczbksr,2),'fm9999999999999999999999999999990.00')bnczbksr,");
		sql.append(" to_char(round(bnczbkzc,2),'fm9999999999999999999999999999990.00')bnczbkzc,");
		sql.append(" to_char(round(nmjz,2),'fm9999999999999999999999999999990.00')nmjz,");
		sql.append(" to_char(round(nmjy,2),'fm9999999999999999999999999999990.00')nmjy");
		sql.append(" from CW_CZBKB t order by xh");
		return this.db.queryForList(sql.toString());
	}
}
