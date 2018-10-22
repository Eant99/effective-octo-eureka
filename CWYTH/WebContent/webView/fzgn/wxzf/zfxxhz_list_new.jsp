<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>支付详情</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px; height:40px;">
<!-- 		<div class="row rybxx" style="margin-left:-15px"> -->
			
<!-- 		</div> -->
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple"> 
			   <div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_export">导出</button>
<!-- 	               <button type="button" class="btn btn-default" id="btn_zhifu">支付</button> -->
	               <button type="button" class="btn btn-default" id="btn_back">返回</button>
<!-- 	               <button type="button" class="btn btn-default" id="btn_over">支付成功</button> -->
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
				            <th>承包商名称</th>
				            <th>窗口名称</th>
				            <th>所属校区</th>
				            <th>总计金额(元)</th>				                   
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
<%-- <script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script> --%>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	var zfzt = $("[name='zfzt']").val();
	//列表数据
    var columns = [
       {"data": "XFDDMC",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.XFDDMC+'" xfze="'+full.XFZE+'">';
       },"width":10,'searchable': false},
       {"data":"XH",orderable:false,'render':function(data,type,full,meta){
    	   if(data==1188899){
    		   return '';
    	   }else{
          	return data;
    	   }
          	},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "CBSMC",defaultContent:"合计",},      
       {"data": "XFDDMC",defaultContent:"-",},
       {"data": "ZLWZMC",defaultContent:"-",},
       {"data": "ZE",defaultContent:"","class":"text-right"},   
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/wxzf/getHzTjPageList?zfzt=${param.zfzt}&zffs=${param.zffs}&ywlx=${param.ywlx}&cbsmc=${param.cbsmc}&xfddmc=${param.xfddmc}&kssj=${param.kssj}&jssj=${param.jssj}&zlwz=${param.zlwz}",[1,'desc'],columns,0,1,setGroup);
    	//点击返回
    	$("#btn_back").click(function(){
       		window.location.href = "${ctx}/wxzf/goPageListNew";
       	});
    	 //导出Excel
      	$(document).on("click","#btn_export",function(){
       		var id = [];
       		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
       		if(checkbox.length > 0){
    			checkbox.each(function(){
    				id.push($(this).attr("guid"));
    			});
    			id = id.join("','");
       		}else{
       			id = "";
       		}
       		doExp("","${ctx}/wxzf/expExcel2?zfzt=${param.zfzt}&zffs=${param.zffs}&ywlx=${param.ywlx}&cbsmc=${param.cbsmc}&xfddmc=${param.xfddmc}&kssj=${param.kssj}&jssj=${param.jssj}","消费记录统计查询","${pageContext.request.contextPath}",id);
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