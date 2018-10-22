<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>在职薪资管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.table>thead>tr>th {
    padding: 12px;
/*     padding-top: 7px; */
/*     padding-right: 7px; */
/*     padding-bottom: px; */
/*     padding-left: 7px; */
}
	.dataTables_scrollHeadInner{
		width:800px ! important;
	}
	table.dataTable{
		width:800px ! important;
	}
	.bottom { 
    width: 99%;
    position: absolute;
    bottom: 27px;
    background-color: #f3f3f3;
}
	.td_input{
		border:none;
		width:100%;
	}
	.text-green{
		color:green!important;
	}
	th{
		text-align:center;
	}
	.table>tbody>tr.selected:nth-of-type(odd) {
 	background-color: #f9f9f9!important;
	}
	.table>tbody>tr.selected:nth-of-type(even) {
 	background-color: #f3f3f3!important;
	}
	
/* 设置了浏览器高度不小于1201px时 abc 显示800px高度 */  
/*  @media screen and (min-width: 770px) {   */
/*  .abc {height: 800px}   */
/*  }  */
   
 /* 设置了浏览器高度不大于900px时 abc 显示630px高度 */  
/*  @media screen and (max-heighe: 900px) {   */
/*  .abc {height: 630px;}   */
/*  }   */

</style>
</head>
<body style="height: 100%">
<div class="" style="background-color: white;">
	<div class="search" id="searchBox" style="padding-top: 0px;margin-bottom: 0px;">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				
				<div class="form-group">
					<label>项目简称</label>
			        <input type="text" id="" class="form-control input-radius" name="xmjc" table="t" placeholder="请输入项目简称" >
				</div>
				<div class="form-group">
					<label>项目名称</label>
			        <input type="text" id="" class="form-control input-radius" name="xmmc" table="t" placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				
				
				<div class="btn-group pull-right" role="group">
				    <button type='button' class="btn btn-default" id="btn_save">保存</button>
				</div>
			</div>
		</form>
	</div>

<form id="mysave" name="mysave" method="post"  >
<div class="container-fluid  " style="overflow: auto;">
		<div class='responsive-table' >
			<div class='scrollable-area'  > 
			<table id="mydatatables" class="table table-striped table-bordered"  >
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
				            <th style="text-align:center;" >项目简称</th>
				            <th style="text-align:center;">项目名称</th>
				        </tr>
					</thead>
				    <tbody>
				   </tbody>
				</table>					
		</div>
			</div>
		</div>	
	</form>		
		</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
// 	height();
// 	$(window).resize(function(){
// 		height();
// 	});
// 	function height(){
// 		$(".abc").height($(window).height()-$("#searchBox").outerHeight(true)-50);
// 	}
 $(function () {
	 	var columns = [
	 	      		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	 	      	       	return '<input type="checkbox" class="keyId" name="guid" shzt="'+full.SHZT+'" value="' + full.GUID + '">';
// 	 	      	       	return '<input type="checkbox" class="keyId" name="gid" shzt="'+full.SHZT+'" value="' + full.GUID + '"> <input type="hidden" class="keyId" name="guid" value="' + full.GUID + '">';
	 	      	    },"width":10,'searchable': false},
	 	      		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	 	      	   		return data;
	 	      		},"width":41,"searchable": false,"class":"text-center"},
	 	      		
	 	      		{"data": "XMJC",defaultContent:"",'render': function(data, type, full, meta){
	 	       		return '<input type="hidden"  name="guid1"  value = "'+full.GUID+'" ><input type="type"  name="xmjc" style="width:100%;height:100%;background-color:#F6F5F5;border:none;text-align:left;"  value = "'+full.XMJC+'" readonly>';
	 	        	}},
	 	      		{"data": "XMMC",defaultContent:"",'render': function(data, type, full, meta){
	 	       		return '<input type="type"  name="xmmc" style="width:100%;border:none;text-align:left;"  value = "'+full.XMMC+'" >';
	 	        	}},
	 	      	];
	 	        table = getDataTableByListHj("mydatatables","${ctx}/gzxmsz/getPageList",[2,'asc'],columns,0,1,setGroup);
	 

	 
 	//点击保存按钮
		$("#btn_save").click(function(){
			var json = $("#mysave").serializeObject("guid1","xmmc");  //json对象				
			var jsonStr = JSON.stringify(json);
			console.log(jsonStr);
			confirm("确定保存？","",function(){
			$.ajax({
				url:"${ctx}/gzxmsz/doSave",
				data:"jsonStr="+jsonStr,
				dataType:"json",
				async:"false",
				type:"post",
				success:function(msg){
					if($.trim(msg)=="true"){
// 						window.location.href="${ctx}/webView/xzgl/xzgl_show.jsp";
						alert("保存成功!");
						table.ajax.reload();
					}else{
						alert("保存失败!");
					}
				}
			});
			});
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