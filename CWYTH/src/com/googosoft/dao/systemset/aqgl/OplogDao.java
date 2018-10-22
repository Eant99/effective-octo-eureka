package com.googosoft.dao.systemset.aqgl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.aqgl.ZC_SYS_OPLOG;
import com.googosoft.util.Validate;

@Repository("oplogDao")
public class OplogDao extends BaseDao{
	private Logger logger = Logger.getLogger(OplogDao.class);
	
	/**
	 * 操作日志信息清空
	 */
	public int doDeleteAll() {
		String sql = "truncate table %SOPLOG ";
		 sql=String.format(sql, SystemSet.sysBz);
		int i = 0;
		try {  
			i = db.update(sql);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 操作日志新增
	 */
	public int doAdd(ZC_SYS_OPLOG rzb){
		String sql="insert into %SOPLOG(LOGBH,RYBH,XM,CZNR,CZJQ,ZT,DBH,DJLX,CZLX,XTBZ,CZRQ,SYD,LLQ) values(sys_guid(),?,?,?,?,?,?,?,?,?,SYSDATE,?,?)";
		 sql=String.format(sql, SystemSet.sysBz);
		List<Object[]> list =new ArrayList<Object[]>();
		if(rzb.getDbh()!=null && rzb.getDbh().length != 0){
			int dbhcnt = rzb.getDbh().length;
			if(dbhcnt == 1){
				String[] dbharr = rzb.getDbh()[0].split(",");
				for(int i = 0; i < dbharr.length; i++){
					Object[] obj = new Object[]{
							rzb.getRybh(),
							rzb.getXm(),		
							rzb.getCznr(),
							rzb.getCzjq(),
							rzb.getZt(),
							Validate.isNullToDefaultString(dbharr[i],"").length()>31?"":dbharr[i],
							rzb.getDjlx(),
							rzb.getCzlx(),
							rzb.getXtbz(),
							rzb.getSyd(),
							rzb.getLlq()
							
						};
					list.add(obj);
				}
			}
			else{
				for(int i=0;i<dbhcnt;i++){
					Object[] obj = new Object[]{
							rzb.getRybh(),
							rzb.getXm(),		
							rzb.getCznr(),
							rzb.getCzjq(),
							rzb.getZt(),
							rzb.getDbh()[i],
							rzb.getDjlx(),
							rzb.getCzlx(),
							rzb.getXtbz(),
							rzb.getSyd(),
							rzb.getLlq()
						};
					list.add(obj);
				}
			}
		}else{
			Object[] obj = new Object[]{
					rzb.getRybh(),
					rzb.getXm(),		
					rzb.getCznr(),
					rzb.getCzjq(),
					rzb.getZt(),
					"",
					rzb.getDjlx(),
					rzb.getCzlx(),
					rzb.getXtbz(),
					rzb.getSyd(),
					rzb.getLlq()
				};
			list.add(obj);
		}try {  
			int[] re = db.batchUpdate(sql, list);
			return re.length;
		} catch (Exception e) {  
			logger.error("【操作日志】数据库操作失败\n" + e.getMessage());
		    throw new RuntimeException(); //排除异常，事务回滚。
		}
	}
}