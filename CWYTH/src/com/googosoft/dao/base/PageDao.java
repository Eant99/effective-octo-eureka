package com.googosoft.dao.base;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.management.RuntimeErrorException;
import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.googosoft.commons.CommonUtil;
import com.googosoft.pojo.PageList;
import com.googosoft.pojo.exp.M_largedata;
import com.googosoft.util.PageData;
import com.googosoft.util.PageListUtil;
import com.googosoft.util.UserAgentUtils;
import com.googosoft.util.Validate;

@Repository("pageDao")
public class PageDao extends BaseDao{
	private Logger logger = Logger.getLogger(PageDao.class);

	/**
	 * 普通列表数据
	 * @param pd
	 * @param cols
	 * @return
	 * @throws SQLException 
	 */
	public PageList findPageList(PageData pd,PageList pagelist){
		PageList pl = PageListUtil.setPgxxList(pd,pagelist);//列表页基本信息封装
		try {
		    db.getPageList(pl);
		} catch (SQLException e) {
			logger.debug("分页工具类异常");
			e.printStackTrace();
		}
		return pl;
	}
	public PageList findPageListA(PageData pd,PageList pagelist){
		PageList pl = PageListUtil.setPgxxListA(pd,pagelist);//列表页基本信息封装
		try {
			db.getPageList(pl);
		} catch (SQLException e) {
			logger.debug("分页工具类异常");
			e.printStackTrace();
		}
		return pl;
	}
	
	public PageList findPageList1(PageData pd,PageList pagelist){
		PageList pl = PageListUtil.setPgxxList1(pd,pagelist);//列表页基本信息封装
		try {
		    db.getPageList(pl);
		} catch (SQLException e) {
			logger.debug("分页工具类异常");
			e.printStackTrace();
		}
		return pl;
	}
	
	/**
	 * 带分组的特殊列表数据
	 * @param pd
	 * @param cols
	 * @return
	 * @throws SQLException 
	 */
	public PageList findPageList(PageData pd,PageList pagelist,String groupSql){
		PageList pl = PageListUtil.setPgxxList(pd,pagelist);//列表页基本信息封装
		try {
			db.getPageList(pl);
			pl.setGroupList(db.queryForList(groupSql));
		} catch (SQLException e) {
			logger.debug("分页工具类异常");
			e.printStackTrace();
		}
		return pl;
	}
	
	/**
	 * 弹窗列表数据
	 * @param pd
	 * @param pagelist
	 * @return
	 */
	public PageList findWindowList(PageData pd, PageList pagelist,String flag) {
		PageList pl = PageListUtil.setPgxxList(pd,pagelist,flag);//列表页基本信息封装
		try {
		    db.getPageList(pl);
		} catch (SQLException e) {
			logger.debug("分页工具类异常");
			e.printStackTrace();
		}
		return pl;
	}
	
	/**
	 * 根据sql语句导出Excel（仅适合于一行表头的导出）
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param file 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	public void ExpExcel(String sql, String file, String filename,List<M_largedata> mlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException {
		ExpExcel(sql,file, filename, mlist, null);
	}
	
//	/**
//	 * 列表信息导出（含合并单元格，只适用合并两行的，不建议使用，如需多行表头，请ExpExcel(String sql, String file, String filename,List<M_largedata> mlist,List<List<M_largedata>> tlist)方法）
//	 * @param sql 获取数据的sql语句（不能包括rn字段）
//	 * @param file 导出文件的路径
//	 * @param filename sheet页的名称
//	 * @param mlist 第一行标题
//	 * @param mlist1 第二行标题
//	 * @param mlist2 列表数据
//	 * @throws UnsupportedEncodingException
//	 * @throws FileNotFoundException
//	 * @throws SQLException
//	 */
//	public void ExpExcel(String sql, String file, String filename,List<M_largedata> mlist,List<M_largedata> mlist1,List<M_largedata> mlist2) throws UnsupportedEncodingException, FileNotFoundException, SQLException {
//		List<List<M_largedata>> tlist = new ArrayList();
//		tlist.add(mlist);
//		tlist.add(mlist1);
//		ExpExcel(sql,file, filename, mlist2, tlist);
//	}
	public void ExpExcel(String sql, String file, String filename,List<M_largedata> mlist,List<M_largedata> mlist1,List<M_largedata> mlist2) throws UnsupportedEncodingException, FileNotFoundException, SQLException {
		int maxRowNumLast = db.queryForObject("select count(*) from ("+sql+")",Integer.class);
		int expCnt = Integer.parseInt(Validate.isNullToDefault("55000","65530") + "");
		largeoutput(expCnt, maxRowNumLast, sql,file, filename, mlist, mlist1, mlist2);
	}
 /**
	 *  列表信息导出（含合并单元格，只适用合并两行的）
	 * @param maxRowNum
	 * @param maxRowNumLast
	 * @param sql
	 * @param fileurl
	 * @param filename
	 * @param mlist 第一行标题
	 * @param mlist1  第二行标题
	 * @param mlist2 列表数据
	 * @return
	 * @throws UnsupportedEncodingException
	 * @throws FileNotFoundException
	 * @throws SQLException
	 */
	private boolean largeoutput(int maxRowNum, int maxRowNumLast, String sql, String fileurl, String filename, List<M_largedata> mlist, List<M_largedata> mlist1, List<M_largedata> mlist2) throws UnsupportedEncodingException, FileNotFoundException, SQLException{		
		if(maxRowNum == 0){
			logger.error("每页最大行数为空");
			return false;
		}else{
			long startTimne = System.currentTimeMillis();//获取当前时间
			StringBuffer headstr = new StringBuffer();
			headstr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
			headstr.append("<?mso-application progid=\"Excel.Sheet\"?>\r\n");
			headstr.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\r\n");
			headstr.append(" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\r\n");
			headstr.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\r\n");
			headstr.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\r\n");
			headstr.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">\r\n");
			headstr.append(" <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">\r\n");
			//headstr.append("  <Created>1996-12-17T01:32:42Z</Created>\r\n");
			//headstr.append("  <LastSaved>2013-08-02T09:21:24Z</LastSaved>\r\n");
			headstr.append("  <Version>12.00</Version>\r\n");
			headstr.append(" </DocumentProperties>\r\n");
			//headstr.append(" <OfficeDocumentSettings xmlns=\"urn:schemas-microsoft-com:office:office\">\r\n");
			//headstr.append("  <RemovePersonalInformation/>\r\n");
			//headstr.append(" </OfficeDocumentSettings>\r\n");
			headstr.append(" <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\r\n");
			headstr.append("  <WindowHeight>4530</WindowHeight>\r\n");
			headstr.append("  <WindowWidth>8505</WindowWidth>\r\n");
			headstr.append("  <WindowTopX>480</WindowTopX>\r\n");
			headstr.append("  <WindowTopY>120</WindowTopY>\r\n");
			//headstr.append("  <AcceptLabelsInFormulas/>\r\n");
			headstr.append("  <ProtectStructure>False</ProtectStructure>\r\n");
			headstr.append("  <ProtectWindows>False</ProtectWindows>\r\n");
			headstr.append(" </ExcelWorkbook>\r\n");
			headstr.append(" <Styles>\r\n");
			
			headstr.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">\r\n");
			headstr.append("   <Alignment ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>\r\n");
			headstr.append("   <Interior/>\r\n");
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"title\">\r\n");
			headstr.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"14\"/>\r\n");
			///headstr.append("   <Interior ss:Color=\"#969696\" ss:Pattern=\"Solid\"/>\r\n");//背景色
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"center\">\r\n");
			headstr.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>\r\n");
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"double\">\r\n");
			headstr.append("   <Alignment ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"12\"/>\r\n");
			headstr.append("   <Interior/>\r\n");
			headstr.append("   <NumberFormat ss:Format=\"0.00\"/>\r\n");//   ss:Format=\"0.00\"
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append(" </Styles>\r\n");
			File filedir = new File(fileurl.substring(0,fileurl.lastIndexOf('\\')));
			if(!filedir.isDirectory()){
				filedir.mkdirs();
			}
			File file = new File(fileurl);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")); 
			writer.print(headstr.toString());
			writer.flush();
			headstr = null;
			StringBuffer bodystr;
			M_largedata m;
			int sheets = (int)Math.ceil((float)maxRowNumLast/(float)maxRowNum);//这里必须转成float类型才能出正确结果（否则不会在除不尽时加1）
			if(sheets == 0){
				bodystr = new StringBuffer();
				bodystr.append(" <Worksheet ss:Name=\"" + filename + "\">\r\n");
				bodystr.append("  <Table ss:ExpandedColumnCount=\"" + mlist.size() + "\" ss:ExpandedRowCount=\"1\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"14.25\">\r\n");
				
				//拼接表头（第一行表头用mlist）
				bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
				for(int c = 0; c < mlist.size(); c++){
					m = (M_largedata)mlist.get(c);
					bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\" ss:MergeDown=\"" + Validate.isNullToDefault(m.getErow(),"") + "\" ss:MergeAcross=\"" + Validate.isNullToDefault(m.getEcol(),"") + "\"><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
					m = null;
				}
				bodystr.append("   </Row>\r\n");
				//第二行表头用mlist1
				bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
				for(int c = 0; c < mlist1.size(); c++){
					m = (M_largedata)mlist1.get(c);
					bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\" ss:Index=\"" + m.getScol() + "\"><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
					m = null;
				}
				bodystr.append("   </Row>\r\n");

				bodystr.append("  </Table>\r\n");
				bodystr.append(" </Worksheet>\r\n");
				
				writer.print(bodystr.toString());
				writer.flush();
				bodystr = null;
			} else {
				Connection con = db.getDataSource().getConnection();
				PreparedStatement st = null;
				ResultSet resultset = null;
				for(int i = 0; i < sheets; i++){
					int s = i * maxRowNum;//每个sheet页开始的条数
					int e = (i + 1) * maxRowNum;//每个sheet页结束的条数
					if(maxRowNumLast < e){
						e = maxRowNumLast;
					}
					String name = filename + (sheets == 1 ? "" : (i + 1) + "");
					try{
						String dbsql = "select * from (select z.*,rownum rn from (" + sql + ") z where rownum <= " + e + ") where rn > " + s;
						st = con.prepareStatement(dbsql);
						resultset = st.executeQuery();
						bodystr = new StringBuffer();
						bodystr.append(" <Worksheet ss:Name=\"" + name + "\">\r\n");
						bodystr.append("  <Table ss:ExpandedColumnCount=\"" + mlist.size() + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"14.25\">\r\n");
						
						//拼接表头（第一行表头用mlist）
						bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
						for(int c = 0; c < mlist.size(); c++){
							m = (M_largedata)mlist.get(c);
							bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\" ss:MergeDown=\"" + Validate.isNullToDefault(m.getErow(),"") + "\" ss:MergeAcross=\"" + Validate.isNullToDefault(m.getEcol(),"") + "\"><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
							m = null;
						}
						bodystr.append("   </Row>\r\n");
						//第二行表头用mlist1
						bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
						for(int c = 0; c < mlist1.size(); c++){
							m = (M_largedata)mlist1.get(c);
							bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\" ss:Index=\"" + m.getScol() + "\"><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
							m = null;
						}
						bodystr.append("   </Row>\r\n");
						writer.print(bodystr.toString());
						writer.flush();
						bodystr = null;
		
						while(resultset.next()){
							bodystr = new StringBuffer();
							bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
							boolean b = false;
							//填充数据用的mlist2
							for(int c = 0; c < mlist2.size(); c++){
								m = (M_largedata)mlist2.get(c);
	
								String data = "";
								try{
									data = Validate.isNullToDefault(resultset.getString(m.getName().toUpperCase()),"") + "";
								}
								catch(Exception ex){
									throw new SQLException(m.getName().toLowerCase()+"列名无效");
								}
								
								if(Validate.isNull(data)){
									b = true;
								}else{
									String style = "";
									if(!"Default".equals(m.getColstyle())){
										style = " ss:StyleID=\"" + m.getColstyle() + "\" ";
									}
									String ind = "";
									if(b){
										b = false;
										ind = " ss:Index=\"" + (c + 1) + "\" ";
									}
									bodystr.append("    <Cell " + ind + style + "><Data ss:Type=\"" + m.getColtype() + "\">");
									
									if("String".equals(m.getColtype())){
										bodystr.append("<![CDATA[" + data + "]]>");
									}
									else{
										bodystr.append(data.trim());
									}
									bodystr.append("</Data></Cell>\r\n");
								}
								m = null;
							}
							bodystr.append("   </Row>\r\n");
							writer.print(bodystr.toString());
							writer.flush();
							bodystr = null;
						}
					}catch(Error ex){
						logger.error("获取数据失败，原因是：" + ex.getMessage());
						throw new RuntimeErrorException(ex);
					}finally{
			      		if(Validate.noNull(st)){
			      			st.close();
			      		}
			      		if(Validate.noNull(resultset)){
			      			resultset.close();
			      		}
			      		if(Validate.noNull(con)){
			      			con.close();
			      		}
					}
					writer.print("  </Table>\r\n");
					writer.print(" </Worksheet>\r\n");
					writer.flush();
					Runtime.getRuntime().gc();
					logger.info("正在生成excel文件的"+name);
				}
			}
			mlist = null;
			//写入excel文件尾部
			writer.print("</Workbook>");
			writer.flush();
			writer.close();
			logger.info("生成excel文件完成");
			long endTime = System.currentTimeMillis();
			logger.info("用时="+((endTime-startTimne)/1000)+"秒");
			return true;
		}
	}
	/**
	 * 根据sql语句导出Excel（适合于多行表头）
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param file 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @param tlist 表头的实体类集合的集合，单行表头是为null
	 */
	public void ExpExcel(String sql, String file, String filename,List<M_largedata> mlist,List<List<M_largedata>> tlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException{
		int maxRowNumLast = db.queryForObject("select count(*) from ("+sql+")",Integer.class);
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_xls"),"65530") + "");
		largeoutput(expCnt, maxRowNumLast,sql, null, file, filename, mlist, tlist);
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
		ExpExcel(list, file, filename, mlist, null);
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
		ExpExcelforGZ(list, file, filename, mlist, null);
	}
	
	/**
	 * 根据已经获取到的数据导出Excel（适合于多行表头）
	 * @param list 存放数据的集合
	 * @param file 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @param tlist 表头的实体类集合的集合，单行表头是为null
	 */
	public void ExpExcel(List list, String file, String filename,List<M_largedata> mlist,List<List<M_largedata>> tlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException{
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_xls"),"65530") + "");
		largeoutput(expCnt, list.size(), "", list, file, filename, mlist, tlist);
	}
	
	/**
	 * 根据已经获取到的数据导出Excel（适合于多行表头）(工资发放查询导出使用的方法)
	 * @param list 存放数据的集合
	 * @param file 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @param tlist 表头的实体类集合的集合，单行表头是为null
	 */
	public void ExpExcelforGZ(List list, String file, String filename,List<M_largedata> mlist,List<List<M_largedata>> tlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException{
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_xls"),"65530") + "");
		largeoutputforGZ(expCnt, list.size(), "", list, file, filename, mlist, tlist);
	}
	
	/**
	 * 导出Excel的核心方法
	 * @param maxRowNum 每个sheet页的最大行数（注意：excel2003每个sheet页最大是65535行，所以这个数最好不要太大）
	 * @param maxRowNumLast 获取到的所有数据总行数
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param list 存放数据的集合（注意，这种不会按照sql语句执行）
	 * @param fileurl 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @param tlist 表头的实体类集合的集合，单行表头是为null
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 * @throws DBException
	 */
	private boolean largeoutput(int maxRowNum, int maxRowNumLast, String sql, List list, String fileurl, String filename, List<M_largedata> mlist, List<List<M_largedata>> tlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException{		
		if(maxRowNum == 0){
			logger.error("每页最大行数为空");
			return false;
		}else{
			long startTimne = System.currentTimeMillis();//获取当前时间
			StringBuffer headstr = new StringBuffer();
			headstr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
			headstr.append("<?mso-application progid=\"Excel.Sheet\"?>\r\n");
			headstr.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\r\n");
			headstr.append(" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\r\n");
			headstr.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\r\n");
			headstr.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\r\n");
			headstr.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">\r\n");
			headstr.append(" <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">\r\n");
			//headstr.append("  <Created>1996-12-17T01:32:42Z</Created>\r\n");
			//headstr.append("  <LastSaved>2013-08-02T09:21:24Z</LastSaved>\r\n");
			headstr.append("  <Version>12.00</Version>\r\n");
			headstr.append(" </DocumentProperties>\r\n");
			//headstr.append(" <OfficeDocumentSettings xmlns=\"urn:schemas-microsoft-com:office:office\">\r\n");
			//headstr.append("  <RemovePersonalInformation/>\r\n");
			//headstr.append(" </OfficeDocumentSettings>\r\n");
			headstr.append(" <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\r\n");
			headstr.append("  <WindowHeight>4530</WindowHeight>\r\n");
			headstr.append("  <WindowWidth>8505</WindowWidth>\r\n");
			headstr.append("  <WindowTopX>480</WindowTopX>\r\n");
			headstr.append("  <WindowTopY>120</WindowTopY>\r\n");
			//headstr.append("  <AcceptLabelsInFormulas/>\r\n");
			headstr.append("  <ProtectStructure>False</ProtectStructure>\r\n");
			headstr.append("  <ProtectWindows>False</ProtectWindows>\r\n");
			headstr.append(" </ExcelWorkbook>\r\n");
			headstr.append(" <Styles>\r\n");
			
			headstr.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">\r\n");
			headstr.append("   <Alignment ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");//ss:Size：2017-06-23jingxuyan修改，由12号改为10号
			headstr.append("   <Interior/>\r\n");
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"title\">\r\n");
			headstr.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");//ss:Size：2017-06-23jingxuyan修改，由14号改为10号
			///headstr.append("   <Interior ss:Color=\"#969696\" ss:Pattern=\"Solid\"/>\r\n");//背景色
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"center\">\r\n");
			headstr.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"double\">\r\n");
			headstr.append("   <Alignment ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");
			headstr.append("   <Interior/>\r\n");
			headstr.append("   <NumberFormat ss:Format=\"#,##0.00\"/>\r\n");//   ss:Format=\"0.00\"
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append(" </Styles>\r\n");
			File filedir = new File(fileurl.substring(0,fileurl.lastIndexOf('\\')));
			if(!filedir.isDirectory()){
				filedir.mkdirs();
			}
			File file = new File(fileurl);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")); 
			writer.print(headstr.toString());
			writer.flush();
			headstr = null;
			StringBuffer bodystr;
			M_largedata m;
			int sheets = (int)Math.ceil((float)maxRowNumLast/(float)maxRowNum);//这里必须转成float类型才能出正确结果（否则不会在除不尽时加1）
			if(sheets == 0){
				bodystr = new StringBuffer();
				bodystr.append(" <Worksheet ss:Name=\"" + filename + "\">\r\n");
				bodystr.append("  <Table ss:ExpandedColumnCount=\"" + mlist.size() + "\" ss:ExpandedRowCount=\"" + (Validate.isNull(tlist) ? "1" : tlist.size()) + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"14.25\">\r\n");

				ExcelTitle(bodystr,mlist,tlist);

				bodystr.append("  </Table>\r\n");
				bodystr.append(" </Worksheet>\r\n");
				
				writer.print(bodystr.toString());
				writer.flush();
				bodystr = null;
			} else {
				Connection con = null;
				if(Validate.noNull(sql)){
					con = db.getDataSource().getConnection();
				}
				PreparedStatement st = null;
				ResultSet resultset = null;
				Map listmap;
				for(int i = 0; i < sheets; i++){
					int s = i * maxRowNum;//每个sheet页开始的条数
					int e = (i + 1) * maxRowNum;//每个sheet页结束的条数
					if(maxRowNumLast < e){
						e = maxRowNumLast;
					}
					String name = filename + (sheets == 1 ? "" : (i + 1) + "");
					
					bodystr = new StringBuffer();
					bodystr.append(" <Worksheet ss:Name=\"" + name + "\">\r\n");
					bodystr.append("  <Table ss:ExpandedColumnCount=\"" + mlist.size() + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"14.25\">\r\n");
					
					ExcelTitle(bodystr,mlist,tlist);
					
					writer.print(bodystr.toString());
					writer.flush();
					bodystr = null;
					
					if(Validate.isNull(list)){
						try{
							String dbsql = "select * from (select z.*,rownum rn from (" + sql + ") z where rownum <= " + e + ") where rn > " + s;
							st = con.prepareStatement(dbsql);
							resultset = st.executeQuery();
			
							while(resultset.next()){
								bodystr = new StringBuffer();
								bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
								boolean b = false;
								for(int c = 0; c < mlist.size(); c++){
									m = (M_largedata)mlist.get(c);
		
									String data = "";
									try{
										data = Validate.isNullToDefault(resultset.getString(((String) Validate.isNullToDefault(m.getName(),"")).toUpperCase()),"") + "";
									}
									catch(Exception ex){
										throw new SQLException(m.getName().toLowerCase()+"列名无效");
									}
									
									ExcelCell(bodystr,data,m,c,b);
								}
								bodystr.append("   </Row>\r\n");
								writer.print(bodystr.toString());
								writer.flush();
								bodystr = null;
							}
						}catch(Error ex){
							logger.error("获取数据失败，原因是：" + ex.getMessage());
							throw new RuntimeErrorException(ex);
						}finally{
				      		if(Validate.noNull(st)){
				      			st.close();
				      		}
				      		if(Validate.noNull(resultset)){
				      			resultset.close();
				      		}
				      		if(Validate.noNull(con)){
				      			con.close();
				      		}
						}
					}
					else{
						for(int l = s; l < e; l++){
							listmap = (Map)list.get(l);
							
							bodystr = new StringBuffer();
							bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
							boolean b = false;
							for(int c = 0; c < mlist.size(); c++){
								m = (M_largedata)mlist.get(c);
	
								String data = "";
								try{
									data = Validate.isNullToDefault(listmap.get(((String) Validate.isNullToDefault(m.getName(),"")).toUpperCase()),"") + "";
								}
								catch(Exception ex){
									throw new SQLException(m.getName().toLowerCase()+"列名无效");
								}
								
								ExcelCell(bodystr,data,m,c,b);
							}
							bodystr.append("   </Row>\r\n");
							writer.print(bodystr.toString());
							writer.flush();
							bodystr = null;
						}
					}
					writer.print("  </Table>\r\n");
					writer.print(" </Worksheet>\r\n");
					writer.flush();
					Runtime.getRuntime().gc();
					logger.info("正在生成excel文件的"+name);
				}
			}
			mlist = null;
			//写入excel文件尾部
			writer.print("</Workbook>");
			writer.flush();
			writer.close();
			logger.info("生成excel文件完成");
			long endTime = System.currentTimeMillis();
			logger.info("用时="+((endTime-startTimne)/1000)+"秒");
			return true;
		}
	}
	/**
	 * 导出Excel的核心方法(工资发放查询导出使用的方法)
	 * @param maxRowNum 每个sheet页的最大行数（注意：excel2003每个sheet页最大是65535行，所以这个数最好不要太大）
	 * @param maxRowNumLast 获取到的所有数据总行数
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param list 存放数据的集合（注意，这种不会按照sql语句执行）
	 * @param fileurl 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成实体类的集合
	 * @param tlist 表头的实体类集合的集合，单行表头是为null
	 * @throws UnsupportedEncodingException 
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 * @throws DBException
	 */
	private boolean largeoutputforGZ(int maxRowNum, int maxRowNumLast, String sql, List list, String fileurl, String filename, List<M_largedata> mlist, List<List<M_largedata>> tlist) throws UnsupportedEncodingException, FileNotFoundException, SQLException{		
		if(maxRowNum == 0){
			logger.error("每页最大行数为空");
			return false;
		}else{
			long startTimne = System.currentTimeMillis();//获取当前时间
			StringBuffer headstr = new StringBuffer();
			headstr.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>\r\n");
			headstr.append("<?mso-application progid=\"Excel.Sheet\"?>\r\n");
			headstr.append("<Workbook xmlns=\"urn:schemas-microsoft-com:office:spreadsheet\"\r\n");
			headstr.append(" xmlns:o=\"urn:schemas-microsoft-com:office:office\"\r\n");
			headstr.append(" xmlns:x=\"urn:schemas-microsoft-com:office:excel\"\r\n");
			headstr.append(" xmlns:ss=\"urn:schemas-microsoft-com:office:spreadsheet\"\r\n");
			headstr.append(" xmlns:html=\"http://www.w3.org/TR/REC-html40\">\r\n");
			headstr.append(" <DocumentProperties xmlns=\"urn:schemas-microsoft-com:office:office\">\r\n");
			//headstr.append("  <Created>1996-12-17T01:32:42Z</Created>\r\n");
			//headstr.append("  <LastSaved>2013-08-02T09:21:24Z</LastSaved>\r\n");
			headstr.append("  <Version>12.00</Version>\r\n");
			headstr.append(" </DocumentProperties>\r\n");
			//headstr.append(" <OfficeDocumentSettings xmlns=\"urn:schemas-microsoft-com:office:office\">\r\n");
			//headstr.append("  <RemovePersonalInformation/>\r\n");
			//headstr.append(" </OfficeDocumentSettings>\r\n");
			headstr.append(" <ExcelWorkbook xmlns=\"urn:schemas-microsoft-com:office:excel\">\r\n");
			headstr.append("  <WindowHeight>4530</WindowHeight>\r\n");
			headstr.append("  <WindowWidth>8505</WindowWidth>\r\n");
			headstr.append("  <WindowTopX>480</WindowTopX>\r\n");
			headstr.append("  <WindowTopY>120</WindowTopY>\r\n");
			//headstr.append("  <AcceptLabelsInFormulas/>\r\n");
			headstr.append("  <ProtectStructure>False</ProtectStructure>\r\n");
			headstr.append("  <ProtectWindows>False</ProtectWindows>\r\n");
			headstr.append(" </ExcelWorkbook>\r\n");
			headstr.append(" <Styles>\r\n");
			
			headstr.append("  <Style ss:ID=\"Default\" ss:Name=\"Normal\">\r\n");
			headstr.append("   <Alignment ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");//ss:Size：2017-06-23jingxuyan修改，由12号改为10号
			headstr.append("   <Interior/>\r\n");
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"title\">\r\n");
			headstr.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");//ss:Size：2017-06-23jingxuyan修改，由14号改为10号
			///headstr.append("   <Interior ss:Color=\"#969696\" ss:Pattern=\"Solid\"/>\r\n");//背景色
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"center\">\r\n");
			headstr.append("   <Alignment ss:Horizontal=\"Center\" ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");
			headstr.append("   <NumberFormat/>\r\n");
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append("  <Style ss:ID=\"double\">\r\n");
			headstr.append("   <Alignment ss:Vertical=\"Center\"/>\r\n");
			headstr.append("   <Borders/>\r\n");
			headstr.append("   <Font ss:FontName=\"宋体\" x:CharSet=\"134\" ss:Size=\"10\"/>\r\n");
			headstr.append("   <Interior/>\r\n");
			headstr.append("   <NumberFormat ss:Format=\"#,##0.00\"/>\r\n");//   ss:Format=\"0.00\"
			headstr.append("   <Protection/>\r\n");
			headstr.append("  </Style>\r\n");
			
			headstr.append(" </Styles>\r\n");
			File filedir = new File(fileurl.substring(0,fileurl.lastIndexOf('\\')));
			if(!filedir.isDirectory()){
				filedir.mkdirs();
			}
			File file = new File(fileurl);
			if(!file.exists()){
				try {
					file.createNewFile();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(file),"UTF-8")); 
			writer.print(headstr.toString());
			writer.flush();
			headstr = null;
			StringBuffer bodystr;
			M_largedata m;
			int sheets = (int)Math.ceil((float)maxRowNumLast/(float)maxRowNum);//这里必须转成float类型才能出正确结果（否则不会在除不尽时加1）
			if(sheets == 0){
				bodystr = new StringBuffer();
				bodystr.append(" <Worksheet ss:Name=\"" + filename + "\">\r\n");
				bodystr.append("  <Table ss:ExpandedColumnCount=\"" + mlist.size() + "\" ss:ExpandedRowCount=\"" + (Validate.isNull(tlist) ? "1" : tlist.size()) + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"14.25\">\r\n");

				ExcelTitle(bodystr,mlist,tlist);

				bodystr.append("  </Table>\r\n");
				bodystr.append(" </Worksheet>\r\n");
				
				writer.print(bodystr.toString());
				writer.flush();
				bodystr = null;
			} else {
				Connection con = null;
				if(Validate.noNull(sql)){
					con = db.getDataSource().getConnection();
				}
				PreparedStatement st = null;
				ResultSet resultset = null;
				Map listmap;
				for(int i = 0; i < sheets; i++){
					int s = i * maxRowNum;//每个sheet页开始的条数
					int e = (i + 1) * maxRowNum;//每个sheet页结束的条数
					if(maxRowNumLast < e){
						e = maxRowNumLast;
					}
					String name = filename + (sheets == 1 ? "" : (i + 1) + "");
					
					bodystr = new StringBuffer();
					bodystr.append(" <Worksheet ss:Name=\"" + name + "\">\r\n");
					bodystr.append("  <Table ss:ExpandedColumnCount=\"" + mlist.size() + "\" x:FullColumns=\"1\" x:FullRows=\"1\" ss:DefaultColumnWidth=\"100\" ss:DefaultRowHeight=\"14.25\">\r\n");
					
					ExcelTitle(bodystr,mlist,tlist);
					
					writer.print(bodystr.toString());
					writer.flush();
					bodystr = null;
					
					if(Validate.isNull(list)){
						try{
							String dbsql = "select * from (select z.*,rownum rn from (" + sql + ") z where rownum <= " + e + ") where rn > " + s;
							st = con.prepareStatement(dbsql);
							resultset = st.executeQuery();
			
							while(resultset.next()){
								bodystr = new StringBuffer();
								bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
								boolean b = false;
								for(int c = 0; c < mlist.size(); c++){
									m = (M_largedata)mlist.get(c);
		
									String data = "";
									try{
										data = Validate.isNullToDefault(resultset.getString(((String) Validate.isNullToDefault(m.getName(),"")).toUpperCase()),"") + "";
									}
									catch(Exception ex){
										throw new SQLException(m.getName().toLowerCase()+"列名无效");
									}
									
									ExcelCell(bodystr,data,m,c,b);
								}
								bodystr.append("   </Row>\r\n");
								writer.print(bodystr.toString());
								writer.flush();
								bodystr = null;
							}
						}catch(Error ex){
							logger.error("获取数据失败，原因是：" + ex.getMessage());
							throw new RuntimeErrorException(ex);
						}finally{
				      		if(Validate.noNull(st)){
				      			st.close();
				      		}
				      		if(Validate.noNull(resultset)){
				      			resultset.close();
				      		}
				      		if(Validate.noNull(con)){
				      			con.close();
				      		}
						}
					}
					else{
						for(int l = s; l < e; l++){
							listmap = (Map)list.get(l);
							
							bodystr = new StringBuffer();
							if(l%2==1){
								bodystr.append("   <Row ss:StyleID=\"center\" ss:AutoFitHeight=\"0\">\r\n");
							}else{
								bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
							}
							boolean b = false;
							for(int c = 0; c < mlist.size(); c++){
								m = (M_largedata)mlist.get(c);
	
								String data = "";
								try{
									data = Validate.isNullToDefault(listmap.get(((String) Validate.isNullToDefault(m.getName(),"")).toUpperCase()),"") + "";
								}
								catch(Exception ex){
									throw new SQLException(m.getName().toLowerCase()+"列名无效");
								}
								
								ExcelCell(bodystr,data,m,c,b);
							}
							bodystr.append("   </Row>\r\n");
							writer.print(bodystr.toString());
							writer.flush();
							bodystr = null;
						}
					}
					writer.print("  </Table>\r\n");
					writer.print(" </Worksheet>\r\n");
					writer.flush();
					Runtime.getRuntime().gc();
					logger.info("正在生成excel文件的"+name);
				}
			}
			mlist = null;
			//写入excel文件尾部
			writer.print("</Workbook>");
			writer.flush();
			writer.close();
			logger.info("生成excel文件完成");
			long endTime = System.currentTimeMillis();
			logger.info("用时="+((endTime-startTimne)/1000)+"秒");
			return true;
		}
	}
	
	/**
	 * 拼接excel的表头
	 * @param bodystr  存放拼接信息的StringBuffer
	 * @param mlist    存放列格式的集合
	 * @param tlist    存放表头信息的集合
	 */
	private void ExcelTitle(StringBuffer bodystr,List<M_largedata> mlist,List<List<M_largedata>> tlist){
		//拼接表头
		List<M_largedata> lslist;
		M_largedata m;
		if(Validate.isNull(tlist) || tlist.size() < 2){//不需要合并表头
			if(Validate.isNull(tlist) || tlist.size() == 0){
				lslist = mlist;
			}
			else{
				lslist = (List<M_largedata>)tlist.get(0);
			}
			
			bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
			for(int c = 0; c < lslist.size(); c++){
				m = (M_largedata)lslist.get(c);
				bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\"");
				if(m.getSindex() > 0){
					bodystr.append(" ss:Index=\"" + m.getSindex() + "\"");
				}
				bodystr.append("><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
				m = null;
			}
			bodystr.append("   </Row>\r\n");
		}
		else{
			for(int c = 0; c < tlist.size(); c++){
				lslist = (List<M_largedata>)tlist.get(c);
				bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
				for(int ls = 0; ls < lslist.size(); ls++){
					m = (M_largedata)lslist.get(ls);
					if(m.getIsmerge()){
						bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\"");
						if(m.getErow() > 0){
							bodystr.append(" ss:MergeDown=\"" + Validate.isNullToDefault(m.getErow(),"") + "\""); 
						}
						if(m.getEcol() > 0){
							bodystr.append(" ss:MergeAcross=\"" + Validate.isNullToDefault(m.getEcol(),"") + "\""); 
						}
						if(m.getSindex() > 0){
							bodystr.append(" ss:Index=\"" + m.getSindex() + "\"");
						}
						bodystr.append("><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
					}
					else{
						bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\"");
						if(m.getSindex() > 0){
							bodystr.append(" ss:Index=\"" + m.getSindex() + "\"");
						}
						bodystr.append("><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
					}
					m = null;
				}
				bodystr.append("   </Row>\r\n");
			}
		}
	}
	
	/**
	 * 拼接Excel的单元格
	 * @param bodystr  存放拼接信息的StringBuffer
	 * @param data     存放该单元格需要显示的数据
	 * @param m        存放当前单元格属性的实体类
	 * @param c        存放当前单元格是第几个单元格，从0开始
	 * @param b        存放标记字段，该单元格如果没值，就不拼接内容，知道下一个有值的单元格再拼接
	 */
	private void ExcelCell(StringBuffer bodystr,String data,M_largedata m,int c,boolean b){
//		if(Validate.isNull(data)){
//			b = true;
//		} else{
			String style = "";
			if(!"Default".equals(m.getColstyle())){
				style = " ss:StyleID=\"" + m.getColstyle() + "\" ";
			}
			String ind = "";
			if(b){
				b = false;
				ind = " ss:Index=\"" + (c + 1) + "\" ";
			}
			bodystr.append("    <Cell " + ind + style + "><Data ss:Type=\"" + m.getColtype() + "\">");
			
			if("String".equals(m.getColtype())){
				bodystr.append("<![CDATA[" + data + "]]>");
			}
			else{
				bodystr.append(data.trim());
			}
			bodystr.append("</Data></Cell>\r\n");
//		}
		m = null;
	}
	
	
	/**
	 * 列表信息导出txt
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param file 导出文件的路径
	 * @param mlist txt的核心组成model的集合
	 * @param errlist 错误信息集合
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 */
	public boolean expTxt(String sql, String file, List<M_largedata> mlist, List errlist,String flag,HttpServletRequest request) throws FileNotFoundException, IOException {
		int maxRowNumLast = db.queryForObject("select count(*) from ("+sql+")",Integer.class);
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_txt"),"10000") + "");
		return txtOutPut(expCnt, maxRowNumLast, sql, file, mlist, errlist, flag, 2, "", request);
	}
	
	/**
	 * 列表信息导出txt（可以自定义分隔符）
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param file 导出文件的路径
	 * @param mlist excel的核心组成model的集合
	 * @param errlist 错误信息集合
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @param endstr 每个字段结尾的分隔符 默认是空
	 */
	public boolean expTxtByEnd(String sql, String file, List<M_largedata> mlist, List errlist,String flag,String endstr,HttpServletRequest request) throws FileNotFoundException, IOException {
		int maxRowNumLast = db.queryForObject("select count(*) from ("+sql+")",Integer.class);
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_txt"),"10000") + "");
		return txtOutPut(expCnt, maxRowNumLast, sql, file, mlist, errlist, flag, 2, endstr, request);
	}

	/**
	 * 导出txt的核心方法
	 * @param maxRowNum 每次获取的最大行数
	 * @param maxRowNumLast 获取到的所有数据总行数
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param fileurl 导出文件的路径
	 * @param filename 导出文件的名称
	 * @param mlist txt的核心组成model的集合
	 * @param errlist 错误信息集合
	 * @param flag 为空时：超长的字段把错误信息放入errlist  不为空时：超长的字段直接截取，不显示错误信息
	 * @param hzlx 字符类型  1：一个汉字代表一个字符  2：一个汉字代表两个字符  默认是2
	 * @param endstr 每个字段结尾的分隔符 默认是空
	 */
	public boolean txtOutPut(int maxRowNum, int maxRowNumLast, String sql, String fileurl, List<M_largedata> mlist, List errlist,String flag,int hzlx,String endstr,HttpServletRequest request) throws FileNotFoundException, IOException{		
		long startTimne = System.currentTimeMillis();//获取当前时间
		StringBuffer bodystr;
		int sheets = (int)Math.ceil((float)maxRowNumLast/(float)maxRowNum);//这里必须转成float类型才能出正确结果（否则不会在除不尽时加1）
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileurl),"GBK"));//这里必须是GBK，要不然无法上报
		String hhf = UserAgentUtils.getLineMark(request);//换行符各个操作系统下不一样
		Map map;
		M_largedata m;
		for(int i = 0; i < sheets; i++){//虽然txt没有大小的限制，但是获取到数据在转入list的时候，可能会内存溢出，所以还是分开获取数据比较好
			int s = i * maxRowNum;//每次获取数据开始的条数
			int e = (i + 1) * maxRowNum;//每次获取数据结束的条数
			if(maxRowNumLast < e){
				e = maxRowNumLast;
			}
			
			List list = new ArrayList();
			try{
				list = db.queryForList("select * from (select z.*,rownum rn from (" + sql + ") z where rownum <= " + e + ") where rn > " + s);
			}
			catch(Exception ex){
				logger.error("查询数据库失败，原因是：" + ex.getMessage());
				ex.printStackTrace();
			}
			for(int j = 0; j < list.size(); j++){
				map = (Map)list.get(j);
				bodystr = new StringBuffer();
				for(int c = 0; c < mlist.size(); c++){
					m = (M_largedata)mlist.get(c);
					String zjzd = "";
					if(Validate.noNull(m.getZjzd())){
						zjzd = Validate.isNullToDefault(map.get(m.getZjzd()),"") + "";
					}
					if(hzlx == 1){
						bodystr.append(CommonUtil.completeStrOne(map.get(m.getName()),m.getWs(),m.getColtype(),errlist,m.getZj(),zjzd,m.getShowname(), flag));
					}
					else{
						bodystr.append(CommonUtil.completeStrTwo(map.get(m.getName()),m.getWs(),m.getColtype(),errlist,m.getZj(),zjzd,m.getShowname(), flag));
					}
					if(Validate.noNull(endstr)){
						bodystr.append(endstr);
					}
					//bodystr.append("==");
				}
				
				if(mlist.size() > 0 && Validate.noNull(endstr)){
//					writer.println(bodystr.deleteCharAt(bodystr.length() - 1).toString());
					writer.print(bodystr.deleteCharAt(bodystr.length() - 1).toString() + hhf);
				}
				else{
//					writer.println(bodystr.toString());
					writer.print(bodystr.toString() + hhf);
				}
				bodystr = null;
			}
			if(errlist.size() > 0){
				return false;
			}
		}
		writer.close();
		
		logger.info("生成txt文件完成");
		long endTime = System.currentTimeMillis();
		logger.info("用时="+((endTime-startTimne)/1000)+"秒");
		return true;
	}
	/**
	 * 主要是获取列表list，当然只要把sql传过来，也能获取其他list
	 * @param sql
	 * @param pars
	 * @return
	 */
	public List getPageList(String sql){
		try{
			logger.debug("获取list数据：" + sql);
			return db.queryForList(sql);
		}
		catch(Exception e){
			e.printStackTrace();
			return new ArrayList();
		}
	}
	/**
	 * 主要是获取列表list，当然只要把sql和参数传过来，也能获取其他list
	 * @param sql
	 * @param pars
	 * @return
	 */
	public List getPageList(String sql,Object... pars){
		try{
			if(pars.length == 0){
				logger.debug("获取list数据：" + sql);
				return db.queryForList(sql);
			}
			else{
				return db.queryForList(sql,pars);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return new ArrayList();
		}
	}
	/**
	 * 主要是获取列表中的总条数和总页数，当然只要把sql传过来，也能获取其他值
	 * @param sql
	 * @param pars
	 * @return
	 */
	public String getPageSingleValue(String sql){
		try{
			return db.queryForSingleValue(sql);
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
	/**
	 * 主要是获取列表中的总条数和总页数，当然只要把sql和参数传过来，也能获取其他值
	 * @param sql
	 * @param pars
	 * @return
	 */
	public String getPageSingleValue(String sql,Object[] par){
		try{
			if(par.length == 0){
				return db.queryForSingleValue(sql);
			}
			else{
				return db.queryForSingleValue(sql,par);
			}
		}
		catch(Exception e){
			e.printStackTrace();
			return "";
		}
	}
}
