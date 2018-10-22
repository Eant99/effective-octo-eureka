<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据字典维护</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	select{
		font-size:12px !important; 
		max-height: 25px !important;  
		padding: 0px !important;
		padding-left: 5px !important;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>代码名称</label>
					<input type="text" class="form-control input-radius" name="mc"  table="K" placeholder="请输入代码名称">
				</div>
				<div class="form-group">
					<label>代码编号</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="dm"  table="K" placeholder="请输入代码编号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button class="btn btn-default" type="button" id="btn_add">增加</button>
	                <button class="btn btn-default" type="button" id="btn_del">批量删除</button>
            	</div>
			</div>
        </form>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
		    	<input type="hidden" name="dmxh" value="${dmxh}"/>
		        <table id="mydatatables" class="table table-striped table-bordered">
					<thead>
		 				<tr>
			 				<th><input type="checkbox" class="select-all"/></th>
			 				<th>序号</th>
						    <th>代码编号</th>
						    <th>代码名称</th>
<!-- 						    <th>级别</th> -->
<!-- 						    <th>说明</th> -->
							<th>上级代码</th>
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
   	$("#more").bind('click', function (){
       	$(this).parents(".box-content").find(".row:not(:first):not(:nth-child(2))").toggleClass("hidden");
        $(this).toggleClass("dropup");
    });
   	//添加按钮
   	$(document).on("click","#btn_add",function(){
//     $("#btn_add").click(function(){
    	select_commonWin("${ctx}/dmb/goEditPage?operateType=C","数据字典信息","400","450");
   	});
   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var dmxh = $(this).parents("tr").find("[name='dmxh']").val();
 	   select_commonWin("${ctx}/dmb/goEditPage?operateType=U&dmxh="+dmxh,"数据字典信息","400","450");
   	});
   	//单个删除
   	$(document).on("click",".btn_delxx",function(){
   		var dmxh = $(this).parents("tr").find("[name='dmxh']").val();
   		doDel("dmxh="+dmxh,"${ctx}/dmb/doDelete",function(val){
   			table.ajax.reload();
   			//....
   		},function(val){
   			//....
   		},"1");
   	});
   	//批量删除按钮
   	$(document).on("click","#btn_del",function(){
//    	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dmxh']").filter(":checked");
   		if(checkbox.length>0){
   			var dmxh = [];
   			var flag = true;
   			checkbox.each(function(){
   				dmxh.push($(this).val());
   				if($(this).attr("bz") =="1"){
   					flag = false;
   				}
   			});
   			if(flag){
   				doDel("dmxh="+dmxh.join(","),"${ctx}/dmb/doDelete",function(val){
   	   	   			table.ajax.reload();
   	   	   		},function(val){
   	   	   		},dmxh.length);
   			}else{
   				alert("系统定义的信息不能删除！");
   			}
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   	});
  //列表数据
   	var columns = [
		{"data": "DMXH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="dmxh" bz="'+full.BZS+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "DM",defaultContent:""},
	   	{"data": "MC",defaultContent:""},
		{"data":"SJQC",defaultContent:""},
	   	{"data":"DMXH","render":function (data, type, full, meta){
	   		if(full.BZS != "1"){
	   			return '<a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
	   		}else return '';
	   	},orderable:false,"width":90}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/dmb/getPageList?treeDm=${param.dm}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
    //综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
});
function reloadList(){
	table.ajax.reload();
}
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