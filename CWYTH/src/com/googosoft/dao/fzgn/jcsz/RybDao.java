package com.googosoft.dao.fzgn.jcsz;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_RYB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Const;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;
import com.googosoft.util.security.EncryptUtils;

@Repository("ryxxDao")
public class RybDao extends BaseDao{
	private Logger logger = Logger.getLogger(RybDao.class);
	/**
	 * 新增
	 * @param ryb
	 * @return
	 */
	public int doAdd(GX_SYS_RYB ryb){
		String sql="insert into %SRYB(rybh,dwbh,xm,xb,csrq,whcd,byrq,sxzy,gzrq,sysgl,zyzc,zygz,drrq,txrq,bz,ryzt,zzbz,pxxh,sfzh,"
				+ "rygh,rownums,url,cssclass,lxdh,qq,mail,zzzt,mm,guid,czr,czrq) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		sql=String.format(sql, SystemSet.gxBz);
		
		Object[] obj;
			obj = new Object[]{
					ryb.getRybh(),
					ryb.getDwbh(),
					ryb.getXm(),
					ryb.getXb(),
					ryb.getCsrq(),
					ryb.getWhcd(),
					ryb.getByrq(),
					ryb.getSxzy(),
					ryb.getGzrq(),
					ryb.getSysgl(),
					ryb.getZyzc(),
					ryb.getZygz(),
					ryb.getDrrq(),
					ryb.getTxrq(),
					ryb.getBz(),
					ryb.getRyzt(),
					ryb.getZzbz(),
					ryb.getPxxh(),
					ryb.getSfzh(),
					ryb.getRygh(),
					ryb.getRownums(),
					ryb.getUrl(),
					ryb.getCssclass(),
					ryb.getLxdh(),
					ryb.getQq(),
					ryb.getMail(),
					ryb.getZzzt(),
					ryb.getMm(),
					ryb.getGuid(),
					ryb.getCzr(),
					ryb.getCzrq(),
					};
			return db.update(sql, obj);
	}
	/**
	 * 获取实体类
	 * @param rybh
	 * @return
	 */
	public Map<String, Object> getObjectById(String rybh){
		String sql = "select A.rybh,(select '('||d.bmh||')'||d.mc from %Sdwb d where d.dwbh=a.dwbh) as dwbh,A.xm,A.xb,A.csrq,A.whcd,"
				+ "A.byrq,A.sxzy,A.gzrq,A.sysgl,A.zyzc,A.zygz,A.drrq,A.txrq,A.bz,A.MWMM,"
				+ "A.ryzt,A.zzbz,A.pxxh,A.sfzh,A.rygh,A.rownums,A.url,A.cssclass,A.lxdh,A.qq,A.mail,A.zzzt,A.mm from %SRYB A"
				+ " where rybh=?";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[]{rybh});
	}
	/**
	 * 修改人员信息
	 * @param rybh
	 * @return
	 */
	public int doUpdate(GX_SYS_RYB ryb){
		String sql = "update %SRYB set mwmm=?,mm=?,bz=? where rybh=?";
		sql=String.format(sql, SystemSet.gxBz);
		String mwmm = Validate.isNullToDefaultString(ryb.getMwmm(), "123456");
		String mm = new SimpleHash("SHA-1", Const.SALTKEY, mwmm).toString().toUpperCase();
		Object[] obj = new Object[]{
				mwmm,
				mm,
				ryb.getBz(),
				ryb.getRybh()
				};
		return db.update(sql, obj);
	}
	/**
	 * 删除人员（暂时没有删除角色信息、管理权限和操作权限等，但是验证了名下有没有资产）
	 * @param rybh
	 * @return
	 */
	public int doDel(String rybh){
		final Object[] params = rybh.split(",");
		//List<Object[]> batchArgs=new ArrayList<Object[]>();
		//batchArgs.add(params);
		String wstr= CommonUtils.getInsql(rybh);
		String sqlryxx = "DELETE %SRYB where RYBH "+wstr;//基础信息
//		String sqlcgjl = "DELETE FROM %SRYCGJLB WHERE RYBH "+wstr;//成果奖励
//		String sqjxqk =  "DELETE FROM %SRYJXB WHERE RYBH "+wstr;//进修情况
//		String sqllwqk = "DELETE FROM %SRYLWB WHERE RYBH "+wstr;//论文情况
//		String sqlwysp = "DELETE FROM %SRYWYSPB WHERE RYBH "+wstr;//外语水平
//		String sqlzzqk = "DELETE FROM %SRYZZB WHERE RYBH "+wstr;//著作情况
		sqlryxx=String.format(sqlryxx, SystemSet.gxBz);
//		sqlcgjl=String.format(sqlcgjl, SystemSet.gxBz);
//		sqjxqk=String.format(sqjxqk, SystemSet.gxBz);
//		sqllwqk=String.format(sqllwqk, SystemSet.gxBz);
//		sqlwysp=String.format(sqlwysp, SystemSet.gxBz);
//		sqlzzqk=String.format(sqlzzqk, SystemSet.gxBz);
		int result=0;
		try
		{   
			result=db.update(sqlryxx, params);
//			db.update(sqlcgjl,params);
//			db.update(sqjxqk,params);
//			db.update(sqllwqk,params);
//			db.update(sqlwysp,params);
//			db.update(sqlzzqk,params);
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
	 * 删除时，如果该人名下有已处置的资产，则只禁用该人员
	 * @param rybh
	 * @return
	 */
	public int doJyRy(String rybh){
		String sql = "UPDATE %SRYB SET RYZT = '0' WHERE RYBH = ?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.update(sql, new Object[]{rybh});
	}
	/**
	 * 删除人员时验证名下是否有资产
	 * @param RYBHS
	 * @return
	 */
	public String validateForZJB(String rybh){
        String[] RYBH = rybh.trim().split(",");
        String flag = "N"; 
        String sql = "";
        String rygh = "";
        int count = 0;
        for(int i=0; i<RYBH.length; i++)
        {
        	sql = "select count(z.yqbh) from zc_zjb z where z.syr = '"+RYBH[i]+"' and z.xz in (select d.dm from gx_sys_dmb d where d.zl ='"+Constant.XZ+"' )";
            count = db.queryForObject(sql, Integer.class);
            rygh = db.queryForSingleValue("select rygh from gx_sys_ryb where rybh = '"+RYBH[i]+"'");
            if(count > 0){
            	flag = "C:"+rygh;  //返回有正常资产，提示用户
            }else{
            	sql = "select count(z.yqbh) from zc_zjb z where z.syr = '"+RYBH[i]+"' and z.xz in (select d.dm from gx_sys_dmb d where d.zl ='"+Constant.HXZ+"' ) and z.bdzt= '"+StateManager.BDZT_CZWC+"' ";
                count = db.queryForObject(sql, Integer.class);
                if(count > 0){
                	flag = "U:"+rygh;  //返回有已处置资产，提示用户
                }
            }
        }
	    return flag;
	}
		
	/**
	 * 检测人员工号的重复性
	 * @param rygh
	 * @return
	 */
	public boolean checkRygh(String rygh){
		String sql = "select count(1) from %SRYB where  rygh = ?";
		sql=String.format(sql, SystemSet.gxBz);
		Integer result =db.queryForObject(sql, new Object[]{rygh},Integer.class);
		if(result>0){
			return true;
		}else{
			return false;
		}
	}
	
	
	/**
	 * 检查人员工号是否重复
	 * @param rygh
	 * @return
	 */
	public boolean checkRygh1(String rygh){
		String sql = "select count(1) from ZC_TXL where  rybh = ?";
		sql=String.format(sql);
		Integer result =db.queryForObject(sql, new Object[]{rygh},Integer.class);
		if(result>0){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 批量赋值
	 * @param ids
	 * @param ziduan
	 * @param zdValue
	 * @return
	 */
	public int doPlfz(String ids,String ziduan,Object zdValue){
		String sql = "update %SRYB set "+ziduan+" = ? where rybh"+ToSqlUtil.getInsql(ids);
		sql=String.format(sql, SystemSet.gxBz);
		Object[] obj = ids.split(",");
		Object[] objs = new Object[obj.length+1];
		System.arraycopy(new Object[]{zdValue}, 0, objs, 0, 1);
		System.arraycopy(obj, 0, objs, 1, obj.length);
		int i = db.update(sql,objs);
		return i;
	}
	
	/**
	 * 修改他人密码
	 * @param ryb 人员表
	 * @return  
	 */
	public int doUpdate_xgtrmm(GX_SYS_RYB ryb){
		String sql="update %SRYB set mm=? where rybh=?";
		sql=String.format(sql, SystemSet.gxBz);
		String passwd = EncryptUtils.encryptToSHA(ryb.getMm(), Const.SALTKEY);	//密码加密
		int i = 0;
		try {  
			i = db.update(sql,new Object[]{passwd,ryb.getRybh()});
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 通过(rygh)xm获取该人的所在单位(bmh)mc
	 * @param ryghmc
	 * @return
	 */
	public String getDwxxByRyxx(String ryghmc){
		String sql = "select nvl2(bmh,'('||bmh||')'||mc,'') from %Sdwb where dwbh=(select dwbh from %Sryb where '('||rygh||')'||xm='"+ryghmc.trim()+"')";
		sql=String.format(sql, SystemSet.gxBz, SystemSet.gxBz);
		return db.queryForSingleValue(sql); 
	}
	/**
	 * 通过人员姓名查询人员编号
	 * @param ryxm
	 * @return
	 */
	public String findRybhByRyxm(String words) {
		String sql = "select rybh from %Sryb where trim('('||rygh||')'||xm)=?";
		sql=String.format(sql, SystemSet.gxBz);
		String rybh="";
		try{
			rybh = db.queryForObject(sql, new Object[]{words},String.class);
		}catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);
		    rybh = "F";
		}
		return rybh;
	}
	
	/**
	 * 通过人员编号查询人员姓名
	 * @param ryxm
	 * @return
	 */
	public String findRyxmByRybh(String rybh) {
		String sql = "select '('||d.rybh||')'||d.xm from %Sryb d where d.rybh = ?";
		sql=String.format(sql, SystemSet.gxBz);
		return db.queryForSingleValue(sql, new Object[]{rybh});
	}
	
	/**
	 * 管理权限设置
	 * 通过人员工号查询人员编号存不存在,数据库有唯一建约束，无需判断查出多条情况
	 */
	public String findRybhByRyxm(String loginrybh, String words) {
		String sql = " SELECT T.RYBH FROM %SRYB T WHERE trim('('||T.RYGH||')'||T.XM) = ? ";
		sql=String.format(sql, SystemSet.gxBz);
		String rybh = "";
		try {
			if(loginrybh.equalsIgnoreCase(SystemSet.AdminRybh()) == false){
			   sql+=" and T.DWBH IN (SELECT DWBH FROM "+TnameU.RYDWQXB+" WHERE RYBH=?)";
			   rybh = db.queryForObject(sql,new Object[]{words,loginrybh},String.class);
			}else{
				rybh = db.queryForObject(sql,new Object[]{words},String.class);
			}
		} catch (DataAccessException e) {  
			SQLException sqle = (SQLException) e.getCause();  
		    logger.error("数据库操作失败\n" + sqle);  
		    rybh = "F";
		}
		return rybh;
	}
	
	/**
	 * 通过rybh查询角色信息
	 * @param rybh
	 * @return
	 */
	public List<Map<String, Object>> getJsxx(String rybh){
		String sql = "select jsbh,jsmc from %SJSB where jsbh in(select jsbh from %SJSRYB where rybh = ?) order by jsbh";
		sql = String.format(sql, SystemSet.sysBz, SystemSet.sysBz);
		return db.queryForList(sql, new Object[]{rybh});
	}
	/**
	 * 密码初始化
	 * @param rybh
	 * @return
	 */
	public int doCshmm(String rybh){
		final Object[] params = rybh.split(",");
		String passwd = new SimpleHash("SHA-1", Const.SALTKEY, Constant.MRMM).toString().toUpperCase();	//密码加密
		String wstr= CommonUtils.getInsql(rybh);
		String sql = "update GX_SYS_RYB set mm='"+passwd+"',mwmm='"+Constant.MRMM+"' where RYBH"+wstr;//基础信息
		int i = 0;
		try {  
			sql=String.format(sql, SystemSet.gxBz);
			i = db.update(sql, params);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 启用禁用人员
	 * @param rybh
	 * @param type
	 * @return
	 */
	public int qyjy(String rybh,String type){
		final Object[] params = rybh.split(",");
		String ryzt = "1";
		if(!type.contains("qy")){
			ryzt = "0";
		}
		String wstr= CommonUtils.getInsql(rybh);
		String sql = "update GX_SYS_RYB set ryzt='"+ryzt+"' where RYBH"+wstr;//基础信息
		int i = 0;
		try {  
			sql=String.format(sql, SystemSet.gxBz);
			i = db.update(sql, params);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 修改密码（设置选项页面）
	 */
	public int doUpdPwd(String passwordn,String rybh){
		String sql="update %SRYB set mm=? where rybh=? ";
		sql=String.format(sql, SystemSet.gxBz);
		String passwd=EncryptUtils.encryptToSHA(passwordn, Const.SALTKEY);//密码加密
		return db.update(sql,new Object[]{passwd,rybh});
	}
}
