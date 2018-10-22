<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目类型</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<style type="text/css">
#btns{
	margin-top: 10px !important;	
	}
.redred{
	color:red;
}
</style>

<body>
<div class="fullscreen">
    <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>票据号</label>
					<input type="text" id="txt_xmlxbh" class="form-control" name="PJH" table="K" placeholder="请输入票据号">
				</div>
				<div class="form-group">
					<label>汇款人全称</label>
					<input type="text" id="txt_fxmlxmc" class="form-control" name="CPR" table="K"  placeholder="请输入汇款人姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_back">返回</button>

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
				            <th style="text-align:center">序号</th>
				            <th style="text-align:center" >状态</th>
				            <th style="text-align:center" >票据号</th>
				            <th style="text-align:center" >汇款人全称</th>
				            <th style="text-align:center" >汇款人账号</th>
				            <th style="text-align:center" >汇出地点</th>
				            <th style="text-align:center" >汇出行名称</th>
				            <th style="text-align:center" >收款人全称</th>
				            <th style="text-align:center" >收款人账号</th>
				            <th style="text-align:center" >汇入地点</th>
				            <th style="text-align:center" >汇入行名称</th>
				            <th style="text-align:center" >金额</th>
				            <th style="text-align:center" >出票日期</th>
				            <th style="text-align:center" >操作</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${ctx}/static/javascript/public/public-checked.js"></script>
<script src="${ctx}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	var columns = [
	       {"data": "GID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" class="keyId " value="' + data + '" guid = "'+full.GID+'">';
	       },"width":10,'searchable': false,"class":"text-center"},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":20,"searchable":false,"class":"text-center"},
	       {"data": "ZTMC",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "PJH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "CPR",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "CPZH",defaultContent:"","class":"text-left","width":30},
	       {"data": "HCDD",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "CPYH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "SKR",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "SKZH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "HRDD",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "SKYH",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "JE",defaultContent:"","width":40,"class":"text-right",},
// 	       {"data": "SFDYMC",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "CPRQ",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "GID","class":"text-center",'render':function(data, type, full, meta){
	    	   if('10'==full.ZT)
		   		return '<a href="javascript:void(0);" id="btn_kp" class="btn btn-link btn_look" guid = "'+full.GID+'">开票</a>|<a href="javascript:void(0);" id="btn_zf" class="btn btn-link btn_upd" guid = "'+full.GID+'">作废</a>|<a href="javascript:void(0);" id="btn_del" class="btn btn-link btn_delxx" guid = "'+full.GID+'">删除</a>';
		   		else if('50'==full.ZT)
		   			return '<a href="javascript:void(0);" class="btn btn-link btn_delxx" id="btn_del" guid = "'+full.GID+'">删除</a>';
		   		else if('00'==full.ZT)
		   			return '<a href="javascript:void(0);" id="btn_ly" class="btn btn-link btn_look" guid = "'+full.GID+'">领用</a>|<a href="javascript:void(0);" id="btn_del" class="btn btn-link btn_delxx" guid = "'+full.GID+'">删除</a>';
		   		else if ('30'==full.ZT)
		   			return '<div class="redred">已报销</div>';	
		   		else
		   			return '<a href="javascript:void(0);" id="btn_bx" class="btn btn-link btn_upd" guid = "'+full.GID+'">报销</a>|<a href="javascript:void(0);" id="btn_zf" class="btn btn-link btn_upd" guid = "'+full.GID+'">作废</a>';	
	       },orderable:false,"width":60}
	     ];
	    table = getDataTableByListHj("mydatatables","${ctx}/pjgl/rcyw/pjlb/getPjListPage?pjzh=${pjzh}",[3,'asc'],columns,0,1,setGroup);

   	$("#btn_back").click(function(){
   		window.location.href  = "${ctx}/pjgl/rcyw/pjlb/getPjlbPage";
   	});

	$(document).on("click","#btn_zf",function(){
		var guid = $(this).attr("guid");
   	    confirm("确认作废该条数据？","",function(){
   	    	$.ajax({
   	   			url:"${ctx}/pjgl/rcyw/pjlb/doZf",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			success: function(val){
   	   				alert("作废成功！");	
   	   				table.ajax.reload();
   	   			},
   	   			error:function(val){
   	   	//			alert("抱歉，系统出现问题！");
   	   			}
   	   		});
   	    });
   	});
	
	$(document).on("click","#btn_bx",function(){
		var guid = $(this).attr("guid");
 //  		var dwbh = $(this).attr("dwbh");
   	    confirm("确认报销该条数据？","",function(){
   	    	$.ajax({
   	   			url:"${ctx}/pjgl/rcyw/pjlb/doBx",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			success: function(val){
   	   				alert("报销成功！");	
   	   				table.ajax.reload();
   	   			},
   	   			error:function(val){
   	   	//			alert("抱歉，系统出现问题！");
   	   			}
   	   		});
   	    });
   	});
	
/*    	$(document).on("click","#btn_bx",function(){
   		var guid = $(this).attr("guid");

	   		confirm("确认报销该条数据？","",function(){
	   			$.ajax({
	   	   			//url:ADDRESS+"/srxm/delete",
	   	   			url:"${ctx}/pjgl/rcyw/pjlb/doBx",
	   	   			data:"guid="+guid,
	   	   			type:"post",
	  // 	   			async:"true",
	   	   			success:function(data){
	   	   				var result = JSON.getJson(data);
	  	   				if(result.success){
							alert("删除成功！");  	   					
		   	   				table.ajax.reload();
	  	   				} 
	   	   			},
	   	   			error:function(){
	   	   				alert("抱歉，系统出现错误！");
	   	   			}
	   	   		});
	   		}); 
   	}); */
		
	
	$(document).on("click","#btn_kp",function(){
		var pjzh="${pjzh}";
   		var guid = $(this).attr("guid");
   		select_commonWin("${ctx}/pjgl/rcyw/pjlb/pageSkip?guid="+guid+"&pjzh="+pjzh,"票据开票","800","600");
   	});
	$(document).on("click","#btn_ly",function(){
   		var guid = $(this).attr("guid");
//   		window.location.href = "${ctx}/pjgl/rcyw/pjlb/pjlb_ly?guid="+guid;	
   		select_commonWin("${ctx}/pjgl/rcyw/pjlb/pjlb_ly?guid="+guid,"票据领用","800","400");
   	});
   

   	$(document).on("click","#btn_del",function(){
   		var guid = $(this).attr("guid");

	   		confirm("确定删除选中信息？","",function(){
	   			$.ajax({
	   	   			//url:ADDRESS+"/srxm/delete",
	   	   			url:"${ctx}/pjgl/rcyw/pjlb/doDelete",
	   	   			data:"guid="+guid,
	   	   			type:"post",
	  // 	   			async:"true",
	   	   			success:function(data){
	   	   				var result = JSON.getJson(data);
	  	   				if(result.success){
							alert("删除成功！");  	   					
		   	   				table.ajax.reload();
	  	   				} 
	   	   			},
	   	   			error:function(){
	   	   				alert("抱歉，系统出现错误！");
	   	   			}
	   	   		});
	   		}); 
   	});
		
});
$(function() {	
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
</script>
</html>