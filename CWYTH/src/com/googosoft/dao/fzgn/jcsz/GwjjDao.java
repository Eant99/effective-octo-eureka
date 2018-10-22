package com.googosoft.dao.fzgn.jcsz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.googosoft.commons.LUser;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_GWJJB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.Logger;
import com.googosoft.util.Validate;

/**
 * 岗位交接Dao层
 * @author RC 2017-08-30
 *
 */
@Repository("gwjjDao")
public class GwjjDao extends BaseDao{
	private Logger logger = Logger.getLogger(GwjjDao.class);
	
	/**
	 * 新增岗位交接信息
	 * @param gwjjb
	 * @param jsxx
	 * @return
	 */
	public boolean doAdd(GX_SYS_GWJJB gwjjb, String jsxx){
		List<String> sqllist = new ArrayList<String>();
		List<Object[]> objlist = new ArrayList<Object[]>();
		
		sqllist.add(String.format("insert into %SGWJJB(gid,ywfqr,ywfqrdh,yqxsyr,yqxsyrdh,jgr,jgrdh,gwjjyy,jdr,jdrq) values(?,?,?,?,?,?,?,?,?,sysdate)", SystemSet.gxBz));
		objlist.add(new Object[]{
				gwjjb.getGid(),
				gwjjb.getYwfqr(),
				gwjjb.getYwfqrdh(),
				gwjjb.getYqxsyr(),
				gwjjb.getYqxsyrdh(),
				gwjjb.getJgr(),
				gwjjb.getJgrdh(),
				gwjjb.getGwjjyy(),
				LUser.getRybh()
		});
		//把原权限所有人的操作权限赋给接岗人
		sqllist.add(String.format("insert into %SCZQXB(rybh,mkbh,czr,czrq,czqx,xtbz) select ?,mkbh,?,sysdate,czqx,xtbz from %SCZQXB where rybh = ? and mkbh not in (select mkbh from %SCZQXB where rybh = ?)",SystemSet.sysBz,SystemSet.sysBz,SystemSet.sysBz));
		objlist.add(new Object[]{gwjjb.getJgr(),gwjjb.getYwfqr(),gwjjb.getYqxsyr(),gwjjb.getJgr()});
		//把原权限所有人的管理权限赋给接岗人
		sqllist.add(String.format("insert into %SRYDWQXB(dwbh,sjdw,dwjc,mc,rybh) select dwbh,sjdw,dwjc,mc,? from %SRYDWQXB where rybh = ? and dwbh not in (select dwbh from %SRYDWQXB where rybh = ?)",SystemSet.sysBz,SystemSet.sysBz,SystemSet.sysBz));
		objlist.add(new Object[]{gwjjb.getJgr(),gwjjb.getYqxsyr(),gwjjb.getJgr()});
		//删除原权限所有人的管理权限
		sqllist.add(String.format("delete %SRYDWQXB where rybh = ?",SystemSet.sysBz));
		objlist.add(new Object[]{gwjjb.getYqxsyr()});
		
		if(Validate.noNull(jsxx)){
			String[] jsxxS = jsxx.split(",");
			for (int i = 0; i < jsxxS.length; i++) {
				sqllist.add(String.format("insert into %SGWJJB_JS(gid,jjgid,jsbh,jsmc,jdr,jdrq) select sys_guid(),?,jsbh,jsmc,?,sysdate from %SJSB where jsbh = ?", SystemSet.gxBz,SystemSet.sysBz));
				objlist.add(new Object[]{gwjjb.getGid(),LUser.getRybh(),jsxxS[i]});
				//把原权限所有人的角色信息赋给接岗人
				sqllist.add(String.format("insert into %SJSRYB(jsbh,rybh) select ?,? from dual where (select count(*) from %SJSRYB where jsbh||'-'||rybh = ?||'-'||?) = 0",SystemSet.sysBz,SystemSet.sysBz));
				objlist.add(new Object[]{jsxxS[i],gwjjb.getJgr(),jsxxS[i],gwjjb.getJgr()});
				//删除原权限所有人的角色信息
//				sqllist.add(String.format("delete %SJSRYB where jsbh = ? and rybh = ?",SystemSet.sysBz));
//				objlist.add(new Object[]{jsxxS[i],gwjjb.getYqxsyr()});
			}
		}
		return db.batchUpdate(sqllist, objlist);
	}
	
	/**
	 * 获取实体类（查看）
	 * @param gid
	 * @return
	 */
	public Map<String, Object> getObjectById(String gid){
		String sql = "select k.gid as gid,k.ywfqr as ywfqrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.ywfqr) as ywfqr,k.ywfqrdh as ywfqrdh,"
				+ "k.yqxsyr as yqxsyrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.yqxsyr) as yqxsyr,k.yqxsyrdh as yqxsyrdh,"
				+ "k.jgr as jgrbh,(select '('||r.rygh||')'||r.xm from gx_sys_ryb r where r.rybh=k.jgr) as jgr,k.jgrdh as jgrdh,k.gwjjyy as gwjjyy"
				+ " from %SGWJJB k where gid = ?";
		sql = String.format(sql, SystemSet.gxBz);
		return db.queryForMap(sql, new Object[]{gid});
	}
	/**
	 * 通过gid查询角色信息（查看）
	 * @param gid
	 * @return
	 */
	public List<Map<String, Object>> getJsxx(String gid){
		String sql = "select jsbh,jsmc from %SGWJJB_JS where jjgid = ?";
		sql = String.format(sql, SystemSet.gxBz);
		return db.queryForList(sql, new Object[]{gid});
	}
}
