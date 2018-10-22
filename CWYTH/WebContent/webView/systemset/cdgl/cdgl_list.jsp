<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>菜单管理</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>模块编号</label>
			        <input type="text" id="" class="form-control input-radius" name="mkbh" value="" table="b" placeholder="请输入模块编号">
				</div>
				<div class="form-group">
					<label>模块名称</label>
			        <input type="text" id="" class="form-control input-radius" name="mkmc" value="" table="b" placeholder="请输入模块名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				
					<div class="btn-group pull-right btn-group-marginTop" role="group"style="padding-right: 20px;">
						<button id="btn_add" type="button" class="btn btn-default">增加</button>
						<button id="btn_delxx" type="button" class="btn btn-default">批量删除</button>
					</div>
		</div>
    </div>
        </form>
	<div class="container-fluid">
        <div class='responsive-table'>
            <div class='scrollable-area'>
                 <table id="mydatatables" class="table table-striped table-bordered">
				    <thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>模块编号</th>
					        <th>模块名称</th>
					        <th>路径</th>
					        <th>同级序号</th>
					        <th>是否启用</th>
					        <th>系统菜单样式</th>	
					        <th>系统标志</th>
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
$(function (){
	//列表数据
   	var columns = [
		{"data": "MKBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="mkbh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "MKBH",defaultContent:""},
		{"data": "MKMC",defaultContent:""},    
	   	{"data": "URL",defaultContent:""},
	   	{"data": "XH",defaultContent:""},
	   	{"data": "QXBZ",defaultContent:""},
	   	{"data": "ICON",defaultContent:""},	
	   	{"data": "XTBZ",defaultContent:""},
	    {"data":"MKBH",'render':function(data, type, full, meta){
          	 return '<a style="font-size:12px" href="javascript:void(0);" class="btn btn_upd" mkbh="'+full.MKBH+'">修改</a>|<a style="font-size:12px" href="javascript:void(0);" class="btn btn_del" mkbh="'+full.MKBH+'">删除</a>';
//           	 return '<a style="font-size:12px" class="btn btn_show"  mkbh ="'+full.MKBH+'">查看详情</a>|<a style="font-size:12px" href="javascript:void(0);" class="btn btn_upd" mkbh="'+full.MKBH+'">修改</a>|<a style="font-size:12px" href="javascript:void(0);" class="btn btn_del" mkbh="'+full.MKBH+'">删除</a>';
           },orderable:false,"width":100,"class":"text-center"}
	];
   	table = getDataTableByListHj("mydatatables","${ctx}/cdgl/getPageList?bh=${mkbh}&treesearch=${treesearch}",[2,'asc'],columns,0,1,setGroup);
	
	//增加
	$(document).on("click","#btn_add",function() {
		var mkbh = "${mkbh}";
		console.log(mkbh);
		var index;
		if(mkbh.length==0){
			confirm("确认添加一级菜单吗？",{title:"提示"},function(index){
		     select_commonWin("${ctx}/cdgl/goAddCdgl?operateType=C","功能菜单维护","600","480");
			 close(index);
			});
		}else{
			select_commonWin("${ctx}/cdgl/goAddCdgl?operateType=C&mkbh="+mkbh,"功能菜单维护","600","480");
			 close(index);
		}
   	})
	
   	//修改
   	$(document).on("click",".btn_upd",function(){
		var mkbh = $(this).attr("mkbh");
	   select_commonWin("${ctx}/cdgl/goAddCdgl?operateType=U&mkbh="+mkbh,"功能菜单维护","600","480");
   	});
	
// 	//单条删除操作
	$(document).on("click",".btn_del",function(){
   		var mkbh = $(this).parents("tr").find("[name='mkbh']").val();
   		doDel("mkbh="+mkbh,"${ctx}/cdgl/doDel",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});

	  //批量删除
	$(document).on("click","#btn_delxx",function(){
		var checkbox = $("#mydatatables").find("[name='mkbh']").filter(":checked");
		console.log(checkbox.length);
   		if(checkbox.length>0){
   	   		var mkbh = [];
   	   		checkbox.each(function(){
   	   		mkbh.push($(this).val());
   	   		});
// 	   	   	confirm("确定删除？","",function(){
	   	   	doDel("mkbh="+mkbh.join("','"),"${ctx}/cdgl/doDel",function(val){
	   	   	table.ajax.reload();
	   	   	},function(val){
	   	   		},mkbh.length);
// 	   			$.ajax({
// 	   	   			url:"${ctx}/cdgl/doDel",
// 	   	   			data:"mkbh="+mkbh.join("','"),
// 	   	   			type:"post",
// 	   	   			async:"false",
// 	   	   			success:function(val){
// 	   	   				alert("删除成功");
// 	   	   				table.ajax.reload();
// 	   	   			}
// 	   	   		});
// 	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
	});
			
	
// 	//导出Excel
// 	$(document).on("click",".btn_exp",function() {
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='logbh']").filter(":checked");
// 		var logbh = [];
// 		checkbox.each(function(){
// 			logbh.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/oplog/expExcel","操作日志","${ctx}",logbh.join(","));
//    	});
  
  
//     //信息清空
//     $(document).on("click",".btn_del",function() {
//    		var checkbox = $("#mydatatables").find(":checkbox");
//    		var index;
//    		if (checkbox.length>1){
//    		confirm("确认要清空所有信息吗？",{title:"提示"},function(index){
//    			$.ajax({
// 				type :"post",
// 				url:"${ctx}/oplog/doDeleteAll",
// 				dataType:"json",
// 				success:function(val){					
// 					close(index);
// 					if(val.success == 'true'){
// 						alert(val.msg);
// 						table.ajax.reload();
// 					}else if(val == "false"){
// 						alert(val.msg);
// 						table.ajax.reload();
// 					}
// 				},
// 				error:function(val){
//       				close(index);
//       				alert(getPubErrorMsg());      					 
//       			},
// 				beforeSend:function(){
// 	   				index = loading(2);
// 	   			}
// 			});
//    		});
//    		}else{
//    			alert("信息已被清空！");
//    		}
//    	});
    
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