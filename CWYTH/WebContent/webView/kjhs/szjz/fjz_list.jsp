<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>反结转</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<%
	String date = Constant.MR_YEAR();
	%>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<div class="form-group">
					<label>凭证号</label>
					<input type="text" id="txt_pzh" class="form-control" name="pzh" table="K" placeholder="请输入凭证号">
				</div> 
							
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>

				<div class="btn-group pull-right" role="group">

	               <button type="button" class="btn btn-default" id="btn_plfjz">批量返结转</button>
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
				            <th>结转人员</th>
				            <th>凭证号</th>
				            <th>结转时间</th>
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
<script>

$(function () {
	//列表数据
    var columns = [
       {"data": "ZBGUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="zbguid" jzyf="'+full.JZYF+'" pzh="'+full.PZH+'" value="'+data+'" class="keyId">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "CZR",defaultContent:""}, 
       {"data": "PZH",defaultContent:""},
       {"data": "PZRQ",defaultContent:""},
       {"data": "ZBGUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_fjz">反结转</a>';
     },orderable:false,"width":220,"class":"text-center"}
     
     ];
	
 	table = getDataTableByListHj("mydatatables","${ctx}/szjz/getFjZPageList",[3,'asc'],columns,0,1,setGroup);

 
  	
  $(document).on("click",".btn_fjz",function(){
	  var zbguid = $(this).parents("tr").find("[name=zbguid]").val();
	  var pzh =  $(this).parents("tr").find("[name=zbguid]").attr("pzh");
	  var jzyf =  $(this).parents("tr").find("[name=zbguid]").attr("jzyf");
	    confirm("确定要反结转这一条信息？","",function(){
	    	$.ajax({
	   			url:"${ctx}/szjz/dofjz?pzh="+pzh+"&jzyf="+jzyf,
	   			data:"zbguid="+zbguid,
	   			type:"post",
	   			async:"true",
	   			success:function(data){
	   				//alert("结转成功");
	   				if(data=="success"){
	   					alert("反结转成功");
	   					location.reload(true);
	   				}else{
	   					
	   				}
	   				
	   			},
	   			error:function(){
	   				alert("抱歉，系统出现错误！");
	   			}
	   		});
	    });
		
	  
  });
  $("#btn_plfjz").click(function(){
	  var checkbox = $("#mydatatables").find("[name='zbguid']").filter(":checked");
	  var pzh = $("#mydatatables tr:last").find("[name='zbguid']").attr("pzh");
	//  var length = checkbox.length();
 		if(checkbox.length>0){
 				var guid = [];
 	   	   		checkbox.each(function(){
 	   	   		guid.push($(this).val());
 	   	   		});
 	   	   		confirm("确定要反结转所选中的这"+checkbox.length+"条信息","",function(){
 	   	   		$.ajax({
 	   	   			//url:ADDRESS+"/srxm/delete",
 	   	   			url:"${ctx}/szjz/doplfjz?length="+length+"&pzh="+pzh,
 	   	   			data:"guid="+guid.join("','"),
 	   	   			type:"post",
 	   	   			async:true,
 	   	   			success:function(data){
 	   	   				if(data=="success"){
 	   	   				alert("反结转成功");
	   					location.reload(true);
 	   	   				
 	   	   				}
 	   	   			},
 	   			error:function(){
 	   				alert("抱歉，系统出现错误！");
 	   			}
 	   	   		});
 	   	   		});
 	   			
 	   		
 		}else{
 			alert("请选择至少一条信息反结转!");
 		} 
	  
	  
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