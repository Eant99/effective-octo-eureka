package com.googosoft.dao.fzgn.jcsz;


import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;


/**
 * 单位信息dao
 * @author googosoft
 *
 */
@Repository("dwbDao")
public class DwbDao extends BaseDao{
	private Logger logger = Logger.getLogger(DwbDao.class);
	
	/**
	 * 增加
	 * @param dwb
	 * @return
	 */
	public int doAdd(GX_SYS_DWB dwb) {
		String sql = "insert into %SDWB(dwbh,pxxh,bmh,mc,jc,jlrq,dz,dwxz,dwld,fgld,sjdw,dwzt,dwbb,sfxy,dwjc,sj,gdfw) "
				+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		sql=String.format(sql, SystemSet.gxBz);//%SDWB==GZ_SYS_DWB
		Object[] obj = new Object[]{
				dwb.getDwbh(),
				dwb.getPxxh(),
				dwb.getBmh(),
				dwb.getMc(),
				dwb.getJc(),
				dwb.getJlrq(),
				dwb.getDz(),
				dwb.getDwxz(),
				dwb.getDwld(),
				dwb.getFgld(),
				dwb.getSjdw(),
				dwb.getDwzt(),
				dwb.getDwbb(),
				dwb.getSfxy(),
				"1",
				dwb.getSj(),
				dwb.getGdfw()
		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
			if(i > 0){ 
				db.insertRydwqx(LUser.getRybh());//赋权限，当前登录人对应单位下所有的单位权限
			}
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 单位信息实体
	 * @param dwbh
	 * @return
	 */
	public Map<String, Object> getObjectById(String dwbh) {
		String sql = "select a.dwbh,a.bmh,a.mc,'('||a.bmh||')'||a.mc as dw,nvl(a.jc,'') jc,nvl(a.dz,'') dz,a.dwxz,a.jlrq,a.dwld,"
				+ "nvl((select '('||r.rygh||')'||to_char(r.xm) from %sryb r where r.rybh=a.dwld),'') dwldmc,"
				+ "nvl((select lxdh from %sryb r where r.rybh=a.dwld),'') dwlddh,"
				+ "a.fgld,nvl((select '('||r.rygh||')'||to_char(r.xm) from %sryb r where r.rybh=a.fgld),'') fgldmc,"
				+ "nvl((select lxdh from %sryb r where r.rybh=a.fgld),'') fglddh,"
				+ "a.sjdw,(case a.sjdw when '000000' then '' else (select '('||d.bmh||')'||to_char(d.mc) from %sdwb d where d.dwbh=a.sjdw) end) sjdwmc,"
				+ "a.dwzt,(case a.dwzt when '1' then '正常' when '0' then '禁用' end) dwztmc,a.dwjc,a.mjbz,a.pxxh,a.bmsx,a.bz,a.sysbz,(case a.sysbz when '1' then '否' when '0' then '是' end) sysbzmc,a.sysmj,"
				+ "a.syslx,(select mc from %sdmb where zl='"+Constant.SYSLX+"' and dm=a.syslx) syslxmc,"
				+ "a.syslb,(select mc from %sdmb where zl='"+Constant.SYSLB+"' and dm=a.syslb) syslbmc,"
				+ "a.sysjb,(select mc from %sdmb where zl='"+Constant.SYSJB+"' and dm=a.sysjb) sysjbmc,"
				+ "a.ssxk,(select mc from %sdmb where zl='"+Constant.SSXK+"' and dm=a.ssxk) ssxkmc,"
				+ "a.sysgs,(select mc from %sdmb where zl='"+Constant.SYSGS+"' and dm=a.sysgs) sysgsmc,"
				+"a.dwbb,a.sfxy,a.dwywmc,a.dwywjc,a.sxrq,a.sfst,a.czr,a.czrq,a.gdfw,"
				+ " (select f.TYPENAME from da_gdfwfl f where f.TYPEID = a.gdfw) as gdfwmc,"
				+ "a.jlnf,(select '('||r.rybh||')'||r.xm from GX_SYS_ryb r where r.rybh=a.yz)yzmc,"
				+ "(select '('||r.rybh||')'||r.xm from GX_SYS_ryb r where r.rybh=a.sj)sjmc from %sdwb a where dwbh = ?";
		sql=String.format(sql, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz, SystemSet.gxBz,SystemSet.gxBz,SystemSet.gxBz);
		return db.queryForMap(sql,new Object[]{dwbh});
	}
	/**
	 * 修改
	 * @param dwb 单位表
	 * @return
	 */
	public int doUpdate(GX_SYS_DWB dwb,PageData pd){
		String sql = "update %sdwb set mc=?,jc=?,dz=?,dwxz=?,jlrq=?,dwld=?,fgld=?,sjdw=?,dwbb=?,sfxy=?,dwywmc=?,dwywjc=?,sxrq=?,sfst=?,czr=?,czrq=?"
				+ ",dwzt=?,pxxh=?,bmh=?,bz=?,sysbz=?,sysjb=?,sysgs=?,syslb=?,syslx=?,ssxk=?,sysmj=?,yz=?,sj=?,gdfw=? where dwbh=?";
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = new Object[]{
				dwb.getMc(),
				dwb.getJc(),
				dwb.getDz(),
				dwb.getDwxz(),
				dwb.getJlrq(),
				dwb.getDwld(),
				dwb.getFgld(),
				dwb.getSjdw(),
				dwb.getDwbb(),
				dwb.getSfxy(),
				dwb.getDwywmc(),
				dwb.getDwywjc(),
				dwb.getSxrq(),
				dwb.getSfst(),
				dwb.getCzr(),
				dwb.getCzrq(),
				dwb.getDwzt(),
				dwb.getPxxh(),
				dwb.getBmh(),
				dwb.getBz(),
				dwb.getSysbz(),
				dwb.getSysjb(),
				dwb.getSysgs(),
				dwb.getSyslb(),
				dwb.getSyslx(),
				dwb.getSsxk(),
				dwb.getSysmj(),
				CommonUtil.getBeginText(pd.getString("yz")),
				CommonUtil.getBeginText(pd.getString("sj")),
				dwb.getGdfw(),
				dwb.getDwbh()
				
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
	 * 删除
	 * @param dwbh   000001
	 * @return 
	 */
	public int doDelete(String dwbh) {
		String sql = "DELETE %SDWB WHERE DWBH"+CommonUtils.getInsql(dwbh);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = dwbh.split(",");
		int i = 0;
		//try {  
			i = db.update(sql, obj);
		//} catch (DataAccessException e) {  
		//    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		//    return -1;
		//}
		return i;
	}
	
	
	/**
	 * 删除时，如果单位下有已处置的资产，则禁用该单位
	 * @param dwbh
	 * @return
	 */
	public int jyDw(String dwbh){
		String sql = "UPDATE %SDWB SET DWZT = '0' WHERE DWBH = ?";
		sql=String.format(sql, SystemSet.gxBz);
		int i = 0;
		try {  
			i = db.update(sql, new Object[]{dwbh});
		} catch (DataAccessException e) {
		    return -1;
		}
		return i;
	}
	/**
	 * 删除单位时验证该单位下是否有人员或下级单位或资产
	 * @param DWBHS
	 * @return
	 */
	public int validateForRyOrXjdwOrZc(String DWBHS){
        DWBHS = DWBHS.trim();
        String[] DWBH = DWBHS.split(",");
        String NEWDWBH = "";
        String FLAG = "N"; 
        String sql = "";
        String bmh = "";
        DwbDao d = new DwbDao();
        int result = 0;
        int count = 0;
        int j = 0; 
        try { 
        	System.err.println("length"+DWBH.length);
	        for(int i=0; i<DWBH.length; i++){
	            sql = "select count(z.rybh) from %Sryb z where z.dwbh = '"+DWBH[i]+"' ";
	            sql=String.format(sql, SystemSet.gxBz);
	            count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	            if(count > 0){
	            	//有人员不能删除
	            	System.err.println("有人员不能删除");
	            }else{
	                sql = "select count(z.dwbh) from %Sdwb z where z.sjdw = '"+DWBH[i]+"' and z.dwbh!=z.sjdw ";
	                sql=String.format(sql, SystemSet.gxBz);
	                count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	                if(count > 0){
	                	//有单位不能删除
		            	System.err.println("有单位不能删除");

	                }else{
//	                	sql = "select count(z.yqbh) from zc_zjb z where z.sydw = '"+DWBH[i]+"' and z.xz in (select d.dm from gx_sys_dmb d where d.zl ='"+Constant.XZ+"' )";
//	    	            count = Integer.valueOf(db.queryForSingleValue(sql)+"");
//	    	            if(count > 0){
	    	            	//有正常资产不能删除
//	    	            	System.err.println("有正常资产不能删除");
//	    	            }else{
	    	            	//可以删除
	    	            	System.err.println("可以删除");
	    	               /* sql = "select count(z.yqbh) from zc_zjb z where z.sydw = '"+DWBH[i]+"' and z.xz in (select d.dm from gx_sys_dmb d where d.zl ='"+Constant.HXZ+"' ) and z.bdzt= '"+StateManager.BDZT_CZWC+"' ";
	    	                count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	    	                if(count > 0){
	    	                    FLAG = "U";  //假删除：改为禁用
	    	                }else{
	    	                    FLAG = "D";  //表示可以删除
	    	                }*/
	    	            	NEWDWBH = NEWDWBH + DWBH[i];
	    	            	if(i<DWBH.length-1){
	    	            		NEWDWBH = NEWDWBH +",";
	    	            	}
//	    	            }
	                }
	            }
	        }
	        if(Validate.noNull(NEWDWBH)){
//	        	d.doDelete(NEWDWBH);
	        	String sql1 = "DELETE %SDWB WHERE DWBH"+CommonUtils.getInsql(NEWDWBH);
	    		sql=String.format(sql1, SystemSet.gxBz);
	    		Object[] obj = NEWDWBH.split(",");
	    		
    			result = db.update(sql, obj);
	        }
	        
	    } catch (DataAccessException e) {
	    	logger.error("数据库操作失败\n" + e.getCause().getMessage());  
	    }
	    return result;
	}
	
	/**
	 * 判断bmh是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckDwbh(String bmh){
		String sql = "select count(1) from %SDWB where  bmh= ? ";
		sql=String.format(sql, SystemSet.gxBz);
		String count = db.queryForObject(sql,new Object[]{bmh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 判断排序序号是否重复
	 * @param pxxh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckPxxh(String pxxh){
		String sql = "select count(1) from %SDWB where  pxxh= ? ";
		sql=String.format(sql, SystemSet.gxBz);
		String count = db.queryForObject(sql,new Object[]{pxxh}, String.class);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 通过所选的上级单位获取单位级次
	 * @param dwbh  选择的上级单位编号
	 * @return
	 */
	public int getDwjc(String dwbh){
		String sql ="select  nvl(dwjc,0)+1 as dwjc from %Sdwb where dwbh = ? and rownum = 1";
		sql=String.format(sql, SystemSet.gxBz);
		String dwjc = db.queryForObject(sql, new Object[]{dwbh},String.class);
		return Integer.parseInt(dwjc);
	}
	/**
	 * 判断bmh是否重复
	 * @param dwbh
	 * @return 0为不重复，1为重复
	 */
	public boolean doCheckBmh(GX_SYS_DWB dwb){
		String sql = "select count(*) from %SDWB where bmh = ? and dwbh <> ? ";
		sql=String.format(sql, SystemSet.gxBz);
		String count = db.queryForObject(sql,new Object[]{dwb.getBmh(),dwb.getDwbh()}, String.class);
		return "0".equals(count)?true:false;
	}
	/**
	 * 批量赋值单位信息
	 * @param dwbh
	 * @return
	 */
	public int doPlfuzhi(String ids,String ziduan,Object zdValue){
		String sql = "update %SDWB set "+ziduan+" =? where dwbh"+CommonUtils.getInsql(ids);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = ids.split(",");
		Object[] objs = new Object[obj.length+1];
		System.arraycopy(new Object[]{zdValue}, 0, objs, 0, 1);
		System.arraycopy(obj, 0, objs, 1, obj.length);
		int i = db.update(sql,objs);
		return i;
	}
	
	/**
	 * 单位机构设置
	 * 通过部门号(名称)查询单位编号
	 */
	public String findDwbhByDwmc(String words) {
		String sql = " SELECT DWBH FROM %SDWB WHERE trim('('||BMH||')'||MC) = ?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql,new Object[]{words});
	}
	/**
	 * 通过dwbh获取（bmh）mc格式
	 * @param dwbh
	 * @return
	 */
	public String getDwxx(String dwbh){
		String sql = "select '('||d.bmh||')'||d.mc from %Sdwb d where d.dwbh = ?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql, new Object[]{dwbh});
	}
	/**
	 * 查询单位的下级单位的数量
	 * @return
	 */
	public String getNewStatus(String dwbh) {
		String sql = "select count(1) from GX_SYS_dwb where sjdw='"+dwbh+"' and dwzt='1'and dwbh is not null";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 人员权限单位信息list
	 * @param dwbh
	 * @param searchValue
	 * @param rybh
	 * @param s1
	 * @param s2
	 * @return
	 */
	public List<Map<String, Object>> getList(String dwbh, String searchValue,String rybh,String s1,String s2,String dwbh1,String mc) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select K.DWBH, K.MC,  K.JC,  K.DZ, TO_CHAR(K.JLRQ, 'yyyy-MM-dd') AS JLRQ, DECODE(K.SFXY, '0', '否', '1', '是') SFXY, DECODE(K.DWZT, '0', '禁用', '1', '正常') AS DWZT, ");
		sql.append(" K.DWJC,  K.MJBZ, K.PXXH,  K.BMH, (SELECT MC  FROM GX_SYS_DMB   WHERE ZL = '"+s1+"' AND DM = K.DWBB) AS DWBB, ");
		sql.append(" K.BMSX, K.BZ, K.SYSBZ,  K.SYSJB, K.SYSGS, K.SYSLB, K.SYSMJ, K.JLNF,  K.SYSLX, ");
		sql.append(" (SELECT MC FROM GX_SYS_DMB M  WHERE ZL = '"+s2+"' AND M.DM = K.DWXZ) AS DWXZ,(SELECT '(' || RYGH || ')' || XM  FROM GX_SYS_RYB B WHERE B.RYBH = K.DWLD) AS DWLD, ");
		sql.append(" K.DWLD AS DWLDH, (SELECT '(' || RYGH || ')' || XM  FROM GX_SYS_RYB B  WHERE B.RYBH = K.FGLD) AS FGLD, ");
		sql.append(" (SELECT NVL2(C.BMH, '(' || C.BMH || ')' || TO_CHAR(C.MC), '') FROM GX_SYS_DWB C WHERE C.DWBH = K.SJDW) AS SJDW, ");
		sql.append(" K.SJDW AS SJDWH from GX_SYS_DWB K where 1 = 1 and K.DWBH in (select z.dwbh ");
		sql.append(" from ZC_SYS_RYDWQXB z  left join gx_sys_dwb d on z.dwbh = d.dwbh where z.rybh = '"+rybh+"'  ");
		if(Validate.noNull(dwbh)){
			sql.append(" and K.dwbh in ('"+dwbh.trim()+"') ");
		}
		if(Validate.noNull(dwbh1)){
			sql.append(" and K.dwbh like '%"+dwbh1+"%' ");
		}
		if(Validate.noNull(mc)){
			sql.append(" and K.mc like '%"+mc+"%' ");
		}
		sql.append(" ) order by BMH asc ");
		
//		Object[] dwbh2 = dwbh.split(",");
		return db.queryForList(sql.toString());
	}
	
	/**
	 * 获取parentid为000000的归档范围分类 
	 * @return
	 */
	public List PowerGdfwflFirst(String rybh) {
		String sql = "SELECT C.TYPEID,C.TYPENAME,C.TYPEPARENTID,(SELECT COUNT(D.TYPEID) FROM DA_GDFWFL D WHERE D.TYPEPARENTID=C.TYPEID ) AS XJCOUNT FROM DA_GDFWFL C WHERE C.TYPEPARENTID='000000' ORDER BY C.TYPENUMBER";
		sql=String.format(sql);
		return db.queryForList(sql);
	}
	/**
	 * 获取某个分类下的分类信息
	 * @param sjdw 上级单位
	 * @return
	 */
	public List PowerGdfwflSecond(String sjdw) {
		String sql = "";
		sql = "SELECT C.TYPEID,C.TYPENAME,C.TYPEPARENTID,(SELECT COUNT(D.TYPEID) FROM DA_GDFWFL D WHERE D.TYPEPARENTID=C.TYPEID ) AS XJCOUNT FROM DA_GDFWFL C WHERE C.TYPEPARENTID='"+sjdw+"' ORDER BY C.TYPENUMBER";
		sql=String.format(sql);
		return db.queryForList(sql);
	}
	
}
