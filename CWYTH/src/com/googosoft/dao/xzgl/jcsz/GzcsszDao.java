package com.googosoft.dao.xzgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;
@Repository("gzcsszDao")
public class GzcsszDao extends BaseDao{
/**\
 * 查询数据
 * @return
 */
	public Map<String, Object> getGzcsMap(){
		String sql = "select b.ffyf,b.nsjs,b.nzjbl from cw_gzcsb b";
		return db.queryForMap(sql);
	}

	
	/**
	 * 保存,进cw_xzb薪资表
	 * @param list
	 * @return
	 */
	public boolean doSave(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String ffyf = Validate.isNullToDefaultString(map.get("ffyf"), "");//发放月份
			String nsjs = Validate.isNullToDefaultString(map.get("nsjs"), "");//纳税基数
			String nzjbl = Validate.isNullToDefaultString(map.get("nzjbl"), "");//年终奖比例
			StringBuffer sql = new StringBuffer();
			sql.append(" update cw_gzcsb set ");
			sql.append(" ffyf='"+ffyf+"',nsjs='"+nsjs+"',nzjbl='"+nzjbl+"' ");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
}
