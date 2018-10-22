<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>岗位交接</title>
	<%@include file="/static/include/public-list-css.inc"%> 
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>原权限所有人</label>
					<div class="input-group">
						<input type="text" id="txt_yqxsyr" class="form-control input-radius" name="yqxsyr" value="" types="R" table="K" placeholder="请选择原权限所有人">
						<span class="input-group-btn"><button type="button" id="btn_yqxsyr" class="btn btn-link ">选择</button></span>
					</div>
				</div>
				<div class="form-group">
					<label>接&ensp;岗&ensp;人</label>
					<div class="input-group">
						<input type="text" id="txt_jgr" class="form-control input-radius" name="jgr" value="" types="R" table="K" placeholder="请选择接岗人">
						<span class="input-group-btn"><button type="button" id="btn_jgr" class="btn btn-link ">选择</button></span>
					</div>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
	                <button type="button" id="btn_add" class="btn btn-default" >增加</button>
	                <button type="button" id="btn_exp" class="btn btn-default" >导出Excel</button>
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
					        <th>业务发起人</th>
					        <th>业务发起人电话</th>
					        <th>原权限所有人</th>
					        <th>原权限所有人电话</th>
					        <th>接岗人</th>
					        <th>接岗人电话</th>
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
	   	//联想输入提示
	   	$("#txt_yqxsyr").bindChange("${ctx}/suggest/getXx","R");
		$("#txt_jgr").bindChange("${ctx}/suggest/getXx","R");
		//查询弹窗
	   	$("#btn_yqxsyr").click(function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_yqxsyr","人员信息","920","630");
	    });
	   	$("#btn_jgr").click(function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_jgr","人员信息","920","630");
	    });
		//列表数据
	   	var columns = [
				{"data": "GID",orderable:false, "render": function (data, type, full, meta){
					return '<input type="checkbox" class="keyId" name="gid" value="' + data + '">';
			    },"width":10,'searchable': false},
				{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
			   		return data;
				},"searchable": false,"class":"text-center"},
			   	{"data": "YWFQR",'render':function (data, type, full, meta){
				   	return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.YWFQRBH==undefined?'':full.YWFQRBH)+'" path="${ctx}">'+ (full.YWFQR==undefined?'':full.YWFQR) +'</a>';
				}},
			   	{"data": "YWFQRDH",defaultContent:""},
			   	{"data": "YQXSYR",'render':function (data, type, full, meta){
				   	return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.YQXSYRBH==undefined?'':full.YQXSYRBH)+'" path="${ctx}">'+ (full.YQXSYR==undefined?'':full.YQXSYR) +'</a>';
				}},
			   	{"data": "YQXSYRDH",defaultContent:""},
			   	{"data": "JGR",'render':function (data, type, full, meta){
				   	return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.JGRBH==undefined?'':full.JGRBH)+'" path="${ctx}">'+ (full.JGR==undefined?'':full.JGR) +'</a>';
				}},
			   	{"data": "JGRDH",defaultContent:""},
			   	{"data": "GID",'render':function (data, type, full, meta){
			   		return '<a class="btn btn-link btn_view">查看</a>';
				},orderable:false,"width":80}
			];
 		   	table = getDataTableByListHj("mydatatables","${ctx}/gwjj/getPageList",[2,'asc'],columns,0,1);
	   		   	
		//添加按钮
	   	$("#btn_add").click(function(){
	   		doOperate("${ctx}/gwjj/goEditPage","C");
	   	});
	   	//查看按钮
	   	$(document).on("click",".btn_view",function(){
		   	var gid = $(this).parents("tr").find("[name='gid']").val();	
			doOperate("${ctx}/gwjj/goEditPage?gid="+gid,"L");
	   	});
		
		//导出Excel
	   	$("#btn_exp").click(function(){
	   		var json = searchJson("searchBox");
	   		var checkbox = $("#mydatatables").find("[name='gid']").filter(":checked");
			var id = [];
			checkbox.each(function(){
				id.push($(this).val());
			});
	   		doExp(json,"${ctx}/gwjj/expExcel","岗位交接信息","${ctx}",id.join(","));
	   	});
		
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