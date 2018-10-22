<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资上报核算列表页</title>
<%@include file="/static/include/public-list-css.inc"%>
<%--表头样式--%>
<style type="text/css">
	.table-bordered > thead > tr > td, .table-bordered > thead > tr > th {
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
	.red{
		color:red;	
	}

</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
		<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
			<div class="search-simple">
			<%-- 	<div class="form-group">
					<label>审核状态</label>
					<select style="width:150px;" class="form-control" id="shzt" name="shzt" table="B">
	                	<option value="" <c:if test="${param.shzt=='00'}">selected</c:if>>全部</option>
	                	<option value="11" <c:if test="${param.shzt=='11'}">selected</c:if>>已提交</option>
	                	<option value="99" <c:if test="${param.shzt=='99'}">selected</c:if>>已通过</option>
	                	<option value="55" <c:if test="${param.shzt=='99'}">selected</c:if>>未通过</option>
	                	<option value="all" <c:if test="${param.shzt=='all'}">selected</c:if>>全部</option>
					</select>
				</div> --%>
				<div class="form-group">
					<label>流水号</label>
					<input type="text" id="txt_djbh" class="form-control" name="fflsh" table="B" placeholder="请输入流水号">
				</div>
			<!--  审核模块只能有校内人员  -->
			<!-- 	<div class="form-group">
					<label>发放人员</label>
					<select style="width:150px;" class="form-control" id="rylx" name="ffry" table="B">
						<option value="">--请选择--</option>
						<option value="xs">学生</option>
						<option value="xnry">校内人员</option>
	                	<option value="xwry">校外人员</option>
					</select>
				</div>  -->
				<button type="button" class="btn btn-primary" id="btn_search"><i class="fa icon-chaxun"></i>查询</button>
				<div class="btn-group pull-right" role="group">
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
				            <th>流水号</th>
				            <th>录入日期</th>
				            <th>发放方式</th>
				            <th>发放方案</th>
				            <th>摘要</th>
				            <th>财务项目</th>
				            <th>人员类型</th>
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
var suffix = "&mkbh=${param.mkbh}";
$(function () {
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" class="keyId"  ffry="'+full.FFRY+'"   value="' + data + '" guid = "'+full.GUID+'">';
       },"width":10,'searchable': false},
       {"data": "_XH",defaultContent:"","class":"text-center","width":20},
       {"data": "SHZTDM",defaultContent:"","class":"text-center"},
       {"data": "FFLSH",defaultContent:""},
       {"data": "LRSJ",defaultContent:""},
       {"data": "FFFS",defaultContent:""},
       {"data": "FFFA",defaultContent:""},
       {"data": "ZY",defaultContent:""},
       {"data": "XMMC",defaultContent:""},
       {"data": "RYLX",defaultContent:""},
       {"data": "PROCINSTID",'render':function(data, type, full, meta){
    	   if(data=='undefined'){
    		   return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>';  
    	   }else if(full.CHECKSHZT=="99"){
//     		   if(full.	FFRY=="xnry")
    			   if(full.JSBZ=="0"){
        			   return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>|<a href="javascript:void(0);" class="btn btn-link btn_hs">核算</a>'; 
    			   }else{
        			   return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>|<div class="red btn btn-link">已核算</div>';   
    			   }
//     		   }else{
//     			   return '<a href="javascript:void(0);" class="btn btn-link btn_look">查看</a>'; 
//     		   }
    	   }
      },orderable:false,"class":"text-center"}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/xzsblr/getHsPageList",[2,'desc'],columns,0,1,setGroup);
  	//查看按钮
	   $(document).on("click",".btn_look",function(){
		   var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
	   	   var guid = $(this).parents("tr").find(":checkbox").val();
		   doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid+"&ymbz=hs","L");
   	});
	 //打印
		$(document).on("click",".btn_print",function(){
	 		var dwbh = $(this).attr("dwbh");
	 		var procinstid = $(this).attr("procinstid"); 
	 		console.log("procinstid------------"+procinstid);
	 		doOperate("${ctx}/webView/srgl/xzsbcx/xzsbcx_print.jsp?guid="+dwbh+"&procinstid="+procinstid);
		});
	
  	//核算
  	$(document).on("click",".btn_hs",function(){
   			var guid = $(this).parents("tr").find(":checkbox").attr("guid");
//    			alert(guid);
//    			return; 	
   			var alert1 = "是否进行核算？";
   			confirm(alert1,"",function(){
		   			$.ajax({
		   	   			url:"${ctx}/xzsblr/doAccount",
		   	   			data:"guid="+guid,
		   	   			type:"post",
		   	   			async:"false",
		   	   			dateType:"json",
		   	   			success:function(val){
		   	   				alert(val);
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});   			
   			});


	//导出Excel
	$(document).on("click","#btn_exp",function(){
   		var json = searchJson("searchBox");
   		var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var guid = [];
		checkbox.each(function(){
			guid.push($(this).val());
		});
   		doExp(json,"${ctx}/wsbx/rcbx/expExcel?treedwbh=${dwbh}","日常报销","${ctx}",guid.join(","));
   	});
	shzt();
});
$(function() {	
	//列表右侧悬浮按钮	
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
	var shzt = $("[id='shztdm']").val();
	if(shzt=="00"||shzt=="all"){
		$(".no").css("display","");
	}else{
		$(".no").css("display","none");
	}
});
function shzt(){
	var json = searchJson("searchBox");
	$('#mydatatables').DataTable().search(json,"json").draw();
}
</script>
</body>
</html>