package com.googosoft.controller.kjhs;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;

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

import com.googosoft.util.Validate;

public class WldwExportExcel {
	/**
	 * 往来单位导出
	 * 
	 * @author Administrator
	 * 
	 */
	 public static Map<String, Object> exportExcel(String fileurl,String shortfileurl,String[] title,String TITLE, List<Map<String, Object>> listContent) {  
			String result="系统提示：Excel文件导出成功！"; 
			boolean flag=true;
			//以下开始输出到EXCEL  
			try {      
			   /** **********创建工作簿************ */  
				WritableWorkbook workbook = Workbook.createWorkbook(new File(fileurl));
			   /** **********创建工作表************ */  
			   WritableSheet sheet = workbook.createSheet("成绩导入模板",0);
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
			   sheet.mergeCells(0, 0, title.length-1, 0); 
			   sheet.setRowView(0, 650, false); //设置行高
			   sheet.setRowView(1, 400, false); 
			   sheet.addCell(new Label(0, 0, TITLE, title_style));  
			   /** ***************以下是EXCEL第一行列标题********************* */  
			   for (int i = 0; i < title.length; i++) {  
				   sheet.addCell(new Label(i, 1,title[i],wcf_center));  
			   } 
			   /*****************以下是EXCEL正文数据**********************/ 
			   /**********数据加载*************/
			   //导出合并功能暂时无法实现
			   int i = 2;
//			   int len2 = 2;
//			   int sum=2;
			   for (Map<String, Object> drow : listContent) {
//				   int len= ((List)drow.get("data")).size();
				    sheet.setRowView(i, 300, false); 
				    
//				    sheet.mergeCells(0,len2,0,sum);
					sheet.addCell(new Label(0, i, Validate.isNullToDefaultString(i-1, ""), wcf_left));
					
//					sheet.mergeCells(1,len2,1,sum);
					sheet.addCell(new Label(1, i, Validate.isNullToDefaultString(drow.get("WLBH"), ""), wcf_left));
					
//					sheet.mergeCells(2,len2,2,sum);
					sheet.addCell(new Label(2, i, Validate.isNullToDefaultString(drow.get("DWMC"), ""), wcf_left));
					
//					sheet.mergeCells(3,len2,3,sum);
					sheet.addCell(new Label(3, i, Validate.isNullToDefaultString(drow.get("DWJC"), ""), wcf_left));
					
//					sheet.mergeCells(4,len2,4,sum);
					sheet.addCell(new Label(4, i, Validate.isNullToDefaultString(drow.get("DWLX"), ""), wcf_left));
					
//					sheet.mergeCells(5,len2,5,sum);
					sheet.addCell(new Label(5, i, Validate.isNullToDefaultString(drow.get("DWDZ"), ""), wcf_left));
					
//					sheet.mergeCells(6,len2,6,sum);
					sheet.addCell(new Label(6, i, Validate.isNullToDefaultString(drow.get("LXR"), ""), wcf_left));
					
//					sheet.mergeCells(7,len2,7,sum);
					sheet.addCell(new Label(7, i, Validate.isNullToDefaultString(drow.get("SH"), ""), wcf_left));
					
//					sheet.mergeCells(8,len2,8,sum);
//					sheet.addCell(new Label(8, i, Validate.isNullToDefaultString(drow.get("BGDH"), ""), wcf_left));
					
//					sheet.mergeCells(9,len2,9,sum);
					sheet.addCell(new Label(9, i, Validate.isNullToDefaultString(drow.get("CZ"), ""), wcf_left));
//					sheet.addCell(new Label(10, i, Validate.isNullToDefaultString(drow.get("KHYH"), ""), wcf_left));
//					sheet.addCell(new Label(11, i, Validate.isNullToDefaultString(drow.get("KHYHZH"), ""), wcf_left));
					
//					for (int j = 0; j <((List)drow.get("data")).size(); j++) {
//						sheet.addCell(new Label(10, i, Validate.isNullToDefaultString( ((Map)(((List)drow.get("data")).get(j))).get("KHYH"), ""), wcf_left));
//						sheet.addCell(new Label(11, i, Validate.isNullToDefaultString( ((Map)(((List)drow.get("data")).get(j))).get("KHYHZH"), ""), wcf_left));
//					}
					i++;
//					len2 = len2=len;
//					sum = len+len2;
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
