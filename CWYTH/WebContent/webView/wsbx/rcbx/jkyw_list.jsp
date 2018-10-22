<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>借款业务信息</title>
<%@include file="/static/include/public-list-css.inc"%> 
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	.search-simple .select2-container--default .select2-selection--single {
   		background-color: #fff;
   		border: 1px solid #ccc;
    	border-radius: 4px 4px 4px 4px;
	}
	th{
		text-align:center; 
	}
	.dataTables_scrollBody{
    height:450px !important;
 }
</style>
</head>
<body>
<div class="fullscreen">
<!--     <div class="search" id="searchBox"> -->
<!--     	<form id="myform" class="form-inline" action=""> -->
<!--     		<div class="search-simple"> -->
<!-- 				<div class="form-group"> -->
<!-- 					<label>项目号</label> -->
<!-- 					<input type="text" id="txt_xmmc" class="form-control" name="xmmc"  table="A" placeholder="请输入项目号"> -->
<!-- 				</div> -->
<!-- 				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button> -->
<!-- 				<div class="btn-group pull-right" role="group"> -->
<!-- 	               <button type="button" class="btn btn-default btn_select">选择</button> -->
<!-- 				</div> -->
<!-- 			</div> -->
<!--         </form> -->
<!--     </div> -->
	<div class="container-fluid">
		<div class="alert alert-info">
			<strong>提示：</strong>先找到需要的信息，然后双击鼠标添加这条信息
		</div>
        <div class='responsive-table'>
            <div class='scrollable-area' style="margin-top: 5px">
				<table id="mydatatables" class="table table-striped table-bordered">
					<thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>借款单号</th>
					        <th>借款人</th>
					        <th>所在部门</th>
					        <th>借款金额（元）</th>
					        <th>剩余金额（元）</th>
					    </tr>
				    </thead>
<!-- 				    <tbody> -->
<%-- 				    	 <c:forEach items="${jkye}" var="jkyw"> --%>
<!-- 				    	 	<tr> -->
<!-- 					        <td style="text-align:center;"> -->
<!-- 					        	<input type="checkbox" name="guid" class="select-all"  -->
<%-- 					        	value="${jkyw.guid}" /> --%>
<!-- 					        </td> -->
<%-- 					        <td>${jkyw.DJBH}</td> --%>
<%-- 					        <td>${jkyw.jkrxm}</td> --%>
<%-- 					        <td>${jkyw.szdw}</td> --%>
<!-- 					        <td style="text-align:right;width:80px;"> -->
<%-- 					        	<input type="text" readonly value="<fmt:formatNumber value="${jkyw.JKJE}"  pattern=".00"/>" /> --%>
<!-- 					        </td> -->
<!-- 					        <td style="text-align:center;"><button type="button" class="btn btn-default btn_select">选择</button></td> -->
<!-- 					    </tr> -->
<%-- 				    	 </c:forEach> --%>
<!-- 				    </tbody> -->
				</table>
            </div>
        </div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>

//列表数据
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
     return '<input type="checkbox" name="guid" data-jkr="'+full.JKR+'" data-jkrxm="'+full.JKRXM+'" data-djbh="'+full.DJBH+'" data-szbm="'+full.SZBM+'" data-jkje="'+full.JKJE+'" class="keyId" value="'+data+'">';
   },"width":10,'searchable': false},
   {"data": "_XH",defaultContent:"","class":"text-center","width":40},
   {"data": "DJBH",defaultContent:"","width":100},
   {"data": "JKRXM",defaultContent:"","width":40},
   {"data": "SZBM",defaultContent:"","width":180},
   {"data": "JKJE",defaultContent:"0.00","class":"text-right","":function(data){
// 		return parseFloat(data).toFixed(2);   
   }},
   {"data": "SYJE",defaultContent:"0.00","class":"text-right","":function(data){
//		return parseFloat(data).toFixed(2);   
  }}
 ];
table = getDataTableByListHj("mydatatables","${ctx}/wsbx/rcbx/getJkywPageList",[2,'desc'],columns,0,1,setGroup);
	$(function () {
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		$(document).on("dblclick","tbody tr",function(){
			var guid = $(this).find("[name='guid']").val();
			var djbh = $(this).find("[name='guid']").data("djbh");
			var jkr = $(this).find("[name='guid']").data("jkr");
			var jkrxm = $(this).find("[name='guid']").data("jkrxm");
			var szdw = $(this).find("[name='guid']").data("szbm");
			var jkje = $(this).find("[name='guid']").data("jkje");
			console.log(djbh+"aaaaaaaaaaaaaaa"+guid);
			getIframeControl("${param.pname}","${param.djbh}").val(djbh);
			getIframeControl("${param.pname}","${param.jkr}").val(jkr);
			getIframeControl("${param.pname}","${param.jkrxm}").val(jkrxm);
			getIframeControl("${param.pname}","${param.szdw}").val(szdw);
			getIframeControl("${param.pname}","${param.jkje}").val(jkje);
        	getIframeControl("${param.pname}","${param.djbh}").focus();//手动触发验证
        	getIframeControl("${param.pname}","${param.djbh}").trigger("blur");//手动触发验证
//         	close(winId);
//         	getIframWindow("${param.pname}","${param.djbh}").val(djbh);//
//         	getIframWindow("${param.pname}").cxjkJs(guid);//
        	close(winId);
	    });
		$(window).resize(function(){
	    	$("div.dataTables_wrapper").width($("#searchBox").width());
	    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
	    	$(".dataTables_scrollBody").height(heights);
	    	table.draw();
		});
	});
</script>
</body>
</html>