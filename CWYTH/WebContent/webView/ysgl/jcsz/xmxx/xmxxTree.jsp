<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目信息列表</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="k"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="k"   placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
<%-- 				<c:if test="${empty isWindow}"> --%>
<!-- 					<div class="btn-group pull-right" role="group"> -->
<!-- 		               <button type="button" class="btn btn-default" id="btn-add">增加</button> -->
<!-- 		               <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
<!-- 		               <button type="button" class="btn btn-default" id="btn_export">导出Excel</button> -->
<!-- 					</div> -->
<%-- 				</c:if> --%>
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
				            <th>项目编号</th>
				            <th>部门名称</th>
				            <th>项目名称</th>
				            <th>项目大类</th>
				            <th>项目类别</th>
				            <th>项目类型</th>
				            <th>负责人</th>
				            <th>项目属性</th>
				            <th>归口部门</th>
				            <th>是否启用</th>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
var target = "${ctx}/ysgl/xmsz/xmxx";
$(function () {
	var winId = getTopFrame().layer.getFrameIndex(window.name);
	//加载列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" values="'+full.XMMC+'" >';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
      {"data": "XMBH",defaultContent:"","class":"text-left"},
//        {"data": "XMBH","render":function (data, type, full, meta){
// 		   	return '<div><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.guid+'">'+ data +'</a></div>';
// 	   }},
       {"data": "BMBH",defaultContent:"","class":"text-left"},
       {"data": "XMMC",defaultContent:"","class":"text-left"},
       {"data": "XMDLMC",defaultContent:"","class":"text-left"},
       {"data": "XMLBMC",defaultContent:"","class":"text-center",},
       {"data": "XMLX",defaultContent:"","width":120},
       {"data": "FZRMC",defaultContent:"","width":120,},
       {"data": "XMSXMC",defaultContent:"","class":"text-center",},
       {"data": "GKBMMC",defaultContent:"","width":120,},
       {"data": "SFQYMC",defaultContent:"","class":"text-center"}
     ];
    table = getDataTableByListHj("mydatatables",target+"/getXmxxPageData",[4,'asc'],columns);
// 		//双击事件
// 	    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
// 	    	var val = $(this).find("[name='guid']").attr("xmmc");
// 	    	if(val==''||val==null||val=='undefined'){
// 	    		alert("没有可以选择的数据！");
// 	    	}else{
// 	    		console.log("${param.pname}__________________________${param.controlId}");
// 	    		getIframeControl("${param.pname}","${param.controlId}").val(val);
// 	        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
// 	        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
// 	        	close(winId);
// 	    	}
// 	    });
	  //双击事件
	    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
	    	var val = $(this).find("[name='guid']").attr("values");
	    	var guid = $(this).find("[name='guid']").val();
	    	if(val==''||val==null||val=='undefined'){
	    		alert("没有可以选择的数据！");
	    	}else{
	    		getIframeControl("${param.pname}","${param.controlId}").val(val);
	    		var id = "${param.id}";
	    		if(id!="undefined"&&id!=""&&id!=null){
	    			getIframeControl("${param.pname}","${param.id}").val(guid);
	    		}
	        	getIframeControl("${param.pname}","${param.controlId}").focus();//手动触发验证
	        	getIframeControl("${param.pname}","${param.controlId}").trigger("blur");//手动触发验证
	        	close(winId);
	    	}
	    });
		//联想输入提示
		$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
		
// 	    	table = getDataTableByListHj("mydatatables","${ctx}/xmxxt/getPageList",[2,'asc'],columns,0,1,setGroup);
		//新增
		$("#btn_add").click(function() {
			doOperate("${ctx}/xmxxt/goXmxxEditPage?dwbh=${param.dwbh}", "C");//operateType=C
		});
		//修改
		$(document).on("click", ".btn_upd", function() {
			var dwbh = $(this).attr("dwbh");
			console.log("__"+dwbh);
			doOperate("${ctx}/xmxxt/goXmxxEditPage?dwbh="+dwbh, "U");
		});
		//批量赋值
		$("#btn_assig").click(
				function() {
					var checkbox = $("#mydatatables").find("[name='keyId']")
							.filter(":checked");
					var djdw = $("#mydatatables").find(".djdw").filter(
							":checked");
					if (djdw.length > 0) {
						alert("包含最顶级单位，不允许进行批量赋值，请单独修改！");
					} else {
						if (checkbox.length > 0) {
							var dwbh = [];
							checkbox.each(function() {
								dwbh.push($(this).val());
							});
							confirm("确认要批量赋值" + dwbh.length + "条信息？", {
								title : "提示"
							}, function(index) {
								select_commonWin(
										"${ctx}/dwb/goPlfuzhiPage?dwbh="
												+ dwbh.join(","), "批量赋值",
										"400", "450");
								close(index);
							});
						} else {
							alert("请选择至少一条信息赋值！");
						}
					}
				});
		//删除
		$(document)
				.on(
						"click",
						".btn_delxx",
						function() {
							var dwbh = $(this).attr("dwbh");
							doDel(
									"dwbh=" + dwbh,
									"${ctx}/dwb/doDelete",
									function(val) {
										parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
									}, function(val) {
									}, "1");
						});
		//批量删除
		$("#btn_del")
				.click(
						function() {
							var checkbox = $("#mydatatables").find(
									"[name='keyId']").filter(":checked");
							var djdw = $("#mydatatables").find(".djdw").filter(
									":checked");
							if (djdw.length > 0) {
								alert("包含最顶级单位，不允许删除！");
							} else {
								if (checkbox.length > 0) {
									var dwbh = [];
									checkbox.each(function() {
										dwbh.push($(this).val());
									});
									doDel(
											"dwbh=" + dwbh.join(","),
											"${ctx}/dwb/doDelete",
											function(val) {
												parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
											}, function(val) {
											}, dwbh.length);
								} else {
									alert("请选择至少一条信息删除！");
								}
							}

						});
		//查看按钮
		$("#btn_look")
				.click(
						function() {
							var checkbox = $("#mydatatables").find(
									"[name='keyId']").filter(":checked");
							if (checkbox.length == 1) {
								window.location.href = "${ctx}/dwb/goEditPage?operateType=L&dwbh="
										+ checkbox.val();
							} else {
								alert("只能选择一条信息查看！");
							}
						});
		//导出Excel
		$("#btn_exp")
				.click(
						function() {
							var json = searchJson("searchBox");
							var checkbox = $("#mydatatables").find(
									"[name='keyId']").filter(":checked");
							var id = [];
							checkbox.each(function() {
								id.push($(this).val());
							});
							doExp(json, "${ctx}/dwb/expExcel?treedwbh=${dwbh}",
									"单位信息",
									"${pageContext.request.contextPath}", id
											.join(","));
						});
	});
	$(function() {
		//列表右侧悬浮按钮
		$(window).resize(
				function() {
					$("div.dataTables_wrapper").width($("#searchBox").width());
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