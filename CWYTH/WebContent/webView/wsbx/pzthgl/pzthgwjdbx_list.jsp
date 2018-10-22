<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证退回 差旅费报销列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control" name="djbh" table="K" placeholder="">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
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
				            <th>单据编号</th>
				            <th>申请人</th>
				            <th>申请日期</th>
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
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" class="keyId" djbh="'+full.DJBH+'"  procinstid="'+full.PROCINSTID+'" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
       {"data": "DJBH",defaultContent:"","class":"text-center"},
       {"data": "SQR",defaultContent:""},
       {"data": "SQRQ",defaultContent:"","class":"text-center"},
       {"data": "SZBM",defaultContent:""},
       {"data": "BXJE",defaultContent:"","width":80,'render':function(data,type,full,meta){
    	   return parseFloat(data).toFixed(2);
       },"class":"text-right"},
       {"data": "JDCS",defaultContent:""},
       {"data": "JDRQ",defaultContent:""},
       {"data": "",'render':function(data, type, full, meta){
    	   return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid="'+full.GUID+'" djbh="'+full.DJBH+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_look" guid="'+full.GUID+'">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_sub" guid="'+full.GUID+'">复核</a>|<a href="javascript:void(0);" class="btn btn-link btn_del" guid="'+full.GUID+'">删除</a>';	   		
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/bxpzth/getgwjdPageList?mkbh=${mkbh}",[2,'desc'],columns,0,1,setGroup);
	//查看按钮
	$(document).on("click",".btn_look",function(){
 		var guid = $(this).attr("guid");
 		location.href = "${ctx}/bxpzth/goGwjdbxEdit?operateType=L&guid="+guid;
 	});
 	//修改按钮
 	$(document).on("click",".btn_upd",function(){
	   	var guid = $(this).attr('guid');
	   	location.href = "${ctx}/bxpzth/goGwjdbxEdit?operateType=U&guid="+guid;
 	});
 	//单条删除
	$(document).on("click",".btn_del",function(){
		var guid = $(this).attr("guid");
		doDel("guid="+guid,"${pageContext.request.contextPath}/bxpzth/doDelete?type=gwjdbx",function(val){
			table.ajax.reload();
		},function(val){
			
		},"1");
	});
	//复核按钮
 	$(document).on("click",".btn_sub",function(){
	   	var guid = $(this).attr('guid');
	   	confirm("确定复核？","",function(){
	  	    $.ajax({
				type:"post",
				url:"${ctx}/bxpzth/GwjdbxSubmit",
				data:"guid="+guid,
				async:false,
				dataType:"json",
				success:function(val){
					if(val.success){
					  alert(val.msg);
					  table.ajax.reload();
					}
				}
	  	    });
  	 	});
		
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