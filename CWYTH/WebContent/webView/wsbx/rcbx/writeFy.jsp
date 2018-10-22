<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
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
	.td_input{
		border:none;
		width:100%;
	}
	.text-green{
		color:green!important;
	}
	th{
		text-align:center;
	}
	.table>tbody>tr.selected:nth-of-type(odd) {
 	background-color: #f9f9f9!important;
	}
	.table>tbody>tr.selected:nth-of-type(even) {
 	background-color: #f3f3f3!important;
	}
	.required{
		color:red;
	}
</style>
</head>
<body>
<div class="fullscreen">
	<div class="search" id="searchBox" style="padding-top: 0px">
	<form id="myform" class="form-inline" action="" style="padding-top: 8px;padding-bottom: 2px;">
	<input type="hidden" name="operateType" value="C" />
			<div class='page-header'>
       <div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>第二步,费用填写</span>
		</div>
		<div class="pull-right" style="margin-top:3px;">
				<div class="sub-title pull-left text-green">
					<div style="width:26px; height:26px; background-color:green; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">1</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-green">选择项目&nbsp;></div>
					
					<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#00ACEC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">2</span>
	    			</div>
					</div>
					<div class="sub-title pull-left text-primary">费用填写&nbsp;></div>
					
					<div class="sub-title pull-left text--primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">3</span>
	    			</div>
					</div>
					<div class="sub-title pull-left  text-left">费用修改&nbsp;></div>
					
					<div class="sub-title pull-left text-primary">
					<div style="width:26px; height:26px; background-color:#CCCCCC; border-radius:13px;">
	        			 <span style="height:26px; line-height:26px; display:block; color:#FFF; text-align:center">4</span>
	    			</div>
					</div>
					<div class="sub-title pull-left">结算方式设置&nbsp;</div>
					&nbsp;&nbsp;&nbsp;&nbsp;
        </div>
    </div>
	<div class="container-fluid">
		<div class='responsive-table'>
			<div class='scrollable-area'>
				<hr style="border:none;">
				<div class="box-header1 clearfix" style="padding-top: 20px;">
	            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>费用填写</div>
	            	<div class="pull-right">
	            		<button type="button" class="btn btn-link" id="btn_ywsm" title="点击可查看业务说明">
							<i class="fa icon-help" style="color:#36b5f8;font-size:20px;"></i>
							<font style="font-size:14px;">业务说明</font>
						</button>
	<!-- 			  		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button> -->
				  		<button type="button" class="btn btn-default" id="btn_back">返回列表</button>
	        		</div>
	        	</div>
	        	<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr style="position: fixed; background-color: #f3f3f3">
				            <th style="width: 12%;">费用名称</th>
				            <th style="width: 12%;"><span class="required">*</span>报销金额(元)</th>
				            <th style="width: 8%;"><span class="required">*</span>附件张数(张)</th>
				            <th style="width: 34%;">描述</th>
				            <th style="width: 34%;">备注</th>
				        </tr>
					</thead>
	        	</table>
				<table id="mydatatables" class="table table-striped table-bordered">
		        	<thead>
				        <tr>
				            <th style="width: 12%;">费用名称</th>
				            <th style="width: 12%;"><span class="required">*</span>报销金额(元)</th>
				            <th style="width: 8%;"><span class="required">*</span>附件张数(张)</th>
				            <th style="width: 34%;">描述</th>
				            <th style="width: 34%;">备注</th>
				        </tr>
					</thead>
				    <tbody>
				    	<c:forEach var="list" items="${list}">
				    		<tr>
				           		<td>
				           		${list.fymc}
				           		<input type="hidden" name="fymc" value="${list.guid}" />
				           		<input type="hidden" name="zbid" value="${zbid}" />
				           		<input type="hidden" name="jflxdm" value="${jflxdm}" />
				           		</td>
				           		<td>
				           			<input type="text" name="bxje" class="td_input number" value="<fmt:formatNumber value="${list.bxje}"  pattern=".00"/>"
				           				style="text-align:right;" onkeyup="value=value.replace(/[^0-9.(\d{2})?$]/g,'');"/>
				           		</td>
				           		<td>
				           			<input type="text" name="fjzs" class="td_input int" style="text-align:right;" value="${list.fjzs}" />
				           		</td>
				           		<td>${list.ms=='null'?'':list.ms}</td>
				           		<td>
				           			<input type="text" name="bz" class="td_input" value="${list.bz=='null'?'':list.bz}" />
				           		</td>
				        	</tr>
				    	</c:forEach>
				    </tbody>
				</table>

				<br>
							<div class="container-fluid box-content">
				<div class="row">
					<div class="pull-center" style="text-align:center;">
			    		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button>
			    		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button>
			 		</div>
				</div>
			</div>
				<br>
				<br>
			</div>
		</div>
	</div>
	<br>
<!-- 	<div class="container-fluid box-content"> -->
<!-- 		<div class="row"> -->
<!-- 			<div class="pull-center" style="text-align:center;"> -->
<!-- 	    		<button type="button" class="btn btn-default" id="btn_after" style="background:#00acec;color: white;">上一步</button> -->
<!-- 	    		<button type="button" class="btn btn-default" id="btn_next" style="background:#00acec;color: white;">下一步</button> -->
<!-- 	 		</div> -->
<!-- 		</div> -->
<!-- 	</div> -->
</form>
</div>
</div>
<%@include file="/static/include/public-list-js.inc"%>
<script>
$(document).on("click","[id='btn_back']",function(){
	var look = "${look}";
	if(look=="look"){
		location.href="${ctx}/kylbx/gowdbxListPage";
	}else{
		location.href="${ctx}/wsbx/rcbx/goRcbxListPage?mkbh=${param.mkbh}";
	}
});
$(document).on("click","[id='btn_ywsm']:button",function(){
	zysx();
});
function zysx(){
	//业务说明
	select_commonWin("${pageContext.request.contextPath}/ywsm/getYwsmWin?mkbh=${param.mkbh}&sfbc=1","", "920", "530");
}
var tag = true;
var checkMsg = "";
var validate = $('#myform').bootstrapValidator({fields:{
    
}
});
$(function () {
  	//添加按钮
  	$("#btn_next").click(function(){
  		checkBxje();
  		checkFjzs();
  		var money = "${money}";
  		if(Number(money)<Number(checkBxje())){
			alert("报销金额不可大于项目剩余金额,剩余金额为"+money+"元");
			return;
		}
  		if(checkMsg.length>0){
  			alert(checkMsg);
  			checkMsg = "";
  			return;
  		}
  		var json = $('#myform').serializeObject("fymc","bz");  //json对象				
  		var jsonStr = JSON.stringify(json);  //json字符串
  		$.ajax({
  			url:"${ctx}/wsbx/rcbx/doSaveBxmx?zbid=${zbid}",
  			data:"jsonStr="+jsonStr,
  			dataType:"json",
  			type:"post",
  			async:false,
  			complete:function(){
  				location.href="${ctx}/wsbx/rcbx/ywbl?look=${look}&mkbh=${mkbh}&xmguid=${xmguid}&zbid=${zbid}&xmmc=${xmmc}&djbh=${djbh}&money=${money}&jflxdm=${jflxdm}";
  				tag = true;
  			}
  		});
  	});
});
$(function() {
	$("[id=btn_after]").click(function(){
		location.href = "${ctx}/webView/wsbx/rcbx/selectXm.jsp?xmguid=${xmguid}&mkbh=${mkbh}&zbid=${zbid}&djbh=${djbh}&look=${look}";
	});
// 	$("[id=btn_next]").click(function(){
// 		if(tag){
// 			location.href="${ctx}/wsbx/rcbx/ywbl?mkbh=${mkbh}&xmguid=${xmguid}&zbid=${zbid}&xmmc=${xmmc}";
// 		}else{
// 			checkBxje();
// 	  		checkFjzs();
// 	  		if(checkMsg.length>0){
// 	  			alert(checkMsg);
// 	  			checkMsg = "";
// 	  			return;
// 	  		}
// 	  		var json = $('#myform').serializeObject("fymc","bz");  //json对象				
// 	  		var jsonStr = JSON.stringify(json);  //json字符串
// 	  		$.ajax({
// 	  			url:"${ctx}/wsbx/rcbx/doSaveBxmx",
// 	  			data:"jsonStr="+jsonStr,
// 	  			dataType:"json",
// 	  			type:"post",
// 	  			success:function(val){
// 	  				location.href="${ctx}/wsbx/rcbx/ywbl?mkbh=${mkbh}&xmguid=${xmguid}&zbid=${zbid}&xmmc=${xmmc}";
// 	  			}
// 	  		});
// 		}
// 	});
	//列表右侧悬浮按钮
	$(window).resize(function(){
    	$("div.dataTables_wrapper").width($("#searchBox").width());
    	heights = $(window).outerHeight()-$(".search").outerHeight()-$(".row.bottom").outerHeight()-20-$(".dataTables_scrollHead").outerHeight();
    	$(".dataTables_scrollBody").height(heights);
    	table.draw();
	});
});
function checkBxje(){
	var count = 0;
	$("[name='bxje']").each(function(){
  		var $this = $(this);
  		var bxje = $this.val();
  		if(bxje==""||bxje==null||isNaN(bxje)){
  			bxje = 0;
  		}
  		count = Number(count)+Number(bxje);
  	});
	if(count==""||isNaN(count)||count==0||count<0){
		checkMsg += "报销金额必须大于0";
		return;
	}
	return count;
}
function checkFjzs(){
	var count = 0;
	$("[name='fjzs']").each(function(){
  		var $this = $(this);
  		var fjzs = $this.val();
  		if(fjzs==""||fjzs==null||isNaN(fjzs)){
  			fjzs = 0;
  		}
  		count = Number(count)+Number(fjzs);
  	});
	if(count==""||isNaN(count)||count==0||count<0){
		checkMsg += "附件张数必须大于0";
		return;
	}
	return parseInt(count);
}

</script>
</body>
</html>