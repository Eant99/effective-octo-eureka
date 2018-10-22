package com.googosoft.dao.kylbx;


import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.pojo.fzgn.jcsz.GX_SYS_DWB;
import com.googosoft.util.CommonUtils;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;

import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

@Repository("kylbxDao")
public class KylbxDao extends BaseDao{
	private Logger logger = Logger.getLogger(KylbxDao.class);
	
	/**
	 * 增加
	 * @param dwb
	 * @return
	 */
	public int doAdd(PageData pd) {
		String sql = "insert into Cw_jfszb (guid,bmbh,nd,xmbh,xmmc,fzr,xmlx,jflx,kssj, jssj,ysje,syje,bz,czr,czrq) "
				+ "values(sys_guid(),?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate)";
		Object[] obj = new Object[]{
				//CommonUtil.getBeginText( pd.getString("xmfzr") ),
				CommonUtil.getBeginText(pd.getString("bmmc")),
				pd.getString("nd"),
				pd.getString("xmbh"),
				pd.getString("xmmc"),
				CommonUtil.getBeginText(pd.getString("fzr")),
				
				pd.getString("xmfl"),
				CommonUtil.getBeginText(pd.getString("jflx")),
				pd.getString("kssj"),
				pd.getString("jssj"),
				pd.getString("ysje"),
				
				pd.getString("syje"),
				pd.getString("bz"),
				pd.getString("czr")
		};
		
//		String sql2 = "insert into CW_XMSQB (guid,xmbh,xmmc,czr,fzr,czrq) "
//				+ "values(sys_guid(),?,?,?,?,sysdate)";
//		
//		Object[] obj2 = new Object[]{
//				pd.getString("ktbh"),
//				pd.getString("ktmc"),
//				pd.getString("czr"),
//				CommonUtil.getBeginText(pd.getString("xmfzr"))
//		};
		int i = 0;
		try {  
			i = db.update(sql, obj);
//			i = db.update(sql2, obj2);
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
	public Map<String, Object> getObjectById(PageData pd,String dwbh) {
		String sql = "";
		System.err.println("_____"+pd.getString("zl"));
		if("3".equals(pd.getString("zl"))){
			sql = " select t.guid,t.djbh,'' as FJZZS,t.BXRY as bxr,('('||t.BXRY||')'||(select r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxrmc,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,to_char(t.CZRQ,'yyyy-mm-dd') "
					+ "from Cw_gwjdfbxzb t where t.guid = '"+dwbh+"' ";
		}else if("2".equals(pd.getString("zl"))){
			sql = " select t.guid,t.djbh,t.sqr as bxr,(select r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc1,"
					+ "('('||t.sqr||')'||(select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00'),'' as szbmmc,to_char(t.CZRQ,'yyyy-mm-dd')  from Cw_clfbxzb t where t.guid = '"+dwbh+"' ";
		}else{
			sql = "  select t.guid,t.djbh,t.bxr,('('||t.bxr||')'||(select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00'),to_char(czrq,'yyyy-mm-dd') from Cw_rcbxzb t where t.guid = '"+dwbh+"' ";
		}
		return db.queryForMap(sql);
	}
	/**
	 * 打印查询
	 */
	public List getObjectDyById(PageData pd,String dwbh) {
		String sql = "";
		if("3".equals(pd.getString("zl"))){
			sql = " select t.guid,t.djbh,'' as FJZZS,t.BXRY as bxr,('('||t.BXRY||')'||(select r.xm from gx_sys_ryb r where r.guid=t.BXRY) ) as bxrmc,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,to_char(t.CZRQ,'yyyy-mm-dd') "
					+ "from Cw_gwjdfbxzb t where t.guid = '"+dwbh+"' ";
		}else if("2".equals(pd.getString("zl"))){
			sql = " select t.guid,t.djbh,t.sqr as bxr,(select r.xm from gx_sys_ryb r where r.guid=t.sqr) as bxrmc1,"
					+ "('('||t.sqr||')'||(select r.xm from gx_sys_ryb r where r.guid=t.sqr) ) as bxrmc,"
					+ "t.kssj,t.jssj,t.cfdd,t.FJZZS,to_char(T.bxzje,'FM9999999999999990.00'),'' as szbmmc,to_char(t.CZRQ,'yyyy-mm-dd')  from Cw_clfbxzb t where t.guid = '"+dwbh+"' ";
		}else{
			sql = "  select c.fjzzs,c.BXSY,BXZJE,to_char(c.czrq,'yyyy-mm-dd')as czrq from CW_RCBXZB c where c.guid in ("+dwbh+") ";
		}
		return db.queryForList(sql);
	}
	/**
	 * 打印前判断是在哪个表里面查询
	 * @param pd
	 * @param dwbh
	 * @return
	 */
	public String getflagById(PageData pd,String dwbh) {
		String sql = "";
		sql = " select count(1) from Cw_rcbxzb t where t.guid = '"+dwbh+"' ";
		return db.queryForSingleValue(sql);
	}
	/**
	 * 修改
	 * @param dwb 单位表
	 * @return
	 */
	public int doUpdate(PageData pd,String dwbh){
		String sql = "update Cw_jfszb set   bmbh=?,nd=?,xmbh=?,xmmc=?,fzr=?  ,xmlx=?,jflx=?,kssj=?,jssj=?,ysje=?  ,syje=?,bz=? where guid = ?";
		Object[] obj = new Object[]{
				CommonUtil.getBeginText(pd.getString("bmbh")),
				pd.getString("nd"),
				pd.getString("xmbh"),
				pd.getString("xmmc"),
				CommonUtil.getBeginText(pd.getString("fzr")),
				
				pd.getString("xmlx"),
				pd.getString("jflx"),
				pd.getString("kssj"),
				pd.getString("jssj"),
				pd.getString("ysje"),
				
				pd.getString("syje"),
				pd.getString("bz"),
				dwbh
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
	 * 复核
	 */
	public boolean goFhPage(PageData pd,String dwbh){
		boolean flag = false;
		String sql = "update Cw_jfszb set sffh=? where guid = ?";
		Object[] obj = new Object[]{
				1,
				pd.getString("dwbh")
		};
	    int i = 0;
		try {  
			i = db.update(sql, obj);
		} catch (DataAccessException e) {
		    logger.error("数据库操作失败\n" + e.getCause().getMessage());  
		    flag = false;
		}
		return flag;
	}
	
	/**
	 * 删除
	 * @param dwbh   000001
	 * @return 
	 */
	public int doDelete(String dwbh) {
		String sql = "DELETE Cw_jfszb WHERE guid in ('"+dwbh+"')";
		return db.update(sql);
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
	public String validateForRyOrXjdwOrZc(String DWBHS){
        DWBHS = DWBHS.trim();
        String[] DWBH = DWBHS.split(",");
        String FLAG = "N"; 
        String sql = "";
        String bmh = "";
        int count = 0;
        try { 
	        for(int i=0; i<DWBH.length; i++){
	            sql = "select count(z.rybh) from %Sryb z where z.dwbh = '"+DWBH[i]+"' ";
	            sql=String.format(sql, SystemSet.gxBz);
	            count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	            if(count > 0){
	            	bmh = db.queryForSingleValue("select bmh from gx_sys_dwb where dwbh='"+DWBH[i]+"'")+"";
	                FLAG = "R:"+bmh;  //返回有人员，提示用户
	            }else{
	                sql = "select count(z.dwbh) from %Sdwb z where z.sjdw = '"+DWBH[i]+"' and z.dwbh!=z.sjdw ";
	                sql=String.format(sql, SystemSet.gxBz);
	                count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	                if(count > 0){
	                	bmh = db.queryForSingleValue("select bmh from gx_sys_dwb where dwbh='"+DWBH[i]+"'")+"";
	                    FLAG = "W:"+bmh;  //返回有单位，提示用户
	                }else{
	                	sql = "select count(z.yqbh) from zc_zjb z where z.sydw = '"+DWBH[i]+"' and z.xz in (select d.dm from gx_sys_dmb d where d.zl ='"+Constant.XZ+"' )";
	    	            count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	    	            if(count > 0){
	    	            	bmh = db.queryForSingleValue("select bmh from gx_sys_dwb where dwbh='"+DWBH[i]+"'")+"";
	    	                FLAG = "C:"+bmh;  //返回有正常资产，提示用户
	    	            }else{
	    	                sql = "select count(z.yqbh) from zc_zjb z where z.sydw = '"+DWBH[i]+"' and z.xz in (select d.dm from gx_sys_dmb d where d.zl ='"+Constant.HXZ+"' ) and z.bdzt= '"+StateManager.BDZT_CZWC+"' ";
	    	                count = Integer.valueOf(db.queryForSingleValue(sql)+"");
	    	                if(count > 0){
	    	                    FLAG = "U";  //假删除：改为禁用
	    	                }else{
	    	                    FLAG = "D";  //表示可以删除
	    	                }
	    	            }
	                }
	            }
	        }
	    } catch (DataAccessException e) {
	    	logger.error("数据库操作失败\n" + e.getCause().getMessage());  
	    }
	    return FLAG;
	}
	
	/**
	 * 判断bmh是否重复
	 * @param bmh
	 * @return true为不重复，false为重复
	 */
	public boolean doCheckDwbh(String bmh){
		String sql = "select count(1) from CW_JFSZB where  bmh= ? ";
		String count = db.queryForObject(sql,new Object[]{bmh}, String.class);
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
	 * 
	 * @return
	 */
	public String getNewStatus(String dwbh) {
		String sql = "select count(1) from GX_SYS_dwb where sjdw='"+dwbh+"' and dwzt='1'and dwbh is not null";
		return db.queryForSingleValue(sql);
	}
	
	/**
	 * 导入
	 * @param file
	 * @return
	 */
	public List insertJcsj(String file) {
		Workbook rwb;
		List list = new ArrayList();
		List sqlList = new ArrayList();
		try {
			rwb = Workbook.getWorkbook(new File(file));
			Sheet rs=rwb.getSheet(0);//或者rwb.getSheet(0)
			int rows=rs.getRows();//得到所有的行
			int j = 0;
			for(int i=1;i<rows;i++){//第一行是列名，不需要导入数据库
				j=0;
				Map map = new HashMap();
				String xmfzr = rs.getCell(j++, i).getContents();
				String ktmc = rs.getCell(j++, i).getContents();
				String nd = rs.getCell(j++, i).getContents();
				String ysje = rs.getCell(j++, i).getContents();
				String syje = rs.getCell(j++, i).getContents();
				String bz = rs.getCell(j++, i).getContents();
				String ktbh = rs.getCell(j++, i).getContents();
				String zcr = rs.getCell(j++, i).getContents();
				String xmlx = rs.getCell(j++, i).getContents();
				String kssj = rs.getCell(j++, i).getContents();
				String jssj = rs.getCell(j++, i).getContents();
				
				map.put("xmfzr", xmfzr);
				map.put("ktmc", ktmc);
				map.put("nd", nd);
				map.put("ysje", ysje);
				map.put("syje", syje);
				map.put("bz", bz);
				map.put("ktbh", ktbh);
				map.put("zcr", zcr);
				map.put("xmlx", xmlx);
				map.put("kssj", kssj);
				map.put("jssj", jssj);
				list.add(map);
			
				String sql = "insert into Cw_grjfszb(guid,xmfzr,ktmc,nd,ysje,syje,bz,ktbh,zcr,xmlx,kssj,jssj) "
						+ "values(sys_guid(),'"+xmfzr+"','"+ktmc+"','"+nd+"','"+ysje+"','"+syje+"','"+bz+"'"
								+ ",'"+ktbh+"','"+zcr+"','"+xmlx+"','"+kssj+"','"+jssj+"')";
				sqlList.add(sql);
			}
			db.batchUpdate(sqlList);
			rwb.close();
		}catch (BiffException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}
	
	
	public  List kmszMenuzj1(String jb){
		String sql = "select t.jb as JB,t.aid as aid,t.kmmc as MC,t.guid,t.isleaf as ISLEAF from cw_kjkmsz t where t.aid='"+jb+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 打印查询信息
	 * @param jb
	 * @return
	 */
	public  List getPrintInfoByIds(PageData pd){
		String guids = pd.getString("dwbh");
		String sql = " select * from (  (select t.guid, t.djbh, '' as FJZZS, to_char(t.BXRY)  as bxrmc,"
					+ " ('(' || t.szbm || ')' || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, t.szbm, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq,"
					+ " '公务接待报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='11033' AND T.DM=t.SHZT)as shztmc"
					+ "  from Cw_gwjdfbxzb t) union (select t.guid, t.djbh, to_char(t.fjzzs),"
					+ " to_char((select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
					+ " ('(' || t.szbm || ')' || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
					+ " to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, t.szbm, to_char(czrq, 'yyyy-mm-dd') as czrq,"
					+ " '日常报销' as lx,t.shzt,(SELECT T.MC FROM GX_SYS_DMB t where  zl='"+Constant.DJSHZT+"' AND T.DM=t.SHZT)as shztmc from Cw_rcbxzb t where t.sfkylbx = '0') ) t where t.guid in ("+guids+")";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	//查询项目信息list
		public List getxmxxlist(String rcbxguid) {
			String sql = "SELECT distinct xm.CCSQID,xm.JFBH,xm.GUID,to_char(xm.bcbxje,'FM999999999999.00')as bcbxje,s.xmmc as xmmc,to_char(s.ye,'FM999999999999.00') as ye FROM CW_CCSQSPB_XM xm left join XMINFOS s on s.guid=xm.jfbh WHERE CCSQID in ('"+rcbxguid+"')";
			List list = new ArrayList<Map<String, Object>>();
			list = db.queryForList(sql);
			System.err.println("项目信息list==>"+sql);
			return list;
		}
	/**
	 * 单个打印
	 * @param pd
	 * @return
	 */
	public  Map<?,?> getSinglePrintInfoByIds(String guids){
		String sql = " select * from ((select t.guid,t.gwhdxm as xmmc,t.djbh,t.jdsy as kznr, t.FYFJZS,to_char((select  r.xm from gx_sys_ryb r where r.rybh = t.bxry)) as bxrmc, (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm) as szbmmc,"
				+ " to_char(T.bxje, 'FM9999999999999990.00') as BXZJE,  t.szbm, t.CZRQ, '公务接待报销' as lx, (SELECT T.MC"
				+ " FROM GX_SYS_DMB t where zl = '11033' AND T.DM = t.SHZT) as shztmc, '公务接待报销' as fymc from Cw_gwjdfbxzb t ) "
				+ " union"
				+ " (select t.guid,(select xmmc from XMINFOS where guid=t.xmmc)as xmmc,t.djbh,t.bxsy as kznr, to_char(t.fjzzs), to_char((select r.xm from gx_sys_ryb r where r.guid = t.bxr)) as bxrmc,"
				+ " (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm) as szbmmc, to_char(T.bxzje, 'FM9999999999999990.00') as bxzje,"
				+ " t.szbm, t.czrq, '日常报销' as lx,(SELECT T.MC FROM GX_SYS_DMB t where zl = 'djshzt' AND T.DM = t.SHZT) as shztmc,wm_concat(distinct to_char(c.kmmc)) as fymc "
				+ " from Cw_rcbxzb t  left join Cw_rcbxmxb f on t.guid=f.zbid left join CW_JJKMB c on c.kmbh=f.fymc group by t.guid,t.djbh,t.bxsy,t.fjzzs,t.szbm,t.czrq,t.bxr,T.bxzje,t.SHZT,t.xmmc)"
				+ " union"
				+ " (select t.guid,(select xmmc from XMINFOS where guid=t.xmmc)as xmmc,t.djbh,t.ccsy as kznr, to_char(t.fjzzs), to_char((select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc,"
				+ " (select r.mc from gx_sys_dwb r where r.dwbh = '') as szbmmc, to_char(T.bxzje, 'FM9999999999999990.00') as bxzje,''szbm,"
				+ " to_date(t.czrq,'yyyy-mm-dd'), '差旅费报销' as lx, (SELECT T.MC FROM GX_SYS_DMB t where zl = 'djshzt' AND T.DM = t.SHZT) as shztmc,"
				+ " t.bz as fymc from Cw_clfbxzb t left join Cw_clfbxmxb f on t.guid = f.djbh left join Cw_fykmdzb c on c.guid = f.djbh"
				+ " where 1=1)) t where t.guid in ("+guids+")";//t.sfkylbx = '0'
		Map menuMap = db.queryForMap(sql);
		return menuMap;
	}
	/**
	 * 单个打印
	 * @param pd
	 * @return
	 */
	public  Map<?,?> getPrintInfoById(String guid){
		String sql = " select * from ("
				+ "select t.guid,t.djbh,t.fjzzs,(select xmmc from Cw_rcbxmxb m where m.zbid=t.guid and rownum<=1) as xmmc,to_char(T.Bxzje, 'FM9999999999999990.00') as BXZJE  from Cw_rcbxzb t "
				+ "union all "
				+ "select t.guid,t.djbh,to_number(nvl(t.fyfjzs, 0)),'公务接待费' as xmmc,to_char(T.bxje, 'FM9999999999999990.00') as BXZJE from Cw_gwjdfbxzb t "
				+ "union all "
				+ "select t.guid,t.djbh,to_number(nvl(t.fjzzs,0)),(select xmmc from cw_clfxmmxb x where x.zbid=t.guid), to_char(T.bxzje, 'FM9999999999999990.00') as bxzje from Cw_clfbxzb t"
				+ ") t where t.guid in ("+guid+")";//t.sfkylbx = '0'
		Map map = db.queryForMap(sql);
		return map;
	}
	/**
	 *获取事前审批id
	 * @param 
	 * @return
	 */
	public Map<String, Object> getSqsp(String guid) {
		String sql="select sqspbh as sqspbid from cw_gwjdbxdzb p where bxbh= "+guid+"";
		return db.queryForMap(sql);
	}
	/**
	 * 打印公共接待服务查询信息
	 * @param pd
	 * @return
	 */
	
	public  List getPrintgInfoByIds(PageData pd){
		String guids = pd.getString("dwbh");
		String sql = " select t.guid,t.djbh,'' as FJZZS,t.BXRY as bxrmc,"
					+ "t.szbm,('('||t.szbm||')'||(select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,to_char(T.bxje,'FM9999999999999990.00') as BXZJE,"
					+ "to_char(t.CZRQ,'yyyy-mm-dd')as czrq ,'公务接待报销' as lx,t.shzt,"
					+ "(SELECT T.MC FROM GX_SYS_DMB t where  zl='11033' AND T.DM=t.SHZT)as shztmc from Cw_gwjdfbxzb t where t.guid in ("+guids+")";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	
	public  List bmysMenu(){
		String sql = "select t.dm,t.mc from GX_SYS_DMB t where zl='"+Constant.BXLB+"' and dm<>'2'";		
		List menuList = db.queryForList(sql);
		return menuList;
	}
	
	public  List wdbxMenu(){
		String sql = "select t.dm,t.mc from GX_SYS_DMB t where zl='"+Constant.BXLB+"' ";		
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 获取会计科目字典树下级
	 */
	public  List kmszMenuzj(String dm){
		//String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+jb+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
			//	"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+jb+"'";
	//	String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count from gx_sys_dmb t where t.jb ='"+dm+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
	//			"select t.guid as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count from cw_kjkmsz t WHERE T.KMJDM='"+dm+"'";
	
		String sql = "select t.dm,t.mc,t.zl,t.jb,(select count(1) from cw_kjkmsz b where b.kmjdm=t.dm) as count,'' as ISLEAF from gx_sys_dmb t where t.jb ='"+dm+"' and t.zl='"+Constant.ZCZL+"' union\r\n" + 
		"select t.jb as dm,t.kmmc as mc,t.zjf as zl,t.kmjdm as jb, 0 as count,t.isleaf as ISLEAF from cw_kjkmsz t WHERE t.kmjdm ='"+dm+"' ";
					
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public List<Map<String, Object>> getList(String searchValue, String guid,String sql) {
//		StringBuffer sql = new StringBuffer();
//		Object[] guid2;
//		sql.append("select distinct* from ( (select t.shzt, t.guid, t.djbh, t.xmmc, to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r"
//				+ " where r.guid = t.bxr)) as bxrmc, t.szbm as szbm, ('(' || t.szbm || ')' ||(select d.mc from gx_sys_dwb d where d.dwbh = t.szbm)) as szbmmc,"
//				+ " to_char(t.fjzzs), to_char(T.bxzje, 'FM9999999999999990.00') as bxzje, to_char(czrq, 'yyyy-mm-dd') as czrq,'日常报销' as lx,'' ccywguid from Cw_rcbxzb t"
//				+ " where t.bxr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"')) "
//				+ " union "
//				+ " (select t.shzt, t.guid,t.djbh,(select x.guid from cw_xmjbxxb x where x.guid = t.xmmc) as xmmc,"
//				+ " to_char((select '(' || r.rybh || ')' || r.xm from gx_sys_ryb r  where r.guid = t.sqr)) as bxrmc, '' as szbm, (select d.mc from gx_sys_dwb d"
//				+ " where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.guid = t.sqr)) as szbmmc,  t.FJZZS, to_char(T.bxzje, 'FM9999999999999990.00') as bxzje,"
//				+ " t.CZRQ as czrq, '差旅费报销' as lx,t.ccywguid ccywguid from Cw_clfbxzb t where t.sqr = (select ry.guid from gx_sys_ryb ry where ry.rybh = '"+dlr+"'))"
//				+ " union "
//				+ " (select t.shzt, t.guid, t.djbh, to_char(t.BXRY) as bxrmc,''xmmc, t.szbm, ('(' || t.szbm || ')' || (select r.mc from gx_sys_dwb r where r.dwbh = t.szbm)) as szbmmc,"
//				+ " '' as FJZZS, to_char(T.bxje, 'FM9999999999999990.00') as BXZJE, to_char(t.CZRQ, 'yyyy-mm-dd') as czrq, '公务接待报销' as lx,'' ccywguid"
//				+ " from Cw_gwjdfbxzb t  where t.bxry = '"+rybhAndxm+"') )k where 1=1");
//		if(Validate.noNull(guid)){
//			sql.append(" and guid "+CommonUtils.getInsql(guid)+" ");
//		}
//		 guid2 = guid.split(",");
	
	return db.queryForList(sql);
	}
	
	public List<Map<String, Object>> getJsList(String guid, String searchValue,
			String rybh, String sql) {

		return db.queryForList(sql);
	}
}
