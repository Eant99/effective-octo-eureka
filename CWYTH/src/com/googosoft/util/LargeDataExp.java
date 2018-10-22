package com.googosoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.management.RuntimeErrorException;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.exp.M_largedata;

@Component("largeDataExp")
public class LargeDataExp {
	
	private static Logger logger = Logger.getLogger(LargeDataExp.class.getName());
	
	@Resource(name="jdbcTemplate")
	private DBHelper dao;
	
	/**
	 * 下载导出的excel
	 * @param request
	 * @param response
	 * @param application
	 * @param sql 获取数据的语句
	 * @param xlsname 文件的名称（也是sheet页的名称）
	 * @param mlist excel的核心组成model的集合
	 * @param maxRowNum 每页显示的行数（不包括标题行）
	 * @throws DBException
	 * @throws IOException
	 */
	public void explargedata(HttpServletRequest request,HttpServletResponse response,ServletContext application,String sql,String xlsname,List<M_largedata> mlist, int maxRowNum) throws IOException{
		int maxRowNumLast = Integer.parseInt(dao.queryForObject("select count(*) from ("+sql+")",String.class));
		String file = application.getRealPath("\\")+"\\" + System.currentTimeMillis() + ".xls"; 
		response.setContentType("application/x-download");
		String filedisplay = xlsname + ".xls";
		filedisplay = URLEncoder.encode(filedisplay,"UTF-8");
		response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);
		OutputStream outp = null;
		FileInputStream in = null;
		try{
			boolean success = largeoutput(maxRowNum, maxRowNumLast,sql,file,xlsname,mlist);
			if(success){
				outp = response.getOutputStream();
				in = new FileInputStream(file);
				byte[] b = new byte[1024];
				int i = 0;
				while((i = in.read(b)) > 0){
					outp.write(b, 0, i);
				}
				outp.flush();
			}else{
				throw new Exception("未知的原因导致导出excel时失败");
			}
		}catch(Exception e){
			logger.error("导出出错，原因是：" + e.getMessage());
			e.printStackTrace();
		}finally{
			if(in != null){
				in.close();
				in = null;
			}
			if(outp != null){
				outp.close();
				outp = null;
			}
			File dir = new File(file);
			if(dir.exists()){
				dir.delete();
			}
		}
	}

	/**
	 * 下载导出的excel（重载，省略了每页行数的设置）
	 * @param request
	 * @param response
	 * @param application
	 * @param sql 获取数据的语句
	 * @param xlsname 文件的名称（也是sheet页的名称）
	 * @param mlist excel的核心组成model的集合
	 * @throws DBException
	 * @throws IOException
	 */
	public void explargedata(HttpServletRequest request,HttpServletResponse response,ServletContext application,String sql,String xlsname,List<M_largedata> mlist)throws IOException{
		int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_xls"),"10000") + "");
		explargedata(request,response,application,sql,xlsname,mlist,expCnt);
	}
	
	/**
	 * 导出的核心方法
	 * @param maxRowNum 每个sheet页的最大行数（注意：excel2003每个sheet页最大是65535行，我电脑是4G的，最大可设置到55000，所以这个数最好不要太大）
	 * @param maxRowNumLast 获取到的所有数据总行数
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param fileurl 导出文件的路径
	 * @param filename sheet页的名称
	 * @param mlist excel的核心组成model的集合
	 * @throws FileNotFoundException 
	 * @throws SQLException 
	 * @throws DBException
	 */
	public boolean largeoutput(int maxRowNum, int maxRowNumLast, String sql, String fileurl, String filename, List<M_largedata> mlist) throws FileNotFoundException, IOException, SQLException{		
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
			PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileurl),"UTF-8")); 
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
				
				//拼接表头
				bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
				for(int c = 0; c < mlist.size(); c++){
					m = (M_largedata)mlist.get(c);
					bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\"><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
					m = null;
				}
				bodystr.append("   </Row>\r\n");

				bodystr.append("  </Table>\r\n");
				bodystr.append(" </Worksheet>\r\n");
				
				writer.print(bodystr.toString());
				writer.flush();
				bodystr = null;
			}
			else{
				Connection con = dao.getDataSource().getConnection();
				PreparedStatement st = null;
				ResultSet resultset;
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
						
						//拼接表头
						bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
						for(int c = 0; c < mlist.size(); c++){
							m = (M_largedata)mlist.get(c);
							bodystr.append("    <Cell ss:StyleID=\"" + m.getTitlestyle() + "\"><Data ss:Type=\"String\">" + Validate.isNullToDefault(m.getShowname(),"") + "</Data></Cell>\r\n");
							m = null;
						}
						bodystr.append("   </Row>\r\n");
						writer.print(bodystr.toString());
						writer.flush();
						bodystr = null;
		
						while(resultset.next()){
							bodystr = new StringBuffer();
							bodystr.append("   <Row ss:AutoFitHeight=\"0\">\r\n");
							for(int c = 0; c < mlist.size(); c++){
								m = (M_largedata)mlist.get(c);
	
								String data = Validate.isNullToDefault(resultset.getString(m.getName().toUpperCase()),"") + "";
								boolean b = false;
								
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
										bodystr.append(data);
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
						st = null;
						resultset = null;
					}catch(Error ex){
						logger.error("获取数据失败，原因是：" + ex.getMessage());
						throw new RuntimeErrorException(ex);
					}
					writer.print("  </Table>\r\n");
					writer.print(" </Worksheet>\r\n");
					writer.flush();
					Runtime.getRuntime().gc();
					logger.info("正在生成excel文件的"+name);
				}
				con = null;
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
}
