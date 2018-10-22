package com.googosoft.controller.cwbb.expExcel;

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
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.googosoft.util.Validate;
/**
 * 除了明细账之外的各种类型明细账
 * @author wangchengxin
 * 
 */
public class QtmxzExportExcel {
	 public static Map<String, Object> exportExcel(String fileurl,String shortfileurl,String[] title,String TITLE, List<Map<String, Object>> listContent) {  
			String result="系统提示：Excel文件导出成功！"; 
			String file = fileurl.substring(0, fileurl.lastIndexOf("\\"));
			boolean flag=true;
			File folder = new File(file); 
			if (!folder.exists() && !folder.isDirectory()) {  
		        System.out.println("//不存在");  
		        folder.mkdirs();  
		    }  
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
			   NumberFormat doubleFormat=new NumberFormat("#,##0.00");//设置为数值类型。带千位符的数值类型。
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
			   WritableCellFormat wcf_center = new WritableCellFormat(BoldFont,doubleFormat);  
			   wcf_center.setBorder(Border.ALL, BorderLineStyle.THIN); // 线条  
			   wcf_center.setVerticalAlignment(VerticalAlignment.CENTRE); // 文字垂直对齐  
			   wcf_center.setAlignment(Alignment.CENTRE); // 文字水平对齐  
			   wcf_center.setWrap(false); // 文字是否换行  
			   wcf_center.setBackground(Colour.GRAY_25);
			   // 用于正文居左  
			   WritableCellFormat wcf_left = new WritableCellFormat(NormalFont,doubleFormat);  
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
			   /**********学生数据加载*************/
			   int i = 2;
			   for (Map<String, Object> drow : listContent) {
				    String jfje = drow.get("JFJE")+"";
				    String dfje = drow.get("DFJE")+"";
				    String ye = drow.get("YE")+"";
				    sheet.setRowView(i, 300, false); 
					sheet.addCell(new Label(0, i, Validate.isNullToDefaultString(drow.get("PZRQ"), ""), wcf_left));
					sheet.addCell(new Label(1, i, Validate.isNullToDefaultString(drow.get("PZLXMC"), ""), wcf_left));
					sheet.addCell(new Label(2, i, Validate.isNullToDefaultString(drow.get("PZH"), ""), wcf_left));
					sheet.addCell(new Label(3, i, Validate.isNullToDefaultString(drow.get("ZY"), ""), wcf_left));
					sheet.addCell(new Label(4, i, Validate.isNullToDefaultString(drow.get("WLDWMC"), ""), wcf_left));
					sheet.addCell(new Label(5, i, Validate.isNullToDefaultString(drow.get("KMMC"), ""), wcf_left));
					sheet.addCell(new Label(6, i, Validate.isNullToDefaultString(drow.get("JJFL"), ""), wcf_left));
					sheet.addCell(new Label(7, i, Validate.isNullToDefaultString(drow.get("BMBH"), ""), wcf_left));
					sheet.addCell(new Label(8, i, Validate.isNullToDefaultString(drow.get("XMBH"), ""), wcf_left));
					if(Validate.noNull(jfje)) {
						double bd =	Double.parseDouble(jfje.replace(",", ""));
						jxl.write.Number labelNF = new jxl.write.Number(9,i,bd,wcf_left);
						sheet.addCell(labelNF);
					}else {
						sheet.addCell(new Label(9, i, "", wcf_left));
					}
					if(Validate.noNull(dfje)) {
						double bd =	Double.parseDouble(dfje.replace(",", ""));
						jxl.write.Number labelNF = new jxl.write.Number(10,i,bd,wcf_left);
						sheet.addCell(labelNF);
					}else {
						sheet.addCell(new Label(10, i, "", wcf_left));
					}
					sheet.addCell(new Label(11, i, Validate.isNullToDefaultString(drow.get("FX"), ""), wcf_left));
					if(Validate.noNull(ye)) {
						double bd =	Double.parseDouble(ye.replace(",", ""));
						sheet.addCell(new jxl.write.Number(12, i, bd, wcf_left));
					}else{
						sheet.addCell(new Label(12, i, "", wcf_left));
					}
					
					i++;
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
