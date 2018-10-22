<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>单位机构设置</title>
<%@include file="/static/include/public-list-css.inc"%>
<style>
.btn-link{
	padding: 0px;
	margin: 0px;
}
.dataTables_scrollHeadInner{
 		width:1000px ! important;
	} 
 	table.dataTable{ 
 		width:1000px ! important;
 	} 
</style>
</head>
<body>
<div class="fullscreen">
    <div class="search" id="searchBox">
    	<form id="myform" class="form-inline" role="form" action=""style="padding-top: 8px;padding-bottom: 2px">
    		<div class="search-simple">
				<div class="form-group">
					<label>事前审批类型</label>
<!-- 				<input type="text" id="txt_mc" class="form-control input-radius" table="K" name="sqsplx" placeholder="请输入事前审批类型"> -->
					<select id="txt_sqsplx" class="form-control input-radius"   >
						<option value="0" <c:if test="${sqsplx=='0'}">selected</c:if> >出差业务</option>
						<option value="1" <c:if test="${sqsplx=='1'}">selected</c:if> >公务接待业务</option>
					</select>
				</div>
				<div class="form-group">
					<label>审核状态</label>
<!-- 					<input type="text" id="txt_dwbh" class="form-control input-radius" name="shztmc" table="K" placeholder="请输入审核状态名称"> -->
					<select id="txt_shzt_cc" class="form-control input-radius" name="shzt"  >
						<option value="">全部</option>
						<c:forEach items="${shztList_cc}" var="item">
							<option value="${item.DM }">${item.MC }</option>
						</c:forEach>
					</select>
					<select id="txt_shzt_gw" class="form-control input-radius" style="display: none;" name="shzt"  >
						<option value="">全部</option>
						<c:forEach items="${shztList_gw}" var="item">
							<option value="${item.DM }">${item.MC }</option>
						</c:forEach>
					</select>
				</div>
				<div class="form-group">
					<label>单据编号</label>
					<input type="text" id="txt_djbh" class="form-control input-radius" name="djbh" placeholder="请输入单据编号">
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
				        
				        <th>审核状态</th>
				        <th>单据编号</th>
				        <th>申请人</th>
				        <th>所在部门</th>
				        <th>事前审批类型</th>
				        
				        <th>申请日期</th>
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
       				return '<input procinstid="'+full.PROCINSTID+'" type="checkbox" mc="'+full.SQSPLX+'" class="keyId" name="keyId" value="' + data + '">';
	       	    },"width":10,'searchable': false},//0
	       	    
	       		{"data":"_XH",orderable:false,'render': function (data, type, full, meta){
	       	   		return data;
	       		},"width":41,"searchable": false,"class":"text-center"},//1
	       		
	       		{"data": "SHZTMC",orderable:false,defaultContent:"","class":"shztmc"},
	       	   	{"data": "DJBH",defaultContent:"","width":150},
	       	 	{"data": "SQRMC",orderable:false,defaultContent:"","width":150},
	       	 	{"data": "SZBMMC",orderable:false,defaultContent:"","width":150},
	       	 	{"data": "SQSPLX",orderable:false,defaultContent:""},
	       	 	{"data": "SQRQ",defaultContent:""},
	       	 {"data": "GUID",'render':function(data, type, full, meta){
	 	   		var str = '<a href="javascript:void(0);" class="btn btn-link btn_view" >&nbsp;查看&nbsp;</a>'
	 	   		+'<a href="javascript:void(0);" class="btn btn-link btn_record">|&nbsp;办理记录</a>';
	 	   		if($("#txt_sqsplx").val()=='1'&&full.SHZT=='06'){
	 	   			str += '|<a href="javascript:void(0);" class="btn btn-link btn_print">打印</a>';
	 	   		}
	 	   		return str;
	       },orderable:false,"width":140,"class":"text-center"}
	       	];
	       	//表数据
          	table = getDataTableByListHj("mydatatables","${ctx}/sqspcx/getPageList?status="+$("#txt_shzt_cc").val()+"&djbh=${djbh}&sqsplx="+$("#txt_sqsplx").val(),[3,'desc'],columns);
    //查看
    $(document).on("click",".btn_view",function(){
 		var guid = $(this).parents("tr").find("[name='keyId']").val();
 		var type = $(this).parents("tr").find("[name='keyId']").attr("mc");
 		location.href="${ctx}/sqspcx/goViewPage?guid="+guid+"&type="+type;
 		
	});
  //打印
	$(document).on("click",".btn_print",function(){
 		var guid = $(this).parents("tr").find("[name='keyId']").val(); 
   		doOperate("${ctx}/gwjdfsq/demoPrint?guid="+guid);
	});
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
   		doExp("","${ctx}/sqspcx/expExcel?status="+$("#txt_shzt_cc").val()+"&djbh=${djbh}&sqsplx="+$("#txt_sqsplx").val(),"事前审批","${pageContext.request.contextPath}",id);
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
/* ------------------------------------------------------------------ */
var shzt = "${param.shzt}";
var sqsplx = "${param.sqsplx}";
var djbh = "${param.djbh}";
//初始化搜索栏内容
/* function iniSearchBox(){
	if(sqsplx == "1"){
		$("#txt_sqsplx").val(sqsplx);
		$("#txt_sqsplx").change();
	}
	$("[name='shzt']:visible").val(shzt);
	$("#txt_djbh").val(djbh);
} */
$(function(){
	$(document).ready(function(){
		iniSearchBox();
	})
	
// 	//事前审批类型改变
// 	$("#txt_sqsplx").change(function(){
// 		var sqsplx= $("#txt_sqsplx").val();
// 		location.href="${ctx}/sqspcx/goListPage?status="+shzt+"&djbh="+djbh+"&sqsplx="+sqsplx;
		
// 	});
	
	//事前审批类型改变
  	$("#txt_sqsplx").change(function(){
  		tableRelod(table);
  		$("#txt_shzt_cc").toggle();
  		$("#txt_shzt_gw").toggle();
  	});
   	
    var tableRelod = function(table) {
		var sqsplx=$("[id='txt_sqsplx']").val();
  	    table.ajax.url("${ctx}/sqspcx/getPageList?sqsplx="+sqsplx);
  	    table.ajax.reload();
  	};
	
// 	//点击搜索页面跳转
// 	$("#btn_search").click(function(){
// 		var shzt = $("[name='shzt']:visible").val();
// 		var sqsplx = $("#txt_sqsplx").val();
// 		var djbh = $("#txt_djbh").val();
// 		window.location = "${ctx}/sqspcx/goListPage?shzt="+shzt+"&sqsplx="+sqsplx+"&djbh="+djbh;
// 	})
	//办理记录
   	$(document).on("click",".btn_record",function(){
   		var shztmc = $(this).parents("tr").find(".shztmc").text();
   		if(shztmc == "未提交")	{
   			alert("未提交状态下不能查看办理记录！");
   			return;
   		}
   		var procinstid=$(this).parents("tr").find(".keyId").attr("procinstid");
   		var sqsplx = $("#txt_sqsplx").val();
   		var type;
   		if(sqsplx == "0"){
   			type = "ccyw";
   		}else if(sqsplx == "1"){
   			type = "gwjdsp";
   		}
   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+procinstid+"&type="+type,"C");
   	});
})
</script>
</html>

