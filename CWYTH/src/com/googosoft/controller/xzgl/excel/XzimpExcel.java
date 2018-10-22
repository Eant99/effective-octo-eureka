package com.googosoft.controller.xzgl.excel;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jxl.Workbook;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.UnderlineStyle;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

/**
*@author 杨超超
*@date   2018年3月6日---上午9:34:35
*/
public class XzimpExcel {

	 public static Map<String, Object> exportExcel(String fileurl,String shortfileurl,String[] title,String TITLE, List<Map<String, Object>> listContent,String[] xzzd) {  
			String result="系统提示：Excel文件导出成功！"; 
			boolean flag=true;
			//以下开始输出到EXCEL  
			try {      
			   /** **********创建工作簿************ */  
				WritableWorkbook workbook = Workbook.createWorkbook(new File(fileurl));
			   /** **********创建工作表************ */  
			   WritableSheet sheet = workbook.createSheet("单位机构",0);
			   sheet.getSettings().setVerticalFreeze(2);
			   for(int i=1;i<title.length;i++){
				   sheet.setColumnView(i,30); 
			   }
			   /** ************设置单元格字体************** */  
			   WritableFont NormalFont = new WritableFont(WritableFont.ARIAL, 10);  
			   WritableFont BoldFont = new WritableFont(WritableFont.ARIAL, 12,WritableFont.BOLD);  
			   WritableFont font = new WritableFont(WritableFont.createFont("楷体"),15, WritableFont.BOLD,false,UnderlineStyle.NO_UNDERLINE); 
			   /** ************以下设置三种单元格样式，灵活备用************ */  
			   //表头
			   WritableCellFormat title_style = new WritableCellFormat(font);  
			   title_style.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
			   title_style.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
			   title_style.setAlignment(Alignment.CENTRE); // 文字水平对齐  
			   title_style.setWrap(false); // 文字是否换行  
			   // 用于标题居中  
			   WritableCellFormat wcf_center = new WritableCellFormat(BoldFont);  
			   wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
			   wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
			   wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
			   wcf_center.setWrap(false); // 文字是否换行  
			   wcf_center.setBackground(Colour.GRAY_25);
			   // 用于正文居左  
			   WritableCellFormat wcf_left = new WritableCellFormat(NormalFont);  
			   wcf_left.setBorder(Border.NONE, BorderLineStyle.THIN); // 线条  
			   wcf_left.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
			   wcf_left.setAlignment(Alignment.LEFT); // 文字水平对齐  
			   wcf_left.setWrap(false); // 文字是否换行    
			   /*****************以下是EXCEL开头大标题*********************/  
//			   sheet.mergeCells(0, 0, title.length-1, 0); 
//			   sheet.setRowView(0, 650, false); //设置行高
//			   sheet.setRowView(1, 400, false); 
//			   sheet.addCell(new Label(0, 0, TITLE, title_style));  
			   for (int j = 0; j < title.length; j++) {  
				   sheet.addCell(new Label(j, 0,xzzd[j],wcf_center));  
			   } 
			   /** ***************以下是EXCEL第一行列标题********************* */  
			   for (int i = 0; i < title.length; i++) {  
				   sheet.addCell(new Label(i, 1,title[i],wcf_center));  
			   } 
			   /************将以上缓存中的内容写到EXCEL文件中*********/  
			   workbook.write();  
			   /***********关闭文件**************/  
			   workbook.close();     
			   } catch (Exception e) {  
				   result="系统提示：Excel文件导出失败，原因："+ e.toString(); 
				   flag=false;
				   e.printStackTrace();  
			   }  
			   Map<String, Object> map=new HashMap<String, Object>();
			   map.put("flag", flag);
			   map.put("msg", result);
			   map.put("url", shortfileurl);
			   return map;  
			}

}
