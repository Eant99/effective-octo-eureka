<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>人员外语水平</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
		<div class="col-md-12 tabs" style="padding-right: 0px">
			<ul id="tabTop" class="nav nav-tabs" role="tablist">
				<li role="presentation"><a id="tab_ryxx">人员信息</a></li>
				<li role="presentation"><a id="tab_lwqk">论文情况</a></li>
				<li role="presentation"  class="active"><a id="tab_wysp">外语水平</a></li>
				<li role="presentation"><a id="tab_jxqk">进修情况</a></li>
				<li role="presentation"><a id="tab_zzqk">著作情况</a></li>
				<li role="presentation"><a id="tab_cgjl">成果奖励</a></li>
			</ul>
		</div>
	</div>
		<div class="pull-left sub-title text-primary" style="margin-top: 6px;" >当前人员： <span style="color:red;font-weight:bolder"> ${xm}&emsp;&emsp;</span> </div> 
        <div class="btn-group pull-right" role="group"style="margin-top: 6px;" >
            <button type="button" class="btn btn-default" id="btn_add">增加</button>
            <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
            <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
           		<table id="mydatatables" class="table table-striped table-bordered">
	   				<thead>
		    			<tr>
			    			<th><input type="checkbox" class="select-all"/></th>
					    	<th>序号</th>
					        <th>语种</th>
					        <th>水平</th>
					        <th>操作</th>
		   	    		</tr>
    				</thead>
   	  				<tbody></tbody>
  				</table>
			</div>
		</div>    
	</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
   	//添加按钮
     $(document).on("click","#btn_add",function(){
   		select_commonWin("${ctx}/rywyspb/goRywyspbPage?operateType=C&rybh=${rybh}","外语水平","460","450");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
		select_commonWin("${ctx}/rywyspb/goRywyspbPage?operateType=U&rybh=${rybh}&guid="+guid+"","外语水平","460","450");
   	});
  	//返回按钮
    $(document).on("click","#btn_back",function(){
    	window.location.href = "${ctx}/ryb/goRybListPage";
   	});
    //人员信息
    $("#tab_ryxx").click(function(){
    	window.location.href = "${ctx}/ryb/goRybListPage";
   	});
    //论文情况
    $("#tab_lwqk").click(function(){
    	window.location.href ="${ctx}/rylwb/goLwqkListPage?rybh=${rybh}";
   	});
  //进修情况
    $("#tab_jxqk").click(function(){
    	window.location.href ="${ctx}/ryjxb/goJxqkListPage?rybh=${rybh}";
   	});
  //著作情况
    $("#tab_zzqk").click(function(){
    	window.location.href ="${ctx}/ryzzb/goZzqkListPage?rybh=${rybh}";
   	});
  //成果奖励
    $("#tab_cgjl").click(function(){
    	window.location.href ="${ctx}/rycgjlb/goCgjlListPage?rybh=${rybh}";
   	});
   	//单个删除
   	$(document).on("click",".btn_delxx",function(){
   		var guid = $(this).parents("tr").find("[name='guid']").val();
   		doDel("guid="+guid,"${ctx}/rywyspb/doDelete",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
   	//批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			var guid = [];
   			checkbox.each(function(){
   				guid.push($(this).val());
   			});
   			doDel("guid="+guid.join(","),"${ctx}/rywyspb/doDelete",function(val){
   	   			table.ajax.reload();
   	   			//....
   	   		},function(val){
   	   			//....
   	   		},guid.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   	});
  //列表数据
   	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "YZ",defaultContent:""},
	   	{"data": "SP",defaultContent:""},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":105}
	];
 	table = getDataTableByListHj("mydatatables","${ctx}/rywyspb/getWyspList?rybh=${rybh}",[2,'asc'],columns,"","",setGroup);
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