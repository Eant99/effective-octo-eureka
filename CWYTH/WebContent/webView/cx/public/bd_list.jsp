<%@page import="com.googosoft.pojo.StateManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>变动资产</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form class="form-inline" role="form" id="myform" action="">
			<div class="search-simple">
				<div class="form-group">
				<c:choose>
					<c:when test="${map.tjbz=='0' }">
						<label>变动单号</label>
						<input type="text" class="form-control" name="bdbgbh" table="t" placeholder="请输入变动报告编号">
					</c:when>
					<c:otherwise>
						<label>${map.tjshow }</label>
						<div class="input-group">
							<input type="text" class="form-control ${map.tjbz}" name="${map.tjjd }" table="t" <c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">value="${olddate}"</c:if> <c:if test="${map.tjbz=='year'}">types="TNL"</c:if><c:if test="${map.tjbz=='nydate'}">types="TML"</c:if> types="${map.tjbz}" placeholder="请输入${map.tjshow }" >
							<c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
							   	</span>
							 </c:if>
						</div>
						<c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">
						<label>--</label>
						<div class="input-group">
							<input type="text" class="form-control ${map.tjbz}" name="${map.tjjd }" table="t" <c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">value="${newdate}"</c:if> <c:if test="${map.tjbz=='year'}">types="TNH"</c:if><c:if test="${map.tjbz=='nydate' }">types="TMH"</c:if> placeholder="请输入${map.tjshow }" >
							<c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
							   	</span>
							 </c:if>
						</div>
						</c:if>
					</c:otherwise>
				</c:choose>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx">
						<i class="fa icon-btn-gjcx"></i>
						<span>高级查询</span>
					</span>
					<div class="search-more">
						<c:if test="${map.tjbz!='0' }">
							<div class="form-group">
								<label>变动单号</label>
								<input type="text" class="form-control" name="bdbgbh" table="t" placeholder="请输入变动报告编号">
							</div>
						</c:if>
						<div class="form-group">
							<label>资产编号</label>
							<input type="text" class="form-control" name="fjbh" table="t" placeholder="请输入资产编号">
						</div>
						<div class="form-group">
							<label>资产名称</label>
							<input type="text" class="form-control input-radius" table="t" name="yqmc" placeholder="请输入资产名称">
						</div>
						<div class="form-group">
							<label>变动前内容</label>
							<input type="text" class="form-control input-radius" table="t" name="bdqnr" placeholder="请输入变动前内容">
						</div>
						<div class="form-group">
							<label>变动后内容</label>
							<input type="text" class="form-control input-radius" table="t" name="bdhnr" placeholder="请输入变动后内容">
						</div>
						<div class="form-group">
							<label>使&ensp;用&ensp;人</label>
							<div class="input-group">
								<input type="text" id="txt_syrxm" class="form-control input-radius" name="syrbh" value="" types="R" table="t" placeholder="请选择使用人">
								<span class="input-group-btn"><button type="button" id="btn_syrxm" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>使用单位</label>
							<div class="input-group">
								<input type="text" id="txt_dwmc" class="form-control input-radius" types="D" table="t" name="sydwbh" placeholder="请选择使用单位">
			     				<span class="input-group-btn"><button type="button" id="btn_dwmc" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									<i class="fa icon-cuowu"></i>
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
                    <button type="button" id="btn_exp"  class="btn btn-default" >导出Excel</button>
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
					        <th>变动单号</th>
					        <th>记账日期</th>
					        <th>资产编号</th>
							<th>资产名称</th>
							<th>变动项目</th>
							<th>变动前内容</th>
							<th>变动后内容</th>
							<th>使用人</th>
							<th>使用单位</th>
							<th>总价</th>
							<th>购置日期</th>
							<th>使用方向</th>
							<th>存放地点</th>
<!-- 						        <th>操作</th> -->
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
$(function (){
// 	var preyear = new Date().getFullYear();//今年，获取上个月之后改成了去年
// 	var prem = new Date().getMonth();
// 	var premonth = "";
// 	if(prem==0){
// 		premonth = preyear - 1 + "12";
// 	}
// 	else if(prem < 10&&prem>0){
// 		premonth = preyear + "0" + prem;
// 	}
// 	else{
// 		premonth = preyear + "" + prem;
// 	}
// 	preyear = preyear - 1;
// 	$(".year").prop("data-format","yyyy").addClass("date").val(preyear);
// 	$(".month").prop("data-format","yyyy-mm").addClass("date").val(premonth);
// 	$(".year").datetimepicker({
// 		format: "yyyy",
// 		autoclose: true,
// 		startView: 4, 
// 		minView: 4
// 	});
// 	$(".month").datetimepicker({
// 		format: "yyyy-mm",
// 		autoclose: true,
// 		startView: 3, 
// 		minView: 3
// 	});

	//列表数据
   	var columns = [
	   	{"data": "BDBGBH","render": function (data, type, full, meta){
			return '<input type="checkbox" class="keyId" name="id" id="' + data + '" fjbh="'+full.FJBH+'" value="' + full.FJBH + '">';
	   	},"width":20,'searchable': false,orderable:false},	
	   	{"data":"_XH",'render': function (data, type, full, meta){
			return data;
	   	},"width":41,"searchable": false,orderable:false,"class":"text-center"},
	   	{"data": "BDBGBH",defaultContent:""},
	   	{"data": "JZRQ",defaultContent:""},
	   	{"data": "FJBH",'render':function (data, type, full, meta){
	   		return '<span class="btn btn-link zclook" yqbh="'+(data==undefined? '': data)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
		}},
		{"data": "YQMC",defaultContent:""},
	   	{"data": "BDXM",defaultContent:""},
	   	{"data": "BDQNR",defaultContent:""},
	   	{"data": "BDHNR",defaultContent:""},
	   	{"data": "SYR",defaultContent:"","render":function(data, type, full,  meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.SYRBH==undefined ? '':full.SYRBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
 	   	}},
 		{"data": "SYDW","render":function(data, type, full,  meta){
 	    	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.SYDWBH==undefined? '': full.SYDWBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
 	    }},
	   	{"data": "ZZJ",defaultContent:"","class":"text-right"},
	   	{"data": "GZRQ",defaultContent:""},  
 	   {"data": "SYFX",defaultContent:""}, 
 	   {"data": "BZXX",defaultContent:"","render":function(data, type, full,  meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link ddlook" ddbh="'+(full.DD==undefined ? '':full.DD)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
	   	}}
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/select/getPublicList_bd?jdbh=${jdbh}",[2,'asc'],columns,0,1,setGroup);
   	$("#btn_search").click();
   	
   	//查看操作
   	$(document).on("click",".btn_look",function(){
   		var yqbh = $(this).parents("tr").find("[name='id']").val();
		select_commonWin("${pageContext.request.contextPath}/zjb/goLookPage?yqbh="+yqbh,"资产信息",1200,630,"yes"); 
	   	return false;
   	});
   	$("#btn_dwmc").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_dwmc","单位信息","920","630");
    });
   	$("#btn_syrxm").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_syrxm","人员信息","920","630");
    });
   	$("#txt_syrxm").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("#txt_dwmc").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	//导出Excel
   	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		var fjbh = [];
		checkbox.each(function(){
			id.push($(this).prop("id"));
			
		});
		checkbox.each(function(){
			fjbh.push($(this).attr("fjbh"));
		});
		doExp(json,"${pageContext.request.contextPath}/select/doExp_bd?jdbh=${jdbh}&where=${map.text}","资产信息","${pageContext.request.contextPath}",id.join(",")+"&fjbh="+fjbh.join(","));
	});
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