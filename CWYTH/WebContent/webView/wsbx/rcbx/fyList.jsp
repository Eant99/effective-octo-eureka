<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@page import="com.googosoft.constant.Constant"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0"/>
<title>费用信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
	body{
		overflow-x:hidden;
	}
	tr td:first-child,th{
		text-align:center;
	}
</style>
<body class="contrast-red" >
<div id='wrapper'>
<section>
    <div class='row' id='content-wrapper'>
        <div class='col-md-12'>
             <div class="box">
                 <div class='box-content' style="padding-bottom: 0; overflow:visible;">
                 	<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的信息，然后<strong>双击</strong>这条信息;
			        </div>
			        <hr class="hr-normal" id="hr">
                     <div class='responsive-table'>
                     <div class='scrollable-area'>
                     <table id="mydatatables" class="table table-striped table-bordered" style="margin-bottom:0;width:100%;">
					     <thead>
					     <tr id="header">
					          <th><input type="checkbox" value="" class="select-all"/></th>
					          <th>费用科目编号</th>
					          <th>费用名称</th>
					     </tr>
					     </thead>
					     <tbody>
					     	<c:forEach items="${list}" var="list">
					     	<tr>
					          <td><input type="checkbox" value="" class="select-all"/>
					          <input type="hidden" name="fy" value="${list.fymc}" guid="${list.guid}"/></td>
					          <td>${list.kmbh}</td>
					          <td>${list.fymc}</td>
					       </tr>
					       </c:forEach>
					     </tbody>
				     </table>
                     </div>
                     </div>
                  </div>
              </div>
         </div>
      </div>
</section>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function (){
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	var val = $(this).find("[name='fy']").val();
    	var guid =  $(this).find("[name='fy']").attr("guid");
    	console.log(val);
    	console.log(guid);
    	if(val==''||val==null||val=='undefined'){
    		alert("没有可以选择的数据！");
    	}else{
    		getIframeControl("${param.pname}","${param.controlId}").val(val);
//     		getIframeControl("${param.pname}","${param.controlIds}").val(guid);
        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
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