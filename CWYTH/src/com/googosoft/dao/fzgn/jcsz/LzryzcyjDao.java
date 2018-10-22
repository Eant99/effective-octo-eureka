package com.googosoft.dao.fzgn.jcsz;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.util.Validate;

/**
 * 离职人员资产移交Dao层
 * @author RC 2017-08-31
 *
 */
@Repository("lzryzcyjDao")
public class LzryzcyjDao extends BaseDao{
	
	/**
	 * 保存离职人员信息
	 * @param lzry 离职人员
	 * @param jsr 接收人员
	 * @param yqbhs 部分移交时选择的部分资产
	 * @return
	 */
	public boolean doSave(String lzry, String jsr, String yqbhs){
		List<String> sqllist = new ArrayList<String>();
		List<Object[]> objlist = new ArrayList<Object[]>();
		String sql =  " insert into %SBDB(bdbh,bz,fjbh,bdxm,bdqnr,bdhnr,bdrq,yqmc,sydw,dj,gzrq,bdbz,pzh,jzrq,flh,flmc,jzbz,syfx,zzj,"
					+ " syr,bzxx,xz,sykh,xmbz,jldw,sccj,xss,jdr,jdrq,bdqnrmc,bdhnrmc)"
					+ " select sys_guid(),?,yqbh,'使用人',syr,?,sysdate,yqmc,sydw,dj,gzrq,?,pzh,rzrq,flh,flmc,jzbz,syfx,zzj,"
					+ " syr,bzxx,xz,sykh,?,jldw,sccj,xss,?,sysdate,(select xm from %SRYB where rybh = ?),(select xm from %SRYB where rybh = ?)"
					+ " from %SZJB where syr = ?";
		if(Validate.noNull(yqbhs)){//部分移交
			sql += " and yqbh in ('" + ToSqlUtil.pointToSql(yqbhs) + "')";
		}
		sql = String.format(sql, SystemSet.zcBz, SystemSet.gxBz, SystemSet.gxBz, SystemSet.zcBz);
		Object[] obj = new Object[]{Constant.BDBZ_ZJ,jsr,StateManager.SHWC,StateManager.BDXM_SYRBD,LUser.getRybh(),lzry,jsr,lzry};
		sqllist.add(sql);
		objlist.add(obj);
		if(Validate.noNull(yqbhs)){//部分移交
			sqllist.add(String.format("update %SZJB set syr = ? where syr = ? and yqbh in ('" + ToSqlUtil.pointToSql(yqbhs) + "')",SystemSet.zcBz));
			objlist.add(new Object[]{jsr,lzry});
		}else{//全部移交
			sqllist.add(String.format("update %SZJB set syr = ? where syr = ?",SystemSet.zcBz));
			objlist.add(new Object[]{jsr,lzry});
		}
		return db.batchUpdate(sqllist, objlist);
	}
}
