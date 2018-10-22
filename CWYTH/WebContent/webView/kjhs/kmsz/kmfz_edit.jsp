<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>数据字典信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
</head>
<body style="background-color: white;">
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden"  name="dmxh" value="${dmb.DMXH}"/>
	<input type="hidden"  id="jb"  name="jb" value="${dmb.jb}"/>
	<div class="container-fluid dialog-body">
		<div class="row">
			<div class="col-sm-3">
				<div class="input-group">
					<span class="input-group-addon">科目编号</span>
					<input type="text" id="btn_kmbh1" class="form-control input-radius" name="kmbh1"  value="${kmfz.kmbh1}"/>
					<span class="input-group-btn">
								<button type="button" id="btn_kmbh" class="btn btn-link btn-custom">选择</button>
							</span>
				</div>
				
			</div>
		</div>
		<div class="row">
			<div class="col-sm-3">
				<div class="input-group">
					<!-- 带有必填项的label元素 -->
					<span class="input-group-addon">数据来源科目</span>
					<input type="text" id="txt_sjlykm" class="form-control input-radius" name="sjlykm"  value="${kmfz.sjlykm}"/>
				<span class="input-group-btn">
								<button type="button" id="btn_sjlykm" class="btn btn-link btn-custom">选择</button>
				</span>
				</div>
				
			</div>
		</div>
		<div class="row">
			<div class="col-sm-6">
						<div class="input-group">

							<span class="input-group-addon">复制从源代码：</span>
                            
                            <input type="text" id="txt_qskm" class="form-control input-radius window" name="qskm" value="${kmfz.qskm}">
							<span class="input-group-btn">
								<button type="button" id="btn_qskm" class="btn btn-link btn-custom">选择</button>
				</span>
						</div>
						
			</div>
			&emsp;&ensp;到
			<div class="col-sm-6">
						<div class="input-group">
								<span class="input-group-btn">
							<button type="button" id="btn_zzkm" class="btn btn-link btn-custom">选择</button>
				</span>
						<input type="text" id="txt_zzkm" class="form-control input-radius window" name="zzkm" value="${kmfz.zzkm}">
							<span class="input-group-addon">之间的科目</span>
						</div>
						
			</div>
		</div>
		<div class="row">
				<div class="col-sm-12">
					<div class="input-group">
						<span class="input-group-addon">复制目的科目</span>
                        <input type="text" id="txt_fzmdkm" class="form-control input-radius window" name="fzmdkm" value="${kmfz.fzmdkm}">
							<span class="input-group-btn">
								<button type="button" id="btn_fzmdkm" class="btn btn-link btn-custom">选择</button>
				</span>
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
<script >
$(function(){
	$("#btn_cancelw").click(function(){
		getIframWindow("${param.pname}").table.ajax.reload();
		var winId = getTopFrame().layer.getFrameIndex(window.name);
    	close(winId);
	});	
	
	//科目编号
	$("#btn_kmbh").click(function(){	
   		select_commonWin("${ctx}/pzlx/window?controlId=btn_kmbh1","科目信息","920","630");
    });
	//数据来源科目
	$("#btn_sjlykm").click(function(){	
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_sjlykm","科目信息","920","630");
    });
	//数据源代码
	$("#btn_qskm").click(function(){	
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_qskm","科目信息","920","630");
    });
	//终止科目
	$("#btn_zzkm").click(function(){	
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_zzkm","科目信息","920","630");
    });
	//目的科目
	$("#btn_fzmdkm").click(function(){	
   		select_commonWin("${ctx}/pzlx/window?controlId=txt_fzmdkm","科目信息","920","630");
    });
	
	
});
var validate = $('#myform').bootstrapValidator({fields:{
    kmbh1:{validators: {notEmpty: {message: '科目编号不能为空'}}},
    sjlykm:{validators: {notEmpty: {message: '数据来源不能为空'}}},
    qskm:{validators: {notEmpty: {message: '起始科目不能为空'}}},
		zzkm  :{validators: {notEmpty: {message: '终止科目不能为空'}}},
		fzmdkm :{validators: {notEmpty: {message: '目的不能为空'}}},
    }
    });
$("#btn_save").click(function(){
	doSave1(validate,"myform","${ctx}/kmsz/doSave",function(val){
		if(val.success){
			alert(val.msg);
			parent.location.reload(true);
		}
	});
});
function doSave1(_validate, _formId, _url, _success, _fail){
	var index;
	var valid;
	if(_validate){
		_validate.bootstrapValidator("validate");
		valid = $('#' + _formId).data('bootstrapValidator').isValid();
	} else {
		valid = true;
	}
	if(valid){
		$.ajax({
			type:"post",
			url:_url,
			data:arrayToJson($('#' + _formId).serializeArray()),
			success:function(val){					
			},
			error:function(val){
				alert("保存成功！");					
			}	
		});
	}
}

</script>

</body>
</html>