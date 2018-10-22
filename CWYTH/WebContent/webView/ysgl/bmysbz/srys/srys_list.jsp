<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.util.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ include file="/webView/include/taglib.jsp"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<%
SimpleDateFormat sdf = new SimpleDateFormat("yyyy");
String date = sdf.format(new Date());
int dyn = Integer.parseInt(date)+1;
int den = Integer.parseInt(date)+2;
int dsn = Integer.parseInt(date)+3;
%>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>申报人员</label>   
					<input type="text" class="form-control input-radius" name="sbry"  table="A" placeholder="请输入申报人员">
				</div>
				<div class="form-group">
					<label>申报部门</label>
					<input type="text" class="form-control input-radius" name="sbbm"  table="A" placeholder="请输入申报部门">
				</div>
			<div class="form-group">
					<label>申报年度</label>
					<input type="text" id="txt_sbnd" class="form-control input-radius window year" name="sbnd" table="A" value="<%=date %>" data-format="yyyy"/>							
			</div>
				
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">	               
	                <button class="btn btn-default" type="button" id="btn_add">增加</button>
	                <button class="btn btn-default" type="button" id="btn_export">导出Excel</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="guid" value="${guid}"/>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
			 				<th>确认状态</th>
						    <th>申报人员</th>
						    <th>申报部门</th>
						    <th>申报年度</th>
						    <th><%=dyn %>年收入汇总（万元）</th>
						    <th><%=den %>年收入汇总（万元）</th>
						    <th><%=dsn %>年收入汇总（万元）</th>

						    <th>操作</th>
			   			</tr>
					</thead>
					<tbody></tbody>
				</table>
			</div>
		</div>
	</div>
</div>
 <%@include file="/static/include/public-list-js.inc"%>  
<script >
$(function(){
	//添加按钮
   	$("#btn_add").click(function(){
		
   		doOperate("${ctx}/bmysbz/goAddsrysPage");
   	});
	
   //单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		
	 	confirm("确定删除？","",function(){
			$.ajax({
	   			url:"${ctx}/bmysbz/srysDel",
	   			data:"guid="+guid,
	   			type:"post",
	   			async:"false",
	   			success:function(val){
	   				alert("删除成功");
   	   			window.location.href = "${ctx}/webView/ysgl/bmysbz/srys/srys_list.jsp";
	   			}
	   		});
		});
	
	});
   // 编辑
	$(document).on("click",".btn_u",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		doOperate("${ctx}/bmysbz/goSrysEditPage?guid="+guid,"U");
	});
	// 编辑
	$(document).on("click",".btn_look",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		doOperate("${ctx}/bmysbz/goSrysEditPage?guid="+guid+"&L=L");
	});
	
	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" bz="'+full.BZS+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
		{"data": "QRZT",defaultContent:"","class":"text-center"},
	   	{"data": "SBRY",defaultContent:""},
	   	{"data": "SBBM",defaultContent:""},
	   	{"data": "SBND",defaultContent:"","class":"text-center"},
	   	{"data": "DYNSRHZ",defaultContent:"","class":"text-right"},
	   	{"data": "DENSRHZ",defaultContent:"","class":"text-right"},
	   	{"data": "DSNSRHZ",defaultContent:"","class":"text-right"},
	   
	   	{"data":"GUID","class":"text-center","render":function (data, type, full, meta){
	   		if(full.QRZT == "未确认"){
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_u">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	   		}else{
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_look" style="text-aligh:center">查看</a>';

	   		}
	   
	   	},orderable:false,"width":90}	
	   		
	];	
   table = getDataTableByListHj("mydatatables","${ctx}/bmysbz/getsryslist?guid=${param.guid}",[2,'asc'],columns,0,1,setGroup);	
   //导出excel
	$("#btn_export").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
			
		});
		console.log("guid============="+guid.join(","));
   		doExp(json,"${ctx}/bmysbz/expExcelsrys","收入预算信息","${pageContext.request.contextPath}",guid.join("','"));
   	});
});


</script>
</body>
</html>