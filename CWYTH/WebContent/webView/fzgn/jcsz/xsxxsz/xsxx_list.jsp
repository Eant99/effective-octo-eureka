<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>学生信息维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			<div class="col-md-12 tabs" style="padding-right: 0px">
				
			</div>
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>学&emsp;&emsp;号</label>
					<input type="text" id="txt_xh" class="form-control" name="xh" table="T" placeholder="请输入学生学号">
				</div>
				<div class="form-group">
					<label>姓&emsp;&emsp;名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="T" placeholder="请输入学生姓名">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
<!-- 				<div id="btn_search_more"> -->
<!-- 					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span> -->
<!-- 					<div class="search-more"> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>所在院系</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" value="" types="D" table="A" placeholder="请选择所在院系"> -->
<!-- 		                    	<span class="input-group-btn"><button type="button" id="btn_dwbh" class="btn btn-link">选择</button></span> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 						<div class="form-group"> -->
<!-- 							<label>性&emsp;&emsp;别</label> -->
<!-- 							<select id="txt_xb" class="form-control" style="width:150px;" name="xb" table="A" types="E"> -->
<!-- 			                	<option value="">未选择</option> -->
<%-- 			                	<c:forEach var="sexList" items="${sexList}"> --%>
<%-- 				               		<option value="${sexList.DM}" <c:if test="${ryb.XB == sexList.DM}">selected</c:if>>${sexList.MC}</option> --%>
<%-- 				            	</c:forEach> --%>
<!-- 			                </select> -->
<!-- 						</div> -->
<!-- 						<div class="search-more-bottom clearfix"> -->
<!-- 							<div class="pull-right"> -->
<!-- 								<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i> -->
<!-- 									查 询 -->
<!-- 								</button> -->
<!-- 								<button type="button" class="btn btn-default" id="btn_cancel"> -->
<!-- 									取 消 -->
<!-- 								</button> -->
<!-- 							</div> -->
<!-- 						</div> -->
<!-- 					</div> -->
<!-- 				</div> -->
				<div class="btn-group pull-right" role="group">
	               <button type="button" class="btn btn-default" id="btn_add">增加</button>
	               <button type="button" class="btn btn-default" id="btn_del">批量删除</button>
	               <!-- <button type="button" class="btn btn-default" id="">导入Excel</button> -->
	               <button type="button" class="btn btn-default" id="btn_exp">导出Excel</button>
	               <button type="button" class="btn btn-default" id="btn_imp">批量导入</button>
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
				            <th>性别</th>
				            <th>出生日期</th>
				            <th>学生类别</th>
				            <th>所在院系</th>
				            <th>专业</th>
				            <th>年级</th>
				            <th>班级</th>
				            <th>民族</th>
				            <th>身份证件类型</th>
				            <th>证件号</th>
				            <th>政治面貌</th>
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
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
	//联想输入提示
	$("#txt_dwbh").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" xh="'+full.XH+'" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},
       {"data": "XH",defaultContent:"","class":"text-left"},
       {"data": "XM",defaultContent:""},
       {"data": "XB",defaultContent:"","class":"text-left"},
       {"data": "CSRQ",defaultContent:"","class":"text-center"},
       {"data": "XSLB",defaultContent:""},
       {"data": "SZYX",defaultContent:"","width":120},
       {"data": "SZZY",defaultContent:"","width":120,},
       {"data": "SZNJ",defaultContent:"","class":"text-left"},
       {"data": "SZBJ",defaultContent:"","width":120,},
       {"data": "MZ",defaultContent:""},
       {"data": "SFZJLX",defaultContent:""},
       {"data": "SFZH",defaultContent:"","class":"text-left"},
       {"data": "ZZMM",defaultContent:""},
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" xh="'+full.XH+'" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" xh="'+full.XH+'" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":220,"class":"text-center","width":100}
     ];
    table = getDataTableByListHj("mydatatables","${ctx}/xsxx/getPageList?treedwbh=${dwbh}",[2,'asc'],columns,0,1,setGroup);
	
  	//添加按钮
   	$("#btn_add").click(function(){	   	
		doOperate("${ctx}/xsxx/goXsxxPage","C");
   	});
	//导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/xsxx/expExcel?treedwbh=${dwbh}","学生信息","${ctx}",guid.join(","));
//    	});
	
    //导出Excel
   	$("#btn_exp").click(function() {
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
   		doExp("","${ctx}/xsxx/expExcel2","学生信息","${pageContext.request.contextPath}",id);
   		
   		
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/xsxx/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","学生信息.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});
    //导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/xsxx/expExcelZhtj?treedwbh=${dwbh}&guid="+id,"登录信息","${pageContext.request.contextPath}",id.join(","));
//    	});
	

   	//修改按钮
   	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");
	   	//window.location.href = "${ctx}/xsxx/goXsxxEditPage?guid="+guid;
   		doOperate("${ctx}/xsxx/goXsxxPage?guid="+guid,"U");
   	});
  
    //批量删除按钮
   	$("#btn_del").click(function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   			confirm("确定删除"+checkbox.length+"条信息？","",function(){
   				var xh = [];
   	   	   		checkbox.each(function(){
   	   	   			xh.push($(this).attr("xh"));
   	   	   		});
   	   			$.ajax({
   	   	   			url:"${ctx}/xsxx/doDelete",
   	   	   			data:"xh="+xh.join(","),
   	   	   			type:"post",
   	   	   			async:"true",
   	   	   			success:function(data){
   	   	   				var result = JSON.getJson(data);
   	   	   				if(result.success){
   							alert("删除成功！");  	   					
	   	   	   				table.ajax.reload();
   	   	   				}
   	   	   			}
   	   	   		});
   	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
 		var xh = $(this).attr("xh");
 		console.log("''''''"+xh);
		doDel("xh="+xh,"${ctx}/xsxx/doDelete",function(val){
			table.ajax.reload();
		},function(val){
			
		},"1");
		
	});
	//数据导入
	$("#btn_imp").click(function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/xsxxsz/txl_imp.jsp", "导入学生信息", 450,350);
		return false;
	});
	//弹窗
	$("#btn_dwbh").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_dwbh","单位信息","920","630");
    });
	$("#btn_rybh").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_rybh","人员信息","920","630");
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
</body>
</html>