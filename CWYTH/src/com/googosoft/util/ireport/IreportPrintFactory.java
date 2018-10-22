package com.googosoft.util.ireport;

import org.apache.log4j.Logger;

public abstract class IreportPrintFactory implements IreportContants {
	private static Logger logger = Logger.getLogger(IreportPrintFactory.class.getName());
	private static IreportAbsPrintMould iExport = null;

	/**
	 * 根据要打印的类型获取打印实体类
	 * 
	 * @param ireportType
	 *            打印类型
	 * @return 打印实体类
	 * @throws IreportException
	 */
	public static IreportAbsPrintMould getInstance(String ireportType)
			throws IreportException {
		if (ireportType.equals(PDF)) {
			iExport = new IreportPrintPDF();
//		} else if (ireportType.equals(EXCEL)) {
//			iExport = new PrintXlsByPOI();
//		} else if (ireportType.equals(EXCEL)) {
//			iExport = new PrintXlsByJExcel();
		} else {
			logger.error("输入" + ireportType + "值错误，只限输入" + IREPORTTYPE);
			throw new IreportException("输入" + ireportType + "值错误，只限输入"
					+ IREPORTTYPE);
		}
		return iExport;
	}
}
