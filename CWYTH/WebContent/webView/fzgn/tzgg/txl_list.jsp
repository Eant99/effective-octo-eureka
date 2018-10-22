<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset=UTF-8/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>通讯录信息</title>
<%@include file="/static/include/public-list-css.inc"%> 
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>人员工号</label>
					<input type="text" class="form-control input-radius" name="rybh"  placeholder="请输入人员工号">
				</div>
				<div class="form-group">
					<label>姓&emsp;&emsp;名</label>
					<input type="text" id="txt_xm" class="form-control input-radius" name="xm"  placeholder="请输入人员姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
				<button type="button" id="test" class="btn btn-default" >测试</button>
	                <button type="button" id="btn_add" class="btn btn-default" >增加</button>
	                <button type="button" id="btn_del" class="btn btn-default">批量删除</button>
	                <button type="button" id="btn_exp" class="btn btn-default" >导出Excel</button>
	                <button type="button" id="btn_imp" class="btn btn-default">数据导入</button>
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
					        <th>人员工号</th>
					        <th>姓名</th>
					        <th>办公地点</th>
					        <th>职务</th>
					        <th>办公电话</th>
					        <th>手机号</th>
					        <th>QQ</th>
					        <th>EMAIL</th>
					        <th>状态</th>
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
	   	$("#btn_add").click(function(){
	   		doOperate("${ctx}/txl/goEditPage","C");
	   	});
	  //添加测试
	   	$("#test").click(function(){
	  
	   	 $.ajax({
	         type: "post",
	         dataType: "json",
	         url: "http://localhost/apis/depart/depart_list",
	         success: function (msg) {
	        	 var json_data = JSON.stringify(msg); 
	        	 alert(json_data);

	         }
	       });
	   	});
	   	//修改按钮(A标签),注意后绑定事件的写法
	   	$(document).on("click",".btn_upd",function(){
		   	var id = $(this).parents("tr").find("[name='id']").val();
		   	doOperate("${ctx}/txl/goEditPage?id="+id,"U");
	   	});
	   	//删除按钮(A标签，单个删除)
	   	$(document).on("click",".btn_delxx",function(){
	   		var id = $(this).parents("tr").find("[name='id']").val();
	   		doDel("id="+id,"${ctx}/txl/doDelete",function(val){
	   			table.ajax.reload();
	   		},function(val){
	   			
	   		},"1");
	   	});
	   	$("#btn_rybh").click(function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
	    });
		//批量删除按钮
	   	$("#btn_del").click(function(){
	   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
	   		if(checkbox.length>0){
	   			var id = [];
	   			checkbox.each(function(){
	   				id.push($(this).val());
	   			});
	   			doDel("id="+id.join(","),"${ctx}/txl/doDelete",function(val){
	   	   			table.ajax.reload();
	   	   		},function(val){
	   	   		},id.length);
	   		}else{
	   			alert("请选择至少一条信息删除！");
	   		}
	   	});
		
	  //批量赋值按钮
	   	$("#btn_plfz").click(function(){
	   		var checkbox = $("#mydatatables").find("[name='ddbh']").filter(":checked");
	   		if(checkbox.length>0){
	   			var ddbh = [];
	   			checkbox.each(function(){
	   				ddbh.push($(this).val());
	   			});
	   			confirm("确认要批量赋值"+ddbh.length+"条信息？",{title:"提示"},function(index){
	   				select_commonWin("${ctx}/ddb/goPlfuzhiPage?ddbh="+ddbh.join(","),"批量赋值","400","450");
	   				close(index);
	   			}); 
	   		}else{
	   			alert("请选择至少一条信息赋值!");
	   		}
	   	});
	  //数据导入
		$("#btn_imp").click(function(){
			select_commonWin("${pageContext.request.contextPath}/txl/doImp?mkbh=${param.mkbh}", "导入通讯录信息", 450,350);
			return false;
		});
		//导出Excel
	   	$("#btn_exp").click(function(){
	   		var json = searchJson("searchBox");
	   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
			var id = [];
			checkbox.each(function(){
				id.push($(this).val());
			});
	   		doExp(json,"${ctx}/txl/expExcel","通讯录","${ctx}",id.join(","));
	   	});
	   	//列表数据
		var columns = [
			{"data": "GID",orderable:false, "render": function (data, type, full, meta){
		       	return '<input type="checkbox" class="keyId" name="id" value="' + data + '">';
		    },"width":10,'searchable': false},
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   		return data;
			},"searchable": false,"class":"text-center"},
		   	{"data": "RYGH",defaultContent:""},
		   	{"data": "XM",defaultContent:""},
		   	{"data": "BGDD",defaultContent:""},
		   	{"data": "ZW",defaultContent:""},
		   	{"data": "BDDH",defaultContent:""},
		   	{"data": "MOBLIE",defaultContent:""},
			{"data": "QQ",defaultContent:""},
			{"data": "EMAIL",defaultContent:""},
			{"data": "ZT",defaultContent:""},
		   	{"data":function (data, type, full, meta){
		   		return '<a class="btn btn-link btn_upd">编辑</a>|<a class="btn btn-link btn_delxx">删除</a>';
			},orderable:false,"width":95}
		];
	   	table = getDataTableByListHj("mydatatables","http://localhost:8083/CWPT/txl/getPageList?",[2,'asc'],columns,0,1,setGroup);

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