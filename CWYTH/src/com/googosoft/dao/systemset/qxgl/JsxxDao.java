package com.googosoft.dao.systemset.qxgl;

import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import com.google.gson.Gson;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.base.PageDao;
import com.googosoft.pojo.PageInfo;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.systemset.qxgl.JSB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

/**
 * 角色信息Dao
 * Create by 刘帅 on 2016-10-20 13:20
 */
@Repository("jsxxDao")
public class JsxxDao extends BaseDao{
	
	private Logger logger = Logger.getLogger(JsxxDao.class);
	
	@Resource(name="pageDao")
	public PageDao pageDao;
	
	
	public String getNewStatus(String sjdd) {
		String sql = "select t.zt from ZC_SYS_JSB t where t.jsbh='"+sjdd+"'";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 角色管理（添加）：添加角色
	 * @param ryb
	 * @return
	 */
	public int doAdd(JSB jsb){
		String sql="insert into "+TnameU.JSB+"(jsbh,jsmc,bz,zt,xgbz,xtbz) values(?,?,?,?,?,?)";
		int i = 0;
		try {  
			i = db.update(sql,new Object[]{jsb.getJsbh().trim(),jsb.getJsmc().trim(),jsb.getBz().trim(),jsb.getZt(),jsb.getXgbz(),jsb.getXtbz().trim()});
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	/**
	 * 角色管理（查看）：获取单个角色详细信息
	 * @param jsbh
	 * @return
	 */
	public Map getRyxx(String jsbh){
		String sql ="select D.jsbh,D.jsmc,D.bz,D.zt,D.xgbz,D.xtbz from "+TnameU.JSB+" D where D.jsbh=?";
		return db.queryForMap(sql, new Object[]{jsbh});
	}
	/**
	 * 角色管理（修改）：修改角色信息
	 * @param jsbh
	 * @return
	 */
	public int doUpdate(JSB jsb){
		String a =jsb.getJsbh();
		String sql="update "+TnameU.JSB+" set jsmc=?,bz=?,zt=?,xgbz=?,xtbz=? where jsbh=?";
		int i=0;
		try {  
			i = db.update(sql,new Object[]{jsb.getJsmc(),jsb.getBz(),jsb.getZt(),jsb.getXgbz(),jsb.getXtbz(),jsb.getJsbh()});
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 角色管理（删除）：删除角色
	 * @param rygh
	 * @return
	 */
	public int doDelete(String jsbh){
		String sql1="delete "+TnameU.JSB+" where jsbh "+CommonUtils.getInsql(jsbh);
		String sql2="delete "+TnameU.JSRYB+" where jsbh "+CommonUtils.getInsql(jsbh);
		String sql3="delete ZC_SYS_JSQXB"+" where jsbh "+CommonUtils.getInsql(jsbh);
		Object[] obj =jsbh.split(",");
		int i =0;
		try {  
			i =db.update(sql1,obj)+db.update(sql2,obj)+db.update(sql3,obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 角色管理（操作权限）：获取一级菜单
	 * @return
	 */
	public List getYjcdList() {
		String sql = "SELECT MKBH,MKMC FROM "+TnameU.MKB+" T WHERE LENGTH(T.MKBH)=2 AND t.QXBZ='1' ORDER BY TO_NUMBER(XH)";
		return db.queryForList(sql);
	}

	/**
	 * 角色管理（操作权限）： 获取二级菜单
	 * @param mkbh
	 * @return
	 */
	public List getEjcdList(String mkbh) {
		String sql = "SELECT MKBH,MKMC FROM "+TnameU.MKB+" T WHERE LENGTH(T.MKBH)=4 AND t.QXBZ='1' and substr(mkbh,1,2)=?  ORDER BY TO_NUMBER(XH)";
		return db.queryForList(sql,new Object[]{mkbh});
	}
	
	/**
	 * 角色管理（操作权限）： 获取三级菜单
	 * @param mkbh
	 * @return
	 */
	public List getSjcdList(String mkbh) {
		String sql = "SELECT MKBH,MKMC FROM "+TnameU.MKB+" T WHERE LENGTH(T.MKBH)=6 AND t.QXBZ='1' and substr(mkbh,1,4)=?  ORDER BY TO_NUMBER(XH)";
		return db.queryForList(sql,new Object[]{mkbh});
	}

	/**
	 * 角色管理（操作权限）： 判断用户是否有该菜单的权限
	 * @param jsbh
	 * @param mkbh
	 * @return
	 */
	public boolean doCheckCzqx(String jsbh, String mkbh){
		String sql = "select count(1) from "+TnameU.JSCZQXB+" WHERE 1=1 AND JSBH=? AND MKBH=? ";
		String count = db.queryForObject(sql,new Object[]{jsbh,mkbh}, String.class);
		return "0".equals(count)?false:true;
	}
	
	/**
	 * 角色管理（操作权限）： 保存权限信息
	 * @param jsbh
	 * @param mkbh
	 * @return
	 */
	@Resource(name="systemSet")
	private SystemSet systemSet;
	public int saveJsCzqx(String mkbh, String jsbh) {
		Date czrq =new Date();
		String mkbhs [] =  mkbh.split(",");
		int k=1;
		if(Validate.noNull(jsbh)){
			String sqls = "DELETE FROM "+TnameU.JSCZQXB+"  WHERE JSBH =?";
			int m = db .update(sqls, new Object[]{jsbh});
			for (int i=0;i<mkbhs.length;i++){
				if(!(mkbhs[i].equals("") || mkbhs[i] == null)){
					String sql = "INSERT INTO "+TnameU.JSCZQXB+"(JSBH,MKBH,XTBZ) VALUES(?,?,?)";
					try {  
						k = db.update(sql, new Object[]{jsbh,mkbhs[i],systemSet.XTBZ});
					} catch (DataAccessException e) {
					    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
					    return -1;
					}
				}
			}
		}
		return k;
	}
	
	/**
	 * 角色管理（角色用户）： 获取用户信息，单位信息，角色信息
	 * @return
	 */
	public PageInfo getJsRyxx(PageData pd,String jsbh){
		StringBuffer sql = new StringBuffer();
		sql.append("D.RYBH,G.XM,G.DWBH,DW.MC,D.JSBH,Z.JSMC");
		PageList pageList = new PageList();
		pageList.setSqlText(sql.toString());
		pageList.setKeyId("d.rybh");//主键
		pageList.setStrWhere("and D.JSBH='"+jsbh+"'");//where条件
		pageList.setHj1("");//合计
		pageList.setTableName(""+TnameU.JSRYB+" D left join "+TnameU.JSB+" Z  on  D.JSBH=Z.JSBH left join GX_SYS_RYB G on D.RYBH=G.RYBH left join GX_SYS_DWB DW  on G.DWBH = DW.DWBH ");//表名
		pageList=pageDao.findPageList(pd,pageList);
		Gson gson = new Gson();
		PageInfo pageInfo = new PageInfo(pd.get("draw")+"",pageList.getTotalList().get(0).get("NUM")+"",pageList.getTotalList().get(0).get("NUM")+"",gson.toJson(pageList.getContentList()));
		return pageInfo;
	}
	
	/**
	 * 角色管理（角色用户 列表）：增加用户，实现与角色关联
	 * 
	 * @return
	 */
	public int doSaveRy(String rygh,String jsbh) throws Exception{
		String ryghs [] =  rygh.split(",");
		int k=0;
		for (int i=0;i<ryghs.length;i++){
			if(!(ryghs[i].equals("") || ryghs[i] == null)){
				String sql = "insert into "+TnameU.JSRYB+"(rybh,jsbh) values(?,?)";
				try {  
					k = db.update(sql, new Object[]{ryghs[i],jsbh});
				} catch (DataAccessException e) {
				    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
				    return -1;
				} 
			}
		}
				return k;
			
	}
	
	/**
	 * 添加角色人员时验证该角色下是否已经存在该人员
	 * @param rybh
	 * @param jsbh
	 * @return
	 */
	public boolean doCheckJsry(String rybh, String jsbh){
		String sql = "select count(*) from "+TnameU.JSRYB+" where JSBH = ? and rybh = ?";
		String a = db.queryForObject(sql, new Object[]{jsbh, rybh},String.class);
		if("0".equals(a)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 角色管理（角色用户 列表）：删除用户，实现与角色解绑
	 * 
	 * @return
	 */
	public int doDeleteRy(String rybh,String jsbh) throws Exception{
		String sql="delete "+TnameU.JSRYB+" where rybh"+CommonUtils.getInsql(rybh)+ " AND JSBH='"+jsbh+"'";
		Object[] obj =rybh.split(",");
		int i =0;
		try {  
			i =db.update(sql,obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    return -1;
		}
		return i;
	}
	
	/**
	 * 启用禁用
	 * @param jsbh
	 * @param type
	 * @return
	 */
	public int qyjy(String jsbh,String type){
		final Object[] params = jsbh.split(",");
		String ryzt = "1";
		if(!type.contains("qy")){
			ryzt = "0";
		}
		String wstr= CommonUtils.getInsql(jsbh);
		String sql = "update ZC_SYS_JSB set zt='"+ryzt+"' where jsbh"+wstr;//基础信息
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

	public List<Map<String, Object>> getList(String guid, String searchValue,String jsbh) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT G.RYGH, G.XM,  G.DWBH,  DW.MC,   D.JSBH,   Z.JSMC, G.RYBH, ");
		sql.append("  '（' || G.RYGH || '）' || G.XM AS RYMC,  '（' || G.DWBH || '）' || DW.MC AS DWMC ");
		sql.append("  FROM ZC_SYS_JSRYB D  left join ZC_SYS_JSB Z   on D.JSBH = Z.JSBH  left join GX_SYS_RYB G    on D.RYBH = G.RYBH ");
		sql.append("   left join GX_SYS_DWB DW     on G.DWBH = DW.DWBH ");
		sql.append("  WHERE 1 = 1  ");
		if(Validate.noNull(guid)){
			sql.append("  AND G.RYBH in ('"+guid.trim()+"') ");
		}
		sql.append("   and D.JSBH = '"+jsbh+"'  ORDER BY G.RYBH ");
		
//		Object[] guid2 = guid.split(",");
		return db.queryForList(sql.toString());
	}
}
