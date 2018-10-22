package com.googosoft.commons;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * Java属性文件操作工具类
 * <p>由于每个人的需求不一样，所以本工具只提供常用的功能（包括根据键名获取键值，根据键名修改键值），
 * 如果不满足，自个完全可以修改，在本工具类中，我们假设属性文件已经存在，并且文件中已经存在我们要操作的key</p>
 * @author HsuChan
 * @version v1.0.0 2015-06-19
 */
public class PropertiesUtil {
    private static Properties properties= new Properties();
    private static Logger logger = Logger.getLogger(PropertiesUtil.class);
    /**测试用 属性文件名*/
    private static final String PROPERTIES_FILE_PATH="/googosoft.properties";
    
    /**测试用 属性文件名*/
    private static final String PROPERTIES_GLOBALFILE_PATH="/global.properties";
    /**测试用 操作的键名*/
    private static final String KEY_NAME="username";
    
    public static final String PROPERTIES_LICENSE_PATH="/license.properties";
    public static final String PROPERTIES_LICENSE_KEYW="googosoft_gdzcgl";
    
    
    /**
     * 初始化properties
     * @param path 属性文件路径,如:/googosoft.properties
     */
    private static void initProperties(String path) {
        
        try {
            InputStream ipsm = PropertiesUtil.class.getResourceAsStream(path);
            properties.load(ipsm);
            ipsm.close();
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
    }

    /**
     * 根据键名key获取属性文件中对应的键值
     * @param path 属性文件路径,如:/googosoft.properties
     * @param key 属性文件 键名
     * @return
     */
    public static String getValueByKey(String path,String key){
            initProperties(path);
        return properties.getProperty(key);
    }
    
    /**
     * 根据键名key获取global.properties属性文件中对应的键值
     * @param key 属性文件 键名
     * @return
     */
    public static String getGlobalValueByKey(String key){
            initProperties("/global.properties");
        return properties.getProperty(key);
    }
    
    /**
     * 根据键名key获取googosoft.properties属性文件中对应的键值
     * @param key 属性文件 键名
     * @return
     */
    public static String getGGSValueByKey(String key){
    	//如果properties为空，则初始化
        if(properties.isEmpty())
            initProperties("/googosoft.properties");
        return properties.getProperty(key);
    }
    
    /**
     * 通过键名key修改其对应的值为value
     * @param path 属性文件路径,如:/googosoft.properties
     * @param key  属性文件 键名
     * @param value 属性文件 键值
     */
    public static void modifyValueByKey(String path,String key,String value) {

         initProperties(path);
        //修改值
        properties.setProperty(key, value);
       
        try {
            //获取文件路径
            URL url = PropertiesUtil.class.getResource(path);
            FileOutputStream fos = new FileOutputStream(new File(url.toURI()));
            //保存到文件
            properties.store(fos, "modify the value of key");
            fos.close();
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
    
  
    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) {
        //取值示例
/*        System.out.println("修改前："+getValueByKey(PROPERTIES_FILE_PATH,KEY_NAME));
        System.out.println("修改前："+getGGSValueByKey("username"));*/
        System.out.println("修改测试："+getGlobalValueByKey("WebSocketUri"));
        //修改示例
        //modifyValueByKey(PROPERTIES_FILE_PATH,KEY_NAME,"3333311s");
        //取值示例
        System.out.println("修改后："+getValueByKey(PROPERTIES_FILE_PATH,KEY_NAME));
        
    }
}
