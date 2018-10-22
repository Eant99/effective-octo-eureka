<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>票据购买增加</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="C">
	<input type="hidden" name="ztgid" id="ztgid" value="${param.ztgid}">
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>银行科目</span>
                        <input type="text" id="txt_kmmc" class="form-control input-radius window" name="kmmc" value="${zffsdz.KMMC}">
                        <input type="hidden" id="txt_kmbh"  name="kmbh" value="${zffsdz.KMBH}">
						<span class="input-group-btn">
							<button type="button" id="btn_yhkm" class="btn btn-link btn-custom">选择</button>
						</span>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<!-- 带有必填项的label元素 -->
					<span class="input-group-addon"><span class="required">*</span>购买日期</span>
					<input type="text" id="txt_gmrq" class="form-control input-radius date" name="gmrq" placeholder="购买时间"/>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>购买人</span>
                        <input type="text" id="txt_gmr" class="form-control input-radius window" name="gmr" value="${dmb.GMRXM}">
						<span class="input-group-btn">
							<button type="button" id="btn_gmr" class="btn btn-link btn-custom">选择</button>
						</span>
				</div>
			</div>
			<div class="col-sm-6">
				<div class="input-group">
					<span class="input-group-addon"><span class="required">*</span>票据账户</span>
                        <input type="text" id="txt_zhmc" class="form-control input-radius window" name="zhmc" value="" readonly>
						<span class="input-group-btn">
							<button type="button" id="btn_pjzh" class="btn btn-link btn-custom">选择</button>
						 <input type="hidden" id="txt_pjlx" class="form-control input-radius window" name="pjlx" value=""/> 
						 <input type="hidden" id="txt_pjzhgid" class="form-control input-radius window" name="pjzhgid" value=""/> 
						</span>
				</div>
			</div> 
		</div>
		<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon">票据前缀</span>
                        <input id="txt_pjqz" class="form-control" name="pjqz" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon">票据张数</span>
                        <input id="txt_pjzs" class="form-control" name="pjzs" readonly/>
					</div>
				</div>
		</div>
		<div class="row">
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>票据起号</span>
                        <input id="txt_pjqh" class="form-control int " name="pjqh" />
					</div>
				</div>
				<div class="col-sm-6">
					<div class="input-group">
						<span class="input-group-addon"><span class="required">*</span>票据止号</span>
                        <input id="txt_qjzh" class="form-control int " name="qjzh" />
					</div>
				</div>
		</div>
	</div>
	<div class='page-bottom clearfix'>
        <div class="pull-right">
			<button type='button' class="btn btn-default" id="btn_save" style="display: <c:if test="${operateType == 'L'}">none</c:if>;">
				<i class="fa icon-save"></i>
				保存
			</button>
			<button type='button' class="btn btn-default btn-without-icon" id="btn_cancelw">
				取消
			</button>
        </div>
	</div>	
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
$(function(){
	$(document).on("keyup","#txt_pjqh, #txt_qjzh",function(){
		var pjqh = $("[name='pjqh']").val();
		var qjzh = $("[name='qjzh']").val();
		
		if(pjqh!=null&&pjqh!=''&&pjqh!='undefined'){
			if(qjzh!=null&&qjzh!=''&&qjzh!='undefined'){
				var pjzs = qjzh - pjqh + 1;
				$("[name='pjzs']").val(pjzs);
			}else{
				$("[name='pjzs']").val('');
			}
		}else{
			$("[name='pjzs']").val('');
		}
	});
	
	//点击保存
	$("#btn_save").click(function(){
		var pjqh =$("#txt_pjqh").val();
		var qjzh =$("#txt_qjzh").val();
		var kmmc =$("#txt_kmmc").val();
		var gmrq =$("#txt_gmrq").val();
		var gmr  =$("#txt_gmr").val();
		var zhmc =$("#txt_zhmc").val();
		var validate = $('#myform').bootstrapValidator({fields: {
			pjqh: {validators: {notEmpty: {message: '不能为空'}}},
			qjzh: {validators: {notEmpty: {message: '不能为空'}}},
			kmmc: {validators: {notEmpty: {message: '不能为空'}}},
			gmrq: {validators: {notEmpty: {message: '不能为空'}}},
			gmr:  {validators: {notEmpty: {message: '不能为空'}}},
			zhmc: {validators: {notEmpty: {message: '不能为空'}}}}
        });
		doSave(validate,"myform","${ctx}/pjgm/doSave",function(val){
			alert(val.msg);
			var pname = "${param.pname}";
			var win = getIframWindow(pname);
			var winId = getTopFrame().layer.getFrameIndex(window.name);
			getIframWindow("${param.pname}").table.ajax.reload();
	    	close(winId);
		},function(val){
		});
	});
	//上级代码弹窗    银行科目
	$("#btn_yhkm").click(function(){
		select_commonWin("${ctx}/fykmdysz/kjkmWindow?controlId=txt_kmmc&controlId1=txt_kmbh","科目信息","920","630");
    });
	
	//购买人
	$("#btn_gmr").click(function(){
		select_commonWin("${ctx}/window/rypage?controlId=txt_gmr","选择购买人","850","630");
    });
	//上级代码弹窗 --票据账户
	$("#btn_pjzh").click(function(){
		select_commonWin("${pageContext.request.contextPath}/pjgm/goPjzhPage?controlId=txt_zhmc&controlId1=txt_pjlx&controlId2=txt_pjzhgid","票据账户","720","600");
    });

	//关闭按钮
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
}); 
</script>
</body>
</html>