<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
   	<input type="hidden" name="zl" id="zl" value="${dwbh}" />
   	<input type="hidden" name="mkbh" id="zl" value="111001" />
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="" style="padding-top: 8px;padding-bottom: 2px;">
    		<div class="search-simple">
    			
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="djbh" table="K" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>报销人</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="bxrmc" table="K" placeholder="请输入报销人名称">
				</div/l        >
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<!-- <div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>预算金额（万元）</label>
							<input type="text" id="txt_mc" class="form-control input-radius" table="K" name="ysje" placeholder="请输入预算金额">
						</div>
						<div class="form-group">
							<label>剩余金额（万元）</label>
							<input type="text" id="txt_mc" class="form-control input-radius" table="K" name="syje" placeholder="请输入剩余金额">
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
				</div> -->
				<div class="btn-group pull-right" role="group">
<!-- 	                <button type="button" class="btn btn-default" id="btn_add">增加</button> -->
<!-- 	                <button type="button" class="btn btn-default" id="btn_imp">批量导入</button> -->
<!-- 	                <button type="button" class="btn btn-default" id="btn_del">批量删除</button> -->
	                <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
<!-- 	                <button type="button" class="btn btn-default" id="btn_printpl">批量打印</button> -->
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
				        
				        <th>审核状态</th>
				        <th>单据编号</th>
<!-- 				        <th>单据张数</th> -->
				        <th>报销人</th>
				        <th>所在部门</th>
				        <th>报销总金额（元）</th>
				        
				        <th>报销日期</th>
				        <th>类型</th>
				        
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(function () {
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       				return '<input type="checkbox" class="keyId" lx="'+full.LX+'" name="keyId" guid="' + full.GUID + '" value="' + data + '">';
	       	    },"width":10,'searchable': false},//0
	       	    
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		
	       		{"data": "SHZTMC",defaultContent:""},
	       		{"data": "DJBH",defaultContent:""},
// 	       	   	{"data": "FJZZS",defaultContent:"","class":"text-right"},
	       	 	{"data": "BXRMC",defaultContent:""},
	       	 	{"data": "SZBMMC",defaultContent:""},
	       	 	{"data": "BXZJE",defaultContent:"","class":"text-right"},
	       	 	{"data": "CZRQ",defaultContent:"","class":"text-center"},
	       	 	{"data": "LX",defaultContent:""},
	       	   	
	       	   	{"data": "GUID",'render':function (data, type, full, meta){
          			return '<a href="javascript:void(0);" class="btn btn-link btn_print" lx="'+full.LX+'" guid="' + full.GUID + '">打印</a>';
	       		},orderable:false,"width":95}
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/kylbx/getPageList?treedwbh=${dwbh}",[4,'asc'],columns);
   	//新增
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/grjfsz/goEditPage?dwbh=${param.dwbh}","C");//operateType=C
   	});
   	
   	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
   		var DJBH = $(this).attr("DJBH");
   		var zl = $("#zl").val();
   		if(zl=="3"){
			doOperate("${ctx}/kylbx/goEditPage?dwbh="+dwbh+"&djbh="+DJBH,"U");
   		}else if(zl=="2"){
   			doOperate("${ctx}/wsbx/ccbx/ywbl?dwbh="+dwbh+"&djbh="+DJBH,"U");
   		}else{
   			doOperate("${ctx}/wsbx/rcbx/ywbl?dwbh="+dwbh+"&djbh="+DJBH,"U");
   		}
	});
  	//打印
	$(document).on("click",".btn_print",function(){
//  		var guid = $(this).parents("tr").find("[name='guid']").val(); 
        var lx=  $(this).parents("tr").find("[name='keyId']").attr("lx");
        var guid = $(this).attr("guid");
        doOperate("${ctx}/kylbx/singlePrint?guid="+guid+"&lx="+lx);
       
 		
   		
	});
	//批量打印
   	$("#btn_printpl").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push("'"+$(this).val()+"'");
   	   			});
				doOperate("${ctx}/kylbx/demoPrint?length="+checkbox.length+"&wdbh="+dwbh);
   	   		}else{
   	   			alert("请选择至少一条信息打印！");
   	   		}
   		
   	});
   	//复核
  	$(document).on("click",".btn_fhxx",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/grjfsz/goFhPage?dwbh="+dwbh,"U");
		parent.location.reload();
	});
    //批量赋值
   	$("#btn_assig").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   		if(djdw.length>0){
   			alert("包含最顶级单位，不允许进行批量赋值，请单独修改！");
   		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   			});
   	   			confirm("确认要批量赋值"+dwbh.length+"条信息？",{title:"提示"},function(index){
   	   				select_commonWin("${ctx}/dwb/goPlfuzhiPage?dwbh="+dwbh.join(","),"批量赋值","400","450");
   	   				close(index);
   	   			}); 
   	   		}else{
   	   			alert("请选择至少一条信息赋值！");
   	   		}
   		}
   	});
 	//删除
	$(document).on("click",".btn_delxx",function(){
   		var dwbh = $(this).attr("dwbh");
   		doDel("dwbh="+dwbh,"${ctx}/grjfsz/doDelete",function(val){
//    			location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	
	//查看按钮
   	$("#btn_look").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length==1){
   			window.location.href = "${ctx}/dwb/goEditPage?operateType=L&dwbh="+checkbox.val();
   		}else{
   			alert("只能选择一条信息查看！");
   		}
   	});
  	//数据导入
	$("#btn_imp").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp3.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
// 		console.log("111111");
//    		doExp(json,"${ctx}/kylbx/expExcel?treedwbh=${dwbh}&id="+id.join("','"),"日常报销汇总","${pageContext.request.contextPath}",id.join("','"));
//    	});
   	
//    	$("#btn_exp").click(
// 			function() {
// 				var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/kylbx/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","日常报销汇总.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
// 			});

	//导出Excel
   	$("#btn_exp").click(function(){
   		var id = [];
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/kylbx/expExcel2?treedwbh=${dwbh}","日常报销汇总","${pageContext.request.contextPath}",id);
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

