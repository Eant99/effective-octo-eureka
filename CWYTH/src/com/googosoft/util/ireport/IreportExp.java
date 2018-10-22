package com.googosoft.util.ireport;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

import org.apache.log4j.Logger;

public abstract class IreportExp implements IreportContants{
	private Logger logger = Logger.getLogger(IreportExp.class.getName());

	/**
	 * 执行导出
	 * 
	 * @param request
	 * @param response
	 * @param ireportType
	 *            导出类型
	 * @param ireportFilePath
	 *            ireport文件路径
	 * @param makeFileName
	 *            生成文件名称
	 * @param dataSourceList
	 *            导出数据集合
	 * @param propertiesNameMap
	 *            ireport属性键值对
	 * @throws IreportException
	 */
	private void exportByIreport(HttpServletRequest request,
			HttpServletResponse response, String ireportType,
			String ireportFilePath, String makeFileName, List dataSourceList,
			Map propertiesNameMap) throws IreportException {
		logger.info("ireport开始导出数据");
		String basePath = request.getSession().getServletContext()
				.getRealPath(SPLIT);
		// 获取ireport文件的全路径
		ireportFilePath = basePath + ireportFilePath;
		// 有数据集合list生成dataSource
		JRDataSource dataSource = new JRBeanCollectionDataSource(dataSourceList);
		try {
			IreportAbsPrintMould iExport = IreportPrintFactory.getInstance(ireportType);
			iExport.export(request, response, ireportFilePath, makeFileName,
					dataSource, propertiesNameMap);
		} catch (IreportException e) {
			logger.error("导出文件文件失败\n" + e);
			throw new IreportException("导出文件文件失败\n" + e);
		} finally {
			logger.info("操作结束");
		}
	}

	/**
	 * ireport对外接口
	 * 
	 * @param request
	 * @param response
	 * @throws IreportException
	 */
	void exportByIreport(HttpServletRequest request,
			HttpServletResponse response) throws IreportException {
		// 获取要导出的文件类型
		String ireportType = getIreportType().toUpperCase();
		// 获取ireport文件路径
		String ireportFilePath = getIreportFilePath();
		// 获取要导出的文件名称
		String makeFileName = getMakeFileName();
		if (IreportValidate.isNull(ireportType)) {
			logger.error("导出文件类型不能为空");
			throw new IreportException("导出文件类型不能为空");
		} else if (IreportValidate.isNull(ireportFilePath)) {
			logger.error("ireport文件路径不能为空");
			throw new IreportException("ireport文件路径不能为空");
		} else if (IreportValidate.isNull(makeFileName)) {
			logger.error("导出文件名称不能为空");
			throw new IreportException("导出文件名称不能为空");
		}
		String[] ireportTypes = IREPORTTYPE.split(_);
		for (int i = 0; i < ireportTypes.length; i++) {
			if (ireportTypes[i].equals(ireportType)) {
				break;
			} else if (i == ireportTypes.length - 1) {
				logger.error("输入" + ireportType + "值错误，只限输入" + IREPORTTYPE);
				throw new IreportException("输入" + ireportType + "值错误，只限输入" + IREPORTTYPE);
			}
		}
		// 获取数据list
		List dataSourceList = getDataSourceList();
		// 获取属性map
		Map propertiesNameMap = getPropertiesNameMap();
		// 执行导出
		exportByIreport(request, response, ireportType, ireportFilePath,
				makeFileName, dataSourceList, propertiesNameMap);
	}

	/**
	 * 获取ireport文件路径
	 * 
	 * @return ireport文件路径
	 */
	public abstract String getIreportFilePath();

	/**
	 * 获取ireport要输出的类型（pdf、excel）
	 * 
	 * @return pdf或excel
	 */
	public abstract String getIreportType();

	/**
	 * ireport下载文件的名称
	 * 
	 * @return ireport下载文件的名称
	 */
	public abstract String getMakeFileName();

	/**
	 * ireport输出数据
	 * 
	 * @return ireport输出数据
	 */
	public abstract List getDataSourceList();

	/**
	 * ireport属性键值对
	 * 
	 * @return ireport属性键值对
	 */
	public abstract Map getPropertiesNameMap();
}
