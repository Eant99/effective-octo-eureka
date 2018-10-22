<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配方案</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div class="form-group">
							<label>审核状态</label>
							 <select id="drp_shzt" class="form-control input-radius" name="shzt"> 							
	                        		<option value="" >未选择</option>
	                        		<option value="0" >通过</option>
	                        		<option value="1" >退回</option>                        		                   
							</select>
						</div>
				<div class="form-group">
					<label>方案编号</label>
					<input type="text" id="txt_fabh" class="form-control" name="fabh"  placeholder="请输入专业编号">
				</div>
				<div class="form-group">
					<label>方案名称</label>
					<input type="text" id="txt_famc" class="form-control" name="famc"   placeholder="请输入专业名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>综合查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>审核状态</label>
							 <select id="drp_shzt" class="form-control input-radius" name="shzt"> 							
	                        		<option value="" >未选择</option>
	                        		<option value="0" >通过</option>
	                        		<option value="1" >退回</option>                        		                   
							</select>
						</div>
						<div class="form-group">
							<label>方案编号</label>
							<input type="text" id="txt_fabh" class="form-control input-radius" name="fabh" value="" types="D"  placeholder="请输入方案编号">         	
						</div>
						<div class="form-group">
							<label>方案名称</label>
							<div class="input-group">							
								<input type="text" id="txt_famc" class="form-control input-radius" name="famc" value="" types="D"  placeholder="请输入方案名称"> 
							</div>
						</div>
						<div class="form-group">
							<label>创建时间</label>
							<input type="text" id="txt_cjrq" class="form-control input-radius date" name="cjrq"   data-format="yyyy-MM-dd" placeholder="请输入创建时间">
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	                <button type="button" class="btn btn-default" id="btn_commit">提交</button>               
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
				            <th>审核状态</th>
				            <th>方案编号</th>
				            <th>方案名称</th>
				            <th>创建时间</th>
				            <th>创建人</th>	           
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
         return '<input type="checkbox" name="guid" class="keyId" shzt="'+full.SHZT+'" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "SHZT",defaultContent:"","class":"text-center"},
       {"data": "FABH",defaultContent:""},
       {"data": "FAMC",defaultContent:""},
       {"data": "CJRQ",defaultContent:"","class":"text-center"},
       {"data": "CJR",defaultContent:""},
       {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/zjfpfa/getPageList2",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/zjfpfa/goAddPage","");
   	});
    //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
   	   		$.ajax({
			type:"post",
			url:ADDRESS+"/zjfpfa/delete",
			data:"guid="+guid.join("','"),
			success:function(val){	
				var result = JSON.getJson(val);
				if(result.success){
					alert("删除成功！");							
				}
			},
			error:function(val){
				alert("删除失败，请稍后重试！");					
			}	
		});
   	   	}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});  
    //提交按钮
   	$("#btn_commit").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked[shzt='未提交']");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
   	   	$.ajax({
			type:"post",
			url:ADDRESS+"/zjfpfa/submit",
			data:"guid="+guid.join("','"),
			success:function(val){	
				var result = JSON.getJson(val);
				if(result.success){
					alert("提交成功！");							
				}
			},
			error:function(val){
				alert("提交失败，请稍后重试！");					
			}	
		});
   		}else{
   			alert("请选择至少一条（未提交）信息!");
   		}
   	}); 
	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","资金分配方案信息","${ctx}",guid.join(","));

   	});
   	//编辑按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find(".keyId").val();
 		window.location.href = "${ctx}/zjfpfa/goEditPage?guid="+guid;
   	});
   	//查看
 	$(".btn_look").click(function(){
 		var guid = $(this).parents("tr").find(".keyId").val();
 		window.location.href = "${ctx}/zjfpfa/goViewPage?guid="+guid;
   	}); 
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		$.ajax({
			type:"post",
			url:ADDRESS+"/zjfpfa/delete",
			data:"guid="+guid,
			success:function(val){	
				var result = JSON.getJson(val);
				if(result.success){
					alert("删除成功！");	
					table.ajax.reload();
				}
			},
			error:function(val){
				alert("删除失败，请稍后重试！");					
			}	
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