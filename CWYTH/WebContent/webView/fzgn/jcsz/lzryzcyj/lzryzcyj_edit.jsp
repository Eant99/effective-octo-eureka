<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>岗位交接</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<div class='page-header'>
        <div class="pull-left">
            <span class="page-title text-primary">
            	<i class='fa icon-xiangxixinxi'></i>
            	<c:choose>
            		<c:when test="${operateType == 'L'}">查看岗位交接信息</c:when>
            		<c:otherwise>编辑岗位交接信息</c:otherwise>
            	</c:choose>
			</span>
		</div>
        <div class="pull-right">
			<c:if test="${operateType == 'U' or operateType == 'C'}">
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
			</c:if>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>岗位交接
            	</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">业务发起人</span>
							<input type="text" id="txt_ywfqr" readonly class="form-control input-radius" name="ywfqr" value="${gwjjb.ywfqr}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							业务发起人电话</span>
							<input type="text" id="txt_ywfqrdh" class="form-control input-radius" name="ywfqrdh" value="${gwjjb.YWFQRDH}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							原权限所有人</span>
							<input type="text" id="txt_yqxsyr" class="form-control input-radius" name="yqxsyr" value="${gwjjb.YQXSYR}"/>
							<span class="input-group-btn">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<button type="button" id="btn_yqxsyr" class="btn btn-link btn-custom">选择</button>
								</c:if>
							</span>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							原权限所有人电话</span>
							<input type="text" id="txt_yqxsyrdh" class="form-control input-radius" name="yqxsyrdh" value="${gwjjb.YQXSYRDH}"/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							接&ensp;岗&ensp;人</span>
							<input type="text" id="txt_jgr" class="form-control input-radius" name="jgr" value="${gwjjb.JGR}"/>
							<span class="input-group-btn">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<button type="button" id="btn_jgr" class="btn btn-link btn-custom">选择</button>
								</c:if>
							</span>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							接岗人电话</span>
							<input type="text" id="txt_jgrdh" class="form-control input-radius" name="jgrdh" value="${gwjjb.JGRDH}"/>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							岗位交接原因</span>
							<textarea id="txt_gwjjyy" class="form-control" name="gwjjyy" >${gwjjb.GWJJYY}</textarea>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group" id="jsxx">
							<span class="input-group-addon" style="width: 85px;height: 25px;border-radius: 5px;border-right: 1px solid #ccc;">
								<c:if test="${operateType == 'U' or operateType == 'C' }">
									<span class="required">*</span>
								</c:if>
							请选择角色</span>
							<input type="hidden">
							<c:forEach var="jsxxList" items="${jsxxList}"> 
                            	<input type="checkbox" disabled value="${jsxxList.JSBH}" checked/>${jsxxList.JSMC}&emsp;
							</c:forEach>
						</div>
					</div>
				</div>
				<div class="row">
			    	<div class="col-md-12">
						<div class="alert alert-danger" style="font-size:13px;" role="alert">
							<strong >请注意：</strong><br/>
							&emsp;&emsp;岗位交接会将选定的角色以及该角色下的待办事项全部移交给接岗人，同时会取消申请人对应的角色权限和待办事项！请谨慎操作！<br/>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//联想输入
// 	$("#txt_yqxsyr").bindChange("${ctx}/suggest/getXx","R");
	$("#txt_jgr").bindChange("${ctx}/suggest/getXx","R");
	//弹窗
	$("#btn_yqxsyr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_yqxsyr","人员信息","920","630");
    });
	$("#btn_jgr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jgr","人员信息","920","630");
    });
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
		ywfqrdh:{validators:{ phone:{message: '请输入有效的联系电话'},}},
		yqxsyr:{validators:{notEmpty:{message:'不能为空'}}},
		jgr:{validators:{notEmpty:{message:'不能为空'}}},
		jgrdh:{validators:{ phone:{message: '请输入有效的联系电话'},}},
		gwjjyy:{validators:{notEmpty:{message:'不能为空'}}}
    }});
	//保存按钮
	$("#btn_save").click(function(e){
		var $jsxx = $("[name='jsxx']").filter(":checked");
		var jsxx = [];
		$jsxx.each(function(){
			jsxx.push($(this).val());
		});
		doSave(validate,"myform","${ctx}/gwjj/doSave?jsxxs="+jsxx.join(","),function(val){});
	});	
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href  = "${ctx}/gwjj/goGwjjListPage";
		return false;
	});
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,textarea").attr("readonly","true");
	}
	//刷新按钮
	$(".reload").click(function(){
		 var operateType =  $("[name='operateType']").val();
		 if(operateType=='U'){
			 window.location.href = window.location.href+"&operateType=U&dwbh=${dwb.DWBH}";
		 }else{
			 var url = window.location.href;
	    	if(url.indexOf("?")>0){
	    		window.location.href = window.location.href+"&gxgdzc_uuid=googosoft2016";
	    	}else{
	    		window.location.href = window.location.href+"?gxgdzc_uuid=googosoft2016";
	    	}
		 }
	});
});
//获取角色信息（选择原权限所有人调用）
function callBack(rybh){
	$.ajax({
		type:"post",
		url:"${ctx}/ryb/getJsxx",
		data:"rybh="+rybh,
		success:function(val){
			var html = "";
			for(var i = 0;i < val.length;i++){
				html += "&ensp;<input type='checkbox' name='jsxx' checked value='"+val[i].JSBH+"'/>"+val[i].JSMC+"&emsp;";
			}
			$("#jsxx").append(html);
		}
	}); 
}
</script>
</html>