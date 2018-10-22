package com.googosoft.util.ireport;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class IreportBean extends IreportExp {
	private String ireportFilePath;
	private String ireportType;
	private String makeFileName;
	private List dataSourceList;
	private Map propertiesNameMap;
	private HttpServletRequest request;
	private HttpServletResponse response;
	// 单例模式Begin
	private static IreportBean instance;

	private IreportBean() {
	}

	public static IreportBean getInstance() {
		instance = new IreportBean();
		return instance;
	}

	// 单例模式End
	public void init(HttpServletRequest request, HttpServletResponse response) {
		this.request = request;
		this.response = response;
	}

	/**
	 * 执行ireport导出
	 * 
	 * @throws IreportException
	 */
	public void toPrint() throws IreportException {
		exportByIreport(request, response);
	}

	@Override
	public String getIreportFilePath() {
		return ireportFilePath;
	}

	@Override
	public String getIreportType() {
		return ireportType;
	}

	@Override
	public String getMakeFileName() {
		return makeFileName;
	}

	@Override
	public List getDataSourceList() {
		if (dataSourceList == null || dataSourceList.isEmpty()) {
			dataSourceList = new ArrayList();
			dataSourceList.add(new HashMap());
		}
		return dataSourceList;
	}

	@Override
	public Map getPropertiesNameMap() {
		return propertiesNameMap;
	}

	/**
	 * 存放ireport文件路径
	 * 
	 * @param ireportFilePath
	 */
	public void setIreportFilePath(String ireportFilePath) {
		this.ireportFilePath = ireportFilePath;
	}

	/**
	 * 存放ireport要输出的格式
	 * 
	 * @param ireportType
	 */
	public void setIreportType(String ireportType) {
		this.ireportType = ireportType;
	}

	/**
	 * 要导出的文件名称
	 * 
	 * @param makeFileName
	 */
	public void setMakeFileName(String makeFileName) {
		this.makeFileName = makeFileName;
	}

	/**
	 * 要到处的数据集合
	 * 
	 * @param dataSourceList
	 */
	public void setDataSourceList(List dataSourceList) {
		this.dataSourceList = dataSourceList;
	}

	/**
	 * ireport属性键值对
	 * 
	 * @param propertiesNameMap
	 */
	public void setPropertiesNameMap(Map propertiesNameMap) {
		this.propertiesNameMap = propertiesNameMap;
	}
}
