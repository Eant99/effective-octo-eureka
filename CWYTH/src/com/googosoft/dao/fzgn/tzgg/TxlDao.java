package com.googosoft.dao.fzgn.tzgg;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.fzgn.jcsz.RybDao;
import com.googosoft.pojo.fzgn.jcsz.ZC_SYS_DDB;
import com.googosoft.pojo.fzgn.tzgg.ZC_TXL;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.WindowUtil;

@Repository("txlDao")
public class TxlDao extends BaseDao{
	private Logger logger = Logger.getLogger(TxlDao.class);
	@Resource(name="ryxxDao")
	private RybDao ryxxDao;
	
	/**
	 * 增加通讯录
	 * @param gid
	 * @return
	 * 10/28整理
	 */
	public int doAdd(ZC_TXL txl) {
		String sql = "insert into ZC_TXL(gid,rybh,xm,bgdd,zw,bddh,moblie,qq,email,zt,pxxh) values(?,?,?,?,?,?,?,?,?,?,?)";
		sql=String.format(sql);
		Object[] obj = new Object[]{
				txl.getGid(),
				txl.getRybh(),
				txl.getXm(),
				txl.getBgdd(),
				txl.getZw(),
				txl.getBddh(),
				txl.getMoblie(),
				txl.getQq(),
				txl.getEmail(),
				txl.getZt(),
				txl.getPxxh()
		};
		int i = 0;
		//1028新增异常捕获
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	/**
	 * 修改通讯录信息 注意，顺序一定要相同，
	 * @param gid
	 * @return  
	 */
	public int doUpdate(ZC_TXL txl){
		String sql = "update ZC_TXL set rybh=?,xm=?,bgdd=?,zw=?,bddh=?,moblie=?,qq=?,email=?,zt=?,pxxh=? WHERE gid=?";
		sql=String.format(sql);
		Object[] obj = new Object[]{
			txl.getRybh(),
			txl.getXm(),
			txl.getBgdd(),
			txl.getZw(),
			txl.getBddh(),
			txl.getMoblie(),
			txl.getQq(),
			txl.getEmail(),
			txl.getZt(),
			txl.getPxxh(),
			txl.getGid()
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 删除通讯录信息
	 * @param ddbh
	 * @return
	 */
	public int doDelete(String gid) {
		String sql = "DELETE ZC_TXL WHERE gid"+CommonUtils.getInsql(gid);
		sql=String.format(sql);
		Object[] obj = gid.split(",");
		int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 获取某个通讯录的详细信息
	 * @param gid
	 * @return
	 */
	public Map getObjectById(String id) {
		String sql = "select d.gid,d.rybh,(select '('||rygh||')'||xm from gx_sys_ryb b where b.rybh=d.rybh) as ryb,d.xm,d.bgdd,d.zw,d.bddh,d.moblie,d.qq,d.email,d.zt," 
				+ "(case d.zt when '1' then '正常' when '0' then '禁用' end) ztmc,d.pxxh " 
				+ " from ZC_TXL d where gid = ?";			
		sql=String.format(sql);
		return db.queryForMap(sql,new Object[]{id});
	}
	

	/**
	 * 导入
	 * @param file
	 * @return
	 */
	public List insertJcsj(String file) {
		Workbook rwb;
		List list = new ArrayList();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			int j = 0;
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				j=0;
				Map map = new HashMap();
				map.put("rybh", WindowUtil.ryghTorybh(rs.getCell(j++, i).getContents()));
				map.put("xm", rs.getCell(j++, i).getContents());
				map.put("bgdd", rs.getCell(j++, i).getContents());
				map.put("zw", rs.getCell(j++, i).getContents());
				map.put("bddh", rs.getCell(j++, i).getContents());
				map.put("moblie", rs.getCell(j++, i).getContents());
				map.put("qq", rs.getCell(j++, i).getContents());
				map.put("email", rs.getCell(j++, i).getContents());
				map.put("zt", rs.getCell(j++, i).getContents());
				map.put("pxxh", rs.getCell(j++, i).getContents());
				list.add(map);
			
				String sql = "insert into zc_txl(gid,rybh,xm,bgdd,zw,bddh,moblie,qq,email,zt,pxxh) "
						+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?)";
				Object[] obj = new Object[]{
						map.get("rybh"),
						map.get("xm"),
						map.get("bgdd"),
						map.get("zw"),
						map.get("bddh"),
						map.get("moblie"),
						map.get("qq"),
						map.get("email"),
						map.get("zt"),
						map.get("pxxh")
				};
				db.update(sql, obj);
			}
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
}

