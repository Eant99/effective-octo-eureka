<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>会计制度启用设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	.dataTables_scrollHeadInner{
 		width:600px ! important;
	} 
 	table.dataTable{ 
 		width:600px ! important;
 	} 
</style>
</head>
<body >
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="" style="padding-bottom: 2px;padding-top: 8px">
    		<div class="search-simple">
				<div class="form-group">
					<label>账套名称</label>
					<input type="text"  class="form-control input-radius" id="txt_gjc" name="ZTMC"   placeholder="请输入账套名称">
					
					<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				</div>
<!-- 				<div class="btn-group pull-right"role="group" > -->
<!-- 		                <button type="button" class="btn btn-default" id="btn_add">新增</button> -->
<!-- 						<button type="button" class="btn btn-default" id="btn_dr">导入</button> -->
<!-- 	            	</div> -->
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
				          <th>账套名称</th>
				          <th>使用制度</th>
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
$(function() {
	//列表数据
	var columns = [
		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="guid" value="' + data + '">';
	    },"width":5,'searchable': false,"class":"text-center"},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"searchable": false,"class":"text-center","width":40},
		{"data": "ZTMC",defaultContent:"","width":160},
		{"data": "ZDM",defaultContent:"", "render": function (data, type, full, meta){
			return '<select name="zdm" class="zdm '+full.GUID+'"  style="width:183px">'+
			<c:forEach var="l" items="${list}">
			'<option class="zdm3" value="${l.guid}" >${l.zdm}</option>'+
			</c:forEach>
			'</select>';
	    },"width":40},
	   	{"data":"ZTMC",'render':function (data, type, full, meta){
	   		if(data=="学校账套" && full.ZDM=="2014旧版会计制度"){
	   		   return '<a class="btn btn-link btn_qy">启用新制度</a>|<a class="btn btn-link btn_save">保存</a>';
	   		}else{
	   			return '<a class="btn btn-link btn_save">保存</a>';
	   		}
		},orderable:false,"width":50,"class":"text-center"}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/kjzdqy/getPageList",
   			[2,'asc'],columns,0,1,function(p1){
   			$(p1.data).each(function(i,ele){
   				$('.zdm.'+ele.GUID+' option').each(function(i,e){
   					if(e.value == ele.ZD){
   						$(e).prop('selected',true)
   					}
   				})
   			})
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
  
  //启用新制度
	$(document).on("click",".btn_qy",function(){
		var guid = $(this).parents("tr").find("[name='guid']").val();
	   select_commonWin("${pageContext.request.contextPath}/kjzdqy/xjkjzdzh?guid="+guid,"新旧会计制度转换","800","750");
   	});
  
		});
</script>
</body>
</html>