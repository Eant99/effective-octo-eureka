<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入预算申报列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<select style="width:70px;" class="form-control">
						<option value="">未选择</option>
	                	<option>待提交</option>
	                	<option>已提交</option>
	                	<option>通过</option>
	                	<option>退回</option>
					</select>
				</div>
				<div class="form-group">
					<label>申销人</label>
					<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="请输入申报人工号活姓名">
				</div>
				<div class="form-group">
					<label>单位名称</label>
					<input type="text" id="txt_bmmc" class="form-control" name="bmmc"  table="A" placeholder="请输入单位名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>状态&emsp;&emsp;</label>
							<div class="input-group">
								<select style="width:70px;" class="form-control select2">
									<option value="">未选择</option>
	                				<c:forEach var="shztList" items="${shztList}">
		               					<option value="${shztList.DM}">${shztList.MC}</option>
		            				</c:forEach>
								</select>
							</div>
						</div>
						<div class="form-group">
							<label>申销人&emsp;</label>
							<input type="text" id="txt_bxr" class="form-control" name="bxr" table="A" placeholder="输入申报人工号活姓名">
						</div>
						<div class="form-group"> 
							<label>单位名称</label>
							<div class="input-group">
								<input type="text" id="txt_bmmc" class="form-control input-radius" name="bmmc"  table="A" placeholder="请请输入单位名称">
							</div>
						</div>
						<div class="form-group">
							<label>单据编号</label>
							<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="A" placeholder="请输入单据编号">
						</div>
						<div class="form-group">
							<label>编制年度</label>
							<input type="text" id="txt_sqrq" class="form-control date" name="sqrq" table="A" placeholder="编制年度" 
							value=""  data-format="yyyy">
						</div>
						<div class="form-group">
							<label>填报时间</label>
							<input type="text" id="txt_bxrq" class="form-control" name="bxrq" table="A" placeholder="填报时间"
							value=""  data-format="yyyy-MM-dd">
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
	                <button type="button" class="btn btn-default" id="btn_tj">提交</button>
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
				            <th>审核状态</th>
				            <th>单据编号</th>
				            <th>单位名称</th>
				            <th>编制年度</th>
				            <th>上年预算总额（万元）</th>
				            <th>上年收入总额（万元）</th>
				            <th>本年预算总额（万元）</th>
				            <th>填报人</th>
				            <th>填报时间</th>
				            <th>审核意见</th>
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
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" sfzt="'+full.SHZT+'" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "SHZT",defaultContent:"未提交","class":"text-center"},
       {"data": "DJBH",defaultContent:"00012"},
       {"data": "DWMC",defaultContent:"教务处"},
       {"data": "BZND",defaultContent:"2017","class":"text-center"},
       {"data": "SNYSZE",defaultContent:"1020.2000","class":"text-right"},
       {"data": "NSRZE",defaultContent:"1250.0100","class":"text-right"},
       {"data": "BNYSZE",defaultContent:"1010.1300","class":"text-right"},
       {"data": "TBR",defaultContent:"超级管理员"},
       {"data": "TBSJ",defaultContent:"2017-10-30","class":"text-center"},
       {"data": "SHYJ",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
      },orderable:false,"width":220,"class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/srys/srys/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){
   		location.href="${ctx}/webView/ysgl/srys/srys_operate.jsp";
   	});
   //查看按钮
   	$(".btn_look").click(function(){
   		location.href="${ctx}/webView/ysgl/srys/srys_view.jsp";
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功！");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var shzt = checkbox.attr("shzt");
   		if(shzt=="已提交"||shzt=="通过"){
   			alert("已提交或者审核通过的无法修改");
   			return;
   		}
   		location.href="${ctx}/webView/ysgl/srys/srys_operate.jsp";
   	});
    //批量提交
   	$("#btn_tj").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		confirm("确定提交？","{title:提示信息}",function(){
   	   			alert("提交成功");
   	   		});
   		}else{
   			alert("请选择至少一条信息提交!");
   		}
   	});
    //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		confirm("是否允许删除？","{title:提示信息}",function(){
   	   			alert("删除成功");
   	   		});
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
		confirm("是否允许删除？","{title:提示信息}",function(){
			alert("删除成功");
		});
	});
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
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