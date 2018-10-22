package com.googosoft.dao.mbxz;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.pojo.ysgl.CW_XMBQMXB;
import com.googosoft.util.PageData;
import com.googosoft.util.Validate;
@Repository("mbxzDao")
public class MbxzDao extends BaseDao{
	private Logger logger = Logger.getLogger(MbxzDao.class);

	/**
	 * 获取所有模板
	 * @return
	 */
	public List getMbList() {
		String sql = " select * from cw_mbb";
		List list = db.queryForList(sql);
		return list;
	}

	
	public int doDelMxb(String zbid) {
		String sql="delete CW_JJKMPZB ";
		int i = db.update(sql);
		return i;
	}
	
	public int doSaveMxb(String fybh,String fymc) {
		String sql = "insert into CW_JJKMPZB(guid,kmbh,kmmc) values(sys_guid(),'"+fybh+"','"+fymc+"')";
		int i = 0;
		try{
			i = db.update(sql);
		}catch (DataAccessException e) {  
		    return -1;
		}
		return i;
	}
	public List getFybh() {
		String sql = " select kmbh,kmmc from CW_JJKMPZB ";
		return db.queryForList(sql);
	}
	public Map getZzzy() {
		String sql = " select ms from GX_SYS_DMB where zl='zzzy'";
		return db.queryForMap(sql);
	}
	/**
	 * 修改状态
	 * @param zt
	 * @param gid
	 * @return
	 */
	public int doZtsz(String zt,String gid) {
		int i = 0;
		if(zt.equals("1")){//启用某一个，需要禁用其他
			String sql = "UPDATE CW_MBB SET ZT = '1' WHERE gid = '"+gid+"'";
			String sql2 = "UPDATE CW_MBB SET ZT = '0' WHERE gid not in '"+gid+"'" ;
			try {
				int a  = db.update(sql);
				int b  = db.update(sql2);
				i = a+b;
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				logger.error("数据库操作失败\n" + sqle);
				return -1;
			}
		}else if(zt.equals("0")){//禁用某个
			String sql = "UPDATE CW_MBB SET ZT = '0' WHERE gid = '"+gid+"'" ;
			try {
				i = db.update(sql);
			} catch (DataAccessException e) {
				SQLException sqle = (SQLException) e.getCause();
				logger.error("数据库操作失败\n" + sqle);
				return -1;
			}
		}
		return i;
	}
	
	/**
	 * 账表模板保存
	 * @param mb
	 * @param gid
	 * @return
	 */
//	public boolean doSave(String mb,String gid) {
//		ArrayList<String> list =new  ArrayList<String>(); 
//		String sql = "UPDATE CW_MBB SET MBMC = '"+mb+"' WHERE gid = '" + gid + "'";
//		System.out.println(mb+"模板名称");
//		list.add(sql);
//		sql="update zc_sys_mkb t set t.qxbz='1'  where t.mkbh =(select a.mkid from cw_mkgxb a  where a.mbid ='"+gid+"' and a.mbxh='"+mb+"')";
//		list.add(sql);
//		if(mb.equals("1")){
//			mb="2";
//		}else{
//			mb="1";
//		}
//		sql="update zc_sys_mkb t set t.qxbz='0'  where t.mkbh =(select a.mkid from cw_mkgxb a  where a.mbid ='"+gid+"' and a.mbxh='"+mb+"')";
//		list.add(sql);
//		return db.batchUpdate(list);
//	}
	public boolean doSave(String mb,String gid) {
		ArrayList<String> list =new  ArrayList<String>(); 
		String sql = "UPDATE CW_MBB SET MBMC = '"+mb+"' WHERE gid = '" + gid + "'";
		list.add(sql);
		return db.batchUpdate(list);
	}
   /**
    * 保存凭证删除
    * @param ms
    * @return
    */
	public boolean doSavePzsc(String ms,String kmbh,String xmbh,String bmbh,String xxjjhj,String xxjjhd,String xxjjzj,String xxjjzd,String xyjjj,String xyjjd,String glf,String xxxm,String xyxm,String xxxmbmbh,String xyxmbmbh) {
		ArrayList<String> list =new  ArrayList<String>(); 
		String sql = "update GX_SYS_DMB set ms = '"+ms+"' where zl='pzscsz' and dm = '01'";
		String sql2 = "update GX_SYS_DMB set ms = '"+kmbh+"' where zl='jkkmbh' and dm = '01'";
		String sql3 = "update GX_SYS_DMB set ms = '"+xmbh+"' where zl='xzxmbh' and dm = '01'";
		String sql4 = "update GX_SYS_DMB set ms = '"+bmbh+"' where zl='xzbmbh' and dm = '01'";
		String sql5 = "update GX_SYS_DMB set ms = '"+xxjjhj+"' where zl='xxjjhj' and dm = '01'";
		String sql6 = "update GX_SYS_DMB set ms = '"+xxjjhd+"' where zl='xxjjhd' and dm = '01'";
		String sql7 = "update GX_SYS_DMB set ms = '"+xxjjzj+"' where zl='xxjjzj' and dm = '01'";
		String sql8 = "update GX_SYS_DMB set ms = '"+xxjjzd+"' where zl='xxjjzd' and dm = '01'";
		String sql9 = "update GX_SYS_DMB set ms = '"+xyjjj+"' where zl='xyjjj' and dm = '01'";
		String sql10 = "update GX_SYS_DMB set ms = '"+xyjjd+"' where zl='xyjjd' and dm = '01'";
		String sql11 = "update GX_SYS_DMB set ms = '"+glf+"' where zl='glf' and dm = '01'";
		String sql12 = "update GX_SYS_DMB set ms = '"+xxxm+"',mc='"+xxxmbmbh+"' where zl='xxxm' and dm = '01'";
		String sql13 = "update GX_SYS_DMB set ms = '"+xyxm+"',mc='"+xyxmbmbh+"' where zl='xyxm' and dm = '01'";
		list.add(sql);
		list.add(sql2);
		list.add(sql3);
		list.add(sql4);
		list.add(sql5);
		list.add(sql6);
		list.add(sql7);
		list.add(sql8);
		list.add(sql9);
		list.add(sql10);
		list.add(sql11);
		list.add(sql12);
		list.add(sql13);
		return db.batchUpdate(list);
	}
	
	public int doSavePzscscsc(String ms) {
		String sql = "update BZTFL set FLFLFL = '"+ms+"' ";
		return db.update(sql);
	}
	/**
	    * 保存凭证删除
	    * @param ms
	    * @return
	    */
		public int doSaveZzzy(String ms) {
			String sql = "update GX_SYS_DMB set ms = '"+ms+"' where zl='zzzy' ";
			return db.update(sql);
		}
	
	/**
	 * 报销类型金额设置
	 * @param map
	 * @return
	 */
	public int doSaveBxlx(Map map) {
		String zyje1 = Validate.isNullToDefaultString(map.get("zyje1"),"0");
		String bmje1 = Validate.isNullToDefaultString(map.get("bmje1"),"0");
		String kyje1 = Validate.isNullToDefaultString(map.get("kyje1"),"0");
		String kyje2 = Validate.isNullToDefaultString(map.get("kyje2"),"0");
		String kyje3 = Validate.isNullToDefaultString(map.get("kyje3"),"0");
		String sql = "update BXLXJE t set t.zyje1=?,t.bmje1=?,t.kyje1=?,t.kyje2=?,t.kyje3=?";
		return db.update(sql,new Object[]{zyje1,bmje1,kyje1,kyje2,kyje3});
	}
	
	public Map getBxlxInfo(){
		String sql="select to_char(zyje1,'fm999999990.00')zyje1,to_char(bmje1,'fm999999990.00')bmje1,to_char(kyje1,'fm999999990.00')kyje1," +
				"to_char(kyje2,'fm999999990.00')kyje2,to_char(kyje3,'fm999999990.00')kyje3 from bxlxje where rownum=1";
		return db.queryForMap(sql);
	}
/**
 * 保存打印参数信息
 * @param pd
 * @return
 */
	public boolean doSaveDyxx(PageData pd) {
		String json = pd.getString("json");	//得到前台的json
		String ajson = json.substring(8,json.length()-1);//截取json,让gson用
		Gson gson = new Gson();
		List list = gson.fromJson(ajson, new TypeToken<List>(){}.getType());//将json转为list
		List<String> listSql=new ArrayList<String>();
		for (int i=0;i<list.size();i++) {
			Map map = (Map) list.get(i);
			listSql.add("update GX_SYS_DMB set mc='"+map.get("dycsmc")+"' where zl='dyxx' and dm='"+map.get("dycsdm")+"'");
		}
		return db.batchUpdate(listSql);
	}

/**
 * 获得打印信息参数
 * @return
 */
public List getDyxx() {
	String sql="select * from GX_SYS_DMB where zl='dyxx'";
	return db.queryForList(sql);
}
}
