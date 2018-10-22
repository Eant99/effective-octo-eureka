package com.googosoft.controller.kjhs.pzxx;

import java.io.FileOutputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
*@author 杨超超
*@date   2018年2月9日---下午3:48:38
*/
public class CreateNewDom {
	  public static void createDom(){
	        Document doc;
	        Element school,student;
	        Element name = null;
	        Element num = null;
	        Element classes = null;
	        Element address = null;
	        Element tel = null;
	        try{
	            //得到DOM解析器的工厂实例
	            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
	            //从DOM工厂中获得DOM解析器
	            DocumentBuilder dbBuilder = dbFactory.newDocumentBuilder();
	            //创建文档树模型对象
	            doc = dbBuilder.newDocument();
	            if(doc != null){
	                //创建school元素
	                school = doc.createElement("School");
	                //创建student元素
	                student = doc.createElement("Student");
	                //设置元素Student的属性值为231
	                student.setAttribute("examId", "23");
	                //创建名称为Name的元素
	                name = doc.createElement("Name");
	                //创建名称为 香香 的文本节点并作为子节点添加到name元素中
	                name.appendChild(doc.createTextNode("香香"));
	                //将name子元素添加到student中
	                student.appendChild(name);
	                /**
	                 * 下面的元素依次加入即可
	                 * */
	                num = doc.createElement("Num");
	                num.appendChild(doc.createTextNode("1006010066"));
	                student.appendChild(num);
	                
	                classes = doc.createElement("Classes");
	                classes.appendChild(doc.createTextNode("眼视光5"));
	                student.appendChild(classes);
	                
	                address = doc.createElement("Address");
	                address.appendChild(doc.createTextNode("浙江温州"));
	                student.appendChild(address);
	                
	                tel = doc.createElement("Tel");
	                tel.appendChild(doc.createTextNode("123890"));
	                student.appendChild(tel);
	                
	                //将student作为子元素添加到树的根节点school
	                school.appendChild(student);
	                //添加到文档树中
	                doc.appendChild(school);
	                //将内存中的文档通过文件流生成insertSchool.xml,XmlDocument位于crison.jar下
//	                ((XmlDocument)doc).write(new FileOutputStream("src/com/googosoft/dao/createSchool.xml"));
	                System.out.println("创建成功");
	            }
	        }catch (Exception e) {
	            // TODO: handle exception
	            e.printStackTrace();
	        }
	    }
	    public static void main(String[] args) {
	        CreateNewDom.createDom();
	    }
}
