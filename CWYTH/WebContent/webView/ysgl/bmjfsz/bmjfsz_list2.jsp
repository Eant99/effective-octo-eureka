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
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action="">
    		<div class="search-simple">
				<div class="form-group">
					<label>部门名称</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="mc" table="K" placeholder="请输入部门名称">
				</div>
				<div class="form-group">
					<label>年度</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="nd" table="K" placeholder="请输入年度">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
						<div class="form-group">
							<label>预算金额（万元）</label>
							<input type="text" id="txt_mc" class="form-control input-radius number1" table="K" name="ysje" placeholder="请输入预算金额">
						</div>
						<div class="form-group">
							<label>剩余金额（万元）</label>
							<input type="text" id="txt_mc" class="form-control input-radius number1" table="K" name="syje" placeholder="请输入剩余金额">
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
				</div>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
	                <button type="button" class="btn btn-default" id="btn_imp">批量导入</button>
	                <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
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
				        <th>部门名称</th>
				        <th>年度</th>
				        <th>预算金额（万元）</th>
				        <th>剩余金额（万元）</th>
				        <th>是否复核</th>
				        <th>备注</th>
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
       				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
	       	    },"width":10,'searchable': false},//0
	       	    
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		
	       	   	{"data": "MC",defaultContent:""},
	       	   	{"data": "ND",defaultContent:""},
	       	   	{"data": "YSJE",defaultContent:"",class:"text-right"},
	       	   	{"data": "SYJE",defaultContent:"",class:"text-right"},
	       	 	{"data": "SFFHMC",defaultContent:""},
	       	   	{"data": "BZ",defaultContent:""},
	       	   	{"data": "GUID",'render':function (data, type, full, meta){
          			return '<a class="btn btn-link btn_upd" dwbh = "'+full.GUID+'">编辑</a>|<a  class="btn btn-link btn_delxx" dwbh = "'+full.GUID+'">删除</a>|<a  class="btn btn-link btn_fhxx" dwbh = "'+full.GUID+'">复核</a>';
	       		},orderable:false,"width":95}
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/bmjfsz/getPageList",[4,'asc'],columns);
   	//新增
   	$("#btn_add").click(function(){
   		doOperate("${ctx}/bmjfsz/goEditPage?dwbh=${param.dwbh}","C");//operateType=C
   	});
   	
   	//编辑
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/bmjfsz/goEditPage?dwbh="+dwbh,"U");
	});
   	//复核
  	$(document).on("click",".btn_fhxx",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/bmjfsz/goFhPage?dwbh="+dwbh,"U");
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
   		doDel("dwbh="+dwbh,"${ctx}/bmjfsz/doDelete",function(val){
//    			parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
   			table.ajax.reload();
   		},function(val){
   		},"1");
   	});
	//批量删除
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		var djdw = $("#mydatatables").find(".djdw").filter(":checked");
   		if(djdw.length>0){
   			alert("包含最顶级单位，不允许删除！");
   		}else{
   			if(checkbox.length>0){
   	   			var dwbh = [];
   	   			checkbox.each(function(){
   	   				dwbh.push($(this).val());
   	   			});
   	   			doDel("dwbh="+dwbh.join("','"),"${ctx}/bmjfsz/doDelete",function(val){
//    	   				parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
   	   			table.ajax.reload();
   	   	   		},function(val){
   	   	   		},dwbh.length);
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
   		}
   		
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
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp2.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//导出Excel
   	$("#btn_exp").click(function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
		var id = [];
		checkbox.each(function(){
			id.push($(this).val());
		});
   		doExp(json,"${ctx}/bmjfsz/expExcel?treedwbh=${dwbh}&id="+id.join("','"),"单位信息","${pageContext.request.contextPath}",id.join(","));
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