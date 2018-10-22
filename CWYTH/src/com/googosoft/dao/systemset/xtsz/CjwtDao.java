package com.googosoft.dao.systemset.xtsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.systemset.xtsz.ZC_CJWT;
import com.googosoft.util.CommonUtils;

@Repository("cjwtdao")
public class CjwtDao extends BaseDao{
	private Logger logger = Logger.getLogger(CjwtDao.class);

	public boolean doSave(ZC_CJWT cjwt) {
		// TODO Auto-generated method stub
		String[] sqls={
				"delete from zc_cjwtb where guid=?",
				"insert into zc_cjwtb(guid,title,xx,upddate,jdr,jdrq) values(?,?,?,sysdate,?,sysdate)"
		};
		List<Object[]> list = new ArrayList<Object[]>();
		list.add(new Object[]{cjwt.getGuid()});
		list.add(new Object[]{cjwt.getGuid(),cjwt.getTitle(),cjwt.getXx(),LUser.getRybh()});
		return db.batchUpdate(sqls, list);
	}

	public Map getObjectById(String id) {
		// TODO Auto-generated method stub
		String sql = "select guid,title,xx,upddate,jdr,jdrq from zc_cjwtb where guid = ?";
		return db.queryForMap(sql, new Object[]{id});
	}

	public boolean doDelete(String id) {
		// TODO Auto-generated method stub
		String sql = "delete from zc_cjwtb where guid " + CommonUtils.getInsql(id);
		return db.batchUpdate(new String[]{sql}, id.split(","));
	}

	public List getList() {
		// TODO Auto-generated method stub
		return db.queryForList("select guid,title,xx,to_char(upddate,'yyyy-mm-dd hh24:mi') as upddate,jdr,jdrq from zc_cjwtb order by upddate desc");
	}

}
