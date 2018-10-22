<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>收入项目编号</label>
					<input type="text" id="txt_srxmbh" class="form-control" name="srxmbh"  placeholder="请输入收入项目编号">
				</div>
				<div class="form-group">
					<label>收入项目名称</label>
					<input type="text" id="txt_srxmmc" class="form-control" name="srxmmc"   placeholder="请输入收入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>综合查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>收入项目编号</label>
							<div class="input-group">
								<input type="text" id="txt_srxmbh" class="form-control input-radius" name="srxmbh" placeholder="请输入收入项目编号">
							</div>
						</div>
						<div class="form-group">
							<label>收入项目名称</label>
							<div class="input-group">
								<input type="text" id="txt_srxmmc" class="form-control input-radius" name="srxmmc" placeholder="请输入收入项目名称">
							</div>
						</div>
						<div class="form-group">
							<label>项目分类&emsp;&emsp;</label>
							<select id="txt_xmfl" class="form-control" style="width:150px;" name="xmfl">
			                	<option value="">未选择</option>
			                	<c:forEach var="item" items="${xmflList}">
				               		<option value="${item.DM}">${item.MC}</option>
				            	</c:forEach>
			                </select>
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
	               <button type="button" class="btn btn-default" id="btn-add">增加</button>
	               <button type="button" class="btn btn-default" id="btn-del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn-export">导出Excel</button>
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
				            <th>收入项目编号</th>
				            <th>收入项目名称</th>
				            <th>项目分类</th>
				            <th>收入标准（万元）</th>
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
var target = "${ctx}/ysgl/xmsz/srxm";
$(function () {
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "SRXMBH",defaultContent:""},
       {"data": "SRXMMC",defaultContent:""},
       {"data": "XMFLMC",defaultContent:""},
       {"data": "SRBZ",defaultContent:"","class":"text-right je"},
       {"data": "BZ",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn-edit">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
      },orderable:false,"width":220,"class":"text-center","width":100}
     ];
    table = getDataTableByListHj("mydatatables",target+"/getSrxmPageData",[2,'asc'],columns,0,1,setGroup);
  	//添加,批量删除，导出excel
   	$("#btn-add").click(function(){
	   	window.location.href = target+"/goSrxmAddPage?";
   	});
   	$("#btn-del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			confirm("确定删除所选"+checkbox.length+"条信息？","{title:'提示'}",function(){
   				var guid = [];
   	   	   		checkbox.each(function(){
   	   	   			guid.push($(this).val());
   	   	   		});
   	   			$.ajax({
   	   	   			//url:ADDRESS+"/srxm/delete",
   	   	   			url:target+"/srxmDelete",
   	   	   			data:"guid="+guid.join("','"),
   	   	   			type:"post",
   	   	   			async:true,
   	   	   			success:function(data){
   	   	   				var result = JSON.getJson(data);
   	   	   				if(result.success){
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
   	$("#btn-export").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,target+"/expExcel","收入项目信息","${ctx}",guid.join("','"));
   	});
   	//编辑,单条删除
   	$(document).on("click",".btn-edit",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	window.location.href = target+"/goSrxmEditPage?guid="+guid;
   	});
	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定删除该条信息？","",function(){
   			$.ajax({
   	   			//url:ADDRESS+"/srxm/delete",
   	   			url:target+"/srxmDelete",
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
   		});
	});
});
</script>
</body>
</html>s