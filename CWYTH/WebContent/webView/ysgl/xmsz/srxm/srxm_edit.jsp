<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>收入项目信息</title>
<%@include file="/static/include/public-manager-css.inc"%>
<style>
	.radio-group{
		height: 25px!important;
		line-height: 25px;
		vertical-align: middle;
		display: inline-block;
	}
	.radio-group > .rdo{
		margin: 0px 0px 0px 10px;
		height: 25px;
	}
</style>
</head>
<body>
<form id="myform" class="form-horizontal" action="" method="post">
	<input type="hidden" name="czr"  value="${loginId}">
	<input type="hidden" name="guid" value="${param.guid }"/>
	<div class="page-header">
		<div class="pull-left">
            <span class="page-title text-primary"><i class='fa icon-xiangxixinxi'></i>编辑收入项目信息</span>
		</div>
        <div class="pull-right">
            <button type="button" class="btn btn-default" id="btn_save" >保存</button>
			<button type="button" class="btn btn-default" id="btn_back" style="background:#00acec;color: white;">返回</button>
        </div>
	</div>
	<div class="box" style="top:36px">
		<div class="box-panel">
			<div class="box-header clearfix">
            	<div class="sub-title pull-left text-primary"><i class="fa icon-xiangxi"></i>收入项目信息</div>
            		<div class="actions">
            		<a href="#" class="btn box-collapse btn-mini btn-link"><i class="fa"></i></a>
            	</div>
        	</div>
			<hr class="hr-normal">
			<div class="container-fluid box-content">
				<div class="row">
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>收入项目编号</span>
							<input type="text" id="txt_srxmbh" name="srxmbh" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;" 
							value="${srxm.srxmbh }"/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>收入项目名称</span>
							<input type="text" id="txt_srxmmc" name="srxmmc" class="form-control input-radius" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"
							value="${srxm.srxmmc }"/>
						</div>
                    </div>
					<div class="col-md-4">
						<div class="input-group">
							<span class="input-group-addon"><span class="required">*</span>项目分类</span>
							<select id="txt_xmfl" class="form-control input-radius select2" name="xmfl">
                            	<option value="">未选择</option>
                               <c:forEach var="item" items="${xmflList}">
                           			<option value="${item.DM}" data-id="${srxm.xmfl }" <c:if test="${item.DM == srxm.xmfl }">selected</c:if>>${item.MC}</option>
                            	</c:forEach>
                            </select>
						</div>
                    </div>
				</div>
				<div class="row">
					<div class="col-md-4">
                    	<div class="input-group">
							<span class="input-group-addon">收入标准（万元）</span>
                            <input type="text" id="txt_srbz" name="srbz" class="form-control input-radius number1 text-right" style="border-bottom-right-radius: 4px;border-top-right-radius: 4px;"  
                            value="${srxm.srbz }"/>
                        </div>
					</div>
				</div>
				<div class="col-dm-18">
					<div class="input-group">
						<span class="input-group-addon">备注</span>
                        <textarea id="txt_bz"  class="form-control" name="bz" style=" height: 142px; width: 100%;">${srxm.bz }</textarea>
					</div>
				</div>
			</div>
         </div>
	</div>
</form>
<%@include file="/static/include/public-manager-js.inc"%>
<script type="text/javascript">
	var target = "${ctx}/ysgl/xmsz/srxm";
//保存方法	
function doSave1(_validate, $form, _url, _success, _fail){
	if(_validate){
		_validate.bootstrapValidator("validate");
		var valid = $form.data('bootstrapValidator').isValid();
		if(!valid){
			return;
		}
	}
	$.ajax({
		type:"post",
		url:_url,
		data:arrayToJson($form.serializeArray()),
		success:function(data){
			_success(data);
		},
		error:function(val){
			alert("抱歉，系统出现问题！");
		},
	});	
}
$(function(){
	//验证表单
	var validate = $('#myform').bootstrapValidator({fields:{
          srxmbh:{validators:{notEmpty: {message: '收入项目编号不能为空'},regexp: {regexp: /^[\w_]+$/,message: '收入项目编号只能包含大写、小写、数字和下划线'},stringLength:{message:'项目编号过长',max:'20'}}},
          srxmmc:{validators:{notEmpty: {message: '收入项目名称不能为空'}}},
          xmfl:{validators:{notEmpty: {message: '项目分类不能为空'}}},
          }
	      });
	//保存
   $("#btn_save").click(function(){
	 	//var url = ADDRESS+"/xmxx/add";
	   	var url = target+"/srxmEdit";
		doSave1(validate,$("#myform"),url,function(val){
			var result = JSON.getJson(val);
			if(result.success){
				alert("保存成功！");
			}
		});
	});
	//返回
	$("#btn_back").click(function(){
		window.location.href = target+"/goSrxmPage";
	});
	//刷新按钮
	$(".reload").click(function(){
		 window.location.reload();
	});
});
</script>
</body>
</html>