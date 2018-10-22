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
    	<form id="myform" class="form-inline" role="form" action="" style="padding-top: 8px;padding-bottom: 2px">
    		<div class="search-simple">
				<div class="form-group">
					<label>单&ensp;位&ensp;号</label>
					<input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" table="K" placeholder="请输入单位号">
				</div>
				<div class="form-group">
					<label>单位名称</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="mc" table="K" placeholder="请输入单位名称">
				</div>
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查 询</button>
				<div id="btn_search_more">
					<span id="btn_search_gjcx"><i class="fa icon-btn-gjcx"></i>高级查询</span>
					<div class="search-more">
<!-- 						<div class="form-group"> -->
<!-- 							<label>单&ensp;位&ensp;号</label> -->
<!-- 							<div class="input-group"> -->
<!-- 								<input type="text" id="txt_dwbh" class="form-control input-radius" name="dwbh" value=""  table="K" placeholder="请输入单位号"> -->
<!-- 							</div> -->
<!-- 						</div> -->
						<div class="form-group">
							<label>部门负责人</label>
							<div class="input-group">
								<input type="text" id="txt_dwld" class="form-control input-radius" name="dwld" value="" types="R" table="K" placeholder="请选择部门负责人">
								<span class="input-group-btn"><button type="button" id="btn_dwld" class="btn btn-link ">选择</button></span>
							</div>
						</div>
						<div class="form-group">
							<label>单位名称</label>
							<input type="text" id="txt_mc" class="form-control input-radius" table="K" name="mc" placeholder="请输入单位名称">
						</div>
						<div class="form-group">
							<label>单位地址</label>
							<input type="text" id="txt_dz" class="form-control input-radius" table="K" name="dz" placeholder="请输入单位地址">
						</div>
						<div class="form-group">
							<label>单位类别</label>
							<select id="drp_dwxz" class="form-control select2" style="width:152px;" name="dwxz" table="K" types="E">
               					<option value="">未选择</option>
	                            <c:forEach var="dwxzlist" items="${dwxzlist}"> 
	                                <option value="${dwxzlist.DM}" <c:if test="${dwb.DWXZ == dwxzlist.DM}">selected</c:if>>${dwxzlist.MC}</option>
								</c:forEach>
	                		</select>
						</div>
						<div class="form-group">
							<label>单位有效标识</label>
							<select id="drp_dwzt" class="form-control select2" style="width:152px;" name="dwzt" table="K">
	             		 		<option value="">未选择</option>
	                       		<option value="1">正常</option>
	                       		<option value="0">禁用</option>
	                   		</select>
						</div>
						<div class="form-group">
							<label>建立日期</label>
							<div class="input-group">
								<input type="text" id="txt_jlrq_begin" class="form-control date" name="jlrq" types="TL" table="K" data-format="yyyy-MM-dd" placeholder="开始日期">
								<span class='input-group-addon'>
							    	<i class="glyphicon glyphicon-calendar"></i>
							   	</span>
						   	</div>
							<label>-</label>
							<div class="input-group">
								<input type="text" id="txt_jlrq_end" class="form-control date" name="jlrq" types="TH" table="K" data-format="yyyy-MM-dd" placeholder="截止日期">
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
									取 消
								</button>
							</div>
						</div>
					</div>
				</div>
				<div class="btn-group pull-right" role="group">
	                <button type="button" class="btn btn-default" id="btn_add">增加</button>
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
				        <th>单位号</th>
				        <th>单位名称</th>
				        <th>单位简称</th>
				        <th>单位地址</th>
				        <th>单位类别</th>
				        <th>建立日期</th>
				        <th>部门负责人</th>
				        <th>隶属单位</th>
				        <th>单位办别</th>
				        <th>是否学院</th>
				        <th>单位有效标识</th>
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
</body>
<%@include file="/static/include/public-list-js.inc"%>
<script src="${pageContext.request.contextPath}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>
<script>
$(function () {
   	//新增
   	$(document).on("click","#btn_add",function(){
   		doOperate("${ctx}/dwb/goEditPage?dwbh=${param.dwbh}","C");//operateType=C
   	});
   	//修改
   	$(document).on("click",".btn_upd",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/dwb/goEditPage?dwbh="+dwbh,"U");
	});
    //批量赋值
    $(document).on("click","#btn_assig",function(){
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
   		doDel("dwbh="+dwbh,"${ctx}/dwb/doDelete",function(val){
   			parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
   		},function(val){
   		},"1");
   	});
	//批量删除
	$(document).on("click","#btn_del",function(){
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
   	   			doDel("dwbh="+dwbh.join(","),"${ctx}/dwb/doDelete",function(val){
   	   				parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
   	   	   		},function(val){
   	   	   		},dwbh.length);
   	   		}else{
   	   			alert("请选择至少一条信息删除！");
   	   		}
   		}
   		
   	});
	//查看按钮
	$(document).on("click","#btn_look",function(){
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length==1){
   			window.location.href = "${ctx}/dwb/goEditPage?operateType=L&dwbh="+checkbox.val();
   		}else{
   			alert("只能选择一条信息查看！");
   		}
   	});
   	//导出Excel
//    	$("#btn_exp").click(function(){
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/dwb/expExcel?treedwbh=${dwbh}","单位信息","${pageContext.request.contextPath}",id.join(","));
//    	});
   	
  //导出Excel
  $(document).on("click","#btn_exp",function(){   		
   		var id = [];
   		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
   		if(checkbox.length > 0){
			checkbox.each(function(){
				id.push($(this).val());
			});
			id = id.join("','");
   		}else{
   			id = "";
   		}
   		doExp("","${ctx}/dwb/expExcel2","单位信息","${pageContext.request.contextPath}",id);
   		
// 				var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 				if (checkbox.length > 0) {
// 					var guid = [];
// 					checkbox.each(function() {guid.push($(this).val());});
// 					$.ajax({
// 						type : "post",
// 						data : {guid:guid.join(",")},
// 						url : "${ctx}/dwb/expExcel2",
// 						success : function(val) {
// 						   FileDownload("${ctx}/file/fileDownload","单位信息.xls",val.url);
// 						}
// 					});
// 				} else {
// 					alert("请至少选择一条信息导出!");
// 				}
			});

   	var columns = [
		{"data": "DWBH",orderable:false, "render": function (data, type, full, meta){
			if(data=='000001'){
				return '<input type="checkbox" class="keyId djdw" name="keyId" value="' + data + '">';
			}else{
				return '<input type="checkbox" class="keyId" name="keyId" value="' + data + '">';
			}
	    },"width":10,'searchable': false},//0
	    
		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	   		return data;
		},"width":41,"searchable": false,"class":"text-center"},//1
	   	{"data": "BMH",defaultContent:""},//2
	   	{"data": "MC",defaultContent:""},
	   	{"data": "JC",defaultContent:""},
	   	{"data": "DZ",defaultContent:""},
	   	{"data": "DWXZ",defaultContent:""},
	   	{"data": "JLRQ",defaultContent:"","class":"text-center"},
	   	{"data": "DWLD",'render':function (data, type, full, meta){
		   	return '<a href="javascript:void(0);" class="btn btn-link rylook" rybh="'+(full.DWLDH==undefined?'':full.DWLDH)+'" path="${ctx}">'+ (full.DWLD==undefined?'':full.DWLD) +'</a>';
		}},
	   	{"data": "SJDW",'render':function (data, type, full, meta){
		   	return full.SJDW==undefined?'':full.SJDW;
		}},
		{"data": "DWBB",defaultContent:""},
		{"data": "SFXY",defaultContent:"","class":"text-center"},
	   	{"data": "DWZT",defaultContent:"","class":"text-center"},
	   	{"data": "PXXH",defaultContent:""},//2
	   	{"data": "DWBH",'render':function (data, type, full, meta){
// 	   		if(data=='000001'){
// 	   			return '<a  class="btn btn-link btn_upd" dwbh = "'+full.DWBH+'">编辑</a>';
// 	   		}else{
	   			return '<a  class="btn btn-link btn_upd" dwbh = "'+full.DWBH+'">编辑</a>|<a  class="btn btn-link btn_delxx" dwbh = "'+full.DWBH+'">删除</a>';
// 	   		}
		},orderable:false,"width":95}
	];
	//表数据
   	table = getDataTableByListHj("mydatatables","${ctx}/dwb/getPageList?treedwbh=${dwbh}",[13,'asc'],columns,0,1,setGroup);
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