<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目授权管理</title>
<%@include file="/static/include/public-manager-css.inc"%>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.dataTables_scrollBody{
height: 720px !important;
}

</style>
</head>
<body>
	<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" name="xmbh"  value="${xmbh}">
	<input type="hidden" name="bmbh"  value="${bmbh}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目授权所有信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_xzsq">新增授权</button>
		    <button type="button" class="btn btn-default" id="btn_plcx">批量撤销</button>	
			<button type="button" class="btn btn-default" id="btn_goback">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目授权信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
			<table id="mydatatables" class="table table-striped table-bordered tt">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>项目编号</th>
								<th>被授权人</th>
								<th>授权人</th>
								<th>开始时间</th>
								<th>截止时间</th>
								<th>是否允许二次授权</th>
								<th>备  注</th>
								<th>操作</th>						
							</tr>
						</thead>
						<tbody>
						</tbody>
					</table>	
		</div>
	</div>
	</div>
</form>
	<%@include file="/static/include/public-list-js.inc"%>
	<script>
$(function () {
	//列表数据
	var rBtnXh = 0;//单选框序号
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="keyId" class="keyId" value="' + data + '" guid = "'+full.GUID+'" bsqr="'+full.BSQR+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "XMBH",defaultContent:""},
       {"data": "BSQRMC",defaultContent:""},
       {"data": "SQRMC",defaultContent:""},
       {"data": "KSSJ",defaultContent:"","class":"text-center"},
       {"data": "JZSJ",defaultContent:"","class":"text-center"},
       {"data": "SFYXECSQMC",defaultContent:""},
       {"data": "BZ",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" dwbh = "'+full.GUID+'">修改</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" dwbh = "'+full.GUID+'" bsqr = "'+full.BSQR+'">撤销</a>';
     },orderable:false,"width":220,"class":"text-center"}
       
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xmsq/getinfoPageList",[4,'asc'],columns);
    
	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
   		var xmbh = $("[name=xmbh]").val();
		doOperate("${ctx}/xmsq/goEditPage?dwbh="+dwbh+"&xmbh="+xmbh,"U");
	});
	
	//授权日志
	$(document).on("click","#btn_info",function(){		
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		if(checkbox.length>0){
			doOperate("${pageContext.request.contextPath}/webView/wsbx/jcsz/xmsq/xmsqrz_list.jsp");
   		}else{
   			alert("请选择一条信息!");
   		}
		
	});
	
	//弹窗
	$("#btn_bsqr").click(function(){
		select_commonWin("${pageContext.request.contextPath}/window/rypage?controlId=txt_bsqr","人员信息","920","630");
    });
	
});
//返回按钮
$("#btn_goback").click(function(){
	window.location.href =  "${ctx}/webView/wsbx/jcsz/xmsq/xmsq_list.jsp";
});
//新增授权按钮
$("#btn_xzsq").click(function(){
// 	window.location.href =  "${ctx}/webView/wsbx/jcsz/xmsq/xmsqgl_edit.jsp";
	doOperate("${ctx}/xmsq/goEditPage?guid=${guid}&xmbh=${xmbh}&bmbh=${bmbh}","C");//operateType=C

});


	//撤销按钮
  	$(document).on("click",".btn_delxx",function(){
		
		var dwbh1 = $(this).attr("dwbh");
		var bsqr=$(this).attr('bsqr');
// 		alert(dwbh1);
// 		return;
		doDel("dwbh1="+dwbh1,"${ctx}/xmsq/doChexiao?xmbh=${xmbh}&bmbh=${bmbh}&bsqr="+bsqr,function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
		
	});
	
  //批量撤销按钮
   	$("#btn_plcx").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
//    		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
//    		if(djdw.length>0){
//    			alert("包含最顶级单位，不允许删除！");
//    		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			var bsqr=[];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   				bsqr.push($(this).attr('bsqr'))
   	   			});
   	   			doDel("dwbh="+dwbh.join("','"),"${ctx}/xmsq/doDelete?xmbh=${xmbh}&bmbh=${bmbh}&bsqr="+bsqr.join("','"),function(val){
   	   			table.ajax.reload();
   	   	   		},function(val){
   	   	   		},dwbh.length);
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
//    		}
   		
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