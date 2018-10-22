package com.googosoft.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import com.googosoft.dao.base.DBHelper;
import com.googosoft.pojo.exp.M_largedata;
/**
 * 导出txt的公共类
 * @author zjy
 *
 */
public class TxtExp {
	private static Logger logger = Logger.getLogger(TxtExp.class.getName());
	
	@Resource(name="jdbcTemplate")
	private static DBHelper db;

	/**
	 * 下载导出的txt
	 * @param request
	 * @param response
	 * @param application
	 * @param sql 获取数据的语句
	 * @param xlsname 文件的名称（也是sheet页的名称）
	 * @param mlist excel的核心组成model的集合
	 * @throws DBException
	 * @throws IOException
	 */
	public static void expTxtData(HttpServletResponse response,ServletContext application,String sql,String filename,List<M_largedata> mlist) throws IOException {
		List list;
		int maxRowNumLast = 0;
		try{
			maxRowNumLast = Integer.parseInt(db.queryForSingleValue("select count(*) from (" + sql + ")") + "");
		}
		catch(OutOfMemoryError e){
			logger.error("查询数据库时内存溢出，原因是：" + e.getMessage());
			e.printStackTrace();
		}
		
		String txtname = UuidUtil.get32UUID();
		String file = application.getRealPath("\\")+"\\" + txtname + ".txt";
		String errorname = String.valueOf(System.currentTimeMillis());
		String errfile = application.getRealPath("\\")+"\\" + errorname + ".txt"; 
		
		response.setContentType("application/x-download");//text/plain
		java.io.OutputStream outp = null;
		FileInputStream in = null;
		try
		{ 
			//按照config.properties这里边设置的默认条数导出
			int expCnt = Integer.parseInt(Validate.isNullToDefault(ResourceBundle.getBundle("global").getString("expCnt_txt"),"10000") + "");
			List errlist = new ArrayList();
			boolean success = expByTxt(expCnt,maxRowNumLast,sql,file,mlist,errlist);
			if(success){
				String filedisplay = filename + ".txt";
				filedisplay = URLEncoder.encode(filedisplay,"UTF-8");
				response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);
				
				outp = response.getOutputStream();
				in = new FileInputStream(file);
				
				byte[] b = new byte[1024];
				int i = 0;
				
				while((i = in.read(b)) > 0)
				{
					outp.write(b, 0, i);
				}
				outp.flush();
			}
			else{
				if(errlist.size() > 0){
					String filedisplay = "错误信息_" + filename + ".txt";
					filedisplay = URLEncoder.encode(filedisplay,"UTF-8");
					response.addHeader("Content-Disposition","attachment;filename=" + filedisplay);
					PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(errfile),"GBK"));
					for(int i = 0; i < errlist.size(); i++){
						writer.println(errlist.get(i));
					}
					outp = response.getOutputStream();
					in = new FileInputStream(errfile);
					writer.close();
					
					byte[] b = new byte[1024];
					int i = 0;
					
					while((i = in.read(b)) > 0)
					{
						outp.write(b, 0, i);
					}
					outp.flush();
				}
				else{
					throw new Exception("未知的原因导致导出txt时失败");
				}
			}
		}
		catch(Exception e)
		{
			logger.error("导出出错，原因是：" + e.getMessage());
			e.printStackTrace();
		}
		finally
		{
			if(in != null)
			{
				in.close();
				in = null;
			}
			if(outp != null)
			{
				outp.close();
				outp = null;
			}
			File dir = new File(file);
			if(dir.exists()){
				dir.delete();
			}
			dir = null;
			dir = new File(errfile);
			if(dir.exists()){
				dir.delete();
			}
		}
	}
	
	/**
	 * 导出txt的核心方法
	 * @param maxRowNum 每次获取的最大行数
	 * @param maxRowNumLast 获取到的所有数据总行数
	 * @param sql 获取数据的sql语句（不能包括rn字段）
	 * @param fileurl 导出文件的路径
	 * @param filename 导出文件的名称
	 * @param mlist excel的核心组成model的集合
	 * @param errlist 错误信息集合
	 */
	public static boolean expByTxt(int maxRowNum, int maxRowNumLast, String sql, String fileurl, List<M_largedata> mlist, List errlist) throws FileNotFoundException, IOException{		
		long startTimne = System.currentTimeMillis();//获取当前时间
		StringBuffer bodystr;
		int sheets = (int)Math.ceil((float)maxRowNumLast/(float)maxRowNum);//这里必须转成float类型才能出正确结果（否则不会在除不尽时加1）
		PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(fileurl),"GBK"));//这里必须是GBK，要不然无法上报
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
					bodystr.append(completeStr(map.get(m.getName()),m.getWs(),m.getColtype(),errlist,m.getZj(),zjzd,m.getShowname()));
					//bodystr.append("==");
				}
				writer.println(bodystr.toString());
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
	 * 补齐字符串
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @return
	 */
	private static String completeStr(Object str, int cd, String coltype, List errlist, String zj, String zjzd, String dqzd){
		return completeStrTwo(str, cd, " ", coltype, errlist, zj, zjzd, dqzd);
	}

	/**
	 * 补齐字符串(汉字按一个字符算)
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param zd 用哪个字符补齐
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @param errlist 存放错误信息的集合
	 * @param zj 主键字段的名称
	 * @param zjzd 主键字段的内容
	 * @param dqzd 当前字段的名称
	 * @return
	 */
	private static String completeStrOne(Object str, int cd, String zd, String coltype, List errlist, String zj, String zjzd, String dqzd){
		String data = Validate.isNullToDefault(str, "") + "";
		int varlen = data.toCharArray().length;
		int zdlen = zd.toCharArray().length;
		if(varlen < cd){//如果要显示的字符长度不够，则用补齐字符补齐
			for(int i = varlen; i < cd; i++){
				if("String".equals(coltype)){
					data = data + zd;
				}
				else{
					data = zd + data;
				}
				if(zdlen != 1){
					i = i + zdlen - 1;
				}
			}
		}
		else if(varlen > cd){//如果要显示的字符超长，则要把错误信息存入list
			String text = "";
			if(Validate.noNull(zjzd)){
				text = zj + zjzd + "的";
			}
			errlist.add(text + dqzd + "超长，规定长度" + cd + "，实际长度" + varlen + "，请修改后导出");
		}
		return data;
	}
	/**
	 * 补齐字符串（一个汉字当两个字符）
	 * @param str 需要补齐的字符串
	 * @param cd 总长度
	 * @param zd 用哪个字符补齐
	 * @param coltype 字段类型（String：在右侧补齐，Number：在左侧补齐）
	 * @param errlist 存放错误信息的集合
	 * @param zj 主键字段的名称
	 * @param zjzd 主键字段的内容
	 * @param dqzd 当前字段的名称
	 * @return
	 */
	private static String completeStrTwo(Object str, int cd, String zd, String coltype, List errlist, String zj, String zjzd, String dqzd){
		String data = Validate.isNullToDefault(str, "") + "";
		String chinese = "[^\\x00-\\xff]|[×±°]";
		int varlen = 0;//当前显示字段的长度
		for (int i = 0; i < data.length(); i++) {
            String temp = data.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
            	varlen += 2;
            } else {
            	varlen += 1;
            }
        }
		int zdlen = 0;//补齐字符的长度
		for (int i = 0; i < zd.length(); i++) {
            String temp = zd.substring(i, i + 1);
            /* 判断是否为中文字符 */
            if (temp.matches(chinese)) {
            	zdlen += 2;
            } else {
            	zdlen += 1;
            }
        }
		
		if(varlen < cd){//如果要显示的字符长度不够，则用补齐字符补齐
			for(int i = varlen; i < cd; i++){
				if("String".equals(coltype)){
					data = data + zd;
				}
				else{
					data = zd + data;
				}
				if(zdlen != 1){
					i = i + zdlen - 1;
				}
			}
		}
		else if(varlen > cd){//如果要显示的字符超长，则要把错误信息存入list
			String text = "";
			if(Validate.noNull(zjzd)){
				text = zj + zjzd + "的";
			}
			errlist.add(text + dqzd + "超长，规定长度" + cd + "，实际长度" + varlen + "（一个汉字等于两个字符），请修改后导出");
		}
		return data;
	}
}
