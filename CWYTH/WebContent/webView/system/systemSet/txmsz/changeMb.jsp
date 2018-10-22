<%@page import="com.googosoft.util.Validate"%>
<%@page import="com.googosoft.service.xtsz.txmsz.TxmService"%>
<%@page import="java.util.Map"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
String designno=request.getParameter("DESIGNNO");
TxmService txmService = new TxmService();
// Map map=txmService.getObjectByid(designno);
StringBuffer str=new StringBuffer();
// str.append(Validate.isNullToDefault(map.get("labelwidth"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("labelheight"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("labelacross"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("labelrows"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("betweencolumn"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("betweenrow"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("designname"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("tablename"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("papersize"), ""));
// str.append(",");
// str.append(Validate.isNullToDefault(map.get("isselect"), "0"));
out.print(str.toString());
%>