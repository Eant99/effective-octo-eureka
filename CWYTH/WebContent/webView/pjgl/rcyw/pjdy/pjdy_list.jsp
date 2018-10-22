<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>票据打印</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
#btns{
	margin-top: 10px !important;	
	}
</style>

<body>
<div class="fullscreen">
    <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>票据号</label>
					<input type="text" id="txt_xmlxbh" class="form-control" name="pjh" table="K" placeholder="请输入票据号">
				</div>
				<div class="form-group">
					<label>收款人姓名</label>
					<input type="text" id="txt_xmlxbh" class="form-control" name="SKR" table="K" placeholder="收款人姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
<!--  					<button type="button" class="btn btn-default" id="btn_printsz">打印设置</button> -->
<!-- 					<button type="button" class="btn btn-default" id="btn_printpl">打印</button> -->
<!-- 					<button type="button" class="btn btn-default" id="btn_chosCol">列选择</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_upd">修改</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
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
				            <th style="text-align:center">序号</th>
				            <th style="text-align:center" >票据号</th>
				            <th style="text-align:center" >票据类型</th>
				            <th style="text-align:center" >收款人</th>
				            <th style="text-align:center" >用途</th>
				            <th style="text-align:center" >出票人账号</th>
				            <th style="text-align:center" >付款行名称</th>
<!-- 				            <th style="text-align:center" >附加信息</th> -->
				            <th style="text-align:center" >出票金额</th>
<!-- 				            <th style="text-align:center" >是否打印</th> -->
				            <th style="text-align:center" >出票/入账日期</th>
				            <th style="text-align:center" >操作</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<%-- <script src="${pageContext.request.contextPath}/static/javascript/public/public-checked.js"></script> --%>
<script>
$(function () {
	var columns = [
	       {"data": "GID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="gid" class="keyId " value="' + data + '" gmgid="'+full.GMGID+'" pjlx = "'+full.PJLX+'">';
	       },"width":10,'searchable': false,"class":"text-center"},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":20,"searchable":false,"class":"text-center"},
	       {"data": "PJH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "PJLXMC",'render':function(data,type,full,meta){
	    	    if(data==undefined){
	    	    	return '';
	    	    }else{
	          		return '<span id="pjlxmc">'+data+'</span>';
	    	    }
	        },"width":40,"class":"text-left",},
	       {"data": "SKR",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "YT",defaultContent:"","width":30,"class":"text-left",},
	       {"data": "CPRZH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "FKHMC",defaultContent:"","width":40,"class":"text-left",},
	       //{"data": "FJXX",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "CPJE",defaultContent:"","width":40,"class":"text-right",},
// 	       {"data": "SFDY",defaultContent:"","width":30,"class":"text-left",},
	       {"data": "CPRQ",defaultContent:"","width":40,"class":"text-center",},
	       {"data": "",'render':function(data,type,full,meta){
	          	if(full.ZT == '30'){//已报销
	          		return '<a href="javascript:void(0);" class="btn btn-link btn_print" onclick="print(this)">打印</a>'
	          	}else{//20 开票的
	          		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" onclick="upd(this)">修改</a>';
	          	}
	          	},"class":"text-center",orderable:false,"width":80}
	     ];
	
	    table = getDataTableByListHj("mydatatables","${ctx}/pjgl/rcyw/pjdy/getPageList",[2,'asc'],columns,0,1,setGroup);
});
//打印
function print(obj){
	var gid = $(obj).parents("tr").find("[name='gid']").val();
	var gmgid = $(obj).parents("tr").find("[name='gid']").attr("gmgid");
	var pjlx = $(obj).parents("tr").find("[name='gid']").attr("pjlx");
	var mc = $(obj).parents("tr").find("#pjlxmc").text();
	doOperate("${ctx}/pjgl/rcyw/pjdy/goPrintPage?gmgid"+gmgid+"&gid="+gid+"&pjlx="+pjlx+"&mc="+mc);
}
	
//修改
function upd(obj){
	var gid = $(obj).parents("tr").find("[name='gid']").val();
	var pjlx = $(obj).parents("tr").find("[name='gid']").attr("pjlx");
	var mc = $(obj).parents("tr").find("#pjlxmc").text();
	console.log(pjlx)
   select_commonWin("${ctx}/pjgl/rcyw/pjdy/goEditPage?gid="+gid+"&pjlx="+pjlx+"&mc="+mc,"修改","900","550");
}
$(function() {	
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</html>