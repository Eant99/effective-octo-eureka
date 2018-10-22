package com.googosoft.util.gen;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;
/**
 * 自动实体类生成器
 * @author master
 * @version 1.0
 */
public class AutoBean {
	
	public static void main(String[] args) {
		new AutoBean("cw_hysqb", "com.googosoft.pojo.wsbx");
	}
	
	private Logger logger = Logger.getLogger(AutoBean.class);
	private String authorName = "dddd";// 作者名字
	private String tablename = "";// 表名
	private String[] colnames; // 列名数组
	private String[] colTypes; // 列名类型数组
	private int[] colSizes; // 列名大小数组

	// 数据库连接
	private static final String URL = "jdbc:oracle:thin:@192.168.11.111:1521:SHITAN";
	private static final String NAME = "ngycw";
	private static final String PASS = "ngycw";
	private static final String DRIVER = "oracle.jdbc.driver.OracleDriver";

	
	public AutoBean(String table,String packagePath){
		// 创建连接
		Connection con;
		// 查要生成实体类的表
		String sql = "select * from " + table;
		Statement pStemt = null;
		try {
			Class.forName(DRIVER);
			con = DriverManager.getConnection(URL, NAME, PASS);
			pStemt = (Statement) con.createStatement();
			ResultSet rs = pStemt.executeQuery(sql);
			ResultSetMetaData rsmd = rs.getMetaData();
			int size = rsmd.getColumnCount(); // 统计列
			colnames = new String[size];
			colTypes = new String[size];
			colSizes = new int[size];
			for (int i = 0; i < size; i++) {
				colnames[i] = rsmd.getColumnName(i + 1);
				colTypes[i] = rsmd.getColumnTypeName(i + 1);
				//date timestamp类型同一按照String类型进行处理
//				if (colTypes[i].equalsIgnoreCase("date") || colTypes[i].equalsIgnoreCase("timestamp")) {
//					f_util = true;
//				}
//				if (colTypes[i].equalsIgnoreCase("blob") || colTypes[i].equalsIgnoreCase("char")) {
//					f_sql = true;
//				}
				colSizes[i] = rsmd.getColumnDisplaySize(i + 1);
			}
			this.tablename = table;
			String content = parse(colnames, colTypes, colSizes,packagePath);
			try {
				File directory = new File("");
				String path = this.getClass().getResource("").getPath();
				String outputPath = directory.getAbsolutePath() + "/src/" + packagePath.replace(".", "/") + "/" + table.toUpperCase() + ".java";
				FileWriter fw = new FileWriter(outputPath);
				PrintWriter pw = new PrintWriter(fw);
				System.out.println(table+"实体类已生成");
				pw.println(content);
				pw.flush();
				pw.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (SQLException e) {
			logger.error("数据库异常，请检查数据库连接相关信息");
		} catch (ClassNotFoundException e1) {
			logger.error("数据库驱动未找到，加载驱动失败");
		} finally {
			
		}
	}

	/**
	 * 功能：生成实体类主体代码
	 * 
	 * @param colnames
	 * @param colTypes
	 * @param colSizes
	 * @return
	 */
	private String parse(String[] colnames, String[] colTypes, int[] colSizes,String packagePath) {
		StringBuffer sb = new StringBuffer();
// 		判断是否导入工具包
//		if (f_util) {
//			sb.append("import java.util.Date;\r\n");
//		}
//		if (f_sql) {
//			sb.append("import java.sql.*;\r\n");
//		}
		sb.append("package " + packagePath + ";\r\n");
		sb.append("\r\n");
		// 注释部分
		sb.append("/**\r\n");
		sb.append("* " + tablename + " 实体类\r\n");
		sb.append("* 创建时间： " + new SimpleDateFormat("yyyy-MM-dd").format(new Date())+ "\r\n");
		sb.append("*@author " + this.authorName + "\r\n");
		sb.append("*/ \r\n");
		// 实体部分
		sb.append("\r\n\r\npublic class " + tablename.toUpperCase() + "{\r\n");
		processAllAttrs(sb);// 属性
		processAllMethod(sb);// get set方法
		sb.append("}\r\n");
		return sb.toString();
	}

	/**
	 * 功能：生成所有属性
	 * @param sb
	 */
	private void processAllAttrs(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tprivate " + sqlTypeJavaType(colTypes[i]) + " " + colnames[i].toLowerCase() + ";\r\n");
		}

	}

	/**
	 * 功能：生成所有方法
	 * 
	 * @param sb
	 */
	private void processAllMethod(StringBuffer sb) {
		for (int i = 0; i < colnames.length; i++) {
			sb.append("\tpublic void set" + initcap(colnames[i]) + "(" + sqlTypeJavaType(colTypes[i]) + " " + colnames[i] + "){\r\n");
			sb.append("\tthis." + colnames[i].toLowerCase() + "=" + colnames[i] + ";\r\n");
			sb.append("\t}\r\n");
			sb.append("\tpublic " + sqlTypeJavaType(colTypes[i]) + " get" + initcap(colnames[i]) + "(){\r\n");
			sb.append("\t\treturn " + colnames[i].toLowerCase() + ";\r\n");
			sb.append("\t}\r\n");
		}
	}

	/**
	 * 功能：将输入字符串的首字母改成大写
	 * 
	 * @param str
	 * @return
	 */
	private String initcap(String str) {
		str = str.toLowerCase();
		char[] ch = str.toCharArray();
		if (ch[0] >= 'a' && ch[0] <= 'z') {
			ch[0] = (char) (ch[0] - 32);
		}
		return new String(ch);
	}

	/**
	 * 功能：获得列的数据类型
	 * 
	 * @param sqlType
	 * @return
	 */
	private String sqlTypeJavaType(String sqlType) {
		if (sqlType.equalsIgnoreCase("binary_double")) {
			return "double";
		} else if (sqlType.equalsIgnoreCase("binary_float")) {
			return "float";
		} else if (sqlType.equalsIgnoreCase("blob")) {
			return "byte[]";
		} else if (sqlType.equalsIgnoreCase("blob")) {
			return "byte[]";
		} else if (sqlType.equalsIgnoreCase("char") || sqlType.equalsIgnoreCase("nvarchar") || sqlType.equalsIgnoreCase("varchar")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("date") || sqlType.equalsIgnoreCase("timestamp") || sqlType.equalsIgnoreCase("timestamp with local time zone") || sqlType.equalsIgnoreCase("timestamp with time zone")) {
			return "String";
		} else if (sqlType.equalsIgnoreCase("number")) {
			return "float";
		}
		return "String";
	}


}