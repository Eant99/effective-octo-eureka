package com.googosoft.dao.xzgl;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.FileDao;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

@Repository("xzglDao")
public class XzglDao extends BaseDao {

	private Logger logger = Logger.getLogger(FileDao.class);

	// 查询，在职薪资表
	public List<Map<String, Object>> getXzList(PageData pd) {
		String rybh = pd.getString("rybh");
		StringBuffer sql = new StringBuffer();
		sql.append("select * from CW_XZB");
		if (Validate.noNull(rybh)) {
			sql.append(" where rybh like '%" + rybh + "%' ");
		}
		return db.queryForList(sql.toString());
	}

	// 查询，工资维护list
	public List<Map<String, Object>> getGzwhList() {
		StringBuffer sql = new StringBuffer();
		sql.append("select guid,xmjc,xmmc from CW_GZXMB where 1=1   order by xh ");
		return db.queryForList(sql.toString());
	}
	
	public List<Map<String, Object>> getList() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.GUID, t.XMJC, t.XMMC\r\n" + 
				"  from CW_GZXMB t\r\n" + 
				" where 1 = 1\r\n" + 
				"   and t.xmjc not in ('shzt', 'rybh', 'xm', 'xl', 'bm', 'ryjb', 'rylx',\r\n" + 
				"        'gzyf', 'bh', 'gh', 'sfzb', 'sfdy', 'rdqk', 'xh')\r\n" + 
				" order by XMMC asc ");
		return db.queryForList(sql.toString());
	}
	
	public List<Map<String, Object>> getListy() {
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from CW_XZXMB where xmjc not in ('shzt', 'rybh', 'xm', 'bh', 'bm' ,'rylb','yfhj','sfhj') order by XMMC asc ");
		return db.queryForList(sql.toString());
	}
	
	public int doPlfuzhi(String ids,String ziduan,Object zdValue,String ids2){
		String sql = "";
		if("nonono".equals(ids)) {
			sql = " update CW_XZB set "+ziduan+" ='"+zdValue+"' where 1=1 and guid"+CommonUtils.getInsql(ids2);
			Object[] obj = ids2.split(",");
//			Object[] objs = new Object[obj.length+1];
			int i = db.update(sql,obj);
			return i;
		}else {
			sql = " update CW_XZB set "+ziduan+" ='"+zdValue+"' where 1=1 and guid"+CommonUtils.getInsql(ids);
			Object[] obj = ids.split(",");
//			Object[] objs = new Object[obj.length+1];
			int i = db.update(sql,obj);
			return i;
		}
	}
	public List cxidlist(PageData pd) {
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"), "");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"), "");
		String xm = Validate.isNullToDefaultString(pd.getString("xm"), "");
		String bm = Validate.isNullToDefaultString(pd.getString("bm"), "");
		String sql = " select T.GUID, t.xm, t.bm, t.gzyf, t.shzt, t.rybh\r\n" + 
				"       \r\n" + 
				"  from (select T.GUID,\r\n" + 
				"               T.RYBH,\r\n" + 
				"               T.XM,\r\n" + 
				"               T.BM,\r\n" + 
				"               T.RYLB,\r\n" + 
				"               T.RYLX,\r\n" + 
				"               t.shzt as shzt, t.gzyf\r\n" + 
				"               \r\n" + 
				"          from CW_XZB T\r\n" + 
				"        union\r\n" + 
				"        select '1' GUID,\r\n" + 
				"               '' RYBH,\r\n" + 
				"               '' XM,\r\n" + 
				"               '' BM,\r\n" + 
				"               '' RYLB,\r\n" + 
				"               '' RYLX,\r\n" + 
				"               '' SHZT, t.gzyf\r\n" + 
				"              \r\n" + 
				"          from CW_XZB T\r\n" + 
				"         ) T\r\n" + 
				" where 1 = 1 "; 
		if(Validate.noNull(rybh)) {
			sql += " and (t.rybh in ('"+rybh.replace(",", "','")+"') or t.rybh is null) ";
		}
		if(Validate.noNull(shzt)) {
			sql += " and (t.shzt ='"+shzt+"' or t.shzt is null) ";
		}
		if(Validate.noNull(gzyf)) {
			sql += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null) ";
		}
		if(Validate.noNull(xm)) {
			sql += " and (t.xm like '%"+xm+"%' or t.xm is null) ";
		}
		if(Validate.noNull(bm)) {
			sql += " and (t.bm like '%"+CommonUtil.getEndText(bm)+"%' or t.bm is null ) ";
		}
		return db.queryForList(sql);
	}
	
	public List cxidlist2(PageData pd) {
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"), "");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"), "");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"), "");
		String sql = " select * from (select L.GUID,\r\n" + 
				"               L.RYBH,\r\n" + 
				"               L.XM,\r\n" + 
				"               L.BM,\r\n" + 
				"               L.RYLB,\r\n" + 
				"               L.SHZT AS SHZT,L.GZYF\r\n" + 
				"               \r\n" + 
				"          from CW_LZXZB L\r\n" + 
				"        union\r\n" + 
				"        select '1' as GUID,\r\n" + 
				"               '' as RYBH,\r\n" + 
				"               '' as XM,\r\n" + 
				"               '' as BM,\r\n" + 
				"               '' as RYLB,\r\n" + 
				"               '' AS SHZT,L.GZYF\r\n" + 
				"          from CW_LZXZB L\r\n" + 
				"        ) L\r\n" + 
				" where 1 = 1\r\n" + 
				"   and (l.shzt like '%0%' or l.rybh is null) " + 
				"   and (l.gzyf like '%2018.02%' or l.gzyf is null) " + 
				" "; 
		if(Validate.noNull(rybh)) {
			sql += " and (l.rybh in ('"+rybh+"') or l.rybh is null) ";
		}
		if(Validate.noNull(shzt)) {
			sql += " and (l.shzt like '%"+shzt+"%' or l.rybh is null) ";
		}
		if(Validate.noNull(gzyf)) {
			sql += " and (l.gzyf like '%"+gzyf+"%' or l.gzyf is null) ";
		}
		return db.queryForList(sql);
	}
	
	public int doPlfuzhiy(String ids,String ziduan,Object zdValue,String ids2,String gzyf){
		String sql = "";
		if("nonono".equals(ids)) {
			sql = " update CW_LZXZB set "+ziduan+" ='"+zdValue+"' where 1=1 and gzyf=? and shzt=0";
			int i = db.update(sql,gzyf);
			return i;
		}else {
			sql = " update CW_LZXZB set "+ziduan+" ='"+zdValue+"' where 1=1 and guid"+CommonUtils.getInsql(ids);
			Object[] obj = ids.split(",");
			Object[] objs = new Object[obj.length+1];
			System.err.println("____________"+obj);
			int i = db.update(sql,obj);
			return i;
		}
	}

	/**
	 * 清空在职薪资表
	 * 
	 * @return
	 */
	public int doDelXz() {
		String sql = " delete from  CW_XZB ";
		return db.update(sql);
	}

	/**
	 * cw_xzb，薪资表
	 * 
	 * @param file
	 * @return
	 */
	public String insertXz(String file) {
		Workbook rwb;
		List list = new ArrayList();
		List sqlList = new ArrayList();
		String info = "";// 错误或者成功信息保存在此
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int rows = rs.getRows();// 得到所有的行
			int cols = rs.getColumns();
			// int j = 0;
			int z = 0;
			int sucessrows = rows;
			// 错误信息储存list
			LinkedList<String> wz_list = new LinkedList<String>();
			for (int i = 2; i < rows; i++) {// 第一行是列名，不需要导入数据库
				String updatepj = "";// 定义update拼接字符串
				String insertpj = "";// 定义insert拼接字符串
				String insertsz = "";// 定义insert字段内容拼接字符串
				String rybhxg = "";
				int a = 0;
				// j=0;
				Map<String, String> map = new HashMap();
				
				for (int j = 0; j < cols; j++) {
					
					switch (rs.getCell(j, 0).getContents()) {
					case "rybh":
						String rybh = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rybh)) {
						if (Validate.noNull(rybh)) {
							rybhxg = rybh;
							insertpj = insertpj + "rybh,";
							insertsz = insertsz + "'" + rybh + "',";
							map.put("RYBH", rybh);
						}
						break;
					case "xm":
						String xm = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(xm)) {
						if (Validate.noNull(xm)) {
							updatepj = updatepj + "xm='" + xm + "',";
							insertpj = insertpj + "xm,";
							insertsz = insertsz + "'" + xm + "',";
							map.put("XM", xm);
						}
						break;
					case "xl":
						String xl = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bm)) {
						if (Validate.noNull(xl)) {
							updatepj = updatepj + "xl='" + xl + "',";
							insertpj = insertpj + "xl,";
							insertsz = insertsz + "'" + xl + "',";
							map.put("XL", xl);
						}
						break;
					case "bm":
						String bm = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bm)) {
						if (Validate.noNull(bm)) {
							String dwbh=getdwbhxx(bm);
							updatepj = updatepj + "bm='" + bm + "',"+ "dwbh='" + dwbh + "',";
							insertpj = insertpj + "bm,"+ "dwbh,";
							insertsz = insertsz + "'" + bm + "',"+ "'" + dwbh + "',";
							map.put("BM", bm);
						}
						break;
					case "rylb":
						String rylb = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rylb)) {
						if (Validate.noNull(rylb)) {
							updatepj = updatepj + "rylb='" + rylb + "',";
							insertpj = insertpj + "rylb,";
							insertsz = insertsz + "'" + rylb + "',";
							map.put("RYLB", rylb);
						}
						break;
					case "rylx":
						String rylx = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rylb)) {
						if (Validate.noNull(rylx)) {
							updatepj = updatepj + "rylx='" + rylx + "',";
							insertpj = insertpj + "rylx,";
							insertsz = insertsz + "'" + rylx + "',";
							map.put("RYLX", rylx);
						}
						break;
					case "gwgz":
						String gwgz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jbgz)) {
						if (Validate.noNull(gwgz)) {
							updatepj = updatepj + "gwgz='" + gwgz + "',";
							insertpj = insertpj + "gwgz,";
							insertsz = insertsz + "'" + gwgz + "',";
							map.put("GWGZ", gwgz);
						}
						break;
					case "xjgz":
						String xjgz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xjgz)) {
						if (Validate.noNull(xjgz)) {
							updatepj = updatepj + "xjgz='" + xjgz + "',";
							insertpj = insertpj + "xjgz,";
							insertsz = insertsz + "'" + xjgz + "',";
							map.put("XJGZ", xjgz);
						}
						break;
					case "dszf":
						String dszf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(dszf)) {
						if (Validate.noNull(dszf)) {
							updatepj = updatepj + "dszf='" + dszf + "',";
							insertpj = insertpj + "dszf,";
							insertsz = insertsz + "'" + dszf + "',";
							map.put("DSZF", dszf);
						}
						break;
					case "jljx1":
						String jljx1 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jljx1)) {
						if (Validate.noNull(jljx1)) {
							updatepj = updatepj + "jljx1='" + jljx1 + "',";
							insertpj = insertpj + "jljx1,";
							insertsz = insertsz + "'" + jljx1 + "',";
							map.put("JLJX1", jljx1);
						}
						break;
					case "bsbt":
						String bsbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bsbt)) {
						if (Validate.noNull(bsbt)) {
							updatepj = updatepj + "bsbt='" + bsbt + "',";
							insertpj = insertpj + "bsbt,";
							insertsz = insertsz + "'" + bsbt + "',";
							map.put("BSBT", bsbt);
						}
						break;
					case "bxqzbbt":
						String bxqzbbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bxqzbbt)) {
						if (Validate.noNull(bxqzbbt)) {
							updatepj = updatepj + "bxqzbbt='" + bxqzbbt + "',";
							insertpj = insertpj + "bxqzbbt,";
							insertsz = insertsz + "'" + bxqzbbt + "',";
							map.put("BXQZBBT", bxqzbbt);
						}
						break;
					case "sybt":
						String sybt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sybt)) {
						if (Validate.noNull(sybt)) {
							updatepj = updatepj + "sybt='" + sybt + "',";
							insertpj = insertpj + "sybt,";
							insertsz = insertsz + "'" + sybt + "',";
							map.put("SYBT", sybt);
						}
						break;
					case "jhbt":
						String jhbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jhbt)) {
						if (Validate.noNull(jhbt)) {
							updatepj = updatepj + "jhbt='" + jhbt + "',";
							insertpj = insertpj + "jhbt,";
							insertsz = insertsz + "'" + jhbt + "',";
							map.put("JHBT", jhbt);
						}
						break;
					case "zjbt":
						String zjbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zjbt)) {
						if (Validate.noNull(zjbt)) {
							updatepj = updatepj + "zjbt='" + zjbt + "',";
							insertpj = insertpj + "zjbt,";
							insertsz = insertsz + "'" + zjbt + "',";
							map.put("ZJBT", zjbt);
						}
						break;
					case "htbt":
						String htbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(htbt)) {
						if (Validate.noNull(htbt)) {
							updatepj = updatepj + "htbt='" + htbt + "',";
							insertpj = insertpj + "htbt,";
							insertsz = insertsz + "'" + htbt + "',";
							map.put("HTBT", htbt);
						}
						break;
					case "fzgzl":
						String fzgzl = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(fzgzl)) {
						if (Validate.noNull(fzgzl)) {
							updatepj = updatepj + "fzgzl='" + fzgzl + "',";
							insertpj = insertpj + "fzgzl,";
							insertsz = insertsz + "'" + fzgzl + "',";
							map.put("FZGZL", fzgzl);
						}
						break;
					case "zsjl":
						String zsjl = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zsjl)) {
						if (Validate.noNull(zsjl)) {
							updatepj = updatepj + "zsjl='" + zsjl + "',";
							insertpj = insertpj + "zsjl,";
							insertsz = insertsz + "'" + zsjl + "',";
							map.put("ZSJL", zsjl);
						}
						break;
					case "fdyyjzbbt":
						String fdyyjzbbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(fdyyjzbbt)) {
						if (Validate.noNull(fdyyjzbbt)) {
							updatepj = updatepj + "fdyyjzbbt='" + fdyyjzbbt
									+ "',";
							insertpj = insertpj + "fdyyjzbbt,";
							insertsz = insertsz + "'" + fdyyjzbbt + "',";
							map.put("FDYYJZBBT", fdyyjzbbt);
						}
						break;
					case "khj":
						String khj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(khj)) {
						if (Validate.noNull(khj)) {
							updatepj = updatepj + "khj='" + khj + "',";
							insertpj = insertpj + "khj,";
							insertsz = insertsz + "'" + khj + "',";
							map.put("KHJ", khj);
						}
						break;
					case "jiangkef":
						String jiangkef = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jiangkef)) {
						if (Validate.noNull(jiangkef)) {
							updatepj = updatepj + "jiangkef='" + jiangkef
									+ "',";
							insertpj = insertpj + "jiangkef,";
							insertsz = insertsz + "'" + jiangkef + "',";
							map.put("JIANGKEF", jiangkef);
						}
						break;
					case "bgzks":
						String bgzks = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgzks)) {
						if (Validate.noNull(bgzks)) {
							updatepj = updatepj + "bgzks='" + bgzks + "',";
							insertpj = insertpj + "bgzks,";
							insertsz = insertsz + "'" + bgzks + "',";
							map.put("BGZKS", bgzks);
						}
						break;
					case "qnjsx":
						String qnjsx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qnjsx)) {
						if (Validate.noNull(qnjsx)) {
							updatepj = updatepj + "qnjsx='" + qnjsx + "',";
							insertpj = insertpj + "qnjsx,";
							insertsz = insertsz + "'" + qnjsx + "',";
							map.put("QNJSX", qnjsx);
						}
						break;
					case "qnjsx1":
						String qnjsx1 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qnjsx)) {
						if (Validate.noNull(qnjsx1)) {
							updatepj = updatepj + "qnjsx1='" + qnjsx1 + "',";
							insertpj = insertpj + "qnjsx1,";
							insertsz = insertsz + "'" + qnjsx1 + "',";
							map.put("QNJSX1", qnjsx1);
						}
						break;
					case "qnjsx2":
						String qnjsx2 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qnjsx)) {
						if (Validate.noNull(qnjsx2)) {
							updatepj = updatepj + "qnjsx2='" + qnjsx2 + "',";
							insertpj = insertpj + "qnjsx2,";
							insertsz = insertsz + "'" + qnjsx2 + "',";
							map.put("QNJSX2", qnjsx2);
						}
						break;
					case "qnjsx3":
						String qnjsx3 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qnjsx)) {
						if (Validate.noNull(qnjsx3)) {
							updatepj = updatepj + "qnjsx3='" + qnjsx3 + "',";
							insertpj = insertpj + "qnjsx3,";
							insertsz = insertsz + "'" + qnjsx3 + "',";
							map.put("QNJSX3", qnjsx3);
						}
						break;
					case "bjcxjxgz2014jsjs":
						String bjcxjxgz2014jsjs = rs.getCell(j, i)
								.getContents();// 人员编号
						// if(!"0".equals(bjcxjxgz2014jsjs)) {
						if (Validate.noNull(bjcxjxgz2014jsjs)) {
							updatepj = updatepj + "bjcxjxgz2014jsjs='"
									+ bjcxjxgz2014jsjs + "',";
							insertpj = insertpj + "bjcxjxgz2014jsjs,";
							insertsz = insertsz + "'" + bjcxjxgz2014jsjs + "',";
							map.put("BJCXJXGZ2014JSJS", bjcxjxgz2014jsjs);
						}
						break;
					case "bxbt2014n1d10yjsjs":
						String bxbt2014n1d10yjsjs = rs.getCell(j, i)
								.getContents();// 人员编号
						// if(!"0".equals(bjcxjxgz2014jsjs)) {
						if (Validate.noNull(bxbt2014n1d10yjsjs)) {
							updatepj = updatepj + "bxbt2014n1d10yjsjs='"
									+ bxbt2014n1d10yjsjs + "',";
							insertpj = insertpj + "bxbt2014n1d10yjsjs,";
							insertsz = insertsz + "'" + bxbt2014n1d10yjsjs
									+ "',";
							map.put("BXBT2014N1D10YJSJS", bxbt2014n1d10yjsjs);
						}
						break;
					case "jsjs":
						String jsjs = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bjcxjxgz2014jsjs)) {
						if (Validate.noNull(jsjs)) {
							updatepj = updatepj + "jsjs='" + jsjs + "',";
							insertpj = insertpj + "jsjs,";
							insertsz = insertsz + "'" + jsjs + "',";
							map.put("JSJS", jsjs);
						}
						break;
					case "pgjj":
						String pgjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bjcxjxgz2014jsjs)) {
						if (Validate.noNull(pgjj)) {
							updatepj = updatepj + "pgjj='" + pgjj + "',";
							insertpj = insertpj + "pgjj,";
							insertsz = insertsz + "'" + pgjj + "',";
							map.put("PGJJ", pgjj);
						}
						break;
					case "ylbx":
						String ylbx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bjcxjxgz2014jsjs)) {
						if (Validate.noNull(ylbx)) {
							updatepj = updatepj + "ylbx='" + ylbx + "',";
							insertpj = insertpj + "ylbx,";
							insertsz = insertsz + "'" + ylbx + "',";
							map.put("YLBX", ylbx);
						}
						break;
					case "dks":
						String dks = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(dks)) {
						if (Validate.noNull(dks)) {
							updatepj = updatepj + "dks='" + dks + "',";
							insertpj = insertpj + "dks,";
							insertsz = insertsz + "'" + dks + "',";
							map.put("DKS", dks);
						}
						break;
					case "bnse":
						String bnse = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bnse)) {
						if (Validate.noNull(bnse)) {
							updatepj = updatepj + "bnse='" + bnse + "',";
							insertpj = insertpj + "bnse,";
							insertsz = insertsz + "'" + bnse + "',";
							map.put("BNSE", bnse);
						}
						break;
					case "snse":
						String snse = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(snse)) {
						if (Validate.noNull(snse)) {
							updatepj = updatepj + "snse='" + snse + "',";
							insertpj = insertpj + "snse,";
							insertsz = insertsz + "'" + snse + "',";
							map.put("SNSE", snse);
						}
						break;
					case "sbj":
						String sbj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sbj)) {
						if (Validate.noNull(sbj)) {
							updatepj = updatepj + "sbj='" + sbj + "',";
							insertpj = insertpj + "sbj,";
							insertsz = insertsz + "'" + sbj + "',";
							map.put("SBJ", sbj);
						}
						break;
					case "sjdck":
						String sjdck = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sjdck)) {
						if (Validate.noNull(sjdck)) {
							updatepj = updatepj + "sjdck='" + sjdck + "',";
							insertpj = insertpj + "sjdck,";
							insertsz = insertsz + "'" + sjdck + "',";
							map.put("SJDCK", sjdck);
						}
						break;
					case "axyrj":
						String axyrj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(axyrj)) {
						if (Validate.noNull(axyrj)) {
							updatepj = updatepj + "axyrj='" + axyrj + "',";
							insertpj = insertpj + "axyrj,";
							insertsz = insertsz + "'" + axyrj + "',";
							map.put("AXYRJ", axyrj);
						}
						break;
					case "xh":
						String xh = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xh)) {
						if (Validate.noNull(xh)) {
							updatepj = updatepj + "xh='" + xh + "',";
							insertpj = insertpj + "xh,";
							insertsz = insertsz + "'" + xh + "',";
							map.put("XH", xh);
						}
						break;
					case "nzjdks":
						String nzjdks = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nzjdks)) {
						if (Validate.noNull(nzjdks)) {
							updatepj = updatepj + "nzjdks='" + nzjdks + "',";
							insertpj = insertpj + "nzjdks,";
							insertsz = insertsz + "'" + nzjdks + "',";
							map.put("NZJDKS", nzjdks);
						}
						break;
					case "gzdks":
						String gzdks = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gzdks)) {
						if (Validate.noNull(gzdks)) {
							updatepj = updatepj + "gzdks='" + gzdks + "',";
							insertpj = insertpj + "gzdks,";
							insertsz = insertsz + "'" + gzdks + "',";
							map.put("GZDKS", gzdks);
						}
						break;
					case "kshj":
						String kshj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kshj)) {
						if (Validate.noNull(kshj)) {
							updatepj = updatepj + "kshj='" + kshj + "',";
							insertpj = insertpj + "kshj,";
							insertsz = insertsz + "'" + kshj + "',";
							map.put("KSHJ", kshj);
						}
						break;
					case "gh":
						String gh = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gh)) {
						if (Validate.noNull(gh)) {
							updatepj = updatepj + "gh='" + gh + "',";
							insertpj = insertpj + "gh,";
							insertsz = insertsz + "'" + gh + "',";
							map.put("GH", gh);
						}
						break;
					case "sfzb":
						String sfzb = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sfzb)) {
						if (Validate.noNull(sfzb)) {
							updatepj = updatepj + "sfzb='" + sfzb + "',";
							insertpj = insertpj + "sfzb,";
							insertsz = insertsz + "'" + sfzb + "',";
							map.put("SFZB", sfzb);
						}
						break;
					case "bkylbx":
						String bkylbx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bkylbx)) {
						if (Validate.noNull(bkylbx)) {
							updatepj = updatepj + "bkylbx='" + bkylbx + "',";
							insertpj = insertpj + "bkylbx,";
							insertsz = insertsz + "'" + bkylbx + "',";
							map.put("BKYLBX", bkylbx);
						}
						break;
					case "bksyj":
						String bksyj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bksyj)) {
						if (Validate.noNull(bksyj)) {
							updatepj = updatepj + "bksyj='" + bksyj + "',";
							insertpj = insertpj + "bksyj,";
							insertsz = insertsz + "'" + bksyj + "',";
							map.put("BKSYJ", bksyj);
						}
						break;
					case "bkylj":
						String bkylj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bkylj)) {
						if (Validate.noNull(bkylj)) {
							updatepj = updatepj + "bkylj='" + bkylj + "',";
							insertpj = insertpj + "bkylj,";
							insertsz = insertsz + "'" + bkylj + "',";
							map.put("BKYLJ", bkylj);
						}
						break;
					case "bksbj":
						String bksbj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bksbj)) {
						if (Validate.noNull(bksbj)) {
							updatepj = updatepj + "bksbj='" + bksbj + "',";
							insertpj = insertpj + "bksbj,";
							insertsz = insertsz + "'" + bksbj + "',";
							map.put("BKSBJ", bksbj);
						}
						break;
					case "sfdy":
						String sfdy = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sfdy)) {
						if (Validate.noNull(sfdy)) {
							updatepj = updatepj + "sfdy='" + sfdy + "',";
							insertpj = insertpj + "sfdy,";
							insertsz = insertsz + "'" + sfdy + "',";
							map.put("SFDY", sfdy);
						}
						break;
					case "rdqk":
						String rdqk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(rdqk)) {
						if (Validate.noNull(rdqk)) {
							updatepj = updatepj + "rdqk='" + rdqk + "',";
							insertpj = insertpj + "rdqk,";
							insertsz = insertsz + "'" + rdqk + "',";
							map.put("RDQK", rdqk);
						}
						break;
					case "yzwbt":
						String yzwbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yzwbt)) {
						if (Validate.noNull(yzwbt)) {
							updatepj = updatepj + "yzwbt='" + yzwbt + "',";
							insertpj = insertpj + "yzwbt,";
							insertsz = insertsz + "'" + yzwbt + "',";
							map.put("YZWBT", yzwbt);
						}
						break;
					case "gwbt":
						String gwbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gwbt)) {
						if (Validate.noNull(gwbt)) {
							updatepj = updatepj + "gwbt='" + gwbt + "',";
							insertpj = insertpj + "gwbt,";
							insertsz = insertsz + "'" + gwbt + "',";
							map.put("GWBT", gwbt);
						}
						break;
					case "xzft":
						String xzft = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xzft)) {
						if (Validate.noNull(xzft)) {
							updatepj = updatepj + "xzft='" + xzft + "',";
							insertpj = insertpj + "xzft,";
							insertsz = insertsz + "'" + xzft + "',";
							map.put("XZFT", xzft);
						}
						break;
					case "hzbt":
						String hzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(hzbt)) {
						if (Validate.noNull(hzbt)) {
							updatepj = updatepj + "hzbt='" + hzbt + "',";
							insertpj = insertpj + "hzbt,";
							insertsz = insertsz + "'" + hzbt + "',";
							map.put("HZBT", hzbt);
						}
						break;
					case "txtgbf":
						String txtgbf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(txtgbf)) {
						if (Validate.noNull(txtgbf)) {
							updatepj = updatepj + "txtgbf='" + txtgbf + "',";
							insertpj = insertpj + "txtgbf,";
							insertsz = insertsz + "'" + txtgbf + "',";
							map.put("TXTGBF", txtgbf);
						}
						break;
					case "shbt":
						String shbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(shbt)) {
						if (Validate.noNull(shbt)) {
							updatepj = updatepj + "shbt='" + shbt + "',";
							insertpj = insertpj + "shbt,";
							insertsz = insertsz + "'" + shbt + "',";
							map.put("SHBT", shbt);
						}
						break;
					case "xzbt":
						String xzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xzbt)) {
						if (Validate.noNull(xzbt)) {
							updatepj = updatepj + "xzbt='" + xzbt + "',";
							insertpj = insertpj + "xzbt,";
							insertsz = insertsz + "'" + xzbt + "',";
							map.put("XZBT", xzbt);
						}
						break;
					case "wjbt":
						String wjbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wjbt)) {
						if (Validate.noNull(wjbt)) {
							updatepj = updatepj + "wjbt='" + wjbt + "',";
							insertpj = insertpj + "wjbt,";
							insertsz = insertsz + "'" + wjbt + "',";
							map.put("WJBT", wjbt);
						}
						break;
					case "txhl":
						String txhl = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(txhl)) {
						if (Validate.noNull(txhl)) {
							updatepj = updatepj + "txhl='" + txhl + "',";
							insertpj = insertpj + "txhl,";
							insertsz = insertsz + "'" + txhl + "',";
							map.put("TXHL", txhl);
						}
						break;
					case "qtbt":
						String qtbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qtbt)) {
						if (Validate.noNull(qtbt)) {
							updatepj = updatepj + "qtbt='" + qtbt + "',";
							insertpj = insertpj + "qtbt,";
							insertsz = insertsz + "'" + qtbt + "',";
							map.put("QTBT", qtbt);
						}
						break;
					case "ltbt":
						String ltbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(ltbt)) {
						if (Validate.noNull(ltbt)) {
							updatepj = updatepj + "ltbt='" + ltbt + "',";
							insertpj = insertpj + "ltbt,";
							insertsz = insertsz + "'" + ltbt + "',";
							map.put("LTBT", ltbt);
						}
						break;
					case "yzbt":
						String yzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yzbt)) {
						if (Validate.noNull(yzbt)) {
							updatepj = updatepj + "yzbt='" + yzbt + "',";
							insertpj = insertpj + "yzbt,";
							insertsz = insertsz + "'" + yzbt + "',";
							map.put("YZBT", yzbt);
						}
						break;
					case "jcjx":
						String jcjx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jcjx)) {
						if (Validate.noNull(jcjx)) {
							updatepj = updatepj + "jcjx='" + jcjx + "',";
							insertpj = insertpj + "jcjx,";
							insertsz = insertsz + "'" + jcjx + "',";
							map.put("JCJX", jcjx);
						}
						break;
					case "jtf":
						String jtf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jtf)) {
						if (Validate.noNull(jtf)) {
							updatepj = updatepj + "jtf='" + jtf + "',";
							insertpj = insertpj + "jtf,";
							insertsz = insertsz + "'" + jtf + "',";
							map.put("JTF", jtf);
						}
						break;
					case "bt":
						String bt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bt)) {
						if (Validate.noNull(bt)) {
							updatepj = updatepj + "bt='" + bt + "',";
							insertpj = insertpj + "bt,";
							insertsz = insertsz + "'" + bt + "',";
							map.put("BT", bt);
						}
						break;
					case "zfbt":
						String zfbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zfbt)) {
						if (Validate.noNull(zfbt)) {
							updatepj = updatepj + "zfbt='" + zfbt + "',";
							insertpj = insertpj + "zfbt,";
							insertsz = insertsz + "'" + zfbt + "',";
							map.put("ZFBT", zfbt);
						}
						break;
					case "bgz":
						String bgz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgz)) {
						if (Validate.noNull(bgz)) {
							updatepj = updatepj + "bgz='" + bgz + "',";
							insertpj = insertpj + "bgz,";
							insertsz = insertsz + "'" + bgz + "',";
							map.put("BGZ", bgz);
						}
						break;
					case "wybt":
						String wybt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wybt)) {
						if (Validate.noNull(wybt)) {
							updatepj = updatepj + "wybt='" + wybt + "',";
							insertpj = insertpj + "wybt,";
							insertsz = insertsz + "'" + wybt + "',";
							map.put("WYBT", wybt);
						}
						break;
					case "jkf":
						String jkf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jkf)) {
						if (Validate.noNull(jkf)) {
							updatepj = updatepj + "jkf='" + jkf + "',";
							insertpj = insertpj + "jkf,";
							insertsz = insertsz + "'" + jkf + "',";
							map.put("JKF", jkf);
						}
						break;
					case "gjf":
						String gjf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gjf)) {
						if (Validate.noNull(gjf)) {
							updatepj = updatepj + "gjf='" + gjf + "',";
							insertpj = insertpj + "gjf,";
							insertsz = insertsz + "'" + gjf + "',";
							map.put("GJF", gjf);
						}
						break;
					case "dhf":
						String dhf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(dhf)) {
						if (Validate.noNull(dhf)) {
							updatepj = updatepj + "dhf='" + dhf + "',";
							insertpj = insertpj + "dhf,";
							insertsz = insertsz + "'" + dhf + "',";
							map.put("DHF", dhf);
						}
						break;
					case "yfhj":
						String yfhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yfhj)) {
						if (Validate.noNull(yfhj)) {
							updatepj = updatepj + "yfhj='" + yfhj + "',";
							insertpj = insertpj + "yfhj,";
							insertsz = insertsz + "'" + yfhj + "',";
							map.put("YFHJ", yfhj);
						}
						break;
					case "fz":
						String fz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(fz)) {
						if (Validate.noNull(fz)) {
							updatepj = updatepj + "fz='" + fz + "',";
							insertpj = insertpj + "fz,";
							insertsz = insertsz + "'" + fz + "',";
							map.put("FZ", fz);
						}
						break;
					case "nqf":
						String nqf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nqf)) {
						if (Validate.noNull(nqf)) {
							updatepj = updatepj + "nqf='" + nqf + "',";
							insertpj = insertpj + "nqf,";
							insertsz = insertsz + "'" + nqf + "',";
							map.put("NQF", nqf);
						}
						break;
					case "nqf1":
						String nqf1 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nqf1)) {
						if (Validate.noNull(nqf1)) {
							updatepj = updatepj + "nqf1='" + nqf1 + "',";
							insertpj = insertpj + "nqf1,";
							insertsz = insertsz + "'" + nqf1 + "',";
							map.put("NQF1", nqf1);
						}
						break;
					case "wyf":
						String wyf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wyf)) {
						if (Validate.noNull(wyf)) {
							updatepj = updatepj + "wyf='" + wyf + "',";
							insertpj = insertpj + "wyf,";
							insertsz = insertsz + "'" + wyf + "',";
							map.put("WYF", wyf);
						}
						break;
					case "jk":
						String jk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jk)) {
						if (Validate.noNull(jk)) {
							updatepj = updatepj + "jk='" + jk + "',";
							insertpj = insertpj + "jk,";
							insertsz = insertsz + "'" + jk + "',";
							map.put("JK", jk);
						}
						break;
					case "ylj":
						String ylj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(ylj)) {
						if (Validate.noNull(ylj)) {
							updatepj = updatepj + "ylj='" + ylj + "',";
							insertpj = insertpj + "ylj,";
							insertsz = insertsz + "'" + ylj + "',";
							map.put("YLJ", ylj);
						}
						break;
					case "bgjj":
						String bgjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgjj)) {
						if (Validate.noNull(bgjj)) {
							updatepj = updatepj + "bgjj='" + bgjj + "',";
							insertpj = insertpj + "bgjj,";
							insertsz = insertsz + "'" + bgjj + "',";
							map.put("BGJJ", bgjj);
						}
						break;
					case "bs":
						String bs = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bs)) {
						if (Validate.noNull(bs)) {
							updatepj = updatepj + "bs='" + bs + "',";
							insertpj = insertpj + "bs,";
							insertsz = insertsz + "'" + bs + "',";
							map.put("BS", bs);
						}
						break;
					case "sjdcj":
						String sjdcj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sjdcj)) {
						if (Validate.noNull(sjdcj)) {
							updatepj = updatepj + "sjdcj='" + sjdcj + "',";
							insertpj = insertpj + "sjdcj,";
							insertsz = insertsz + "'" + sjdcj + "',";
							map.put("SJDCJ", sjdcj);
						}
						break;
					case "syj":
						String syj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(syj)) {
						if (Validate.noNull(syj)) {
							updatepj = updatepj + "syj='" + syj + "',";
							insertpj = insertpj + "syj,";
							insertsz = insertsz + "'" + syj + "',";
							map.put("SYJ", syj);
						}
						break;
					case "kkhj":
						String kkhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kkhj)) {
						if (Validate.noNull(kkhj)) {
							updatepj = updatepj + "kkhj='" + kkhj + "',";
							insertpj = insertpj + "kkhj,";
							insertsz = insertsz + "'" + kkhj + "',";
							map.put("KKHJ", kkhj);
						}
						break;
					case "sfhj":
						String sfhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sfhj)) {
						if (Validate.noNull(sfhj)) {
							updatepj = updatepj + "sfhj='" + sfhj + "',";
							insertpj = insertpj + "sfhj,";
							insertsz = insertsz + "'" + sfhj + "',";
							map.put("SFHJ", sfhj);
						}
						break;
					case "gzyf":
						String gzyf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gzyf)) {
						if (Validate.noNull(gzyf)) {
							String cfsql = "SELECT COUNT(*) FROM cw_xzb T WHERE T.RYBH='"
									+ rybhxg + "' AND T.GZYF='" + gzyf + "'";
							a = Integer.parseInt(db.queryForSingleValue(cfsql));
							updatepj = updatepj + "gzyf='" + gzyf + "',";
							insertpj = insertpj + "gzyf,";
							insertsz = insertsz + "'" + gzyf + "',";
							if (a > 0) {
								map.put("GZYF",
										gzyf
												+ "&nbsp&nbsp&nbsp<font color='red'>(该月薪资信息已存在)</font>");
							} else {
								map.put("GZYF", gzyf);
							}
						}
						break;
					case "bh":
						String bh = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bh)) {
						if (Validate.noNull(bh)) {
							updatepj = updatepj + "bh='" + bh + "',";
							insertpj = insertpj + "bh,";
							insertsz = insertsz + "'" + bh + "',";
							map.put("BH", bh);
						}
						break;
					case "jsx":
						String jsx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jsx)) {
						if (Validate.noNull(jsx)) {
							updatepj = updatepj + "jsx='" + jsx + "',";
							insertpj = insertpj + "jsx,";
							insertsz = insertsz + "'" + jsx + "',";
							map.put("JSX", jsx);
						}
						break;
					case "zfjj":
						String zfjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zfjj)) {
						if (Validate.noNull(zfjj)) {
							updatepj = updatepj + "zfjj='" + zfjj + "',";
							insertpj = insertpj + "zfjj,";
							insertsz = insertsz + "'" + zfjj + "',";
							map.put("ZFJJ", zfjj);
						}
						break;
					case "nzj":
						String nzj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nzj)) {
						if (Validate.noNull(nzj)) {
							updatepj = updatepj + "nzj='" + nzj + "',";
							insertpj = insertpj + "nzj,";
							insertsz = insertsz + "'" + nzj + "',";
							map.put("NZJ", nzj);
						}
						break;
					case "kk":
						String kk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kk)) {
						if (Validate.noNull(kk)) {
							updatepj = updatepj + "kk='" + kk + "',";
							insertpj = insertpj + "kk,";
							insertsz = insertsz + "'" + kk + "',";
							map.put("KK", kk);
						}
						break;
					}
				}
				System.err.println("updatepj111==>" + updatepj);
				System.err.println("insertpj111==>" + insertpj);
				System.err.println("insertsz1==>" + insertsz);
				try {
					updatepj = updatepj.substring(0, updatepj.length() - 1);
					insertpj = insertpj.substring(0, insertpj.length() - 1);
					insertsz = insertsz.substring(0, insertsz.length() - 1);

				} catch (Exception e) {
					// TODO: handle exception
				}
				
				list.add(map);
				String sql = "";
				Map<String, String> m = new HashMap<String, String>();
				String ryxx[] = updatepj.split(",");
				for(String s:ryxx){
					String[] ms = s.split("=");
					m.put(ms[0], ms[1]);
				}
				String gzyf = m.get("gzyf")+"";
				String gzyfs = "";
				if(Validate.noNull(gzyf)) {
					gzyfs= gzyf.substring(1,gzyf.length()-1);
				}
				if (a > 0) {// 重复的 执行update
					sql = "update cw_xzb set " + updatepj + " where rybh='"
							+ rybhxg + "' and gzyf='"+gzyfs+"'";
					System.err.println("updatesql=" + sql);
					sqlList.add(sql);
				} else {
					sql = "insert into cw_xzb(guid," + insertpj
							+ ") values (sys_guid()," + insertsz + ")";
					System.err.println("isnertsql=>" + sql);
					sqlList.add(sql);
				}
				/*if (a > 0) {// 重复的 执行update
					if (z == 0) {
						z++;
					} else {
						sql = "update cw_xzb set " + updatepj + " where rybh='"
								+ rybhxg + "' ";
						System.err.println("updatesql=" + sql);
						sqlList.add(sql);
						z++;
					}
				} else {
					if (z == 0) {
						z++;
					} else {
						sql = "insert into cw_xzb(guid," + insertpj
								+ ") values (sys_guid()," + insertsz + ")";
						System.err.println("isnertsql=>" + sql);
						sqlList.add(sql);
						z++;
					}
				}*/
			

			}
			try {
				db.batchUpdate(sqlList);
				if (wz_list.size() > 0) {
					info = "导入成功" + (sucessrows - 2) + "行！";
					info += "第";
					for (int i = 0, len = wz_list.size(); i < len; i++) {
						if (i == 0) {
							info += wz_list.get(i);
						} else {
							info += "、" + wz_list.get(i);
						}
						// 控制错误信息的数量，不能让他数量爆炸的全部输出
						if (i > 20) {
							break;
						}
					}
					info += "行数据导入失败！<br/>";
				} else {
					info = "导入成功" + (sucessrows - 2) + "行！";
				}
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				info = "数据库操作失败！";
				logger.error("数据库操作失败\n" + sqle);
			}

			// + a=db.batchUpdate(sqlList);
			rwb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	public static void main(String[] args) {
		String a = "rybh,xm,bm,rylx,gwgz,xjgz,gzyf,bh,";
		System.out.println(a.substring(0, a.length() - 1));
	}

	/**
	 * 保存,进cw_xzb薪资表
	 * 
	 * @param list
	 * @return
	 */
	public boolean doSave(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			StringBuffer sql = new StringBuffer();
			String guid = Validate.isNullToDefaultString(map.get("gid"), "");
			sql.append(" update cw_xzb set ");
			int m = 1;
			for(Object key : map.keySet()){
				String keyValue = Validate.isNullToDefaultString(key, "");
				if(!"gid".equals(keyValue)&&!"shzt".equals(keyValue)){
					String value = Validate.isNullToDefaultString(map.get(keyValue), "");
					String fg = ",";
					if(m==map.size()){
						fg = "";
					}
					sql.append(keyValue+"="+"'"+value+"'"+fg);
				}
				m++;
//				String guid = Validate.isNullToDefaultString(map.get("gid"), "");
//				String rybh = Validate.isNullToDefaultString(map.get("rybh"), "");// 人员编号
//				String xm = Validate.isNullToDefaultString(map.get("xm"), "");// 姓名
//				String bm = Validate.isNullToDefaultString(map.get("bm"), "");// 部门
//				String rylb = Validate.isNullToDefaultString(map.get("rylb"), "");// 人员类别
//				String rylx = Validate.isNullToDefaultString(map.get("rylx"), "");// 人员类型
//				String gwgz = Validate.isNullToDefaultString(map.get("gwgz"), "");// 岗位工资
//				String xjgz = Validate.isNullToDefaultString(map.get("xjgz"), "");// 薪级工资
//				String xzft = Validate.isNullToDefaultString(map.get("xzft"), "");// 新住房贴
//				String wybt = Validate.isNullToDefaultString(map.get("wybt"), "");// 物业补贴
//				String dszf = Validate.isNullToDefaultString(map.get("dszf"), "");// 独生子费
//				String jcjx = Validate.isNullToDefaultString(map.get("jcjx"), "");// 基础绩效
//				String jljx1 = Validate.isNullToDefaultString(map.get("jljx1"), "");// 奖励绩效1
//				String bsbt = Validate.isNullToDefaultString(map.get("bsbt"), "");// 博士补贴
//				String gwbt = Validate.isNullToDefaultString(map.get("gwbt"), "");// 岗位补贴
//				String bxqzbbt = Validate.isNullToDefaultString(map.get("bxqzbbt"),
//						"");// 北校区值班补贴
//				String sybt = Validate.isNullToDefaultString(map.get("sybt"), "");// 生育补贴
//				String jhbt = Validate.isNullToDefaultString(map.get("jhbt"), "");// 教护补贴
//				String zjbt = Validate.isNullToDefaultString(map.get("zjbt"), "");// 驻济补贴
//				String htbt = Validate.isNullToDefaultString(map.get("htbt"), "");// 合同补贴
//				String qtbt = Validate.isNullToDefaultString(map.get("qtbt"), "");// 其他补贴
//				String bgz = Validate.isNullToDefaultString(map.get("bgz"), "");// 补工资
//				String jkf = Validate.isNullToDefaultString(map.get("jkf"), "");// 监考费
//				String fzgzl = Validate.isNullToDefaultString(map.get("fzgzl"), "");// 辅助工作量
//				String zsjl = Validate.isNullToDefaultString(map.get("zsjl"), "");// 招生奖励
//				String fdyyjzbbt = Validate.isNullToDefaultString(
//						map.get("fdyyjzbbt"), "");// 辅导员夜间值班补贴
//				String khj = Validate.isNullToDefaultString(map.get("khj"), "");// 考核奖
//				String dhf = Validate.isNullToDefaultString(map.get("dhf"), "");// 电话费
//				String bt = Validate.isNullToDefaultString(map.get("bt"), "");// 补贴
//				String zfbt = Validate.isNullToDefaultString(map.get("zfbt"), "");// 租房补贴
//				String yfhj = Validate.isNullToDefaultString(map.get("yfhj"), "");// 应发合计
//				String jiangkef = Validate.isNullToDefaultString(
//						map.get("jiangkef"), "");// 讲课费
//				String bgzks = Validate.isNullToDefaultString(map.get("bgzks"), "");// 补工资扣税
//				String jsx = Validate.isNullToDefaultString(map.get("jsx"), "");// 计税项
//				String qnjsx = Validate.isNullToDefaultString(map.get("qnjsx"), "");// 全年计税项
//				String qnjsx1 = Validate.isNullToDefaultString(map.get("qnjsx1"),
//						"");// 全年计税项1
//				String qnjsx2 = Validate.isNullToDefaultString(map.get("qnjsx2"),
//						"");// 全年计税项2
//				String qnjsx3 = Validate.isNullToDefaultString(map.get("qnjsx3"),
//						"");// 全年计税项3
//				String bjcxjxgz2014jsjs = Validate.isNullToDefaultString(
//						map.get("bjcxjxgz2014jsjs"), "");// 补基础性绩效工资2014计税基数
//				String bxbt2014n1d10yjsjs = Validate.isNullToDefaultString(
//						map.get("bxbt2014n1d10yjsjs"), "");// 北校补贴2014年1到10月计税基数
//				String jsjs = Validate.isNullToDefaultString(map.get("jsjs"), "");// 计税基数
//				String zfjj = Validate.isNullToDefaultString(map.get("zfjj"), "");// 住房积金
//				String pgjj = Validate.isNullToDefaultString(map.get("pgjj"), "");// 聘公积金
//				String ylbx = Validate.isNullToDefaultString(map.get("ylbx"), "");// 医疗保险
//				String bgjj = Validate.isNullToDefaultString(map.get("bgjj"), "");// 补公积金
//				String dks = Validate.isNullToDefaultString(map.get("dks"), "");// 代扣税
//				String bnse = Validate.isNullToDefaultString(map.get("bnse"), "");// 本年税额
//				String snse = Validate.isNullToDefaultString(map.get("snse"), "");// 上年税额
//				String bs = Validate.isNullToDefaultString(map.get("bs"), "");// 补税
//				String fz = Validate.isNullToDefaultString(map.get("fz"), "");// 房租
//				String syj = Validate.isNullToDefaultString(map.get("syj"), "");// 失业金
//				String nqf = Validate.isNullToDefaultString(map.get("nqf"), "");// 暖气费
//				String nqf1 = Validate.isNullToDefaultString(map.get("nqf1"), "");// 暖气费1
//				String wyf = Validate.isNullToDefaultString(map.get("wyf"), "");// 物业费
//				String sbj = Validate.isNullToDefaultString(map.get("sbj"), "");// 社保金
//				String sjdck = Validate.isNullToDefaultString(map.get("sjdck"), "");// 四季度菜款
//				String kk = Validate.isNullToDefaultString(map.get("kk"), "");// 扣款
//				String ylj = Validate.isNullToDefaultString(map.get("ylj"), "");// 养老金
//				String jk = Validate.isNullToDefaultString(map.get("jk"), "");// 借款
//				String axyrj = Validate.isNullToDefaultString(map.get("axyrj"), "");// 爱心一日捐
//				String kkhj = Validate.isNullToDefaultString(map.get("kkhj"), "");// 扣款合计
//				String sfhj = Validate.isNullToDefaultString(map.get("sfhj"), "");// 实发合计
//				String gzyf = Validate.isNullToDefaultString(map.get("gzyf"), "");// 工资月份
//				String bh = Validate.isNullToDefaultString(map.get("bh"), "");// 编号
//				String xh = Validate.isNullToDefaultString(map.get("xh"), "");// 序号
//				String jtf = Validate.isNullToDefaultString(map.get("jtf"), "");// 交通费
//				String nzj = Validate.isNullToDefaultString(map.get("nzj"), "");// 年终奖
//				String nzjdks = Validate.isNullToDefaultString(map.get("nzjdks"),
//						"");// 年终奖代扣税
//				String gzdks = Validate.isNullToDefaultString(map.get("gzdks"), "");// 工资代扣税
//				String kshj = Validate.isNullToDefaultString(map.get("kshj"), "");// 扣税合计
//				String gh = Validate.isNullToDefaultString(map.get("gh"), "");// 工号
//				String sfzb = Validate.isNullToDefaultString(map.get("sfzb"), "");// 是否在编
//				String bkylbx = Validate.isNullToDefaultString(map.get("bkylbx"),
//						"");// 补扣医疗保险
//				String bksyj = Validate.isNullToDefaultString(map.get("bksyj"), "");
//				;// 补扣失业金
//				String bkylj = Validate.isNullToDefaultString(map.get("bkylj"), "");// 补扣养老金
//				String bksbj = Validate.isNullToDefaultString(map.get("bksbj"), "");// 补扣社保金
//				String sfdy = Validate.isNullToDefaultString(map.get("sfdy"), "");// 是否党员
//				String rdqk = Validate.isNullToDefaultString(map.get("rdqk"), "");// 入党情况
			}
			sql.append(" where guid='"+guid+"'");
			sqlList.add(sql.toString());
			// String sql =
			// "insert into cw_xzzb(rybh,xm,bm,rylb,rylx,gwgz,xjgz,xzft,wybt,dszf,"
			// +
			// "jcjx,jljx1,bsbt,gwbt,bxqzbbt,sybt,jhbt,zjbt,htbt,qtbt," +
			// "bgz,jkf,fzgzl,zsjl,fdyyjzbbt,khj,dhf,bt,zfbt,yfhj," +
			// "jiangkef,bgzks,jsx,qnjsx,qnjsx1,qnjsx2,qnjsx3,bjcxjxgz2014jsjs,bxbt2014n1d10yjsjs,jsjs,"
			// +
			// "zfjj,pgjj,ylbx,bgjj,dks,bnse,snse,bs,fz,syj," +
			// "nqf,nqf1,wyf,sbj,sjdck,kk,ylj,jk,axyrj,kkhj," +
			// "sfhj,gzyf,bh,guid," +
			// "xh,jtf,nzj,nzjdks,gzdks,kshj,gh,sfzb,bkylbx,bksyj," +
			// "bkylj,bksbj,sfdy,rdqk) VALUES ( " +
			// "'"+rybh+"', '"+xm+"', '"+bm+"', '"+rylb+"', '"+rylx+"','"+gwgz+"', '"+xjgz+"', '"+xzft+"', '"+wybt+"', '"+dszf+"',"
			// +
			// "'"+jcjx+"', '"+jljx1+"', '"+bsbt+"', '"+gwbt+"', '"+bxqzbbt+"','"+sybt+"', '"+jhbt+"', '"+zjbt+"', '"+htbt+"', '"+qtbt+"',"
			// +
			// "'"+bgz+"', '"+jkf+"', '"+fzgzl+"', '"+zsjl+"', '"+fdyyjzbbt+"','"+khj+"', '"+dhf+"', '"+bt+"', '"+zfbt+"', '"+yfhj+"',"
			// +
			// "'"+jiangkef+"', '"+bgzks+"', '"+jsx+"', '"+qnjsx+"', '"+qnjsx1+"','"+qnjsx2+"', '"+qnjsx3+"', '"+bjcxjxgz2014jsjs+"', '"+bxbt2014n1d10yjsjs+"', '"+jsjs+"',"
			// +
			// "'"+zfjj+"', '"+pgjj+"', '"+ylbx+"', '"+bgjj+"', '"+dks+"','"+bnse+"', '"+snse+"', '"+bs+"', '"+fz+"', '"+syj+"',"
			// +
			// "'"+nqf+"', '"+nqf1+"', '"+wyf+"', '"+sbj+"', '"+sjdck+"','"+kk+"', '"+ylj+"', '"+jk+"', '"+axyrj+"', '"+kkhj+"',"
			// +
			// "'"+sfhj+"', '"+gzyf+"', '"+bh+"', '"+guid+"'," +
			// "'"+xh+"', '"+jtf+"','"+nzj+"','"+nzjdks+"','"+gzdks+"','"+kshj+"','"+gh+"','"+sfzb+"','"+bkylbx+"','"+bksyj+"',"
			// +
			// "'"+bkylj+"','"+bksbj+"','"+sfdy+"','"+rdqk+"'   )";
//			StringBuffer sql = new StringBuffer();
//			sql.append(" update cw_xzb set ");
//			sql.append(" rybh='" + rybh + "',xm='" + xm + "',bm='" + bm
//					+ "',rylb='" + rylb + "',rylx='" + rylx + "', ");
//			sql.append(" gwgz='" + gwgz + "',xjgz='" + xjgz + "',xzft='" + xzft
//					+ "',wybt='" + wybt + "',dszf='" + dszf + "', ");
//			sql.append(" jcjx='" + jcjx + "',jljx1='" + jljx1 + "',bsbt='"
//					+ bsbt + "',gwbt='" + gwbt + "',bxqzbbt='" + bxqzbbt
//					+ "', ");
//			sql.append(" sybt='" + sybt + "',jhbt='" + jhbt + "',zjbt='" + zjbt
//					+ "',htbt='" + htbt + "',qtbt='" + qtbt + "', ");
//			sql.append(" bgz='" + bgz + "',jkf='" + jkf + "',fzgzl='" + fzgzl
//					+ "',zsjl='" + zsjl + "',fdyyjzbbt='" + fdyyjzbbt + "', ");
//			sql.append(" khj='" + khj + "',dhf='" + dhf + "',bt='" + bt
//					+ "',zfbt='" + zfbt + "',yfhj='" + yfhj + "', ");
//			sql.append(" jiangkef='" + jiangkef + "',bgzks='" + bgzks
//					+ "',jsx='" + jsx + "',qnjsx='" + qnjsx + "',qnjsx1='"
//					+ qnjsx1 + "', ");
//			sql.append(" qnjsx2='" + qnjsx2 + "',qnjsx3='" + qnjsx3
//					+ "',bjcxjxgz2014jsjs='" + bjcxjxgz2014jsjs
//					+ "',bxbt2014n1d10yjsjs='" + bxbt2014n1d10yjsjs
//					+ "',jsjs='" + jsjs + "', ");
//			sql.append(" zfjj='" + zfjj + "',pgjj='" + pgjj + "',ylbx='" + ylbx
//					+ "',bgjj='" + bgjj + "',dks='" + dks + "', ");
//			sql.append(" bnse='" + bnse + "',snse='" + snse + "',bs='" + bs
//					+ "',fz='" + fz + "',syj='" + syj + "', ");
//			sql.append(" nqf='" + nqf + "',nqf1='" + nqf1 + "',wyf='" + wyf
//					+ "',sbj='" + sbj + "',sjdck='" + sjdck + "', ");
//			sql.append(" kk='" + kk + "',ylj='" + ylj + "',jk='" + jk
//					+ "',axyrj='" + axyrj + "',kkhj='" + kkhj + "', ");
//			sql.append(" sfhj='" + sfhj + "',gzyf='" + gzyf + "',bh='" + bh
//					+ "',xh='" + xh + "',jtf='" + jtf + "', ");
//			sql.append(" nzj='" + nzj + "',nzjdks='" + nzjdks + "',gzdks='"
//					+ gzdks + "',kshj='" + kshj + "',gh='" + gh + "', ");
//			sql.append(" sfzb='" + sfzb + "',bkylbx='" + bkylbx + "',bksyj='"
//					+ bksyj + "',bkylj='" + bkylj + "',bksbj='" + bksbj + "', ");
//			sql.append(" sfdy='" + sfdy + "',rdqk='" + rdqk + "' ");
//			sql.append(" where guid='" + guid + "' ");
//			System.err.println(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		return bool;
	}

	/**
	 * 离职薪资查询
	 * 
	 * @param pd
	 * @return
	 */
	public List<Map<String, Object>> getLzxzList(PageData pd) {
		String rybh = pd.getString("rybh");
		StringBuffer sql = new StringBuffer();
		sql.append(" select * from CW_LZXZB ");
		if (Validate.noNull(rybh)) {
			sql.append(" where rybh like '%" + rybh + "%' ");
		}
		return db.queryForList(sql.toString());
	}

	public String insertLzxz(String file) {
		Workbook rwb;
		List list = new ArrayList();
		List sqlList = new ArrayList();
		String info = "";// 错误或者成功信息保存在此
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int rows = rs.getRows();// 得到所有的行
			int cols = rs.getColumns();
			// int j = 0;
			int sucessrows = rows;
			// 错误信息储存list
			LinkedList<String> wz_list = new LinkedList<String>();
			for (int i = 1; i < rows; i++) {// 第一行是列名，不需要导入数据库
				String updatepj = "";// 定义update拼接字符串
				String insertpj = "";// 定义insert拼接字符串
				String insertsz = "";// 定义insert字段内容拼接字符串
				String rybhxg = "";
				// j=0;
				Map<String, String> map = new HashMap();
				int a = 0;
				for (int j = 0; j < cols; j++) {
					switch (rs.getCell(j, 0).getContents()) {
					case "人员编号":
						String rybh = rs.getCell(j, i).getContents();// 人员编号
						if(Validate.noNull(rybh)) {
						rybhxg = rybh;
						insertpj = insertpj + "rybh,";
						insertsz = insertsz + "'" + rybh + "',";
						map.put("rybh", rybh);
						 }
						break;
					case "姓名":
						String xm = rs.getCell(j, i).getContents();// 人员编号
					    if(Validate.noNull(xm)) {
						updatepj = updatepj + "xm='" + xm + "',";
						insertpj = insertpj + "xm,";
						insertsz = insertsz + "'" + xm + "',";
						map.put("xm", xm);
						 }
						break;
					case "部门":
						String bm = rs.getCell(j, i).getContents();// 人员编号
						String dwbh=getdwbhxx(bm);
						if(Validate.noNull(bm)) {
						updatepj = updatepj + "bm='" + bm + "',"+ "dwbh='" + dwbh + "',";
						insertpj = insertpj + "bm,"+ "dwbh,";
						insertsz = insertsz + "'" + bm + "',"+ "'" + dwbh + "',";
						map.put("bm", bm);
						 }
						break;
					case "人员类别":
						String rylb = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(rylb)) {
						updatepj = updatepj + "rylb='" + rylb + "',";
						insertpj = insertpj + "rylb,";
						insertsz = insertsz + "'" + rylb + "',";
						map.put("rylb", rylb);
						 }
						break;
					case "基本工资":
						String jbgz = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jbgz)) {
						updatepj = updatepj + "jbgz='" + jbgz + "',";
						insertpj = insertpj + "jbgz,";
						insertsz = insertsz + "'" + jbgz + "',";
						map.put("jbgz", jbgz);
						 }
						break;
					case "增加离退费":
						String zjltf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(zjltf)) {
						updatepj = updatepj + "zjltf='" + zjltf + "',";
						insertpj = insertpj + "zjltf,";
						insertsz = insertsz + "'" + zjltf + "',";
						map.put("zjltf", zjltf);
						 }
						break;
					case "原职务补贴":
						String yzwbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(yzwbt)) {
						updatepj = updatepj + "yzwbt='" + yzwbt + "',";
						insertpj = insertpj + "yzwbt,";
						insertsz = insertsz + "'" + yzwbt + "',";
						map.put("yzwbt", yzwbt);
						 }
						break;
					case "岗位补贴":
						String gwbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(gwbt)) {
						updatepj = updatepj + "gwbt='" + gwbt + "',";
						insertpj = insertpj + "gwbt,";
						insertsz = insertsz + "'" + gwbt + "',";
						map.put("gwbt", gwbt);
						 }
						break;
					case "新住房贴":
						String xzft = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(xzft)) {
						updatepj = updatepj + "xzft='" + xzft + "',";
						insertpj = insertpj + "xzft,";
						insertsz = insertsz + "'" + xzft + "',";
						map.put("xzft", xzft);
						 }
						break;
					case "回族补贴":
						String hzbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(hzbt)) {
						updatepj = updatepj + "hzbt='" + hzbt + "',";
						insertpj = insertpj + "hzbt,";
						insertsz = insertsz + "'" + hzbt + "',";
						map.put("hzbt", hzbt);
						 }
						break;
					case "退休提高部分":
						String txtgbf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(txtgbf)) {
						updatepj = updatepj + "txtgbf='" + txtgbf + "',";
						insertpj = insertpj + "txtgbf,";
						insertsz = insertsz + "'" + txtgbf + "',";
						map.put("txtgbf", txtgbf);
						 }
						break;
					case "生活补贴":
						String shbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(shbt)) {
						updatepj = updatepj + "shbt='" + shbt + "',";
						insertpj = insertpj + "shbt,";
						insertsz = insertsz + "'" + shbt + "',";
						map.put("shbt", shbt);
						 }
						break;
					case "新增补贴":
						String xzbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(xzbt)) {
						updatepj = updatepj + "xzbt='" + xzbt + "',";
						insertpj = insertpj + "xzbt,";
						insertsz = insertsz + "'" + xzbt + "',";
						map.put("xzbt", xzbt);
						 }
						break;
					case "物价补贴":
						String wjbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(wjbt)) {
						updatepj = updatepj + "wjbt='" + wjbt + "',";
						insertpj = insertpj + "wjbt,";
						insertsz = insertsz + "'" + wjbt + "',";
						map.put("wjbt", wjbt);
						 }
						break;
					case "特需护理":
						String txhl = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(txhl)) {
						updatepj = updatepj + "txhl='" + txhl + "',";
						insertpj = insertpj + "txhl,";
						insertsz = insertsz + "'" + txhl + "',";
						map.put("txhl", txhl);
						 }
						break;
					case "救护补贴":
						String jhbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jhbt)) {
						updatepj = updatepj + "jhbt='" + jhbt + "',";
						insertpj = insertpj + "jhbt,";
						insertsz = insertsz + "'" + jhbt + "',";
						map.put("jhbt", jhbt);
						 }
						break;
					case "其他补贴":
						String qtbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(qtbt)) {
						updatepj = updatepj + "qtbt='" + qtbt + "',";
						insertpj = insertpj + "qtbt,";
						insertsz = insertsz + "'" + qtbt + "',";
						map.put("qtbt", qtbt);
						 }
						break;
					case "离退补贴":
						String ltbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(ltbt)) {
						updatepj = updatepj + "ltbt='" + ltbt + "',";
						insertpj = insertpj + "ltbt,";
						insertsz = insertsz + "'" + ltbt + "',";
						map.put("ltbt", ltbt);
						 }
						break;
					case "月增补贴":
						String yzbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(yzbt)) {
						updatepj = updatepj + "yzbt='" + yzbt + "',";
						insertpj = insertpj + "yzbt,";
						insertsz = insertsz + "'" + yzbt + "',";
						map.put("yzbt", yzbt);
						 }
						break;
					case "基础绩效":
						String jcjx = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jcjx)) {
						updatepj = updatepj + "jcjx='" + jcjx + "',";
						insertpj = insertpj + "jcjx,";
						insertsz = insertsz + "'" + jcjx + "',";
						map.put("jcjx", jcjx);
						 }
						break;
					case "交通费":
						String jtf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jtf)) {
						updatepj = updatepj + "jtf='" + jtf + "',";
						insertpj = insertpj + "jtf,";
						insertsz = insertsz + "'" + jtf + "',";
						map.put("jtf", jtf);
						 }
						break;
					case "补贴":
						String bt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(bt)) {
						updatepj = updatepj + "bt='" + bt + "',";
						insertpj = insertpj + "bt,";
						insertsz = insertsz + "'" + bt + "',";
						map.put("bt", bt);
						 }
						break;
					case "租房补贴":
						String zfbt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(zfbt)) {
						updatepj = updatepj + "zfbt='" + zfbt + "',";
						insertpj = insertpj + "zfbt,";
						insertsz = insertsz + "'" + zfbt + "',";
						map.put("zfbt", zfbt);
						 }
						break;
					case "补工资":
						String bgz = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(bgz)) {
						updatepj = updatepj + "bgz='" + bgz + "',";
						insertpj = insertpj + "bgz,";
						insertsz = insertsz + "'" + bgz + "',";
						map.put("bgz", bgz);
						 }
						break;
					case "物业补贴":
						String wybt = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(wybt)) {
						updatepj = updatepj + "wybt='" + wybt + "',";
						insertpj = insertpj + "wybt,";
						insertsz = insertsz + "'" + wybt + "',";
						map.put("wybt", wybt);
						 }
						break;
					case "监考费":
						String jkf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jkf)) {
						updatepj = updatepj + "jkf='" + jkf + "',";
						insertpj = insertpj + "jkf,";
						insertsz = insertsz + "'" + jkf + "',";
						map.put("jkf", jkf);
						 }
						break;
					case "过节费":
						String gjf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(gjf)) {
						updatepj = updatepj + "gjf='" + gjf + "',";
						insertpj = insertpj + "gjf,";
						insertsz = insertsz + "'" + gjf + "',";
						map.put("gjf", gjf);
						 }
						break;
					case "电话费":
						String dhf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(dhf)) {
						updatepj = updatepj + "dhf='" + dhf + "',";
						insertpj = insertpj + "dhf,";
						insertsz = insertsz + "'" + dhf + "',";
						map.put("dhf", dhf);
						}
						break;
					case "应发合计":
						String yfhj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(yfhj)) {
						updatepj = updatepj + "yfhj='" + yfhj + "',";
						insertpj = insertpj + "yfhj,";
						insertsz = insertsz + "'" + yfhj + "',";
						map.put("yfhj", yfhj);
						 }
						break;
					case "房租":
						String fz = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(fz)) {
						updatepj = updatepj + "fz='" + fz + "',";
						insertpj = insertpj + "fz,";
						insertsz = insertsz + "'" + fz + "',";
						map.put("fz", fz);
						 }
						break;
					case "暖气费":
						String nqf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(nqf)) {
						updatepj = updatepj + "nqf='" + nqf + "',";
						insertpj = insertpj + "nqf,";
						insertsz = insertsz + "'" + nqf + "',";
						map.put("nqf", nqf);
						 }
						break;
					case "暖气费1":
						String nqf1 = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(nqf1)) {
						updatepj = updatepj + "nqf1='" + nqf1 + "',";
						insertpj = insertpj + "nqf1,";
						insertsz = insertsz + "'" + nqf1 + "',";
						map.put("nqf1", nqf1);
						 }
						break;
					case "物业费":
						String wyf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(wyf)) {
						updatepj = updatepj + "wyf='" + wyf + "',";
						insertpj = insertpj + "wyf,";
						insertsz = insertsz + "'" + wyf + "',";
						map.put("wyf", wyf);
						 }
						break;
					case "借款":
						String jk = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jk)) {
						updatepj = updatepj + "jk='" + jk + "',";
						insertpj = insertpj + "jk,";
						insertsz = insertsz + "'" + jk + "',";
						map.put("jk", jk);
						 }
						break;
					case "养老金":
						String ylj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(ylj)) {
						updatepj = updatepj + "ylj='" + ylj + "',";
						insertpj = insertpj + "ylj,";
						insertsz = insertsz + "'" + ylj + "',";
						map.put("ylj", ylj);
						 }
						break;
					case "补公积金":
						String bgjj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(bgjj)) {
						updatepj = updatepj + "bgjj='" + bgjj + "',";
						insertpj = insertpj + "bgjj,";
						insertsz = insertsz + "'" + bgjj + "',";
						map.put("bgjj", bgjj);
						 }
						break;
					case "补税":
						String bs = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(bs)) {
						updatepj = updatepj + "bs='" + bs + "',";
						insertpj = insertpj + "bs,";
						insertsz = insertsz + "'" + bs + "',";
						map.put("bs", bs);
						 }
						break;
					case "四季度菜金":
						String sjdcj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(sjdcj)) {
						updatepj = updatepj + "sjdcj='" + sjdcj + "',";
						insertpj = insertpj + "sjdcj,";
						insertsz = insertsz + "'" + sjdcj + "',";
						map.put("sjdcj", sjdcj);
						 }
						break;
					case "失业金":
						String syj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(syj)) {
						updatepj = updatepj + "syj='" + syj + "',";
						insertpj = insertpj + "syj,";
						insertsz = insertsz + "'" + syj + "',";
						map.put("syj", syj);
						 }
						break;
					case "扣款合计":
						String kkhj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(kkhj)) {
						updatepj = updatepj + "kkhj='" + kkhj + "',";
						insertpj = insertpj + "kkhj,";
						insertsz = insertsz + "'" + kkhj + "',";
						map.put("kkhj", kkhj);
						 }
						break;
					case "实发合计":
						String sfhj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(sfhj)) {
						updatepj = updatepj + "sfhj='" + sfhj + "',";
						insertpj = insertpj + "sfhj,";
						insertsz = insertsz + "'" + sfhj + "',";
						map.put("sfhj", sfhj);
						 }
						break;
					case "工资月份":
						String gzyf = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(gzyf)) {
						String cfsql = "SELECT COUNT(*) FROM CW_LZXZB T WHERE T.RYBH='"
								+ rybhxg + "' AND T.GZYF='" + gzyf + "'";
						a = Integer.parseInt(db.queryForSingleValue(cfsql));
						System.err.println("cfsql==" + cfsql);
						System.err.println("a==" + a);
						updatepj = updatepj + "gzyf='" + gzyf + "',";
						insertpj = insertpj + "gzyf,";
						insertsz = insertsz + "'" + gzyf + "',";
						map.put("gzyf", gzyf);
						 }
						break;
					case "编号":
						String bh = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(bh)) {
						updatepj = updatepj + "bh='" + bh + "',";
						insertpj = insertpj + "bh,";
						insertsz = insertsz + "'" + bh + "',";
						map.put("bh", bh);
						 }
						break;
					case "计税项":
						String jsx = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(jsx)) {
						updatepj = updatepj + "jsx='" + jsx + "',";
						insertpj = insertpj + "jsx,";
						insertsz = insertsz + "'" + jsx + "',";
						map.put("jsx", jsx);
						 }
						break;
					case "住房积金":
						String zfjj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(zfjj)) {
						updatepj = updatepj + "zfjj='" + zfjj + "',";
						insertpj = insertpj + "zfjj,";
						insertsz = insertsz + "'" + zfjj + "',";
						map.put("zfjj", zfjj);
						 }
						break;
					case "年终奖":
						String nzj = rs.getCell(j, i).getContents();// 人员编号
						 if(Validate.noNull(nzj)) {
						updatepj = updatepj + "nzj='" + nzj + "',";
						insertpj = insertpj + "nzj,";
						insertsz = insertsz + "'" + nzj + "',";
						map.put("nzj", nzj);
					    }
						break;
					case "扣款":
						String kk = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(kk)) {
						 if(Validate.noNull(kk)) {
						updatepj = updatepj + "kk='" + kk + "',";
						insertpj = insertpj + "kk,";
						insertsz = insertsz + "'" + kk + "',";
						map.put("kk", kk);
						 }
						break;
					}
				}
				list.add(map);
				String sql = "";
				System.out.println("map======" + map);
				updatepj = updatepj.substring(0, updatepj.length() - 1);
				insertpj = insertpj.substring(0, insertpj.length() - 1);
				insertsz = insertsz.substring(0, insertsz.length() - 1);
				System.err.println("updatepj==>" + updatepj);
				System.err.println("insertpj==>" + insertpj);
				System.err.println("insertsz==>" + insertsz);
				System.err.println("a==> " + a);
				Map<String, String> m = new HashMap<String, String>();
				String ryxx[] = updatepj.split(",");
				for(String s:ryxx){
					String[] ms = s.split("=");
					m.put(ms[0], ms[1]);
				}
				String gzyf = m.get("gzyf")+"";
				String gzyfs = "";
				if(Validate.noNull(gzyf)) {
					gzyfs= gzyf.substring(1,gzyf.length()-1);
				}
				if (a > 0) {// 重复的 执行update
					sql = "update cw_lzxzb set " + updatepj + " where rybh='"
							+ rybhxg + "' and gzyf='"+gzyfs+"'";
					System.err.println("updatesql=" + sql);
					sqlList.add(sql);
				} else {
					sql = "insert into cw_lzxzb(guid," + insertpj
							+ ") values (sys_guid()," + insertsz + ")";
					System.err.println("isnertsql=>" + sql);
					sqlList.add(sql);
				}
			}
			try {
				db.batchUpdate(sqlList);
				if (wz_list.size() > 0) {
					info = "导入成功" + (sucessrows - 1) + "行！";
					info += "第";
					for (int i = 0, len = wz_list.size(); i < len; i++) {
						if (i == 0) {
							info += wz_list.get(i);
						} else {
							info += "、" + wz_list.get(i);
						}
						// 控制错误信息的数量，不能让他数量爆炸的全部输出
						if (i > 20) {
							break;
						}
					}
					info += "行数据导入失败！<br/>";
				} else {
					info = "导入成功" + (sucessrows - 1) + "行！";
				}
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				info = "数据库操作失败！";
				logger.error("数据库操作失败\n" + sqle);
			}
			// a=db.batchUpdate(sqlList);
			rwb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return info;
	}

	/**
	 * 保存,进cw_lzxzb离职薪资表
	 * 
	 * @param list
	 * @return
	 */
	public boolean doSaveLz(List list) {
		boolean bool = false;
		ArrayList<String> sqlList = new ArrayList<String>();
		for (int i = 0; i < list.size(); i++) {
			Map map = (Map) list.get(i);
			String guid = Validate.isNullToDefaultString(map.get("gid"),UuidUtil.get32UUID());
			StringBuffer sql = new StringBuffer();
			sql.append(" update CW_LZXZB set ");
			int m = 1;
			for(Object key : map.keySet()){
				String keyValue = Validate.isNullToDefaultString(key, "");
				if(!"gid".equals(keyValue)&&!"shzt".equals(keyValue)){
					String value = Validate.isNullToDefaultString(map.get(keyValue), "");
					String fg = ",";
					if(m==map.size()){
						fg = "";
					}
					sql.append(keyValue+"="+"'"+value+"'"+fg);
				}
				m++;
			}
			sql.append(" WHERE GUID = '" + guid + "' ");
			sqlList.add(sql.toString());
		}
		bool = db.batchUpdate(sqlList);
		List<String> hsList = new ArrayList<>();
		StringBuffer sqlYfhj = new StringBuffer(); 
		sqlYfhj.append(" UPDATE CW_LZXZB SET YFHJ = (NVL(JBGZ, 0) + NVL(ZJLTF, 0) + NVL(YZWBT, 0) + NVL(GWBT, 0) + NVL(XZFT, 0)+ ");
		sqlYfhj.append(" NVL(HZBT, 0) + NVL(TXTGBF, 0) + NVL(SHBT, 0) + NVL(XZBT, 0) + NVL(WJBT, 0)+ "); 
		sqlYfhj.append(" NVL(TXHL, 0) +NVL(JHBT, 0) +NVL(QTBT, 0) +NVL(LTBT, 0) +NVL(YZBT, 0) +NVL(JCJX, 0) +NVL(JTF, 0)+ "); 
		sqlYfhj.append(" NVL(BT, 0) +NVL(ZFBT, 0) +NVL(BGZ, 0) +NVL(WYBT, 0) +NVL(JKF, 0) +NVL(GJF, 0) +NVL(DHF, 0) )");
		hsList.add(sqlYfhj.toString());
		StringBuffer sqlKkhj = new  StringBuffer();
		sqlKkhj.append(" UPDATE CW_LZXZB SET KKHJ = (NVL(FZ, 0) + NVL(SBJ, 0) + NVL(NQF, 0) + NVL(WYF, 0)+ NVL(NQF1, 0) + NVL(JK, 0) + NVL(YLJ, 0) + ");
		sqlKkhj.append(" NVL(BGJJ, 0) + NVL(BS, 0) + NVL(SJDCK, 0) + NVL(SYJ, 0) + NVL(ZFJJ, 0) + NVL(KK, 0) )");
		hsList.add(sqlKkhj.toString());
		StringBuffer sqlSfhj = new StringBuffer();
		sqlSfhj.append(" UPDATE CW_LZXZB SET sfhj = nvl(yfhj,0)-nvl(kkhj,0)");
		hsList.add(sqlSfhj.toString());
		db.batchUpdate(hsList);
		return bool;
	}

	/**
	 * 清空离职薪资表
	 * 
	 * @return
	 */
	public int doDelLzxz() {
		String sql = " delete from  CW_LZXZB ";
		return db.update(sql);
	}

	/**
	 * 在职薪资核算
	 * @return
	 */
	public int doHs(String gzyf,String rybh,String bm) {
		List sqlList = new ArrayList<>();
		StringBuffer sqlYf = new StringBuffer();
		sqlYf.append("update cw_xzb set yfhj = (NVL(GWGZ, 0) + NVL(XJGZ, 0) + NVL(XZFT, 0) + NVL(WYBT, 0) +");
		sqlYf.append("NVL(DSZF, 0) + NVL(JCJX, 0) + NVL(JLJX1, 0) + NVL(BSBT, 0) + NVL(GWBT, 0) + NVL(BXQZBBT, 0) + NVL(SYBT, 0) + NVL(JHBT, 0) +");
		sqlYf.append("NVL(ZJBT, 0) + NVL(HTBT, 0) + NVL(QTBT, 0) + NVL(BGZ, 0) +NVL(JKF, 0) + NVL(FZGZL, 0) + NVL(ZSJL, 0) + NVL(FDYYJZBBT, 0) +");
		sqlYf.append(" NVL(KHJ, 0) + NVL(DHF + BT, 0) + NVL(ZFBT, 0) + NVL(JTF, 0)) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlYf.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlYf.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlYf.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlYf.toString());
		StringBuffer sqlJs = new StringBuffer();
		sqlJs.append("update cw_xzb set jsjs = ((NVL(YFHJ, 0) + NVL(JIANGKEF, 0) + NVL(QNJSX, 0) + NVL(QNJSX1, 0) +");
		sqlJs.append("NVL(QNJSX2, 0) + NVL(QNJSX3, 0) + NVL(JSX, 0) - NVL(DSZF, 0) -NVL(ZFJJ, 0) - NVL(PGJJ, 0) - NVL(YLBX, 0) - NVL(BGJJ, 0) -");
		sqlJs.append(" NVL(SYJ, 0) - NVL(SBJ, 0) - NVL(YLJ, 0)) - NVL(SYBT, 0) - NVL(JHBT, 0) - NVL(BKYLBX, 0) - NVL(BKSYJ, 0) - NVL(BKYLJ, 0) -");
		sqlJs.append("  NVL(BKSBJ, 0)) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlJs.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlJs.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlJs.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlJs.toString());
		StringBuffer sqlgZdks = new StringBuffer();
		sqlgZdks.append("update cw_xzb  set gzdks = (case when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b), 0))>0 then ");
		sqlgZdks.append(" (case when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0) ) <=  nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='1'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='1')/100,0)) ");
		sqlgZdks.append(" when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) <= nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='2'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='2')/100,0)) -  nvl((select b.sskcs  from CW_GRSDSCELJSLB b where b.qyynsjc='2'),0) ");
		sqlgZdks.append(" when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) <= nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='3'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='3')/100,0)) -  nvl((select b.sskcs  from CW_GRSDSCELJSLB b where b.qyynsjc='3'),0) ");
		sqlgZdks.append(" when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) <= nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='4'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='4')/100,0)) -  nvl((select b.sskcs  from CW_GRSDSCELJSLB b where b.qyynsjc='4'),0) ");
		sqlgZdks.append(" when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) <= nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='5'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='5')/100,0)) -  nvl((select b.sskcs  from CW_GRSDSCELJSLB b where b.qyynsjc='5'),0) ");
		sqlgZdks.append(" when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) <= nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='6'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='6')/100,0)) -  nvl((select b.sskcs  from CW_GRSDSCELJSLB b where b.qyynsjc='6'),0) ");
		sqlgZdks.append(" when (nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) > nvl((select b.qyynsbzh  from CW_GRSDSCELJSLB b where b.qyynsjc='6'),0) then ");
		sqlgZdks.append(" ((nvl(jsjs, 0) - nvl((select b.nsjs from cw_gzcsb b),0)) * nvl((select b.sl from CW_GRSDSCELJSLB b where b.qyynsjc='7')/100,0)) -  nvl((select b.sskcs  from CW_GRSDSCELJSLB b where b.qyynsjc='7'),0) ");
		sqlgZdks.append(" else  null  end)  else null end) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlgZdks.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlgZdks.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlgZdks.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlgZdks.toString());
		StringBuffer sqlGnzjdks = new StringBuffer();
		sqlGnzjdks.append(" update cw_xzb set nzjdks = (nvl(nzj, 0) * 0.03) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlGnzjdks.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlGnzjdks.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlGnzjdks.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlGnzjdks.toString());
		StringBuffer sqlKshj = new StringBuffer();
		sqlKshj.append("update cw_xzb set kshj =(nvl(nzjdks, 0) + nvl(gzdks, 0)), DKS =(nvl(nzjdks, 0) + nvl(gzdks, 0)) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlKshj.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlKshj.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlKshj.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlKshj.toString());
		StringBuffer sqlkkhj = new StringBuffer();
		sqlkkhj.append("update cw_xzb set kkhj  = (NVL(ZFJJ, 0) + NVL(PGJJ, 0) + NVL(YLBX, 0) + NVL(BGJJ, 0) + NVL(DKS, 0) + NVL(BS, 0)");
		sqlkkhj.append("+ NVL(FZ, 0) + NVL(SYJ, 0) + NVL(NQF, 0) + NVL(NQF1, 0) + NVL(WYF, 0) + NVL(SBJ, 0)  + NVL(SJDCK, 0) + NVL(KK, 0) + NVL(YLJ, 0) + NVL(JK, 0) + NVL(BKYLBX, 0) + NVL(BKSYJ, 0) ");
		sqlkkhj.append("  + NVL(BKYLJ, 0) + NVL(BKSBJ, 0)) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlkkhj.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlkkhj.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlkkhj.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlkkhj.toString());
		StringBuffer sqlSfhj = new StringBuffer();
		sqlSfhj.append("update cw_xzb  set sfhj  = (nvl(YFHJ, 0) - nvl(KKHJ, 0)) where 1=1 ");
		if(Validate.noNull(gzyf)){
			sqlSfhj.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlSfhj.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlSfhj.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlSfhj.toString());
		StringBuffer sqlHs = new StringBuffer();
		sqlHs.append("update cw_xzb  set shzt  = '5' where trim(shzt) = '0' ");
		if(Validate.noNull(gzyf)){
			sqlHs.append(" and gzyf='"+gzyf+"'");
		}
		if(Validate.noNull(rybh)){
			sqlHs.append(" and rybh='"+rybh+"'");
		}
		if(Validate.noNull(bm)){
			sqlHs.append(" bm gzyf='"+bm+"'");
		}
		sqlList.add(sqlHs.toString());
		db.batchUpdate(sqlList);
		return 1;
	}

	/**
	 * 在职薪资导入数据确认
	 * 
	 * @param file
	 * @return
	 */
	public List<Map<String, Object>> getXzImpeQr(String file) {
		Workbook rwb;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int rows = rs.getRows();// 得到所有的行
			int cols = rs.getColumns();
			String[] num = new String[rows];
			for (int i =  2; i < rows; i++) {// 第一行是列名，不需要导入数据库;获得的所有行比实际数据多一行。
				String rybhxg = "";
				String updatepj = "";// 定义update拼接字符串
				String insertpj = "";// 定义insert拼接字符串
				String insertsz = "";// 定义insert字段内容拼接字符串
				int a = 0;
				Map<String, Object> map = new HashMap<String, Object>();
				// String sql = "SELECT COUNT(*) FROM CW_XZB T WHERE T.RYBH='"+
				// rs.getCell(0, i).getContents() + "' AND T.GZYF='"+
				// rs.getCell(61, i).getContents() + "'";
				for (int j = 0; j < cols; j++) {
					// List<Map<String,Object>> gzwhlist=getGzwhList();
					// for(int z=0;z<gzwhlist.size();z++) {
					// String
					// xmjc=Validate.isNullToDefaultString(gzwhlist.get(z).get("xmjc"),
					// "");
					// String
					// xmmc=Validate.isNullToDefaultString(gzwhlist.get(z).get("xmmc"),
					// "");
					// if(xmmc.equals(rs.getCell(j, 0).getContents())) {
					// System.err.println("xmmc==> "+xmmc);
					// String rybh = rs.getCell(j, i).getContents();//人员编号
					// if("rybh".equals(xmjc)) {
					// rybhxg=rybh;
					// }
					// if("gzyf".equals(xmjc)) {
					// String cfsql =
					// "SELECT COUNT(*) FROM cw_xzb T WHERE T.RYBH='"+ rybhxg +
					// "' AND T.GZYF='"+ rybh + "'";
					// a = Integer.parseInt(db.queryForSingleValue(cfsql));
					// System.err.println("a=="+a);
					// System.err.println("cfsql=="+cfsql);
					// }
					// updatepj = updatepj+ ""+xmjc+"='"+rybh+"',";
					// insertpj = insertpj+ xmjc+",";
					// insertsz = insertsz+ "'"+rybh+"',";
					// if(a>0&&"gzyf".equals(xmjc)) {
					// map.put(""+xmjc+"",
					// rybh+"&nbsp&nbsp&nbsp<font color='red'>(该月薪资信息已存在)</font>");
					// }else {
					// map.put(""+xmjc+"", rybh);
					// }
					// System.err.println("map=> "+map);
					// }
					// }
					switch (rs.getCell(j, 0).getContents()) {
					case "rybh":
						String rybh = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rybh)) {
						rybhxg = rybh;
						insertpj = insertpj + "rybh,";
						insertsz = insertsz + "'" + rybh + "',";
						map.put("RYBH", rybh);
						// }
						break;
					case "xm":
						String xm = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(xm)) {
						updatepj = updatepj + "xm='" + xm + "',";
						insertpj = insertpj + "xm,";
						insertsz = insertsz + "'" + xm + "',";
						map.put("XM", xm);
						// }
						break;
					case "xl":
						String xl = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bm)) {
						updatepj = updatepj + "xl='" + xl + "',";
						insertpj = insertpj + "xl,";
						insertsz = insertsz + "'" + xl + "',";
						map.put("XL", xl);
						// }
						break;
					case "bm":
						String bm = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bm)) {
						updatepj = updatepj + "bm='" + bm + "',";
						insertpj = insertpj + "bm,";
						insertsz = insertsz + "'" + bm + "',";
						map.put("BM", bm);
						// }
						break;
					case "rylb":
						String rylb = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rylb)) {
						updatepj = updatepj + "rylb='" + rylb + "',";
						insertpj = insertpj + "rylb,";
						insertsz = insertsz + "'" + rylb + "',";
						map.put("RYLB", rylb);
						// }
						break;
					case "rylx":
						String rylx = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rylb)) {
						updatepj = updatepj + "rylx='" + rylx + "',";
						insertpj = insertpj + "rylx,";
						insertsz = insertsz + "'" + rylx + "',";
						map.put("RYLX", rylx);
						// }
						break;
					case "gwgz":
						String gwgz = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(jbgz)) {
						updatepj = updatepj + "gwgz='" + gwgz + "',";
						insertpj = insertpj + "gwgz,";
						insertsz = insertsz + "'" + gwgz + "',";
						map.put("GWGZ", gwgz);
						// }
						break;
					case "xjgz":
						String xjgz = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(xjgz)) {
						updatepj = updatepj + "xjgz='" + xjgz + "',";
						insertpj = insertpj + "xjgz,";
						insertsz = insertsz + "'" + xjgz + "',";
						map.put("XJGZ", xjgz);
						// }
						break;
					case "dszf":
						String dszf = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(dszf)) {
						updatepj = updatepj + "dszf='" + dszf + "',";
						insertpj = insertpj + "dszf,";
						insertsz = insertsz + "'" + dszf + "',";
						map.put("DSZF", dszf);
						// }
						break;
					case "jljx1":
						String jljx1 = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(jljx1)) {
						updatepj = updatepj + "jljx1='" + jljx1 + "',";
						insertpj = insertpj + "jljx1,";
						insertsz = insertsz + "'" + jljx1 + "',";
						map.put("JLJX1", jljx1);
						// }
						break;
					case "bsbt":
						String bsbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bsbt)) {
						updatepj = updatepj + "bsbt='" + bsbt + "',";
						insertpj = insertpj + "bsbt,";
						insertsz = insertsz + "'" + bsbt + "',";
						map.put("BSBT", bsbt);
						// }
						break;
					case "bxqzbbt":
						String bxqzbbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bxqzbbt)) {
						updatepj = updatepj + "bxqzbbt='" + bxqzbbt + "',";
						insertpj = insertpj + "bxqzbbt,";
						insertsz = insertsz + "'" + bxqzbbt + "',";
						map.put("BXQZBBT", bxqzbbt);
						// }
						break;
					case "sybt":
						String sybt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(sybt)) {
						updatepj = updatepj + "sybt='" + sybt + "',";
						insertpj = insertpj + "sybt,";
						insertsz = insertsz + "'" + sybt + "',";
						map.put("SYBT", sybt);
						// }
						break;
					case "jhbt":
						String jhbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(jhbt)) {
						updatepj = updatepj + "jhbt='" + jhbt + "',";
						insertpj = insertpj + "jhbt,";
						insertsz = insertsz + "'" + jhbt + "',";
						map.put("JHBT", jhbt);
						// }
						break;
					case "zjbt":
						String zjbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(zjbt)) {
						updatepj = updatepj + "zjbt='" + zjbt + "',";
						insertpj = insertpj + "zjbt,";
						insertsz = insertsz + "'" + zjbt + "',";
						map.put("ZJBT", zjbt);
						// }
						break;
					case "htbt":
						String htbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(htbt)) {
						updatepj = updatepj + "htbt='" + htbt + "',";
						insertpj = insertpj + "htbt,";
						insertsz = insertsz + "'" + htbt + "',";
						map.put("HTBT", htbt);
						// }
						break;
					case "fzgzl":
						String fzgzl = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(fzgzl)) {
						updatepj = updatepj + "fzgzl='" + fzgzl + "',";
						insertpj = insertpj + "fzgzl,";
						insertsz = insertsz + "'" + fzgzl + "',";
						map.put("FZGZL", fzgzl);
						// }
						break;
					case "zsjl":
						String zsjl = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(zsjl)) {
						updatepj = updatepj + "zsjl='" + zsjl + "',";
						insertpj = insertpj + "zsjl,";
						insertsz = insertsz + "'" + zsjl + "',";
						map.put("ZSJL", zsjl);
						// }
						break;
					case "fdyyjzbbt":
						String fdyyjzbbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(fdyyjzbbt)) {
						updatepj = updatepj + "fdyyjzbbt='" + fdyyjzbbt + "',";
						insertpj = insertpj + "fdyyjzbbt,";
						insertsz = insertsz + "'" + fdyyjzbbt + "',";
						map.put("FDYYJZBBT", fdyyjzbbt);
						// }
						break;
					case "khj":
						String khj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(khj)) {
						updatepj = updatepj + "khj='" + khj + "',";
						insertpj = insertpj + "khj,";
						insertsz = insertsz + "'" + khj + "',";
						map.put("KHJ", khj);
						// }
						break;
					case "jiangkef":
						String jiangkef = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(jiangkef)) {
						updatepj = updatepj + "jiangkef='" + jiangkef + "',";
						insertpj = insertpj + "jiangkef,";
						insertsz = insertsz + "'" + jiangkef + "',";
						map.put("JIANGKEF", jiangkef);
						// }
						break;
					case "bgzks":
						String bgzks = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgzks)) {
						updatepj = updatepj + "bgzks='" + bgzks + "',";
						insertpj = insertpj + "bgzks,";
						insertsz = insertsz + "'" + bgzks + "',";
						map.put("BGZKS", bgzks);
						// }
						break;
					case "qnjsx":
						String qnjsx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qnjsx)) {
						updatepj = updatepj + "qnjsx='" + qnjsx + "',";
						insertpj = insertpj + "qnjsx,";
						insertsz = insertsz + "'" + qnjsx + "',";
						map.put("QNJSX", qnjsx);
						// }
						break;
					case "qnjsx1":
						String qnjsx1 = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(qnjsx)) {
						updatepj = updatepj + "qnjsx1='" + qnjsx1 + "',";
						insertpj = insertpj + "qnjsx1,";
						insertsz = insertsz + "'" + qnjsx1 + "',";
						map.put("QNJSX1", qnjsx1);
						// }
						break;
					case "qnjsx2":
						String qnjsx2 = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(qnjsx)) {
						updatepj = updatepj + "qnjsx2='" + qnjsx2 + "',";
						insertpj = insertpj + "qnjsx2,";
						insertsz = insertsz + "'" + qnjsx2 + "',";
						map.put("QNJSX2", qnjsx2);
						// }
						break;
					case "qnjsx3":
						String qnjsx3 = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(qnjsx)) {
						updatepj = updatepj + "qnjsx3='" + qnjsx3 + "',";
						insertpj = insertpj + "qnjsx3,";
						insertsz = insertsz + "'" + qnjsx3 + "',";
						map.put("QNJSX3", qnjsx3);
						// }
						break;
					case "bjcxjxgz2014jsjs":
						String bjcxjxgz2014jsjs = rs.getCell(j, i)
								.getContents();// 人员编号
						// if(Validate.noNull(bjcxjxgz2014jsjs)) {
						updatepj = updatepj + "bjcxjxgz2014jsjs='"
								+ bjcxjxgz2014jsjs + "',";
						insertpj = insertpj + "bjcxjxgz2014jsjs,";
						insertsz = insertsz + "'" + bjcxjxgz2014jsjs + "',";
						map.put("BJCXJXGZ2014JSJS", bjcxjxgz2014jsjs);
						// }
						break;
					case "bxbt2014n1d10yjsjs":
						String bxbt2014n1d10yjsjs = rs.getCell(j, i)
								.getContents();// 人员编号
						// if(Validate.noNull(bjcxjxgz2014jsjs)) {
						updatepj = updatepj + "bxbt2014n1d10yjsjs='"
								+ bxbt2014n1d10yjsjs + "',";
						insertpj = insertpj + "bxbt2014n1d10yjsjs,";
						insertsz = insertsz + "'" + bxbt2014n1d10yjsjs + "',";
						map.put("BXBT2014N1D10YJSJS", bxbt2014n1d10yjsjs);
						// }
						break;
					case "jsjs":
						String jsjs = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bjcxjxgz2014jsjs)) {
						updatepj = updatepj + "jsjs='" + jsjs + "',";
						insertpj = insertpj + "jsjs,";
						insertsz = insertsz + "'" + jsjs + "',";
						map.put("JSJS", jsjs);
						// }
						break;
					case "pgjj":
						String pgjj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bjcxjxgz2014jsjs)) {
						updatepj = updatepj + "pgjj='" + pgjj + "',";
						insertpj = insertpj + "pgjj,";
						insertsz = insertsz + "'" + pgjj + "',";
						map.put("PGJJ", pgjj);
						// }
						break;
					case "ylbx":
						String ylbx = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bjcxjxgz2014jsjs)) {
						updatepj = updatepj + "ylbx='" + ylbx + "',";
						insertpj = insertpj + "ylbx,";
						insertsz = insertsz + "'" + ylbx + "',";
						map.put("YLBX", ylbx);
						// }
						break;
					case "dks":
						String dks = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(dks)) {
						updatepj = updatepj + "dks='" + dks + "',";
						insertpj = insertpj + "dks,";
						insertsz = insertsz + "'" + dks + "',";
						map.put("DKS", dks);
						// }
						break;
					case "bnse":
						String bnse = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bnse)) {
						updatepj = updatepj + "bnse='" + bnse + "',";
						insertpj = insertpj + "bnse,";
						insertsz = insertsz + "'" + bnse + "',";
						map.put("BNSE", bnse);
						// }
						break;
					case "snse":
						String snse = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(snse)) {
						updatepj = updatepj + "snse='" + snse + "',";
						insertpj = insertpj + "snse,";
						insertsz = insertsz + "'" + snse + "',";
						map.put("SNSE", snse);
						// }
						break;
					case "sbj":
						String sbj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(sbj)) {
						updatepj = updatepj + "sbj='" + sbj + "',";
						insertpj = insertpj + "sbj,";
						insertsz = insertsz + "'" + sbj + "',";
						map.put("SBJ", sbj);
						// }
						break;
					case "sjdck":
						String sjdck = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(sjdck)) {
						updatepj = updatepj + "sjdck='" + sjdck + "',";
						insertpj = insertpj + "sjdck,";
						insertsz = insertsz + "'" + sjdck + "',";
						map.put("SJDCK", sjdck);
						// }
						break;
					case "axyrj":
						String axyrj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(axyrj)) {
						updatepj = updatepj + "axyrj='" + axyrj + "',";
						insertpj = insertpj + "axyrj,";
						insertsz = insertsz + "'" + axyrj + "',";
						map.put("AXYRJ", axyrj);
						// }
						break;
					case "xh":
						String xh = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(xh)) {
						updatepj = updatepj + "xh='" + xh + "',";
						insertpj = insertpj + "xh,";
						insertsz = insertsz + "'" + xh + "',";
						map.put("XH", xh);
						// }
						break;
					case "nzjdks":
						String nzjdks = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(nzjdks)) {
						updatepj = updatepj + "nzjdks='" + nzjdks + "',";
						insertpj = insertpj + "nzjdks,";
						insertsz = insertsz + "'" + nzjdks + "',";
						map.put("NZJDKS", nzjdks);
						// }
						break;
					case "gzdks":
						String gzdks = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(gzdks)) {
						updatepj = updatepj + "gzdks='" + gzdks + "',";
						insertpj = insertpj + "gzdks,";
						insertsz = insertsz + "'" + gzdks + "',";
						map.put("GZDKS", gzdks);
						// }
						break;
					case "kshj":
						String kshj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(kshj)) {
						updatepj = updatepj + "kshj='" + kshj + "',";
						insertpj = insertpj + "kshj,";
						insertsz = insertsz + "'" + kshj + "',";
						map.put("KSHJ", kshj);
						// }
						break;
					case "gh":
						String gh = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(gh)) {
						updatepj = updatepj + "gh='" + gh + "',";
						insertpj = insertpj + "gh,";
						insertsz = insertsz + "'" + gh + "',";
						map.put("GH", gh);
						// }
						break;
					case "sfzb":
						String sfzb = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(sfzb)) {
						updatepj = updatepj + "sfzb='" + sfzb + "',";
						insertpj = insertpj + "sfzb,";
						insertsz = insertsz + "'" + sfzb + "',";
						map.put("SFZB", sfzb);
						// }
						break;
					case "bkylbx":
						String bkylbx = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bkylbx)) {
						updatepj = updatepj + "bkylbx='" + bkylbx + "',";
						insertpj = insertpj + "bkylbx,";
						insertsz = insertsz + "'" + bkylbx + "',";
						map.put("BKYLBX", bkylbx);
						// }
						break;
					case "bksyj":
						String bksyj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bksyj)) {
						updatepj = updatepj + "bksyj='" + bksyj + "',";
						insertpj = insertpj + "bksyj,";
						insertsz = insertsz + "'" + bksyj + "',";
						map.put("BKSYJ", bksyj);
						// }
						break;
					case "bkylj":
						String bkylj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bkylj)) {
						updatepj = updatepj + "bkylj='" + bkylj + "',";
						insertpj = insertpj + "bkylj,";
						insertsz = insertsz + "'" + bkylj + "',";
						map.put("BKYLJ", bkylj);
						// }
						break;
					case "bksbj":
						String bksbj = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bksbj)) {
						updatepj = updatepj + "bksbj='" + bksbj + "',";
						insertpj = insertpj + "bksbj,";
						insertsz = insertsz + "'" + bksbj + "',";
						map.put("BKSBJ", bksbj);
						// }
						break;
					case "sfdy":
						String sfdy = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(sfdy)) {
						updatepj = updatepj + "sfdy='" + sfdy + "',";
						insertpj = insertpj + "sfdy,";
						insertsz = insertsz + "'" + sfdy + "',";
						map.put("SFDY", sfdy);
						// }
						break;
					case "rdqk":
						String rdqk = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rdqk)) {
						updatepj = updatepj + "rdqk='" + rdqk + "',";
						insertpj = insertpj + "rdqk,";
						insertsz = insertsz + "'" + rdqk + "',";
						map.put("RDQK", rdqk);
						// }
						break;
					case "yzwbt":
						String yzwbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(yzwbt)) {
						updatepj = updatepj + "yzwbt='" + yzwbt + "',";
						insertpj = insertpj + "yzwbt,";
						insertsz = insertsz + "'" + yzwbt + "',";
						map.put("YZWBT", yzwbt);
						// }
						break;
					case "gwbt":
						String gwbt = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(gwbt)) {
						updatepj = updatepj + "gwbt='" + gwbt + "',";
						insertpj = insertpj + "gwbt,";
						insertsz = insertsz + "'" + gwbt + "',";
						map.put("GWBT", gwbt);
						// }
						break;
					case "xzft":
						String xzft = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(xzft)) {
						updatepj = updatepj + "xzft='" + xzft + "',";
						insertpj = insertpj + "xzft,";
						insertsz = insertsz + "'" + xzft + "',";
						map.put("XZFT", xzft);
						// }
						break;
					case "hzbt":
						String hzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(hzbt)) {
						updatepj = updatepj + "hzbt='" + hzbt + "',";
						insertpj = insertpj + "hzbt,";
						insertsz = insertsz + "'" + hzbt + "',";
						map.put("HZBT", hzbt);
						// }
						break;
					case "txtgbf":
						String txtgbf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(txtgbf)) {
						updatepj = updatepj + "txtgbf='" + txtgbf + "',";
						insertpj = insertpj + "txtgbf,";
						insertsz = insertsz + "'" + txtgbf + "',";
						map.put("TXTGBF", txtgbf);
						// }
						break;
					case "shbt":
						String shbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(shbt)) {
						updatepj = updatepj + "shbt='" + shbt + "',";
						insertpj = insertpj + "shbt,";
						insertsz = insertsz + "'" + shbt + "',";
						map.put("SHBT", shbt);
						// }
						break;
					case "xzbt":
						String xzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xzbt)) {
						updatepj = updatepj + "xzbt='" + xzbt + "',";
						insertpj = insertpj + "xzbt,";
						insertsz = insertsz + "'" + xzbt + "',";
						map.put("XZBT", xzbt);
						// }
						break;
					case "wjbt":
						String wjbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wjbt)) {
						updatepj = updatepj + "wjbt='" + wjbt + "',";
						insertpj = insertpj + "wjbt,";
						insertsz = insertsz + "'" + wjbt + "',";
						map.put("WJBT", wjbt);
						// }
						break;
					case "txhl":
						String txhl = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(txhl)) {
						updatepj = updatepj + "txhl='" + txhl + "',";
						insertpj = insertpj + "txhl,";
						insertsz = insertsz + "'" + txhl + "',";
						map.put("TXHL", txhl);
						// }
						break;
					case "qtbt":
						String qtbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qtbt)) {
						updatepj = updatepj + "qtbt='" + qtbt + "',";
						insertpj = insertpj + "qtbt,";
						insertsz = insertsz + "'" + qtbt + "',";
						map.put("QTBT", qtbt);
						// }
						break;
					case "ltbt":
						String ltbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(ltbt)) {
						updatepj = updatepj + "ltbt='" + ltbt + "',";
						insertpj = insertpj + "ltbt,";
						insertsz = insertsz + "'" + ltbt + "',";
						map.put("LTBT", ltbt);
						// }
						break;
					case "yzbt":
						String yzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yzbt)) {
						updatepj = updatepj + "yzbt='" + yzbt + "',";
						insertpj = insertpj + "yzbt,";
						insertsz = insertsz + "'" + yzbt + "',";
						map.put("YZBT", yzbt);
						// }
						break;
					case "jcjx":
						String jcjx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jcjx)) {
						updatepj = updatepj + "jcjx='" + jcjx + "',";
						insertpj = insertpj + "jcjx,";
						insertsz = insertsz + "'" + jcjx + "',";
						map.put("JCJX", jcjx);
						// }
						break;
					case "jtf":
						String jtf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jtf)) {
						updatepj = updatepj + "jtf='" + jtf + "',";
						insertpj = insertpj + "jtf,";
						insertsz = insertsz + "'" + jtf + "',";
						map.put("JTF", jtf);
						// }
						break;
					case "bt":
						String bt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bt)) {
						updatepj = updatepj + "bt='" + bt + "',";
						insertpj = insertpj + "bt,";
						insertsz = insertsz + "'" + bt + "',";
						map.put("BT", bt);
						// }
						break;
					case "zfbt":
						String zfbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zfbt)) {
						updatepj = updatepj + "zfbt='" + zfbt + "',";
						insertpj = insertpj + "zfbt,";
						insertsz = insertsz + "'" + zfbt + "',";
						map.put("ZFBT", zfbt);
						// }
						break;
					case "bgz":
						String bgz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgz)) {
						updatepj = updatepj + "bgz='" + bgz + "',";
						insertpj = insertpj + "bgz,";
						insertsz = insertsz + "'" + bgz + "',";
						map.put("BGZ", bgz);
						// }
						break;
					case "wybt":
						String wybt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wybt)) {
						updatepj = updatepj + "wybt='" + wybt + "',";
						insertpj = insertpj + "wybt,";
						insertsz = insertsz + "'" + wybt + "',";
						map.put("WYBT", wybt);
						// }
						break;
					case "jkf":
						String jkf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jkf)) {
						updatepj = updatepj + "jkf='" + jkf + "',";
						insertpj = insertpj + "jkf,";
						insertsz = insertsz + "'" + jkf + "',";
						map.put("JKF", jkf);
						// }
						break;
					case "gjf":
						String gjf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gjf)) {
						updatepj = updatepj + "gjf='" + gjf + "',";
						insertpj = insertpj + "gjf,";
						insertsz = insertsz + "'" + gjf + "',";
						map.put("GJF", gjf);
						// }
						break;
					case "dhf":
						String dhf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(dhf)) {
						updatepj = updatepj + "dhf='" + dhf + "',";
						insertpj = insertpj + "dhf,";
						insertsz = insertsz + "'" + dhf + "',";
						map.put("DHF", dhf);
						// }
						break;
					case "yfhj":
						String yfhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yfhj)) {
						updatepj = updatepj + "yfhj='" + yfhj + "',";
						insertpj = insertpj + "yfhj,";
						insertsz = insertsz + "'" + yfhj + "',";
						map.put("YFHJ", yfhj);
						// }
						break;
					case "fz":
						String fz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(fz)) {
						updatepj = updatepj + "fz='" + fz + "',";
						insertpj = insertpj + "fz,";
						insertsz = insertsz + "'" + fz + "',";
						map.put("FZ", fz);
						// }
						break;
					case "nqf":
						String nqf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nqf)) {
						updatepj = updatepj + "nqf='" + nqf + "',";
						insertpj = insertpj + "nqf,";
						insertsz = insertsz + "'" + nqf + "',";
						map.put("NQF", nqf);
						// }
						break;
					case "nqf1":
						String nqf1 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nqf1)) {
						updatepj = updatepj + "nqf1='" + nqf1 + "',";
						insertpj = insertpj + "nqf1,";
						insertsz = insertsz + "'" + nqf1 + "',";
						map.put("NQF1", nqf1);
						// }
						break;
					case "wyf":
						String wyf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wyf)) {
						updatepj = updatepj + "wyf='" + wyf + "',";
						insertpj = insertpj + "wyf,";
						insertsz = insertsz + "'" + wyf + "',";
						map.put("WYF", wyf);
						// }
						break;
					case "jk":
						String jk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jk)) {
						updatepj = updatepj + "jk='" + jk + "',";
						insertpj = insertpj + "jk,";
						insertsz = insertsz + "'" + jk + "',";
						map.put("JK", jk);
						// }
						break;
					case "ylj":
						String ylj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(ylj)) {
						updatepj = updatepj + "ylj='" + ylj + "',";
						insertpj = insertpj + "ylj,";
						insertsz = insertsz + "'" + ylj + "',";
						map.put("YLJ", ylj);
						// }
						break;
					case "bgjj":
						String bgjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgjj)) {
						updatepj = updatepj + "bgjj='" + bgjj + "',";
						insertpj = insertpj + "bgjj,";
						insertsz = insertsz + "'" + bgjj + "',";
						map.put("BGJJ", bgjj);
						// }
						break;
					case "bs":
						String bs = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bs)) {
						updatepj = updatepj + "bs='" + bs + "',";
						insertpj = insertpj + "bs,";
						insertsz = insertsz + "'" + bs + "',";
						map.put("BS", bs);
						// }
						break;
					case "sjdcj":
						String sjdcj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sjdcj)) {
						updatepj = updatepj + "sjdcj='" + sjdcj + "',";
						insertpj = insertpj + "sjdcj,";
						insertsz = insertsz + "'" + sjdcj + "',";
						map.put("SJDCJ", sjdcj);
						// }
						break;
					case "syj":
						String syj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(syj)) {
						updatepj = updatepj + "syj='" + syj + "',";
						insertpj = insertpj + "syj,";
						insertsz = insertsz + "'" + syj + "',";
						map.put("SYJ", syj);
						// }
						break;
					case "kkhj":
						String kkhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kkhj)) {
						updatepj = updatepj + "kkhj='" + kkhj + "',";
						insertpj = insertpj + "kkhj,";
						insertsz = insertsz + "'" + kkhj + "',";
						map.put("KKHJ", kkhj);
						// }
						break;
					case "sfhj":
						String sfhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sfhj)) {
						updatepj = updatepj + "sfhj='" + sfhj + "',";
						insertpj = insertpj + "sfhj,";
						insertsz = insertsz + "'" + sfhj + "',";
						map.put("SFHJ", sfhj);
						// }
						break;
					case "gzyf":
						String gzyf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gzyf)) {
						String cfsql = "SELECT COUNT(*) FROM cw_xzb T WHERE T.RYBH='"
								+ rybhxg + "' AND T.GZYF='" + gzyf + "'";
						a = Integer.parseInt(db.queryForSingleValue(cfsql));
						updatepj = updatepj + "gzyf='" + gzyf + "',";
						insertpj = insertpj + "gzyf,";
						insertsz = insertsz + "'" + gzyf + "',";
						// map.put("gzyf", gzyf);
						if (a > 0) {
							map.put("GZYF",
									gzyf
											+ "&nbsp&nbsp&nbsp<font color='red'>(该月薪资信息已存在)</font>");
						} else {
							map.put("GZYF", gzyf);
						}
						// }
						break;
					case "bh":
						String bh = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bh)) {
						updatepj = updatepj + "bh='" + bh + "',";
						insertpj = insertpj + "bh,";
						insertsz = insertsz + "'" + bh + "',";
						map.put("BH", bh);
						// }
						break;
					case "jsx":
						String jsx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jsx)) {
						updatepj = updatepj + "jsx='" + jsx + "',";
						insertpj = insertpj + "jsx,";
						insertsz = insertsz + "'" + jsx + "',";
						map.put("JSX", jsx);
						// }
						break;
					case "zfjj":
						String zfjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zfjj)) {
						updatepj = updatepj + "zfjj='" + zfjj + "',";
						insertpj = insertpj + "zfjj,";
						insertsz = insertsz + "'" + zfjj + "',";
						map.put("ZFJJ", zfjj);
						// }
						break;
					case "nzj":
						String nzj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nzj)) {
						updatepj = updatepj + "nzj='" + nzj + "',";
						insertpj = insertpj + "nzj,";
						insertsz = insertsz + "'" + nzj + "',";
						map.put("NZJ", nzj);
						// }
						break;
					case "kk":
						String kk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kk)) {
						// if(Validate.noNull(kk)) {
						updatepj = updatepj + "kk='" + kk + "',";
						insertpj = insertpj + "kk,";
						insertsz = insertsz + "'" + kk + "',";
						map.put("KK", kk);
						// }
						break;
					}
				}
				updatepj = updatepj.substring(0, updatepj.length() - 1);
				insertpj = insertpj.substring(0, insertpj.length() - 1);
				insertsz = insertsz.substring(0, insertsz.length() - 1);
				list.add(map);
			}
			rwb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 离职薪资导入数据确认
	 * 
	 * @param file
	 * @return
	 */
	public List<Map<String, Object>> getLzxzImpeQr(String file) {
		Workbook rwb;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs = rwb.getSheet(0);// 或者rwb.getSheet(0)
			int rows = rs.getRows();// 得到所有的行
			int cols = rs.getColumns();
			String[] num = new String[rows];
			for (int i = 1; i < rows; i++) {// 第一行是列名，不需要导入数据库;获得的所有行比实际数据多一行。
				Map<String, Object> map = new HashMap<String, Object>();
				String rybhxg = "";
				String updatepj = "";// 定义update拼接字符串
				String insertpj = "";// 定义insert拼接字符串
				String insertsz = "";// 定义insert字段内容拼接字符串
				int a = 0;
				for (int j = 0; j < cols; j++) {
					switch (rs.getCell(j, 0).getContents()) {
					case "人员编号":
						String rybh = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rybh)) {
						rybhxg = rybh;
						insertpj = insertpj + "rybh,";
						insertsz = insertsz + "'" + rybh + "',";
						map.put("RYBH", rybh);
						// }
						break;
					case "姓名":
						String xm = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(xm)) {
						updatepj = updatepj + "xm='" + xm + "',";
						insertpj = insertpj + "xm,";
						insertsz = insertsz + "'" + xm + "',";
						map.put("XM", xm);
						// }
						break;
					case "部门":
						String bm = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(bm)) {
						updatepj = updatepj + "bm='" + bm + "',";
						insertpj = insertpj + "bm,";
						insertsz = insertsz + "'" + bm + "',";
						map.put("BM", bm);
						// }
						break;
					case "人员类别":
						String rylb = rs.getCell(j, i).getContents();// 人员编号
						// if(Validate.noNull(rylb)) {
						updatepj = updatepj + "rylb='" + rylb + "',";
						insertpj = insertpj + "rylb,";
						insertsz = insertsz + "'" + rylb + "',";
						map.put("RYLB", rylb);
						// }
						break;
					case "基本工资":
						String jbgz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jbgz)) {
						updatepj = updatepj + "jbgz='" + jbgz + "',";
						insertpj = insertpj + "jbgz,";
						insertsz = insertsz + "'" + jbgz + "',";
						map.put("JBGZ", jbgz);
						// }
						break;
					case "增加离退费":
						String zjltf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zjltf)) {
						updatepj = updatepj + "zjltf='" + zjltf + "',";
						insertpj = insertpj + "zjltf,";
						insertsz = insertsz + "'" + zjltf + "',";
						map.put("ZJLTF", zjltf);
						// }
						break;
					case "原职务补贴":
						String yzwbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yzwbt)) {
						updatepj = updatepj + "yzwbt='" + yzwbt + "',";
						insertpj = insertpj + "yzwbt,";
						insertsz = insertsz + "'" + yzwbt + "',";
						map.put("YZWBT", yzwbt);
						// }
						break;
					case "岗位补贴":
						String gwbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gwbt)) {
						updatepj = updatepj + "gwbt='" + gwbt + "',";
						insertpj = insertpj + "gwbt,";
						insertsz = insertsz + "'" + gwbt + "',";
						map.put("GWBT", gwbt);
						// }
						break;
					case "新住房贴":
						String xzft = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xzft)) {
						updatepj = updatepj + "xzft='" + xzft + "',";
						insertpj = insertpj + "xzft,";
						insertsz = insertsz + "'" + xzft + "',";
						map.put("XZFT", xzft);
						// }
						break;
					case "回族补贴":
						String hzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(hzbt)) {
						updatepj = updatepj + "hzbt='" + hzbt + "',";
						insertpj = insertpj + "hzbt,";
						insertsz = insertsz + "'" + hzbt + "',";
						map.put("HZBT", hzbt);
						// }
						break;
					case "退休提高部分":
						String txtgbf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(txtgbf)) {
						updatepj = updatepj + "txtgbf='" + txtgbf + "',";
						insertpj = insertpj + "txtgbf,";
						insertsz = insertsz + "'" + txtgbf + "',";
						map.put("TXTGBF", txtgbf);
						// }
						break;
					case "生活补贴":
						String shbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(shbt)) {
						updatepj = updatepj + "shbt='" + shbt + "',";
						insertpj = insertpj + "shbt,";
						insertsz = insertsz + "'" + shbt + "',";
						map.put("SHBT", shbt);
						// }
						break;
					case "新增补贴":
						String xzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(xzbt)) {
						updatepj = updatepj + "xzbt='" + xzbt + "',";
						insertpj = insertpj + "xzbt,";
						insertsz = insertsz + "'" + xzbt + "',";
						map.put("XZBT", xzbt);
						// }
						break;
					case "物价补贴":
						String wjbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wjbt)) {
						updatepj = updatepj + "wjbt='" + wjbt + "',";
						insertpj = insertpj + "wjbt,";
						insertsz = insertsz + "'" + wjbt + "',";
						map.put("WJBT", wjbt);
						// }
						break;
					case "特需护理":
						String txhl = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(txhl)) {
						updatepj = updatepj + "txhl='" + txhl + "',";
						insertpj = insertpj + "txhl,";
						insertsz = insertsz + "'" + txhl + "',";
						map.put("TXHL", txhl);
						// }
						break;
					case "救护补贴":
						String jhbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jhbt)) {
						updatepj = updatepj + "jhbt='" + jhbt + "',";
						insertpj = insertpj + "jhbt,";
						insertsz = insertsz + "'" + jhbt + "',";
						map.put("JHBT", jhbt);
						// }
						break;
					case "其他补贴":
						String qtbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(qtbt)) {
						updatepj = updatepj + "qtbt='" + qtbt + "',";
						insertpj = insertpj + "qtbt,";
						insertsz = insertsz + "'" + qtbt + "',";
						map.put("QTBT", qtbt);
						// }
						break;
					case "离退补贴":
						String ltbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(ltbt)) {
						updatepj = updatepj + "ltbt='" + ltbt + "',";
						insertpj = insertpj + "ltbt,";
						insertsz = insertsz + "'" + ltbt + "',";
						map.put("LTBT", ltbt);
						// }
						break;
					case "月增补贴":
						String yzbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yzbt)) {
						updatepj = updatepj + "yzbt='" + yzbt + "',";
						insertpj = insertpj + "yzbt,";
						insertsz = insertsz + "'" + yzbt + "',";
						map.put("YZBT", yzbt);
						// }
						break;
					case "基础绩效":
						String jcjx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jcjx)) {
						updatepj = updatepj + "jcjx='" + jcjx + "',";
						insertpj = insertpj + "jcjx,";
						insertsz = insertsz + "'" + jcjx + "',";
						map.put("JCJX", jcjx);
						// }
						break;
					case "交通费":
						String jtf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jtf)) {
						updatepj = updatepj + "jtf='" + jtf + "',";
						insertpj = insertpj + "jtf,";
						insertsz = insertsz + "'" + jtf + "',";
						map.put("JTF", jtf);
						// }
						break;
					case "补贴":
						String bt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bt)) {
						updatepj = updatepj + "bt='" + bt + "',";
						insertpj = insertpj + "bt,";
						insertsz = insertsz + "'" + bt + "',";
						map.put("BT", bt);
						// }
						break;
					case "租房补贴":
						String zfbt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zfbt)) {
						updatepj = updatepj + "zfbt='" + zfbt + "',";
						insertpj = insertpj + "zfbt,";
						insertsz = insertsz + "'" + zfbt + "',";
						map.put("ZFBT", zfbt);
						// }
						break;
					case "补工资":
						String bgz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgz)) {
						updatepj = updatepj + "bgz='" + bgz + "',";
						insertpj = insertpj + "bgz,";
						insertsz = insertsz + "'" + bgz + "',";
						map.put("BGZ", bgz);
						// }
						break;
					case "物业补贴":
						String wybt = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wybt)) {
						updatepj = updatepj + "wybt='" + wybt + "',";
						insertpj = insertpj + "wybt,";
						insertsz = insertsz + "'" + wybt + "',";
						map.put("WYBT", wybt);
						// }
						break;
					case "监考费":
						String jkf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jkf)) {
						updatepj = updatepj + "jkf='" + jkf + "',";
						insertpj = insertpj + "jkf,";
						insertsz = insertsz + "'" + jkf + "',";
						map.put("JKF", jkf);
						// }
						break;
					case "过节费":
						String gjf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gjf)) {
						updatepj = updatepj + "gjf='" + gjf + "',";
						insertpj = insertpj + "gjf,";
						insertsz = insertsz + "'" + gjf + "',";
						map.put("GJF", gjf);
						// }
						break;
					case "电话费":
						String dhf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(dhf)) {
						updatepj = updatepj + "dhf='" + dhf + "',";
						insertpj = insertpj + "dhf,";
						insertsz = insertsz + "'" + dhf + "',";
						map.put("DHF", dhf);
						// }
						break;
					case "应发合计":
						String yfhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(yfhj)) {
						updatepj = updatepj + "yfhj='" + yfhj + "',";
						insertpj = insertpj + "yfhj,";
						insertsz = insertsz + "'" + yfhj + "',";
						map.put("YFHJ", yfhj);
						// }
						break;
					case "房租":
						String fz = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(fz)) {
						updatepj = updatepj + "fz='" + fz + "',";
						insertpj = insertpj + "fz,";
						insertsz = insertsz + "'" + fz + "',";
						map.put("FZ", fz);
						// }
						break;
					case "暖气费":
						String nqf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nqf)) {
						updatepj = updatepj + "nqf='" + nqf + "',";
						insertpj = insertpj + "nqf,";
						insertsz = insertsz + "'" + nqf + "',";
						map.put("NQF", nqf);
						// }
						break;
					case "暖气费1":
						String nqf1 = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nqf1)) {
						updatepj = updatepj + "nqf1='" + nqf1 + "',";
						insertpj = insertpj + "nqf1,";
						insertsz = insertsz + "'" + nqf1 + "',";
						map.put("NQF1", nqf1);
						// }
						break;
					case "物业费":
						String wyf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(wyf)) {
						updatepj = updatepj + "wyf='" + wyf + "',";
						insertpj = insertpj + "wyf,";
						insertsz = insertsz + "'" + wyf + "',";
						map.put("WYF", wyf);
						// }
						break;
					case "借款":
						String jk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jk)) {
						updatepj = updatepj + "jk='" + jk + "',";
						insertpj = insertpj + "jk,";
						insertsz = insertsz + "'" + jk + "',";
						map.put("JK", jk);
						// }
						break;
					case "养老金":
						String ylj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(ylj)) {
						updatepj = updatepj + "ylj='" + ylj + "',";
						insertpj = insertpj + "ylj,";
						insertsz = insertsz + "'" + ylj + "',";
						map.put("YLJ", ylj);
						// }
						break;
					case "补公积金":
						String bgjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bgjj)) {
						updatepj = updatepj + "bgjj='" + bgjj + "',";
						insertpj = insertpj + "bgjj,";
						insertsz = insertsz + "'" + bgjj + "',";
						map.put("BGJJ", bgjj);
						// }
						break;
					case "补税":
						String bs = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bs)) {
						updatepj = updatepj + "bs='" + bs + "',";
						insertpj = insertpj + "bs,";
						insertsz = insertsz + "'" + bs + "',";
						map.put("BS", bs);
						// }
						break;
					case "四季度菜金":
						String sjdcj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sjdcj)) {
						updatepj = updatepj + "sjdcj='" + sjdcj + "',";
						insertpj = insertpj + "sjdcj,";
						insertsz = insertsz + "'" + sjdcj + "',";
						map.put("SJDCJ", sjdcj);
						// }
						break;
					case "失业金":
						String syj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(syj)) {
						updatepj = updatepj + "syj='" + syj + "',";
						insertpj = insertpj + "syj,";
						insertsz = insertsz + "'" + syj + "',";
						map.put("SYJ", syj);
						// }
						break;
					case "扣款合计":
						String kkhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kkhj)) {
						updatepj = updatepj + "kkhj='" + kkhj + "',";
						insertpj = insertpj + "kkhj,";
						insertsz = insertsz + "'" + kkhj + "',";
						map.put("KKHJ", kkhj);
						// }
						break;
					case "实发合计":
						String sfhj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(sfhj)) {
						updatepj = updatepj + "sfhj='" + sfhj + "',";
						insertpj = insertpj + "sfhj,";
						insertsz = insertsz + "'" + sfhj + "',";
						map.put("SFHJ", sfhj);
						// }
						break;
					case "工资月份":
						String gzyf = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(gzyf)) {
						String cfsql = "SELECT COUNT(*) FROM CW_LZXZB T WHERE T.RYBH='"
								+ rybhxg + "' AND T.GZYF='" + gzyf + "'";
						a = Integer.parseInt(db.queryForSingleValue(cfsql));
						updatepj = updatepj + "gzyf='" + gzyf + "',";
						insertpj = insertpj + "gzyf,";
						insertsz = insertsz + "'" + gzyf + "',";
						// map.put("gzyf", gzyf);
						if (a > 0) {
							map.put("SFCF", "1");
							map.put("GZYF",
									gzyf
											+ "&nbsp&nbsp&nbsp<font color='red'>(该月薪资信息已存在)</font>");
						} else {
							map.put("SFCF", "0");
							map.put("GZYF", gzyf);
						}
						// }
						break;
					case "编号":
						String bh = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(bh)) {
						updatepj = updatepj + "bh='" + bh + "',";
						insertpj = insertpj + "bh,";
						insertsz = insertsz + "'" + bh + "',";
						map.put("BH", bh);
						// }
						break;
					case "计税项":
						String jsx = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(jsx)) {
						updatepj = updatepj + "jsx='" + jsx + "',";
						insertpj = insertpj + "jsx,";
						insertsz = insertsz + "'" + jsx + "',";
						map.put("JSX", jsx);
						// }
						break;
					case "住房积金":
						String zfjj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(zfjj)) {
						updatepj = updatepj + "zfjj='" + zfjj + "',";
						insertpj = insertpj + "zfjj,";
						insertsz = insertsz + "'" + zfjj + "',";
						map.put("ZFJJ", zfjj);
						// }
						break;
					case "年终奖":
						String nzj = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(nzj)) {
						updatepj = updatepj + "nzj='" + nzj + "',";
						insertpj = insertpj + "nzj,";
						insertsz = insertsz + "'" + nzj + "',";
						map.put("NZJ", nzj);
						// }
						break;
					case "扣款":
						String kk = rs.getCell(j, i).getContents();// 人员编号
						// if(!"0".equals(kk)) {
						// if(Validate.noNull(kk)) {
						updatepj = updatepj + "kk='" + kk + "',";
						insertpj = insertpj + "kk,";
						insertsz = insertsz + "'" + kk + "',";
						map.put("KK", kk);
						// }
						break;
					}
				}
				list.add(map);
			}
			rwb.close();
		} catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 在职薪资提交
	 * 
	 * @param guid
	 * @return
	 */
	public int doTj(String guid, String gzyf,String rybh,String bm) {
		String sql = new String();
		int result = 0;
		if (Validate.isNull(guid)) {
			StringBuffer sqlTj = new StringBuffer();
			sqlTj.append("update cw_xzb set shzt='1' where 1=1 ");
			if(Validate.noNull(gzyf)) {
				sqlTj.append(" and gzyf='" + gzyf + "' ");
			}
			if(Validate.noNull(rybh)) {
				sqlTj.append(" and rybh='"+rybh+"' ");
			}
			if(Validate.noNull(bm)) {
				sqlTj.append(" and bm = '"+bm+"' ");
			}
			result = db.update(sqlTj.toString());
		} else {
			
			final Object[] params = guid.split(",");
			String id = CommonUtils.getInsql(guid);
			if(Validate.isNull(id)) {
				sql = " update cw_xzb set shzt='1'";
			}else {
				sql = " update cw_xzb set shzt='1' where guid  " + id;
			}
			result = db.update(sql, params);
		}
		return result;
	}

	/**
	 * 离职薪资提交
	 */
	public int doLzTj(String guid, String gzyf) {
		String sql = new String();
		int result = 0;
		if (Validate.isNull(guid)) {
			sql = " update cw_lzxzb set shzt='1' where gzyf='" + gzyf + "' ";
			result = db.update(sql);
		} else {
			final Object[] params = guid.split(",");
			String id = CommonUtils.getInsql(guid);
			sql = " update cw_lzxzb set shzt='1' where guid  " + id;
			result = db.update(sql, params);
		}
		return result;
	}

	/**
	 * 在职薪资--复制上月工资
	 * 
	 * @return
	 */
	public int doFzsygz(String gzyf) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_xzb ( shzt,");
		sql.append(" rybh,xm,bm,rylb,rylx,gwgz,xjgz,xzft,wybt,dszf, ");
		sql.append(" jcjx,jljx1,bsbt,gwbt,bxqzbbt,sybt,jhbt,zjbt,htbt,qtbt, ");
		sql.append(" bgz,jkf,fzgzl,zsjl,fdyyjzbbt,khj,dhf,bt,zfbt,yfhj,  ");
		sql.append(" jiangkef,bgzks,jsx,qnjsx,qnjsx1,qnjsx2,qnjsx3,bjcxjxgz2014jsjs,bxbt2014n1d10yjsjs,jsjs,  ");
		sql.append(" zfjj,pgjj,ylbx,bgjj,dks,bnse,snse,bs,fz,syj, ");
		sql.append(" nqf,nqf1,wyf,sbj,sjdck,kk,ylj,jk,axyrj,kkhj,  ");
		sql.append(" sfhj,gzyf,bh,guid,  ");
		sql.append(" xh,jtf,nzj,nzjdks,gzdks,kshj,gh,sfzb,bkylbx,bksyj, ");
		sql.append(" bkylj,bksbj,sfdy,rdqk,dwbh)  (  ");
		sql.append(" select 0,");
		sql.append(" rybh,xm,bm,rylb,rylx,gwgz,xjgz,xzft,wybt,dszf, ");
		sql.append(" jcjx,jljx1,bsbt,gwbt,bxqzbbt,sybt,jhbt,zjbt,htbt,qtbt, ");
		sql.append(" bgz,jkf,fzgzl,zsjl,fdyyjzbbt,khj,dhf,bt,zfbt,yfhj,  ");
		sql.append(" jiangkef,bgzks,jsx,qnjsx,qnjsx1,qnjsx2,qnjsx3,bjcxjxgz2014jsjs,bxbt2014n1d10yjsjs,jsjs,  ");
		sql.append(" zfjj,pgjj,ylbx,bgjj,dks,bnse,snse,bs,fz,syj, ");
		sql.append(" nqf,nqf1,wyf,sbj,sjdck,kk,ylj,jk,axyrj,kkhj,  ");
		sql.append(" sfhj,'" + gzyf + "',bh,sys_guid(),  ");
		sql.append(" xh,jtf,nzj,nzjdks,gzdks,kshj,gh,sfzb,bkylbx,bksyj, ");
		sql.append(" bkylj,bksbj,sfdy,rdqk,dwbh ");
		sql.append(" from cw_xzb b where b.gzyf=to_char(add_months(to_date('"
				+ gzyf + "','yyyy.mm'),-1 ),'yyyy.mm') ");
		sql.append(" ) ");
		return db.update(sql.toString());
	}
	/**
	 * 在职薪资--查询上月离职人员
	 * 
	 * @return
	 */
	 public List getLzry(String gzyf) {
		 StringBuffer sql = new StringBuffer();
		 sql.append(" select '('||t.gh||')'||t.xm as ry from cw_xzb t where t.gh in ");
		 sql.append(" (select c.xh from CW_JZGXXB c where c.zzjzglx in(4,5)) and ");
		 sql.append(" t.gzyf=to_char(add_months(to_date('"+ gzyf + "','yyyy.mm'),-1 ),'yyyy.mm') ");
		 List list = new ArrayList();
		 list = db.queryForList(sql.toString());
		 return list;
	 }

	//查询当前月份是否有 rybh 的信息
		public List getcount(String rybh,String txry,String gzyf) {
//		    String gzyf= new SimpleDateFormat("yyyy.MM").format(new Date());
		    String sql="";
		    if(Validate.isNull(txry)) {
		    	 sql=" select * from cw_xzb b where b.rybh='"+rybh+"' and b.gzyf='"+gzyf+"' ";
		    }else {
		    	 sql=" select * from CW_LZXZB b where b.rybh='"+rybh+"' and b.gzyf='"+gzyf+"' ";
		    }
				System.err.println("添加验证重复 sql=> "+sql);
		    return db.queryForList(sql);
		}
	public boolean getbooGzny(String gzyf, String type) {
		String sql = "";
		if ("zzry".equals(type)) {
			sql = " select * from cw_xzb b where b.gzyf='" + gzyf + "' ";
		} else if ("txry".equals(type)) {
			sql = " select * from CW_LZXZB b where b.gzyf='" + gzyf + "' ";
		}
		List list = db.queryForList(sql);
		if (list.size() > 0) {
			return false;
		} else {
			return true;
		}

	}

	// 添加人员信息
	public int addryxx(String rybh, String txry, String gzyf, String xl,String dwbh) {
		// String gzyf= new SimpleDateFormat("yyyy-MM").format(new Date());
		String sql = "";
		if (Validate.isNull(txry)) {
			sql = "insert into cw_xzb (guid,rybh,xm,gzyf,bm,xl,dwbh) values (sys_guid(),'"
					+ rybh
					+ "',(select xm from gx_sys_ryb where rybh='"
					+ rybh
					+ "'),to_char('"
					+ gzyf
					+ "'),(select dw.mc from gx_sys_dwb dw left join gx_sys_ryb ry on ry.dwbh=dw.dwbh where ry.rybh='"
					+ rybh + "' ),'" + xl + "','"+dwbh+"') ";
		} else {
			sql = "insert into CW_LZXZB (guid,rybh,xm,gzyf,bm,dwbh) values (sys_guid(),'"
					+ rybh
					+ "',(select xm from gx_sys_ryb where rybh='"
					+ rybh
					+ "'),to_char('"
					+ gzyf
					+ "'),(select dw.mc from gx_sys_dwb dw left join gx_sys_ryb ry on ry.dwbh=dw.dwbh where ry.rybh='"
					+ rybh + "' ),'"+dwbh+"') ";
			System.err.println("添加sql=" + sql);
		}
		return db.update(sql);
	}

	/**
	 * 离职薪资--复制上月工资
	 * 
	 * @return
	 */
	public int doFzsygzLz(String gzyf) {
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into cw_lzxzb ( ");
		sql.append(" guid,rybh,xm,bm,rylb,jbgz,zjltf,yzwbt,gwbt,xzft, ");
		sql.append(" hzbt,txtgbf,shbt,xzbt,wjbt,txhl,jhbt,qtbt,ltbt,yzbt, ");
		sql.append(" jcjx,jtf,bt,zfbt,bgz,wybt,jkf,gjf,dhf,yfhj, ");
		sql.append(" fz,sbj,nqf,nqf1,wyf,jk,ylj,bgjj,bs,sjdck, ");
		sql.append(" syj,kkhj,sfhj,gzyf,bh,jsx,zfjj,nzj,kk,dwbh ");
		sql.append(" ) ( ");
		sql.append(" select  ");
		sql.append(" sys_guid(),rybh,xm,bm,rylb,jbgz,zjltf,yzwbt,gwbt,xzft, ");
		sql.append(" hzbt,txtgbf,shbt,xzbt,wjbt,txhl,jhbt,qtbt,ltbt,yzbt, ");
		sql.append(" jcjx,jtf,bt,zfbt,bgz,wybt,jkf,gjf,dhf,yfhj, ");
		sql.append(" fz,sbj,nqf,nqf1,wyf,jk,ylj,bgjj,bs,sjdck, ");
		sql.append(" syj,kkhj,sfhj,'" + gzyf + "',bh,jsx,zfjj,nzj,kk,dwbh ");
		sql.append(" from cw_lzxzb b where b.gzyf=to_char(add_months(to_date('"
				+ gzyf + "','yyyy.mm'),-1 ),'yyyy.mm') ");
		// sql.append(" from cw_lzxzb b where b.gzyf='"+gzyf+"' ");
		sql.append(" ) ");
		return db.update(sql.toString());
	}

	public String getLastMon() {
		return db.queryForSingleValue("select (case (select count(*) from cw_xzb where shzt='2') when 0 then to_char(sysdate,'yyyy.mm') else (select max(gzyf) from cw_xzb where shzt='2') end) from dual ");
	}

	public String getLastMonLz() {
		return db.queryForSingleValue("select (case (select count(*) from cw_lzxzb where shzt='2') when 0 then to_char(sysdate,'yyyy.mm') else (select max(gzyf) from cw_lzxzb where shzt='2') end) from dual");
	}

	/**
	 * 获取发放月份
	 */
	public Map<String, Object> getffyf() {
		String sql = "select to_char(to_date(b.ffyf,'yyyy.MM'),'yyyy.MM')as ffyf,b.nsjs,b.nzjbl from cw_gzcsb b";
		return db.queryForMap(sql);
	}

	/**
	 * 删除
	 */
	public int doDelete(String guid) {
		String sql = new String();
		int result = 0;
		final Object[] params = guid.split(",");
		String id = CommonUtils.getInsql(guid);
		sql = " delete from cw_xzb  where guid  " + id;
		result = db.update(sql, params);
		return result;
	}

	/**
	 * 删除
	 */
	public int doDelete_txry(String guid) {
		String sql = new String();
		int result = 0;
		final Object[] params = guid.split(",");
		String id = CommonUtils.getInsql(guid);
		sql = " delete from CW_LZXZB  where guid  " + id;
		result = db.update(sql, params);
		return result;
	}

	/**
	 * 在职薪资数据导出
	 * 
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getListByid(String guid, PageData pd) {
		String rybh = pd.getString("rybh");
		String ryxm = pd.getString("ryxm");
		String shzt = pd.getString("shzt");
		String gzyf = pd.getString("gzyf");
		String xm   =pd.getString("xm");
		String bm   =pd.getString("bm");
		StringBuffer sql = new StringBuffer();
		sql.append("select   ");
		sql.append(" T.GUID,T.RYBH,T.XM,T.BM,T.RYLB,T.RYLX,DECODE(T.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回') AS SHZT, " +
				"TO_CHAR(T.GWGZ,'FM9999990.00') AS GWGZ,TO_CHAR(T.XJGZ,'FM9999990.00') AS XJGZ,TO_CHAR(T.XZFT,'FM9999990.00') AS XZFT," +
				"TO_CHAR(T.WYBT,'FM9999990.00') AS WYBT,TO_CHAR(T.DSZF,'FM9999990.00') AS DSZF,TO_CHAR(T.JCJX,'FM9999990.00') AS JCJX," +
				"TO_CHAR(T.JLJX1,'FM9999990.00') AS JLJX1,TO_CHAR(T.BSBT,'FM9999990.00') AS BSBT," +
				"TO_CHAR(T.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(T.BXQZBBT,'FM9999990.00') AS BXQZBBT,TO_CHAR(T.SYBT,'FM9999990.00') AS SYBT," +
				"TO_CHAR(T.JHBT,'FM9999990.00') AS JHBT,TO_CHAR(T.ZJBT,'FM9999990.00') AS ZJBT,TO_CHAR(T.HTBT,'FM9999990.00') AS HTBT," +
				"TO_CHAR(T.QTBT,'FM9999990.00') AS QTBT," +
				"TO_CHAR(T.BGZ,'FM9999990.00') AS BGZ,TO_CHAR(T.JKF,'FM9999990.00') AS JKF,TO_CHAR(T.FZGZL,'FM9999990.00') AS FZGZL," +
				"TO_CHAR(T.ZSJL,'FM9999990.00') AS ZSJL,TO_CHAR(T.FDYYJZBBT,'FM9999990.00') AS FDYYJZBBT,TO_CHAR(T.KHJ,'FM9999990.00') AS KHJ," +
				"TO_CHAR(T.DHF,'FM9999990.00') AS DHF,TO_CHAR(T.BT,'FM9999990.00') AS BT,TO_CHAR(T.ZFBT,'FM9999990.00') AS ZFBT," +
				"TO_CHAR(T.YFHJ,'FM999999.00') AS YFHJ," +
				"TO_CHAR(T.JIANGKEF,'FM9999990.00') AS JIANGKEF,TO_CHAR(T.BGZKS,'FM9999990.00') AS BGZKS,TO_CHAR(T.JSX,'FM9999990.00') AS JSX," +
				"TO_CHAR(T.QNJSX,'FM9999990.00') AS QNJSX,TO_CHAR(T.QNJSX1,'FM9999990.00') AS QNJSX1,TO_CHAR(T.QNJSX2,'FM9999990.00') AS QNJSX2," +
				"TO_CHAR(T.QNJSX3,'FM9999990.00') AS QNJSX3,TO_CHAR(T.BJCXJXGZ2014JSJS,'FM9999990.00') AS BJCXJXGZ2014JSJS,TO_CHAR(T.BXBT2014N1D10YJSJS,'FM9999990.00') AS BXBT2014N1D10YJSJS," +
				"TO_CHAR(T.JSJS,'FM9999990.00') AS JSJS," +
				"TO_CHAR(T.ZFJJ,'FM9999990.00') AS ZFJJ,TO_CHAR(T.PGJJ,'FM9999990.00') AS PGJJ,TO_CHAR(T.YLBX,'FM9999990.00') AS YLBX," +
				"TO_CHAR(T.BGJJ,'FM9999990.00') AS BGJJ,TO_CHAR(T.DKS,'FM9999990.00') AS DKS,TO_CHAR(T.BNSE,'FM9999990.00') AS BNSE," +
				"TO_CHAR(T.SNSE,'FM9999990.00') AS SNSE,TO_CHAR(T.BS,'FM9999990.00') AS BS,TO_CHAR(T.FZ,'FM9999990.00') AS FZ," +
				"TO_CHAR(T.SYJ,'FM9999990.00') AS SYJ," +
				"TO_CHAR(T.NQF,'FM9999990.00') AS NQF,TO_CHAR(T.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(T.WYF,'FM9999990.00') AS WYF," +
				"TO_CHAR(T.SBJ,'FM9999990.00') AS SBJ,TO_CHAR(T.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(T.KK,'FM9999990.00') AS KK," +
				"TO_CHAR(T.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(T.JK,'FM9999990.00') AS JK,TO_CHAR(T.AXYRJ,'FM9999990.00') AS AXYRJ," +
				"TO_CHAR(T.KKHJ,'FM9999990.00') AS KKHJ," +
				"TO_CHAR(T.SFHJ,'FM9999990.00') AS SFHJ,T.GZYF AS GZYF,T.BH," +
				"T.XH,TO_CHAR(T.JTF,'FM9999990.00') AS JTF,TO_CHAR(T.NZJ,'FM9999990.00') AS NZJ," +
				"TO_CHAR(T.NZJDKS,'FM9999990.00') AS NZJDKS,TO_CHAR(T.GZDKS,'FM9999990.00') AS GZDKS,TO_CHAR(T.KSHJ,'FM9999990.00') AS KSHJ," +
				"T.GH,T.SFZB,TO_CHAR(T.BKYLBX,'FM9999990.00') AS BKYLBX," +
				"TO_CHAR(T.BKSYJ,'FM9999990.00') AS BKSYJ,TO_CHAR(T.BKYLJ,'FM9999990.00') AS BKYLJ,TO_CHAR(T.BKSBJ,'FM9999990.00') AS BKSBJ," +
				"T.SFDY,T.RDQK,T.XL ");
		sql.append(" FROM  ");
		sql.append("(select  T.GUID, T.RYBH, T.XM,T.BM,T.RYLB,T.RYLX,t.shzt as shzt,T.GWGZ  GWGZ,T.XJGZ  XJGZ,T.XZFT XZFT,T.WYBT WYBT,T.DSZF  DSZF,T.JCJX  JCJX,T.JLJX1 JLJX1,T.BSBT BSBT,T.GWBT GWBT,T.BXQZBBT  BXQZBBT,T.SYBT  SYBT,T.JHBT  JHBT,T.ZJBT  ZJBT,T.HTBT  HTBT,T.QTBT  QTBT,T.BGZ  BGZ,T.JKF  JKF,T.FZGZL  FZGZL,T.ZSJL  ZSJL,T.FDYYJZBBT  FDYYJZBBT,T.KHJ  KHJ,T.DHF  DHF,T.BT  BT,T.ZFBT  ZFBT,T.YFHJ  YFHJ,T.JIANGKEF  JIANGKEF,T.BGZKS  BGZKS,T.JSX JSX,T.QNJSX  QNJSX,T.QNJSX1  QNJSX1,T.QNJSX2 QNJSX2,T.QNJSX3  QNJSX3,T.BJCXJXGZ2014JSJS  BJCXJXGZ2014JSJS,T.BXBT2014N1D10YJSJS  BXBT2014N1D10YJSJS,T.JSJS JSJS,T.ZFJJ  ZFJJ,T.PGJJ  PGJJ,T.YLBX  YLBX,T.BGJJ  BGJJ,T.DKS  DKS,T.BNSE  BNSE,T.SNSE  SNSE,T.BS BS,T.FZ  FZ,T.SYJ SYJ,T.NQF  NQF,T.NQF1  NQF1,T.WYF  WYF,T.SBJ  SBJ,T.SJDCK  SJDCK,T.KK  KK,T.YLJ YLJ,T.JK JK,T.AXYRJ  AXYRJ,T.KKHJ  KKHJ,T.SFHJ  SFHJ,T.GZYF  GZYF,T.BH,T.XH,T.JTF  JTF,T.NZJ  NZJ,T.NZJDKS  NZJDKS,T.GZDKS  GZDKS,T.KSHJ  KSHJ,T.GH,T.SFZB,T.BKYLBX  BKYLBX,T.BKSYJ  BKSYJ,T.BKYLJ  BKYLJ,T.BKSBJ  BKSBJ,T.SFDY,T.RDQK,T.XL from CW_XZB T");
		sql.append(" union ");
		sql.append(" select '1'  GUID, ''  RYBH, ''  XM, ''  BM,''  RYLB,''  RYLX,''  SHZT,sum(T.GWGZ)  GWGZ,sum(T.XJGZ)  XJGZ,sum(T.XZFT) XZFT,sum(T.WYBT)  WYBT,sum(T.DSZF)  DSZF,sum(T.JCJX)  JCJX,sum(T.JLJX1)  JLJX1,sum(T.BSBT)  BSBT,sum(T.GWBT)  GWBT,sum(T.BXQZBBT)  BXQZBBT,sum(T.SYBT)  SYBT,sum(T.JHBT)   JHBT,sum(T.ZJBT)  ZJBT,sum(T.HTBT)  HTBT,sum(T.QTBT)  QTBT,sum(T.BGZ)  BGZ,sum(T.JKF)  JKF,sum(T.FZGZL)  FZGZL,sum(T.ZSJL)  ZSJL,sum(T.FDYYJZBBT)  FDYYJZBBT,sum(T.KHJ)  KHJ,sum(T.DHF)  DHF,sum(T.BT)  BT,sum(T.ZFBT)  ZFBT,sum(T.YFHJ)  YFHJ,sum(T.JIANGKEF)  JIANGKEF,sum(T.BGZKS)  BGZKS,sum(T.JSX)  JSX,sum(T.QNJSX)  QNJSX,sum(T.QNJSX1) QNJSX1,sum(T.QNJSX2)  QNJSX2,sum(T.QNJSX3)  QNJSX3,sum(T.BJCXJXGZ2014JSJS)  BJCXJXGZ2014JSJS,sum(T.BXBT2014N1D10YJSJS)  BXBT2014N1D10YJSJS,sum(T.JSJS)  JSJS,sum(T.ZFJJ)  ZFJJ,sum(T.PGJJ)  PGJJ,sum(T.YLBX) YLBX,sum(T.BGJJ)  BGJJ,sum(T.DKS)  DKS,sum(T.BNSE)  BNSE,sum(T.SNSE) AS SNSE,sum(T.BS)  BS,sum(T.FZ) FZ,sum(T.SYJ)  SYJ,sum(T.NQF)  NQF,sum(T.NQF1)  NQF1,sum(T.WYF)  WYF,sum(T.SBJ)  SBJ,sum(T.SJDCK)  SJDCK,sum(T.KK)  KK,sum(T.YLJ)  YLJ,sum(T.JK)  JK,sum(T.AXYRJ)  AXYRJ,sum(T.KKHJ)  KKHJ,sum(T.SFHJ)  SFHJ,''  GZYF,''  BH,''  XH,sum(T.JTF)  JTF,sum(T.NZJ)  NZJ,sum(T.NZJDKS)  NZJDKS,sum(T.GZDKS)  GZDKS,sum(T.KSHJ)  KSHJ,''  GH,''  SFZB,sum(T.BKYLBX)  BKYLBX,sum(T.BKSYJ)  BKSYJ,sum(T.BKYLJ)  BKYLJ,sum(T.BKSBJ)  BKSBJ,''  SFDY,''  RDQK,''  XL  from CW_XZB T where 1=1 ");
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			sql.append(" and t.rybh in('"+rybh+"')");
		}
		if(Validate.noNull(shzt)){
			sql.append(" and t.shzt like '%"+shzt+"%'");
		}
		if(Validate.noNull(gzyf)){
			sql.append(" and t.gzyf like '%"+gzyf+"%' ");
		}
		if(Validate.noNull(xm)){
			sql.append(" and t.xm like '%"+xm+"%'");
		}
		if(Validate.noNull(bm)){
			sql.append(" and t.bm like '%"+CommonUtil.getEndText(bm)+"%' ");
		}
		if(Validate.noNull(guid)){
			sql.append(" and t.guid in ('" + guid.trim() + "') ");
		}
		sql.append(" ) T ");
		if (Validate.noNull(guid)) {
			sql.append("WHERE (T.GUID in ('" + guid.trim() + "') or T.guid='1') order by rybh asc ");
		} else {
			String strWhere = " where 1=1";
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				strWhere += " and (t.rybh in('"+rybh+"') or t.rybh is null)";
			}
			if(Validate.noNull(shzt)){
				strWhere += " and (t.shzt like '%"+shzt+"%' or t.shzt is null)";
			}
			if(Validate.noNull(gzyf)){
				strWhere += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null)";
			}
			if(Validate.noNull(xm)){
				strWhere += " and (t.xm like '%"+xm+"%' or t.xm is null) ";
			}
			if(Validate.noNull(bm)){
				strWhere += " and (t.bm like '%"+CommonUtil.getEndText(bm)+"%' or t.bm is null )";
			}
			strWhere +=" order by rybh asc ";
			sql.append(strWhere);
//			sql.append("WHERE T.RYBH LIKE '%" + rybh + "%'  AND  T.XM LIKE '%"
//					+ ryxm + "%' AND T.SHZT LIKE '%" + shzt
//					+ "%' AND T.GZYF LIKE '%" + gzyf + "%' ");
		}
		return db.queryForList(sql.toString());
	}
	/**
	 * 在职薪资数据导出
	 * 
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getListByidnew(String guid, PageData pd) {
		String rybh = pd.getString("rybh");
		String ryxm = pd.getString("ryxm");
		String shzt = pd.getString("shzt");
		String gzyf = pd.getString("gzyf");
		String xm   =pd.getString("xm");
		String bm   =pd.getString("bm");
		StringBuffer sql = new StringBuffer();
		sql.append("select   ");
		sql.append("   distinct T.RYBH,T.XM,'' as BM,'' as RYLB,'' as RYLX,'' AS SHZT, " +
				" '' AS GWGZ,'' AS XJGZ,'' AS XZFT," +
				" '' AS WYBT,'' AS DSZF,'' AS JCJX," +
				" '' AS JLJX1,'' AS BSBT," +
				" '' AS GWBT,'' AS BXQZBBT,'' AS SYBT," +
				" '' AS JHBT,'' AS ZJBT,'' AS HTBT," +
				" '' AS QTBT," +
				" '' AS BGZ,'' AS JKF,'' AS FZGZL," +
				" '' AS ZSJL,'' AS FDYYJZBBT,'' AS KHJ," +
				" '' AS DHF,'' AS BT,'' AS ZFBT," +
				" '' AS YFHJ," +
				"  '' AS JIANGKEF,'' AS BGZKS,'' AS JSX," +
				" '' AS QNJSX,'' AS QNJSX1,'' AS QNJSX2," +
				" '' AS QNJSX3,'' AS BJCXJXGZ2014JSJS,'' AS BXBT2014N1D10YJSJS," +
				" '' AS JSJS," +
				" '' AS ZFJJ,'' AS PGJJ,'' AS YLBX," +
				" '' AS BGJJ,'' AS DKS,'' AS BNSE," +
				" '' AS SNSE,'' AS BS,'' AS FZ," +
				" '' AS SYJ," +
				" '' AS NQF,'' AS NQF1,'' AS WYF," +
				" '' AS SBJ,'' AS SJDCK,'' AS KK," +
				" '' AS YLJ,'' AS JK,'' AS AXYRJ," +
				" '' AS KKHJ," +
				" '' AS SFHJ,'' AS GZYF, '' BH," +
				" '' XH,'' AS JTF,'' AS NZJ," +
				" '' AS NZJDKS,'' AS GZDKS,'' AS KSHJ," +
				" '' GH,'' SFZB,'' AS BKYLBX," +
				"  '' AS BKSYJ,'' AS BKYLJ,'' AS BKSBJ," +
				" '' SFDY,'' RDQK,'' XL ");
		sql.append(" FROM  ");
		sql.append("(select  T.GUID, T.RYBH, T.XM,T.BM,T.RYLB,T.RYLX,t.shzt as shzt,T.GWGZ  GWGZ,T.XJGZ  XJGZ,T.XZFT XZFT,T.WYBT WYBT,T.DSZF  DSZF,T.JCJX  JCJX,T.JLJX1 JLJX1,T.BSBT BSBT,T.GWBT GWBT,T.BXQZBBT  BXQZBBT,T.SYBT  SYBT,T.JHBT  JHBT,T.ZJBT  ZJBT,T.HTBT  HTBT,T.QTBT  QTBT,T.BGZ  BGZ,T.JKF  JKF,T.FZGZL  FZGZL,T.ZSJL  ZSJL,T.FDYYJZBBT  FDYYJZBBT,T.KHJ  KHJ,T.DHF  DHF,T.BT  BT,T.ZFBT  ZFBT,T.YFHJ  YFHJ,T.JIANGKEF  JIANGKEF,T.BGZKS  BGZKS,T.JSX JSX,T.QNJSX  QNJSX,T.QNJSX1  QNJSX1,T.QNJSX2 QNJSX2,T.QNJSX3  QNJSX3,T.BJCXJXGZ2014JSJS  BJCXJXGZ2014JSJS,T.BXBT2014N1D10YJSJS  BXBT2014N1D10YJSJS,T.JSJS JSJS,T.ZFJJ  ZFJJ,T.PGJJ  PGJJ,T.YLBX  YLBX,T.BGJJ  BGJJ,T.DKS  DKS,T.BNSE  BNSE,T.SNSE  SNSE,T.BS BS,T.FZ  FZ,T.SYJ SYJ,T.NQF  NQF,T.NQF1  NQF1,T.WYF  WYF,T.SBJ  SBJ,T.SJDCK  SJDCK,T.KK  KK,T.YLJ YLJ,T.JK JK,T.AXYRJ  AXYRJ,T.KKHJ  KKHJ,T.SFHJ  SFHJ,T.GZYF  GZYF,T.BH,T.XH,T.JTF  JTF,T.NZJ  NZJ,T.NZJDKS  NZJDKS,T.GZDKS  GZDKS,T.KSHJ  KSHJ,T.GH,T.SFZB,T.BKYLBX  BKYLBX,T.BKSYJ  BKSYJ,T.BKYLJ  BKYLJ,T.BKSBJ  BKSBJ,T.SFDY,T.RDQK,T.XL from CW_XZB T where  t.gzyf=to_char(add_months(to_date(to_char(sysdate,'yyyy.mm'),'yyyy.mm'),-1 ),'yyyy.mm') ");
		sql.append(" union ");
		sql.append(" select '1'  GUID, ''  RYBH, ''  XM, ''  BM,''  RYLB,''  RYLX,''  SHZT,sum(T.GWGZ)  GWGZ,sum(T.XJGZ)  XJGZ,sum(T.XZFT) XZFT,sum(T.WYBT)  WYBT,sum(T.DSZF)  DSZF,sum(T.JCJX)  JCJX,sum(T.JLJX1)  JLJX1,sum(T.BSBT)  BSBT,sum(T.GWBT)  GWBT,sum(T.BXQZBBT)  BXQZBBT,sum(T.SYBT)  SYBT,sum(T.JHBT)   JHBT,sum(T.ZJBT)  ZJBT,sum(T.HTBT)  HTBT,sum(T.QTBT)  QTBT,sum(T.BGZ)  BGZ,sum(T.JKF)  JKF,sum(T.FZGZL)  FZGZL,sum(T.ZSJL)  ZSJL,sum(T.FDYYJZBBT)  FDYYJZBBT,sum(T.KHJ)  KHJ,sum(T.DHF)  DHF,sum(T.BT)  BT,sum(T.ZFBT)  ZFBT,sum(T.YFHJ)  YFHJ,sum(T.JIANGKEF)  JIANGKEF,sum(T.BGZKS)  BGZKS,sum(T.JSX)  JSX,sum(T.QNJSX)  QNJSX,sum(T.QNJSX1) QNJSX1,sum(T.QNJSX2)  QNJSX2,sum(T.QNJSX3)  QNJSX3,sum(T.BJCXJXGZ2014JSJS)  BJCXJXGZ2014JSJS,sum(T.BXBT2014N1D10YJSJS)  BXBT2014N1D10YJSJS,sum(T.JSJS)  JSJS,sum(T.ZFJJ)  ZFJJ,sum(T.PGJJ)  PGJJ,sum(T.YLBX) YLBX,sum(T.BGJJ)  BGJJ,sum(T.DKS)  DKS,sum(T.BNSE)  BNSE,sum(T.SNSE) AS SNSE,sum(T.BS)  BS,sum(T.FZ) FZ,sum(T.SYJ)  SYJ,sum(T.NQF)  NQF,sum(T.NQF1)  NQF1,sum(T.WYF)  WYF,sum(T.SBJ)  SBJ,sum(T.SJDCK)  SJDCK,sum(T.KK)  KK,sum(T.YLJ)  YLJ,sum(T.JK)  JK,sum(T.AXYRJ)  AXYRJ,sum(T.KKHJ)  KKHJ,sum(T.SFHJ)  SFHJ,''  GZYF,''  BH,''  XH,sum(T.JTF)  JTF,sum(T.NZJ)  NZJ,sum(T.NZJDKS)  NZJDKS,sum(T.GZDKS)  GZDKS,sum(T.KSHJ)  KSHJ,''  GH,''  SFZB,sum(T.BKYLBX)  BKYLBX,sum(T.BKSYJ)  BKSYJ,sum(T.BKYLJ)  BKYLJ,sum(T.BKSBJ)  BKSBJ,''  SFDY,''  RDQK,''  XL  from CW_XZB T where 1=1 ");
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			sql.append(" and t.rybh in('"+rybh+"')");
		}
		if(Validate.noNull(shzt)){
			sql.append(" and t.shzt like '%"+shzt+"%'");
		}
		if(Validate.noNull(gzyf)){
			sql.append(" and t.gzyf like '%"+gzyf+"%' ");
		}
		if(Validate.noNull(xm)){
			sql.append(" and t.xm like '%"+xm+"%'");
		}
		if(Validate.noNull(bm)){
			sql.append(" and t.bm like '%"+CommonUtil.getEndText(bm)+"%' ");
		}
		if(Validate.noNull(guid)){
			sql.append(" and t.guid in ('" + guid.trim() + "') ");
		}
		sql.append(" ) T ");
		if (Validate.noNull(guid)) {
			sql.append("WHERE (T.GUID in ('" + guid.trim() + "') or T.guid='1') order by rybh asc ");
		} else {
			String strWhere = " where 1=1  ";
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				strWhere += " and (t.rybh in('"+rybh+"') or t.rybh is null)";
			}
			if(Validate.noNull(shzt)){
				strWhere += " and (t.shzt like '%"+shzt+"%' or t.shzt is null)";
			}
			if(Validate.noNull(gzyf)){
				strWhere += " and (t.gzyf like '%"+gzyf+"%' or t.gzyf is null)";
			}
			if(Validate.noNull(xm)){
				strWhere += " and (t.xm like '%"+xm+"%' or t.xm is null) ";
			}
			if(Validate.noNull(bm)){
				strWhere += " and (t.bm like '%"+CommonUtil.getEndText(bm)+"%' or t.bm is null )";
			}
			strWhere +=" and rybh is not null  order by rybh asc ";
			sql.append(strWhere);
//			sql.append("WHERE T.RYBH LIKE '%" + rybh + "%'  AND  T.XM LIKE '%"
//					+ ryxm + "%' AND T.SHZT LIKE '%" + shzt
//					+ "%' AND T.GZYF LIKE '%" + gzyf + "%' ");
		}
		return db.queryForList(sql.toString());
	}

	/**
	 * 离职薪资数据导出
	 * 
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getLzListByid(String guid, PageData pd) {
//		String rybh = pd.getString("rybh");
//		String ryxm = pd.getString("ryxm");
//		String shzt = pd.getString("shzt");
//		String gzyf = pd.getString("gzyf");
		StringBuffer sql = new StringBuffer();
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		sql.append("select   ");
		sql.append("L.GUID,L.RYBH,L.XM,L.BM,L.RYLB,DECODE(L.SHZT,0,'未提交',1,'待审核',2,'审核通过',3,'退回','合计') AS SHZT," +
				"TO_CHAR(L.JBGZ,'FM9999990.00') AS JBGZ,TO_CHAR(L.ZJLTF,'FM9999990.00') AS ZJLTF,TO_CHAR(L.YZWBT,'FM9999990.00') AS YZWBT," +
				"TO_CHAR(L.GWBT,'FM9999990.00') AS GWBT,TO_CHAR(L.XZFT,'FM9999990.00') AS XZFT,TO_CHAR(L.HZBT,'FM9999990.00') AS HZBT," +
				"TO_CHAR(L.TXTGBF,'FM9999990.00') AS TXTGBF,TO_CHAR(L.SHBT,'FM9999990.00') AS SHBT,TO_CHAR(L.XZBT,'FM9999990.00') AS XZBT," +
				"TO_CHAR(L.WJBT,'FM9999990.00') AS WJBT,TO_CHAR(L.TXHL,'FM9999990.00') AS TXHL,TO_CHAR(L.JHBT,'FM9999990.00') AS JHBT," +
				"TO_CHAR(L.QTBT,'FM9999990.00') AS QTBT,TO_CHAR(L.LTBT,'FM9999990.00') AS LTBT,TO_CHAR(L.YZBT,'FM9999990.00') AS YZBT," +
				"TO_CHAR(L.JCJX,'FM9999990.00') AS JCJX,TO_CHAR(L.JTF,'FM9999990.00') AS JTF,TO_CHAR(L.BT,'FM9999990.00') AS BT," +
				"TO_CHAR(L.ZFBT,'FM9999990.00') AS ZFBT,TO_CHAR(L.BGZ,'FM9999990.00') AS BGZ," +
				"TO_CHAR(L.WYBT,'FM9999990.00') AS WYBT," +
				"TO_CHAR(L.JKF,'FM9999990.00') AS JKF,TO_CHAR(L.GJF,'FM9999990.00') AS GJF,TO_CHAR(L.DHF,'FM9999990.00') AS DHF," +
				"TO_CHAR(L.YFHJ,'FM9999990.00') AS YFHJ,TO_CHAR(L.FZ,'FM9999990.00') AS FZ,TO_CHAR(L.SBJ,'FM9999990.00') AS SBJ," +
				"TO_CHAR(L.NQF,'FM9999990.00') AS NQF,TO_CHAR(L.NQF1,'FM9999990.00') AS NQF1,TO_CHAR(L.WYF,'FM9999990.00') AS WYF," +
				"TO_CHAR(L.JK,'FM9999990.00') AS JK,TO_CHAR(L.YLJ,'FM9999990.00') AS YLJ,TO_CHAR(L.BGJJ,'FM9999990.00') AS BGJJ," +
				"TO_CHAR(L.BS,'FM9999990.00') AS BS,TO_CHAR(L.SJDCK,'FM9999990.00') AS SJDCK,TO_CHAR(L.SYJ,'FM9999990.00') AS SYJ," +
				"TO_CHAR(L.KKHJ,'FM9999990.00') AS KKHJ,TO_CHAR(L.SFHJ,'FM9999990.00') AS SFHJ,L.GZYF," +
				"L.BH,TO_CHAR(L.JSX,'FM9999990.00') AS JSX,TO_CHAR(L.ZFJJ,'FM9999990.00') AS ZFJJ," +
				"TO_CHAR(L.NZJ,'FM9999990.00') AS NZJ,TO_CHAR(L.KK,'FM9999990.00') AS KK");
		sql.append(" FROM  ");
		sql.append("( select L.GUID, L.RYBH,L.XM,L.BM,L.RYLB,L.SHZT AS SHZT,TO_CHAR(L.JBGZ, 'FM999999990.00') AS JBGZ,TO_CHAR(L.ZJLTF, 'FM999999990.00') AS ZJLTF,TO_CHAR(L.YZWBT, 'FM999999990.00') AS YZWBT,TO_CHAR(L.GWBT, 'FM999999990.00') AS GWBT,TO_CHAR(L.XZFT, 'FM999999990.00') AS XZFT,TO_CHAR(L.HZBT, 'FM999999990.00') AS HZBT,TO_CHAR(L.TXTGBF, 'FM9999990.00') AS TXTGBF,TO_CHAR(L.SHBT, 'FM9999990.00') AS SHBT,TO_CHAR(L.XZBT, 'FM999999990.00') AS XZBT,TO_CHAR(L.WJBT, 'FM999999990.00') AS WJBT,TO_CHAR(L.TXHL, 'FM999999990.00') AS TXHL,TO_CHAR(L.JHBT, 'FM999999990.00') AS JHBT,TO_CHAR(L.QTBT, 'FM999999990.00') AS QTBT,TO_CHAR(L.LTBT, 'FM999999990.00') AS LTBT,TO_CHAR(L.YZBT, 'FM999999990.00') AS YZBT,TO_CHAR(L.JCJX, 'FM999999990.00') AS JCJX,TO_CHAR(L.JTF, 'FM999999990.00') AS JTF,TO_CHAR(L.BT, 'FM999999990.00') AS BT,TO_CHAR(L.ZFBT, 'FM999999990.00') AS ZFBT,TO_CHAR(L.BGZ, 'FM999999990.00') AS BGZ,TO_CHAR(L.WYBT, 'FM999999990.00') AS WYBT,TO_CHAR(L.JKF, 'FM999999990.00') AS JKF,TO_CHAR(L.GJF, 'FM999999990.00') AS GJF,TO_CHAR(L.DHF, 'FM999999990.00') AS DHF,TO_CHAR(L.YFHJ, 'FM999999990.00') AS YFHJ,TO_CHAR(L.FZ, 'FM999999990.00') AS FZ,TO_CHAR(L.SBJ, 'FM999999990.00') AS SBJ,TO_CHAR(L.NQF, 'FM999999990.00') AS NQF,TO_CHAR(L.NQF1, 'FM999999990.00') AS NQF1,TO_CHAR(L.WYF, 'FM999999990.00') AS WYF,TO_CHAR(L.JK, 'FM999999990.00') AS JK,TO_CHAR(L.YLJ, 'FM999999990.00') AS YLJ,TO_CHAR(L.BGJJ, 'FM999999990.00') AS BGJJ,TO_CHAR(L.BS, 'FM999999990.00') AS BS,TO_CHAR(L.SJDCK, 'FM999999990.00') AS SJDCK,TO_CHAR(L.SYJ, 'FM999999990.00') AS SYJ,TO_CHAR(L.KKHJ, 'FM999999990.00') AS KKHJ,TO_CHAR(L.SFHJ, 'FM999999990.00') AS SFHJ,L.GZYF,L.BH,TO_CHAR(L.JSX, 'FM999999990.00') AS JSX,TO_CHAR(L.ZFJJ, 'FM999999990.00') AS ZFJJ,TO_CHAR(L.NZJ, 'FM999999990.00') AS NZJ,TO_CHAR(L.KK, 'FM999999990.00') AS KK from CW_LZXZB L ");
		sql.append(" union ");
		sql.append(" select '1' as GUID,'' as RYBH,'' as XM,'' as BM,'' as RYLB,'' AS SHZT,TO_CHAR(sum(L.JBGZ), 'FM999999990.00') AS JBGZ,TO_CHAR(sum(L.ZJLTF), 'FM999999990.00') AS ZJLTF,TO_CHAR(sum(L.YZWBT), 'FM999999990.00') AS YZWBT,TO_CHAR(sum(L.GWBT), 'FM999999990.00') AS GWBT,TO_CHAR(sum(L.XZFT), 'FM999999990.00') AS XZFT,TO_CHAR(sum(L.HZBT), 'FM999999990.00') AS HZBT,TO_CHAR(sum(L.TXTGBF), 'FM9999990.00') AS TXTGBF,TO_CHAR(sum(L.SHBT), 'FM9999990.00') AS SHBT,TO_CHAR(sum(L.XZBT), 'FM999999990.00') AS XZBT,TO_CHAR(sum(L.WJBT), 'FM999999990.00') AS WJBT,TO_CHAR(sum(L.TXHL), 'FM999999990.00') AS TXHL,TO_CHAR(sum(L.JHBT), 'FM999999990.00') AS JHBT,TO_CHAR(sum(L.QTBT), 'FM999999990.00') AS QTBT,TO_CHAR(sum(L.LTBT), 'FM999999990.00') AS LTBT,TO_CHAR(sum(L.YZBT), 'FM999999990.00') AS YZBT,TO_CHAR(sum(L.JCJX), 'FM999999990.00') AS JCJX,TO_CHAR(sum(L.JTF), 'FM999999990.00') AS JTF,TO_CHAR(sum(L.BT), 'FM999999990.00') AS BT,TO_CHAR(sum(L.ZFBT), 'FM999999990.00') AS ZFBT,TO_CHAR(sum(L.BGZ), 'FM999999990.00') AS BGZ,TO_CHAR(sum(L.WYBT), 'FM999999990.00') AS WYBT,TO_CHAR(sum(L.JKF), 'FM999999990.00') AS JKF,TO_CHAR(sum(L.GJF), 'FM999999990.00') AS GJF,TO_CHAR(sum(L.DHF), 'FM999999990.00') AS DHF,TO_CHAR(sum(L.YFHJ), 'FM999999990.00') AS YFHJ,TO_CHAR(sum(L.FZ), 'FM999999990.00') AS FZ,TO_CHAR(sum(L.SBJ), 'FM999999990.00') AS SBJ,TO_CHAR(sum(L.NQF), 'FM999999990.00') AS NQF,TO_CHAR(sum(L.NQF1), 'FM999999990.00') AS NQF1,TO_CHAR(sum(L.WYF), 'FM999999990.00') AS WYF,TO_CHAR(sum(L.JK), 'FM999999990.00') AS JK,TO_CHAR(sum(L.YLJ), 'FM999999990.00') AS YLJ,TO_CHAR(sum(L.BGJJ), 'FM999999990.00') AS BGJJ,TO_CHAR(sum(L.BS), 'FM999999990.00') AS BS,TO_CHAR(sum(L.SJDCK), 'FM999999990.00') AS SJDCK,TO_CHAR(sum(L.SYJ), 'FM999999990.00') AS SYJ,TO_CHAR(sum(L.KKHJ), 'FM999999990.00') AS KKHJ,TO_CHAR(sum(L.SFHJ), 'FM999999990.00') AS SFHJ,'' as GZYF,'' as BH,TO_CHAR(sum(L.JSX), 'FM999999990.00') AS JSX,TO_CHAR(sum(L.ZFJJ), 'FM999999990.00') AS ZFJJ,TO_CHAR(sum(L.NZJ), 'FM999999990.00') AS NZJ,TO_CHAR(sum(L.KK), 'FM999999990.00') AS KK from CW_LZXZB L where 1=1 ");
		
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			sql.append("and l.rybh in('"+rybh+"') ");
		}
		if(Validate.noNull(shzt)){
			sql.append(" and l.shzt like '%"+shzt+"%'  ");
		}
		if(Validate.noNull(gzyf)){
			sql.append("and l.gzyf like '%"+gzyf+"%' ");
		}
		if(Validate.noNull(guid)){
			sql.append(" and L.GUID in ('" + guid.trim() + "') ");
		}
		sql.append(") l ");
		if (Validate.noNull(guid)) {
			sql.append("WHERE (L.GUID in ('" + guid.trim() + "') or guid='1') order by rybh asc ");
		} else {

			String strWhere = " where 1=1 ";
			
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				strWhere += " and (l.rybh in('"+rybh+"') or l.rybh is null)";
			}
			if(Validate.noNull(shzt)){
				strWhere += " and (l.shzt like '%"+shzt+"%' or l.shzt is null)";
			}
			if(Validate.noNull(gzyf)){
				strWhere += " and (l.gzyf like '%"+gzyf+"%' or l.gzyf is null)";
			}
			strWhere +=" order by rybh asc ";
			sql.append(strWhere);
		}
		return db.queryForList(sql.toString());
	}
	/**
	 * 离职薪资数据导出
	 * 
	 * @param guid
	 * @return
	 */
	public List<Map<String, Object>> getLzListByidnew(String guid, PageData pd) {
//		String rybh = pd.getString("rybh");
//		String ryxm = pd.getString("ryxm");
//		String shzt = pd.getString("shzt");
//		String gzyf = pd.getString("gzyf");
		StringBuffer sql = new StringBuffer();
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		sql.append("select   ");
		sql.append("distinct L.RYBH,L.XM,'' as BM,'' as RYLB,'' AS SHZT," +
				"'' AS JBGZ,'' AS ZJLTF,'' AS YZWBT," +
				"'' AS GWBT,'' AS XZFT,'' AS HZBT," +
				"'' AS TXTGBF,'' AS SHBT,'' AS XZBT," +
				"'' AS WJBT,'' AS TXHL,'' AS JHBT," +
				"'' AS QTBT,'' AS LTBT,'' AS YZBT," +
				"'' AS JCJX,'' AS JTF,'' AS BT," +
				"'' AS ZFBT,'' AS BGZ," +
				"'' AS WYBT," +
				"'' AS JKF,'' AS GJF,'' AS DHF," +
				"'' AS YFHJ,'' AS FZ,'' AS SBJ," +
				"'' AS NQF,'' AS NQF1,'' AS WYF," +
				"'' AS JK,'' AS YLJ,'' AS BGJJ," +
				"'' AS BS,'' AS SJDCK,'' AS SYJ," +
				"'' AS KKHJ,'' AS SFHJ,  '' as GZYF," +
				" '' as BH,''  AS JSX,'' AS ZFJJ," +
				"'' AS NZJ,'' AS KK");
		sql.append(" FROM  ");
		sql.append("( select L.GUID, L.RYBH,L.XM,L.BM,L.RYLB,L.SHZT AS SHZT,TO_CHAR(L.JBGZ, 'FM999999990.00') AS JBGZ,TO_CHAR(L.ZJLTF, 'FM999999990.00') AS ZJLTF,TO_CHAR(L.YZWBT, 'FM999999990.00') AS YZWBT,TO_CHAR(L.GWBT, 'FM999999990.00') AS GWBT,TO_CHAR(L.XZFT, 'FM999999990.00') AS XZFT,TO_CHAR(L.HZBT, 'FM999999990.00') AS HZBT,TO_CHAR(L.TXTGBF, 'FM9999990.00') AS TXTGBF,TO_CHAR(L.SHBT, 'FM9999990.00') AS SHBT,TO_CHAR(L.XZBT, 'FM999999990.00') AS XZBT,TO_CHAR(L.WJBT, 'FM999999990.00') AS WJBT,TO_CHAR(L.TXHL, 'FM999999990.00') AS TXHL,TO_CHAR(L.JHBT, 'FM999999990.00') AS JHBT,TO_CHAR(L.QTBT, 'FM999999990.00') AS QTBT,TO_CHAR(L.LTBT, 'FM999999990.00') AS LTBT,TO_CHAR(L.YZBT, 'FM999999990.00') AS YZBT,TO_CHAR(L.JCJX, 'FM999999990.00') AS JCJX,TO_CHAR(L.JTF, 'FM999999990.00') AS JTF,TO_CHAR(L.BT, 'FM999999990.00') AS BT,TO_CHAR(L.ZFBT, 'FM999999990.00') AS ZFBT,TO_CHAR(L.BGZ, 'FM999999990.00') AS BGZ,TO_CHAR(L.WYBT, 'FM999999990.00') AS WYBT,TO_CHAR(L.JKF, 'FM999999990.00') AS JKF,TO_CHAR(L.GJF, 'FM999999990.00') AS GJF,TO_CHAR(L.DHF, 'FM999999990.00') AS DHF,TO_CHAR(L.YFHJ, 'FM999999990.00') AS YFHJ,TO_CHAR(L.FZ, 'FM999999990.00') AS FZ,TO_CHAR(L.SBJ, 'FM999999990.00') AS SBJ,TO_CHAR(L.NQF, 'FM999999990.00') AS NQF,TO_CHAR(L.NQF1, 'FM999999990.00') AS NQF1,TO_CHAR(L.WYF, 'FM999999990.00') AS WYF,TO_CHAR(L.JK, 'FM999999990.00') AS JK,TO_CHAR(L.YLJ, 'FM999999990.00') AS YLJ,TO_CHAR(L.BGJJ, 'FM999999990.00') AS BGJJ,TO_CHAR(L.BS, 'FM999999990.00') AS BS,TO_CHAR(L.SJDCK, 'FM999999990.00') AS SJDCK,TO_CHAR(L.SYJ, 'FM999999990.00') AS SYJ,TO_CHAR(L.KKHJ, 'FM999999990.00') AS KKHJ,TO_CHAR(L.SFHJ, 'FM999999990.00') AS SFHJ,L.GZYF,L.BH,TO_CHAR(L.JSX, 'FM999999990.00') AS JSX,TO_CHAR(L.ZFJJ, 'FM999999990.00') AS ZFJJ,TO_CHAR(L.NZJ, 'FM999999990.00') AS NZJ,TO_CHAR(L.KK, 'FM999999990.00') AS KK from CW_LZXZB L where l.gzyf=to_char(add_months(to_date(to_char(sysdate,'yyyy.mm'),'yyyy.mm'),-1 ),'yyyy.mm') ");
		sql.append(" union ");
		sql.append(" select '1' as GUID,'' as RYBH,'' as XM,'' as BM,'' as RYLB,'' AS SHZT,TO_CHAR(sum(L.JBGZ), 'FM999999990.00') AS JBGZ,TO_CHAR(sum(L.ZJLTF), 'FM999999990.00') AS ZJLTF,TO_CHAR(sum(L.YZWBT), 'FM999999990.00') AS YZWBT,TO_CHAR(sum(L.GWBT), 'FM999999990.00') AS GWBT,TO_CHAR(sum(L.XZFT), 'FM999999990.00') AS XZFT,TO_CHAR(sum(L.HZBT), 'FM999999990.00') AS HZBT,TO_CHAR(sum(L.TXTGBF), 'FM9999990.00') AS TXTGBF,TO_CHAR(sum(L.SHBT), 'FM9999990.00') AS SHBT,TO_CHAR(sum(L.XZBT), 'FM999999990.00') AS XZBT,TO_CHAR(sum(L.WJBT), 'FM999999990.00') AS WJBT,TO_CHAR(sum(L.TXHL), 'FM999999990.00') AS TXHL,TO_CHAR(sum(L.JHBT), 'FM999999990.00') AS JHBT,TO_CHAR(sum(L.QTBT), 'FM999999990.00') AS QTBT,TO_CHAR(sum(L.LTBT), 'FM999999990.00') AS LTBT,TO_CHAR(sum(L.YZBT), 'FM999999990.00') AS YZBT,TO_CHAR(sum(L.JCJX), 'FM999999990.00') AS JCJX,TO_CHAR(sum(L.JTF), 'FM999999990.00') AS JTF,TO_CHAR(sum(L.BT), 'FM999999990.00') AS BT,TO_CHAR(sum(L.ZFBT), 'FM999999990.00') AS ZFBT,TO_CHAR(sum(L.BGZ), 'FM999999990.00') AS BGZ,TO_CHAR(sum(L.WYBT), 'FM999999990.00') AS WYBT,TO_CHAR(sum(L.JKF), 'FM999999990.00') AS JKF,TO_CHAR(sum(L.GJF), 'FM999999990.00') AS GJF,TO_CHAR(sum(L.DHF), 'FM999999990.00') AS DHF,TO_CHAR(sum(L.YFHJ), 'FM999999990.00') AS YFHJ,TO_CHAR(sum(L.FZ), 'FM999999990.00') AS FZ,TO_CHAR(sum(L.SBJ), 'FM999999990.00') AS SBJ,TO_CHAR(sum(L.NQF), 'FM999999990.00') AS NQF,TO_CHAR(sum(L.NQF1), 'FM999999990.00') AS NQF1,TO_CHAR(sum(L.WYF), 'FM999999990.00') AS WYF,TO_CHAR(sum(L.JK), 'FM999999990.00') AS JK,TO_CHAR(sum(L.YLJ), 'FM999999990.00') AS YLJ,TO_CHAR(sum(L.BGJJ), 'FM999999990.00') AS BGJJ,TO_CHAR(sum(L.BS), 'FM999999990.00') AS BS,TO_CHAR(sum(L.SJDCK), 'FM999999990.00') AS SJDCK,TO_CHAR(sum(L.SYJ), 'FM999999990.00') AS SYJ,TO_CHAR(sum(L.KKHJ), 'FM999999990.00') AS KKHJ,TO_CHAR(sum(L.SFHJ), 'FM999999990.00') AS SFHJ,'' as GZYF,'' as BH,TO_CHAR(sum(L.JSX), 'FM999999990.00') AS JSX,TO_CHAR(sum(L.ZFJJ), 'FM999999990.00') AS ZFJJ,TO_CHAR(sum(L.NZJ), 'FM999999990.00') AS NZJ,TO_CHAR(sum(L.KK), 'FM999999990.00') AS KK from CW_LZXZB L where 1=1 ");
		
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			sql.append("and l.rybh in('"+rybh+"') ");
		}
		if(Validate.noNull(shzt)){
			sql.append(" and l.shzt like '%"+shzt+"%'  ");
		}
		if(Validate.noNull(gzyf)){
			sql.append("and l.gzyf like '%"+gzyf+"%' ");
		}
		if(Validate.noNull(guid)){
			sql.append(" and L.GUID in ('" + guid.trim() + "') ");
		}
		sql.append(" ) l ");
		if (Validate.noNull(guid)) {
			sql.append("WHERE (L.GUID in ('" + guid.trim() + "') or guid='1') order by rybh asc ");
		} else {

			String strWhere = " where 1=1 and rybh is not null  ";
			
			if(Validate.noNull(rybh)){
				rybh = rybh.replace(",", "','");
				strWhere += " and (l.rybh in('"+rybh+"') or l.rybh is null)";
			}
			if(Validate.noNull(shzt)){
				strWhere += " and (l.shzt like '%"+shzt+"%' or l.shzt is null)";
			}
			if(Validate.noNull(gzyf)){
				strWhere += " and (l.gzyf like '%"+gzyf+"%' or l.gzyf is null)";
			}
			strWhere +=" order by rybh asc ";
			sql.append(strWhere);
		}
		return db.queryForList(sql.toString());
	}

	/**
	 * 在职薪资导入模板生成
	 */
	public List<Map<String, Object>> getZzxzdrmb() {
		String sql = " SELECT B.* FROM  CW_GZXMB B WHERE B.XH ^=1 ORDER BY B.XH ";
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 在职薪资导入模板生成
	 */
	public List<Map<String, Object>> getZzxzdrmbs(String name) {
		String sql = " SELECT B.* FROM  CW_GZXMB B WHERE xmmc in('"+name+"') and B.XH ^=1 ORDER BY B.XH ";
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 离职薪资导入模板生成
	 */
	public List<Map<String, Object>> getZzxzdrmbsBylz(String name) {
		String sql = " SELECT B.* FROM  CW_XZXMB B WHERE xmmc in('"+name+"')  ORDER BY B.XH ";
		return db.queryForList(sql.toString());
	}

	/**
	 * 导入根据名称查询dwbh
	 * @param bm
	 * @return
	 */
	public String getdwbhxx(String bm) {
		String sql="SELECT DWBH FROM GX_SYS_DWB WHERE MC='"+bm+"'";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 薪资合计
	 * @param pd
	 * @return
	 */
	public List<Map<String, Object>> getXzHjList(PageData pd) {
		StringBuffer sql=new StringBuffer();
		sql.append(" select  ");
		sql.append(" TO_CHAR(sum(T.yfhj), 'FM999999990.00') AS YFHJ ");
		sql.append(" from cw_xzb t where 1=1 ");
		String rybh = Validate.isNullToDefaultString(pd.getString("rybh"),"");
		String shzt = Validate.isNullToDefaultString(pd.getString("shzt"),"");
		String gzyf = Validate.isNullToDefaultString(pd.getString("gzyf"),"");
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			sql.append(" and t.rybh in('"+rybh+"')");
		}
		if(Validate.noNull(shzt)){
			sql.append(" and t.shzt like '%"+shzt+"%'");
		}
		if(Validate.noNull(gzyf)){
			sql.append(" and t.gzyf like '%"+gzyf+"%'");
		}
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 查询是否有数据
	 * @param bm
	 * @return
	 */
	public int getcount(String tablename,String rybh,String shzt,String gzyf,String xm,String bm) {
		StringBuffer sql=new StringBuffer();
		sql.append("SELECT count(*) from "+tablename+" t WHERE 1=1");
		if(Validate.noNull(rybh)){
			rybh = rybh.replace(",", "','");
			sql.append(" and t.rybh in('"+rybh+"') ");
		}
		if(Validate.noNull(shzt)){
			sql.append("and t.shzt like '%"+shzt+"%' ");
		}
		if(Validate.noNull(gzyf)){
			sql.append("and t.gzyf like '%"+gzyf+"%' ");
		}
		if(Validate.noNull(xm)){
			sql.append("and t.xm like '%"+xm+"%' ");
		}
		if(Validate.noNull(bm)){
			sql.append(" and t.bm like '%"+CommonUtil.getEndText(bm)+"%' ");
		}
		int count=db.queryForInt(sql.toString());
		return count;
		
	}
	/**
	 * 
	 * @author 作者：PR
	 * @date 2018-9-13  下午4:34:08
	 */
	public List<Map<String,Object>> getExcelData(String sql){
		
		return db.queryForList(sql);
	}

	/**
	 * @Description: 描述:验证是否为在职人员
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-14下午1:28:56
	 * @param rybh
	 * @return    
	 */
	public int zzryCheck(String rybh) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.zzzt from gx_sys_ryb t where t.rybh='"+rybh+"'");
		String ryzt=db.queryForSingleValue(sql.toString());
		if(ryzt.equals("1") ||ryzt.equals("3")){
			 return 1;
		}else{
			 return 0;
		}
	}

	/**
	 * @Description: 描述:验证是否为退休人员
	 * @author 作者:李烁
	 * @date 创建时间:2018-9-14下午1:53:31
	 * @param rybh
	 * @return    
	 */
	public int txryCheck(String rybh) {
		StringBuffer sql = new StringBuffer();
		sql.append("select t.zzzt from gx_sys_ryb t where t.rybh='"+rybh+"'");
		String ryzt=db.queryForSingleValue(sql.toString());
		if(ryzt.equals("2")){
			 return 1;
		}else{
			 return 0;
		}
	}
	
	
	
}
