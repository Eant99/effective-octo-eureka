<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>绩效指标设定</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">		
		<form id="myform" class="form-inline" action="" style="padding-top: 10px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目负责人</label>
					<input type="text" id="txt_wlbm" class="form-control" name="xmfzr" table="A" placeholder="请输入项目负责人">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_dwmc" class="form-control" name="xmmc"  table="A" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				
				<div class="btn-group pull-right" role="group">
<!-- 	               <button type="button" class="btn btn-default" id="btn_add">增加</button> -->
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
				            <th>项目负责人</th>
				            <th>项目名称</th>
				            <th>所属部门</th>
				            <th>实际得分</th>
				            <th>审核结果</th>
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data":"XMFZR","width":100,"render":function(data,type,full,mate){
    	   return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
       }},
       {"data": "XMMC",defaultContent:""},
       {"data": "BM",defaultContent:""},
       {"data": "SJDF",defaultContent:""},
       {"data": "SHJG",defaultContent:""},
       {"data": "",'render':function(data, type, full, meta){
//	   		return '<a href="javascript:void(0);" guid="'+full.GUID+'" class="btn btn-link btn_upd">编辑</a>';
	   		return '<a href="javascript:void(0);" guid="'+full.GUID+'" class="btn btn-link btn_upd">审核</a>|<a href="javascript:void(0);" dm="'+full.GUID+'" class="btn btn-link btn_delxx">删除</a>';
  	   },orderable:false,"width":60}
      
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/jxpjsh/getPageList",[4,'asc'],columns,0,1,setGroup);
  	//添加按钮
   	$("#btn_add").click(function(){
   		//doOperate("${ctx}/wldwsz/goEditPage");
   		location.href="${ctx}/webView/jxgl/jxzbsd/jxzbsd_edit.jsp";
   	});
  //编辑按钮
   	$(document).on("click",".btn_upd",function(){
//    		var guid = $(this).attr("guid");
//    		doOperate("${ctx}/wldwsz/goEdit1Page?guid="+guid,"U");
//    	    location.href="${ctx}/webView/jxgl/jxzbsd/jxzbsd_edit.jsp";
   		select_commonWin("${pageContext.request.contextPath}/webView/jxgl/jxpjshtc.jsp","绩效评价审核","400","250");
   	});  	
   	//查看按钮
//    	$(document).on("click",".btn_look",function(){
//    		var guid = $(this).attr("guid");
//    		doOperate("${ctx}/wldwsz/goLookPage?guid="+guid,"L");
//    	});
    //单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).attr("guid");
 		alert("删除成功！")
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