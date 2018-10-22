<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">

</style>

<body>
<div class="fullscreen">
    <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>标题</label>
					<input type="text" class="form-control" name="title" placeholder="请输入标题">
				</div>
				<div class="form-group">
					<label>审核意见</label>
					<input type="text" class="form-control" name="shyj" placeholder="请输入审核意见">
				</div>
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
				            <th>标题</th>
				            <th>审核类型</th>
				            <th>审核意见</th>
				            <th>操作日期</th>
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "TITLE",defaultContent:"","width":200},
       {"data": "SHLX",defaultContent:"","width":100},
       {"data": "SHYJ",defaultContent:""},
       {"data": "CZRQ",defaultContent:"","class":"text-center","width":80},
      
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":60}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wsbx/shyj/getPageList",[2,'asc'],columns,0,1,setGroup);

    $(document).on("click","#btn_add",function(){
//    		doOperate("${ctx}/wsbx/shyj/goEditPage","C");
    	select_commonWin("${ctx}/wsbx/shyj/goEditPage?operateType=C","新增","400","450");
   	});
 
   	$(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   			if(checkbox.length>0){
   	   			var guids = [];
   	   			checkbox.each(function(){
   	   				guids.push($(this).val());
   	   			});
   	   			confirm("确定要删除所选中的这"+checkbox.length+"条信息吗？","",function(){
					$.ajax({
			   			url:"${ctx}/wsbx/shyj/doDelete",
			   			data:"guid="+guids.join("','"),
			   			type:"post",
			   			dataType:"json",
			   			async:"false",
			   			success:function(val){
			   				if(val.success){
			   					alert("删除成功！");
			   				}
			   				table.ajax.reload();
			   			},
			   			error:function(){
			   				alert("抱歉，系统出现问题！");
			   			}
			   		});
				});
   	   		}else{
   	   			alert("请选择至少一条信息删除!");
   	   		}
   		
   	});
    
	
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
   		select_commonWin("${ctx}/wsbx/shyj/goEditPage?operateType=U&guid="+guid,"修改信息","400","450");
//    		window.location.href = "${ctx}/wsbx/shyj/goEditPage?guid="+guid+"&operateType=U";
   	});
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).attr("guid");
		confirm("确定删除？","",function(){
			$.ajax({
	   			url:"${ctx}/wsbx/shyj/doDelete",
	   			data:"guid="+guid,
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val.success){
	   					alert("删除成功！");
	   				}
	   				
	   				table.ajax.reload();
	   			},
	   			error:function(){
	   				alert("抱歉，系统出现问题！");
	   			}
	   		});
		});   		
   	});

		
});
$(function() {	
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</html>