<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>成本核算模型</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 12px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh" table="k"  placeholder="请输入编号">
				</div>
				<div class="form-group">
					<label>名称</label>
					<input type="text" id="txt_xmmc" class="form-control" name="xmmc" table="k"   placeholder="请输入名称">
				</div>
				<div class="form-group">
					<label>成本中心</label>
					<select class="form-control" id="txt_zfzt" name="zfzt">
						<option value="">--请选择--</option>
						<option value="01" <c:if test="${'01' eq param.zfzt }">selected</c:if>>行政成本中心</option>
						<option value="02" <c:if test="${'02' eq param.zfzt }">selected</c:if>>教学成本中心</option>					
					</select>
				</div>
				<div class="form-group">
					<label>成本对象</label>
					<select class="form-control" id="txt_zfzt" name="zfzt">
						<option value="">--请选择--</option>
						<option value="01" <c:if test="${'01' eq param.zfzt }">selected</c:if>>行政成本中心</option>
						<option value="02" <c:if test="${'02' eq param.zfzt }">selected</c:if>>教学成本中心</option>					
					</select>
				</div>
				<div class="form-group">
					<label>成本要素</label>
					<select class="form-control" id="txt_zfzt" name="zfzt">
						<option value="">--请选择--</option>
						<option value="01" <c:if test="${'01' eq param.zfzt }">selected</c:if>>行政成本中心</option>
						<option value="02" <c:if test="${'02' eq param.zfzt }">selected</c:if>>教学成本中心</option>					
					</select>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<c:if test="${empty isWindow}">
					<div class="btn-group pull-right" role="group">
		               <button type="button" class="btn btn-default" id="btn-add">增加</button>
		               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
		               <button type="button" class="btn btn-default" id="btn_export">导出Excel</button>
					</div>
				</c:if>
			</div>
		</form>
	</div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'> 
				<table id="mydatatables" class="table table-striped table-bordered" >
		        	<thead>
				        <tr style="background-color: #f3f3f3">
				            <th style="text-align:center"><input type="checkbox" class="select-all"/></th>
				            <th style="text-align:center">序号</th>
				            <th style="text-align:center">成本核算模型编号</th>
				            <th style="text-align:center">成本核算模型名称</th>
				            <th style="text-align:center">成本中心</th>
				            <th style="text-align:center">成本对象</th>
				            <th style="text-align:center">成本要素</th>
				            <th style="text-align:center">操作</th>
				        </tr>
					</thead>
				    <tbody>
				     <tr>
				            <td style="text-align:center"><input type="checkbox" class="select-all"/></td>
				            <td>1</td>
				            <td>1</td>
				            <td>测试模型</td>
				            <td>行政中心</td>
				            <td>测试</td>
				            <td>测试数据</td>
				            <td style="text-align:center"><div><a href="javascript:void(0);" class="btn btn-link btn_upd">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxxl">删除</a></div></td>
				        </tr>
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
  	$(document).on("click","#btn-add",function(){
	   	select_commonWin("${ctx}/cbgl/cbhsmx/getCbhsmxxx?operateType=C","成本核算模型信息", "400", "450");
   	});
  	 //双击事件
    $(document).on("dblclick","#mydatatables tr:not(#header)",function(){
    	select_commonWin("${ctx}/cbgl/cbhsmx/getCbhsmxxx?operateType=C","成本核算模型信息", "400", "450");
    });
   	$(document).on("click",".btn-del",function(){
   		alert("该项目正在被使用，暂时不允许删除");
   	});
  //导出Excel
  	$(document).on("click","#btn_export",function(){
  		alert("暂时未实现该功能！");
			});
   	//编辑
   	$(document).on("click",".btn_upd",function(){
   		select_commonWin("${ctx}/cbgl/cbhsmx/getCbhsmxxx?operateType=C","成本核算模型信息", "400", "450");
   	});
  //单条删除
	$(document).on("click",".btn_delxxl",function() {
		alert("测试数据暂时不能删除！");
	});
   	})
</script>
</body>
</html>