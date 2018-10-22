package com.googosoft.dao.system.shenhe;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.websocket.ContainerProvider;
import javax.websocket.Session;
import javax.websocket.WebSocketContainer;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.googosoft.commons.LUser;
import com.googosoft.commons.PropertiesUtil;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.constant.Constant;
import com.googosoft.constant.MenuFlag;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.system.index.DeskDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.util.AutoKey;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
import com.googosoft.websocket.echo.MyClient;
import com.googosoft.websocket.echo.SessionMap;

@Repository("shenheDao")
public class ShenheDao extends BaseDao{
	private Logger logger = Logger.getLogger(ShenheDao.class);
	
	
	@Resource(name="deskDao")
	private DeskDao deskDao;
	
	/**
	 * 公共提交方法
	 * @param pd
	 * @return
	 */
	public boolean doSubmit(PageData pd){
		String mkbh = pd.getString("mkbh");
		String keyid = pd.getString("keyid");
		String[] idarr = keyid.split(",");
		String tjr = LUser.getRybh();
		String tjrxm = LUser.getXm();
		if("sjdjk".equals(pd.getString("sjdjk"))){//手机端
			tjr = pd.getString("rybh");
			tjrxm = pd.getString("xm");
		}
		String bqz = SystemSet.sysBz;
		if(Validate.isNull(mkbh) || Validate.isNull(keyid)){
			logger.error("传递参数有误,mkbh:" + mkbh + ",keyid:" + keyid);
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
			return false;
		}else if("undefined".equals(keyid)){
			logger.error("没有获取到单据编号!");
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
			return false;
		}
		String sql = "select id,sqmk,(case nvl(sqmkmc,'$') when '$' then (select mkmc from " + bqz + "mkb m where m.mkbh = l.sqmk) else sqmkmc end) sqmkmc,bzs,shmkbh,(case nvl(mkmc,'$') when '$' then (select mkmc from " + bqz + "mkb m where m.mkbh = l.shmkbh) else mkmc end) shmkmc,lczt,tgzt,tgztms,thzt,thztms,shrlm,shyjlm,shsjlm,shidlm,shztlm,shdwlm,shfllm,shbmc,djdwlm,rysql,rylb from " + bqz + "shlcb l where sqmk = ? order by bzs";
		List list = db.queryForList(sql, new Object[]{mkbh});
		if(list.size() == 0){
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
			return false;
		}else{
			List<String> sqllist = new ArrayList();
			List<Object[]> objlist = new ArrayList();

			sqllist.add("update zc_sys_shjlb set sxsj = sysdate where sxsj is null and dbh " + ToSqlUtil.getInsql(idarr) + " and sqmkbh = '" + mkbh + "'");
			objlist.add(idarr);

			StringBuffer inSql;
			for(int i = 0; i < idarr.length; i++){
				String dbh = idarr[i];
				if(MenuFlag.ZCQC_ZCZC.equals(mkbh)){//资产自查
					String qczt = db.queryForSingleValue("select qcqk from zc_zczcb where yqbh = '"+dbh+"' and qcbh in (select qcbh from ZC_ZCZCZT where zt = '1')");
					if("1".equals(qczt)){//如果自查状态是1（相符），则没有审核流程，所以不向和审核有关的的表中插入数据
						continue;
					}
				}
				Map map;
				for(int j = 0; j < list.size(); j++){
					map = (Map)list.get(j);
					String shbmc = Validate.isNullToDefault(map.get("SHBMC"), "") + "";
					String shidlm = Validate.isNullToDefault(map.get("SHIDLM"), "") + "";
					String shztlm = Validate.isNullToDefault(map.get("SHZTLM"), "") + "";
					String djdwlm = Validate.isNullToDefault(map.get("DJDWLM"), "") + "";
					String shdwlm = Validate.isNullToDefault(map.get("SHDWLM"), "") + "";
					String shfllm = Validate.isNullToDefault(map.get("SHFLLM"), "") + "";
					String lcid = map.get("ID").toString();
					String bzs = map.get("BZS").toString();
					String shmkbh = map.get("SHMKBH").toString();
					String shmkmc = map.get("SHMKMC").toString();
					String mkmc = map.get("SQMKMC").toString();//申请的模块编号在方法一开始，是传进来的参数
					String lczt = map.get("LCZT").toString();
					lczt = lczt.replace(",", "','");
					String tgzt = Validate.isNullToDefault(map.get("TGZT"), "") + "";
					String tgztms = Validate.isNullToDefault(map.get("TGZTMS"), "") + "";
					String thzt = Validate.isNullToDefault(map.get("THZT"), "") + "";
					String thztms = Validate.isNullToDefault(map.get("THZTMS"), "") + "";
					String shrlm = Validate.isNullToDefault(map.get("SHRLM"), "") + "";
					String shyjlm = Validate.isNullToDefault(map.get("SHYJLM"), "") + "";
					String shsjlm = Validate.isNullToDefault(map.get("SHSJLM"), "") + "";
					String rysql = Validate.isNullToDefault(map.get("RYSQL"), "") + "";
					String rylb = Validate.isNullToDefault(map.get("RYLB"), "1") .toString();
					
					Map djmap;
					String mapsql = "select " + shidlm + "," + shztlm;
					if(Validate.noNull(djdwlm)){
						mapsql += "," + djdwlm;
					}
					if(Validate.noNull(shdwlm)){
						mapsql += "," + shdwlm;
					}
					if(Validate.noNull(shfllm)){
						mapsql += "," + shfllm + " flxx ";
					}
					mapsql += " from " + shbmc + " where " + shidlm + " = ?";
					if(MenuFlag.ZCQC_ZCZC.equals(mkbh)){//如果是资产自查，则需要另外加以下条件
						mapsql += " and qcbh in (select qcbh from zc_zczczt where zt = '1')";
					}
					try{
						djmap = db.queryForMap(mapsql, new Object[]{dbh});
					}catch (Exception e){  
						logger.error("获取单据信息失败\n" + e.getCause().getMessage());
						pd.put("msg", "{\"success\":\"false\",\"msg\":\"获取单据信息失败！\"}");
						return false;
					}
					
					String djzt = Validate.isNullToDefault(djmap.get(shztlm.toUpperCase()), "") + "";
					String dwbh = "";//需要显示的单位编号,为空表示不显示
					if(Validate.noNull(djdwlm)){
						dwbh = Validate.isNullToDefault(djmap.get(djdwlm.toUpperCase()), "") + "";
					}
					String shdwbh = "";//需要限制单位权限用的单位编号,为空表示不限制
					if(Validate.noNull(shdwlm)){
						shdwbh = Validate.isNullToDefault(djmap.get(shdwlm.toUpperCase()), "") + "";
					}
					String shflbh = "";//需要限制分类权限用的分类编号,为空表示不限制，应该是flh||'-'||flmc这样的
					if(Validate.noNull(shfllm)){
						shflbh = Validate.isNullToDefault(djmap.get("FLXX"), "") + "";
					}
					
					sql = "insert into " + bqz + "shcshb(dbh,rybh,tjr,tjrxm,djdw,djdwmc,tjsj,mkbh,mkmc,sqmkbh,sqmkmc,bzs,lcid,lczt,tgzt,tgztms,thzt,thztms,shrlm,shyjlm,shsjlm,shidlm,shztlm,shbmc) ";
					
					inSql = new StringBuffer();
					inSql.append(" select ?,rybh,?,?,?,(select '('||bmh||')'||mc from gx_sys_dwb d where d.dwbh = ?),sysdate,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,? ");
					inSql.append(" from gx_sys_ryb r where 1 = 1 ");
					if("1".equals(rylb)){
						inSql.append(" and ryzt = '1' ");
						//******************限制操作权限start***************
						inSql.append(" and rybh in ( ");
						//当前登录人具有该模块的权限
						inSql.append(" (select rybh from zc_sys_czqxb where mkbh = '" + shmkbh + "' and xtbz = '" + SystemSet.XTBZ + "') ");
						inSql.append(" union all ");
						//当前登录人的角色具有该模块的权限
						inSql.append(" (select rybh from zc_sys_jsryb where jsbh in (select jsbh from zc_sys_jsqxb where mkbh = '" + shmkbh + "' and xtbz = '" + SystemSet.XTBZ + "')) ");
						inSql.append(" union all ");
						//角色-一般用户具有该模块的权限
						inSql.append(" (select rybh from gx_sys_ryb where (1 = 2 or (select count(*) from zc_sys_jsqxb where mkbh = '" + shmkbh + "' and jsbh = '" + MenuFlag.MRJS + "' and xtbz = '" + SystemSet.XTBZ + "') > 0) and ryzt = '1') ");
						inSql.append(" ) ");
						//******************限制操作权限end*****************
						//限制单位权限
						if(Validate.noNull(shdwbh)){
							inSql.append(" and rybh in ( ");
							inSql.append(" (select rybh from zc_sys_rydwqxb where dwbh = '" + shdwbh + "' group by rybh) ");
							inSql.append(" ) ");
						}
						//限制分类权限
						if(Validate.noNull(shflbh)){
							inSql.append(" and ( ");
							//没有设置分类权限的不限制分类权限
							inSql.append(" (select count(*) from " + TnameU.GLQXB + " where zl = '" + Constant.FLQX + "' and xtbz = '" + SystemSet.XTBZ + "' and rybh = r.rybh) = 0 ");
							inSql.append(" or ");
							//设置分类权限，并且具有00的，是具有所有权限
							inSql.append(" (select count(*) from " + TnameU.GLQXB + " where zl = '" + Constant.FLQX + "' and xtbz = '" + SystemSet.XTBZ + "' and rybh = r.rybh and dwbh = '00') > 0 ");
							inSql.append(" or ");
							//设置了分类权限的，只能审核分类权限下的
							inSql.append(" rybh in (select rybh from " + TnameU.GLQXB + " where zl = '" + Constant.FLQX + "' and xtbz = '" + SystemSet.XTBZ + "' and dwbh in (select bzdm from (select bzdm,flh,flmc,(case when bzdm = sjdm then '0000000000' else sjdm end) sjdm from zc_flb_jyb) start with flh||'-'||flmc = '" + shflbh + "' connect by prior bzdm = sjdm)) ");
							inSql.append(" ) ");
						}
						//如果有rysql的话，要把取到的人员也加进去
						if(Validate.noNull(rysql)){
							inSql.append(" or rybh in (" + rysql.replace("?","'" + dbh + "'") + ") ");
						}
					}else if(Validate.noNull(rysql)){
						inSql.append(" and rybh in (" + rysql.replace("?","'" + dbh + "'") + ") ");
					}else{
						logger.error("流程设置中设置了只需要rysql中获取的人员，但并没有设置rysql");
						pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
						return false;
					}
					Object[] parobj = new Object[]{dbh,tjr,tjrxm,dwbh,dwbh,shmkbh,shmkmc,mkbh,mkmc,bzs,lcid,lczt.replace("','",","),tgzt,tgztms,thzt,thztms,shrlm,shyjlm,shsjlm,shidlm,shztlm,shbmc};
					try{
						String str = db.queryForSingleValue("select count(*) from (" + inSql.toString() + ")", parobj);
						if(Integer.parseInt(Validate.isNullToDefault(str, "0").toString()) == 0){
							pd.put("msg", "{\"success\":\"false\",\"msg\":\"第" + bzs + "步审核中没有可以审核的人员，提交失败！\"}");
							return false;
						}
					}catch (Exception e){  
						logger.error("获取审核人员信息失败\n" + e.getCause().getMessage());
						pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
						return false;
					}
					
					sqllist.add(sql + inSql.toString());// + " or rybh = '000000' "
					objlist.add(parobj);
					
					if(j == 0){//第一步的时候把插入记录表的sql和初始化表中sfdqjd的sql放进去  注意跟外边这个语句的顺序，只有先把数据插进去，才能修改
						sqllist.add("insert into zc_sys_shjlb(sqmkbh,dbh,shr,shrxm,shrq,shbz) select ?,?,?,?,sysdate,? from dual");
						objlist.add(new Object[]{mkbh,dbh,tjr,tjrxm,"提交"});
						sqllist.add("update zc_sys_shcshb set sfdqjd = '1' where sqmkbh = ? and dbh = ? and bzs = ?");
						objlist.add(new Object[]{mkbh,dbh,bzs});
					}
				}
			}
			//这里判断的是申请的模块编号
			if(MenuFlag.ZCJZ_LYR.equals(mkbh)){//领用人建账
				sqllist.add("update zc_yshd set tjsj = sysdate,ztbz = '" + StateManager.ZCJZ_TJ + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_zjb set tjsj = sysdate,ztbz = '" + StateManager.ZCJZ_TJ + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				//附件表
				sqllist.add("update zc_fjb set ztbz = '" + StateManager.ZCJZ_TJ + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				//验收单附件表
				sqllist.add("update zc_yshdfjb set ztbz = '" + StateManager.ZCJZ_TJ + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				
			}else if(MenuFlag.ZCJZ_GLY.equals(mkbh)){//管理员建账
				sqllist.add("update zc_yshd set tjsj = sysdate,ztbz = '" + StateManager.ZCJZ_GLY_TG + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_zjb set tjsj = sysdate,ztbz = '" + StateManager.ZCJZ_GLY_TG + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				//附件表
				sqllist.add("update zc_fjb set ztbz = '" + StateManager.ZCJZ_GLY_TG + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				//验收单附件表
				sqllist.add("update zc_yshdfjb set ztbz = '" + StateManager.ZCJZ_GLY_TG + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCCZ_CZSQ.equals(mkbh)){//资产处置申请
				sqllist.add("update zc_czsqb set ztbz = '" + StateManager.ZCCZ_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_czsqmxb set ztbz = '" + StateManager.ZCCZ_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCCZ_GLYHZ.equals(mkbh)||MenuFlag.ZCCZ_ZCCZ.equals(mkbh)){//资产处置汇总或资产处置
				sqllist.add("update zc_czbgb set ztbz = '" + StateManager.ZCCZ_TJ + "'  where czbgbh" + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_czb set bdbz = '" + StateManager.ZCCZ_TJ + "' where czbgbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}
//			else if(MenuFlag.ZCBD_XMBD.equals(mkbh)||MenuFlag.ZCBD_BFBF.equals(mkbh)){//项目变动
//				sqllist.add("update zc_bdbgb set ztbz = '" + StateManager.ZCBD_TJ + "' where bdbgbh " + ToSqlUtil.getInsql(idarr));
//				objlist.add(idarr);
//				sqllist.add("update zc_bdb set bdbz = '" + StateManager.ZCBD_TJ + "' where bdbgbh " + ToSqlUtil.getInsql(idarr));
//				objlist.add(idarr);
//			}
			//项目变动,单价变动，部分报废，附加增加，附加处置，单位内调拨，单位间调拨，使用人变动，存放地点变动
			else if(MenuFlag.ZCBD_XMBD.equals(mkbh)||MenuFlag.ZCBD_DJBD.equals(mkbh)||MenuFlag.ZCBD_BFBF.equals(mkbh)||MenuFlag.ZCBD_FJZJ.equals(mkbh)||MenuFlag.ZCBD_FJCZ.equals(mkbh)||MenuFlag.ZCBD_DWJDB.equals(mkbh)||MenuFlag.ZCBD_DWNDB.equals(mkbh)||MenuFlag.ZCBD_SYRBD.equals(mkbh)||MenuFlag.ZCBD_CFDDBD.equals(mkbh)){
				sqllist.add("update zc_bdbgb set ztbz = '" + StateManager.ZCBD_TJ + "' where bdbgbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_bdb set bdbz = '" + StateManager.ZCBD_TJ + "' where bdbgbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCBD_BFDB.equals(mkbh)){//部分调拨
				sqllist.add("update zc_zcdbb set ztbz = '" + StateManager.ZCBD_TJ + "' where bdbgbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_zcdbmxb set ztbz = '" + StateManager.ZCBD_TJ + "' where bdbgbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCWX_WXSQ.equals(mkbh)){//维修申请
				sqllist.add("update zc_wxsqb set ztbz = '"+StateManager.WXSQ_TJ+"' where reportid" + ToSqlUtil.getInsql(idarr) + "and ztbz in ('"+StateManager.WXSQ_LR + "','" + StateManager.WXSQ_WTG + "')");
				objlist.add(idarr);
			}else if(MenuFlag.ZCWX_WXBG.equals(mkbh)){//维修报告
				sqllist.add("update zc_wxbgb set status = '" + StateManager.WXBGDJ_TJ + "' where orderid" + ToSqlUtil.getInsql(idarr) + " and status in ('" + StateManager.WXBGDJ_LR + "','" + StateManager.WXBGDJ_LD_WTG + "')");
				objlist.add(idarr);
			}else if(MenuFlag.ZCWX_JFZJ.equals(mkbh)){
				sqllist.add("update zc_wxjfzj set zjsqzt = '" + StateManager.WXJFZJ_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr) + "and zjsqzt in ('" + StateManager.WXJFZJ_LR + "','" + StateManager.WXJFZJ_DW_WTG + "')");
				objlist.add(idarr);
			}else if(MenuFlag.ZCXZ_XZTJ.equals(mkbh)){//资产闲置申请
				sqllist.add("update zc_xztj_sqb set ztbz = '" + StateManager.ZCBD_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_xztj_sqmxb set ztbz = '" + StateManager.ZCBD_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCXZ_XZTJLY.equals(mkbh)){//资产领用申请
				sqllist.add("update zc_xztj_lysqb set ztbz = '" + StateManager.ZCBD_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_xztj_lysqmxb set ztbz = '" + StateManager.ZCBD_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCQC_ZCZC.equals(mkbh)){//资产自查
				//update zc_zczcb k set k.qczt=( case  when k.qcqk ='1' then '"+sm.ZCZC_TG+"' when k.qcqk='2' then '"+sm.ZCZC_TJ+"'  end ) where k.qcbh in (select qcbh  from zc_zczczt where zt='1') and k.yqbh"+ToSqlUtil.getInsql(yqbh)
				sqllist.add("update zc_zczcb k set k.qczt = ( case  when k.qcqk ='1' then '"+StateManager.ZCZC_TG+"' when k.qcqk='2' then '"+StateManager.ZCZC_TJ+"' end) where k.qcbh in (select qcbh  from zc_zczczt where zt='1') and k.yqbh" + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.JMYQ_NSYWH.equals(mkbh)){//年使用信息维护
				sqllist.add("update zc_jmyqb set ztbz = '" + StateManager.JMYQ_TJ + "' where jybh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCWX_JFZJ.equals(mkbh)){
				sqllist.add("update zc_wxjfzj set zjsqzt = '" + StateManager.WXJFZJ_TJ + "' where sqbh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else if(MenuFlag.ZCJY_SQ.equals(mkbh)){
				sqllist.add("update zc_jysqb set ztbz = '" + StateManager.ZCJY_SQ_YTJ + "' where jysqbh" + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else{
				logger.error("ShenheDao.java里没有添加提交需要修改状态的语句");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
				return false;
			}
			if(db.batchUpdate(sqllist, objlist)){
				pd.put("msg", "{\"success\":\"true\",\"msg\":\"提交成功！\"}");
				sendMsg(keyid,mkbh);
				return true;
			}else{
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
				return false;
			}
		}
	}
	
	/**公共撤销方法*/
	public boolean doRevoke(PageData pd){
		List<String> sqllist = new ArrayList();
		List<Object[]> objlist = new ArrayList();
		
		String mkbh = pd.getString("mkbh");
		String keyid = pd.getString("keyid");
		String jzrlx = "1";//建账人类型，只有管理员建账处的撤回才会用到，默认是1，只有领用人建账是0
		if(MenuFlag.ZCJZ_GLY.equals(mkbh)){//管理员建账
			jzrlx = pd.getString("jzrlx");
		}
		String[] idarr = keyid.split(",");
		String tjr = LUser.getRybh();
		String tjrxm = LUser.getXm();
		if("sjdjk".equals(pd.getString("sjdjk"))){//手机端
			tjr = pd.getString("rybh");
			tjrxm = pd.getString("xm");
		}
		String bqz = SystemSet.sysBz;
		
		if(Validate.isNull(mkbh) || Validate.isNull(keyid)){
			logger.error("传递参数有误,mkbh:" + mkbh + ",keyid:" + keyid);
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"撤销失败！\"}");
			return false;
		}
		
		for(int i = 0; i < idarr.length; i++){
			String dbh = idarr[i];
			//如果是领用人建账的管理员确认之后的撤销，则在撤销之后流程并没有结束，所以应该是把初始化表中的数据改成待管理员确认的状态
			//注意：如果是领用人建账的管理员确认之后的撤销，则这里传过来的mkbh是初始化表中的审核模块编号，其他时候传过来的mkbh是申请模块编号
			if("0".equals(jzrlx)){
				sqllist.add("update " + bqz + "shcshb set sfdqjd = '0' where sqmkbh = (select sqmkbh from zc_sys_shcshb where mkbh = ? and dbh = ? group by sqmkbh) and dbh = ? and sfdqjd = '1'");
				objlist.add(new Object[]{mkbh,dbh,dbh});
				sqllist.add("update " + bqz + "shcshb set sfdqjd = '1',sjshry='',sjshsj='',shyj='',sjshryxm='' where mkbh = ? and dbh = ?");
				objlist.add(new Object[]{mkbh,dbh});
				sqllist.add("insert into " + bqz + "shjlb(sqmkbh,dbh,shr,shrxm,shrq,shmkbh,shbz) select (select sqmkbh from zc_sys_shcshb where mkbh = ? and dbh = ? group by sqmkbh),?,?,?,sysdate,?,? from dual");
				objlist.add(new Object[]{mkbh,dbh,dbh,tjr,tjrxm,mkbh,"撤销"});
			}else{
				sqllist.add("delete from " + bqz + "shcshb where sqmkbh = ? and dbh = ? ");
				objlist.add(new Object[]{mkbh,dbh});
				sqllist.add("insert into " + bqz + "shjlb(sqmkbh,dbh,shr,shrxm,shrq,shbz) select ?,?,?,?,sysdate,? from dual");
				objlist.add(new Object[]{mkbh,dbh,tjr,tjrxm,"撤销"});
			}
		}
		
		//这里判断的是申请的模块编号
		if(MenuFlag.ZCJZ_LYR.equals(mkbh) ){//领用人建账
			sqllist.add("update zc_yshd set ztbz = '"+StateManager.ZCJZ_LR + "' where ysdh " + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
			sqllist.add("update zc_zjb set ztbz = '" + StateManager.ZCJZ_LR + "' where ysdh " + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
		}else if(MenuFlag.ZCJZ_GLY.equals(mkbh)){//管理员建账
			if("1".equals(jzrlx)){//管理员建账撤回
				sqllist.add("update zc_yshd set ztbz = '"+StateManager.ZCJZ_LR + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_zjb set ztbz = '" + StateManager.ZCJZ_LR + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}else{//领用人建账的管理员确认之后撤回
				sqllist.add("update zc_yshd set ztbz = '"+StateManager.ZCJZ_TJ + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
				sqllist.add("update zc_zjb set ztbz = '" + StateManager.ZCJZ_TJ + "' where ysdh " + ToSqlUtil.getInsql(idarr));
				objlist.add(idarr);
			}
		}else if(MenuFlag.ZCCZ_CZSQ.equals(mkbh)){//资产处置申请
			sqllist.add("update zc_czsqb set ztbz='"+StateManager.ZCCZ_LR+"' where sqbh " + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
			sqllist.add("update zc_czsqmxb set ztbz='"+StateManager.ZCCZ_LR+"' where sqbh " + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
		}else if(MenuFlag.ZCCZ_GLYHZ.equals(mkbh)||MenuFlag.ZCCZ_ZCCZ.equals(mkbh)){//资产处置汇总或资产处置
			sqllist.add("update zc_czbgb set ztbz='"+StateManager.ZCCZ_LR+"' where czbgbh" + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
			sqllist.add("update zc_czb set bdbz='"+StateManager.ZCCZ_LR+"' where czbgbh " + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
		}else if(MenuFlag.ZCWX_WXSQ.equals(mkbh)){
			sqllist.add("update zc_wxsqb set ztbz = '"+StateManager.WXSQ_LR+"' where reportid" + ToSqlUtil.getInsql(idarr) + "and ztbz = '"+StateManager.WXSQ_TJ + "'");
			objlist.add(idarr);
		}else if(MenuFlag.ZCWX_WXBG.equals(mkbh)){
			sqllist.add("update zc_wxbgb set status = '" + StateManager.WXBGDJ_LR + "' where orderid" + ToSqlUtil.getInsql(idarr) + " and status = '" + StateManager.WXBGDJ_TJ + "'");
			objlist.add(idarr);
		}else if(MenuFlag.ZCWX_JFZJ.equals(mkbh)){
			sqllist.add("update zc_wxjfzj set zjsqzt = '" + StateManager.WXJFZJ_LR + "' where sqbh" + ToSqlUtil.getInsql(idarr) + "and zjsqzt = '" + StateManager.WXJFZJ_TJ + "'");
			objlist.add(idarr);
		}else if(MenuFlag.ZCXZ_XZTJ.equals(mkbh)){//资产闲置申请
			sqllist.add("update zc_xztj_sqb set ztbz='"+StateManager.ZCCZ_LR+"'  where sqbh" + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
			sqllist.add("update zc_xztj_sqmxb set ztbz='"+StateManager.ZCCZ_LR+"' where sqbh" + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
		}else if(MenuFlag.ZCXZ_XZTJLY.equals(mkbh)){//资产领用申请
			sqllist.add("update ZC_XZTJ_LYSQB set ztbz='"+StateManager.ZCCZ_LR+"' where sqbh" + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
			sqllist.add("update ZC_XZTJ_LYSQMXB set ztbz='"+StateManager.ZCCZ_LR+"' where sqbh" + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
		}else if(MenuFlag.JMYQ_NSYWH.equals(mkbh)){//年使用信息维护
			sqllist.add("update zc_jmyqb set ztbz='"+StateManager.JMYQ_FB+"' where jybh" + ToSqlUtil.getInsql(idarr));
			objlist.add(idarr);
		}else{
			logger.error("ShenheDao.java里没有添加撤销需要修改状态的语句");
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"提交失败！\"}");
			return false;
		}
		if(db.batchUpdate(sqllist, objlist)){
			pd.put("msg", "{\"success\":\"true\",\"msg\":\"撤销成功！\"}");
			sendMsg(keyid,mkbh);
			return true;
		}else{
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"撤销失败！\"}");
			return false;
		}
	}
	
	/**公共审核退回方法*/
	public boolean doBack(PageData pd){
		String mkbh = pd.getString("mkbh");//审核模块编号
		String keyid = "["+pd.getString("keyid")+"]";
		JSONArray jsar = new JSONArray(keyid);
		String shr = LUser.getRybh();
		String shrxm = LUser.getXm();
		if("sjdjk".equals(pd.getString("sjdjk"))){//手机端
			shr = pd.getString("rybh");
			shrxm = pd.getString("xm");
		}
		String bqz = SystemSet.sysBz;
		if(Validate.isNull(mkbh) || Validate.isNull(keyid)){
			logger.error("传递参数有误,mkbh:" + mkbh + ",keyid:" + keyid);
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
			return false;
		}
		List<String> sqllist = new ArrayList();
		List<Object[]> objlist = new ArrayList();
		
		String bzs = "";
		String next_bzs = "";
		String sqmkbh = "";
		for(int i = 0; i < jsar.length(); i++){
			String dbh = jsar.getJSONObject(i).get("dbh").toString();
			String shyj = Validate.isNullToDefaultString(jsar.getJSONObject(i).get("shyj")+"","");
			
			if(Validate.isNull(dbh) || "".equals(dbh)){
				logger.error("没有获取到单编号的信息");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
				return false;
			}
			//目前同一个审核模块中是不会有相同的单编号的，所以通过单编号和审核模块编号可以获取到申请模块编号，以后如果单编号会重复，需要另外处理
			String sql = "select sqmkbh from " + bqz + "shcshb where dbh = ? and mkbh = ? group by sqmkbh";
			sqmkbh = db.queryForSingleValue(sql, new Object[]{dbh,mkbh});
			if(Validate.isNull(sqmkbh)){
				logger.error("获取不到" + bqz + "shcshb表中该单据的信息");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
				return false;
			}
			//获取该单据最小的步骤数，目前最小的都应该是1，不排除以后会变，所以这里还是通过数据库获取最小的步骤数
			sql = "select min(bzs) from " + bqz + "shcshb where dbh = ? and sqmkbh = ?";
			bzs = db.queryForSingleValue(sql, new Object[]{dbh,sqmkbh});
			int zxbzs = 0;
			if(Validate.isNull(bzs)){
				logger.error("获取不到" + bqz + "shcshb表中该单据的最小步骤数");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
				return false;
			}else{
				zxbzs = Integer.parseInt(bzs.trim());
			}
			//获取当前节点的信息
			sql = "select dbh,rybh,tjr,mkbh,mkmc,tjsj,sfdqjd,tjrxm,sqmkbh,sqmkmc,bzs,sjshry,sjshsj,djdw,djdwmc,lczt,tgzt,tgztms,thzt,thztms,shrlm,shyjlm,shsjlm,shidlm,shztlm,shbmc from " + bqz + "shcshb where dbh = ? and mkbh = ? and sqmkbh = ? and sfdqjd = '1' and rybh = ?";
			Map dqmap = new HashMap();
			try{
				dqmap = db.queryForMap(sql, dbh, mkbh, sqmkbh, shr);
			}catch (Exception e){  
				logger.error("获取" + bqz + "shcshb表中该单据当前节点的信息时报错\n" + e.getCause().getMessage());
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
				return false;
			}
			if(dqmap.isEmpty()){
				logger.error("没有获取到" + bqz + "shcshb表中该单据当前节点的信息\n");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"该单据已经被审核，请刷新后重试！\"}");
				return false;
			}
			bzs = dqmap.get("BZS") + "";
			int dqbzs = 0;
			if(Validate.isNull(bzs)){
				logger.error("获取不到" + bqz + "shcshb表中该单据的当前步骤数");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
				return false;
			}else{
				dqbzs = Integer.parseInt(bzs.trim());
			}
			
			//boolean isend = false;
			if(zxbzs == dqbzs){//是第一步的话，要把初始化表里的数据删掉
				//isend = true;
				sqllist.add("delete from " + bqz + "shcshb where sqmkbh = ? and dbh = ?");
				objlist.add(new Object[]{sqmkbh,dbh});
			}else{//不是第一步的话，要把初始化表里当前节点的信息更新进去，上一节点的sfdqjd字段改成1
				sql = "select max(bzs) from " + bqz + "shcshb where dbh = ? and sqmkbh = ? and bzs < ?";
				next_bzs = db.queryForSingleValue(sql, new Object[]{dbh,sqmkbh,dqbzs});
				if(Validate.isNull(next_bzs)){
					logger.error("获取不到" + bqz + "shcshb表中该单据的上一步的步骤数");
					pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
					return false;
				}else{
					next_bzs = next_bzs.trim();
				}
			}
			
			sqllist.add("update " + bqz + "shcshb set sfdqjd='0',sjshry=?,sjshryxm=?,sjshsj=sysdate,shyj=? where mkbh = ? and dbh = ? and bzs = ?");
			objlist.add(new Object[]{shr,shrxm,shyj,mkbh,dbh,dqbzs});
			sqllist.add("update " + bqz + "shcshb set sfdqjd = '1',sjshry='',sjshryxm='',sjshsj='',shyj='' where sqmkbh = ? and dbh = ? and bzs = ?");
			objlist.add(new Object[]{sqmkbh,dbh,next_bzs});
			//插入审核记录表
			sqllist.add("insert into " + bqz + "shjlb(sqmkbh,dbh,shr,shrxm,shrq,shyj,shmkbh,shbz) select ?,?,?,?,sysdate,?,?,? from dual");
			objlist.add(new Object[]{sqmkbh,dbh,shr,shrxm,shyj,mkbh,"退回"});
			//修改原表中的信息
			String shbmc = Validate.isNullToDefault(dqmap.get("SHBMC"), "") + "";
			String lczt = Validate.isNullToDefault(dqmap.get("LCZT"), "") + "";
			lczt = lczt.replace(",", "','");
			//String tgzt = Validate.isNullToDefault(dqmap.get("TGZT"), "") + "";
			String thzt = Validate.isNullToDefault(dqmap.get("THZT"), "") + "";
			String shrlm = Validate.isNullToDefault(dqmap.get("SHRLM"), "") + "";
			String shyjlm = Validate.isNullToDefault(dqmap.get("SHYJLM"), "") + "";
			String shsjlm = Validate.isNullToDefault(dqmap.get("SHSJLM"), "") + "";
			String shidlm = Validate.isNullToDefault(dqmap.get("SHIDLM"), "") + "";
			String shztlm = Validate.isNullToDefault(dqmap.get("SHZTLM"), "") + "";
			//修改原表中的信息
			//sqllist.add("update " + shbmc + " set (" + shrlm + "," + shyjlm + "," + shsjlm + "," + shztlm + ") = (select ?,?,sysdate,? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
			sqllist.add("update " + shbmc + " set (" + shrlm + "," + shyjlm + "," + shsjlm + "," + shztlm + ") = (select ?,?,sysdate,? from dual) where " + shidlm + " = ?  ");
			objlist.add(new Object[]{shr,shyj,thzt,dbh});
			//如果还有其他sql语句，可以添加到这里
			if(MenuFlag.ZCJZ_LYR.equals(sqmkbh) || MenuFlag.ZCJZ_GLY.equals(sqmkbh)){//领用人建账 || 管理员建账
				sqllist.add("update zc_zjb set (" + shrlm + "," + shyjlm + "," + shsjlm + "," + shztlm + ") = (select ?,?,sysdate,? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
				objlist.add(new Object[]{shr,shyj,thzt,dbh});
				sqllist.add("update zc_yshdfjb set (" + shztlm + ") = (select ? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
				
				objlist.add(new Object[]{thzt,dbh});
				sqllist.add("update zc_fjb set (" + shztlm + ") = (select ? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
				objlist.add(new Object[]{thzt,dbh});
			}//项目变动,单价变动，部分报废，附加增加，附加处置，单位内调拨，单位间调拨，使用人变动，存放地点变动
			else if(MenuFlag.ZCBD_XMBD.equals(sqmkbh)||MenuFlag.ZCBD_DJBD.equals(sqmkbh)||MenuFlag.ZCBD_BFBF.equals(sqmkbh)||MenuFlag.ZCBD_FJCZ.equals(sqmkbh)||MenuFlag.ZCBD_FJZJ.equals(sqmkbh)||MenuFlag.ZCBD_DWNDB.equals(sqmkbh)||MenuFlag.ZCBD_DWJDB.equals(sqmkbh)||MenuFlag.ZCBD_SYRBD.equals(sqmkbh)||MenuFlag.ZCBD_CFDDBD.equals(sqmkbh)){
				sqllist.add("update zc_bdb set bdbz = ? where " + shidlm + " = ? and bdbz in ('" + lczt + "') ");
				objlist.add(new Object[]{thzt,dbh});
			}else if(MenuFlag.ZCBD_BFDB.equals(sqmkbh)){//资产变动==>部分调拨
				sqllist.add("update zc_zcdbmxb set ZTBZ = ? where " + shidlm + " = ? and ZTBZ in ('" + lczt + "') ");
				objlist.add(new Object[]{thzt,dbh});
			}else if(MenuFlag.ZCCZ_CZSQ.equals(sqmkbh)){//资产处置申请
				sqllist.add("update zc_czsqmxb set ztbz=? where sqbh=? ");
				objlist.add(new Object[]{thzt,dbh});
			}else if(MenuFlag.ZCCZ_GLYHZ.equals(sqmkbh)||MenuFlag.ZCCZ_ZCCZ.equals(sqmkbh)){//资产处置汇总或资产处置
				sqllist.add("update zc_czb set bdbz=? where czbgbh=? ");
				objlist.add(new Object[]{thzt,dbh});
			}else if(MenuFlag.ZCXZ_XZTJ.equals(sqmkbh)){//资产闲置申请
				sqllist.add("update zc_xztj_sqmxb set ztbz=? where sqbh=? ");
				objlist.add(new Object[]{thzt,dbh});
			}else if(MenuFlag.ZCXZ_XZTJLY.equals(sqmkbh)){//资产闲置领用申请
				sqllist.add("update zc_xztj_lysqmxb set ztbz=? where sqbh=? ");
				objlist.add(new Object[]{thzt,dbh});
			}
		}
		if(db.batchUpdate(sqllist, objlist)){
			pd.put("msg", "{\"success\":\"true\",\"msg\":\"审核退回成功！\"}");
			sendMsg(keyid,sqmkbh);
			return true;
		}else{
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核退回失败！\"}");
			return false;
		}
	}
	
	/**公共审核通过方法*/
	public boolean doCheck(PageData pd){
		String mkbh = pd.getString("mkbh");//审核模块编号
		String keyid = "["+pd.getString("keyid")+"]";
		JSONArray jsar = new JSONArray(keyid);
		String shr = LUser.getRybh();
		String shrxm = LUser.getXm();
		if("sjdjk".equals(pd.getString("sjdjk"))){//手机端
			shr = pd.getString("rybh");
			shrxm = pd.getString("xm");
		}
		String bqz = SystemSet.sysBz;
		if(Validate.isNull(mkbh) || Validate.isNull(keyid)){
			logger.error("传递参数有误,mkbh:" + mkbh + ",keyid:" + keyid);
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
			return false;
		}
		
		List<String> sqllist = new ArrayList();
		List<Object[]> objlist = new ArrayList();
		String bzs = "";
		String next_bzs = "";
		String sqmkbh = "";
		String wxbgbh = "";//用来存放维修报告财务审核时，如果需要把维修费用存入变动报告表，这里用来拼接维修报告的编号，其他时候没有用
		for(int i = 0; i < jsar.length(); i++){
			String dbh = jsar.getJSONObject(i).get("dbh").toString();
			String shyj = Validate.isNullToDefaultString(jsar.getJSONObject(i).get("shyj")+"","");
			keyid=dbh;
			if(Validate.isNull(dbh) || "".equals(dbh)){
				logger.error("没有获取到单编号的信息");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
				return false;
			}
			//目前同一个审核模块中是不会有相同的单编号的，所以通过单编号和审核模块编号可以获取到申请模块编号，以后如果单编号会重复，需要另外处理
			String sql = "select sqmkbh from " + bqz + "shcshb where dbh = ? and mkbh = ? group by sqmkbh";
			sqmkbh = db.queryForSingleValue(sql, new Object[]{dbh,mkbh});
			if(Validate.isNull(sqmkbh)){
				logger.error("获取不到" + bqz + "shcshb表中该单据的信息");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
				return false;
			}
			//获取该单据最大的步骤数
			sql = "select max(bzs) from " + bqz + "shcshb where dbh = ? and sqmkbh = ?";
			bzs = db.queryForSingleValue(sql, new Object[]{dbh,sqmkbh});
			int zdbzs = 0;
			if(Validate.isNull(bzs)){
				logger.error("获取不到" + bqz + "shcshb表中该单据的最大步骤数");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
				return false;
			}else{
				zdbzs = Integer.parseInt(bzs.trim());
			}
			//获取当前节点的信息
			sql = "select dbh,rybh,tjr,mkbh,mkmc,tjsj,sfdqjd,tjrxm,sqmkbh,sqmkmc,bzs,sjshry,sjshsj,djdw,djdwmc,lczt,tgzt,tgztms,thzt,thztms,shrlm,shyjlm,shsjlm,shidlm,shztlm,shbmc from " + bqz + "shcshb where dbh = ? and mkbh = ? and sqmkbh = ? and sfdqjd = '1' and rybh = ?";
			Map dqmap = new HashMap();
			try{
				dqmap = db.queryForMap(sql, dbh, mkbh, sqmkbh, shr);
			}catch (Exception e){
				logger.error("获取" + bqz + "shcshb表中该单据当前节点的信息时报错\n" + e.getCause().getMessage());
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
				return false;
			}
			if(dqmap.isEmpty()){
				logger.error("没有获取到" + bqz + "shcshb表中该单据当前节点的信息\n");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"该单据已经被审核，请刷新后重试！\"}");
				return false;
			}
			bzs = dqmap.get("BZS") + "";
			int dqbzs = 0;
			if(Validate.isNull(bzs)){
				logger.error("获取不到" + bqz + "shcshb表中该单据的当前步骤数");
				pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
				return false;
			}else{
				dqbzs = Integer.parseInt(bzs.trim());
			}
			//获取和审核有关的字段信息，放在此处以方便下方的sql中用到
			String shbmc = Validate.isNullToDefault(dqmap.get("SHBMC"), "") + "";
			String lczt = Validate.isNullToDefault(dqmap.get("LCZT"), "") + "";
			lczt = lczt.replace(",", "','");
			String tgzt = Validate.isNullToDefault(dqmap.get("TGZT"), "") + "";
			//String thzt = Validate.isNullToDefault(dqmap.get("THZT"), "") + "";
			String shrlm = Validate.isNullToDefault(dqmap.get("SHRLM"), "") + "";
			String shyjlm = Validate.isNullToDefault(dqmap.get("SHYJLM"), "") + "";
			String shsjlm = Validate.isNullToDefault(dqmap.get("SHSJLM"), "") + "";
			String shidlm = Validate.isNullToDefault(dqmap.get("SHIDLM"), "") + "";
			String shztlm = Validate.isNullToDefault(dqmap.get("SHZTLM"), "") + "";
			//修改原表中的信息
			sqllist.add("update " + shbmc + " set (" + shrlm + "," + shyjlm + "," + shsjlm + "," + shztlm + ") = (select ?,?,sysdate,? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
			objlist.add(new Object[]{shr,shyj,tgzt,dbh});
			//如果还有其他sql语句，可以添加到这里（注意：加在这里的sql是每一次审核通过都要走的语句，如果只在最后一步审核通过中执行的sql，请加在下边的if(zdbzs == dqbzs){}里边）
			if(MenuFlag.ZCJZ_LYR.equals(sqmkbh) || MenuFlag.ZCJZ_GLY.equals(sqmkbh)){//领用人建账 || 管理员建账
				sqllist.add("update zc_zjb set (" + shrlm + "," + shyjlm + "," + shsjlm + "," + shztlm + ") = (select ?,?,sysdate,? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
				objlist.add(new Object[]{shr,shyj,tgzt,dbh});
				sqllist.add("update zc_yshdfjb set (" + shztlm + ") = (select ? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
				objlist.add(new Object[]{tgzt,dbh});
				sqllist.add("update zc_fjb set (" + shztlm + ") = (select ? from dual) where " + shidlm + " = ? and " + shztlm + " in ('" + lczt + "') ");
				objlist.add(new Object[]{tgzt,dbh});
			}//项目变动,单价变动，部分报废，附加增加，附加处置，单位内调拨，单位间调拨，使用人变动，存放地点变动
			else if(MenuFlag.ZCBD_XMBD.equals(sqmkbh)||MenuFlag.ZCBD_DJBD.equals(sqmkbh)||MenuFlag.ZCBD_BFBF.equals(sqmkbh)||MenuFlag.ZCBD_FJCZ.equals(sqmkbh)||MenuFlag.ZCBD_FJZJ.equals(sqmkbh)||MenuFlag.ZCBD_DWNDB.equals(sqmkbh)||MenuFlag.ZCBD_DWJDB.equals(sqmkbh)||MenuFlag.ZCBD_SYRBD.equals(sqmkbh)||MenuFlag.ZCBD_CFDDBD.equals(sqmkbh)){
				sqllist.add("update zc_bdb set bdbz = ? where " + shidlm + " = ? and bdbz in ('" + lczt + "') ");
				objlist.add(new Object[]{tgzt,dbh});
			}else if(MenuFlag.ZCBD_BFDB.equals(sqmkbh)){//资产变动==>部分调拨
				sqllist.add("update zc_zcdbmxb set ZTBZ = ? where " + shidlm + " = ? and ZTBZ in ('" + lczt + "') ");
				objlist.add(new Object[]{tgzt,dbh});
			}else if(MenuFlag.ZCCZ_CZSQ.equals(sqmkbh)){//资产处置申请
				sqllist.add("update zc_czsqmxb set ztbz=? where sqbh=? ");
				objlist.add(new Object[]{tgzt,dbh});
			}else if(MenuFlag.ZCCZ_GLYHZ.equals(sqmkbh)||MenuFlag.ZCCZ_ZCCZ.equals(sqmkbh)){//资产处置汇总或资产处置
				sqllist.add("update zc_czb set bdbz=? where czbgbh=? ");
				objlist.add(new Object[]{tgzt,dbh});
			}else if(MenuFlag.ZCXZ_XZTJ.equals(sqmkbh)){//资产闲置申请
				sqllist.add("update zc_xztj_sqmxb set ztbz=?,xz='E' where sqbh=? ");
				objlist.add(new Object[]{tgzt,dbh});
			}else if(MenuFlag.ZCXZ_XZTJLY.equals(sqmkbh)){//资产闲置领用申请
				sqllist.add("update zc_xztj_lysqmxb set ztbz=? where sqbh=? ");
				objlist.add(new Object[]{tgzt,dbh});
			}
			
			if(zdbzs == dqbzs){//是最后一步的话，要把关联的表里的数据更新（注意：这里边只是最后一步审核通过才会走的sql，如果每次审核通过之后都需要执行的sql语句，请放在上边带有注释的地方）
				if(MenuFlag.ZCJZ_GLY.equals(sqmkbh) || MenuFlag.ZCJZ_LYR.equals(sqmkbh)){
					sqllist.add("update zc_yshdfjb set ztbz = ? where ysdh = ?");
					objlist.add(new Object[]{tgzt,dbh});
					sqllist.add("update zc_fjb set ztbz = ? where ysdh = ?");
					objlist.add(new Object[]{tgzt,dbh});
				}else if(MenuFlag.ZCBD_XMBD.equals(sqmkbh)){//项目变动 ——现状，存放地点，使用人，使用单位附件随主件一起改 
					String sqlD = "and "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"' and rownum=1),bdzt='"+StateManager.BDZT_Normal+"' where yqbh in (select fjbh from zc_bdb where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"'";
					String sqlDFJ = "and "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"' and rownum=1) where substr(fjbh,1,length(fjbh)-3) in (select fjbh from zc_bdb where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"'";
					sqllist.add("update zc_zjb t set syr = (select bdhnr from zc_bdb where bdxm = '使用人' " + sqlD + " and bdxm = '使用人')");
					objlist.add(null);
					sqllist.add("update zc_fjb t set syr = (select bdhnr from zc_bdb where bdxm = '使用人' " +   sqlDFJ    + " and bdxm = '使用人') and xz<>'6' ");
					objlist.add(null);
					sqllist.add("update zc_zjb t set sydw = (select bdhnr from zc_bdb where bdxm = '使用单位' " + sqlD + " and bdxm = '使用单位')");
					objlist.add(null);
					sqllist.add("update zc_fjb t set sydw = (select bdhnr from zc_bdb where bdxm = '使用单位' " + sqlDFJ + " and bdxm = '使用单位')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set bzxx = (select bdhnr from zc_bdb where bdxm = '存放地点' " + sqlD + " and bdxm = '存放地点')");
					objlist.add(null);
					sqllist.add("update zc_fjb t set bzxx = (select bdhnr from zc_bdb where bdxm = '存放地点' " + sqlDFJ + " and bdxm = '存放地点')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set xz=(select bdhnr from zc_bdb where bdxm='现状' " + sqlD + " and bdxm='现状')");
					objlist.add(null);
					sqllist.add("update zc_fjb t set xz=(select bdhnr from zc_bdb where bdxm='现状' " + sqlDFJ + " and bdxm='现状')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set syfx = (select bdhnr from zc_bdb where bdxm='使用方向' " + sqlD + " and bdxm='使用方向')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set cz6=fun_getcz6(t.flh,t.syfx) where yqbh in (select fjbh from zc_bdb where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"' and bdxm='使用方向')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set jkdj=(select bdhnr from zc_bdb where bdxm='层数' " + sqlD + " and bdxm='层数')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set sykh=(select bdhnr from zc_bdb where bdxm='册数' " + sqlD + " and bdxm='册数')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set fjzj=(select bdhnr from zc_bdb where bdxm='建筑面积' " + sqlD + " and bdxm='建筑面积')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set fw_zrjs=(select bdhnr from zc_bdb where bdxm='自然间数' " + sqlD + " and bdxm='自然间数')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set gzyq=(select bdhnr from zc_bdb where bdxm='附属设施' " + sqlD + " and bdxm='附属设施')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set fjzj=(select bdhnr from zc_bdb where bdxm='土地面积' " + sqlD + " and bdxm='土地面积')");
					objlist.add(null);
					sqllist.add("update zc_zjb t set sykh=sykh-(select to_number(bdhnr) from zc_bdb where bdxm='数量' and "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"' and rownum=1),bdzt='"+StateManager.BDZT_Normal+"' where yqbh in(select fjbh from zc_bdb where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"' and bdxm='数量')");
					objlist.add(null);
					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_XMBD+"' ");
					objlist.add(null);
					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_XMBD+"' ");
					objlist.add(null);
				}else if(MenuFlag.ZCBD_DJBD.equals(sqmkbh)){//单价变动财务审核是最后一步，改变主机表的单价、总价（注：总价和sykh没关系，资产增加的时候就没有关系）、变动状态
					sqllist.add("update zc_zjb set dj = nvl(dj,0)+nvl((select bddj from zc_bdbgb where bdbgbh = ?),0),zzj = nvl(zzj,0)+nvl((select bddj from zc_bdbgb where bdbgbh = ?),0),bdzt=? where yqbh in (select fjbh from zc_bdb where bdbgbh = ?)");
					objlist.add(new Object[]{keyid,keyid,StateManager.BDZT_Normal,keyid});
					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_DJBD+"' ");
					objlist.add(null);
					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_DJBD+"' ");
					objlist.add(null);
				}else if(MenuFlag.ZCBD_BFDB.equals(sqmkbh)){//部分调拨，修改主机表数据，同时生成主机表的主键（拆分几条插入几条）
					String yqbh = "";
					try{
						yqbh = db.queryForSingleValue("select yqbh from zc_zcdbb where bdbgbh = ?", new Object[]{keyid});
					}catch(Exception e){
						logger.error("获取原资产信息时报错，" + e.getMessage());
						pd.put("msg", "{\"success\":\"false\",\"msg\":\"获取原资产信息失败！\"}");
						return false;
					}
					
					sqllist.add("update zc_zjb z set bdzt = ?,sykh=z.sykh-nvl((select sum(sl) from zc_zcdbmxb where bdbgbh = ?),0),zzj=z.zzj-nvl((select sum(je) from zc_zcdbmxb where bdbgbh = ?),0),dj=z.dj-nvl((select sum(je) from zc_zcdbmxb where bdbgbh = ?),0) where yqbh = ?");
					objlist.add(new Object[]{StateManager.BDZT_Normal,keyid,keyid,keyid,yqbh});
					List mxlist = db.queryForList("select * from zc_zcdbmxb where bdbgbh =?",new Object[]{keyid});
					String flh  = db.queryForSingleValue("select flh from zc_zjb where yqbh =?",new Object[]{yqbh});
					List bh = AutoKey.createYqbh(flh, mxlist.size());
					for(int k = 0; k < bh.size(); k++){
						Map mxmap = (Map)mxlist.get(k);
						String newyqbh = ((Map)bh.get(k)).get("YQBH") + "";
						sqllist.add("insert into zc_zjb(yqbh,flh,flmc,yqmc,dj,jldw,zzj,dzrrq,sydw,syr,syfx,xz,jfkm,zcly,gzrq,ysdh,dabh,"
								+ "jdr,ztbz,jdrq,shr,shrq,shyj,rzrq,cz10,cz6,bmsx,bzxx,sccj,fjzj,fjs,gbm,cch,ccrq,xss,djh,xh,gg,wbzl,"
								+ "wbje,jkdj,jsh,gzyq,synx,htbh,cgr,kyxm,fzr,zdsys,jcnf,sykh,zj,czrq,pzh,jzbz,gkdw,gkry,gkyj,gkrq,xk,"
								+ "xklb,jzlx,xmh,cllx,clbz,ccly,syxz,bz,czr,fzrq,fgxs,gnmj,wfmj,fwyt,symj,jsr,pgjz,djrq,djjg,zlh,pzwh,"
								+ "gljg,tjsj,jzxs,glbm,ljzj,zmjz,zjjt,cgzzxs,cjdfdw,czdfdw,btzdw,jj,td_qszm,td_qsxz,td_fzrq,zmmj,"
								+ "td_dh,td_zymj,td_cjmj,td_czmj,td_jymj,td_xzmj,td_qtmj,zyjz,cjjz,czjz,jyjz,xzjz,qtjz,td_rzkm,"
								+ "td_qsnx,td_yt,td_qdjz,td_dymj,td_ftmj,fw_fgxs,cqxs,qsxz,fw_qszm,dxmj,dsmj,fw_gnmj,qsrq,fw_fzrq,"
								+ "fw_bgmj,fw_hymj,fw_ckmj,fw_stmj,fw_pdmj,fw_jfmj,fw_zymj,fw_jymj,fw_cjmj,fw_czmj,fw_xzmj,fw_qtmj,"
								+ "fw_zrjs,pp,sb_gl,bxjzrq,sb_dyjlb,jt_bzqk,jt_clcd,jt_cpxh,jt_pql,jt_ppxz,jt_zcnf,jt_cllx,jt_clzws,"
								+ "jt_syxz,jj_bgzylb,jj_sflb,wx_pgjz,wx_djrq,wx_djjg,wx_zlh,wx_pzwh,wx_gljg,wx_ntxe,wx_fmmc,zyyt,bdbz,"
								+ "flgbm,gbmid,bdzt,tph,yt,jybyqbh,jczl,zjff,rj_ppgsd,rj_lx,rj_sfzb,rj_sqxklx,rj_sqxkqx,"
								+ "rj_zdsqs,wx_kff,wx_xcfs,glyyj,glyrq,glyry,glydw,jzrlx,czzj,fczzj,wwmc,cqly,tddj)"
								+ "select ?,flh,flmc,yqmc,?,jldw,?,dzrrq,?,?,syfx,xz,jfkm,zcly,gzrq,ysdh,dabh,"
								+ "jdr,ztbz,jdrq,shr,shrq,shyj,rzrq,cz10,cz6,bmsx,?,sccj,fjzj,fjs,gbm,cch,ccrq,xss,djh,xh,gg,wbzl,"
								+ "wbje,jkdj,jsh,gzyq,synx,htbh,cgr,kyxm,fzr,zdsys,jcnf,?,zj,czrq,pzh,jzbz,gkdw,gkry,gkyj,gkrq,xk,"
								+ "xklb,jzlx,xmh,cllx,clbz,ccly,syxz,bz,czr,fzrq,fgxs,gnmj,wfmj,fwyt,symj,jsr,pgjz,djrq,djjg,zlh,pzwh,"
								+ "gljg,tjsj,jzxs,glbm,ljzj,zmjz,zjjt,cgzzxs,cjdfdw,czdfdw,btzdw,jj,td_qszm,td_qsxz,td_fzrq,zmmj,"
								+ "td_dh,td_zymj,td_cjmj,td_czmj,td_jymj,td_xzmj,td_qtmj,zyjz,cjjz,czjz,jyjz,xzjz,qtjz,td_rzkm,"
								+ "td_qsnx,td_yt,td_qdjz,td_dymj,td_ftmj,fw_fgxs,cqxs,qsxz,fw_qszm,dxmj,dsmj,fw_gnmj,qsrq,fw_fzrq,"
								+ "fw_bgmj,fw_hymj,fw_ckmj,fw_stmj,fw_pdmj,fw_jfmj,fw_zymj,fw_jymj,fw_cjmj,fw_czmj,fw_xzmj,fw_qtmj,"
								+ "fw_zrjs,pp,sb_gl,bxjzrq,sb_dyjlb,jt_bzqk,jt_clcd,jt_cpxh,jt_pql,jt_ppxz,jt_zcnf,jt_cllx,jt_clzws,"
								+ "jt_syxz,jj_bgzylb,jj_sflb,wx_pgjz,wx_djrq,wx_djjg,wx_zlh,wx_pzwh,wx_gljg,wx_ntxe,wx_fmmc,zyyt,bdbz,"
								+ "flgbm,gbmid,?,tph,yt,jybyqbh,jczl,zjff,rj_ppgsd,rj_lx,rj_sfzb,rj_sqxklx,rj_sqxkqx,"
								+ "rj_zdsqs,wx_kff,wx_xcfs,glyyj,glyrq,glyry,glydw,jzrlx,czzj,fczzj,wwmc,cqly,tddj  from zc_zjb where yqbh=?");
						objlist.add(new Object[]{newyqbh,mxmap.get("JE"),mxmap.get("JE"),mxmap.get("SYDW"),mxmap.get("SYR"),mxmap.get("CFDD"),mxmap.get("SL"),StateManager.BDZT_Normal,yqbh});
					}
					sqllist.add("update zc_zcdbmxb set jzrq = sysdate where bdbgbh = ?");
					objlist.add(new Object[]{keyid});
					sqllist.add("update zc_zcdbb set jzrq = sysdate where "+shidlm+"= ?");
					objlist.add(new Object[]{keyid});
				}else if(MenuFlag.ZCBD_BFBF.equals(sqmkbh)){//部分报废财务审核是最后一步，改变主机表的数量、单价、总价、变动状态和现状
					sqllist.add("update zc_zjb z set (sykh,dj,zzj,bdzt,xz) = (select nvl(z.sykh,0)-nvl(b.sh,0),nvl(z.dj,0)-nvl(b.bddj,0),nvl(z.zzj,0)-nvl(b.bddj,0),?,'1' from zc_bdbgb b where bdbgbh = ?) where yqbh in (select fjbh from zc_bdb where bdbgbh = ?)");
					objlist.add(new Object[]{StateManager.BDZT_Normal,keyid,keyid});
					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_BFBF+"' ");
					objlist.add(null);
					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_BFBF+"' ");
					objlist.add(null);
				}else if(MenuFlag.ZCBD_FJCZ.equals(sqmkbh) || MenuFlag.ZCBD_FJZJ.equals(sqmkbh)){//附件处置、附件增加财务审核是最后一步，改变主机表的总价、变动状态、附件总价、附件数
					String yqbh = "";
					try{
						yqbh = db.queryForSingleValue("select fjbh from zc_bdb where bdbgbh = ? and bz = ?", new Object[]{keyid,Constant.BDBZ_ZJ});
					}catch(Exception e){
						logger.error("获取主件信息时报错，" + e.getMessage());
						pd.put("msg", "{\"success\":\"false\",\"msg\":\"获取主件信息失败！\"}");
						return false;
					}
					if(Validate.isNull(yqbh)){
						pd.put("msg", "{\"success\":\"false\",\"msg\":\"没有获取到主件信息！\"}");
						return false;
					}
					if(MenuFlag.ZCBD_FJCZ.equals(sqmkbh)){//附件处置
						sqllist.add("update  zc_fjb z  set xz='6' where  fjbh in (select fjbh from zc_bdb where bdbgbh = ? and bz = ?)  ");
						objlist.add(new Object[]{keyid,Constant.BDBZ_FJ});
						sqllist.add("update zc_zjb z set (zzj,fjs,fjzj,bdzt) = (select nvl(z.zzj,0) - nvl(sum(dj),0),nvl(z.fjs,0) - count(fjbh),nvl(z.fjzj,0) - nvl(sum(fjdj),0),? from zc_bdb where bdbgbh = ? and bz = ?) where yqbh = ?");
						objlist.add(new Object[]{StateManager.BDZT_Normal,keyid,Constant.BDBZ_FJ,yqbh});
						sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_FJCZ+"' ");
						objlist.add(null);
						sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_FJCZ+"' ");
						objlist.add(null);
					}else if(MenuFlag.ZCBD_FJZJ.equals(sqmkbh)){//增加附件
						sqllist.add("insert into zc_fjb(fjbh,flh,jdr,jdrq,fjmc,dj,fjdj,jldw,sydw,syr,xz,sfjrzj,sccj,xss,fjgg,fjxh,flmc,ztbz,rzrq,bzxx,pzh) select fjbh,flh,?,sysdate,yqmc,dj,fjdj,jldw,sydw,syr,xz,sfjrzj,sccj,xss,fjgg,fjxh,flmc,bdbz,jzrq,bzxx,pzh from zc_bdb where bdbgbh = ? and bz = ?");
						objlist.add(new Object[]{shr,keyid,Constant.BDBZ_FJ});
						sqllist.add("update zc_zjb z set (zzj,fjs,fjzj,bdzt) = (select nvl(z.zzj,0) + nvl(sum(dj),0),nvl(z.fjs,0) + count(fjbh),nvl(z.fjzj,0) + nvl(sum(fjdj),0),? from zc_bdb where bdbgbh = ? and bz = ?) where yqbh = ?");
						objlist.add(new Object[]{StateManager.BDZT_Normal,keyid,Constant.BDBZ_FJ,yqbh});
						sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_FJZJ+"' ");
						objlist.add(null);
						sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_FJZJ+"' ");
						objlist.add(null);
					}
				}else if(MenuFlag.ZCBD_DWNDB.equals(sqmkbh)){//单位内调拨管理员审核是最后一步，需要更改zc_zjb中的使用人、使用单位和存放地点，同时修改zc_fjb表中的使用人、使用单位和存放地点
					sqllist.add("update zc_zjb z set(syr,sydw,bzxx,bdzt)=(select nvl(hrybh,z.syr),nvl(hdwbh,z.sydw),nvl(hcfdd,z.bzxx),'"+StateManager.BDZT_Normal+"' from zc_bdbgb where bdbgbh = '"+keyid+"') where yqbh in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"')");
					objlist.add(null);
					sqllist.add("update zc_fjb z set(syr,sydw,bzxx)=(select nvl(hrybh,z.syr),nvl(hdwbh,z.sydw),nvl(hcfdd,z.bzxx) from zc_bdbgb where bdbgbh = '"+keyid+"') where substr(fjbh,1,length(fjbh)-3) in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"') and xz<>'6'");
					objlist.add(null);
					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_DWNDB+"' ");
					objlist.add(null);
					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_DWNDB+"' ");
					objlist.add(null);
				}else if(MenuFlag.ZCBD_DWJDB.equals(sqmkbh)){//单位间调拨归口审核是最后一步，需要更改zc_zjb中的使用人、使用单位和存放地点，同时修改zc_fjb表中的使用人、使用单位和存放地点
					sqllist.add("update zc_zjb z set(syr,sydw,bzxx,bdzt)=(select nvl(hrybh,z.syr),nvl(hdwbh,z.sydw),nvl(hcfdd,z.bzxx),'"+StateManager.BDZT_Normal+"' from zc_bdbgb where bdbgbh = '"+keyid+"') where yqbh in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"')");
					objlist.add(null);
					sqllist.add("update zc_fjb z set(syr,sydw,bzxx)=(select nvl(hrybh,z.syr),nvl(hdwbh,z.sydw),nvl(hcfdd,z.bzxx) from zc_bdbgb where bdbgbh = '"+keyid+"') where substr(fjbh,1,length(fjbh)-3) in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"') and xz<>'6'");
					objlist.add(null);
					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_DWJDB+"' ");
					objlist.add(null);
					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_DWJDB+"' ");
					objlist.add(null);
				}else if(MenuFlag.ZCBD_SYRBD.equals(sqmkbh)){//资产变动，使用人变动审核，需要更改zc_zjb中的使用人，同时修改zc_fjb表中的使用人
					sqllist.add("update zc_zjb set(syr,bdzt)=(select hrybh,'"+StateManager.BDZT_Normal+"' from zc_bdbgb where bdbgbh = '"+keyid+"') where yqbh in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"')");
					objlist.add(null);
					sqllist.add("update zc_fjb set syr=(select hrybh from zc_bdbgb where bdbgbh = '"+keyid+"') where substr(fjbh,1,length(fjbh)-3) in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"') and xz<>'6' ");
					objlist.add(null);
					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_SYRBD+"' ");
					objlist.add(null);
					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_SYRBD+"' ");
					objlist.add(null);
				}
//				else if(MenuFlag.ZCBD_CFDDBD.equals(sqmkbh)){//资产变动，存放地点变动审核
//					sqllist.add("update zc_zjb set(bzxx,bdzt)=(select hcfdd,'"+StateManager.BDZT_Normal+"' from zc_bdbgb where bdbgbh = '"+keyid+"') where yqbh in(select fjbh from zc_bdb where bdbgbh = '"+keyid+"')");
//					objlist.add(null);
//					sqllist.add("update zc_bdb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and xmbz='"+StateManager.BDXM_CFDDBD+"' ");
//					objlist.add(null);
//					sqllist.add("update zc_bdbgb set jzrq = sysdate where "+shidlm+"= '"+keyid+"' and djbz='"+StateManager.BDXM_CFDDBD+"' ");
//					objlist.add(null);
//				}
				else if(MenuFlag.ZCCZ_GLYHZ.equals(sqmkbh)||MenuFlag.ZCCZ_ZCCZ.equals(sqmkbh)){//资产处置汇总或资产处置
					sqllist.add("update zc_zjb set (bdzt,xz,czrq) =(select ?,hxz,jzrq from zc_czbgb where "+shidlm+"= ?) where yqbh in (select yqbh from zc_czb where "+shidlm+"= ?)");
					objlist.add(new Object[]{StateManager.BDZT_CZWC,keyid,keyid});
					List yqbhlist = db.queryForList("select yqbh from zc_czb where "+shidlm+"= ?", new Object[]{keyid});
					for(int k = 0;k<yqbhlist.size(); k++){//更新附件表的现状和处置日期
						Map map = (Map)yqbhlist.get(k);
						sqllist.add("update zc_fjb set (xz,czrq) =(select hxz,jzrq from zc_czbgb where "+shidlm+"= ?) where substr(fjbh,1,length("+map.get("YQBH")+"))='"+map.get("YQBH")+"' and xz not in (select dm from "+ SystemSet.gxBz+"dmb where zl='41')");
						objlist.add(new Object[]{keyid});
					}
				}else if(MenuFlag.ZCWX_WXSQ.equals(sqmkbh)){//设备维修申请
					sqllist.add("update zc_zjb set bdzt=? where yqbh in (select yqbh from zc_wxsqmxb m where sqbh = (select reportid from  zc_wxsqb  where "+shidlm+" = '"+keyid+"'))");
					objlist.add(new Object[]{StateManager.BDZT_WXBD});
				}else if(MenuFlag.ZCWX_WXBG.equals(sqmkbh)){//维修报告
					sqllist.add("update zc_zjb set bdzt = '" + StateManager.BDZT_Normal + "' where yqbh in (select yqbh from zc_wxsqmxb m where sqbh = (select reportid from " + shbmc + " where " + shidlm + " = '" + keyid + "'))");
					objlist.add(null);
					String flag = db.queryForSingleValue("select nvl(wxjrjz,'0')||nvl(wxfygl,'0') from zc_sys_xtb ",null);
					if("1".equals(flag.substring(0, 1))){//维修费用是否计入资产价值为：是
						String bdbgbh = "wxfy_"+keyid;//在循环之后，会把babgbh该成正确的
						wxbgbh += bdbgbh + ",";
						sqllist.add("insert into zc_bdb(bdbgbh,bdbz,bdxm,bdqnr,bdhnr,xmbz,bz,bdbh,bdrq,fjbh,yqmc,flh,flmc,dj,sykh,zzj,syr,sydw,bzxx,syfx,xz,jldw,jdr,jdrq,sccj,xss,pzh,jzrq,gzrq,bdyy) select ?,?,'单价',dj,(dj+nvl((select wxfy from zc_wxsqmxb m where sqbh = (select reportid from zc_wxbgb where orderid= '"+keyid+"') and m.yqbh=t.yqbh) ,0)),?,'1',sys_guid(),sysdate,yqbh,yqmc,flh,flmc,dj,sykh,zzj,syr,sydw,bzxx,syfx,xz,jldw,?,sysdate,sccj,xss,pzh,rzrq,gzrq,'系统：维修费用计入资产价值' from zc_zjb t where yqbh in (select yqbh from zc_wxsqmxb m where sqbh = (select reportid from zc_wxbgb where orderid = ?))");
						objlist.add(new Object[]{bdbgbh,StateManager.ZCBD_CW_TG,StateManager.BDXM_DJBD,shr,keyid});
						sqllist.add("insert into zc_bdbgb(bdbgbh,shr,shrq,zi,hao,bdrq,rybh,dwbh,bdyy,pzh,jzrq,djbz,ztbz) select ?,cwry,cwsj,?,?,checktime,cwry,repaircompany,?,pzh,jzrq,?,? from zc_wxbgb where orderid = ?");
						objlist.add(new Object[]{bdbgbh,"变",bdbgbh,"设备维修",StateManager.BDXM_DJBD,"99",keyid});
						
						sqllist.add("update zc_zjb t set dj=(t.dj+nvl((select wxfy from zc_wxsqmxb m where sqbh = (select reportid from zc_wxbgb where orderid = ?) and m.yqbh=t.yqbh),0)),zzj=(t.zzj+nvl((select wxfy from zc_wxsqmxb m where sqbh = (select reportid from zc_wxbgb where orderid= ?) and m.yqbh=t.yqbh),0)) where t.yqbh in (select yqbh from zc_wxsqmxb m where sqbh = (select reportid from zc_wxbgb where orderid= ?))");
						objlist.add(new Object[]{keyid,keyid,keyid});
					}
					if("1".equals(flag.substring(1))){//是否启用维修经费管理为：是
						sqllist.add("update zc_wxjfhb t set zjyje=(t.zjyje-nvl((select sum(wxfy) from zc_wxsqmxb m left join zc_wxsqb s on s.reportid=m.sqbh left join zc_wxbgb b on b.reportid=m.sqbh where b.orderid= ? and m.sydw=t.sydw and t.nd=to_char(s.replytime,'yyyy')),0)) where t.sydw in (select sydw from zc_wxsqmxb m left join zc_wxbgb b on b.reportid=m.sqbh where b.orderid= ?) and t.nd = (select to_char(repairtime,'yyyy') from zc_wxbgb where orderid= ?)");
						objlist.add(new Object[]{keyid,keyid,keyid});
						sqllist.add("update zc_wxbgb set fpinfo='1' where orderid= ?");
						objlist.add(new Object[]{keyid});
					}
				}else if(MenuFlag.ZCWX_JFZJ.equals(sqmkbh)){//经费追加
					if(db.queryForObject("select count(*) from zc_wxjfhb where sydw||'-'||nd = (select sydw||'-'||nd from zc_wxjfzj where sqbh = ?)", new Object[]{keyid}, Integer.class) == 0){
						String hbxh = AutoKey.createYear("zc_wxjfhb","hbxh","4");
						if(db.update("insert into zc_wxjfhb(hbxh,hbry,hbrq,sydw,hbje,zjje,nd,hbzt,jdr,jdrq) select ?,?,sysdate,sydw,0,je,nd,?,?,sysdate from zc_wxjfzj j where sqbh = ?", new Object[]{hbxh,shr,StateManager.YFH,shr,keyid}) == 0){
							logger.error(keyid + "插入zc_wxjfhb表失败！");
							pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核失败！\"}");
							return false;
						}
					}else{
						sqllist.add("update zc_wxjfhb h set zjje = nvl((select nvl(h.zjje,0) + nvl(je,0) from zc_wxjfzj where sqbh = ?),0) where sydw||'-'||nd = (select sydw||'-'||nd from zc_wxjfzj where sqbh = ?)");
						objlist.add(new Object[]{keyid,keyid});
					}
				}else if(MenuFlag.ZCXZ_XZTJ.equals(sqmkbh)){//资产闲置申请
					sqllist.add("update zc_zjb set bdzt='"+StateManager.BDZT_XZDGS+"',xz='E' where yqbh in(select yqbh from zc_xztj_sqmxb where "+shidlm+"= '"+keyid+"')");
					objlist.add(null);
				}else if(MenuFlag.ZCXZ_XZTJLY.equals(sqmkbh)){//资产闲置领用申请
					sqllist.add("update zc_zjb set (bdzt,xz,syr,sydw)=(select '"+StateManager.BDZT_Normal+"','1',sqry,sqdw from zc_xztj_lysqb where "+shidlm+"= '"+keyid+"') where yqbh in(select yqbh from zc_xztj_lysqmxb where "+shidlm+"= '"+keyid+"')");
					objlist.add(null);
					sqllist.add("update zc_fjb set (xz,syr,sydw)=(select '1',sqry,sqdw from zc_xztj_lysqb where "+shidlm+"= '"+keyid+"') where substr(fjbh,1,length(fjbh)-3) in(select yqbh from zc_xztj_lysqmxb where "+shidlm+"= '"+keyid+"') and xz<>'6' ");
					objlist.add(null);
				}
			}else{//不是最后一步的话，要把初始化表里当前节点的信息更新进去，下一节点的sfdqjd字段改成1
				sql = "select min(bzs) from " + bqz + "shcshb where dbh = ? and sqmkbh = ? and bzs > ?";
				next_bzs = db.queryForSingleValue(sql, new Object[]{dbh,sqmkbh,dqbzs});
				if(Validate.isNull(next_bzs)){
					logger.error("获取不到" + bqz + "shcshb表中该单据的下一步的步骤数");
					pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核通过失败！\"}");
					return false;
				}else{
					next_bzs = next_bzs.trim();
				}
			}
			//修改初始化表
			sqllist.add("update " + bqz + "shcshb set sfdqjd='0',sjshry=?,sjshryxm=?,sjshsj=sysdate,shyj=? where mkbh = ? and dbh = ? and bzs = ?");
			objlist.add(new Object[]{shr,shrxm,shyj,mkbh,dbh,dqbzs});
			sqllist.add("update " + bqz + "shcshb set sfdqjd = '1',sjshry='',sjshryxm='',sjshsj='',shyj='' where sqmkbh = ? and dbh = ? and bzs = ?");
			objlist.add(new Object[]{sqmkbh,dbh,next_bzs});
			//插入审核记录表
			sqllist.add("insert into " + bqz + "shjlb(sqmkbh,dbh,shr,shrxm,shrq,shyj,shmkbh,shbz) select ?,?,?,?,sysdate,?,?,? from dual");
			objlist.add(new Object[]{sqmkbh,dbh,shr,shrxm,shyj,mkbh,"通过"});
		}
		
		if(db.batchUpdate(sqllist, objlist)){
			if(Validate.noNull(wxbgbh)){
				String[] bdbgbharr = wxbgbh.split(",");
				for(int i = 0; i < bdbgbharr.length; i++){
					String bdbh = AutoKey.createKey("ZC_BDBGB","bdbgbh","4");
					db.update("update zc_bdbgb set bdbgbh = ?,hao = ? where bdbgbh = ?", new Object[]{bdbh,bdbh,bdbgbharr[i]});
					db.update("update zc_bdb set bdbgbh = ? where bdbgbh = ?", new Object[]{bdbh,bdbgbharr[i]});
				}
			}
			
			pd.put("msg", "{\"success\":\"true\",\"msg\":\"审核成功！\"}");
			sendMsg(keyid,sqmkbh);
			return true;
		}else{
			pd.put("msg", "{\"success\":\"false\",\"msg\":\"审核失败！\"}");
			return false;
		}
	}
	/**
	 * 获取删除审核记录的sql语句,这里的dbh必须是当做参数，要不然有些地方拼不进去
	 */
	public String getDelShjlSql(String mkbh, String dbh){
		return "delete from zc_sys_shjlb where sqmkbh in ('" + mkbh.replace(",", "','") + "') and dbh " + ToSqlUtil.getInsql(dbh);
	}
	/**公共保存方法*/
	public boolean doSave(PageData pd){
		Gson json = new Gson();
		if(Validate.isNull(pd.getString("data"))){
			logger.error("传入参数有误！");
			return false;
		}else{
			List list = json.fromJson(pd.getString("data"), List.class);
			List sqllist = new ArrayList();
			List parlist = new ArrayList();
			Map map;
			boolean flag = false;
			String mkbh,shyj,dbh,pzh,xmh,jzrq;
			for(int i = 0; i < list.size(); i++){
				map = (Map)list.get(i);
				if(!setDoSave(sqllist,parlist,map)){
					logger.error(map.get("mkbh").toString() + "菜单下，没有可以使用的保存方法");
					return false;
				}
			}
			return db.batchUpdate(sqllist, parlist);
		}
	}
	/**手机端保存审核信息*/
	public boolean doSavePhone(Map map){
		List sqllist = new ArrayList();
		List parlist = new ArrayList();
		if(!setDoSave(sqllist,parlist,map)){
			logger.error(map.get("mkbh").toString() + "菜单下，没有可以使用的保存方法");
			return false;
		}
		return db.batchUpdate(sqllist, parlist);
	}
	/**设置保存审核信息的语句 */
	private boolean setDoSave(List sqllist,List parlist,Map map){
		String mkbh = map.get("mkbh").toString();
		String shyj = Validate.isNullToDefaultString(map.get("shyj"), "");
		String dbh = map.get("dbh").toString();
		String pzh = Validate.isNullToDefaultString(map.get("pzh"),"");
		String xmh = Validate.isNullToDefaultString(map.get("xmh"), "");
		String jzrq = Validate.isNullToDefaultString(map.get("jzrq"), "");
		
		if(MenuFlag.ZCJZ_GLYSH.equals(mkbh)){
			sqllist.add("update zc_yshd set glyyj = ? where ysdh = ?");
			parlist.add(new Object[]{shyj,dbh});
			sqllist.add("update zc_zjb set glyyj = ? where ysdh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCJZ_GKSH.equals(mkbh)){
			sqllist.add("update zc_yshd set gkyj = ? where ysdh = ?");
			parlist.add(new Object[]{shyj,dbh});
			sqllist.add("update zc_zjb set gkyj = ? where ysdh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCJZ_CWSH.equals(mkbh)){
			sqllist.add("update zc_yshd set (pzh,xmh,jzrq,shyj,dzrrq) = (select ?,?,to_date(?,'yyyy-mm-dd'),?,to_date(?,'yyyy-mm-dd') from dual) where ysdh = ?");
			parlist.add(new Object[]{pzh,xmh,jzrq,shyj,jzrq,dbh});
			sqllist.add("update zc_zjb set (pzh,xmh,rzrq,shyj,dzrrq) = (select ?,?,to_date(?,'yyyy-mm-dd'),?,to_date(?,'yyyy-mm-dd') from dual) where ysdh = ?");
			parlist.add(new Object[]{pzh,xmh,jzrq,shyj,jzrq,dbh});
			return true;
		}else if(MenuFlag.ZCBD_ZCDB_GLYSH.equals(mkbh)){
			sqllist.add("update zc_bdbgb set glyshyj = ? where bdbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCBD_ZCDB_DCDWLD.equals(mkbh)){
			sqllist.add("update zc_bdbgb set dcdwshyj = ? where bdbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCBD_ZCDB_DRDWLD.equals(mkbh)){
			sqllist.add("update zc_bdbgb set drdwshyj = ? where bdbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCBD_XMBD_GKSH.equals(mkbh) || MenuFlag.ZCBD_BFBF_GKSH.equals(mkbh) || MenuFlag.ZCBD_ZCDB_GKSH.equals(mkbh)){
			sqllist.add("update zc_bdbgb set gkyj = ? where bdbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCBD_BFDB_GKSH.equals(mkbh)){
			sqllist.add("update zc_zcdbb set gkyj = ? where bdbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCBD_JZBD_GKSH.equals(mkbh) || MenuFlag.ZCBD_FJCZ_GKSH.equals(mkbh)){
			sqllist.add("update zc_bdbgb set gkyj = ? where bdbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCBD_JZBD_CWSH.equals(mkbh) || MenuFlag.ZCBD_FJCZ_CWSH.equals(mkbh)){
			sqllist.add("update zc_bdbgb set (pzh,jzrq,shyj) = (select ?,to_date(?,'yyyy-mm-dd'),? from dual) where bdbgbh = ?");
			parlist.add(new Object[]{pzh,jzrq,shyj,dbh});
			sqllist.add("update zc_bdb set (pzh,jzrq) = (select ?,to_date(?,'yyyy-mm-dd') from dual) where bdbgbh = ?");
			parlist.add(new Object[]{pzh,jzrq,dbh});
			return true;
		}else if(MenuFlag.ZCBD_BFBF_CWSH.equals(mkbh)){
			sqllist.add("update zc_bdbgb set (pzh,jzrq,shyj) = (select ?,to_date(?,'yyyy-mm-dd'),? from dual) where bdbgbh = ?");
			parlist.add(new Object[]{pzh,jzrq,shyj,dbh});
			sqllist.add("update zc_bdb set (pzh,jzrq) = (select ?,to_date(?,'yyyy-mm-dd') from dual) where bdbgbh = ?");
			parlist.add(new Object[]{pzh,jzrq,dbh});
			return true;
		}else if(MenuFlag.ZCCZ_GLYSH.equals(mkbh)){
			sqllist.add("update zc_czsqb set shyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCCZ_GKSH.equals(mkbh)){
			sqllist.add("update zc_czbgb set gkyj = ? where czbgbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCCZ_CWSH.equals(mkbh)){
			sqllist.add("update zc_czbgb set (pzh,jzrq,shyj) = (select ?,to_date(?,'yyyy-mm-dd'),? from dual) where czbgbh = ?");
			parlist.add(new Object[]{pzh,jzrq,shyj,dbh});
			return true;
		}else if(MenuFlag.JMYQ_NSYSH.equals(mkbh)){
			sqllist.add("update zc_jmyqb set syxxshyj = ? where jybh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCWX_WXSQSH.equals(mkbh)){
			sqllist.add("update zc_wxsqb set checkadvice = ? where reportid = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCWX_WXBG_SH.equals(mkbh)){
			sqllist.add("update zc_wxbgb set ysrq = to_date(?,'yyyy-mm-dd'),checkadvice = ? where orderid = ?");
			parlist.add(new Object[]{jzrq,shyj,dbh});
			return true;
		}else if(MenuFlag.ZCWX_WXBG_CWSH.equals(mkbh)){
			sqllist.add("update zc_wxbgb set (pzh,jzrq,cwyj) = (select ?,to_date(?,'yyyy-mm-dd'),? from dual) where orderid = ?");
			parlist.add(new Object[]{pzh,jzrq,shyj,dbh});
			return true;
		}else if(MenuFlag.ZCWX_JFZJ_DWLDSH.equals(mkbh)){
			sqllist.add("update zc_wxjfzj set dwyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCWX_JFZJ_CWSH.equals(mkbh)){
			sqllist.add("update zc_wxjfzj set zccyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCXZ_ZGSH.equals(mkbh)){
			sqllist.add("update zc_xztj_sqb set zgyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCXZ_GKSH.equals(mkbh)){
			sqllist.add("update zc_xztj_sqb set gkyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCXZLY_ZGSH.equals(mkbh)){
			sqllist.add("update zc_xztj_lysqb set zgshyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCXZLY_GKSH.equals(mkbh)){//资产闲置领用申请，归口审核
			sqllist.add("update zc_xztj_lysqb set gkshyj = ? where sqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCQC_ZCSH.equals(mkbh)){
			sqllist.add("update zc_zczcb set shyj = ? where yqbh = ? and qcbh in (select qcbh from zc_zczczt where zt = '1')");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}else if(MenuFlag.ZCJY_SQSH.equals(mkbh)){
			sqllist.add("update zc_jysqb set shyj = ? where jysqbh = ?");
			parlist.add(new Object[]{shyj,dbh});
			return true;
		}
		return false;
	}
	/** 批量赋值*/
	public int doPlfz(String ids,String ziduan,Object zdValue){
		String sql = "update zc_yshd set "+ziduan+" = ? where ysdh"+ToSqlUtil.getInsql(ids);
		Object[] obj = ids.split(",");
		Object[] objs = new Object[obj.length+1];
		System.arraycopy(new Object[]{zdValue}, 0, objs, 0, 1);
		System.arraycopy(obj, 0, objs, 1, obj.length);
		int i = db.update(sql,objs);
		return i;
	}
	/** 批量赋值 */
	public int doPlfz1(String ids,String ziduan,Object zdValue){
		String sql = "update zc_bdbgb set "+ziduan+" = ? where bdbgbh"+ToSqlUtil.getInsql(ids);
		Object[] obj = ids.split(",");
		Object[] objs = new Object[obj.length+1];
		System.arraycopy(new Object[]{zdValue}, 0, objs, 0, 1);
		System.arraycopy(obj, 0, objs, 1, obj.length);
		int i = db.update(sql,objs);
		return i;
	}
	/**批量赋值*/
	public int doPlfz2(String ids,String ziduan,Object zdValue){
		String sql = "update zc_czbgb set "+ziduan+" = ? where czbgbh"+ToSqlUtil.getInsql(ids);
		Object[] obj = ids.split(",");
		Object[] objs = new Object[obj.length+1];
		System.arraycopy(new Object[]{zdValue}, 0, objs, 0, 1);
		System.arraycopy(obj, 0, objs, 1, obj.length);
		int i = db.update(sql,objs);
		return i;
	}
	/** 获取部分报废明细表数据*/
	public List getBfbfMx(String bdbgbh) {
		String sql = "select bdbh,bdbgbh,bdxm,bdqnr,decode(nvl(bdqnr,0),0,'0.00',(to_char(round(bdqnr,2),'fm99999999999990.00'))) bdqnrs,bdhnr,decode(nvl(bdhnr,0),0,'0.00',(to_char(round(bdhnr,2),'fm99999999999990.00'))) bdhnrs,fjbh yqbh,(select yqmc from zc_zjb z where z.yqbh = b.fjbh) yqmc from zc_bdb b where bdbgbh = ? order by bdxm";
		return db.queryForList(sql,new Object[]{bdbgbh});
	}
	/** 验证审核必填项是否已填*/
	public List checkSave(PageData pd){
		String sql = "select " + pd.getString("zdm") + " from " + pd.getString("tname") + " where " + pd.getString("keycol") + ToSqlUtil.getInsql(pd.getString("dbh"));
		return db.queryForList(sql, pd.getString("dbh").split(","));
	}
	/**管理员建账审核，判断获取所选择的验收单号中，分类号是否是末级分类*/
	public boolean checkEndFlh(PageData pageData) {
		String sql ="select count(*)from zc_flb_jyb where  flh || '-' || flmc in(select flh || '-' || yqbh from zc_yshd where ysdh in('"+pageData.getString("dbh")+"') ) and sfmj='0'";
		String count  = db.queryForSingleValue(sql);
		if("0".equals(count)){
			return true;
		}else{
			return false;
		}
	}
	
	/**
	 * 触发websocket
	 * @param dbh 单编号
	 * @param sqmkbh 申请模块编号
	 * @return 当前是按照推送给所有人做的，所以dqbzs和isall这俩参数没用到
	 */
	public void sendMsg(String dbh,String sqmkbh){
		sendMsg(dbh,sqmkbh,"",true);
	}
	/**
	 * 触发websocket
	 * @param dbh 单编号
	 * @param sqmkbh 申请模块编号
	 * @param dqbzs 当前步骤数（提交：传""  撤销：传""  通过：有next_bzs的传bzs+","+next_bzs，没有next_bzs的传bzs  退回：有next_bzs的传bzs+","+next_bzs，没有next_bzs的传""）
	 * @param isall 是否通知所有人，这个参数是true时，dqbzs不起作用
	 */
	public void sendMsg(String dbh,String sqmkbh,String dqbzs,boolean isall){
		try{
			String sql = "";
			Map datamap;//存放各种临时的数据
			Gson gson = new Gson();
			String bh = "";//存放单编号，多个单编号以逗号隔开

			try{
				List dbhlist = gson.fromJson(dbh, List.class);
				for(int i = 0; i < dbhlist.size(); i++){
					datamap = (Map)dbhlist.get(i);
					if(i > 0){
						bh += ",";
					}
					bh += datamap.get("dbh").toString();
				}
			}
			catch(Exception e){
				bh = dbh;
			}
			
			List<Map<String,Object>> rylist;
			if(isall){//如果是推送给全部的人员，则从SessionMap.sessions里获取
				rylist = new ArrayList<Map<String,Object>>();
				if(SessionMap.sessions.size() > 0){
					for(String key : SessionMap.sessions.keySet()){
						datamap = new HashMap();
						datamap.put("RYBH", key);
						rylist.add(datamap);
					}
				}
			}
			else{//推送相关人员
				dqbzs = Validate.isNullToDefaultString(dqbzs, "0");
				if("0".equals(dqbzs)){
					sql = "select min(bzs) from zc_sys_shlcb where sqmk = ?";
					dqbzs = db.queryForObject(sql, new Object[]{sqmkbh}, String.class);
				}
				//能批量审核的步骤数都是一样的，所以这里的bzs直接in就好
				sql = "select rybh from zc_sys_shcshb where sqmkbh = ? and dbh in ('" + ToSqlUtil.pointToSql(bh) + "') and bzs in (" + dqbzs + ") group by rybh";
				rylist = db.queryForList(sql, new Object[]{sqmkbh});
			}
			
			if(rylist.size() > 0){
				String uri = PropertiesUtil.getGlobalValueByKey("WebSocket_Uri");
				int sockcnt = Integer.parseInt(PropertiesUtil.getGlobalValueByKey("WebSocket_Sh_Cnt"));
				WebSocketContainer container = ContainerProvider.getWebSocketContainer();
				Session sendsession = container.connectToServer(MyClient.class, new URI(uri));
				
				sql = "select k.dbh,'【'||sqmkmc||'】等待【'||mkmc||'】' mc,tjrxm tjr,(select url from "+TnameU.MKB+" m where m.mkbh = k.mkbh) tzlj,mkbh,sqmkbh,mkmc from zc_sys_shcshb k where k.rybh = ? and sfdqjd = '1' and (mkbh like '" + MenuFlag.SHCD + "%' or mkbh = '" + MenuFlag.ZCJZ_GLY + "') order by dbh";
				List<Map<String,Object>> shlist;
				StringBuffer json;
				for(Map rymap : rylist){
					String rybh = Validate.isNullToDefaultString(rymap.get("RYBH"), "");
					shlist = db.queryForList(sql, new Object[]{rybh});
					int shsl = shlist.size();

					json = new StringBuffer();
					json.append("{\"shsl\":" + shsl + ",\"shxx\":[");
					if(shsl > 0){
						Map shmap;
						int cnt = sockcnt;
						if(shsl < sockcnt){
							cnt = shsl;
						}

						for(int i = 0; i < cnt; i++){
							shmap = (Map)shlist.get(i);
							json.append("{");
							json.append("\"dbh\":\"" + Validate.isNullToDefault(shmap.get("DBH"), "") + "\",");
							json.append("\"mc\":\"" + Validate.isNullToDefault(shmap.get("MC"), "") + "\",");
							json.append("\"tjr\":\"" + Validate.isNullToDefault(shmap.get("TJR"), "") + "\",");
							json.append("\"tzlj\":\"" + Validate.isNullToDefault(shmap.get("TZLJ"), "") + "\",");
							json.append("\"mkbh\":\"" + Validate.isNullToDefault(shmap.get("MKBH"), "") + "\",");
							json.append("\"sqmkbh\":\"" + Validate.isNullToDefault(shmap.get("SQMKBH"), "") + "\",");
							json.append("\"mkmc\":\"" + Validate.isNullToDefault(shmap.get("MKMC"), "") + "\"");
							json.append("},");
						}
						json.deleteCharAt(json.length() - 1);
					}
					json.append("]}");
					
					Session receivesession = SessionMap.sessions.get(rybh);
					if(receivesession != null){
						receivesession.getBasicRemote().sendText(json.toString());
					}
				}
				sendsession.close();
			}
			else{
				System.err.println("没有需要推送数据的信息!!!");
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}