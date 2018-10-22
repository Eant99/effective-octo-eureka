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
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>审核状态</label>
					<select class="form-control" id="txt_shzt">
						<option value="00">未审核</option>
						<option value="11">已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_djbh" class="form-control" name="BMMCS"  placeholder="请输入部门名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
					   <button type="button" class="btn btn-default" id="btn_tgs">批量通过</button>
						<button type="button" class="btn btn-default" id="btn_ths">批量退回</button>
<!-- 	               <button type="button" class="btn btn-default plugin" id="btn_check">查看</button> -->
<!-- 	               <button type="button" class="btn btn-default plugin" id="btn_tongguo">通过</button> -->
<!-- 	               <button type="button" class="btn btn-default plugin" id="btn_tuihui">退回</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button> -->
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
				        	<th style="text-align: center;"><input type="checkbox" class="select-all" /></th>
				            <th>序号</th>
				            <th>审核状态</th>
				            <th>申报年度</th>
				            <th>部门编号</th>
				            <th>部门名称</th>
				            <th>经办人</th>
				            <th>部门负责人</th>
				            <th>财务负责人</th>
				            <th>收入预算汇总（万元）</th>
				            <th>支出预算汇总（万元）</th>
				            <th>操作</th>
				        </tr>
					</thead>
				</table>
			</div>
		</div>
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
var target = "${ctx}/wsbx/ccyw/ccywsq";

$(function () {
	
   	//查看
 		$(document).on("click",".btn_ck",function(){
			var guid = $(this).parents("tr").find(":checkbox").val();
			var SRYSHZ=$(this).parents("tr").find(":checkbox").attr("SRYSHZ");
			var ZCYSHZ=$(this).parents("tr").find(":checkbox").attr("ZCYSHZ");
			var procinstid=$(this).parents("tr").find(":checkbox").attr("procinstid");
			doOperate("${ctx}/window/mainBmyssbTree?ck=ck&sh=sh&procinstid="+procinstid+"&pass=true&guid="+guid+"&ZCYSHZ="+ZCYSHZ+"&SRYSHZ="+SRYSHZ+"&mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C
		});
   	//提交
   	$(document).on("click",".btn_submitxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var url = target+"/submit";
   		doSub("guid="+guid,url,function(){
   			table.ajax.reload();
   		},"",1);
   	});
   	
	$(document).on("change","[id='txt_shzt']",function(){
		var shzt = $(this).val();
		table.ajax.url("${ctx}/ysgl/bmyssh/getPageList?shzt="+shzt);
		table.ajax.reload();
	});
	//加载列表数据
  	var columns = [
    	{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
				return '<input type="checkbox" class="keyId" name="keyId" procinstid="'+full.PROCINSTID+'" shztsz="'+full.SHZTSZ+'" SRYSHZ="'+full.SRYSHZ+'" ZCYSHZ="'+full.ZCYSHZ+'" value="' + data + '">';
   	    },"width":10,'searchable': false},//0
   		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
   	   		return data;
   		},"width":41,"searchable": false,"class":"text-center"},//1
   		{"data": "SHZTMC",defaultContent:"","width":120},
   		{"data": "ND",defaultContent:""},
   		{"data": "BMMC",defaultContent:""},
   		{"data": "BMMCS",defaultContent:""},
   	   	{"data": "JBRMC",defaultContent:""},
   	 	{"data": "DWFZRMC",defaultContent:""},
   	 	{"data": "CWFZRMC",defaultContent:""},
   	 	{"data": "SRYSHZ",defaultContent:"-","class":"text-right"},
   		{"data": "ZCYSHZ",defaultContent:"-","class":"text-right"},
   	   	{"data": "GUID",'render':function (data, type, full, meta){
//    	   	 if(full.SHZTSZ=="001"||full.SHZTSZ!="002"||full.SHZTSZ!="003"||full.SHZTSZ!="004"||full.SHZTSZ!="005"||full.SHZTSZ!="013"||full.SHZTSZ!="014"||full.SHZTSZ!="015"||full.SHZTSZ!="016"){
  		   return '<a class="btn btn-link btn_ck" dwbh = "'+full.GUID+'">查看</a><span class="sg">|</span><a class="btn btn-link btn_sh shan" dwbh = "'+full.GUID+'">审核</a>|<a  class="btn btn-link btn_bljl" guid = "'+full.GUID+'">办理记录</a>'; 
   	   	},orderable:false,"width":95}
     ];
  	 table = getDataTableByListHj("mydatatables","${ctx}/ysgl/bmyssh/getPageList",[3,'asc'],columns,0,1,kz);    
 	function kz(){
		var shzt = $("#txt_shzt").val();
		if(shzt=="00"){//未审核
			$(".shan").css("display","");
		}else{//已审核
			$(".shan,.sg").css("display","none");
		}
	}
    //导出Excel
	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	var guid = [];
	checkbox.each(function(){
		guid.push($(this).val());
	});
		doExp(json,"${ctx}/jzmb/expExcel?treedwbh=${dwbh}","部门预算审核信息","${ctx}",guid.join(","));
	});
	 var target = "${ctx}/ysgl/bmyssb/";
	//审核
		$(document).on("click",".btn_sh",function(){
			var guid = $(this).parents("tr").find(":checkbox").val();
			var SRYSHZ=$(this).parents("tr").find(":checkbox").attr("SRYSHZ");
			var ZCYSHZ=$(this).parents("tr").find(":checkbox").attr("ZCYSHZ");
			var procinstid=$(this).parents("tr").find(":checkbox").attr("procinstid");
			doOperate("${ctx}/window/mainBmyssbTree?sh=sh&procinstid="+procinstid+"&pass=true&guid="+guid+"&ZCYSHZ="+ZCYSHZ+"&SRYSHZ="+SRYSHZ+"&mkbh=010301&pageUrl=/xmxxt/goXmxxPage&treeJson=/glqxb/qxdwTree?dwbh=${param.dwbh}", "C");//operateType=C
		});
	//通过
	$(document).on("click",".btn_tg",function(){
		var guid = $(this).parents("tr").find(":checkbox").val();
		var SRYSHZ=$(this).parents("tr").find(":checkbox").attr("SRYSHZ");
		var ZCYSHZ=$(this).parents("tr").find(":checkbox").attr("ZCYSHZ");
		var procinstid=$(this).parents("tr").find(":checkbox").attr("procinstid");
		$.ajax({
			type:"post",
			url:"${ctx}/ysgl/bmyssh/doApprove",
			data:"&procinstid="+procinstid+"&pass=true&guid="+guid+"&ZCYSHZ="+ZCYSHZ+"&SRYSHZ="+SRYSHZ,
			success:function(val){
					alert("操作成功！");
// 					getIframWindow("${param.pname}").location.href="${ctx}/wsbx/sqspsh/goSqspsqPage";
// 					close(winId);
			}
		});
   	});
	//退回
	$(document).on("click",".btn_th",function(){
		var guid = $(this).parents("tr").find(":checkbox").val();
		var procinstid=$(this).parents("tr").find(":checkbox").attr("procinstid");
			$.ajax({
				type:"post",
				url:"${ctx}/ysgl/bmyssh/doApprove",
				data:"&procinstid="+procinstid+"&pass=false&guid="+guid,
				success:function(val){
					var data = JSON.getJson(val);
					if(data.success='true'){
						alert("操作成功！");
// 						getIframWindow("${param.pname}").location.href="${ctx}/wsbx/sqspsh/goSqspsqPage";
						close(winId);
					}
				}
			});
   	});
	//批量通过
	$("#btn_tgs").click(
		function() {
			var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
			if (checkbox.length > 0) {
				var id = [];
				var procinstid = [];
				var SRYSHZ=[];
				var ZCYSHZ=[];
				checkbox.each(function() {
					id.push($(this).val());
					procinstid.push($(this).attr("procinstid"));
					SRYSHZ.push($(this).attr("SRYSHZ"));
					ZCYSHZ.push($(this).attr("ZCYSHZ"));
				});
				 select_commonWin("${ctx}/ysgl/bmyssh/check?check=checktgs&guid="+id.join(",")+"&procinstid="+procinstid.join(",")+"&SRYSHZ="+SRYSHZ.join(",")+"&ZCYSHZ="+ZCYSHZ.join(","),"通过页面","500","200");
			} else {
				alert("请选择至少一条信息！");
			}
	});
	//批量退回
	$("#btn_ths").click(
			function() {
				var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
				if (checkbox.length > 0) {
					var id = [];
					var procinstid = [];
					var SRYSHZ=[];
					var ZCYSHZ=[];
					checkbox.each(function() {
						id.push($(this).val());
						procinstid.push($(this).attr("procinstid"));
						SRYSHZ.push($(this).attr("SRYSHZ"));
						ZCYSHZ.push($(this).attr("ZCYSHZ"));
					});
					 select_commonWin("${ctx}/ysgl/bmyssh/check?check=checkths&guid="+id.join(",")+"&procinstid="+procinstid.join(",")+"&SRYSHZ="+SRYSHZ.join(",")+"&ZCYSHZ="+ZCYSHZ.join(","),"退回页面","500","200");
				} else {
					alert("请选择至少一条信息！");
				}
		});
	//办理记录
   	$(document).on("click",".btn_bljl",function(){
   		var processInstanceId=$(this).parents("tr").find(":checkbox").attr("procinstid");
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb","C");
   		
   	});
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