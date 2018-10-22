package com.googosoft.dao.fzgn.jcsz;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.LUser;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.CW_JSXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.service.base.PageService;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;

@Repository("jsxxsDao")
public class JsxxsDao extends BaseDao {
	@Resource(name = "pageService")
	private PageService pageService;
	private Logger logger = Logger.getLogger(JsxxsDao.class);

	/**
	 * 学号是否重复
	 * 
	 * @param xh
	 * @return
	 */
	public boolean checkXh(String xh) {
		String sql = "SELECT COUNT(1) FROM GX_SYS_RYB WHERE  RYBH = ?";
		sql = String.format(sql);
		Integer result = db.queryForObject(sql, new Object[] { xh },
				Integer.class);
		if (result > 0) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 排序序号是否重复
	 * 
	 * @param xh
	 * @return
	 */
	public boolean checkPxxh(String pxxh,String guid) {
		String sql = "SELECT COUNT(1) FROM cw_jzgxxb WHERE  pxxh = ? and guid !=?";
		sql = String.format(sql);
		Integer result = db.queryForObject(sql, new Object[] { pxxh,guid },
				Integer.class);
		if (result > 0) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * 新增
	 * 
	 * @param ryb
	 * @return
	 */
	public int doAdd(CW_JSXXB jsxx) {
		String delSql = "DELETE FROM CW_JZGXXB WHERE GUID =?";
		String search = "select mm from gx_sys_ryb where rybh=?";
		String mm = Validate.isNullToDefaultString(db.queryForSingleValue(search,new Object[]{jsxx.getXh()}),"2D53C2CEB978784CB5FB575FC36EFC2E12C68715");
		int m = db.update(delSql, new Object[]{jsxx.getGuid()});
		String sql = "INSERT INTO CW_JZGXXB(GUID,XH,XM,XBM,CSRQ,CSDM,JG,MZM,GJDQM,SFZJLXM,SFZH,HYZKM,ZZMMM,JKZKM,WHCD,ZC,ZW,LXSJ,SZDW,LXFS,YX,CZR,CZRQ," +
				"zwhzcjb,zxdyzwhzcjb,zzlx,zzjzglx,zzryly,rysf,lscs,sfssmz,cjgzsj,gl,u1,bxjb,pxxh,xkly,yjxk,yjfx) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] { 
				jsxx.getGuid(), 
				Validate.isNullToDefault(jsxx.getXh(), ""),
				Validate.isNullToDefault(jsxx.getXm(), ""),
				Validate.isNullToDefault(jsxx.getXbm(), ""),
				Validate.isNullToDefault(jsxx.getCsrq(), ""),
				Validate.isNullToDefault(jsxx.getCsdm(), ""),
				Validate.isNullToDefault(jsxx.getJg(), ""),
				Validate.isNullToDefault(jsxx.getMzm(), ""),
				Validate.isNullToDefault(jsxx.getGjdqm(), ""),
				Validate.isNullToDefault(jsxx.getSfzjlxm(), ""),
				Validate.isNullToDefault(jsxx.getSfzh(), ""),
				Validate.isNullToDefault(jsxx.getHyzkm(), ""),
				Validate.isNullToDefault(jsxx.getZzmmm(), ""),
				Validate.isNullToDefault(jsxx.getJkzkm(), ""),
				Validate.isNullToDefault(jsxx.getWhcd(), ""),
				Validate.isNullToDefault(jsxx.getZc(), ""),
				Validate.isNullToDefault(jsxx.getZw(), ""),
				Validate.isNullToDefault(jsxx.getLxsj(), ""),
				Validate.isNullToDefault(jsxx.getSzdw(), ""),
				Validate.isNullToDefault(jsxx.getLxfs(), ""),
				Validate.isNullToDefault(jsxx.getYx(), ""),
				Validate.isNullToDefault(jsxx.getCzr(), ""),
				Validate.isNullToDefault(jsxx.getCzrq() , ""),
				Validate.isNullToDefault(jsxx.getZwhzcjb(), ""),
				Validate.isNullToDefault(jsxx.getZxdyzwhzcjb(), ""),
				Validate.isNullToDefault(jsxx.getZzlx(), ""),
				Validate.isNullToDefault(jsxx.getZzjzglx(), ""),
				Validate.isNullToDefault(jsxx.getZzryly(), ""),
				Validate.isNullToDefault(jsxx.getRysf(), ""),
				Validate.isNullToDefault(jsxx.getLscs(), ""),
				Validate.isNullToDefault(jsxx.getSfssmz(), ""),
				Validate.isNullToDefault(jsxx.getCjgzsj(), ""),
				Validate.isNullToDefault(jsxx.getGl(), ""),
				Validate.isNullToDefault(mm, ""),
				Validate.isNullToDefault(jsxx.getBxjb(), ""),
				Validate.isNullToDefault(jsxx.getPxxh(), ""),
				Validate.isNullToDefault(jsxx.getXkly(), ""),
				Validate.isNullToDefault(jsxx.getYjxk(), ""),
				Validate.isNullToDefault(jsxx.getYjfx(), "")
			};
		return db.update(sql, obj);
	}
	/**
	 * 获取实体类
	 * 
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getJzgkzxxById(String guid) {
		String sql = "SELECT gzgxblry,gzlb,gjhsjxmndhj,aykzgzjbtxmhj,jbgzhj,zwgz,jbgz,jsdjgz,syqgz,aykzjtbthj,sjgbzgf,dsznbt,ssmzbt,fgspfybaryjt,"
				+"jxjtjczq,jcgjcjcybarybt,jjjcjgbabt,gjswgzryswzsjt,sjjgsjrygzbt,mmrygwjt,fydwhybjjt,"
				+"xfgzrygwjt,zzcnbt,jlxjxgz,jswmj,qtanffxm,"
				+"ayjnxmhj,zfgjj,sybx,zynj,ylbx,ylbxhgssy,anjnxmhj,dbbx,qtbx,"
				+"sfjngjjhshbzjf,dwcgyjfzkzxmhj,ayffxmhj,gjhsjgddjbthj,pfdjxgz,jxgzsfbf,"
				+"ndxmzj,czbzkzsjsjs,bz,lylx,qttsgwjt,jcxjxgz,zfbt,zzywfwbt,wfhbt,agdffdqtggxbt,jpzdjxgzsfbf,xm,jzgguid"
				+ " FROM CW_JZGKZXXB A WHERE GUID=?";
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			sql = String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
			map = db.queryForMap(sql, new Object[] { guid });
		} catch (Exception e) {
			System.out.println("异常爆出-------");
		}
		return map;
	}
	/**
	 * 新增
	 * 
	 * @param ryb
	 * @return
	 */
	public int doAdd1(CW_JZGKZXXB jsxx) {
		String delSql = "DELETE FROM CW_JZGKZXXB WHERE GUID =?";
		int m = db.update(delSql, new Object[]{jsxx.getGuid()});
		String czr = LUser.getGuid();
		String sysDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		jsxx.setCzr(czr);
		jsxx.setCzrq(sysDate);
		String sql = "INSERT INTO CW_JZGKZXXB(guid,czr,czrq,gzgxblry,gzlb,gjhsjxmndhj,aykzgzjbtxmhj,jbgzhj," +
				"zwgz,jbgz,jsdjgz,syqgz,aykzjtbthj,sjgbzgf,dsznbt,ssmzbt,fgspfybaryjt,"+
				"jxjtjczq,jcgjcjcybarybt,jjjcjgbabt,gjswgzryswzsjt,sjjgsjrygzbt,mmrygwjt,fydwhybjjt,"+
				"xfgzrygwjt,zzcnbt,jlxjxgz,jswmj,qtanffxm,"+
				"ayjnxmhj,zfgjj,sybx,zynj,ylbx,ylbxhgssy,anjnxmhj,dbbx,qtbx,"+
				"sfjngjjhshbzjf,dwcgyjfzkzxmhj,ayffxmhj,gjhsjgddjbthj,pfdjxgz,jxgzsfbf,"+
				"ndxmzj,czbzkzsjsjs,bz,lylx,qttsgwjt,jcxjxgz,zfbt,zzywfwbt,wfhbt,agdffdqtggxbt,jpzdjxgzsfbf,xm,jzgguid) "
				+ "VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] {jsxx.getGuid(),jsxx.getCzr(),jsxx.getCzrq(),jsxx.getGzgxblry(),jsxx.getGzlb(),
				jsxx.getGjhsjxmndhj(),jsxx.getAykzgzjbtxmhj(),jsxx.getJbgzhj(),
				jsxx.getZwgz(),jsxx.getJbgz(),jsxx.getJsdjgz(),jsxx.getSyqgz(),jsxx.getAykzjtbthj(),jsxx.getSjgbzgf(),
				jsxx.getDsznbt(),jsxx.getSsmzbt(),jsxx.getFgspfybaryjt(),jsxx.getJxjtjczq(),jsxx.getJcgjcjcybarybt(),jsxx.getJjjcjgbabt(),
				jsxx.getGjswgzryswzsjt(),jsxx.getSjjgsjrygzbt(),jsxx.getMmrygwjt(),jsxx.getFydwhybjjt(),
				jsxx.getXfgzrygwjt(),jsxx.getZzcnbt(),jsxx.getJlxjxgz(),jsxx.getJswmj(),
				jsxx.getQtanffxm(),jsxx.getAyjnxmhj(),jsxx.getZfgjj(),jsxx.getSybx(),jsxx.getZynj(),jsxx.getYlbx(),
				jsxx.getYlbxhgssy(),jsxx.getAnjnxmhj(),jsxx.getDbbx(),jsxx.getQtbx(),
				jsxx.getSfjngjjhshbzjf(),jsxx.getDwcgyjfzkzxmhj(),jsxx.getAyffxmhj(),jsxx.getGjhsjgddjbthj(),
				jsxx.getPfdjxgz(),jsxx.getJxgzsfbf(),jsxx.getNdxmzj(),jsxx.getCzbzkzsjsjs(),jsxx.getBz(),jsxx.getLylx(),
				jsxx.getQttsgwjt(),jsxx.getJcxjxgz(),jsxx.getZfbt(),jsxx.getZzywfwbt(),jsxx.getWfhbt(),jsxx.getAgdffdqtggxbt(),
				jsxx.getJpzdjxgzsfbf(),jsxx.getXm(),jsxx.getJzgguid()};
		return db.update(sql, obj);
	}
	/**
	 * 教师银行账号删除
	 * @param 
	 * @return 
	 */
	public int doDeleteJsyhzh(String jsbh,CW_JSYHZHB jsyhzhb) {
		String sql = "DELETE CW_JSYHZHB WHERE jsbh"+CommonUtils.getInsql(jsbh);
		sql=String.format(sql, SystemSet.gxBz);	
		
		int i = 0;
		try {  
			i = db.update(sql, new Object[] {jsbh});
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 添加教师银行账号信息
	 * @param dmb
	 * @return
	 */
	public int doAddJsyhzh(CW_JSYHZHB jsyhzhb) {
		String sql = "insert into cw_jsyhzhb (guid,jsbh,khyh,khyhzh,yhlhh)values(?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);		
		int i = 0;
		try{
			i = db.update(sql,new Object[]{jsyhzhb.getGuid(),jsyhzhb.getJsbh(),jsyhzhb.getKhyh(),jsyhzhb.getKhyhzh(),jsyhzhb.getYhlhh()});
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 教师银行账号表信息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getObjectByIdYhzh(String guid) {
		String sql = "select guid,jsbh,khyh,khyhzh,yhlhh from cw_jsyhzhb where jsbh=?";
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 查询当前登录人的id
	 * 
	 * @param rybh
	 * @return
	 */
	public String getLoginIdByRybh(String rybh) {
		String sql = "select guid from GX_SYS_RYB where rybh='" + rybh + "'";
		String loginId = "";
		loginId = Validate.isNullToDefaultString(db.queryForSingleValue(sql),
				"");
		return loginId;
	}

	/**
	 * 获取实体类
	 * 
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "SELECT A.GUID,A.XH,A.XM,A.XBM,A.CSRQ CSRQ,A.CSDM,A.JG,A.MZM,A.GJDQM,A.SFZJLXM,A.SFZH,A.HYZKM,A.ZZMMM,A.BXJB,"
				+ "A.JKZKM,A.WHCD,A.ZC,A.ZW,TO_char(A.LXSJ,'YYYY-MM-DD')LXSJ,A.SZDW,A.LXFS,A.YX,A.CZR,TO_DATE(A.CZRQ,'YYYY-MM-DD HH24:MI:SS')CZRQ,"
				+"zwhzcjb,zxdyzwhzcjb,zzlx,zzjzglx,zzryly,rysf,sfssmz,cjgzsj,gl,pxxh,"
				+" xkly,yjxk,yjfx,"
				+"(SELECT '('||D.BMH||')'||D.MC FROM %Sdwb D WHERE D.DWBH=A.LSCS) AS LSCS,"
				+ "(SELECT '('||D.BMH||')'||D.MC FROM %Sdwb D WHERE D.DWBH=A.SZDW) AS SZDW FROM CW_JZGXXB A WHERE GUID=?";
		sql = String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[] { guid });
	}
	/**
	 * 获取教职工guid
	 * @param guid
	 * @return
	 */
	public String getLoginIdByKz(String guid) {
		String sql = "select guid from CW_JZGKZXXB where jzgguid='" + guid + "'";
		String jzgguid = "";
		jzgguid = Validate.isNullToDefaultString(db.queryForSingleValue(sql),
				"");
		return jzgguid;
	}
	/**
	 * 修改人员信息
	 * 
	 * @param rybh
	 * @return
	 */
	public int doUpdate(CW_JSXXB jsxx) {
		String sql = "UPDATE CW_JZGXXB SET XH=?, XM=?, XBM =?,CSRQ =?,CSDM =?,JG =?,MZM =?,"
				+ "GJDQM =?,SFZJLXM=?, SFZH=?, HYZKM=?, ZZMMM =?,JKZKM=?, WHCD=?, ZC=?, ZW=?, LXSJ=?, SZDW=?,"
				+ " LXFS =?,YX=?,CZR=?,CZRQ=?,BXJB=? WHERE GUID=?";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] { jsxx.getXh(), jsxx.getXm(),
				jsxx.getXbm(), jsxx.getCsrq(), jsxx.getCsdm(), jsxx.getJg(),
				jsxx.getMzm(), jsxx.getGjdqm(), jsxx.getSfzjlxm(),
				jsxx.getSfzh(), jsxx.getHyzkm(), jsxx.getZzmmm(),
				jsxx.getJkzkm(), jsxx.getWhcd(), jsxx.getZc(), jsxx.getZw(),
				jsxx.getLxsj(), jsxx.getSzdw(), jsxx.getLxfs(), jsxx.getYx(),
				jsxx.getCzr(), jsxx.getCzrq(), jsxx.getBxjb(),jsxx.getGuid() };
		return db.update(sql, obj);
	}

	/**
	 * 删除人员（暂时没有删除角色信息、管理权限和操作权限等，但是验证了名下有没有资产）
	 * 
	 * @param rybh
	 * @return
	 */
	public int doDel(String guid) {
		final Object[] params = guid.split("','");
		String wstr = CommonUtils.getInsql(guid);
		String sqlryxx = "DELETE %SRYB WHERE GUID " + wstr;
		String sqljsb = "DELETE zc_sys_jsryb WHERE rybh in(select rybh from gx_sys_ryb where guid in('"+guid+"'))";
		String xsxx = "DELETE FROM CW_JZGXXB WHERE GUID " + wstr;
		String DELSQL = "DELETE FROM CW_JZGKZXXB WHERE JZGGUID " + wstr;
		sqlryxx = String.format(sqlryxx, SystemSet.gxBz);
		xsxx = String.format(xsxx, SystemSet.gxBz);
		DELSQL = String.format(DELSQL, SystemSet.gxBz);
		int result = 0;
		try {
			db.update(sqljsb);
			result = db.update(sqlryxx, params);
			result = db.update(xsxx, params);
			int i = db.update(DELSQL, params);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();
			logger.error("数据库操作失败\n" + sqle);
			result = -1;
			TransactionAspectSupport.currentTransactionStatus()
					.setRollbackOnly();// 事务回滚
		}
		return result;
	}
	/**
	 * 导入
	 * @param file
	 * @return
	 */
	public List insertJcsj(String file) {
		Workbook rwb;
		List list = new ArrayList();
		List sqlList = new ArrayList();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			int j = 0;
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				j=0;
				Map map = new HashMap();
				String xh = rs.getCell(j++, i).getContents();
				String xm = rs.getCell(j++, i).getContents();
				String xb = rs.getCell(j++, i).getContents();
				String dw = rs.getCell(j++, i).getContents();
				String zc = rs.getCell(j++, i).getContents();
				String zw = rs.getCell(j++, i).getContents();
				String csd = rs.getCell(j++, i).getContents();
				String sfzjlx = rs.getCell(j++, i).getContents();
				String sfzjh = rs.getCell(j++, i).getContents();
				String csrq = rs.getCell(j++, i).getContents();
				String mzm = rs.getCell(j++, i).getContents();
				String hyzk = rs.getCell(j++, i).getContents();
				String zzmm = rs.getCell(j++, i).getContents();
				String lxfs = rs.getCell(j++, i).getContents();
				map.put("xh", xh);
				map.put("xm", xm);
				map.put("xb", xb);
				map.put("dw", dw);
				map.put("zc", zc);
				map.put("zw", zw);
				map.put("csd", csd);
				map.put("sfzjlx", sfzjlx);
				map.put("sfzjh", sfzjh);
				map.put("csrq", csrq);
				map.put("mzm", mzm);
				map.put("hyzk", hyzk);
				map.put("zzmm", zzmm);
				map.put("lxfs", lxfs);
				list.add(map);
				String sql = "insert into cw_jzgxxb(guid,xh,xm,xbm,szdw,zc,zw,csdm,SFZJLXM,sfzh,csrq,mzm,hyzkm,zzmmm,lxfs) "
						+ "values(sys_guid(),'"+xh+"','"+xm+"'," +
								"'"+xb+"'," +
								"(select dwbh from gx_sys_dwb where mc='"+dw+"')," +
								"(select dm from gx_sys_dmb where zl = '"+Constant.ZYZC+"' and mc = '"+zc+"')," +
								"(select dm from gx_sys_dmb where zl = '"+Constant.ZW+"' and mc = '"+zw+"')," +
								"(select dm from gx_sys_dmb where zl = '"+Constant.QYDM+"' and mc = '"+csd+"')," +
								"(select dm from gx_sys_dmb where zl = '"+Constant.ZJLX+"' and mc = '"+sfzjlx+"')," +
								"'"+sfzjh+"'," +
								"to_char(to_date('"+csrq+"','yyyy-mm-dd'),'yyyy-MM-dd'),"
										+ "(select dm from gx_sys_dmb where zl = '"+Constant.MZ+"' and mc = '"+mzm+"'),"
										+ "(select dm from gx_sys_dmb where zl = '"+Constant.HYZK+"' and mc = '"+hyzk+"'),"
										+ "(select dm from gx_sys_dmb where zl = '"+Constant.ZZMM+"' and mc = '"+zzmm+"'),"
												+ "'"+lxfs+"')";
				sqlList.add(sql);
			}
			db.batchUpdate(sqlList);
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	/**
	 * 根据人员权限查询教师信息list
	 * @param guid
	 * @param searchValue
	 * @param rybh
	 * @param s1
	 * @param s2
	 * @param s3
	 * @param s4
	 * @param s5
	 * @param s6
	 * @param s7
	 * @param s8
	 * @param s9
	 * @param s10
	 * @return
	 */
	public List<Map<String, Object>> getJsList(String guid, String searchValue,
			String rybh, String s1, String s2, String s3, String s4, String s5,
			String s6, String s7, String s8, String s9, String s10,String dwbh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select A.GUID,  A.XH, A.XM,  A.JG, A.SFZH, LXFS,  A.YX, TO_CHAR(A.CJGZSJ, 'YYYY-MM-DD') CJGZSJ,  GL, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '"+s1+"'  AND DM = A.XBM) AS XBM, ");
		sql.append(" DECODE(ZZLX, '0', '正式', '1', '试用', '2', '实习', '兼职') ZZLX, DECODE(NVL(ZZRYLY, '0'), '1', '网聘', '校招') ZZRYLY, A.CSRQ,A.CZRQ,TO_CHAR(A.LXSJ, 'YYYY-MM-DD') LXSJ, ");
		sql.append("  (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '"+s2+"' AND DM = A.CSDM) AS CSDM, ");
		sql.append("  (SELECT MC FROM GX_SYS_DMB WHERE ZL = '"+s3+"' AND DM = A.SFZJLXM) AS SFZJLXM, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '"+s4+"' AND DM = A.MZM) AS MZM, A.gjdqm, ");
		sql.append("  (SELECT MC FROM GX_SYS_DMB  WHERE ZL = '"+s5+"'  AND DM = A.HYZKM) AS HYZKM, ");
		sql.append("  (SELECT MC FROM GX_SYS_DMB WHERE ZL = '"+s6+"' AND DM = A.ZZMMM) AS ZZMMM, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB  WHERE ZL = '"+s7+"'  AND DM = A.JKZKM) AS JKZKM, ");
		sql.append(" (SELECT '(' || D.BMH || ')' || D.MC  FROM GX_SYS_DWB D  WHERE D.DWBH = A.SZDW) AS SZDW, ");
		sql.append("  (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '"+s8+"'  AND DM = A.WHCD) AS WHCD, ");
		sql.append("  (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '"+s9+"' AND DM = A.ZC) AS ZC, ");
		sql.append("  (SELECT MC FROM GX_SYS_DMB WHERE ZL = '"+s10+"' AND DM = A.ZW) AS ZW, ");
		sql.append(" (SELECT D.XM FROM CW_JZGXXB D WHERE D.GUID = A.CZR) AS CZR from CW_JZGXXB A ");
		sql.append("  where 1 = 1  ");
		if(Validate.noNull(guid)){
			sql.append(" and  a.guid in ('"+guid.trim()+"') ");
		}
		if(Validate.noNull(searchValue)){
			sql.append(ToSqlUtil.jsonToSql(searchValue));
		}
		sql.append(" and A.SZDW in (select z.dwbh from ZC_SYS_RYDWQXB z left join gx_sys_dwb d on z.dwbh = d.dwbh where z.rybh = '"+rybh+"') order by XH asc");
//		sql.append("  where 1 = 1 and A.SZDW in (select z.dwbh from ZC_SYS_RYDWQXB z  left join gx_sys_dwb d  on z.dwbh = d.dwbh ");
//		sql.append("  where z.rybh = '"+rybh+"' and a.guid "+CommonUtils.getInsql(guid)+" ) order by XH asc ");
		
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}

}
