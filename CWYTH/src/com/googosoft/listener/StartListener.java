package com.googosoft.listener;

import java.util.HashMap;
import java.util.Map;

import java.util.List;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.ExecutionListener;
import org.springframework.jdbc.core.JdbcTemplate;

import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.SpringBeanFactoryUtils;
import com.googosoft.util.Validate;

@SuppressWarnings("serial")
public class StartListener implements ExecutionListener {
	// startListener

	@Override
	public void notify(DelegateExecution execution) throws Exception {
		JdbcTemplate jdbcTemplate = (JdbcTemplate) SpringBeanFactoryUtils
				.getBean("jdbcTemplate");
		Map<String, Object> variables = new HashMap<String, Object>();
		// if(Validate.noNull(CommonUtils.getSaas())){
		// String sql =
		// "SELECT r.RYBH FROM  "+SystemSet.sysBz+"RYB d  left join "+SystemSet.sysBz+"JSRYB r on d.rybh=r.rybh WHERE r.jsbh = '1' and d.saas='"+CommonUtils.getSaas()+"' and rownum=1";
		// String sql1 =
		// "SELECT r.RYBH FROM  "+SystemSet.sysBz+"RYB d  left join "+SystemSet.sysBz+"JSRYB r on d.rybh=r.rybh WHERE r.jsbh = '1' and d.saas='"+CommonUtils.getSaas()+"' and rownum=1";
		// String sql2 =
		// "SELECT r.RYBH FROM  "+SystemSet.sysBz+"RYB d  left join "+SystemSet.sysBz+"JSRYB r on d.rybh=r.rybh WHERE r.jsbh = '1' and d.saas='"+CommonUtils.getSaas()+"' and rownum=1";
		// String sql3 =
		// "SELECT r.RYBH FROM  "+SystemSet.sysBz+"RYB d  left join "+SystemSet.sysBz+"JSRYB r on d.rybh=r.rybh WHERE r.jsbh = '1' and d.saas='"+CommonUtils.getSaas()+"' and rownum=1";
		// Map<String,Object> rybh=jdbcTemplate.queryForMap(sql, new
		// Object[]{});
		// Map<String,Object> xldRybh=jdbcTemplate.queryForMap(sql1, new
		// Object[]{});
		// Map<String,Object> bgszrRybh=jdbcTemplate.queryForMap(sql2, new
		// Object[]{});
		// Map<String,Object> dcdbRybh=jdbcTemplate.queryForMap(sql3, new
		// Object[]{});
		// String sql4 = "";
		// List list;
		// Map<String,Object> mmap1=null;
		// String saas = CommonUtils.getSaas();
		// String lx=(String)execution.getVariable("lx");
		// if(rybh.size()>0){
		// String xbrybh = rybh.get("RYBH")+"";
		// sql4 =
		// "select wtuserid from oa_lcwtdb g WHERE g.wtkey='"+lx+"' and g.saas='"+saas+"' and g.jdr='"+xbrybh+"' and (g.kssj<to_char(sysdate,'yyyy-mm-dd hh24:Mi') and g.jssj>to_char(sysdate,'yyyy-mm-dd hh24:Mi'))";
		// list= jdbcTemplate.queryForList(sql4);
		// if(list.size()>0){
		// mmap1=(Map) list.get(0);
		// String bwtr=mmap1.get("WTUSERID")+"";
		// xbrybh=(String) Validate.isNullToDefault(bwtr, xbrybh);
		// }
		// // variables.put("xb",xbrybh);
		// }
		// if(xldRybh.size()>0){
		// String xbrybh = xldRybh.get("RYBH")+"";
		// sql4 =
		// "select wtuserid from oa_lcwtdb g WHERE g.wtkey='"+lx+"' and g.saas='"+saas+"' and g.jdr='"+xbrybh+"' and (g.kssj<to_char(sysdate,'yyyy-mm-dd hh24:Mi') and g.jssj>to_char(sysdate,'yyyy-mm-dd hh24:Mi'))";
		// list= jdbcTemplate.queryForList(sql4);
		// if(list.size()>0){
		// mmap1=(Map) list.get(0);
		// String bwtr=mmap1.get("WTUSERID")+"";
		// xbrybh=(String) Validate.isNullToDefault(bwtr, xbrybh);
		// }
		// // variables.put("xld", xbrybh);
		// }
		// if(bgszrRybh.size()>0){
		// String xbrybh = bgszrRybh.get("RYBH")+"";
		// sql4 =
		// "select wtuserid from oa_lcwtdb g WHERE g.wtkey='"+lx+"' and g.saas='"+saas+"' and g.jdr='"+xbrybh+"' and (g.kssj<to_char(sysdate,'yyyy-mm-dd hh24:Mi') and g.jssj>to_char(sysdate,'yyyy-mm-dd hh24:Mi'))";
		// list= jdbcTemplate.queryForList(sql4);
		// if(list.size()>0){
		// mmap1=(Map) list.get(0);
		// String bwtr=mmap1.get("WTUSERID")+"";
		// xbrybh=(String) Validate.isNullToDefault(bwtr, xbrybh);
		// }
		// // variables.put("bgszr", xbrybh);
		// }
		// if(dcdbRybh.size()>0){
		// String xbrybh = dcdbRybh.get("RYBH")+"";
		// sql4 =
		// "select wtuserid from oa_lcwtdb g WHERE g.wtkey='"+lx+"' and g.saas='"+saas+"' and g.jdr='"+xbrybh+"' and (g.kssj<to_char(sysdate,'yyyy-mm-dd hh24:Mi') and g.jssj>to_char(sysdate,'yyyy-mm-dd hh24:Mi'))";
		// list= jdbcTemplate.queryForList(sql4);
		// if(list.size()>0){
		// mmap1=(Map) list.get(0);
		// String bwtr=mmap1.get("WTUSERID")+"";
		// xbrybh=(String) Validate.isNullToDefault(bwtr, xbrybh);
		// }
		// // variables.put("dcdbshy", xbrybh);
		// }
		// 查询财务预审人员的信息
		Map cwysry = jdbcTemplate.queryForMap(getSql(Constant.CWRY));
		//查询财务负责人的信息
		Map cwfzr = jdbcTemplate.queryForMap(getSql(Constant.CWFZR));
		//查询财务分管领导
		Map cwfgld = jdbcTemplate.queryForMap(getSql(Constant.CWFGLD));
		//查询科研处负责人
		Map kycfzr = jdbcTemplate.queryForMap(getSql(Constant.KYCFZR));
		//查询办公室负责人
		Map bgsfzr = jdbcTemplate.queryForMap(getSql(Constant.BGSFZR));
		//查询部门领导和部门分管领导
		Map dwld = jdbcTemplate.queryForMap(getDwldSql());
		//查询校长
		String xzsql = "select xzxm from CW_XXXXB t";
		Map map = jdbcTemplate.queryForMap(xzsql);
		String xzxm = Validate.isNullToDefaultString(map.get("XZXM"), "");
		if(xzxm.contains(")")&&xzxm.length()>2){
			xzxm = xzxm.substring(1,xzxm.indexOf(")"));
		}
		String sql = "select r.guid as guid from gx_sys_ryb r where r.rybh='"+xzxm+"'";
		Map xz = jdbcTemplate.queryForMap(sql);
		
		variables.put("applyUser", LUser.getGuid());
		variables.put("cwysry", Validate.isNullToDefault(cwysry.get("GUID"), ""));
		variables.put("bmfzr",Validate.isNullToDefault(dwld.get("DWLD"), ""));
		variables.put("bgsfzr",Validate.isNullToDefault(bgsfzr.get("GUID"), ""));
		variables.put("kycfzr", Validate.isNullToDefault(kycfzr.get("GUID"), ""));
		variables.put("cwfzr", Validate.isNullToDefault(cwfzr.get("GUID"), ""));
		variables.put("bmfgld",Validate.isNullToDefault(dwld.get("DWFGLD"), ""));
		variables.put("cwfgld", Validate.isNullToDefault(cwfgld.get("GUID"),""));
		variables.put("xz", Validate.isNullToDefault(xz.get("GUID"), ""));
		variables.put("fky", false);// 非科研
		variables.put("gwjd", false);// 公务接待
		variables.put("ky", false);// 科研
		execution.setVariables(variables);
	}
	public String getSql(String jsbh){
		String sql = "select distinct(r.guid)as guid from ZC_SYS_JSRYB t left join gx_sys_ryb r on r.rybh=t.rybh where t.jsbh='"+jsbh+"' and rownum=1";
		return sql;
	}
	public String getDwldSql(){
		StringBuffer sql = new StringBuffer();
		sql.append(" select");
		sql.append(" r1.guid as dwld,");
		sql.append(" r2.guid as dwfgld");
		sql.append(" from gx_sys_dwb d");
		sql.append(" left join gx_sys_ryb r1 on r1.rybh=d.DWLD");
		sql.append(" left join gx_sys_ryb r2 on r2.rybh=d.FGLD");
		sql.append(" where d.dwbh='"+LUser.getDwbh()+"'");
		return sql.toString();
	}
}
// }
