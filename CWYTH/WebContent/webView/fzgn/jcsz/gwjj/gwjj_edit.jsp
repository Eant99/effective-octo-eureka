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
<style type="text/css">
	.tool-fix{
		display:none;
	}
</style>
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
			   <button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>确定</button>
			</c:if>
			   <button type="button" class="btn btn-default" id="btn_back">返回</button>
        </div>
    </div>
	<div class="box">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>岗位交接</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">业务发起人</span>
							<input type="text" id="txt_ywfqr" readonly class="form-control input-radius" name="ywfqr" value="${gwjjb.YWFQR}"/>
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
							<input type="text" id="txt_yqxsyr" class="form-control input-radius window" name="yqxsyr" value="${gwjjb.YQXSYR}"/>
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
							<input type="text" id="txt_jgr" class="form-control input-radius window" name="jgr" value="${gwjjb.JGR}"/>
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
							<span class="input-group-addon" style="width: 85px;height: 25px;border-radius: 5px;border-right: 1px solid #ccc;">原权限所有人角色信息</span>
							<c:forEach var="jsxxList" items="${jsxxList}"> 
                            	<span name="s_jsxx"><input type="checkbox" disabled value="${jsxxList.JSBH}" checked/>${jsxxList.JSMC}&emsp;</span>
							</c:forEach>
						</div>
					</div>
				</div>
				<c:if test="${operateType == 'U' or operateType == 'C' }">
				<div class="row">
			    	<div class="col-md-12">
						<div class="alert alert-danger" style="font-size:13px;" role="alert">
							<strong >请注意：</strong><br/>
							&emsp;&emsp;岗位交接会将原权限所有人的管理权限，以及选定的角色全部移交给接岗人，同时会取消原权限所有人的管理权限，以及对应角色所赋予的操作权限！请谨慎操作！<br/>
						</div>
					</div>
				</div>
				</c:if>
			</div>
		</div>
	</div>
</form>
</body>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	//联想输入
	$("#txt_yqxsyr").bindChange("${ctx}/suggest/getXx","R");
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
		ywfqrdh:{validators:{notEmpty:{message:'不能为空'},phone:{message: '请输入有效的联系电话'}}},
		yqxsyr:{validators:{notEmpty:{message:'不能为空'}}},
		yqxsyrdh:{validators:{notEmpty:{message:'不能为空'},phone:{message: '请输入有效的联系电话'}}},
		jgr:{validators:{notEmpty:{message:'不能为空'}}},
		jgrdh:{validators:{notEmpty:{message:'不能为空'},phone:{message: '请输入有效的联系电话'}}},
		gwjjyy:{validators:{notEmpty:{message:'不能为空'}}}
    }});
	//保存按钮
	$("#btn_save").click(function(e){
		if($("#txt_yqxsyr").val() == $("#txt_jgr").val()){
			alert("原权限所有人和接岗人不能是同一个人！");
		}
		else{
			validate.bootstrapValidator("validate");
			if($('#myform').data('bootstrapValidator').isValid()){
				confirm("确定后原权限所有人的管理权限，以及选定的角色将全部移交给接岗人，请谨慎操作！确定移交吗？", {title:"提示"}, function(){
					var $jsxx = $("[name='jsxx']").filter(":checked");
					var jsxx = [];
					$jsxx.each(function(){
						jsxx.push($(this).val());
					});
					doSave(null,"myform","${ctx}/gwjj/doSave?jsxxs="+jsxx.join(","),function(val){
						$("#btn_back").click();
					});
				});
			}
		}
	});	
	//返回按钮
	$("#btn_back").click(function(e){
		window.location.href = "${ctx}/gwjj/goGwjjListPage";
		return false;
	});
	//查看页面
	if($("[name='operateType']").val()=='L'){
		$("input,textarea").attr("readonly","true");
	}
});
//获取角色信息（选择原权限所有人调用）
function ryWindowCallBack(cid,rydom){
	if("txt_yqxsyr" == cid){
		$("#txt_yqxsyrdh").val(rydom.attr("lxdh"));
		var rybh = rydom.attr("rybh");
		$.ajax({
			type:"post",
			url:"${ctx}/ryb/getJsxx",
			data:"rybh="+rybh,
			success:function(val){
				$("span[name='s_jsxx']").remove();
				
				if(val.length == 0){
					$("#jsxx").append("<span name='s_jsxx' style='color:Red;height:25px;line-height:25px;padding-left:6px;'>原权限所有人没有角色信息</span>");
				}
				else{
					var html = "";
					for(var i = 0;i < val.length;i++){
						html += "<span name='s_jsxx' style='height:25px;line-height:25px;padding-left:6px;'><input type='checkbox' name='jsxx' checked value='"+val[i].JSBH+"'/>"+val[i].JSMC+"</span>";
					}
					$("#jsxx").append(html);
				}
			}
		});
	}
	else if("txt_jgr" == cid){
		$("#txt_jgrdh").val(rydom.attr("lxdh"));
	}
}
</script>
</html>