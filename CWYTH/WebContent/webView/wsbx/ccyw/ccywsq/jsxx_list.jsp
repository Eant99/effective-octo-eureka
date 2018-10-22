<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>教师信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>工号</label>
					<input type="text" id="txt_xh" class="form-control" name="xh" placeholder="请输入教师工号">
				</div>
				<div class="form-group">
					<label>姓名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm" placeholder="请输入教师姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-primary" id="btn_add">确定</button>
				</div>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
			<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong>先选中需要的信息，然后点击<b style="color: text-primary;">确定</b>按钮添加这条信息（可多选）。
			        </div> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>工号</th>
				            <th>姓名</th>
				            <th>所在部门</th>
				            <th>职务</th>
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
<%@include file="/static/include/public-manager-js.inc"%>
<script>
var target = "${ctx}/wsbx/ccyw/ccywsq";
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center",'render':function(data,type,full,meta){
    	   return data;
       },"width":41,'searchable':false,orderable:false},
       {"data": "XH",defaultContent:"","class":"gh"},
       {"data": "XM",defaultContent:"","class":"xm"},
       {"data": "SZDW",defaultContent:"","class":"szbm"},
       {"data": "ZW",defaultContent:"","class":"zw"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/jsxxs/getPageList",[2,'asc'],columns,0,1,setGroup);
	//确定按钮
   	$(document).on("click","#btn_add",function(){
   		var $arr_tr = $("#mydatatables").find("tr").has("[name='guid']:checked");
   		if($arr_tr.length>0){
   			getIframWindow("${param.pname}").addTxry($arr_tr);
   			var winId = getTopFrame().layer.getFrameIndex(window.name);
   			console.log(winId);
   			close(winId);
   		}else{
   			alert("请选择至少一条信息！");
   		}
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