package com.googosoft.util.ireport;

import java.io.File;
import java.util.Map;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import org.apache.log4j.Logger;

public class IreportJasperByDataSource {
	private Logger logger = Logger.getLogger(IreportJasperByDataSource.class.getName());
	
	/** 传入的参数 */
	private Map map;
	/** 模板文件的地址 */
	private String reportFilePath;
	/** dataSrouce */
	private JRDataSource dataSource;
	// 单例模式Begin
	private static IreportJasperByDataSource instance;

	private IreportJasperByDataSource() {
	}

	public static IreportJasperByDataSource getInstance() {
		instance = new IreportJasperByDataSource();
		return instance;
	}

	// 单例模式End
	/**
	 * 检查数据并存入数据
	 * 
	 * @param reportFilePath
	 *            ireport文件路径
	 * @param map
	 *            属性map
	 * @param dataSource
	 *            数据源
	 * @throws IreportException
	 */
	public void init(String reportFilePath, Map map, JRDataSource dataSource)
			throws IreportException {
		if (reportFilePath == null || !reportFilePath.endsWith(".jasper")) {
			logger.error("模板文件格式不对，请传入以.jasper为后缀的文件!");
			throw new IreportException("模板文件格式不对，请传入以.jasper为后缀的文件!");
		}
		if (dataSource == null) {
			logger.error("DataSource不应当为null!");
			throw new IreportException("DataSource不应当为null!");
		}
		this.setReportFilePath(reportFilePath);
		this.setMap(map);
		this.setDataSource(dataSource);
	}

	/**
	 * 取得JasperPrint
	 * 
	 * @return JasperPrint对象
	 * @throws IreportException
	 */
	public JasperPrint getJasperPrint() throws IreportException {
		File reportFile = new File(this.reportFilePath);
		if (!reportFile.exists()) {
			logger.error("传入的模板文件不存在!");
			throw new IreportException("传入的模板文件不存在!");
		} else {
			logger.info("获取到传入的模板");
		}
		try {
			// 进行数据填充
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					this.reportFilePath, this.map, this.dataSource);
			logger.info("数据填充成功");
			return jasperPrint;
		} catch (JRException jre) {
			jre.printStackTrace();
			logger.error("在进行数据填充时发生了错误中，请检查是否填充的参数map有误!");
			throw new IreportException("在进行数据填充时发生了错误中，请检查是否填充的参数map有误!");
		}
	}

	public JRDataSource getDataSource() {
		return dataSource;
	}

	public void setDataSource(JRDataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
	}

	public String getReportFilePath() {
		return reportFilePath;
	}

	/**
	 * @param reportFilePath
	 *            ireport文件路径
	 * @throws IreportException
	 */
	public void setReportFilePath(String reportFilePath)
			throws IreportException {
		if (reportFilePath == null || !reportFilePath.endsWith(".jasper")) {
			logger.error("您传入的模板文件格式不对，请传入以.jasper为后缀的文件!");
			throw new IreportException("您传入的模板文件格式不对，请传入以.jasper为后缀的文件!");
		}
		this.reportFilePath = reportFilePath;
	}
}
