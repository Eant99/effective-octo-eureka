package com.googosoft.dao.echo;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Repository;

import com.googosoft.dao.base.BaseDao;
import com.googosoft.websocket.info.DshInfo;

@Repository("echoDao")
public class EchoDao extends BaseDao{

	/**
	 * 获取待审核信息DshInfo
	 * @param ywguid   业务表guid
	 * @return JavaBean:DshInfo
	 */
	public DshInfo getCcsqspDshxxMsg(String ywguid) {
		String sql = " select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from cw_ccsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where a.guid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	public DshInfo getJkDshxxMsg(String ywguid) {
		String sql = " select a.gid as guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from CW_JKYWB a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.jkr = c.guid left join zc_sys_mkb d on d.mkbh = '130202' where a.gid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	public DshInfo getGwjdsqspDshxxMsg(String ywguid) {
		String sql = " select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from cw_gwjdywsqspb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.rybh left join zc_sys_mkb d on d.mkbh = '130102' where a.guid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	public DshInfo getXzsbshDshxxMsg(String ywguid) {
		String sql = " select a.guid,a.procinstid,a.fflsh as djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from cw_grsrzb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.jbr = c.rybh left join zc_sys_mkb d on d.mkbh = '800301' where a.guid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	public DshInfo getRcbxDshxxMsg(String ywguid) {
		String sql = " select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from cw_rcbxzb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.bxr = c.guid left join zc_sys_mkb d on d.mkbh = '110203' where a.guid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	public DshInfo getCcbxDshxxMsg(String ywguid) {
		String sql = " select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from cw_clfbxzb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.sqr = c.guid left join zc_sys_mkb d on d.mkbh = '110302' where a.guid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	public DshInfo getGwjdbxDshxxMsg(String ywguid) {
		String sql = " select a.guid,a.procinstid,a.djbh,c.xm,d.mkmc as shmc,d.mkbh,substr(d.mkbh,0,4) as sjmkbh,substr(d.mkbh,0,2) as rootmkbh from cw_gwjdfbxzb a left join act_ru_task b on a.procinstid = b.proc_inst_id_ "
				+ " left join gx_sys_ryb c on a.bxryid = c.guid left join zc_sys_mkb d on d.mkbh = '111102' where a.guid = ? ";
		Object[] obj = {ywguid};
		return db.queryForObject(sql, obj, new BeanPropertyRowMapper<DshInfo>(DshInfo.class));
	}
	/**
	 * 获取审核人guid（审核人不存在的情况：①审核通过②流程节点是申请人）
	 * @param procinstid 流程id
	 * @return
	 */
	public String getShrGuid(String procinstid) {
		String sql = "select assignee_ from act_ru_task where proc_inst_id_ = ? and task_def_key_ <> 'sqr'";
		Object[] obj = {procinstid};
		return db.queryForSingleValue(sql,obj);
	}
	
}
