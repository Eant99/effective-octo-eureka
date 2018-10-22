<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收支结转</title>
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
					<label>模板编号</label>
					<input type="text" id="txt_mbbh" class="form-control" name="mbbh" table="K" placeholder="请输入模板编号">
				</div> 
				<div class="form-group">
					<label>模板名称</label>
					<input type="text" id="txt_mbnr" class="form-control" name="mbmc" table="K" placeholder="请输入模板名称">
				</div>
				<div class="form-group">
				  
				   <%--  <select class="form-control" name="kmnd"  id="txt_kmnd"  >	
				      
			            <c:choose > 
				              <c:when test="${nlist=change}">
				               <c:forEach var="nlist" items="${nlist}">  	
  	             			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==kmnd}">selected</c:if> >${nlist.kmnd }</option> 
  	             			   </c:forEach>
	             			  </c:when> 
  	             			  <c:otherwish> 
   	             			     <option value="${nlist.kmnd}" <c:if test="${nlist.kmnd==jn}">selected</c:if> >${nlist.kmnd }</option>  
  	             			     
  	             			  </c:otherwish> 
 	             	      </c:choose>  
 	             	      
	             	</select> --%>
	             	   
					<%-- <label>年度</label>
					<input type="text" id="txt_sbnd" class="form-control input-radius window year" name="nd" table="k" value="<%=date %>" data-format="yyyy"/>				 --%>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>

				<div class="btn-group pull-right" role="group">
	              <!--  <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
	               <button type="button" class="btn btn-default" id="btn_jz">结转</button>
	               <button type="button" class="btn btn-default" id="btn_fjz">反结转</button>
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
<!-- 				            <th><input type="checkbox" class="select-all"/></th> -->
				            <th>序号</th>
				            <th>模板编号</th>
				            <th>模板名称</th>
				            <th>凭证摘要</th>
				            <th>转出科目</th>
				            <th>转入科目</th>
				           
				       
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
var target = "${ctx}/jzmb";
$(function () {
	//列表数据
    var columns = [
//        {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
//          return '<input type="checkbox" zy="'+full.ZY+'" pzlx="'+full.PZLXBH+'"  name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
//        },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "MBBH",defaultContent:"","width":70}, 
       {"data": "MBMC",defaultContent:"","width":200},
       {"data": "PZZY",defaultContent:"","width":300}, 
       {"data": "ZCKM",defaultContent:""},
       {"data": "ZRKM",defaultContent:""},
      
     ];
	
 	table = getDataTableByListHj("mydatatables","${ctx}/szjz/getPageList",[1,'asc'],columns,0,1,setGroup);

 	$(document).on("click","#btn_fjz",function(){
  		select_commonWin("${ctx}/webView/kjhs/szjz/fjz_list.jsp","结转信息","920","630");
  		
  	});
  
 	$(document).on("click","#btn_jz",function(){
    	/* var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
    	
    	console.log("checkbox.length===="+checkbox.length);
    	if(checkbox.length>1){
    		alert("每次只能对一个模板进行结转");
    	}else if(checkbox.length==0){
    		alert("请选择一个模板进行结转");
    	}
    	else{
    		var checkbox = $("[type=checkbox]:not(.select-all)");
    		var guid = []
    		$.each(checkbox,function(){
    			
    		});
    		var zy = checkbox.attr("zy");
    		var pzlx = checkbox.attr("pzlx"); */
    		var length = $("tbody tr").length;
    		confirm("确定要结转这些模板？","",function(){
    			$.ajax({
       	   			url:"${ctx}/szjz/doszjz?length="+length,
       	   			type:"post",
       	   			async:"true",
       	   			success:function(data){
       	   				if(data==0){
       	   					alert("没有模板可以结转");
       	   				}else if(data >0 ){
       	   					alert("结转成功");
       	   				}else{
       	   				    alert("数据操作失败，系统出现错误！");
       	   				}
       	   				
       	   			},
       	   			error:function(){
       	   				alert("抱歉，系统出现错误！");
       	   			}
       	   		});
    		});
  
    });
 	//单条删除
 	$(document).on("click",".btn_delxx",function(){
 		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定删除该条信息？","",function(){
   			$.ajax({
   	   			//url:ADDRESS+"/srxm/delete",
   	   			url:target+"/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"true",
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
//导出Excel
 	$(document).on("click","#btn_exp",function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
	var guid = [];
	checkbox.each(function(){
		guid.push($(this).val());
	});
		doExp(json,"${ctx}/jzmb/expExcel?treedwbh=${dwbh}","结转模板信息","${ctx}",guid.join(","));
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