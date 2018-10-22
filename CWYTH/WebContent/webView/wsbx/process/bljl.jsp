<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>办理记录</title>
<%-- <link href="${pageContext.request.contextPath}/static/css/systemSet/seachBody_wev8.css" rel="stylesheet" /> --%>
<link href="${pageContext.request.contextPath}/static/plugins/datatable/css/dataTables.bootstrap.css" rel="stylesheet" type="text/css" />
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.leftfont {
	border-bottom: 1px solid;
	border-color: #F3F2F2;
	height: 30px;}

.box-panel {
	border-left: 0px !important;}

.btn-file {
	left: 34px;
	top: 10px;}

.xtb {
	font-size: 23px;
	color: #fff !important;
	}

a:hover,a:focus {
	color: #CCCCCC;}

.dw {
	height: 20px;
	line-height: 38px;
	margin-left: 5px;
	font-size: 12px;
	font-weight: normal;
	margin-top: 5px;
}
.tab_box{
	margin-top: 40px;
}
#tit {
	line-height: 39px !important;
}
.kssj,.jssj{
	margin-bottom: 15px;
    position: relative;
	display:inline-table;
	vertical-align:middle
	display: table;
    border-collapse: separate;
	}
</style>
</head>
<body  >
	
			<table class="table table-striped table-bordered dataTable no-footer" style="width: 100%; height: 100%;">
			 <thead>
				    <tr>
				          <th>序号</th>
				          <th>任务类型</th>
				          <th>办理人</th>
				          <th>开始时间</th>
				          <th>结束时间</th>
				          <th>审核状态</th>
				          <th>办理结果</th>
				    </tr>
				    </thead>
	     <tbody>
	     <c:forEach var="bljl" items="${bljl}" varStatus="gid">
	     <tr>
	     <td>${gid.index+1}</td>
	     <td>${bljl.taskname }</td>
	     <td>${bljl.xm}</td>
	      <td>${bljl.starttime}</td>
	      <td>${bljl.endtime}</td>
	      <td>${bljl.shzt}</td>
	      <td>${bljl.shyj}</td>
	     </tr>
	     </c:forEach>
	     </tbody>			    
	
</table>
	
<%@include file="/static/include/public-list-js.inc"%>
<script type="text/javascript">


</script>

	
	
		
</body>
</html>