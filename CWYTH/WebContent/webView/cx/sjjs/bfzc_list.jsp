<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.googosoft.constant.Constant"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>使用人与所在单位不符的资产浏览</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>资产编号</label>
					 <input type="text" id="txt_yqbh" class="form-control" name="yqbh"  table="K" placeholder="请输入资产编号">
				</div>
				<div class="form-group">
					<label>资产名称</label>
					 <input type="text" id="txt_yqmc" class="form-control" name="yqmc"  table="K" placeholder="请输入资产名称">
				</div>
				<div class="form-group">
					<label>分&ensp;类&ensp;号</label>
					<div class="input-group">
						<input type="text" id="sel_flh" class="form-control input-radius" types="F" table="K" name="flh" placeholder="请选择分类号">
						<span class="input-group-btn"><button type="button" id="btn_flh" class="btn btn-link ">选择</button></span>
					</div>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<c:if test="${empty dwmc}">
					<span class="alert alert-info"><strong>查询条件：</strong>管理权限下资产</span>
				</c:if>
				<c:if test="${not empty dwmc}">
					<span class="alert alert-info"><strong>查询条件：</strong>${dwmc }资产</span>
				</c:if>
				<%-- <div id="btn_search_more">
					<span id="btn_search_gjcx">
						<i class="fa icon-btn-gjcx"></i>
						<span>高级查询</span>
					</span>
					<div class="search-more">
						<div class="form-group">
							<label>分&ensp;类&ensp;号</label>
							<input type="text" id="txt_flh" class="form-control" table="K" name="flh" placeholder="请输入分类号">
						</div>
						<div class="form-group">
							<label>分类名称</label>
						    <input type="text" id="txt_flmc" class="form-control" name="flmc" table="K" placeholder="请输入分类名称">
						</div>
						<div class="form-group">
					        <label>使&ensp;用&ensp;人</label>
					        <div class="input-group">
						        <input type="text" id="txt_syr" class="form-control input-radius" name="syr" value="" types="R" table="K" placeholder="请选择使用人">
						        <span class="input-group-btn"><button type="button" id="btn_syr" class="btn btn-link ">选择</button></span>
					        </div>
				        </div>
				        <div class="form-group">
							<label>使用单位</label>
							<div class="input-group">
								<input type="text" id="txt_sydw" class="form-control input-radius" name="sydw" value="" types="D" table="K" placeholder="请选择使用单位">
								<span class="input-group-btn"><button type="button" id="btn_sydw" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>使用方向</label>
							<select id="drp_syfx" class="form-control" name="syfx" table="K" types="E">
	               				<option value="">未选择</option>
	                            <c:forEach var="syfxList" items="${syfxList}"> 
	                                <option value="${syfxList.DM}" >${syfxList.MC}</option>
								</c:forEach>
	                		</select>
						</div>
						<div class="form-group">
							<label>现&emsp;&emsp;状</label>
							<select id="drp_xz" class="form-control" name="xz" table="K" types="E">
	               				<option value="">未选择</option>
	                            <c:forEach var="xzList" items="${xzList}"> 
	                                <option value="${xzList.DM}" >${xzList.MC}</option>
								</c:forEach>
	                		</select>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search">
									<i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div> --%>
               <div class="btn-group pull-right" role="group">
               	   <button type="button" id="btn_zhcx" class="btn btn-default">综合查询</button>
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
<!-- 			            <th>状态</th> -->
			            <th>资产编号</th>
						<th>资产名称</th>
						<th>分类号</th>
						<th>分类名称</th>
			            <th>单价</th>
			            <th>总价</th>
			            <th>档案号</th>
			            <th>调转入日期</th>
			            <th>使用方向</th>
			            <th>使用人</th>	
			            <th>使用人所在部门</th>
			            <th>使用单位</th>	
			            <th>现状</th>	
			            <th>生产厂家</th>
			            <th>型号</th>	
			            <th>规格</th>
			            <th>资产来源</th>	
			            <th>经费来源</th>	
			            <th>计量单位</th>	
			            <th>学科</th>	
			            <th>学科类别</th>
			            <th>记账类型</th>
			            <th>财务审核意见</th>
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
$(function (){
	//联想输入提示
	$("#txt_syr").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	$("#txt_sydw").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
   	//弹窗
   	$("#btn_syr").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_syr","人员信息","920","630");
    });
   	$("#btn_sydw").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_sydw","单位信息","920","630");
    });
   	//列表数据
   	var columns = [
		{"data": "YQBH",orderable:false, "render": function (data, type, full, meta){
	       	return '<input type="checkbox" class="keyId" name="yqbh" yqh="'+full.YQH+'" value="' + data + '">';
	    },"width":10,'searchable': false},
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},
// 		{"data": "ZTBZ",defaultContent:""},
		{"data": "YQBH",'render':function (data, type, full, meta){
	   		return '<span class="btn btn-link zclook" yqbh="'+(full.YQH==undefined? '': full.YQH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</span>';
		}},
		{"data": "YQMC",defaultContent:""},    
		{"data": "FLH",defaultContent:""},    
		{"data": "FLMC",defaultContent:""},    
	   	{"data": "DJ",defaultContent:"",class:"text-right"},
	   	{"data": "ZZJ",defaultContent:"",class:"text-right"},
		{"data": "DABH",defaultContent:""},
		{"data": "DZRRQ",defaultContent:""},
	   	{"data": "SYFX",defaultContent:""},
		{"data": "SYR",'render':function(data,type,full,meta){
    		return '<a href="javascript:void(0);" id="look" class="btn btn-link rylook" rybh="'+(full.SYRH==undefined? '': full.SYRH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
        }},
        {"data": "SYRBM","render":function (data, type, full, meta){
	    	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.SYRBMH==undefined? '': full.SYRBMH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
		},orderable:false},
		{"data": "SYDW","render":function (data, type, full, meta){
	    	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.SYDWH==undefined? '': full.SYDWH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
		},orderable:false},
	   	{"data": "XZ",defaultContent:""},
	   	{"data": "SCCJ",defaultContent:""},
		{"data": "XH",defaultContent:""},
		{"data": "GG",defaultContent:""},
		{"data": "ZCLY",defaultContent:""},
		{"data": "JFKM",defaultContent:""},
		{"data": "JLDW",defaultContent:""},
		{"data": "XK",defaultContent:""},
		{"data": "XKLB",defaultContent:""},
		{"data": "JZLX",defaultContent:""},
		{"data": "SHYJ",defaultContent:""},
	];
   	table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/bfzc/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,"","",setGroup);
   
 	 //综合查询
   	$("#btn_zhcx").click(function(){
		var list = [];
		list.push("{zhcxzdm:'ztbz',zhcxmc:'状态'}");
		list.push("{zhcxzdm:'flh',zhcxmc:'分类号'}");
		list.push("{zhcxzdm:'flmc',zhcxmc:'分类名称'}");
		list.push("{zhcxzdm:'dj',zhcxmc:'单价',zdlx:'number'}");
		list.push("{zhcxzdm:'zzj',zhcxmc:'总价',zdlx:'number'}");
		list.push("{zhcxzdm:'dabh',zhcxmc:'档案号'}");
		list.push("{zhcxzdm:'dzrrq',zhcxmc:'调转入日期',zdlx:'date'}");
		list.push("{zhcxzdm:'syfx',zhcxmc:'使用方向',zdlx:'zl_<%=Constant.SYFX%>'}");
		list.push("{zhcxzdm:'jldw',zhcxmc:'计量单位',zdlx:'zl_<%=Constant.JLDW%>'}");
		list.push("{zhcxzdm:'syr',zhcxmc:'使用人',zdlx:'ry'}");
 		list.push("{zhcxzdm:'sydw',zhcxmc:'使用单位',zdlx:'dw'}");
		list.push("{zhcxzdm:'xz',zhcxmc:'现状',zdlx:'zl_<%=Constant.XZ%>'}");
		list.push("{zhcxzdm:'sccj',zhcxmc:'生产厂家'}");
		list.push("{zhcxzdm:'xh',zhcxmc:'型号'}");
		list.push("{zhcxzdm:'gg',zhcxmc:'规格'}");
		list.push("{zhcxzdm:'jfkm',zhcxmc:'经费来源',zdlx:'zl_<%=Constant.JFKM%>'}");
		list.push("{zhcxzdm:'zcly',zhcxmc:'资产来源',zdlx:'zl_<%=Constant.ZCLY%>'}");
		list.push("{zhcxzdm:'xk',zhcxmc:'学科',zdlx:'zl_<%=Constant.XKML%>'}");
		list.push("{zhcxzdm:'xklb',zhcxmc:'学科类别',zdlx:'zl_<%=Constant.XKLB%>'}");
		list.push("{zhcxzdm:'jzlx',zhcxmc:'记账类型',zdlx:'zl_<%=Constant.JZLX%>'}");
		list.push("{zhcxzdm:'shyj',zhcxmc:'财务审核意见'}");
		zhcx_win("${pageContext.request.contextPath}/webView/cx/zhcx/zhcxfrm.jsp?mkbh=${param.mkbh}&cols=" + encodeURIComponent("[" + list.join(",") + "]"));
		return false;
	});
   	
    //导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='yqbh']").filter(":checked");
		var yqbh = [];
		checkbox.each(function(){
			yqbh.push($(this).attr("yqh"));
		});
   		doExp(json,"${pageContext.request.contextPath}/bfzc/expExcel?treedwbh=${dwbh}","使用人与所在单位不符的资产信息","${pageContext.request.contextPath}",yqbh.join(","));
   	});
   	//综合查询
    $("#btn_search").on("click",function(){
		var json = searchJson("searchBox");
    	$('#mydatatables').DataTable().search(json,"json").draw();
    });
    $("#sel_flh").bindChange("${pageContext.request.contextPath}/suggest/getXx","FC");
  	//分类代码弹窗
	$("#btn_flh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/zcfltree/goWindow?controlId=sel_flh&&Next=C","分类信息","920","630");
    });
	$(".btn_search_more").on("click", function(){
		$(".btn_search_more").toggleClass("dropup");
		$("#searchBox .row:not(:first-child)").toggleClass("hide");
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