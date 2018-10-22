<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证模板管理</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>模板编号</label>
					<input type="text" id="txt_mbbh" class="form-control" name="mbbh" table="A" placeholder="请输入模板编号">
				</div>
				<div class="form-group">
					<label>模板名称</label>
					<input type="text" id="txt_mbnr" class="form-control" name="mbnr"  table="A" placeholder="请输入模板名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">查询</button>

				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
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
				            <th>模板编号</th>
				            <th>模板名称</th>
				            <th>凭证摘要</th>
				            <th>科目编号</th>
				            <th>科目名称</th>
				         
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data":"MBBH","render":function(data,type,full,mate){
         	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'">'+ data +'</a></div>';
       }},
       {"data": "MBNR",defaultContent:"",'render':function(data, type, full, meta){
     	   var maxlength=30;
 			if(data==""||data==null||data=="undefined"){
 				return "";
 			}else{
 				if(data.length>=maxlength){
 					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
 				}else{
 					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
 				}
 			}
       },"width":300},//模板名称
       {"data": "ZY",orderable:false,defaultContent:"",'render':function(data, type, full, meta){
     	   var maxlength=30;
 			if(data==""||data==null||data=="undefined"){
 				return "";
 			}else{
 				if(data.length>=maxlength){
 					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
 				}else{
 					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
 				}
 			}
       }},
       {"data": "KMBH",orderable:false,defaultContent:"",'render':function(data, type, full, meta){
     	   var maxlength=30;
 			if(data==""||data==null||data=="undefined"){
 				return "";
 			}else{
 				if(data.length>=maxlength){
 					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
 				}else{
 					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
 				}
 			}
       }},
       {"data": "KMMC",orderable:false,defaultContent:"",'render':function(data, type, full, meta){
       	   var maxlength=30;
   			if(data==""||data==null||data=="undefined"){
   				return "";
   			}else{
   				if(data.length>=maxlength){
   					return '<div class="change"><span title="'+data+'">'+data.substr(0,maxlength-3)+'...</sapn></div>'
   				}else{
   					return '<div class="change"><span title="'+data+'">'+ data +'</span></div>'
   				}
   			}
       }},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":80,"class":"text-center"}
     ];
 table = getDataTableByListHj("mydatatables","${ctx}/pzmb/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
 	//添加按钮
 $(document).on("click","#btn_add",function(){
//    	$("#btn_add").click(function(){
   		doOperate("${ctx}/pzmb/addpzmb");
   	});
	

	//导出Excel
	$(document).on("click","#btn_exp",function(){
//    	$("#btn_exp").click(function(){
   		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/pzmb/expExcel2","凭证模板","${pageContext.request.contextPath}",id);
   	});
	
   	//修改按钮
	$(document).on("click",".btn_upd",function(){
		var guid = $(this).attr("guid");

   		doOperate("${ctx}/pzmb/goeditpzmb?guid="+guid,"U");
   	});
   //查看按钮
   $(document).on("click",".btn_look",function(){
	   var guid = $(this).attr("guid");
	   doOperate("${ctx}/pzmb/goLookPage?guid="+guid,"L");
   });
	//批量删除
	$(document).on("click","#btn_del",function(){
//    	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		var guid = []
   			 if(checkbox.length>0){
   	   			var guid = [];
   	   			checkbox.each(function(){
   	   				guid.push($(this).val());
   	   			});
   	   			/*  doDel("guid="+guid.join("','"),"${ctx}/pzmb/pzmbsDel",function(val){
   	   			//window.location.href = "${ctx}/webView/kjhs/pzmb/pzmb_list.jsp";
   	   			table.ajax.reload();
   	   	   		},function(val){
   	   	   		},guid.length);  */
   	   		confirm("确定要删除选中的这"+checkbox.length+"条信息吗？","",function(){
   				$.ajax({
   		   			url:"${ctx}/pzmb/pzmbsDel",
   		   			data:"guid="+guid.join("','"),
   		   			type:"post",
   		   			dataType:"json",
   		   			async:"false",
   		   			success:function(val){
   		   				if(val.success){
   		   					alert("删除成功！");
   		   				}
   		   				
   		   				table.ajax.reload();
   		   			},
   		   			error:function(){
   		   				alert("抱歉，系统出现问题！");
   		   			}
   		   		});
   			});
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		} 
   	});
 	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).attr("guid");		
	 	confirm("确定删除？","",function(){
			$.ajax({
	   			url:"${ctx}/pzmb/pzmbsDel",
	   			data:"guid="+guid,
	   			type:"post",
	   			dataType:"json",
	   			async:"false",
	   			success:function(val){
	   				if(val.success){
	   					alert("删除成功！");
	   				}
	   				
	   				table.ajax.reload();
	   			},
	   			error:function(){
	   				alert("抱歉，系统出现问题！");
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