package com.googosoft.service.base;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.googosoft.commons.ToSqlUtil;
import com.googosoft.dao.base.PageDao;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.service.common.DwbService;
import com.googosoft.service.systemset.qxgl.GlqxbService;
import com.googosoft.util.FileUtil;
import com.googosoft.util.PageData;
import com.googosoft.util.StringUtils;
import com.googosoft.util.UuidUtil;
import com.googosoft.util.Validate;

/**
 * 列表信息service
 * @author master
 */
@Service("pageService")
public class PageService extends BaseService{
	@Resource(name="pageDao")
	public PageDao pageDao;
	
	@Resource(name="glqxbService")
	private  GlqxbService glqxbService;//单例

	@Resource(name="deptService")
	private  DwbService deptService;//单例
	
	/**
	 * 普通列表数据
	 * @param pd
	 * @param pagelist
	 * @return
	 */
	public PageList findPageList(PageData pd,PageList pagelist){
		return pageDao.findPageList(pd,pagelist);
	}
	/**
	 * 列表查询，无order by排序的查询
	 * @author 作者：BiMing
	 * @version 创建时间:2018-4-14下午6:58:47
	 */
	public PageList findPageListA(PageData pd,PageList pagelist){
		return pageDao.findPageListA(pd,pagelist);
	}
	
	public PageList findPageList1(PageData pd,PageList pagelist){
		return pageDao.findPageList1(pd,pagelist);
	}
	/**
	 * 带分组的特殊列表数据
	 * @param pd
	 * @param pagelist
	 * @return
	 */
	public PageList findPageList(PageData pd,PageList pagelist,String groupSql){
		return pageDao.findPageList(pd,pagelist,groupSql);
	}
	
	/**
	 * 公共导出方法
	 * @param request
	 * @param pagelist
	 * @param filedisplay
	 * @param columns
	 * @return
	 */
	public String doExp(HttpServletRequest request,PageList pagelist,PageData pd){
		//临时文件名
		String file = System.currentTimeMillis()+"";
		//文件绝对路径
		String realfile = request.getServletContext().getRealPath("\\")+"WEB-INF\\file\\excel\\"+file+".xls";
		
		List<M_largedata> mlist = joinExpCol(pd.getString("columns"));
		
		StringBuffer wh = new StringBuffer();
		//查询数据的sql语句
		String searchJson = ToSqlUtil.jsonToSql(pd.getString("searchJson"));
		if(Validate.noNull(searchJson)){
			wh.append(searchJson + " ");//查询条件语句
		}
		//导出时选择的id
		wh.append(ToSqlUtil.getLongInSql(pd.getString("id"), pagelist.getKeyId()));
		pagelist.setStrWhere(Validate.isNullToDefaultString(pagelist.getStrWhere(),"") + wh);
		
		//处理导出时的排序
		String ordercol = pd.getString("ordercol");
		if(Validate.isNull(pagelist.getOrderBy()) && Validate.isNull(ordercol)){
			pagelist.setOrderBy(" order by " + pagelist.getKeyId());
		}
		else if(Validate.isNull(pagelist.getOrderBy())){
			pagelist.setOrderBy(" order by " + ordercol);
		}
		else{
			pagelist.setOrderBy(" order by " + pagelist.getOrderBy());
		}

		String sql = "select " + pagelist.getSqlText() + " from " + pagelist.getTableName() + " where 1 = 1 " + Validate.isNullToDefault(pagelist.getStrWhere(),"") + pagelist.getOrderBy();
		//导出方法
		String errmsg = ExpExcel(sql,realfile,pd.getString("xlsname"),mlist);
		if(Validate.isNull(errmsg)){
			return "{\"success\":true,\"url\":\"excel\\\\"+file+".xls\"}";
		}
		else{
			return "{\"success\":false,\"msg\":\"" + errmsg + "\"}";
		}
	}
	
	/**
	 * 拼接通用导出的字段信息
	 * @param columns
	 * @return
	 */
	public static List<M_largedata> joinExpCol(String columns) {
		List<M_largedata> mlist = new ArrayList<M_largedata>();
		M_largedata m = new M_largedata();
		Gson gson =new Gson();
		ArrayList list = gson.fromJson(columns,ArrayList.class);
		for (int i = 0,len=list.size();i < len; i++) {
			Map map = (Map) list.get(i);
			String expData = StringUtils.StringFilter(map.get("expData")+"");
			String expVal = StringUtils.StringFilter(map.get("expVal")+"");
			String expType = StringUtils.StringFilter(map.get("expType")+"");
			m = new M_largedata();
			if(Validate.noNull(expType)){
				m.setColtype("Number");
				if("dbl".equals(expType)){
					m.setColstyle("double");
				}
			}
			m.setName(expData);
			m.setShowname(expVal);
			mlist.add(m);
			m = null;
		}
		return mlist;
	}
	
	/**
	 * 弹窗信息列表
	 * @param pd
	 * @param pagelist
	 * @param flag
	 * @return
	 */
	public PageList findWindowList(PageData pd,PageList pagelist,String flag){
		return pageDao.findWindowList(pd,pagelist,flag);
	}
	/**
	 * 列表信息导出（仅适合于一行表头）
	 * @param sql 
	 * @param file 
	 * @param filedisplay 
	 * @param mlist 
	 * @return
	 */
	public String ExpExcel(String sql,String file,String filedisplay,List<M_largedata> mlist){
		try {
			pageDao.ExpExcel(sql,file,filedisplay,mlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	/**
	 * 列表信息导出（适合于多行表头）
	 * @param sql 
	 * @param file 
	 * @param filedisplay 
	 * @param mlist 
	 * @return
	 */
	public String ExpExcel(String sql,String file,String filedisplay,List<M_largedata> mlist, List<List<M_largedata>> tlist){
		try {
			pageDao.ExpExcel(sql,file,filedisplay,mlist,tlist);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return "";
	}
	
	/**
	 * 根据已经获取到的数据导出Excel（仅适合于一行表头的导出）
	 * @param list 存放数据的集合
	 * @param file 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public void ExpExcel(List list, String file, String filename,List<M_largedata> mlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException {
		try {
			pageDao.ExpExcel(list, file, filename, mlist);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 根据已经获取到的数据导出Excel（仅适合于一行表头的导出）(工资发放查询导出使用的方法)
	 * @param list 存放数据的集合
	 * @param file 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public void ExpExcelforGZ(List list, String file, String filename,List<M_largedata> mlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException {
		try {
			pageDao.ExpExcelforGZ(list, file, filename, mlist);
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
//	/**
//	 * 列表信息导出（含合并单元格，只适用合并两行的）
//	 * @param sql
//	 * @param file
//	 * @param filedisplay
//	 * @param mlist 第一行标题
//	 * @param mlist1 第二行标题
//	 * @param mlist2 列表数据
//	 * @return
//	 */
//	public String ExpExcel(String sql,String file,String filedisplay,List<M_largedata> mlist,List<M_largedata> mlist1,List<M_largedata> mlist2){
//		try {
//			pageDao.ExpExcel(sql,file,filedisplay,mlist,mlist1,mlist2);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return "";
//	}
	
	/**
	 * 列表信息导出txt
	 * @param sql 
	 * @param file 
	 * @param filedisplay 
	 * @param mlist 
	 * @return
	 */
	public String expTxt(String sql,String file,List<M_largedata> mlist,HttpServletRequest request){
		return expTxt(sql, file, mlist, "", request);
	}
	public String expTxt(String sql,String file,List<M_largedata> mlist,String flag,HttpServletRequest request){
		PrintWriter writer = null;
		try
		{
			FileUtil.createDir(file);
			String filename = UuidUtil.get32UUID() + ".txt";
			String fileurl = file + filename;
			List<String> errlist = new ArrayList<String>();
			if(pageDao.expTxt(sql, fileurl, mlist, errlist, flag, request)){
				return "{\"success\":true,\"url\":\"" + filename + "\"}";
			}
			else{
				if(errlist.size() == 0){
					return "{\"success\":false,\"msg\":\"未知的原因导致导出txt时失败！\"}";
				}
				else{
					filename = UuidUtil.get32UUID() + ".txt";
					String errorurl = file + filename;
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(errorurl),"GBK"));
					for(int i = 0; i < errlist.size(); i++){
						writer.println(errlist.get(i));
					}
					return "{\"success\":false,\"url\":\"" + filename + "\"}";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "{\"success\":false,\"msg\":\"导出txt文件失败！\"}";
		}
		finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	/**
	 * 以逗号隔开
	 */
	public String expTxtByEnd(String sql,String file,List<M_largedata> mlist,String flag,String endStr,HttpServletRequest request){
		PrintWriter writer = null;
		try
		{
			FileUtil.createDir(file);
			String filename = UuidUtil.get32UUID() + ".txt";
			String fileurl = file + filename;
			List<String> errlist = new ArrayList<String>();
			if(pageDao.expTxtByEnd(sql, fileurl, mlist, errlist, flag, endStr, request)){
				return "{\"success\":true,\"url\":\"" + filename + "\"}";
			}
			else{
				if(errlist.size() == 0){
					return "{\"success\":false,\"msg\":\"未知的原因导致导出txt时失败！\"}";
				}
				else{
					filename = UuidUtil.get32UUID() + ".txt";
					String errorurl = file + filename;
					writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(errorurl),"GBK"));
					for(int i = 0; i < errlist.size(); i++){
						writer.println(errlist.get(i));
					}
					return "{\"success\":false,\"url\":\"" + filename + "\"}";
				}
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "{\"success\":false,\"msg\":\"导出txt文件失败！\"}";
		}
		finally{
			if(writer != null){
				writer.close();
			}
		}
	}
	
	/**
	 * 获取人员权限的快捷语句
	 * @param rybh 人员编号
	 * @param colName 列名
	 * @param flag D  单位权限sql   F  资产分类权限sql
	 * @return
	 */
	public String getQxsql(String rybh,String colName,String flag){
		return glqxbService.getQxsql(rybh,colName,flag);
	}
	/**
	 * 获取人员单位权限where条件
	 * @param rybh 人员编号
	 * @param colName 列名
	 * @param dwbh 单位编号
	 * @param flag 是否包含下级
	 * @return
	 */
	public String getDwqxWhereSql(String rybh,String colName,String dwbh,boolean flag){
		return deptService.getDwqxWhereSql(rybh, colName, dwbh, flag);
	}
	
	public String getDwqxWhereSql1(String rybh,String colName,String dwbh,boolean flag){
		return deptService.getDwqxWhereSql1(rybh, colName, dwbh, flag);
	}
	
	
	/**
	 * 获取列表信息
	 * @param pagelist
	 * @return
	 */
	public List getPageList(PageList pagelist){
		String hxsql = this.getAllPageSql(pagelist);
		
		List list = pageDao.getPageList(getPageSql(pagelist));
		
		if(Validate.noNull(pagelist.getGroupSql())){
			pagelist.setGroupList(pageDao.getPageList(pagelist.getGroupSql()));
		}
		
		pagelist.setRecordCnt(pageDao.getPageSingleValue("select count(*) from (" + hxsql + ")"));
		pagelist.setPage_length(pageDao.getPageSingleValue("select ceil(count(*)/" + pagelist.getPage_length() + ") from (" + hxsql + ")"));
		
		return list;
	}
	/**
	 * 获取list
	 * @param sql
	 * @return
	 */
	public List getList(String sql){
		return pageDao.getPageList(sql);
	}
	/**
	 * 获取一行一列数据
	 * @param sql
	 * @return
	 */
	public String getSingleValue(String sql){
		return pageDao.getPageSingleValue(sql);
	}
	/**
	 * 获取一行一列数据
	 * @param sql
	 * @return
	 */
	public String getSingleValue(String sql,Object[] par){
		return pageDao.getPageSingleValue(sql,par);
	}
	
	/**
	 * 获取总计信息
	 * @param pagelist
	 * @return
	 */
	public List getPageZjList(PageList pagelist){
		String sql = "select " + pagelist.getHj1() + " from (" + getAllPageSql(pagelist) + ") z where 1 = 1 " + (Validate.noNull(pagelist.getHjWhere()) ? pagelist.getHjWhere() : "");
		return pageDao.getPageList(sql);
	}
	/**
	 * 获取当前页合计信息
	 * @param pagelist
	 * @return
	 */
	public List getPageHjList(PageList pagelist){
		String sql = "select " + pagelist.getHj1() + " from (" + getPageSql(pagelist) + ") z where 1 = 1 " + (Validate.noNull(pagelist.getHjWhere()) ? pagelist.getHjWhere() : "");
		return pageDao.getPageList(sql);
	}
	
	/**
	 * 获取符合条件的所有信息的sql语句
	 * @param pagelist
	 * @return
	 */
	private String getAllPageSql(PageList pagelist){
		return "select " + pagelist.getSqlText()+" from "+pagelist.getTableName() + " where 1 = 1 " + pagelist.getStrWhere() + " " + pagelist.getOrderBy();
	}
	
	/**
	 * 获取当前页信息的sql语句
	 * @param pagelist
	 * @return
	 */
	private String getPageSql(PageList pagelist){
		StringBuffer sql = new StringBuffer();
		sql.append("select z.*,rn \"_XH\" from (");
			sql.append("select z.*,rownum rn from (");
				sql.append(getAllPageSql(pagelist));
			sql.append(") z where rownum <= " + pagelist.getPage_record());
		sql.append(") z where rn > " + pagelist.getCurPage());
		return sql.toString();
	}
}
