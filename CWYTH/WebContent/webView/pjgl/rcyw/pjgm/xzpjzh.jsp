<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配方案</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div id='wrapper'>
<section>
	<div class='row' id='content-wrapper'>
		<div class='col-md-12'>
			<div class="box">
				<div class='box-content' style="padding-bottom: 0; overflow:visible;">
					<input type="hidden" name="guid" value="${guid}" />
					<div class="alert alert-info">
			          	<strong>提示：</strong>先找到需要的人员，然后<strong>双击</strong>这条信息。
			        </div>
		        <hr class="hr-normal" id="hr">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th><input type="checkbox" class="select-all"/></th>
				            <th>序号</th>
				            <th>账户名称</th>
				            <th>账户类型</th>
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
					{"data" : "GUID",orderable : false,"render" : function(data, type, full, meta) {
						return "<input type='checkbox' class='keyId' name='guid' pjlx='"+full.PJLX+"'  zhmc='"+full.ZHMC+"' value='"+full.GUID+"'>";},
						"width" : 10,'searchable' : false},	
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
						return data;},"width":41,"searchable": false,"class":"text-center"},
					{"data" : "ZHMC",defaultContent : "","render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="zhmc"  value="' + data + '">'+data+'';}},
					{"data" : "PJLX",defaultContent : "","render" : function(data, type, full, meta) {
						return '<input type="hidden"  class="keyId" name="pjlx"  value="' + data + '">'+data+'';}}
					 ];
		    table = $('#mydatatables').DataTable({
		        ajax: {
		            url: "${ctx}/pjgm/getPjzh"//获取数据的方法
		        },
		         "lengthMenu":getTopFrame().window.sessionRowNumLength,
		         "order": [ 3, 'asc' ],
		       "serverSide": true,
		        "columns": columns,
		          "language":{
		         	"search":"",
		             "searchPlaceholder":"请输入账户类型或账户名称"
		        },  
		        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
		 /*        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
		  */   });
		    $("input[type=search]").parent("label").addClass("zhxxcx");
			$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
		});
		
		 //双击事件
	    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	    	var val1 = $(this).find("[name='guid']").attr("pjlx");
	    	var val2 = $(this).find("[name='guid']").attr("zhmc");
	    	var val3 = $(this).find("[name='guid']").val();
	    	var winId = getTopFrame().layer.getFrameIndex(window.name);
	    	if(val1==''||val1==null||val1=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		getIframeControl("${param.pname}","${param.controlId}").val(val2);
	    		getIframeControl("${param.pname}","${param.controlId1}").val(val1);
	    		getIframeControl("${param.pname}","${param.controlId2}").val(val3);
	        	close(winId);
	    	}
	    });
		$(function() {	
			//列表右侧悬浮按钮
			$(window).resize(function(){
		    	$("div.dataTables_wrapper").width($("#searchBox").width());
		    	heights = $(window).outerHeight()-$(".row.bottom").outerHeight()-$(".dataTables_scrollHead").outerHeight()-20;
		    	$(".dataTables_scrollBody").height(heights);
		    	table.draw();
			});
		});
	</script>
</body>
</html>