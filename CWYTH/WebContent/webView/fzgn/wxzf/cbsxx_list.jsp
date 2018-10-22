<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>承包商信息</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>承包商名称</label>
					<input type="text" id="txt_cbsmc" class="form-control" name="cbsmc" table="K" placeholder="请输入承包商名称">
				</div>
<!-- 				<div class="form-group"> -->
<!-- 					<label>承包日期</label> -->
<!-- 					<div class="input-group"> -->
<!-- 						<input type="text" id="txt_ksrq_begin" class="form-control date" name="cbksrq" types="TL" table="A" data-format="yyyy-MM-dd" placeholder="开始日期"> -->
<!-- 						<span class='input-group-addon'> -->
<!-- 					    	<i class="glyphicon glyphicon-calendar"></i> -->
<!-- 					   	</span> -->
<!-- 				   	</div> -->
<!-- 					<label>--</label> -->
<!-- 					<div class="input-group"> -->
<!-- 						<input type="text" id="txt_ksrq_end" class="form-control date" name="cbjsrq" types="TH" table="A" data-format="yyyy-MM-dd" placeholder="截止日期"> -->
<!-- 						<span class='input-group-addon'> -->
<!-- 					    	<i class="glyphicon glyphicon-calendar"></i> -->
<!-- 				   		</span> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>承包商编号</th>
				            <th>承包商名称</th>
				            <th>联系人</th>
				            <th>联系电话</th>	
				            <th>承包开始日期</th>
				            <th>承包结束日期</th>	    
				                    <th>是否统计结算</th>
				            <th>操作</th>
				        </tr>
					</thead>
				    <tbody>
				    </tbody>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
          	
       {"data": "CBSBH",defaultContent:""},
       {"data": "CBSMC",defaultContent:""},
       {"data": "LXR",defaultContent:""},       
       {"data": "LXDH",defaultContent:""},
       {"data": "CBKSRQ",defaultContent:"","class":"text-center"},       
       {"data": "CBJSRQ",defaultContent:"","class":"text-center"},
       {"data": "SFTJMC",defaultContent:"","class":"text-left"},
       
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":120}
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/cbsgl/getPageList",[2,'asc'],columns,0,1,setGroup);
 	//添加按钮
 	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/cbsgl/goCbsxxPage","C");
   	});
 	//编辑
   	 	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
   		doOperate("${ctx}/cbsgl/goCbsxxPage?guid="+guid,"U");
   	});
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/cbsgl/doDelete",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).attr("guid");
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/cbsgl/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
	});
});
$(function() {	
	//列表右侧悬浮按钮
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