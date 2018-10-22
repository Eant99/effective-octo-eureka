<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生补助金统计</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<input type="hidden" name="qc" value="${qc}">
	<input type="hidden" name="bmh" value="${bmh}">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
    			<div class="form-group">
					<label>学号</label>
					<input type="text" id="txt_xh" class="form-control input-radius" name="xh" table="K" placeholder="请输入学号">
				</div>
				<div class="form-group">
					<label>所在院系</label>
					<input type="text" id="txt_szyx" class="form-control input-radius" name="szyx" table="K" placeholder="请输入所在院系">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div class="btn-group pull-right" role="group">
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
				        <th><input type="checkbox" class="select-all"/></th>
				        <th>序号</th>
				        <th>学号</th>
				        <th>姓名</th>
				        <th>所在院系</th>
				        <th>专业</th>
				        <th>发放总金额（元）</th>
				        <th>发放次数</th>
				    </tr>
				    </thead>
				    <tbody>
				    </tbody>
				</table>
            </div>
		</div>
	</div>
</div>
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
				{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
						return '<input type="checkbox" class="keyId" name="keyId" guid="'+full.GUID+'" xh="'+full.XH+'" value="' + data + '">';
		   	    },"width":10,'searchable': false},//0
		   		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
		   	   		return data;
		   		},"width":41,"searchable": false,"class":"text-left"},//1
		   		{"data": "XH",defaultContent:"","class":"text-left"},
		   		{"data": "XM",defaultContent:"","class":"text-left" },
		   		{"data": "SZYX",defaultContent:"","class":"text-left"},
		   	   	{"data": "SZZY",defaultContent:"","class":"text-left"},
		   	 	{"data": "FFJE","render":function (data, type, full, meta){
				   	return '<div class="text-right"><a href="javascript:void(0);" class="btn btn_look btn-link" guid = "'+full.GUID+'" xh = "'+full.XH+'">'+ data +'</a></div>';
			    },"width":200},
			    {"data": "BZCS",defaultContent:"","class":"text-center"},
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/xsbzjtj/getPageList?treedwbh=${dwbh}",[2,'asc'],columns);
	//查看补助详情按钮
   $(document).on("click",".btn_look",function(){
	   var xh = $(this).attr("xh");
	   select_commonWin("${ctx}/webView/bzjgl/xsbzjtj/xsbzjtj.jsp?xh="+xh,"学生补助金统计","950","500");
   	});
  	//数据导入
	$("#btn_imp").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp3.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//导出Excel
   	$("#btn_exp").click(function(){
   		var xhList = [];
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				xhList.push($(this).attr("xh"));
			});
			xhList = xhList.join("','");
   		}else{
   			xhList = "";
   		}
   		doExp(json,"${ctx}/xsbzjtj/expExcel?xhList="+xhList,"学生补助金统计","${pageContext.request.contextPath}",xhList);
   	});
    //查询联想输入
	$("#txt_dwld").bindChange("${ctx}/suggest/getXx","R");
	//查询弹窗
   	$("#btn_dwld").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_dwld","人员信息","920","630");
    });
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
</script>
</html>

