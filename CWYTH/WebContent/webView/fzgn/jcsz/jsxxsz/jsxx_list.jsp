<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>教师信息维护</title>
<%@include file="/static/include/public-list-css.inc"%>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<div class="row rybxx" style="margin-left:-15px">
			
		</div>
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
				<div class="form-group">
					<label>工&emsp;&emsp;号</label>
					<input type="text" id="txt_xh" class="form-control" name="xh" table="A" placeholder="请输入教师工号">
				</div>
				<div class="form-group">
					<label>姓&emsp;&emsp;名</label>
					<input type="text" id="txt_xm" class="form-control" name="xm"  table="A" placeholder="请输入教师姓名">
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
	               <button type="button" class="btn btn-default" id="btn_imp">批量导入</button>
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
				            <th>工号</th>
				            <th>姓名</th>
				            <th>性别</th>				            
				            <th>所在单位</th>				            
				            <th>职务</th>
				            <th>职称</th>
				            <th>文化程度</th>
				            <th>来校时间</th>
				            <th>出生日期</th>
				            <th>民族</th>
				            <th>身份证件类型</th>
				            <th>身份证件号</th>
				            <th>婚姻状况</th>
				            <th>政治面貌</th>
				            <th>联系方式</th>				            
				            <th>邮箱</th>
				            <th>在职类型</th>
				            <th>在职人员来源</th>
				            <th>参加工作时间</th>
				            <th>工龄（年）</th>
				            <th>排序序号</th>
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
         return '<input type="checkbox" name="guid" class="keyId" value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data":"_XH",orderable:false,'render':function(data,type,full,meta){
          	return data;},"width":41,"searchable":false,"class":"text-center"},  
       {"data": "XH",defaultContent:""},
       {"data": "XM",defaultContent:"","class":"text-left"},
       {"data": "XBM",defaultContent:"","class":"text-left"},       
       {"data": "SZDW",defaultContent:""},
       {"data": "ZW",defaultContent:""},
       {"data": "ZC",defaultContent:""},
       {"data": "WHCD",defaultContent:""},
       {"data": "LXSJ",defaultContent:"","class":"text-left"},
       {"data": "CSRQ",defaultContent:"","class":"text-left"},
       {"data": "MZM",defaultContent:""},       
       {"data": "SFZJLXM",defaultContent:""},
       {"data": "SFZH",defaultContent:"","class":"text-left"},
       {"data": "HYZKM",defaultContent:"","class":"text-left"},
       {"data": "ZZMMM",defaultContent:""},
       {"data": "LXFS",defaultContent:""},
       {"data": "YX",defaultContent:""},
       {"data": "ZZLX",defaultContent:""},
       {"data": "ZZRYLY",defaultContent:""},                                                       
       {"data": "CJGZSJ",defaultContent:"","class":"text-left"},
       {"data": "GL",defaultContent:""},   
       {"data": "PXXH",defaultContent:""},   	
       {"data": "GUID",'render':function(data, type, full, meta){
	   		return '<a href="javascript:void(0);" class="btn btn-link btn_upd" guid = "'+full.GUID+'">编辑</a>|<a href="javascript:void(0);" class="btn btn-link btn_delxx" guid = "'+full.GUID+'">删除</a>';
      },orderable:false,"width":220}
     ];
    	table = getDataTableByListHj("mydatatables","${ctx}/jsxxs/getPageList?treedwbh=${param.dwbh}",[22,'asc'],columns,0,1,setGroup);

 	//添加按钮
 	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/jsxxs/goJsxxPage","C");
   	});
  	//数据导入
  	$(document).on("click","#btn_imp",function(){
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp.jsp", "导入教师信息", 450,350);
		return false;
	});
	//导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
// 		var guid = [];
// 		checkbox.each(function(){
// 			guid.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/jsxxs/expExcel?treedwbh=${dwbh}","教师信息","${ctx}",guid.join(","));
//    	});
	
    //导出Excel
    $(document).on("click","#btn_exp",function(){
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
   		doExp("","${ctx}/jsxxs/expExcel2?treedwbh=${param.dwbh}","教师信息","${pageContext.request.contextPath}",id);
   	});
	
	
	
 //编辑
   	 	$(document).on("click",".btn_upd",function(){
   		var guid = $(this).attr("guid");

   		doOperate("${ctx}/jsxxs/goJsxxPage?guid="+guid,"U");

   	});
  
    //批量删除按钮
    $(document).on("click","#btn_del",function(){
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
   		if(checkbox.length>0){
   	   		var guid = [];
   	   		checkbox.each(function(){
   	   			guid.push($(this).val());
   	   		});
	   	   	confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/jsxxs/doDelete",
	   	   			data:"guid="+guid.join("','"),
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("请选择至少一条信息删除!");
   		}
   	});
 
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var guid = $(this).attr("guid");
   		confirm("确定删除？","",function(){
   			$.ajax({
   	   			url:"${ctx}/jsxxs/doDelete",
   	   			data:"guid="+guid,
   	   			type:"post",
   	   			async:"false",
   	   			success:function(val){
   	   				alert("删除成功");
   	   				table.ajax.reload();
   	   			}
   	   		});
   		});
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