package com.googosoft.dao.systemset.qxgl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;

@Repository("syfxqxDao")
public class SyfxqxDao  extends BaseDao{
	private Logger logger = Logger.getLogger(SyfxqxDao.class);

	public List<Map<String, Object>> getSyfxList(String rybh, String syfx) {
		// TODO Auto-generated method stub
		String sql = "select t.dm,t.mc,count(q.rybh) as cnt from gx_sys_dmb t left join zc_dirRight q on t.dm=q.syfx and q.rybh=? where zl=? group by t.dm,t.mc order by t.dm ";
		return db.queryForList(sql, new Object[]{rybh,syfx});
	}
	
	public boolean doSave(String syfx, String rybh) {
		String[] sqls = {
				"delete from zc_dirRight where rybh = ?",
				"insert into zc_dirRight(rybh,syfx,tjr,tjrq) values(?,?,?,sysdate)"
		};
		List<Object[]> list1 = new ArrayList<Object[]>();
		list1.add(new Object[]{rybh});
		List<Object[]> list2 = new ArrayList<Object[]>();
		if(syfx.length()>0){
			String[] fx = syfx.split(",");
			for(int i = 0; i < fx.length; i++){
				list2.add(new Object[]{rybh,fx[i],LUser.getRybh()});
			}
		}
		return db.batchUpdate(sqls, list1,list2);
	}
	
}
