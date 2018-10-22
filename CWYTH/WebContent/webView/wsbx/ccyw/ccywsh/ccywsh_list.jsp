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
					<select class="form-control" id="txt_shzt">
						<option value="0" <c:if test="${param.status eq 0 }">selected</c:if> >未审核</option>
						<option value="1" <c:if test="${param.status eq 1 }">selected</c:if> >已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh"  placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>申请人</label>
					<input type="text" id="txt_sqr" class="form-control" name="sqrxm"   placeholder="请输入申请人姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default plugin isshow" id="btn_pltg">批量通过</button>
	               <button type="button" class="btn btn-default plugin isshow" id="btn_plth">批量退回</button>
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
				            <th>申请人</th>
				            <th>所在部门</th>
				            <th>出差类型</th>
<!-- 				            <th>经费类型</th> -->
				            <th>项目名称</th>
				            <th>出差天数</th>
				            <th>申请日期</th>
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
var target = "${ctx}/wsbx/ccyw/ccywsh";
var target2 = "${ctx}/wsbx/ccyw/ccywsq";
var status = "${param.status}";
//加载列表数据
   var columns = [
      {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    	  return '<input type="hidden" class="procinstid" shzt="'+full.TASK_DEF_KEY_+'" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'"   value="' + data + '" guid = "'+full.GUID+'">';      },"width":10,'searchable': false},
      {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
         	return data;},"width":41,"searchable":false,"class":"text-center"},
      {"data": "SHZT",defaultContent:"","class":"text-left"},
      {"data": "DJBH","class":"text-left","render":function(data,type,full,meta){
      	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
      }},
      {"data": "SQRXM",defaultContent:""},
      {"data": "SZBM",defaultContent:""},
      {"data": "CCLX",defaultContent:""},
//       {"data": "JFLX",defaultContent:""},
      {"data": "XMMC",defaultContent:"","class":"text-left"},
      {"data": "CCTS",defaultContent:"","class":"text-right"},
      {"data": "SQRQ",defaultContent:"","class":"text-center","width":100},
      {"data": "GUID",'render':function(data, type, full, meta){
    		var link = "";
    	   	var shzt = full.SHZTDM;
    	   	switch (shzt) {
    		case "0":
    			link = '<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>' 
    			+ '<a href="javascript:void(0);" class="btn btn-link btn_verifyxx">&nbsp;审核&nbsp;|</a>' 
    			+ '<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
    			break;
    		case "1":
    			link = '<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>' 
    			+ '<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
    		default:
    			break;
    		}
    	   	return link;
     },orderable:false,"class":"text-center","width":100}
    ];
   table = getDataTableByListHj("mydatatables","${ctx}/wsbx/ccyw/ccywsh/getCcywshPageData?status="+$("#txt_shzt").val(),[3,'desc'],columns,0,1,setGroup);
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
//   		window.location.href = target+"/goCcywshPage?status="+status;
//   	});
	// 下拉框改变  
	// ajax，仿财务一体化/网上报销/报销业务查询 
	$("#txt_shzt").change(function(){
		tableRelod(table);
	});
	var tableRelod = function(table) {
		var status = $("[id='txt_shzt']").val();
		if(status=="1"){
			$(".isshow").attr("style","display:none;")
		}else{
			$(".isshow").attr("style","display:block;")
		}
	    table.ajax.url("${ctx}/wsbx/ccyw/ccywsh/getCcywshPageData?status="+status);
	    table.ajax.reload();
	};
    //批量通过,退回，导出excel
    $(document).on("click","#btn_pltg",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
			var procinstid = [];
			checkbox.each(function() {
				guid.push($(this).val());
				procinstid.push($(this).parents("td").find(".procinstid").val());
			});
	   	 	select_commonWin(target+"/goVerifyPage?guid="+guid.join(",")+"&procinstid="+procinstid.join(",")+"&status=pass","出差业务审核","500","300");
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
	   	 	select_commonWin(target+"/goVerifyPage?guid="+guid.join(",")+"&procinstid="+procinstid.join(",")+"&status=false","出差业务审核","500","300");
   		}else{
   			alert("请选择至少一条信息审核!");
   		}
   	});
   	//
//    	$("#btn_export").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
//    		doExp(json,target+"/expExcel?shzt=${param.shzt}","出差业务申请信息","${ctx}",guid.join("','"));
//    	});
   	$(document).on("click","#btn_export",function(){
   	  var id = [];
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var shzt = $("#txt_shzt").val();
		if(checkbox.length > 0){
		checkbox.each(function(){
			id.push($(this).val());
		});
		id = id.join("','");
		}else{
			id = "";
		}
		doExp("","${ctx}/wsbx/ccyw/ccywsh/expExcel2?status="+shzt,"出差业务申请","${pageContext.request.contextPath}",id,"");
   		
// 				var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/wsbx/ccyw/ccywsh/expExcel3",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","出差业务申请.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
   	//审核,查看
   	$(document).on("click",".btn_verifyxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var procinstid=$(this).parents("tr").find("[name='procinstid']").val();
   		var src = "";
   		var status = $(this).parents("tr").find("[name='procinstid']").attr("shzt");
   		if(status=="bzr"){
   			src = target2+"/goCcywsqViewPageByBzy?guid="+guid+"&action=sh&link=sh&procinstid="+procinstid;
   			window.location.href = src;
   		}else{
   			src = target2+"/goCcywsqViewPage?guid="+guid+"&action=sh&link=sh&procinstid="+procinstid;
   			window.location.href = src;
   		}
   	});
	$(document).on("click",".btn_view",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	window.location.href = target2+"/goCcywsqViewPage?guid="+guid+"&action=view&link=sh";
	});
	$(document).on("click",".btn_look",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	window.location.href = target2+"/goCcywsqViewPage?guid="+guid+"&action=view&link=sh";
	});
	//办理记录
   	$(document).on("click",".btn_record",function(){
   		var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=ccyw","C");
   	});
});
</script>
</body>
</html>