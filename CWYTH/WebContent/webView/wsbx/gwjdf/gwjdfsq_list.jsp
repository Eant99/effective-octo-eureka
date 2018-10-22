<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待费申请</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>

</style>
</head>
<body>
	<div class="fullscreen">
		<div class="search" id="searchBox" style="padding-top: 0px">
			<form id="myform" class="form-inline" action=""
				style="padding-top: 8px; padding-bottom: 2px;">
				<div class="search-simple">
					<div class="form-group">
						<label>审核状态</label> 
						<select id="txt_shzt" class="form-control" table="K">
<%-- 							<c:forEach items="${shztList }" var="item"> --%>
<%-- 						    	<option value="${item.DM}" id="op" <c:if test="${item.DM eq param.shzt}">selected</c:if>>${item.MC }</option> --%>
<%-- 							</c:forEach> --%>
							<option value="00" <c:if test="${param.shzt=='00'}">selected</c:if>>未提交</option>
	                		<option value="11" <c:if test="${param.shzt=='11'}">selected</c:if>>审核中</option>
	                		<option value="99" <c:if test="${param.shzt=='99'}">selected</c:if>>已通过</option>
							<option value="all" <c:if test="${'all' eq param.shzt }">selected</c:if>>全部</option>
						</select>
					</div>
					<div class="form-group">
						<label>单据编号</label> <input type="text" id="txt_djbh"
							class="form-control" name="djbh" placeholder="请输入单据编号">
					</div>
					<div class="form-group">
						<label>申请人</label> <input type="text" id="txt_sqr"
							class="form-control" name="sqr" placeholder="请输入人员编号">
					</div>
					<div class="form-group">
						<label>接待部门</label> <input type="text" id="txt_szbm"
							class="form-control" name="jdbm" placeholder="请输入部门编号">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default" id="btn_add">增加</button>
	               		<button type="button" class="btn btn-default plugin" id="btn_submit" >批量提交</button>
	               		<button type="button" class="btn btn-default plugin" id="btn_del" >批量删除</button>
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
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>审核状态</th>
								<th>单据编号</th>
								<th>申请人</th>
								<th>接待部门</th>
 								<th>接待日期</th>
								<th>来宾单位</th>
								<th>接待地点</th>
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
	var suffix = "&mkbh=${param.mkbh}";

	var shzt = "${param.shzt}";
	
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
    	   return '<input type="hidden" class="procinstid" taskid="'+full.TASKID+'" name="procinstid" value="'+full.PROCINSTID+'"><input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'"   value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "SHZTMC",orderable:false,defaultContent:"","class":"text-left"},
            {"data": "DJBH","class":"text-left","render":function(data,type,full,meta){
            	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
            }},
            {"data": "SQR",defaultContent:""},
             {"data": "JDBM",defaultContent:"","class":"text-left"},
            {"data": "JDRQ",defaultContent:"","class":"text-center"},
            {"data": "LBDW",defaultContent:""},
            {"data": "JDFD",defaultContent:""},
            {"data": "SQRQ",defaultContent:"","class":"text-center"},  
            {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
            	 if(full.ZT=='01'){
     	   			return '<a href="javascript:void(0);" class="btn btn-link btn_edit">编辑</a>|'
     	   			+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|'
       				+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">提交</a>|'
       				+'<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>|';
     	   		}else if(full.CHECKSHZT=='11'){
     	   			return	'<a href="javascript:void(0);" class="btn btn-link btn_record plugin" >办理记录</a>|'
     	   			+'<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>';
     	   		}else if(full.CHECKSHZT=='00' &&full.ZT!='01' ){
     	   			return '<a href="javascript:void(0);" class="btn btn-link btn_edit">编辑</a>|'
     	   			+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>|'
       				+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">提交</a>|'
       				+'<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>|'
       				+'<a href="javascript:void(0);" class="btn btn-link btn_record plugin" >办理记录</a>';
     	   		}else if(full.CHECKSHZT=='99'){
     	   			return '<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>|'
   				+'<a href="javascript:void(0);" class="btn btn-link btn_record plugin" >办理记录</a>|'
   				+'<a href="javascript:void(0);" class="btn btn-link btn_print">打印</a>'
   				;
     	   		}	
      },orderable:false,"width":220}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/gwjdfsq/getPageList?shzt="+$("#txt_shzt").val(),[3,'desc'],columns,0,1,setGroup);
    
    
    
    //按钮显示
    function anxs(a_shzt){
    	$(".plugin").hide();
    	switch (a_shzt) {
    	case "01":
    	case "03":
    	case "00":
    		$("#btn_submit,#btn_del").show();
    		break;
    	default:
    		break;
    	}
    }    
    
$(function () {
	$(document).ready(function(){
		switch (shzt) {
		case "02":
		case "11":
			$("#btn_submit,#btn_del").hide();
			break;
		case "all":
			$("#btn_submit,#btn_del").hide();
			break;
		case "99":
			$("#btn_submit,#btn_del").hide();
			break;
		default:
			break;
		}
	});
	
//     //审核状态改变
//   	$("#txt_shzt").change(function(){
//   		var shzt = $("#txt_shzt").val();
//    		window.location.href = "${ctx}/gwjdfsq/goListPage?shzt="+shzt+"&shztm="+shzt;
//   	});

	$("#txt_shzt").change(function(){
  		tableRelod(table);
  	});
   	
    var tableRelod = function(table) {
		var shzt=$("[id='txt_shzt']").val();
		anxs(shzt);
  	    table.ajax.url("${ctx}/gwjdfsq/getPageList?shzt="+shzt);
  	    table.ajax.reload();
  	};

  	//添加按钮
  	$(document).on("click","#btn_add",function(){
   		var mkbh = "${param.mkbh}";
   		var sftzxz = querySftzxz(mkbh);
   		if(sftzxz){
   			alert("该模块已经禁止新增单据！");
   		}else{
   			doOperate("${ctx}/gwjdfsq/goEditPage?shztm="+"${param.shztm}"+suffix, "C");
   		}
   	});
  	//编辑
   	$(document).on("click",".btn_edit",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/gwjdfsq/goEditPage?guid="+guid+"&shztm="+"${param.shztm}"+suffix,"U");

   	});
  //导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
// 		console.log("11111111111");
//    		doExp(json,"${ctx}/gwjdfsq/expExcel?shztm=${param.shztm}","公务接待费申请信息","${ctx}",guid.join(","));
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
		doExp("","${ctx}/gwjdfsq/expExcel2?shztm="+shzt,"公务接待申请","${pageContext.request.contextPath}",id);
	  
// 				var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/gwjdfsq/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","往来单位设置信息.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
   	//审核状态按钮
   	$(document).on("click",".btn_ZT",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/webView/wsbx/fykmdysz/fykmdysz_edit.jsp?"+suffix, "C");
   	});
    //提交按钮
    $(document).on("click","#btn_submit",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		checkbox.each(function(){
   			if($(this).attr("shzt")=='01'){
   				alert("该单据已经提交不允许再次提交!");
   				return;
   			}
   	   	});
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var alert1 = "是否提交？";
   		$.ajax({
   			type:"post",
   			url:"${ctx}/gwjdfsq/checkWhoSh",
   			data:"djbh=",
   			dataType:"json",
   			async:false,
   			success:function(val){
   				alert1 = "单据提交至:"+val.shr+"审核是否提交？";
   			}
   		})
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
   	    	confirm(alert1,"",function(){
   			$.ajax({
   	   			url:"${ctx}/gwjdfsq/submit",
   	   			data:"guid="+guid.join(","),
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("提交成功！");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
   		}else{
   			alert("请选择至少一条信息提交!");
   		}
   	});
  //单条提交
	$(document).on("click",".btn_submitxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		var alert1 = "是否提交？";
   		$.ajax({
   			type:"post",
   			url:"${ctx}/gwjdfsq/checkWhoSh",
   			data:"djbh=",
   			dataType:"json",
   			async:false,
   			success:function(val){
   				alert1 = "单据提交至:"+val.shr+"审核是否提交？";
   			}
   		})
   		confirm(alert1,"",function(){
   			$.ajax({
   	   			url:"${ctx}/gwjdfsq/submit",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("提交成功！");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
	});
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/gwjdfsq/doDelete",
	   	   			data:"guid="+guid.join(","),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
 
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/gwjdfsq/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
	});
	//查看
	$(document).on("click",".btn_view",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
	    var shzt=$("#txt_shzt").val();
   		doOperate("${ctx}/gwjdfsq/goEditPage?guid="+guid+"&shzt="+shzt+suffix,"V");
	});
	$(document).on("click",".btn_look",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
	    var shzt=$("#txt_shzt").val();
   		doOperate("${ctx}/gwjdfsq/goEditPage?guid="+guid+"&shzt="+shzt+suffix,"V");
	});
	//打印
	$(document).on("click",".btn_print",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val(); 
   		doOperate("${ctx}/gwjdfsq/demoPrint?guid="+guid);
	});
	//办理记录
   	$(document).on("click",".btn_record",function(){
   		var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=gwjdsp"+suffix,"C");
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