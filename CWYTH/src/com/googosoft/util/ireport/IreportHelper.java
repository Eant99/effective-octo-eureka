package com.googosoft.util.ireport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IreportHelper {
	private IreportBean ireportBean;

	public IreportHelper(HttpServletRequest request, HttpServletResponse response) {
		ireportBean = IreportBean.getInstance();
		ireportBean.init(request, response);
	}

	/**
	 * 执行ireport导出
	 * 
	 * @throws IreportException
	 */
	public void toPrint() throws IreportException {
		ireportBean.toPrint();
	}

	/**
	 * 存放ireport文件路径
	 * 
	 * @param ireportFilePath
	 */
	public void setIreportFilePath(String ireportFilePath) {
		ireportBean.setIreportFilePath(ireportFilePath);
	}

	/**
	 * 存放ireport要输出的格式
	 * 
	 * @param ireportType
	 */
	public void setIreportType(String ireportType) {
		ireportBean.setIreportType(ireportType);
	}

	/**
	 * 要导出的文件名称
	 * 
	 * @param makeFileName
	 */
	public void setMakeFileName(String makeFileName) {
		ireportBean.setMakeFileName(makeFileName);
	}

	/**
	 * 要导出的数据集合
	 * 
	 * @param dataSourceList
	 */
	public void setDataSourceList(List dataSourceList) {
		ireportBean.setDataSourceList(dataSourceList);
	}

	/**
	 * ireport属性键值对
	 * 
	 * @param propertiesNameMap
	 */
	public void setPropertiesNameMap(Map propertiesNameMap) {
		ireportBean.setPropertiesNameMap(propertiesNameMap);
	}
}
