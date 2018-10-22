<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html> 
<head> 
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资产信息查询</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
  	<div class="container-fluid">
  		<div style="font-size: 17px">
  			<big><b><i style="font-style: normal;">|资产信息查询</i></b></big>
  		</div>
		 <div class="input-group row" style=" margin-left:450px;margin-right: 450px">
			<input  class="form-control" id="btn_search" name="keyword" value ="${keyword}" placeholder="请输入资产名称 ">
			<span id="sp_search" style="cursor:pointer" class="input-group-addon"><i class="fa icon-chaxun"></i></span>
		</div> 
		
	    	<div class='responsive-table' style="margin-top: 30px">
		    	<div style="font-size: 17px">
	  			<i style="font-style: normal;">为您找到相关结果<span id="assetsnum"></span>个:"<span id="sp_infor"></span>"</i>
	  			</div>
	        	<div class='scrollable-area'>
	            	<table id="mydatatables" class="table table-striped table-bordered" style="font-size:13px;font-family:sans-serif;">
			    		 <thead style="display:none;">
			    			<tr>
								<th><input type="checkbox" class="select-all"/></th>
								<th></th>
								<th></th> 
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
$(function() {
	//列表数据
	var columns = [
		{"data": "YQBH",orderable:false, 'render': function (data, type, full, meta){
		    return '<input type="checkbox" value="' + data + '">';
		},"width":10,'searchable': false,'visible':false},
		{"data":"_XH",class:'text-center',orderable:false,'render': function (data, type, full, meta){
				return data;
		},"width":41,"searchable": false,"class":"text-center"}, 
		{"data": "MC",defaultContent:""},
		];
	
	table = getSerDataTable("mydatatables","${pageContext.request.contextPath}/index/getPageSerList?keyword="+$("#btn_search").val(),[2,'asc'],columns,getAssetsnum);	
	//查询
	$("#sp_search").bind("click",function(){
		window.location.href="${pageContext.request.contextPath}/index/goSearchPage?keyword="+$("#btn_search").val();
	}); 
	
	function getSerDataTable(_formId,_url,_order,_columns,_drawCallback){
		var _length = ["${sessionScope.rownum}"];//全局的分页函数 
		return $('#'+_formId).DataTable({
	        "ajax": {
	            url:_url//获取数据的方法
	        },
	        "autoWidth": false,
	        "lengthMenu":["100"],
	        "order": _order,
	        "serverSide": true,
	        "columns": _columns,
	        "dom":"<'row'<'overflow' t>><'row'<'col-md-5 col-sm-5 col-xs-5'li><'col-md-7 col-sm-7 col-xs-7 pull-right'p>>",
	        "drawCallback":function(){
	        	if(_drawCallback!=undefined&&_drawCallback!=null){
	        		_drawCallback();
	        	}
	        } 
		});
	}
	function getAssetsnum(){
		$.ajax({
			type:"post",
			data:"keyword="+$("#btn_search").val(),
			url:"${pageContext.request.contextPath}/index/getAssetsnum",
			success:function(val){
				$("#assetsnum").html(val);	
				$("#sp_infor").html($("#btn_search").val());	
			}
		});
	}

});
</script>
</body>
</html>