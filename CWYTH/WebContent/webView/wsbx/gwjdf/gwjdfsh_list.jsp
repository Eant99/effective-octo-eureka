<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>公务接待费审核</title>
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
						<select id="txt_shzt" class="form-control">
							<option value="0" <c:if test="${param.status eq 0 }">selected</c:if> >未审核</option>
							<option value="1" <c:if test="${param.status eq 1 }">selected</c:if> >已审核</option>
 						<!-- 	<input type="hidden" value="04" name="shzt" table="m"></input> -->
 						<%-- <input type="text" value="${param.shztm }" name="shzt" table="m"></input> --%>
						</select>
					</div>
					<div class="form-group">
						<label>单据编号</label> <input type="text" id="txt_djbh"
							class="form-control" name="djbh"  placeholder="请输入单据编号">
					</div>
					<div class="form-group">
						<label>申请人</label> <input type="text" id="txt_sqr"
							class="form-control" name="sqr"  placeholder="请输入人员编号">
					</div>
					<div class="form-group">
						<label>所在部门</label> <input type="text" id="txt_szbm"
							class="form-control" name="szbm"  placeholder="请输入部门编号">
					</div>
					<button type="button" class="btn btn-primary" id="btn_search">
						<i class="fa icon-chaxun"></i>查询
					</button>
					
					<div class="btn-group pull-right" role="group">
						<button type="button" class="btn btn-default plugin" id="btn_pltg">批量通过</button>
	              		 <button type="button" class="btn btn-default plugin" id="btn_plth">批量退回</button>
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
	var target = "${ctx}/gwjdfsh";
	var status = "${param.status}";
	
	
	 function anxs(){
		 if(status == "1"){
	   		$("#btn_pltg,#btn_plth").hide();
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
//   		window.location.href = "${ctx}/gwjdfsh/goListPage?status="+status+"&shztm="+status;
//   	});
	
	//审核状态改变
  	$("#txt_shzt").change(function(){
  		tableRelod(table);
  	});
   	
    var tableRelod = function(table) {
		var status=$("[id='txt_shzt']").val();
  	    table.ajax.url("${ctx}/gwjdfsh/getPageList?status="+status);
  	    table.ajax.reload();
  	};
	
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
            	var link = "";
        	   	var shzt = full.SHZTDM;
        	   	switch (shzt) {
        		case "0":
        			link = '<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>|' 
        			+ '<a href="javascript:void(0);" class="btn btn-link btn_verifyxx">审核</a>|' 
        			+ '<a href="javascript:void(0);" class="btn btn-link btn_record">办理记录</a>';
        			break;
        		case "1":
        			link = '<a href="javascript:void(0);" class="btn btn-link btn_view">查看</a>|' 
        			+ '<a href="javascript:void(0);" class="btn btn-link btn_record">办理记录</a>';
        		default:
        			break;
        		}
        	   	return link;
      },orderable:false,"width":220}
     ];
       table = getDataTableByListHj("mydatatables","${ctx}/gwjdfsh/getPageList?status="+$("#txt_shzt").val(),[3,'desc'],columns,0,1,setGroup);
/*     table = getDataTableByListHj("mydatatables","${ctx}/gwjdfsh/getPageList?shztm="+"${param.shztm}",[2,'asc'],columns,0,1,setGroup);
 */  
      // table = getDataTableByListHj("mydatatables","${ctx}/gwjdfsh/getPageList",[2,'asc'],columns,0,1,setGroup);

 //
  //导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/gwjdfsh/expExcel?status=${param.status}&shztm=${param.shztm}","公务接待费审核信息","${ctx}",guid.join(","));
//    	});
 $(document).on("click","#btn_exp",function(){
	 var id = [];
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var status = $("#txt_shzt").val();
		if(checkbox.length > 0){
		checkbox.each(function(){
			id.push($(this).val());
		});
		id = id.join("','");
		}else{
			id = "";
		}
		doExp("","${ctx}/gwjdfsh/expExcel2?status="+status,"公务接待审核","${pageContext.request.contextPath}",id);
		
// 				var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/gwjdfsh/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","往来单位设置信息.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
  //批量通过,退回
  $(document).on("click","#btn_pltg",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
			var procinstid = [];
			checkbox.each(function() {
				guid.push($(this).val());
				procinstid.push($(this).parents("td").find(".procinstid").val());
			});
	   	 	select_commonWin(target+"/pageSkip?pageName=check&guid="+guid.join(",")+"&procinstid="+procinstid.join(",")+"&status=pass","出差业务审核","500","300");
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
	   	 	select_commonWin(target+"/pageSkip?pageName=check&guid="+guid.join(",")+"&procinstid="+procinstid.join(",")+"&status=false","出差业务审核","500","300");
   		}else{
   			alert("请选择至少一条信息审核!");
   		}
   	});
  //审核,查看
   	$(document).on("click",".btn_verifyxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var procinstid=$(this).parents("tr").find("[name='procinstid']").val();
   		doOperate("${ctx}/gwjdfsh/goEditPage?guid="+guid+"&procinstid="+procinstid+"&shztm=${param.shztm}","U");
   	});
	$(document).on("click",".btn_view",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/gwjdfsh/goEditPage?guid="+guid+"&shztm="+"${param.shztm}","V");
	});
	$(document).on("click",".btn_look",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doOperate("${ctx}/gwjdfsh/goEditPage?guid="+guid+"&shztm="+"${param.shztm}","V");
	});
});
//办理记录
	$(document).on("click",".btn_record",function(){
		var processInstanceId=$(this).parents("tr").find("[name='procinstid']").val();
		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=gwjdsp","C");
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