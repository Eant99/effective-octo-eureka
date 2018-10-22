<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
	<meta charset=UTF-8/>
	<meta name="viewport" content="width=device-width, initial-scale=1.0" />
	<title>离职人员资产移交</title>
	<%@include file="/static/include/public-list-css.inc"%> 
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>离职人员</label>
					<div class="input-group">
						<input type="text" id="txt_syr" class="form-control input-radius" name="rybh" value="" types="R" table="R" placeholder="请选择离职人员">
						<span class="input-group-btn"><button type="button" id="btn_syr" class="btn btn-link">选择</button></span>
					</div>
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
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
					        <th>人员工号</th>
					        <th>人员姓名</th>
					        <th>所在单位</th>
					        <th>联系电话</th>
					        <th>资产总数</th>
					        <th>资产总值</th>
					        <th>操作</th>
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
	$(function () {
	   	//联想输入提示
	   	$("#txt_syr").bindChange("${ctx}/suggest/getXx","R");
		//查询弹窗
	   	$("#btn_syr").click(function(){
			select_commonWin("${ctx}/window/rypage?controlId=txt_syr","人员信息","920","630");
	    });
		//列表数据
	   	var columns = [
			{"data": "RYBH",orderable:false, "render": function (data, type, full, meta){
				return '<input type="checkbox" class="keyId" name="rybh" value="' + data + '">';
		    },"width":10,'searchable': false},
			{"data":"_XH",orderable:false,"searchable": false,"class":"text-center"},
		   	{"data": "RYGH",defaultContent:""},
		   	{"data": "XM",'render':function (data, type, full, meta){
			   	return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.RYBH==undefined?'':full.RYBH)+'" path="${ctx}">'+ (full.XM==undefined?'':full.XM) +'</a>';
			}},
		   	{"data": "DWBH",'render':function (data, type, full, meta){
			   	return '<a href="javascript:void(0);" class="btn btn-link dwlook" dwbh="'+(full.DWBH==undefined?'':full.DWBH)+'" path="${ctx}">'+ (full.SZDWMC==undefined?'':full.SZDWMC) +'</a>';
			}},
		   	{"data": "LXDH",defaultContent:""},
		   	{"data": "ZCZS",defaultContent:"","class":"text-right",orderable:false},
		   	{"data": "ZZJ",defaultContent:"","class":"text-right",orderable:false},
		   	{"data": "RYBH",'render':function (data, type, full, meta){
		   		return '<a class="btn btn-link btn_part" rybh="' + full.RYBH + '">部分资产移交</a>|<a class="btn btn-link btn_all" rybh="' + full.RYBH + '">全部资产移交</a>';
			},orderable:false,"width":160}
		];
	   	table = getDataTableByListHj("mydatatables","${ctx}/lzry/getPageList",[2,'asc'],columns,0,1);
			
	   	//部分资产移交
	   	$(document).on("click",".btn_part",function(){
	   		var rybh = $(this).attr("rybh");
	   		select_commonWin("${ctx}/lzry/goZcPage?flag=part&rybh="+rybh,"部分资产移交","1000","600");
	   	}); 
		//全部资产移交
		$(document).on("click",".btn_all",function(){
			var rybh = $(this).attr("rybh");
	   		select_commonWin("${ctx}/lzry/goZcPage?flag=all&rybh="+rybh,"选择接收人","370","140");
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