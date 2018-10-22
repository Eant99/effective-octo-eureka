<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>个人名下无资产的人员</title>
<%@include file="/static/include/public-list-css.inc"%>
<style type="text/css">
	ul{
		cursor: pointer;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox">
		<form id="myform" class="form-inline" action="">
			<div class="search-simple">
				<div class="form-group">
					<label>人员工号</label>
					<div class="input-group">
						<input type="text" id="txt_rybh" class="form-control input-radius" name="rybh" types="R" table="t" placeholder="请输入人员工号">
						<span class="input-group-btn"><button type="button" id="btn_rybh" class="btn btn-link ">选择</button></span>
					</div>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx">
						<i class="fa icon-btn-gjcx"></i>
						<span>高级查询</span>
					</span>
					<div class="search-more">
						<div class="form-group">
							<label>所在单位</label>
							<div class="input-group">
								<input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" value="" types="D" table="t" placeholder="请选择所在单位">
		                    	<span class="input-group-btn"><button type="button" id="btn_dwbh" class="btn btn-link">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>性&emsp;&emsp;别</label>
							<select id="txt_xb" class="form-control" style="width:150px;" name="xb" table="t" types="E">
			                	<option value="">未选择</option>
			                	<c:forEach var="sexList" items="${sexList}">
				               		<option value="${sexList.DM}" <c:if test="${ryb.XB == sexList.DM}">selected</c:if>>${sexList.MC}</option>
				            	</c:forEach>
			                </select>
						</div>
						<div class="form-group">
							<label>专业职称</label>
							<select id="txt_zyzc" class="form-control select2" style="width:150px;" name="zyzc" table="t" types="E">
			                     <option value="">未选择</option>
			                     <c:forEach var="zyzcList" items="${zyzcList}">
			               			<option value="${zyzcList.DM}" <c:if test="${ryb.zyzc == zyzcList.DM}">selected</c:if>>${zyzcList.MC}</option>
			               		</c:forEach>
		               		</select>
						</div>
						<div class="form-group">
							<label>文化程度</label>
							<select id="txt_whcd" class="form-control select2" style="width:150px;" name="whcd" table="t" types="E">
			                     <option value="">未选择</option>
			                     <c:forEach var="whcdList" items="${whcdList}">
			               			<option value="${whcdList.DM}" <c:if test="${ryb.whcd == whcdList.DM}">selected</c:if>>${whcdList.MC}</option>
			               		 </c:forEach>
			                 </select>
						</div>
						<div class="form-group">
							<label>人员状态</label>
							<select id="drp_ryzt" class="form-control" style="width:150px;" name="ryzt" table="t" types="E">
				                 <option value="">未选择</option>
				                 <option value="1">正常</option>
			                     <option value="0">禁用</option>
			               	</select>
						</div>
						<div class="form-group">
							<label>调入日期</label>
							<div class="input-group">
								<input type="text" id="txt_drrq_begin" class="form-control date" name="drrq" types="TL" table="t" data-format="yyyy-MM-dd" placeholder="开始日期">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
							   	</span>
						   	</div>
							<label>--</label>
							<div class="input-group">
								<input type="text" id="txt_drrq_end" class="form-control date" name="drrq" types="TH" table="t" data-format="yyyy-MM-dd" placeholder="截止日期">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
						   		</span>
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
                    <button id="btn_exp" type="button"  class="btn btn-default" >导出Excel</button>
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
					            <th>工号</th>
					            <th>姓名</th>
					            <th>性别</th>
					            <th>文化程度</th>
					            <th>调入日期</th>
					            <th>专业职称</th>
					            <th>所在单位</th>
					            <th>状态</th>
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
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${pageContext.request.contextPath}/suggest/getXx","D");
	$("#txt_rybh").bindChange("${pageContext.request.contextPath}/suggest/getXx","R");
	//列表数据
    var columns = [
       {"data": "RYBH",orderable:false, 'render': function (data, type, full, meta){
            return '<input type="checkbox" name="id" class="keyId" value="' + data + '" rygh = "'+full.RYGH+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,"width":41,"searchable":false,"class":"text-center"},
       {"data": "RYGH",defaultContent:""},
       {"data": "XM",'render':function(data,type,full,meta){
    	   return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.RYBH==undefined ? '':full.RYBH)+'" path="${pageContext.request.contextPath}">'+ (data==undefined? '': data) +'</a>';
       }},
       {"data": "XB",defaultContent:""},
       {"data": "WHCD",defaultContent:""},
       {"data": "DRRQ",defaultContent:""},
       {"data": "ZYZC",defaultContent:""},
//        {"data": "DWBH",defaultContent:""},
       {"data": "DWBH","render":function (data, type, full, meta){
	    	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.DW==undefined? '': full.DW)+'" path="${pageContext.request.contextPath}">'+ (data==undefined ? '':data) +'</a>';
		}},
       {"data": "RYZT",defaultContent:""}
     ];
    table = getDataTableByListHj("mydatatables","${pageContext.request.contextPath}/select/getPublicList_ry?jdbh=${jdbh}",[2,'asc'],columns,0,1,setGroup);
    //$("#btn_search").click();
//     //查看按钮
//     $(document).on("click",".btn_look",function(){
//    		var rybh = $(this).attr("name");
//    		select_commonWin("${pageContext.request.contextPath}/ryb/goRybLook?rybh="+rybh,"人员信息","920","630");
//    	});
	//弹窗
	$("#btn_dwbh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/dwxx/window.jsp?controlId=txt_dwbh","单位信息","920","630");
    });
	$("#btn_rybh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/window/ryxx/window.jsp?controlId=txt_rybh","人员信息","920","630");
    });
	//导出Excel
   	$("#btn_exp").click(function(){
		var json = searchJson("searchBox");
		var checkbox = $("#mydatatables").find("[name='id']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
		doExp(json,"${pageContext.request.contextPath}/select/doExp_ry?jdbh=${jdbh}","人员信息","${pageContext.request.contextPath}",id.join(","));
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