package com.googosoft.util.ireport;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.j2ee.servlets.BaseHttpServlet;

import org.apache.log4j.Logger;

public abstract class IreportAbsPrintMould implements IreportContants {
	private Logger logger = Logger.getLogger(IreportAbsPrintMould.class.getName());

	/**
	 * 导出报表
	 * 
	 * @param request
	 * @param response
	 * @param ireportFilePath
	 *            ireport文件路径
	 * @param makeFileName
	 *            生成文件的名称
	 * @param dataSource
	 *            数据源
	 * @param propertiesNameMap
	 *            属性集合
	 * @throws IreportException
	 * @throws IreportException
	 */
	public void export(HttpServletRequest request,
			HttpServletResponse response, String ireportFilePath,
			String makeFileName, JRDataSource dataSource, Map propertiesNameMap)
			throws IreportException, IreportException {
		JasperPrint jasperPrint = null;
		try {
			IreportJasperByDataSource jasperPrintWithDataSource = IreportJasperByDataSource.getInstance();
			jasperPrintWithDataSource.init(ireportFilePath, propertiesNameMap,dataSource);
			jasperPrint = jasperPrintWithDataSource.getJasperPrint();
		} catch (Exception e) {
			logger.error("把数据放入jasper文件失败，请检查数据或jasper设计是否有误\n" + e);
			throw new IreportException("把数据放入jasper文件失败，请检查数据或jasper设计是否有误\n" + e);
		}
		// 将填充完的japserPrint放入session中。
		request.getSession().setAttribute(
				BaseHttpServlet.DEFAULT_JASPER_PRINT_SESSION_ATTRIBUTE,
				jasperPrint);
		// 拿到japserPrintList
		List jasperPrintList = BaseHttpServlet.getJasperPrintList(request);
		// 若没有JasperPrintList，则抛出异常
		if (jasperPrintList == null) {
			logger.error("在Http Session中没有找到JasperPrint List");
			throw new IreportException("在Http Session中没有找到JasperPrint List");
		}
		OutputStream out = null;
		String ireportType = getIreportType();
		try {
			out = response.getOutputStream();
			response.setContentType("application/" + ireportType);
			response.setCharacterEncoding(ENCODING);
			response.setHeader("Content-Disposition", "inline; filename=\""
					+ URLEncoder.encode(makeFileName, ENCODING) + DOT
					+ ireportType + "\"");
			setExporter(jasperPrintList, out);
		} catch (IOException ioe) {
			logger.error("从Response中取得OutputStream时发生错误!");
			throw new IreportException("从Response中取得OutputStream时发生错误!");
		} catch (JRException e) {
			logger.error("导出文件失败");
			throw new IreportException(e);
		} finally {
			if (out != null) {
				try {
					out.close();
					out = null;
				} catch (IOException ex) {
				}
			}
		}
	}

	/**
	 * 获取要导出文件的格式类型
	 * 
	 * @return
	 */
	abstract String getIreportType();

	/**
	 * 设置导出器
	 * 
	 * @throws JRException
	 */
	abstract void setExporter(List jasperPrintList, OutputStream out)
			throws JRException;
}
