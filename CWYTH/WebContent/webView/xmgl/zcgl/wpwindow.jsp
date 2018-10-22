<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>物品信息</title>
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
					<input type="hidden" name="flmc" id="flmc" />
					<div class="alert alert-info" style="padding: 6px;margin-bottom: 4px;">
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的物品，然后<strong>双击</strong>这条信息;
			        </div>
					<table id="mydatatables" class="table table-striped table-bordered">					
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>物品编号</th>
								<th>物品名称</th>
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
					{"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
                     return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
                      },"width":10,'searchable': false},	
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		            	return data;
		            },"width":41,"searchable": false,"class":"text-center"},
					{
						"data" : "WPBH",
						defaultContent : "001"
					},
					{
						"data" : "WPMC",						
						defaultContent : "电脑",
							"render" : function(data, type, full, meta) {
								return '<input type="hidden"  class="keyId" name="wpmc" bz="'+full.BZS+'" value="' + data + '">'+data+'';
							}
					}				
					 ];
			table = $('#mydatatables').DataTable({
		        ajax: {
		            url: "${ctx}/zclr/getPageList1"//获取数据的方法
		        },
		        "lengthMenu":getTopFrame().window.sessionRowNumLength,
		        "order": [ 2, 'asc' ],
		        "serverSide": true,
		        "columns": columns,
		        "language":{
		            "search":"",
		            "searchPlaceholder":"请输入物品名称"
		        },
		        "dom":"<'row'<'col-md-7 col-sm-7 col-xs-7'li><'col-md-5 col-sm-5 col-xs-5'f>>t<'row'<'col-md-12 col-sm-12 col-xs-12 pull-right'p>>"
		    });
			$("input[type=search]").parent("label").addClass("zhxxcx");
			$("input[type=search]").after("<i class='fa icon-chaxun'></i>").css("width",search_wid);
			//综合查询
			$("#btn_search").on("click", function() {
				var json = searchJson("searchBox");
				$('#mydatatables').DataTable().search(json, "json").draw();
			});
		});
		
		 //双击事件
	    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	    	var val1 = $(this).find("[name='wpmc']").val();
            //var val2 = $("#flmc").val();
            var val2 ="测试目录02";
	    	if(val1==''||val1==null||val1=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		
	    		getIframeControl("iframe_130701","txt_wpmc").val(val1);  
	    		getIframeControl("iframe_130701","txt_cgml").val(val2); 
	    		var winId =  top.layer.getFrameIndex(parent.window.name);
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