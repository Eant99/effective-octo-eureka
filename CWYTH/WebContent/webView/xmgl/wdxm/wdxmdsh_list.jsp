<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支出录入</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	
	<div class="container-fluid" >
	<div class="btn-group pull-right" role="group" style="margin-top:5px;">
	               <button type="button" class="btn btn-default" id="btn_fh">返回</button>
				</div>
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>项目编号</th>
				            <th>项目名称</th>
				            <th>项目负责人</th>
				            <th>报销时间</th>
				            <th>报销人</th>
				            <th>报销金额（元）</th>
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
$(function () {
	//列表数据
   var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" >';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "XMBH",defaultContent:"","class":"text-center"},
       {"data": "XMMC",defaultContent:"","class":"text-center"},
       {"data": "XMFZR",defaultContent:"","class":"text-center"},
       {"data": "BXSJ",defaultContent:"","class":"text-center"},
       {"data": "BXR",defaultContent:"","class":"text-center"},
       {"data": "BXZJE",defaultContent:"","class":"text-right"}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/wdxm/getDshPage?guid=${guid}",[2,'asc'],columns,0,1,setGroup);
  	//添加按钮
   	$("#btn_add").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   		guid.push($(this).val());
   	   		});
   	   	doOperate("${ctx}/zclr/goEditPage","C");
   		}else{
   			alert("请选择一条项目信息进行录入!");
   		}
   	});
	//导出Excel
   	$("#btn_exp").click(function(){
   		alert("导出成功");
   	});
   	//查看按钮
   	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		window.location.href="${ctx}/wdxm/goMxPage?guid="+guid;
   	});
  //返回
  $(document).on("click","#btn_fh",function(){
	 window.location.href="${ctx}/wdxm/getWdxm"; 
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