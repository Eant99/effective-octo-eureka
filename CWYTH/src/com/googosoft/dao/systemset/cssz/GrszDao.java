package com.googosoft.dao.systemset.cssz;

import java.util.List;
import java.util.Map;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;

@Repository("grszdao")
public class GrszDao extends BaseDao{
	private Logger logger = Logger.getLogger(GrszDao.class);
	
	/**
	 * 获取人员基本信息（查看）
	 * @param rybh
	 * @return
	 */
	public Map getGrxx(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append(" select t.rybh,t.dwbh,t.xm,(select mc from gx_sys_dmb where zl='"+Constant.SEX+"' and dm=t.xb) as xb,");
		sql.append(" to_char(t.csrq,'yyyy-mm-dd') as csrq,"); 
		sql.append(" to_char(t.byrq,'yyyy-mm-dd') as byrq,"); 
		sql.append(" to_char(t.gzrq,'yyyy-mm-dd') as gzrq,"); 
		sql.append(" to_char(t.drrq,'yyyy-mm-dd') as drrq,"); 
		sql.append(" to_char(t.txrq,'yyyy-mm-dd') as txrq,"); 
		sql.append(" t.bz,(case t.ryzt when '1' then '正常' else '禁用' end) as ryzt,"); 
		sql.append(" (case t.zzbz when '1' then '实验室专职人员' else '非实验室专职人员' end) as zzbz,"); 
		sql.append(" t.sysgl,t.pxxh,t.sfzh,t.rygh,t.rownums,t.url,t.cssclass,t.lxdh,t.qq,t.mail,t.mm,"); 
		sql.append(" (select mc from  gx_sys_dmb  where zl = '"+Constant.ZZZT+"' and  dm=t.zzzt) as zzzt,"); 
		sql.append(" (select mc from  gx_sys_dmb  where zl = '"+Constant.WHCD+"' and  dm=t.whcd ) as whcd,"); 
		sql.append(" (select mc from  gx_sys_dmb  where zl = '"+Constant.SXZY+"' and  dm=t.sxzy ) as sxzy,"); 
		sql.append(" (select mc from  gx_sys_dmb  where zl = '"+Constant.ZYZC+"' and  dm=t.zyzc ) as zyzc,"); 
		sql.append(" (select mc from  gx_sys_dmb  where zl = '"+Constant.ZYGZ+"' and  dm=t.zygz ) as zygz,"); 
		sql.append(" (select '('||d.bmh||')'||d.mc  from  gx_sys_dwb d where dwbh=t.dwbh) as dwmc,"); 
		sql.append(" (select  ld.xm from  gx_sys_ryb ld where rybh=(select dwld from gx_sys_dwb where dwbh=t.dwbh) ) as ldxm "); 
		sql.append(" from gx_sys_ryb t where t.rybh=?"); 
		return db.queryForMap(sql.toString(),new Object[]{rybh});
	}
	/**
	 * 保存人员头像信息
	 * @param 
	 * @return
	 */
	public int doUpdRyphoto(String rybh,String path){
		String sql = " update GX_SYS_RYB set url=? where rybh=? ";
		Object[] obj = new Object[]{path,rybh};
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
	 * 获取个人外语水平信息
	 */
	public List getWysp(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.rybh,(select mc from gx_sys_dmb where zl='"+Constant.WYYZ+"'and dm =a.yz)yz, ");
		sql.append("(select mc from gx_sys_dmb where zl='"+Constant.WYSP+"'and dm =a.sp)sp from gx_sys_rywyspb  a where a.rybh =? order by a.sp");
		return db.queryForList(sql.toString(),rybh);
	}
	/**
	 * 获取个人论文情况
	 */
	public List getLwqk(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.rybh,a.rq,(select mc from gx_sys_dmb where zl='"+Constant.LWJB+"'and dm =a.lwjb) lwjb, ");
		sql.append("a.lwtm,a.sfkw from gx_sys_rylwb  a where a.rybh =? order by a.rq");
		return db.queryForList(sql.toString(),rybh);
	}
	/**
	 * 获取个人进修情况
	 */
	public List getJxqk(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select (select mc from gx_sys_dmb where zl='"+Constant.JXNR+"'and dm =a.jxnr) jxnr,a.rybh,decode(a.jxzl,'1','国内进修','2','国外进修') jxzl , ");
		sql.append("to_char(a.jxrq,'YYYY-MM-DD') jxrq,a.jxsj  from gx_sys_ryjxb  a where a.rybh =? order by a.jxrq");
		return db.queryForList(sql.toString(),rybh);
	}
	/**
	 * 获取个人著作情况
	 */
	public List getZzqk(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.rybh,to_char(a.rq,'YYYY-MM-DD') rq,(select mc from gx_sys_dmb where zl='"+Constant.ZZJB+"'and dm =a.zzjb) zzjb, ");
		sql.append("a.zzmc from gx_sys_ryzzb  a where a.rybh =? order by a.rq");
		return db.queryForList(sql.toString(),rybh);
	}
	/**
	 * 获取个人成果奖励
	 */
	public List getCgjl(String rybh){
		StringBuffer sql = new StringBuffer();
		sql.append("select a.rybh,to_char(a.rq,'YYYY-MM-DD') rq,(select mc from gx_sys_dmb where zl='"+Constant.CG+"'and dm =a.cg) cg, ");
		sql.append("a.hjsm from gx_sys_rycgjlb  a where a.rybh =? order by a.rq");
		return db.queryForList(sql.toString(),rybh);
	}
	/**
	 * 读取密保问题
	 */
	public Map getMmzh(String rybh) {
		String sql="select RYGH, MMWT, MMDA, GID from "+SystemSet.gxBz+"MMZH where RYGH=(select rygh from "+SystemSet.gxBz+"RYB where rybh=? )";
		return db.queryForMap(sql.toString(),new Object[]{rybh});
	}
}
