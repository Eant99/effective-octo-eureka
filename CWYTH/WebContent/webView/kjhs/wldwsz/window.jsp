<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>开户银行</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
select {
	font-size: 12px !important;
	max-height: 25px !important;
	padding: 0px !important;
	padding-left: 5px !important;
}
</style>
</head>
<body>
	<div class="fullscreen">
		
		<div class="container-fluid">
			<div class='responsive-table'>
				<div class='scrollable-area'>
					<input type="hidden" name="dmxh" value="${dmxh}" />
					<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的银行，然后<strong>双击</strong>这条信息;
			        </div>
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>开户银行名称</th>
								<th>开户银行帐号</th>															
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
					{
						"data" : "GUID",
						orderable : false,
						"render" : function(data, type, full, meta) {
							return '<input type="checkbox" class="keyId" name="dmxh" bz="'+full.BZS+'" value="' + data + '">';
						},
						"width" : 10,
						'searchable' : false
					},	
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		            	return data;
		            },"width":41,"searchable": false,"class":"text-center"},
					{
						"data" : "YHMC",
						defaultContent : "",
							"render" : function(data, type, full, meta) {
								return '<input type="hidden"  class="keyId" name="yhmc" bz="'+full.BZS+'" value="' + data + '">'+data+'';
							}
					},
					{
						"data" : "KHYHH",
						defaultContent : "",
						"render" : function(data, type, full, meta) {
							return '<input type="hidden"  class="keyId" name="khyhh" bz="'+full.BZS+'" value="' + data + '">'+data+'';
						}
						
					}
					 ];
			table = $('#mydatatables').DataTable({
		        ajax: {
		            url: "${pageContext.request.contextPath}/yhzhgl/getPageList"//获取数据的方法
		        },
		        "lengthMenu":getTopFrame().window.sessionRowNumLength,
		        "order": [ 2, 'asc' ],
		        "serverSide": true,
		        "columns": columns,
		        "language":{
		            "search":"",
		            "searchPlaceholder":"请输入银行信息关键字"
		        },
		        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
		    });
			//综合查询
			$("#btn_search").on("click", function() {
				var json = searchJson("searchBox");
				$('#mydatatables').DataTable().search(json, "json").draw();
			});
		});
		
		 //双击事件
	    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	    	var val1 = $(this).find("[name='yhmc']").val();
	    	var val2 = $(this).find("[name='khyhh']").val();

	    	if(val1==''||val1==null||val1=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		
	    		getIframeControl("${param.pname}","txt_khyh").val(val1);
	    		getIframeControl("${param.pname}","txt_khyhzh").val(val2);
	        	//getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	        	//getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	    		var winId = getTopFrame().layer.getFrameIndex(window.name);
	        	//alert(val2);
	        	close(winId);
	    	}
	    });
		function reloadList() {
			table.ajax.reload();
		}
		$(function() {
			//列表右侧悬浮按钮
			$(window).resize(
					function() {
						$("div.dataTables_wrapper").width(
								$("#searchBox").width());
						heights = $(window).outerHeight()
								- $(".search").outerHeight()
								- $(".row.bottom").outerHeight() - 20
								- $(".dataTables_scrollHead").outerHeight();
						$(".dataTables_scrollBody").height(heights);
						table.draw();
					});
		});
	</script>
</body>
</html>