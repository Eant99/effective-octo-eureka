<%@page import="com.googosoft.constant.MenuFlag"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@page import="com.googosoft.pojo.StateManager"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>入库资产</title>
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
							<label>资产编号</label>
							<input type="text" class="form-control" name="yqbh" table="K" placeholder="请输入资产编号">
						</c:when>
						<c:otherwise>
							<label>${map.tjshow}</label>
							<div class="input-group">
								<input type="text" class="form-control ${map.tjbz}" style="width:120px;" name="${map.tjjd }" table="K" <c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">value="${olddate}"</c:if> <c:if test="${map.tjbz=='year'}">types="TNL"</c:if><c:if test="${map.tjbz=='nydate'}">types="TML"</c:if> types="${map.tjbz}" placeholder="请输入${map.tjshow }" >
								<c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">
									<span class='input-group-addon'>
								    	<i class="glyphicon glyphicon-calendar"></i>
								   	</span>
								 </c:if>
							</div>
							<c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">
								<label>--</label>
								<div class="input-group">
									<input type="text" class="form-control ${map.tjbz}" style="width:120px;" name="${map.tjjd }" table="K" <c:if test="${map.tjbz=='year' or map.tjbz=='nydate'}">value="${newdate}"</c:if> <c:if test="${map.tjbz=='year'}">types="TNH"</c:if><c:if test="${map.tjbz=='nydate' }">types="TMH"</c:if> placeholder="请输入${map.tjshow }" >
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
				<c:if test="${jdbh!=qbzc}">
					<div class="form-group">
						<label>资产名称</label>
						<input type="text" id="txt_yqmc" class="form-control input-radius" table="K" name="yqmc" placeholder="请输入资产名称">
					</div>
				</c:if>
				<c:if test="${jdbh == qbzc or jdbh == jrrk or jdbh == bzrk or jdbh == byrk or jdbh == bnrk }">
					<div class="form-group">
				        <label>使&ensp;用&ensp;人</label>
				        <div class="input-group">	
					        <input type="text" id="txt_syr" class="form-control input-radius" name="syr" value="" types="R" table="K" placeholder="请选择使用人">
					        <span class="input-group-btn"><button type="button" id="btn_syr" class="btn btn-link ">选择</button></span>
						</div>
					</div>
				</c:if>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
<!-- 					<button type="button" id="btn_dayin3" class="btn btn-default">打印卡片</button><i class='fa icon-add'></i> -->
					<button type="button" id="btn_gzcx" class="btn btn-default">跟踪查询</button>
					<button type="button" id="btn_zhcx" class="btn btn-default">综合查询</button>
					<button type="button" id="btn_zhpx" class="btn btn-default">组合排序</button>
					<c:if test="${jdbh!=dwryfl && mkbh != mkbh_wdqbzc}">
					<button type="button" id="btn_hzfx" class="btn btn-default">汇总分析</button>
					</c:if>
					<div class="btn-group">
				        <button class="btn btn-default dropdown-toggle" type="button" data-toggle="dropdown">
				            <i class='fa icon-sangangy'></i>
				        </button>
				        <ul class="dropdown-menu dropdown-menu-right" role="menu">
				        	<c:if test="${jdbh!=dwryfl}">
				            <li><a id="btn_dayin1" class="btn btn-default">打印预览</a></li>
				       		</c:if>
				            <li><a id="btn_dayin2" class="btn btn-default">打印列表</a></li>
				            <!--<li><a class="btn btn-default" id="btn_txm">打印条码</a></li>-->
							<li><a id="btn_exp" class="btn btn-default">导出Excel</a></li>
				        </ul>
				    </div>
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
					        <th>资产编号</th>
							<th>资产名称</th>
							<th>分类号</th>
							<th>分类名称</th>
							<th>现状</th>
							<th>套（件）数</th>
							<th>单价</th>
							<th>总价</th>
							<th>使用人</th>
							<th>使用单位</th>
							<th>使用方向</th>
							<th>存放地点</th>
							<th>规格</th>
							<th>型号</th>
							<th>凭证号</th>
							<th>项目号</th>
							<th>资产来源</th>
							<th>购置日期</th>
							<th>入账时间</th>
							<th>经手人</th>
							<th>采购人</th>
							<th>归口审核人员</th>
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
	//列表数据
   	var columns = [
	   	{"data": "YQBH","render": function (data, type, full, meta){
			return '<input type="checkbox" class="keyId" name="id" value="' + data + '">';
	   	},"width":20,'searchable': false,orderable:false},
	   	{"data":"_XH",'render': function (data, type, full, meta){
			return data;
	   	},"width":41,"searchable": false,orderable:false,"class":"text-center"},
	   	{"data": "YQBH","zhpxname":"资产编号",'render':function (data, type, full, meta){
	   		return '<span class="btn btn-link zclook" yqbh="'+(data==undefined? '': data)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
		}},
		{"data": "YQMC","zhpxname":"资产名称",defaultContent:""},
	   	{"data": "FLH","zhpxname":"分类号",defaultContent:""},
	   	{"data": "FLMC","zhpxname":"分类名称",defaultContent:""},
	   	{"data": "XZ","zhpxname":"现状",defaultContent:""},
	   	{"data": "SYKH","zhpxname":"套（件）数",defaultContent:"","class":"text-right"},
	   	{"data": "DJ","zhpxname":"单价",defaultContent:"","class":"text-right"},
	   	{"data": "ZZJ","zhpxname":"总价",defaultContent:"","class":"text-right"},
	   	{"data": "SYR","zhpxname":"使用人",defaultContent:"","render":function(data, type, full,  meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.SYRBH==undefined ? '':full.SYRBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
 	   	}},
 	    {"data": "SYDW","zhpxname":"使用单位","render":function(data, type, full,  meta){
 	    	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.SYDWBH==undefined? '': full.SYDWBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
 	    }},
 	    {"data": "SYFX","zhpxname":"使用方向",defaultContent:""},
 	    {"data": "BZXX","zhpxname":"存放地点",defaultContent:"","render":function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link ddlook" ddbh="'+(full.DD==undefined ? '':full.DD)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
	   	}}, 
	   	{"data": "XH","zhpxname":"型号",defaultContent:""},
	   	{"data": "GG","zhpxname":"规格",defaultContent:""},
	   	{"data": "PZH","zhpxname":"凭证号",defaultContent:""},
	   	{"data": "XMH","zhpxname":"项目号",defaultContent:""},
	   	{"data": "ZCLY","zhpxname":"资产来源",defaultContent:""},
	   	{"data": "GZRQ","zhpxname":"购置日期",defaultContent:""},
	   	{"data": "RZRQ","zhpxname":"入账日期",defaultContent:""},
	   	{"data": "JSR","zhpxname":"经手人",defaultContent:"","render":function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.JSRBH==undefined ? '':full.JSRBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
 	   	}},
 	    {"data": "CGR","zhpxname":"采购人",defaultContent:"","render":function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.CGRBH==undefined ? '':full.CGRBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
	   	}},
	    {"data": "GKRY","zhpxname":"归口审核人员",defaultContent:"","render":function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.GKRYBH==undefined ? '':full.GKRYBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
	   	}}
	   	
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/select/getPublicList?jdbh=${jdbh}",[2,'asc'],columns,"","",setGroup);
   	$("#btn_search").click();
   	
   	//查看操作
   	$(document).on("click",".btn_look",function(){
   		var yqbh = $(this).parents("tr").find("[name='id']").val();
		select_commonWin("${pageContext.request.contextPath}/zjb/goLookPage?yqbh="+yqbh,"资产信息",1200,630,"yes"); 
	   	return false;
   	});
   	$("#sel_flh").bindChange("${pageContext.request.contextPath}/suggest/getXx","FC");
  	//分类代码弹窗
	$("#btn_flh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goWindow?controlId=sel_flh&&Next=C","分类信息","920","630");
    });
	//联想输入提示
	$("#txt_syr").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
   	//弹窗
   	$("#btn_syr").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_syr","人员信息","920","630");
    });
    //导出Excel
   	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
		doExp(json,"${pageContext.request.contextPath}/select/doExp?jdbh=${jdbh}&where=${map.text}","资产信息","${pageContext.request.contextPath}",id.join(","));
		return false;
	});
  	//跟踪查询
   	$("#btn_gzcx").click(function(){
   		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
   		if(checkbox.length<1){
   			alert("请选择跟踪信息！");
   		}else{
			var id = [];
			checkbox.each(function(){
				id.push($(this).val());
			});
	   		window.open("${pageContext.request.contextPath}/zjb/doGzcx?yqbh="+id.join("','"));
			return false;
   		}
   	});
  
	//打印卡片
	$("#btn_dayin3").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		if($checked.length != 1){
			alert("请先选择一条数据进行打印！");
		}else{
			var yqbh = [];
			$checked.each(function(){
				yqbh.push($(this).val());
			})
			window.open("${pageContext.request.contextPath}/zcxxcx/doPrintCard?yqbh="+yqbh.join("','"));
		}
		return false;
	});
	//打印卡片
	$("#btn_dayin1").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		if($checked.length != 1){
			alert("请先选择一条数据进行打印！");
		}else{
			var yqbh = $checked.val();
			window.open("${pageContext.request.contextPath}/zcxxcx/doPrintCard?yqbh="+yqbh);
		}
		return false;
	});
  
	//打印列表
	$("#btn_dayin2").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		$checked.each(function(){
			id.push($(this).val());
		});
		window.open("${pageContext.request.contextPath}/zcxxcx/doPrintCardlb?yqbh="+id.join(","));
		return false;
	});
  
	//打印条码
	$("#btn_txm").click(function(){
		var $checked = $("#mydatatables").find("[name='id']").filter(":checked");
		if($checked.length > 0){
			var id = [];
			$checked.each(function(){
				id.push($(this).val());
			});
			window.open("${pageContext.request.contextPath}/zcxxcx/doPrintTxm?lb=1&id="+id.join(","));
		}else{
			alert("请先选择数据再进行打印！");
		}
		return false;
	});
  	//排序组合
	$("#btn_zhpx").click(function(){
		var list = [];
		for(var i = 0; i < columns.length; i++){
			if(columns[i].zhpxname != undefined){
				list.push("{zhpxzd:'" + columns[i].data + "',zhpxname:'" + columns[i].zhpxname + "'}");
			}
		}
		select_commonWin("${pageContext.request.contextPath}/webView/cx/public/pxzh_list.jsp?jdbh=${jdbh}&columns=" + encodeURIComponent("[" + list.join(",") + "]"), "选择组合排序", 500, 500);
		return false;
	});
	//汇总分析
	$("#btn_hzfx").click(function(){
		select_commonWin("${pageContext.request.contextPath}/hzfx/goHzfxPage?lx=2&jdbh=${jdbh}", "汇总分析", 500, 400);
		return false;
	});
	$("#btn_zhcx").click(function(){
		var list = [];
		list.push("{zhcxzdm:'flh',zhcxmc:'分类号'}");
		list.push("{zhcxzdm:'flmc',zhcxmc:'分类名称'}");
		list.push("{zhcxzdm:'xz',zhcxmc:'现状',zdlx:'zl_<%=Constant.XZ%>'}");
		list.push("{zhcxzdm:'dj',zhcxmc:'单价',zdlx:'number'}");
		list.push("{zhcxzdm:'zzj',zhcxmc:'总价',zdlx:'number'}");
		list.push("{zhcxzdm:'syr',zhcxmc:'使用人',zdlx:'ry'}");
		list.push("{zhcxzdm:'sydw',zhcxmc:'使用单位',zdlx:'dw'}");
		list.push("{zhcxzdm:'syfx',zhcxmc:'使用方向',zdlx:'zl_<%=Constant.SYFX%>'}");
		list.push("{zhcxzdm:'bzxx',zhcxmc:'存放地点',zdlx:'dd'}");
		list.push("{zhcxzdm:'xh',zhcxmc:'型号'}");
		list.push("{zhcxzdm:'gg',zhcxmc:'规格'}");
		list.push("{zhcxzdm:'zcly',zhcxmc:'资产来源',zdlx:'zl_<%=Constant.ZCLY%>'}");
		list.push("{zhcxzdm:'gzrq',zhcxmc:'购置日期',zdlx:'date'}");
		list.push("{zhcxzdm:'jsr',zhcxmc:'经手人',zdlx:'ry'}");
		list.push("{zhcxzdm:'cgr',zhcxmc:'采购人',zdlx:'ryall'}");
		zhcx_win("${pageContext.request.contextPath}/webView/cx/zhcx/zhcxfrm.jsp?mkbh=${param.mkbh}&cols=" + encodeURIComponent("[" + list.join(",") + "]"));
		return false;
	});

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