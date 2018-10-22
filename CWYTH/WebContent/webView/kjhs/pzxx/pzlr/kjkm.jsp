<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
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
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>科目编号</label>
					<input type="text" id="txt_kmbh" class="form-control" name="kmbh" placeholder="请输入科目编号">
				</div>
				<div class="form-group">
					<label>科目名称</label>
					<input type="text" id="txt_kmmc" class="form-control" name="kmmc" placeholder="请输入科目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search">查询</button>
				<button type="button" class="btn btn-primary" id="btn_qd">确定</button>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class="alert alert-info">
		          	<strong>提示：</strong>先找到需要的信息，然后<strong>双击</strong>这条信息，或先选中需要的信息，然后点击<strong>确定</strong>按钮。
	        </div>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>科目编号</th>
				            <th>科目名称</th>
				            <th>科目属性</th>
				            <th>余额方向</th>
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
var winId = top.layer.getFrameIndex(window.parent.name);
$(function () {
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" data-kmbh="'+full.KMBH+'" data-sfmj="'+full.SFMJ+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "KMBH",defaultContent:""}, 
       {"data": "KMMC",defaultContent:""},
       {"data": "KMSX",defaultContent:""},     
       {"data": "YEFX",defaultContent:"","class":"text-center",'render':function(data){
    	   return data == "0"?"借方":"贷方";
       }},     
     ];
 table = getDataTableByListHj("mydatatables",target+"/getKjkmData?bh=${kmbh}",[2,'asc'],columns,0,1,setGroup);
	
});
$(function() {	
	//双击事件
    $(document).on("dblclick","tr:not(#header)",function(){
    	var kmbh = $(this).find(".keyId").data("kmbh");
    	var sfmj = $(this).find(".keyId").data("sfmj");
    	var kmmc= $(this).find(".keyId").data("kmmc");
    	if(kmbh == null||kmbh == ""){
  		  alert("没有可选的数据！");
  		  return;
  	  	}
    	if(sfmj==0){
    		alert("该科目非末级科目！");
    	}
    	getIframeControlForPzlr("${param.pname}","${param.controlId}").val(kmbh);
    	getIframeControlForPzlr("${param.pname}","${param.controlId}").attr("title",kmmc);
    	getIframeControlForPzlr("${param.pname}","${param.controlId}").focus();//手动触发验证
    	getIframWindow("${param.pname}").triggerChange("${param.controlId}");
    	close(winId);
    	
    });
    $(document).on("keypress","#txt_kmbh,#txt_kmmc",function(e){
    	if(e.keyCode==13){
    	   $("#btn_search").click();
    	}
    });
    $(document).on("click","#btn_qd",function(){
    	var checkbox = $(".keyId").filter(":checked");
    	if(checkbox.length==0){
    		alert("请选择一条信息！");
    		return;
    	}else if(checkbox.length>1){
    		alert("只能选择一条信息！");
    		return;
    	}else{
    		var kmbh = $("#mydatatables").find(".keyId").filter(":checked").data("kmbh");
    		var sfmj = $("#mydatatables").find(".keyId").filter(":checked").data("sfmj");
    		var kmmc = $("#mydatatables").find(".keyId").filter(":checked").data("kmmc");
        	if(sfmj==0){
      		  alert("该科目非末级科目！");
      	  	}
        	getIframeControlForPzlr("${param.pname}","${param.controlId}").val(kmbh);
        	getIframeControlForPzlr("${param.pname}","${param.controlId}").attr("title",kmmc);
        	getIframeControlForPzlr("${param.pname}","${param.controlId}").focus();//手动触发验证
        	getIframWindow("${param.pname}").triggerChange("${param.controlId}");
        	close(winId);
    	}
    })
});
</script>
</body>
</html>