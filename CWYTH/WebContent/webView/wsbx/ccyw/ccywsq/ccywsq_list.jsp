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
<%-- 						<c:forEach items="${shztList}" var="item"> --%>
<%-- 							<option value="${item.DM }" <c:if test="${item.DM eq param.shzt }">selected</c:if>>${item.MC }</option> --%>
<%-- 						</c:forEach> --%>
<%-- 						<option value="all" <c:if test="${'all' eq param.shzt }">selected</c:if>>全部</option> --%>
						<option value="00" <c:if test="${param.shzt=='00'}">selected</c:if>>未提交</option>
	                	<option value="11" <c:if test="${param.shzt=='11'}">selected</c:if>>审核中</option>
	                	<option value="99" <c:if test="${param.shzt=='99'}">selected</c:if>>已通过</option>
	                	<option value="all" <c:if test="${param.shzt=='all'}">selected</c:if>>全部</option>
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
				<table id="mydatatables" class="table table-striped table-bordered" >
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
				            <th>出差天数（天）</th>
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
//基本路径
var basePath = "${ctx}/wsbx/ccyw/ccywsq";
//审核状态
var shzt = "${param.shzt}";
//路径后缀
var suffix = "&mkbh=${param.mkbh}";
//封装表格对象
var columns = [
   {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	   return '<input type="checkbox" name="guid" data-shzt="'+full.CHECKSHZT+'" data-procinstid="'+full.PROCINSTID+'"  class="keyId" xmguid="'+full.XMGUID+'" djbh="'+full.DJBH+'"   value="' + data + '" guid = "'+full.GUID+'">';
   },"width":10,'searchable': false},
   {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
      	return data;},"width":41,"searchable":false,"class":"text-left"},
   {"data": "SHZT",defaultContent:"","class":"text-left"},
   {"data": "DJBH","class":"text-left","render":function(data,type,full,meta){
	    return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>'; 
   }},
   {"data": "SQRXM",defaultContent:""},
   {"data": "SZBM",defaultContent:""},
   {"data": "CCLX",defaultContent:""},
   {"data": "XMMC",defaultContent:"","width":250,"class":"text-left"},
   {"data": "CCTS",defaultContent:"","class":"text-right",width:10},
   {"data": "SQRQ",defaultContent:"","class":"text-center","width":100},
   {"data": "A.GUID",'render':function(data, type, full, meta){
	   	var link = "";
	   	var shzt = full.SHZTDM;
	 	if(shzt=="01"){
	 		link = '<a href="javascript:void(0);" class="btn btn-link btn_edit">&nbsp;编辑&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">&nbsp;删除&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">&nbsp;提交&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;</a>';
	 	}else if(shzt!="01"&&full.CHECKSHZT=="00"){
	 		link = '<a href="javascript:void(0);" class="btn btn-link btn_edit">&nbsp;编辑&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_delxx">&nbsp;删除&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_submitxx">&nbsp;提交&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
	 	}else if(full.CHECKSHZT=="11"){
	 		link = '<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
	 	}else{
	 		link = //'<a href="javascript:void(0);" class="btn btn-link btn_print">&nbsp;打印&nbsp;</a>'+
    			'<a href="javascript:void(0);" class="btn btn-link btn_view">&nbsp;查看&nbsp;|</a>'
    			+'<a href="javascript:void(0);" class="btn btn-link btn_record">&nbsp;办理记录&nbsp;</a>';
	 	}
	   	return link;
  },orderable:false,"class":"text-center","width":100}
 ];
 //加载表格
/**
 * 根据审核状态控制表格上方操作按钮显示
 * a_shzt	审核状态，string
 */
function anxs(a_shzt){
	$(".plugin").hide();
	switch (a_shzt) {
	case "01":
	case "04":
	case "00":
		$("#btn_submit,#btn_del").show();
		break;
	default:
		break;
	}
}

    var tableRelod = function(table) {
		var shzt=$("[id='txt_shzt']").val();
		anxs(shzt);
  	    table.ajax.url(basePath+"/getCcywsqPageData?shzt="+shzt);
  	    table.ajax.reload();
  	};
$(function () {
table = getDataTableByListHj("mydatatables",basePath+"/getCcywsqPageData?shzt=${param.shzt}",[2,'asc'],columns,0,1);
tableRelod(table);
	//审核状态改变
  	$("#txt_shzt").change(function(){
  		tableRelod(table);
  	});
   	

    //添加,批量提交，批量删除，导出excel
    $(document).on("click","#btn_add",function(){
   		var mkbh = "${param.mkbh}";
   		var sftzxz = querySftzxz(mkbh);
   		if(sftzxz){
   			alert("该模块已经禁止新增单据！");
   		}else{
		   	window.location.href = basePath+"/goCcywsqAddPage?"+suffix;
   		}
   	});
    $(document).on("click","#btn_submit",function(){
    	var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
    	var guid = $(this).parents("tr").find("[name='guid']").val();
   		var alert = "是否提交？";
   		$.ajax({
   			type:"post",
   			url:basePath+"/checkWhoSh",
   			data:"djbh=",
   			dataType:"json",
   			async:false,
   			success:function(val){
   				alert = "单据提交至:"+val.shr+"审核，是否提交？";
   			}
   		});
    	confirm(alert, {title:"提示"}, function(){
	   		if(checkbox.length>0){
	   			var guid = [];
		   	   	checkbox.each(function(){
		   	   		guid.push($(this).val());
		   	   	});
		   	   	var url = basePath+"/submit";
		   	 	doSubNoAlert("guid="+guid.join(","),url,function(){
		   	   		table.ajax.reload();
		   	   	},"",checkbox.length);
	   		}else{
	   			alert("请选择至少一条信息提交!");
	   		}
    	});
    });
	$(document).on("click",".btn_submitxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		var alert = "是否提交？";
   		$.ajax({
   			type:"post",
   			url:basePath+"/checkWhoSh",
   			data:"djbh=",
   			dataType:"json",
   			async:false,
   			success:function(val){
   				alert = "单据提交至:"+val.shr+"审核，是否提交？";
   			}
   		});
   		confirm(alert, {title:"提示"}, function(){
	   		var url = basePath+"/submit";
	   		doSubNoAlert("guid="+guid,url,function(){
	   			table.ajax.reload();
	   		},"",1);
   		});
   	});
	$(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
	   	   	checkbox.each(function(){
	   	   		guid.push($(this).val());
	   	   	});
	   	   	var url = basePath+"/ccywsqDelete";
	   	   	doDel("guid="+guid.join("','"),url,function(){
	   	   		table.ajax.reload();
	   	   	},"",checkbox.length);
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
   	
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
		doExp("","${ctx}/wsbx/ccyw/ccywsq/expExcel?shzt="+shzt,"出差业务申请","${pageContext.request.contextPath}",id,"");
});
   	//编辑,单条删除,单挑提交，查看，打印
   	$(document).on("click",".btn_edit",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
	   	window.location.href = basePath+"/goCcywsqEditPage?guid="+guid+suffix+"&type=U";
   	});
	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
 		var url = basePath+"/ccywsqDelete";
		doDel("guid="+guid,url,function(){
			table.ajax.reload();
		},"",1);
	});
   
	$(document).on("click",".btn_view",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
 		window.location.href = basePath+"/goCcywsqViewPage?guid="+guid+"&action=view&link=sq"+suffix;
	});
	$(document).on("click",".btn_look",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
 		window.location.href = basePath+"/goCcywsqViewPage?guid="+guid+"&action=view&link=sq"+suffix;
	});
	$(document).on("click",".btn_print",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
		var pId=$(this).parents("tr").find("[name='guid']").attr('data-procinstid');
   		var url = basePath+"/goPrintPage?guid="+guid+"&procinstid="+pId;
   		window.location.href=url;
   	});
   	//办理记录
   	$(document).on("click",".btn_record",function(){
   		var processInstanceId=$(this).parents("tr").find(".keyId").data("procinstid");
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=ccyw"+suffix,"C");
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