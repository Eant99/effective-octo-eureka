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

import com.googosoft.util.Validate;
/**
 * 在职人员薪资导出
 * @author Administrator
 *
 */
public class ZzryExportExcel {

	 public static Map<String, Object> exportExcel(String fileurl,String shortfileurl,String[] title,String TITLE, List<Map<String, Object>> listContent,String[] newText) {  
			String result="系统提示：Excel文件导出成功！"; 
			boolean flag=true;
			//以下开始输出到EXCEL  
			try {      
			   /** **********创建工作簿************ */  
				WritableWorkbook workbook = Workbook.createWorkbook(new File(fileurl));
			   /** **********创建工作表************ */  
			   WritableSheet sheet = workbook.createSheet("薪资",0);
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
				   sheet.addCell(new Label(i, 2,title[i],wcf_center)); 
				   sheet.addCell(new Label(0, 1,"",wcf_center));
				   if((i+1)<title.length){
					   sheet.addCell(new Label(i+1, 1, (newText[i]+"").toLowerCase(), wcf_center));
				   }
			   } 
			   /*****************以下是EXCEL正文数据**********************/ 
			   /**********学生数据加载*************/
			   int i = 3;
			   for (Map<String, Object> drow : listContent) {
				    sheet.setRowView(i, 300, false); 
					sheet.addCell(new Label(0, i, Validate.isNullToDefaultString(i-2, ""), wcf_left));
					for(int a=0;a<newText.length;a++){
//						System.err.println((a+1)+"----------------"+(newText[a]+"").toUpperCase());
						sheet.addCell(new Label(a+1, i, Validate.isNullToDefaultString(drow.get((newText[a]+"").toUpperCase()), ""), wcf_left));
					}
//					sheet.addCell(new Label(1, i, Validate.isNullToDefaultString(drow.get("RYBH"), ""), wcf_left));
//					sheet.addCell(new Label(2, i, Validate.isNullToDefaultString(drow.get("XM"), ""), wcf_left));
//					sheet.addCell(new Label(3, i, Validate.isNullToDefaultString(drow.get("XL"), ""), wcf_left));
//					sheet.addCell(new Label(4, i, Validate.isNullToDefaultString(drow.get("BM"), ""), wcf_left));
//					sheet.addCell(new Label(5, i, Validate.isNullToDefaultString(drow.get("RYLB"), ""), wcf_left));
//					sheet.addCell(new Label(6, i, Validate.isNullToDefaultString(drow.get("RYLX"), ""), wcf_left));
//					sheet.addCell(new Label(7, i, Validate.isNullToDefaultString(drow.get("GWGZ"), ""), wcf_left));
//					sheet.addCell(new Label(8, i, Validate.isNullToDefaultString(drow.get("XJGZ"), ""), wcf_left));
//					sheet.addCell(new Label(9, i, Validate.isNullToDefaultString(drow.get("XZFT"), ""), wcf_left));
//					sheet.addCell(new Label(10, i, Validate.isNullToDefaultString(drow.get("WYBT"), ""), wcf_left));
//					sheet.addCell(new Label(11, i, Validate.isNullToDefaultString(drow.get("DSZF"), ""), wcf_left));
//					sheet.addCell(new Label(12, i, Validate.isNullToDefaultString(drow.get("JCJX"), ""), wcf_left));
//					sheet.addCell(new Label(13, i, Validate.isNullToDefaultString(drow.get("JLJX1"), ""), wcf_left));
//					sheet.addCell(new Label(14, i, Validate.isNullToDefaultString(drow.get("BSBT"), ""), wcf_left));
//					sheet.addCell(new Label(15, i, Validate.isNullToDefaultString(drow.get("GWBT"), ""), wcf_left));
//					sheet.addCell(new Label(16, i, Validate.isNullToDefaultString(drow.get("BXQZBBT"), ""), wcf_left));
//					sheet.addCell(new Label(17, i, Validate.isNullToDefaultString(drow.get("SYBT"), ""), wcf_left));
//					sheet.addCell(new Label(18, i, Validate.isNullToDefaultString(drow.get("JHBT"), ""), wcf_left));
//					sheet.addCell(new Label(19, i, Validate.isNullToDefaultString(drow.get("ZJBT"), ""), wcf_left));
//					sheet.addCell(new Label(20, i, Validate.isNullToDefaultString(drow.get("HTBT"), ""), wcf_left));
//					sheet.addCell(new Label(21, i, Validate.isNullToDefaultString(drow.get("QTBT"), ""), wcf_left));
//					sheet.addCell(new Label(22, i, Validate.isNullToDefaultString(drow.get("BGZ"), ""), wcf_left));
//					sheet.addCell(new Label(23, i, Validate.isNullToDefaultString(drow.get("JKF"), ""), wcf_left));
//					sheet.addCell(new Label(24, i, Validate.isNullToDefaultString(drow.get("FZGZL"), ""), wcf_left));
//					sheet.addCell(new Label(25, i, Validate.isNullToDefaultString(drow.get("ZSJL"), ""), wcf_left));
//					sheet.addCell(new Label(26, i, Validate.isNullToDefaultString(drow.get("FDYYJZBBT"), ""), wcf_left));
//					sheet.addCell(new Label(27, i, Validate.isNullToDefaultString(drow.get("KHJ"), ""), wcf_left));
//					sheet.addCell(new Label(28, i, Validate.isNullToDefaultString(drow.get("DHF"), ""), wcf_left));
//					sheet.addCell(new Label(29, i, Validate.isNullToDefaultString(drow.get("BT"), ""), wcf_left));
//					sheet.addCell(new Label(30, i, Validate.isNullToDefaultString(drow.get("ZFBT"), ""), wcf_left));
//					sheet.addCell(new Label(31, i, Validate.isNullToDefaultString(drow.get("YFHJ"), ""), wcf_left));
//					sheet.addCell(new Label(32, i, Validate.isNullToDefaultString(drow.get("JIANGKEF"), ""), wcf_left));
//					sheet.addCell(new Label(33, i, Validate.isNullToDefaultString(drow.get("BGZKS"), ""), wcf_left));
//					sheet.addCell(new Label(34, i, Validate.isNullToDefaultString(drow.get("JSX"), ""), wcf_left));
//					sheet.addCell(new Label(35, i, Validate.isNullToDefaultString(drow.get("QNJSX"), ""), wcf_left));
//					sheet.addCell(new Label(36, i, Validate.isNullToDefaultString(drow.get("QNJSX1"), ""), wcf_left));
//					sheet.addCell(new Label(37, i, Validate.isNullToDefaultString(drow.get("QNJSX2"), ""), wcf_left));
//					sheet.addCell(new Label(38, i, Validate.isNullToDefaultString(drow.get("QNJSX3"), ""), wcf_left));
//					sheet.addCell(new Label(39, i, Validate.isNullToDefaultString(drow.get("BJCXJXGZ2014JSJS"), ""), wcf_left));
//					sheet.addCell(new Label(40, i, Validate.isNullToDefaultString(drow.get("BXBT2014N1D10YJSJS"), ""), wcf_left));
//					sheet.addCell(new Label(41, i, Validate.isNullToDefaultString(drow.get("JSJS"), ""), wcf_left));
//					sheet.addCell(new Label(42, i, Validate.isNullToDefaultString(drow.get("ZFJJ"), ""), wcf_left));
//					sheet.addCell(new Label(43, i, Validate.isNullToDefaultString(drow.get("PGJJ"), ""), wcf_left));
//					sheet.addCell(new Label(44, i, Validate.isNullToDefaultString(drow.get("YLBX"), ""), wcf_left));
//					sheet.addCell(new Label(45, i, Validate.isNullToDefaultString(drow.get("BGJJ"), ""), wcf_left));
//					sheet.addCell(new Label(46, i, Validate.isNullToDefaultString(drow.get("DKS"), ""), wcf_left));
//					sheet.addCell(new Label(47, i, Validate.isNullToDefaultString(drow.get("BNSE"), ""), wcf_left));
//					sheet.addCell(new Label(48, i, Validate.isNullToDefaultString(drow.get("SNSE"), ""), wcf_left));
//					sheet.addCell(new Label(49, i, Validate.isNullToDefaultString(drow.get("BS"), ""), wcf_left));
//					sheet.addCell(new Label(50, i, Validate.isNullToDefaultString(drow.get("FZ"), ""), wcf_left));
//					sheet.addCell(new Label(51, i, Validate.isNullToDefaultString(drow.get("SYJ"), ""), wcf_left));
//					sheet.addCell(new Label(52, i, Validate.isNullToDefaultString(drow.get("NQF"), ""), wcf_left));
//					sheet.addCell(new Label(53, i, Validate.isNullToDefaultString(drow.get("NQF1"), ""), wcf_left));
//					sheet.addCell(new Label(54, i, Validate.isNullToDefaultString(drow.get("WYF"), ""), wcf_left));
//					sheet.addCell(new Label(55, i, Validate.isNullToDefaultString(drow.get("SBJ"), ""), wcf_left));
//					sheet.addCell(new Label(56, i, Validate.isNullToDefaultString(drow.get("SJDCK"), ""), wcf_left));
//					sheet.addCell(new Label(57, i, Validate.isNullToDefaultString(drow.get("KK"), ""), wcf_left));
//					sheet.addCell(new Label(58, i, Validate.isNullToDefaultString(drow.get("YLJ"), ""), wcf_left));
//					sheet.addCell(new Label(59, i, Validate.isNullToDefaultString(drow.get("JK"), ""), wcf_left));
//					sheet.addCell(new Label(60, i, Validate.isNullToDefaultString(drow.get("AXYRJ"), ""), wcf_left));
//					sheet.addCell(new Label(61, i, Validate.isNullToDefaultString(drow.get("KKHJ"), ""), wcf_left));
//					sheet.addCell(new Label(62, i, Validate.isNullToDefaultString(drow.get("SFHJ"), ""), wcf_left));
//					sheet.addCell(new Label(63, i, Validate.isNullToDefaultString(drow.get("GZYF"), ""), wcf_left));
//					sheet.addCell(new Label(64, i, Validate.isNullToDefaultString(drow.get("BH"), ""), wcf_left));
//					sheet.addCell(new Label(65, i, Validate.isNullToDefaultString(drow.get("XH"), ""), wcf_left));
//					sheet.addCell(new Label(66, i, Validate.isNullToDefaultString(drow.get("JTF"), ""), wcf_left));
//					sheet.addCell(new Label(67, i, Validate.isNullToDefaultString(drow.get("NZJ"), ""), wcf_left));
//					sheet.addCell(new Label(68, i, Validate.isNullToDefaultString(drow.get("NZJDKS"), ""), wcf_left));
//					sheet.addCell(new Label(69, i, Validate.isNullToDefaultString(drow.get("GZDKS"), ""), wcf_left));
//					sheet.addCell(new Label(70, i, Validate.isNullToDefaultString(drow.get("KSHJ"), ""), wcf_left));
//					sheet.addCell(new Label(71, i, Validate.isNullToDefaultString(drow.get("GH"), ""), wcf_left));
//					sheet.addCell(new Label(72, i, Validate.isNullToDefaultString(drow.get("SFZB"), ""), wcf_left));
//					sheet.addCell(new Label(73, i, Validate.isNullToDefaultString(drow.get("BKYLBX"), ""), wcf_left));
//					sheet.addCell(new Label(74, i, Validate.isNullToDefaultString(drow.get("BKSYJ"), ""), wcf_left));
//					sheet.addCell(new Label(75, i, Validate.isNullToDefaultString(drow.get("BKYLJ"), ""), wcf_left));
//					sheet.addCell(new Label(76, i, Validate.isNullToDefaultString(drow.get("BKSBJ"), ""), wcf_left));
//					sheet.addCell(new Label(77, i, Validate.isNullToDefaultString(drow.get("SFDY"), ""), wcf_left));
//					sheet.addCell(new Label(78, i, Validate.isNullToDefaultString(drow.get("RDQK"), ""), wcf_left));
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
	 
/**
 * 
 * @author  wangguanghua
 * @version  2018-9-14上午9:33:18
 * 下载模板
 */
	 public static Map<String, Object> exportExcelnew(String fileurl,String shortfileurl,String[] title,String TITLE, List<Map<String, Object>> listContent,String[] newText) {  
			String result="系统提示：Excel文件导出成功！"; 
			boolean flag=true;
			//以下开始输出到EXCEL  
			try {      
			   /** **********创建工作簿************ */  
				WritableWorkbook workbook = Workbook.createWorkbook(new File(fileurl));
			   /** **********创建工作表************ */  
			   WritableSheet sheet = workbook.createSheet("薪资",0);
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
			  
			   /** ***************以下是EXCEL第一行列标题********************* */  
			   for (int i = 0; i < title.length; i++) {  
				   sheet.addCell(new Label(i, 1,title[i],wcf_center)); 
				  
					   sheet.addCell(new Label(i, 0, (newText[i]+"").toLowerCase(), wcf_center));
				  
			   } 
			   /*****************以下是EXCEL正文数据**********************/ 
			   /**********学生数据加载*************/
			   int i = 2;
			   for (Map<String, Object> drow : listContent) {
				    sheet.setRowView(i, 300, false); 
					for(int a=0;a<newText.length;a++){
//						System.err.println((a+1)+"----------------"+(newText[a]+"").toUpperCase());
						sheet.addCell(new Label(a, i, Validate.isNullToDefaultString(drow.get((newText[a]+"").toUpperCase()), ""), wcf_left));
					}
//					sheet.addCell(new Label(1, i, Validate.isNullToDefaultString(drow.get("RYBH"), ""), wcf_left));
//					sheet.addCell(new Label(2, i, Validate.isNullToDefaultString(drow.get("XM"), ""), wcf_left));
//					sheet.addCell(new Label(3, i, Validate.isNullToDefaultString(drow.get("XL"), ""), wcf_left));
//					sheet.addCell(new Label(4, i, Validate.isNullToDefaultString(drow.get("BM"), ""), wcf_left));
//					sheet.addCell(new Label(5, i, Validate.isNullToDefaultString(drow.get("RYLB"), ""), wcf_left));
//					sheet.addCell(new Label(6, i, Validate.isNullToDefaultString(drow.get("RYLX"), ""), wcf_left));
//					sheet.addCell(new Label(7, i, Validate.isNullToDefaultString(drow.get("GWGZ"), ""), wcf_left));
//					sheet.addCell(new Label(8, i, Validate.isNullToDefaultString(drow.get("XJGZ"), ""), wcf_left));
//					sheet.addCell(new Label(9, i, Validate.isNullToDefaultString(drow.get("XZFT"), ""), wcf_left));
//					sheet.addCell(new Label(10, i, Validate.isNullToDefaultString(drow.get("WYBT"), ""), wcf_left));
//					sheet.addCell(new Label(11, i, Validate.isNullToDefaultString(drow.get("DSZF"), ""), wcf_left));
//					sheet.addCell(new Label(12, i, Validate.isNullToDefaultString(drow.get("JCJX"), ""), wcf_left));
//					sheet.addCell(new Label(13, i, Validate.isNullToDefaultString(drow.get("JLJX1"), ""), wcf_left));
//					sheet.addCell(new Label(14, i, Validate.isNullToDefaultString(drow.get("BSBT"), ""), wcf_left));
//					sheet.addCell(new Label(15, i, Validate.isNullToDefaultString(drow.get("GWBT"), ""), wcf_left));
//					sheet.addCell(new Label(16, i, Validate.isNullToDefaultString(drow.get("BXQZBBT"), ""), wcf_left));
//					sheet.addCell(new Label(17, i, Validate.isNullToDefaultString(drow.get("SYBT"), ""), wcf_left));
//					sheet.addCell(new Label(18, i, Validate.isNullToDefaultString(drow.get("JHBT"), ""), wcf_left));
//					sheet.addCell(new Label(19, i, Validate.isNullToDefaultString(drow.get("ZJBT"), ""), wcf_left));
//					sheet.addCell(new Label(20, i, Validate.isNullToDefaultString(drow.get("HTBT"), ""), wcf_left));
//					sheet.addCell(new Label(21, i, Validate.isNullToDefaultString(drow.get("QTBT"), ""), wcf_left));
//					sheet.addCell(new Label(22, i, Validate.isNullToDefaultString(drow.get("BGZ"), ""), wcf_left));
//					sheet.addCell(new Label(23, i, Validate.isNullToDefaultString(drow.get("JKF"), ""), wcf_left));
//					sheet.addCell(new Label(24, i, Validate.isNullToDefaultString(drow.get("FZGZL"), ""), wcf_left));
//					sheet.addCell(new Label(25, i, Validate.isNullToDefaultString(drow.get("ZSJL"), ""), wcf_left));
//					sheet.addCell(new Label(26, i, Validate.isNullToDefaultString(drow.get("FDYYJZBBT"), ""), wcf_left));
//					sheet.addCell(new Label(27, i, Validate.isNullToDefaultString(drow.get("KHJ"), ""), wcf_left));
//					sheet.addCell(new Label(28, i, Validate.isNullToDefaultString(drow.get("DHF"), ""), wcf_left));
//					sheet.addCell(new Label(29, i, Validate.isNullToDefaultString(drow.get("BT"), ""), wcf_left));
//					sheet.addCell(new Label(30, i, Validate.isNullToDefaultString(drow.get("ZFBT"), ""), wcf_left));
//					sheet.addCell(new Label(31, i, Validate.isNullToDefaultString(drow.get("YFHJ"), ""), wcf_left));
//					sheet.addCell(new Label(32, i, Validate.isNullToDefaultString(drow.get("JIANGKEF"), ""), wcf_left));
//					sheet.addCell(new Label(33, i, Validate.isNullToDefaultString(drow.get("BGZKS"), ""), wcf_left));
//					sheet.addCell(new Label(34, i, Validate.isNullToDefaultString(drow.get("JSX"), ""), wcf_left));
//					sheet.addCell(new Label(35, i, Validate.isNullToDefaultString(drow.get("QNJSX"), ""), wcf_left));
//					sheet.addCell(new Label(36, i, Validate.isNullToDefaultString(drow.get("QNJSX1"), ""), wcf_left));
//					sheet.addCell(new Label(37, i, Validate.isNullToDefaultString(drow.get("QNJSX2"), ""), wcf_left));
//					sheet.addCell(new Label(38, i, Validate.isNullToDefaultString(drow.get("QNJSX3"), ""), wcf_left));
//					sheet.addCell(new Label(39, i, Validate.isNullToDefaultString(drow.get("BJCXJXGZ2014JSJS"), ""), wcf_left));
//					sheet.addCell(new Label(40, i, Validate.isNullToDefaultString(drow.get("BXBT2014N1D10YJSJS"), ""), wcf_left));
//					sheet.addCell(new Label(41, i, Validate.isNullToDefaultString(drow.get("JSJS"), ""), wcf_left));
//					sheet.addCell(new Label(42, i, Validate.isNullToDefaultString(drow.get("ZFJJ"), ""), wcf_left));
//					sheet.addCell(new Label(43, i, Validate.isNullToDefaultString(drow.get("PGJJ"), ""), wcf_left));
//					sheet.addCell(new Label(44, i, Validate.isNullToDefaultString(drow.get("YLBX"), ""), wcf_left));
//					sheet.addCell(new Label(45, i, Validate.isNullToDefaultString(drow.get("BGJJ"), ""), wcf_left));
//					sheet.addCell(new Label(46, i, Validate.isNullToDefaultString(drow.get("DKS"), ""), wcf_left));
//					sheet.addCell(new Label(47, i, Validate.isNullToDefaultString(drow.get("BNSE"), ""), wcf_left));
//					sheet.addCell(new Label(48, i, Validate.isNullToDefaultString(drow.get("SNSE"), ""), wcf_left));
//					sheet.addCell(new Label(49, i, Validate.isNullToDefaultString(drow.get("BS"), ""), wcf_left));
//					sheet.addCell(new Label(50, i, Validate.isNullToDefaultString(drow.get("FZ"), ""), wcf_left));
//					sheet.addCell(new Label(51, i, Validate.isNullToDefaultString(drow.get("SYJ"), ""), wcf_left));
//					sheet.addCell(new Label(52, i, Validate.isNullToDefaultString(drow.get("NQF"), ""), wcf_left));
//					sheet.addCell(new Label(53, i, Validate.isNullToDefaultString(drow.get("NQF1"), ""), wcf_left));
//					sheet.addCell(new Label(54, i, Validate.isNullToDefaultString(drow.get("WYF"), ""), wcf_left));
//					sheet.addCell(new Label(55, i, Validate.isNullToDefaultString(drow.get("SBJ"), ""), wcf_left));
//					sheet.addCell(new Label(56, i, Validate.isNullToDefaultString(drow.get("SJDCK"), ""), wcf_left));
//					sheet.addCell(new Label(57, i, Validate.isNullToDefaultString(drow.get("KK"), ""), wcf_left));
//					sheet.addCell(new Label(58, i, Validate.isNullToDefaultString(drow.get("YLJ"), ""), wcf_left));
//					sheet.addCell(new Label(59, i, Validate.isNullToDefaultString(drow.get("JK"), ""), wcf_left));
//					sheet.addCell(new Label(60, i, Validate.isNullToDefaultString(drow.get("AXYRJ"), ""), wcf_left));
//					sheet.addCell(new Label(61, i, Validate.isNullToDefaultString(drow.get("KKHJ"), ""), wcf_left));
//					sheet.addCell(new Label(62, i, Validate.isNullToDefaultString(drow.get("SFHJ"), ""), wcf_left));
//					sheet.addCell(new Label(63, i, Validate.isNullToDefaultString(drow.get("GZYF"), ""), wcf_left));
//					sheet.addCell(new Label(64, i, Validate.isNullToDefaultString(drow.get("BH"), ""), wcf_left));
//					sheet.addCell(new Label(65, i, Validate.isNullToDefaultString(drow.get("XH"), ""), wcf_left));
//					sheet.addCell(new Label(66, i, Validate.isNullToDefaultString(drow.get("JTF"), ""), wcf_left));
//					sheet.addCell(new Label(67, i, Validate.isNullToDefaultString(drow.get("NZJ"), ""), wcf_left));
//					sheet.addCell(new Label(68, i, Validate.isNullToDefaultString(drow.get("NZJDKS"), ""), wcf_left));
//					sheet.addCell(new Label(69, i, Validate.isNullToDefaultString(drow.get("GZDKS"), ""), wcf_left));
//					sheet.addCell(new Label(70, i, Validate.isNullToDefaultString(drow.get("KSHJ"), ""), wcf_left));
//					sheet.addCell(new Label(71, i, Validate.isNullToDefaultString(drow.get("GH"), ""), wcf_left));
//					sheet.addCell(new Label(72, i, Validate.isNullToDefaultString(drow.get("SFZB"), ""), wcf_left));
//					sheet.addCell(new Label(73, i, Validate.isNullToDefaultString(drow.get("BKYLBX"), ""), wcf_left));
//					sheet.addCell(new Label(74, i, Validate.isNullToDefaultString(drow.get("BKSYJ"), ""), wcf_left));
//					sheet.addCell(new Label(75, i, Validate.isNullToDefaultString(drow.get("BKYLJ"), ""), wcf_left));
//					sheet.addCell(new Label(76, i, Validate.isNullToDefaultString(drow.get("BKSBJ"), ""), wcf_left));
//					sheet.addCell(new Label(77, i, Validate.isNullToDefaultString(drow.get("SFDY"), ""), wcf_left));
//					sheet.addCell(new Label(78, i, Validate.isNullToDefaultString(drow.get("RDQK"), ""), wcf_left));
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
