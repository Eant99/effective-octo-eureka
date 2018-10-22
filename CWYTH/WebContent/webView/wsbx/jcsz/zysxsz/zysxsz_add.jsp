<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>增加经济科目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style type="text/css">
	.tool-fix{
		display:none;
	}
	.radiodiv{
	    border: 1px solid #ccc;
	    border-top-right-radius: 4px;
		border-bottom-right-radius:4px;
/* 	    border-radius: 4px; */
	    height: 25px;
	    line-height: 25px;
	    padding-left: 6px;
	    
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post" >
	<input type="hidden" name="operateType" id="operateType" value="${operateType}">
	<input type="hidden" name="gid" value="${gid}" />
	<div class='page-header'>
        <div class="pull-right">		  
		   <button type="button" class="btn btn-default" id="btn_save">保存</button>	
		   <button type="button" class="btn btn-default" id="btn_back">返回</button>		   
        </div>
    </div>
	<div class="box" style="top:36px">
		<div class="box-panel">		
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>注意事项信息</div>
            	<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模块编号</span>
							<input type="text"  id="zysxsz_mkbh" class="form-control input-radius window" name="mkbh" value="">
							<span class="input-group-btn"><c:if test="${operateType == 'U' or operateType == 'C' }"></c:if><button type="button" id="btn_jfbykm1" class="btn btn-link btn-custom">选择</button></span>							
							
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>模块名称</span>
							<input type="text"  id="zysxsz_mkmc" class="form-control input-radius window" name="mkmc" value=""/>
						</div>
					</div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon">是否展示</span>
							<div class="radiodiv">
							<input type="radio"  id="txt_sfzs" name="sfzs" checked/>是
							<input type="radio"  id="txt_sfzs" name="sfzs" />否
							</div>
						</div>
					</div>
				</div>
				<div class="row">
					<div class="col-md-12">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>注意事项内容</span>
                            <textarea id="zysxsz_zysxnr" class="form-control" name="zysxnr" ></textarea>
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
	/* //选择框
	 $("#btn_jfbykm1").click(function(){
  		select_commonWin("${ctx}/kmsz/window?controlId=zysxsz_mkbh","模块信息","920","630");
   });  */
   $("#btn_jfbykm1").click(function(){
		select_commonWin("${ctx}/zysxsz/zdpage?controlId=zysxsz_mkbh","模块信息","720","630");
		//select_commonWin("${ctx}/window/zdpage?controlId=txt_zl","字典信息","720","630");
   });
	
	//返回按钮
	$("#btn_back").click(function(){
		window.location.href = "${ctx}/webView/wsbx/jcsz/zysxsz/zysxsz_list.jsp";
	});
	var validate = $('#myform').bootstrapValidator({fields:{
		mkbh:{validators: {notEmpty: {message: '模块编号不能为空'}}},
        mkmc:{validators: {notEmpty: {message: '模块名称不能为空'}}},
        zysxnr:{validators: {notEmpty: {message: '注意事项内容不能为空'}}}
        }
	      });
	$("#btn_save").click(function(){
		doSave1(validate,"myform","${ctx}/Jjkm/doSave",function(val){
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
	
});

</script>

</html>