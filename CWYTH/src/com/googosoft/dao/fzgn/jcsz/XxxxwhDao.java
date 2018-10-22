package com.googosoft.dao.fzgn.jcsz;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.clbx.GX_CLBX;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_XXXXWH;


/**
 * 学校信息维护dao
 * @author googosoft
 *
 */
@Repository("xxxxwhDao")
public class XxxxwhDao  extends BaseDao{
	private Logger logger = Logger.getLogger(XxxxwhDao.class);
	/**
	 * 添加
	 * @param xxxxwh
	 * @return
	 */
	public int doAdd(GX_SYS_XXXXWH xxxxwh)
	{
		String sql="insert into CW_XXXXB(guid,xxdm,xxmc,xxdz,xxyzbm,xxxzm, xxzgbmm,fddbrh ,xzgh ,xzxm , "
				+ "dwfzrh,zzjgm , lxdh, czdh,dzxx ,zydz , xzqym,jxny , xxywmc , xxbxlxm, xxjbzm, frzsh,lsyg ,"
				+ "xxgczk , gcyxzk, zdxxzk,yjsyzk ,jbwljyzk ,jbcrjyzk ,xkmls,gjsfxgzyxzk,cwfzr,czr,czrq) values(?,?,?,?,?,?,?"
				+ ",?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		//sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				xxxxwh.getGuid(),
				xxxxwh.getXxdm(),
				xxxxwh.getXxmc(),
				xxxxwh.getXxdz(),
				xxxxwh.getXxyzbm(),
				xxxxwh.getXxxzm(),
				xxxxwh.getXxzgbmm(),
				xxxxwh.getFddbrh(),
				xxxxwh.getXzgh(),
				xxxxwh.getXzxm(),
				xxxxwh.getDwfzrh(),
				xxxxwh.getZzjgm(),
				xxxxwh.getLxdh(),
				xxxxwh.getCzdh(),
				xxxxwh.getDzxx(),
				xxxxwh.getZydz(),
				xxxxwh.getXzqym(),
				xxxxwh.getJxny(),
				xxxxwh.getXxywmc(),
				xxxxwh.getXxbxlxm(),
				xxxxwh.getXxjbzm(),
				xxxxwh.getFrzsh(),
				xxxxwh.getLsyg(),
				xxxxwh.getXxgczk(),
				xxxxwh.getGcyxzk(),
				xxxxwh.getZdxxzk(),			
				xxxxwh.getYjsyzk(),
				xxxxwh.getJbwljyzk(),
				xxxxwh.getJbcrjyzk(),
				xxxxwh.getXkmls(),
				xxxxwh.getGjsfxgzyxzk(),
				xxxxwh.getCwfzr(),
				xxxxwh.getCzr(),
				xxxxwh.getCzrq()									
		};
		return  db.update(sql, obj);
	}	
	/**
	 * 修改
	 * @param 学校信息表
	 * @return
	 */
	public int doUpdate(GX_SYS_XXXXWH xxxxwh){
		String sql = "update CW_XXXXB set xxdm=?,xxmc=?,xxdz=?,xxyzbm=?,xxxzm=?,lxdh=?,fddbrh=?,xzxm=?,xzqym=?,czdh=?,dzxx=? where guid='001'";
		
		Object[] obj = new Object[]{			
				xxxxwh.getXxdm(),
				xxxxwh.getXxmc(),
				xxxxwh.getXxdz(),
				xxxxwh.getXxyzbm(),
				xxxxwh.getXxxzm(),
				xxxxwh.getLxdh(),
				xxxxwh.getFddbrh(),
				xxxxwh.getXzxm(),
				xxxxwh.getXzqym(),
				xxxxwh.getCzdh(),
				xxxxwh.getDzxx()
				
		};
	    int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 根据id查询学校详情信息
	 * @param guid
	 * @return
	 */
	public Map<String, Object> getObjectById(String guid) {
		String sql = "select guid,xxdm,xxmc,xxdz,xxyzbm,xxxzm, xxzgbmm,fddbrh ,xzgh ,xzxm , "
				+ "dwfzrh,zzjgm , lxdh, czdh,dzxx ,zydz , xzqym,jxny , xxywmc , xxbxlxm, xxjbzm, frzsh,lsyg ,"
				+ "xxgczk , gcyxzk, zdxxzk,yjsyzk ,jbwljyzk ,jbcrjyzk ,xkmls,gjsfxgzyxzk,cwfzr,czr,czrq from CW_XXXXB  where guid = ?";
		
		return db.queryForMap(sql,new Object[]{guid});
	}
	
	/**
	 * 差旅报销标准信息保存
	 * @param clbx
	 * @param zsbz1
	 * @param zsbz2
	 * @param wj1
	 * @param wj2
	 * @param wj3
	 * @param sfbl1
	 * @param bz
	 * @param jtbz
	 * @param city
	 * @return
	 */
	public int doclbxSave(GX_CLBX clbx,String[] zsbz1,String[] zsbz2,String[] wj1,String[] wj2,String[] wj3,String[] sfbl1,String[] bz,String[] jtbz,String[] city) {
		List sqlList = new ArrayList<>();
		if(zsbz1.length>0){
			String sql1 = "delete gx_clbx";
			db.update(sql1);
			for (int i = 0; i < zsbz1.length; i++) {
				String sql = "insert into gx_clbx(gid,zsbz1,zsbz2,wj1,wj2,wj3,sfbl1,bz,jtbz,city) values(sys_guid(),'"+zsbz1[i]+"','"+zsbz2[i]+"','"+wj1[i]+"','"+wj2[i]+"','"+wj3[i]+"','"+sfbl1[i]+"','"+bz[i]+"','"+jtbz[i]+"','"+city[i]+"')";
				sqlList.add(sql);
			}
		}
		int i = 0;
		try {  
			 db.batchUpdate(sqlList);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}catch(ArrayIndexOutOfBoundsException e) {
			System.out.print("数组越界! ");
			System.out.println("下标: " + e.getMessage());
		}
		return 1;
	}
	
	/**
	 * 查询差旅报销信息标准list
	 * @return
	 */
	public List getClbxlist() {
		String sql = "select * from gx_clbx";
		return db.queryForList(sql);
	}
}
