<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>薪资上报审核列表页</title>
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
				<div class="form-group">
					<label>审核状态</label>
					<select style="width:150px;" class="form-control" id="status">
	               		<option value="0" <c:if test="${param.status=='0'}">selected</c:if>>未审核</option>
	               		<option value="1" <c:if test="${param.status=='1'}">selected</c:if>>已审核</option>
					</select>
				</div>
				<div class="form-group">
					<label>流水号</label>
					<input type="text" id="txt_djbh" class="form-control" name="fflsh" table="B" placeholder="请输入流水号">
				</div>
				<div class="form-group">
					<label>发放人员</label>
					<select style="width:150px;" class="form-control" id="rylx" name="ffry" table="B">
						<option value="">--请选择--</option>
						<option value="xs">学生</option>
						<option value="xnry">校内人员</option>
	                	<option value="xwry">校外人员</option>
					</select>
				</div> 
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
<input type="hidden" name="dual" value="0" />
<%@include file="/static/include/public-list-js.inc"%>
<script>
var suffix = "&mkbh=${param.mkbh}";
$(function () {
	var shzt = $("#status").val();
	//联想输入提示
	$("#btn_bmmc").bindChange("${ctx}/suggest/getXx","D");
	//列表数据
    var columns = [
       {"data": "GUID",orderable:false, 'render': function (data, type, full, meta){
         return '<input type="checkbox" name="guid" shzt="'+full.SHZTDM+'" procinstid="'+full.PROCINSTID+'" class="keyId"  ffry="'+full.FFRY+'"   value="' + data + '" guid = "'+full.GUID+'">';
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
       {"data": "GUID",'render':function(data, type, full, meta){
 			if(shzt=="0"){
 				return '<a href="javascript:void(0);" class="btn btn-link btn_check an" guid = "'+full.GUID+'" procinstid="'+full.PROCINSTID+'"  shzt="'+full.SHZT+'" xmmc="'+full.XMMC+'" xmbh=="'+full.XMBH+'" money="'+full.FFZJE+'" bxr="'+full.JBR+'">审核</a>|<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+full.PROCINSTID+'" >办理记录</a>|<a href="javascript:void(0);" class="btn btn-link btn_view an" procinstid="'+full.PROCINSTID+'" guid = "'+full.GUID+'" >详情</a>';
 			}else{
 				return '<a href="javascript:void(0);" class="btn btn-link btn_bljl" procinstid="'+full.PROCINSTID+'" >办理记录</a>|<a href="javascript:void(0);" class="btn btn-link btn_view an" procinstid="'+full.PROCINSTID+'" guid = "'+full.GUID+'">详情</a>';
 			}
	
		},orderable:false,"width":140}
     ];
  table = getDataTableByListHj("mydatatables","${ctx}/xzsblr/getShPageList?status=${param.status}",[2,'desc'],columns,0,1,setGroup);
  	//查看按钮
	   $(document).on("click",".btn_look",function(){
		   var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
	   	   var guid = $(this).parents("tr").find(":checkbox").val();
		   doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid+"&ymbz=sh","L");
   	});
	   //办理记录
	   	$(document).on("click",".btn_bljl",function(){
	   		var processInstanceId=$(this).attr("procinstid");
	   		doOperate("${pageContext.request.contextPath}/process/processLs?processInstanceId="+processInstanceId+"&type=fwdb"+suffix,"C");
	   		
	   	});
	 //打印
		$(document).on("click",".btn_print",function(){
	 		var dwbh = $(this).attr("dwbh");
	 		var procinstid = $(this).attr("procinstid"); 
	 		console.log("procinstid------------"+procinstid);
	 		doOperate("${ctx}/webView/srgl/xzsbcx/xzsbcx_print.jsp?guid="+dwbh+"&procinstid="+procinstid);
		});
		//详情
		$(document).on("click",".btn_view",function(){
			var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
	   	    var guid = $(this).parents("tr").find(":checkbox").val();
		    doOperate("${pageContext.request.contextPath}/xzsblr/goEditPage?rylx="+rylx+"&guid="+guid+"&ymbz=sh","L");
		});
	//审核
  	$(document).on("click",".btn_check",function(){
		var status = $("#status").find("option:selected").val();
		var rylx = $(this).parents("tr").find(":checkbox").attr("ffry");
		var shzt =  $(this).attr("shzt");
		var guid =  $(this).attr("guid");
		if(status=="1"){
			alert("不可重复审核！");
			return;
		}
		var procinstid = $(this).attr("procinstid");
		var src = "";
		var xmmc =  $(this).attr("xmmc");
		var money =  $(this).attr("money");
	 	if(shzt=="01"||shzt=="04"){//如果是报账员审核，跳转编辑页面
			var djbh = $(this).attr("djbh");
			var xmguid = "";
			var bxr = $(this).attr("bxr");
			$.ajax({//查询项目id
				type:"post",
				url:"${ctx}/wsbx/rcbx/getXmxx",
				data:{"zbid":guid},
				dataType:"json",
				async:false,
				success:function(val){
					$.each(val,function(index, n){
						xmguid = xmguid + n.XMGUID + ",";
					 });
					xmguid = xmguid.substring(0,xmguid.length-1);
					src += "${ctx}/xzsblr/goEditPage?";
					src += "&zbid="+guid+"&xmmc="+xmmc+"&xmguid="+xmguid+"&djbh="+djbh+"&money="+money+"&bz=delete&flag=1&bxr="+bxr+"&guid="+guid+"&procinstid="+procinstid;
				}
			});
		}else{ 
			src = "${pageContext.request.contextPath}/xzsblr/goEditPage?guid="+guid+"&action=sh&link=sh&procinstid="+procinstid+"&rylx="+rylx+"&operateType=S&ymbz=sh";
		}
		location.href = src;
   	});
  	//核算
  	$(document).on("click",".btn_hs",function(){
   			var guid = $(this).parents("tr").find(":checkbox").attr("guid");
   			var alert1 = "是否进行核算？";
   			confirm(alert1,"",function(){
		   			$.ajax({
		   	   			url:"${ctx}/xzsblr/doAccount",
		   	   			data:"guid="+guid,
		   	   			type:"post",
		   	   			async:"false",
		   	   			success:function(val){
		   	   				alert("操作成功！");
		   	   				table.ajax.reload();
		   	   			}
		   	   		});
		   		});   			
   			});
	//单条删除
	$(document).on("click",".btn_delxx",function(){
		var parentTr = $(this).parents("tr").find(":checkbox");
   		var shzt = parentTr.attr("shzt");
   		if(shzt == '未提交'){
	   		var guid = parentTr.attr("guid");
			confirm("确定删除？","",function(){
	   			$.ajax({
	   	   			url:"${ctx}/xzsblr/doDelete",
	   	   			data:"guid="+guid,
	   	   			type:"post",
	   	   			async:"false",
	   	   			success:function(val){
	   	   				alert("删除成功");
	   	   				table.ajax.reload();
	   	   			}
	   	   		});
	   		});
   		}else{
   			alert("已提交的数据和未审核的数据能删除!");
   		}
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
	$("#status").change(function(){
		var val = $(this).val();
		$("[name='dual']").val(val);
	});
});
$("#status").change(function(){
	val = $(this).val();
	location.href="${ctx}/webView/srgl/xzsbsh/xzsbsh_list.jsp?status="+val+"&djbh="+$("[name='djbh']").val()+"&xmguid="+$("[name='xmguid']").val();
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