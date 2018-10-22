package com.googosoft.dao.xmgl.jcsz;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.CW_ZYXXB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Validate;

@Repository("zyxxwhDao")
public class ZyxxwhDao extends BaseDao{
	
	/**
	 * 新增
	 * 
	 * @param ryb
	 * @return
	 */
	public int doAdd(CW_ZYXXB zyxxb) {
		String sql = "insert into cw_zyxxb(guid,zybh,zymc,ssyx,zyfx,xz,zyzt) values(sys_guid(),?,?,?,?,?,?)";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] {zyxxb.getZybh(),zyxxb.getZymc(),CommonUtil.getBeginText(zyxxb.getSsyx()),CommonUtil.getBeginText(zyxxb.getZyfx()),zyxxb.getXz(),zyxxb.getZyzt()};
		return db.update(sql, obj);
	}
	/**
	 * 获得专业信息维护数据
	 */
	public Map getObjectById(String guid) {
	    String sql = "select zybh,zymc,guid,(select '('||dwbh||')'||mc from gx_sys_dwb dm where dm.dwbh = zy.ssyx) as ssyx,(select '('||d.dm||')'||d.mc  from gx_sys_dmb d where d.dm= zy.zyfx and d.zl='zyfx') as zyfx,xz,zyzt from cw_zyxxb zy where guid='"+guid+"'";
	    return db.queryForMap(sql);
	}
	/**
	 * 编辑
	 * 
	 * @param ryb
	 * @return
	 */
	public int doEdit(CW_ZYXXB zyxxb) {
		String sql = "update cw_zyxxb set zybh=?,zymc=?,ssyx=?,zyfx=?,xz=?,zyzt=? where guid=?";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] {zyxxb.getZybh(),zyxxb.getZymc(),CommonUtil.getBeginText(zyxxb.getSsyx()),CommonUtil.getBeginText(zyxxb.getZyfx()),zyxxb.getXz(),zyxxb.getZyzt(),zyxxb.getGuid()};
		return db.update(sql, obj);
	}
	//得到可以删除的guid
	public String docheckisdelete(String guid) {
		String candel = "";
		String[] gid = guid.split(",");
		System.err.println("____"+gid.length);
		for(int i = 0;i<gid.length;i++) {
			System.err.println("__i__"+i+"___"+gid[i]);
			String flag = db.queryForSingleValue(" select count(zybh) from CW_XJXXB b where b.zybh=(select z.zybh from cw_zyxxb z where z.guid = '"+gid[i]+"') ");
//			String flag2 = db.queryForSingleValue("select count(1) from cw_cgmlszb b where b.sjml='"+gid[i]+"'");
			if(Integer.parseInt(flag)==0 /*&& Integer.parseInt(flag2)==0*/) {
				candel = candel+gid[i];
				if(i<gid.length-1) {
					candel+=',';
				}
			}
		}
		return candel;
	}
	//得到不能删除的guid
	public String dochecknotdelete(String guid) {
		String candel = "";
		String[] gid = guid.split(",");
		System.err.println("____"+gid.length);
		for(int i = 0;i<gid.length;i++) {
			System.err.println("__i__"+i);
			String flag = db.queryForSingleValue(" select count(zybh) from CW_XJXXB b where b.zybh=(select z.zybh from cw_zyxxb z where z.guid = '"+gid[i]+"') ");
//			String flag2 = db.queryForSingleValue("select count(1) from cw_cgmlszb b where b.sjml='"+gid[i]+"'");
			if(Integer.parseInt(flag)!=0 /*|| Integer.parseInt(flag2)!=0*/) {
				candel = candel+gid[i];
				if(i<gid.length-1) {
					candel+=',';
				}
			}
		}
		return candel;
	}
	//根据不能删除的guid得到不能删除的mldm
	public String seledmbyid(String guid) {
		if(Validate.noNull(guid)) {
			System.err.println("______zzzzz"+guid.substring(guid.length()-1,guid.length()));
			if(",".equals(guid.substring(guid.length()-1,guid.length()))) {
				guid = guid.substring(0,guid.length() - 1);
			}
		}
		final Object[] params = guid.split(",");
//		return db.queryForSingleValue(" select wm_concat(t.ZYBH) dm from CW_XJXXB t where t.guid "+CommonUtils.getInsql(guid)+" ",params);
		return db.queryForSingleValue(" select wm_concat(distinct t.ZYBH) dm from CW_XJXXB t where t.zybh in (select z.zybh from cw_zyxxb z where z.guid "+CommonUtils.getInsql(guid)+") ",params);
	}
	//得到成功删除数量
	public int getcgscnum(String guid) {
		int num = 0;
		String[] gid = guid.split(",");
		for(int i = 0;i<gid.length;i++) {
			System.err.println("__i__"+i);
			String flag = db.queryForSingleValue("select count(1) from CW_XJXXB b where b.zybh=(select z.zybh from cw_zyxxb z where z.guid = '"+gid[i]+"')");
//			String flag2 = db.queryForSingleValue("select count(1) from cw_cgmlszb b where b.sjml='"+gid[i]+"'");
			if(Integer.parseInt(flag)==0 /*&& Integer.parseInt(flag2)==0*/) {
				num++;
			}
		}
		return num;
	}
	/**
	 * 删除
	 */
	public int doDel(String guid) {
		if(Validate.noNull(guid)) {
			if(",".equals(guid.substring(guid.length()-1,guid.length()))) {
				guid = guid.substring(0,guid.length() - 1);
			}
		}
		String sql = "delete cw_zyxxb where guid in ('"+guid+"')";
		return db.update(sql);
	}
	/**
	 * 启用 
	 */
	public int doqy(String guid) {
		String sql = "update cw_zyxxb set zyzt = '1' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/*
	 * 禁用
	 */
	public int dojy(String guid) {
		String sql = "update cw_zyxxb set zyzt = '0' where guid = '"+guid+"'";
		return db.update(sql);
	}
	/**
	 * 验证专业编号
	 */
	public int checkbh(String zybh) {
		String sql = "select count(*) from cw_zyxxb where zybh='"+zybh+"'";
		return db.queryForInt(sql);
	}
	public int checkbh1(String zybh,String guid) {
		String sql = "select count(*) from cw_zyxxb where zybh='"+zybh+"' and guid != '"+guid+"'";
		return db.queryForInt(sql);
	}
	/**
	 * update启用状态
	 */
	public int updZt(String guid,String zt) {
		String sql = "update cw_zyxxb set zyzt='"+zt+"' where guid in ('"+guid+"')";
		return db.update(sql);
	}
	public List<Map<String, Object>> getList(String searchValue, String guid,String dwbh) {
		StringBuffer sql = new StringBuffer();
		sql.append("select  *  from  (select b.GUID,b.ZYBH,b.ZYMC, '('||DWBH||')'||MC  AS SSYX, '('||DWBH||')'||MC  AS ZYFX, dm.MC AS XZ,(CASE b.ZYZT WHEN '0' THEN '禁用'  ELSE '启用' END) AS ZYZT from CW_ZYXXB b left join gx_sys_dwb dw on dw.dwbh=b.ssyx and dw.dwbh = b.zyfx left join gx_sys_dmb dm on dm.dm=b.xz and zl='xz'");
		if(Validate.noNull(dwbh)){
			sql.append(" and b.ssyx = '"+dwbh+"' ");
		}
		sql.append(")k where 1=1");
		//选中条件
		if(Validate.noNull(guid)){
			sql.append(" and  k.guid in ('"+guid.trim()+"') ");
		}
		//查询条件
		if(Validate.noNull(searchValue)){
			sql.append(ToSqlUtil.jsonToSql(searchValue));
		}
		sql.append("   order by ZYBH asc");
		return db.queryForList(sql.toString());
	}

}
