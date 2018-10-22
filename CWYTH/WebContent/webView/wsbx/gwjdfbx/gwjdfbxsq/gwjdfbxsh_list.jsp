<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>出差业务申请信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.hidden{
	display: none;
}
.btn-link{
	padding: 0px!important;
	margin: 0px!important;
}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>审核状态</label>
					<select class="form-control" id="txt_shzt" >
						<option value="0" <c:if test="${param.status eq 0 }">selected</c:if>>待审核</option>
						<option value="1" <c:if test="${param.status eq 1 }">selected</c:if>>已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh"  placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>报销人</label>
					<input type="text" id="txt_bxry" class="form-control" name="bxry"   placeholder="请输入报销人员姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default plugin" id="btn_pltg">批量通过</button>
	               <button type="button" class="btn btn-default plugin" id="btn_plth">批量退回</button>
	               <button type="button" class="btn btn-default plugin" id="btn_export">导出Excel</button>
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
				            <th>单据编号</th>
				            <th>报销人员</th>
				            <th>所在部门</th>
				            <th>报销金额（元）</th>
				            <th>接待场所</th>
				            <th>接待日期</th>
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
<script src="${ctx}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var target = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq";
var status = "${param.status}";
//加载列表数据
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	   return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'"   value="' + data + '" guid = "'+full.GUID+'">';
   },"width":10,'searchable': false,"class":"text-center"},
   {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
      	return data;},"width":41,"searchable":false,"class":"text-center"},
   {"data": "SHZT",defaultContent:"","class":"text-center","width":50},
   {"data": "DJBH",defaultContent:"","class":"text-center","width":90},
   {"data": "BXRY",defaultContent:"","width":70},
   {"data": "SZBM",defaultContent:"","width":150},
   {"data": "BXJE",defaultContent:"","class":"text-right bxje","width":90, "render":function(data){
	   return parseFloat(data).toFixed(2);
   }},
   {"data": "JDCS",defaultContent:"","width":300},
   {"data": "JDRQ",defaultContent:"","width":50},
   {"data": "A.GUID",'render':function(data, type, full, meta){
	   var link = "";
	   	var shzt = full.SHZTDM;
	   	if(shzt == "0"){
	   		link = '<a href="javascript:void(0);" class="btn btn-link btn_view" guid = "'+full.GUID+'">&nbsp;查看&nbsp;|</a>' 
    			+ '<a href="javascript:void(0);" class="btn btn-link btn_verifyxx" guid = "'+full.GUID+'" procinstid="'+full.PROCINSTID+'">&nbsp;审核&nbsp;|</a>' 
    			+ '<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+full.PROCINSTID+'">&nbsp;办理记录&nbsp;</a>';
 		}else if(shzt == "1"){
 			link = '<a href="javascript:void(0);" class="btn btn-link btn_view" guid = "'+full.GUID+'">&nbsp;查看&nbsp;|</a>' 
    			+ '<a href="javascript:void(0);" class="btn btn-link btn_record" procinstid="'+full.PROCINSTID+'">&nbsp;办理记录&nbsp;</a>';
 		}
	   	return link;
   },orderable:false,"class":"text-center","width":100}
 ];
var table = getDataTableByListHj("mydatatables",target+"/getGwjdfbxshPageData?status="+$("#txt_shzt").val(),[3,'desc'],columns,0,1,setGroup);
//按钮显示
function anxs(){
	switch (status) {
	case "1":
		$("#btn_pltg,#btn_plth").hide();
		break;
	default:
		break;
	}
}

$(function () {
	//初始化页面
	$(document).ready(function(){
		anxs();
	})
//     //审核状态改变
//   	$("#txt_shzt").change(function(){
//   		var status = $("#txt_shzt").val();
//   		pageSkip(target,"gwjdfbxsh_list&status="+status);
//   	});
	// 下拉框改变  
	// ajax刷新，仿财务一体化/网上报销/报销业务查询 
	$("#txt_shzt").change(function(){
		tableRelod(table);
	});

	var tableRelod = function(table) {
		var status = $("[id='txt_shzt']").val();
	    table.ajax.url(target+"/getGwjdfbxshPageData?status="+status);
	    table.ajax.reload();
	};
    //批量审核，导出excel
    $(document).on("click","#btn_pltg",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
			var procinstid = [];
			checkbox.each(function() {
				guid.push($(this).val());
				procinstid.push($(this).parents("td").find(".procinstid").val());
			});
	   	   	select_commonWin(target+"/pageSkip?pageName=check3&guid="+guid.join(",")+"&procinstid="+procinstid.join(","),"审核","500","300");
   		}else{
   			alert("请选择至少一条信息审核!");
   		}
   	});
    $(document).on("click","#btn_plth",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
			var procinstid = [];
			checkbox.each(function() {
				guid.push($(this).val());
				procinstid.push($(this).parents("td").find(".procinstid").val());
			});
	   	   	select_commonWin(target+"/pageSkip?pageName=check4&guid="+guid.join(",")+"&procinstid="+procinstid.join(","),"审核","500","300");
   		}else{
   			alert("请选择至少一条信息审核!");
   		}
   	});
    $(document).on("click","#btn_export",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var shzt = $("#txt_shzt").val();
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		//doExp(json,target+"/expExcel2?shzt="+shzt+"&guid="+guid,"公务接待费报销申请","${ctx}",guid.join("','"));
		doExp("",target+"/expExcel?status="+shzt,"公务接待费报销申请","${ctx}",guid,"");
   		
   	});
   	//审核,查看
   	$(document).on("click",".btn_verifyxx",function(){
   		//var guid = $(this).parents("tr").find("[name='guid']").val();
   		//var procinstid = $(this).parents("tr").find("[name='procinstid']").val();
   		var guid = $(this).attr("guid");
   		var procinstid = $(this).attr("procinstid");
   		
   		pageSkip(target,"gwjdmx_view&guid="+guid+"&action=sh&link=sh&procinstid="+procinstid);
   	});
   	$(document).on("click",".btn_view",function(){
 		//var guid = $(this).parents("tr").find("[name='guid']").val();
 		var guid = $(this).attr("guid");
 		
 		pageSkip(target,"gwjdmx_view&guid="+guid+"&link=sh");
	});
 	//办理记录
   	$(document).on("click",".btn_record",function(){
   		//var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
   		var processInstanceId = $(this).attr("procinstid");
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb","C");
   	});
});
</script>
</body>
</html>