package com.googosoft.util;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
/**
 * 目前是从网上搞得。需要持续研究中。 
 * @author 贩子崔
 *
 */
public class PatchUtil {

	public static String patchFile="D:/patch.txt";//补丁文件,由eclipse svn plugin生成
	
	public static String projectPath="E:/JavaCode/GXGDZC7";//项目文件夹路径
	
	public static String webContent="WebContent";//web应用文件夹名
	
	public static String classPath="E:/JavaCode/GXGDZC7/build/classes";//class存放路径
	
	public static String desPath="d:/update_pkg";//补丁文件包存放路径
	
	public static String version="20170608中国地质大学"+"/程序";//补丁版本 ： 日期+学校名称
	
	
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		copyFiles(getPatchFileList());
	}
	
	public static List<String> getPatchFileList() throws Exception{
		List<String> fileList=new ArrayList<String>();
		FileInputStream f = new FileInputStream(patchFile); 
		BufferedReader dr=new BufferedReader(new InputStreamReader(f,"utf-8"));
		String line;
		while((line=dr.readLine())!=null){ 
			if(line.indexOf("Index:")!=-1){
				line=line.replaceAll(" ","");
				line=line.substring(line.indexOf(":")+1,line.length());
				fileList.add(line);
			}
		} 
		return fileList;
	}
	
	public static void copyFiles(List<String> list){
		
		for(String fullFileName:list){
			if(fullFileName.indexOf("src/")!=-1){//对源文件目录下的文件处理
				String fileName=fullFileName.replace("src","");
				fullFileName=classPath+fileName;
				if(fileName.endsWith(".java")){
					fileName=fileName.replace(".java",".class");
					fullFileName=fullFileName.replace(".java",".class");
				}
				String tempDesPath=fileName.substring(0,fileName.lastIndexOf("/"));
				String desFilePathStr=desPath+"/"+version+"/WEB-INF/classes"+tempDesPath;
				String desFileNameStr=desPath+"/"+version+"/WEB-INF/classes"+fileName;
				File desFilePath=new File(desFilePathStr);
				if(!desFilePath.exists()){
					desFilePath.mkdirs();
				}
				copyFile(fullFileName, desFileNameStr);
				System.out.println(fullFileName+"复制完成");
			}else{//对普通目录的处理
				String desFileName=fullFileName.replaceAll(webContent,"");
				fullFileName=projectPath+"/"+fullFileName;//将要复制的文件全路径
				String fullDesFileNameStr=desPath+"/"+version+desFileName;
				String desFilePathStr=fullDesFileNameStr.substring(0,fullDesFileNameStr.lastIndexOf("/"));
				File desFilePath=new File(desFilePathStr);
				if(!desFilePath.exists()){
					desFilePath.mkdirs();
				}
				copyFile(fullFileName, fullDesFileNameStr);
				System.out.println(fullDesFileNameStr+"复制完成");
			}
			
		}
		
	}

	private static void copyFile(String sourceFileNameStr, String desFileNameStr) {
		File srcFile=new File(sourceFileNameStr);
		File desFile=new File(desFileNameStr);
		try {
			copyFile(srcFile, desFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	
	
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
        BufferedInputStream inBuff = null;
        BufferedOutputStream outBuff = null;
        try {
            // 新建文件输入流并对它进行缓冲
            inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

            // 新建文件输出流并对它进行缓冲
            outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

            // 缓冲数组
            byte[] b = new byte[1024 * 5];
            int len;
            while ((len = inBuff.read(b)) != -1) {
                outBuff.write(b, 0, len);
            }
            // 刷新此缓冲的输出流
            outBuff.flush();
        } finally {
            // 关闭流
            if (inBuff != null)
                inBuff.close();
            if (outBuff != null)
                outBuff.close();
        }
    }
	
}
