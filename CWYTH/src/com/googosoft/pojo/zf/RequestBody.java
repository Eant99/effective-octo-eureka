package com.googosoft.pojo.zf;
import javax.servlet.jsp.tagext.BodyContent;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
/**
*@author 杨超超
*@date   2018年2月9日---下午1:50:10
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "soapenv:Body")
public class RequestBody {
	 @XmlElement(required = true,name="eapp:aaaaaa")
	    public BodyContent aaaaaaa;
	    //get set方法省略
}
