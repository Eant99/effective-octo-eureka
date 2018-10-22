<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>票据用途信息</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{
 		width:580px ! important;
	} 
 	table.dataTable{ 
 		width:580px ! important;
 	} 
</style>
</head>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>票据用途名称</label>
					<input type="text" id="txt_mbbh" class="form-control" name="pjytmc" table="k" placeholder="请输入票据用途名称">
				</div>
				<!-- <div class="form-group">
					<label>账套内容</label>
					<input type="text" id="txt_mbnr" class="form-control" name="mbnr"  table="A" placeholder="请输入票据用途内容">
				</div> -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>

				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
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
				            <th>票据用途编码</th>
				            <th>票据用途名称</th>
				            <th>是否启用</th>
				            <th>备注</th>
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
       {"data": "PJYTBH",defaultContent:"","width":150}, 
       {"data": "PJYTMC",defaultContent:"","class":"","width":150},
       {"data": "SFQY",defaultContent:"","width":50},     
       {"data": "BZ",defaultContent:"","class":"","width":300},     
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":100,"class":"text-center"}
     ];
	 table = getDataTableByListHj("mydatatables","${ctx}/pjgl/pjyt/getPageList",[2,'asc'],columns,0,1,setGroup);


  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/pjgl/pjyt/goEditPage?operateType=C","C");
   	});
	//导出Excel
  //导出Excel
  	$(document).on("click","#btn_export",function(){
//    	$("#btn_export").click(function() {
   		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/pjgl/pjyt/expExcel2","票据用途信息","${pageContext.request.contextPath}",id);
   		
	});
  
   	//修改按钮
   /* 	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	doOperate("${ctx}/webView/kjhs/pzmb/pzmb_edit.jsp","C");
   	});*/ 
	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();

   		doOperate("${ctx}/pjgl/pjyt/goEditPage?operateType=U&guid="+guid,"U");
   	});
  
   
   	$(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
	   	   	checkbox.each(function(){
	   	   		guid.push($(this).val());
	   	   	});

	   		confirm("确定要删除所选中的这"+checkbox.length+"条信息吗？","",function(){
	   			
	   			$.ajax({
		   			url:"${ctx}/pjgl/pjyt/doCheck",
		   			data:"guid="+guid.join("','"),
		   			type:"post",
		   			dataType:"json",
		   			async:"false",
		   			success:function(val){
		   				console.log("caonimaweisha"+val)
		   				if(val=='0'){
		   						$.ajax({
		   			   	   			//url:ADDRESS+"/srxm/delete",
		   			   	   			url:"${ctx}/pjgl/pjyt/doDelete",
		   			   	   			data:"guid="+guid.join(","),
		   			   	   			type:"post",
		   			   	   			async:"true",
		   			   	   			success:function(data){
		   			   	   				var result = JSON.getJson(data);
		   			  	   				if(result.success){
		   									alert("删除成功！");  	   					
		   				   	   				table.ajax.reload();
		   			  	   				}
		   			   	   			},
		   			   	   			error:function(){
		   			   	   				alert("抱歉，系统出现错误！");
		   			   	   			}
		   			   	   		});	
		   				}else{
		   					alert("抱歉，票据用途已经被使用，不能删除！");
		   				}
		   			},
		   			error:function(){
		   				alert("抱歉，系统出现问题!");
		   			}
   				});
	   		});
   		
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});


   	$(document).on("click",".btn_delxx",function(){
  		var guid = $(this).parents("tr").find("[name='guid']").val();
  		
  		confirm("确定要删除该条信息吗？","",function(){
   			
   			$.ajax({
	   			url:"${ctx}/pjgl/pjyt/doCheck",
	   			data:"guid="+guid,
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val=='0'){
	   						$.ajax({
	   			   	   			//url:ADDRESS+"/srxm/delete",
	   			   	   			url:"${ctx}/pjgl/pjyt/doDelete",
	   			   	   			data:"guid="+guid,
	   			   	   			type:"post",
	   			   	   			async:"true",
	   			   	   			success:function(data){
	   			   	   				var result = JSON.getJson(data);
	   			  	   				if(result.success){
	   									alert("删除成功！");  	   					
	   				   	   				table.ajax.reload();
	   			  	   				}
	   			   	   			},
	   			   	   			error:function(){
	   			   	   				alert("抱歉，系统出现错误！");
	   			   	   			}
	   			   	   		});	
	   				}else{
	   					alert("抱歉，票据用途已经被使用，不能删除！");
	   				}
	   			},
	   			error:function(){
	   				alert("抱歉，系统出现问题!");
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