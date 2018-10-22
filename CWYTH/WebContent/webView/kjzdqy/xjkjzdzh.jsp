<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>新旧会计制度转换</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css"> 
/*  	.dataTables_scrollHeadInner{  */
/*   		width:600px ! important;  */
/* 	}   */
/* 	table.dataTable{   */
/*  		width:600px ! important;  */
/* 	}   */
</style> 
</head>
<body >
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-bottom: 2px;padding-top: 8px">
    		<div class="search-simple">
				<div class="form-group">
					<label>原科目名称</label>
					<input type="text"  class="form-control input-radius" id="txt_gjc" name="YKMMC"   placeholder="请输入原科目名称">
					<label>原科目编号</label>
					<input type="text"  class="form-control input-radius" id="txt_gjc" name="YKMBH"   placeholder="请输入原科目编号">
					
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				</div>
				<div class="btn-group pull-right"role="group" >
		                <button type="button" class="btn btn-default" id="btn_add">新增</button>
		                <button type="button" class="btn btn-default" id="btn_del">删除</button>
<!-- 						<button type="button" class="btn btn-default" id="btn_save">保存</button> -->
						<button type="button" class="btn btn-default" id="btn_zx">执行</button>
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
				          <th>原科目编号</th>
				          <th>原科目名称</th>
				          <th>现科目编号</th>
  						  <th>现科目名称</th>
  						  <th>操作</th>
				    </tr>
				    </thead>
<!-- 				      <tbody> -->
<%-- 					    	<c:forEach var="kjzd" items="${list}"> --%>
<!-- 					        	<tr> -->
<!-- 					            	<td ><input type="checkbox"  name="guid" ></td> -->
<!-- 					                <td style="width:385px"> -->
<%-- 					                <input id="txt_kmzye"  name="kmzye" width="100%" value="${kjzd.YKMBH}">  --%>
<!-- 					                </td> -->
<!-- 					                <td style="width:385px"> -->
<%-- 					                 <input id="txt_kmzye"  name="kmzye" width="100%" value="${kjzd.YKMMC}">  --%>
<!-- 					                </td> -->
<!-- 					                <td style="width:385px"> -->
<%-- 									 <input id="txt_kmzye"  name="kmzye" width="100%" value="${kjzd.XKMBH}">  --%>
<!-- 					                </td> -->
<!-- 					                <td style="width:385px"> -->
<%--  									<input id="txt_kmzye"  name="kmzye" width="100%" value="${kjzd.XKMMC}">  --%>
<!-- 					                </td> -->
<!-- 					           	</tr> -->
<%-- 					        </c:forEach> --%>
<!-- 						</tbody> -->
				</table>
            </div>
        </div>
	</div>
	</div>

<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function() {
	//列表数据
	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" value="' + data + '">';
	    },"width":5,'searchable': false,"class":"text-center"},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"searchable": false,"class":"text-center","width":40},
		{"data": "YKMBH",defaultContent:"","width":80},
		{"data": "YKMMC",defaultContent:"","width":100},
		{"data": "XKMBH",defaultContent:"","width":80},
		{"data": "XKMMC",defaultContent:"","width":100},
	   	{"data": "GUID",'render':function (data, type, full, meta){
	   		return '<a class="btn btn-link btn_update" GUID ="'+full.GUID+'" >修改</a>';
		},orderable:false,"width":50,"class":"text-center"}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/kjzdqy/getKjkmList",
   			[2,'asc'],columns,0,1,setGroup
//    			,function(p1){
//    			$(p1.data).each(function(i,ele){
//    				$('.zdm.'+ele.GUID+' option').each(function(i,e){
//    					if(e.value == ele.ZD){
//    						$(e).prop('selected',true)
//    					}
//    				})
//    			})
//    		}
   	);
   	
    //增加
	$(document).on("click","#btn_add",function(){
	   select_commonWin("${pageContext.request.contextPath}/kjzdqy/kjzdzh_add?operateType=C","会计制度转换新增","480","350");
   	});
    //修改
	$(document).on("click",".btn_update",function(){
		var guid = $(this).attr("GUID");
// 		alert(guid);
	   select_commonWin("${pageContext.request.contextPath}/kjzdqy/kjzdzh_add?operateType=U&guid="+guid,"会计制度转换新增","480","350");
   	});
    
  //保存
	$(document).on("click",".btn_save",function(){
		var zdm = $(this).parents("tr").find("[name='zdm']").val();
		var guid = $(this).parents("tr").find("[name='guid']").val();
		confirm("确定使用模板？",{title:"提示信息"},function(){
			$.ajax({
				url:"${pageContext.request.contextPath}/kjzdqy/doSave",
				data:"zdm="+zdm+"&guid="+guid,
				type:"post",
				dataType:"json",
				success:function(data){
					if(data.success){
						alert("操作成功！");
					}else{
						alert("操作失败！");
					}
				}
			});
			table.ajax.reload();
		});
   	});
  
	//删除
	$(document).on("click","#btn_del",function(){
		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
   	   		console.log(guid);
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/kjzdqy/doDelKjkm",
	   	   			data:"guid="+guid.join("','"),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
	});
	//执行
	$(document).on("click","#btn_zx",function(){
		var checkbox = $("#mydatatables").find("[name='guid']");
		//账套id
		var ztid="${guid}";
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定执行？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/kjzdqy/doZx?ztid=${guid}",
	   	   			data: "guid="+guid.join("','"),   
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("操作成功！");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
	});
  
		});
</script>
</body>
</html>