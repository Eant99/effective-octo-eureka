package com.googosoft.dao.fzgn.jcsz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.fzgn.jcsz.CW_XJXXB;
import com.googosoft.pojo.fzgn.jcsz.CW_XSXXB;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;

@Repository("xjxxDao")
public class XJxxDao extends BaseDao {
	private Logger logger = Logger.getLogger(XJxxDao.class);

	/**
	 * 新增
	 * 
	 * @param ryb
	 * @return
	 */
	public int doAdd(CW_XJXXB xjxx,String zbguid) {
		String sql = "INSERT INTO CW_XJXXB(guid,xh,rxny,bjbh,xslb,njbh,yxbh,zybh,xkml,pyfs,yjfx,dsh,hdxlfs,sfxfz,pycc,ldfs,xsdqzt,czr) " +
				"VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		sql = String.format(sql, SystemSet.gxBz);
		System.out.println("++++++++++++++++++++++"+xjxx.getYxsh());
		Object[] obj = new Object[] {xjxx.getGuid(),zbguid,xjxx.getRxny(),xjxx.getSzbh(),
				xjxx.getXslb(),xjxx.getSznj(),xjxx.getYxsh(),xjxx.getZy(),xjxx.getXkml(),xjxx.getPyfs(),
				xjxx.getYjfx(),xjxx.getDsh(),xjxx.getHdxlfs(),xjxx.getSfxfz(),xjxx.getPycc(),xjxx.getLdfs(),xjxx.getXsdqzt(),xjxx.getCzr()};
		return db.update(sql, obj);
	}

	/**
	 * 获取实体类
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getObjectById(String zbguid){
		String sql = "select  A.GUID AS GUID, A.XH AS XH, A.RXNY AS RXNY," +
				"(SELECT '('||D.BMH||')'||D.MC FROM GX_SYS_DWB D WHERE D.BMH=A.YXBH)AS SZYX, (SELECT '('||Z.ZYBH||')'||Z.ZYMC FROM CW_ZYXXB Z WHERE Z.ZYBH = A.ZYBH)AS SZZY, (SELECT '('||X.BJBH||')'||X.BJMC FROM CW_BJXXB X WHERE X.BJBH = A.BJBH)AS SZBJ, (SELECT '('||N.NJBH||')'||N.NJMC FROM CW_NJXXB N WHERE N.NJBH = A.NJBH)AS SZNJ, nvl((select '('||r.rygh||')'||to_char(r.xm) from GX_SYS_RYB r where r.rybh=A.DSH),'')AS DSH, " +
				"(SELECT DM FROM GX_SYS_DMB WHERE ZL='103' AND DM=A.xslb)AS XSLB,(SELECT DM FROM GX_SYS_DMB WHERE ZL='45' AND DM=A.XKML)AS XKML,(SELECT DM FROM GX_SYS_DMB WHERE ZL='200' AND DM=A.PYFS)AS PYFS,(SELECT DM FROM GX_SYS_DMB WHERE ZL='201' AND DM=A.YJFX)AS YJFX,(SELECT DM FROM GX_SYS_DMB WHERE ZL='202' AND DM=A.HDXLFS)AS HDXLFS,"+
				"(SELECT DM FROM GX_SYS_DMB WHERE ZL='203' AND DM=A.PYCC)AS PYCC,(SELECT DM FROM GX_SYS_DMB WHERE ZL='204' AND DM=A.LDFS)AS LDFS,(SELECT DM FROM GX_SYS_DMB WHERE ZL='205' AND DM=A.XSDQZT)AS XSDQZT,A.SFXFZ AS SFXFZ"+
				" FROM CW_XJXXB A  WHERE XH=?";
		
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[]{zbguid});
	}
	/**
	 * 修改人员信息
	 * @param rybh
	 * @return
	 */
	public int doUpdate(CW_XJXXB xjxx,String zbguid){
		String sql = "update CW_XJXXB set rxny=?, bjbh=?, xslb =?,njbh =?,yxbh =?,zybh =?,xkml =?," +
				"pyfs =?,yjfx=?,dsh=?, hdxlfs =?,sfxfz=?, pycc=?,ldfs =?,xsdqzt =?,czr =? where xh=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{xjxx.getRxny(),xjxx.getSzbh(),
				xjxx.getXslb(),xjxx.getSznj(),xjxx.getYxsh(),xjxx.getZy(),xjxx.getXkml(),xjxx.getPyfs(),
				xjxx.getYjfx(),xjxx.getDsh(),xjxx.getHdxlfs(),xjxx.getSfxfz(),xjxx.getPycc(),xjxx.getLdfs(),xjxx.getXsdqzt(),xjxx.getCzr(),zbguid};
		return db.update(sql, obj);
	}

	public int doDel(String guid){
		final Object[] params = guid.split(",");
		String wstr= CommonUtils.getInsql(guid);
		String sqlryxx = "DELETE %SRYB WHERE GUID "+wstr;//基础信息
		String xjxx = "DELETE FROM CW_XJXXB WHERE GUID "+wstr;//成果奖励
		sqlryxx=String.format(sqlryxx, SystemSet.gxBz);
		xjxx=String.format(xjxx, SystemSet.gxBz);
		int result=0;
		try
		{   
			result=db.update(sqlryxx, params);
			result=db.update(xjxx,params);
		}
		catch(DataAccessException e)
		{
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
			result=-1;
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();//事务回滚
		}
		return result;
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
}
