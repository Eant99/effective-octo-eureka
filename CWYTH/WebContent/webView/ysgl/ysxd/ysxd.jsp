<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>预算下达</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>项目编号</label>
					<input type="text" id="txt_xmbh" class="form-control" name="xmbh"  placeholder="请输入项目编号">
				</div>
				<div class="form-group">
					<label>项目名称</label>
					<input type="text" id="txt_xmmc"  class="form-control" name="xmmc"   placeholder="请输入项目名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>单位名称</label>
							<div class="input-group">
								<input type="text" id="txt_dwbh" class="form-control" name="dwbh"  placeholder="请输入单位名称">
							</div>
						</div>
						<div class="form-group">
							<label>项目名称</label>
							<div class="input-group">
								<input type="text" id="txt_xmbh" class="form-control" name="xmbh"  placeholder="请输入项目名称">
							</div>
						</div>
						<div class="form-group">
							<label>预算年度</label>
							<div class="input-group">
								<input type="text" id="txt_ysnd" onkeyup="value=value.replace(/[^0-9]/g,'');" class="form-control" name="ysnd"  placeholder="请输入预算年度">
							</div>
						</div>
						<div class="form-group" style="width: 300px">
							<label>预算总额</label>
							<div class="input-group">
								<input type="text" id="txt_ysze" onkeyup="value=value.replace(/[^0-9|\.]/g,'');" class="form-control number1" name="ysze"  placeholder="请输入预算总额" />
							</div>
							（万元）
						</div>
						<div class="form-group" style="width: 300px"> 
							<label>下达金额</label>
							<div class="input-group">
								<input type="text" id="txt_xdje" onkeyup="value=value.replace(/[^0-9|\.]/g,'');" class="form-control number1" name="xdje"  placeholder="请输入下达金额">
							</div>
							（万元）
						</div>
						<div class="form-group">
							<label>资金来源</label>
							<div  class="input-group">
								<input type="text" id="txt_zjly" class="form-control" name="zjly"  placeholder="请输入资金来源"/>
							</div>
						</div>
						<div class="search-more-bottom clearfix">
							<div class="pull-right">
								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>
									查 询
								</button>
								<button type="button" class="btn btn-default" id="btn_cancel">
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_bc">保存</button>
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
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
				        	<th>序号</th>
				            <th>单位名称</th>
				            <th>项目名称</th>
				            <th>项目类型</th>
				            <th>项目名称</th>
				            <th>预算年度</th>    
				            <th>预算总额（万元）</th>
				            <th>下达金额（万元）</th>
				            <th>资金来源</th>
				            <th>复核状态</th>
				            <th>操作</th>
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
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
        {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
           	return data;},"width":41,"searchable":false,"class":"text-center"},
        {"data": "DWBH",defaultContent:""},       
        {"data": "XMBH",defaultContent:""},
        {"data": "XMFL",defaultContent:"一级类"},
        {"data": "XMLX",defaultContent:"出差类"},
       /*  {"data": "YSND",defaultContent:"","class":"text-center"}, */  
        {"data": "YSND",'render':function(data, type, full, meta){
 	   		return '<input type="text"  class="form-control year" name="xdje"  style="border:none;text-align: right;width:100%;"  >';
     	 },orderable:false,"width": 200},
        {"data": "YSJE",defaultContent:"","class":"text-right"},   
        {"data": "XDJE",'render':function(data, type, full, meta){
 	   		return '<input type="text"  class="xdje number1" name="xdje" onkeyup="value=value.replace(/[^0-9|\.]/g,\'\');" style="border:none;text-align: right;width:100%;" placeholder="请输入下达金额"  value="">';
      	 },orderable:false,"width": 200},
       /*  {"data": "ZJLY",defaultContent:""}, */ 
         {"data": "ZJLY",'render':function(data, type, full, meta){
 	   		return '<select  class="form-control input-radius" ><option>税收</option><option>捐助</option><option>彩票</option> </select>';
     	 },orderable:false,"width": 200},
        {"data": "FHZT",defaultContent:"未复核","class":"text-center"},
        {"data": "CZ",'render':function(data, type, full, meta){
 	   		return '<a href="javascript:void(0);" class="fuhe">复核</a>';
       },orderable:false,"width":100,"class":"text-center"}
      ];
     table = getDataTableByListHj("mydatatables","${ctx}/ysxd/getYsxdPageData?",[2,'asc'],columns,0,1,setGroup);
	
  //导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","教师信息","${ctx}",guid.join(","));
   	});
  
	//查询
	$("#btn_search").click(function(){
		window.location.reload();
	});
	
	//复核
 	$(".fuhe").click(function(){
 		var a = $(this);
 		confirm("确定复核？","{title:提示信息}",function(){
 			alert("复核成功");
 			a.parent().prev().text("已复核");
 		});
 	});
 	//保存按钮
	$("#btn_bc").click(function(){
		alert("保存成功！");
	});
	//下达金额输入值验证
	$(".xdje").blur(function(){
		var ysje = $(this).parent().prev().text();
		var xdje = $(this).val();
		ysje = parseInt(ysje);
		if(xdje > ysje){
			alert("下达金额不能大于预算金额！");
			$(this).val("");
		}
	});
	$(function() {	
	//列表右侧悬浮按钮
		$(window).resize(function(){
    		$("div.dataTables_wrapper").width($("#searchBox").width());
    		heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    		$(".dataTables_scrollBody").height(heights);
    		table.draw();
		});
	});
	});
</script>
</body>
</html>