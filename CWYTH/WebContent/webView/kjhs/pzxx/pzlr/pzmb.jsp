<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>凭证模板管理</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
 .dataTables_scrollBody{
    height:435px !important;
 }
</style>
</head>
<body>
 <div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;" onkeydown="if(event.keyCode==13)return false;">
			<div class="search-simple">
				<div class="form-group">
					<label>模板编号</label>
					<input type="text" id="txt_mbbh" class="form-control" name="mbbh" placeholder="请输入模板编号">
				</div>
				<div class="form-group">
					<label>模板名称</label>
					<input type="text" id="txt_mbmc" class="form-control" name="mbnr" placeholder="请输入模板名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">查询</button>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息。
	        </div>
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
				            <th>备注</th>
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
var target = "${ctx}/kjhs/pzxx/pzlr";
var winId = top.layer.getFrameIndex(window.name);
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'" flag="'+full.U1+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "MBBH",defaultContent:""}, 
       {"data": "MBNR",defaultContent:""},
       {"data": "ZY",defaultContent:""},
       {"data": "KMBH",defaultContent:""},
       {"data": "BZ",defaultContent:""},     
     ];
 table = getDataTableByListHj("mydatatables","${ctx}/pzmb/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	
});
$(function() {	
	//双击事件
    $(document).on("dblclick","tr:not(#header)",function(){
    	var guid = $(this).find(".keyId").val();
    	if(guid == null||guid == ""){
  		  alert("没有可选的数据！");
  		  return;
  	  	}
    	$.ajax({
			  type:"post",
			  url:target+"/getPzJsonByMb",
			  data:"guid="+guid,
			  dataType:"json",
			  success:function(data){
		   		 	getIframWindow("${param.pname}").xzmb(data);
		        	close(winId);
			  },
			  error:function(){
				  alert("抱歉，系统出现错误！");
			  }
		  });
    });
});
</script>
</body>
</html>