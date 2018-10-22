<%@page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据字典维护</title>
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
			          	<strong>提示：</strong><c:if test="${param.from == 'wxjfhb'}">（1）</c:if>先找到需要的字典种类，然后<strong>双击</strong>这条信息;
			        </div>
					<table id="mydatatables" class="table table-striped table-bordered">
						<thead>
							<tr>
								<th><input type="checkbox" class="select-all" /></th>
								<th>序号</th>
								<th>代码编号</th>
								<th>代码名称</th>
								<th>级别</th>
								<th>种类</th>
								<th>说明</th>
								
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
			
			$("#more").bind(
					'click',
					function() {
						$(this).parents(".box-content").find(
								".row:not(:first):not(:nth-child(2))")
								.toggleClass("hidden");
						$(this).toggleClass("dropup");
					});
			//添加按钮
			$("#btn_add").click(
					function() {
						/*
						if("" == "${param.dm}"){
							alert("请先选择代码类别！");
						}else{
							select_commonWin("${ctx}/dmb/goEditPage?operateType=C&zl=${param.dm}&jb=${param.jb}","数据字典信息","400","450");
						}
						 */
						select_commonWin("${ctx}/dmb/goEditPage?operateType=C",
								"数据字典信息", "400", "450");

					});
			//修改按钮
			$(document).on(
					"click",
					".btn_upd",
					function() {
						var dmxh = $(this).parents("tr").find("[name='dmxh']")
								.val();
						select_commonWin(
								"${ctx}/dmb/goEditPage?operateType=U&dmxh="
										+ dmxh, "数据字典信息", "400", "450");
					});
			//单个删除
			$(document).on("click", ".btn_delxx", function() {
				var dmxh = $(this).parents("tr").find("[name='dmxh']").val();
				doDel("dmxh=" + dmxh, "${ctx}/dmb/doDelete", function(val) {
					table.ajax.reload();
					//....
				}, function(val) {
					//....
				}, "1");
			});
			//批量删除按钮
			$("#btn_del").click(
					function() {
						var checkbox = $("#mydatatables").find("[name='dmxh']")
								.filter(":checked");
						if (checkbox.length > 0) {
							var dmxh = [];
							var flag = true;
							checkbox.each(function() {
								dmxh.push($(this).val());
								if ($(this).attr("bz") == "1") {
									flag = false;
								}
							});
							if (flag) {
								doDel("dmxh=" + dmxh.join(","),
										"${ctx}/dmb/doDelete", function(val) {
											table.ajax.reload();
										}, function(val) {
										}, dmxh.length);
							} else {
								alert("系统定义的信息不能删除！");
							}
						} else {
							alert("请选择至少一条信息删除");
						}
					});
			//列表数据
			var columns = [
					{
						"data" : "DMXH",
						orderable : false,
						"render" : function(data, type, full, meta) {
							return "<input type='checkbox' class='keyId' name='dmxh' dmmc='("+full.DM+")"+full.MC+"' value='"+JSON.stringify(full)+"'>";
						},
						"width" : 10,
						'searchable' : false
					},	
					{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		            	return data;
		            },"width":41,"searchable": false,"class":"text-center"},
					{
						"data" : "DM",
						defaultContent : "",
							"render" : function(data, type, full, meta) {
								return '<input type="hidden"  class="keyId" name="dm" bz="'+full.BZS+'" value="' + data + '">'+data+'';
							}
					},
					{
						"data" : "MC",
						defaultContent : ""
					},
					{
						"data" : "JB",						
						defaultContent : "",
							"render" : function(data, type, full, meta) {
								return '<input type="hidden"  class="keyId" name="jb" bz="'+full.BZS+'" value="' + data + '">'+data+'';
							}
					},
					{
						"data" : "ZL",
						defaultContent : "",
							"render" : function(data, type, full, meta) {
								return '<input type="hidden"  class="keyId" name="zl" bz="'+full.BZS+'" value="' + data + '">'+data+'';
							}
					},
					{
						"data" : "BZ",
						defaultContent : ""
					}
					 ];
			table = $('#mydatatables').DataTable({
		        ajax: {
		            url: "${pageContext.request.contextPath}/dmb/zd1page"//获取数据的方法
		        },
		        "lengthMenu":getTopFrame().window.sessionRowNumLength,
		        "order": [ 2, 'asc' ],
		        "serverSide": true,
		        "columns": columns,
		        "language":{
		            "search":"",
		            "searchPlaceholder":"请输入种类名称"
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
	    	var val1 = $(this).find("[name='jb']").val();
	    	var val2 = $(this).find("[name='dmxh']").attr("dmmc");

	    	if(val1==''||val1==null||val1=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		
	    		getIframeControl("${param.pname}","${param.controlId}").val(val2);
	    		getIframeControl("${param.pname}","jb").val(val1);
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