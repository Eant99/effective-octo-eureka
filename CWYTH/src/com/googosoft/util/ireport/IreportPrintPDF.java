package com.googosoft.util.ireport;

import java.io.OutputStream;
import java.util.List;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import org.apache.log4j.Logger;

public class IreportPrintPDF extends IreportAbsPrintMould {
	private Logger logger = Logger.getLogger(IreportPrintPDF.class.getName());
	String getIreportType() {
		String[] pdf_messages = PDF_MESSAGE.split(_);
		for (int i = 0; i < pdf_messages.length; i++) {
			logger.info(pdf_messages[i]);
		}
		return pdf;
	}

	void setExporter(List jasperPrintList,OutputStream out) throws JRException {
		// 使用JRPdfExproter导出器导出pdf
		JRPdfExporter exporter = new JRPdfExporter();
		// 设置JasperPrintList
		exporter.setParameter(JRExporterParameter.JASPER_PRINT_LIST,
				jasperPrintList);
		exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, out);
		exporter.exportReport();
	}
}
