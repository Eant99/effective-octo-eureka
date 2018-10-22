<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支付方式设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>支付编码</label>
					<input type="text" id="txt_zfbm" class="form-control" name="zfbm" table="A" placeholder="请输入支付编码">
				</div>
				<div class="form-group">
					<label>支付名称</label>
					<input type="text" id="txt_zfmc" class="form-control" name="zfmc"  table="A" placeholder="请输入支付名称">
				</div>
				<div class="form-group">
					<label>启用状态</label>
					<select id="drp_qyzt" class="form-control input-radius select2" name="qyzt">
									<option value="1" <c:if test="${dmb.dmsx == '1'}">selected</c:if>>启用</option>
									<option value="2" <c:if test="${dmb.dmsx == '2'}">selected</c:if>>禁用</option>
								</select>
				</div>
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
				<table id="mydatatables" class="table table-striped table-bordered" >			
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>支付编码</th>
				            <th>支付名称</th>
				            <th>启用状态</th>			            
				            <th style="text-align:center;">操作</th>
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
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "ZFBM",defaultContent:""},
       {"data": "ZFMC",defaultContent:""},
       {"data": "QYZT",defaultContent:"",
    	   'render':function(data, type, full, meta){
    		   if(data='1'){
    			   return "<span>启用</span>";
    		   }else if(data='2'){
    			   return "<span>禁用</span>"; 			   
    		   }
    	   }
       },
      
       {"data": "GUID",class:"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zffssz/getPageList",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/webView/zffssz/goEditPage","C");
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		<!--
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/zffssz/expExcel","支付方式信息","${ctx}",guid.join(","));
   		-->
   		alert("导出成功！");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/zffssz/goEditPage","C");
   	});
  
  //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		confirm("确定删除？","{title:提示信息}",function(){
   	   			alert("删除成功");
   	   		})
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var shzt = checkbox.attr("shzt");
   		if(shzt=="已提交"||shzt=="通过"){
   			alert("已提交或者审核通过的无法修改");
   			return;
   		}
		confirm("确定删除？","{title:提示信息}",function(){
			alert("删除成功");
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