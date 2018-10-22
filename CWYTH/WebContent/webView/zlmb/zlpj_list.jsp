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
</style>

<body>
<div class="fullscreen">
    <div class="search" id="searchBox" style="padding-top: 0px">
		
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_bmmc" class="form-control" name="BMMC" table="" placeholder="请输入部门名称">
				</div>
				<div class="form-group">
					<label>目标实施内容</label>
					<input type="text" id="txt_mbssnr" class="form-control" name="MBSSNR" table=""  placeholder="请输入目标实施内容">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
<!-- 	               <button type="button" class="btn btn-default" id="btn_add">增加</button> -->
<!-- 	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
	               <button type="button" class="btn btn-default" id="btn_save">保存</button>
	               <button type="button" class="btn btn-default" id="btn_fh">复核</button>

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
				            <th style="text-align:center" >部门名称</th>
				            <th style="text-align:center" >一级指标</th>
				            <th style="text-align:center" >二级指标</th>
				            <th style="text-align:center" >指标内容</th>
				            <th style="text-align:center" >目标实施内容</th>
				            <th style="text-align:center" >目标完成比例</th>
				            <th style="text-align:center" >目标完成情况</th>
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
<script src="${pageContext.request.contextPath}/static/javascript/public/public-checked.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
    var columns = [
	       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="checkbox" name="guid" class="keyId " value="' + data + '" guid = "'+full.GUID+'">';
	       },"width":10,'searchable': false,"class":"text-center"},
	       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
	          	return data;},"width":20,"searchable":false,"class":"text-left"},
	       {"data": "BMMC",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "YJZB",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "EJZB",defaultContent:"","width":40,"class":"text-left",},
	       {"data": "ZBNR",defaultContent:"","class":"text-left","width":30},
	       {"data": "MBSSNR",defaultContent:"","class":"text-left","width":30},
	       {"data": "MBWCBL",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="text" id="txt_bmh" class="form-control input-radius" name="mbbh" value="15%">';
	       },"width":10,'searchable': false,"class":"text-center"},
	       {"data": "MBWCQK",orderable:false, 'render': function (data, type, full, meta){
	         return '<input type="text" id="txt_bmh" class="form-control input-radius" name="mbbh" value="良好">';
	       },"width":10,'searchable': false,"class":"text-center"}
	       ]
		    table = getDataTableByListHj("mydatatables","${ctx}/webView/zlmb/zlpj.json",[2,'asc'],columns,0,1,setGroup);

   	$("#btn_save").click(function(){
//    		doOperate("${ctx}/xmlx/goEditPage","C");
//    		window.location.href  = "${ctx}/webView/zlmb/zlzd_add.jsp";
		alert("保存成功！");
   	});
   	$("#btn_fh").click(function(){
		alert("复核成功！");
   	});
   	$("#btn_del1").click(function(){
   		alert("删除成功！");
   	});
 
   	$("#btn_del").click(function(){
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
//    		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
//    			if(checkbox.length>0){
//    	   			var dwbh = [];
//    	   			checkbox.each(function(){
//    	   				dwbh.push($(this).val());
//    	   			});
//              console.log("dwbh====id==="+dwbh);
// 				$.ajax({
// 	   			url:"${ctx}/xmlx/selectXmlx",
// 	   			data:"dwbh="+dwbh.join("','"),
// 	   			type:"post",
// 	   			dataType:"json",
// 	   			async:"false",
// 	   			success:function(val){
// 	   				console.log("val====val==="+val);
// 	   				if(val){
// 	   	    	   		confirm("确定要删除所选中的这"+checkbox.length+"条信息吗？","",function(){
// 	    				$.ajax({
// 	    		   			url:"${ctx}/xmlx/doDelete",
// 	    		   			data:"dwbh="+dwbh.join("','"),
// 	    		   			type:"post",
// 	    		   			dataType:"json",
// 	    		   			async:"false",
// 	    		   			success:function(val){
// 	    		   				if(val.success){
// 	    		   					alert("删除成功！");
// 	    		   				}
	   		   				
// 	    		   				table.ajax.reload();
// 	    		   			},
// 	    		   			error:function(){
// 	    		   				alert("抱歉，系统出现问题！");
// 	    		   			}
// 	    		   		});
// 	    			});
// 	   				}else{
// 	   					alert("存在正在使用的项目类型，不允许删除");
// 	   				}
	   			
// 	   			},
// 	   			error:function(){
// 	   				alert("抱歉，系统出现问题！");
// 	   			}
// 	   		});
             

//    	   		}else{
//    	   			alert("请选择至少一条信息删除!");
//    	   		}
	alert("删除成功！");
   		
   	});
    
	
   	$(document).on("click","#btn_edit",function(){
//    		var guid = $(this).attr("guid");
//    		$.ajax({
//    			url:"${ctx}/xmlx/selectXmlx",
//    			data:"dwbh="+guid,
//    			type:"post",
//    			dataType:"json",
//    			async:"false",
//    			success:function(val){
//    				if(val){
//    					window.location.href = "${ctx}/xmlx/goEditPage1111?guid="+guid+"&operateType=U";
//    				}else{
//    					alert("该项目正在被使用，不允许编辑");
//    				}
//    			},
//    			error:function(val){
//    				alert("抱歉，系统错误");
//    			}
// 		  });
 		window.location.href ="${ctx}/webView/zlmb/zlzd_add.jsp";
   	});
	$(document).on("click",".btn_look",function(){
   		var guid = $(this).attr("guid");
   		window.location.href = "${ctx}/xmlx/goLookPage?guid="+guid+"&operateType=L";	
   	});
   
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).attr("guid");
   		var dwbh = $(this).attr("dwbh");
   		$.ajax({
   			url:"${ctx}/xmlx/selectXmlx",
   			data:"dwbh="+guid,
   			type:"post",
   			dataType:"json",
   			async:"false",
   			success: function(val){
   				console.log("val======="+val);
   				if(val){
   					confirm("确定删除？","",function(){
   						$.ajax({
   				   			url:"${ctx}/xmlx/doDelete",
   				   			data:"dwbh="+guid,
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
   					alert("该项目类型正在使用中，不允许删除！")   					
   				}  				
   			},
   			error:function(val){
   				
   			}
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