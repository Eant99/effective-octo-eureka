package com.googosoft.dao.fzgn.grsds;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;

/**
 * 个人所得税超额累进税率表dao
 * @author googosoft
 *
 */
@Repository("grsdsceljDao")
public class GrsdsceljDao extends BaseDao{
	private Logger logger = Logger.getLogger(GrsdsceljDao.class);
	
	/**
	 * 个人所得税超额累进税率表列表查询
	 * @return
	 */
	public List getPageList() {
		String sql = "select t.id,t.qyynsjc,to_char(t.qyynsbzl,'99999999999990.99')qyynsbzl,to_char(t.qyynsbzh,'99999999999990.99')qyynsbzh,to_char(t.sl,'99999999999990.99')sl,to_char(t.sskcs,'99999999999990.99')sskcs from CW_GRSDSCELJSLB T";
		return db.queryForList(sql);
		
	}
	
	/**
	 * 个人所得税超额累进税率表保存
	 * @param qyynsjcs
	 * @param qyynsbzls
	 * @param qyynsbzhs
	 * @param sls
	 * @param sskcss
	 * @return
	 */
	public int dogrsdsSave(String [] qyynsjcs,String [] qyynsbzls,String[] qyynsbzhs,String[] sls,String[] sskcss) {
		List sqlList = new ArrayList<>();
		if(qyynsjcs.length>0){
			String sql1 = "delete CW_GRSDSCELJSLB";
			db.update(sql1);
			for (int i = 0; i < qyynsjcs.length; i++) {
				String sql = "insert into CW_GRSDSCELJSLB values(sys_guid(),'"+qyynsjcs[i]+"','"+qyynsbzls[i]+"','"+qyynsbzhs[i]+"','"+sls[i]+"','"+sskcss[i]+"')";
				sqlList.add(sql);
			}
		}
		int i = 0;
		try {  
			 db.batchUpdate(sqlList);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return 1;
	}
}
