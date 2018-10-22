package com.googosoft.dao.xzgl.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.util.Validate;
@Repository("gzxmszDao")
public class GzxmszDao extends BaseDao{

	/**
	 * 保存,进cw_gzxmb表（工资项目表）
	 * @param list
	 * @return
	 */
	public boolean doSave(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String xmjc = Validate.isNullToDefaultString(map.get("xmjc"), "");//发放月份
			String xmmc = Validate.isNullToDefaultString(map.get("xmmc"), "");//纳税基数
			String guid = Validate.isNullToDefaultString(map.get("guid1"), "");//纳税基数
			StringBuffer sql = new StringBuffer();
			sql.append(" update cw_gzxmb set ");
			sql.append(" xmjc='"+xmjc+"',xmmc='"+xmmc+"' where guid='"+guid+"' ");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}
	

}
