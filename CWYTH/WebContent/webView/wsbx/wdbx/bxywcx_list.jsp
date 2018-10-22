<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
/*     	border-bottom-width: 0px; */
    	border-bottom:1px solid #ddd;
	}
	 table{
		border-collapse:collapse!important;
	}
	.search-simple .select2-container--default .select2-selection--single {
   		background-color: #fff;
   		border: 1px solid #ccc;
    	border-radius: 4px 4px 4px 4px;
	}
/* 	.dataTables_scrollHeadInner{ */
/* 		width:1020px ! important; */
/* 	} */
/* 	table.dataTable{ */
/* 		width:1020px ! important; */
/* 	} */
</style>
</head>
<body>
<div class="fullscreen">
   	<input type="hidden" name="zl" id="zl" value="${dwbh}" />
   	<input type="hidden" name="mkbh" id="zl" value="00" />
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action=""style="padding-top: 8px;padding-bottom: 2px">
    		    
   	        <div class="form-group" style="float:left">
   	       		<label>审核状态</label>
				<select  class="form-control input-radius" id="shzt"  table="K" >
					<option value="all" <c:if test="${shzt=='all'}">selected</c:if>>全部</option>
					<option value="00" <c:if test="${shzt=='00'}">selected</c:if>>未提交</option>
               		<option value="11" <c:if test="${shzt=='11'}">selected</c:if>>审核中</option>
               		<option value="99" <c:if test="${shzt=='99'}">selected</c:if>>已通过</option>
	    		</select>
		    </div>
		    
		    <div class="search-simple">
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="djbh" table="K" placeholder="请输入单据编号">
				</div>
				<div class="form-group">
					<label>报销人</label>
					<input type="text" id="txt_mc" class="form-control input-radius" name="bxrmc" table="K" placeholder="请输入报销人名称">
				</div>
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
				        <th>报销类型</th>
				        <th>报销人</th>
				        <th>所在部门</th>
				        
				        <th>报销日期</th>
				        <th>报销总金额（元）</th>
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
<%-- <script src="${ctx}/static/plugins/datatable/js/dataTables.fixedColumns.js"></script>  --%>

<script>
var shzt=$("[id='shzt']").val();
$(function () {
	var columns = [
	       		{"data": "GUID",orderable:false, "render": function (data, type, full, meta){
       				return '<input type="checkbox" class="keyId" bbb="'+full.PROCINSTID+'"  bxlx="'+full.BXLX+'" name="keyId" value="' + data + '" ccywguid="'+full.CCYWGUID+'">';
	       	    },"width":10,'searchable': false,"class":"text-center"},//0
	       	    
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		
	       		{"data": "SHZTMC",defaultContent:""},
	       		{"data": "DJBH",defaultContent:""},
	       	   	{"data": "BXLX",defaultContent:"","class":"text-left"},
	       	 	{"data": "BXRMC",defaultContent:""},
	       	 	{"data": "SZBMMC",defaultContent:""},
	       	 	{"data": "CZRQ",defaultContent:"","class":"text-left"},
	       	 	{"data": "BXZJE",defaultContent:"","class":"text-right"},
	       	    {"data": "GUID","class":"text-center",'render':function(data, type, full, meta){
	 	   		
	 	   			return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';
	 	   		
	       },orderable:false,"width":80}
	       	   	
	       	];
	
 	//表数据
   	table = getDataTableByListHj("mydatatables",
   				"${ctx}/zkylbx/getbxywcxPageList?treedwbh=${dwbh}&shzt1="+shzt,[3,'desc'],columns);
	       	
 	//查看
 	$(document).on("click",".btn_look",function(){
  		     var bxlx = $(this).parents("tr").find("[name='keyId']").attr("bxlx");
  		     var guid = $(this).parents("tr").find("[name='keyId']").attr("value");
  		     var ccywguid = $(this).parents("tr").find("[name='keyId']").attr("ccywguid");
  		     var procinstid = $(this).parents("tr").find("[name='keyId']").attr("bbb");
   	     if(bxlx=="日常报销"){
   	    		location.href="${ctx}/wsbx/rcbx/goViewJsp?operate=CK&operate=L&look=look&guid="+guid+"&procinstid="+procinstid;

   	     }else if(bxlx=="差旅费报销"){
   	    	
   	    		location.href = "${ctx}/wsbx/ccbx/goViewJsp?guid="+guid+"&look=look&procinstid="+procinstid+"&ccywguid="+ccywguid;

   	     }else if(bxlx=="公务接待报销"){
   	    	 	//guid="+guid+"&link=sq${ctx}/wsbx/gwjdfbx/gwjdfbxsq
   	    	 location.href = "${ctx}/wsbx/gwjdfbx/gwjdfbxsq/pageSkip?guid="+guid+"&pageName=gwjdmx_view&procinstid="+procinstid+"&link=bxywcx";
   	     }
   	     
   	     
  	     //doOperate("${ctx}/gwjdfsq/goEditPage?guid="+guid+"&shztm="+"${param.shztm}","U");
 
   	});
    /*  $("#btn_look").click(function(){
    	var val = $(this).find("[name='xmlx']").val();
    	console.log("xmlx=========="+xmlx);

         // doOperate("${ctx}/grjfsz/goEditPage?dwbh=${param.dwbh}","C");//operateType=C
          
    });  */
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
   			doOperate("${ctx}/kylbx/goEditPage?dwbh="+dwbh+"&djbh="+DJBH,"U");
   		}else{
   			doOperate("${ctx}/wsbx/rcbx/ywbl?dwbh="+dwbh+"&djbh="+DJBH,"U");
   		}
	});
   	//复核
  	$(document).on("click",".btn_fhxx",function(){
   		var dwbh = $(this).attr("dwbh");
		doOperate("${ctx}/grjfsz/goFhPage?dwbh="+dwbh,"U");
		parent.location.reload();
	});
   	
   	//审核状态改变
  	$("#shzt").change(function(){
  		tableRelod(table);
  	});
   	
    var tableRelod = function(table) {
		var shzt=$("[id='shzt']").val();
  	    table.ajax.url("${ctx}/zkylbx/getbxywcxPageList?treedwbh=${dwbh}&shzt1="+shzt);
  	    table.ajax.reload();
  	};
  	
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
   	   			doDel("dwbh="+dwbh.join("','"),"${ctx}/grjfsz/doDelete",function(val){
//    	   				parent.location.href = "${ctx}/window/mainDwTree?pageUrl=/dwb/goDwbPage&treeJson=/glqxb/qxdwTree";
						location.reload();
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
		select_commonWin("${pageContext.request.contextPath}/webView/fzgn/jcsz/jsxxsz/txl_imp3.jsp", "导入经费信息", 450,350);
		return false;
	});
   	//Excel
//    	$("#btn_exp").click(function(){
//    		var shzt=$("[name='shzt']").val();
//    		var djbh=$("[name='djbh']").val();
//    		var bxr=$("[name='bxrmc']").val();
//    		var json = searchJson("searchBox");
//    		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 		var id = [];
// 		checkbox.each(function(){
// 			id.push($(this).val());
// 		});
//    		doExp(json,"${ctx}/zkylbx/expExcelbxywcx?treedwbh=${dwbh}&id="+id.join("','")+"&shzt="+shzt+"&djbh="+djbh+"&bxr="+bxr,"报销业务","${pageContext.request.contextPath}",id.join("','"));
//    	});
   	//导出Excel
   	 $(document).on("click","#btn_exp",function(){
   		var shzt=$("[id='shzt']").val();
		var djbh=$("[name='djbh']").val();
		var bxrmc=$("[name='bxrmc']").val();
		var json = searchJson("searchBox");
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
 		doExp("","${ctx}/zkylbx/expExcel6?treedwbh=${dwbh}&shzt="+shzt+"&djbh="+djbh+"&bxrmc="+bxrmc,"报销业务","${pageContext.request.contextPath}",id);
 		
   		
// 		var treedwbh="${dwbh}";
// 		var checkbox = $("#mydatatables").find("[name='keyId']").filter(":checked");
// 		if (checkbox.length > 0) {
// 				var guid = [];
// 				checkbox.each(function() {guid.push($(this).val());});
// 				$.ajax({
// 					type : "post",
// 					data : {guid:guid.join(","),treedwbh:treedwbh,shzt:shzt,djbh:djbh,bxr:bxr},
// 					url : "${ctx}/zkylbx/expExcel6",
// 					success : function(val) {
// 						  FileDownload("${ctx}/file/fileDownload","报销业务.xls",val.url);
// 					}
// 				});
// 			} else {
// 				alert("请至少选择一条信息导出!");
// 		}
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


