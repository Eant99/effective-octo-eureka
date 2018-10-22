package com.googosoft.commons;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import javax.annotation.Resource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import com.googosoft.controller.base.BaseController;
import com.googosoft.util.Const;
import com.googosoft.util.Tools;

@Component("systemConfig")
public class SystemConfig  extends BaseController{
	@Resource(name="jdbcTemplate")
	public JdbcTemplate db;
	/**
	 * 配置
	 * @return
	 */
	public  String ConfigState()
	{
		//数据库配置情况
		if(DBState().length()>0)
		{
			return DBState();
		}
		else 
		{
			//加密方式：试用期-0、授权码-1 、无加密-99
			if(Tools.readXmlFile(Const.SYSCONFIG,"app-iskey").equals("99"))
			{
				return "";
			}
			else if(Tools.readXmlFile(Const.SYSCONFIG,"app-iskey").equals("0"))
			{
				//试用期
				return TryTime();
			}
			else if(Tools.readXmlFile(Const.SYSCONFIG,"app-iskey").equals("1"))
			{
				return SoftCode();
			}
			else
			{
				return "软件配置错误，请重新配置！";
			}
		}
	}
	/**
	 * 获取数据库状态
	 * @return
	 */
	public  String DBState(){
//		Connection con=null;
//        try {
//        	con=db.getDataSource().getConnection();
//        	con.close();
//		} catch (SQLException e) {
//			return "error";
//		}
//        finally
//        {
//        	try {
//        		if (con != null)
//                    con.close();
//			} catch (SQLException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//        }
		return ""; 
	}
	/**
	 * 获取数据库状态
	 * @return 0，连接成功；1，oracle数据库驱动 加载不成功；2，连接不成功。
	 * @throws ClassNotFoundException 
	 * @throws SQLException 
	 */
	public  String DBState(String url,String user,String passWord){
		Connection con = null;// 创建一个数据库连接
		try {
			 logger.info("开始尝试连接数据库！");
			 Class.forName("oracle.jdbc.driver.OracleDriver");//加载oracle数据库驱动
			 con = DriverManager.getConnection(url, user, passWord); // 获取连接
			 System.out.println("连接成功！");
		     return "0";
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			return "1";
		}catch (SQLException e) {
			 e.getSQLState();//状态码获取
			 System.out.println("错误码：" + e.getSQLState());
			 return "2";
		}  
        finally
        {
            try
            {
                // 对象关闭，因为不关闭的话会影响性能、并且占用资源
                if (con != null)
                    con.close();
                System.out.println("数据库连接已关闭！");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
	}
	/**
	 * 试用期
	 */
	private String TryTime()
	{   
		//googosoft+zcgl+2016-11-11  miyao:googosoft
		String trytime=Tools.readXmlFile(Const.SYSCONFIG,"trytime");
		if(!trytime.equalsIgnoreCase(""))
		{
			return "";
		}
		return "软件配置错误,请重新配置!";
	}
	/**
	 * 软件序列号
	 */
	private String SoftCode()
	{

		String softcode=Tools.readXmlFile(Const.SYSCONFIG,"softcode");
		if(!softcode.equalsIgnoreCase(""))
		{
			return "";
		}
		return "软件配置错误,请重新配置!";
	}
	//配置数据库文件(写入)
	public String writeData(String url,String username,String password,String databack){
		
		String filePath = this.getRequest().getServletContext().getRealPath("\\")+"WEB-INF\\classes\\googosoft.properties";
		Properties props = new Properties();
		   try {
				InputStream fis = new FileInputStream(filePath);
				props.load(fis);
				fis.close();
				OutputStream fos = new FileOutputStream(filePath);
				props.setProperty("url", url);
				props.setProperty("password", password);
				props.setProperty("username", username);
				props.setProperty("databack", databack);
				//以适合使用 load 方法加载到 Properties 表中的格式，
				//将此 Properties 表中的属性列表（键和元素对）写入输出流
				props.store(fos,"oracle"); 
				fos.close(); //关闭流
			   } catch (IOException e) {
					System.err.println("Visit "+filePath+" for updating "+username+" value error");
/*				writeLog(this.class.getName(), "Visit "+filePath+" for updating "+json.toString()+" value error", e);*/
		   }
		   return "success";
	}
}
