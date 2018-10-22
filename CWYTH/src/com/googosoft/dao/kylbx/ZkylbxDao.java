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

@Repository("zkylbxDao")
public class ZkylbxDao extends BaseDao{
	private Logger logger = Logger.getLogger(ZkylbxDao.class);
	
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
	 * 单个打印
	 * @param pd
	 * @return
	 */
	public  Map<?,?> getSinglePrintInfoByIds(String guids){
		String sql = " select * from (select t.guid,t.xmmc,t.djbh,a.fzr ,(select d.mc from gx_sys_dwb d where d.dwbh=(select r.dwbh from gx_sys_ryb r where r.rybh = a.fzr))as xmzgbm,(select r.xm from gx_sys_ryb r where r.rybh=a.fzr)as zcr,a.xmmc as xmmc1,a.xmbh,a.kssj,a.jssj,a.ysje,a.syje,(a.ysje-a.syje)as xsyje,"
				+ "t.sqr as bxr,((select r.xm from gx_sys_ryb r where r.guid = t.sqr)) as bxrmc, to_char(t.FJZZS), to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE,"
				+ " '' as szbmmc,  t.CZRQ,  '差旅费报销' as lx   from Cw_clfbxzb t left join cw_jfszb a on a.guid=t.xmmc where t.sfkylbx = '1')  k where k.guid = '"+guids+"'";
		Map menuList = db.queryForMap(sql);
		return menuList;
	}
	/**
	 * biao2
	 * @param guids
	 * @return
	 */
	public  List getSinglePrintInfoByIds2(String guids){
		String sql = " select g.szbm,g.ryxm,g.kh,(select r.xm from gx_sys_ryb r where r.rybh = g.ryxm)as xm,g.skrq,g.skje,d.je,d.dfzh from CW_CLFBXZB t left join Cw_gwkb g on g.skdh=t.guid left join Cw_dgzfb d on d.zfdh = t.guid where t.guid ='"+guids+"' ";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  List getSinglePrintInfoByIds3(String guids){
		String sql = " select sum( g.skje) as cskje,sum(d.je) as cje from CW_CLFBXZB t left join Cw_gwkb g on g.skdh = t.guid left join Cw_dgzfb d on d.zfdh = t.guid where t.guid in '"+guids+"' ";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	/**
	 * 科研-日常 
	 * @param guids
	 * @return
	 */
	public  List getKyrcSinglePrintInfoByIds(String guids){
		String sql = " select * from (select t.guid,t.xmmc, t.djbh,a.fzr,(select d.mc from gx_sys_dwb d where d.dwbh = (select r.dwbh from gx_sys_ryb r where r.rybh = a.fzr)) as xmzgbm,(select r.xm from gx_sys_ryb r where r.rybh = a.fzr) as zcr, a.xmmc as xmmc1,"
				+ " a.xmbh, a.kssj, a.jssj, a.ysje, a.syje,(a.ysje - a.syje) as xsyje, to_char(t.FJZZS), to_char(T.bxzje, 'FM9999999999999990.00') as BXZJE,"
				+ " '' as szbmmc, t.CZRQ, '日常报销' as lx from CW_RCBXZB t left join cw_jfszb a on a.xmbh = t.xmmc where t.sfkylbx = '02' ) k where k.guid ='"+guids+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  List getKyrcSinglePrintInfoByIds2(String guids){
		String sql = " select t.xmmc,t.bz fymc,t.bxzje from CW_RCBXZB t left join Cw_Fykmdzb r on r.kmbh=t.xmmc where t.guid ='"+guids+"'";
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  Map getClSinglePrintInfoByIds(String guids){
		String sql = " select * from ( select t.bz FYMC,t.guid,t.djbh,t.bxr,((select r.xm from gx_sys_ryb r where r.guid=t.bxr) ) as bxrmc,((select r.mc from gx_sys_dwb r where r.dwbh=t.szbm) ) as szbmmc,"
					+ "t.szbm,t.fjzzs,to_char(T.bxzje,'FM9999999999999990.00')as BXZJE,"
					+ "to_char(czrq,'yyyy-mm-dd')as CZRQ,'日常报销'as lx from Cw_rcbxzb t where t.sfkylbx='02')k where k.guid ='"+guids+"'";
		Map menuList = db.queryForMap(sql);
		return menuList;
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
	public  List bmysMenu(){
		String sql = "select t.dm,t.mc from GX_SYS_DMB t where zl='"+Constant.BXLB+"' and t.dm<>'3'";		
		List menuList = db.queryForList(sql);
		return menuList;
	}
	public  List bdbxMenu(){
		System.err.println("________1111_");
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
	
	
	public List<Map<String, Object>> getList6(String searchValue, String sql) {
		
		return db.queryForList(sql);
	}
	//差旅费报销-科研处
	public Map<String,Object> getPrintYxByKyc(String guid){
		String sql = "select * from(select shyj,to_date(jdsj,'yyyy-mm-dd hh24:mi:ss') jdsj,t.rybh  from OA_SHYJB t left join ACT_HI_ACTINST a on a.task_id_=t.taskid where  a.act_id_ in('kycfzr') and t.PROCINSTID=(select PROCINSTID from CW_CLFBXZB where guid='"+guid+"')  order by t.jdsj desc) where rownum=1";
		return db.queryForMap(sql);
	}
	public List<Map<String, Object>> getList5(String searchValue, String guid,String sql) {
		return db.queryForList(sql);
	
	} 
}
