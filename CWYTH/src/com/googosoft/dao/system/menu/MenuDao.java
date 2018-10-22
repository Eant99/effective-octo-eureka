package com.googosoft.dao.system.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;


@Repository("menuDao")
public class MenuDao extends BaseDao{
	/**
	 * 获取mkb按钮功能
	 * @return
	 * @throws Exception
	 */
	public List findMkbList(String rybh){
		List list = new ArrayList();
		if("000000".equals(rybh)){
			list = db.queryForList("select case when length(mkbh) = 6 then  '1' else  '0' end isleaf, "
				+ "case when length(mkbh)=2 then '1' when length(mkbh)=4 then '2' else '3' end levels, "
				+ "nvl(icon,'icon-cogs') as mclass,mkbh as mgid,mkmc as mname, "
				+ "CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END MPARENT, "
				+ "URL AS MURL "
				+ " FROM "+TnameU.MKB+" where qxbz='1' and nvl(mklx,'0')<>'3' order by mkbh ");

		}else{
			list = db.queryForList("select case when length(mkbh) = 6 then  '1' else  '0' end isleaf, "
				+ "case when length(mkbh)=2 then '1' when length(mkbh)=4 then '2' else '3' end levels, "
				+ "nvl(icon,'icon-cogs') as mclass,mkbh as mgid,mkmc as mname, "
				+ "CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END MPARENT,URL AS MURL "
				+ " FROM "+TnameU.MKB+" WHERE ((LENGTH(MKBH)=6 AND (MKBH IN(SELECT DISTINCT Z.MKBH FROM "+TnameU.CZQXB+" Z WHERE Z.RYBH =?)"
				+ " OR MKBH IN(SELECT DISTINCT J.MKBH FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
				+ "OR (LENGTH(MKBH)=4 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,4) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =?) "
				+ "OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,4) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
				+ "OR (LENGTH(MKBH)=2 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,2) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =? ) "
				+ "OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,2) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))))"
				+ " and nvl(qxbz,'0')='1' and nvl(mklx,'0')<>'3' order by levels,xh ",new Object[]{rybh,rybh,rybh,rybh,rybh,rybh});
		}
		return list;
	}
	
	/**
	 * 获取mkb按钮功能
	 * @return
	 * @throws Exception
	 */
	public List findMkbListnew(String rybh){
		List list = new ArrayList();
		if("000000".equals(rybh)){
			list = db.queryForList("select case when length(mkbh) = 6 then  '1' else  '0' end isleaf, "
				+ "case when length(mkbh)=2 then '1' when length(mkbh)=4 then '2' else '3' end levels, "
				+ "nvl(icon,'icon-cogs') as mclass,mkbh as mgid,mkmc as mname, "
				+ "CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END MPARENT, "
				+ "URL AS MURL "
				+ " FROM "+TnameU.MKB+" where qxbz='1' and nvl(mklx,'0')<>'3' order by mkbh ");

		}else{
			list = db.queryForList("select case when length(mkbh) = 6 then  '1' else  '0' end isleaf, "
				+ "case when length(mkbh)=2 then '1' when length(mkbh)=4 then '2' else '3' end levels, "
				+ "nvl(icon,'icon-cogs') as mclass,mkbh as mgid,mkmc as mname, "
				+ "CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END MPARENT,URL AS MURL "
				+ " FROM "+TnameU.MKB+" WHERE ((LENGTH(MKBH)=6 AND (MKBH IN(SELECT DISTINCT Z.MKBH FROM "+TnameU.CZQXB+" Z WHERE Z.RYBH =?)"
				+ " OR MKBH IN(SELECT DISTINCT J.MKBH FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
				+ "OR (LENGTH(MKBH)=4 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,4) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =?) "
				+ "OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,4) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
				+ "OR (LENGTH(MKBH)=2 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,2) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =? ) "
				+ "OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,2) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))))"
				+ " and nvl(qxbz,'0')='1' and nvl(mklx,'0')<>'3' order by levels,xh ",new Object[]{rybh,rybh,rybh,rybh,rybh,rybh});
		}
		return list;
	}
	/**
	 * 获取mkb地址
	 * @return
	 * @throws Exception
	 */
	public Map getMkUrl(String mkbh) {
		String sql = "select url,mkmc from "+SystemSet.sysBz+"mkb where mkbh=?";
		return db.queryForMap(sql,new Object[]{mkbh});
	}
	
	/**
	 * 获取一级菜单
	 * @return
	 */
	public List getYjcdList(String rybh) {
		if("000000".equals(rybh)){
			String sql = "select mkbh as mgid,mkmc as mname,nvl(icon,'icon-cogs') as mclass,URL AS MURL, "
					+ " (CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END) MPARENT "
					+ " FROM "+TnameU.MKB
					+ " where qxbz='1' and nvl(mklx,'0')<>'3' and length(mkbh)=2 order by xh,mkbh";
			System.err.println("++++++++"+sql);
			return db.queryForList(sql);
		}else{
			String sql = "select mkbh as mgid,mkmc as mname,nvl(icon,'icon-cogs') as mclass,URL AS MURL, "
					+ " (CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END) MPARENT "
					+ " FROM "+TnameU.MKB
					+ " where ((LENGTH(MKBH)=6 AND (MKBH IN(SELECT DISTINCT Z.MKBH FROM "+TnameU.CZQXB+" Z WHERE Z.RYBH =?)"
					+ " OR MKBH IN(SELECT DISTINCT J.MKBH FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
					+ " OR (LENGTH(MKBH)=4 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,4) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =?) "
					+ " OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,4) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
					+ " OR (LENGTH(MKBH)=2 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,2) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =? ) "
					+ " OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,2) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))))"
					+ " and qxbz='1' and nvl(mklx,'0')<>'3' and length(mkbh)=2 order by xh,mkbh  ";
			System.err.println("++++++++"+sql);
			return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh,rybh,rybh});
		}
	}

	/**
	 * 获取二级菜单
	 * @param mkbh
	 * @return
	 */
	public List getEjcdList(String mkbh,String rybh) {
		if("000000".equals(rybh)){
			String sql = "select mkbh as mgid,mkmc as mname,nvl(icon,'icon-cogs') as mclass,URL AS MURL, "
					+ " (CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END) MPARENT "
					+ " FROM "+TnameU.MKB+" t where qxbz='1' and nvl(mklx,'0')<>'3' and length(mkbh)=4 and substr(mkbh,1,2)=? order by xh,mkbh  ";
			return db.queryForList(sql,new Object[]{mkbh});
		}else{
			String sql = "select mkbh as mgid,mkmc as mname,nvl(icon,'icon-cogs') as mclass,URL AS MURL, "
					+ " (CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END) MPARENT  "
					+ " FROM "+TnameU.MKB+" t "
					+ " where ((LENGTH(MKBH)=6 AND (MKBH IN(SELECT DISTINCT Z.MKBH FROM "+TnameU.CZQXB+" Z WHERE Z.RYBH =?)"
					+ " OR MKBH IN(SELECT DISTINCT J.MKBH FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
					+ " OR (LENGTH(MKBH)=4 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,4) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =?) "
					+ " OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,4) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
					+ " OR (LENGTH(MKBH)=2 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,2) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =? ) "
					+ " OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,2) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))))"
					+ " and qxbz='1' and nvl(mklx,'0')<>'3' and length(mkbh)=4 and substr(mkbh,1,2)=? order by xh,mkbh  ";
			return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh,rybh,rybh,mkbh});
		}
	}
	
	/**
	 * 获取三级菜单
	 * @param mkbh
	 * @return
	 */
	public List getSjcdList(String mkbh,String rybh) {
		if("000000".equals(rybh)){
			String sql = "select mkbh as mgid,mkmc as mname,nvl(icon,'icon-cogs') as mclass,URL AS MURL, "
					+ " (CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END) MPARENT "
					+ " FROM "+TnameU.MKB+" "
					+ " where qxbz='1' and nvl(mklx,'0')<>'3' and length(mkbh)=6 and substr(mkbh,1,4)=? order by xh,mkbh  ";
			return db.queryForList(sql,new Object[]{mkbh});
		}else{
			String sql = "select mkbh as mgid,mkmc as mname,nvl(icon,'icon-cogs') as mclass,URL AS MURL, "
					+ " (CASE WHEN LENGTH(MKBH)=2 THEN '0' ELSE SUBSTR(MKBH,1,LENGTH(MKBH)-2) END) MPARENT "
					+ " FROM "+TnameU.MKB
					+ " where ((LENGTH(MKBH)=6 AND (MKBH IN(SELECT DISTINCT Z.MKBH FROM "+TnameU.CZQXB+" Z WHERE Z.RYBH =?)"
					+ " OR MKBH IN(SELECT DISTINCT J.MKBH FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
					+ " OR (LENGTH(MKBH)=4 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,4) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =?) "
					+ " OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,4) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))) "
					+ " OR (LENGTH(MKBH)=2 AND (mkbh in(SELECT DISTINCT SUBSTR(Z.MKBH,1,2) FROM "+TnameU.CZQXB+" Z WHERE z.RYBH =? ) "
					+ " OR MKBH IN(SELECT DISTINCT SUBSTR(J.MKBH,1,2) FROM "+TnameU.JSCZQXB+" J WHERE J.JSBH='"+MenuFlag.MRJS+"' OR J.JSBH IN(SELECT R.JSBH FROM "+TnameU.JSRYB+" R WHERE R.RYBH=?)))))"
					+ " and qxbz='1' and nvl(mklx,'0')<>'3' and length(mkbh)=6 and substr(mkbh,1,4)=? order by xh,mkbh  ";
			return db.queryForList(sql,new Object[]{rybh,rybh,rybh,rybh,rybh,rybh,mkbh});
		}
	}
}
