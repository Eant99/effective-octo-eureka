<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>项目授权管理</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
.radiodiv{
    border: 1px solid #ccc;
    border-top-right-radius:4px;
    border-bottom-right-radius:4px;
    height: 25px;
    line-height: 25px;
    padding-left: 6px;
}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="guid"  value="${guid}">
	<input type="hidden" name="xmbh"  value="${xmbh}">
	<input type="hidden" name="bmbh"  value="${bmbh}">
	<input type="hidden" name="ispl"  value="${ispl}">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" id="111111" name="guid" value="${guid}">
	<!-- 	被授权起止时间 -->
	<input type="hidden" name="bsqkssj"  value="${sjmap.KSSJ}"> 
	<input type="hidden" name="bsqjzsj"  value="${sjmap.JZSJ}">
	<input type="hidden" name="xmfzrbh"  value="${xmfzrbh}">
	<input type="hidden" name="loginbh"  value="${loginbh}">
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>项目授权管理</span>
		</div>
		<div class="pull-right">
			<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			<button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>项目授权编辑</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					 <div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目编号</span>
							    <input type="hidden" id="txt_guid" name="xmguid" value="${guid}">
                           		<input type="text" id="txt_xmbh"  class="form-control input-radius" readonly="readonly" name="xmbh" 
                           		<c:if test="${operateType == 'C' }"> value="${xmbh}"</c:if> 
                           		<c:if test="${ operateType == 'U' }"> value="${dwb.XMBH}"</c:if>
                           		/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>被授权人</span>
                            <input type="text" id="txt_bsqr" class="form-control input-radius window" name="bsqr" readonly="readonly" value="${dwb.bsqrmc1}"/>
                            <span class="input-group-btn"><button type="button" id="btn_sqdx" class="btn btn-link btn-custom">选择</button></span>
                            <input type="hidden" id="txt_bsqrs" class="form-control input-radius window" name="bsqrs" value="${dwb.BSQR}"/>
						</div>
					</div>
						<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>开始时间</span>
                            <input type="text" id="txt_kssj" class="form-control input-radius date" name="kssj" value="${dwb.KSSJ}" placeholder="开始时间"/>
                             <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                             </span>
						</div>
					</div>			
				</div>
				<div class="row">
					
						<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>截止时间</span>
                            <input type="text" id="txt_jzrq" class="form-control input-radius date" name="jzsj" value="${dwb.jzsj}" placeholder="截止日期"/>
                             <span class='input-group-addon'>
                            <i class="glyphicon glyphicon-calendar"></i>
                        </span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>是否允许二次授权</span>
                            <div class="radiodiv">
                            <c:if test="${operateType == 'C' }">
	                            <input type="radio" name="sfyxecsq" value="1"  > 是&ensp;
								<input type="radio" name="sfyxecsq" value="2" checked="checked"> 否
							</c:if>
							<c:if test="${operateType == 'U' }">
	                            <input type="radio" name="sfyxecsq" value="1"  <c:if test="${dwb.sfyxecsq=='1'}">checked="checked"</c:if>> 是&ensp;
								<input type="radio" name="sfyxecsq" value="2" <c:if test="${dwb.sfyxecsq=='2'}">checked="checked"</c:if>> 否
							</c:if>
                            </div>
                           
						</div>
					</div>	
			</div>
			<div class="row">
			<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">备&emsp;&emsp;注</span>
                             <textarea id="txt_bz" class="form-control"  name="bz" >${dwb.BZ}</textarea>
						</div>
					</div>
			</div>
		</div>
	</div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
function addTxry($checkbox){
	var name = "";
	$.each($checkbox,function(i){
		name = name + "("+$(this).find(".gh").text()+")";
		name = name + $(this).find(".xm").text();
		if($checkbox.length>(i+1)){
			name = name + ",";
		}
	})
	$("#txt_bsqr").val(name);
}
$(function(){
	//获取json数据
	$.getJSON("${ctx}/json/wsbx/jcsz/fykmdysz/fykmdyUpdate.json",function(data){
	});
	
	//验证方法
	var validate = $('#myform').bootstrapValidator({fields:{
		bsqr:{validators: {notEmpty: {message: '不能为空'}}},
		 kssj:{validators: {notEmpty: {message: '不能为空'}}},
		 jzsj:{validators: {notEmpty: {message: '不能为空'}}},
          }
	      });
	$("#btn_save").click(function(){
		var xmbh = $("[name='xmbh']").val();
		var bmbh = $("[name='bmbh']").val();
		var guid1 = $("[name='bsqr']").val();
		var ispl = $("[name='ispl']").val();
		var xmfzrbh = $("[name='xmfzrbh']").val();
		var loginbh = $("[name='loginbh']").val();
		var bsqkssj = $("[name='bsqkssj']").val();//当前操作者的被授权时间
		var bsqjzsj = $("[name='bsqjzsj']").val();//当前操作者的被授权时间
		var kssj1 = $("[name='kssj']").val();
		var jzsj1 = $("[name='jzsj']").val();
		var xmguid = $("[name='xmguid']").val();//项目的guid
		var kssj = $.trim(kssj1.replace('-',''));
		var jzsj = $.trim(jzsj1.replace('-',''));
// 		console.log("xmbh=" + xmbh + ",xmfzrbh="+xmfzrbh+",loginbh="+loginbh+",bsqkssj="+bsqkssj+",bsqjzsj="+bsqjzsj+",kssj="+kssj+",jzsj="+jzsj);
		if(xmfzrbh!=loginbh){
			if(bsqkssj<=kssj<=jzsj<=bsqjzsj){
				doSave(validate,"myform","${ctx}/xmsq/doSave?guid1="+guid1+"&ispl="+ispl+"&xmbh1="+xmbh+"&bmbh="+bmbh+"&xmguid="+xmguid,function(val){
					if(val.success){
						alert(val.msg);
						window.location.href  = "${ctx}/xmsq/getSq1PageList?operateType=U&guid=";
					}
				}); 
			}else {
				alert("开始时间和截止时间请在["+bsqkssj+"至"+bsqjzsj+"]时间内选择！");
			}
		}else{
			doSave(validate,"myform","${ctx}/xmsq/doSave?guid1="+guid1+"&ispl="+ispl+"&xmbh1="+xmbh+"&bmbh="+bmbh,function(val){
				if(val.success){
					alert(val.msg);
					window.location.href  = "${ctx}/xmsq/getSq1PageList?operateType=U&guid=";
				}
			});
		}
	});
	
	//授权对象弹窗
	 $("#btn_sqdx").click(function(){
			select_commonWin("${ctx}/window/rypage?xmbh=${param.xmbh}&controlId=bsqr&bmbh=${bmbh}&ymbz=xmsq","人员信息","1000","750");
// 			select_commonWin("${ctx}/xmsq/goJsxxPage?xmbh=${param.xmbh}&controlId=bsqr&bmbh=${bmbh}","人员信息","920","650");
	    }); 
// 	//负责人弹窗
// 		$(document).on("click","#btn_sqdx",function(){
// 			select_commonWin("${ctx}/xmsq/rypage?controlId=btn_sqdx","被授权人","920","630");
// 		});
	//返回按钮
	$("#btn_back").click(function(){
// 		window.location.href =  "${ctx}/webView/wsbx/jcsz/xmsq/xmsqgl.jsp";
        var xmbh = $("[name=xmbh]").val();
		var guid = $("#111111").val();
		window.location.href  = "${ctx}/xmsq/getSqPageList?operateType=C&guid="+guid+"&xmbh="+xmbh;
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