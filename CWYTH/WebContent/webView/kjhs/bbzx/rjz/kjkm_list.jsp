<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
	tr td:first-child,th{
		text-align:center;
	}
	.dataTables_scrollBody{
		overflow:none!important;
	}
</style>
<body class="contrast-red" >
<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>序号</th>
					          <th>科目编号</th>
					          <th>科目名称</th>
					     </tr>
					     </thead>
					     <tbody>
					     </tbody>
				     </table>
                     </div>
                     </div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var columns = [
	       		{"data": "guid",orderable:false, "render": function (data, type, full, meta){
	       	       	return '<input type="checkbox" class="keyId" name="guid"  kmxx="'+full.KMXX+'" >';
	       	    },"width":10,'searchable': false},
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},
	       	   	{"data": "KMBH",defaultContent:""},
	       	   	{"data": "KMMC",defaultContent:""},
	       	];
	table = getDataTableByListHj("mydatatables","${ctx}/zjrbb/getkmxxPageList",[2,'asc'],columns,0,1,setGroup);
	var winId = getTopFrame().layer.getFrameIndex(window.parent.name);
	//双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='guid']").attr("kmxx");
    	var vamc = $(this).find("[name='guid']").attr("kmmc");
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
        	//getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	//getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
        	close(winId);
    	}
    });
	//取消
   	$("#btn_cancel").on("click",function(){
   		close(winId);
   	});
});
</script>
</body>
</html>