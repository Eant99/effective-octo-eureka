<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>帮助信息-目录维护</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
.required{
  color:red;
}
#btn_save {
  background-color: #00acec;
  color: white;
}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="container-fluid">
		<div class="list">
	        <div class="clearfix" id ="clearfix" >
	            <div class="row">
		            <div class="col-md-3 col-sm-3">
			            <div class="input-group">
				            <span class="input-group-addon"><span class="required">*</span>目录编号</span>
				            <input type="text" name="bh" id ="txt_bh" class="form-control"  value="${mlxx.bh}" readonly>
			            </div>
		            </div>
		            <div class="col-md-4 col-sm-4">
			            <div class="input-group">
				            <span class="input-group-addon"><span class="required">*</span>目录名称</span>
				            <input type="text" name="mc" id="txt_mc" class="form-control "  value="${mlxx.mc}">
			            </div>
		            </div>
		            <div class="col-md-4 col-sm-4">
			            <div class="input-group">
				            <span class="input-group-addon">排序序号</span>
				            <input type="number"  min="1" name="zt" id="txt_zt"  class="form-control input-radius int"  value="${mlxx.zt}">
			            </div>
		            </div>
		            <div class="col-md-1 col-sm-1" >
			            <button class="btn btn-default " id="btn_save"  style ="float:right">
			                <i class="fa icon-save" style="color:white"></i>
				                             保存
		                </button>
		            </div>
	            </div>
	        </div>
	        <hr class="hr-normal"/>
	        <div class='responsive-table'>
	            <div class='scrollable-area'>
	                 <table id="mydatatables" class="table table-striped table-bordered">
					    <thead>
					    <tr>
					        <th><input type="checkbox" class="select-all"/></th>
					        <th>序号</th>
					        <th>目录编号</th>
					        <th>目录名称</th>
					        <th>排序序号</th>
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
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
 	//单条删除操作
	$(document).on("click",".btn_delxx",function(){
   		var bh = $(this).parents("tr").find("[name='bh']").val();
   		doDel("bh="+bh,"${ctx}/bzml/doDelete",function(val){
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
 	
	//保存按钮
	$("#btn_save").click(function(e){
		var bh = $("#txt_bh").val();
		var mc = $("#txt_mc").val();
		var zt = $("#txt_zt").val();
		if(mc == "" ){
			alert("目录名称不能为空！");
		}else{
			$.ajax({
	    		type:"post",
	    		url:"${ctx}/bzml/doSave?operateType=C&bh="+bh+"&mc="+mc+"&zt="+zt,
	    		dataType:"json",
	            success:function(val){	                	
	    	    if(val.success == 'true') {
	                alert(val.msg);
	                window.location.href= window.location.href;
	                getIframWindow("${param.pname}").location.href = "${ctx}/bzxx/getBzxx?operateType=C&pname=${param.pname}";
	    	    } else{
	    	        alert(val.msg);
	    	    }
	    	        close(index);
	            },
	            error:function(){
	               alert("请稍后再试！");
	            },
	            beforeSend:function(){
	   			    index = loading(2);
	   		    }
	    	});				
		}
	}); 
	
	//列表数据
   	var columns = [
		{"data": "BH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="bh" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
	   	{"data": "BH",defaultContent:""},
	   	{"data": "MC",defaultContent:""},
	   	{"data": "ZT",defaultContent:""},
	   	{"data":function (data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_delxx">删除</a>';
		},orderable:false,"width":52,}
	];
   	table = getDataTable("mydatatables","${ctx}/bzml/getPageList",[2,'asc'],columns);
});
</script>
</body>
</html>