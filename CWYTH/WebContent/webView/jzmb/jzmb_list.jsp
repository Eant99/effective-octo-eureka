<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>结转模板管理</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<%
	String date = Constant.MR_YEAR();
	%>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>模板编号</label>
					<input type="text" id="txt_mbbh" class="form-control" name="mbbh" table="K" placeholder="请输入模板编号">
				</div>
				<div class="form-group">
					<label>模板名称</label>
					<input type="text" id="txt_mbnr" class="form-control" name="mbmc" table="K" placeholder="请输入模板名称">
				</div>
<!-- 				<div class="input-group"> -->
<!-- 					<span class="input-group-addon"><span class="required">*</span>年度</span> -->
<%-- 					<input type="text" id="txt_nd" class="form-control input-radius year" name="czrq" table="K" value="<%=date %>" data-format="yyyy" /> --%>
<!-- 				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>

				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
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
				            <th>模板编号</th>
				            <th>模板名称</th>
				            <!-- <th>凭证类型</th> -->
				            <th>凭证摘要</th>
				            <th>转出科目</th>
				            <th>转入科目</th>
				            
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var target = "${ctx}/jzmb";
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "MBBH","class":"hj","render":function (data, type, full, meta){
		  	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
	   }}, 
       {"data": "MBMC",defaultContent:"","width":30},
       /* {"data": "PZLX",defaultContent:"","width":30},  */
       {"data": "ZY",defaultContent:""},
       {"data": "ZCKM",defaultContent:""},
       {"data": "ZRKM",defaultContent:""},
       
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd"  guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx"  guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":70,"class":"text-center"}
     ];
	
 	table = getDataTableByListHj("mydatatables","${ctx}/jzmb/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);

 	//修改按钮 
 	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
		doOperate("${ctx}/jzmb/goEditPage?guid="+guid,"U");
	});
 	//查看按钮
 	$(document).on("click",".btn_look",function(){
 		var guid = $(this).attr("guid");
 		doOperate("${ctx}/jzmb/goLookPage?guid="+guid,"U");
 	});
  	//添加按钮
  	$(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/jzmb/goAddPage?operateType=C","C");
   	});
  
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
//     $("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			confirm("确定删除所选"+checkbox.length+"条信息？","{title:'提示'}",function(){
   				var guid = [];
   	   	   		checkbox.each(function(){
   	   	   			guid.push($(this).val());
   	   	   		});
   	   			$.ajax({
   	   	   			//url:ADDRESS+"/srxm/delete",
   	   	   			url:target+"/doDelete",
   	   	   			data:"guid="+guid.join("','"),
   	   	   			type:"post",
   	   	   			dataType:"json",
   	   	   			async:true,
   	   	   			success:function(data){  	   	   				
   	   	   				if(data.success){
   							alert("删除成功！");  	   					
	   	   	   				table.ajax.reload();
   	   	   				}else{
   	   	   					alert("删除失败，请稍后重试！");
   	   	   				}
   	   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
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
		confirm("确定删除该条信息？","",function(){
   			$.ajax({
   	   			//url:ADDRESS+"/srxm/delete",
   	   			url:target+"/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:true,
   	   			dataType:"json",
   	   			success:function(data){
   	   				//var result = JSON.getJson(data);
  	   				if(data.success){
						alert("删除成功！");  	   					
	   	   				table.ajax.reload();
  	   				}
   	   			},
   	   			error:function(){
   	   				alert("抱歉，系统出现错误！");
   	   			},  	   			
   	   		});
   		});
	});
});

	//导出Excel
// 	$("#btn_exp").click(function(){
// 		var json = searchJson("searchBox");
// 		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
// 		doExp(json,"${ctx}/jzmb/expExcel?treedwbh=${dwbh}","结转模板信息","${ctx}",guid.join(","));
// 	});
	
	//导出Excel
   	$("#btn_exp").click(function() {
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var id=[];
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/jzmb/expExcel2","结转模板","${pageContext.request.contextPath}",id);
   	});
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/jzmb/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","结转模板.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
// 			});
	
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