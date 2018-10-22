<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/webView/include/taglib.jsp"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<title>地点信息</title>
<link href="${ctxStatic}/css/bootstrap/bootstrap.min.css" rel="stylesheet"/>
<link href="${ctxStatic}/css/bootstrap/bootstrap-editable.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/bootstrap/jquery.fileupload-ui.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/bootstrap/light-theme.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/bootstrap/index.css" rel="stylesheet" type="text/css" />
<link href="${ctxStatic}/css/bootstrap/bootstrap-datetimepicker.css" rel="stylesheet" />
<link href="${ctxStatic}/css/bootstrap/fileinput.css" rel="stylesheet" />
<link href="${ctxStatic}/css/bootstrap/iconfont.css" rel="stylesheet" type="text/css"/>
<link href="${ctxStatic}/js/plugins/validator/bootstrapValidator.css"/><!--前端验证框架样式文件-->
<style type="text/css">
   .input-group[class*=col-]{
       float:left;
       margin-bottom:5px;
       height: 42px;
   }
   .input-group-addon{
       padding:0 6px;
       vertical-align:top;
   }
   .input-group-btn{
   	   vertical-align:top;
   }
   .box{
        border-radius: 8px;
        border: 1px solid #F3F3F3;
   }
   .input-group-addon:first-child{
       	background-color:transparent;
       	border:none;
       	min-width:88px;
       	font-size:12px;
       	text-align:right;
       	padding-top:5px;
   }
   .input-group-btn{
       	background-color:transparent;
       	border:none;
       	padding:0px 0px 0px 6px;
   }
   .btn{
       	font-size:12px;
   }
   .input-group .form-control:nth-child(2){
       	border-top-left-radius: 3px !important; 
       	border-bottom-left-radius: 3px !important;
   }
   .btn-custom{
   		height: 26px;
   		border-radius: 5px !important;
   		color: white;
   		background-color: #00ACEC;
   }
   .btn-link:focus, .btn-link:hover{
   		background-color: #00ACEC;
   }
   .help-block{
   		margin-top: 0px;
    	margin-bottom: 0px;
   }
   .data-span{
   		display: inline-block;
    	width: 25px;
    	height: 25px;
   }
</style>
</head>
<body class="contrast-blue">
<div id='wrapper'>
    <section id='content'>
	    <div class='container-fluid'>
	        <div class='row' id='content-wrapper'>
	            <div class='col-md-12'>
		       		<form id="dwxx_form" class="form-horizontal" action="" method="post" >
		                <div class='row'>
		                    <div class='col-md-12'>
		                        <div class="box">
		                            <div class="box-content">
		                                <div class="container-fluid">
		                                	<div class="row">
			                                	<div class="input-group col-md-4">
			                                         <label class="input-group-addon">数据列：</label>
			                                         <select>
			                                         	<option value="sjdd" bj="txt">上级地点</option>
			                                         	<option value="dwbh" bj="txt">所属单位</option>
			                                         </select>
			                                    </div>
			                                	<div class="input-group col-md-4">
			                                         <span class="input-group-addon">替换列：</span>
			                                         <input type="text" class="form-control input-sm" name="mc" value=""/>
			                                    </div>
		                                    </div>
		                                </div>
		                            </div>
		                        </div>
		                    </div>
		                </div>
	                </form>
	            </div>
	        </div>
	    </div>
    </section>
</div>
<script src="${ctxStatic}/js/jquery.js"></script>
<script src="${ctxStatic}/js/bootstrap/bootstrap.min.js"></script>
<script src="${ctxStatic}/js/main.js"></script>
<script src="${ctxStatic}/js/bootstrap-datetimepicker.js"></script>
<script src="${ctxStatic}/js/fileinput.js"></script>
<script src="${ctxStatic}/js/window.js"></script>
<script src="${ctxStatic}/js/plugins/validator/bootstrapValidator.js"></script><!--表单验证框架-->
<script type="text/javascript">
$(function(){
	//查看页面时处理函数
	if($("[name='operateType']").val()=='L'){
		$("input,select,textarea").attr("readonly","true");
		$("select").attr("disabled","true");
	}
	if($("[name='operateType']").val()!='L'){
		//日期控件
		$(".datetimepicker").datetimepicker({
		    format: "yyyy-mm-dd",
		    autoclose: true,
		    todayBtn: true,
		    todayHighlight: true,
		    minView: "month"
		});
	}
	//返回按钮
	$("#back").click(function(e){
		window.location.href  = "${ctx}/dwxx/findDwxxList";
		return e.preventDefault();
	});
	var validate = $('#dwxx_form').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
            dwbh: {
                validators: {
                    notEmpty: { 
                        message: '不能为空'
                    }
                }
            },
        }
    });
	//保存按钮
	$("#save").click(function(e){
		validate.bootstrapValidator("validate");
		var valid = $('#dwxx_form').data('bootstrapValidator').isValid();
		var index;
		if(valid){
			$.ajax({
				type:"post",
				url:"${ctx}/dwxx/saveDwxx",
				data:$("form").serialize(),
				success:function(val){
					Json = getJson(val);
					val = $.trim(val);
					val = JSON.getJson(val);
					if(val.success == 'T'){
						top.layer.alert(val.msg);
						$("#operateType").val("U");
					}else if(val.success == 'F'){
						top.layer.alert(val.msg);
						$("#bmh").val("");
						$("#bmh").focus();
					}
				},
				error:function(val){
					alert("发生了未知的错误，请联系管理员！！");
				},
				beforeSend:function(val){
					index = loading(2);
				}
			});
		}
	});
});
</script>
</body>
</html>