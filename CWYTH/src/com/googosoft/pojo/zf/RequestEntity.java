package com.googosoft.pojo.zf;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;


/**
*@author 杨超超
*@date   2018年2月9日---下午1:49:25
*/
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
        "header",
        "body"
    })
@XmlRootElement(name = "soapenv:Envelope")
public class RequestEntity {
	
	@XmlAttribute(name="xmlns:soapenv")
    protected String soapenv="http://schemas.xmlsoap.org/soap/envelope/";
    @XmlAttribute(name="xmlns:eapp")
    protected String eapp="http://wx.gmw9.com";
    
    @XmlAttribute(name="xmlns:bus")
    protected String bus=null;
    
    
    
    @XmlElement(required = true,name="soapenv:Header")
    protected RequestHeader header;
    
    @XmlElement(required = true,name="soapenv:Body")
    protected RequestBody body;

    
    //get set方法省略
}
