package com.googosoft.dao.system.suggest;

import java.util.List;
import java.util.Map;
import javax.annotation.Resource;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.commons.LUser;
import com.googosoft.constant.Constant;
import com.googosoft.constant.SystemSet;
import com.googosoft.constant.TnameU;
import com.googosoft.dao.base.BaseDao;
import com.googosoft.dao.systemset.qxgl.GlqxbDao;
import com.googosoft.pojo.StateManager;
import com.googosoft.util.Validate;

@Repository("suggestDao")
public class SuggestDao extends BaseDao{
	@Resource(name="glqxbDao")
	private GlqxbDao glqxbDao;
	
	/**
	 * 手输联想获取单位信息（管理权限下的单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getDwxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.bmh||')'||d.mc dwxx from gx_sys_dwb d where '('||d.bmh||')'||d.mc like ? " +glqxbDao.getQxsql(userId,"d.dwbh","D")+" and dwzt='1' and rownum<=10 order by d.dwbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwxx")+";;");
			}else{
				suggestXx.append(map.get("dwxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取数据字典信息
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getZdxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.dm||')'||d.mc zdxx from GX_SYS_DMB d where '('||d.dm||')'||d.mc like ? and rownum<=10 and zl='00' order by d.dm";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("zdxx")+";;");
			}else{
				suggestXx.append(map.get("zdxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 联想输入专业信息
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getZYxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "SELECT '('||D.ZYBH||')'||D.ZYMC ZYXX FROM CW_ZYXXB D WHERE '('||D.ZYBH||')'||D.ZYMC LIKE ? AND ROWNUM<=10 ORDER BY D.ZYBH";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ZYXX")+";;");
			}else{
				suggestXx.append(map.get("ZYXX"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 联想输入班级信息
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getBJxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "SELECT '('||D.BJBH||')'||D.BJMC BJXX FROM CW_BJXXB D WHERE '('||D.BJBH||')'||D.BJMC LIKE ? AND ROWNUM<=10 ORDER BY D.BJBH";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("BJXX")+";;");
			}else{
				suggestXx.append(map.get("BJXX"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 联想输入年级信息
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getNJxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "SELECT '('||D.NJBH||')'||D.NJMC NJXX FROM CW_NJXXB D WHERE '('||D.NJBH||')'||D.NJMC LIKE ? AND ROWNUM<=10 ORDER BY D.NJBH";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("NJXX")+";;");
			}else{
				suggestXx.append(map.get("NJXX"));
			}
		}
		return suggestXx.toString();
	}
	
	
	/**
	 * 手输联想获取单位信息（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllDwxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.bmh||')'||d.mc as dwxx from gx_sys_dwb d where '('||d.bmh||')'||d.mc like ? and dwzt='1' and rownum<=10 order by d.bmh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwxx")+";;");
			}else{
				suggestXx.append(map.get("dwxx"));
			}
		}
		return suggestXx.toString();
	}
	
	/**
	 * 手输联想获取项目类型（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllXmlx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.xmlxbh||')'||d.xmlxmc as xmlxxx from CW_XMLXB d  where '('||d.xmlxbh||')'||d.xmlxmc like ?  and rownum<=10 order by d.xmlxbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取项目名称（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getXmmc(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.xmbh||')'||d.xmmc as xmmc from XMINFOS d  where '('||d.xmbh||')'||d.xmmc like ? and bsqr='"+LUser.getRybh()+"' and rownum<=10 order by d.xmbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			//if(i<list.size()-1){
				suggestXx.append(map.get("xmmc")+";;");
//			}else{
//				suggestXx.append(map.get("xmmc"));
//			}
		}
		return suggestXx.toString();
	}
	
	/**
	 * 手输联想获取经济科目（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllJjkm(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_JJKMB d   where '('||d.kmbh||')'||d.kmmc like ?  and rownum<=10  order by d.kmbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取经济科目（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllBmxm(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.xmbh||')'||d.xmmc as xmlxxx from cw_xmjbxxb d   where '('||d.xmbh||')'||d.xmmc like ?  and rownum<=10  order by d.xmbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取功能科目（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllGnkm(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_GNKMB d   where '('||d.kmbh||')'||d.kmmc like ?  and rownum<=10  order by d.kmbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取会计科目（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllKjkm(String value, String flag) {
		value = "%"+value+"%";
		String sql = "";
		System.err.println("flag================="+flag);
		if("YHCK".equals(flag)){
			sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and d.kmbh like '1002%' and rownum<=10  order by d.kmbh";
		}else if("KCXJ".equals(flag)){
			sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and d.kmbh like '1001%' and rownum<=10  order by d.kmbh";
		}else if("LYE".equals(flag)){
			sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and d.kmbh like '1011%' and rownum<=10  order by d.kmbh";
		}
		else{
			sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_KJKMSZB d   where '('||d.kmbh||')'||d.kmmc like ?  and rownum<=10  order by d.kmbh";
		}
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取功能菜单（所有单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getAllGncd(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.mkbh||')'||d.mkmc as xmlxxx from zc_sys_mkb d   where '('||d.mkbh||')'||mkmc like ?  and rownum<=10  order by d.mkbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		return suggestXx.toString();
	}
	
	/**
	 * 得到二级目录
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getEjKjkm(String value, String userId,String sjkm) {
		value = "%"+value+"%";
		String sql = "select '('||d.kmbh||')'||d.kmmc as xmlxxx from CW_KJKMSZB d where sjfl = ? and rownum<=10 order by d.kmbh";
		List list = db.queryForList(sql,new Object[]{sjkm});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("xmlxxx")+";;");
			}else{
				suggestXx.append(map.get("xmlxxx"));
			}
		}
		System.err.println("__________________"+suggestXx.toString());
		return suggestXx.toString();
	}
	
	/**
	 * 二级单位（有管理权限限制）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getEjdw(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.bmh||')'||d.mc dwxx from gx_sys_dwb d where '('||d.bmh||')'||d.mc like ? " +glqxbDao.getQxsql(userId,"d.dwbh","D")+" and d.dwjc='"+Constant.DWJC_EJ+"' and dwzt='1' and rownum<=10 order by d.dwbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwxx")+";;");
			}else{
				suggestXx.append(map.get("dwxx"));
			}
		}
		return suggestXx.toString();
	}
		
	/**
	 * 手输联想获取单位信息（经费追加用到的单位）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getJFDwxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.bmh||')'||d.mc dwxx from gx_sys_dwb d where '('||d.bmh||')'||d.mc like ? and d.dwbh not in (select sydw from zc_wxjfhb where nd='"+Constant.MR_YEAR()+"') " +glqxbDao.getQxsql(userId,"d.dwbh","D")+" and d.dwjc='"+Constant.DWJC_EJ+"' and dwzt='1' and rownum<=10 order by d.dwbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwxx")+";;");
			}else{
				suggestXx.append(map.get("dwxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取单位信息（卡片的使用单位）
	 * @param value
	 * @return
	 */
	public String getSyDwxx(String value, String userId) {
		String sfmjsql = "select sftjzmj from zc_sys_xtb";
		String sfmj = db.queryForSingleValue(sfmjsql);
		String dwbhsql = "select dwbh as bh from "+TnameU.GLQXB+" where zl='2'  and rybh = ?";
		List listdw = db.queryForList(dwbhsql, new Object[]{userId});
		StringBuffer dwbhs = new StringBuffer();
		if(listdw.size()==0){
			String sql = "select dwbh from gx_sys_ryb where rybh=?";
			String DWBH = db.queryForSingleValue(sql, new Object[]{userId});
			dwbhs.append(DWBH);
		}else{
			for (int i = 0; i < listdw.size(); i++) {
				Map map = (Map)listdw.get(i);
				dwbhs.append(Validate.isNullToDefault(map.get("BH"), ""));
				if(i != listdw.size()-1){
					dwbhs.append("','");
				}
			}
		}
		String dwbh = dwbhs.toString();
		//value = "%"+value+"%";//如果是查询的话，service里边已经加%号了
		StringBuffer sql = new StringBuffer();
		sql.append("select * from (select '(' || bmh || ')' || mc as dwxx,");
		if("0".equals(sfmj)){
			sql.append(" '1' ischeck ");
		}else{
			sql.append("(case (select count(dwbh) from gx_sys_dwb d where d.sjdw = t.dwbh) when 0 then '1' else '0' end) ischeck ");
		}
		sql.append("from gx_sys_dwb t where 1=1 and dwzt='1' ");
		sql.append("and t.dwbh in (select dwbh from gx_sys_dwb start with dwbh in ('"+dwbh+"') connect by prior dwbh = sjdw ) and t.dwzt = 1 ) k ");
		sql.append("where ischeck = 1 and dwxx like ?");
		List list = db.queryForList(sql.toString(),new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwxx")+";;");
			}else{
				suggestXx.append(map.get("dwxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 联想输入获取二级单位信息（单位内和单位间调拨用到）
	 * @param value
	 * @param userId
	 * @param ejdw  二级单位
	 * @param flag 区分单位内和单位间的标志
	 * @return
	 */
	public String getDwxx(String value, String userId, String ejdw, String flag) {
		value = "%"+value+"%";
		String where = "";
		if(StateManager.BDXM_DWNDB.equals(flag)){
			where = " and d.dwbh in(select dwbh from gx_sys_dwb start with dwbh = '"+ejdw+"' connect by prior dwbh=sjdw and dwzt='1') ";
		}else if(StateManager.BDXM_DWJDB.equals(flag)){
			where = " and d.dwbh in(select dwbh from gx_sys_dwb start with dwbh in(select dwbh from gx_sys_dwb where dwbh not in(select dwbh from gx_sys_dwb start with dwbh='"+ejdw+"' connect by prior dwbh = sjdw) "+glqxbDao.getQxsql(userId, "dwbh", "D")+") connect by prior dwbh=sjdw and dwzt='1') ";
		}
		String sql = "select '('||d.bmh||')'||d.mc as dwxx from gx_sys_dwb d where '('||d.bmh||')'||d.mc like ? and d.dwzt='1' "+where+" and rownum<=10 order by d.dwbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwxx")+";;");
			}else{
				suggestXx.append(map.get("dwxx"));
			}
		}
		return suggestXx.toString();
	}

	/**
	 * 手输联想获取人员信息（单位权限下的人员）
	 * @param value 输入的值
	 * @param userId 当前登录人
	 * @return
	 */
	public String getRyxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||r.rygh||')'||r.xm ryxx1 from gx_sys_ryb r where '('||r.rygh||')'||r.xm like ? and r.rybh <> '000000' and ryzt='1' "+glqxbDao.getQxsql(userId, "r.dwbh", "D")+" and rownum<=10 order by r.rybh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ryxx1")+";;");
			}else{
				suggestXx.append(map.get("ryxx1"));
			}
		}
		return suggestXx.toString();
	}
	
	public String getDRyxx(String value, String userId){
		String sql = "select '('||r.rygh||')'||r.xm ryxx from gx_sys_ryb r where '('||r.rygh||')'||r.xm like ? and r.rybh <> '000000' and ryzt='1' and dwbh = '" + LUser.getDwbh() + "' and rownum <= 10 order by r.rybh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ryxx")+";;");
			}else{
				suggestXx.append(map.get("ryxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 采购人联想
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getCgrRyxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||r.rygh||')'||r.xm as ryxx from gx_sys_ryb r where '('||r.rygh||')'||r.xm like ? and r.rybh <> '000000' and ryzt='1' and rownum<=10 order by r.rybh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ryxx")+";;");
			}else{
				suggestXx.append(map.get("ryxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 学生联想
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getXsRyxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||r.rygh||')'||r.xm as ryxx from gx_sys_ryb r where '('||r.rygh||')'||r.xm like ? and r.rybh <> '000000' and ryzt='1' and r.type = 'S' order by r.rybh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ryxx")+";;");
			}else{
				suggestXx.append(map.get("ryxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 教师联想
	 * @param value
	 * @param userId
	 * @return
	 */
	public String getJsRyxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||r.rygh||')'||r.xm as ryxx from gx_sys_ryb r where '('||r.rygh||')'||r.xm like ? and r.rybh <> '000000' and ryzt='1' and r.type = 'T' order by r.rybh ";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ryxx")+";;");
			}else{
				suggestXx.append(map.get("ryxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 手输联想获取二级单位人员信息（单位内和单位间调拨用到）
	 * @param value
	 * @param userId
	 * @param ejdw 二级单位
	 * @param flag 区分单位内和单位间的标志
	 * @return
	 */
	public String getRyxx(String value, String userId, String ejdw, String flag) {
		String where = "";
		if(StateManager.BDXM_SYRBD.equals(flag)){
			where = " and dwbh = '" + LUser.getDwbh() + "' ";
		}else if(StateManager.BDXM_DWNDB.equals(flag)){
			where = " and r.dwbh in (select dwbh from gx_sys_dwb start with dwbh = '"+ejdw+"' and dwzt='1' connect by prior dwbh = sjdw)  ";//and dwbh <> '" + LUser.getDwbh() + "'
		}else if(StateManager.BDXM_DWJDB.equals(flag)){
			where = " and dwbh not in (select dwbh from gx_sys_dwb start with dwbh = '"+ejdw+"' and dwzt='1' connect by prior dwbh = sjdw) " + glqxbDao.getQxsql(userId, "dwbh", "D") + " ";
		}
		
		String sql = "select '('||r.rygh||')'||r.xm ryxx from gx_sys_ryb r where '('||r.rygh||')'||r.xm like ? and r.rybh <> '"+SystemSet.AdminRybh()+"' and r.ryzt = '1' "+where+" and rownum<=10 order by r.rybh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ryxx")+";;");
			}else{
				suggestXx.append(map.get("ryxx"));
			}
		}
		return suggestXx.toString();
	}
	
	/**
	 * 获取教育部十六大类分类信息
	 * @param value
	 * @return
	 */
	public String getJybfl(String value) {
		value = "%"+value+"%";
		String sql = "select '('||f.flh||')'||f.flmc as flxx from zc_flb_jyb f where '('||f.flh||')'||f.flmc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLXX")+";;");
			}else{
				suggestXx.append(map.get("FLXX"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取教育部十六大类分类信息(带权限)
	 * @param value
	 * @return
	 */
	public String getJybflh(String value,String rybh) {
		value = ""+value+"%";
		String sql = "select '('||flh||')'||flmc as flh from zc_flb_jyb where (flh like ? or flmc like '%"+value+"%') and (substr(flh,0,2) in (select substr(dwbh,0,2) from "+TnameU.GLQXB+" where rybh = ? and zl='1') or (select count(*) from "+TnameU.GLQXB+" where rybh = ? and zl='1') = 0 or (select count(*) from "+TnameU.GLQXB+" where rybh = ? and zl='1' and dwbh = '00') > 0) and rownum <= 10";
		List list = db.queryForList(sql,new Object[]{value,rybh,rybh,rybh});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLH")+";;");
			}else{
				suggestXx.append(map.get("FLH"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取教育部十六大类分类信息(带权限)
	 * @param value
	 * @return
	 */
	public String getJybflmc(String value,String rybh) {
		value = ""+value+"%";
		String sql = "select '('||flh||')'||flmc as flh from zc_flb_jyb where sfmj = '1' and flh like ? and (substr(flh,0,2) in (select substr(dwbh,0,2) as bh from zc_sys_glqxb where rybh = ? and zl='1') or (select count(rybh) from zc_sys_glqxb where rybh = ? and zl='1') = 0) and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value,rybh,rybh});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLH")+";;");
			}else{
				suggestXx.append(map.get("FLH"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取教育部十六大类分类信息(带权限)
	 * @param value
	 * @return
	 */
	public String getJybflmcH(String value,String rybh) {
		value = ""+value+"%";
		String sql = "select '('||flh||')'||flmc as flh from zc_flb_jyb where sfmj = '1' and flh like ? and (substr(flh,0,2) in (select substr(dwbh,0,2) from "+TnameU.GLQXB+" where rybh = ? and zl='1') or (select count(*) from "+TnameU.GLQXB+" where rybh = ? and zl='1') = 0 or (select count(*) from "+TnameU.GLQXB+" where rybh = ? and zl='1' and dwbh = '00') > 0) and rownum <= 10";
		List list = db.queryForList(sql,new Object[]{value,rybh,rybh,rybh});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLH")+";;");
			}else{
				suggestXx.append(map.get("FLH"));
			}
		}
		return suggestXx.toString();
	}

	/**
	 * 验证教育部十六大类分类信息(带权限)
	 * @param value
	 * @param flag  是否必须是末级
	 * @return
	 */
	public String validateJybflmc(String value,String rybh,boolean flag){
		String sql = "select count(flh) from zc_flb_jyb where (flh = ? or '('||flh||')'||flmc = ?) " + (flag ? " and sfmj = '1' " : "") + " and (substr(flh,0,2) in (select substr(dwbh,0,2) as bh from zc_sys_glqxb where rybh = ? and zl='1') or (select count(rybh) from zc_sys_glqxb where rybh = ? and zl='1') = 0 or (select count(rybh) from zc_sys_glqxb where rybh = ? and zl='1' and dwbh = '00') > 0)";
		int counts = db.queryForObject(sql,new Object[]{value,value,rybh,rybh,rybh},Integer.class);
		if(counts > 0){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}

	/**
	 * 获取财政六大类分类信息
	 * @param value
	 * @return
	 */
	public String getCzbfl(String value) {
		value = "%"+value+"%";
		String sql = "select '('||f.flh||')'||f.flmc flxx from zc_flb_czbn f where '('||f.flh||')'||f.flmc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLXX")+";;");
			}else{
				suggestXx.append(map.get("FLXX"));
			}
		}
		return suggestXx.toString();
	}

	/**
	 * 获取财政六大类分类信息（带权限）
	 * @param value
	 * @return
	 */
	public String getCzbflqx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||f.flh||')'||f.flmc flxx from zc_flb_czbn f where '('||f.flh||')'||f.flmc like ?" + glqxbDao.getQxsql(LUser.getRybh(), "flh", "C") + " and rownum <= 10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLXX")+";;");
			}else{
				suggestXx.append(map.get("FLXX"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取财政六大类分类信息
	 * @param value
	 * @return
	 */
	public String getCzbCzfldz(String value) {
		value = "%"+value+"%";
		String sql = "select '('||f.flh||')'||f.flmc flxx from zc_flb_czbn f where '('||f.flh||')'||f.flmc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLXX")+";;");
			}else{
				suggestXx.append(map.get("FLXX"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取财政十大类分类信息
	 * @param value
	 * @return
	 */
	public String getCzbfls(String value) {
		value = "%"+value+"%";
		String sql = "select '('||f.zcdm||')'||f.mc flxx from zc_flb_czbo f where '('||f.zcdm||')'||f.mc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("FLXX")+";;");
			}else{
				suggestXx.append(map.get("FLXX"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 验证教育部十六大类信息
	 * @param value
	 * @return
	 */
	public String validateJybfl(String value) {
		String sql = "select count(f.flh) from zc_flb_jyb f where '('||f.flh||')'||f.flmc = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	/**
	 * 验证财政六大类信息
	 * @param value
	 * @return
	 */
	public String validateCzbfl(String value) {
		String sql = "select count(f.flh) from zc_flb_czbn f where '('||f.flh||')'||f.flmc = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	/**
	 * 验证财政六大类信息（带权限）
	 * @param value
	 * @return
	 */
	public String validateCzbflqx(String value) {
		String sql = "select count(f.flh) from zc_flb_czbn f where '('||f.flh||')'||f.flmc = ?" + glqxbDao.getQxsql(LUser.getRybh(), "flh", "C");
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if("1".equals(counts)){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	/**
	 * 验证财政六大类信息,财政分类对照
	 * @param value
	 * @return
	 */
	public String validategetCzbCzfldz(String value) {
		String sql = "select count(f.flh) from zc_flb_czbn f where f.flh = ?";
		int counts = db.queryForObject(sql,new Object[]{value},Integer.class);
		if(counts>0){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	/**
	 * 获取地点信息
	 * @param value
	 * @return
	 */
	public String getDdxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||d.ddh||')'||d.mc ddxx from zc_sys_ddb d where '('||d.ddh||')'||d.mc like ? and  DDZT='1' and rownum<=10 order by d.ddbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("ddxx")+";;");
			}else{
				suggestXx.append(map.get("ddxx"));
			}
		}
		return suggestXx.toString();
	}
	
	/**
	 * 获取学科信息
	 * @param value
	 * @return
	 */
	public String getXkxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||d.dm||')'||d.mc dmxx from gx_sys_dmb d where zl=? and '('||d.dm||')'||d.mc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{Constant.XKML,value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dmxx")+";;");
			}else{
				suggestXx.append(map.get("dmxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取学科信息
	 * @param value
	 * @return
	 */
	public String getFykmxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||f.fybh||')'||f.fymc fyxx from Cw_fykmdzb f where  '('||f.fybh||')'||f.fymc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("fyxx")+";;");
			}else{
				suggestXx.append(map.get("fyxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 获取国别信息
	 * @param value
	 * @return
	 */
	public String getGbxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||d.dm||')'||d.mc as dmxx from gx_sys_dmb d where zl=? and '('||d.dm||')'||d.mc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{Constant.GB,value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dmxx")+";;");
			}else{
				suggestXx.append(map.get("dmxx"));
			}
		}
		return suggestXx.toString();
	}
	public String validateDwxx(String value) {
		String sql = "select count(d.bmh) as counts from gx_sys_dwb d where '('||d.bmh||')'||d.mc = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	public String validateXmlx(String value) {
		String sql = "select count(d.xmlxbh) as counts from CW_XMLXB d where '('||d.xmlxbh||')'||d.xmlxmc = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	public String validateKjkm(String value,String kjzd) {
		System.err.println("value===="+value);
		String sql = "select count(d.kmbh) as counts from CW_KJKMSZB d where '('||d.kmbh||')'||d.kmmc = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		System.err.println("===counts==="+counts);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	
	
	public String validateGncd(String value,String kjzd) {
		System.err.println("value===="+value);
		String sql = "select count(d.mkbh) as counts from zc_sys_mkb d where '('||d.mkbh||')'||d.mkmc = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		System.err.println("===counts==="+counts);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateGnkm(String value,String kjzd) {
		System.err.println("value===="+value);
		String sql = "select count(d.kmbh) as counts from Cw_Gnkmb d where '('||d.kmbh||')'||d.kmmc = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		System.err.println("===counts==="+counts);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateJjkm(String value,String kjzd) {
		System.err.println("value===="+value);
		String sql = "select count(d.kmbh) as counts from CW_JJKMB d where '('||d.kmbh||')'||d.kmmc = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		System.err.println("===counts==="+counts);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	
	public String validateKjkmej(String value) {
//		String sql = "select count(d.kmbh) as counts from CW_KJKMSZB d where '('||d.kmbh||')'||d.kmmc = ?";
//		String counts = db.queryForObject(sql,new Object[]{value},String.class);
//		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
//		}else{
//			return "{\"success\":\"false\"}";
//		}
	}
	
	public String validateZYxx(String value) {
		String sql = "SELECT COUNT(D.ZYBH) FROM CW_ZYXXB D WHERE '('||D.ZYBH||')'||D.ZYMC = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateNJxx(String value) {
		String sql = "SELECT COUNT(D.NJBH) FROM CW_NJXXB D WHERE '('||D.NJBH||')'||D.NJMC = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateBJxx(String value) {
		String sql = "SELECT COUNT(D.BJBH) FROM CW_BJXXB D WHERE '('||D.BJBH||')'||D.BJMC = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateZdxx(String value) {
		//String sql = "select count(d.bmh) as counts from gx_sys_dwb d where '('||d.bmh||')'||d.mc = ?";
		String sql = "select count(d.dm) from GX_SYS_DMB d where '('||d.dm||')'||d.mc = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateRyxx(String value) {
		String sql = "select count(r.rygh) from gx_sys_ryb r where '('||r.rygh||')'||r.xm = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateJsRyxx(String value) {
		String sql = "select count(r.rygh) from gx_sys_ryb r where '('||r.rygh||')'||r.xm = ? and type='T'";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateXsRyxx(String value) {
		String sql = "select count(r.rygh) from gx_sys_ryb r where '('||r.rygh||')'||r.xm = ? and type='S'";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateEjdw(String value) {
		String sql = "select count(d.bmh) counts from gx_sys_dwb d where '('||d.bmh||')'||d.mc = ? " + glqxbDao.getQxsql(LUser.getRybh(),"d.dwbh","D") + " and d.dwjc='"+Constant.DWJC_EJ+"' and dwzt='1'";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateJFDwxx(String value) {
		String sql = "select count(d.bmh) counts from gx_sys_dwb d where '('||d.bmh||')'||d.mc = ? " + glqxbDao.getQxsql(LUser.getRybh(),"d.dwbh","D") + " and d.dwjc='"+Constant.DWJC_EJ+"' and dwzt='1' and d.dwbh not in (select sydw from zc_wxjfhb where nd='"+Constant.MR_YEAR()+"')";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	public String validateWXS(String value) {
		String sql = "select count(r.gsbh) from zc_wxsinfor r where '('||r.gsbh||')'||r.mc = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	//地点信息验证
	public String validateDdxx(String value) {
		String sql = "select count(r.ddh) from zc_sys_ddb r where '('||r.ddh||')'||r.mc = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	//费用信息验证
	public String validateFyxx(String value) {
		String sql = "select '('||f.fybh||')'||f.fymc fyxx from Cw_fykmdzb f where  '('||f.fybh||')'||f.fymc = ? ";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	//学科信息验证
		public String validateXkxx(String value) {
			String sql = "select count(r.dm) from gx_sys_dmb r where zl=? and '('||r.dm||')'||r.mc = ?";
			String counts = db.queryForObject(sql,new Object[]{Constant.XKML,value},String.class);
			if(counts.equals("1")){
				return "{\"success\":\"true\"}";
			}else{
				return "{\"success\":\"false\"}";
			}
		}
	//国别信息验证
	public String validateGbxx(String value) {
		String sql = "select count(r.dm) from gx_sys_dmb r where zl=? and '('||r.dm||')'||r.mc = ?";
		String counts = db.queryForObject(sql,new Object[]{Constant.GB,value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	//维修商信息
	public String getWxsxx(String value, String userId) {
		value = "%"+value+"%";
		String sql = "select '('||d.gsbh||')'|| d.mc wxsxx from zc_wxsinfor d where '('||d.gsbh||')'||d.mc like ? and rownum<=10 order by d.gsbh";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("wxsxx")+";;");
			}else{
				suggestXx.append(map.get("wxsxx"));
			}
		}
		return suggestXx.toString();
	}
	public String validategetCzbfls(String value) {
		String sql = "select count(f.zcdm) from zc_flb_czbo f where f.zcdm = ?";
		String counts = db.queryForObject(sql,new Object[]{value},String.class);
		if(counts.equals("1")){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	/**
	 * 手输联想获取往来单位
	 * @param value 输入的值
	 * @return
	 */
	public String getWldw(String value) {
		value = "%"+value+"%";
		String sql = "select * from cw_wldwb where dwmc like ?";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		System.out.println("list=="+list);
		System.out.println("list.size()=="+list.size());
		for(int i=0;i<list.size();i++){
			Map map  = (Map) list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwmc")+";;");
			}else{
				suggestXx.append(map.get("dwmc"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 专业方向
	 * @param value
	 * @return
	 */
	public String getWLDW(String value) {
		value = "%"+value+"%";
		String sql = "select '('||d.wlbh||')'||d.dwmc dwmc from CW_WLDWB d where  '('||d.wlbh||')'||d.dwmc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("dwmc")+";;");
			}else{
				suggestXx.append(map.get("dwmc"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 验证往来单位
	 * @param value
	 * @return
	 */
	public String validategetWldw(String value) {
		String sql = "select count(dwmc) from cw_wldwb where dwmc = ?";
		int counts = db.queryForObject(sql,new Object[]{value},Integer.class);
		if(counts>0){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	/**
	 * 项目分类名称
	 * @param value
	 * @return
	 */
	public String getFlmcxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||d.dm||')'||d.mc mcxx from gx_sys_dmb d where d.zl=? and '('||d.dm||')'||d.mc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{Constant.XMFL,value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("mcxx")+";;");
			}else{
				suggestXx.append(map.get("mcxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 专业方向
	 * @param value
	 * @return
	 */
	public String getZyfxxx(String value) {
		value = "%"+value+"%";
		String sql = "select '('||d.dm||')'||d.mc mcxx from gx_sys_dmb d where d.zl=? and '('||d.dm||')'||d.mc like ? and rownum<=10";
		List list = db.queryForList(sql,new Object[]{Constant.ZYFX,value});
		StringBuffer suggestXx = new StringBuffer(); 
		for(int i=0;i<list.size();i++){
			Map map = (Map)list.get(i);
			if(i<list.size()-1){
				suggestXx.append(map.get("mcxx")+";;");
			}else{
				suggestXx.append(map.get("mcxx"));
			}
		}
		return suggestXx.toString();
	}
	/**
	 * 验证往来单位
	 * @param value
	 * @return
	 */
	public String validategetXmmc(String value) {
		String sql = "select count(xmmc) from XMINFOS where  '('||xmbh||')'||xmmc = ?";
		int counts = db.queryForObject(sql,new Object[]{value},Integer.class);
		if(counts>0){
			return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
	}
	//分类名称验证
	public String validateFlmc(String value) {
//			String sql = "select '('||b.fybh||')'||f.fymc fyxx from gx_sys_dmb b where  '('||f.fybh||')'||f.fymc = ? ";
//			String counts = db.queryForObject(sql,new Object[]{value},String.class);
//			if(counts.equals("1")){
			return "{\"success\":\"true\"}";
//			}else{
//				return "{\"success\":\"false\"}";
//			}
	}
	public String validateZyfx(String value) {
//		String sql = "select '('||b.fybh||')'||f.fymc fyxx from gx_sys_dmb b where  '('||f.fybh||')'||f.fymc = ? ";
//		String counts = db.queryForObject(sql,new Object[]{value},String.class);
//		if(counts.equals("1")){
		return "{\"success\":\"true\"}";
//		}else{
//			return "{\"success\":\"false\"}";
//		}
}
	public String validateWLDW(String value) {
		String sql = "select count(0) from CW_WLDWB d where  '('||d.wlbh||')'||d.dwmc = ?";
		int counts = db.queryForObject(sql,new Object[]{value},Integer.class);
		if(counts>0){
		return "{\"success\":\"true\"}";
		}else{
			return "{\"success\":\"false\"}";
		}
}
}
