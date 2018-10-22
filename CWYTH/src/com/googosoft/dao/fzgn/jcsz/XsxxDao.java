package com.googosoft.dao.fzgn.jcsz;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.fzgn.jcsz.CW_JSYHZHB;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Repository("xsxxDao")
public class XsxxDao extends BaseDao {
	private Logger logger = Logger.getLogger(XsxxDao.class);

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
		if (result > 1) {
			return true;
		} else {
			return false;
		}
	}
	/**
	 * 教师银行账号删除
	 * @param 
	 * @return 
	 */
	public int doDeleteJsyhzh(String jsbh,CW_JSYHZHB jsyhzhb) {
		String sql = "DELETE CW_YHZHB WHERE jsbh"+CommonUtils.getInsql(jsbh);
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
	 * 教师银行账号表信息实体
	 * @param dwbh
	 * @return
	 */
	public List<Map<String, Object>> getObjectByIdYhzh(String guid) {
		String sql = "select guid,jsbh,YHMC,KHYHH,LHH from CW_YHZHB where jsbh=?";
		return db.queryForList(sql,new Object[]{guid});
	}
	/**
	 * 添加教师银行账号信息
	 * @param dmb
	 * @return
	 */
	public int doAddJsyhzh(CW_JSYHZHB jsyhzhb) {
		String sql = "insert into CW_YHZHB (guid,jsbh,YHMC,KHYHH,LHH)values(?,?,?,?,?)";
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
	 * 新增
	 * 
	 * @param ryb
	 * @return
	 */
	public int doAdd(CW_XSXXB xsxx) {
		String sql = "INSERT INTO CW_XSXXB(guid,xh,xm,xb_m,csrq,csd_m,jg,mz_m,gjdq_m,sfzjlx_m,sfzh,hyzk_m,zzmm_m,jkzk_m,czr) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		sql = String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[] {xsxx.getGuid(),xsxx.getXh(),xsxx.getXm(),xsxx.getXbm(),
				xsxx.getCsrq(),xsxx.getCsdm(),xsxx.getJg(),xsxx.getMzm(),xsxx.getGjdqm(),xsxx.getSfzjlxm(),
				xsxx.getSfzh(),xsxx.getHyzkm(),xsxx.getZzmmm(),xsxx.getJkzkm(),xsxx.getCzr()};
		return db.update(sql, obj);
	}
	/**
	 * 查询当前登录人的id
	 * @param rybh
	 * @return
	 */
	public String getLoginIdByRybh(String rybh){
		String sql = "select guid from GX_SYS_RYB where rybh='"+rybh+"'";
		String loginId = "";
		loginId = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "");
		return loginId;
	}
	public String getRybhByRybh(String rybh){
		String sql = "select rybh from GX_SYS_RYB where rybh='"+rybh+"'";
		String loginId = "";
		loginId = Validate.isNullToDefaultString(db.queryForSingleValue(sql), "");
		return loginId;
	}
	/**
	 * 获取实体类
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid){
		String sql = "SELECT A.GUID AS GUID, A.XH AS XH, A.XM AS XM," +
				"A.CSRQ AS CSRQ, A.JG AS JG,A.SFZH AS SFZH, A.XB_M AS XB_M," +
				"A.CSD_M AS CSD_M,A.MZ_M AS MZ_M, A.GJDQ_M AS GJDQ_M, A.SFZJLX_M AS SFZJLX_M, A.HYZK_M AS HYZK_M,"+
				"A.ZZMM_M AS ZZMM_M, A.JKZK_M AS JKZK_M"+
				" FROM CW_XSXXB A WHERE guid=?";
		
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[]{guid});
	}
	/**
	 * 修改人员信息
	 * @param rybh
	 * @return
	 */
	public int doUpdate(CW_XSXXB xsxx){
		String sql = "update CW_XSXXB set xh=?, xm=?, xb_m =?,csrq =?,csd_m =?,jg =?,MZ_M =?," +
				"GJDQ_M =?,SFZJLX_M=?, sfzh=?, HYZK_M=?, ZZMM_M =?,jkzk_m=?,czrq =? where guid=? ";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{xsxx.getXh(),xsxx.getXm(),xsxx.getXbm(),
				xsxx.getCsrq(),xsxx.getCsdm(),xsxx.getJg(),xsxx.getMzm(),xsxx.getGjdqm(),xsxx.getSfzjlxm(),
				xsxx.getSfzh(),xsxx.getHyzkm(),xsxx.getZzmmm(),xsxx.getJkzkm(),xsxx.getCzrq(),xsxx.getGuid()};
		return db.update(sql, obj);
	}
	/**
	 * 删除人员（暂时没有删除角色信息、管理权限和操作权限等，但是验证了名下有没有资产）
	 * @param rybh
	 * @return
	 */
	public int doDel(String xh){		
		String sql = "DELETE CW_XSXXB WHERE xh"+CommonUtils.getInsql(xh);
		String sql2 = "DELETE CW_XJXXB WHERE xh"+CommonUtils.getInsql(xh);
		sql=String.format(sql, SystemSet.gxBz);
		sql2=String.format(sql2, SystemSet.gxBz);
		Object[] obj = xh.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
			db.update(sql2, obj);
		} catch (DataAccessException e) {  
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 专业信息
	 * 查询专业信息
	 */
	public String findZyxx(String words,String dwbh) {
		String sql = " SELECT ZYBH FROM CW_ZYXXB WHERE TRIM('('||ZYBH||')'||ZYMC) = ? AND DWBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql,new Object[]{words,dwbh});
	}
	/**
	 * 专业信息
	 * 查询专业信息
	 */
	public List findZyxxList(String dwbh) {
		String sql = " SELECT ZYBH,ZYMC FROM CW_ZYXXB WHERE DWBH=?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForList(sql,new Object[]{dwbh});
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
				String csrq = rs.getCell(j++, i).getContents();
				String xslb = rs.getCell(j++, i).getContents();
				String szyx = rs.getCell(j++, i).getContents();
				String szzy = rs.getCell(j++, i).getContents();
				String sznj = rs.getCell(j++, i).getContents();
				String szbj = rs.getCell(j++, i).getContents();
				String mz = rs.getCell(j++, i).getContents();
				String sfzjlx = rs.getCell(j++, i).getContents();
				String sfzjh = rs.getCell(j++, i).getContents();
				String zzmm = rs.getCell(j++, i).getContents();
				map.put("xh", xh);
				map.put("xm", xm);
				map.put("xb", xb);
				map.put("csrq", csrq);
				map.put("xslb", xslb);
				map.put("szyx", szyx);
				map.put("szzy", szzy);
				map.put("sznj", sznj);
				map.put("szbj", szbj);
				map.put("mz", mz);
				map.put("sfzjlx", sfzjlx);
				map.put("sfzjh", sfzjh);
				map.put("zzmm", zzmm);
				list.add(map);
			
				String sql = "INSERT INTO CW_XSXXB(guid,xh,xm,xb_m,csrq,mz_m,sfzjlx_m,sfzh,zzmm_m)  "
						+ "values(sys_guid(),'"+xh+"','"+xm+"','" +xb+"','"+csrq+"','" +mz+"','" +sfzjlx+"','" +sfzjh+"','" +zzmm+"')";
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
	 * 导出学生信息Excel   wzd
	 * @return
	 */
	public List<Map<String, Object>> getXsList(String guid, String searchValue,String rybh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from");
		sql.append(" (select A.GUID AS GUID,  A.XH AS XH,  A.XM AS XM,  A.CSRQ AS CSRQ, (SELECT MC FROM GX_SYS_DMB WHERE ZL = '103'  ");
		sql.append(" AND DM = (select xslb from CW_XJXXB b where b.xh = a.guid)) AS XSLB, A.SFZH AS SFZH, ");
		sql.append(" (SELECT MC FROM GX_SYS_DMB WHERE ZL = '20'  AND DM = A.XB_M) AS XB, ");
		sql.append(" (SELECT MC   FROM GX_SYS_DMB WHERE ZL = '104'  AND DM = A.CSD_M) AS CSD, ");
		sql.append("  (SELECT MC   FROM GX_SYS_DMB  WHERE ZL = '105' AND DM = A.MZ_M) AS MZ, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '02' AND DM = A.GJDQ_M) AS GJDQ, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '111' AND DM = A.SFZJLX_M) AS SFZJLX, ");
		sql.append("  (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '108'  AND DM = A.HYZK_M) AS HYZK, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB WHERE ZL = '109'  AND DM = A.ZZMM_M) AS ZZMM, ");
		sql.append(" (SELECT MC  FROM GX_SYS_DMB  WHERE ZL = '110'  AND DM = A.JKZK_M) AS JKZK, ");
		sql.append("  (select '(' || d.bmh || ')' || to_char(d.mc)  from GX_SYS_DWB d  where d.bmh = (select yxbh from CW_XJXXB b where b.xh = a.guid)) AS SZYX, ");
		sql.append(" (SELECT ZYMC FROM CW_ZYXXB Z  WHERE Z.ZYBH = (select zybh from CW_XJXXB b where b.xh = a.guid)) AS SZZY, ");
		sql.append(" (SELECT BJMC FROM CW_BJXXB X WHERE X.BJBH = (select bjbh from CW_XJXXB b where b.xh = a.guid)) AS SZBJ, ");
		sql.append("  (SELECT NJMC  FROM CW_NJXXB N  WHERE N.NJBH = (select njbh from CW_XJXXB b where b.xh = a.guid)) AS SZNJ ");
		sql.append("   from CW_XSXXB A ) t where 1=1  ");
		sql.append(" and (select d.bmh from GX_SYS_DWB d where d.bmh = (select yxbh from CW_XJXXB b where b.xh = t.guid)) in");
		sql.append(" (select z.dwbh from ZC_SYS_RYDWQXB z left join gx_sys_dwb d on z.dwbh = d.dwbh where z.rybh = '"+guid.trim()+"')");
//		if(Validate.noNull(guid)){
//			sql.append(" and  a.guid in ('"+guid.trim()+"') ");
//		}
		sql.append("  order by XH asc ");
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}
}
