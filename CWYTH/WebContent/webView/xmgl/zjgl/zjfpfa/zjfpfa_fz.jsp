<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>资金分配方案详细信息</title>

<%@include file="/static/include/public-manager-css.inc"%>
<style type='text/css'>
.bttn{
    background-color: #00acec;
    color: white;
}
table{
	border-collapse: collapse!important;
}
</style>

</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${zjfpfa.guid}">
	<input type="hidden" name="czr"  value="${loginId}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑资金分配方案信息</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>		
			
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>批量赋值</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<table id="mydatatables" class="table table-striped table-bordered yc">
				<tr>
				<td>
				<div class="input-group">
							<span class="input-group-addon">资金编号</span>
							<input type="text" id="txt_zjbh" name="zjbh" class="form-control input-radius" onkeyup="value=value.replace(/(\W)/g,'');"/>
						</div>
				</td>
				</tr>
				<tr>
				<td>
				<div class="input-group">
							<span class="input-group-addon">资金来源</span>
							<select id="txt_zjly" class="form-control input-radius" name="zjly">					
									<option value="01" <c:if test="${dmb.dmsx == '2'}">selected</c:if>>纳入财政管理的资金</option>
									<option value="02" <c:if test="${dmb.dmsx == '1'}">selected</c:if>>企业资助资金</option>
									<option value="03" <c:if test="${dmb.dmsx == '2'}">selected</c:if>>学校自筹资金</option>
									<option value="04" <c:if test="${dmb.dmsx == '1'}">selected</c:if>>财政拨款</option>
									<option value="05" <c:if test="${dmb.dmsx == '1'}">selected</c:if>>其它资金</option>								
								</select>
						</div>
				</td>
				</tr>
				<tr>
				<td>
				<div class="input-group">
							<span class="input-group-addon">分配金额</span>
							<input type="text" id="txt_fpje" name="fpje" class="form-control input-radius text-right number"/>
						</div>
				</td>
				
				</tr>						
				</table>
			</div>
		</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">


$(function(){
	//保存按钮
	$("#btn_save").click(function(){
		var zjbh=$("#txt_zjbh").val();
		var zjly=$("#txt_zjly").val();
		var fpje=$("#txt_fpje").val();
		getIframWindow("${param.pname}").fz(zjbh,zjly,fpje);
		var winId = getTopFrame().layer.getFrameIndex(window.name);
		close(winId);
	});
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/xmgl/zjgl/zjfpfa/zjfpfa_list.jsp";
	});
	//提交按钮
	$("#btn_commit").click(function(){
		alert("提交成功");
	});
	//删除按钮
	$("#btn_del").click(function(){
		//var checkbox = $("#mydatatables").find("[name='guid']").filter(":checked");
		var a=$("[class^=keyId]").filter(":checked");
		
		if(a.length==0){
			alert("请至少选择一条记录删除");
			return;
		}
		$.each(a,function(){
			$(this).parents("tr").parents("table").remove();
		});
		
		
		
		
		
	});
	//选择按钮
	$("#btn_select").click(function(){
		select_commonWin("${ctx}/webView/xmgl/zjgl/zjfpfa/xm_list.jsp","项目信息","1120","630");
	});
	
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&rybh=${ryb.rybh}"
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016"
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016"
	    	}
		 }
	});
	
});

</script>
</body>
</html>