package com.googosoft.controller.ysgl.jcsz;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.GenAutoKey;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;

@Repository("bmysckDao")
public class BmysckDao extends BaseDao {
	
	public Map<?, ?> getObjectById(String guid) {
		String sql = "select b.guid,b.bmbh,b.bmmc,b.sbnd,to_char(b.sryshz, 'FM9999999999.000') sryshz,to_char(b.zcyshz, 'FM9999999999.000') zcyshz,to_char(b.jyje, 'FM9999999999.000') jyje from cw_bmysb b"
					+ " where b.guid='"+guid+"'" ; 
				
				

		return db.queryForMap(sql);
	}

}
