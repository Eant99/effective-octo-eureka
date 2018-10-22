package com.googosoft.dao.xzgl;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("xzshDao")
public class XzshDao extends BaseDao {

	/**
	 * 在职薪资审核通过
	 */
	public int doZztg(String guid, String where, String gzyf) {
		String sql = new String();
		int result = 0;
		if(Validate.isNull(guid)){
			sql = " update cw_xzb t set shzt='2' where 1 = 1 and shzt = '1' " + where;
			result = db.update(sql);
		}else{
			sql = " update cw_xzb t set shzt='2' where guid " + CommonUtils.getInsql(guid);
			result=  db.update(sql, guid.split(","));
		}
		int sum=0;
		int count=0;
		sum = this.getWshZz(gzyf);
		if(sum == 0){
			count = this.getWshLz(gzyf);
			if(count == 0 ){
				StringBuffer sql1 = new StringBuffer();
				sql1.append("update cw_gzcsb set ffyf=(select to_char(add_months(to_date(ffyf,'yyyy-mm'),1),'yyyy-mm') from cw_gzcsb)");
				db.update(sql1.toString());
			}
		}
		return  result;
	}

	/**
	 * @Description: 描述:查询离职人员该月份是否都已审核
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-13下午3:34:12
	 * @param where
	 * @return    
	 */
	private int getWshLz(String gzyf) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from CW_LZXZB t where shzt <> '2' and gzyf='"+gzyf+"' ");
		String result = db.queryForSingleValue(sql.toString());
		if(result.equals("0") ){
			return 0;
		}else{
			return 1;
		}
	}

	/**
	 * @Description: 描述:查询在职人员该月份是否都已审核
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-13下午3:20:12
	 * @return
	 */
	private int getWshZz(String gzyf) {
		StringBuffer sql = new StringBuffer();
		sql.append("select count(*) from cw_xzb t where shzt <> '2' and gzyf='"+gzyf+"' ");
		String result = db.queryForSingleValue(sql.toString());
		if(result.equals("0") ){
			return 0;
		}else{
			return 1;
		}
	}

	/**
	 * 在职薪资审核退回
	 */
	public int doZzth(String guid, String where) {
		String sql = new String();
		int result = 0;
		if(Validate.isNull(guid)){
			sql = " update cw_xzb t set shzt='3' where 1 = 1 and shzt = '1' " + where;
			result = db.update(sql);
		}else{
			sql = " update cw_xzb t set shzt='3' where guid " + CommonUtils.getInsql(guid);
			result=  db.update(sql, guid.split(","));
		}
		return  result;
	}

	/**
	 * 离职薪资审核通过
	 */
	public int doTxtg(String guid, String where,String gzyf) {
		String sql = new String();
		int result = 0;
		if(Validate.isNull(guid)){
			sql = " update cw_lzxzb l set shzt='2' where 1 = 1 and shzt = '1' " + where;
			result = db.update(sql);
		}else{
			sql = " update cw_lzxzb l set shzt='2' where guid " + CommonUtils.getInsql(guid);
			result=  db.update(sql, guid.split(","));
		}
		int sum=0;
		int count=0;
		sum = this.getWshZz(gzyf);
		if(sum == 0){
			count = this.getWshLz(gzyf);
			if(count == 0 ){
				StringBuffer sql1 = new StringBuffer();
				sql1.append("update cw_gzcsb set ffyf=(select to_char(add_months(to_date(ffyf,'yyyy-mm'),1),'yyyy-mm') from cw_gzcsb)");
				db.update(sql1.toString());
			}
		}
		return  result;
	}

	/**
	 * 离职薪资审核退回
	 */
	public int doTxth(String guid, String where) {
		String sql = new String();
		int result = 0;
		if(Validate.isNull(guid)){
			sql = " update cw_lzxzb l set shzt='3' where 1 = 1 and shzt = '1' " + where;
			result = db.update(sql);
		}else{
			sql = " update cw_lzxzb l set shzt='3' where guid " + CommonUtils.getInsql(guid);
			result=  db.update(sql, guid.split(","));
		}
		return  result;
	}

	public String getLastMon() {
		// TODO Auto-generated method stub
		return db.queryForSingleValue("select (case (select count(*) from cw_xzb where shzt <> '0') when 0 then to_char(sysdate,'yyyy.mm') else (select max(gzyf) from cw_xzb where shzt <> '0') end) from dual ");
	}
	
	public String getLastMonLz() {
		// TODO Auto-generated method stub
		return db.queryForSingleValue("select (case (select count(*) from cw_lzxzb where shzt <> '0') when 0 then to_char(sysdate,'yyyy.mm') else (select max(gzyf) from cw_lzxzb where shzt <> '0') end) from dual");
	}

}
