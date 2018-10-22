<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>地点信息</title>
	<%@include file="/static/include/public-list-css.inc"%> 
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>地&ensp;点&ensp;号</label>
					<input type="text" id="txt_ddbh" class="form-control input-radius" name="ddbh" table="D" placeholder="请输入地点号">
				</div>
				<div class="form-group">
					<label>地点名称</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="mc" table="D" placeholder="请输入地点号">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx">
						<i class="fa icon-btn-gjcx"></i>
						高级查询
					</span>
					<div class="search-more">
						<div class="form-group">
							<label>所属单位</label>
							<div class="input-group">
								<input type="text" id="txt_dwmc" class="form-control input-radius" name="dwbh" table="D" types="D" placeholder="请选择所属单位">
								<span class="input-group-btn"><button type="button" id="btn_dwmc" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>上级地点</label>
							<div class="input-group">
								<input type="text" id="txt_sjddmc" class="form-control input-radius" table="D" types="P" name="sjdd" placeholder="请选择上级地点">
								<span class="input-group-btn"><button type="button" id="btn_sjddmc" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>地点状态</label>
							<select id="drp_ddzt" class="form-control" style="width:152px;" name="ddzt" table="D">
	             		 		<option value="">未选择</option>
	                       		<option value="1">正常</option>
	                       		<option value="0">禁用</option>
	                   		</select>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	                <button type="button" id="btn_add" class="btn btn-default" >增加</button>
	                <button type="button" id="btn_del" class="btn btn-default">批量删除</button>
	                <button type="button" id="btn_plfz" class="btn btn-default">批量赋值</button>
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
					        <th>地点号</th>
					        <th>地点名称</th>
					        <th>所属单位</th>
					        <th>上级地点</th>
					        <th>地点状态</th>
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
	   	$("#txt_dwmc").bindChange("${ctx}/suggest/getXx","D");
		$("#txt_sjddmc").bindChange("${ctx}/suggest/getXx","P");
		//添加按钮
	   	$("#btn_add").click(function(){
	   		select_commonWin("${ctx}/ddb/goEditPage?operateType=C&ddbh=${param.ddbh}","地点信息","400","460");
	   	});
	   	//修改按钮(A标签),注意后绑定事件的写法
	   	$(document).on("click",".btn_upd",function(){
		   	var ddbh = $(this).parents("tr").find("[name='ddbh']").val();	
	   		select_commonWin("${ctx}/ddb/goEditPage?operateType=U&ddbh="+ddbh,"地点信息","400","460");
	   	});
	   	//删除按钮(A标签，单个删除)
	   	$(document).on("click",".btn_delxx",function(){
	   		var ddbh = $(this).parents("tr").find("[name='ddbh']").val();
	   		doDel("ddbh="+ddbh,"${ctx}/ddb/doDelete",function(val){
	   			parent.location.href = "${ctx}/window/mainDdTree?pageUrl=/ddb/goDdbPage&treeJson=/glqxb/ddTree";
	   			//....
	   		},function(val){
	   			//....
	   		},"1");
	   	});
	   	
		//查看功能（单机地点名称超链接（A标签））
	   	$(document).on("click",".btn_look",function(){
	   		var ddbh = $(this).parents("tr").find("[name='ddbh']").val();
	   		select_commonWin("${ctx}/ddb/goLookPage?operateType=L&ddbh="+ddbh,"地点信息","400","460");
	   	});
	   	
		//批量删除按钮
	   	$("#btn_del").click(function(){
	   		var checkbox = $("#mydatatables").find("[name='ddbh']").filter(":checked");
	   		var djdd = $("#mydatatables").find(".djdd").filter(":checked");
	   		if(djdd.length>0){
	   			alert("包含最顶级地点，不允许删除！");
	   		}else{
	   			if(checkbox.length>0){
		   			var ddbh = [];
		   			checkbox.each(function(){
		   				ddbh.push($(this).val());
		   			});
		   			doDel("ddbh="+ddbh.join(","),"${ctx}/ddb/doDelete",function(val){
		   				parent.location.href = "${ctx}/window/mainDdTree?pageUrl=/ddb/goDdbPage&treeJson=/glqxb/ddTree";
		   	   			//....
		   	   		},function(val){
		   	   			//....
		   	   		},ddbh.length);
		   		}else{
		   			alert("请选择至少一条信息删除！");
		   		}
	   		}
	   		
	   	});
		
	  //批量赋值按钮
	   	$("#btn_plfz").click(function(){
	   		var checkbox = $("#mydatatables").find("[name='ddbh']").filter(":checked");
	   		var djdd = $("#mydatatables").find(".djdd").filter(":checked");
	   		if(djdd.length>0){
	   			alert("包含最顶级地点，不允许进行批量赋值，请单独修改！");
	   		}else{
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
	   		}
	   		
	   	});
		//导出Excel
	   	$("#btn_exp").click(function(){
	   		var json = searchJson("searchBox");
	   		var checkbox = $("#mydatatables").find("[name='ddbh']").filter(":checked");
			var id = [];
			checkbox.each(function(){
				id.push($(this).val());
			});
	   		doExp(json,"${ctx}/ddb/expExcel?treeddbh=${ddbh}","地点信息","${ctx}",id.join(","));
	   	});
	
	   	//列表数据
		var columns = [
			{"data": "DDBH",orderable:false, "render": function (data, type, full, meta){
				if(data=='000001'){
					return '<input type="checkbox" class="keyId djdd" name="ddbh" value="' + data + '">';
				}else{
					return '<input type="checkbox" class="keyId" name="ddbh" value="' + data + '">';
				}
		       	
		    },"width":10,'searchable': false},
			{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   		return data;
			},"searchable": false,"class":"text-center"},
		   	{"data": "DDH",defaultContent:""},
		   	{"data": "MC",'render':function (data, type, full, meta){
			   	return '<a  class="btn btn-link btn_look">'+ data +'</a>';
			}},
			{"data": "DWBH",defaultContent:""},
			//{"data": "DWMC",defaultContent:"",orderable:false},
			/* {"data": "DWBH","render":function (data, type, full, meta){
	    		return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.DW==undefined? '': full.DW)+'" path="${ctx}">'+ (data==undefined ? '':data) +'</a>';
			}}, */
		   	{"data": "SJDD",defaultContent:""},
			//{"data": "SJDD","render":function (data, type, full, meta){
			//return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.DD==undefined? '': full.DD)+'" path="${ctx}">'+ (data==undefined ? '':data) +'</a>';
			//}},
		   	{"data": "DDZT",defaultContent:""},
		   	{"data": "DDH",'render':function (data, type, full, meta){
		   		if(data=='000001'){
		   			return '<a class="btn btn-link btn_upd">编辑</a>';
		   		}else{
		   			return '<a class="btn btn-link btn_upd">编辑</a>|<a  class="btn btn-link btn_delxx">删除</a>';
		   		}
			},orderable:false,"width":95}
		];
	   	table = getDataTableByListHj("mydatatables","${ctx}/ddb/getPageList?treeddbh=${ddbh}",[2,'asc'],columns,0,1,setGroup);
	    //综合查询所属单位弹窗
	    $("#btn_dwmc").click(function(){
			select_commonWin("${ctx}/ddb/szdwWin?controlId=txt_dwmc","单位信息","920","630");
	    });
	    //综合查询上级地点弹窗
		$("#btn_sjddmc").click(function(){
			select_commonWin("${ctx}/ddb/sjddWin?controlId=txt_sjddmc","地点信息","920","630");
		});
		//综合查询地点编号弹窗
		$("#btn_ddbh").click(function(){
			select_commonWin("${ctx}/ddb/sjddWin?controlId=txt_ddbh","地点信息","920","630");
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