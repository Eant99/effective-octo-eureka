<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%-- <%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%> --%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>服务类型设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>网站名称：</label>
					<input type="text" id="txt_xm" class="form-control" name="wzmc"  table="" placeholder="请输入网站名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
					<input type="button" class="btn btn-default" id="btn_add" value="增加"/>
					<input type="button" class="btn btn-default" id="btn_del" value="批量删除"/>
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
<!-- 				            <th>应用名称</th> -->
				            <th>网站名称</th>
				            <th>APPID</th>
				            <th>创建时间 </th>
				            <th>审核状态</th>
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
<script src="${pageContext.request.contextPath}/static/javascript/public/download.js"></script>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>

<script>
$(function () {
	//联想输入提示
// 	$("#txt_dwbh").bindChange("${ctx}/system/suggest/SuggestControl/getXx","D");
	//列表数据
     var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
        return '<input type="checkbox" name="dm" class="keyId" value="' + data + '">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
       	return data;},"width":41,"searchable":false,"class":"text-center"},
//        {"data": "YYID",class:'text-left',defaultContent:""},
       {"data": "WZMC",class:'text-left',defaultContent:""},
       {"data": "APPID",class:'text-left',defaultContent:""},
       {"data": "CJSJ",class:'text-left',defaultContent:""},
       {"data": "SHZT",class:'text-left',defaultContent:""},
   	   {"data": "APPKEY",'render':function(data, type, full, meta){
// 	   		return '<a href="javascript:void(0);" guid="'+full.GUID+'" class="btn btn-link btn_upd">编辑</a>';
	   		return '<a href="javascript:void(0);" guid="'+full.GUID+'" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" dm="'+full.GUID+'" class="btn btn-link btn_delxx">删除</a>';
   	   },orderable:false,"width":60}
     ];

    table = getDataTableByListHj("mydatatables","${ctx}/oauth/getPageList",[4,'asc'],columns,0,1,setGroup);
    
   	//添加按钮
   	$("#btn_add").click(function(){
     select_commonWin("${ctx}/oauth/goExitpage?operateType=C","添加","500","440"); 
   	}); 
 
	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
//    		alert(dm);
        select_commonWin("${ctx}/oauth/goExitpage?operateType=U&guid="+guid,"修改","500","480"); 
   	});
  
    //单条删除
	$(document).on("click",".btn_delxx",function() {
		var dm = $(this).attr("dm");
		doDel("dm="+dm,"${ctx}/oauth/doDelete",function(val){
 			table.ajax.reload();
 		},function(val){
 			
 		},"1");
	});
	//批量删除按钮
	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='dm']").filter(":checked");
   		if(checkbox.length>0){
   	   		var mdid = [];
   	   		var isdel = [];
   	   		checkbox.each(function(){
   	   		mdid.push($(this).val());
   	   		isdel.push($(this).attr("isdel"));
   	   		});
   	   		
		   		doDel("dm="+mdid.join(","),"${ctx}/oauth/doDelete",function(val){
		   			table.ajax.reload();
		   		},function(val){
		   			
		   		},mdid.length);
   		}else{
   			alert("请选择至少一条信息删除");
   		}
   		return false;
   	});
	
	
});
 

</script>
</body>
</html>