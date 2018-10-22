<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8"/>
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title></title>
<%@include file="/static/include/public-list-css.inc"%>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
#bmmc {
	font-size: 20px; 
	margin-left: 410px;
	margin-top: 80px;
}
#ysbm {
	font-size: 20px; 
	margin-left: 410px;
	margin-top: 40px;
}
#bzrq_top {
	font-size: 20px; 
	margin-left: 410px;
	margin-top: 40px;
}
#title {
	margin-top: 120px;
/* 	margin-left: 80px; */
}
#bottom {
	margin-top: 50px;
	padding-left:20px;
}
#bmfzr {
	margin-left: 100px;
}
#jbr {
	margin-left: 80px;
}
#bzrq {
	margin-left: 80px;
}
#myform input,bk{
		border:none;
	
	    padding:4px !important;
	} 
</style>
</head>
<body>
	<form id="myform" class="form-inline" action="" method="post">
	<div class='page-header'>
    <div class="pull-left" role="group">
	<c:if test="${'sh'!=sh && 'ck'!=ck}">
		<button type="button" class="btn btn-default" id="btn_save"><i class="fa icon-save"></i>保存</button>
	</c:if>
	 <button type="button" class="btn btn-default" id="btn_back">返回</button>
	</div>
   
	<c:if test="${'sh'==sh}">
	<div class="btn-group pull-right" role="group">
<!-- 		<button type="button" class="btn btn-default " id="btn_tg"><i class="fa icon-save"></i>通过</button> -->
<!-- 		<button type="button" class="btn btn-default " id="btn_th"></i>退回</button> -->
		<button type="button" class="btn btn-default" id="btn_tg">通过</button>
  		<button type="button" class="btn btn-default" id="btn_th">退回</button>
	</div>
	</c:if>
	</div>
	<input type="hidden" name="year" value="${year}">
		<div id="title">
			<h1 align="center">${year}年度</h1>
			<h1 align="center">${xxxx}收支预算编制表</h1>
		</div>
		<div id="bmmc" class="form-group ">
			<span>部门名称：
				<c:if test="${operateType=='C' }">
					<input type="text" class=" input-radius" name="bmmc" readonly="readonly" id="txt_bmmc" value="${bmmc }">
				</c:if>
				<c:if test="${operateType=='U' }">
					<input type="text" class=" input-radius" name="bmmc" readonly id="txt_bmmc" value="${info.bmmc }">
				</c:if>
<!-- 				<button type="button" id="btn_dwmc" class="btn btn-link btn-custom">选择</button> -->
			</span>
		</div>
		<br>
		<div id="ysbm" class="form-group">
			<span>预算编码：
				<input type="text" class=" input-radius" name="ysbm"  id="txt_ysbm" value="${info.ysbm }">
			</span>
		</div>
		<br>
		<div class="form-group" id="bzrq_top">
			<span>申请时间：</span>
			<input type="text" id="txt_sqsj" class=" date" name="sqsj" value="${info.sqsj}">
		</div>
		<div id="bottom" style="text-align: left;">
			<span id="bmfzr">部门负责人：
			<c:if test="${operateType=='C' }">
				<input type="text" class=" input-radius" name="bmfzr" readonly id="txt_bmfzr" value="${info.bmfzr }">
			</c:if>
			<c:if test="${operateType=='U' }">
				<input type="text" class=" input-radius" name="bmfzr" readonly id="txt_bmfzr" value="${info.bmfzr }">
			</c:if>
				<button type="button" id="btn_bmfzr" class="btn btn-link btn-custom">选择</button>
			</span>
			<span id="jbr">经办人：
				<input type="text" class=" input-radius" name="jbr" readonly id="txt_jbr" value="${rymc}">
				<button type="button" id="btn_jbr" class="btn btn-link btn-custom">选择</button>
			</span>
			<span id="bzrq">编制日期：
				<input type="text" id="txt_bzrq" class=" date" name="bzrq" value="${info.bzrq}" >
			</span>
		</div>
		
	</form>
</body>
<script src="${pageContext.request.contextPath}/static/javascript/public/public-smsc.js"></script>
<%@include file="/static/include/public-list-js.inc"%>
<%@include file="/static/include/public-manager-js.inc"%>
</html>
<script type="text/javascript">
$(function(){
	if("${flag==1}"){
// 		alert("您已经进行过添加 ,已进入编辑页面！");
	}
	//弹框
	$("#btn_dwmc").click(function(){
		select_commonWin("${ctx}/window/dwpage?controlId=txt_bmmc","单位信息","920","630");
	});
	//弹框
	$("#btn_bmfzr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_bmfzr","人员信息","920","630");
    });
    //弹框
	$("#btn_jbr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_jbr","人员信息","920","630");
    });
	//保存按钮
	$("#btn_save").click(function(e){
		if($("#txt_bmmc").val()==""){
			alert("部门名称不能为空！");
		}else{
			doSave(validate,"myform","${ctx}/ysgl/bmyssb/doSave?operateType=C&dm=1&bzid=${bzid}",function(val){
				if(val.success){
					alert(val.msg);
// 					window.location.href  = "${ctx}/ysgl/bmyssb/goEditPage?dm=1&bzid=${bzid}";
				}
			}); 	
		}
	});
	//返回按钮
	$("#btn_back").click(function(e){
		var sh="${param.sh}";
		var ck="${param.ck}";
		parent.location.href  = "${ctx}/ysgl/bmyssb1/goBmyssbPage";
// 		if(sh==''||sh==null||sh==undefined){
// 			if(ck==''||ck==null||ck==undefined){
// 			parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
// 			}else{
// 				parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 			}
// 		}else{
// 		parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 		}
// 		if(sh=="sh"&&ck=="ck"){
// 			parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 			return;
// 		}
// 		if(sh=="sh"&&ck!="ck"){
// 			parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// 			return;
// 		}
// 		if(ck=="ck"&&sh!="sh"){
// 			parent.location.href  = "${ctx}/ysgl/bmyssb/goBmyssbPage";
// 		}
	});
	//必填验证
	var validate = $('#myform').bootstrapValidator({fields:{
		fzr:{validators:{notEmpty:{message:'不能为空'}}},
		xmmc:{validators:{notEmpty:{message:'不能为空'}}},
		nd:{validators:{notEmpty: {message: '不能为空'}}},
    }});
	
// 	//通过
// 	$(document).on("click","#btn_tg",function(){
// 		var guid = "${guid}";
// 		var SRYSHZ="${SRYSHZ}";
// 		var ZCYSHZ="${ZCYSHZ}";
// 		var procinstid="${procinstid}";
// 		$.ajax({
// 			type:"post",
// 			url:"${ctx}/ysgl/bmyssh/doApprove",
// 			data:"&procinstid="+procinstid+"&pass=true&guid="+guid+"&ZCYSHZ="+ZCYSHZ+"&SRYSHZ="+SRYSHZ,
// 			success:function(val){
// 				var data = JSON.getJson(val);
// 				if(data.success='true'){
// 					alert("操作成功！");
// // 					getIframWindow("${param.pname}").location.href="${ctx}/wsbx/sqspsh/goSqspsqPage";
// 					parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// // 					close(winId);
// 				}
// 			}
// 		});
//    	});
// 	//退回
// 	$(document).on("click","#btn_th",function(){
// 		var guid = "${guid}";
// 		var SRYSHZ="${SRYSHZ}";
// 		var ZCYSHZ="${ZCYSHZ}";
// 		var procinstid="${procinstid}";
// 			$.ajax({
// 				type:"post",
// 				url:"${ctx}/ysgl/bmyssh/doApprove",
// 				data:"&procinstid="+procinstid+"&pass=false&guid="+guid+"&ZCYSHZ="+ZCYSHZ+"&SRYSHZ="+SRYSHZ,
// 				success:function(val){
// 					var data = JSON.getJson(val);
// 					if(data.success='true'){
// 						alert("操作成功！");
// 						parent.location.href  = "${ctx}/ysgl/bmyssh/goBmysshPage";
// // 						getIframWindow("${param.pname}").location.href="${ctx}/wsbx/sqspsh/goSqspsqPage";
// // 						close(winId);
// 					}
// 				}
// 			});
//    	});
	//通过
	$(document).on("click","#btn_tg",function(){
		var guid = "${guid}";
		var SRYSHZ="${SRYSHZ}";
		var ZCYSHZ="${ZCYSHZ}";
		var procinstid="${procinstid}";
   		select_commonWin("${ctx}/ysgl/bmyssh/check?check=checktg&guid="+guid+"&procinstid="+procinstid+"&status=pass"+"&SRYSHZ="+SRYSHZ+"&ZCYSHZ="+ZCYSHZ,"审核通过","500","200");
   	});
	//退回
	$(document).on("click","#btn_th",function(){
		var guid = "${guid}";
		var SRYSHZ="${SRYSHZ}";
		var ZCYSHZ="${ZCYSHZ}";
		var procinstid="${procinstid}";
   		select_commonWin("${ctx}/ysgl/bmyssh/check?check=checkth&guid="+guid+"&procinstid="+procinstid+"&status=false"+"&SRYSHZ="+SRYSHZ+"&ZCYSHZ="+ZCYSHZ,"审核退回","500","200");
   	});
	
});
</script>